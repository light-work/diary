package com.diary.entity.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gbcp on 15/8/8.
 */
public class GoldCoinUtils {


    public static final List<Integer> goldList = new ArrayList<>();
    public static final  Random rand = new Random();

    static {
        goldList.add(8);
        goldList.add(10);
        goldList.add(15);
        goldList.add(20);
        goldList.add(25);
    }

    public static Integer build() {
       return goldList.get(rand.nextInt(4));
    }


}
