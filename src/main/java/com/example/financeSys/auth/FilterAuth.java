package com.example.financeSys.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.entity.User;
import com.example.financeSys.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    private UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.equals("/transaction/create")) {
            String authorization = request.getHeader("Authorization");
            String authEncoded = authorization.substring(authorization.lastIndexOf(" ") + 1);
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            String authString = new String(authDecoded);
            String[] credentials = authString.split(":");

            User user = service.findByUsername(credentials[0]);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                var password = BCrypt.verifyer().verify(credentials[1].toCharArray(), user.getPassword());
                if (password.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
