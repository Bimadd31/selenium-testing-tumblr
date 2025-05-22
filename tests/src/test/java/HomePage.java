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



public class HomePage {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private final By logoutButton = By.xpath("//*[@id='wt-popover-content-b7YEqEvY7q8MWo-3FS94-']/div/div[4]/a");

    public HomePage(WebDriver driver) {
        this.driver = driver;

        if (!driver.findElement(logoutButton).isDisplayed()) {
            throw new IllegalStateException("This is not WeTransfer Page," +
                  " current page is: " + driver.getCurrentUrl());
          }
    }

    public boolean isUserLoggedIn() {
        try {
            WebElement logoutButtonElement = driver.findElement(logoutButton);
            return logoutButtonElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
   
}
