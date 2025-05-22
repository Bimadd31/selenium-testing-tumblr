package tests.src.test.java;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;


import static org.junit.Assert.assertTrue;
import org.openqa.selenium.WebDriver;


public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);

        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver, 5);
        driver.get("https://auth.wetransfer.com/login");
    }


    @Test
    public void testLogin(){
        LoginPage loginPage = new LoginPage(this.driver);
        HomePage homePage = loginPage.loginValidUser("", "");
        assertTrue(homePage.isUserLoggedIn());
    }



    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
