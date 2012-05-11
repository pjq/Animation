
package me.pjq.rotate3d;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class TranslateAlphaScaleAnimation extends Animation {
    private int mFromXType = ABSOLUTE;
    private int mToXType = ABSOLUTE;

    private int mFromYType = ABSOLUTE;
    private int mToYType = ABSOLUTE;

    private float mFromXValue = 0.0f;
    private float mToXValue = 0.0f;

    private float mFromYValue = 0.0f;
    private float mToYValue = 0.0f;

    private float mFromXDelta;
    private float mToXDelta;
    private float mFromYDelta;
    private float mToYDelta;

    // ScaleAnimation
    private float mFromX;
    private float mToX;
    private float mFromY;
    private float mToY;

    private int mPivotXType = ABSOLUTE;
    private int mPivotYType = ABSOLUTE;
    private float mPivotXValue = 0.0f;
    private float mPivotYValue = 0.0f;

    private float mPivotX;
    private float mPivotY;

    // AlphaAnimation
    private float mFromAlpha;
    private float mToAlpha;

    private float mCenterX;
    private float mCenterY;

    /**
     * Constructor to use when building a TranslateAnimation from code
     * 
     * @param fromXDelta Change in X coordinate to apply at the start of the
     *            animation
     * @param toXDelta Change in X coordinate to apply at the end of the
     *            animation
     * @param fromYDelta Change in Y coordinate to apply at the start of the
     *            animation
     * @param toYDelta Change in Y coordinate to apply at the end of the
     *            animation
     */
    public TranslateAlphaScaleAnimation(float fromXDelta, float toXDelta, float fromYDelta,
            float toYDelta, float fromX, float toX, float fromY, float toY,
            float pivotX, float pivotY, float fromAlpha, float toAlpha, float centerX, float centerY) {
        // TranslateAnimation
        mFromXValue = fromXDelta;
        mToXValue = toXDelta;
        mFromYValue = fromYDelta;
        mToYValue = toYDelta;

        mFromXType = ABSOLUTE;
        mToXType = ABSOLUTE;
        mFromYType = ABSOLUTE;
        mToYType = ABSOLUTE;

        // ScaleAnimation
        mFromX = fromX;
        mToX = toX;
        mFromY = fromY;
        mToY = toY;

        mPivotXType = ABSOLUTE;
        mPivotYType = ABSOLUTE;
        mPivotXValue = pivotX;
        mPivotYValue = pivotY;

        // AlphaAnimation
        mFromAlpha = fromAlpha;
        mToAlpha = toAlpha;

        this.mCenterX = centerX;
        this.mCenterY = centerY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        // TranslateAnimation
        float dx = mFromXDelta;
        float dy = mFromYDelta;
        if (mFromXDelta != mToXDelta) {
            dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
        }
        if (mFromYDelta != mToYDelta) {
            dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
        }
        // t.getMatrix().setTranslate(dx, dy);
        matrix.setTranslate(dx, dy);

        // ScaleAnimation
        float sx = 1.0f;
        float sy = 1.0f;

        if (mFromX != 1.0f || mToX != 1.0f) {
            sx = mFromX + ((mToX - mFromX) * interpolatedTime);
        }
        if (mFromY != 1.0f || mToY != 1.0f) {
            sy = mFromY + ((mToY - mFromY) * interpolatedTime);
        }

        if (mPivotX == 0 && mPivotY == 0) {
            // t.getMatrix().setScale(sx, sy);
           // matrix.setScale(sx, sy);
        } else {
            // t.getMatrix().setScale(sx, sy, mPivotX, mPivotY);
            //matrix.setScale(sx, sy, mPivotX, mPivotY);
        }

        // AlphaAnimaiton
        final float alpha = mFromAlpha;
        t.setAlpha(alpha + ((mToAlpha - alpha) * interpolatedTime));

        // matrix.preTranslate(-mCenterX, -mCenterY);
        // matrix.postTranslate(mCenterX, mCenterY);

    }

}
