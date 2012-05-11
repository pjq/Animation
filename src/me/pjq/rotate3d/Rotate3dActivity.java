
package me.pjq.rotate3d;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import me.pjq.rotate3d.Rotate3d.TYPE;

import java.util.ArrayList;

public class Rotate3dActivity extends Activity implements OnClickListener {
    private static final String tag = Rotate3dActivity.class.getSimpleName();
    Rotate3d mRotate3dAnimation;
    Rotate3d mTranslateAnimation1;
    Rotate3d mTranslateAnimation2;
    int mCenterX = 240;
    int mCenterY = 155;
    public static final int DURATION = 500;
    private RelativeLayout mRelativeLayout;

    public static int HEIGHT = 155;
    public static int WIDTH = 480;
    public static int topMarginStep = 10;
    public static int leftMarginStep = 30;
    public static int rightMarginStep = leftMarginStep;
    public static int imageLeftPadding = 8;
    public static int imageRightPadding = 8;
    public static int imageTopPadding = 8;
    public static int imageBottomPadding = 8;

    public static int LEFT_APPEND_MARGIN = 40;
    public static int RIGHT_APPEND_MARGIN = 40;

    private ArrayList<Drawable> mDrawableArrayList;
    private ArrayList<ImageView> mImageViewArrayList;
    public static final int MAX_COUNT = 4;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        if (true) {
            initImageViews();

            return;
        }

