package TheInternetTests.Extensions;

import org.junit.jupiter.api.extension.*;

import java.io.FileReader;
import java.util.Properties;

public class PropsResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Properties.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Properties properties = new Properties();

        try{
            properties.load(new FileReader("src/main/resources/credentials.properties"));
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        return properties;
    }
}