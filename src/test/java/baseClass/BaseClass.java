package baseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import utils.ExcelUtils;


public class BaseClass {
	protected WebDriver driver;


	@BeforeClass
	@Parameters("browser")
	public void browserSetup(String parameterValue) throws Exception
	{
		// Open Excel sheet
	    ExcelUtils.openExcel("Login");
	    if (parameterValue.equalsIgnoreCase("ChromeBrowser")) {
	        driver = new ChromeDriver();
	    }
	    else if (parameterValue.equalsIgnoreCase("FirefoxBrowser")) {
	        driver = new FirefoxDriver();
	    }
	    else if (parameterValue.equalsIgnoreCase("EdgeBrowser")) {
	    	//System.setProperty("webdriver.edge.driver","D:\\Seleniumbinaries\\BrowserBinaries\\msedgedriver.exe");
	    	driver = new EdgeDriver();
	    }
	    else {
	        throw new IllegalArgumentException("Invalid browser: " + parameterValue);
	    }
	    driver.manage().window().maximize();
	    System.out.println("Application launched in " + parameterValue);
		
	}
	
	@BeforeMethod
	public void urlLaunch()
	{
		driver.get("https://practicetestautomation.com/practice-test-login");
	}
	
	@AfterClass
	public void teardown() throws Exception
	{
		//save and close Excel-- write data into excel
		ExcelUtils.saveAndClose();
		driver.quit();
	}
}









