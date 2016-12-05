package com.application.sample.selectcardviewprototype.app.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.application.sample.selectcardviewprototype.app.BuildConfig;
import com.application.sample.selectcardviewprototype.app.R;
import com.application.sample.selectcardviewprototype.app.model.Setting;
import com.google.android.gms.ads.AdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment
        implements OnRestoreRecyclerViewInterface {
    @Bind(R.id.settingsListViewId) ListView settingsListView;
    private static final String SETTINGS_ACTIONBAR_TITLE = "Settings";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        ButterKnife.bind(this, view);
        onInitView();
        return view;
    }

    /**
     *
     */
    private void onInitView() {
        Resources res = getActivity().getResources();
        setHasOptionsMenu(true);
        initActionbar();
        ArrayList<Setting> settingList = new ArrayList<Setting>();
        settingList.add(new Setting("Author", res.getString(R.string.author)));
        settingList.add(new Setting("Contacts", res.getString(R.string.contact_email)));
        settingList.add(new Setting("Build version", BuildConfig.VERSION_NAME));
        settingsListView.setAdapter(new SettingsAdapter(getActivity().getApplicationContext(),
                R.layout.setting_item, settingList));
    }


    /**
     *
     */
    public void initActionbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(SETTINGS_ACTIONBAR_TITLE);
        }
    }
    @Override
    public void onRestoreRecyclerView() {
    }

    @Override
    public void destroyBehavior() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return true;
    }

    /**
     * array adapter
     */
    public class SettingsAdapter extends ArrayAdapter<Setting> {
        private final ArrayList<Setting> settingList;

        /**
         *
         * @param context
         * @param resource
         * @param data
         */
        public SettingsAdapter(Context context, int resource, ArrayList<Setting> data) {
            super(context, resource, data);
            this.settingList = data;
        }

        /**
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            Setting settingObj = settingList.get(position);

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.setting_item, parent, false);
            }
            TextView label = (TextView) convertView.findViewById(R.id.settingLabelTextId);
            TextView description = ((TextView) convertView.findViewById(R.id.settingDescriptionTextId));

            label.setText(settingObj.getLabel());
            description.setVisibility(settingObj.getDescription() == null ? View.GONE : View.VISIBLE);
            description.setText(settingObj.getDescription());

            return convertView;
        }
    }
}
