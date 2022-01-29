package PageComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import AbstractComponents.AbstractComponent;

public class FooterNav extends AbstractComponent {

	//when selenium executes any method in this class - scope of selenium should be 
	//in the footer only
	// we are creating custom element
	By flights = By.cssSelector("[title='Flights']");
	By links = By.cssSelector("a");
	
	public FooterNav (WebDriver driver, By sectionElement) {
		super(driver, sectionElement); //when we inherit parent class - you should invoke parent class constructor
		// in your own child class
	}
	
	public String getFlightAttribute() {
		return findElement(flights).getAttribute("class");
	}
	
	public int getLinkCount() {
		return findElements(links).size();
	}
}
