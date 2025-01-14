package com.example.financeSys.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.dto.UserDTO;
import com.example.financeSys.entity.User;
import com.example.financeSys.services.AuthService;
import com.example.financeSys.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    private AuthService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.equals("/transaction/create")) {
            String authorization = request.getHeader("Authorization");
            String authEncoded = authorization.substring(authorization.lastIndexOf(" ") + 1);
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            String authString = new String(authDecoded);
            String[] credentials = authString.split(":");
            var validationResponse = service.validateCredentials(credentials[0], credentials[1]);
            if (validationResponse.getStatusCode() == HttpStatus.ACCEPTED) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
