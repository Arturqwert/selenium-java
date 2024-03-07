package MyCucumber;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@SelectClasspathResource("/MyCucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "MyCucumber")

public class glue {
}
