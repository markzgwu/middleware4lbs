package org.examples.v1.ntru;

import net.sf.ntru.encrypt.*;
import net.sf.ntru.sign.*;

public class ntru_test {
    public static void main(String[] args) {
        encrypt();
        System.out.println();
        sign();
    }

    public static void encrypt() {
        System.out.println("NTRU encryption");
        
        // create an instance of NtruEncrypt with a standard parameter set
        //EncryptionParameters encpara = EncryptionParameters.APR2011_743_FAST;
        EncryptionParameters encpara = EncryptionParameters.APR2011_439_FAST;
        
        NtruEncrypt ntru = new NtruEncrypt(encpara);
        System.out.println("N="+encpara.N+";q="+encpara.q);
        
        // create an encryption key pair
        EncryptionKeyPair kp = ntru.generateKeyPair();

        //String msg = "The quick brown fox";
        //String msg = "1";
        String msg = "The quick brown fox The quick brown fox The quick brown fox The q";
        System.out.println(msg.length());
        System.out.println("  Before encryption: " + msg);

        // encrypt the message with the public key created above
        byte[] enc = ntru.encrypt(msg.getBytes(), kp.getPublic());
        
        System.out.println(enc.length);
        //byte[] enc1 = ntru.encrypt(msg.getBytes(), kp.getPublic());
        // decrypt the message with the private key created above

        byte[] dec = ntru.decrypt(enc, kp);

        // print the decrypted message
        System.out.println("  After decryption:  " + new String(dec));
    }

    private static void sign() {
    	String msg = "The quick brown fox";
    	
    	System.out.println("  Signature valid? " + sign(msg));
    	
    }
    
    public static boolean sign(String msg) {
        //System.out.println("NTRU signature");
        
        // create an instance of NtruSign with a test parameter set
        NtruSign ntru = new NtruSign(SignatureParameters.TEST157);
        
        // create an signature key pair
        SignatureKeyPair kp = ntru.generateKeyPair();

        //String msg = "The quick brown fox";
        //System.out.println("  Message: " + msg);
        
        // sign the message with the private key created above
        byte[] sig = ntru.sign(msg.getBytes(), kp);
        
        // verify the signature with the public key created above
        boolean valid = ntru.verify(msg.getBytes(), sig, kp.getPublic());
        return valid;
        //System.out.println("  Signature valid? " + valid);
    }    
}
