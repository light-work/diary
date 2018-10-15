package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserHouse;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.res.ResHouse;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserHouseBiz;
import com.diary.providers.store.app.AppUserHouseStore;
import com.diary.providers.store.app.AppUserLimitStore;
import com.diary.providers.store.app.AppUserManStore;
import com.diary.providers.store.app.AppUserStore;
import com.diary.providers.store.res.ResHouseStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserHouseBizImp extends BaseBiz implements UserHouseBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String buyHouse(Long userId, Long houseId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserManStore != null && resHouseStore != null && appUserHouseStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    if (appUser.getGender() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        ResHouse resHouse = resHouseStore.getById(houseId);
                        if (appUserMan != null && resHouse != null) {
                            Integer houseLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "HOUSE");
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            if (houseLimit == 0) {

                                if (appUserMan.getMoney() >= resHouse.getBuyPrice()) {
                                    AppUserLimit appUserLimit = new AppUserLimit();
                                    appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserLimit.setUserId(appUser);
                                    appUserLimit.setAction("HOUSE");
                                    appUserLimit.setDay(appUserMan.getDays());
                                    bind(appUserLimit, userId);
                                    appUserLimit.setUseYn("Y");

                                    AppUserHouse appUserHouse = new AppUserHouse();
                                    appUserHouse.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserHouse.setUserId(appUser);
                                    appUserHouse.setHouseId(resHouse);
                                    appUserHouse.setUseYn("Y");
                                    bind(appUserHouse, userId);

                                    AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                    appUserMan.setMoney(appUserMan.getMoney() - resHouse.getBuyPrice());
                                    effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    GameUtils.useHour(appUserMan);
                                    bind(appUserMan, userId);


                                    appUserHouseStore.buy(appUserHouse, Persistent.SAVE, appUserMan, appUserLimit);
                                    GameUtils.addResultArray(resultArray, "恭喜你,眼光独到,喜提:" + resHouse.getTitle(), null);
                                    GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                    result = 0;
                                } else {
                                    GameUtils.addResultArray(resultArray, "有梦想是好的，但是现实也需要真金白银！", null);
                                    result = 1;
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖房屋", null);
                                result = 1;
                            }
                            resultObj.put("result", result);
                            resultObj.put("resultArray", resultArray);

                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String sellHouse(Long userId, Long houseId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserManStore != null && appUserHouseStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = "";
                    if (appUser.getGender() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer carLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "HOUSE");
                            if (carLimit == 0) {
                                AppUserLimit appUserLimit = new AppUserLimit();
                                appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserLimit.setUserId(appUser);
                                appUserLimit.setAction("HOUSE");
                                appUserLimit.setDay(appUserMan.getDays());
                                bind(appUserLimit, userId);
                                appUserLimit.setUseYn("Y");

                                List<AppUserHouse> appUserHouseList = appUserHouseStore.getByUserIdHouseId(userId, houseId);
                                if (appUserHouseList != null && !appUserHouseList.isEmpty()) {
                                    AppUserHouse appUserHouse = appUserHouseList.get(0);
                                    if (appUserHouse != null) {
                                        ResHouse resHouse = appUserHouse.getHouseId();
                                        if (resHouse != null) {
                                            AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                            appUserMan.setMoney(appUserMan.getMoney() + resHouse.getSellPrice());
                                            effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                            GameUtils.useHour(appUserMan);
                                            bind(appUserMan, userId);
                                            appUserHouseStore.sell(appUserHouse, appUserMan, appUserLimit);
                                            GameUtils.addResultArray(resultArray, "成功出售房屋:" + resHouse.getTitle(), null);
                                            GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                            result = 0;
                                        }
                                    }
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖房屋", null);
                                result = 1;
                            }
                            resultObj.put("result", result);
                            resultObj.put("resultArray", resultArray);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }
}
