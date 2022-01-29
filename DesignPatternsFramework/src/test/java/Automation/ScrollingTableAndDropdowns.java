package Automation;

import automationResources.CommonBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class ScrollingTableAndDropdowns extends CommonBase {

    public WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test(groups = {"Regression", "author-Radhika"}, description = "Scroll Window and Table")
    public void windowAndTableScroll() throws InterruptedException {
        driver.get(prop.getProperty("separateLinkUrl"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(5000);
        js.executeScript("document.querySelector('.tableFixHead').scrollTop=5000");

        List<WebElement> values = driver.findElements(By.cssSelector(".tableFixHead td:nth-child(4)"));
        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum = sum + Integer.parseInt(values.get(i).getText());
        }
        System.out.println(sum);
        driver.findElement(By.cssSelector(".totalAmount")).getText();
        int total = Integer.parseInt(driver.findElement(By.cssSelector(".totalAmount")).getText().split(":")[1].trim());
        Assert.assertEquals(sum, 0);
    }

    @Test(groups = {"Regression", "author-Radhika"}, description = "Check functionality of AutoSuggestive Dropdown")
    public void autoSuggestiveDropdowns() throws InterruptedException {
        driver.get(prop.getProperty("dropdownurl"));
        driver.findElement(By.id("autosuggest")).sendKeys("ind");
        Thread.sleep(3000);
        List<WebElement> options = driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase("India")) {
                option.click();
                break;
            }
        }
    }
}
