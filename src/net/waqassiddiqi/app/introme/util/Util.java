package net.waqassiddiqi.app.introme.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class Util {
	public static void showAsPopup(Activity activity) {
	    //To show activity as dialog and dim the background, you need to declare android:theme="@style/PopupTheme" on for the chosen activity on the manifest
	    activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
	    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
	            WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	    LayoutParams params = activity.getWindow().getAttributes(); 
	    params.height = 850; //fixed height
	    params.width = 850; //fixed width
	    params.alpha = 1.0f;
	    params.dimAmount = 0.5f;
	    activity.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); 
	    activity.getWindow().setLayout(850,850);
	}
}
