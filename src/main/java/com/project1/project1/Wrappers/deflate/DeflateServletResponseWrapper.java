package com.project1.project1.Wrappers.deflate;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

public class DeflateServletResponseWrapper extends HttpServletResponseWrapper {

    private DeflateServletOutputStream deflateOutputStream;

    public DeflateServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (deflateOutputStream == null) {
            deflateOutputStream = new DeflateServletOutputStream(getResponse().getOutputStream());
        }
        return deflateOutputStream;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (deflateOutputStream != null) {
            deflateOutputStream.flush();
        }
        super.flushBuffer();
    }
}