package com.example.smsintens;

import com.example.smsintens.R;
import com.example.smsintens.MainActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button b1, b2;
	int request_Code = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 =(Button)findViewById(R.id.messageinternal);
		b1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0){
				Intent i = new
				Intent(android.content.Intent.ACTION_SENDTO,Uri.parse("smsto:"));
				startActivity(i);
			}
		});

	}
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == request_Code)
		{
			if(resultCode == RESULT_OK)
			{
				Toast.makeText(this,data.getData().toString(),
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(
						android.content.Intent.ACTION_VIEW,
						Uri.parse(data.getData().toString()));
				startActivity(i);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void messageExternal(View v)
	{
		Intent i=new Intent(this,ExternalActivity.class);
		startActivity(i);
	}
	public void aboutMe(View v)
	{
		Intent i=new Intent(this,AboutActivity.class);
		startActivity(i);
	}

}
