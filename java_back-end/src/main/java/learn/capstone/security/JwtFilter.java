package learn.capstone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

public class JwtFilter implements Filter {

    private static final String SECRET = "de8a26d0-f6e8-4470-91d0-ba7a44391281";
    private Key key = new SecretKeySpec(SECRET.getBytes(), HS256.getJcaName());
    private final String ISSUER = "dev10-users-api";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("POST")
                || request.getMethod().equalsIgnoreCase("PUT")
                || request.getMethod().equalsIgnoreCase("DELETE")) {

            AppUser user = getUserFromToken(request.getHeader("Authorization"));
            if (user == null) {
                response.setStatus(403);
                System.out.println("FORBIDDEN");
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            String authStr = (String) jws.getBody().get("roles");
            List<String> roles = Arrays.stream(authStr.split(",")).collect(Collectors.toList());

            AppUser user = new AppUser();
            user.setUsername(username);
            user.setRoles(roles);

            return user;

        } catch (JwtException e) {
            System.out.println(e);
        }

        return null;
    }
}
