/**
 * DSA Visualizer Pro - 1920x1080 Master Explainer Video Engine
 * 7 Complete Scenes covering the full end-to-end user journey
 */

class VideoRenderer {
  constructor(canvas) {
    this.canvas = canvas;
    this.ctx = canvas.getContext('2d');
    this.width = 1920;
    this.height = 1080;
    this.canvas.width = this.width;
    this.canvas.height = this.height;

    this.duration = 66.0; // 66 seconds total master walkthrough
    this.fps = 60;

    this.images = {};
    this.imageSources = {
      appIcon: '../../play_store_assets/app_icon_512.png',
      featureGraphic: '../../play_store_assets/feature_graphic_1024x500.png',
      screenshot1: '../../play store/screenshots/screenshot_1_algorithm_visualizer.png',
      screenshot2: '../../play store/screenshots/screenshot_2_interactive_skill_tree.png',
      screenshot3: '../../play store/screenshots/screenshot_3_practice_quizzes.png',
      screenshot4: '../../play store/screenshots/screenshot_4_classroom_analytics.png',
      heroPoster: 'C:/Users/PRASANNA/.gemini/antigravity-ide/brain/a3a96618-f65f-4326-b520-1ca04e3db9f8/dsa_launch_poster_1791132597721.jpg'
    };

    this.loadedImages = 0;
    this.totalImages = Object.keys(this.imageSources).length;
    this.initImages();

    this.particles = [];
    for (let i = 0; i < 90; i++) {
      this.particles.push({
        x: Math.random() * this.width,
        y: Math.random() * this.height,
        radius: Math.random() * 2.2 + 0.8,
        speedX: (Math.random() - 0.5) * 0.35,
        speedY: -Math.random() * 0.45 - 0.2,
        opacity: Math.random() * 0.7 + 0.2
      });
    }

    this.subtitles = [
      { start: 0.5, end: 8.2, text: "If you have ever prepared for coding interviews or university exams, you know how frustrating it is to look at static code and imagine where every pointer is moving. Let's see how DSA Visualizer Pro transforms this." },
      { start: 8.5, end: 17.6, text: "When you first open the app, sign in securely with Google to auto-sync your progress, or continue as a guest. Right on the dashboard, track your daily streak, resume your last topic, and search across seven core categories." },
      { start: 18.0, end: 28.5, text: "Here is the step-by-step visualizer for Quick Sort. Notice how the array is drawn as horizontal cards. Our amber badge locks the pivot, while the purple and green pointers show exactly when elements swap." },
      { start: 29.0, end: 38.0, text: "This visual clarity extends to over 25 data structures & algorithms — including binary search trees, doubly linked lists, and Dijkstra's shortest path graph traversals." },
      { start: 38.5, end: 47.5, text: "To keep your study engaging, our interactive RPG skill tree tracks your learning journey. As you complete visualizations and challenges, unlock new nodes and earn mastery XP." },
      { start: 48.0, end: 57.0, text: "Test your intuition with decision-based quizzes. Mistakes are automatically saved to your Mistake Bank for spaced review, while educators get live classroom analytics to monitor student comprehension." },
      { start: 57.5, end: 66.0, text: "DSA Visualizer Pro is completely ad-free and open-source. Download the Android app for free on Google Play, or check out the code and web client on GitHub!" }
    ];
  }

  initImages() {
    for (const [key, src] of Object.entries(this.imageSources)) {
      const img = new Image();
      img.crossOrigin = 'anonymous';
      img.onload = () => { this.images[key] = img; this.loadedImages++; };
      img.onerror = () => { this.images[key] = null; this.loadedImages++; };
      img.src = src;
    }
  }

  drawBackground(t) {
    const ctx = this.ctx;
    const grad = ctx.createRadialGradient(
      this.width / 2, this.height * 0.45, 100,
      this.width / 2, this.height * 0.5, this.width * 0.85
    );
    grad.addColorStop(0, '#0d1326');
    grad.addColorStop(0.5, '#070a14');
    grad.addColorStop(1, '#020409');
    ctx.fillStyle = grad;
    ctx.fillRect(0, 0, this.width, this.height);

    ctx.strokeStyle = 'rgba(0, 242, 254, 0.04)';
    ctx.lineWidth = 1;
    const gridSize = 80;
    const offsetY = (t * 20) % gridSize;

    ctx.beginPath();
    for (let x = 0; x < this.width; x += gridSize) {
      ctx.moveTo(x, 0); ctx.lineTo(x, this.height);
    }
    for (let y = offsetY; y < this.height; y += gridSize) {
      ctx.moveTo(0, y); ctx.lineTo(this.width, y);
    }
    ctx.stroke();

    this.particles.forEach((p, idx) => {
      const py = (p.y + p.speedY * t * 40) % this.height;
      const actualY = py < 0 ? py + this.height : py;
      const px = (p.x + p.speedX * t * 40) % this.width;
      const actualX = px < 0 ? px + this.width : px;

      ctx.beginPath();
      ctx.arc(actualX, actualY, p.radius, 0, Math.PI * 2);
      ctx.fillStyle = `rgba(0, 242, 254, ${p.opacity * (0.6 + 0.4 * Math.sin(t * 2 + idx))})`;
      ctx.fill();
    });
  }

  drawTopBadge(text, color = '#00f2fe') {
    const ctx = this.ctx;
    ctx.save();
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.font = '700 16px "JetBrains Mono", monospace';

    const cx = this.width / 2;
    const cy = 54;
    const textWidth = ctx.measureText(text).width;
    const boxW = textWidth + 36;
    const boxH = 34;

    ctx.fillStyle = 'rgba(13, 18, 34, 0.85)';
    ctx.strokeStyle = color;
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(cx - boxW / 2, cy - boxH / 2, boxW, boxH, 17);
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.arc(cx - boxW / 2 + 18, cy, 4, 0, Math.PI * 2);
    ctx.fillStyle = color;
    ctx.shadowColor = color;
    ctx.shadowBlur = 8;
    ctx.fill();

    ctx.fillStyle = '#ffffff';
    ctx.shadowBlur = 0;
    ctx.fillText(text, cx + 8, cy);
    ctx.restore();
  }

  drawGlassCard(x, y, w, h, r = 16, borderColor = 'rgba(255, 255, 255, 0.12)') {
    const ctx = this.ctx;
    ctx.save();
    ctx.fillStyle = 'rgba(13, 18, 36, 0.78)';
    ctx.strokeStyle = borderColor;
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(x, y, w, h, r);
    ctx.fill();
    ctx.stroke();
    ctx.restore();
  }

