package me.pjq.rotate3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * 图片三维翻转
 * @author chroya
 */
public class CubeView extends View {
    //摄像机
    private Camera mCamera;
    
    //翻转用的图片
    private Bitmap mBitmap;    
    private Matrix mMatrix = new Matrix();
    private Paint mPaint = new Paint();

    private int mLastMotionX, mLastMotionY;
    
    //图片的中心点坐标
    private int mCenterX, mCenterY;
    //转动的总距离，跟度数比例1:1
    private int mDeltaX, mDeltaY;
    //图片宽度高度
    private int mWidth, mHeight;
    
    public CubeView(Context context) {
        super(context);
        setWillNotDraw(false);
        mCamera = new Camera(); 
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo1);
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        mCenterX = mWidth>>1;
        mCenterY = mHeight>>1;
    }   
    
    /**
     * 转动
     * @param degreeX
     * @param degreeY
     */
    void rotate(int degreeX, int degreeY) {
        mDeltaX += degreeX;
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
    
    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);       
    }
}
