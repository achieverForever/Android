package com.example.weichenggwc.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Multi-Touch Test
 */
public class MultiTouchActivity extends Activity {
	public static final String TAG = "MultiTouchActivity";

	private FrameLayout mFrame;
	private List<MarkerView> mInActiveList;
	private Map<Integer, MarkerView> mMarkerMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.multitouch);

	    mFrame = (FrameLayout) findViewById(R.id.frame);
	    mFrame.setOnTouchListener(onTouchListener);

	    mInActiveList = new LinkedList<MarkerView>();
	    mMarkerMap = new HashMap<Integer, MarkerView>();
	    for (int i = 0; i < 15; i++) {
		    mInActiveList.add(new MarkerView(this));
	    }
	}

	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.d(TAG, event.toString());
			int pointerIdx, pointerId;

			switch (event.getActionMasked()) {

				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					pointerIdx = event.getActionIndex();
					pointerId = event.getPointerId(pointerIdx);

					if (mMarkerMap.get(pointerId) == null && mInActiveList.size() > 0) {
						float radius = mFrame.getWidth() / 2 / event.getPointerCount();
						MarkerView free = mInActiveList.remove(0);
						if (free != null) {
							free.init(event.getX(pointerIdx), event.getY(pointerIdx), radius);

							mMarkerMap.put(pointerId, free);
							mFrame.addView(free);
							updateMarkerViews();
						}
					}
					break;

				case MotionEvent.ACTION_MOVE:
					final int count = event.getPointerCount();
					for (int i = 0; i < count; i++) {
						pointerId = event.getPointerId(i);
						MarkerView m = mMarkerMap.get(pointerId);
						if (m != null) {
							m.setX(event.getX(i));
							m.setY(event.getY(i));
						}
					}
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					pointerIdx = event.getActionIndex();
					pointerId = event.getPointerId(pointerIdx);
					MarkerView free = mMarkerMap.remove(pointerId);

					if (free != null) {
						mFrame.removeView(free);
						mInActiveList.add(free);
						updateMarkerViews();
					}
					break;

				default:
			}
			return true;
		}
	};

	private void updateMarkerViews() {
		float baseRadius = mFrame.getWidth() / 2;
		float radius = mMarkerMap.size() == 0 ? baseRadius : baseRadius / mMarkerMap.size();
		for (MarkerView each : mMarkerMap.values()) {
			each.setRadius(radius);
		}
	}

	private class MarkerView extends View {
		private Paint mPaint;

		private float mX;
		private float mY;
		private float mRadius;

		private MarkerView(Context context) {
			super(context);
			init(0, 0, 0);
		}

		public void init(float cx, float cy, float radius) {
			mX = cx;
			mY = cy;
			mRadius = radius;
			int color = (new Random()).nextInt(0xffffff) | 0xff000000;
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(color);
			mPaint.setStyle(Paint.Style.FILL);
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawCircle(mX, mY, mRadius, mPaint);
		}

		public void setRadius(float radius) {
			mRadius = radius;
			invalidate();
		}

		public void setX(float x) {
			mX = x;
			invalidate();
		}

		public void setY(float y) {
			mY = y;
			invalidate();
		}
	}
}
