package testcases;

import java.net.MalformedURLException;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProjectPage1;

public class ControlIdTest extends base.TestBase {

    ProjectPage1 projectPageObj;
    public WebDriver driver;
    By userNameInput = By.id("user-name");
    By passwordInput = By.id("password");
    //By login = By.id("login-button");
    By login = By.xpath("//input[@name='login-button']");

    public ControlIdTest() {
        super();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        //driver = OpenBrowser_local();
        //OpenBrowser_remote();
    }

    @Test(priority = 1, description = "FF test")
    @Description("The test Desc")
    @Epic("Epic")
    @Feature("Feature")
    @Story("Story")
    @Step("Step")
    @Severity(SeverityLevel.CRITICAL)
    public void test_ff() throws MalformedURLException {
        Allure.addAttachment(" My Test Start : ", " FF test");
        //driver = OpenBrowser_remote("ff");
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " FF test");
    }

    @Test(description = "Chrome test")
    public void test_ch() throws Exception {
        Allure.addAttachment(" My Test Start : ", " Chrome test");
        //driver =OpenBrowser_remote("ch");
        //driver = OpenBrowser_local();
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @Test(description = "Eadge test")
    public void test_ed() throws MalformedURLException {
        Allure.addAttachment(" My Test Start : ", " Eadge test");
        //driver = OpenBrowser_remote("ed");
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " Eadge test");
    }

    @Test(description = "Remote Chrome test")
    public void test_ch_remote() throws Exception {
        Allure.addAttachment(" My Test Start : ", " Chrome test");
        driver = OpenBrowser_remote("ch");
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @Test(description = "Eadge test")
    public void test_ed_remote() throws MalformedURLException {
        Allure.addAttachment(" My Test Start : ", " Eadge test");
        driver = OpenBrowser_remote("ed");
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " Eadge test");
    }

    @Test(description = "FF test")
    public void test_ff_remote() throws MalformedURLException {
        Allure.addAttachment(" My Test Start : ", " FireFox test");
        driver = OpenBrowser_remote("ff");
        projectPageObj = new ProjectPage1(driver, properties);
        projectPageObj.getWebControlsDetails();

        Allure.addAttachment(" My Test End : ", " FireFox test");
    }

    @Test()
    public void test_localBrowser() {
        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
    }

    @Test(description = "Remote Chrome test")
    public void test2_ch_remote() throws Exception {
        Allure.addAttachment(" My Test Start : ", " Chrome test");
        driver = OpenBrowser_remote("ch");

        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @Test(description = "Remote ff test")
    public void test2_ff() throws Exception {
        Allure.addAttachment(" My Test Start : ", " Chrome test");
        driver = OpenBrowser_remote("ff");

        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @Test(description = "Remote ed test")
    public void test2_ed() throws Exception {
        Allure.addAttachment(" My Test Start : ", " Chrome test");
        driver = OpenBrowser_remote("ed");

        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
        Allure.addAttachment(" My Test End : ", " chrome test");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("==== Close the Browser ====");
        driver.quit();
    }

    @AfterSuite
    public void afterSuite() throws Throwable {
        System.out.println("==== After Suite ====");

        runAllureReport();
    }
}
