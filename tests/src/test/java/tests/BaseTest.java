package sqat.selenium.tests;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import sqat.selenium.pages.HomePage;
import static sqat.selenium.helpers.CUtil.loginValidUserWithDriver;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    HomePage homePage;

    @BeforeEach
    void setUp() throws MalformedURLException {
        // Set up Chrome options
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--window-size=1800,960");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.setHeadless(false);
        
        // Set a unique user data directory to avoid conflicts with existing profiles
        options.addArguments("user-data-dir=/tmp/fresh_profile_" + System.currentTimeMillis());

        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);

        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.tumblr.com/");

        // Login the user before running tests
        homePage = loginValidUserWithDriver(this.driver);
        assertTrue(homePage.isUserLoggedIn(this.driver));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
