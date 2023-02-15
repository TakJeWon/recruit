package com.castis.career.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final String DEFAULT_FAILURE_URL = "/admin/adminLogin?error=true";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String errorMessage = null;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if (exception instanceof DisabledException){
            errorMessage = "비활성화된 계정입니다. 관리자에게 문의하세요.";
        } else if (exception instanceof  CredentialsExpiredException){
            errorMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        session.setAttribute("errorMessage", errorMessage);

        response.sendRedirect("/admin/adminLogin");
    }
}
