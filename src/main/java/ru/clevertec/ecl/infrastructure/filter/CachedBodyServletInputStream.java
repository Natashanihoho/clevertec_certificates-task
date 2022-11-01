package ru.clevertec.ecl.infrastructure.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class CachedBodyServletInputStream extends ServletInputStream {

    private final InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this(new ByteArrayInputStream(cachedBody));
    }

    @SneakyThrows
    @Override
    public boolean isFinished() {
        return cachedBodyInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }

    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
}
