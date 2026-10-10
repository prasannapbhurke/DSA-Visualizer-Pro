/**
 * DSA Visualizer Pro - Sound Studio & Human Voiceover Engine
 * Synchronized with the 7-scene Master Explainer Video
 */

class LaunchSoundtrack {
  constructor() {
    this.ctx = null;
    this.dest = null;
    this.masterGain = null;
    this.musicGain = null;
    this.voiceGain = null;
    this.compressor = null;
    this.tempo = 120; // BPM
    this.stepDuration = 60 / this.tempo / 4;

    // Natural, Conversational Spoken Narration Script
    this.voiceoverScript = [
      {
        time: 0.5,
        text: "If you have ever prepared for coding interviews or university exams, you know how frustrating it is to look at static code and try to imagine where every pointer is moving. Let's see how DSA Visualizer Pro completely transforms this experience."
      },
      {
        time: 8.5,
        text: "When you first open the app, you can sign in securely with Google to automatically sync your progress, or continue as a guest. Right on the dashboard, you can track your daily streak, resume your last topic, and search across seven core categories."
      },
      {
        time: 18.0,
        text: "Here is the step-by-step visualizer for Quick Sort. Notice how the array is drawn as horizontal cards. Our amber badge locks the pivot, while the purple and green pointers show exactly when elements swap into the partitioned region."
      },
      {
        time: 29.0,
        text: "This visual clarity extends to over twenty-five data structures and algorithms, including binary search trees, doubly linked lists, and Dijkstra's shortest path graph traversals."
      },
      {
        time: 38.5,
        text: "To keep your study engaging, our interactive RPG skill tree tracks your learning journey. As you complete visualizations and practice challenges, you unlock new nodes and earn mastery XP."
      },
      {
        time: 48.0,
        text: "Test your intuition with decision-based quizzes. Any mistake is automatically saved to your Mistake Bank for spaced review, while educators get live classroom analytics to monitor student comprehension."
      },
      {
        time: 57.5,
        text: "DSA Visualizer Pro is completely ad-free and open-source. Download the Android app for free on Google Play, or check out the code and web client on GitHub!"
      }
    ];

    this.activeUtterances = [];
  }

  init() {
    if (this.ctx) return;
    const AudioContext = window.AudioContext || window.webkitAudioContext;
    this.ctx = new AudioContext();

    this.compressor = this.ctx.createDynamicsCompressor();
    this.compressor.threshold.setValueAtTime(-14, this.ctx.currentTime);
    this.compressor.knee.setValueAtTime(8, this.ctx.currentTime);
    this.compressor.ratio.setValueAtTime(4, this.ctx.currentTime);
    this.compressor.attack.setValueAtTime(0.003, this.ctx.currentTime);
    this.compressor.release.setValueAtTime(0.2, this.ctx.currentTime);

    this.masterGain = this.ctx.createGain();
    this.masterGain.gain.setValueAtTime(0.9, this.ctx.currentTime);

    this.musicGain = this.ctx.createGain();
    this.musicGain.gain.setValueAtTime(0.26, this.ctx.currentTime); // ducked under speech

    this.voiceGain = this.ctx.createGain();
    this.voiceGain.gain.setValueAtTime(1.0, this.ctx.currentTime);

    this.dest = this.ctx.createMediaStreamDestination();

    this.musicGain.connect(this.masterGain);
    this.voiceGain.connect(this.masterGain);

    this.masterGain.connect(this.compressor);
    this.compressor.connect(this.ctx.destination);
    this.compressor.connect(this.dest);
  }

  getAudioStream() {
    if (!this.ctx) this.init();
    return this.dest.stream;
  }

  playVoiceover(startTime = 0) {
    if (!('speechSynthesis' in window)) return;
    window.speechSynthesis.cancel();
    this.activeUtterances.forEach(timer => clearTimeout(timer));
    this.activeUtterances = [];

    const voices = window.speechSynthesis.getVoices();
    let preferredVoice = voices.find(v => v.lang.startsWith('en') && (v.name.includes('Natural') || v.name.includes('Google') || v.name.includes('India') || v.name.includes('David') || v.name.includes('Guy')));
    if (!preferredVoice) {
      preferredVoice = voices.find(v => v.lang.startsWith('en')) || null;
    }

    this.voiceoverScript.forEach(entry => {
      if (entry.time + 7.0 < startTime) return;
      const delay = Math.max(0, (entry.time - startTime) * 1000);
      const timer = setTimeout(() => {
        const utter = new SpeechSynthesisUtterance(entry.text);
        if (preferredVoice) utter.voice = preferredVoice;
        utter.rate = 1.0; // friendly natural human speaking rate
        utter.pitch = 1.0;
        utter.volume = 1.0;

        utter.onstart = () => { window.currentSpokenText = entry.text; };
        utter.onend = () => { if (window.currentSpokenText === entry.text) window.currentSpokenText = ""; };

        window.speechSynthesis.speak(utter);
      }, delay);

      this.activeUtterances.push(timer);
    });
  }

