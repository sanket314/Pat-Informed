package base;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


public class BaseTest {
	

    public static WebDriver driver;
    public static Properties prop = new Properties();
    public static Properties loc = new Properties();
    public static FileReader fr;
    public static FileReader fr1;
	public String filepath="/Users/apple/eclipse-workspace/PatInformed/src/test/resources/testdata/PatTestData.xlsx";
    public WebDriverWait wait;

    
	@BeforeSuite
	public void Propload() throws IOException {
		
		FileInputStream configFis = new FileInputStream("/Users/apple/eclipse-workspace/PatInformed/src/test/resources/configfiles/config.properties");
		prop.load(configFis);
		
		FileInputStream locFis =new FileInputStream("/Users/apple/eclipse-workspace/PatInformed/src/test/resources/configfiles/locators.properties");
		loc.load(locFis);
	}

    @BeforeTest
    public void setUpBrowser() throws IOException {
        
    	
    	 ChromeOptions options = new ChromeOptions();
         options.addArguments("--incognito");

         driver = new ChromeDriver(options);
         driver.manage().window().maximize();
    	
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
         driver.get(prop.getProperty("url"));
    }


    @AfterClass
    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }	

}
