package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.entity.res.*;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserBiz;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserBizImp extends BaseBiz implements UserBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String login(String code) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            if (appUserStore != null) {
                String appId = "wxca42881e5a26343e";
                String secret = "8ee520a070022faf864512d8a60d72f0";
                if (StringUtils.isNotBlank(secret)) {
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("appid", appId);
                    paramMap.put("secret", secret);
                    paramMap.put("js_code", code);
                    paramMap.put("grant_type", "authorization_code");
                    String resultWX = OKHttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", paramMap);
                    if (StringUtils.isNotBlank(resultWX)) {
                        JSONObject wxObj = JSONObject.fromObject(resultWX);
                        if (wxObj != null) {
                            String openid = wxObj.getString("openid");
                            if (StringUtils.isNotBlank(openid)) {
                                AppUser appUser = appUserStore.getByOpenId(openid);
                                if (appUser == null) {
                                    appUser = new AppUser();
                                    appUser.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUser.setUserGender(1);
                                    appUser.setOpenId(openid);
                                    appUser.setUseYn("Y");
                                    bind(appUser, 1l);
                                    appUserStore.save(appUser, Persistent.SAVE);
                                }
                                JSONObject userObj = JsonUtils.formIdEntity(appUser, 0);
                                GameUtils.minish(userObj);
                                userObj.put("userId", appUser.getId() + "");
                                resultObj.put("userData", userObj);
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
    public String refresh(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        JSONObject infoObj = null;
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getUserGender(), 1);
                    resultObj.put("attrList", attrArray);
                    if (appUser.getUserGender().intValue() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if(appUserMan!=null){
                            infoObj = JsonUtils.formIdEntity(appUserMan, 0);
                            if (infoObj != null) {
                                GameUtils.man(infoObj);
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if(appUserLady!=null){
                            infoObj = JsonUtils.formIdEntity(appUserLady, 0);
                            if (infoObj != null) {
                                GameUtils.lady(infoObj);
                            }
                        }
                    }
                    if (infoObj != null) {
                        resultObj.put("userState", infoObj);
                    }
                    resultObj.put("result", 0);
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
    public String start(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        JSONObject infoObj = null;
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getUserGender(), 1);
                    resultObj.put("attrList", attrArray);
                    if (appUser.getUserGender().intValue() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan == null) {
                            appUserMan = new AppUserMan();
                            appUserMan.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserMan.setUserId(appUser);
                            appUserMan.setHealth(100);
                            appUserMan.setMoney(8000);
                            appUserMan.setProfit(0);
                            appUserMan.setAbility(100);
                            appUserMan.setExperience(100);
                            appUserMan.setHappy(100);
                            appUserMan.setPositive(100);
                            appUserMan.setConnections(100);
                            appUserMan.setDays(10);
                            appUserMan.setHours(8);
                            appUserMan.setUseYn("Y");
                            bind(appUserMan, userId);
                            appUserManStore.save(appUserMan, Persistent.SAVE);
                        }
                        infoObj = JsonUtils.formIdEntity(appUserMan, 0);
                        if (infoObj != null) {
                            GameUtils.man(infoObj);
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady == null) {
                            appUserLady = new AppUserLady();
                            appUserLady.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserLady.setUserId(appUser);
                            appUserLady.setHealth(100);
                            appUserLady.setMoney(8000);
                            appUserLady.setProfit(0);
                            appUserLady.setAbility(100);
                            appUserLady.setWisdom(100);
                            appUserLady.setHappy(100);
                            appUserLady.setBeauty(100);
                            appUserLady.setPopularity(100);
                            appUserLady.setDays(10);
                            appUserLady.setHours(8);
                            appUserLady.setUseYn("Y");
                            bind(appUserLady, userId);
                            appUserLadyStore.save(appUserLady, Persistent.SAVE);
                        }

                        infoObj = JsonUtils.formIdEntity(appUserLady, 0);
                        if (infoObj != null) {
                            GameUtils.lady(infoObj);
                        }
                    }
                    if (infoObj != null) {
                        resultObj.put("userState", infoObj);
                    }
                    resultObj.put("result", 0);
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
    public String updateUserInfo(Long userId, String userNickName, String userAvatarUrl, String userGender, String userCity, String userProvince, String userCountry) throws BizException {
        return null;
    }

    @Override
    public String resData(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            ResCoupleStore resCoupleStore = hsfServiceFactory.consumer(ResCoupleStore.class);
            if (appUserStore != null && resJobStore != null && resPlanStore != null && resCarStore != null && resHouseStore != null && resClothesStore != null
                    && resLuxuryStore != null && resCoupleStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray jobArray = new JSONArray();
                    JSONArray planArray = new JSONArray();
                    JSONArray coupleArray = new JSONArray();
                    JSONArray carArray = new JSONArray();
                    JSONArray houseArray = new JSONArray();
                    JSONArray clothesArray = new JSONArray();
                    JSONArray luxuryArray = new JSONArray();
                    List<Selector> selectorList = new ArrayList<>();
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getUserGender()));
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    selectorList.add(SelectorUtils.$order("price", true));
                    List<ResJob> jobList = resJobStore.getList(selectorList);
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResJob resJob : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resJob, 0);
                            if (jobObj != null) {
                                GameUtils.minish(jobObj);
                                jobArray.add(jobObj);
                            }
                        }
                    }

                    selectorList.clear();
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getUserGender()));
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    selectorList.add(SelectorUtils.$order("displayOrder", true));
                    List<ResPlan> planList = resPlanStore.getList(selectorList);
                    if (planList != null && !planList.isEmpty()) {
                        for (ResPlan resPlan : planList) {
                            JSONObject planObj = JsonUtils.formIdEntity(resPlan, 0);
                            if (planObj != null) {
                                GameUtils.minish(planObj);
                                planArray.add(planObj);
                            }
                        }
                    }

                    selectorList.clear();
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getUserGender()));
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    List<ResCouple> coupleList = resCoupleStore.getList(selectorList);
                    if (coupleList != null && !coupleList.isEmpty()) {
                        for (ResCouple resCouple : coupleList) {
                            JSONObject coupleObj = JsonUtils.formIdEntity(resCouple, 0);
                            if (coupleObj != null) {
                                GameUtils.minish(coupleObj);
                                coupleArray.add(coupleObj);
                            }
                        }
                    }
                    if (appUser.getUserGender().intValue() == 1) {
                        selectorList.clear();
                        selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                        selectorList.add(SelectorUtils.$order("buyPrice", true));
                        List<ResCar> carList = resCarStore.getList(selectorList);
                        if (carList != null && !carList.isEmpty()) {
                            for (ResCar resCar : carList) {
                                JSONObject carObj = JsonUtils.formIdEntity(resCar, 0);
                                if (carObj != null) {
                                    GameUtils.minish(carObj);
                                    carArray.add(carObj);
                                }
                            }
                        }

                        selectorList.clear();
                        selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                        selectorList.add(SelectorUtils.$order("buyPrice", true));
                        List<ResHouse> houseList = resHouseStore.getList(selectorList);
                        if (houseList != null && !houseList.isEmpty()) {
                            for (ResHouse resHouse : houseList) {
                                JSONObject houseObj = JsonUtils.formIdEntity(resHouse, 0);
                                if (houseObj != null) {
                                    GameUtils.minish(houseObj);
                                    houseArray.add(houseObj);
                                }
                            }
                        }

                    } else if (appUser.getUserGender().intValue() == 0) {
                        selectorList.clear();
                        selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                        selectorList.add(SelectorUtils.$order("buyPrice", true));
                        List<ResClothes> clothesList = resClothesStore.getList(selectorList);
                        if (clothesList != null && !clothesList.isEmpty()) {
                            for (ResClothes resClothes : clothesList) {
                                JSONObject clothesObj = JsonUtils.formIdEntity(resClothes, 0);
                                if (clothesObj != null) {
                                    GameUtils.minish(clothesObj);
                                    clothesArray.add(clothesObj);
                                }
                            }
                        }

                        selectorList.clear();
                        selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                        selectorList.add(SelectorUtils.$order("buyPrice", true));
                        List<ResLuxury> luxuryList = resLuxuryStore.getList(selectorList);
                        if (luxuryList != null && !luxuryList.isEmpty()) {
                            for (ResLuxury resLuxury : luxuryList) {
                                JSONObject luxuryObj = JsonUtils.formIdEntity(resLuxury, 0);
                                if (luxuryObj != null) {
                                    GameUtils.minish(luxuryObj);
                                    luxuryArray.add(luxuryObj);
                                }
                            }
                        }
                    }
                    resultObj.put("jobArray", jobArray);
                    resultObj.put("planArray", planArray);
                    resultObj.put("coupleArray", coupleArray);
                    resultObj.put("carArray", carArray);
                    resultObj.put("houseArray", houseArray);
                    resultObj.put("clothesArray", clothesArray);
                    resultObj.put("luxuryArray", luxuryArray);
                    resultObj.put("result", 0);
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
    public String applyJob(Long userId, Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (appUserManStore != null && appUserLadyStore != null && appUserStore != null
                    && resJobRequireStore != null && appUserJobStore != null && resJobStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<ResJobRequire> requireList = resJobRequireStore.getListByJobId(jobId);
                    boolean pass = true;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    if (appUser.getUserGender().intValue() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            if (requireList != null && !requireList.isEmpty()) {
                                for (ResJobRequire require : requireList) {
                                    String requireKey = require.getAttrKey().toLowerCase();
                                    if (StringUtils.isNotBlank(requireKey)) {
                                        Integer requireValue = BeanUtils.getValue(appUserMan, requireKey, Integer.class);
                                        if (requireValue != null) {
                                            if (requireValue.intValue() < require.getValue().intValue()) {
                                                pass = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            if (requireList != null && !requireList.isEmpty()) {
                                for (ResJobRequire require : requireList) {
                                    String requireKey = require.getAttrKey().toLowerCase();
                                    if (StringUtils.isNotBlank(requireKey)) {
                                        Integer requireValue = BeanUtils.getValue(appUserLady, requireKey, Integer.class);
                                        if (requireValue != null) {
                                            if (requireValue.intValue() < require.getValue().intValue()) {
                                                pass = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (pass) {
                        AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                        ResJob resJob = resJobStore.getById(jobId);
                        if (resJob != null) {
                            Persistent persistent = Persistent.UPDATE;
                            if (appUserJob == null) {
                                appUserJob = new AppUserJob();
                                appUserJob.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserJob.setUserId(appUser);
                                persistent = Persistent.SAVE;
                            }
                            appUserJob.setJobId(resJob);
                            appUserJob.setUseYn("Y");
                            bind(appUserJob, userId);
                            if (appUser.getUserGender().intValue() == 1) {
                                if (appUserMan != null) {
                                    useHour(appUserMan);
                                    appUserJobStore.save(appUserJob, persistent, appUserMan);
                                }
                            } else if (appUser.getUserGender().intValue() == 0) {
                                if (appUserLady != null) {
                                    useHour(appUserLady);
                                    appUserJobStore.save(appUserJob, persistent, appUserLady);
                                }
                            }
                            resultObj.put("text", "恭喜，即可上班");
                            resultObj.put("result", 0);
                        }
                    } else {
                        if (appUser.getUserGender().intValue() == 1) {
                            if (appUserMan != null) {
                                useHour(appUserMan);
                                appUserManStore.save(appUserMan, Persistent.UPDATE);
                            }
                        } else if (appUser.getUserGender().intValue() == 0) {
                            if (appUserLady != null) {
                                useHour(appUserLady);
                                appUserLadyStore.save(appUserLady, Persistent.UPDATE);
                            }
                        }
                        resultObj.put("text", "抱歉，您当前的能力无法胜任这份工作");
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
    public String callPlan(Long userId, Long planId) throws BizException {
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
                    && appUserPlanStore != null && resPlanStore != null&&resPlanEffectStore!=null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<ResPlanEffect> effectList = resPlanEffectStore.getListByPlanId(planId);
                    boolean pass = true;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    String resultEffect="";
                    if (appUser.getUserGender().intValue() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            if (effectList != null && !effectList.isEmpty()) {
                                for (ResPlanEffect effect : effectList) {
                                    String effectKey = effect.getAttrKey().toLowerCase();
                                    if (StringUtils.isNotBlank(effectKey)&&effect.getOperation().equals("SUB")) {
                                        Integer effectValue = BeanUtils.getValue(appUserMan, effectKey, Integer.class);
                                        if (effectValue != null) {
                                            if (effectValue.intValue() < effect.getValue().intValue()) {
                                                pass = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            if (effectList != null && !effectList.isEmpty()) {
                                for (ResPlanEffect effect : effectList) {
                                    String effectKey = effect.getAttrKey().toLowerCase();
                                    if (StringUtils.isNotBlank(effectKey)&&effect.getOperation().equals("SUB")) {
                                        Integer effectValue = BeanUtils.getValue(appUserLady, effectKey, Integer.class);
                                        if (effectValue != null) {
                                            if (effectValue.intValue() < effect.getValue().intValue()) {
                                                pass = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (pass) {
                        ResPlan resPlan = resPlanStore.getById(planId);
                        if (resPlan != null) {
                            AppUserPlan appUserPlan=new AppUserPlan();
                            appUserPlan.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserPlan.setUserId(appUser);
                            appUserPlan.setPlanId(resPlan);
                            appUserPlan.setUseYn("Y");
                            bind(appUserPlan, userId);
                            if (appUser.getUserGender().intValue() == 1) {
                                if (appUserMan != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        for (ResPlanEffect effect : effectList) {
                                            String effectKey = effect.getAttrKey().toLowerCase();
                                            if (StringUtils.isNotBlank(effectKey)) {
                                                Integer effectValue = BeanUtils.getValue(appUserMan, effectKey, Integer.class);
                                                if (effectValue != null) {
                                                    resultEffect+=GameUtils.getAttrNameMan(effectKey);
                                                    if(effect.getOperation().equals("SUB")){
                                                        effectValue=effectValue-effect.getValue();
                                                        resultEffect+="-"+effect.getValue();
                                                    }else  if(effect.getOperation().equals("ADD")){
                                                        effectValue=effectValue+effect.getValue();
                                                        resultEffect+="+"+effect.getValue();
                                                    }
                                                    BeanUtils.setValue(appUserMan, effectKey, effectValue);
                                                }
                                            }
                                        }
                                    }
                                    appUserPlan.setPlanOfDay(appUserMan.getDays());
                                    appUserPlan.setPlanOfHour(appUserMan.getHours());
                                    useHour(appUserMan);
                                    appUserPlanStore.save(appUserPlan,Persistent.SAVE, appUserMan);
                                }
                            } else if (appUser.getUserGender().intValue() == 0) {
                                if (appUserLady != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        for (ResPlanEffect effect : effectList) {
                                            String effectKey = effect.getAttrKey().toLowerCase();
                                            if (StringUtils.isNotBlank(effectKey)) {
                                                Integer effectValue = BeanUtils.getValue(appUserLady, effectKey, Integer.class);
                                                if (effectValue != null) {
                                                    resultEffect+=GameUtils.getAttrNameMan(effectKey);
                                                    if(effect.getOperation().equals("SUB")){
                                                        effectValue=effectValue-effect.getValue();
                                                        resultEffect+="-"+effect.getValue();
                                                    }else  if(effect.getOperation().equals("ADD")){
                                                        effectValue=effectValue+effect.getValue();
                                                        resultEffect+="+"+effect.getValue();
                                                    }
                                                    BeanUtils.setValue(appUserLady, effectKey, effectValue);
                                                }
                                            }
                                        }
                                    }
                                    appUserPlan.setPlanOfDay(appUserLady.getDays());
                                    appUserPlan.setPlanOfHour(appUserLady.getHours());
                                    useHour(appUserLady);
                                    appUserPlanStore.save(appUserPlan,Persistent.SAVE, appUserLady);
                                }
                            }
                            resultObj.put("text", "最终:"+resultEffect);
                            resultObj.put("result", 0);
                        }
                    } else {
                        resultObj.put("text", "抱歉，您当前的属性无法进行该操作");
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

    private void useHour(Object appUserObj) throws Exception {
        if (appUserObj != null) {
            Integer days = BeanUtils.getValue(appUserObj, "days", Integer.class);
            Integer hours = BeanUtils.getValue(appUserObj, "hours", Integer.class);
            if (days != null && hours != null) {
                if (hours > 0) {
                    hours = hours - 1;
                    BeanUtils.setValue(appUserObj, "hours", hours);
                } else if (hours == 0 && days > 0) {
                    hours = 8;
                    days = days - 1;
                    BeanUtils.setValue(appUserObj, "hours", hours);
                    BeanUtils.setValue(appUserObj, "days", days);
                }
            }
        }
    }
}
