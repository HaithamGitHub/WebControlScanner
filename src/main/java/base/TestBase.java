package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.Headers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase {

	//private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static WebDriver driver;
	public static Properties properties; 
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	

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
	
    public static WebDriver OpenBrowser () {
		
		String BrowserType = properties.getProperty("browser");
		String url = properties.getProperty("url");
		//url = "https://the-internet.herokuapp.com/basic_auth";
		System.out.println("==== Open the Browser ====");
		System.out.println("Scanning the application : [" + url + "]");
		
		if (BrowserType.equals("chrome")){
			
			ChromeOptions ops = new ChromeOptions();
			//ops.addArguments("--disable-notifications");
			ops.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			//ops.addArguments("--disable-blink-features='BlockCredentialedSubresources'");
			ops.addArguments("--incognito");
			//System.setProperty("illegal-access", "deny");
			
			//WebDriverManager.chromedriver().forceDownload().browserPath("C:\\Users\\hahmed20\\.m2\\haitham");   			
			WebDriverManager.chromedriver().forceDownload().setup();
			driver = new ChromeDriver(ops);
						
		}
		else if (BrowserType.equals("firefox"))
		{
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); 
		}
		else
		{System.out.println("Other Driver is selected");}
	
		driver.get(url);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		tdriver.set(driver);
		return getDriver();
	}

   
    public static void newRobotfunction()
    {
    	System.out.println("==== Start new Robot function ====");
    	String username = "BSCAdi"; // authentication username
    	String password = "Reno2016"; // authentication password
    	
    	username = "admin"; // authentication username
    	password = "admin"; // authentication password
    	
    	// Get the devtools from the running driver and create a session
		DevTools devTools = ((ChromiumDriver) driver).getDevTools();		
		devTools.createSession(); 
		
		// Enable the Network domain of devtools
		devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
		String auth = username +":"+ password;
		
		// Encoding the username and password using Base64 (java.util)
		String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());
		
		// Pass the network header -> Authorization : Basic <encoded String>
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "Basic "+encodeToString);				
		devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
    	System.out.println("==== complete new Robot function ====");
    }
    
    
    public static synchronized WebDriver getDriver() {
    	return tdriver.get();
    }
    
    
}
