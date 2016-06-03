package info.textview.TasksItems;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import info.textview.R;



/**
 * Created by Paweł on 2016-06-03.
 */
public class TaskDetailFragment extends android.support.v4.app.Fragment {

    public TaskDetailFragment(){

    }

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detail, container, false);


        setView(root);

        return root;



    }

    private void setView(View root) {

        ImageView imageView = (ImageView) root.findViewById(R.id.imageViewFragmentDetali);
        imageView.setImageResource(R.drawable.ok_man);
    }








}
