package mi.filmdatenprofis.movieapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for setting up web-related configurations such as CORS mappings.
 * Implements the WebMvcConfigurer interface to customize the default Spring MVC configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * Configures CORS mappings for the application.
     *
     * This method allows cross-origin requests to all endpoints (/**) from the specified origin (http://localhost:3000).
     * It permits several HTTP methods (GET, POST, PUT, DELETE, OPTIONS) and allows specific headers.
     * Additionally, it exposes certain headers in the response.
     *
     * @param registry the CorsRegistry to add the CORS mapping configurations
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow cross-origin requests to all paths
        registry.addMapping("/**")
                // Allow requests from this specific origin
                .allowedOrigins("http://localhost:3000")
                // Allow these HTTP methods
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Allow these request headers
                .allowedHeaders("Content-Type", "Accept", "Authorization")
                // Expose these headers in the response
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

        // Log that the CORS mappings have been added
        logger.info("CORS mappings added");
    }
}