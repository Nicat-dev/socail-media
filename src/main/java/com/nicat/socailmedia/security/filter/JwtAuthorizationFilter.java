package com.nicat.socailmedia.security.filter;

import com.nicat.socailmedia.security.service.CustomUserDetailsService;
import com.nicat.socailmedia.security.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("processing authentication for '{}'", request.getRequestURL());


        String requestHeader = request.getHeader(this.tokenHeader);

        String email = null;
        String authToken = null;
        if(Objects.nonNull(requestHeader) && requestHeader.startsWith("Bearer ")){
            authToken = requestHeader.substring(7);
            try{
                email = jwtTokenUtil.getUsernameFromToken(authToken);
            }catch (IllegalArgumentException e){
                log.error("an error occurred during getting username from token", e);
            }catch (ExpiredJwtException e) {
                log.warn("the token is expired and not valid anymore", e);
            }
        }else {
            log.warn("couldn't find bearer string, will ignore the header");
        }

        log.debug("checking authentication for user '{}'", email);

        if(Objects.nonNull(email) && SecurityContextHolder.getContext().getAuthentication() == null){
            log.debug("security context was null, so authorizing user");

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if(jwtTokenUtil.validateToken(authToken, userDetails)){
                //???
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authorized user '{}', setting security context", email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
