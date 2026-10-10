# DSA Visualizer Pro - Remade Launch Video Plan (`brag-plan.md`)

## 1. Concept & Creative Direction

- **Product**: DSA Visualizer Pro (Android & Web)
- **Tagline**: Stop memorizing code. See the algorithm move.
- **Key Requirement**: Authentic Android app visualization engine for QuickSort (horizontal rounded glass cards with neon gradient borders `#38BDF8` to `#C084FC`, amber `#F59E0B` pivot, emerald `#10B981` partition boundary, traveling photon sweep line, and authentic Android player console).
- **Audio Upgrade**: Full spoken voiceover narration explaining the entire app end-to-end, synchronized with a ducked cyberpunk synth backing track and tactile sound effects.
- **Duration**: **32.0 seconds** (optimal pacing for comprehensive voiceover + visual absorption).
- **Resolution**: 1920 × 1080 (16:9 Landscape) at 60 FPS.

---

## 2. Storyboard & Scene Breakdown

### Scene 1: The Authentic Android QuickSort Visualizer (0.0s – 6.5s)
- **Visual**:
  - Exact Android app UI recreated from `SortVisualizerEngine.java` and `TechniqueVisualizerView.java`.
  - Top header: App Icon + `DSA Visualizer Pro` + `QUICKSORT • STEP-BY-STEP`.
  - Array elements rendered as **horizontal rounded glass cards**: `[8, 3, 1, 5, 2, 9]` (from `AlgorithmCatalog.java`).
  - Active pointers: `PIVOT [9]` (amber neon glow), `SCAN [j]` (cyan glow), `PARTITION [i]` (emerald glow).
  - Glowing partition sweep line (`drawPartitionSweep`) with traveling photon dot along the active subarray.
  - Operation Console:
    - `Operation: QuickSort(0, 5) • Partition Phase`
    - `Comparing: arr[scan] <= Pivot ? YES ➔ Swap into left partition`
    - `Time: O(n log n) | Space: O(log n)`
  - Media player toolbar: `Speed: 1x`, `Progress: 35%`, `[⏮ ◀ ⏸ ▶ ⏭ 🔄]`.
- **Spoken Narration**:
  > *"Welcome to DSA Visualizer Pro. Stop memorizing static code. Our app breaks down algorithms into crystal-clear, step-by-step animations with real-time pointer tracking, showing exactly how elements compare and partition."*

### Scene 2: The 25+ DSA Catalog (6.5s – 13.0s)
- **Visual**:
  - Headline: `25+ DATA STRUCTURES & ALGORITHMS`
  - 4 Dynamic interactive showcase cards:
    - **Linked Structures**: Singly, Doubly, Circular Linked Lists with `Loc`, `Prev`, `Next` pointers.
    - **Trees & Heaps**: Binary Search Trees with search beam (`60 FOUND!`) and AVL rotations.
    - **Graphs**: Weighted graph with Dijkstra shortest path wavefront (`A ➔ C ➔ E`).
    - **Complexity & Searching**: Hashing, Radix Sort, Binary Search with `O(1)` and `O(log n)` tags.
  - Category pill ticker along bottom: `Strings • Sorting • Searching • Linked Lists • Stacks • Trees • Graphs`.
- **Spoken Narration**:
  > *"Explore an extensive catalog of over twenty-five data structures and algorithms — from linked lists and binary search trees to Dijkstra graph traversals and advanced dynamic programming."*

### Scene 3: The Interactive Skill Tree (13.0s – 19.5s)
- **Visual**:
  - Cosmic circuit constellation showing prerequisite trees: `Arrays ➔ Linked Lists ➔ Stacks ➔ Trees ➔ Graphs ➔ DP`.
  - Energy pulses race along circuit conduits.
  - Node unlocks with celebratory shockwave: `🎉 SKILL MASTERED! +250 XP REWARD`.
  - Real Android phone mockup displaying `screenshot_2_interactive_skill_tree.png`.
- **Spoken Narration**:
  > *"Level up your computer science mastery with our interactive RPG skill tree. Earn XP, unlock milestones, and build deep intuition for your technical interviews and exams."*

### Scene 4: Practice Quizzes, Mistake Bank & Classrooms (19.5s – 26.0s)
- **Visual**:
  - Left: Interactive decision-based quiz challenge:
    - *"What is the worst-case space complexity of DFS on a binary tree?"*
    - Pointer selects `B: O(h)` ➔ instant emerald burst with `✓ CORRECT (+100 XP)`.
    - Mistake Bank auto-syncing incorrect attempts for spaced repetition review.
  - Right: Real Android phone mockup displaying `screenshot_4_classroom_analytics.png` showing teacher analytics and weak-topic heatmaps.
- **Spoken Narration**:
  > *"Sharpen your skills with curated practice quizzes, an automated mistake bank for targeted review, and built-in classroom analytics for teachers and study groups."*

### Scene 5: Grand Finale & Launch Call-to-Action (26.0s – 32.0s)
- **Visual**:
  - Center 3D high-res app icon with radiant cyan & violet glow.
  - Headline: **DSA VISUALIZER PRO**
  - Subtitle: *"Master Data Structures & Algorithms with True Visual Intuition."*
  - Value propositions: `✓ 100% Ad-Free`, `✓ Real-Time Pointer Tracking`, `✓ Android APK + React Web`, `✓ Free & Open Source`.
  - Badges: Official Google Play Store button + GitHub repository URL.
- **Spoken Narration**:
  > *"DSA Visualizer Pro is completely ad-free and open-source. Download the Android app today on Google Play, or try the web client now on GitHub!"*

---

## 3. Audio & Voiceover Specs
- **Voiceover**: Web Speech Synthesis API with high-clarity voice + on-screen synchronized closed captions.
- **Music**: Ducked Web Audio synth track (volume automatically ducks by 60% during voiceover phrases so speech is crisp, clear, and punchy).
- **Sound Effects**:
  - Swap clicks during QuickSort.
  - Level-up arpeggiated chime at 16.5s.
  - Correct answer bell at 22.0s.
  - Deep sub-bass crescendo boom at 26.0s.
