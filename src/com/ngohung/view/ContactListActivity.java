package com.ngohung.view;

import java.util.ArrayList;
import java.util.List;

import net.waqassiddiq.app.introme.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.ngohung.example.adapter.ExampleContactAdapter;
import com.ngohung.example.models.ExampleContactItem;
import com.ngohung.example.models.ExampleDataSource;
import com.ngohung.widget.ContactItemInterface;

public class ContactListActivity extends ActionBarActivity implements
		TextWatcher {

	private ExampleContactListView listview;

	private EditText searchBox;
	private String searchString;

	private Object searchLock = new Object();

	private final static String TAG = "com.ngohung.view.ContactListActivity";

	List<ContactItemInterface> contactList;
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);

		getActionBar().setTitle("Mobilink IntroMe");
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		filterList = new ArrayList<ContactItemInterface>();
		contactList = ExampleDataSource
				.getPhoneContacts(getApplicationContext());

		ExampleContactAdapter adapter = new ExampleContactAdapter(this,
				R.layout.example_contact_item, contactList);

		listview = (ExampleContactListView) this.findViewById(R.id.listview);
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);

		// use this to process individual clicks
		// cannot use OnClickListener as the touch event is overrided by
		// IndexScroller
		// use last touch X and Y if want to handle click for an individual item
		// within the row
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {

				String num = "";

				if (filterList != null && filterList.size() > 0) {
					num = filterList.get(position).getPhoneNumber();
				} else {
					num = contactList.get(position).getPhoneNumber();
				}

				num = num.replace("+92", "");

				if (num.startsWith("0092")) {
					num = num.substring(4);
				} else if (num.startsWith("92")) {
					num = num.substring(2);
				}

				if (num.startsWith("0") == false)
					num = "0" + num;

				num = "98" + num;

				num = num.replace("-", "").trim();
				num.replace(" ", "").trim();

				Toast.makeText(ContactListActivity.this, num,
						Toast.LENGTH_SHORT).show();

				String uri = "tel:" + num;
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse(uri));
				startActivity(intent);

				/*
				 * float lastTouchX =
				 * listview.getScroller().getLastTouchDownEventX();
				 * if(lastTouchX < 45 && lastTouchX > -1){
				 * Toast.makeText(ContactListActivity.this,
				 * "User image is clicked ( " +
				 * contactList.get(position).getItemForIndex() + ")",
				 * Toast.LENGTH_SHORT).show(); } else
				 * Toast.makeText(ContactListActivity.this, "Nickname: " +
				 * contactList.get(position).getItemForIndex() ,
				 * Toast.LENGTH_SHORT).show();
				 */

			}
		});

		searchBox = (EditText) findViewById(R.id.input_search_query);
		searchBox.addTextChangedListener(this);
	}

	@Override
	public void afterTextChanged(Editable s) {

		searchString = searchBox.getText().toString().trim().toUpperCase();

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
			try {
				curSearchTask.cancel(true);
			} catch (Exception e) {
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString); // put it in a task so that ui is
												// not freeze
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// do nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String> {

		boolean inSearchMode = false;

		@Override
		protected String doInBackground(String... params) {
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode) {
				// get all the items matching this
				for (ContactItemInterface item : contactList) {
					ExampleContactItem contact = (ExampleContactItem) item;

					if ((contact.getNickName().toUpperCase().indexOf(keyword) > -1)) {
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result) {

			synchronized (searchLock) {

				if (inSearchMode) {

					ExampleContactAdapter adapter = new ExampleContactAdapter(
							ContactListActivity.this,
							R.layout.example_contact_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else {
					ExampleContactAdapter adapter = new ExampleContactAdapter(
							ContactListActivity.this,
							R.layout.example_contact_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

}
