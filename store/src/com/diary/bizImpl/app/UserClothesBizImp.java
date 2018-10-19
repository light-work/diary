package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserClothes;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.res.ResClothes;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserClothesBiz;
import com.diary.providers.store.app.AppUserClothesStore;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.providers.store.app.AppUserLimitStore;
import com.diary.providers.store.app.AppUserStore;
import com.diary.providers.store.res.ResClothesStore;
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

public class UserClothesBizImp extends BaseBiz implements UserClothesBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String buyClothes(Long userId, Long clothesId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserLadyStore != null && appUserClothesStore != null && resClothesStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 2) {

                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        ResClothes resClothes = resClothesStore.getById(clothesId);
                        if (appUserLady != null && resClothes != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer clothesLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "CLOTHES");

                            if (clothesLimit == 0) {


                                if (appUserLady.getMoney() >= resClothes.getBuyPrice()) {

                                    AppUserLimit appUserLimit = new AppUserLimit();
                                    appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserLimit.setUserId(appUser);
                                    appUserLimit.setAction("CLOTHES");
                                    appUserLimit.setDay(appUserLady.getDays());
                                    bind(appUserLimit, userId);
                                    appUserLimit.setUseYn("Y");

                                    AppUserClothes appUserClothes = new AppUserClothes();
                                    appUserClothes.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserClothes.setUserId(appUser);
                                    appUserClothes.setClothesId(resClothes);
                                    appUserClothes.setUseYn("Y");
                                    bind(appUserClothes, userId);
                                    AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                    appUserLady.setMoney(appUserLady.getMoney() - resClothes.getBuyPrice());
                                    effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                    GameUtils.useHour(appUserLady);
                                    bind(appUserLady, userId);
                                    appUserClothesStore.buy(appUserClothes, Persistent.SAVE, appUserLady, appUserLimit);

                                    GameUtils.addResultArray(resultArray, "恭喜你,穿上了:" + resClothes.getTitle() + "，我怎么这么好看，这么好看怎么办！", null);
                                    GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                    result = 0;
                                } else {
                                    GameUtils.addResultArray(resultArray, "爱美是好事，但是现实也需要真金白银！", null);
                                    result = 1;
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖服装", null);
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
    public String sellClothes(Long userId, Long clothesId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserLadyStore != null && appUserClothesStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 2) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer clothesLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "CLOTHES");
                            if (clothesLimit == 0) {
                                AppUserLimit appUserLimit = new AppUserLimit();
                                appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserLimit.setUserId(appUser);
                                appUserLimit.setAction("CLOTHES");
                                appUserLimit.setDay(appUserLady.getDays());
                                bind(appUserLimit, userId);
                                appUserLimit.setUseYn("Y");

                                List<AppUserClothes> appUserClothesList = appUserClothesStore.getByUserIdClothesId(userId, clothesId);
                                if (appUserClothesList != null && !appUserClothesList.isEmpty()) {
                                    AppUserClothes appUserClothes = appUserClothesList.get(0);
                                    if (appUserClothes != null) {
                                        ResClothes resClothes = appUserClothes.getClothesId();
                                        if (resClothes != null) {
                                            AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                            appUserLady.setMoney(appUserLady.getMoney() + resClothes.getSellPrice());
                                            effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                            GameUtils.useHour(appUserLady);
                                            bind(appUserLady, userId);
                                            appUserClothesStore.sell(appUserClothes, appUserLady, appUserLimit);
                                            GameUtils.addResultArray(resultArray, "已将不想要的" + resClothes.getTitle() + "衣服出售，这身衣服过时了！", null);
                                            GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                            result = 0;
                                        }
                                    }
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖衣服", null);
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
