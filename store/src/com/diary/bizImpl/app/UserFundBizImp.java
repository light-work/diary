package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserFundMarket;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.res.ResFund;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserFundBiz;
import com.diary.providers.store.app.AppUserFundMarketStore;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.providers.store.app.AppUserManStore;
import com.diary.providers.store.app.AppUserStore;
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
    public String buyFund(Long userId, Long fundId, Long money) throws BizException {
        return null;
    }

    @Override
    public String sellFund(Long userId, Long fundId, Long money) throws BizException {
        return null;
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
                    &&appUserManStore!=null&&appUserLadyStore!=null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResFund resFund = resFundStore.getById(fundId);
                    if (resFund != null) {
                        int day=0;
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                            if (appUserMan != null) {
                                day=appUserMan.getDays();
                            }
                        }else if (appUser.getGender() ==2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                            if (appUserLady != null) {
                                day=appUserLady.getDays();
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
                            int d=GameUtils.gameDays-day;
                            int sum=7+d;
                            for (int i = 1; i <= sum; i++) {
                                marketArray.add(String.valueOf(GameUtils.fundMarket(doubleList, resFund.getMinNum(), resFund.getMaxNum())));
                            }
                            appUserFundMarket.setMarket(marketArray.toString());
                            appUserFundMarket.setUseYn("Y");
                            bind(appUserFundMarket, userId);
                            appUserFundMarketStore.save(appUserFundMarket, Persistent.SAVE);
                        } else {

                            String market=appUserFundMarket.getMarket();
                            if(StringUtils.isNotBlank(market)){
                                JSONArray marketArray=JSONArray.fromObject(market);
                                int d=GameUtils.gameDays-day;
                                int sum=7+d;
                                if(marketArray.size()<sum){
                                    int diff=sum-marketArray.size();
                                    for(int i=1;i<=diff;i++){
                                        marketArray.add(String.valueOf(GameUtils.fundMarket(doubleList, resFund.getMinNum(), resFund.getMaxNum())));
                                    }
                                    appUserFundMarket.setMarket(marketArray.toString());
                                    appUserFundMarket.setUseYn("Y");
                                    bind(appUserFundMarket, userId);
                                    appUserFundMarketStore.save(appUserFundMarket, Persistent.UPDATE);
                                }
                            }
                        }
                        JSONObject appUserFundMarketObj=JsonUtils.formIdEntity(appUserFundMarket,0);
                        if(appUserFundMarketObj!=null){
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
