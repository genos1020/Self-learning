package com.selflearning.tw.security.rsa.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

@Slf4j
@Component
public class RSAUtils {

    public KeyPair genKeypair() {
        KeyPair pair = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            pair = generator.generateKeyPair();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return pair;
    }

    public String encrypt(String secretMessage, Key key){
        String encodedMessage = "";

        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes); // 執行加密動作

            // Now, our message is successfully encoded.
            // If we'd like to store it in a database or send it via REST API,
            // it would be more convenient to encode it with the Base64.
            // This way, the message will be more readable and easier to work with
            encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            // e.g. A/mj8BvNktaMWmMR2zYCCeq7frm9xxg54lr0lVA8sa5d0AA+HxiIPRl3HslzU433bSmgsMzu3+XMg
            // 9klGOlMpEOQJo6JgExY8U+oB/bzvfx1A5TLGzY1C88FHVHm+S61Z38HwfT4VZkaJeyR56+LJXybEnHqM0hE
            // eWhlieS7rvu1G+5SFoQf+ReXSH2x09GbNZpzxCC+iWAP2jJY4sTTfkh5OvMifNDGXKVsqhVh8BeLprU8bwi
            // is+ZA6h3YeG+WhI71Yw2ojKKDNHefJCS0dTTm4tipWiNzHm2ULlsVfh2KhxCYOPLwbs7xpPxJjVEBoEx6RQ
            // FrfezhOGy5v0MBeA==

        }catch (Exception e){
            log.error(e.getMessage());
        }

        return encodedMessage;
    }

    public String decrypt(String encryptedString, Key key) {
        String decryptedMessage = "";
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedString.getBytes(StandardCharsets.UTF_8));
            decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (Exception e){
            log.error(e.getMessage(), e); // 第二個e 相當於 e.printStackTrace()
        }
        return decryptedMessage;
    }
}
