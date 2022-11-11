package com.example.board_springboot.cryptoTest;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordTest {


    @Test
    public void test() {
        String originalPassword = "$2a$10$T0jo84kkLu/xBrZwJX0xmOs/U6jHkx5d5MLv4fdLiFUYHVNlasjEq";

        String sha256HexPassword = DigestUtils.sha256Hex(originalPassword);
        String sha512HexPassword = DigestUtils.sha512Hex(originalPassword);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String BcryptEncodedPassword = passwordEncoder.encode(originalPassword);

        System.out.println("sha256HexPassword: " + sha256HexPassword);
        System.out.println("sha512HexPassword: " + sha512HexPassword);
        System.out.println("BcryptEncodedPassword: "+ BcryptEncodedPassword);

        boolean BcryptEncodedPasswordMatches = passwordEncoder.matches(originalPassword, BcryptEncodedPassword);
        System.out.println("BcryptEncodedPasswordMatches: "+ BcryptEncodedPasswordMatches);

        boolean BcryptEncodedPasswordMatchesTest = passwordEncoder.matches("dsg1!", "$2a$10$T0jo84kkLu/xBrZwJX0xmOs/U6jHkx5d5MLv4fdLiFUYHVNlasjEq");
        System.out.println("BcryptEncodedPasswordMatchesTest: " + BcryptEncodedPasswordMatchesTest);

    }

}
