package cn.mapview.huangchuan

/**
 * @author
 * data: 2024/5/30 17:38
 * desc:
 */
enum class HcArea(val value: String, val index: Int) {

    BDX("白店乡", 0),
    PTJZ("卜塔集镇", 1),
    CLDX("传流店乡", 2),
    FDZ("付店镇", 3),
    HHNC("黄湖农场", 4),
    HSGZ("黄寺岗镇", 5),
    HCX("潢川县", 6),
    JJJZ("江家集镇", 7),

    LLX("来龙乡", 8),
    LGX("隆古乡", 9),
    RHZ("仁和镇", 10),
    SPSZ("伞陂寺镇", 11),
    SYGX("上油岗乡", 12),
    SLSZ("双柳树镇", 13),
    TDX("谈店乡", 14),
    TLPZ("桃林铺镇", 15),

    WGX("魏岗乡", 16),
    ZZZ("踅孜镇", 17),
    ZJX("张集乡", 18);

    companion object {
        fun getNames(): Array<String> {
            return entries.map { it.value }.toTypedArray()
        }

        fun getHcArea(index: Int): HcArea {
            val area = entries.first { it.index == index }
            return area
        }
    }

}