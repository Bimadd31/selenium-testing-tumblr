package sqat.selenium.helpers;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.Cookie;

import sqat.selenium.pages.HomePage;
import sqat.selenium.pages.LoginPage;

public class CUtil {
    
    public static HomePage loginValidUserWithDriver(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginValidUser();
        return homePage;
    }
}
