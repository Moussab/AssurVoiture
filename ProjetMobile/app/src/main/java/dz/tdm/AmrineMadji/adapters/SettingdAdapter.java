package dz.tdm.AmrineMadji.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dz.tdm.AmrineMadji.R;

/**
 * Created by Amine on 15/04/2017.
 */

public class SettingdAdapter extends RecyclerView.Adapter<SettingdAdapter.MyViewHolder>  {

    private List<String> stringList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView param;

        public MyViewHolder(View view) {
            super(view);
            param = (TextView) view.findViewById(R.id.param);
        }
    }

    public void setItem(int index,String item)
    {
        stringList.set(index, item);
        this.notifyItemChanged(index);
    }

    public void addItem(String item)
    {
        stringList.add(item);
        this.notifyItemInserted(stringList.size() - 1);
    }
    public SettingdAdapter(List<String> strings) {
        this.stringList = strings;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String string = stringList.get(position);
        holder.param.setText(string);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
