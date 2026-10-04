# 🚀 Google Play Store Listing & Launch Handbook

Everything you need to publish **DSA Visualizer Pro** to the Google Play Store is organized in this folder.

---

## 📁 Files Included in this Folder (`play store/`)

| File | Purpose | Where to Upload in Play Console |
| :--- | :--- | :--- |
| **`app-release.aab`** | The signed production Android App Bundle | **Release** &rarr; **Create new release** &rarr; Upload App Bundle |
| **`app-release.apk`** | Signed APK for direct testing on physical phones | (For manual phone install / sharing with friends) |
| **`app_icon_512x512.png`** | High-res 512 &times; 512 App Icon | **Main store listing** &rarr; **App icon** |
| **`feature_graphic_1024x500.png`** | 1024 &times; 500 Banner graphic | **Main store listing** &rarr; **Feature graphic** |
| **`screenshots/screenshot_1_algorithm_visualizer.png`** | Step-by-Step Visualizer in action | **Phone screenshots** |
| **`screenshots/screenshot_2_interactive_skill_tree.png`** | Skill Tree & Mastery Map | **Phone screenshots** |
| **`screenshots/screenshot_3_practice_quizzes.png`** | Practice Quizzes & Mistake Bank | **Phone screenshots** |
| **`screenshots/screenshot_4_classroom_analytics.png`** | Classroom & Weak Topic Tracker | **Phone screenshots** |

---

## 📝 Store Listing Text (Copy & Paste)

### App Name (max 30 characters)
```text
DSA Visualizer Pro
```

### Short Description (max 80 characters)
```text
Interactive Data Structures & Algorithms visualizer, quizzes, and skill tree.
```

### Full Description (max 4000 characters)
```text
Master Data Structures and Algorithms (DSA) with step-by-step interactive animations and visual walkthroughs.

Whether you are preparing for coding interviews, computer science university exams, or looking to sharpen your algorithmic problem-solving skills, DSA Visualizer Pro breaks down complex code into intuitive, visual steps.

Key Features:
• Interactive Visualizer: Watch algorithms execute step-by-step with real-time pointer tracking (Loc, Prev, Current, Target).
• Extensive DSA Catalog: Includes Arrays, Linked Lists (Singly, Doubly, Circular), Stacks, Queues, Binary Search Trees, AVL Trees, Heaps, and Graph Traversals (BFS, DFS, Dijkstra).
• Algorithm Skill Tree: Track your personal learning journey and unlock achievements as you master each topic.
• Interactive Practice & Quizzes: Test your algorithmic intuition with curated decision-based challenges and mistake bank reviews.
• Classroom & Teacher Integration: Join study groups, review teacher notes, and track your weak topic summaries.

No advertisements. Built specifically for computer science students, educators, and software engineers.
```

---

## 🌐 Compliance & Policy URLs

* **Privacy Policy URL**:
  ```text
  https://dsa-visualizer-api-nu.vercel.app/privacy-policy
  ```
* **Account Deletion URL**:
  ```text
  https://dsa-visualizer-api-nu.vercel.app/delete-account
  ```
* **Developer Contact Email**:
  ```text
  prasannapbhurke@gmail.com
  ```

---

## 🛡️ Data Safety Form Answers (Cheat Sheet)

When filling out the Google Play Data Safety questionnaire:
* **Does your app collect or share user data?** &rarr; **Yes**
* **Is all of the user data collected by your app encrypted in transit?** &rarr; **Yes** (HTTPS)
* **Do you provide a way for users to request that their data is deleted?** &rarr; **Yes**
  * Enter URL: `https://dsa-visualizer-api-nu.vercel.app/delete-account`
  * Deletion timeline: **0 days** (immediate upon request).

### Data Types Collected:
1. **Personal info &rarr; Name & Email address**:
   * *Collected?* Yes (for Google Account sign-in).
   * *Shared?* No.
   * *Purpose?* App functionality, Account management.
2. **App info and performance &rarr; Diagnostics**:
   * *Collected?* Yes (learning progress, quiz scores, mistake bank entries).
   * *Purpose?* Analytics, App functionality.

---

## 🔑 Signing Certificate Details

* **SHA-1 Fingerprint**:
  ```text
  4B:46:A5:E5:6B:98:B4:4F:A4:23:A2:08:E8:71:A9:C4:26:1F:24:D4
  ```
* **SHA-256 Fingerprint**:
  ```text
  09:10:AD:CB:3A:57:F7:03:A1:EB:D0:1B:53:5A:B8:E3:9A:98:95:61:DD:CB:E0:DE:81:AD:6E:D9:F6:D6:EE:58
  ```
* **Certificate Validity**: Valid through **January 2054** (28 years).
