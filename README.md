# productivity-timer-app
A Java timer app for productivity with popups, built with Gradle and GitHub workflows.


A simple Java app that pops up windows every 25 minutes to boost productivity, showing the current clock time.

## Purpose
Learning Gradle, GitHub workflows, and Java.

## Setup
1. Clone: `git clone https://github.com/yourusername/productivity-timer-app.git`
2. Build: `./gradlew build` (or `gradlew.bat` on Windows)
3. Run: `./gradlew run`
4. Test: `./gradlew test`

## Publishing
- To Sonatype: Set `SONATYPE_USERNAME` and `SONATYPE_PASSWORD` env vars, then `./gradlew publish`.

## Workflows
- `build-test.yml`: Builds and runs tests on push/PR.
- `scan.yml`: Scans for vulnerabilities daily and on push.

## Customization
- Replace `com.example.timerapp` with your namespace in all Java files and build.gradle.
- Adjust timer interval in `App.java`.

MIT License.