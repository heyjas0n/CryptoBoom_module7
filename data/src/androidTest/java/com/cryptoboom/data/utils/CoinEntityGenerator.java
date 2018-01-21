package com.cryptoboom.data.utils;

import com.cryptoboom.data.entities.CryptoCoinEntity;

import java.util.Random;
import java.util.UUID;

/**
 * Created by omrierez on 21.01.18.
 */

public class CoinEntityGenerator {

    private final static Random rand = new Random();

    public static CryptoCoinEntity createRandomEntity() {
        return new CryptoCoinEntity(UUID.randomUUID().toString(), "BTC", String.valueOf(rand.nextInt()),
                String.valueOf(rand.nextFloat()), String.valueOf(rand.nextFloat()),
                String.valueOf(rand.nextInt()), String.valueOf(rand.nextInt()));
    }
}
