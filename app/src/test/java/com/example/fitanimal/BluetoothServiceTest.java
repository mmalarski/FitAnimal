package com.example.fitanimal;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import android.content.Context;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import android.os.Handler;

public class BluetoothServiceTest {

    @Mock
    FragmentActivity mockFragment;
    @Mock
    Handler mockHandler;
    @Mock
    Context mockContext;
    @Mock
    BluetoothAdapter mockAdapter;

    @InjectMocks
    BluetoothService btService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        btService.BluetoothService(mockContext, mockHandler);
    }

    @Test
    public void getStateTest() {
        Assert.assertEquals(0, btService.getState());
    }

    @Test
    public void startTest() {
        btService.start();
        Assert.assertNull(btService.getConnectThread());
        Assert.assertNull(btService.getConnectedThread());
        Assert.assertNotNull(btService.getSecureAcceptThread());
        Assert.assertNotNull(btService.getInsecureAcceptThread());
    }

    @Test
    public void stopTest() {
        btService.stop();
        Assert.assertNull(btService.getConnectThread());
        Assert.assertNull(btService.getConnectedThread());
        Assert.assertNull(btService.getSecureAcceptThread());
        Assert.assertNull(btService.getInsecureAcceptThread());
        Assert.assertEquals(0, btService.getState());
    }

    //TODO(mock obtainMessage method in Handler class, probably using PowerMockito)
//    @Test
//    public void connectionFailedTest() {
//        btService.connectionFailed();
////        PowerMockito.when(Handler.obtainMessage)
////        Mockito.when(Handler.obtainMessage(any(Integer.class)))
//        Assert.assertEquals(0, btService.getState());
//    }

    @After
    public void tearDown() {
        btService = null;
    }
}
