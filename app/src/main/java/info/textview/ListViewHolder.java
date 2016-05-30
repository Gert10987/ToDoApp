package info.textview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2016-05-30.
 */
public class ListViewHolder extends ArrayAdapter<ItemToDoApp> {

    Context context;
    int layoutResourceId;
    ArrayList<ItemToDoApp> data = null;


    public ListViewHolder(Context context, int layoutResourceId, ArrayList<ItemToDoApp> data) {
        super(context, layoutResourceId, data);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ItemToDoAppHolder holder = null;

        if (row == null) {

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemToDoAppHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgView);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.time = (TextView)row.findViewById(R.id.txtTime);

            row.setTag(holder);


        }else{
            holder = (ItemToDoAppHolder) row.getTag();
        }

        ItemToDoApp object = data.get(position);
        holder.txtTitle.setText(object.title);
        holder.imgIcon.setImageResource(object.icon);
        holder.time.setText(object.time);

        return row;
    }

    static class ItemToDoAppHolder

    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView time;
    }
}
