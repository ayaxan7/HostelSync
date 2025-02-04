# HostelSync - PG/Hostel Communication & Management System

## 📌 Overview
**HostelSync** is a **two-app system** designed to modernize communication between **PG/hostel residents** and **staff/owner**. Built using **Firebase, Jetpack Compose, and MVVM**, the system streamlines notices, residents' requests, and payments.

### 🚀 Features

### **Resident App**
1. **🏡 Home Screen** – Displays notices posted by the admin in real-time.
2. **💳 Payment Gateway (Razorpay)** – Allows rent payments with room-sharing options (**Single, Double, Triple**). The payment button is enabled upon room selection.
3. **📢 Complaint/Request Form** – Residents can submit complaints or requests, which appear on the admin app.

### **Admin App**
1. **📋 Home Screen** – Displays complaints/requests submitted by residents.
2. **👥 User Creation** – Only an **admin** can create new resident accounts.
3. **📝 Noticeboard Form** – Admins can post notices, which are updated on the resident app.

---

## 🛠️ Tech Stack
- **Android:** Kotlin + Jetpack Compose + MVVM 
- **Backend:** Firebase (Firestore, Authentication)
- **Payment Gateway:** Razorpay
- **State Management:** ViewModel + LiveData

## 🔧 Installation & Setup
1. Clone this repository:
   ```sh
   git clone https://github.com/ayaxan7/HostelSync.git
   ```
2. Open in **Android Studio**.
3. Set up **Firebase**:
   - Add `google-services.json` in `app/`.
   - Enable Firestore & Authentication in Firebase Console.
4. Configure **Razorpay API Keys**.
5. Run the app on an emulator or device.

## 📌 Future Improvements
- ✅ **Push Notifications** for new notices & complaint updates.
- ✅ **Complaint Status Tracking** (Pending, In Progress, Resolved).
- ✅ **Payment History** section.

---
Made with ❤️ by your friendly neighbourhood crazy coder
