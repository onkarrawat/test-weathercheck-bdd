# test-weathercheck-bdd
**Desciption about testcase**
--Test to compare current temperature for a city from Accu weather and Open Weather Map sources
--This project comtains featue file for testing the current temperature shown by 2 different sources for same city.
--Variance difference of upto 1 degree celcius is taken as accesptable for the current temperature shown by both the sources
e.g. for mumbai if Accu weather shows 32 degree celcius and Open weather shows 33 degree celcius then test case accepts it as Pass otherwise fail

**Prerequisites**
--Chrome browser Version 94.0.4606.71 (Official Build) (64-bit) is required as the latest selenium version  i.e. 4.0.0-rc-2 is being used in this project.
--latest supporting ChromeDriver to match with selenium version 4.0.0-rc-2 and Chrome browser Version 94.0.4606.71 is already provided in the project.

**How to run test**
 
--Open command line e.g. Gitbash
--clone the project in local using git url
--go to the project folder test-weathercheck-bdd
--then use maven command mvn clean verify
--it will run the test and at the end generate the html cucumber report under the target folder.
-- there are many more report formats generated under target folder of the project like json, xml.
