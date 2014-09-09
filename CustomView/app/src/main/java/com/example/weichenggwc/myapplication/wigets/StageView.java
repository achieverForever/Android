package com.example.weichenggwc.myapplication.wigets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.weichenggwc.myapplication.R;

public class StageView extends View implements View.OnClickListener {
	public static final String TAG = "StageView";

	private Paint mPaint;

	private int mNumStages;
	private float mLineWidth;
	private int mPassedColor;
	private int mUnPassedColor;
	private int mCurrPosition;
	private float mTextSize;
	private float mTextPadding;

	private String[] mStageNames;
	private float mPointSize = 50f;

	public StageView(Context context) {
		this(context, null);
	}

	public StageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.StageView,
				0, 0);

		try {
			mNumStages = a.getInt(R.styleable.StageView_numStages, 0);
			mLineWidth = a.getDimension(R.styleable.StageView_lineWidth, 80f);
			mPassedColor = a.getColor(R.styleable.StageView_passedColor, 0xff0000ff);
			mUnPassedColor = a.getColor(R.styleable.StageView_unpassedColor, 0xff1d1ddd);
			mCurrPosition = a.getInt(R.styleable.StageView_currentPosition, 0);
			mTextSize = a.getDimension(R.styleable.StageView_textSize, 10f);
			mTextPadding = a.getDimension(R.styleable.StageView_textPadding, 10f);
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

		setOnClickListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int widthMeasureSpec) {
		float result;
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int widthPixels = getResources().getDisplayMetrics().widthPixels;

		if (widthMode == MeasureSpec.EXACTLY) {
			return widthSize;
		} else {
			result = getPaddingLeft() + getPaddingRight() + mLineWidth * (mNumStages - 1);
			if (widthMode == MeasureSpec.AT_MOST) {
				result = Math.min(widthSize, result);
			}
			return (int) result;
		}
	}

	private int measureHeight(int heightMeasureSpec) {
		float result;
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		if (heightMode == MeasureSpec.EXACTLY) {
			return heightSize;
		} else {
			result = getPaddingTop() + getPaddingBottom() + mTextPadding + mTextSize * 4;
			if (heightSize == MeasureSpec.AT_MOST) {
				result = Math.min(heightSize, result);
			}
			return (int) result;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		final int paddingLeft = getPaddingLeft();
		final int paddingRight = getPaddingRight();
		final int paddingTop = getPaddingTop();
		final int paddingBottom = getPaddingBottom();
		final float totalLength = mLineWidth * (mNumStages - 1);

		// Draw the passed part of line
		mPaint.setColor(mPassedColor);
		mPaint.setStrokeWidth(8);

		float horizontalOffset = paddingLeft + (getWidth() - paddingLeft - paddingRight) / 2 - totalLength / 2;
		float verticalOffset = paddingTop + (getHeight() - paddingTop - paddingBottom) / 2;
		float startX = horizontalOffset;
		float stopX = startX + mLineWidth * mCurrPosition;

		canvas.drawLine(
				startX, verticalOffset,
				stopX, verticalOffset,
				mPaint);

		// Draw the unpassed part of line
		mPaint.setColor(mUnPassedColor);

		startX = stopX;
		stopX = horizontalOffset + mLineWidth * (mNumStages - 1);

		canvas.drawLine(
				startX, verticalOffset,
				stopX, verticalOffset,
				mPaint);

		// Draw the passed points
		mPaint.setColor(mPassedColor);
		mPaint.setStyle(Paint.Style.FILL);
		final float step = mLineWidth;
		for (int i = 0; i < mCurrPosition; i++) {
			float x = horizontalOffset + i * step;
			float y = verticalOffset;
			canvas.drawCircle(x, y, 20f, mPaint);
		}

		// Draw the current point
		canvas.drawCircle(horizontalOffset + mCurrPosition * step, verticalOffset, 30f, mPaint);

		// Draw the unpassed points
		mPaint.setColor(mUnPassedColor);
		for (int i = mCurrPosition + 1; i < mNumStages; i++) {
			float x = horizontalOffset + i * step;
			float y = verticalOffset;
			canvas.drawCircle(x, y, 20f, mPaint);
		}

		// Draw the stages' text
		mPaint.setColor(0xff000000);
		mPaint.setTextSize(mTextSize);
		mPaint.setTextAlign(Paint.Align.CENTER);

		float y = verticalOffset + mTextPadding + 2 * mTextSize;
		for (int i = 0; i < mNumStages; i++) {
			float x = horizontalOffset + i * step;
			canvas.drawText(mStageNames[i], x, y, mPaint);
		}
	}

	public void setCurrentPosition(int index) {
		mCurrPosition = index;
		invalidate();
	}

	@Override
	public void onClick(View v) {
		setCurrentPosition((mCurrPosition + 1) % mNumStages);
	}
}
