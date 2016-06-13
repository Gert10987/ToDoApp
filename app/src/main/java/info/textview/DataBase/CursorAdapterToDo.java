package info.textview.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

                imageView.setImageResource(R.drawable.start_but_not_end);

                break;

            case 2:

                imageView.setImageResource(R.drawable.ok_man);

        }


    }

    private String getSumColumnTimeInteger(int timeInInteger)
    {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = timeInInteger % SECONDS_IN_A_MINUTE;
        int totalMinutes = timeInInteger / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return String.valueOf(hours + ":" + minutes + ":" + seconds);

    }
}
