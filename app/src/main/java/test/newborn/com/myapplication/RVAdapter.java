package test.newborn.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin-1 on 17-5-26.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {
    private List<String> list;
    private LayoutInflater mInflater;

    public RVAdapter(Context context, List<String> list) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RVHolder(mInflater.inflate(R.layout.layout_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public RVHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
