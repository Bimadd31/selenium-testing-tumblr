package sqat.selenium.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class IndexPage extends BasePage{
    
    private WebDriverWait wait;

    public IndexPage(WebDriver driver) {
        super(driver);
        if (isUserLoggedIn(driver)) {
            throw new IllegalStateException("User is logged in !" +
                  " current page is: " + driver.getCurrentUrl());
        }
    }  

}
