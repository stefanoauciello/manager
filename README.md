# Manager

Manager is an Android application designed to organize friendly soccer matches.
It lets you save players to a local database, choose participants for a match and automatically generate two balanced teams. The app supports both 5-a-side and 7-a-side modes. By shaking the device you can randomly reshuffle the team members.

This project was developed as my high school final exam project.

## Main features
- Add, edit and remove players through a dedicated interface with storage via SQLite.
- Select the available players and create teams for 5 or 7-a-side matches.
- Ability to reshuffle the teams using a button or by shaking the phone thanks to the accelerometer.

## Build requirements
- Android SDK (minSdkVersion 16).
- Gradle (the project includes the necessary scripts).

To build the application run
```
./gradlew assembleDebug
```
The resulting APK will be located under `app/build/outputs/apk/`.
