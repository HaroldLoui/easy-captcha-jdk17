package com.wf.captcha.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * <p>Date: 2022-05-07</p>
 * <p>文件操作工具类，此类源码从org.apache.commons.io.FileUtils中复制</p>
 *
 * @author zrh 455741807@qq.com
 */
public class FileUtil {

    /**
     * 默认缓冲区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * 文件结束标志位
     */
    public static final int EOF = -1;

    /**
     * 文件流复制
     *
     * @param inputStream 输入流
     * @param file        文件
     * @throws IOException e
     */
    public static void copyToFile(final InputStream inputStream, final File file) throws IOException {
        try (OutputStream out = openOutputStream(file)) {
            copy(inputStream, out);
        }
    }

    /**
     * 打开文件输出流
     *
     * @param file 文件
     * @return FileOutputStream
     * @throws IOException e
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * 打开文件输出流
     *
     * @param file   文件
     * @param append 是否追加
     * @return FileOutputStream
     * @throws IOException e
     */
    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        Objects.requireNonNull(file, "file");
        if (file.exists()) {
            requireFile(file, "file");
            requireCanWrite(file, "file");
        } else {
            createParentDirectories(file);
        }
        return new FileOutputStream(file, append);
    }

    /**
     * 判断是否是文件，是则返回该文件，否则抛出异常信息
     *
     * @param file 文件
     * @param name 文件名
     * @return
     */
    private static File requireFile(final File file, final String name) {
        Objects.requireNonNull(file, name);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Parameter '" + name + "' is not a file: " + file);
        }
        return file;
    }

    /**
     * 判断文件是否可写
     *
     * @param file 文件
     * @param name 文件名
     */
    private static void requireCanWrite(final File file, final String name) {
        Objects.requireNonNull(file, "file");
        if (!file.canWrite()) {
            throw new IllegalArgumentException("File parameter '" + name + " is not writable: '" + file + "'");
        }
    }

    /**
     * 创建文件父目录
     *
     * @param file 文件
     * @return 目录
     * @throws IOException e
     */
    public static File createParentDirectories(final File file) throws IOException {
        return mkdirs(getParentFile(file));
    }

    /**
     * 创建文件夹
     *
     * @param directory 文件目录
     * @return 文件夹
     * @throws IOException e
     */
    private static File mkdirs(final File directory) throws IOException {
        if ((directory != null) && (!directory.mkdirs() && !directory.isDirectory())) {
            throw new IOException("Cannot create directory '" + directory + "'.");
        }
        return directory;
    }

    /**
     * 获取父文件（目录）
     *
     * @param file 文件
     * @return 文件（目录）
     */
    private static File getParentFile(final File file) {
        return file == null ? null : file.getParentFile();
    }

    /**
     * 将输入流写到输出流里
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return 写入数量
     * @throws IOException e
     */
    public static int copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final long count = copyLarge(inputStream, outputStream);
        if (count > Integer.MAX_VALUE) {
            return EOF;
        }
        return (int) count;
    }

    /**
     * 将输入流写到输出流里
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @return 写入数量
     * @throws IOException e
     */
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 将输入流写到输出流里
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param bufferSize   缓冲区大小
     * @return 写入数量
     * @throws IOException e
     */
    public static long copy(final InputStream inputStream, final OutputStream outputStream, final int bufferSize) throws IOException {
        return copyLarge(inputStream, outputStream, byteArray(bufferSize));
    }

    /**
     * 将输入流写到输出流里
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param buffer       缓冲区
     * @return 写入数量
     * @throws IOException e
     */
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] buffer) throws IOException {
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long count = 0;
        int n;
        while (EOF != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 生成指定大小的byte数组
     *
     * @param size 大小
     * @return byte数组
     */
    public static byte[] byteArray(final int size) {
        return new byte[size];
    }
}
