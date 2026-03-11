
```markdown
# Productivity Timer App

A simple, lightweight desktop application built in **Java** to help improve focus and productivity using timed work sessions (count-up timer with Play/Pause/Finish controls) and daily statistics visualization.

The timer counts up during work sessions.
When you finish a session (or pause), the time is saved per day.
Statistics show daily focused hours in a line chart.

**Current version**: 1.0.0 (learning project with Gradle, GitHub Actions, Maven publishing)

## Features

- Count-up timer (shows elapsed time in HH:MM:SS)
- Play / Pause / Finish controls
- Always-on-top small window (persistent during work)
- Daily focused time saved automatically
- Statistics window with line chart (daily hours worked)
- Data stored in simple JSON file (`daily_sessions.json`)
- Built with Swing (no external heavy UI framework)
- Gradle build system + GitHub Actions (build, test, security scan)



## Project Structure
productivity-timer-app/
├── .github/
│   └── workflows/
│       ├── build-test.yml          # CI: build + test on push/PR
│       └── scan.yml                # Security + dependency scanning
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/timerapp/
│   │   │   │       ├── App.java                # Main entry point
│   │   │   │       ├── MainTimerWindow.java    # Main UI + logic
│   │   │   │       ├── SessionDataManager.java # Save/load daily data
│   │   │   │       └── StatsWindow.java        # Chart window
│   │   │   └── resources/                      # (future: icons, etc.)
│   │   └── test/
│   │       └── java/
│   │           └── com/example/timerapp/       # Unit tests (to be added)
│   └── build.gradle.kts                        # or build.gradle (module config)
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── screenshots/                  
├── daily_sessions.json           # Generated: daily time data (git ignored)
├── build.gradle                  # Root build file
├── settings.gradle               # Project name + modules
├── gradlew                       # Unix wrapper
├── gradlew.bat                   # Windows wrapper
├── .gitignore
└── README.md


## Data Model Explanation

The application uses a very simple file-based persistence model:

- File: `daily_sessions.json` (CSV-like format, one line per day)
- Format example:

  ```
  2026-03-07,2521
  2026-03-08,4500
  ```

  - Key: `yyyy-MM-dd` (ISO date)
  - Value: total seconds focused that day

- In memory: `Map<LocalDate, Long>` where `Long` = total seconds
- Save/load happens automatically on Finish/Pause and app close
- No database → easy to understand and modify for learning

## Prerequisites

- **Java 17** (or higher) installed
  - Recommended: Eclipse Temurin / Adoptium JDK 17
  - Download: https://adoptium.net/temurin/releases/?version=17
  - Verify: `java -version` should show `openjdk 17.x.x ...`

- **Git** installed (to clone the repository)
- Internet connection (first Gradle run downloads dependencies)

No need to install Gradle manually — the project uses **Gradle Wrapper** (`gradlew.bat`).

## How to Install & Run

1. Clone the repository

   ```bash
   git clone https://github.com/yourusername/productivity-timer-app.git
   cd productivity-timer-app
   ```

2. Build the project (downloads dependencies)

   Windows:
   ```batch
   gradlew.bat build
   ```

   Linux/macOS:
   ```bash
   ./gradlew build
   ```

3. Run the application

   Windows:
   ```batch
   gradlew.bat run
   ```

   Linux/macOS:
   ```bash
   ./gradlew run
   ```

   → The small timer window should appear.

4. (Optional) Create a runnable JAR

   ```bash
   gradlew.bat shadowJar   # if you add shadow plugin
   # or classic jar
   gradlew.bat jar
   ```

   Then run:
   ```bash
   java -jar app/build/libs/app-all.jar
   ```

## How to Contribute / Participate

1. Fork the repository on GitHub
2. Clone your fork locally:

   ```bash
   git clone https://github.com/YOUR-USERNAME/productivity-timer-app.git
   ```

3. Create a new branch:

   ```bash
   git checkout -b feature/my-cool-idea
   ```

4. Make changes (add features, fix bugs, improve UI, add tests...)
5. Commit & push:

   ```bash
   git add .
   git commit -m "Add tray icon support"
   git push origin feature/my-cool-idea
   ```

6. Open a **Pull Request** on the original repository

**Ideas for contributions**:
- Add break timer (Pomodoro style)
- System tray icon + minimize to tray
- Export statistics to CSV/PDF
- Configurable session goal (daily target)
- Sound notifications
- Better chart (zoom, tooltips, colors)
- Unit tests (JUnit)
- Dark mode support

## Technologies Used

- Java 17
- Swing (GUI)
- JFreeChart (statistics chart)
- Gradle (build tool)
- GitHub Actions (CI/CD: build, test, security scan)

## License

MIT License (see [LICENSE](LICENSE) file)

---

Made with ❤️ for learning Java, Gradle, GitHub, and productivity tools.
Feel free to fork, modify, and PR!
```
```
https://github.com/user-attachments/assets/846eb4d6-a1ed-4120-907f-4adc1e9b1028
```




