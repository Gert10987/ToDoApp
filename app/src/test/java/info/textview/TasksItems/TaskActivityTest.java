package info.textview.TasksItems;

import org.junit.Before;
import org.junit.Test;

import org.mockito.MockitoAnnotations;

import info.textview.DataBase.DataBaseToDo;


import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Paweł on 2016-06-03.
 */
public class TaskActivityTest {


    @Before
    public void setupAddNotePresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testGetTimeInIntegerFromString() {

        String testTime = "33:00:45";
        int testHours = 33;
        int testMinutes = 00;
        int testSeconds = 45;

        TasksActivity tasksActivity = mock(TasksActivity.class);

        when(tasksActivity.getSecondsFromDurationString(testTime)).
                thenReturn(testSeconds + (testMinutes * 60) + (testHours * 3600));

        assertEquals(tasksActivity.getSecondsFromDurationString(testTime),
                testSeconds + (testMinutes * 60) + (testHours * 3600));

    }

    @Test(expected=NullPointerException.class)
    public void testForNullPointerException() {
        // create an configure mock
        DataBaseToDo dataBaseToDo = mock(DataBaseToDo.class);
        doThrow(new NullPointerException()).when(dataBaseToDo).setNameOfTable(null);

        // use mock
        dataBaseToDo.setNameOfTable(null);
    }


}
