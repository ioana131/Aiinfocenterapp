AIInfoCenter – Spring Boot Application

This project is a Java Spring Boot application that provides a centralized platform for student–admin interaction, including authentication, request management, and AI-assisted conversations. It is designed as an academic project and follows a layered architecture with persistent storage.

Project Overview

AIInfoCenter allows:

students to register, log in, create conversation threads, and submit requests

administrators to manage users and handle student requests

persistent storage of users, messages, conversations, and requests in a relational database

The application exposes a backend API used by the user interface.

Tech Stack

Backend: Java, Spring Boot

Build Tool: Maven

Database: PostgreSQL

Persistence: Spring Data JPA / Hibernate

Security: Role-based access (STUDENT / ADMIN)


Running the Application
Prerequisites

Java JDK 17+ (or the version required by your project)

PostgreSQL installed and running

IntelliJ IDEA (recommended)

Steps

Open the project folder in IntelliJ IDEA

Configure the database connection in application.properties:

database URL

username

password

Let IntelliJ resolve Maven dependencies

Run the main Spring Boot application class using the IDE

The application will start locally and connect to the configured database.

Database Management

The application uses a relational PostgreSQL database with normalized tables and constraints.

Main entities include:

users

student_profiles

conversation_threads

chat_messages

requests

message_log

SQL views are defined to simplify data access and reporting, such as:

student request summaries

conversation summaries

open requests filtering

Main Features
Authentication and Authorization

User registration and login

Role-based access control (STUDENT / ADMIN)

Conversation Management

Students can create conversation threads

Messages are stored and linked to threads

AI responses are logged for later inspection

Request Management

Students can submit requests with a specific type and message

Administrators can view, update status, and respond to requests

Data Persistence

All data is stored in a relational database

Integrity enforced using primary keys, foreign keys, and constraints

Development Notes

Dependency management is handled by Maven

The project follows a layered architecture:

controller

service

repository

The application can be extended with additional features such as notifications or analytics
