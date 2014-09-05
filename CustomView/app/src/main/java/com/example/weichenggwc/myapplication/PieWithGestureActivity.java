package com.example.weichenggwc.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Spinning Pie with Gesture Control
 */
public class PieWithGestureActivity extends Activity {
	public static final String TAG = "PieWithGestureActivity";

	/*private ScrollView mScrollView;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplepie);

		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		mTextView = (TextView) findViewById(R.id.textview);

	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_view);
	}
}
