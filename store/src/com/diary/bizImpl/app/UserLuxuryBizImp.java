package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserLuxury;
import com.diary.entity.res.ResLuxury;
import com.diary.entity.res.ResLuxuryEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserLuxuryBiz;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.providers.store.app.AppUserLimitStore;
import com.diary.providers.store.app.AppUserLuxuryStore;
import com.diary.providers.store.app.AppUserStore;
import com.diary.providers.store.res.ResLuxuryEffectStore;
import com.diary.providers.store.res.ResLuxuryStore;
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

public class UserLuxuryBizImp extends BaseBiz implements UserLuxuryBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String buyLuxury(Long userId, Long luxuryId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserLadyStore != null && appUserLuxuryStore != null && resLuxuryStore != null && appUserLimitStore != null
                    &&resLuxuryEffectStore!=null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 2) {

                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        ResLuxury resLuxury = resLuxuryStore.getById(luxuryId);
                        if (appUserLady != null && resLuxury != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer luxuryLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "LUXURY");

                            if (luxuryLimit == 0) {
                                Integer currentBuyPrice = GameUtils.dynamicPrice(appUserLady.getDays(), resLuxury.getBuyPrice(), resLuxury.getOffsetBuy());

                                if (appUserLady.getMoney() >= currentBuyPrice) {
                                    List<ResLuxuryEffect> luxuryEffectList = resLuxuryEffectStore.getListByLuxuryId(luxuryId);
                                    AppUserLimit appUserLimit = new AppUserLimit();
                                    appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserLimit.setUserId(appUser);
                                    appUserLimit.setAction("LUXURY");
                                    appUserLimit.setDay(appUserLady.getDays());
                                    bind(appUserLimit, userId);
                                    appUserLimit.setUseYn("Y");

                                    AppUserLuxury appUserLuxury = new AppUserLuxury();
                                    appUserLuxury.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserLuxury.setUserId(appUser);
                                    appUserLuxury.setLuxuryId(resLuxury);
                                    appUserLuxury.setUseYn("Y");
                                    bind(appUserLuxury, userId);
                                    AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                    appUserLady.setMoney(appUserLady.getMoney() - currentBuyPrice);
                                    GameUtils.useEffect(luxuryEffectList, appUserLady);
                                    effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);

                                    bind(appUserLady, userId);
                                    appUserLuxuryStore.buy(appUserLuxury, Persistent.SAVE, appUserLady, appUserLimit);
                                    GameUtils.addResultArray(resultArray, "恭喜你,添置了:" + resLuxury.getTitle() , null);
                                    GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                    result = 0;
                                } else {
                                    GameUtils.addResultArray(resultArray, "爱美是好事，但是现实也需要真金白银！", null);
                                    result = 1;
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖饰品", null);
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
    public String sellLuxury(Long userId, Long luxuryId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && appUserLadyStore != null && appUserLuxuryStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    String resultText = null;
                    if (appUser.getGender() == 2) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
                            JSONArray effectArray = null;
                            Integer luxuryLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "LUXURY");
                            if (luxuryLimit == 0) {
                                AppUserLimit appUserLimit = new AppUserLimit();
                                appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserLimit.setUserId(appUser);
                                appUserLimit.setAction("LUXURY");
                                appUserLimit.setDay(appUserLady.getDays());
                                bind(appUserLimit, userId);
                                appUserLimit.setUseYn("Y");

                                List<AppUserLuxury> appUserLuxuryList = appUserLuxuryStore.getByUserIdLuxuryId(userId, luxuryId);
                                if (appUserLuxuryList != null && !appUserLuxuryList.isEmpty()) {
                                    AppUserLuxury appUserLuxury = appUserLuxuryList.get(0);
                                    if (appUserLuxury != null) {
                                        ResLuxury resLuxury = appUserLuxury.getLuxuryId();
                                        if (resLuxury != null) {
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserLady.getDays(), resLuxury.getSellPrice(), resLuxury.getOffsetSell());
                                            AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                            appUserLady.setMoney(appUserLady.getMoney() + currentSellPrice);
                                            effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                            bind(appUserLady, userId);
                                            appUserLuxuryStore.sell(appUserLuxury, appUserLady, appUserLimit);
                                            GameUtils.addResultArray(resultArray, "已将不想要的" + resLuxury.getTitle() + "出售，老娘需要更好的！", null);
                                            GameUtils.addResultArray(resultArray, "最终:", effectArray);
                                            result = 0;
                                        }
                                    }
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次买卖饰品", null);
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
