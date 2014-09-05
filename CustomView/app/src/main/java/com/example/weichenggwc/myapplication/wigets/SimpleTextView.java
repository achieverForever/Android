package com.example.weichenggwc.myapplication.wigets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class SimpleTextView extends TextView {
	public static final String TAG = "SimpleTextView";

	public SimpleTextView(Context context) {
		super(context);
	}

	public SimpleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SimpleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(1080, 8000);
	}
}
