package com.uploader.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import com.uploader.services.CryptService;
import org.springframework.stereotype.Repository;
import sun.misc.BASE64Encoder;

@Repository("cryptService")
public class CryptServiceImpl implements CryptService {

    private final static int saltLength = 9;

    @Override
    public String cryptPassword(String password, String salt) {
        return generateHash(password, salt);
    }

    @Override
    public String cryptFilename(String name) {
        return generateHash(name, null);
    }

    private static String generateHash(String text, String salt) {
        MessageDigest m, sha;
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] saltb;
        if (salt != null) {
            saltb = salt.getBytes();
        }
        byte[] textb = text.getBytes();
        String finalStr = "";

        try {
            m = MessageDigest.getInstance("MD5");
            sha = MessageDigest.getInstance("SHA-1");

            if (salt == null) {
                m.update(UUID.randomUUID().toString().getBytes());
                saltb = m.digest();
                saltb = encoder.encodeBuffer(saltb).substring(0, saltLength).getBytes();
            } else {
                saltb = salt.getBytes();
            }

            byte[] res = new byte[saltLength + text.length()];
            for (int x = 0; x < res.length; x++) {
                if (x < saltLength) {
                    res[x] = saltb[x];
                } else {
                    res[x] = textb[x - saltLength];
                }
            }
            sha.update(res);
            finalStr = encoder.encodeBuffer(sha.digest());
            finalStr = (new String(saltb)) + finalStr;
            finalStr = finalStr.replace("\n", "");
            finalStr = finalStr.replace("\r", "");
            finalStr = finalStr.replace("+", "g");
            return finalStr;
        } catch (NoSuchAlgorithmException ex) {
//			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getSalt(String passwordHash) {
        if (null != passwordHash && !passwordHash.isEmpty()) {
            return passwordHash.substring(0, saltLength);
        } else {
            return null;
        }
    }

    @Override
    public String cryptCookiesPassword(String login, String passwordHash) {

        byte[] pass;
        MessageDigest m;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        m.reset();
        m.update(login.getBytes());
        m.update(":".getBytes());

        if (null == passwordHash || passwordHash.isEmpty()) {
            return null;
        }

        m.update(passwordHash.getBytes());
        pass = m.digest();
        pass = encoder.encodeBuffer(pass).substring(0, saltLength).getBytes();
        return new String(pass);
    }
}
