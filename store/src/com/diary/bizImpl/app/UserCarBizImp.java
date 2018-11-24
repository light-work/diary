package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.entity.res.*;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserBiz;
import com.diary.providers.biz.app.UserCarBiz;
import com.diary.providers.store.app.*;
import com.diary.providers.store.res.*;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.OKHttpUtil;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.*;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserCarBizImp extends BaseBiz implements UserCarBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String buyCar(Long userId, Long carId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserManStore != null && resCarStore != null && appUserCarStore != null && appUserLimitStore != null
                    && resCarEffectStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 1) {

                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        ResCar resCar = resCarStore.getById(carId);
                        if (appUserMan != null && resCar != null) {
                            Integer carLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "CAR");
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            if (carLimit == 0) {
                                Integer currentBuyPrice = GameUtils.dynamicPrice(appUserMan.getDays(), resCar.getBuyPrice(), resCar.getOffsetBuy());
                                if (appUserMan.getMoney() >= currentBuyPrice) {
                                    List<ResCarEffect> carEffectList = resCarEffectStore.getListByCarId(carId);
                                    AppUserLimit appUserLimit = new AppUserLimit();
                                    appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserLimit.setUserId(appUser);
                                    appUserLimit.setAction("CAR");
                                    appUserLimit.setDay(appUserMan.getDays());
                                    bind(appUserLimit, userId);
                                    appUserLimit.setUseYn("Y");

                                    AppUserCar appUserCar = new AppUserCar();
                                    appUserCar.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserCar.setUserId(appUser);
                                    appUserCar.setCarId(resCar);
                                    appUserCar.setUseYn("Y");
                                    bind(appUserCar, userId);
                                    AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                    appUserMan.setMoney(appUserMan.getMoney() - currentBuyPrice);
                                    GameUtils.useEffect(carEffectList, appUserMan);
                                    effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    bind(appUserMan, userId);
                                    appUserCarStore.buy(appUserCar, Persistent.SAVE, appUserMan, appUserLimit);
                                    GameUtils.addResultArray(resultArray, "恭喜你,阔气！喜提:" + resCar.getTitle(), null);
                                    GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                    result = 0;
                                } else {
                                    GameUtils.addResultArray(resultArray, "有梦想是好的，但是现实也需要真金白银！", null);
                                    result = 1;
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖车辆", null);
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
    public String sellCar(Long userId, Long carId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserManStore != null && appUserCarStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer carLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "CAR");
                            if (carLimit == 0) {
                                AppUserLimit appUserLimit = new AppUserLimit();
                                appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserLimit.setUserId(appUser);
                                appUserLimit.setAction("CAR");
                                appUserLimit.setDay(appUserMan.getDays());
                                bind(appUserLimit, userId);
                                appUserLimit.setUseYn("Y");

                                List<AppUserCar> appUserCarList = appUserCarStore.getByUserIdCarId(userId, carId);
                                if (appUserCarList != null && !appUserCarList.isEmpty()) {
                                    AppUserCar appUserCar = appUserCarList.get(0);
                                    if (appUserCar != null) {
                                        ResCar resCar = appUserCar.getCarId();
                                        if (resCar != null) {
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserMan.getDays(), resCar.getSellPrice(), resCar.getOffsetSell());

                                            AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                            appUserMan.setMoney(appUserMan.getMoney() + currentSellPrice);
                                            effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                            bind(appUserMan, userId);
                                            appUserCarStore.sell(appUserCar, appUserMan, appUserLimit);
                                            GameUtils.addResultArray(resultArray, "成功出售车辆:" + resCar.getTitle(), null);
                                            GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                            result = 0;
                                        }
                                    }
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖车辆", null);
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
