package com.eauction.seller.filter;

import com.eauction.seller.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
//@CrossOrigin("*")
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Inside filter method");
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpServletRequest httpReq = (HttpServletRequest) request;

        if(httpReq.getMethod().equals("OPTIONS")) {
            System.out.println("OPTIONS");
            httpRes.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//            httpRes.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            httpRes.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpRes.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            httpRes.setHeader("Access-Control-Allow-Credentials", "true");
            httpRes.setHeader("Access-Control-Max-Age", "3600");
            httpRes.setStatus((HttpServletResponse.SC_OK));
            return;
        }

        try {
            String authHeader = httpReq.getHeader("authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                throw new ServletException("Missing or invalid auth header");
            }

            String jwtToken = authHeader.substring(7);

            String url = "http://localhost:8080/e-auction/api/v1/user/authenticate-user?token={token}";
            Map<String, String> params = Collections.singletonMap("token", jwtToken);
            restTemplate = new RestTemplate();
            UserDetails userDetails = restTemplate.getForObject(url, UserDetails.class, params);

            if (userDetails != null && userDetails.getEmail() != null
                    && userDetails.getUserType() != null && userDetails.getUserType().equalsIgnoreCase("Seller")) {
                httpReq.setAttribute("username", userDetails.getEmail());
            } else {
                throw new ServletException("Invalid Session");
            }
        }
        catch (ServletException e){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
        catch (HttpClientErrorException.Unauthorized e){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
        catch (Exception e){
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }

        filterChain.doFilter(httpReq, httpRes);
    }

}
