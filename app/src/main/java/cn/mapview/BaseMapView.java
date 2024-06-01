package cn.mapview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseMapView extends View {
    private static final int DEFAULT_COLOR = Color.rgb(0xff, 0xff, 0xff);
    private static final int DEFAULT_SELECTD_COLOR = Color.rgb(0x00, 0xff, 0xff);
    private static final String TAG = "BaseMapView";
    private int commonTextSixe = 25;
    private int textColor = 0xff108EE9;
    private PointF[] mPointFs = new PointF[4];
    private float height = 0;
    private float width = 0;
    private int padding = 8;
    private float svgPathScale = 2.5f;
    protected Path[] xPaths = new Path[size()];
    private Paint[] xPaints = new Paint[size()];
    private Paint xPaintsBorder;
    private Paint touchPaint;
    private int selected = -1;
    private Matrix xMatrix = new Matrix();
    private float translateX, translateY;
    private int viewHeight, viewWidth;
    private float minScale = 1;
    private float maxScale = 6;
    private float scale;
    private float defaultScale = 1;
    private int selectedColor = -1;
    private int mapColor = -1;
    private long startOnTouchTime = 0;
    GestureDetector gestureDetector;

    public abstract int size();

    public void setPaintColor(int index, int color, boolean isFull) {
        Paint p = xPaints[index];
        p.setColor(color);
        if (isFull) {
            p.setStyle(Paint.Style.FILL);
        }
        invalidate();
    }

    public void setSelectedColor(int color) {
        this.selectedColor = color;
        invalidate();
    }

    public void setMapColor(int pMapColor) {
        mapColor = pMapColor;
        invalidate();
    }

    public BaseMapView(Context context) {
        super(context);
        initPaths();
        computeBounds();
        initPaints();
    }

    public BaseMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaths();
        computeBounds();
        initPaints();
        gestureDetector = new GestureDetector(context, new mapGestureListener());
    }

    /**
     * 调整清晰度，竖直越大，地图越清晰，一般2.5倍即可，改成其他值之后，需要在drawOneArea方法中调整文字的大小和位置
     */

    protected void initPaths() {
        try {
//            SvgPathToAndroidPath lParser = new SvgPathToAndroidPath();
//            lParser.setScale(svgPathScale);
            for (int i = 0; i < getSvgPaths().length; i++) {
//                String svgPath = getSvgPaths()[i];
//                Path path = XmlToPathConverter.INSTANCE.convertXmlToPath(this.getContext(),i);
//                Path path =  XmlToPathConverter.INSTANCE.getXmlValue(this.getContext(),i);
//                Path path = lParser.parser(svgPath);
                xPaths[i] = getPath(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public abstract Path getPath(int index);


    public abstract String[] getSvgPaths();

    public abstract String[] getNames();

    public abstract String getName(int index);

    private void initPaints() {
        for (int i = 0; i < xPaints.length; i++) {
            Paint xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            xPaint.setColor(DEFAULT_COLOR);
            xPaint.setStyle(Paint.Style.FILL);
            xPaints[i] = xPaint;
        }
        touchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        touchPaint.setStyle(Paint.Style.FILL);
        touchPaint.setColor(DEFAULT_SELECTD_COLOR);

        xPaintsBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaintsBorder.setStyle(Paint.Style.STROKE);
        xPaintsBorder.setColor(0xffB0D2F6);
        xPaintsBorder.setStrokeWidth(1.5f);
    }

    private void computeBounds() {
        RectF hljRF = new RectF();
        xPaths[right()].computeBounds(hljRF, true);

        RectF hnRF = new RectF();
        xPaths[bottom()].computeBounds(hnRF, true);

        mPointFs[0] = new PointF(0, 0);
        mPointFs[1] = new PointF(hljRF.right, 0);
        mPointFs[2] = new PointF(hljRF.right, hnRF.bottom);
        mPointFs[3] = new PointF(0, hnRF.bottom);

        width = hljRF.right + 2 * padding;
        height = hnRF.bottom + 2 * padding;
        Log.e(TAG, "computeBounds: " + width + "  " + height);
    }

    public abstract int bottom();

    public abstract int right();

    private class mapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
            return super.onSingleTapConfirmed(motionEvent);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (onProvinceDoubleClickListener != null) {
                onProvinceDoubleClickListener.onProvinceDoubleClick();
                return true;
            }
            return super.onDoubleTap(e);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int speSize = MeasureSpec.getSize(widthMeasureSpec);
        minScale = defaultScale = scale = speSize / width;
        Log.i(TAG, "onMeasure: " + speSize + "   " + minScale + "  " + (int) (speSize * height / width));
        setMeasuredDimension(speSize, (int) (speSize * height / width));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        scale = scale > maxScale ? maxScale : Math.max(scale, minScale);
        xMatrix.setScale(scale, scale);
        canvas.concat(xMatrix);
        canvas.translate(translateX + padding, translateY + padding);
        drawBaseMap(canvas);
        drawSelectedMap(canvas);
    }

    private void drawBaseMap(Canvas pCanvas) {
        Paint textPaint = new Paint();

        for (int i = 0; i < xPaths.length; i++) {
            textPaint.setTextSize(commonTextSixe);
            textPaint.setColor(textColor);

            if (mapColor != -1 && xPaints[i].getColor() == DEFAULT_COLOR) {
                xPaints[i].setColor(mapColor);
            }

            if (xPaints[i].getColor() != DEFAULT_COLOR) {
                textPaint.setColor(Color.WHITE);
            }

            pCanvas.drawPath(xPaths[i], xPaints[i]);
            pCanvas.drawPath(xPaths[i], xPaintsBorder);
            if (isUnDrawText(i)) {
                continue;
            }
            drawOneArea(pCanvas, textPaint, i);
        }
    }

    public abstract boolean isUnDrawText(int position);

    public abstract int getPaddingTop(int index);

    public abstract int getPaddingLeft(int index);

    public abstract void setTextSize(Paint textPaint, int index);

    private void drawOneArea(Canvas pCanvas, Paint textPaint, int index) {
        RectF testRect = new RectF();
        xPaths[index].computeBounds(testRect, true);
        int paddingTop;
        int paddingLeft;
        paddingTop = getPaddingTop(index);
        paddingLeft = getPaddingLeft(index);
        setTextSize(textPaint, index);
        pCanvas.drawText(getName(index), testRect.left + testRect.width() / 2 - padding + paddingLeft, testRect.top + testRect.height() / 2 + paddingTop, textPaint);
    }

    private void drawSelectedMap(Canvas pCanvas) {
        if (selected >= 0) {
            if (selectedColor != -1) {
                touchPaint.setColor(selectedColor);
            }
            pCanvas.drawPath(xPaths[selected], touchPaint);
            Paint textPaint = new Paint();
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(0xffffffff);
            textPaint.setTextSize(commonTextSixe);
            drawOneArea(pCanvas, textPaint, selected);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }


    @Override
    public boolean onTouchEvent(MotionEvent pMotionEvent) {
        gestureDetector.onTouchEvent(pMotionEvent);

        switch (pMotionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = NONE;
                if (pMotionEvent.getPointerCount() == 1) {
                    startOnTouchTime = System.currentTimeMillis();
                    mode = NONE;
                    startPoint.set(pMotionEvent.getX(), pMotionEvent.getY());

                }
                break;
            case MotionEvent.ACTION_UP:
                mode = NONE;
                if (pMotionEvent.getPointerCount() == 1) {
                    long timeCount = System.currentTimeMillis() - startOnTouchTime;
                    if (timeCount < 4000 && Math.abs(pMotionEvent.getX() - startPoint.x) < 5f && Math.abs(pMotionEvent.getY() - startPoint.y) < 5f) {
                        try {
                            for (int i = 0; i < xPaths.length; i++) {
                                RectF r = new RectF();
                                xPaths[i].computeBounds(r, true);
                                Region re = new Region();
                                re.setPath(xPaths[i], new Region((int) r.left, (int) r.top, (int) r.right, (int) r.bottom));
                                if (re.contains((int) (pMotionEvent.getX() / scale - translateX - padding), (int) (pMotionEvent.getY() / scale - translateY - padding))) {
                                    boolean doubleClick = i == selected;
                                    selected = i;
                                    if (this.onProvinceSelectedListener != null) {
                                        this.onProvinceSelectedListener.onProvinceSelected(selected, doubleClick);
                                    }
                                    invalidate();
                                    return true;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 模式 NONE：无. MOVE：移动. ZOOM:缩放
     */

    private static final int NONE = 0;
    private static final int MOVE = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;    // 默认模式

    private double startDis = 0d;
    private PointF startPoint = new PointF();

    /**
     * 多触点
     */
    private void onPointerDown(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            mode = ZOOM;
            startDis = getDistance(event);
        }
    }

    private void onTouchMove(MotionEvent event) {
        if (mode == ZOOM && event.getPointerCount() == 2) {
            double endDis = getDistance(event);//结束距离
            if (endDis > 10f) {
                float tmpScale = (float) (endDis / startDis);//放大倍数
                if (tmpScale == 1) {
                    return;
                } else {
                    scale = tmpScale;
                    invalidate();
                }
            }
        } else {
            long timeCount = System.currentTimeMillis() - startOnTouchTime;
            if (timeCount > 300 && Math.abs(event.getX() - startPoint.x) > 10f && Math.abs(event.getY() - startPoint.y) > 10f) {
                translateX = event.getX() - startPoint.x;
                translateY = event.getY() - startPoint.y;
                invalidate();
            }
        }
    }

    /**
     * @return 获取两手指之间的距离
     */
    private double getDistance(MotionEvent event) {
        double x = event.getX(0) - event.getX(1);
        double y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }

    public interface OnProvinceSelectedListener {
        void onProvinceSelected(int selected, boolean repeatClick);
    }

    public interface OnProvinceDoubleClickListener {
        void onProvinceDoubleClick();
    }

    private OnProvinceSelectedListener onProvinceSelectedListener;
    private OnProvinceDoubleClickListener onProvinceDoubleClickListener;

    public void setOnProvinceSelectedListener(OnProvinceSelectedListener pOnProvinceSelectedListener) {
        this.onProvinceSelectedListener = pOnProvinceSelectedListener;
    }

    public void setOnProvinceDoubleClickListener(OnProvinceDoubleClickListener onDoubleClickListener) {
        this.onProvinceDoubleClickListener = onDoubleClickListener;
    }
}
