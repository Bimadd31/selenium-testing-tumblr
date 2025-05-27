package sqat.selenium.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Checks if the user is logged in by looking for the logout button.
     *
     * @param driver the WebDriver instance
     * @return true if the user is logged in, false otherwise
     */
    public boolean isUserLoggedIn(WebDriver driver) {
        By accountDropdownBtn = By.xpath("//button[@id='account_button']");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until( 
                ExpectedConditions.elementToBeClickable(accountDropdownBtn)
            );
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Sleeps for the specified number of milliseconds without throwing an InterruptedException.
     * If the thread is interrupted, it will reset the interrupt flag.
     *
     * @param millis the number of milliseconds to sleep
     */
    public void sleepSilently(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Clears local and session storage in the browser.
     *
     * @param driver the WebDriver instance
     */
    public WebDriver clearLocalAndSessionStorage(WebDriver driver) {
        // Clear local storage
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
        // Clear session storage
        ((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");

        return driver;
    }

    /**
     * Injects consent cookies into the WebDriver instance.
     *
     * @param driver the WebDriver instance
     */
    public WebDriver injectConsentCookies(WebDriver driver) {
       
        // Load properties from .env file
        Dotenv dotenv = Dotenv.load();

        Cookie consentCookie1 = new Cookie.Builder("euconsent-v2", dotenv.get("EUCONSENT_V2_TOKEN"))
        .domain(".tumblr.com")
        .path("/")
        .isHttpOnly(false)
        .isSecure(true)
        .sameSite("Lax")
        .build();

        Cookie consentCookie2 = new Cookie.Builder("euconsent-v2-analytics", "1")
        .domain(".tumblr.com")
        .path("/")
        .isHttpOnly(false)
        .isSecure(true)
        .sameSite("Lax")
        .build();

        Cookie consentCookie3 = new Cookie.Builder("euconsent-v2-noniab",dotenv.get("EUCONSENT_V2_NONIAB"))
        .domain(".tumblr.com")
        .path("/")
        .isHttpOnly(false)
        .isSecure(true)
        .sameSite("Lax")
        .build();

        driver.manage().addCookie(consentCookie1);
        driver.manage().addCookie(consentCookie2);
        driver.manage().addCookie(consentCookie3);

        return driver;

    }
}