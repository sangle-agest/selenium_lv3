package framework.elements.control;

import framework.elements.core.BaseElement;
import java.io.File;

/**
 * File upload element wrapper
 */
public class FileUpload extends BaseElement {
    public FileUpload(String locator, String name) {
        super(locator, name);
    }

    /**
     * Upload a file by providing its path
     */
    public void uploadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        getElement().uploadFile(file);
    }
    
    /**
     * Upload a file by providing its path with method chaining
     * @return this FileUpload for method chaining
     */
    public FileUpload uploadFileAndChain(String filePath) {
        uploadFile(filePath);
        return this;
    }

    /**
     * Upload multiple files
     */
    public void uploadFiles(String... filePaths) {
        for (String filePath : filePaths) {
            uploadFile(filePath);
        }
    }
    
    /**
     * Upload multiple files with method chaining
     * @return this FileUpload for method chaining
     */
    public FileUpload uploadFilesAndChain(String... filePaths) {
        uploadFiles(filePaths);
        return this;
    }

    /**
     * Get the name of the uploaded file
     */
    public String getUploadedFileName() {
        String value = getElement().getValue();
        if (value != null && !value.isEmpty()) {
            // Extract filename from full path
            return value.substring(value.lastIndexOf(File.separator) + 1);
        }
        return "";
    }

    @Override
    public String toString() {
        String fileName = getUploadedFileName();
        return String.format("FileUpload '%s' [%s]", getName(), 
            fileName.isEmpty() ? "no file" : fileName);
    }
}