  stopVoiceover() {
    this.activeUtterances.forEach(timer => clearTimeout(timer));
    this.activeUtterances = [];
    if ('speechSynthesis' in window) window.speechSynthesis.cancel();
    window.currentSpokenText = "";
  }

  // Synthesized audio components
  playKick(time) {
    const osc = this.ctx.createOscillator();
    const gain = this.ctx.createGain();
    osc.type = 'sine';
    osc.frequency.setValueAtTime(130, time);
    osc.frequency.exponentialRampToValueAtTime(36, time + 0.12);
    gain.gain.setValueAtTime(0.7, time);
    gain.gain.exponentialRampToValueAtTime(0.001, time + 0.22);
    osc.connect(gain);
    gain.connect(this.musicGain);
    osc.start(time);
    osc.stop(time + 0.24);
  }

  playSnare(time) {
    const bufferSize = this.ctx.sampleRate * 0.15;
    const buffer = this.ctx.createBuffer(1, bufferSize, this.ctx.sampleRate);
    const data = buffer.getChannelData(0);
    for (let i = 0; i < bufferSize; i++) {
      data[i] = (Math.random() * 2 - 1) * Math.exp(-i / (bufferSize * 0.2));
    }
    const noise = this.ctx.createBufferSource();
    noise.buffer = buffer;
    const filter = this.ctx.createBiquadFilter();
    filter.type = 'highpass';
    filter.frequency.setValueAtTime(1500, time);
    const gain = this.ctx.createGain();
    gain.gain.setValueAtTime(0.3, time);
    gain.gain.exponentialRampToValueAtTime(0.001, time + 0.15);
    noise.connect(filter);
    filter.connect(gain);
    gain.connect(this.musicGain);
    noise.start(time);
    noise.stop(time + 0.16);
  }

  playHiHat(time) {
    const dur = 0.04;
    const bufferSize = this.ctx.sampleRate * dur;
    const buffer = this.ctx.createBuffer(1, bufferSize, this.ctx.sampleRate);
    const data = buffer.getChannelData(0);
    for (let i = 0; i < bufferSize; i++) data[i] = Math.random() * 2 - 1;
    const src = this.ctx.createBufferSource();
    src.buffer = buffer;
    const filter = this.ctx.createBiquadFilter();
    filter.type = 'highpass';
    filter.frequency.setValueAtTime(8500, time);
    const gain = this.ctx.createGain();
    gain.gain.setValueAtTime(0.18, time);
    gain.gain.exponentialRampToValueAtTime(0.001, time + dur);
    src.connect(filter);
    filter.connect(gain);
    gain.connect(this.musicGain);
    src.start(time);
    src.stop(time + dur + 0.01);
  }

  playBassNote(freq, time, duration = 0.16) {
    const osc = this.ctx.createOscillator();
    const filter = this.ctx.createBiquadFilter();
    const gain = this.ctx.createGain();
    osc.type = 'sawtooth';
    osc.frequency.setValueAtTime(freq, time);
    filter.type = 'lowpass';
    filter.frequency.setValueAtTime(1600, time);
    filter.frequency.exponentialRampToValueAtTime(320, time + duration);
    gain.gain.setValueAtTime(0.28, time);
    gain.gain.exponentialRampToValueAtTime(0.001, time + duration);
    osc.connect(filter);
    filter.connect(gain);
    gain.connect(this.musicGain);
    osc.start(time);
    osc.stop(time + duration + 0.02);
  }

