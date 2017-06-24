package com.nikola.zadataktest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikola.zadataktest.R;

import java.util.ArrayList;

/**
 * Created by nikola on 6/24/17.
 */

public class DrawerListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NavigationItem> items;

    public DrawerListAdapter(Context context, ArrayList<NavigationItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
//        return 0;
        return items.size();
    }

    @Override
    public Object getItem(int position) {
//        return null;
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_list_item, null);
        } else {
            view = convertView;
        }

        TextView drawerTitle = (TextView) view.findViewById(R.id.tv_drawer_title);
        TextView drawerSubtitle = (TextView) view.findViewById(R.id.tv_drawer_subtitle);
        ImageView drawerIcon = (ImageView) view.findViewById(R.id.iv_drawer_icon);

        drawerTitle.setText(items.get(position).getTitle());
        drawerSubtitle.setText(items.get(position).getSubtitle());
        drawerIcon.setImageResource(items.get(position).getIcon());

        return view;

    }
}
