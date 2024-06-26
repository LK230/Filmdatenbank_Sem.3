# Film Database App -
<img src="Frontend/my-app/src/assets/images/Logo-cinecritique.png" alt="logo" width="200" height="auto" />

## Description
This is a complete film database software project that consists of a backend with Java Spring Boot and a frontend with React.
The project allows users to browse movies, rate them, add favorites, and write reviews.
It includes a REST API and connects to a MongoDB database.

## Installation
### Requirements

- Java 11 or higher
- Node.js and npm
- MongoDB

### Backend Installation (Java Spring Boot)

1. **Clone repository:**
   ```sh
   git clone https://gitlab.mi.hdm-stuttgart.de/jf136/filmdatenbank.git

2. **Set up MongoDB Atlas:**
- Create an account on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
- Create a new cluster and obtain the connection string.

3. **Configure MongoDB Connection:**
- Enter your MongoDB connection cridentials in `src/main/resources/.env` file
  <span style="color: red;">or just leave it and use our environment variables.</span>

4. **Navigate to the backend directory:**
   ```sh
   cd Backend

5. **Install dependencies:**
   ```sh
   ./mvnw install

6. **Start the application:**
   Run main in Backend/src/main/java/mi/filmdatenprofis/movieapp/MovieApplication.java
   <br>or: 
   ```sh
   ./mvnw spring-boot:run
   
### Frontend Installation

1. Install dependencies:
   ```sh
   cd Frontend/my-app/
   npm install

2. Start the application:
   ```sh
   npm start
   
3. Go to http://localhost:3000

## Usage
You can reach the backend via http://localhost:8080 and see/test endpoints via Swagger UI on http://localhost:8080/swagger-ui/index.html

### *Note
In the Backend directory there is a README file with information about all the API-Endpoints.