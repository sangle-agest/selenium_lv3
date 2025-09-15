package framework.pages.vietjet;

import com.codeborne.selenide.SelenideElement;
import framework.base.BasePage;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class PassengerDetailsPage extends BasePage {
    // Passenger form elements
    private final SelenideElement titleSelect = $("#title");
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement birthDateInput = $("#birthDate");
    private final SelenideElement nationalitySelect = $("#nationality");
    private final SelenideElement passportInput = $("#passport");
    private final SelenideElement passportExpiryInput = $("#passportExpiry");
    
    // Contact information
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement phoneInput = $("#phone");
    private final SelenideElement countryCodeSelect = $("#countryCode");
    
    // Navigation buttons
    private final SelenideElement continueButton = $("#continue-button");
    private final SelenideElement backButton = $("#back-button");

    /**
     * Fill passenger details
     * @param title Title (Mr/Mrs/Ms)
     * @param firstName First name
     * @param lastName Last name
     * @param birthDate Birth date in DD/MM/YYYY format
     * @param nationality Nationality
     */
    public void fillPassengerDetails(String title, String firstName, String lastName, 
                                   String birthDate, String nationality) {
        titleSelect.selectOption(title);
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        birthDateInput.setValue(birthDate);
        nationalitySelect.selectOption(nationality);
    }

    /**
     * Fill passport information
     * @param passportNumber Passport number
     * @param expiryDate Passport expiry date in DD/MM/YYYY format
     */
    public void fillPassportInfo(String passportNumber, String expiryDate) {
        passportInput.setValue(passportNumber);
        passportExpiryInput.setValue(expiryDate);
    }

    /**
     * Fill contact information
     * @param email Email address
     * @param countryCode Country code (e.g., +84)
     * @param phoneNumber Phone number
     */
    public void fillContactInfo(String email, String countryCode, String phoneNumber) {
        emailInput.setValue(email);
        countryCodeSelect.selectOption(countryCode);
        phoneInput.setValue(phoneNumber);
    }

    /**
     * Continue to next page
     */
    public void continueToPay() {
        waitForElementClickable(continueButton);
        continueButton.click();
    }

    /**
     * Go back to previous page
     */
    public void goBack() {
        backButton.click();
    }
}
