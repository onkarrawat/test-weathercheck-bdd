package apiobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


import io.restassured.response.ValidatableResponse;
import utils.LoggerFile;
import utils.PropertyHandler;
import utils.RestWrapper;

public class OpenWeatherMapObject {
	Double current_temp_from_API_Response_Json;
	Logger logger = utils.LoggerFile.logConfig(this.getClass().getName());

	PropertyHandler propertyHandler = new PropertyHandler();
	RestWrapper restWrapper = new RestWrapper(this.propertyHandler);

	public ValidatableResponse getCallForCurrentTempOfCitys(String cityname) throws Throwable {
		String serviceHost = propertyHandler.readProperty("Open_Weather_Map_Host");
		String ServiceEndPoint = "/data/2.5/weather";
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", cityname);
		params.put("appid", propertyHandler.readProperty("API_Key"));
		params.put("units", propertyHandler.readProperty("Temperture_unit"));
		restWrapper.simpleGetCallWithParams(serviceHost, ServiceEndPoint, params, restWrapper.setHeaders());

		return restWrapper.getResponse();
	}

	public Double getCurrentTempForCityFromOpenWeatherAPI() {
		JSONObject jsonObj = new JSONObject(restWrapper.getResponse().extract().body().asString());
		jsonObj = jsonObj.getJSONObject("main");
		current_temp_from_API_Response_Json = Double.valueOf(jsonObj.get("temp").toString());
		return current_temp_from_API_Response_Json;
	}

}
