package com.selflearning.tw.security.rsa.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class FileUtils {

    public void writeKeyToFile(byte[] bytesToFile) {
        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            // getEncoded() returns the key content in its primary encoding format.
            fos.write(bytesToFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void readKeyFromFile() {
        try {
            // 1. load the content as a byte array
            File publicKeyFile = new File("public.key");
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

            // 2. then use the KeyFactory to recreate the actual instance
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            keyFactory.generatePublic(publicKeySpec);

        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }
}
