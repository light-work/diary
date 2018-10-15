package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserPlan;
import com.diary.entity.res.ResPlan;
import com.diary.entity.res.ResPlanEffect;
import com.diary.entity.res.ResPlanEvent;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserPlanBiz;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.providers.store.app.AppUserManStore;
import com.diary.providers.store.app.AppUserPlanStore;
import com.diary.providers.store.app.AppUserStore;
import com.diary.providers.store.res.ResPlanEffectStore;
import com.diary.providers.store.res.ResPlanEventStore;
import com.diary.providers.store.res.ResPlanStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.Page;
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

public class UserPlanBizImp extends BaseBiz implements UserPlanBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String applyPlan(Long userId, Long planId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserPlanStore appUserPlanStore = hsfServiceFactory.consumer(AppUserPlanStore.class);
            ResPlanEffectStore resPlanEffectStore = hsfServiceFactory.consumer(ResPlanEffectStore.class);
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (appUserManStore != null && appUserLadyStore != null && appUserStore != null
                    && appUserPlanStore != null && resPlanStore != null && resPlanEffectStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<ResPlanEffect> effectList = resPlanEffectStore.getListByPlanId(planId);
                    List<ResPlanEffect> effectSubList = new ArrayList<>();
                    if (effectList != null && !effectList.isEmpty()) {
                        for (ResPlanEffect effect : effectList) {
                            if (effect.getOperation().equals("SUB") && effect.getAttrKey().equals("MONEY")) {
                                effectSubList.add(effect);
                            }
                        }
                    }
                    boolean pass = true;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    JSONArray resultArray = new JSONArray();
                    JSONArray effectArray = null;
                    if (appUser.getUserGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            pass = GameUtils.requirePass(effectSubList, appUserMan);
                        }
                    } else if (appUser.getUserGender() == 0) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            pass = GameUtils.requirePass(effectSubList, appUserLady);
                        }
                    }
                    if (pass) {
                        ResPlan resPlan = resPlanStore.getById(planId);
                        if (resPlan != null) {
                            AppUserPlan appUserPlan = new AppUserPlan();
                            appUserPlan.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserPlan.setUserId(appUser);
                            appUserPlan.setPlanId(resPlan);
                            appUserPlan.setUseYn("Y");
                            bind(appUserPlan, userId);
                            String resultText = resPlan.getResult();

                            if (appUser.getUserGender() == 1) {
                                if (appUserMan != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                        GameUtils.useEffect(effectList, appUserMan);
                                        effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    }
                                    appUserPlan.setPlanOfDay(appUserMan.getDays());
                                    appUserPlan.setPlanOfHour(appUserMan.getHours());
                                    GameUtils.useHour(appUserMan);
                                    appUserPlanStore.save(appUserPlan, Persistent.SAVE, appUserMan);
                                }
                            } else if (appUser.getUserGender() == 0) {
                                if (appUserLady != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                        GameUtils.useEffect(effectList, appUserLady);
                                        effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                    }
                                    appUserPlan.setPlanOfDay(appUserLady.getDays());
                                    appUserPlan.setPlanOfHour(appUserLady.getHours());
                                    GameUtils.useHour(appUserLady);
                                    appUserPlanStore.save(appUserPlan, Persistent.SAVE, appUserLady);
                                }
                            }
                            GameUtils.addResultArray(resultArray, resultText, null);
                            GameUtils.addResultArray(resultArray, "最终:", effectArray);


                            resultObj.put("resultArray", resultArray);
                            resultObj.put("result", 0);
                        }
                    } else {
                        GameUtils.addResultArray(resultArray, "", null);
                        resultObj.put("text", GameUtils.callName(appUser.getUserGender()) + "，钱包那么瘪，如果不想付出劳动获得金钱，就好好宅着吧！");
                        resultObj.put("result", 1);
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
    public String findEvent(Long userId, Long planId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        ResPlanEventStore resPlanEventStore = hsfServiceFactory.consumer(ResPlanEventStore.class);
        if (resPlanEventStore != null) {
            List<Selector> selectorList = new ArrayList<>();
            selectorList.add(SelectorUtils.$eq("planId.id", planId));
            GameUtils.randSelector(selectorList);
            Page<ResPlanEvent> resPlanEventPage = resPlanEventStore.getPageList(0, 20, selectorList);
            if(resPlanEventPage!=null){
                List<ResPlanEvent> resPlanEventList=resPlanEventPage.getResultList();
                if(resPlanEventList!=null&&!resPlanEventList.isEmpty()){
                   int index=GameUtils.randGetIndex(resPlanEventList.size());
                   ResPlanEvent resPlanEvent=resPlanEventList.get(index);
                   if(resPlanEvent!=null){
                       resultObj.put("eventId", resPlanEvent.getEventId().getId()+"");
                       resultObj.put("result", 0);
                   }
                }
            }
        }
        return resultObj.toString();
    }
}
