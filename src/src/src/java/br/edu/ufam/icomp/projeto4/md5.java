/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author bruna
 */
public class md5 {
    private static String md5(String senha) {

        MessageDigest md = null;       
        
        try {
            
            md = MessageDigest.getInstance("MD5");
            
        } catch (NoSuchAlgorithmException e) {
            
            e.printStackTrace();
            
        }

        String seed = "M@h 0i3¡ 0lh4 @ s3m3nt1nh@ :D";        
        String password = seed+senha;
        
        BigInteger hash = new BigInteger(1, md.digest( password.getBytes()));
        String sen = hash.toString(16);
        return sen;
    }
    
    public static void main(String[] args) {
       //String senha = "dcc2010";
        String senha = "123456";
        System.out.println(">>>>>>> Senha " + senha + " = " + md5(senha));
    }
}
