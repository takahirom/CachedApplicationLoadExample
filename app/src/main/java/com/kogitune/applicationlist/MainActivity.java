package com.kogitune.applicationlist;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<List<App>> {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.app_grid);

        getLoaderManager().initLoader(0, null, this);

    }

    private ArrayAdapter getAdapter(List<App> appList) {
        return new ArrayAdapter<App>(this, 0, appList) {
            LayoutInflater mInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            class ViewHolder {
                ImageView imageView;
                TextView textView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                App item = getItem(position);

                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.cell, null);
                }
                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.app_icon);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.app_name);
                    convertView.setTag(viewHolder);

                }
                viewHolder.imageView.setImageDrawable(item.getIcon(MainActivity.this));
                viewHolder.textView.setText("p:" + position);


                return convertView;
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<App>> onCreateLoader(int id, Bundle args) {
        AppListLoader appLoader = new AppListLoader(getApplication());

        // start loader
        appLoader.forceLoad();
        return appLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<App>> loader, List<App> data) {
        gridView.setAdapter(getAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<App>> loader) {

    }
}
