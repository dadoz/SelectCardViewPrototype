package com.application.sample.selectcardviewprototype.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.application.sample.selectcardviewprototype.app.fragment.ContactListFragment;
import com.application.sample.selectcardviewprototype.app.fragment.OnRestoreRecyclerViewInterface;
import com.application.sample.selectcardviewprototype.app.fragment.SettingsFragment;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.SELECTED;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.getInstance;


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
