package com.example.filmz_project;

import org.mindrot.jbcrypt.BCrypt;


public class Blowfish {
    private static int workload = 10;

    public static boolean checkPassword(String hashed_password, String password) {
        boolean password_verified = BCrypt.checkpw(password, hashed_password);
        return password_verified;
    }

    public static String Encrypt (String password_plaintext){
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
        return(hashed_password);
    }
}
