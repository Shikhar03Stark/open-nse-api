package com.devitvish.nsestockprice.auth.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devitvish.nsestockprice.auth.ApiKeyAuthentication;
import com.devitvish.nsestockprice.exception.error.NseServerUnauthorizedError;
import com.devitvish.nsestockprice.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class APITokenFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final Optional<String> optionalToken = extractTokenFromRequest(request);
        final String token = optionalToken.orElseThrow(() -> new BadCredentialsException("API key not found"));
        
        if(jwtService.isTokenValid(token) && !jwtService.isTokenExpired(token)){
            final Authentication auth = new ApiKeyAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new BadCredentialsException(String.format("The token %s is not valid or expired", token));
        }

        doFilter(request, response, filterChain);
    }

    /**
     * Order of precedence in checking API token will be
     * 1. Authorization Bearer header
     * 2. Query Parameter apikey=<token>
     * @param request
     * @return Optional<String>
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader)){
            // remove prefix Bearer
            final String token = authHeader.substring("Bearer ".length());
            return Optional.of(token);
        }

        final String queryValue = request.getParameter("apikey");
        if(StringUtils.hasText(queryValue)){
            return Optional.of(queryValue);
        }

        return Optional.empty();
    }

}
