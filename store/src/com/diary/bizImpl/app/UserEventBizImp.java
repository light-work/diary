package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventResult;
import com.diary.entity.res.ResEventResultEffect;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserEventBiz;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.providers.store.app.AppUserManStore;
import com.diary.providers.store.app.AppUserStore;
import com.diary.providers.store.res.ResEventResultEffectStore;
import com.diary.providers.store.res.ResEventResultStore;
import com.diary.providers.store.res.ResEventStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.JsonUtils;
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

public class UserEventBizImp extends BaseBiz implements UserEventBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String findEvent(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
        ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
        if (resEventStore != null) {
            AppUser appUser = appUserStore.getById(userId);
            if (appUser != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$eq("gender", appUser.getGender()));
                selectorList.add(SelectorUtils.$eq("source", "RANDOM"));

                GameUtils.randSelector(selectorList);
                Page<ResEvent> resEventPage = resEventStore.getPageList(0, 50, selectorList);
                if (resEventPage != null) {
                    List<ResEvent> resEventList = resEventPage.getResultList();
                    if (resEventList != null && !resEventList.isEmpty()) {
                        int index = GameUtils.randGetIndex(resEventList.size());
                        ResEvent resEvent = resEventList.get(index);
                        if (resEvent != null) {
                            resultObj.put("eventId", resEvent.getId() + "");
                            resultObj.put("result", 0);
                        }
                    }
                }
            }

        }
        return resultObj.toString();
    }

    @Override
    public String loadEvent(Long userId, Long eventId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && resEventStore != null && resEventResultStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                ResEvent resEvent = resEventStore.getById(eventId);
                if (appUser != null && resEvent != null) {
                    List<ResEventResult> eventResultList = resEventResultStore.getListByEventId(resEvent.getId());
                    JSONArray eventResultArray = null;
                    if (eventResultList != null && !eventResultList.isEmpty()) {
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                            if (appUserMan != null) {
                                eventResultArray = GameUtils.requireCompare(eventResultList, appUserMan);
                            }
                        } else if (appUser.getGender() == 2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                            if (appUserLady != null) {
                                eventResultArray = GameUtils.requireCompare(eventResultList, appUserLady);
                            }
                        }
                        JSONObject resEventObj = JsonUtils.formIdEntity(resEvent);
                        if (resEventObj != null) {
                            GameUtils.minish(resEventObj);
                            resultObj.put("event", resEventObj);
                        }

                    }
                    resultObj.put("result", 0);
                    resultObj.put("eventResultArray", eventResultArray);
                }
                System.out.println(resultObj.toString());
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
    public String applyResult(Long userId, Long resultId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (resEventResultStore != null && appUserManStore != null && appUserLadyStore != null && resEventResultEffectStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    ResEventResult resEventResult = resEventResultStore.getById(resultId);
                    if (resEventResult != null) {
                        JSONArray resultArray = new JSONArray();
                        JSONArray effectArray = null;
                        List<ResEventResultEffect> eventResultEffectList = resEventResultEffectStore.getListByResultId(resultId);
                        if (eventResultEffectList != null && !eventResultEffectList.isEmpty()) {
                            if (appUser.getGender() == 1) {
                                AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                                if (appUserMan != null) {
                                    AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                    if (eventResultEffectList != null && !eventResultEffectList.isEmpty()) {
                                        GameUtils.useEffect(eventResultEffectList, appUserMan);
                                    }
                                    effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    GameUtils.addResultArray(resultArray, resEventResult.getContent(), null);
                                    GameUtils.addResultArray(resultArray, "最终", effectArray);
                                    appUserManStore.save(appUserMan, Persistent.UPDATE);
                                }
                            } else if (appUser.getGender() == 2) {
                                AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                                if (appUserLady != null) {
                                    AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                    if (eventResultEffectList != null && !eventResultEffectList.isEmpty()) {
                                        GameUtils.useEffect(eventResultEffectList, appUserLady);
                                    }
                                    effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                    GameUtils.addResultArray(resultArray, resEventResult.getContent(), null);
                                    GameUtils.addResultArray(resultArray, "最终", effectArray);
                                    appUserLadyStore.save(appUserLady, Persistent.UPDATE);
                                }
                            }
                        }
                        resultObj.put("result", 0);
                        resultObj.put("resultArray", resultArray);
                        System.out.println(resultObj.toString());
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
