package framework.elements.input;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import framework.elements.core.BaseElement;
import framework.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

/**
 * Rich Text Editor element wrapper (WYSIWYG editors like TinyMCE, CKEditor, Quill)
 */
public class RichTextEditor extends BaseElement {
    private final String frameLocator; // Used for iframe-based editors
    private final EditorType editorType;

    /**
     * Constructor for iframe-based editors
     */
    public RichTextEditor(String editorLocator, String frameLocator, String name, EditorType editorType) {
        super(editorLocator, name);
        this.frameLocator = frameLocator;
        this.editorType = editorType;
    }

    /**
     * Constructor for editors that don't use iframes
     */
    public RichTextEditor(String editorLocator, String name, EditorType editorType) {
        this(editorLocator, null, name, editorType);
    }

    /**
     * Set text to editor (replaces existing content)
     */
    public void setText(String text) {
        LogUtils.logAction(toString(), "Setting text in rich text editor");
        try {
            switchToEditorFrame();

            // Clear existing content
            clearEditor();
            
            // Insert new content based on editor type
            switch (editorType) {
                case TINYMCE:
                    Selenide.executeJavaScript("tinymce.activeEditor.setContent(arguments[0])", text);
                    break;
                case CKEDITOR:
                    Selenide.executeJavaScript("CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].setData(arguments[0])", text);
                    break;
                case QUILL:
                    Selenide.executeJavaScript("document.querySelector('.ql-editor').innerHTML = arguments[0]", text);
                    break;
                default:
                    // For other editors, use the content-editable div
                    element.sendKeys(text);
            }
            
            LogUtils.logSuccess(toString(), "Text set successfully");
            switchToDefaultContent();
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to set text in editor", e);
            throw e;
        }
    }

    /**
     * Insert text at current cursor position
     */
    public void insertText(String text) {
        LogUtils.logAction(toString(), "Inserting text at cursor position");
        try {
            switchToEditorFrame();
            
            switch (editorType) {
                case TINYMCE:
                    Selenide.executeJavaScript("tinymce.activeEditor.insertContent(arguments[0])", text);
                    break;
                case CKEDITOR:
                    Selenide.executeJavaScript(
                        "CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].insertText(arguments[0])", 
                        text);
                    break;
                case QUILL:
                    // Focus the editor first
                    Selenide.executeJavaScript("document.querySelector('.ql-editor').focus()");
                    element.sendKeys(text);
                    break;
                default:
                    // For other editors, simply send keys
                    element.sendKeys(text);
            }
            
            LogUtils.logSuccess(toString(), "Text inserted successfully");
            switchToDefaultContent();
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to insert text", e);
            throw e;
        }
    }

    /**
     * Get text from editor
     */
    public String getText() {
        LogUtils.logAction(toString(), "Getting text from rich text editor");
        try {
            switchToEditorFrame();
            
            String content;
            switch (editorType) {
                case TINYMCE:
                    content = Selenide.executeJavaScript("return tinymce.activeEditor.getContent()");
                    break;
                case CKEDITOR:
                    content = Selenide.executeJavaScript(
                        "return CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].getData()");
                    break;
                case QUILL:
                    content = Selenide.executeJavaScript("return document.querySelector('.ql-editor').innerHTML");
                    break;
                default:
                    // For other editors, get the HTML content
                    content = element.getAttribute("innerHTML");
            }
            
            LogUtils.logSuccess(toString(), "Got text from editor: " + 
                (content.length() > 100 ? content.substring(0, 97) + "..." : content));
            switchToDefaultContent();
            return content;
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to get text from editor", e);
            throw e;
        }
    }

    /**
     * Clear editor content
     */
    public void clearEditor() {
        LogUtils.logAction(toString(), "Clearing rich text editor");
        try {
            switchToEditorFrame();
            
            switch (editorType) {
                case TINYMCE:
                    Selenide.executeJavaScript("tinymce.activeEditor.setContent('')");
                    break;
                case CKEDITOR:
                    Selenide.executeJavaScript(
                        "CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].setData('')");
                    break;
                case QUILL:
                    Selenide.executeJavaScript("document.querySelector('.ql-editor').innerHTML = ''");
                    break;
                default:
                    // For other editors, clear via select all and delete
                    element.click();
                    element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    element.sendKeys(Keys.DELETE);
            }
            
            LogUtils.logSuccess(toString(), "Editor cleared successfully");
            switchToDefaultContent();
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to clear editor", e);
            throw e;
        }
    }

