package notificationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTTokenConfig {

    @Bean
    public String getToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODIwMDE3MzYsImlzcyI6ImZhYnJpcXVl" +
                "IiwibmFtZSI6IkZpbGlwcEJhbGFrc2hpbiJ9.dC-ewYqmAaibiT4uNZKoB2g3AD343EG9d52U8wQfaQI";
    }
}
