package com.application.sample.selectcardviewprototype.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.application.sample.selectcardviewprototype.app.fragment.OnRestoreRecyclerViewInterface;
import com.application.sample.selectcardviewprototype.app.fragment.ShoppingListFragment;
import com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton;

import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.*;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.*;
import static com.application.sample.selectcardviewprototype.app.singleton.StatusSingleton.StatusEnum.NOT_SET;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private StatusSingleton mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatus = getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ShoppingListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mStatus.getStatus() == SELECTED) {
            getFragment().onRestoreRecyclerView();
            mStatus.setStatus(NOT_SET);
            return;
        }
        getFragment().destroyBehavior();
        super.onBackPressed();
    }

    private OnRestoreRecyclerViewInterface getFragment() {
        int size = getSupportFragmentManager().getFragments().size();
        return ((OnRestoreRecyclerViewInterface) getSupportFragmentManager()
                .getFragments().get(size - 1));
    }

}
