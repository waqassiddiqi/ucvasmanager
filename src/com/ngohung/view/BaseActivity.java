package com.ngohung.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	
	public void setupActionBar() {
		getActionBar().setTitle("Mobilink IntroMe");
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
	}
	
}
