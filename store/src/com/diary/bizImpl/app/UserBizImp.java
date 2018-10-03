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

    private void loadUserData(JSONObject resultObj, Long userId) throws BizException {
        JSONObject infoObj = null;
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserLimitStore != null && appUserJobStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getUserGender(), 1);
                    resultObj.put("attrList", attrArray);
                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                    String myJobId="";
                    if(appUserJob!=null){
                        myJobId=appUserJob.getJobId().getId()+"";
                    }
                    if (appUser.getUserGender().intValue() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            Integer day = appUserMan.getDays();
                            Integer jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "JOB");
                            Integer financialLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FINANCIAL");
                            Integer luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUCK");
                            Integer houseLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "HOUSE");
                            Integer carLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "CAR");
                            Integer coupleLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "COUPLE");
                            infoObj = JsonUtils.formIdEntity(appUserMan, 0);
                            if (infoObj != null) {
                                GameUtils.man(infoObj, jobLimit, financialLimit, luckLimit, houseLimit, carLimit, coupleLimit);
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            Integer day = appUserLady.getDays();
                            Integer jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "JOB");
                            Integer financialLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FINANCIAL");
                            Integer luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUCK");
                            Integer clothesLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "CLOTHES");
                            Integer luxuryLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUXURY");
                            Integer coupleLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "COUPLE");
                            infoObj = JsonUtils.formIdEntity(appUserLady, 0);
                            if (infoObj != null) {
                                GameUtils.lady(infoObj, jobLimit, financialLimit, luckLimit, clothesLimit, luxuryLimit, coupleLimit);
                            }
                        }
                    }
                    if (infoObj != null) {
                        if(appUserJob!=null){
                            infoObj.put("myJobId",myJobId);
                        }
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
    }

    @Override
    public String refresh(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        loadUserData(resultObj, userId);
        return resultObj.toString();
    }

    @Override
    public String start(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
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
                    }
                    loadUserData(resultObj, userId);
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
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (appUserManStore != null && appUserLadyStore != null && appUserStore != null
                    && resJobRequireStore != null && appUserJobStore != null && resJobStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<ResJobRequire> requireList = resJobRequireStore.getListByJobId(jobId);
                    boolean pass = true;
                    Integer jobLimit = 1;
                    Integer day = -1;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    AppUserLimit appUserLimit = null;
                    if (appUser.getUserGender().intValue() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            day = appUserMan.getDays();
                            jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "JOB");
                            if (requireList != null && !requireList.isEmpty()) {
                                pass = GameUtils.requirePass(requireList, appUserMan);
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            day = appUserLady.getDays();
                            jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "JOB");
                            if (requireList != null && !requireList.isEmpty()) {
                                pass = GameUtils.requirePass(requireList, appUserLady);
                            }
                        }
                    }
                    if (jobLimit == 0) {
                        appUserLimit = new AppUserLimit();
                        appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                        appUserLimit.setUserId(appUser);
                        appUserLimit.setAction("JOB");
                        appUserLimit.setDay(day);
                        bind(appUserLimit, userId);
                        appUserLimit.setUseYn("Y");
                    }
                    if (pass && jobLimit == 0) {
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
                                    appUserJobStore.save(appUserJob, persistent, appUserMan, appUserLimit);
                                }
                            } else if (appUser.getUserGender().intValue() == 0) {
                                if (appUserLady != null) {
                                    useHour(appUserLady);
                                    appUserJobStore.save(appUserJob, persistent, appUserLady, appUserLimit);
                                }
                            }
                            resultObj.put("text", "恭喜你轻而易举的得到了面试官的认可，获得了工作。");
                            resultObj.put("result", 0);
                        }
                    } else {
                        if (appUser.getUserGender().intValue() == 1) {
                            if (appUserMan != null) {
                                useHour(appUserMan);
                                appUserManStore.save(appUserMan, Persistent.UPDATE, appUserLimit);
                            }
                        } else if (appUser.getUserGender().intValue() == 0) {
                            if (appUserLady != null) {
                                useHour(appUserLady);
                                appUserLadyStore.save(appUserLady, Persistent.UPDATE, appUserLimit);
                            }
                        }
                        if (jobLimit == 1) {
                            resultObj.put("text", "抱歉，每日只能应聘一次工作");
                        } else {
                            resultObj.put("text", "你卖力的表现了下自己，但是面试官觉得你的能力无法胜任这份工作");
                        }
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
                            if (effect.getOperation().equals("SUB")) {
                                effectSubList.add(effect);
                            }
                        }
                    }
                    boolean pass = true;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    String resultEffect = "";
                    if (appUser.getUserGender().intValue() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            pass = GameUtils.requirePass(effectSubList, appUserMan);
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
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
                            if (appUser.getUserGender().intValue() == 1) {
                                if (appUserMan != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                        GameUtils.useEffect(effectList, appUserMan);
                                        resultEffect = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    }
                                    appUserPlan.setPlanOfDay(appUserMan.getDays());
                                    appUserPlan.setPlanOfHour(appUserMan.getHours());
                                    useHour(appUserMan);
                                    appUserPlanStore.save(appUserPlan, Persistent.SAVE, appUserMan);
                                }
                            } else if (appUser.getUserGender().intValue() == 0) {
                                if (appUserLady != null) {
                                    if (effectList != null && !effectList.isEmpty()) {
                                        AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                        GameUtils.useEffect(effectList, appUserLady);
                                        resultEffect = GameUtils.diffEffectLady(oldLady, appUserLady);
                                    }
                                    appUserPlan.setPlanOfDay(appUserLady.getDays());
                                    appUserPlan.setPlanOfHour(appUserLady.getHours());
                                    useHour(appUserLady);
                                    appUserPlanStore.save(appUserPlan, Persistent.SAVE, appUserLady);
                                }
                            }
                            resultObj.put("text", "最终:" + resultEffect);
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

    @Override
    public String nextDay(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            ResJobEffectStore resJobEffectStore = hsfServiceFactory.consumer(ResJobEffectStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            ResHouseEffectStore resHouseEffectStore = hsfServiceFactory.consumer(ResHouseEffectStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserJobStore != null
                    && appUserCarStore != null && appUserHouseStore != null && appUserClothesStore != null && appUserLuxuryStore != null && resJobEffectStore != null
                    && resCarEffectStore != null && resHouseEffectStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);

                    String resultText;

                    if (appUser.getUserGender().intValue() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            Integer days = appUserMan.getDays();
                            Integer hours = appUserMan.getHours();
                            if (days > 0 && hours == 0) {
                                appUserMan.setHours(8);
                                appUserMan.setDays(days - 1);
                                resultText = "第" + GameUtils.dayText(appUserMan.getDays()) + "天早上,";
                                List<AppUserCar> appUserCarList = appUserCarStore.getByUserId(userId);
                                List<AppUserHouse> appUserHouseList = appUserHouseStore.getByUserId(userId);
                                if (appUserJob == null && appUserCarList.isEmpty() && appUserHouseList.isEmpty()) {
                                    resultText += "你又没工作，又没车，又没房，哎，仅仅做了个梦！";
                                } else {
                                    if (appUserJob != null) {
                                        List<ResJobEffect> jobEffectList = resJobEffectStore.getListByJobId(appUserJob.getJobId().getId());
                                        if (jobEffectList != null && !jobEffectList.isEmpty()) {
                                            resultText += "因为有一份工作,";
                                            AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                            GameUtils.useEffect(jobEffectList, appUserMan);
                                            resultText += GameUtils.diffEffectMan(oldMan, appUserMan);
                                        }
                                    }
                                    if (appUserCarList != null && !appUserCarList.isEmpty()) {
                                        resultText += "因为有座驾的日常花费,";
                                        AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                        for (AppUserCar appUserCar : appUserCarList) {
                                            List<ResCarEffect> carEffectList = resCarEffectStore.getListByCarId(appUserCar.getCarId().getId());
                                            if (carEffectList != null && !carEffectList.isEmpty()) {
                                                GameUtils.useEffect(carEffectList, appUserMan);
                                            }
                                        }
                                        resultText += GameUtils.diffEffectMan(oldMan, appUserMan);
                                    }
                                    if (appUserHouseList != null && !appUserHouseList.isEmpty()) {
                                        resultText += "因为有置业的日常花费,";
                                        AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                        for (AppUserHouse appUserHouse : appUserHouseList) {
                                            List<ResHouseEffect> houseEffectList = resHouseEffectStore.getListByHouseId(appUserHouse.getHouseId().getId());
                                            if (houseEffectList != null && !houseEffectList.isEmpty()) {
                                                GameUtils.useEffect(houseEffectList, appUserMan);
                                            }
                                        }
                                        resultText += GameUtils.diffEffectMan(oldMan, appUserMan);
                                    }
                                }
                                appUserManStore.save(appUserMan, Persistent.UPDATE);
                                resultObj.put("result", 0);
                                resultObj.put("text", resultText);
                            }
                        }
                    } else if (appUser.getUserGender().intValue() == 0) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            Integer days = appUserLady.getDays();
                            Integer hours = appUserLady.getHours();
                            if (days > 0 && hours == 0) {
                                appUserLady.setHours(8);
                                appUserLady.setDays(days - 1);
                                resultText = "第" + GameUtils.dayText(appUserLady.getDays()) + "天早上,";
                                appUserLadyStore.save(appUserLady, Persistent.UPDATE);
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
    public String applyCouple(Long userId, Long coupleId) throws BizException {
        return null;
    }

    @Override
    public String buyHouse(Long userId, Long houseId) throws BizException {
        return null;
    }

    @Override
    public String sellHouse(Long userId, Long houseId) throws BizException {
        return null;
    }

    @Override
    public String buyCar(Long userId, Long carId) throws BizException {
        return null;
    }

    @Override
    public String sellCar(Long userId, Long carId) throws BizException {
        return null;
    }

    @Override
    public String buyClothes(Long userId, Long clothesId) throws BizException {
        return null;
    }

    @Override
    public String sellClothes(Long userId, Long clothesId) throws BizException {
        return null;
    }

    @Override
    public String buyLuxury(Long userId, Long luxuryId) throws BizException {
        return null;
    }

    @Override
    public String sellLuxury(Long userId, Long luxuryId) throws BizException {
        return null;
    }

    private void useHour(Object appUserObj) throws Exception {
        if (appUserObj != null) {
            Integer days = BeanUtils.getValue(appUserObj, "days", Integer.class);
            Integer hours = BeanUtils.getValue(appUserObj, "hours", Integer.class);
            if (days != null && hours != null) {
                if (hours > 0) {
                    hours = hours - 1;
                    BeanUtils.setValue(appUserObj, "hours", hours);
                }
            }
        }
    }
}
