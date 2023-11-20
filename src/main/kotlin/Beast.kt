package com.msktmi

import kotlin.text.Regex as Regex1

object Beast {
    private val bd = charArrayOf('嗷', '呜', '啊', '~')

    fun isBeast(content: String): Boolean {
        val regex = Regex1("^~呜嗷.*啊$")
        return regex.matches(content)
    }
    fun isTranslation(content: String): Boolean {
        val regex = Regex1("^翻译\\s.*")
        return regex.matches(content)
    }

    //字符串转兽语
    fun strToBeast(str: String): String {
        return "" + bd[3] + bd[1] + bd[0] + hexToBeast(strToHex(str)) + bd[2]
    }

    //兽语转字符串
    fun beastToStr(str: String): String {
        return hexToStr(beastToHex(str.substring(3, str.length - 1)))
    }

    //字符串转十六进制，不足4位补零
    private fun strToHex(str: String): String {
        val bytes = str.toCharArray()
        val stringBuilder = StringBuilder()
        for (c in bytes) {
            val hexB = StringBuilder(Integer.toHexString(c.code))
            while (hexB.length < 4) {
                hexB.insert(0, "0")
            }
            stringBuilder.append(hexB)
        }
        return stringBuilder.toString()
    }

    //兽语转十六进制
    private fun beastToHex(encode: String): String {
        val bfArr = encode.toCharArray()
        val bf = StringBuffer()
        var i = 0
        while (i <= bfArr.size - 2) {
            var pos1 = 0
            var pos2 = 0
            val c = bfArr[i]
            while (pos1 <= 3 && c != bd[pos1]) {
                pos1++
            }
            val c2 = bfArr[i + 1]
            while (pos2 <= 3 && c2 != bd[pos2]) {
                pos2++
            }
            var k = pos1 * 4 + pos2 - i / 2 % 16
            if (k < 0) {
                k += 16
            }
            bf.append(Integer.toHexString(k))
            i += 2
        }
        return bf.toString()
    }

    //十六进制字符串转字符串
    private fun hexToStr(dataStr: String): String {
        val stringBuffer = StringBuffer()
        var start = 0
        var end = 4
        while (end <= dataStr.length) {
            stringBuffer.append(dataStr.substring(start, end).toInt(16).toChar().toString())
            start += 4
            end += 4
        }
        return stringBuffer.toString()
    }

    //十六进制转兽语
    private fun hexToBeast(tf: String): String {
        val tfArr = tf.toCharArray()
        val beast = StringBuffer()
        for (i in tfArr.indices) {
            var k = Integer.valueOf(tfArr[i].toString(), 16) + i % 16
            if (k >= 16) {
                k -= 16
            }
            beast.append("" + bd[k / 4] + bd[k % 4])
        }
        return beast.toString()
    }

}