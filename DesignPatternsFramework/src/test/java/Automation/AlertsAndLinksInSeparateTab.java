package Automation;

import automationResources.CommonBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class AlertsAndLinksInSeparateTab extends CommonBase {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test(groups = {"Regression", "author-Radhika"}, description = "Handle Alerts")
    public void javaAlerts() {
        driver.get(prop.getProperty("windowScroll"));
        String text = "Radhika";
        driver.findElement(By.id("name")).sendKeys(text);
        driver.findElement(By.cssSelector("[id='alertbtn']")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();
        driver.findElement(By.id("confirmbtn")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().dismiss();
    }

    @Test(groups = {"Regression", "author-Radhika"}, description = "Open Links in Separate Tab and Extract Title")
    public void openLinkInSeparateTab() throws InterruptedException {
        driver.get(prop.getProperty("windowScroll"));

        WebElement footerdriver = driver.findElement(By.id("gf-BIG"));// Limiting webdriver scope
        WebElement coloumndriver = footerdriver.findElement(By.xpath("//table/tbody/tr/td[1]/ul"));

        for (int i = 1; i < coloumndriver.findElements(By.tagName("a")).size(); i++) {
            String clickonlinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER);
            coloumndriver.findElements(By.tagName("a")).get(i).sendKeys(clickonlinkTab);
            Thread.sleep(5000);
        }// opens all the tabs
        Set<String> abc = driver.getWindowHandles();//4
        Iterator<String> it = abc.iterator();

        while (it.hasNext()) {
            driver.switchTo().window(it.next());
            System.out.println(driver.getTitle());
        }
    }

    @Test(groups = {"Regression", "author-Radhika"}, description = "Select date in calendar")
    public void selectDateInCalendar() {
        driver.get(prop.getProperty("dropdownurl"));
        System.out.println(driver.findElement(By.id("Div1")).getAttribute("style"));
        driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();
        System.out.println(driver.findElement(By.id("Div1")).getAttribute("style"));
        if (driver.findElement(By.id("Div1")).getAttribute("style").contains("1")) {
            System.out.println("its enabled");
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }
}
