package com.project1.project1.filters;

import com.project1.project1.Wrappers.gzip.GZipServletResponseWrapper;
import com.project1.project1.Wrappers.deflate.DeflateServletResponseWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CompressionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String acceptEncoding = httpReq.getHeader("Accept-Encoding");

        if (acceptEncoding == null) {
            chain.doFilter(request, response);
            return;
        }

        // Qualités associées aux algorithmes
        double gzipQ = getQuality(acceptEncoding, "gzip");
        double deflateQ = getQuality(acceptEncoding, "deflate");

        // Aucun algo accepté → pas de compression
        if (gzipQ == 0 && deflateQ == 0) {
            chain.doFilter(request, response);
            return;
        }

        ServletResponse wrapper;

        if (gzipQ >= deflateQ) {
            httpRes.setHeader("Content-Encoding", "gzip");
            wrapper = new GZipServletResponseWrapper(httpRes);
        } else {
            httpRes.setHeader("Content-Encoding", "deflate");
            wrapper = new DeflateServletResponseWrapper(httpRes);
        }

        chain.doFilter(request, wrapper);

        wrapper.getOutputStream().close();
    }

    private double getQuality(String header, String algo) {
        String[] parts = header.split(",");

        for (String p : parts) {
            p = p.trim();
            if (p.startsWith(algo)) {
                if (p.contains("q=")) {
                    return Double.parseDouble(p.split("q=")[1]);
                }
                return 1.0;
            }
        }
        return 0;
    }
}