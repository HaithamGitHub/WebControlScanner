/**
 * 
 */
/**
 * 
 */
package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LoginSteps {
    WebDriver driver;
    By usernametext = By.id("user-name");
    By Passwordtext = By.id("password");
    By LoginButton = By.id("login-button");

    @Given("User is on the login page")
    public void user_is_on_login_page() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @When("User enters valid username and password")
    public void user_enters_valid_credentials() {
        // Code to enter username and password
        System.out.println("user_enters_valid_credentials");
    }

    @When("user login into application with {string} and password {string}")
    public void user_logs_into_the_application_with_username_and_password(String myUsername, String myPassword) {
        // Locate the username and password fields and enter the provided credentials
        System.out.println("username: " + myUsername + "Password: " + myPassword);

        System.out.println("user_enters_valid_credentials");
        driver.findElement(usernametext).sendKeys(myUsername);
        driver.findElement(Passwordtext).sendKeys(myPassword);

        // Click the login button
        driver.findElement(LoginButton).click();
    }

    @Then("User is redirected to the homepage")
    public void user_is_redirected_to_the_homepage() {
        // Verify that the user is redirected to the homepage
        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html",
                            "'Incorrect username or password.' message is not displayed or not correct");
        //        if (!currentUrl.equals("https://www.saucedemo.com/inventory.html")) {
        //            throw new AssertionError("User is not on the homepage. Current URL: " + currentUrl);
        //        }
        // Close the browser
        driver.quit();
    }
}