  playWhoosh(time, duration = 0.6) {
    const bufferSize = this.ctx.sampleRate * duration;
    const buffer = this.ctx.createBuffer(1, bufferSize, this.ctx.sampleRate);
    const data = buffer.getChannelData(0);
    for (let i = 0; i < bufferSize; i++) data[i] = Math.random() * 2 - 1;
    const noise = this.ctx.createBufferSource();
    noise.buffer = buffer;
    const filter = this.ctx.createBiquadFilter();
    filter.type = 'bandpass';
    filter.frequency.setValueAtTime(300, time);
    filter.frequency.exponentialRampToValueAtTime(2600, time + duration * 0.6);
    filter.frequency.exponentialRampToValueAtTime(500, time + duration);
    const gain = this.ctx.createGain();
    gain.gain.setValueAtTime(0.01, time);
    gain.gain.linearRampToValueAtTime(0.22, time + duration * 0.5);
    gain.gain.exponentialRampToValueAtTime(0.001, time + duration);
    noise.connect(filter);
    filter.connect(gain);
    gain.connect(this.voiceGain);
    noise.start(time);
    noise.stop(time + duration + 0.02);
  }

  playLevelUpChime(time) {
    [523.25, 659.25, 783.99, 1046.50].forEach((freq, idx) => {
      const osc = this.ctx.createOscillator();
      const gain = this.ctx.createGain();
      const noteTime = time + idx * 0.07;
      osc.type = 'triangle';
      osc.frequency.setValueAtTime(freq, noteTime);
      gain.gain.setValueAtTime(0.22, noteTime);
      gain.gain.exponentialRampToValueAtTime(0.001, noteTime + 0.5);
      osc.connect(gain);
      gain.connect(this.voiceGain);
      osc.start(noteTime);
      osc.stop(noteTime + 0.55);
    });
  }

  playCorrectAnswerBell(time) {
    [880, 1320].forEach((freq, idx) => {
      const osc = this.ctx.createOscillator();
      const gain = this.ctx.createGain();
      const noteTime = time + idx * 0.08;
      osc.type = 'sine';
      osc.frequency.setValueAtTime(freq, noteTime);
      gain.gain.setValueAtTime(0.25, noteTime);
      gain.gain.exponentialRampToValueAtTime(0.001, noteTime + 0.45);
      osc.connect(gain);
      gain.connect(this.voiceGain);
      osc.start(noteTime);
      osc.stop(noteTime + 0.5);
    });
  }

  playImpactBoom(time) {
    const osc = this.ctx.createOscillator();
    const gain = this.ctx.createGain();
    osc.type = 'sine';
    osc.frequency.setValueAtTime(100, time);
    osc.frequency.exponentialRampToValueAtTime(28, time + 0.8);
    gain.gain.setValueAtTime(0.65, time);
    gain.gain.exponentialRampToValueAtTime(0.001, time + 1.2);
    osc.connect(gain);
    gain.connect(this.voiceGain);
    osc.start(time);
    osc.stop(time + 1.25);
  }

  scheduleSoundtrack(startTime = 0) {
    if (!this.ctx) this.init();
    if (this.ctx.state === 'suspended') this.ctx.resume();

    const t0 = startTime;
    const step = this.stepDuration; // ~0.125s

    this.playVoiceover(0);

    // Scene cut cues
    this.playWhoosh(t0 + 0.1);
    this.playWhoosh(t0 + 8.2);
    this.playWhoosh(t0 + 17.6);
    this.playWhoosh(t0 + 28.5);
    this.playWhoosh(t0 + 38.0);
    this.playWhoosh(t0 + 47.5);
    this.playWhoosh(t0 + 56.8);
    this.playImpactBoom(t0 + 57.0);

    this.playLevelUpChime(t0 + 42.5);
    this.playCorrectAnswerBell(t0 + 51.5);

    const bassFreqs = [73.42, 87.31, 65.41, 98.00]; // D, F, C, G
    const totalSteps = Math.floor(66.0 / step);

    for (let s = 0; s < totalSteps; s++) {
      const time = t0 + s * step;
      const stepInBar = s % 16;
      const bar = Math.floor(s / 16);

      if (stepInBar % 4 === 0) this.playKick(time);
      if (stepInBar === 4 || stepInBar === 12) this.playSnare(time);
      if (stepInBar % 2 === 1) this.playHiHat(time);
      if (stepInBar % 2 === 0) {
        this.playBassNote(bassFreqs[bar % bassFreqs.length], time, 0.14);
      }
    }
  }
}

window.LaunchSoundtrack = LaunchSoundtrack;
