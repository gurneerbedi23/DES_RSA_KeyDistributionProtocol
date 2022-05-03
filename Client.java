package coe817lab2;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.spec.InvalidKeySpecException;
public class Client {
 
    public static void main(String[] args) throws Exception 
    {
        byte[] key,encryptedtext,decryptedtext = null, message = null;
        Cipher DEScipher = null;
        SecretKey DESKey;
        SecretKeyFactory keyfactory;
        String plaintext;
        
        String host = "localhost";
        int port = 7999;
        String id = "INITIATOR A";//idenity of A
        String km = "NETWORK SECURITY"; //master key 
        
        try {
        System.out.println("CLIENT side");
        key = km.getBytes();
        keyfactory = SecretKeyFactory.getInstance("DES");
        DESKey = keyfactory.generateSecret(new DESKeySpec(key));
        
        System.out.println("Connecting to host on port "+ port);
        Socket c = new Socket(host, port);
        System.out.println("Successfully connected to " + c.getRemoteSocketAddress());
        System.out.println("Client ID sent to Server: "  + id);
        DataOutputStream out = new DataOutputStream(c.getOutputStream());
        out.writeUTF(id);
        
        DataInputStream in = new DataInputStream(c.getInputStream());
        
          int length = in.readInt();
           
            if(length > 0) message = new byte[length];
            in.read(message,0,length);
            
          
             System.out.println("Encrypted Message Received from Server: " + 
                    new String(message));
            
            DEScipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       
            DEScipher.init(Cipher.DECRYPT_MODE, DESKey);
            decryptedtext = DEScipher.doFinal(message);
            System.out.println("Decrypted Message is: " + 
                    new String(decryptedtext) + "\n");
            
        String decrypted = new String(decryptedtext);
            String[] Arrdecrypted = decrypted.split("\\|");
            
             key =  (Arrdecrypted[0]+ " ").getBytes();
            keyfactory = SecretKeyFactory.getInstance("DES");
            DESKey = keyfactory.generateSecret(new DESKeySpec(key));
           
            encryptedtext = Arrdecrypted[2].getBytes();
      
            DEScipher.init(Cipher.ENCRYPT_MODE, DESKey);
            encryptedtext = DEScipher.doFinal(encryptedtext);
            
            System.out.println("The Session key is: " + Arrdecrypted[0]);
            System.out.println("The Host ID is: " + Arrdecrypted[2]);
            
            System.out.println("Sending Cipher to Host for plaintext --> " + (Arrdecrypted[2]) );
            
            out.writeInt(encryptedtext.length);
            out.write(encryptedtext);
            
            in.close();
            out.close();
            c.close(); 
           } catch(IOException | InvalidKeyException | NoSuchAlgorithmException |
                InvalidKeySpecException | IllegalBlockSizeException |
               BadPaddingException | NoSuchPaddingException ex) {
            System.out.println("There was an error.");
            ex.printStackTrace();
        } 
    }
} 
            
        
        
    