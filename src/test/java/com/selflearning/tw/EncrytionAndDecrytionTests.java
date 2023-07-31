package com.selflearning.tw;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class EncrytionAndDecrytionTests {

    @Test // https://www.baeldung.com/java-cipher-class
    void whenIsEncryptedAndDecrypted_thenDecryptedEqualsOriginal() throws Exception {
        String encryptionKeyString = "thisisa128bitkey";
        String originalMessage = "This is a secret message"; // 要被加密的字串
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // 執行加密動作
        byte[] encryptedMessageBytes = cipher.doFinal(originalMessage.getBytes());
        System.out.println(encryptedMessageBytes); // [B@69e1dd28
        // [-52, 46, -110, 80, -75, -85, -14, -38, -128,...
        System.out.println(Arrays.toString(encryptedMessageBytes));
        // zC6SULWr8tqA+0qPc5FFv6qljaaVB9GjTAGWWWSZW20=
        System.out.println(Base64.getEncoder().encodeToString(encryptedMessageBytes));

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // 執行解密動作
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);
        System.out.println(decryptedMessageBytes); // [B@be35cd9
        // [84, 104, 105, 115, 32, 105, 115, 32, 97, 32,...
        System.out.println(Arrays.toString(decryptedMessageBytes));
        System.out.println(new String(decryptedMessageBytes)); // This is a secret message

        assertThat(originalMessage).isEqualTo(new String(decryptedMessageBytes));
    }



    @Test
    void gogo() {
        String[] spiltSubPlate = spiltSubPlate("2ขญ8738288");
        System.out.println(Arrays.toString(spiltSubPlate));
    }


    public String[] spiltSubPlate(String plate) {

        Pattern pattern = Pattern.compile("[0-9]{2,5}");
        String[] plates = { "", "" };

        if (plate.matches("[0-9]+")) {
            if (plate.length() < 2){
                plates[0] = plate;
                System.out.println("0"+ Arrays.toString(plates));}
            else {
                plates[0] = plate.substring(0, 2);
                plates[1] = plate.substring(2, plate.length());
                System.out.println("1"+ Arrays.toString(plates));
            }
        } else if (plate.length() <= 3 && plate.matches(".*[0-9]{1}")) {
            plates[0] = plate.substring(0, plate.length() - 1);
            plates[1] = plate.substring(plate.length() - 1);
            System.out.println("2"+ Arrays.toString(plates));
        } else {
            Matcher matcher = pattern.matcher(plate);
            while (matcher.find()) {
                plates[1] = (matcher.group());
            }
            plates[0] = plate.substring(0, plate.indexOf(plates[1]));
            System.out.println("3"+ Arrays.toString(plates));
        }
        if (plates[0].equals("") && plates[1].equals("")) {
            plates[0] = plate;
            System.out.println("4"+ Arrays.toString(plates));
        }

        return plates;
    }

}
