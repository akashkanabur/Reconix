# Reconix 🛡️⚡
> **Premium Android Cybersecurity Reconnaissance Platform**

Reconix is a modern, production-level Android application built for ethical hackers, security researchers, and bug bounty hunters. Inspired by premium design systems like Linear, Stripe, and modern glassmorphic cyberpunk aesthetics, Reconix bridges the gap between field security work and mobile productivity.

---

## 🎯 The Core Goal
The objective of Reconix is to provide security practitioners with a high-fidelity, responsive mobile cockpit to **discover targets**, **conduct lightweight passive and active reconnaissance utilities**, **manage vulnerability pipelines**, and **generate client-ready security audit reports** directly from their mobile device.

---

## 🚀 Key Problems Solved
1. **Tool Fragmention**: Security professionals traditionally rely on dozens of command-line tools (WHOIS, dig, Nmap, Wappalyzer). Reconix aggregates these utilities into a single, unified, cyber-themed Hub.
2. **Disconnected Workflows**: Bug hunting results are often scattered. Reconix provides an integrated pipeline where target discovery flows directly into subdomain enumeration, which can then be logged as a vulnerability, and eventually exported as a report.
3. **Lack of Mobility**: Laptops are not always accessible. Reconix offers lightweight, on-the-go scanning capabilities so researchers can keep track of scoping updates and alerts.
4. **Network Resilience & Offline Capability**: Public networks are notoriously unstable during field audits. Reconix implements an offline-first architecture with localized SQLite databases (Room) and graceful repository-level fallbacks to serve mock data when offline.

---

## 🛠️ Tech Stack & Libraries
* **Language**: 100% Kotlin with Coroutines & StateFlow
* **UI/UX**: Jetpack Compose, Material 3, Lottie, Coil (Image Loading)
* **Architecture**: Clean Architecture + MVVM Pattern
* **Local Storage**: Room DB (SQLite) & DataStore Preferences (Encrypted)
* **Networking**: Retrofit & OkHttp
* **DI**: Dagger Hilt
* **Security**: Biometric Prompt, Android KeyStore, EncryptedSharedPreferences
* **Authentication**: Firebase Authentication
* **Background Tasks**: WorkManager (for periodic target synchronization)

---

## 📂 Project Structure Used

The project is structured under a clean, modular, and scalable package system:

```text
com.reconix/
│
├── data/                      # Data layer (Repositories, DAOs, DTOs, API Interfaces)
│   ├── local/                 # Room database configuration, Entity models, and DAOs
│   ├── remote/                # Retrofit API Services and Data Transfer Objects (DTOs)
│   └── repository/            # Repository implementations mediating between local DB and network
│
├── domain/                    # Pure Kotlin Domain layer
│   └── model/                 # Pure domain models (User, BountyProgram, Subdomain, Vulnerability)
│
├── viewmodel/                 # UI State Owners (MVVM ViewModels managing lifecycle and business logic)
│
├── ui/                        # Presentation layer
│   ├── theme/                 # Cyberpunk colors, Outfit typography, and custom M3 themes
│   ├── components/            # Reusable glassmorphic cards, neon buttons, and top bars
│   └── screens/               # Individual screen layouts:
│       ├── splash/            # Futuristic radar animated startup screen
│       ├── onboarding/        # Jetpack Compose HorizontalPager intro
│       ├── auth/              # Glassmorphic Login/Register screen with Password Strength Meter
│       ├── home/              # Dashboard with Stripe-style statistics, actions, and findings
│       ├── domain/            # Bug bounty platform integration (HackerOne, Bugcrowd targets)
│       ├── subdomain/         # Subdomain Enumeration Terminal Simulator
│       ├── vulnerability/     # Issue pipeline with severity tags and attachments
│       ├── tools/             # Recon Tools Hub (DNS, WHOIS, Port Scanner, Headers, Tech Detect, SSL)
│       ├── reports/           # PDF Generation engine and sharing page
│       ├── profile/           # User gamified level, reputation points, and stats
│       ├── settings/          # Key management, dark/light theme, and security lock preferences
│       └── premium/           # Subscription UI with neon border gradients
│
├── navigation/                # Compose Navigation Compose routes and graphs
│
└── di/                        # Hilt modules for Dependency Injection
```

---

## 📈 Future Roadmap: Carrying it Forward
For developers or researchers taking this repository further, here are the recommended next steps:

1. **Integrate Real-Time API Scanners**:
   * Replace the dummy base URL in [NetworkModule.kt](file:///C:/Users/akash/OneDrive/Desktop/Reconix/app/src/main/java/com/reconix/di/NetworkModule.kt) with a real recon backend (e.g., custom Python API running `subfinder`, `nmap`, or `nuclei`).
2. **Local PDF Compile Engine**:
   * Expand the PDF generation in [ReportsScreen.kt] to dynamically construct reports using Android `PdfDocument` graphics rendering or HTML-to-PDF templates.
3. **Local Encryption Hardening**:
   * Wrap the Room Database in SQLCipher for complete device-level encryption of discovered targets and finding logs.
4. **Push Notifications Customization**:
   * Bind the Firebase Cloud Messaging module to monitor live scopes and alert users the millisecond a target adds new domains.
