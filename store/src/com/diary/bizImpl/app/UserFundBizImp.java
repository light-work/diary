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
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
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
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && resFundStore != null && appUserFundMarketStore != null
                    && appUserManStore != null && appUserLadyStore != null && appUserFundStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserFundId(userId, fundId);
                        if (appUserFundMarket != null) {
                            int result = -1;
                            JSONArray resultArray = new JSONArray();
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
                            Integer fundLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FUND");
                            if (fundLimit == 0) {
                                if (userMoney >= money) {
                                    AppUserFund appUserFund = appUserFundStore.getByUserFundId(userId, fundId);
                                    Persistent persistent = Persistent.UPDATE;
                                    if (appUserFund == null) {
                                        persistent = Persistent.SAVE;
                                        appUserFund = new AppUserFund();
                                        appUserFund.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                        appUserFund.setUserId(appUser);
                                        appUserFund.setFundId(resFund);
                                        appUserFund.setBuy(0);
                                        appUserFund.setDay(day);
                                        appUserFund.setMoney(0);
                                    }
                                    String market = appUserFundMarket.getMarket();
                                    if (StringUtils.isNotBlank(market)) {
                                        JSONArray marketArray = JSONArray.fromObject(market);
                                        if (marketArray != null && !marketArray.isEmpty()) {
                                            Double lastIndex = marketArray.getDouble(marketArray.size() - 1);
                                            if (lastIndex != null) {
                                                AppUserLimit appUserLimit = new AppUserLimit();
                                                appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                                appUserLimit.setUserId(appUser);
                                                appUserLimit.setAction("FUND");
                                                appUserLimit.setDay(day);
                                                bind(appUserLimit, userId);
                                                appUserLimit.setUseYn("Y");


                                                appUserFund.setMarket(lastIndex);
                                                appUserFund.setMoney(appUserFund.getMoney() + money);
                                                appUserFund.setBuy(appUserFund.getBuy() + money);

                                                userMoney = userMoney - money;

                                                bind(appUserFund, userId);
                                                appUserFund.setUseYn("Y");
                                                if (appUser.getGender() == 1) {
                                                    appUserMan.setMoney(userMoney);
                                                    bind(appUserMan, userId);
                                                    appUserFundStore.saveMan(appUserFund, persistent, appUserMan, appUserLimit);
                                                } else if (appUser.getGender() == 2) {
                                                    appUserLady.setMoney(userMoney);
                                                    bind(appUserLady, userId);
                                                    appUserFundStore.saveLady(appUserFund, persistent, appUserLady, appUserLimit);
                                                }
                                                GameUtils.addResultArray(resultArray, "你已经成功买入:" + resFund.getTitle() + "，投资有风险，见好就收，及时止损。", null);
                                                result = 0;
                                            }
                                        }
                                    }
                                }
                            } else {
                                GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次理财操作", null);
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
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            if (appUserStore != null && resFundStore != null && appUserFundMarketStore != null
                    && appUserManStore != null && appUserLadyStore != null && appUserFundStore != null && appUserFundDetailStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserFundId(userId, fundId);
                        if (appUserFundMarket != null) {
                            AppUserFund appUserFund = appUserFundStore.getByUserFundId(userId, fundId);
                            if (appUserFund != null) {
                                int result = -1;
                                JSONArray resultArray = new JSONArray();
                                if (money <= appUserFund.getMoney()) {
                                    AppUserMan appUserMan = null;
                                    AppUserLady appUserLady = null;
                                    int day = 0;
                                    int userMoney = 0;
                                    if (appUser.getGender() == 1) {
                                        appUserMan = appUserManStore.getByUserId(userId);
                                        if (appUserMan != null) {
                                            userMoney = appUserMan.getMoney();
                                            day = appUserMan.getDays();
                                        }
                                    } else if (appUser.getGender() == 2) {
                                        appUserLady = appUserLadyStore.getByUserId(userId);
                                        if (appUserLady != null) {
                                            userMoney = appUserLady.getMoney();
                                            day = appUserLady.getDays();

                                        }
                                    }
                                    Integer fundLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FUND");
                                    if (fundLimit == 0) {

                                        AppUserLimit appUserLimit = new AppUserLimit();
                                        appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                        appUserLimit.setUserId(appUser);
                                        appUserLimit.setAction("FUND");
                                        appUserLimit.setDay(day);
                                        bind(appUserLimit, userId);
                                        appUserLimit.setUseYn("Y");

                                        int diffMoney = appUserFund.getMoney() - money;
                                        if (diffMoney == 0) {
                                            List<AppUserFundDetail> appUserFundDetails = appUserFundDetailStore.getByUserFundId(appUserFund.getId());
                                            userMoney = userMoney + money;
                                            if (appUser.getGender() == 1) {
                                                appUserMan.setMoney(userMoney);
                                                bind(appUserMan, userId);
                                                appUserFundStore.deleteMan(appUserFund, appUserMan, appUserFundMarket, appUserFundDetails, appUserLimit);
                                            } else if (appUser.getGender() == 2) {
                                                appUserLady.setMoney(userMoney);
                                                bind(appUserLady, userId);
                                                appUserFundStore.deleteLady(appUserFund, appUserLady, appUserFundMarket, appUserFundDetails, appUserLimit);
                                            }
                                            GameUtils.addResultArray(resultArray, "你已经成功卖出:" + resFund.getTitle() + "，投资有风险，见好就收，及时止损。", null);

                                            result = 0;
                                        } else {
                                            appUserFund.setMoney(diffMoney);
                                            appUserFund.setBuy(appUserFund.getBuy() - money);
                                            bind(appUserFund, userId);
                                            userMoney = userMoney + money;
                                            if (appUser.getGender() == 1) {
                                                appUserMan.setMoney(userMoney);
                                                bind(appUserMan, userId);
                                                appUserFundStore.saveMan(appUserFund, Persistent.UPDATE, appUserMan, appUserLimit);
                                            } else if (appUser.getGender() == 2) {
                                                appUserLady.setMoney(userMoney);
                                                bind(appUserLady, userId);
                                                appUserFundStore.saveLady(appUserFund, Persistent.UPDATE, appUserLady, appUserLimit);
                                            }
                                            GameUtils.addResultArray(resultArray, "你已经成功卖出:" + resFund.getTitle() + "，投资有风险，见好就收，及时止损。", null);

                                            result = 0;
                                        }
                                    } else {
                                        GameUtils.addResultArray(resultArray, "抱歉，每日只能进行一次理财操作", null);
                                        result = 1;
                                    }
                                    resultObj.put("result", result);
                                    resultObj.put("resultArray", resultArray);
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
    public String trade(Long userId, Long fundId) throws BizException {
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
                        AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserFundId(userId, fundId);
                        if (appUserFundMarket != null) {
                            List<Double> doubleList = new ArrayList<>();
                            doubleList.add(resFund.getProbability());
                            doubleList.add(NumberUtils.subtract(1.00, resFund.getProbability()));
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
                                JSONObject appUserFundMarketObj = JsonUtils.formIdEntity(appUserFundMarket, 0);
                                if (appUserFundMarketObj != null) {
                                    GameUtils.minish(appUserFundMarketObj);
                                    resultObj.put("market", appUserFundMarketObj);
                                }
                                AppUserFund appUserFund = appUserFundStore.getByUserFundId(userId, fundId);
                                Integer fundMoney = 0;
                                Integer buyAll = 0;
                                if (appUserFund != null) {
                                    fundMoney = appUserFund.getMoney();
                                    buyAll=appUserFund.getBuy();
                                }
                                Integer diffMoney = fundMoney-buyAll;

                                resultObj.put("fundMoney", GameUtils.formatGroupingUsed(fundMoney.longValue()));
                                resultObj.put("diffMoney", GameUtils.formatGroupingUsed(diffMoney.longValue()));
                                resultObj.put("result", 0);
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
    public String market(Long userId) throws BizException {
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
                    List<Selector> selectorList = new ArrayList<>();
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    selectorList.add(SelectorUtils.$order("probability", true));
                    List<ResFund> resFundList = resFundStore.getList(selectorList);
                    if (resFundList != null && !resFundList.isEmpty()) {
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
                        List<AppUserFundMarket> saveAppUserFundMarketList = new ArrayList<>();
                        List<AppUserFundMarket> updateAppUserFundMarketList = new ArrayList<>();
                        for (ResFund resFund : resFundList) {
                            if (resFund != null) {
                                AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserFundId(userId, resFund.getId());
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
                                    saveAppUserFundMarketList.add(appUserFundMarket);
                                    resultObj.put(resFund.getId() + "", marketArray.getDouble(marketArray.size() - 1));
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
                                            updateAppUserFundMarketList.add(appUserFundMarket);
                                        }
                                        resultObj.put(resFund.getId() + "", marketArray.getDouble(marketArray.size() - 1));
                                    }
                                }

                            }
                        }
                        if (saveAppUserFundMarketList != null && !saveAppUserFundMarketList.isEmpty()) {
                            appUserFundMarketStore.save(saveAppUserFundMarketList, Persistent.SAVE);
                        }
                        if (updateAppUserFundMarketList != null && !updateAppUserFundMarketList.isEmpty()) {
                            appUserFundMarketStore.save(updateAppUserFundMarketList, Persistent.UPDATE);
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
