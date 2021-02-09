package testcases;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
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
	public void beforeMethod() {
		driver = OpenBrowser();
	}
	
	@Test(priority=1, description="Test Name")
	@Description("The test Desc")
	@Epic("Epic")
	@Feature("Feature")
	@Story("Story")
	@Step("Step")
	@Severity(SeverityLevel.CRITICAL)
	public void test1()
	{
		Allure.addAttachment(" My Test Start : ", " Test1");
		
		projectPageObj = new ProjectPage1(driver, properties);		
		//projectPageObj.getWebControlsDetails();
			
		Allure.addAttachment(" My Test End : ", " Test1");
	}
	
	@AfterMethod
	public void afterMethod()
	{
		System.out.println("==== Close the Browser ====");
		//driver.quit();
	}
}
