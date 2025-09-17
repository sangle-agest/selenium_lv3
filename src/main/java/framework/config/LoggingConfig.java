package framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Configuration to suppress Selenium logging
 */
public class LoggingConfig {
    
    private static final Logger log = LoggerFactory.getLogger(LoggingConfig.class);
    
    /**
     * Initialize logging configurations to suppress unwanted logs
     */
    public static void initSuppressedLogging() {
        try {
            // Suppress CDP version mismatch warnings
            System.setProperty("org.openqa.selenium.devtools.CdpVersionFinder.suppressDevToolsLogging", "true");
            
            // Suppress Selenium logs
            java.util.logging.Logger seleniumLogger = LogManager.getLogManager().getLogger("");
            seleniumLogger.setLevel(Level.SEVERE); // Only show severe errors
            
            log.info("Selenium logging configured to suppress warnings");
        } catch (Exception e) {
            log.warn("Failed to configure Selenium logging: {}", e.getMessage());
        }
    }
}
