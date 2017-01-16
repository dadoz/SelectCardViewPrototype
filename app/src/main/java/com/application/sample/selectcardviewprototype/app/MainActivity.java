package com.application.sample.selectcardviewprototype.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.application.sample.selectcardviewprototype.app.fragment.OnRestoreRecyclerViewInterface;
import com.application.sample.selectcardviewprototype.app.fragment.ContactListFragment;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;
import com.flurry.android.FlurryAgent;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.*;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.*;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlurryAgent.onStartSession(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new ContactListFragment())
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
        if (StatusSingleton.getInstance().getStatus() == SELECTED) {
            getFragment().onRestoreRecyclerView();
            StatusSingleton.getInstance().setStatus(NOT_SET);
            return;
        }

        if (getFragment() != null) {
            getFragment().destroyBehavior();
        }
        super.onBackPressed();
    }

    /**
     * get fragment
     * @return
     */
    private OnRestoreRecyclerViewInterface getFragment() {
        final int CONTACT_LIST_FRAGMENT_ID = 0;
        return ((OnRestoreRecyclerViewInterface) getSupportFragmentManager()
                .getFragments().get(CONTACT_LIST_FRAGMENT_ID));
    }

}
