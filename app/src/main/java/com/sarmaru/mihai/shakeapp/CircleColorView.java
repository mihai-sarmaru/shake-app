package com.sarmaru.mihai.shakeapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mihai Sarmaru on 03.02.2015.
 */
public class CircleColorView extends View {

    private static final int TEXT_SIZE = 10;
    private static final int PADDING = 10;

    private int circleColor, labelColor, labelTextSize;
    private String circleText;
    private Paint paint;

    public CircleColorView (Context context) {
        this(context, null);
    }

    public CircleColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

        TypedArray typedArray = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.CircleColorView, 0, 0);
        try {
            circleColor = typedArray.getInteger(R.styleable.CircleColorView_circleColor, 0);
            labelColor = typedArray.getInteger(R.styleable.CircleColorView_labelColor, 0);
            circleText = typedArray.getString(R.styleable.CircleColorView_labelText);
            labelTextSize = typedArray.getInteger(R.styleable.CircleColorView_labelTextSize, TEXT_SIZE);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewHalfWidth = this.getMeasuredWidth() / 2;
        int viewHalfHeight = this.getMeasuredHeight() / 2;

        int radius = viewHalfWidth > viewHalfHeight ?
                viewHalfHeight - PADDING : viewHalfWidth - PADDING;

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(circleColor);

        canvas.drawCircle(viewHalfWidth, viewHalfHeight, radius, paint);

        paint.setColor(labelColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(labelTextSize);

        int textCyPosition = viewHalfHeight + labelTextSize / 3;
        canvas.drawText(circleText, viewHalfWidth, textCyPosition, paint);
    }

    public int getCircleColor () {
        return this.circleColor;
    }

    public void setCircleColor (int color) {
        this.circleColor = color;
        invalidate();
        requestLayout();
    }

    public int getLabelColor () {
        return this.labelColor;
    }

    public void setLabelColor (int color) {
        this.labelColor = color;
        invalidate();
        requestLayout();
    }

    public String getCircleText () {
        return this.circleText;
    }

    public void setCircleText (String text) {
        this.circleText = text;
        invalidate();
        requestLayout();
    }
}
