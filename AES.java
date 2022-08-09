import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;

public class AES {
    private SecretKey key;
    private final int keySize = 128;
    private final int T_LEN = 128;
    private Cipher cipherEncryption;

    public void init() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(keySize);
        key = generator.generateKey();
    }

    public String encrypt(String msg) throws Exception{
        byte [] msgInBytes = msg.getBytes();
        cipherEncryption = Cipher.getInstance("AES/GCM/NoPadding");
        cipherEncryption.init(Cipher.ENCRYPT_MODE, key);
        byte [] ecryptedBytes = cipherEncryption.doFinal(msgInBytes);
        return encode(ecryptedBytes);
    }

    public String decrypt(String encryptedMsg) throws Exception{
        byte [] msginbytes = decode(encryptedMsg);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, cipherEncryption.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte [] decryptedBytes = decryptionCipher.doFinal(msginbytes);
        return new String(decryptedBytes);

    }
    

    private String encode(byte [] data){
        return Base64.getEncoder().encodeToString(data);
    }

    private byte [] decode(String data){
        return Base64.getDecoder().decode(data);
    }


    public static void main(String[] args) {
        try {
            AES aes = new AES();
            aes.init();

            Scanner sc = new Scanner(System.in);
            System.out.println("Please, Enter the message: ");
            String message = sc.nextLine();

            String encryptedMessage = aes.encrypt(message);
            String decryptedMessage = aes.decrypt(encryptedMessage);

            System.err.println("Encrypted Message: " + encryptedMessage);
            System.err.println("Decrypted Message: " + decryptedMessage);


        } catch (Exception e) {}
    }

}
