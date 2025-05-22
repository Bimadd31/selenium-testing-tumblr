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

public class SignUpPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private final By emailFieldLocator = By.xpath("@id('email')");
    private final By passwordFieldLocator = By.xpath("@id('password')");
    private final By nameFieldLocator = By.xpath("@id('firstName')");    


  
    
    public SignUpPage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals("WeTransfer | Sign up")) {
            throw new IllegalStateException("This is not Sign Up Page," +
                  " current page is: " + driver.getCurrentUrl());
          }
    }

    public HomePage registerUser(String name,String email, String password) {
        WebElement emailField = driver.findElement(emailFieldLocator);
        WebElement nameField = driver.findElement(nameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement signUpButton = driver.findElement(By.xpath("//button[@type='submit']"));

        emailField.sendKeys(email);
        nameField.sendKeys(name);
        passwordField.sendKeys(password);
        signUpButton.click();

        return new HomePage(driver);
    }

}