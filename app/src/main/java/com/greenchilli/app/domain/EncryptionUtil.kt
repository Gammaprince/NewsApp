import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object EncryptionUtil {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"
    private const val KEY_ALIAS = "MyKeyAlias"

    fun generateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEYSTORE
            )

            val keyGenSpec = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build()

            keyGenerator.init(keyGenSpec)
            keyGenerator.generateKey()
        }

        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    fun encryptData(data: String, secretKey: SecretKey): EncryptedData {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        // Extract the IV from the Cipher after it's initialized
        val iv = cipher.parameters.getParameterSpec(IvParameterSpec::class.java).iv

        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val encryptedText = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)

        return EncryptedData(encryptedText, iv)
    }

    fun decryptData(encryptedData: EncryptedData, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // Provide the IV for decryption
        val ivSpec = IvParameterSpec(encryptedData.iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData.encryptedText, Base64.DEFAULT))
        return String(decryptedBytes, Charsets.UTF_8)
    }
}

data class EncryptedData(val encryptedText: String, val iv: ByteArray)
