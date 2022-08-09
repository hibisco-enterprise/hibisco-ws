package enterprise.hibisco.hibiscows.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/application.yml", encoding="UTF-8")
public class AppConfiguration {
}
