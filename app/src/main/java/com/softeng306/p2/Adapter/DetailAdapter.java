package com.softeng306.p2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softeng306.p2.ViewModel.DetailModel;
import com.softeng306.p2.R;

import java.util.List;

public class DetailAdapter  extends ArrayAdapter<DetailModel> {

    //Define fields for later use in methods
    private Context activityContext;
    private List<DetailModel> list;
    public static final String TAG = "ListView";

    public DetailAdapter(Context context,List<DetailModel> list){
        super(context, R.layout.detail_item,list);
        this.activityContext = context;
        this.list = list;
    }

    /**
     * create a View containing information about vehicle details supplied by the database
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){
        final ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(activityContext).inflate(R.layout.detail_item, null);
            viewHolder = new ViewHolder();

            viewHolder.keyText = (TextView) view.findViewById(R.id.keyText);
            viewHolder.valueText = (TextView) view.findViewById(R.id.valueText);

            viewHolder.keyText.setText(list.get(position).getKey());
            viewHolder.valueText.setText(list.get(position).getValue());

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    /**
     * a private class holding view elements for each detail row
     */
    private static class ViewHolder{
        TextView keyText;
        TextView valueText;
    }
}