  drawSubtitles(t) {
    const current = this.subtitles.find(s => t >= s.start && t < s.end);
    if (!current) return;

    const ctx = this.ctx;
    ctx.save();
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.font = '600 20px "Plus Jakarta Sans", sans-serif';

    const text = current.text;
    const textW = ctx.measureText(text).width;
    const padX = 32;
    const boxW = Math.min(1760, textW + padX * 2 + 40);
    const boxH = 52;
    const cx = this.width / 2;
    const cy = this.height - 44;

    ctx.fillStyle = 'rgba(7, 10, 19, 0.94)';
    ctx.strokeStyle = 'rgba(0, 242, 254, 0.45)';
    ctx.lineWidth = 1.5;
    ctx.shadowColor = 'rgba(0, 242, 254, 0.3)';
    ctx.shadowBlur = 16;
    ctx.beginPath();
    ctx.roundRect(cx - boxW / 2, cy - boxH / 2, boxW, boxH, 26);
    ctx.fill();
    ctx.stroke();

    ctx.shadowBlur = 0;
    ctx.font = '18px sans-serif';
    ctx.fillText('🎙️', cx - boxW / 2 + 30, cy);

    ctx.font = '600 20px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#f8fafc';
    ctx.fillText(text, cx + 12, cy);
    ctx.restore();
  }

