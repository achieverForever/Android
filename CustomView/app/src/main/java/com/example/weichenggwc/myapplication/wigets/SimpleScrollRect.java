package com.example.weichenggwc.myapplication.wigets;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.example.weichenggwc.myapplication.R;

public class SimpleScrollRect extends ScrollView {
	public static final String TAG = "SimpleScrollRect";

	private Paint mRectPaint;
	private Scroller mScroller;

	private View mChild;

	private GestureDetector mGestureDetector;

	public SimpleScrollRect(Context context) {
		this(context, null);
	}

	public SimpleScrollRect(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mChild.layout(l, t, r, b);
	}

	private void init() {
		mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPaint.setStyle(Paint.Style.FILL);

		mScroller = new Scroller(getContext(), null, true);

		mGestureDetector = new GestureDetector(getContext(), new GestureListener());
		mGestureDetector.setIsLongpressEnabled(false);

		mChild = new View(getContext());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mRectPaint.setShader(new SweepGradient(0, 0, 0xffff0000, 0xff00ff00));
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.t), 0, 0, mRectPaint);
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			mScroller.computeScrollOffset();
			SimpleScrollRect.this.setScrollX(mScroller.getCurrX());
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			mScroller.fling(
					0,
					0,
					(int) velocityX / 5,
					(int) velocityY / 5,
					0,
					Integer.MAX_VALUE,
					0,
					Integer.MAX_VALUE
			);
			return true;
		}
	}

}
