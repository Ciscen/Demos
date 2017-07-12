package test.newborn.com.demos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import test.newborn.com.demos.R;

/**
 * Created by xiaochongzi on 17-7-8
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Integer> list;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, List<Integer> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.item, parent, false);
        return new ViewHOlder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHOlder) {
            ((ViewHOlder) holder).tv.setText("aldkjgak" + getEnter() + "dnga;i" + position);
        }
    }

    private String getEnter() {
        String s = "\n";
        String str = "";
        for (int i = 0; i < new Random().nextInt(5); i++) {
            str += s;
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHOlder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHOlder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}
