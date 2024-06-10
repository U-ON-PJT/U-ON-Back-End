package com.uon.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.uon.util.JWTUtil;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //단순 조회 요청과 preflight 요청인 경우, true 로 넘김
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.debug("AuthInterceptor()의 preHandle실행 method:{}", method);
//		System.out.println(method);
//		System.out.println(requestURI);

        log.debug(requestURI);
        if (method.equals("OPTIONS")) return true;    //preflight request 허용
        if (method.equals("GET")) return true;        //조회요청은 권한 필요 없음

        String tokenHeader = request.getHeader("Authorization");    //Header에서 토큰 정보 추출

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }

        //토큰이 유효하지 않은 경우
        String token = tokenHeader.substring(7);
        if (!jwtUtil.isValid(token)) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }

        return true;
    }
}
