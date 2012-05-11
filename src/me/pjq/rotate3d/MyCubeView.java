
package me.pjq.rotate3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyCubeView extends View {
    private static final String tag=MyCubeView.class.getSimpleName();
    private Context mContext;

    private int mCenterX;
    private int mCenterY;
    private Bitmap mBitmap;
    private Paint mPaint = new Paint();
    private Matrix mMatrix = new Matrix();
    private Camera mCamera;
    private int mHeight;
    private int mWidth;

    private int mDeltaX;
    private int mDeltaY;
    private int mLastMotionX;
    private int mLastMotionY;

    public MyCubeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init(context, attrs, defStyle);

    }

    public MyCubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs, 0);
    }

    public MyCubeView(Context context) {
        super(context);
        mContext = context;
        init(context, null, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        setWillNotDraw(false);
        mCamera = new Camera();
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo1);
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
        mCenterX = mWidth >> 1;
        mCenterY = mHeight >> 1;
    }

    
    public boolean onTouchEvent2(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // int action = event.getAction();

        Log.i(tag, "action="+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = x;
                mLastMotionY = y;

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                mDeltaX = x - mLastMotionX;
                mDeltaY = y - mLastMotionY;

                rotate(mDeltaX, mDeltaY);
                mLastMotionX = x;
                mLastMotionY = y;

                break;
            }

            default:
                break;
        }

        return true;
    }


    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mLastMotionX = x;
            mLastMotionY = y;
            break;
        case MotionEvent.ACTION_MOVE:
            int dx = x - mLastMotionX;
            int dy = y - mLastMotionY;
            rotate(dx, dy);
            mLastMotionX = x;
            mLastMotionY = y;
            break;
        case MotionEvent.ACTION_UP:
            break;
        }
        return true;
    }
    
    private void rotate2(int degreeX, int degreeY) {
        mDeltaX += degreeX;
        mDeltaY += degreeY;

        mCamera.save();

        mCamera.rotateX(-mDeltaY);
        mCamera.rotateY(mDeltaX);
        mCamera.translate(0, 0, -mCenterX);
        mCamera.getMatrix(mMatrix);

        mCamera.restore();

        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);
        mCamera.save();

        postInvalidate();
    }
    
    /**
     * 转动
     * @param degreeX
     * @param degreeY
     */
    void rotate(int degreeX, int degreeY) {
        mDeltaX += degreeX;
        mDeltaX=0;
        mDeltaY += degreeY;
        
        mCamera.save();
        mCamera.rotateY(mDeltaX);
        mCamera.rotateX(-mDeltaY);
        mCamera.translate(0, 0, -mCenterX);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();  
        //以图片的中心点为旋转中心,如果不加这两句，就是以（0,0）点为旋转中心
        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);        
        mCamera.save(); 
        
        postInvalidate();
    }   

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }

}
