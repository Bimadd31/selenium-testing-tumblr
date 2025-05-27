package sqat.selenium.pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;


public class LoginPage extends BasePage{

    private final By emailFieldLocator = By.xpath("//*[@id='identifierId']");
    private final By passwordFieldLocator = By.xpath("//*[@id='password']//input[@name='Passwd']");
    private final By loginBtnLocator = By.xpath("//ul[@aria-label='Navigation menu']//button[@aria-label='Log in']");
    private final By loginBtnInFrameLocator = By.xpath("//form[@method='post']//a[contains(text(), 'Google')]");
    private final By nextBtnLocator = By.xpath("//div[@id='identifierNext']//button");
    private final By confirmLoginBtnLocator = By.xpath("//div[@id='passwordNext']//button");



    public LoginPage(WebDriver driver) {
        super(driver);

        if (!driver.getTitle().equals("Browse communities on Tumblr")) {
            throw new IllegalStateException("This is not Sign In Page," +
                  " current page is: " + driver.getCurrentUrl());
          }
    }

    
    /**
     * Logs in a valid user with the provided email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return HomePage instance after successful login
     */
    public HomePage loginValidUser() {

        if (isUserLoggedIn(this.driver)) {
            throw new IllegalStateException("User is already logged in !" +
                  " current page is: " + driver.getCurrentUrl());
        }

        // Load environment variables  
        // Ensure you have a .env file with TUMBLR_EMAIL and TUMBLR_PASSWORD set
        Dotenv dotenv = Dotenv.load();
        String email = dotenv.get("TUMBLR_EMAIL");
        String password = dotenv.get("TUMBLR_PASSWORD");
        
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email or password not set in .env file");
        }
        
        // Clear cookies before login
        driver.manage().deleteAllCookies();

        // Clear local and session storage
        this.driver = clearLocalAndSessionStorage(this.driver);
        
        // Inject consent cookies
        this.driver = injectConsentCookies(this.driver);

        sleepSilently(2000); // Wait for the cookie to be set
        driver.navigate().refresh();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for the login button to be visible
        WebElement loginBtn = wait.until(
            ExpectedConditions.visibilityOfElementLocated(loginBtnLocator)
        );
        loginBtn.click();

        WebElement loginBtnInFrame = wait.until(ExpectedConditions.presenceOfElementLocated(loginBtnInFrameLocator));
        // Wait for the login button in the frame to be present
        loginBtnInFrame.click();


        WebElement emailField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(emailFieldLocator)
        );
        // Wait for the email field to be visible and then send keys
        emailField.sendKeys(email);

        
        WebElement nextButton =  wait.until(
            ExpectedConditions.elementToBeClickable(nextBtnLocator)
        );
        // Wait for the next button to be clickable and then click it
        nextButton.click();

     
        WebDriverWait waitForCaptcha = new WebDriverWait(driver,Duration.ofSeconds(30));
        WebElement passwordField = waitForCaptcha.until(
            ExpectedConditions.elementToBeClickable(passwordFieldLocator)
        );
        // Wait for the password field to be clickable and then send keys
        passwordField.sendKeys(password);

        
        WebElement confirmLoginBtn =  wait.until(
            ExpectedConditions.elementToBeClickable(confirmLoginBtnLocator)
        );
        // Click on the confirm login button
        confirmLoginBtn.click();
        
        return new HomePage(this.driver);
    }



}