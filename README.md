# CalcApp 🧮

A simple and clean Android calculator app built with Kotlin. Features a dark theme with circular purple buttons and supports basic arithmetic operations.

> Developed as a personal Android learning project.

---

## 🛠️ Technologies

- [Kotlin](https://kotlinlang.org/) — primary programming language
- [Android SDK](https://developer.android.com/) — Android development framework
- [ConstraintLayout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout) — flexible UI layout system
- [Google Fonts (Chewy)](https://fonts.google.com/specimen/Chewy) — downloadable font via Google Play Services

---

## 📱 Features

- Basic operations: addition, subtraction, multiplication, division
- Expression-based input (e.g. `3+5*2`)
- AC button to clear the display
- Clean dark UI with circular buttons and the Chewy font

---

## 🗂️ Project Structure

```
app/src/main/
│
├── java/com/example/myappli/
│   └── MainActivity.kt                  # Main activity: UI logic and expression parser
│
└── res/
    ├── drawable/
    │   ├── circleb.xml                  # Circular shape used as button background
    │   ├── ic_launcher_background.xml   # Adaptive icon background layer
    │   └── ic_launcher_foreground.xml   # Adaptive icon foreground (calculator symbol)
    │
    ├── font/
    │   └── chewy.xml                    # Downloadable font definition (Google Fonts)
    │
    ├── layout/
    │   └── layout.xml                   # Main calculator screen layout
    │
    └── values/
        ├── colors.xml                   # App color palette
        ├── font_certs.xml               # SSL certificates for Google Fonts provider
        ├── preloaded_fonts.xml          # Font pre-loading declaration
        ├── strings.xml                  # App name and string resources
        └── themes.xml                   # App visual theme
```

---

## ⚙️ Running the Project

### 1. Install Android Studio

Go to [https://developer.android.com/studio](https://developer.android.com/studio), download the latest stable version and follow the setup wizard.

### 2. Clone the Repository

```bash
git clone https://github.com/angel-lara-g/CalcApp.git
```

Or download it directly from GitHub as a ZIP and extract it.

### 3. Open the Project

1. Open Android Studio
2. Click **File** → **Open**
3. Navigate to the cloned/extracted folder and click **OK**
4. Wait for Android Studio to index the project and sync Gradle automatically

### 4. Build the Project

1. Click **File** → **Sync Project with Gradle Files** (or the elephant icon 🐘 in the toolbar)
2. Once sync completes, click **Build** → **Assemble Project** to verify there are no errors

---

## 📦 Generating the APK

### Option A — Debug APK (for personal testing)

1. Go to **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. Wait for the build to finish
3. A notification will appear at the bottom right — click **locate** to find the file
4. The APK will be at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```
5. Transfer it to your Android device (via USB, email, or Google Drive) and install it

> **Note:** To install an APK manually you may need to enable **"Install unknown apps"** in your device settings under **Security** or **Privacy**.

### Option B — Release APK (for sharing or publishing)

1. Go to **Build** → **Generate Signed Bundle / APK**
2. Select **APK** and click **Next**
3. Create or select a **keystore file** (this signs the app to verify its author)
4. Fill in the keystore details and click **Next**
5. Select **release** as the build variant and click **Finish**
6. The signed APK will be at:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

> **Tip:** Keep your keystore file safe — you will need it to publish future updates to the same app.

---

## 🧪 Testing on an Emulator

1. In Android Studio, click **Device Manager** (in the right toolbar or under **View** → **Tool Windows**)
2. Click **Create Device**, choose a phone model (e.g. Pixel 6) and click **Next**
3. Download a system image (e.g. Android 13 / API 33) and click **Next** → **Finish**
4. Press the **▶ Run** button (or `Shift + F10`) to launch the app on the emulator

---

## Demo

<p align="center">
  <video src="https://github.com/user-attachments/assets/79a0d1ea-022a-412d-84d7-ce09cb6876bd" width="300" controls></video>
</p>

