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



public class IndexPage {
    private WebDriver driver;
    private WebDriverWait wait;



    public IndexPage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals("WeTransfer | Send large files & Share photos online - Up to 2GB free")) {
            throw new IllegalStateException("This is not WeTransfer Page," +
                  " current page is: " + driver.getCurrentUrl());
          }
    }


}
