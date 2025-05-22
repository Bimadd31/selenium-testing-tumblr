package tests.src.test.java;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginPage {

    private WebDriver driver;
    private final By emailFieldLocator = By.xpath("//input[@id='email']");
    private final By passwordFieldLocator = By.xpath("//input[@id='password']");
    private final By loginButtonLocator = By.xpath("//button[@type='submit']");



    public LoginPage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals("WeTransfer | Sign in")) {
            throw new IllegalStateException("This is not Sign In Page," +
                  " current page is: " + driver.getCurrentUrl());
          }
    }

 

    public HomePage loginValidUser(String email, String password) {
        WebElement emailField = driver.findElement(emailFieldLocator);
        WebElement loginButton = driver.findElement(loginButtonLocator);

        emailField.sendKeys(email);
        loginButton.click();

        return new HomePage(driver);
    }

 
}