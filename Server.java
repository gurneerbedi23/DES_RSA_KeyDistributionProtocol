
package coe817lab2;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.*;
import javax.crypto.*;
public class Server extends Thread {
  
    public static void main(String[] args) throws Exception 
    {
      ServerSocket s;  
     byte[] key,encryptedtext,decryptedtext, message = null;
     SecretKey DESKey;
     String text;
     SecretKeyFactory keyfactory;
        String id = "RESPONDER B";
        String km = "NETWORK SECURITY";
        String ks = "RYERSON ";
        int port = 7999;
        Cipher DEScipher = null;
        
        try {
            System.out.println("SERVER side");
            //start socket for server
            s = new ServerSocket(port);
        s.setSoTimeout(500000);
        Socket server = s.accept();
        
        System.out.println("Successfully connected to " + server.getRemoteSocketAddress() + "\n");
        
        DataInputStream in = new DataInputStream(server.getInputStream());
        String cID = in.readUTF();
        System.out.println("Client ID received is: " + cID );
        
        //get key from master key
        key = km.getBytes();
        keyfactory = SecretKeyFactory.getInstance("DES");
        DESKey = keyfactory.generateSecret(new DESKeySpec(key));
        text = ks + "|" + cID + "|" + id;
        encryptedtext = text.getBytes();
        //encrypt message before sending to client
        DEScipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        DEScipher.init(Cipher.ENCRYPT_MODE,DESKey);
        encryptedtext = DEScipher.doFinal(encryptedtext);
        
        System.out.println("Sending Encrypted Message to Client: " + 
                    new String(encryptedtext)+ "\n");
        
      DataOutputStream val =
                new DataOutputStream(server.getOutputStream());
            val.writeInt(encryptedtext.length);
            val.write(encryptedtext);
            
            
             int length = in.readInt();
            //read encrypted message from client
            if(length > 0) message = new byte[length];
            in.read(message, 0, length);
           
            System.out.println("Recieved Encrypted Message from Client: " + 
                    new String(message) + "\n");
            
            // use session key RYERSON to decrypt client message
            key = ks.getBytes();
            keyfactory = SecretKeyFactory.getInstance("DES");
            DESKey = keyfactory.generateSecret(new DESKeySpec(key));
            
        
            DEScipher.init(Cipher.DECRYPT_MODE, DESKey);
            // decrypt the message
            decryptedtext = DEScipher.doFinal(message);
           
            System.out.println("Decrypted Message is: " + 
                    new String(decryptedtext) + "\n");
            
            in.close();
            val.close();
            server.close();
            
        }
        catch(SocketTimeoutException error){
            System.out.println("Socket timed out!");

        }catch (IOException | NoSuchAlgorithmException | 
                NoSuchPaddingException | InvalidKeyException | 
                InvalidKeySpecException | IllegalBlockSizeException 
                | BadPaddingException e) {  
           System.out.println("There was an error.");
           e.printStackTrace();
        }
    }
}