package cn.mapview

import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.EncryptUtils
import org.junit.Assert.*
import org.junit.Test
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun md5() {
        val password = "02EBEF8C56A7ADA3F1ED5E900E3143C0"
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(password.toByteArray())
            val digest = md.digest()

            // 将字节数组转换为十六进制字符串
            val sb = StringBuilder()
            for (b in digest) {
                sb.append(String.format("%02x", b.toInt() and 0xff))
            }
            println(sb)
            val encryptMD5ToString = EncryptUtils.encryptMD5ToString(password)
            println("encryptMD5ToString: $encryptMD5ToString")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }
}