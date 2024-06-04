package cn.mapview.china;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import cn.mapview.BaseMapView;
import cn.mapview.SvgPathToAndroidPath;

/**
 * @author data: 2024/5/29 13:54
 * desc:
 */
public class ChinaView extends BaseMapView {


    public enum ChineArea {
        /**
         * name and position
         */
        BeiJing("北京市", 0), TianJin("天津市", 1), ShangHai("上海市", 2), ChongQing("重庆市", 3), HeBei("河北省", 4), ShanXi("山西省", 5), LiaoNing("辽宁省", 6), HeiLongJiang("黑龙江省", 7), JiLin("吉林省", 8), JiangSu("江苏省", 9), ZheJiang("浙江省", 10), AnHui("安徽省", 11), FuJian("福建省", 12), JiangXi("江西省", 13), ShanDong("山东省", 14), HeNan("河南省", 15), HuBei("湖北省", 16), HuNan("湖南省", 17), GuangDong("广东省", 18), HaiNan("海南省", 19), SiChuan("四川省", 20), GuiZhou("贵州省", 21), YunNan("云南省", 22), ShaanXi("陕西省", 23), GanSu("甘肃省", 24), QingHai("青海省", 25), NeiMengGu("内蒙古自治区", 26), GuangXi("广西自治区", 27), XiZang("西藏自治区", 28), NingXia("宁夏自治区", 29), XinJiang("新疆自治区", 30), AoMen("澳门", 31), XiangGang("香港", 32), TaiWan("台湾", 33);
        public int value;
        public String name;

        ChineArea(String pName, int pValue) {
            this.name = pName;
            this.value = pValue;
        }

        public static ChineArea valueOf(int value) {
            switch (value) {
                case 0:
                    return BeiJing;
                case 1:
                    return TianJin;
                case 2:
                    return ShangHai;
                case 3:
                    return ChongQing;
                case 4:
                    return HeBei;
                case 5:
                    return ShanXi;
                case 6:
                    return LiaoNing;
                case 7:
                    return HeiLongJiang;
                case 8:
                    return JiLin;
                case 9:
                    return JiangSu;
                case 10:
                    return ZheJiang;
                case 11:
                    return AnHui;
                case 12:
                    return FuJian;
                case 13:
                    return JiangXi;
                case 14:
                    return ShanDong;
                case 15:
                    return HeNan;
                case 16:
                    return HuBei;
                case 17:
                    return HuNan;
                case 18:
                    return GuangDong;
                case 19:
                    return HaiNan;
                case 20:
                    return SiChuan;
                case 21:
                    return GuiZhou;
                case 22:
                    return YunNan;
                case 23:
                    return ShaanXi;
                case 24:
                    return GanSu;
                case 25:
                    return QingHai;
                case 26:
                    return NeiMengGu;
                case 27:
                    return GuangXi;
                case 28:
                    return XiZang;
                case 29:
                    return NingXia;
                case 30:
                    return XinJiang;
                case 31:
                    return AoMen;
                case 32:
                    return XiangGang;
                case 33:
                    return TaiWan;
                default:
                    return null;
            }
        }

    }

    @Override
    public int size() {
        return 34;
    }

    public ChinaView(Context context) {
        super(context);
    }

    public ChinaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private float svgPathScale = 2.5f;

    private SvgPathToAndroidPath lParser;

