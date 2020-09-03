// This page is created for extending to any project

package pages;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import base.PageBase;

public class ProjectPage1 extends PageBase {
	public WebDriver  driver;
	public Properties properties;
	
	public ProjectPage1(WebDriver driver, Properties properties)
	{
		this.driver = driver;
		this.properties = properties;	
	}

	public void getWebControlsDetails()
	{
		getControlsDetails(driver);
	}


}
