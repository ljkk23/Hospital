package edu.swu.cs.filter;

import com.alibaba.fastjson.JSON;
import edu.swu.cs.Exception.SystemException;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.domain.securityEntity.UserDetailsImpl;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.utils.JwtUtil;
import edu.swu.cs.utils.RedisCache;
import edu.swu.cs.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(httpServletRequest.getRequestURI());
        //白名单：login和fegin接口
        if (Objects.equals(httpServletRequest.getRequestURI(), "/security-auth/login") ||
                Objects.equals(httpServletRequest.getRequestURI(), "/service-user/doctor/getDoctorByFeign")
               || Objects.equals(httpServletRequest.getRequestURI(), "/service-user/doctor/FeignGetDoctorInfo")
                || Objects.equals(httpServletRequest.getRequestURI(), "/service-user/patient/FeignGetPatientInfo")
                ||  Objects.equals(httpServletRequest.getRequestURI(), "/service-user/user/getUserByFeign")
                ||  Objects.equals(httpServletRequest.getRequestURI(), "/service-product/product/FeignGetProductInfo")
                || Objects.equals(httpServletRequest.getRequestURI(),"/service-ware/ware/lockWare")
                || Objects.equals(httpServletRequest.getRequestURI(),"/service-ware/ware/getWareByProductId")
                || Objects.equals(httpServletRequest.getRequestURI(),"/service-ware/ware/updateHotOrderWare")
                || httpServletRequest.getRequestURI().contains("swagger-resources")
                || httpServletRequest.getRequestURI().contains("swagger-ui.html")
                || httpServletRequest.getRequestURI().contains("/v2/api-docs")
                || httpServletRequest.getRequestURI().contains("webjars")
                || httpServletRequest.getRequestURI().contains("/service-user/user/addUser")
                || httpServletRequest.getRequestURI().contains("Feign")
                || httpServletRequest.getRequestURI().contains("getImage")
        ){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //获取请求token
        String token = httpServletRequest.getHeader("token");
        String userID;
        try {
            userID= String.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        UserDetailsImpl userDetails = null;
        boolean isVisitor=false;
        if(userID.contains("visitor")){
            isVisitor=true;
        }else {
            String[] userIdArray = userID.split("-");
            String realUserId = userIdArray[1];
            //通过redis获取登陆的时候存的UserDetailsImpl
            if (userID.contains("doctor-")) {
                userDetails = redisCache.getCacheObject("Login:" + "doctor-" + realUserId);
            } else if (userID.contains("user-")) {
                userDetails = redisCache.getCacheObject("Login:" + "user-" + realUserId);
            }
        }
        if (Objects.isNull(userDetails) && !isVisitor) {
            //redis中缓存过期，登陆超时
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
        if(!isVisitor && userID.contains("doctor-")) {
            //将获取的UserDetailsImpl通过UsernamePasswordAuthenticationToken包装存入SecurityContext中
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }else {
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        }
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
