package com.dev.web.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonActions {
    WebDriver driver;
    public CommonActions(WebDriver driver) {
        this.driver = driver;
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public boolean checkElementIsDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }

    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }
}