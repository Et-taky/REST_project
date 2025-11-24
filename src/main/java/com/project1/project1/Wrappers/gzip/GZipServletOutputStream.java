package com.project1.project1.Wrappers.gzip;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipServletOutputStream extends ServletOutputStream {

    private final GZIPOutputStream gzipOutputStream;

    public GZipServletOutputStream(ServletOutputStream outputStream) throws IOException {
        this.gzipOutputStream = new GZIPOutputStream(outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        gzipOutputStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        gzipOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        gzipOutputStream.close();
    }

    // === Obligatoire pour Jakarta ===

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // Not implemented (sync mode)
    }
}