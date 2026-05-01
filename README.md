# AutoCare Hub - Vehicle Service Booking System

AutoCare Hub is a Java MVC web application for booking vehicle service appointments online. It helps customers register, add their vehicles, choose an available service, book a date and time slot, and track their booking history. It also includes an admin panel for managing services and monitoring all users, vehicles, and bookings.

## Project Features

### User Features

- User registration with name, email, and password.
- User login with session-based authentication.
- Passwords are stored as SHA-256 hashes.
- User dashboard showing registered vehicles and recent bookings.
- Add vehicle details for cars and bikes.
- Vehicle number is stored in uppercase for consistent records.
- Duplicate vehicle numbers are prevented for the same user.
- Book service appointments for registered vehicles.
- Select an active service from the service list.
- Choose booking date and time.
- Booking validation allows today or future dates only.
- Booking time is restricted between 08:00 and 18:00.
- Duplicate bookings for the same vehicle, date, and time are prevented.
- View personal booking history with vehicle, service, price, date, time, and status.
- Logout support to end the current session.

### Admin Features

- Admin login with role-based access.
- Admin users are redirected to the admin panel after login.
- Normal users cannot access the admin panel.
- View all registered users.
- View all registered vehicles with owner details.
- View all service bookings across the system.
- Add new vehicle services with price.
- Update service name, price, and active status.
- Soft delete services by marking them inactive.
- Inactive services are hidden from the user booking page.
- Update booking status as `Pending`, `Completed`, or `Cancelled`.

### Booking and Validation Features

- Checks that the selected vehicle belongs to the logged-in user.
- Checks that the selected service exists and is active.
- Prevents past-date bookings.
- Prevents invalid service times outside working hours.
- Checks slot availability before creating a booking.
- Uses a database unique constraint on `date`, `time`, and `vehicle_id` to avoid duplicate slots.
- Handles last-moment duplicate slot conflicts with a clear validation message.

### Database Features

- MySQL database schema included in `src/main/resources/sql/schema.sql`.
- Tables included:
  - `users`
  - `vehicles`
  - `services`
  - `bookings`
- Foreign key relationships connect users, vehicles, services, and bookings.
- Vehicle records are deleted automatically when the related user is deleted.
- Booking records are linked to users, vehicles, and services.
- Demo admin, demo user, sample vehicles, sample services, and one sample booking are inserted by the schema.

## Tech Stack

- Java 11
- Servlet API 4.0.1
- JSP
- JSTL
- JDBC
- MySQL
- Maven
- Apache Tomcat 9
- HTML, CSS, and JavaScript

## Project Structure

```text
autocare-hub
+-- pom.xml
+-- README.md
+-- src/main/java/com/project
|   +-- controller
|   +-- dao
|   +-- model
|   +-- service
|   +-- util
+-- src/main/resources
|   +-- db.properties
|   +-- sql/schema.sql
+-- src/main/webapp
    +-- css
    +-- js
    +-- jsp
    +-- WEB-INF/web.xml
```

## Main Pages and Routes

- `/login` - user and admin login page.
- `/register` - new user registration page.
- `/dashboard` - user dashboard.
- `/vehicles` - add and view user vehicles.
- `/book` - book a vehicle service.
- `/history` - view user booking history.
- `/admin` - admin dashboard and management panel.
- `/logout` - logout route.

## Demo Logins

### Admin

```text
Email: admin@autocare.com
Password: admin123
```

### User

```text
Email: rahul@example.com
Password: admin123
```

## Setup and Run Steps

### 1. Create Database

Run the schema file from MySQL using a user that has permission to create databases and users:

```bash
mysql -u root -p < src/main/resources/sql/schema.sql
```

This creates the `autocare_hub` database, application user, tables, and demo data.

### 2. Check Database Configuration

Database credentials are configured in:

```text
src/main/resources/db.properties
```

The schema creates this application database user:

```properties
db.url=jdbc:mysql://localhost:3306/autocare_hub
db.username=autocare_user
db.password=autocare123
```

If you prefer to connect with another MySQL account, update `db.properties` accordingly.

### 3. Build the WAR File

```bash
mvn clean package
```

The WAR file is generated at:

```text
target/autocare-hub.war
```

### 4. Deploy on Tomcat

1. Stop Tomcat.
2. Delete any old `webapps/autocare-hub` folder.
3. Delete any old `webapps/autocare-hub.war` file.
4. Copy `target/autocare-hub.war` into Tomcat's `webapps` folder.
5. Start Tomcat.

### 5. Open the Application

```text
http://localhost:8080/autocare-hub
```

## Application Flow

1. A new customer registers an account.
2. The customer logs in and reaches the dashboard.
3. The customer adds one or more vehicles.
4. The customer books a service by selecting vehicle, service, date, and time.
5. The booking is saved with `Pending` status.
6. The customer can view booking history.
7. The admin can view all records and update booking status.
8. The admin can add, update, activate, or deactivate services.

## Notes

- Services use soft delete through the `is_active` column.
- Only active services are shown to users during booking.
- Booking statuses supported by the system are `Pending`, `Completed`, and `Cancelled`.
- This is a student-friendly MVC project using Servlets, JSP, DAO classes, and JDBC.
- Password hashing uses SHA-256 for simplicity. For production systems, use stronger password hashing such as BCrypt or Argon2.
