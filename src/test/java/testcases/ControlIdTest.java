package testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.ProjectPage1;

public class ControlIdTest extends base.TestBase{

	ProjectPage1 projectPageObj;

	public ControlIdTest() {
		super();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		OpenBrowser();
	}
	
	@Test
	public void test1()
	{
		projectPageObj = new ProjectPage1(driver, properties);		
		projectPageObj.getWebControlsDetails();
	}

	@AfterMethod
	public void afterMethod()
	{
		System.out.println("==== Close the Browser ====");
		driver.quit();
	}
}
