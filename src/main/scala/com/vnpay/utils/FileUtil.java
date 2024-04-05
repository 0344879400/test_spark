/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpay.utils;

import java.io.*;
import java.util.Properties;

/**
 * @author nam
 */
public class FileUtil {
    public static void writeBytesToFile(byte[] key, String file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        out.write(key);
        out.close();
    }

    public static byte[] readBytesFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > 2147483647L) {
        }
        byte[] bytes = new byte[((int) length)];
        int offset = 0;
        while (offset < bytes.length) {
            int numRead = is.read(bytes, offset, bytes.length - offset);
            if (numRead < 0) {
                break;
            }
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Key File Error: Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

    public static String fileToString(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        try {

            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } finally {
            br.close();
        }

        return sb.toString();
    }

    public static Properties readConfig(String path) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}