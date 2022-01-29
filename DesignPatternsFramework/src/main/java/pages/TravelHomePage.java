package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import AbstractComponents.SearchFlightAvail;
import PageComponents.FooterNav;
import PageComponents.NavigationBar;

public class TravelHomePage {

    public WebDriver driver;
    By sectionElement = By.id("traveller-home");
    By footerNavSectionElement = By.id("buttons");
    SearchFlightAvail searchFlightAvail;

    public TravelHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public NavigationBar getNavigationBar() {
        return new NavigationBar(driver, footerNavSectionElement);
    }

    public FooterNav getFooterNav() {
        return new FooterNav(driver, sectionElement);
    }

    public void setBookingStrategy(SearchFlightAvail searchFlightAvail) {
        this.searchFlightAvail = searchFlightAvail;
    }

    public void checkAvail(String origin, String destination) {
        searchFlightAvail.checkAvailibility(origin, destination);
    }
}
