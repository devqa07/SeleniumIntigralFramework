package com.dev.web.tests;

import com.dev.web.base.TestBase;
import com.dev.web.pages.SubscriptionPackagesPage;
import com.dev.web.utils.CommonActions;
import com.dev.web.utils.ListenerClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import com.dev.web.utils.Constants;

@Listeners(ListenerClass.class)
public class SubscriptionPackagesTest extends TestBase {
    public WebDriver driver;
    public CommonActions commonActions;
    SubscriptionPackagesPage subscriptionPage;
    private Logger log = LogManager.getLogger(SubscriptionPackagesTest.class.getName());

    @BeforeMethod
    public void initialize() throws IOException {
        driver = initializeDriver();
        log.info("Driver is initialized.");
        commonActions = new CommonActions(driver);
        subscriptionPage = new SubscriptionPackagesPage(driver);
        commonActions.navigateTo(prop.getProperty("url"));
    }

    @Test(priority = 0)
    public void verifySubscriptionPackageDetailsForKSA() throws IOException {
        subscriptionPage.verifyCountryName(Constants.COUNTRY_KSA);
        subscriptionPage.compareCountryData(Constants.COUNTRY_KSA);
        log.info("Successfully verified subscription package details for KSA");
    }

    @Test(priority = 1)
    public void verifySubscriptionPackageDetailsForBahrain() throws IOException {
        subscriptionPage.selectCountryFromPopUp(Constants.COUNTRY_BAHRAIN);
        subscriptionPage.verifyCountryName(Constants.COUNTRY_BAHRAIN);
        subscriptionPage.compareCountryData(Constants.COUNTRY_BAHRAIN);
        log.info("Successfully verified subscription package details for Bahrain");
    }

    @Test(priority = 2)
    public void verifySubscriptionPackageDetailsForKuwait() throws IOException {
        subscriptionPage.selectCountryFromPopUp(Constants.COUNTRY_KUWAIT);
        subscriptionPage.verifyCountryName(Constants.COUNTRY_KUWAIT);
        subscriptionPage.compareCountryData(Constants.COUNTRY_KUWAIT);
        log.info("Successfully verified subscription package details for Kuwait");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}