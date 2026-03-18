# Soccer Field Booking & Tournament Management System

## Project Overview
Hệ thống số hóa quy trình đặt sân bóng và quản lý giải đấu phong trào. Giải quyết các vấn đề về trùng lịch và quy trình thủ công.

## Tech Stack
- Backend: Java, Spring Boot (Spring Data JPA, Spring Security, REST API)
- Frontend: Java, JavaFX, SceneBuilder (FXML)
- Database: MySQL / PostgreSQL
- Build Tool: Maven

## Project Structure
```
football-booking-project/
├── backend/                        # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hust/it3180/
│   │   │   │   ├── config/         # Security & App configuration
│   │   │   │   ├── controllers/    # REST Endpoints (Booking, Field, User)
│   │   │   │   ├── services/       # Business logic
│   │   │   │   ├── repositories/   # Database access (JPA)
│   │   │   │   ├── entities/       # Database models
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   └── FootballApp.java  # Main entry
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── static/         # Image uploads (fields, logos)
│   │   └── test/                   # Unit tests for backend
│   └── pom.xml
├── frontend/                       # JavaFX Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hust/it3180/
│   │   │   │   ├── controllers/    # UI logic (Link with FXML)
│   │   │   │   ├── services/       # API Clients (To call Spring Boot)
│   │   │   │   ├── models/         # Client-side data models
│   │   │   │   ├── utils/          # Navigation & Helper classes
│   │   │   │   └── App.java        # JavaFX Entry Point
│   │   │   └── resources/
│   │   │       ├── fxml/           # UI layout files (Scene Builder)
│   │   │       ├── css/            # Styling for application
│   │   │       └── images/         # Icons & Static UI assets
│   │   └── test/                   # UI testing
│   └── pom.xml
├── docs/                           # Project Documentation
│   ├── diagrams/                   # Use Case, ERD
│   └── Idea_Proposal.pdf           # Original idea 
└── README.md
```
## Core Features Implementation
- [x] Field Search & Filters: Tìm sân theo khu vực, giá, khung giờ.
- [x] Online Booking: Đặt sân trực tuyến và xem lịch trống dạng Calendar.
- [x] Manager Dashboard: Chủ sân quản lý đơn đặt và lịch thi đấu.
- [ ] Tournament Management: Tự động sinh lịch thi đấu (Phase 2).
- [ ] Reward System: Tích điểm và ưu đãi cho người dùng (Phase 2).
