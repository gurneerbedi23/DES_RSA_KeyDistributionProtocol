package coe817lab2;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
class JEncrypDES{
    public static void main(String[] args) {
        try {
         //Generate a DES Key
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        SecretKey DesKey = keygen.generateKey();
        
         //create and initialize the cipher for encryption
        Cipher cipherDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipherDES.init(Cipher.ENCRYPT_MODE, DesKey);
        
         //Allow the user to enter message to be encrypted
        Scanner in = new Scanner (System.in);
        System.out.print("Enter the message to be encrypted: ");
        String plaintext = in.nextLine();
        
        byte[] message = plaintext.getBytes(); 

        //encrypt the text
        byte[] encryptedtext = cipherDES.doFinal(message);
        String encrypted;
            encrypted = Base64.getEncoder().encodeToString(encryptedtext);
        System.out.println("The message to be encrypted: "+ plaintext);
        System.out.println("Encrypted message: "+ encrypted);
        
         //intilize the cipher for decryption and decrypt text
        cipherDES.init(Cipher.DECRYPT_MODE,DesKey);
        byte[] decryptedtext = cipherDES.doFinal(encryptedtext);
        String decrypted =new String(decryptedtext);
        System.out.println("Decrypted Message: "+ decrypted);
        
        }
        catch(NoSuchPaddingException e){
         e.printStackTrace();
    }
         catch(NoSuchAlgorithmException e){
         e.printStackTrace();
    }
       
         catch(InvalidKeyException e){
         e.printStackTrace();
    }
        catch(BadPaddingException e){
         e.printStackTrace();
    }
         catch(IllegalBlockSizeException e){
         e.printStackTrace();
    }
      
    }
}
