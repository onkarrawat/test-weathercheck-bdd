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
import pages.AccueWeatherSearchPage;
import pages.WeatherForeCastPage;
import utils.BaseClass;
import utils.LoggerFile;
import utils.PropertyHandler;
import utils.RestWrapper;

public class AccuWeatherStepDef extends BaseClass {
	RestWrapper restWrapper;
	Double current_temprature_accu_weather;
	WebDriver driver = null;
	ValidatableResponse response;
	Double current_temprature_Open_weather_Map;
	AccueWeatherSearchPage accueWeatherSearchPage;
	WeatherForeCastPage weatherForeCastPage;
	OpenWeatherMapObject openWeatherMapObject = new OpenWeatherMapObject();
	Logger logger = utils.LoggerFile.logConfig(AccuWeatherStepDef.class.getName());
	


	@Given("open browser and launch url")
	public void open_browser() throws Throwable {
		BaseClass.initialization();

	}

	
	@When("^Perform city search for weather info \"(.*)\"$")
	public void Perform_city_search_for_weather_info(String city) throws Throwable {
		accueWeatherSearchPage =  new AccueWeatherSearchPage();
		accueWeatherSearchPage.searchCityWeatherForecast(city);
		

	}

	@When("^verify current weather page for the searched city$")
	public void verify_current_weather_page_for_the_city() throws Throwable {
		weatherForeCastPage = new WeatherForeCastPage();
		weatherForeCastPage.verifyWeatherforeCastPageIsDisplayed();

	}

	@When("^store the temprature in variable$")
	public void stoe_the_temprature_in_variable() throws Throwable {
		weatherForeCastPage = new WeatherForeCastPage();
		current_temprature_accu_weather = Double.valueOf(weatherForeCastPage.getCurrentTemperatureOfCity());
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
		Double diffInTemprature = current_temprature_accu_weather - current_temprature_Open_weather_Map;
		logger.info("diffInTemprature  " + Math.abs(diffInTemprature));
		if (Math.abs(diffInTemprature) <= Double.valueOf(AcceptedVariance))
			logger.info("Current Temperature for the city is in acceptable variance of upto " + AcceptedVariance
					+ " degree celcious from Accu weather source and Open Weather API Source");
		else
			org.junit.Assert.fail(Math.abs(diffInTemprature)
					+ " Current Temperature variation is more than the Acceptable variance which is " + AcceptedVariance
					+ " degree celcious for the city from Accu weather source and Open Weather API source current_temprature_accu_weather "
					+ current_temprature_accu_weather + " and current_temprature_Open_weather_Map "
					+ current_temprature_Open_weather_Map);

	}

	@When("close the browser")
	public void close_the_browser() throws Throwable {
		BaseClass.closeBrowser();
	}
}
