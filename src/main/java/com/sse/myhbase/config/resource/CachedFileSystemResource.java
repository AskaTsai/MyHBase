package com.sse.myhbase.config.resource;

import org.springframework.core.io.FileSystemResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Cai Shunda
 * @description: 重写FileSystemResource,
 *                FileSystemResource是以文件的绝对路径方式进行访问
 * @date: Created in 22:51 2017/11/12
 * @modified by:
 */
public class CachedFileSystemResource extends FileSystemResource{

    private byte[] data;

    public CachedFileSystemResource(File file) {
        super(file);
    }

    public CachedFileSystemResource(String path) {
        super(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (data == null) {
            InputStream inputStream =super.getInputStream();
            List<Byte> temp = new ArrayList<>();
            for (int i = inputStream.read(); i != -1; i = inputStream.read()) {
                temp.add((byte) i);
            }
            data = new byte[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                data[i] = temp.get(i);
            }
        }
        return new ByteArrayInputStream(data);
    }
}
