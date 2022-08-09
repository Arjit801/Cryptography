import java.util.*;
import java.math.BigInteger;
import java.lang.*;

class RSA {
    public static void main(String[] args) {
        Random rand1 = new Random(System.currentTimeMillis());
        Random rand2 = new Random(System.currentTimeMillis()*10);

        int pubkey = 2;

        BigInteger p = BigInteger.probablePrime(32, rand1);
        BigInteger q = BigInteger.probablePrime(32, rand2);

        // multiplying p and q
        BigInteger n = p.multiply(q);

        BigInteger p_1 = p.subtract(new BigInteger("1"));
        BigInteger q_1 = q.subtract(new BigInteger("1"));

        BigInteger z = p_1.multiply(q_1);

        while (true) {
            BigInteger GCD = z.gcd(new BigInteger("" + pubkey));

            if(GCD.equals(BigInteger.ONE)){
                break;
            }
            pubkey++;
        }


        BigInteger newPubkey = new BigInteger("" + pubkey);
        BigInteger prvkey = newPubkey.modInverse(z);
        System.out.println("Public key " + pubkey + " , " + n);
        System.out.println("Private key " + prvkey + " , " + n);

        //  RSA encryption and decryption

        Scanner sc = new Scanner(System.in);
            System.out.println("Enter the message to be encrypted: ");
            String msg = sc.nextLine();

            byte [] bytes = msg.getBytes();
            
            for (int i = 0; i < msg.length(); i++){
                int asciiVal = bytes[i];
                BigInteger val = new BigInteger("" + asciiVal);
              
                // int ascii = bytes[i];
                System.out.println("Encrypting the " + i + "th byte of message: ");
                BigInteger cipherText = val.modPow(newPubkey, n);
                System.out.println("Cipher Text: " + cipherText);
                

                System.out.println("Dencrypting the " + i + "th byte of cipher text: ");
                BigInteger plainText = cipherText.modPow(prvkey, n);
                int i_plainText = plainText.intValue();

                System.out.println("Plain Text: " + Character.toString((char)i_plainText));
                
            }
        

    }
}
