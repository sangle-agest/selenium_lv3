package framework.elements.input;

import framework.elements.core.Button;
import framework.utils.FileUtils;
import framework.utils.LogUtils;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

/**
 * Specialized button for file downloads
 */
public class FileDownloadButton extends Button {
    private final String downloadDirectory;
    private final int downloadTimeoutSeconds;

    /**
     * Constructor with custom download directory and timeout
     */
    public FileDownloadButton(String locator, String name, String downloadDirectory, int downloadTimeoutSeconds) {
        super(locator, name);
        this.downloadDirectory = downloadDirectory;
        this.downloadTimeoutSeconds = downloadTimeoutSeconds;
    }

    /**
     * Constructor with default download directory and timeout
     */
    public FileDownloadButton(String locator, String name) {
        this(locator, name, getDefaultDownloadDirectory(), 30);
    }

    /**
     * Get default download directory
     */
    private static String getDefaultDownloadDirectory() {
        return System.getProperty("user.home") + "/Downloads";
    }

    /**
     * Click button and wait for file to download
     */
    public File download(String expectedFileName) {
        LogUtils.logAction(toString(), "Downloading file: " + expectedFileName);
        
        try {
            // Delete existing file with same name if exists
            FileUtils.deleteFile(expectedFileName, downloadDirectory);
            
            // Click the download button
            click();
            
            // Wait for file to be downloaded
            boolean downloaded = FileUtils.waitForFileDownload(expectedFileName, downloadTimeoutSeconds);
            
            if (downloaded) {
                Path filePath = Paths.get(downloadDirectory, expectedFileName);
                File downloadedFile = filePath.toFile();
                LogUtils.logSuccess(toString(), String.format(
                    "File downloaded successfully: %s (%d bytes)", 
                    expectedFileName, downloadedFile.length()));
                return downloadedFile;
            } else {
                LogUtils.logError(toString(), 
                    "Download failed: File not found after " + downloadTimeoutSeconds + " seconds", 
                    new RuntimeException("Download timeout"));
                throw new RuntimeException("Download failed: Timeout waiting for file " + expectedFileName);
            }
        } catch (Exception e) {
            LogUtils.logError(toString(), "Download failed: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Download file when filename is not known in advance (uses predicate to match file)
     */
    public File downloadWithPattern(Predicate<String> fileNameMatcher) {
        LogUtils.logAction(toString(), "Downloading file with pattern matcher");
        
        try {
            // Get list of files before download
            File dir = new File(downloadDirectory);
            File[] filesBefore = dir.listFiles();
            
            // Click the download button
            click();
            
            // Wait for new file to appear
            long endTime = System.currentTimeMillis() + (downloadTimeoutSeconds * 1000L);
            File newFile = null;
            
            while (System.currentTimeMillis() < endTime) {
                File[] filesAfter = dir.listFiles();
                
                // Look for new files that match the pattern
                for (File file : filesAfter) {
                    if ((filesBefore == null || !containsFile(filesBefore, file)) && 
                            fileNameMatcher.test(file.getName())) {
                        newFile = file;
                        break;
                    }
                }
                
                if (newFile != null) {
                    break;
                }
                
                // Wait a bit before checking again
                Thread.sleep(500);
            }
            
            if (newFile != null) {
                LogUtils.logSuccess(toString(), String.format(
                    "File downloaded successfully: %s (%d bytes)", 
                    newFile.getName(), newFile.length()));
                return newFile;
            } else {
                LogUtils.logError(toString(), 
                    "Download failed: No matching file found after " + downloadTimeoutSeconds + " seconds", 
                    new RuntimeException("Download timeout"));
                throw new RuntimeException("Download failed: No matching file found");
            }
        } catch (InterruptedException e) {
            LogUtils.logError(toString(), "Download interrupted", e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Download interrupted", e);
        } catch (Exception e) {
            LogUtils.logError(toString(), "Download failed: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Check if array contains file
     */
    private boolean containsFile(File[] files, File targetFile) {
        for (File file : files) {
            if (file.getAbsolutePath().equals(targetFile.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the path where files will be downloaded
     */
    public String getDownloadPath() {
        return downloadDirectory;
    }

    /**
     * Wait for download to complete
     */
    public boolean waitForDownload(String fileName) {
        LogUtils.logAction(toString(), "Waiting for file to download: " + fileName);
        try {
            boolean downloaded = FileUtils.waitForFileDownload(fileName, downloadTimeoutSeconds);
            if (downloaded) {
                LogUtils.logSuccess(toString(), "File downloaded successfully: " + fileName);
            } else {
                LogUtils.logWarning(toString(), "File not downloaded after " + downloadTimeoutSeconds + " seconds");
            }
            return downloaded;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Error waiting for download", e);
            throw e;
        }
    }

    /**
     * Get full path to downloaded file
     */
    public String getDownloadedFilePath(String fileName) {
        LogUtils.logAction(toString(), "Getting path for downloaded file: " + fileName);
        try {
            Path filePath = Paths.get(downloadDirectory, fileName);
            String path = filePath.toString();
            LogUtils.logSuccess(toString(), "File path: " + path);
            return path;
        } catch (Exception e) {
            LogUtils.logError(toString(), "Error getting file path", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("FileDownloadButton '%s' [%s] {dir: %s}", 
                getName(), getLocator(), downloadDirectory);
        } catch (Exception e) {
            return String.format("FileDownloadButton '%s'", getName());
        }
    }
}
