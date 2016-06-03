package info.textview.TasksItems;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Paweł on 2016-06-03.
 */
public class TaskActivityTest {


    @Mock
    TasksActivity tasksActivity;


    @Before
    public void setupAddNotePresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testGetSecondsFormDuration(){

        tasksActivity = new TasksActivity();
        when(tasksActivity.getSecondsFromDurationString("00:00")).thenReturn(0);



    }

}