    /**
     * Apply formatting (bold, italic, etc.)
     */
    public void applyFormat(FormatType formatType) {
        LogUtils.logAction(toString(), "Applying format: " + formatType);
        try {
            switchToEditorFrame();
            
            switch (editorType) {
                case TINYMCE:
                    switch (formatType) {
                        case BOLD:
                            Selenide.executeJavaScript("tinymce.activeEditor.execCommand('Bold')");
                            break;
                        case ITALIC:
                            Selenide.executeJavaScript("tinymce.activeEditor.execCommand('Italic')");
                            break;
                        case UNDERLINE:
                            Selenide.executeJavaScript("tinymce.activeEditor.execCommand('Underline')");
                            break;
                    }
                    break;
                    
                case CKEDITOR:
                    switch (formatType) {
                        case BOLD:
                            Selenide.executeJavaScript(
                                "CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].execCommand('bold')");
                            break;
                        case ITALIC:
                            Selenide.executeJavaScript(
                                "CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].execCommand('italic')");
                            break;
                        case UNDERLINE:
                            Selenide.executeJavaScript(
                                "CKEDITOR.instances[Object.keys(CKEDITOR.instances)[0]].execCommand('underline')");
                            break;
                    }
                    break;
                    
                case QUILL:
                    switch (formatType) {
                        case BOLD:
                            Selenide.executeJavaScript("document.querySelector('.ql-bold').click()");
                            break;
                        case ITALIC:
                            Selenide.executeJavaScript("document.querySelector('.ql-italic').click()");
                            break;
                        case UNDERLINE:
                            Selenide.executeJavaScript("document.querySelector('.ql-underline').click()");
                            break;
                    }
                    break;
                    
                default:
                    // For other editors, use keyboard shortcuts
                    switch (formatType) {
                        case BOLD:
                            element.sendKeys(Keys.chord(Keys.CONTROL, "b"));
                            break;
                        case ITALIC:
                            element.sendKeys(Keys.chord(Keys.CONTROL, "i"));
                            break;
                        case UNDERLINE:
                            element.sendKeys(Keys.chord(Keys.CONTROL, "u"));
                            break;
                    }
            }
            
            LogUtils.logSuccess(toString(), "Format applied successfully: " + formatType);
            switchToDefaultContent();
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to apply format: " + formatType, e);
            throw e;
        }
    }

    /**
     * Switch to editor iframe if it exists
     */
    private void switchToEditorFrame() {
        if (frameLocator != null) {
            try {
                Selenide.switchTo().frame(Selenide.$(frameLocator));
            } catch (Exception e) {
                LogUtils.logWarning(toString(), "Failed to switch to editor frame: " + frameLocator);
                // Continue without switching frame
            }
        }
    }

    /**
     * Switch back to default content
     */
    private void switchToDefaultContent() {
        if (frameLocator != null) {
            try {
                Selenide.switchTo().defaultContent();
            } catch (Exception e) {
                LogUtils.logWarning(toString(), "Failed to switch back to default content");
                // Continue anyway
            }
        }
    }

    /**
     * Check if editor is ready/loaded
     */
    public boolean isEditorReady() {
        LogUtils.logAction(toString(), "Checking if editor is ready");
        try {
            switchToEditorFrame();
            
            boolean ready;
            switch (editorType) {
                case TINYMCE:
                    ready = Selenide.executeJavaScript("return (typeof tinymce !== 'undefined' && tinymce.activeEditor !== null)");
                    break;
                case CKEDITOR:
                    ready = Selenide.executeJavaScript("return (typeof CKEDITOR !== 'undefined' && Object.keys(CKEDITOR.instances).length > 0)");
                    break;
                case QUILL:
                    ready = Selenide.executeJavaScript("return document.querySelector('.ql-editor') !== null");
                    break;
                default:
                    ready = element.isDisplayed();
            }
            
            LogUtils.logSuccess(toString(), "Editor is " + (ready ? "ready" : "not ready"));
            switchToDefaultContent();
            return ready;
        } catch (Exception e) {
            switchToDefaultContent();
            LogUtils.logError(toString(), "Failed to check if editor is ready", e);
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            return String.format("RichTextEditor '%s' [%s] {type: %s, frame: %s}", 
                getName(), getLocator(), editorType, 
                frameLocator != null ? frameLocator : "none");
        } catch (Exception e) {
            return String.format("RichTextEditor '%s'", getName());
        }
    }

    /**
     * Types of rich text editors
     */
    public enum EditorType {
        TINYMCE,
        CKEDITOR,
        QUILL,
        GENERIC
    }

    /**
     * Types of formatting
     */
    public enum FormatType {
        BOLD,
        ITALIC,
        UNDERLINE
    }
}
