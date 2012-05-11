
package me.pjq.rotate3d;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3d extends Animation {
    private float mFromDegree;
    private float mToDegree;
    private float mCenterX;
    private float mCenterY;
    private float mLeft;
    private float mTop;
    private Camera mCamera;
    private static final String TAG = "Rotate3d";
    private TYPE mType = TYPE.ROTATE;

    enum TYPE {
        ROTATE, TRANSLATE, LAST_ITEM
    }

    public void setType(TYPE type) {
        mType = type;
    }

    public Rotate3d(float fromDegree, float toDegree, float left, float top,
            float centerX, float centerY) {
        this.mFromDegree = fromDegree;
        this.mToDegree = toDegree;
        this.mLeft = left;
        this.mTop = top;
        this.mCenterX = centerX;
        this.mCenterY = centerY;

        setFillAfter(true);
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // final float FromDegree = mFromDegree;
        // float degrees = FromDegree + (mToDegree - mFromDegree)
        // * interpolatedTime;
        // Log.i(TAG, "interpolatedTime=" + interpolatedTime + "degrees=" +
        // degrees);
        // final float centerX = mCenterX;
        // final float centerY = mCenterY;
        // final Matrix matrix = t.getMatrix();
        //
        // if (degrees <= -76.0f) {
        // degrees = -90.0f;
        // mCamera.save();
        // mCamera.rotateX(degrees);
        // mCamera.getMatrix(matrix);
        // mCamera.restore();
        // } else if (degrees >= 76.0f) {
        // degrees = 90.0f;
        // mCamera.save();
        // mCamera.rotateX(degrees);
        // mCamera.getMatrix(matrix);
        // mCamera.restore();
        // } else {
        // mCamera.save();
        // // 这里很重要哦。
        // mCamera.translate(0, 0, degrees);
        // // mCamera.rotateX(degrees);
        // // mCamera.translate(0, 0, -centerX);
        // mCamera.getMatrix(matrix);
        // mCamera.restore();
        // }
        //
        // matrix.preTranslate(-centerX, -centerY);
        // matrix.postTranslate(centerX, centerY);

        if (TYPE.ROTATE == mType) {
            rotateX(interpolatedTime, t);

        } else if (TYPE.TRANSLATE == mType) {
            translate2(interpolatedTime, t);
            // translate3(interpolatedTime, t);
            // rotateX(interpolatedTime, t);
        } else if (TYPE.LAST_ITEM == mType) {
            translateLast(interpolatedTime, t);
            // translate3(interpolatedTime, t);
            // rotateX(interpolatedTime, t);
        }
    }

    private void translate(float interpolatedTime, Transformation t) {
        final float FromDegree = mFromDegree;
        float degrees = FromDegree + (mToDegree - mFromDegree)
                * interpolatedTime;
        Log.i(TAG, "translate,interpolatedTime=" + interpolatedTime + "degrees=" + degrees);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Matrix matrix = t.getMatrix();

        mCamera.save();
        // 这里很重要哦。
        mCamera.translate(0, 0, degrees);
        // mCamera.rotateX(degrees);
        // mCamera.translate(0, 0, -centerX);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);

    }

    private void translate2(float interpolatedTime, Transformation t) {

        int width = Rotate3dActivity.WIDTH;
        int newWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep
                - Rotate3dActivity.rightMarginStep;
        float scale = (float) width / (float) newWidth;// 1.11
        Log.i(TAG, "scale=" + scale);

        final Matrix matrix = t.getMatrix();

        float interpolated = interpolatedTime * (scale - 1) + 1;
        Log.i(TAG, "scale=" + scale + ",interpolated=" + interpolated);
        matrix.preScale(interpolated, 1);
        matrix.preTranslate(0,
                Rotate3dActivity.topMarginStep * interpolatedTime);
        // matrix.preRotate(interpolatedTime * 360);
        // matrix.preTranslate(mCenterX, mCenterY);
        // matrix.preTranslate(-centerX,-centerY);
        // matrix.postTranslate(centerX, centerY);
        // matrix.
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }

    private void translateLast(float interpolatedTime, Transformation t) {

        int width = Rotate3dActivity.WIDTH;
        int newWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep
                - Rotate3dActivity.rightMarginStep;
        float scale = (float) width / (float) newWidth;// 1.11
        Log.i(TAG, "scale=" + scale);

        final Matrix matrix = t.getMatrix();

        float interpolated = interpolatedTime * (scale - 1) + 1;
        Log.i(TAG, "scale=" + scale + ",interpolated=" + interpolated);
        matrix.preScale(interpolated, 1);
        matrix.preTranslate(0,
                Rotate3dActivity.topMarginStep * interpolatedTime);

        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }

    private void translate3(float interpolatedTime, Transformation t) {
        final float FromDegree = mFromDegree;
        float degrees = FromDegree + (mToDegree - mFromDegree)
                * interpolatedTime;
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Matrix matrix = t.getMatrix();

        mCamera.save();
        // 这里很重要哦。
        mCamera.translate(0, 0, degrees);
        // mCamera.rotateX(degrees);
        // mCamera.translate(0, 0, -degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }

    private void rotateX(float interpolatedTime, Transformation t) {
        final float FromDegree = mFromDegree;
        float degrees = FromDegree + (mToDegree - mFromDegree)
                * interpolatedTime;
        Log.i(TAG, "rotateX,interpolatedTime=" + interpolatedTime + "degrees=" + degrees);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Matrix matrix = t.getMatrix();

        degrees = -degrees;
        if (false) {

            if (degrees <= -76.0f) {
                degrees = -90.0f;
                mCamera.save();
                mCamera.rotateX(degrees);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            } else if (degrees >= 76.0f) {
                degrees = 90.0f;
                mCamera.save();
                mCamera.rotateX(degrees);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            } else {
                mCamera.save();
                // 这里很重要哦。
                mCamera.translate(0, 0, centerX);
                mCamera.rotateX(degrees);
                mCamera.translate(0, 0, -centerX);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            }
        } else {

            if (degrees <= -90.0f) {
                degrees = -90.0f;
                mCamera.save();
                mCamera.rotateX(degrees);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            } else if (degrees >= 90.0f) {
                degrees = 90.0f;
                mCamera.save();
                mCamera.rotateX(degrees);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            } else {
                mCamera.save();
                // 这里很重要哦。
//                mCamera.translate(0, 0, centerX);
                mCamera.rotateX(degrees);
//                mCamera.translate(0, 0, -centerX);
                mCamera.getMatrix(matrix);
                mCamera.restore();
            }
        }

        // mCamera.save();
        // // 这里很重要哦。
        // mCamera.translate(0, 0, centerX);
        // mCamera.rotateX(degrees);
        // mCamera.translate(0, 0, -centerX);
        // mCamera.getMatrix(matrix);
        // mCamera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }

    protected void applyTransformation2(float interpolatedTime, Transformation t) {
        final float FromDegree = mFromDegree;
        float degrees = FromDegree + (mToDegree - mFromDegree)
                * interpolatedTime;
        Log.i(TAG, "degrees=" + degrees);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Matrix matrix = t.getMatrix();

        if (degrees <= -76.0f) {
            degrees = -90.0f;
            mCamera.save();
            mCamera.rotateY(degrees);
            mCamera.getMatrix(matrix);
            mCamera.restore();
        } else if (degrees >= 76.0f) {
            degrees = 90.0f;
            mCamera.save();
            mCamera.rotateY(degrees);
            mCamera.getMatrix(matrix);
            mCamera.restore();
        } else {
            mCamera.save();
            // 这里很重要哦。
            mCamera.translate(0, 0, centerX);
            mCamera.rotateY(degrees);
            mCamera.translate(0, 0, -centerX);
            mCamera.getMatrix(matrix);
            mCamera.restore();
        }

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
