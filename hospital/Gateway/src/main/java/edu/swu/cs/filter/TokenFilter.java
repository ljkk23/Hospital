package edu.swu.cs.filter;

import edu.swu.cs.utils.JwtUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TokenFilter implements GlobalFilter, Ordered {
    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String value = exchange.getRequest().getPath().value();
        System.out.println(value);
        // 判断是否符合白名单/api/security-auth/login
        if (value.equals("/api/security-auth/login")) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        String check=null;
        log.info(token+"    "+check);
        try {
            //解析token
            check= String.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            //token解析出错
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //token解析成功但是出错
        if (!(check.contains("doctor-")|| check.contains("user-"))) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -100;
    }
}
