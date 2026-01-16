
---

## Running the Application

### Prerequisites

- Java JDK 17+ (or the version required by the project)
- PostgreSQL installed and running
- IntelliJ IDEA (recommended)

### Steps

1. Open the project folder in **IntelliJ IDEA**
2. Configure the database connection in `application.properties`:
   - database URL
   - username
   - password
3. Let IntelliJ resolve Maven dependencies
4. Run the main Spring Boot application class using the IDE

The application will start locally and connect to the configured database.

---

## Database Management

The application uses a **relational PostgreSQL database** with normalized tables and constraints.

**Main entities include:**

- users  
- student_profiles  
- conversation_threads  
- chat_messages  
- requests  
- message_log  

**SQL views** are defined to simplify data access and reporting, such as:

- student request summaries  
- conversation summaries  
- open requests filtering  

---

## Main Features

### Authentication and Authorization
- User registration and login
- Role-based access control (`STUDENT` / `ADMIN`)

### Conversation Management
- Students can create conversation threads
- Messages are stored and linked to threads
- AI responses are logged for later inspection

### Request Management
- Students can submit requests with a specific type and message
- Administrators can view, update status, and respond to requests

### Data Persistence
- All data is stored in a relational database
- Integrity enforced using primary keys, foreign keys, and constraints

---

## Development Notes

- Dependency management is handled by **Maven**
- The project follows a **layered architecture**:
  - controller
  - service
  - repository
- The application can be extended with additional features such as notifications or analytics

---

## Future Improvements

Possible extensions include:

- improved user interface
- notifications for request status changes
- advanced AI assistance features
- reporting and statistics for administrators