        // CubeView cubeView=new CubeView(getApplicationContext());
        // MyCubeView cubeView = new MyCubeView(getApplicationContext());
        // addContentView(cubeView, new LayoutParams(LayoutParams.FILL_PARENT,
        // LayoutParams.FILL_PARENT));
        // setContentView(cubeView, new LayoutParams(LayoutParams.FILL_PARENT,
        // LayoutParams.FILL_PARENT));
        // getImageBounds();
    }

    ArrayList<ImageView> createImageViewArrayList() {
        int COUNT = MAX_COUNT;
        ArrayList<ImageView> arrayList = new ArrayList<ImageView>();

        Context context = getApplicationContext();
        for (int i = 0; i < COUNT; i++) {
            ImageView imageView = new ImageView(context);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    WIDTH, HEIGHT);

            int stepCount = i;
            // if (i == COUNT - 1) {
            // stepCount -= 1;
            // }

            int topPadding = imageTopPadding;

            if (i != 0) {
                // topPadding *= 3;
            }

            layoutParams.setMargins(leftMarginStep * stepCount + LEFT_APPEND_MARGIN,
                    topMarginStep * (COUNT - stepCount), rightMarginStep
                            * stepCount + RIGHT_APPEND_MARGIN, 0);

            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            imageView.setBackgroundResource(R.drawable.bg_drawable_white);
            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(imageLeftPadding, topPadding, imageRightPadding,
                    imageBottomPadding);
            imageView.setScaleType(ScaleType.FIT_XY);
            arrayList.add(imageView);
        }

        return arrayList;
    }

    private void addImageViews(ArrayList<ImageView> imageViews) {

        int length = imageViews.size();
        int index = 0;
        for (int i = length - 1; i >= 0; i--) {

            // Don't add the last item,just when click the image view to add it.
            if (i == length - 1) {

            } else {
                ImageView imageView = imageViews.get(i);
                mRelativeLayout.addView(imageView, index++);

            }
        }
    }

    private void addLastImageView(ArrayList<ImageView> imageViews) {
        int length = imageViews.size();
        ImageView imageView = imageViews.get(length - 1);
        mRelativeLayout.addView(imageView, 0);
    }

    private ArrayList<Drawable> getBitmapArrayList() {
        ArrayList<Drawable> arrayList = new ArrayList<Drawable>();

        for (int i = 0; i < MAX_COUNT; i++) {
            int remain = i % 3;

            if (0 == remain) {
                arrayList.add(getResources().getDrawable(R.drawable.photo1));
            } else if (1 == remain) {
                arrayList.add(getResources().getDrawable(R.drawable.photo2));
            } else if (2 == remain) {
                arrayList.add(getResources().getDrawable(R.drawable.photo3));
            }
        }

        return arrayList;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.image3:
            case R.id.image2:
            case R.id.image1: {
                executeAnimation();

                break;
            }

            default:

                executeAnimation();

                break;
        }

    }

    private Rotate3d createRotateAnimation() {
        // rotateDegree = 90 * mCount;
        Rotate3d animation = new Rotate3d(0, 90, 0.0f, 0.0f,
                mCenterX, mCenterY);
        animation.setType(TYPE.ROTATE);

        animation.setFillAfter(true);
        animation.setDuration(DURATION);

        return animation;
    }

    private int mStep = -70;

    // private int mTotal = -mStep * 2;

    private Rotate3d createTranslateAnimation1() {
        // rotateDegree = 90 * mCount;
        Rotate3d animation = new Rotate3d(0, mStep, 0.0f, 0.0f,
                mCenterX, mCenterY);
        animation.setType(TYPE.TRANSLATE);

        // animation.setFillAfter(true);
        animation.setDuration(DURATION);

        return animation;
    }

    private Rotate3d createLastTranslateAnimation() {
        // rotateDegree = 90 * mCount;
        Rotate3d animation = new Rotate3d(0, mStep, 0.0f, 0.0f,
                mCenterX, mCenterY);
        animation.setType(TYPE.LAST_ITEM);

        // animation.setFillAfter(true);
        animation.setDuration(DURATION);

        return animation;
    }

    private Animation loadLastAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.last_animation);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation loadMiddleAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.middle_animation);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation loadFirstAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.first_animation);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation loadSecondAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.second_animation);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation loadThirdAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.third_animation);
        animation.setFillAfter(true);
        return animation;
    }

    private Rotate3d createTranslateAnimation2() {
        // rotateDegree = 90 * mCount;
        Rotate3d animation = new Rotate3d(0, mStep, 0.0f, 0.0f,
                mCenterX, mCenterY);
        animation.setType(TYPE.TRANSLATE);

        // animation.setFillAfter(true);
        animation.setDuration(DURATION);

        return animation;
    }

    private TranslateAlphaScaleAnimation createAlphaScaleAnimation() {
        int width = Rotate3dActivity.WIDTH;
        int newWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep
                - Rotate3dActivity.rightMarginStep;
        float scale = (float) width / (float) newWidth;// 1.11
        Log.i(tag, "scale=" + scale);
        TranslateAlphaScaleAnimation animation = new TranslateAlphaScaleAnimation(0,
                0, 0, topMarginStep, 1f, scale, 1, 1, 50, 0, 0.5f,
                1.0f, mCenterX, mCenterY);

        return animation;
    }

    private void printScale() {
        int length = mImageViewArrayList.size();

        for (int i = 1; i < length; i++) {
            float scale = getScale(i);
            Log.i(tag, "scale=" + scale);
        }

    }

    /**
     * Get the scale
     * 
     * @param index
     */
    private float getScale(int index) {
        int preWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep * (index - 1)
                - Rotate3dActivity.rightMarginStep * (index - 1);
        int newWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep * index
                - Rotate3dActivity.rightMarginStep * index;
        float scale = (float) preWidth / (float) newWidth;
        // Log.i(tag, "scale=" + scale);

        return scale;
    }

    private int mCount = 0;
    private boolean mAlreadyLastImageView = false;

    private void executeAnimation() {
        // printScale();

        if (!mAlreadyLastImageView) {
            addLastImageView(mImageViewArrayList);
            mAlreadyLastImageView = true;
        }

        mRotate3dAnimation = createRotateAnimation();
        mTranslateAnimation1 = createTranslateAnimation1();
        mTranslateAnimation2 = createTranslateAnimation2();
        Rotate3d lastTranslateAnimation = createLastTranslateAnimation();

        ArrayList<Drawable> arrayList = mDrawableArrayList;

        int length = mImageViewArrayList.size();

        for (int i = 0; i < length; i++) {
            if (i == 0) {
                mImageViewArrayList.get(i).startAnimation(mRotate3dAnimation);
            } else {
                if (false) {

                    // Last item,not animation.
                    // if (i == length - 1) {
                    if (false) {
                        mImageViewArrayList.get(i).startAnimation(loadLastAnimation());

                    } else {
                        // mImageViewArrayList.get(i).startAnimation(mTranslateAnimation1);
                        // mImageViewArrayList.get(i).startAnimation(createAlphaScaleAnimation());
                        if (1 == i) {
                            mImageViewArrayList.get(i).startAnimation(loadFirstAnimation());
                        } else if (2 == i) {
                            mImageViewArrayList.get(i).startAnimation(loadSecondAnimation());
                        } else if (3 == i) {
                            mImageViewArrayList.get(i).startAnimation(loadThirdAnimation());
                        } else {
                            mImageViewArrayList.get(i).startAnimation(loadMiddleAnimation());
                        }

                    }
                } else {
                    mImageViewArrayList.get(i).startAnimation(createAnimationSet(i));
                }

            }
        }

        updateImageViews();

        mDrawableArrayList = rotateArrayList(arrayList);
        mCount++;
    }

    /**
     * Always get index=0 object and ,append to the list,then remove the index=0
     * object.
     * 
     * @param arrayList
     * @return
     */
    private ArrayList<Drawable> rotateArrayList(ArrayList<Drawable> arrayList) {

        Drawable drawable = arrayList.get(0);
        arrayList.add(drawable);
        arrayList.remove(0);

        return arrayList;
    }

    private void initImageViews() {
        mDrawableArrayList = getBitmapArrayList();
        mImageViewArrayList = createImageViewArrayList();
        addImageViews(mImageViewArrayList);

        int length = mImageViewArrayList.size();
        for (int i = 0; i < length; i++) {
            mImageViewArrayList.get(i).setOnClickListener(this);
        }
    }

    private void updateImageViews() {
        int length = mImageViewArrayList.size();

        for (int i = 0; i < length; i++) {
            ImageView imageView = mImageViewArrayList.get(i);
            imageView.setImageDrawable(mDrawableArrayList.get(i));
        }
    }

    // private int mDeltaX;
    // private int mDeltaY;
    private int mLastMotionX;
    private int mLastMotionY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int dx = x - mLastMotionX;
                int dy = y - mLastMotionY;
                Log.i(tag, "dx=" + dx + ",dy=" + dy);
                // move(dx, dy);
                // rotate(dx, dy);
                // mLastMotionX = x;
                // mLastMotionY = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                int dx = x - mLastMotionX;
                int dy = y - mLastMotionY;
                Log.i(tag, "dx=" + dx + ",dy=" + dy);
                move(dx, dy);
                break;
            }
        }
        return true;
    }

    private void move(int dx, int dy) {
        if (dy < topMarginStep) {
            return;
        }

        // int step = dy / topMarginStep;

        executeAnimation();
        // AlphaAnimation
    }

    private float mScaleStep = 1f;

    private AnimationSet createAnimationSet(int index) {
        int length = mImageViewArrayList.size();

        AnimationSet animationSet = new AnimationSet(true);
        // animationSet.setDuration(DURATION);
        animationSet.setFillAfter(true);
        float scaleX = getScale(index);
        Log.i(tag, "index=" + index + ",scaleX=" + scaleX);

        int newWidth = Rotate3dActivity.WIDTH - Rotate3dActivity.leftMarginStep * index
                - Rotate3dActivity.rightMarginStep * index;
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, scaleX, 1f, 1f, newWidth / 2,
                0f);
        scaleAnimation.setDuration(DURATION);
        scaleAnimation.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);

        float preScale = (length - index - 1) * mScaleStep;
        float toScale = (length - index) * mScaleStep;
        preScale = preScale > 1 ? 1 : preScale;
        toScale = toScale > 1 ? 1 : toScale;

        AlphaAnimation alphaAnimation = new AlphaAnimation(preScale, toScale);
        alphaAnimation.setDuration(DURATION);
        alphaAnimation.setFillAfter(true);
        animationSet.addAnimation(alphaAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                0, topMarginStep);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);

        // View view=new View(getApplicationContext());
        // view.startAnimation(animationSet);
        return animationSet;

    }
}
