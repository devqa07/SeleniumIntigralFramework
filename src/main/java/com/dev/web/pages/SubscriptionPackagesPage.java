package com.dev.web.pages;

import com.dev.web.utils.CommonActions;
import com.dev.web.utils.Constants;
import com.dev.web.utils.JSONHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionPackagesPage {
    public WebDriver driver;
    public CommonActions commonActions;
    public SubscriptionPackagesPage(WebDriver driver) {
        this.driver = driver;
        commonActions = new CommonActions(driver);
    }

    // UI Locators for Subscription Packages page
    private By country_name = By.xpath("//span[@id='country-name']");
    private By country_popup = By.xpath("//div[@id='country-header']");
    private By bahrain_country = By.xpath("//a[@id='bh']");
    private By kuwait_country = By.xpath("//a[@id='kw']");
    private By price_list = By.xpath("//div[@class='price']/b");
    private By currency_list = By.xpath("//div[@class='price']/i");
    private By package_list = By.xpath("//strong[@class='plan-title']");

    public void verifyCountryName(String country) {
        String countryName = commonActions.getText(country_name);
        Assert.assertEquals(countryName, country);
    }
    public void selectCountryFromPopUp(String country) {
        commonActions.click(country_name);
        Assert.assertTrue(commonActions.checkElementIsDisplayed(country_popup));
        if (country.equalsIgnoreCase(Constants.COUNTRY_KUWAIT)) {
            commonActions.click(kuwait_country);
        } else if (country.equalsIgnoreCase(Constants.COUNTRY_BAHRAIN)) {
            commonActions.click(bahrain_country);
        }
    }

    //method to get the plan/package details, price and currency
    public Map getPlanDetails() {
        List<WebElement> listOfPlans = driver.findElements(package_list);
        List<WebElement> listOfPrices = driver.findElements(price_list);
        List<WebElement> listOfCurrency = driver.findElements(currency_list);
        Map<String, Object[]> dataMap = new HashMap<>();
        for (int i = 0; i < listOfPlans.size(); i++) {
            String planText = listOfPlans.get(i).getText().trim();
            String priceText = listOfPrices.get(i).getText().trim();
            Object currencyText = listOfCurrency.get(i).getText().trim();
            dataMap.put(planText, new Object[]{priceText, currencyText});
        }
        return dataMap;
    }

    //method to compare the UI(actual) data and Json(expected) data
    public void compareCountryData(String country) throws IOException {
        Map<String, Object[]> dataMap = getPlanDetails();
        JSONHelper jsonHelper = new JSONHelper();
        Map<String, Object[]> jsonData = jsonHelper.loadData(country);
        System.out.print("UI Data for " + country + " : ");
        printMap(dataMap);
        System.out.print("JSON Data for " + country + " : ");
        printMap(jsonData);
        Assert.assertTrue(compareMaps(dataMap, jsonData));
    }

    //method to compare the key and values of maps(dataMap, jsonData)
    public static boolean compareMaps(Map<String, Object[]> map1, Map<String, Object[]> map2) {
        // Checking if the maps have the same size
        if (map1.size() != map2.size()) {
            return false;
        }
        if (map1.keySet().equals(map2.keySet())) {
            System.out.println("The keys of both the maps are equal.");
        } else {
            System.out.println("The keys of both the maps are not equal.");
            return false;
        }
        // Compare values
        for (Map.Entry<String, Object[]> entry : map1.entrySet()) {
            String key = entry.getKey();
            Object[] values1 = entry.getValue();
            Object[] values2 = map2.get(key);
            if (!Arrays.equals(values1, values2)) {
                System.out.println("The values of both the maps are not equal.");
                return false;
            } else {
                System.out.println("The values of both the maps are equal.");
            }
        }
        // If all comparisons are passed, then the maps are equal
        return true;
    }

    //method to print the UI and Json data on console
    public void printMap(Map<String, Object[]> map) {
        for (Map.Entry<String, Object[]> entry : map.entrySet()) {
            String key = entry.getKey();
            Object[] values = entry.getValue();
            System.out.print(key + ": ");
            for (Object value : values) {
                System.out.print(value + ", ");
            }
        }
        System.out.println();
    }
}