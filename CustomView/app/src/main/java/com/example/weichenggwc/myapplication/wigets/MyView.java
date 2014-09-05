package com.example.weichenggwc.myapplication.wigets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.weichenggwc.myapplication.R;

import java.util.Random;

public class MyView extends View {
	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(100f);

		final float step = 100f;

		Random rand = new Random();
		final int width = getWidth();
		final int height = getHeight();
		for (float x = 0f; x < width; x += step) {
			for (float y = 0f; y < height; y += step) {
				int color = rand.nextInt(0x1000000) + 0xFF000000;
				paint.setColor(color);
				canvas.drawPoint(0.5f * step + x, 0.5f * step + y, paint);

			}
		}
	}

	Bitmap getRoundedCornerBitmap(Bitmap in, int width, int height, int radius) {
		Bitmap out = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(out);

		final int color = 0xffff0000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		final float roundPx = radius;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(in, rect, rect, paint);

		return out;
	}
}
