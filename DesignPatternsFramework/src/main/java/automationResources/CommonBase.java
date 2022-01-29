package automationResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import PageComponents.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import static automationResources.ExtentTestManager.reporterLog;

public class CommonBase {

    public WebDriver driver;
    public Properties prop;
    public static Logger log = LogManager.getLogger(CommonBase.class.getName());

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeClass(alwaysRun = true)
    public WebDriver initializeDriver() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\automationResources\\config.properties");
        prop.load(fis);
        String browser = prop.getProperty("browser");
        if (browser.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src\\main\\java\\chromeDriver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            if (browser.contains("headless"))
                options.addArguments("headless");
            driver = new ChromeDriver();
            reporterLog("Maximize window");
            driver.manage().window().maximize();
        } else if (browser.equals("firefox")) {
            System.out.print("Firefox Driver");

        } else if (browser.equals("IE")) {
            System.out.print("IE Driver");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }

    public synchronized String getScreenshotPath(String testcaseName, WebDriver driver) throws IOException {
        String encodedBase64 = null;
        TakesScreenshot sc = (TakesScreenshot) driver;
        File testcaseScreenshot = sc.getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "\\ExtentReport\\Screenshots";
        File folder = new File(screenshotPath);
        if (!folder.exists())
            folder.mkdir();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);
        String destinationFile = screenshotPath + File.separator + testcaseName + actualDate + ".png";
        FileInputStream fileInputStreamReader;
        fileInputStreamReader = new FileInputStream(testcaseScreenshot);
        byte[] bytes = new byte[(int) testcaseScreenshot.length()];
        fileInputStreamReader.read(bytes);
        encodedBase64 = Base64.encodeBase64String(bytes);
        FileUtils.copyFile(testcaseScreenshot, new File(destinationFile));
        Log.info(destinationFile);
        return encodedBase64;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        reporterLog("Close browser");
        if (driver != null)
            driver.quit();
    }
}
