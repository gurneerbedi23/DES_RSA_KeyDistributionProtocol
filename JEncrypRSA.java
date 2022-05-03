package coe817lab2;
import java.util.Scanner;
import javax.crypto.Cipher;
import java.security.*;
public class JEncrypRSA {
    public static void main (String[] args) throws Exception {
    
        //Allow the user to enter the message to be encrypted
        Scanner in = new Scanner (System.in);
        System.out.print("Enter the message to be encrypted: ");
        String plaintext = in.nextLine();
        
        byte[] message = plaintext.getBytes(); 
        
        PublicKey public_key;
        PrivateKey private_key;
        
        KeyPairGenerator RSAKey = KeyPairGenerator.getInstance("RSA");
        RSAKey.initialize(2048);
        KeyPair RSAkeypair = RSAKey.generateKeyPair();
        //get the public and private keys
        public_key = RSAkeypair.getPublic();
        private_key = RSAkeypair.getPrivate();
        
       
       Cipher cipherRSA = Cipher.getInstance("RSA");
       cipherRSA.init(cipherRSA.ENCRYPT_MODE,public_key);
       byte [] encryptedtext = cipherRSA.doFinal(message);
       String encrypted = new String(encryptedtext);
       System.out.println("Encrypted Message: "+ encrypted);
       
       cipherRSA.init(cipherRSA.DECRYPT_MODE,private_key);
       byte [] decryptedtext = cipherRSA.doFinal(encryptedtext);
       String decrypted = new String(decryptedtext);
       System.out.println("Decrypted Message: "+ decrypted);
       
    }
}
