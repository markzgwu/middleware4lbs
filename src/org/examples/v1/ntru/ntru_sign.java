package org.examples.v1.ntru;

import net.sf.ntru.sign.NtruSign;
import net.sf.ntru.sign.SignatureKeyPair;
import net.sf.ntru.sign.SignatureParameters;

public class ntru_sign {
	NtruSign ntru = new NtruSign(SignatureParameters.TEST157);
    public boolean sign(String msg) {
        // create an signature key pair
        SignatureKeyPair kp = ntru.generateKeyPair();
        
        // sign the message with the private key created above
        byte[] sig = ntru.sign(msg.getBytes(), kp);
        
        // verify the signature with the public key created above
        boolean valid = ntru.verify(msg.getBytes(), sig, kp.getPublic());
        return valid;
    }	
}
