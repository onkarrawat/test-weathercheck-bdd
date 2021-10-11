package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.BaseClass;

public class WeatherForeCastPage extends BaseClass {
	@FindBy(xpath="//h2[@class='cur-con-weather-card__title' and contains(text(),'Current Weather')]")
	WebElement currentWeatherTitle;
	
	@FindBy(xpath="//a[contains(@class,'cur-con-weather-card')]//div[@class='temp']")
	WebElement currentTemperature;
	
	
	public WeatherForeCastPage(){
		PageFactory.initElements(driver, this);
	}
	
	public void verifyWeatherforeCastPageIsDisplayed() {
		try {
			currentWeatherTitle.isDisplayed();

		}

		catch (Exception e) {
			Assert.fail("Weather report page for searched city not found");
		}
	}
	
	public String getCurrentTemperatureOfCity() {
		String currenttemp = currentTemperature.getText().replaceAll("[^\\d]", "");
		return currenttemp;
	}

}