    @Override
    protected void initPaths() {
        try {
            SvgPathToAndroidPath lParser = new SvgPathToAndroidPath();
            lParser.setScale(svgPathScale);
            for (int i = 0; i < getSvgPaths().length; i++) {
                String name = getNames()[i];
                Log.e("ChinaView","加载:" + name);
                String svgPath = getSvgPaths()[i];
                Path path = lParser.parser(svgPath);
                Log.e("ChinaView","path:" + path);
                xPaths[i] = path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SvgPathToAndroidPath getSvgPathToAndroidPath() {
        if (lParser == null) {
            lParser = new SvgPathToAndroidPath();
            lParser.setScale(svgPathScale);
        }
        return lParser;
    }

    @Override
    public Path getPath(int index) {
        String svgPath = getSvgPaths()[index];
        return getSvgPathToAndroidPath().parser(svgPath);
    }

    @Override
    public String[] getSvgPaths() {
        return ChinaManager.INSTANCE.getSvgPaths();
    }

    @Override
    public String[] getNames() {
        return ChinaManager.INSTANCE.getProvinceNames();
    }

    @Override
    public String getName(int index) {
        return ChinaManager.INSTANCE.getProvinceNames()[index];
    }

    @Override
    public int bottom() {
        return ChineArea.HaiNan.value;
    }

    @Override
    public int right() {
        return ChineArea.HeiLongJiang.value;
    }

    @Override
    public boolean isUnDrawText(int position) {
        return ChineArea.XiangGang == ChineArea.valueOf(position) || ChineArea.AoMen == ChineArea.valueOf(position);
    }

    @Override
    public int getPaddingTop(int index) {
        int paddingTop = 0;

        if (ChineArea.NeiMengGu == ChineArea.valueOf(index)) {
            paddingTop += 180;
        }

        if (ChineArea.XinJiang == ChineArea.valueOf(index)) {
            paddingTop += 50;
        }

        if (ChineArea.XiZang == ChineArea.valueOf(index)) {
        }

        if (ChineArea.GanSu == ChineArea.valueOf(index)) {
            paddingTop += 90;
        }
        if (ChineArea.ShangHai == ChineArea.valueOf(index)) {
        }

        if (ChineArea.GuangDong == ChineArea.valueOf(index)) {
            paddingTop -= 20;
        }

        if (ChineArea.ShaanXi == ChineArea.valueOf(index)) {
            paddingTop += 38;
        }
        if (ChineArea.ShanXi == ChineArea.valueOf(index)) {
        }

        if (ChineArea.HeiLongJiang == ChineArea.valueOf(index)) {
            paddingTop += 30;
        }

        if (ChineArea.HeBei == ChineArea.valueOf(index)) {
            paddingTop += 50;
        }
        if (ChineArea.BeiJing == ChineArea.valueOf(index)) {
            paddingTop += 1;
        }

        if (ChineArea.TianJin == ChineArea.valueOf(index)) {
            paddingTop += 10;
        }

        if (ChineArea.NingXia == ChineArea.valueOf(index)) {
            paddingTop += 10;
        }

        if (ChineArea.ChongQing == ChineArea.valueOf(index)) {
            paddingTop += 25;
        }

        if (ChineArea.JiangXi == ChineArea.valueOf(index)) {
        }

        if (ChineArea.AnHui == ChineArea.valueOf(index)) {
        }
        if (ChineArea.YunNan == ChineArea.valueOf(index)) {
        }
        return paddingTop;
    }

    @Override
    public int getPaddingLeft(int index) {
        int paddingLeft = 0;
        if (ChineArea.NeiMengGu == ChineArea.valueOf(index)) {
            paddingLeft -= 120;
        }

        if (ChineArea.XinJiang == ChineArea.valueOf(index)) {
            paddingLeft -= 100;
        }

        if (ChineArea.XiZang == ChineArea.valueOf(index)) {
            paddingLeft -= 110;

        }

        if (ChineArea.GanSu == ChineArea.valueOf(index)) {
            paddingLeft += 50;
        }
        if (ChineArea.ShangHai == ChineArea.valueOf(index)) {
            paddingLeft += 7;
        }

        if (ChineArea.GuangDong == ChineArea.valueOf(index)) {
        }

        if (ChineArea.ShaanXi == ChineArea.valueOf(index)) {
        }
        if (ChineArea.ShanXi == ChineArea.valueOf(index)) {
            paddingLeft -= 20;
        }

        if (ChineArea.HeiLongJiang == ChineArea.valueOf(index)) {
            paddingLeft -= 25;
        }

        if (ChineArea.HeBei == ChineArea.valueOf(index)) {
            paddingLeft -= 50;
        }
        if (ChineArea.BeiJing == ChineArea.valueOf(index)) {
        }

        if (ChineArea.TianJin == ChineArea.valueOf(index)) {
            paddingLeft -= 3;
        }

        if (ChineArea.NingXia == ChineArea.valueOf(index)) {
            paddingLeft -= 15;
        }

        if (ChineArea.ChongQing == ChineArea.valueOf(index)) {
            paddingLeft -= 25;
        }

        if (ChineArea.JiangXi == ChineArea.valueOf(index)) {
            paddingLeft -= 25;
        }

        if (ChineArea.AnHui == ChineArea.valueOf(index)) {
            paddingLeft -= 25;
        }
        if (ChineArea.YunNan == ChineArea.valueOf(index)) {
            paddingLeft -= 25;
        }
        return paddingLeft;
    }

    @Override
    public void setTextSize(Paint textPaint, int index) {

        if (ChineArea.BeiJing == ChineArea.valueOf(index)) {
            textPaint.setTextSize(14);
        }

        if (ChineArea.TianJin == ChineArea.valueOf(index)) {
            textPaint.setTextSize(14);
        }

        if (ChineArea.NingXia == ChineArea.valueOf(index)) {
            textPaint.setTextSize(21);
        }

        if (ChineArea.ChongQing == ChineArea.valueOf(index)) {
            textPaint.setTextSize(22);
        }
    }

    @Override
    public boolean isCustomDrawText(int index) {
        return false;
    }

    @Override
    public void drawCustomDrawText(Canvas pCanvas, Paint textPaint, int index, RectF rectF, int paddingTop, int paddingLeft, int padding) {

    }

}
