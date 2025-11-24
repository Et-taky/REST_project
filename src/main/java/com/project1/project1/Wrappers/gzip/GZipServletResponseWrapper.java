package com.project1.project1.Wrappers.gzip;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {

    private GZipServletOutputStream gzipOutputStream;

    public GZipServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (gzipOutputStream == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
        }
        return gzipOutputStream;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (gzipOutputStream != null) {
            gzipOutputStream.flush();
        }
        super.flushBuffer();
    }
}