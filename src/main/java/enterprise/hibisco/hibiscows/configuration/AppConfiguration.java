package enterprise.hibisco.hibiscows.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/application.properties", encoding="UTF-8")
public class AppConfiguration {
}
