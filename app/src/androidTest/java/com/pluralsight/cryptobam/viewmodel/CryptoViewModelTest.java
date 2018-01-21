package com.pluralsight.cryptobam.viewmodel;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cryptoboom.data.entities.CryptoCoinEntity;
import com.cryptoboom.data.mappers.CryptoMapper;
import com.cryptoboom.data.models.CoinModel;
import com.cryptoboom.data.repository.CryptoRepositoryImpl;
import com.pluralsight.cryptobam.utils.CoinEntityGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.verify;

/**
 * Created by omrierez on 21.01.18.
 */
@RunWith(AndroidJUnit4.class)

public class CryptoViewModelTest {

    private final int NUM_OF_INSERT_COINS = 100;

    private CryptoRepositoryImpl repo;
    @Mock
    private Observer<Double> observer;

    private CryptoViewModel mViewModel;
    private Double totalMarketCap;

    @Before
    public void init() throws Exception

    {

        MockitoAnnotations.initMocks(this);
        Context context = InstrumentationRegistry.getTargetContext();
        repo = CryptoRepositoryImpl.createImpl(context);
        mViewModel = new CryptoViewModel((Application) context.getApplicationContext(), repo);

    }

    @Test
    public void testTotalMarketCap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<CryptoCoinEntity> coins = createRandomCoins();
        CryptoMapper mapper=new CryptoMapper();
        totalMarketCap = calculateTotalMarketCap(mapper.mapEntityToModel(coins));
        mViewModel.getTotalMarketCap().observeForever(observer);
        repo.insertAllCoins(coins);
        latch.await(5, TimeUnit.SECONDS);
        verify(observer).onChanged(totalMarketCap);

    }

    private List<CryptoCoinEntity> createRandomCoins() {
        List<CryptoCoinEntity> coins = new ArrayList<>();
        CryptoCoinEntity entity;
        for (int i = 0; i < NUM_OF_INSERT_COINS; i++) {
            entity = CoinEntityGenerator.createRandomEntity();
            coins.add(entity);
        }
        return coins;
    }

    private double calculateTotalMarketCap(List<CoinModel> coins) {
        double totalMK = 0;
        for (int i = 0; i < NUM_OF_INSERT_COINS; i++) {
            totalMK += coins.get(i).marketCap;
        }
        return totalMK;
    }

}
