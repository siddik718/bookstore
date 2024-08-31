package co.library.bookstore.filter;

import co.library.bookstore.model.UserClient.UserResponse;
import co.library.bookstore.service.UserClient;
import feign.FeignException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final UserClient userClient;
    private final FilterUtils filterUtils;

    @Autowired
    public JwtFilter(UserClient userClient) {
        this.userClient = userClient;
        this.filterUtils = new FilterUtils(userClient);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authorizationHeader = httpRequest.getHeader("Authorization");

        try {
            System.out.println("UserResponse: " + this.filterUtils.verifyToken(authorizationHeader));
            chain.doFilter(request, response);

        }catch(FeignException e) {
            int status = e.status();
            String errorMessage = e.contentUTF8();  // Get the exact error content from the microservice

            httpResponse.setStatus(status);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(errorMessage);
        }
    }

}
