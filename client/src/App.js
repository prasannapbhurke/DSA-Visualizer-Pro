import React, { useEffect, useRef, useState, useCallback, lazy, Suspense } from 'react';
import './App.css';
import { buildTechniqueSteps, categories, getTechniqueById, getTechniquesForCategory, techniques } from './data/techniques';

const Dashboard = lazy(() => import('./components/Dashboard'));

const getApiBaseUrl = () => {
  const configuredUrl = process.env.REACT_APP_API_URL?.trim();
  if (configuredUrl) {
    return configuredUrl.replace(/\/+$/, '');
  }

  if (typeof window === 'undefined') {
    return 'http://localhost:5000';
  }

  const { protocol, hostname } = window.location;
  return `${protocol}//${hostname}:5000`;
};

const getGoogleClientId = () => (process.env.REACT_APP_GOOGLE_CLIENT_ID || '628188239936-0m8j5kh9hht76t5edo86mkfij7f6s5t2.apps.googleusercontent.com').trim();

const normalizeStepIndex = (steps, index) => {
  if (!steps.length) {
    return 0;
  }
  return Math.max(0, Math.min(index, steps.length - 1));
};

const createTechniqueCounts = () =>
  techniques.reduce((counts, technique) => {
    counts[technique.categoryId] = (counts[technique.categoryId] || 0) + 1;
    return counts;
  }, {});

/**
 * Main web visualizer shell.
 *
 * State ownership:
 * - selectedCategoryId / selectedTechniqueId: current catalog position.
 * - currentStepIndex / isPlaying: visualization timeline playback.
 * - shareState: generated or loaded shared visualization links.
 * - graphZoom: Canvas scale for graph/tree scenes.
 * - user: authenticated Google user returned by the backend.
 */
