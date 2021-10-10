package cucumberrunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features"
		,glue={"stepdefinitions"},
				format = {
		                "pretty",
		                "html:target/cucumber-reports/cucumber-pretty",
		                "json:target/cucumber-reports/CucumberTestReport.json",
		                "junit:target/cucumber-reports/CucumberTestReport.xml"
		        }
		)
public class TestRunner {

}
