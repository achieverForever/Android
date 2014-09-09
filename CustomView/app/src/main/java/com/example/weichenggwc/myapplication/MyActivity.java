/* Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.example.weichenggwc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener {
	public static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_my);

	    findViewById(R.id.btn_pie_chart).setOnClickListener(this);
	    findViewById(R.id.btn_multi_touch).setOnClickListener(this);
	    findViewById(R.id.btn_pie_with_gesture).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent;
		if (v.getId() ==R.id.btn_pie_chart) {
			intent = new Intent(this, PieChartActivity.class);
		} else if (v.getId() == R.id.btn_multi_touch) {
			intent = new Intent(this, MultiTouchActivity.class);
		} else {
			intent = new Intent(this, StageViewActivity.class);
		}
		startActivity(intent);
	}
}

