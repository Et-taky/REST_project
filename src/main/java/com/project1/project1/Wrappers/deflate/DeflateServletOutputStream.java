package com.project1.project1.Wrappers.deflate;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

public class DeflateServletOutputStream extends ServletOutputStream {

    private final DeflaterOutputStream deflaterOutputStream;

    public DeflateServletOutputStream(ServletOutputStream outputStream) {
        this.deflaterOutputStream = new DeflaterOutputStream(outputStream);
    }

    @Override
    public void write(int b) throws IOException {
        deflaterOutputStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        deflaterOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        deflaterOutputStream.close();
    }

    // === Obligatoire Jakarta ===
    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // sync mode
    }
}