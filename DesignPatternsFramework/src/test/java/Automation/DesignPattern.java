package Automation;

import automationResources.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import PageComponents.MultiTrip;
import automationResources.CommonBase;
import pages.TravelHomePage;

public class DesignPattern extends CommonBase {

	private WebDriver driver;
	@BeforeClass
	public void setUp() {
		driver = getDriver();
	}

	@Test(groups = { "Sanity", "author-Radhika" },description="Design Pattern")
	public void flightTest() {
		driver.get(prop.getProperty("dropdownurl"));
		TravelHomePage travelHomePage = new TravelHomePage(driver);
		System.out.println(travelHomePage.getFooterNav().getFlightAttribute());
		ExtentTestManager.reporterLog("Successfully printed Footer flight attribute");

		System.out.println(travelHomePage.getNavigationBar().getFlightAttribute());
		ExtentTestManager.reporterLog("Successfully printed Navigation Bar flight attribute");

		System.out.println(travelHomePage.getFooterNav().getLinkCount());
		System.out.println(travelHomePage.getNavigationBar().getLinkCount());
		
		//one way, round trip, multi trip - check availibility(origin, destination)
		ExtentTestManager.reporterLog("Checking availability of multi trip booking");
		travelHomePage.setBookingStrategy(new MultiTrip());
		travelHomePage.checkAvail("origin", "destination");
	}
}
