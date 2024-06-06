package cn.mapview.huangchuan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

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
        return HuangChuanManager.INSTANCE.getNames().length;
    }

    @Override
    public Path getPath(int index) {
        return XmlToPathConverter.INSTANCE.getXmlValue(this.getContext(),index,true);
    }

    @Override
    public String[] getSvgPaths() {
        return HuangChuanManager.INSTANCE.getNames();
    }

    @Override
    public String[] getNames() {
        return HuangChuanManager.INSTANCE.getNames();
    }

    @Override
    public String getName(int index) {
        return HuangChuanManager.INSTANCE.getNameFromNumber(index);
    }

    @Override
    public int bottom() {
        return HcArea.RHZ.getIndex();
    }

    @Override
    public int right() {
        return HcArea.ZJX.getIndex();
    }

    @Override
    public boolean isUnDrawText(int position) {
        return false;
    }

    public void updateNames() {
        invalidate();
    }

    @Override
    public int getPaddingTop(int index) {
        HcArea hcArea = HcArea.Companion.getHcArea(index);
        int paddingTop = 0;
        switch (hcArea) {
            case PTJZ:
                paddingTop = 26;
                break;
            case JJJZ:
                paddingTop = 80;
                break;
            case HSGZ:
                paddingTop = 70;
                break;
            case SPSZ:
                paddingTop = 40;
                break;
            case CLDX:
                paddingTop = 20;
                break;
            case ZZZ:
                paddingTop = 60;
                break;
            case LLX:
                paddingTop = 30;
                break;
        }
        return paddingTop;
    }

    @Override
    public int getPaddingLeft(int index) {
        HcArea hcArea = HcArea.Companion.getHcArea(index);
        int paddingLeft = 0;
        switch (hcArea) {
            case PTJZ:
                paddingLeft = -18;
                break;
            case BDX:
                paddingLeft = -16;
                break;
            case SLSZ:
                paddingLeft = -6;
                break;
            case SYGX:
                paddingLeft = -40;
                break;
            case SPSZ:
                paddingLeft = -36;
                break;
            case RHZ:
                paddingLeft = -20;
                break;
            case LLX:
                paddingLeft = -60;
                break;
            case HHNC:
                paddingLeft = -50;
                break;
            case HCX:
                paddingLeft = -20;
                break;
            case TLPZ:
                paddingLeft = -42;
                break;
            case ZJX:
                paddingLeft = -16;
                break;
            case LGX:
                paddingLeft = -30;
                break;
            case WGX:
                paddingLeft = -36;
                break;
            case HSGZ:
                paddingLeft = -70;
                break;
            case TDX:
                paddingLeft = -40;
                break;
            case CLDX:
                paddingLeft = -20;
                break;
            case JJJZ:
                paddingLeft = -30;
                break;
            case ZZZ:
                paddingLeft = -16;
            }
        return paddingLeft;
    }

    @Override
    public void setTextSize(Paint textPaint, int index) {
        HcArea hcArea = HcArea.Companion.getHcArea(index);
        if (hcArea == HcArea.PTJZ) {
            textPaint.setTextSize(14);
        }
    }

    @Override
    public boolean isCustomDrawText(int index) {
        return index == HcArea.HHNC.getIndex();
    }

    @Override
    public void drawCustomDrawText(Canvas pCanvas, Paint textPaint, int index, RectF rectF, int paddingTop, int paddingLeft, int padding) {
        Log.d("HcView","rectF: "+ rectF.toString()+ "top: " +paddingTop +  "left: " +paddingLeft +  "padding: " +padding);
        float x = rectF.left + (rectF.right - rectF.left) / 2;
        float y = rectF.top + (rectF.bottom - rectF.top) / 2;
        Log.d("HcView","x: "+ x+ ",y: " +y);
        textPaint.setStrokeWidth(1.5f);
        textPaint.setAntiAlias(true);
        pCanvas.drawLine(x+10 ,y+10,x + 14,y- 64,textPaint);
        pCanvas.drawText(getName(index), x - padding + paddingLeft + 20, y + paddingTop - 70, textPaint);
    }

    @Override
    public int getSelectTextColor(int selected) {
        if (selected == HcArea.HHNC.getIndex()) {
            return 0xff108EE9;
        }
        return 0xffffffff;
    }

}
