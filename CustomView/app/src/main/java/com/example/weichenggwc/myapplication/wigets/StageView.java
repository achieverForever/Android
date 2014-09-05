package com.example.weichenggwc.myapplication.wigets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.weichenggwc.myapplication.R;

public class StageView extends View {
	public static final String TAG = "StageView";

	private Paint mPaint;

	private int mNumStages;
	private int mPassedColor;
	private int mYetPassedColor;
	private int mCurrPoint;

	private String[] mStageNames;
	private float mPointSize;

	public StageView(Context context) {
		super(context);
		init();
	}

	public StageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.StageView,
				0, 0);

		try {
			mNumStages = a.getInt(R.styleable.StageView_numStages, 0);
			mPassedColor = a.getColor(R.styleable.StageView_passedColor, 0xff0000ff);
			mYetPassedColor = a.getColor(R.styleable.StageView_yetPassedColor, 0xff1d1ddd);
			mCurrPoint = a.getInt(R.styleable.StageView_currPoint, 0);
		} finally {
			a.recycle();
		}

		init();
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		if (isInEditMode()) {
			mNumStages = 4;
		}

		mStageNames = new String[mNumStages];
		for (int i = 0; i < mStageNames.length; i++) {
			mStageNames[i] = "Stage " + (i + 1);
		}

		mPointSize = 50f;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		Log.d(TAG, "widthSize=" + widthSize);
		Log.d(TAG, "heightSize=" + heightSize);

		int measuredWidth = 0;
		int measuredHeight = 0;

		switch (widthMode) {
			case MeasureSpec.AT_MOST:
			case MeasureSpec.EXACTLY:
				measuredWidth = widthSize;
				measuredHeight = 3 * measuredWidth / 4;
				break;
			case MeasureSpec.UNSPECIFIED:
				measuredWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
				measuredHeight = 3 * measuredWidth / 4;
				break;
		}

		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Draw the passed line
		mPaint.setColor(mPassedColor);
		mPaint.setStrokeWidth(8);

		float totalLength = getMeasuredWidth() * 0.8f;
		float startX = getMeasuredWidth() * 0.1f;
		float stopX = totalLength * (mCurrPoint + 1) / mNumStages;

		canvas.drawLine(
				startX, getMeasuredHeight() / 3,
				stopX, getMeasuredHeight() / 3,
				mPaint);

		// Draw the yet passed line
		mPaint.setColor(mYetPassedColor);

		startX = totalLength * (mCurrPoint + 1) / mNumStages;
		stopX = 0.9f * totalLength;

		canvas.drawLine(
				startX, getMeasuredHeight() / 3,
				stopX, getMeasuredHeight() / 3,
				mPaint);

		// Draw the passed points
		mPaint.setStyle(Paint.Style.FILL);

		final float step = totalLength / mNumStages;
		for (int i = 0; i < mCurrPoint - 1; i++) {
			float x = i * step;
			float y = getMeasuredHeight() / 3;
			canvas.drawCircle(x, y, 20f, mPaint);
		}

		canvas.drawCircle(mCurrPoint * step, getMeasuredHeight() / 3, 40f, mPaint);

		// Draw the yet passed points
		mPaint.setColor(mYetPassedColor);

		for (int i = mCurrPoint + 1; i < mNumStages; i++) {
			float x = i * step;
			float y = getMeasuredHeight() / 3;
			canvas.drawCircle(x, y, 20f, mPaint);
		}

		// Draw the stages' text
		mPaint.setColor(0xff000000);
		mPaint.setTextAlign(Paint.Align.CENTER);

		for (int i = 0; i < mNumStages; i++) {
			float x = i * step;
			float y = getMeasuredHeight() / 3 * 2;
			canvas.drawText(mStageNames[i], x, y, mPaint);
		}

	}
}
