package com.ngohung.view;

import net.waqassiddiq.app.introme.R;
import net.waqassiddiqi.app.introme.Constants;
import net.waqassiddiqi.app.introme.business.IntroTask;
import net.waqassiddiqi.app.introme.sms.TextManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GeneralStatusDialogActivity extends Activity {
	
	Button btnOk;
	EditText txtMessage;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0.7f;				
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); 
		
		getWindow().setLayout(600, 600);
        setContentView(R.layout.general_status_dialog);
        
        btnOk = (Button) findViewById(R.id.btnOk);
        
        btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(txtMessage.getText().toString().isEmpty()) {
					Toast.makeText(GeneralStatusDialogActivity.this, 
							"Please enter valid Mobinlink number", Toast.LENGTH_SHORT).show();
					return;
				}
				
				new IntroTask(txtMessage.getText().toString().trim()).perform();
			}
		});
        
        txtMessage = (EditText) findViewById(R.id.txtMessage);
    }
	
	@Override
    public void onResume() {
        super.onResume();

        registerReceiver(SetIntroReceiver, new IntentFilter(Constants.SMS_ACTION));
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(SetIntroReceiver);
	}
	
	public BroadcastReceiver SetIntroReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.SMS_ACTION)) {
				String text = TextManager.parseTextMessage(context, intent);
				
				if(text != null) {
				}
			}
		}		
	};
}
