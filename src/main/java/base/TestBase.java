package base;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.Base64;
import java.util.HashMap;

import java.util.Map;

import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;



public class TestBase {

	
    //private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    //private static final ThreadLocal<WebDriverManager> webDriverManager = new ThreadLocal<>();
	public static WebDriver driver;
	public static Properties properties; 
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	

	public TestBase()
	{
		try {
			properties = new Properties();
			FileInputStream ip = new FileInputStream("src/test/resources/Config/config.properties");
			properties.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static WebDriver OpenBrowser_local () throws Exception {
		
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
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(ops);
			
			//driver.set(WebDriverManager.chromedriver().proxy("").capabilities(ops).create());
			
			//driver = new RemoteWebDriver(new URL(url) , new ChromeOptions());
			//driver = new ChromeDriver(ops);
						
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
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		tdriver.set(driver);
		return getDriver();
	}

    
   public WebDriver OpenBrowser_remote (String BrowserType) throws MalformedURLException {
		///////////////////////////////////////////////////////
     	/*
		String BrowserType = properties.getProperty("browser");
		String url = properties.getProperty("url");
		//url = "https://the-internet.herokuapp.com/basic_auth";
		System.out.println("==== Open the Browser ====");
		System.out.println("Scanning the application : [" + url + "]");
		
			
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    */	

     
     // BROWSER => chrome / firefox
     // HUB_HOST => localhost / 10.0.1.3 / hostname
  
     String host = "localhost";
     String baseURL = "http://demo.guru99.com/test/guru99home/";
     String nodeURL = "http://" + host + ":4444/wd/hub";
     //String BrowserType = properties.getProperty("browser");
	 //String url = properties.getProperty("url");
  
	System.out.println("==== Open the Browser ====");
	System.out.println("Scanning the application : [" + baseURL + "]");
     
	
     if (BrowserType.equals("ch"))
     {
    	 driver = new RemoteWebDriver(new URL(nodeURL),getChromeConfiguration(""));
    	 //driver = new RemoteWebDriver(new URL(nodeURL),new ChromeOptions());
     }
     else if (BrowserType.equals("ff"))
     {driver = new RemoteWebDriver(new URL(nodeURL),getFireFoxConfiguration(""));}
     else if (BrowserType.equals("ed"))
     {driver = new RemoteWebDriver(new URL(nodeURL),getEdgeConfiguration(""));}
     
     
     driver.get(baseURL);
     driver.manage().window().maximize();
     
     return driver;
}

   

	private static FirefoxOptions getFireFoxConfiguration(String platform)
	{
		FirefoxOptions firefoxOption = new FirefoxOptions();
		firefoxOption.addArguments("--start-maximized");
		firefoxOption.addArguments("--private");
		DesiredCapabilities desireCapabilities = new DesiredCapabilities();
		desireCapabilities.setCapability(CapabilityType.PLATFORM_NAME, getPlatform(""));
		//desireCapabilities.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
		desireCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		desireCapabilities.setCapability("moz:firefoxOptions",firefoxOption);
		firefoxOption.merge(desireCapabilities);
	return firefoxOption;
	}

	private static ChromeOptions getChromeConfiguration(String platform)
	{
		ChromeOptions chromeOption = new ChromeOptions();
		chromeOption.addArguments("--incognito");
		chromeOption.addArguments("--start-maximized");
		DesiredCapabilities desireCapabilities = new DesiredCapabilities();
		desireCapabilities.setCapability(CapabilityType.PLATFORM_NAME, getPlatform(platform));
		//desireCapabilities.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
		desireCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		desireCapabilities.setCapability(ChromeOptions.CAPABILITY,chromeOption);
		chromeOption.merge(desireCapabilities);
		return chromeOption;
	}
	
	private static EdgeOptions getEdgeConfiguration(String platform)
	{
		EdgeOptions edgeOption = new EdgeOptions();
		edgeOption.addArguments("--start-maximized");
		edgeOption.addArguments("--inprivate");
		DesiredCapabilities desireCapabilities = new DesiredCapabilities();
		desireCapabilities.setCapability(CapabilityType.PLATFORM_NAME, getPlatform(platform));
		//desireCapabilities.setCapability(CapabilityType.BROWSER_VERSION,browserVersion);
		desireCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		desireCapabilities.setCapability(EdgeOptions.CAPABILITY,edgeOption);
		edgeOption.merge(desireCapabilities);
		return edgeOption;
	}

    private static Platform getPlatform(String platform) {
    	
		return Platform.WINDOWS;
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
		//devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
		String auth = username +":"+ password;
		
		// Encoding the username and password using Base64 (java.util)
		String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());
		
		// Pass the network header -> Authorization : Basic <encoded String>
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Authorization", "Basic "+encodeToString);				
		//devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
    	System.out.println("==== complete new Robot function ====");
    }
        
    public static synchronized WebDriver getDriver() {
    	return tdriver.get();
    }
    
 // -------------------------------------------

 	public static void runBatchFile(String Filename) {
 		try {
 			System.out.println("============== RunBatchFile Started ===========");

 			String command = "cmd /C start " + System.getProperty("user.dir") + "/" + Filename;
 			System.out.println("Current path : [" + System.getProperty("user.dir") + "]");
 			System.out.println("command : [" + command + "]");
 			Runtime rt = Runtime.getRuntime();
 			Process pr = rt.exec(command);

 			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 			BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

 			// read the output from the command
 			String s = null;
 			while ((s = stdInput.readLine()) != null) {
 				System.out.println("Here is the standard output of the command:\n");
 				System.out.println(s);
 			}
 			// read any errors from the attempted command
 			while ((s = stdError.readLine()) != null) {
 				System.out.println("Here is the standard error of the command (if any):\n");
 				System.out.println(s);
 			}

 			pr.destroy();
            
 		} catch (IOException e) {
 			System.out.println("Here is the standard error of the command (if any):\n");
 			System.out.println(e.getMessage());
 		}

 		System.out.println("============== RunBatchFile Ended ===========");
 	}

 	public static void runWindowsCommand(String command) {
 		try {

 			System.out.println("command : [" + command + "]");
 			Runtime rt = Runtime.getRuntime();
 			Process pr = rt.exec(command);

 			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 			BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

 			// read the output from the command
 			String s = null;
 			while ((s = stdInput.readLine()) != null) {
 				System.out.println("Here is the standard output of the command:\n");
 				System.out.println(s);
 			}
 			// read any errors from the attempted command
 			while ((s = stdError.readLine()) != null) {
 				System.out.println("Here is the standard error of the command (if any):\n");
 				System.out.println(s);
 			}
 			pr.destroy();
 		} catch (IOException e) {
 			System.out.println("Here is the standard error of the command (if any):\n");
 			System.out.println(e.getMessage());
 		}

 	}


 	public void runAllureReport() throws Throwable
 	{
        
	    
        /*
        String allurePath = System.getProperty("user.dir") +"\\allure-results";
        String allureprogramfilesPath = System.getenv("ProgramFiles") + "\\allure\\allure-2.13.9\\bin\\allure";
        System.out.println("allureFolderPath : [" + allurePath + "]");
        System.out.println("allureprogramfilesPath : [" + allureprogramfilesPath + "]");
        String cmd = "cd "+ allurePath;
		//runWindowsCommand(cmd);
        
		cmd = "allure serve "+ allurePath;
		cmd = allureprogramfilesPath +" serve C:\\Haitham\\ProjectWorkspaces\\WebControlScanner\\allure-results";
		cmd = " C:\\'Program Files'\\allure\\allure-2.13.9\\bin\\allure serve C:\\Haitham\\ProjectWorkspaces\\WebControlScanner\\allure-results";
		//runWindowsCommand(cmd); 
         */
		
		runBatchFile("allure.bat");
		
		/*
		 final List<Extension> extensions = Arrays.asList(new JacksonContext(), new MarkdownContext(), new FreemarkerContext(), new RandomUidContext(), new MarkdownDescriptionsPlugin(), new RetryPlugin(), new RetryTrendPlugin(), new TagsPlugin(), new SeverityPlugin(), new OwnerPlugin(), new IdeaLinksPlugin(), new CategoriesPlugin(), new CategoriesTrendPlugin(), new HistoryPlugin(), new HistoryTrendPlugin(), new DurationPlugin(), new DurationTrendPlugin(), new StatusChartPlugin(), new TimelinePlugin(), new SuitesPlugin(), new TestsResultsPlugin(), new AttachmentsPlugin(), new MailPlugin(), new InfluxDbExportPlugin(), new PrometheusExportPlugin(), new SummaryPlugin(), new ExecutorPlugin(), new LaunchPlugin(), new Allure1Plugin(), new Allure1EnvironmentPlugin(), new Allure2Plugin(), new ReportWebPlugin());
         Configuration configuration = (new ConfigurationBuilder()).fromExtensions(extensions).build();
         Path resultDi = Paths.get("target/allure-results");
         Path outDir = Paths.get("target/allure-report");
         new ReportGenerator(configuration).generate(outDir, resultDi);
         
               new AllureReportBuilder("1.5.4", new File("target/allure-report")).unpackFace();
         new AllureReportBuilder("1.5.4", new File("target/allure-report")).processResults(new File("target/allure-results"));
		*/
         
   
 	}
 	
}
