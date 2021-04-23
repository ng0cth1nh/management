package com.ng0cth1nh.management.security;

import com.ng0cth1nh.management.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {

    private final static String TOKEN_HEADER = "authorization";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(TOKEN_HEADER);
        if (jwtUtil.validateTokenLogin(authToken)) {
            String username = jwtUtil.getUsernameFromToken(authToken);
            com.ng0cth1nh.management.model.User user = userService.findByUsername(username);
            if (user != null) {
                System.out.println(user.getUsername());
                System.out.println(user.getRoles().iterator().next().getPermissions().iterator().next().getPermissionKey());
                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
                UserDetails userDetail = new User(username, user.getPassword(), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked, user.getAuthorities());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
                        null, userDetail.getAuthorities());
                System.out.println("userdetail " + userDetail.getAuthorities().size());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
