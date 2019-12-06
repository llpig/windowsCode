package com.kong.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.Vector;

public class CustomView extends View {
    ArrayList<Float> cirCleVectorX=new ArrayList<Float>();
    ArrayList<Float> cirCleVectorY=new ArrayList<Float>();
    //创建一个画笔
    Paint paint=new Paint();
    //定义圆的半径
    int radius=15;
    public CustomView(Context context) {
        super(context);
        //给数组设置初值
        cirCleVectorX.add(new Float(radius));
        cirCleVectorY.add(new Float(radius));
    }

    //当界面被重新绘制时调用该方法
    public void onDraw(Canvas canvas)
    {
        //设置圆心位置、半径和画笔
        for(int i=0;i<cirCleVectorX.size();++i)
        {
            //将圆圈初始化在左上角
            canvas.drawCircle(cirCleVectorX.get(i),cirCleVectorY.get(i),radius,paint);
        }
    }
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        //获取当前点击事件的坐标
        cirCleVectorX.add(motionEvent.getX());
        cirCleVectorY.add(motionEvent.getY());
        //重绘界面
        invalidate();
        return true;
    }
    //选择画笔的粗细
    public void selectPaintSize(int paintSize)
    {
        radius=paintSize;
    }
    //选择画笔的颜色
    public void selectPaintColor(int color)
    {
        paint.setColor(color);
    }
    //清空屏幕
    public void clearScreen()
    {
        cirCleVectorX.clear();
        cirCleVectorY.clear();
        invalidate();
    }
}
