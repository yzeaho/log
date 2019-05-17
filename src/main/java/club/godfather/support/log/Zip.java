package club.godfather.support.log;

import android.support.annotation.IntRange;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class Zip {

    /**
     * 打包文件夹
     *
     * @param sourceDir 需要打包的文件夹
     * @param zipFile   打包后的文件
     */
    static void zip(String sourceDir, String zipFile) throws IOException {
        zip(new File(sourceDir), new File(zipFile));
    }

    /**
     * 打包文件夹
     *
     * @param sourceDir 需要打包的文件夹
     * @param zipFile   打包后的文件
     */
    static void zip(File sourceDir, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            byte[] buffs = new byte[1024];
            out = new ZipOutputStream(new FileOutputStream(zipFile, false));
            eachItem(null, sourceDir, out, buffs);
        } finally {
            closeQuietly(out);
        }
    }

    /**
     * 打包文件夹
     *
     * @param sourceDir 需要打包的文件夹
     * @param zipFile   打包后的文件
     * @param level     压缩等级
     */
    static void zip(File sourceDir, File zipFile, @IntRange(from = 0, to = 9) int level) throws IOException {
        ZipOutputStream out = null;
        try {
            byte[] buffs = new byte[1024];
            out = new ZipOutputStream(new FileOutputStream(zipFile, false));
            out.setLevel(level);
            eachItem(null, sourceDir, out, buffs);
        } finally {
            closeQuietly(out);
        }
    }

    private static void eachItem(String baseDir, File dir, ZipOutputStream out, byte[] bufs) throws IOException {
        File[] files = dir.listFiles();
        String prex = "";
        if (baseDir != null) {
            prex = baseDir + "/";
        }
        if (files == null || files.length == 0) {
            out.putNextEntry(new ZipEntry(prex));
            return;
        }

        String name;
        ZipEntry entry;
        for (File file : files) {
            name = prex + file.getName();
            if (!file.isFile()) {
                eachItem(name, file, out, bufs);
            } else {
                entry = new ZipEntry(name);
                entry.setTime(file.lastModified());
                out.putNextEntry(entry);
                write2zip(out, file, bufs);
            }
        }
    }

    private static void write2zip(ZipOutputStream out, File file, byte[] buffs) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            int len;
            while ((len = in.read(buffs)) != -1) {
                out.write(buffs, 0, len);
            }
        } finally {
            closeQuietly(in);
        }
    }

    private static void write2file(ZipInputStream in, File file, byte[] buffer) throws IOException {
        FileOutputStream out = null;
        try {
            int count;
            out = new FileOutputStream(file, false);
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        } finally {
            closeQuietly(out);
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}