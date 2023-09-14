package testcases;


import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.ProjectPage1;

public class ControlIdTest extends base.TestBase{

	ProjectPage1 projectPageObj;
	public WebDriver driver; 


	public ControlIdTest() {
		super();
	}
	
	
	@BeforeMethod
	public void beforeMethod() throws MalformedURLException {
		//driver = OpenBrowser();
		//OpenBrowser_remote();
	}
	
	@Test(priority=1, description="FF test")
	@Description("The test Desc")
	@Epic("Epic")
	@Feature("Feature")
	@Story("Story")
	@Step("Step")
	@Severity(SeverityLevel.CRITICAL)
	public void test_ff() throws MalformedURLException
	{
		Allure.addAttachment(" My Test Start : ", " FF test");
		driver = OpenBrowser_remote("ff");
		projectPageObj = new ProjectPage1(driver, properties);		
		projectPageObj.getWebControlsDetails();
			
		Allure.addAttachment(" My Test End : ", " FF test");
	}
	
	@Test( description="Chrome test")
	public void test_ch() throws Exception
	{
		Allure.addAttachment(" My Test Start : ", " Chrome test");
		driver =OpenBrowser_remote("ch");
		//driver = OpenBrowser_local();
		projectPageObj = new ProjectPage1(driver, properties);		
		projectPageObj.getWebControlsDetails();
			
		Allure.addAttachment(" My Test End : ", " chrome test");
	}
		
	@Test( description="Eadge test")
	public void test_ed() throws MalformedURLException
	{
		Allure.addAttachment(" My Test Start : ", " Eadge test");
		driver = OpenBrowser_remote("ed");
		projectPageObj = new ProjectPage1(driver, properties);		
		projectPageObj.getWebControlsDetails();
			
		Allure.addAttachment(" My Test End : ", " Eadge test");
	}
	
	
	@AfterMethod
	public void afterMethod()
	{
		System.out.println("==== Close the Browser ====");
		//driver.quit();
	}
	
	@AfterSuite
	public void afterSuite() throws Throwable
	{
		System.out.println("==== After Suite ====");
		
		runAllureReport();
	}
}
