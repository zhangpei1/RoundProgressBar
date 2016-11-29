package com.example.hasee.mytest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hasee on 2016/11/29.
 */
public class MyView extends View {
    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;
    /**
     * 圆环的宽度
     */

    private float roundWidth;
    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;
    /***
     * 上面的文字
     */
    private String up_text;
    /**
     * 下面的文字
     */
    private String down_text;
    private float up_text_size;//上面文字大小
    private float down_text_size;//下面文字大小
    private int within_circle;//内环圆颜色
    private Paint paint;
    private int progress=0;


    public MyView(Context context, AttributeSet attrs) {

        super(context, attrs);
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        roundColor = typedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 60);
        up_text = typedArray.getString(R.styleable.RoundProgressBar_up_text);
        down_text = typedArray.getString(R.styleable.RoundProgressBar_down_text);
        up_text_size = typedArray.getDimension(R.styleable.RoundProgressBar_up_text_size, 25);
        down_text_size = typedArray.getDimension(R.styleable.RoundProgressBar_down_text_size, 18);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.RED);
        within_circle = typedArray.getColor(R.styleable.RoundProgressBar_within_circle, Color.BLUE);
        typedArray.recycle();
    }

    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2;
        Log.e("centre", String.valueOf(centre));
        int radius = (int) (centre - roundWidth / 2);//第一个圆

        paint.setColor(within_circle);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        //第二个园
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环
//        canvas.save();
//        canvas.rotate(i,centre,centre);



//        canvas.restore();
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(roundProgressColor);  //设置进度的颜色
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, 0, 360 *progress / 100, false, paint);  //根据进度画圆弧
        paint.setColor(roundProgressColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
        paint.setTextSize(up_text_size);

//        canvas.drawText("排",centre - 20/ 2, centre + 20/2, paint);
        float textWidth = paint.measureText(up_text);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        canvas.drawText(up_text, centre - textWidth / 2, (centre - 25) + textWidth / 2, paint);
        paint.setTextSize(down_text_size);
        float textWidth1 = paint.measureText(down_text);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(down_text, centre - textWidth1 / 2, (centre) + textWidth1 / 2, paint);

    }
    public void setPercent(int percent) {
//百分比不可能超过100 如果超过100则抛出异常
        if (percent > 100) {
            throw new IllegalArgumentException("percent must less than 100!");
        }

        setCurPercent(percent);

    }

    private void setCurPercent(final int percent) {

        progress = percent;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<=percent;i++){
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress = i;
                    Log.e("a",String.valueOf(progress));
                    MyView.this.postInvalidate();
                }
            }

        }).start();

    }

}
