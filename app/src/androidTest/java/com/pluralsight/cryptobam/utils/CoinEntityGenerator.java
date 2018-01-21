package com.pluralsight.cryptobam.utils;

import com.cryptoboom.data.entities.CryptoCoinEntity;

import java.util.Random;

/**
 * Created by omrierez on 21.01.18.
 */

public class CoinEntityGenerator {

    private final static Random rand = new Random();
    private static int id=0;
    public static CryptoCoinEntity createRandomEntity() {
        CryptoCoinEntity entity = new CryptoCoinEntity();
        entity.setId(String.valueOf(id++));
        entity.setSymbol("BTC");
        entity.setName("Bitcoin");
        entity.setRank(String.valueOf(rand.nextInt()));
        entity.setPriceUsd(String.valueOf(rand.nextFloat()));
        entity.setMarketCapUsd(String.valueOf(rand.nextInt(100)));
        return entity;
    }
}
