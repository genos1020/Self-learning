package com.selflearning.tw.security.rsa;

import com.selflearning.tw.security.rsa.utils.FileUtils;
import com.selflearning.tw.security.rsa.utils.RSAUtils;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
public class main {

    public static void main(String[] args) {
        RSAUtils rsaUtils = new RSAUtils();
        FileUtils fileUtils = new FileUtils();
        KeyPair pair = rsaUtils.genKeypair();

        // use the public key to encrypt the data and the private one for decrypting it.
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // (key) write to and read from file
        fileUtils.writeKeyToFile(publicKey.getEncoded());
        fileUtils.readKeyFromFile();

        //:: Working With Strings ::
        String secretMessage = "Baeldung secret message";
        // 公鑰加密
        log.info(" Public key to Encrypt...");
        String encryptedString = rsaUtils.encrypt(secretMessage, publicKey);
        System.out.println(encryptedString);
        // 私鑰解密
        log.info(" Private key to Decrypt...");
        String originalString = rsaUtils.decrypt(encryptedString, privateKey);
        System.out.println(secretMessage.equals(originalString));

    }
}
