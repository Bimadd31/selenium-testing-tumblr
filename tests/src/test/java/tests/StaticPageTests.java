package sqat.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.stream.Stream;
import java.util.Arrays;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import sqat.selenium.pages.HomePage;
import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import sqat.selenium.pages.IndexPage;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StaticPageTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() throws MalformedURLException {
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

    }
   

    /**
     * Method to provide an array of static pages for parameterized tests.
     * @return An array of objects containing URL, page class, and expected heading.
     */
     private static final Object[][] pages = {
        { "https://www.tumblr.com/", IndexPage.class, "Browse communities on Tumblr" },
    //  { "OtherLink", OtherPage.class, "Expected Heading for Other Page" },
    };

    /**
     * Parameterized test to verify static pages.
     * It checks if the page title contains the expected heading.
     * @throws Exception if there is an error during the test
    */
   @Test
    void testStaticPagesFromArray() throws Exception {
        for (Object[] entry : pages) {
            String url = (String) entry[0];
            Class<?> pageClass = (Class<?>) entry[1];
            String expectedHeading = (String) entry[2];

            driver.get(url);

            // Create an instance of the page class using reflection
            Constructor<?> constructor = pageClass.getConstructor(WebDriver.class);
            Object page = constructor.newInstance(driver);

            // Assuming the page class has a method to get the page title
            Method getHeading = pageClass.getMethod("getPageTitle");
            String actualHeading = (String) getHeading.invoke(page);

            assertTrue(actualHeading.contains(expectedHeading),
                    "Page at " + url + " did not contain expected heading: " + expectedHeading);
        }
    }


    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
