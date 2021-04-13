package org.sonan.framework.container.servlet;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class CachedServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream buffer;

    public CachedServletInputStream(byte[] contents) {
        this.buffer = new ByteArrayInputStream(contents);
    }

    @Override
    public int read() {
        return this.buffer.read();
    }

    @Override
    public boolean isFinished() {
        return this.buffer.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // nothing to do.
        // TODO: not support Async Servlet NOT YET.
    }
}
