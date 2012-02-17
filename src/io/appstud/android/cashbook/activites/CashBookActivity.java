package io.appstud.android.cashbook.activites;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.EntriesDataSource;
import io.appstud.android.cashbook.helpers.Entry;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class CashBookActivity extends ListActivity {

	private EntriesDataSource entriesDataSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		entriesDataSource = new EntriesDataSource(this);
		entriesDataSource.open();

		List<Entry> values = entriesDataSource.getAllEntries();
		ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_entry_menu_button:
			Intent intent = new Intent(this, AddEntry.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Entry> adapter = (ArrayAdapter<Entry>) getListAdapter();
		Entry entry = null;
		switch (view.getId()) {
		case R.id.add:
			entry = entriesDataSource.createEntry("Some Description", "Rs 500",
					"Credit");
			adapter.add(entry);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				entry = (Entry) getListAdapter().getItem(0);
				entriesDataSource.deleteEntry(entry);
				adapter.remove(entry);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		entriesDataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		entriesDataSource.close();
		super.onPause();
	}

}