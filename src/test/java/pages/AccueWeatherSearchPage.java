package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.BaseClass;

public class AccueWeatherSearchPage extends BaseClass {
	@FindBy(xpath="//input[@name='query']")
	WebElement searchBox;
	
	//initializing page objects
	public AccueWeatherSearchPage() {
		PageFactory.initElements(driver, this);
		
	}
	
	public WeatherForeCastPage searchCityWeatherForecast(String city) throws Throwable {
		searchBox.clear();
		searchBox.sendKeys(city);
		searchBox.sendKeys(Keys.ENTER);
		return new WeatherForeCastPage();
	}

}
