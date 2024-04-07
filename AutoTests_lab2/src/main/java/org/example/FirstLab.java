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

public class FirstLab {
    private WebDriver chromeDriver;
    public static final String baseUrl = "https://www.nmu.org.ua/ua/";

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
        WebElement header = chromeDriver.findElement(By.id("heder"));
        //verification
        Assert.assertNotNull(header);
    }
    @Test
    public void testClickOnForStudent() {
        //find element by xpath
        WebElement forStudentButton = chromeDriver.findElement(By.xpath("/html/body/center/div[4]/div/div[1]/ul/li[4]/a"));
        //verification
        Assert.assertNotNull(forStudentButton);
        forStudentButton.click();
        //verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }
    @Test
    //will be failed - fix it
    public void testSearchFieldOnForStudentPage() {
        String studentPageUrl = "content/students/";
        chromeDriver.get(baseUrl + studentPageUrl);
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
        String inputValue = "I need info";
        searchField.sendKeys(inputValue);
        //verification test
        Assert.assertEquals(searchField.getText(), inputValue);
        //click enter
        searchField.sendKeys(Keys.ENTER);
        //Verification page changed
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), studentPageUrl);
    }
    @Test
    public void testSlider() {
        //find element by class name
        WebElement nextButton = chromeDriver.findElement(By.className("next"));
        //find element by css selector
        WebElement nextButtonByCss = chromeDriver.findElement(By.cssSelector("a.next"));
        //verification equality
        Assert.assertEquals(nextButton, nextButtonByCss);

        WebElement previousButton = chromeDriver.findElement((By.className("prev")));

        for(int i = 0; i < 3; i++){
            if(nextButton.getAttribute("class").contains("disabled")){
                for (int j = 0; j < 10; j++){
                    previousButton.click();
                }
                Assert.assertTrue(previousButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(nextButton.getAttribute("class").contains("disabled"));
            }
            else {
                for (int j = 0; j < 10; j++){
                    nextButton.click();
                }
                Assert.assertTrue(nextButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(previousButton.getAttribute("class").contains("disabled"));
            }
        }
    }
}


