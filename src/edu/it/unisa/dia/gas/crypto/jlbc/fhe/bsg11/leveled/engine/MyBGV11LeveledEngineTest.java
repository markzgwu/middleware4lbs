package edu.it.unisa.dia.gas.crypto.jlbc.fhe.bsg11.leveled.engine;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jlbc.util.io.IOUtils;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.engine.BGV11LeveledEngine;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators.BGV11LeveledKeyPairGenerator;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.generators.BGV11LeveledKeyPairGeneratorParametersGenerator;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledAddParameters;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledMulParameters;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledPublicKeyParameters;
import edu.it.unisa.dia.gas.crypto.jlbc.fhe.bgv11.leveled.params.BGV11LeveledSecretKeyParameters;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class MyBGV11LeveledEngineTest {

    protected BGV11LeveledEngine engine;

    @Before
    public void init() {
        engine = new BGV11LeveledEngine();
    }

    @Test
    public void testBESBGV11Engine() {
    	//final int module = 71;//71是素数,这个模要求是素数吗?
    	//final int module = 2;//boolean logic 速度很慢,不知道原因,似乎整数越大,运算越快.尤其是生成秘要的过程很长.
    	final int module = 256;//一个byte长度 8位
    	//final int module = 65536;//一个word长度 16位 似乎死循环了
    	//final int module = 1024;
    	//final int module = 2048;
    	AsymmetricCipherKeyPair keyPair = genKey(new SecureRandom(), 64, 5, 4, BigInteger.valueOf(module), 8);

        //AsymmetricCipherKeyPair keyPair = genKey(new SecureRandom(), 64, 5, 4, BigInteger.valueOf(71), 8);

        BGV11LeveledPublicKeyParameters pk = (BGV11LeveledPublicKeyParameters) keyPair.getPublic();        
        BGV11LeveledSecretKeyParameters sk = (BGV11LeveledSecretKeyParameters) keyPair.getPrivate();

        //int a = 30, b = 30, c = 20, d = 30, zero = 0;
        //int a = 10, b = 12, c = 2, d = 3, zero = 0;
        //int a = 30, b = 48, c = 2, d = 3, zero = 0;
        int a = 30, b = 40, c = 10, d = 10, zero = 0;
        
        byte[] ct_A = enc(pk, a);
        Assert.assertEquals(a%module, dec(sk, ct_A));

        byte[] ct_B = enc(pk, b);
        Assert.assertEquals(b%module, dec(sk, ct_B));

        byte[] ct_C = enc(pk, c);
        Assert.assertEquals(c%module, dec(sk, ct_C));

        byte[] ct_D = enc(pk, d);
        Assert.assertEquals(d%module, dec(sk, ct_D));

        byte[] ct_Zero = enc(pk, zero);
        Assert.assertEquals(zero%module, dec(sk, ct_Zero));

        Assert.assertEquals(c%module, dec(sk, add(pk, ct_Zero, add(pk, ct_Zero, ct_C))));

        byte[] ct_AAddB = add(pk, ct_A, ct_B);
        int test1 = ((a+b)% module);
        int test2 = dec(sk, ct_AAddB);
        Assert.assertEquals(test1, test2);
        System.out.println("(a+b) % module:"+test1+";a+b Dec:"+test2);
        //Assert.assertEquals((a + b), dec(sk, ct_AAddB));
        //System.out.println("(a+b) % module:"+((a+b)% module)+";a+b Dec:"+dec(sk, ct_AAddB));
        

        byte[] ct_AMulB = mul(pk, ct_A, ct_B);
        Assert.assertEquals((a * b) % module, dec(sk, ct_AMulB));

        byte[] ct_AAddB_Add_AMulB = add(pk, ct_AAddB, ct_AMulB);
        Assert.assertEquals(((a + b) + (a * b)) % module, dec(sk, ct_AAddB_Add_AMulB));

        byte[] ct_AAddB_Add_AMulB_Add_C = add(pk, ct_AAddB_Add_AMulB, ct_C);
        Assert.assertEquals(( (a + b) + (a * b) + c ) % module, dec(sk, ct_AAddB_Add_AMulB_Add_C));

        byte[] ct_AAddB_Add_AMulB_Add_C_MUL_D = mul(pk, ct_AAddB_Add_AMulB_Add_C, ct_D);
        Assert.assertEquals(( (((a + b) + (a * b)) + c) * d ) % module, dec(sk, ct_AAddB_Add_AMulB_Add_C_MUL_D));
    }

    private byte[] add(BGV11LeveledPublicKeyParameters pk, byte[] ctA, byte[] ctB) {
        engine.init(true, new BGV11LeveledAddParameters(pk));
        byte[] message = IOUtils.toByteArray(ctA, ctB);

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.add " + (end - start));
        }
    }

    private byte[] mul(BGV11LeveledPublicKeyParameters pk, byte[] ctA, byte[] ctB) {
        engine.init(true, new BGV11LeveledMulParameters(pk));
        byte[] message = IOUtils.toByteArray(ctA, ctB);

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.mul " + (end - start));
        }
    }

    protected AsymmetricCipherKeyPair genKey(SecureRandom random, int strength, int L, int d, BigInteger t, int sigma) {
        BGV11LeveledKeyPairGenerator keyPairGenerator = new BGV11LeveledKeyPairGenerator();
        keyPairGenerator.init(
                new BGV11LeveledKeyPairGeneratorParametersGenerator(
                        random, strength, L, d, t, sigma
                ).generate()
        );
        long start = System.currentTimeMillis();
        try {
            return keyPairGenerator.generateKeyPair();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.genKey " + (end - start));
        }
    }


    protected byte[] enc(BGV11LeveledPublicKeyParameters pk, int value) {
        engine.init(true, pk);
        byte[] message = pk.getInputLevelParameters().getRq().newElement().set(value).toBytes();

        long start = System.currentTimeMillis();
        try {
            return engine.processBlock(message, 0, message.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("BGV11LeveledEngineTest.enc " + (end - start));
        }
    }

    protected int dec(BGV11LeveledSecretKeyParameters sk, byte[] ct) {
        engine.init(false, sk);

        long start = System.currentTimeMillis();
        long end = 0;
        try {
            byte[] message = engine.processBlock(ct, 0, ct.length);

            end = System.currentTimeMillis();

            Element result = sk.getParametersAt(engine.getDecryptionLevel()).getRq().newElement();
            result.setFromBytes(message);

            return result.toBigInteger().intValue();
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("BGV11LeveledEngineTest.dec " + (end - start));
        }
    }

}
