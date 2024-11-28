package az.edu.turing.gateway.config;

import az.edu.turing.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;



import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private WebFilter jwtAuthenticationFilter() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();

            if (path.contains("/api/v1/auth/register") ||
                    path.contains("/api/v1/auth/login")) {
                return chain.filter(exchange);
            }

            String token = extractTokenFromRequest(exchange);

            if (token == null) {
                return unauthorized(exchange);
            }

            try {
                if (!jwtUtil.validateToken(token)) {
                    return unauthorized(exchange);
                }

                Map<String, Object> claims = jwtUtil.extractAllClaims(token);

                String role = (String) claims.get("role");
                if (!isAuthorized(path, role)) {
                    return forbidden(exchange);
                }

                return chain.filter(exchange);
            } catch (Exception e) {
                return unauthorized(exchange);
            }
        };
    }

    private String extractTokenFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isAuthorized(String path, String role) {
        if (path.contains("/admin") && !"ADMIN".equals(role)) {
            return false;
        }
        return !path.contains("/user") || "USER".equals(role);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}

