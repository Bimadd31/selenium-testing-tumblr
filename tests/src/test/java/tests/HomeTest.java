package sqat.selenium.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import sqat.selenium.pages.IndexPage;
import sqat.selenium.pages.HomePage;
import static sqat.selenium.helpers.CUtil.loginValidUserWithDriver;


public class HomeTest extends BaseTest {


    @Test
    /**
     * Test to verify that the user can successfully post a picture.
     * It checks if the user is logged in, posts a picture, and verifies the notification banner.
     */
    public void testPostPicture() throws IOException {
        HomePage homePage = new HomePage(driver);
        WebElement notificationBanner = homePage.postPicture();
        assertAll(
            () -> assertTrue(homePage.isUserLoggedIn(driver), "User should be logged in before posting a picture"),
            () -> assertTrue(notificationBanner.isDisplayed(), "Notification banner should be displayed after posting a picture"),
            () -> assertTrue(notificationBanner.getText().contains("Posted to"), "Notification banner should indicate that the post was successful")
        );
    }
    
    @Test
    /**
     * Test to verify that the user can log out successfully.
     */
    public void TestLogout() {

        HomePage homePage = new HomePage(this.driver);
        assertTrue(homePage.isUserLoggedIn(this.driver), "User should be logged in before logout");
        IndexPage indexPage = homePage.logoutUser();
        assertFalse(indexPage.isUserLoggedIn(this.driver));

    }

}
