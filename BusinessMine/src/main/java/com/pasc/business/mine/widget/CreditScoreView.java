package com.pasc.business.mine.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.pasc.business.mine.R;
import com.pasc.lib.base.util.ScreenUtils;

/**
 * Created by xiongjiaping112 on 17/12/13.
 */

public class CreditScoreView extends View {
    private final Paint paint = new Paint();
    private final RectF rectF = new RectF();
    private final Rect rect = new Rect();
    private float outPaintStroke = 1;
    private DashPathEffect effect;
    private float offset = 9;
    private int outArcColor = Color.WHITE;
    private int innerArcColor = Color.WHITE;
    private float innerPaintStroke = 2;
    private float innerAlpha = 0.3f;
    private float scorePercent = 90f;
    private int circleResId = R.drawable.temp_btn_circular;
    private Bitmap bitmap;
    private float circleR = 7;
    private float screenDensity;
    private String score = "";
    private float scoreTextSize = 44;
    private int scoreTextColor = Color.WHITE;
    private String scoreDesc = "";
    private float descTextSize = 14;
    private int descTextColor = Color.WHITE;
    private final Paint.FontMetrics ma = new Paint.FontMetrics();
    private static final String TAG = "CreditScoreView";

    private static final float ANGLE_OUTER_START = 64.79f;
    private static final float ANGLE_INNER_START = 62.67f;

    public CreditScoreView(Context context) {
        super(context);
        init(null);
    }

    public CreditScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        bitmap = BitmapFactory.decodeResource(getResources(), circleResId);
        screenDensity = ScreenUtils.getScreenDensity();
        offset *= screenDensity;
        outPaintStroke *= screenDensity;
        innerPaintStroke *= screenDensity;
        effect = new DashPathEffect(
            new float[] { 4 * screenDensity, 4 * screenDensity, 4 * screenDensity, 4 * screenDensity },
            1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float right = getWidth() - getPaddingRight() - outPaintStroke / 2;
        float left = getPaddingLeft() + outPaintStroke / 2;
        float top = getPaddingTop() + outPaintStroke / 2;
        float r = (right - left) / 2;
        circleR = 3.5f / 190 * getWidth() * screenDensity;

        //        外部虚线圆弧
        rectF.set(left, top, right, top + 2 * r);
        paint.setStrokeWidth(outPaintStroke);
        paint.setColor(outArcColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(effect);
        canvas.drawArc(rectF, 90 + ANGLE_OUTER_START, 360 - 2 * ANGLE_OUTER_START, false, paint);

        //        内部实线圆弧
        rectF.set(rectF.left + offset, rectF.top + offset, rectF.right - offset, rectF.bottom - offset);
        paint.setPathEffect(null);
        paint.setColor(innerArcColor);
        paint.setStrokeWidth(innerPaintStroke);
        paint.setAlpha((int) (innerAlpha * 0xff));
        canvas.drawArc(rectF, 90 + ANGLE_INNER_START, 360 - 2 * ANGLE_INNER_START, false, paint);

        //       数值
        paint.setAlpha(0xff);
        float sweepAngle = scorePercent / 100 * (360 - 2 * ANGLE_INNER_START);
        canvas.drawArc(rectF, 90 + ANGLE_INNER_START, sweepAngle, false, paint);
        //圆点
        //if(sweepAngle==0){
        //    sweepAngle=circleDegree;
        //}else if(sweepAngle==(360 - 2 * v)){
        //    sweepAngle=360 - 2 * v-circleDegree;
        //}else{
        //    sweepAngle=sweepAngle*((sweepAngle-circleDegree)/sweepAngle);
        //}

        float v1 = 90 + ANGLE_INNER_START + sweepAngle;
        double a = Math.toRadians(v1);
        float bitmapL = left + r + (r - offset) * (float) Math.cos(a);
        float bitmapT = top + r + (r - offset) * (float) Math.sin(a);

        rectF.set(bitmapL - circleR, bitmapT - circleR, bitmapL + circleR, bitmapT + circleR);
        canvas.drawBitmap(bitmap, null, rectF, null);

        //        分值
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(scoreTextSize * screenDensity);
        paint.setColor(scoreTextColor);
        paint.getTextBounds(score, 0, score.length(), rect);
        paint.getFontMetrics(ma);
        float height = top + r + rect.height() / 2;
        canvas.drawText(score, left + r - rect.width() / 2, height, paint);
        //        分值描述
        paint.setTextSize(descTextSize * screenDensity);
        paint.setColor(descTextColor);
        paint.getTextBounds(scoreDesc, 0, scoreDesc.length(), rect);
        paint.getFontMetrics(ma);
        canvas.drawText(scoreDesc, left + r - rect.width() / 2, 45.0f / 136 * getHeight() - ma.bottom,
            paint);
    }

    public void showScore(int score, int maxScore, String desc) {
        if (score == -1) {
            this.score = "暂无评分";
            this.scoreTextSize = 22;
        } else {
            this.score = score + "";
        }
        this.scorePercent = score * 100 / maxScore;
        this.scoreDesc = desc;
        invalidate();
    }
}
