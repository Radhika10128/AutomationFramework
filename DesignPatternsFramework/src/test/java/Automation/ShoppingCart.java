package Automation;

import automationResources.CommonBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.ShoppingCartPage;

import static automationResources.ExtentTestManager.reporterLog;

public class ShoppingCart extends CommonBase {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test(groups = {"Sanity", "author-Radhika"}, description = "Add Items to Shopping Cart")
    public void addItemsToShoppingCart() throws InterruptedException {
        String[] itemsNeeded = {"Cucumber", "Brocolli", "Beetroot"};
        reporterLog("Opening " + prop.getProperty("shoppingCart"));
        driver.get(prop.getProperty("shoppingCart"));
        Thread.sleep(3000);
        ShoppingCartPage shoppingCart = new ShoppingCartPage(driver);
        shoppingCart.addItems(itemsNeeded);
    }
}
