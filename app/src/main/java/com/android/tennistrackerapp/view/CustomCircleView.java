package com.android.tennistrackerapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomCircleView extends View {
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public CustomCircleView(Context context) {
        super(context);

        init(null);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = this.getWidth();
        float y = this.getHeight();
        float padding_x = (float)(this.getWidth() * 0.05);
        float radius = ((float) this.getWidth() /2) - padding_x;

        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

        canvas.drawCircle(x/2, y/2, radius, paint);
    }
}