  // --- SCENE 1: HOOK & THE LEARNING PROBLEM (0.0s - 8.5s) ---
  renderScene1(t) {
    const ctx = this.ctx;
    const st = t;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('01 • THE PROBLEM & SOLUTION', '#00f2fe');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.shadowColor = 'rgba(0, 242, 254, 0.6)';
    ctx.shadowBlur = 24;
    ctx.fillText('STOP MEMORIZING CODE. WATCH IT MOVE.', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.shadowBlur = 0;
    ctx.fillText('Textbooks make you guess pointer logic. DSA Visualizer Pro brings it to life.', this.width / 2, 185);

    // Left Box: Confusing static code
    const cardY = 240;
    const cardH = 650;
    const boxW = 680;
    this.drawGlassCard(180, cardY, boxW, cardH, 20, 'rgba(239, 68, 68, 0.3)');

    ctx.fillStyle = 'rgba(239, 68, 68, 0.15)';
    ctx.fillRect(180, cardY, boxW, 55);
    ctx.textAlign = 'left';
    ctx.font = '700 17px "JetBrains Mono", monospace';
    ctx.fillStyle = '#ef4444';
    ctx.fillText('❌ THE OLD WAY: STATIC TEXTBOOK CODE', 210, cardY + 34);

    const oldLines = [
      "int partition(int arr[], int low, int high) {",
      "    int pivot = arr[high]; // where does it move?",
      "    int i = low - 1;",
      "    for (int j = low; j < high; j++) {",
      "        if (arr[j] < pivot) {",
      "            i++;",
      "            swap(arr[i], arr[j]); // ???",
      "        }",
      "    }",
      "    swap(arr[i + 1], arr[high]);",
      "    return i + 1;",
      "}"
    ];
    ctx.font = '500 17px "JetBrains Mono", monospace';
    ctx.fillStyle = '#cbd5e1';
    oldLines.forEach((l, idx) => {
      ctx.fillText(l, 210, cardY + 105 + idx * 38);
    });

    // Right Box: The Visualizer Phone Solution
    this.drawGlassCard(1060, cardY, boxW, cardH, 20, 'rgba(16, 185, 129, 0.4)');
    ctx.fillStyle = 'rgba(16, 185, 129, 0.15)';
    ctx.fillRect(1060, cardY, boxW, 55);
    ctx.font = '700 17px "JetBrains Mono", monospace';
    ctx.fillStyle = '#10b981';
    ctx.fillText('✓ THE VISUAL WAY: REAL-TIME POINTER TRACKING', 1090, cardY + 34);

    // Mini animated horizontal boxes on right
    const miniValues = [8, 3, 1, 5, 2, 9];
    const miniCellW = 80;
    const miniGap = 16;
    const miniStartX = 1090 + (boxW - (6 * miniCellW + 5 * miniGap)) / 2;
    const miniBoxY = cardY + 220;

    miniValues.forEach((v, idx) => {
      let bx = miniStartX + idx * (miniCellW + miniGap);
      let isP = idx === 5;
      let isS = idx === Math.min(4, Math.floor(st * 0.8));

      ctx.fillStyle = isP ? 'rgba(245, 158, 11, 0.25)' : isS ? 'rgba(168, 85, 247, 0.25)' : 'rgba(15, 23, 42, 0.8)';
      ctx.strokeStyle = isP ? '#f59e0b' : isS ? '#c084fc' : '#38bdf8';
      ctx.lineWidth = 3;
      ctx.beginPath();
      ctx.roundRect(bx, miniBoxY, miniCellW, 75, 12);
      ctx.fill();
      ctx.stroke();

      ctx.textAlign = 'center';
      ctx.font = '700 24px "JetBrains Mono", monospace';
      ctx.fillStyle = '#ffffff';
      ctx.fillText(v, bx + miniCellW / 2, miniBoxY + 45);

      if (isP) this.drawPointerTag(bx + miniCellW / 2, miniBoxY - 35, 'PIVOT', '#f59e0b');
      if (isS) this.drawPointerTag(bx + miniCellW / 2, miniBoxY - 35, 'SCAN', '#c084fc');
    });

    ctx.textAlign = 'center';
    ctx.font = '600 18px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#34d399';
    ctx.fillText('Dynamic step execution with Lomuto partition tracking', 1060 + boxW / 2, cardY + 440);
    ctx.restore();
  }

  // --- SCENE 2: GETTING STARTED & HOME DASHBOARD (8.5s - 18.0s) ---
  renderScene2(t) {
    const ctx = this.ctx;
    const st = t - 8.5;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('02 • GETTING STARTED & DASHBOARD', '#38bdf8');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('GOOGLE SIGN-IN & INTERACTIVE DASHBOARD', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('Seamless cloud synchronization, daily study streaks, and categorized catalogs.', this.width / 2, 185);

    // Left: Login & Authentication card
    const cardY = 240;
    const cardH = 650;
    this.drawGlassCard(140, cardY, 680, cardH, 20, 'rgba(56, 189, 248, 0.35)');

    ctx.textAlign = 'left';
    ctx.font = '800 22px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#38bdf8';
    ctx.fillText('Quick Authentication (LoginActivity)', 180, cardY + 55);

    // Google Sign in button mockup
    ctx.fillStyle = '#ffffff';
    ctx.beginPath();
    ctx.roundRect(240, cardY + 110, 480, 60, 30);
    ctx.fill();

    ctx.textAlign = 'center';
    ctx.font = '700 19px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#1e293b';
    ctx.fillText('Sign in with Google', 480, cardY + 146);

    ctx.font = '600 17px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('or Continue as Guest Scholar', 480, cardY + 210);

    // Sync status pill
    ctx.fillStyle = 'rgba(16, 185, 129, 0.15)';
    ctx.strokeStyle = '#10b981';
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(240, cardY + 260, 480, 50, 12);
    ctx.fill();
    ctx.stroke();

    ctx.fillStyle = '#34d399';
    ctx.font = '700 16px "JetBrains Mono", monospace';
    ctx.fillText('✓ Cloud Synced: student@university.edu', 480, cardY + 292);

    // Right: Main Dashboard (MainActivity)
    this.drawGlassCard(880, cardY, 900, cardH, 20, 'rgba(0, 242, 254, 0.35)');

    ctx.textAlign = 'left';
    ctx.font = '800 22px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#00f2fe';
    ctx.fillText('Home Screen & Navigation (MainActivity)', 920, cardY + 55);

    // Streak & Goal cards
    ctx.fillStyle = 'rgba(245, 158, 11, 0.15)';
    ctx.strokeStyle = '#f59e0b';
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(920, cardY + 85, 380, 75, 14);
    ctx.fill();
    ctx.stroke();

    ctx.font = '800 22px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#fbbf24';
    ctx.fillText('🔥 Daily Streak: 5 Days', 940, cardY + 130);

    ctx.fillStyle = 'rgba(56, 189, 248, 0.15)';
    ctx.strokeStyle = '#38bdf8';
    ctx.beginPath();
    ctx.roundRect(1330, cardY + 85, 410, 75, 14);
    ctx.fill();
    ctx.stroke();

    ctx.fillStyle = '#38bdf8';
    ctx.fillText('🎯 Daily Goal: 2 / 2 Mastered', 1350, cardY + 130);

    // Continue learning card
    ctx.fillStyle = 'rgba(168, 85, 247, 0.15)';
    ctx.strokeStyle = '#a855f7';
    ctx.beginPath();
    ctx.roundRect(920, cardY + 185, 820, 80, 14);
    ctx.fill();
    ctx.stroke();

    ctx.font = '800 20px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#c084fc';
    ctx.fillText('▶ Continue Learning: Quick Sort (Step 4 of 12)', 945, cardY + 225);
    ctx.font = '500 15px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#cbd5e1';
    ctx.fillText('Pick up exactly where you left off with zero loss of progress', 945, cardY + 250);

    // Category Grid list
    const cats = ['1. String Algorithms', '2. Sorting Algorithms', '3. Searching Algorithms', '4. Linked Structures', '5. Stack, Queue, Recursion', '6. Trees & Heaps', '7. Graph Traversals'];
    cats.forEach((cat, idx) => {
      const cy = cardY + 295 + idx * 46;
      ctx.fillStyle = 'rgba(255, 255, 255, 0.04)';
      ctx.beginPath();
      ctx.roundRect(920, cy, 820, 38, 8);
      ctx.fill();

      ctx.font = '600 16px "JetBrains Mono", monospace';
      ctx.fillStyle = '#e2e8f0';
      ctx.fillText(cat, 945, cy + 25);
    });

    ctx.restore();
  }

  // --- SCENE 3: AUTHENTIC ANDROID QUICKSORT ENGINE (18.0s - 29.0s) ---
  renderScene3(t) {
    const ctx = this.ctx;
    const st = t - 18.0;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('03 • NATIVE VISUALIZER ENGINE (QUICKSORT)', '#00f2fe');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('STEP-BY-STEP QUICKSORT LOMUTO PARTITION', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('Horizontal rounded cards with neon gradient borders, pointer tracking, and partition sweeps.', this.width / 2, 185);

    const cardW = 1440;
    const cardH = 680;
    const cardX = (this.width - cardW) / 2;
    const cardY = 220;
    this.drawGlassCard(cardX, cardY, cardW, cardH, 24, 'rgba(0, 242, 254, 0.35)');

    // Action Bar
    ctx.fillStyle = 'rgba(255, 255, 255, 0.04)';
    ctx.fillRect(cardX, cardY, cardW, 64);

    if (this.images.appIcon && this.images.appIcon.complete) {
      ctx.drawImage(this.images.appIcon, cardX + 24, cardY + 13, 38, 38);
    }

    ctx.textAlign = 'left';
    ctx.font = '800 20px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('DSA Visualizer Pro', cardX + 72, cardY + 38);

    ctx.font = '700 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#00f2fe';
    ctx.fillText('•  QUICK SORT (LOMUTO PARTITION)', cardX + 285, cardY + 38);

    ctx.textAlign = 'right';
    ctx.font = '600 15px "JetBrains Mono", monospace';
    ctx.fillStyle = '#10b981';
    ctx.fillText('Time: O(n log n)  |  Space: O(log n)', cardX + cardW - 30, cardY + 38);

    // HORIZONTAL ROUNDED BOXES
    const values = [8, 3, 1, 5, 2, 9];
    const n = values.length;
    const cellW = 160;
    const cellH = 135;
    const gap = 36;
    const totalW = n * cellW + (n - 1) * gap;
    const startX = cardX + (cardW - totalW) / 2;
    const boxY = cardY + 175;

    const pivotIdx = 5;
    let scanIdx = Math.min(4, Math.floor(st * 0.9));
    let partitionIdx = Math.min(scanIdx, Math.floor(st * 0.8));

    values.forEach((val, i) => {
      let bx = startX + i * (cellW + gap);
      let by = boxY;

      let isPivot = i === pivotIdx;
      let isScan = i === scanIdx;
      let isPartition = i === partitionIdx;

      let lift = (isScan || isPivot) ? 14 : 0;
      by -= lift;

      let strokeColorA = '#38bdf8';
      let strokeColorB = '#c084fc';
      let glowColor = 'rgba(56, 189, 248, 0.3)';

      if (isPivot) {
        strokeColorA = '#f59e0b';
        strokeColorB = '#ef4444';
        glowColor = 'rgba(245, 158, 11, 0.45)';
      } else if (isScan) {
        strokeColorA = '#c084fc';
        strokeColorB = '#a855f7';
        glowColor = 'rgba(168, 85, 247, 0.45)';
      } else if (i <= partitionIdx && !isPivot) {
        strokeColorA = '#34d399';
        strokeColorB = '#10b981';
        glowColor = 'rgba(16, 185, 129, 0.35)';
      }

      ctx.save();
      ctx.shadowColor = glowColor;
      ctx.shadowBlur = (isPivot || isScan) ? 22 : 10;

      const boxGrad = ctx.createLinearGradient(bx, by, bx + cellW, by + cellH);
      boxGrad.addColorStop(0, '#0f172a');
      boxGrad.addColorStop(1, '#1e293b');
      ctx.fillStyle = boxGrad;
      ctx.beginPath();
      ctx.roundRect(bx, by, cellW, cellH, 20);
      ctx.fill();

      const borderGrad = ctx.createLinearGradient(bx, by, bx + cellW, by + cellH);
      borderGrad.addColorStop(0, strokeColorA);
      borderGrad.addColorStop(1, strokeColorB);
      ctx.strokeStyle = borderGrad;
      ctx.lineWidth = 5;
      ctx.stroke();
      ctx.shadowBlur = 0;

      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.font = '800 48px "JetBrains Mono", monospace';
      ctx.fillStyle = '#ffffff';
      ctx.fillText(val, bx + cellW / 2, by + cellH / 2);

      ctx.font = '600 16px "JetBrains Mono", monospace';
      ctx.fillStyle = '#64748b';
      ctx.fillText(`[${i}]`, bx + cellW / 2, boxY + cellH + 26);

      if (isPivot) this.drawPointerTag(bx + cellW / 2, by - 44, 'PIVOT: 9', '#f59e0b');
      if (isScan) this.drawPointerTag(bx + cellW / 2, by - 44, 'SCAN (j)', '#c084fc');
      if (isPartition && !isPivot && !isScan) this.drawPointerTag(bx + cellW / 2, by - 44, 'PARTITION (i)', '#10b981');
      ctx.restore();
    });

    // Partition Sweep Line
    const sweepLeft = startX - 10;
    const sweepRight = startX + totalW + 10;
    const sweepY = boxY + cellH + 58;

    ctx.save();
    const sweepGrad = ctx.createLinearGradient(sweepLeft, sweepY, sweepRight, sweepY);
    sweepGrad.addColorStop(0, '#f59e0b');
    sweepGrad.addColorStop(1, '#38bdf8');
    ctx.strokeStyle = sweepGrad;
    ctx.lineWidth = 6;
    ctx.beginPath();
    ctx.moveTo(sweepLeft, sweepY);
    ctx.lineTo(sweepRight, sweepY);
    ctx.stroke();

    const pulseProgress = (st * 0.7) % 1;
    const sweepDotX = sweepLeft + (sweepRight - sweepLeft) * pulseProgress;
    ctx.fillStyle = '#ffffff';
    ctx.shadowColor = '#00f2fe';
    ctx.shadowBlur = 14;
    ctx.beginPath();
    ctx.arc(sweepDotX, sweepY, 8, 0, Math.PI * 2);
    ctx.fill();
    ctx.restore();

    // Operation Console Card
    const consoleY = cardY + 415;
    const consoleW = cardW - 60;
    const consoleH = 140;
    const consoleX = cardX + 30;

    ctx.fillStyle = 'rgba(10, 15, 30, 0.9)';
    ctx.strokeStyle = 'rgba(0, 242, 254, 0.25)';
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(consoleX, consoleY, consoleW, consoleH, 16);
    ctx.fill();
    ctx.stroke();

    ctx.textAlign = 'left';
    ctx.textBaseline = 'top';
    ctx.font = '700 18px "JetBrains Mono", monospace';
    ctx.fillStyle = '#38bdf8';
    ctx.fillText('⚙️ OPERATION: partition(arr, low=0, high=5)', consoleX + 24, consoleY + 20);

    ctx.font = '600 18px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#f8fafc';
    const statusMsg = st < 4.0
      ? 'Quicksort picks pivot 9. Scanning values to build left partition of items <= pivot.'
      : st < 7.5
      ? 'Value arr[scan] <= 9: Swapped into left-partition zone so elements stay grouped.'
      : 'Subarray partitioned. Pivot placed in final sorted position between left & right parts.';
    ctx.fillText(statusMsg, consoleX + 24, consoleY + 54);

    ctx.font = '600 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#10b981';
    ctx.fillText('✓ Left Region: [<= Pivot]  |  Pivot: [9]  |  Right Region: [> Pivot]', consoleX + 24, consoleY + 95);

    // Player Toolbar
    const ctrlY = cardY + 580;
    ctx.fillStyle = 'rgba(255, 255, 255, 0.03)';
    ctx.fillRect(cardX, ctrlY, cardW, cardH - (ctrlY - cardY));

    ctx.textAlign = 'left';
    ctx.textBaseline = 'middle';
    ctx.font = '700 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#38bdf8';
    ctx.fillText('Speed: 1x', cardX + 34, ctrlY + 45);

    const pbarX = cardX + 160;
    const pbarW = 320;
    ctx.fillStyle = 'rgba(255, 255, 255, 0.15)';
    ctx.beginPath();
    ctx.roundRect(pbarX, ctrlY + 40, pbarW, 10, 5);
    ctx.fill();

    const progWidth = Math.min(pbarW, pbarW * (st / 11.0));
    ctx.fillStyle = '#00f2fe';
    ctx.beginPath();
    ctx.roundRect(pbarX, ctrlY + 40, progWidth, 10, 5);
    ctx.fill();

    ctx.font = '700 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#ffffff';
    ctx.fillText(`${Math.round((st / 11.0) * 100)}%`, pbarX + pbarW + 18, ctrlY + 45);

    ctx.textAlign = 'right';
    ctx.font = '22px sans-serif';
    ctx.fillStyle = '#38bdf8';
    ctx.fillText('⏮   ◀   ⏸   ▶   ⏭   🔄', cardX + cardW - 34, ctrlY + 45);

    ctx.restore();
  }

  drawPointerTag(x, y, text, color) {
    const ctx = this.ctx;
    ctx.save();
    ctx.font = '800 14px "JetBrains Mono", monospace';
    const w = ctx.measureText(text).width + 20;
    const h = 28;

    ctx.fillStyle = color;
    ctx.shadowColor = color;
    ctx.shadowBlur = 10;
    ctx.beginPath();
    ctx.roundRect(x - w / 2, y, w, h, 6);
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(x - 6, y + h);
    ctx.lineTo(x + 6, y + h);
    ctx.lineTo(x, y + h + 8);
    ctx.closePath();
    ctx.fill();

    ctx.fillStyle = '#03060d';
    ctx.shadowBlur = 0;
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.fillText(text, x, y + h / 2);
    ctx.restore();
  }

  // --- SCENE 4: 25+ DSA CATALOG & TREES/GRAPHS (29.0s - 38.5s) ---
  renderScene4(t) {
    const ctx = this.ctx;
    const st = t - 29.0;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('04 • 25+ ALGORITHM CATALOG', '#38bdf8');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('EXTENSIVE DATA STRUCTURES & ALGORITHMS', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('From linked structures and AVL trees to shortest-path Dijkstra traversals.', this.width / 2, 185);

    const cardW = 410;
    const cardH = 520;
    const gap = 34;
    const startX = (this.width - (4 * cardW + 3 * gap)) / 2;
    const cardY = 240;

    // Card 1: Linked Structures
    this.drawCatalogCard(startX, cardY, cardW, cardH, 'LINKED STRUCTURES', 'Singly, Doubly, Circular', '#00f2fe', (cx, cy, cw, ch) => {
      this.drawMiniLinkedList(cx, cy, cw, ch);
    });

    // Card 2: Binary Search Tree
    this.drawCatalogCard(startX + (cardW + gap), cardY, cardW, cardH, 'TREES & HEAPS', 'BST, AVL, Huffman Coding', '#a855f7', (cx, cy, cw, ch) => {
      this.drawMiniBST(cx, cy, cw, ch);
    });

    // Card 3: Graph Theory
    this.drawCatalogCard(startX + 2 * (cardW + gap), cardY, cardW, cardH, 'GRAPH ALGORITHMS', 'BFS, DFS, Dijkstra, MST', '#10b981', (cx, cy, cw, ch) => {
      this.drawMiniGraph(cx, cy, cw, ch);
    });

    // Card 4: Asymptotic Complexities
    this.drawCatalogCard(startX + 3 * (cardW + gap), cardY, cardW, cardH, 'SEARCH & COMPLEXITY', 'Radix, QuickSort, Merge, Hash', '#f59e0b', (cx, cy, cw, ch) => {
      this.drawMiniComplexity(cx, cy, cw, ch);
    });

    this.drawPillBar(810);
    ctx.restore();
  }

  drawCatalogCard(x, y, w, h, title, subtitle, accentColor, drawInner) {
    const ctx = this.ctx;
    this.drawGlassCard(x, y, w, h, 18, `${accentColor}44`);
    ctx.save();
    ctx.fillStyle = 'rgba(255, 255, 255, 0.04)';
    ctx.fillRect(x, y, w, 70);

    ctx.font = '800 17px "JetBrains Mono", monospace';
    ctx.fillStyle = accentColor;
    ctx.textAlign = 'left';
    ctx.fillText(title, x + 24, y + 34);

    ctx.font = '500 14px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText(subtitle, x + 24, y + 56);

    drawInner(x + 20, y + 85, w - 40, h - 105);
    ctx.restore();
  }

  drawMiniLinkedList(x, y, w, h) {
    const ctx = this.ctx;
    const nodes = ['10', '25', '40'];
    const startX = x + 15;
    const startY = y + h / 2 - 22;

    nodes.forEach((val, idx) => {
      const nx = startX + idx * 105;
      ctx.fillStyle = idx === 1 ? '#00f2fe' : 'rgba(15, 23, 42, 0.9)';
      ctx.strokeStyle = '#00f2fe';
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.roundRect(nx, startY, 60, 44, 8);
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = idx === 1 ? '#03060d' : '#f8fafc';
      ctx.font = '700 17px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(val, nx + 30, startY + 22);

      if (idx < nodes.length - 1) {
        ctx.strokeStyle = '#38bdf8';
        ctx.lineWidth = 2;
        ctx.beginPath();
        ctx.moveTo(nx + 64, startY + 22);
        ctx.lineTo(nx + 101, startY + 22);
        ctx.stroke();
      }
    });

    this.drawPointerTag(startX + 105 + 30, startY + 64, 'LOC / CURR', '#00f2fe');
  }

  drawMiniBST(x, y, w, h) {
    const ctx = this.ctx;
    const tree = [
      { id: '50', x: x + w / 2, y: y + 45, parent: null, active: true },
      { id: '30', x: x + w / 4, y: y + 140, parent: '50', active: true },
      { id: '70', x: x + (3 * w) / 4, y: y + 140, parent: '50', active: false },
      { id: '35', x: x + w / 4 + 35, y: y + 235, parent: '30', active: true }
    ];

    tree.forEach(node => {
      if (node.parent) {
        const p = tree.find(t => t.id === node.parent);
        ctx.strokeStyle = node.active ? '#c084fc' : 'rgba(255, 255, 255, 0.15)';
        ctx.lineWidth = 2.5;
        ctx.beginPath();
        ctx.moveTo(p.x, p.y);
        ctx.lineTo(node.x, node.y);
        ctx.stroke();
      }
    });

    tree.forEach(node => {
      ctx.beginPath();
      ctx.arc(node.x, node.y, 22, 0, Math.PI * 2);
      ctx.fillStyle = node.active ? '#a855f7' : '#1e293b';
      ctx.strokeStyle = '#c084fc';
      ctx.lineWidth = 2;
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = '#ffffff';
      ctx.font = '700 15px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(node.id, node.x, node.y);
    });

    ctx.font = '600 14px "JetBrains Mono", monospace';
    ctx.fillStyle = '#c084fc';
    ctx.fillText('BST Search: [35] FOUND!', x + w / 2, y + h - 25);
  }

  drawMiniGraph(x, y, w, h) {
    const ctx = this.ctx;
    const nodes = [
      { id: 'A', x: x + 60, y: y + 70 },
      { id: 'B', x: x + w - 70, y: y + 60 },
      { id: 'C', x: x + 80, y: y + 220 },
      { id: 'D', x: x + w - 80, y: y + 210 },
      { id: 'E', x: x + w / 2, y: y + 140 }
    ];

    const edges = [['A', 'B', '4'], ['A', 'C', '2'], ['C', 'E', '1'], ['E', 'D', '7'], ['B', 'D', '3']];

    edges.forEach(([u, v, weight]) => {
      const nu = nodes.find(n => n.id === u);
      const nv = nodes.find(n => n.id === v);
      ctx.strokeStyle = 'rgba(16, 185, 129, 0.4)';
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.moveTo(nu.x, nu.y);
      ctx.lineTo(nv.x, nv.y);
      ctx.stroke();
    });

    nodes.forEach(node => {
      const isVisited = node.id === 'A' || node.id === 'C' || node.id === 'E';
      ctx.beginPath();
      ctx.arc(node.x, node.y, 20, 0, Math.PI * 2);
      ctx.fillStyle = isVisited ? '#10b981' : '#0f172a';
      ctx.strokeStyle = '#34d399';
      ctx.lineWidth = 2;
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = isVisited ? '#03060d' : '#ffffff';
      ctx.font = '700 15px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(node.id, node.x, node.y);
    });

    ctx.font = '600 14px "JetBrains Mono", monospace';
    ctx.fillStyle = '#10b981';
    ctx.fillText('Dijkstra Shortest Path: A➔C➔E', x + w / 2, y + h - 25);
  }

  drawMiniComplexity(x, y, w, h) {
    const ctx = this.ctx;
    const comps = [
      { name: 'Binary Search', time: 'O(log n)' },
      { name: 'Merge Sort', time: 'O(n log n)' },
      { name: 'Radix Sort', time: 'O(d · n)' },
      { name: 'Hash Map', time: 'O(1) Avg' }
    ];

    comps.forEach((item, idx) => {
      const rowY = y + 25 + idx * 56;
      ctx.fillStyle = 'rgba(255, 255, 255, 0.04)';
      ctx.beginPath();
      ctx.roundRect(x, rowY, w, 44, 8);
      ctx.fill();

      ctx.textAlign = 'left';
      ctx.font = '700 14px "Plus Jakarta Sans", sans-serif';
      ctx.fillStyle = '#f8fafc';
      ctx.fillText(item.name, x + 16, rowY + 27);

      ctx.textAlign = 'right';
      ctx.font = '700 14px "JetBrains Mono", monospace';
      ctx.fillStyle = '#f59e0b';
      ctx.fillText(item.time, x + w - 16, rowY + 27);
    });
  }

  drawPillBar(y) {
    const ctx = this.ctx;
    const topics = ['Arrays', 'Linked Lists', 'Stack & Queue', 'Binary Trees', 'AVL Trees', 'Heaps', 'Hashing', 'Graphs', 'Topological Sort', 'Dijkstra'];
    const totalW = 1400;
    const startX = (this.width - totalW) / 2;

    ctx.save();
    ctx.textAlign = 'center';
    ctx.font = '600 15px "JetBrains Mono", monospace';

    let curX = startX;
    topics.forEach((t) => {
      const tw = ctx.measureText(t).width + 30;
      ctx.fillStyle = 'rgba(255, 255, 255, 0.06)';
      ctx.strokeStyle = 'rgba(255, 255, 255, 0.12)';
      ctx.lineWidth = 1;
      ctx.beginPath();
      ctx.roundRect(curX, y, tw, 36, 18);
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = '#e2e8f0';
      ctx.fillText(t, curX + tw / 2, y + 22);
      curX += tw + 14;
    });
    ctx.restore();
  }

  // --- SCENE 5: RPG SKILL TREE & MASTERY MAP (38.5s - 48.0s) ---
  renderScene5(t) {
    const ctx = this.ctx;
    const st = t - 38.5;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('05 • RPG SKILL TREE & MASTERY MAP', '#a855f7');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('GAMIFIED PROGRESSION & UNLOCKABLE MILESTONES', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('Turn abstract concepts into an RPG quest. Unlock prerequisites and earn XP.', this.width / 2, 185);

    const leftW = 950;
    const leftH = 600;
    const leftX = 140;
    const leftY = 240;
    this.drawGlassCard(leftX, leftY, leftW, leftH, 20, 'rgba(168, 85, 247, 0.35)');

    // Constellation Canvas
    const nodes = [
      { id: 'arrays', label: 'Arrays & Strings', status: 'mastered', x: leftX + 150, y: leftY + 130, stars: 3 },
      { id: 'lists', label: 'Linked Lists', status: 'mastered', x: leftX + 440, y: leftY + 130, stars: 3 },
      { id: 'stacks', label: 'Stacks & Queues', status: 'unlocked', x: leftX + 740, y: leftY + 130, stars: 2 },
      { id: 'trees', label: 'Binary Search Trees', status: 'unlocking', x: leftX + 280, y: leftY + 330, stars: 1 },
      { id: 'graphs', label: 'Graph Traversals', status: 'locked', x: leftX + 620, y: leftY + 330, stars: 0 },
      { id: 'dp', label: 'Dynamic Programming', status: 'legendary', x: leftX + 450, y: leftY + 480, stars: 0 }
    ];

    const edges = [['arrays', 'lists'], ['lists', 'stacks'], ['lists', 'trees'], ['stacks', 'graphs'], ['trees', 'graphs'], ['graphs', 'dp']];

    edges.forEach(([u, v]) => {
      const nu = nodes.find(n => n.id === u);
      const nv = nodes.find(n => n.id === v);
      ctx.strokeStyle = 'rgba(168, 85, 247, 0.3)';
      ctx.lineWidth = 4;
      ctx.beginPath();
      ctx.moveTo(nu.x, nu.y);
      ctx.lineTo(nv.x, nv.y);
      ctx.stroke();

      const pulseProgress = (st * 0.8 + (nu.x % 10) * 0.1) % 1;
      const px = nu.x + (nv.x - nu.x) * pulseProgress;
      const py = nu.y + (nv.y - nu.y) * pulseProgress;

      ctx.fillStyle = '#a855f7';
      ctx.shadowColor = '#c084fc';
      ctx.shadowBlur = 10;
      ctx.beginPath();
      ctx.arc(px, py, 4, 0, Math.PI * 2);
      ctx.fill();
    });

    nodes.forEach(node => {
      let isUnlocking = node.status === 'unlocking';
      let isMastered = node.status === 'mastered';
      let isLocked = node.status === 'locked';

      let nodeColor = isMastered ? '#10b981' : isUnlocking ? '#a855f7' : isLocked ? '#1e293b' : '#38bdf8';
      let strokeColor = isMastered ? '#34d399' : isUnlocking ? '#c084fc' : isLocked ? '#475569' : '#00f2fe';

      ctx.save();
      ctx.beginPath();
      ctx.arc(node.x, node.y, 38, 0, Math.PI * 2);
      ctx.fillStyle = nodeColor;
      ctx.strokeStyle = strokeColor;
      ctx.lineWidth = 3;
      ctx.shadowColor = nodeColor;
      ctx.shadowBlur = isUnlocking ? 20 : 8;
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = isLocked ? '#64748b' : '#ffffff';
      ctx.font = '800 20px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.shadowBlur = 0;
      ctx.fillText(isLocked ? '🔒' : isMastered ? '✓' : '★', node.x, node.y);

      ctx.font = '700 15px "Plus Jakarta Sans", sans-serif';
      ctx.fillStyle = '#f8fafc';
      ctx.fillText(node.label, node.x, node.y + 54);
      ctx.restore();
    });

    if (st > 2.0) {
      const bx = leftX + leftW / 2;
      const by = leftY + 70;
      ctx.fillStyle = 'rgba(168, 85, 247, 0.92)';
      ctx.strokeStyle = '#c084fc';
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.roundRect(bx - 190, by - 24, 380, 48, 24);
      ctx.fill();
      ctx.stroke();

      ctx.fillStyle = '#ffffff';
      ctx.font = '800 16px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText('🎉 SKILL MASTERED! +250 XP REWARD', bx, by);
    }

    // Right Phone frame
    const phoneX = 1180;
    const phoneY = 220;
    const phoneW = 580;
    const phoneH = 660;
    this.drawAppPhone(phoneX, phoneY, phoneW, phoneH, this.images.screenshot2, 'Skill Tree & Mastery Map', st);

    ctx.restore();
  }

  // --- SCENE 6: PRACTICE QUIZZES & CLASSROOM ANALYTICS (48.0s - 57.5s) ---
  renderScene6(t) {
    const ctx = this.ctx;
    const st = t - 48.0;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    this.drawTopBadge('06 • PRACTICE ENGINE & CLASSROOM SYNC', '#10b981');

    ctx.textAlign = 'center';
    ctx.font = '900 56px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('DECISION QUIZZES, MISTAKE BANK & CLASSROOMS', this.width / 2, 135);

    ctx.font = '600 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('Reinforce algorithmic intuition with spaced repetition and educator dashboards.', this.width / 2, 185);

    const leftW = 860;
    const leftH = 600;
    const leftX = 120;
    const leftY = 240;
    this.drawGlassCard(leftX, leftY, leftW, leftH, 20, 'rgba(16, 185, 129, 0.35)');

    // Quiz Demo
    ctx.fillStyle = 'rgba(255, 255, 255, 0.05)';
    ctx.fillRect(leftX, leftY, leftW, 65);

    ctx.font = '800 18px "JetBrains Mono", monospace';
    ctx.fillStyle = '#10b981';
    ctx.textAlign = 'left';
    ctx.fillText('QUESTION #14 • INTERVIEW CHALLENGE', leftX + 30, leftY + 40);

    ctx.textAlign = 'right';
    ctx.font = '700 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#f59e0b';
    ctx.fillText('🔥 STREAK: 7 DAYS', leftX + leftW - 30, leftY + 40);

    ctx.textAlign = 'left';
    ctx.font = '700 25px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('What is the worst-case space complexity of DFS', leftX + 30, leftY + 125);
    ctx.fillText('when searching a Binary Tree of height h?', leftX + 30, leftY + 162);

    const options = [
      { key: 'A', text: 'O(1) Auxiliary Space', correct: false },
      { key: 'B', text: 'O(h) Stack Recursion', correct: true },
      { key: 'C', text: 'O(n²) Traversal Matrix', correct: false }
    ];

    const isSelected = st > 2.0;

    options.forEach((opt, idx) => {
      const oy = leftY + 205 + idx * 80;
      let cardBg = 'rgba(255, 255, 255, 0.04)';
      let cardBorder = 'rgba(255, 255, 255, 0.12)';
      let badgeBg = '#1e293b';

      if (isSelected && opt.correct) {
        cardBg = 'rgba(16, 185, 129, 0.18)';
        cardBorder = '#10b981';
        badgeBg = '#10b981';
      }

      ctx.fillStyle = cardBg;
      ctx.strokeStyle = cardBorder;
      ctx.lineWidth = 2;
      ctx.beginPath();
      ctx.roundRect(leftX + 30, oy, leftW - 60, 64, 12);
      ctx.fill();
      ctx.stroke();

      ctx.beginPath();
      ctx.arc(leftX + 65, oy + 32, 18, 0, Math.PI * 2);
      ctx.fillStyle = badgeBg;
      ctx.fill();

      ctx.font = '800 16px "JetBrains Mono", monospace';
      ctx.fillStyle = '#ffffff';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(opt.key, leftX + 65, oy + 32);

      ctx.textAlign = 'left';
      ctx.font = '600 19px "Plus Jakarta Sans", sans-serif';
      ctx.fillStyle = isSelected && opt.correct ? '#34d399' : '#f8fafc';
      ctx.fillText(opt.text, leftX + 105, oy + 32);

      if (isSelected && opt.correct) {
        ctx.textAlign = 'right';
        ctx.font = '800 20px "JetBrains Mono", monospace';
        ctx.fillStyle = '#10b981';
        ctx.fillText('✓ CORRECT (+100 XP)', leftX + leftW - 55, oy + 32);
      }
    });

    ctx.textAlign = 'left';
    ctx.font = '600 16px "JetBrains Mono", monospace';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('💡 Mistake Bank automatically tracks challenging problems for spaced review.', leftX + 30, leftY + leftH - 35);

    // Right phone
    const phoneX = 1080;
    const phoneY = 220;
    const phoneW = 720;
    const phoneH = 660;
    this.drawAppPhone(phoneX, phoneY, phoneW, phoneH, this.images.screenshot4, 'Classroom & Weak Topic Analytics', st);

    ctx.restore();
  }

  // --- SCENE 7: GRAND CLIMAX & CALL-TO-ACTION (57.5s - 66.0s) ---
  renderScene7(t) {
    const ctx = this.ctx;
    const st = t - 57.5;
    const alpha = Math.min(1, st * 2.5);
    ctx.save();
    ctx.globalAlpha = alpha;

    if (this.images.heroPoster && this.images.heroPoster.complete) {
      ctx.save();
      ctx.globalAlpha = alpha * 0.42;
      ctx.drawImage(this.images.heroPoster, 0, 0, this.width, this.height);
      ctx.restore();
    }

    const cx = this.width / 2;
    const iconY = 210;
    const aura = ctx.createRadialGradient(cx, iconY, 20, cx, iconY, 160);
    aura.addColorStop(0, 'rgba(0, 242, 254, 0.45)');
    aura.addColorStop(0.5, 'rgba(121, 40, 202, 0.25)');
    aura.addColorStop(1, 'rgba(0, 0, 0, 0)');
    ctx.fillStyle = aura;
    ctx.beginPath();
    ctx.arc(cx, iconY, 160, 0, Math.PI * 2);
    ctx.fill();

    const iconSize = 170;
    const ix = cx - iconSize / 2;
    const iy = iconY - iconSize / 2;

    ctx.save();
    ctx.shadowColor = 'rgba(0, 242, 254, 0.6)';
    ctx.shadowBlur = 30;
    ctx.beginPath();
    ctx.roundRect(ix, iy, iconSize, iconSize, 38);
    ctx.clip();

    if (this.images.appIcon && this.images.appIcon.complete) {
      ctx.drawImage(this.images.appIcon, ix, iy, iconSize, iconSize);
    } else {
      ctx.fillStyle = 'linear-gradient(135deg, #00f2fe, #7928ca)';
      ctx.fillRect(ix, iy, iconSize, iconSize);
      ctx.fillStyle = '#ffffff';
      ctx.font = '900 64px "Plus Jakarta Sans", sans-serif';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText('DSA', cx, iconY);
    }
    ctx.restore();

    ctx.textAlign = 'center';
    ctx.font = '900 74px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.shadowColor = 'rgba(0, 242, 254, 0.6)';
    ctx.shadowBlur = 30;
    ctx.fillText('DSA VISUALIZER PRO', cx, 395);

    ctx.font = '700 26px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#38bdf8';
    ctx.shadowBlur = 0;
    ctx.fillText('MASTER DATA STRUCTURES & ALGORITHMS WITH TRUE VISUAL INTUITION', cx, 445);

    const features = [
      '✓ 100% Ad-Free & Free Forever',
      '✓ Step-by-Step Pointer Tracking',
      '✓ Interactive Skill Tree & XP',
      '✓ Android APK + React Web Client',
      '✓ Practice Quizzes & Mistake Bank',
      '✓ Classroom Analytics & Notes'
    ];

    const gridY = 515;
    const colW = 420;
    const colH = 50;
    const cols = 3;

    features.forEach((feat, i) => {
      const col = i % cols;
      const row = Math.floor(i / cols);
      const fx = cx - (cols * colW + (cols - 1) * 20) / 2 + col * (colW + 20);
      const fy = gridY + row * (colH + 16);

      ctx.fillStyle = 'rgba(13, 18, 36, 0.85)';
      ctx.strokeStyle = 'rgba(0, 242, 254, 0.3)';
      ctx.lineWidth = 1.5;
      ctx.beginPath();
      ctx.roundRect(fx, fy, colW, colH, 12);
      ctx.fill();
      ctx.stroke();

      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.font = '700 18px "Plus Jakarta Sans", sans-serif';
      ctx.fillStyle = '#f8fafc';
      ctx.fillText(feat, fx + colW / 2, fy + colH / 2);
    });

    const ctaY = 695;
    const gplayW = 320;
    const gplayH = 76;
    const gplayX = cx - gplayW - 20;

    ctx.fillStyle = '#0a0d1a';
    ctx.strokeStyle = '#38bdf8';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.roundRect(gplayX, ctaY, gplayW, gplayH, 16);
    ctx.fill();
    ctx.stroke();

    ctx.textAlign = 'left';
    ctx.font = '600 13px "JetBrains Mono", monospace';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('GET IT FOR ANDROID ON', gplayX + 80, ctaY + 28);
    ctx.font = '800 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('Google Play', gplayX + 80, ctaY + 54);

    ctx.fillStyle = '#00f2fe';
    ctx.beginPath();
    ctx.moveTo(gplayX + 28, ctaY + 20);
    ctx.lineTo(gplayX + 62, ctaY + 38);
    ctx.lineTo(gplayX + 28, ctaY + 56);
    ctx.closePath();
    ctx.fill();

    const webW = 320;
    const webH = 76;
    const webX = cx + 20;

    ctx.fillStyle = '#0a0d1a';
    ctx.strokeStyle = '#a855f7';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.roundRect(webX, ctaY, webW, webH, 16);
    ctx.fill();
    ctx.stroke();

    ctx.textAlign = 'left';
    ctx.font = '600 13px "JetBrains Mono", monospace';
    ctx.fillStyle = '#94a3b8';
    ctx.fillText('OPEN SOURCE ON GITHUB', webX + 80, ctaY + 28);
    ctx.font = '800 24px "Plus Jakarta Sans", sans-serif';
    ctx.fillStyle = '#ffffff';
    ctx.fillText('React Web App', webX + 80, ctaY + 54);

    ctx.font = '30px sans-serif';
    ctx.fillText('⚡', webX + 26, ctaY + 48);

    ctx.textAlign = 'center';
    ctx.font = '700 20px "JetBrains Mono", monospace';
    ctx.fillStyle = '#00f2fe';
    ctx.fillText('github.com/prasannapbhurke/DSA-Visualizer-Pro', cx, 835);

    ctx.restore();
  }

  drawAppPhone(x, y, w, h, img, caption, st) {
    const ctx = this.ctx;
    ctx.save();
    const hoverY = Math.sin(st * 2.5) * 6;
    const fy = y + hoverY;

    ctx.shadowColor = 'rgba(0, 242, 254, 0.25)';
    ctx.shadowBlur = 35;
    ctx.fillStyle = '#0a0e1c';
    ctx.strokeStyle = 'rgba(255, 255, 255, 0.2)';
    ctx.lineWidth = 3;
    ctx.beginPath();
    ctx.roundRect(x, fy, w, h, 36);
    ctx.fill();
    ctx.stroke();
    ctx.shadowBlur = 0;

    const screenX = x + 16;
    const screenY = fy + 16;
    const screenW = w - 32;
    const screenH = h - 32;

    ctx.save();
    ctx.beginPath();
    ctx.roundRect(screenX, screenY, screenW, screenH, 24);
    ctx.clip();

    if (img && img.complete) {
      ctx.drawImage(img, screenX, screenY, screenW, screenH);
    } else {
      ctx.fillStyle = '#0f172a';
      ctx.fillRect(screenX, screenY, screenW, screenH);
      ctx.fillStyle = '#38bdf8';
      ctx.font = '700 20px "JetBrains Mono", monospace';
      ctx.textAlign = 'center';
      ctx.fillText(caption, screenX + screenW / 2, screenY + screenH / 2);
    }
    ctx.restore();

    ctx.fillStyle = '#000000';
    ctx.beginPath();
    ctx.roundRect(x + w / 2 - 40, fy + 8, 80, 8, 4);
    ctx.fill();
    ctx.restore();
  }

  render(t) {
    this.drawBackground(t);

    if (t < 8.5) {
      this.renderScene1(t);
    } else if (t < 18.0) {
      this.renderScene2(t);
    } else if (t < 29.0) {
      this.renderScene3(t);
    } else if (t < 38.5) {
      this.renderScene4(t);
    } else if (t < 48.0) {
      this.renderScene5(t);
    } else if (t < 57.5) {
      this.renderScene6(t);
    } else {
      this.renderScene7(t);
    }

    this.drawSubtitles(t);
  }
}

window.VideoRenderer = VideoRenderer;
