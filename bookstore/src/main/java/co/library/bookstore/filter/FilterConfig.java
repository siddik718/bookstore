package co.library.bookstore.filter;

import co.library.bookstore.service.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public JwtFilter jwtFilter(UserClient userClient) {
        return new JwtFilter(userClient);
    }
}