function App() {
  const [status, setStatus] = useState('Connecting to server...');
  const [selectedCategoryId, setSelectedCategoryId] = useState(categories[0].id);
  const [selectedTechniqueId, setSelectedTechniqueId] = useState('linear_search');
  const [currentStepIndex, setCurrentStepIndex] = useState(0);
  const [shareState, setShareState] = useState({ loading: false, url: '', message: '', activeShareId: '' });
  const [isPlaying, setIsPlaying] = useState(false);
  const [graphZoom, setGraphZoom] = useState(1);
  const [user, setUser] = useState(null);
  const [showTour, setShowTour] = useState(() => window.localStorage.getItem('dsa-tour-complete') !== 'true');
  const graphCanvasRef = useRef(null);

  const apiBaseUrl = getApiBaseUrl();
  const selectedTechnique = getTechniqueById(selectedTechniqueId);
  const categoryTechniques = getTechniquesForCategory(selectedCategoryId);
  const steps = buildTechniqueSteps(selectedTechniqueId);
  const activeStep = steps[normalizeStepIndex(steps, currentStepIndex)] || null;
  const techniqueCounts = createTechniqueCounts();

  const handleGoogleSignIn = useCallback(async (response) => {
    try {
      const res = await fetch(`${apiBaseUrl}/api/auth/google`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ idToken: response.credential }),
      });
      const data = await res.json();
      if (res.ok) {
        setUser(data.data.user);
        localStorage.setItem('token', data.data.token);
        setStatus('Authenticated');
      } else {
        setStatus('Authentication failed');
      }
    } catch (error) {
      setStatus('Authentication error');
    }
  }, [apiBaseUrl]);

  useEffect(() => {
    const controller = new AbortController();
    const storedToken = window.localStorage.getItem('token');

    fetch(`${apiBaseUrl}/status`, { signal: controller.signal })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Status request failed with ${res.status}`);
        }
        return res.json();
      })
      .then((payload) => setStatus(payload?.data?.status || 'Server connected'))
      .catch((error) => {
        if (error.name !== 'AbortError') {
          setStatus('Server error');
        }
      });

    if (storedToken) {
      fetch(`${apiBaseUrl}/api/me`, {
        signal: controller.signal,
        headers: { Authorization: `Bearer ${storedToken}` },
      })
        .then(async (res) => {
          const body = await res.json();
          if (!res.ok) {
            throw new Error(body?.error?.message || 'Session expired');
          }
          return body;
        })
        .then((payload) => {
          setUser(payload?.data?.user || null);
          setStatus('Authenticated');
        })
        .catch(() => {
          window.localStorage.removeItem('token');
          setUser(null);
        });
    }

    const initializeGoogle = () => {
      if (window.google) {
        window.google.accounts.id.initialize({
          client_id: getGoogleClientId(),
          callback: handleGoogleSignIn,
          auto_select: false, // Prevents automatic sign-in
          context: 'signin',
        });

        const buttonDiv = document.getElementById("google-signin-button");
        if (buttonDiv) {
          window.google.accounts.id.renderButton(buttonDiv, {
            theme: "outline",
            size: "large",
            shape: "rectangular",
            text: "signin_with"
          });
        }
      }
    };

    if (window.google) {
      initializeGoogle();
    } else {
      const script = document.querySelector('script[src="https://accounts.google.com/gsi/client"]');
      if (script) {
        script.addEventListener('load', initializeGoogle);
      }
    }

    return () => {
      controller.abort();
    };
  }, [apiBaseUrl, handleGoogleSignIn]);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const shareId = params.get('share');
    if (!shareId) {
      return;
    }

    const controller = new AbortController();
    setShareState((current) => ({ ...current, loading: true, message: 'Loading shared visualization...', activeShareId: shareId }));

    fetch(`${apiBaseUrl}/shared/${shareId}`, { signal: controller.signal })
      .then(async (res) => {
        const body = await res.json();
        if (!res.ok) {
          throw new Error(body?.error?.message || `Share request failed with ${res.status}`);
        }
        return body;
      })
      .then((payload) => {
        const shared = payload?.data?.share;
        const technique = getTechniqueById(shared?.techniqueId || 'linear_search');
        const sharedSteps = buildTechniqueSteps(technique.id);
        setSelectedCategoryId(technique.categoryId);
        setSelectedTechniqueId(technique.id);
        setCurrentStepIndex(normalizeStepIndex(sharedSteps, shared?.currentStep || 0));
        setShareState({
          loading: false,
          url: window.location.href,
          message: `Viewing shared visualization for ${shared?.techniqueTitle || technique.title}.`,
          activeShareId: shareId,
        });
      })
      .catch((error) => {
        if (error.name !== 'AbortError') {
          setShareState({
            loading: false,
            url: '',
            message: error.message || 'Unable to load shared visualization.',
            activeShareId: '',
          });
        }
      });

    return () => controller.abort();
  }, [apiBaseUrl]);

  useEffect(() => {
    if (!isPlaying || steps.length <= 1) {
      return undefined;
    }

    const timer = window.setInterval(() => {
      setCurrentStepIndex((current) => {
        if (current >= steps.length - 1) {
          setIsPlaying(false);
          return current;
        }
        return current + 1;
      });
    }, 1400);

    return () => window.clearInterval(timer);
  }, [isPlaying, steps.length]);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    params.set('technique', selectedTechniqueId);
    if (shareState.activeShareId) {
      params.set('share', shareState.activeShareId);
    } else {
      params.delete('share');
    }
    window.history.replaceState({}, '', `${window.location.pathname}?${params.toString()}`);
  }, [selectedTechniqueId, shareState.activeShareId]);

  useEffect(() => {
    const isGraphLike = selectedTechnique?.categoryId === 'trees' || selectedTechnique?.categoryId === 'graph';
    if (!isGraphLike) {
      return undefined;
    }

    const canvas = graphCanvasRef.current;
    if (!canvas) {
      return undefined;
    }

    const drawScene = () => {
      const rect = canvas.getBoundingClientRect();
      const dpr = window.devicePixelRatio || 1;
      const width = Math.max(320, rect.width);
      const height = Math.max(260, rect.height);
      canvas.width = Math.round(width * dpr);
      canvas.height = Math.round(height * dpr);
      const context = canvas.getContext('2d');
      if (!context) {
        return;
      }

      context.setTransform(dpr, 0, 0, dpr, 0, 0);
      context.clearRect(0, 0, width, height);

      const nodes = Array.isArray(activeStep?.nodes) ? activeStep.nodes : [];
      const edges = Array.isArray(activeStep?.edges) ? activeStep.edges : [];
      if (!nodes.length) {
        context.fillStyle = '#b8c8dd';
        context.font = '600 18px Segoe UI, sans-serif';
        context.textAlign = 'center';
        context.fillText('No graph/tree nodes found for this step.', width / 2, height / 2);
        return;
      }

      const toX = (node) => (Number(node.x) / 100) * width;
      const toY = (node) => (Number(node.y) / 100) * height;
      const focusedIndex = currentStepIndex % Math.max(1, nodes.length);

      context.save();
      context.translate(width / 2, height / 2);
      context.scale(graphZoom, graphZoom);
      context.translate(-width / 2, -height / 2);

      edges.forEach(([from, to]) => {
        const source = nodes.find((node) => String(node.id) === String(from));
        const target = nodes.find((node) => String(node.id) === String(to));
        if (!source || !target) {
          return;
        }
        context.beginPath();
        context.moveTo(toX(source), toY(source));
        context.lineTo(toX(target), toY(target));
        context.strokeStyle = 'rgba(135, 167, 214, 0.42)';
        context.lineWidth = 2;
        context.stroke();
      });

      nodes.forEach((node, index) => {
        const x = toX(node);
        const y = toY(node);
        const isFocus = index === focusedIndex;
        const radius = isFocus ? 30 : 25;
        const gradient = context.createLinearGradient(x, y - radius, x, y + radius);
        gradient.addColorStop(0, isFocus ? '#f97316' : '#1d3554');
        gradient.addColorStop(1, isFocus ? '#b45309' : '#0f1c2e');
        context.beginPath();
        context.arc(x, y, radius, 0, Math.PI * 2);
        context.fillStyle = gradient;
        context.fill();
        context.strokeStyle = isFocus ? '#ffd3b0' : 'rgba(135, 167, 214, 0.38)';
        context.lineWidth = isFocus ? 3 : 2;
        context.stroke();
        context.fillStyle = '#edf4ff';
        context.font = '700 16px Segoe UI, sans-serif';
        context.textAlign = 'center';
        context.textBaseline = 'middle';
        context.fillText(String(node.id), x, y);
      });

      context.restore();
    };

    drawScene();
    window.addEventListener('resize', drawScene);
    return () => window.removeEventListener('resize', drawScene);
  }, [activeStep, currentStepIndex, graphZoom, selectedTechnique]);

  const handleSelectCategory = (categoryId) => {
    setSelectedCategoryId(categoryId);
    const nextTechniques = getTechniquesForCategory(categoryId);
    if (nextTechniques.length) {
      setSelectedTechniqueId(nextTechniques[0].id);
      setCurrentStepIndex(0);
      setIsPlaying(false);
      setShareState((current) => ({ ...current, activeShareId: '', url: '' }));
    }
  };

  const handleSelectTechnique = (techniqueId) => {
    if (!getTechniqueById(techniqueId)) {
      setShareState((current) => ({
        ...current,
        message: `Technique not found: ${techniqueId}`,
      }));
      return;
    }
    setSelectedTechniqueId(techniqueId);
    setCurrentStepIndex(0);
    setIsPlaying(false);
    setShareState((current) => ({ ...current, activeShareId: '', url: '', message: current.message }));
  };

  const handleCreateShare = async () => {
    if (!selectedTechnique) {
      return;
    }
    setShareState((current) => ({ ...current, loading: true, message: 'Creating share link...' }));
    try {
      const response = await fetch(`${apiBaseUrl}/share-visualization`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          share: {
            techniqueId: selectedTechnique.id,
            techniqueTitle: selectedTechnique.title,
            categoryId: selectedTechnique.categoryId,
            currentStep: normalizeStepIndex(steps, currentStepIndex),
            note: activeStep ? activeStep.label : '',
          },
        }),
      });
      const payload = await response.json();
      if (!response.ok) {
        throw new Error(payload?.error?.message || 'Unable to create share link');
      }
      const shareId = payload?.data?.shareId;
      const shareUrl = `${window.location.origin}${window.location.pathname}?technique=${selectedTechnique.id}&share=${shareId}`;
      if (navigator.clipboard?.writeText) {
        await navigator.clipboard.writeText(shareUrl);
      }
      setShareState({
        loading: false,
        url: shareUrl,
        message: 'Share link created and copied.',
        activeShareId: shareId,
      });
    } catch (error) {
      setShareState({
        loading: false,
        url: '',
        message: error.message || 'Unable to create share link.',
        activeShareId: '',
      });
    }
  };

  const renderLinearCells = () => {
    const values = activeStep?.values || [];
    return (
      <div className="linear-strip">
        {values.map((value, index) => {
          const isFocus = activeStep.focusIndex === index;
          const isSecondary = activeStep.secondaryIndex === index;
          const isDone = (activeStep.doneIndices || []).includes(index);
          return (
            <div
              key={`${selectedTechniqueId}-${index}-${value}`}
              className={`linear-cell${isFocus ? ' is-focus' : ''}${isSecondary ? ' is-secondary' : ''}${isDone ? ' is-done' : ''}`}
            >
              <span className="linear-index">{index}</span>
              <strong>{value}</strong>
            </div>
          );
        })}
      </div>
    );
  };

  const handleGraphZoom = (event) => {
    event.preventDefault();
    const delta = event.deltaY > 0 ? -0.1 : 0.1;
    setGraphZoom(prev => Math.max(0.5, Math.min(3, prev + delta)));
  };

  const handleSignOut = () => {
    setUser(null);
    localStorage.removeItem('token');
    setStatus('Signed out');

    // Clear Google session hint to force account chooser on next login
    if (window.google) {
      window.google.accounts.id.disableAutoSelect();
    }
  };

  const renderGraphScene = () => {
    return (
      <div className="graph-scene-container" onWheel={handleGraphZoom}>
        <canvas ref={graphCanvasRef} className="graph-canvas" aria-label={`${selectedTechnique?.title || 'Graph'} visualization`} />
        <span className="graph-zoom-hint">Mouse wheel zoom: {graphZoom.toFixed(1)}x</span>
      </div>
    );
  };

  const renderVisualization = () => {
    if (!selectedTechnique) {
      return (
        <div className="empty-state">
          <strong>Element not found</strong>
          <span>This technique ID does not exist yet. Pick another topic from the list.</span>
        </div>
      );
    }
    if (selectedTechnique.categoryId === 'trees' || selectedTechnique.categoryId === 'graph') {
      return renderGraphScene();
    }
    const values = activeStep?.values || [];
    return values.length ? renderLinearCells() : (
      <div className="empty-state">
        <strong>No values found</strong>
        <span>This step has no array values, so there is nothing to draw yet.</span>
      </div>
    );
  };

  const finishTour = () => {
    window.localStorage.setItem('dsa-tour-complete', 'true');
    setShowTour(false);
  };

  return (
    <div className="App">
      <header className="App-header">
        <div>
          <p className="eyebrow">DSA Visualizer Web</p>
          <h1>Android parity and collaborative share mode</h1>
        </div>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
          {user ? (
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <span>Welcome, {user.name}</span>
              <button onClick={handleSignOut} style={{ padding: '0.5rem 1rem' }}>Sign Out</button>
            </div>
          ) : (
            <div id="google-signin-button"></div>
          )}
          <div className="header-chip">Backend: {status}</div>
        </div>
      </header>
      <main className="app-shell">
        <section className="hero-panel">
          <div>
            <p className="hero-kicker">Visualization parity</p>
            <h2>{selectedTechnique?.title || 'Technique not found'}</h2>
            <p>{selectedTechnique?.description || 'Choose a valid algorithm from the technique list to continue.'}</p>
          </div>
          <div className="hero-meta">
            <span>{selectedTechnique?.difficulty || 'Unknown'}</span>
            <span>{selectedTechnique?.categoryId?.replace('_', ' ') || 'missing'}</span>
            <span>{steps.length} guided steps</span>
          </div>
        </section>

        <Suspense fallback={<div className="loading-state">Loading dashboard...</div>}>
          <Dashboard
            categories={categories}
            selectedCategoryId={selectedCategoryId}
            techniqueCounts={techniqueCounts}
            onSelectCategory={handleSelectCategory}
          />
        </Suspense>

        <section className="content-grid">
          <aside className="technique-panel">
            <div className="panel-heading">
              <h3>Techniques</h3>
              <p>{categoryTechniques.length} mirrored web visualizations</p>
            </div>
            <div className="technique-list">
              {categoryTechniques.length ? categoryTechniques.map((technique) => (
                <button
                  key={technique.id}
                  type="button"
                  className={`technique-card${technique.id === selectedTechniqueId ? ' is-active' : ''}`}
                  onClick={() => handleSelectTechnique(technique.id)}
                >
                  <strong>{technique.title}</strong>
                  <span>{technique.description}</span>
                  <small>{technique.difficulty}</small>
                </button>
              )) : (
                <div className="empty-state is-small">
                  <strong>No techniques found</strong>
                  <span>This category is empty right now.</span>
                </div>
              )}
            </div>
          </aside>

          <section className="visualization-panel">
            <div className="visual-toolbar">
              <div>
                <p className="hero-kicker">Now doing</p>
                <h3>{activeStep?.label || 'Ready'}</h3>
              </div>
              <div className="toolbar-actions">
                <button type="button" onClick={() => setCurrentStepIndex((current) => Math.max(0, current - 1))}>
                  Previous
                </button>
                <button type="button" onClick={() => setIsPlaying((current) => !current)}>
                  {isPlaying ? 'Pause' : 'Play'}
                </button>
                <button
                  type="button"
                  onClick={() => setCurrentStepIndex((current) => Math.min(steps.length - 1, current + 1))}
                >
                  Next
                </button>
                <button type="button" className="share-button" onClick={handleCreateShare} disabled={shareState.loading}>
                  {shareState.loading ? 'Sharing...' : 'Share Link'}
                </button>
              </div>
            </div>

            <div className="visual-stage">
              {renderVisualization()}
            </div>

            <div className="step-card">
              <div className="step-topline">
                <span>Step {normalizeStepIndex(steps, currentStepIndex) + 1} of {Math.max(steps.length, 1)}</span>
                <span>{selectedTechnique?.title || 'Missing technique'}</span>
              </div>
              <p>{activeStep?.description}</p>
              <div className="badge-row">
                {(activeStep?.badges || []).map((badge) => (
                  <span key={badge} className="step-badge">{badge}</span>
                ))}
                {activeStep?.note ? <span className="step-badge is-note">{activeStep.note}</span> : null}
              </div>
            </div>

            <div className="timeline">
              {steps.map((step, index) => (
                <button
                  key={`${selectedTechniqueId}-step-${index}`}
                  type="button"
                  className={`timeline-step${index === currentStepIndex ? ' is-active' : ''}`}
                  onClick={() => {
                    setCurrentStepIndex(index);
                    setIsPlaying(false);
                  }}
                >
                  <span>Step {index + 1}</span>
                  <strong>{step.label}</strong>
                </button>
              ))}
            </div>
          </section>
        </section>

        <section className="share-panel">
          <div>
            <h3>Collaborative share mode</h3>
            <p>{shareState.message || 'Create a link to reopen this exact technique and step on another device.'}</p>
          </div>
          <code>{shareState.url || 'No active share link yet.'}</code>
        </section>
      </main>
      {showTour ? (
        <div className="tour-overlay" role="dialog" aria-modal="true" aria-labelledby="tour-title">
          <div className="tour-card">
            <p className="hero-kicker">First-time guide</p>
            <h2 id="tour-title">How to use DSA Visualizer</h2>
            <div className="tour-steps">
              <span>1. Pick a category from the dashboard.</span>
              <span>2. Choose a technique from the left list.</span>
              <span>3. Use Previous, Play, Next, and Share Link to study or send the exact step.</span>
              <span>4. For graph/tree topics, scroll over the Canvas to zoom without creating hundreds of DOM elements.</span>
            </div>
            <button type="button" onClick={finishTour}>Start learning</button>
          </div>
        </div>
      ) : null}
    </div>
  );
}

export default App;
