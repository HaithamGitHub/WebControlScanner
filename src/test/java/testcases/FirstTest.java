package testcases;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FirstTest {

    private WebDriver driver;
    By userNameInput = By.id("user-name");
    By passwordInput = By.id("password");
    //By login = By.id("login-button");
    By login = By.xpath("//input[@name='login-button']");

    @Test
    public void login_ff() {

        Allure.addAttachment("My Login", "Chrome");

        //driver = new ChromeDriver();
        driver = new FirefoxDriver();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
    }

    @Test
    public void login_ch() {

        Allure.addAttachment("My Login", "Chrome");

        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(userNameInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");

        driver.findElement(login).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Incorrect Navigation");
        Allure.addAttachment("End", "Chrome");
    }

    @BeforeMethod
    public void beforeMethod() {}

    @AfterMethod
    public void afterMethod() {}

    @BeforeClass
    public void beforeClass() {}

    @AfterClass
    public void afterClass() {}

    @BeforeTest
    public void beforeTest() {}

    @AfterTest
    public void afterTest() {
        runBatchFile("allure.bat");
        driver.quit();
    }

    public static void runBatchFile(String Filename) {
        //        try {
        //            System.out.println("============== RunBatchFile Started ===========");
        //
        //            String command = "cmd /C start " + System.getProperty("user.dir") + "/" + Filename;
        //            System.out.println("Current path : [" + System.getProperty("user.dir") + "]");
        //            System.out.println("command : [" + command + "]");
        //            Runtime rt = Runtime.getRuntime();
        //            Process pr = rt.exec(command);
        //
        //            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        //            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        //
        //            // read the output from the command
        //            String s = null;
        //            while ((s = stdInput.readLine()) != null) {
        //                System.out.println("Here is the standard output of the command:\n");
        //                System.out.println(s);
        //            }
        //            // read any errors from the attempted command
        //            while ((s = stdError.readLine()) != null) {
        //                System.out.println("Here is the standard error of the command (if any):\n");
        //                System.out.println(s);
        //            }
        //
        //            pr.destroy();
        //
        //        } catch (IOException e) {
        //            System.out.println("Here is the standard error of the command (if any):\n");
        //            System.out.println(e.getMessage());
        //        }

        System.out.println("============== RunBatchFile Ended ===========");
    }

    @BeforeSuite
    public void beforeSuite() {}

    @AfterSuite
    public void afterSuite() {}

}
