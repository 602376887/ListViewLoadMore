package dd.com.loadmore;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView lv_load_more;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_load_more = (ListView) findViewById(R.id.lv_load_more);
        myAdapter = new MyAdapter();
        lv_load_more.setAdapter(myAdapter);
        final dd.com.loadmore.LoadMoreScrollListener endlessListScrollListener = new LoadMoreScrollListener(lv_load_more, R.layout.load_more);
        endlessListScrollListener.setListener(new LoadMoreScrollListener.EndlessListener() {
            @Override
            public void onLoadData() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.addFlag();
                        endlessListScrollListener.onLoadComplete();
                    }
                }, 1000);
            }

            @Override
            public boolean shouldLoadData() {
                return true;
            }
        });
        lv_load_more.setOnScrollListener(endlessListScrollListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends BaseAdapter {
        int flag = 20;

        public void addFlag() {
            flag += 20;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return flag;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getBaseContext());
            textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
            textView.setPadding(40, 40, 40, 40);
            textView.setText("position" + position);
            return textView;
        }
    }
}
