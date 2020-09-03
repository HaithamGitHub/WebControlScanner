package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestBase {

	//private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static WebDriver driver;
	public static Properties properties; 
	
	public TestBase()
	{
		try {
			properties = new Properties();
			FileInputStream ip = new FileInputStream(
					"src/test/resources/Config/config.properties");
			properties.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void OpenBrowser () {
		
		String BrowserType = properties.getProperty("browser");
		String url = properties.getProperty("url");
		System.out.println("==== Open the Browser ====");
		System.out.println("Scanning the application : [" +url+ "]");
		
		if (BrowserType.equals("chrome")){
			
			ChromeOptions ops = new ChromeOptions();
			ops.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver","src/main/resources/Drivers/chromedriver.exe");
			driver = new ChromeDriver(ops);
			
		}
		else if (BrowserType.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","src/main/resources/Drivers/geckodriver.exe"); // Setting system properties of FirefoxDriver
			driver = new FirefoxDriver(); 
		}
		else
		{System.out.println("Other Driver is selected");}
		

		driver.manage().window().maximize();
		driver.get(url);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
}
