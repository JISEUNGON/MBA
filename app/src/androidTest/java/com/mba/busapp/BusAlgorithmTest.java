package com.mba.busapp;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class BusAlgorithmTest {
    int [] cityRequiredTime = {60,60,60,60,60,60};
    int [] mjRequiredTime = {60,60,60,60,60,60,60,60};
    int [] weekendRequiredTime = {60,60,60,60,60,60,60,60,60};

    BusAlgorithm busAlgorithm;

    @Before
    public void dataLoading(){
        busAlgorithm = new BusAlgorithm(ApplicationProvider.getApplicationContext());
    }


    @Test
    public void ArrivalDataTest(){
        //1. 방학
        //case 1) 중간 시간 대: 13:20 ~ 15:20
        //명지대역 방면: 6 정거장,
        ArrivalData arrivalData = busAlgorithm.getArrivalData(true, "명지대역", "14:20", "금", mjRequiredTime,cityRequiredTime,weekendRequiredTime);
        Assert.assertEquals("명지대역, 출발시간 + 6분","15:26",arrivalData.getBusArrivalTime().getTime());
        Assert.assertEquals("명지대역, 출발시간 + 9분","15:29", arrivalData.getArrivalTime().getTime());

        //case 1) 끝 시간 대: 13:20 ~ 15:20

    }

}
