package testcases;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testData.ExcelUtils;

public class ExcelDrivenTest {
    private WebDriver driver;
    By usernametext = By.id("user-name");
    By Passwordtext = By.id("password");
    By LoginButton = By.id("login-button");

    @BeforeClass
    public void setUp() {
        // Set the path to your WebDriver executable
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
    }

    @BeforeMethod
    public void initialize() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws InvalidFormatException, IOException {

        Object[][] data;
        data = ExcelUtils.fetchData("./src/test/java/testData/testdata.xlsx", "logintest");
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        // Navigate to login page
        driver.get("https://www.saucedemo.com/");

        System.out.println("username: " + username + "Password: " + password);

        System.out.println("user_enters_valid_credentials");
        driver.findElement(usernametext).sendKeys(username);
        driver.findElement(Passwordtext).sendKeys(password);

        // Click the login button
        driver.findElement(LoginButton).click();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html",
                            "'Incorrect username or password.' message is not displayed or not correct");
    }

    @Test()
    public void testValidLogin(String username, String password) {
        // Navigate to login page
        driver.get("https://www.saucedemo.com/");

        System.out.println("username: " + username + "Password: " + password);

        System.out.println("user_enters_valid_credentials");
        driver.findElement(usernametext).sendKeys(username);
        driver.findElement(Passwordtext).sendKeys(password);

        // Click the login button
        driver.findElement(LoginButton).click();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html",
                            "'Incorrect username or password.' message is not displayed or not correct");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
