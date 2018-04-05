package com.example.frank.busmap;

import android.support.design.widget.FloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by frank on 26/03/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class FabTest {
    private MainActivity mainActivity ;
    @Mock
    private FloatingActionButton floatingActionButton;

    @Before
    public void setUp(){
        mainActivity = Mockito.mock(MainActivity.class);
        floatingActionButton = (FloatingActionButton) mainActivity.findViewById(R.id.fab_direction);
    }
    @Test
    public void a (){
        floatingActionButton.performClick();
    }
}
