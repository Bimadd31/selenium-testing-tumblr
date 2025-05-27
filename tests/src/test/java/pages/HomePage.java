package sqat.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.cdimascio.dotenv.Dotenv;



public class HomePage extends BasePage {
    
    private WebDriverWait wait;
    // Locators for various elements on the HomePage
    private static By accountButtonLocator = By.id("account_button");
    private static By logoutBtnLocator = By.xpath("//ul[@id='account_subnav']//button[@aria-label='Log out']/span");
    private static By imgPostBtnLocator = By.xpath("//button[@aria-label='Photo']");
    private static By postInputFieldLocator = By.xpath("//p[@aria-label='Empty block; start writing or type forward slash to choose a block']");
    private static By additionalPostInputLocator = By.xpath("//p[@data-title='Paragraph' and @role='document']");
    private static By postOptionsDropDownLocator = By.xpath("//span[@data-testid='controlled-popover-wrapper']//button[@aria-label='Open post state options']");
    private static By postPrivatelyBtnLocator = By.xpath("//span[@data-testid='controlled-popover-wrapper']//div[@role='group']//li[@role='button']//span[normalize-space(text())='Post privately']");
    private static By confirmPostBtnLocator = By.xpath("//button/span[normalize-space(text())='Post privately']");
    private static By triggerAdditionalInputLocator = By.xpath("//*[@id='glass-container']//div[@class='block-list-appender wp-block']");
    private static By logoutConfirmationBtnLocator = By.xpath("//div[@id='glass-container']//button[.//span[normalize-space()='OK']]");
    private static By postedToBannerLocator = By.xpath("//*[contains(normalize-space(), 'Posted to')]");

    public HomePage(WebDriver driver) {
        super(driver);

        if (!isUserLoggedIn(this.driver)) {
            throw new IllegalStateException("User failed to log in !" +
                  " current page is: " + driver.getCurrentUrl());
          }
    }

    /**
     * Logs out the user from the application.
     *
     * @return IndexPage instance after logging out.
     */
    public IndexPage logoutUser() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement accountButton = wait.until(
            ExpectedConditions.elementToBeClickable(accountButtonLocator)
        );
        // Click the account button to open the account menu
        accountButton.click();


        WebElement logoutButton = wait.until(
            ExpectedConditions.elementToBeClickable(logoutBtnLocator)
        );
        // Click the logout button to initiate the logout process
        logoutButton.click();

        // Wait for the confirmation button to be clickable
        WebElement logoutConfirmationButton = wait.until(
            ExpectedConditions.elementToBeClickable(logoutConfirmationBtnLocator)
        );
        // Click the confirmation button to complete the logout process
        logoutConfirmationButton.click();


        sleepSilently(2000); // Wait for the logout process to complete
        driver.navigate().refresh(); // Refresh the page to ensure the user is logged out

        // Return a new IndexPage instance after logging out
        return new IndexPage(driver);
    }

    /**
     * Posts a picture to the user's home page.
     *
     * @return HomePage instance after posting the picture.
     */
    public WebElement postPicture() {

        // Check if the user is logged in before proceeding
        if (!isUserLoggedIn(this.driver)) {
            throw new IllegalStateException("User is not logged in !" +
                  " current page is: " + driver.getCurrentUrl());
          }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Wait for the image post button to be clickable
        WebElement ImgPostButton = wait.until(
            ExpectedConditions.elementToBeClickable(imgPostBtnLocator)
        );

        ImgPostButton.click();
        sleepSilently(2000); // Wait for the post editor to open

        // Wait for the input field to be clickable
        WebElement postInputField = wait.until(
            ExpectedConditions.elementToBeClickable(postInputFieldLocator)
        );

        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Send the image link to the input field
        postInputField.sendKeys(dotenv.get("TEST_IMG_LINK"));
        
        sleepSilently(1000);
        postInputField.sendKeys(Keys.ENTER); // Simulate pressing Enter to recognize the link as image

        sleepSilently(2000);

        WebElement triggerAdditionalInputField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(triggerAdditionalInputLocator)
        );

        triggerAdditionalInputField.click(); // Click to focus the input field
        

        WebElement additionalInputField = wait.until(
            ExpectedConditions.elementToBeClickable(additionalPostInputLocator)
        );

        additionalInputField.sendKeys("Beautiful picture");

        WebElement PostOptionsDropDown = wait.until(
            ExpectedConditions.elementToBeClickable(postOptionsDropDownLocator)
        );

        PostOptionsDropDown.click();
        sleepSilently(2000); // Wait for the dropdown to open
        
        WebElement postPrivatelyButton = wait.until(
            ExpectedConditions.elementToBeClickable(postPrivatelyBtnLocator)
        );

        postPrivatelyButton.click();

        sleepSilently(2000); // Wait for the post options to apply

        WebElement confirmPostButton = wait.until(
            ExpectedConditions.elementToBeClickable(confirmPostBtnLocator)
        );

        confirmPostButton.click(); // Click the button to confirm posting privately

        sleepSilently(2000); // Wait for the post to be processed

        
        WebDriverWait notificationWait = new WebDriverWait(driver, Duration.ofSeconds(5));


        return notificationWait.until(
            ExpectedConditions.visibilityOfElementLocated(postedToBannerLocator)
        );
    }


}
