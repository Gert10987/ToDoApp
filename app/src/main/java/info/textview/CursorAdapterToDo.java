package info.textview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

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
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        txtTitle.setText(title);

        TextView txtTime = (TextView) view.findViewById(R.id.txtTitle);
        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        txtTime.setText(title);

    }
}
