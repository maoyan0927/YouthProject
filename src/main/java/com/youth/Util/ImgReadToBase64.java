package com.youth.Util;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImgReadToBase64 {
    public static String readImage(String path) {
        byte[] fileByte = null;
        try {
            File file = new File(path);
            fileByte = Files.readAllBytes(file.toPath());
            System.out.println("data:image/png;base64," + Base64.encodeBase64String(fileByte));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + Base64.encodeBase64String(fileByte);
    }
}
