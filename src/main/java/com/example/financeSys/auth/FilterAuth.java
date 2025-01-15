package com.example.financeSys.auth;

import jakarta.servlet.*;
import com.example.financeSys.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class FilterAuth extends OncePerRequestFilter {

    private final AuthService service;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public FilterAuth(AuthService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if ("/transaction/create".equals(servletPath)) {
            String authorization = request.getHeader(AUTHORIZATION_HEADER);
            if (authorization.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header is missing");
            }

            try {
                String authEncoded = authorization.substring(authorization.lastIndexOf(" ") + 1);
                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
                String authString = new String(authDecoded);
                String[] credentials = authString.split(":");
                var validationResponse = service.validateCredentials(credentials[0], credentials[1]);

                if (validationResponse.getStatusCode() == HttpStatus.ACCEPTED) {
                    var userIdResponse = service.getUserIdByUsername(credentials[0]);
                    if (userIdResponse.getStatusCode() == HttpStatus.OK) {
                        request.setAttribute("userId", userIdResponse.getBody());
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid authorization header");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}