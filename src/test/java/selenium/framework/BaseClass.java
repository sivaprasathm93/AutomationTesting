package selenium.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import selenium.framework.reports.ExtentManager;

import java.util.concurrent.TimeUnit;

public class BaseClass extends ExtentManager{

    private static WebDriver driver;
    private static ExtentReports extent;
    public static ExtentTest logger;
    private String browser="chrome";

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite()  {
        initializeDriver(browser);
        getDriver().manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        extent = ExtentManager.getInstance();
    }

    @AfterTest(alwaysRun = true)
    public void afterSuite() {
        getDriver().quit();
        extent.flush();
    }

    private  void initializeDriver(String browserType){
        if(browserType.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }
    }
}