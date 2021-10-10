package stepdefinitions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import apiobjects.OpenWeatherMapObject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import utils.LoggerFile;
import utils.PropertyHandler;
import utils.RestWrapper;

public class AccuWeatherStepDef {
	RestWrapper restWrapper;
	Double current_temprature_accu_weather;
	WebDriver driver = null;
	ValidatableResponse response;
	Double current_temprature_Open_weather_Map;

	OpenWeatherMapObject openWeatherMapObject = new OpenWeatherMapObject();
	Logger logger = utils.LoggerFile.logConfig(AccuWeatherStepDef.class.getName());
	PropertyHandler propertyHandler = new PropertyHandler();

	@SuppressWarnings("deprecation")
	@Given("open browser")
	public void open_browser() throws Throwable {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + File.separator + "\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Long.valueOf(propertyHandler.readProperty("ImplicitWaitInSeconds")),
				TimeUnit.SECONDS);
		logger.info("Browser opened successfully");

	}

	@And("launch the url")
	public void launch_the_url() throws Throwable {

		driver.get(propertyHandler.readProperty("Accu_Weather_Host"));
		Thread.sleep(3000);
		logger.info("url launched successfully");

	}

	@When("^Enter city name in search textbox \"(.*)\"$")
	public void Enter_city_name_in_search_textbox(String city) throws Throwable {

		driver.findElement(By.xpath("//input[@name='query']")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='query']")).sendKeys(city);

		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@name='query']")).sendKeys(Keys.ENTER);
		logger.info("search perfomed successfully");
		Thread.sleep(2000);

	}

	@When("^verify current weather page for the searched city$")
	public void verify_current_weather_page_for_the_city() throws Throwable {
		Thread.sleep(2000);
		try {
			driver.findElement(
					By.xpath("//h2[@class='cur-con-weather-card__title' and contains(text(),'Current Weather')]"))
					.isDisplayed();
			Thread.sleep(2000);
		}

		catch (Exception e) {
			Assert.fail("Weather report page for searched city not found");
		}

	}

	@When("^store the temprature in variable$")
	public void stoe_the_temprature_in_variable() throws Throwable {
		String currenttemp = driver
				.findElement(By.xpath("//a[contains(@class,'cur-con-weather-card')]//div[@class='temp']")).getText()
				.replaceAll("[^\\d]", "");
		current_temprature_accu_weather = Double.valueOf(currenttemp);
		logger.info("current_temprature_accu_weather  " + current_temprature_accu_weather);
	}

	@When("^user makes a get call to open weather map api with city name \"(.*)\"$")
	public void user_makes_a_get_call_to_open_weather_map_api(String city) throws Throwable {
		response = openWeatherMapObject.getCallForCurrentTempOfCitys(city);

		logger.info("response is " + response.extract().body().asString());
	}

	@And("^store the current temprature for city from response json in variable$")
	public void stoe_the_temprature_for_city_from_response_json_in_variable() throws Throwable {

		current_temprature_Open_weather_Map = openWeatherMapObject.getCurrentTempForCityFromOpenWeatherAPI();
		logger.info("current_temprature_Open_weather_Map  " + current_temprature_Open_weather_Map);
	}

	@Then("^validate the response status code (\\d+)$")
	public void validate_response_status_code(int responseCode) throws Throwable {
		Assert.assertEquals(response.extract().statusCode(), responseCode);
	}

	@When("^Assert that current temprature from Accu Weather and Open Weather API are matching uptill variance of \"(.*)\" degree$")
	public void Assert_that_current_tempratue_from_Accu_Weather_and_open_Weather_API_are_matching_uptill_variance_of_1_degree(
			String AcceptedVariance) throws Throwable {
		logger.info("current_temprature_accu_weather  " + current_temprature_accu_weather);
		logger.info("current_temprature_Open_weather_Map  " + current_temprature_Open_weather_Map);
		Double diffInTemprature = current_temprature_accu_weather - current_temprature_Open_weather_Map;
		logger.info("diffInTemprature  " + Math.abs(diffInTemprature));
		if (Math.abs(diffInTemprature) <= Double.valueOf(AcceptedVariance))
			logger.info("Current Temperature for the city is in acceptable variance of " + AcceptedVariance
					+ " from Accu weather source and Open Weather API Source");
		else
			org.junit.Assert.fail(Math.abs(diffInTemprature)
					+ " Current Temperature variation is more than the Acceptable variance which is " + AcceptedVariance
					+ " degree celcious for the city from Accu weather source and Open Weather API source current_temprature_accu_weather "
					+ current_temprature_accu_weather + " and current_temprature_Open_weather_Map "
					+ current_temprature_Open_weather_Map);

	}

	@When("close the browser")
	public void close_the_browser() throws Throwable {
		driver.quit();
	}
}
