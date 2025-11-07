package com.backend.hackaton.shared.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Component
@Order(0)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        logger.debug("Original request: {} {}", method, requestURI);

        String correctedURI = removeDuplicatePath(requestURI);

        if (!correctedURI.equals(requestURI)) {
            logger.warn("⚠️ Detected duplicate path. Original: {} | Corrected: {}", requestURI, correctedURI);

            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getRequestURI() {
                    return correctedURI;
                }

                @Override
                public String getServletPath() {
                    String servletPath = urlPathHelper.getServletPath(request);
                    return removeDuplicatePath(servletPath);
                }

                @Override
                public String getPathInfo() {
                    String pathInfo = urlPathHelper.getPathWithinServletMapping(request);
                    return removeDuplicatePath(pathInfo);
                }
            };

            filterChain.doFilter(wrappedRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String removeDuplicatePath(String uri) {
        if (uri == null || uri.isEmpty()) {
            return uri;
        }

        int firstApiIndex = uri.indexOf("/api/");
        if (firstApiIndex < 0) {
            return uri;
        }

        String afterFirstApi = uri.substring(firstApiIndex + 5); // +5 para "/api/"
        int secondApiIndex = afterFirstApi.indexOf("/api/");

        if (secondApiIndex >= 0) {
            String corrected = uri.substring(firstApiIndex + 5 + secondApiIndex);
            logger.warn("⚠️ Detected duplicate /api/ path. Original: '{}' | Corrected: '{}'", uri, corrected);
            return corrected;
        }

        return uri;
    }
}

