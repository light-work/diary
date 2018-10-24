package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.entity.res.ResFund;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserFundBiz;
import com.diary.providers.store.app.*;
import com.diary.providers.store.res.ResFundStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserFundBizImp extends BaseBiz implements UserFundBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String buyFund(Long userId, Long fundId, Integer money) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            AppUserFundMarketStore appUserFundMarketStore = hsfServiceFactory.consumer(AppUserFundMarketStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            if (appUserStore != null && resFundStore != null && appUserFundMarketStore != null
                    && appUserManStore != null && appUserLadyStore != null && appUserFundStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserId(userId);
                        if (appUserFundMarket != null) {
                            int day = 0;
                            int userMoney = 0;
                            AppUserMan appUserMan = null;
                            AppUserLady appUserLady = null;
                            if (appUser.getGender() == 1) {
                                appUserMan = appUserManStore.getByUserId(userId);
                                if (appUserMan != null) {
                                    day = appUserMan.getDays();
                                    userMoney = appUserMan.getMoney();
                                }
                            } else if (appUser.getGender() == 2) {
                                appUserLady = appUserLadyStore.getByUserId(userId);
                                if (appUserLady != null) {
                                    day = appUserLady.getDays();
                                    userMoney = appUserLady.getMoney();
                                }
                            }
                            if (userMoney >= money) {
                                AppUserFund appUserFund = appUserFundStore.getByUserFundId(userId, fundId);
                                Persistent persistent = Persistent.UPDATE;
                                if (appUserFund == null) {
                                    persistent = Persistent.SAVE;
                                    appUserFund.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUserFund.setUserId(appUser);
                                    appUserFund.setFundId(resFund);
                                    appUserFund.setDay(day);
                                    appUserFund.setMoney(0);
                                }
                                String market = appUserFundMarket.getMarket();
                                if (StringUtils.isNotBlank(market)) {
                                    JSONArray marketArray = JSONArray.fromObject(market);
                                    if (marketArray != null && !marketArray.isEmpty()) {
                                        Double lastIndex = marketArray.getDouble(marketArray.size() - 1);
                                        if (lastIndex != null) {
                                            appUserFund.setMarket(lastIndex);
                                            appUserFund.setMoney(appUserFund.getMoney() + money);

                                            userMoney = userMoney - money;

                                            bind(appUserFund, userId);
                                            appUserFund.setUseYn("Y");
                                            if (appUser.getGender() == 1) {
                                                appUserMan.setMoney(userMoney);
                                                bind(appUserMan, userId);
                                                appUserFundStore.saveMan(appUserFund, persistent, appUserMan);
                                            } else if (appUser.getGender() == 2) {
                                                appUserLady.setMoney(userMoney);
                                                bind(appUserLady, userId);
                                                appUserFundStore.saveLady(appUserFund, persistent, appUserLady);
                                            }
                                            resultObj.put("result", 0);
                                        }
                                    }
                                }
                            }
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
    public String sellFund(Long userId, Long fundId, Integer money) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            AppUserFundMarketStore appUserFundMarketStore = hsfServiceFactory.consumer(AppUserFundMarketStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            AppUserFundDetailStore appUserFundDetailStore = hsfServiceFactory.consumer(AppUserFundDetailStore.class);
            if (appUserStore != null && resFundStore != null && appUserFundMarketStore != null
                    && appUserManStore != null && appUserLadyStore != null && appUserFundStore != null && appUserFundDetailStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserId(userId);
                        if (appUserFundMarket != null) {
                            AppUserFund appUserFund = appUserFundStore.getByUserFundId(userId, fundId);
                            if (appUserFund != null) {
                                if (money <= appUserFund.getMoney()) {
                                    AppUserMan appUserMan = null;
                                    AppUserLady appUserLady = null;
                                    int userMoney = 0;
                                    if (appUser.getGender() == 1) {
                                        appUserMan = appUserManStore.getByUserId(userId);
                                        if (appUserMan != null) {
                                            userMoney = appUserMan.getMoney();
                                        }
                                    } else if (appUser.getGender() == 2) {
                                        appUserLady = appUserLadyStore.getByUserId(userId);
                                        if (appUserLady != null) {
                                            userMoney = appUserLady.getMoney();
                                        }
                                    }
                                    int diffMoney = appUserFund.getMoney() - money;
                                    if (diffMoney == 0) {
                                        List<AppUserFundDetail> appUserFundDetails = appUserFundDetailStore.getByUserFundId(appUserFund.getId());
                                        userMoney = userMoney + money;
                                        if (appUser.getGender() == 1) {
                                            appUserMan.setMoney(userMoney);
                                            bind(appUserMan, userId);
                                            appUserFundStore.deleteMan(appUserFund, appUserMan, appUserFundMarket, appUserFundDetails);
                                        } else if (appUser.getGender() == 2) {
                                            appUserLady.setMoney(userMoney);
                                            bind(appUserLady, userId);
                                            appUserFundStore.deleteLady(appUserFund, appUserLady, appUserFundMarket, appUserFundDetails);
                                        }
                                        resultObj.put("result", 0);
                                    } else {
                                        appUserFund.setMoney(diffMoney);
                                        bind(appUserFund, userId);
                                        userMoney = userMoney + money;
                                        if (appUser.getGender() == 1) {
                                            appUserMan.setMoney(userMoney);
                                            bind(appUserMan, userId);
                                            appUserFundStore.saveMan(appUserFund, Persistent.UPDATE, appUserMan);
                                        } else if (appUser.getGender() == 2) {
                                            appUserLady.setMoney(userMoney);
                                            bind(appUserLady, userId);
                                            appUserFundStore.saveLady(appUserFund, Persistent.UPDATE, appUserLady);
                                        }
                                        resultObj.put("result", 0);
                                    }
                                }
                            }
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
    public String market(Long userId, Long fundId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            AppUserFundMarketStore appUserFundMarketStore = hsfServiceFactory.consumer(AppUserFundMarketStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (appUserStore != null && resFundStore != null && appUserFundMarketStore != null
                    && appUserManStore != null && appUserLadyStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        int day = 0;
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                            if (appUserMan != null) {
                                day = appUserMan.getDays();
                            }
                        } else if (appUser.getGender() == 2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                            if (appUserLady != null) {
                                day = appUserLady.getDays();
                            }
                        }
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserId(userId);
                        List<Double> doubleList = new ArrayList<>();
                        doubleList.add(resFund.getProbability());
                        doubleList.add(NumberUtils.subtract(1.00, resFund.getProbability()));
                        if (appUserFundMarket == null) {
                            JSONArray marketArray = new JSONArray();
                            appUserFundMarket = new AppUserFundMarket();
                            appUserFundMarket.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserFundMarket.setUserId(appUser);
                            appUserFundMarket.setFundId(resFund);
                            int d = GameUtils.gameDays - day;
                            int sum = 7 + d;
                            for (int i = 1; i <= sum; i++) {
                                marketArray.add(String.valueOf(GameUtils.fundMarket(doubleList, resFund.getMinNum(), resFund.getMaxNum())));
                            }
                            appUserFundMarket.setMarket(marketArray.toString());
                            appUserFundMarket.setUseYn("Y");
                            bind(appUserFundMarket, userId);
                            appUserFundMarketStore.save(appUserFundMarket, Persistent.SAVE);
                        } else {

                            String market = appUserFundMarket.getMarket();
                            if (StringUtils.isNotBlank(market)) {
                                JSONArray marketArray = JSONArray.fromObject(market);
                                int d = GameUtils.gameDays - day;
                                int sum = 7 + d;
                                if (marketArray.size() < sum) {
                                    int diff = sum - marketArray.size();
                                    for (int i = 1; i <= diff; i++) {
                                        marketArray.add(String.valueOf(GameUtils.fundMarket(doubleList, resFund.getMinNum(), resFund.getMaxNum())));
                                    }
                                    appUserFundMarket.setMarket(marketArray.toString());
                                    appUserFundMarket.setUseYn("Y");
                                    bind(appUserFundMarket, userId);
                                    appUserFundMarketStore.save(appUserFundMarket, Persistent.UPDATE);
                                }
                            }
                        }
                        JSONObject appUserFundMarketObj = JsonUtils.formIdEntity(appUserFundMarket, 0);
                        if (appUserFundMarketObj != null) {
                            GameUtils.minish(appUserFundMarketObj);
                            resultObj.put("data", appUserFundMarketObj);
                        }
                        resultObj.put("result", 0);
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
