package info.textview.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import info.textview.DataBase.DataBaseToDo;
import info.textview.R;

/**
 * Created by Paweł on 2016-05-30.
 */
public class CursorAdapterToDo extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public CursorAdapterToDo(Context context, Cursor c, int flags) {
        super(context, c, flags);

        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.lis_view_record, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseToDo.COLUMN_TITLE));
        txtTitle.setText(title);

        TextView txtTime = (TextView) view.findViewById(R.id.txtTime);
        int time = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseToDo.COLUMN_TIME_INTEGER));

        txtTime.setText(getSumColumnTimeInteger(time));

        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);
        int isDone = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseToDo.COLUMN_IS_DONE));

        switch (isDone) {

            case 0:

                imageView.setImageResource(R.drawable.lets_do_it);

                break;

            case 1:

                imageView.setImageResource(R.drawable.doing);

                break;

            case 2:

                imageView.setImageResource(R.drawable.ok_man);

        }


    }

    private String getSumColumnTimeInteger(int timeInInteger)
    {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date(timeInInteger*1000L));

        return time;

    }
}