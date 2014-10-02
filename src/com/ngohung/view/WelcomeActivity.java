package com.ngohung.view;

import net.waqassiddiq.app.introme.R;
import net.waqassiddiqi.app.introme.business.SubscriptionTask;
import net.waqassiddiqi.app.introme.model.Subscriber;
import net.waqassiddiqi.app.introme.sms.TextManager;
import net.waqassiddiqi.app.introme.util.PrefsUtil;
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

public class WelcomeActivity extends BaseActivity {
	
	private Button btnSubscriber;
	private View layoutWait, layoutForm;
	private EditText txtCellNo;
	private final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
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
				
				new SubscriptionTask(new Subscriber(txtCellNo.getText().toString())).perform();
			}
		});
	}
	
	@Override
    public void onResume() {
        super.onResume();

        registerReceiver(SubscriptionRequestReceiver, new IntentFilter(SMS_ACTION));
        
        if(PrefsUtil.get(getApplicationContext(), "is_subscribed", false) == false) {
			layoutForm.setVisibility(View.VISIBLE);
		} else {
			showContactView();
		}
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(SubscriptionRequestReceiver);
	}
	
	private void showContactView() {
		Intent intent = new Intent(WelcomeActivity.this, ContactListActivity.class);
		startActivity(intent);
		finish();
	}
	
	public BroadcastReceiver SubscriptionRequestReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(SMS_ACTION)) {
				String text = TextManager.parseTextMessage(context, intent);
				
				if(text != null) {
					PrefsUtil.put(getApplicationContext(), "is_subscribed", true);
					showContactView();
				}
			}
		}		
	};
}
