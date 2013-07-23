
package com.uploader.services;


public interface CryptService {

    public String cryptPassword(String password, String salt);

    public String cryptFilename(String name);

    public String cryptCookiesPassword(String login, String passwordHash);

    public String getSalt(String passwordHash);
}
