package info.textview.TasksItems;

import org.junit.Test;

import info.textview.DataBase.DataBaseToDo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Paweł on 2016-06-03.
 */
public class TaskActivityTest {


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

    @Test(expected = NullPointerException.class)
    public void testForNullPointerException() {
        // create an configure mock
        DataBaseToDo dataBaseToDo = mock(DataBaseToDo.class);
        doThrow(new NullPointerException()).when(dataBaseToDo).setNameOfTable(null);

        // use mock
        dataBaseToDo.setNameOfTable(null);
    }



}
