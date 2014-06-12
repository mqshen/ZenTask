package util

/**
 * Created by GoldRatio on 4/10/14.
 */
import java.io.{InputStreamReader, InputStream}
import java.security.spec.{RSAPrivateKeySpec, RSAPublicKeySpec, X509EncodedKeySpec}
import java.security._
/*
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.{PEMKeyPair, PEMParser}
import org.bouncycastle.crypto.util.{PrivateKeyFactory, PublicKeyFactory}
import org.bouncycastle.crypto.params.RSAKeyParameters

/**
 * RSA signing & verification
 */
object Key {
  Security.addProvider(new BouncyCastleProvider)
  val keyType = "RSA"
  val sigType = "SHA1withRSA"

  /** Load a PEM-encoded RSA public key from the given input stream */
  def pub(input: InputStream): Option[PublicKey] = {
    Option {
      new PEMParser(new InputStreamReader(input)).readObject()
    } collect { case spki: SubjectPublicKeyInfo =>
      PublicKeyFactory.createKey(spki)
    } collect { case p: RSAKeyParameters =>
      KeyFactory.getInstance(keyType).generatePublic(
        new RSAPublicKeySpec(p.getModulus, p.getExponent))
    }
  }

  /** Load a PEM-encoded RSA private key from the given input stream */
  def priv(input: InputStream): Option[PrivateKey] = {
    Option {
      new PEMParser(new InputStreamReader(input)).readObject()
    } collect { case kp: PEMKeyPair =>
      PrivateKeyFactory.createKey(kp.getPrivateKeyInfo)
    } collect { case pk: RSAKeyParameters =>
      KeyFactory.getInstance(keyType).generatePrivate(
        new RSAPrivateKeySpec(pk.getModulus, pk.getExponent))
    }
  }

  /** Verify a SHA1+RSA signature with the given public key */
  def verify(key: PublicKey, data: Array[Byte], signature: Array[Byte]): Boolean = {
    val sig = Signature getInstance sigType
    sig.initVerify(key)
    sig.update(data)
    sig.verify(signature)
  }

  /** Construct a SHA1+RSA signature with the given private key */
  def sign(key: PrivateKey, data: Array[Byte]): Array[Byte] = {
    val sig = Signature getInstance sigType
    sig.initSign(key)
    sig.update(data)
    sig.sign()
  }

}
*/