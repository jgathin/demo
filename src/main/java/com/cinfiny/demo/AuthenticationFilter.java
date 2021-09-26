package com.cinfiny.demo;

import com.cinfiny.demo.controllers.AuthenticationController;
import com.cinfiny.demo.models.Designer;
import com.cinfiny.demo.models.Installer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/login", "/reg", "/logout", "/css",
            "/styles.css", "/index", "/company", "/bmiLoggedOut", "/bootstrap.min.css", "/signin.css",
            "/images/icon.png", "/favicon.png");

    private static boolean isWhiteListed(String path) {
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // Don't require sign-in for whitelisted pages
        if (isWhiteListed(request.getRequestURI())) {
            //returning true indicates that the request may proceed
            return true;
        }

        HttpSession session = request.getSession();
        Designer designer = authenticationController.getDesignerFromSession(session);
        Installer installer = authenticationController.getInstallerFromSession(session);

        // the designer is logged in
        if (designer != null) {
            return true;
        }

        // the installer is logged in
        if (installer != null) {
            return true;
        }

        // the user is NOT logged in
        response.sendRedirect("/index");
        return false;
    }
}