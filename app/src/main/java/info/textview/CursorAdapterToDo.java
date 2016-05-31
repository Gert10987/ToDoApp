package info.textview;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.textview.DataBase.DataBaseToDo;

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
        String time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseToDo.COLUMN_TIME));
        txtTime.setText(time);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);
        int isDone = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseToDo.COLUMN_IS_DONE));

        switch (isDone){

                case 0:

                    imageView.setImageResource(R.drawable.lets_do_it);

                    break;

                case 1:

                    imageView.setImageResource(R.drawable.ok_man);

                    break;

        }


    }
}
