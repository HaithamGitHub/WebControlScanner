package testcases;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class Test1 extends base.TestBase {

    private WebDriver driver;
    By usernametext = By.id("user-name");
    By Passwordtext = By.id("password");
    By LoginButton = By.id("login-button");

    @Test(description = "Chrome test")
    public void test_ch() throws Exception {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        Allure.addAttachment(" My Test Start : ", " Chrome test");

        String username = "standard_user";
        String password = "secret_sauce";

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
        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("==== Close the Browser ====");
        //driver.quit();
    }

    @AfterSuite
    public void afterSuite() throws Throwable {
        System.out.println("==== After Suite ====");

        runAllureReport();
    }
}
