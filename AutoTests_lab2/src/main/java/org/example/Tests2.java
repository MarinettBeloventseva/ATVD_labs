package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Tests2 {
    private WebDriver chromeDriver;
    public static final String baseUrl = "https://prom.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        //Run diver
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        //set fullscreen
        chromeOptions.addArguments("--start-fullscreen");
        //setup wait for loading elements
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }
    @BeforeMethod
    public void preconditions() {
        //open main page
        chromeDriver.get(baseUrl);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        chromeDriver.quit();
    }
    @Test
    //will be failed - fix it
    public void testHeaderExists() {
        //find element by id
        WebElement header = chromeDriver.findElement(By.tagName("header"));
        //verification
        Assert.assertNotNull(header);
    }
    @Test
    public void testClickOnForButton() {
        //find element by xpath
        WebElement forTestButton = chromeDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/div[3]/div/div/div/div/a"));
        //verification
        Assert.assertNotNull(forTestButton);
        forTestButton.click();
        //verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }
    @Test
    //will be failed - fix it
    public void testSearchFieldOnForMainPage() {
        String categoryPageUrl = "Krasota-i-zdorove";
        chromeDriver.get(baseUrl + categoryPageUrl);
        //find element by tagName
        WebElement searchField = chromeDriver.findElement(By.tagName("input"));
        //verification
        Assert.assertNotNull(searchField);
        //different params of searchField
        System.out.println(String.format("Name attribute: %s", searchField.getAttribute("name"))+
                String.format("\nID attribute: %s", searchField.getAttribute("id"))+
                String.format("\nType attribute: %s", searchField.getAttribute("type"))+
                String.format("\nValue attribute: %s", searchField.getAttribute("value"))+
                String.format("\nPosition: (%d;%d)", searchField.getLocation().x, searchField.getLocation().y)+
                String.format("\nSize: %dx%d", searchField.getSize().height, searchField.getSize().width));
        //input value
        String inputValue = "ковдра";
        searchField.sendKeys(inputValue);
        //verification test
        //Assert.assertEquals(searchField.getText(), inputValue);
        //click enter
        searchField.sendKeys(Keys.ENTER);
        //Verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), categoryPageUrl);
    }
}
