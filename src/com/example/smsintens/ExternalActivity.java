package com.example.smsintens;

import com.example.smsintens.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExternalActivity extends Activity {
	Button sendButton;
	EditText editNumber;
	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_external);
		sendButton =  (Button) findViewById(R.id.sendButton);
		editNumber = (EditText) findViewById(R.id.editNumber);
		editText = (EditText) findViewById(R.id.editText);
		sendButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				String phoneNo = editNumber.getText().toString();
				String message = editText.getText().toString();
				if (phoneNo.length()>0 && message.length()>0){
					sendSms(phoneNo, message);
				}
				else
				{
					Toast.makeText(getBaseContext(), "Please enter both phone number and message. ",
					Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
		//---sends an SMS message to another device----
		private void sendSms(String phoneNumber, String message)
		{
			String SENT = "SMS_SENT";
			String DELIVERED = "SMS_DELIVERED";
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
			PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);
		//===when the SMS has been sent---
		registerReceiver(new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
			switch (getResultCode())
			{
			case Activity.RESULT_OK:
				Toast.makeText(getBaseContext(), "SMS sent", 
						Toast.LENGTH_SHORT).show();
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:	
				Toast.makeText(getBaseContext(), "Generic Failure",
						Toast.LENGTH_SHORT).show();
				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:	
				Toast.makeText(getBaseContext(), "No service",
						Toast.LENGTH_SHORT).show();
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:	
				Toast.makeText(getBaseContext(), "Null PDU",
						Toast.LENGTH_SHORT).show();
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:	
				Toast.makeText(getBaseContext(), "Radio off",
						Toast.LENGTH_SHORT).show();
				
			}
			}
		}, new IntentFilter(SENT));
		//===when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver(){
			public void onReceive(Context arg0, Intent arg1){
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered", 
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered", 
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
		}
		
	}
