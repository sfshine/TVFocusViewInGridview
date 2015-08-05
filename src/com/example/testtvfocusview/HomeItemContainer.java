package com.example.testtvfocusview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.testtvgridview.R;

public class HomeItemContainer extends LinearLayout
{
    
    private static final float SCALEFACTOR = 1.2f;
    private Rect mBound;
    private Drawable mDrawable;
    private Rect mRect;
    private AnimatorSet mAnimatorSetZoomOut;
    private AnimatorSet mAnimatorSetZoomIn;
    private int SELECT_PADDING = 10;
    private boolean isSelected;
    private Drawable mNormalDrawable;
    
    public HomeItemContainer(Context context) {
        super(context);
        init();
    }
    
    public HomeItemContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    public HomeItemContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    protected void init() {
        setWillNotDraw(false);
        mRect = new Rect();
        mBound = new Rect();
        mDrawable = getResources().getDrawable(R.drawable.btn_common_focused);//nav_focused_2,poster_shadow_4  
        mNormalDrawable = getResources().getDrawable(R.drawable.btn_common_normal);//nav_focused_2,poster_shadow_4  
        
        setChildrenDrawingOrderEnabled(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOrientation(LinearLayout.VERTICAL);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (isSelected) {
            super.getDrawingRect(mRect);
            mBound.set(mRect.left - SELECT_PADDING, mRect.top - SELECT_PADDING, mRect.right + SELECT_PADDING, mRect.bottom + SELECT_PADDING);
            mDrawable.setBounds(mBound);
            canvas.save();
            mDrawable.draw(canvas);
            canvas.restore();
        }
        else {
            canvas.save();
            mNormalDrawable.draw(canvas);
            canvas.restore();
        }
        super.onDraw(canvas);
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
        if (isSelected) {
            zoomIn();
        }
        else {
            zoomOut();
        }
        // postInvalidate();
    }
    
    private void zoomOut() {
        Log.d("HomeItemContainer", "zoomOut");
        if (this.getScaleX() == SCALEFACTOR) {
            //缩小动画
            if (mAnimatorSetZoomIn == null) {
                mAnimatorSetZoomIn = new AnimatorSet();
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", SCALEFACTOR, 1.0f);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", SCALEFACTOR, 1.0f);
                animatorX.setDuration(300);
                animatorY.setDuration(300);
                mAnimatorSetZoomIn.playTogether(animatorX, animatorY);
            }
            mAnimatorSetZoomIn.start();
        }
        
    }
    
    private void zoomIn() {
        Log.d("HomeItemContainer", "zoomIn");
        
        //放大动画
        if (mAnimatorSetZoomOut == null) {
            mAnimatorSetZoomOut = new AnimatorSet();
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, SCALEFACTOR);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, SCALEFACTOR);
            animatorX.setDuration(300);
            animatorY.setDuration(300);
            mAnimatorSetZoomOut.playTogether(animatorX, animatorY);
        }
        mAnimatorSetZoomOut.start();
    }
}
