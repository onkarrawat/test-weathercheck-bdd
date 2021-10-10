Feature: Compare city temprature from Accu Weather and Open weather API 

 Scenario Outline: validate difference between City curent temprature from UI and API for "<City>"
    Given open browser
    And  launch the url
    When Enter city name in search textbox "<City>"
    And verify current weather page for the searched city 
    And store the temprature in variable
    Then close the browser
    And user makes a get call to open weather map api with city name "<CityForAPI>"
    And validate the response status code <responseCode>
    And store the current temprature for city from response json in variable
    Then Assert that current temprature from Accu Weather and Open Weather API are matching uptill variance of "<variance_Acceptable>" degree

 Examples: 
   |City               |CityForAPI | responseCode |variance_Acceptable  |
   |Mumbai, Maharashtra|  Mumbai   |   200        |1.0                  |
   |Delhi, Delhi, IN   |  Delhi    |   200        |1.0                  |
