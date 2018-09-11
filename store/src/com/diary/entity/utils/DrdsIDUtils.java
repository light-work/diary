package com.diary.entity.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenjiaWang on 16/7/14.
 */
public class DrdsIDUtils {


    public static Map<DrdsTable, IdWorker> idWorkerMap = new HashMap<>();

    static {
        IdWorker sysIdWorker = new IdWorker(1);
        idWorkerMap.put(DrdsTable.RES, sysIdWorker);

        IdWorker entrustIdWorker = new IdWorker(2);
        idWorkerMap.put(DrdsTable.APP, entrustIdWorker);




    }

    public static Long getID(DrdsTable drdsTable) {
        return idWorkerMap.get(drdsTable).getId();
    }

    public static void main(String[] args) {
    }
}
