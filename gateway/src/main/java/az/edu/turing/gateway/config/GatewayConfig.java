package az.edu.turing.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    @Value("${jwt.secret}")
    private static String SECRET_KEY;
    private static final String[] PUBLIC_ENDPOINTS = {
            "/ms-identity/api/v1/auth/login",
            "/ms-identity/api/v1/auth/register"
    };

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-account", r -> r.path("/ms-account/**")
                        .filters(f -> f.rewritePath("/ms-account/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ms-account"))
                .route("ms-currency", r -> r.path("/ms-currency/**")
                        .filters(f -> f.rewritePath("/ms-currency/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ms-currency"))
                .route("ms-identity", r -> r.path("/ms-identity/**")
                        .filters(f -> f.rewritePath("/ms-identity/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ms-identity"))
                .route("ms-transfer", r -> r.path("/ms-transfer/**")
                        .filters(f -> f.rewritePath("/ms-transfer/(?<segment>.*)", "/${segment}"))
                        .uri("lb://ms-transfer"))
                .build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(tokenValidationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private WebFilter tokenValidationFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String path = exchange.getRequest().getPath().toString();
            for (String publicEndpoint : PUBLIC_ENDPOINTS) {
                if (path.startsWith(publicEndpoint)) {
                    return chain.filter(exchange);
                }
            }

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                return Mono.fromRunnable(() ->
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                );
            }

            try {
                token = token.substring(7);
                validateToken(token);
                return chain.filter(exchange);
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            }
        };
    }

    private void validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
