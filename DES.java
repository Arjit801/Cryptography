import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DES {

    private final SecretKey key;
    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    private void init() throws Exception{
        encryptionCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptionCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(encryptionCipher.getIV()));
    }

    //  key generation
    public DES() throws Exception{
        this.key = generateKey();
        init();
    }

    public DES (SecretKey key) throws Exception{
        this.key = key;
        init();
    }
    
    public static SecretKey generateKey() throws Exception{
        return KeyGenerator.getInstance("DES").generateKey();
    }

    //  Encryption -------------->>>>>>>>>>>>>>>>>>>>>>>

    public byte [] encrypt(String msg) throws Exception{
        return encryptionCipher.doFinal(msg.getBytes());
    }


    // Decryption ------------------>>>>>>>>>>>>>>>>>>>>>
    public String decrypt(byte [] msg) throws Exception{
        return new String(decryptionCipher.doFinal(msg));
    }
 

    //  encoder and decoder---------------------->>>>>>>>>
    public static String encode(byte [] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte [] decode(String data){
        return Base64.getDecoder().decode(data);
    }


    //  main method------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>...
    public static void main(String[] args) throws Exception{

        

        System.out.println("Enter the message for DES encryption and decryption: ");
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();


        SecretKey key = DES.generateKey();
        System.out.println("Your Encryption and Decryption key is: ");
        System.out.println(encode(key.getEncoded()));
        System.out.println();
        

        DES des = new DES(key);
        
        

        // final String encryptedMsg = "";
        int a = -1;
        while(a != 0){
            System.out.println("Choose, '0' to end this program: ");
            System.out.println("Choose, '1' for Encryption:  ");
            System.out.println("Choose, '2' to get your plain text: ");
    
    
            Scanner sk = new Scanner(System.in);
            a = sk.nextInt();

            if(a == 1){
                String encryptedMsg = encode(des.encrypt(message));
                System.out.println("Encrypted Message is: " + encryptedMsg);
                // break;
            }
            else if(a == 2){
                System.out.println("Decrypted Message is: " + des.decrypt(decode(encode(des.encrypt(message)))));
                break;
            }
            else if(a == 0){
                System.out.println("You have entered '0' to finish the program, Thank You ");
            }
        }
        
        System.out.println("Program finished successfully");

    }
}


