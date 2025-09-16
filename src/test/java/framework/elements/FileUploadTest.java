package framework.elements;

import framework.elements.control.FileUpload;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import static org.testng.Assert.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tests for FileUpload element using The Internet Herokuapp "File Upload" page
 */
public class FileUploadTest extends ElementsTestBase {
    
    private Path tempFilePath;
    private File tempFile;
    
    @BeforeMethod
    public void createTempFile() throws IOException {
        // Create a temp file for testing uploads
        tempFilePath = Files.createTempFile("test-upload-", ".txt");
        Files.write(tempFilePath, "Test file content".getBytes());
        tempFile = tempFilePath.toFile();
    }
    
    @AfterMethod
    public void cleanupTempFile() {
        // Clean up the temp file after each test
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testFileUpload() {
        // Navigate to the file upload example
        navigateToExample("/upload");
        
        // Arrange - initialize the file upload element
        FileUpload fileUpload = new FileUpload("#file-upload", "File Upload Input");
        
        // Act - upload the file
        fileUpload.uploadFile(tempFile.getAbsolutePath());
        
        // Click the submit button
        $("#file-submit").click();
        
        // Assert - verify the file was uploaded successfully
        $("#uploaded-files").shouldHave(text(tempFilePath.getFileName().toString()));
    }
    
    @Test
    public void testFileUploadMethodChaining() {
        // Navigate to the file upload example
        navigateToExample("/upload");
        
        // Arrange - initialize the file upload element
        FileUpload fileUpload = new FileUpload("#file-upload", "File Upload Input");
        
        // Act - upload the file using method chaining
        fileUpload.uploadFileAndChain(tempFile.getAbsolutePath());
        
        // Click the submit button
        $("#file-submit").click();
        
        // Assert - verify the file was uploaded successfully
        $("#uploaded-files").shouldHave(text(tempFilePath.getFileName().toString()));
    }
}
