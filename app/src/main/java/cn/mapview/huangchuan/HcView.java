package cn.mapview.huangchuan;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import cn.mapview.BaseMapView;
import cn.mapview.XmlToPathConverter;

/**
 * @author data: 2024/5/30 17:01
 * desc:
 */
public class HcView extends BaseMapView {
    public HcView(Context context) {
        super(context);
    }

    public HcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public int size() {
        return HuangChuanManager.INSTANCE.getName().length;
    }

    @Override
    public Path getPath(int index) {
        return XmlToPathConverter.INSTANCE.getXmlValue(this.getContext(),index,true);
    }

    @Override
    public String[] getSvgPaths() {
        return HuangChuanManager.INSTANCE.getName();
    }

    @Override
    public String[] getNames() {
        return HuangChuanManager.INSTANCE.getName();
    }

    @Override
    public int bottom() {
        return 0;
    }

    @Override
    public int right() {
        return 0;
    }

    @Override
    public boolean isUnDrawText(int position) {
        return false;
    }

    @Override
    public int getPaddingTop(int index) {
        return 0;
    }

    @Override
    public int getPaddingLeft(int index) {
        return 0;
    }

    @Override
    public void setTextSize(Paint textPaint, int index) {

    }
}
