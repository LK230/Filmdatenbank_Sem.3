package mi.filmdatenprofis.movieapp;

// Importing necessary libraries and classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Annotation to indicate this is a Spring Boot application
@SpringBootApplication
public class MoviesApplication {

	// Main method that serves as an entry point for the application
	public static void main(String[] args) {
		// Running the Spring Boot application
		SpringApplication.run(MoviesApplication.class, args);
	}

}