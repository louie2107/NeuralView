package io.samlewis.neuralview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralView extends View {

    private Paint groupOnePaint = new Paint();
    private Paint groupTwoPaint = new Paint();
    private Paint linePaint = new Paint();
    private int group1Radius;
    private int group2Radius;
    private int count;
    private int groupOneColor;
    private int groupTwoColor;
    private int lineColor;
    private int lineWidth;

    Random r = new Random();
    List<DirectedPoint> groupOnePoints = new ArrayList<>();
    List<DirectedPoint> groupTwoPoints = new ArrayList<>();

    public NeuralView(Context context) {
        super(context);
        init(context, null);
    }

    public NeuralView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NeuralView, 0, 0);
        group1Radius = ta.getInt(R.styleable.NeuralView_groupOneRadius, 20);
        group2Radius = ta.getInt(R.styleable.NeuralView_groupTwoRadius, 30);
        count = ta.getInt(R.styleable.NeuralView_count, 10);
        lineWidth = ta.getInt(R.styleable.NeuralView_lineWidth, 1);
        groupOneColor = ta.getColor(R.styleable.NeuralView_groupOneColor, Color.GREEN);
        groupTwoColor = ta.getColor(R.styleable.NeuralView_groupTwoColor, Color.BLUE);
        lineColor = ta.getColor(R.styleable.NeuralView_lineColor, Color.CYAN);

        ta.recycle();

        for (int i=0; i<count; i++) {
            groupOnePoints.add(new DirectedPoint(r.nextBoolean(), r.nextBoolean()));
            groupTwoPoints.add(new DirectedPoint(r.nextBoolean(), r.nextBoolean()));
        }
        groupOnePaint.setColor(groupOneColor);
        groupTwoPaint.setColor(groupTwoColor);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0; i<count; i++) {
            groupOnePoints.get(i).set(r.nextInt(MeasureSpec.getSize(widthMeasureSpec)), r.nextInt(MeasureSpec.getSize(heightMeasureSpec)));
            groupTwoPoints.get(i).set(r.nextInt(MeasureSpec.getSize(widthMeasureSpec)), r.nextInt(MeasureSpec.getSize(heightMeasureSpec)));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        for(DirectedPoint point : groupOnePoints) {
            boundCheck(point);
            canvas.drawCircle(point.x, point.y, group2Radius, groupOnePaint);
            if (groupOnePoints.indexOf(point)+1 == groupOnePoints.size()) {
                canvas.drawLine(point.x, point.y, groupOnePoints.get(0).x, groupOnePoints.get(0).y, linePaint);
            } else {
                canvas.drawLine(point.x, point.y, groupOnePoints.get(groupOnePoints.indexOf(point) + 1).x, groupOnePoints.get(groupOnePoints.indexOf(point) + 1).y, linePaint);
            }
        }

        for(DirectedPoint point : groupTwoPoints) {
            boundCheck(point);
            canvas.drawCircle(point.x, point.y, group1Radius, groupOnePaint);
            if (groupTwoPoints.indexOf(point)+1 == groupTwoPoints.size()) {
                canvas.drawLine(point.x, point.y, groupTwoPoints.get(0).x, groupTwoPoints.get(0).y, linePaint);
            } else {
                canvas.drawLine(point.x, point.y, groupTwoPoints.get(groupTwoPoints.indexOf(point) + 1).x, groupTwoPoints.get(groupTwoPoints.indexOf(point) + 1).y, linePaint);
            }
        }

        canvas.restore();
        invalidate();
    }

    private void boundCheck(DirectedPoint directedPoint) {
        if(directedPoint.x <= 0) {
            directedPoint.xDirection = true;
        } else if(directedPoint.x >= getWidth()) {
            directedPoint.xDirection = false;
        }
        if(directedPoint.xDirection) {
            directedPoint.x++;
        } else {
            directedPoint.x--;
        }

        if(directedPoint.y <= 0) {
            directedPoint.yDirection = true;
        } else if(directedPoint.y >= getHeight()) {
            directedPoint.yDirection = false;
        }
        if(directedPoint.yDirection) {
            directedPoint.y++;
        } else {
            directedPoint.y--;
        }
    }

}
