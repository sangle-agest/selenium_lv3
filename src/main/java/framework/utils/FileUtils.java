package framework.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for file operations
 */
public class FileUtils {
    
    private static final String DEFAULT_DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads";
    
    /**
     * Check if file exists in downloads folder
     */
    public static boolean isFileDownloaded(String fileName) {
        return isFileDownloaded(fileName, DEFAULT_DOWNLOAD_DIR);
    }

    /**
     * Check if file exists in specific directory
     */
    public static boolean isFileDownloaded(String fileName, String directory) {
        LogUtils.logAction("File", String.format("Checking if file '%s' exists in: %s", fileName, directory));
        try {
            Path filePath = Paths.get(directory, fileName);
            File file = filePath.toFile();
            boolean exists = file.exists();
            
            if (exists) {
                LogUtils.logSuccess("File", String.format("File '%s' found", fileName));
            } else {
                LogUtils.logWarning("File", String.format("File '%s' not found", fileName));
            }
            
            return exists;
        } catch (Exception e) {
            LogUtils.logError("File", String.format("Failed to check file: %s", fileName), e);
            throw e;
        }
    }

    /**
     * Wait for file to be downloaded
     */
    public static boolean waitForFileDownload(String fileName, int timeoutSeconds) {
        LogUtils.logAction("File", String.format("Waiting for file '%s' to download (timeout: %ds)", 
            fileName, timeoutSeconds));
        
        try {
            long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
            
            while (System.currentTimeMillis() < endTime) {
                if (isFileDownloaded(fileName)) {
                    LogUtils.logSuccess("File", String.format("File '%s' downloaded successfully", fileName));
                    return true;
                }
                Thread.sleep(500); // Check every half second
            }
            
            LogUtils.logWarning("File", String.format("File '%s' not downloaded after %d seconds", 
                fileName, timeoutSeconds));
            return false;
        } catch (InterruptedException e) {
            LogUtils.logError("File", "Wait interrupted", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (Exception e) {
            LogUtils.logError("File", String.format("Failed while waiting for file: %s", fileName), e);
            throw e;
        }
    }

    /**
     * Delete file if it exists
     */
    public static boolean deleteFile(String fileName) {
        return deleteFile(fileName, DEFAULT_DOWNLOAD_DIR);
    }

    /**
     * Delete file from specific directory
     */
    public static boolean deleteFile(String fileName, String directory) {
        LogUtils.logAction("File", String.format("Deleting file '%s' from: %s", fileName, directory));
        try {
            Path filePath = Paths.get(directory, fileName);
            File file = filePath.toFile();
            
            if (!file.exists()) {
                LogUtils.logWarning("File", String.format("File '%s' does not exist", fileName));
                return false;
            }
            
            boolean deleted = file.delete();
            if (deleted) {
                LogUtils.logSuccess("File", String.format("File '%s' deleted successfully", fileName));
            } else {
                LogUtils.logWarning("File", String.format("Failed to delete file '%s'", fileName));
            }
            
            return deleted;
        } catch (Exception e) {
            LogUtils.logError("File", String.format("Error deleting file: %s", fileName), e);
            throw e;
        }
    }
}
