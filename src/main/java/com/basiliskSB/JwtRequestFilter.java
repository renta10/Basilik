package com.basiliskSB;
import com.basiliskSB.utility.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JwtRequestFilter extends OncePerRequestFilter: Inside doFilterInternal");

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ", "");
//            username = jwtToken.getUsername(token);

            // test
            try {
                username = jwtToken.getUsername(token);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(username != null && authentication == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtToken.validateToken(token, userDetails)){

                System.out.println(jwtToken.validateToken(token, userDetails));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
