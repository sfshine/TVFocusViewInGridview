package com.example.testtvfocusview;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.TextView;
import com.example.testtvgridview.R;
import com.su.adapter.DpBaseAdapter;

public class MainActivity extends Activity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView1 = (GridView) findViewById(R.id.gridView1);
        
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 53; i++) {
            data.add("测试数据" + i);
        }
        
        final TVAdapter tvdapter = new TVAdapter(this);
        tvdapter.setData(data);
        gridView1.setAdapter(tvdapter);
        gridView1.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvdapter.setSelectedPosition(position);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        gridView1.setOnScrollListener(new OnScrollListener()
        {
            
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    tvdapter.setSelectedPosition(-1);
                }
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
        });
        
    }
    
    class TVAdapter extends DpBaseAdapter<String>
    {
        private int mSelectedPosition = -1;
        
        public void setSelectedPosition(int position) {
            Log.d("MyListAdapter", "selectedPosition " + position);
            mSelectedPosition = position;
            notifyDataSetChanged();
        }
        
        public TVAdapter(Context context) {
            super(context);
        }
        
        @Override
        public int onGetItemLayout() {
            return R.layout.tv_grid_item;
        }
        
        @Override
        public void onGetView(View convertView, int position) {
            TextView textView = findViewById(convertView, R.id.title);
            textView.setText(mDataList.get(position));
            if (mSelectedPosition == position) {
                ((HomeItemContainer) convertView).setSelected(true);
            }
            else {
                ((HomeItemContainer) convertView).setSelected(false);
                
            }
        }
        
    }
}
