package tg.ceel.cj.casierapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwtAuthprizationToken = request.getHeader(Constants.HEADER_STRING);
           // System.err.println(" Token: "+jwtAuthprizationToken);
           // logger.info(" Token: "+jwtAuthprizationToken);
            if (jwtAuthprizationToken == null || !jwtAuthprizationToken.startsWith(Constants.TOKEN_PREFIX)){
                filterChain.doFilter(request, response);
               // logger.info(" Filtring entrance request..............");
               // System.err.println(" Filtring entrance request..............");
                return;
            }else {
                String token = jwtAuthprizationToken.replace(Constants.TOKEN_PREFIX, "");
                Algorithm algorithm = Algorithm.HMAC256(Constants.SIGNING_KEY);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT jwt = JWT.decode(token);
                if( jwt.getExpiresAt().before(new Date())) {
                    logger.error("token is expired");
                    System.err.println("token is expired");
                }
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String st : roles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(st));
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
              //  System.err.println("authentication "+SecurityContextHolder.getContext().getAuthentication());
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ecrror-message"+ e.getMessage());
            response.addHeader("ecrror-message", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
