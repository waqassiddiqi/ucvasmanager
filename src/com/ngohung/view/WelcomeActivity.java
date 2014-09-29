package com.ngohung.view;

import net.waqassiddiq.app.introme.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends BaseActivity {
	
	private Button btnSubscriber;
	private View layoutWait, layoutForm;
	private EditText txtCellNo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		                
		setContentView(R.layout.welcome_activitiy);
		
		layoutWait = findViewById(R.id.layoutWait);
		layoutForm = findViewById(R.id.layoutForm);
		
		txtCellNo = (EditText) findViewById(R.id.txtCellNo);
		
		btnSubscriber = (Button) findViewById(R.id.btnSubscriber);
		btnSubscriber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(txtCellNo.getText().toString().isEmpty()) {
					Toast.makeText(WelcomeActivity.this, "Please enter valid Mobinlink number", Toast.LENGTH_SHORT).show();
					return;
				}
				
				layoutWait.setVisibility(View.VISIBLE);
				layoutForm.setVisibility(View.GONE);
			}
		});
		
	}
}
