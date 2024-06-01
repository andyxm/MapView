package cn.mapview.huangchuan

/**
 * @author
 * data: 2024/5/30 14:37
 * desc:
 */
object HuangChuanManager {

    private var nameNumbers = mutableMapOf<Int, String>()

    fun getNameFromNumber(index: Int): String {
        if (nameNumbers.isNotEmpty()) {
            if (nameNumbers.contains(index)) {
                return nameNumbers[index]!!
            } else {
                return ""
            }
        } else {
            return ""
        }
    }

    fun updateName(index: Int, number: Int) {
        if (nameNumbers.isNotEmpty()) {
            nameNumbers[index] = "${nameNumbers[index]}/n$number"
        }
    }

    fun clearNameNumbers() {
        nameNumbers.clear()
    }

    fun getNames(): Array<String> {
        val names = HcArea.getNames()
        if (nameNumbers.isEmpty()) {
            names.forEachIndexed { index, s ->
                nameNumbers[index] = s
            }
        }
        return names
    }

}