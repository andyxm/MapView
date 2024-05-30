package cn.mapview.beijing;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import cn.mapview.BaseMapView;

/**
 * @author data: 2024/5/29 15:11
 * desc:
 */
public class BeijingView extends BaseMapView {
    public BeijingView(Context context) {
        super(context);
    }

    public BeijingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int size() {
        return BeijingManager.INSTANCE.getName().length;
    }

    @Override
    public String[] getSvgPaths() {
        return BeijingManager.INSTANCE.getName();
    }

    @Override
    public String[] getNames() {
        return BeijingManager.INSTANCE.getName();
    }

    @Override
    public int bottom() {
        return BeijingManager.INSTANCE.getName().length - 1;
    }

    @Override
    public int right() {
        return 1;
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
