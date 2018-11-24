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
import org.guiceside.commons.Page;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserBizImp extends BaseBiz implements UserBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    static String appId = "wxadc0c22656d6c116";
    static String secret = "890342da41f48c2dbbd1b4038060b056";

    @Override
    public String login(String code, Long userId, String nickName, String avatarUrl,
                        Integer gender,
                        String city, String province, String country) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null) {
                AppUser appUser = null;
                if (userId != null) {
                    appUser = appUserStore.getById(userId);
                }
                if (gender == 0) {
                    gender = 1;
                }
                if (appUser != null) {
                    appUser.setNickName(nickName);
                    appUser.setGender(gender);
                    appUser.setAvatarUrl(avatarUrl);
                    appUser.setCity(city);
                    appUser.setProvince(province);
                    appUser.setCountry(country);
                    bind(appUser, 1l);
                    appUserStore.save(appUser, Persistent.UPDATE);
                } else {
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
                                appUser = appUserStore.getByOpenId(openid);
                                if (appUser == null) {
                                    appUser = new AppUser();
                                    appUser.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                    appUser.setOpenId(openid);
                                    appUser.setNickName(nickName);
                                    appUser.setGender(gender);
                                    appUser.setAvatarUrl(avatarUrl);
                                    appUser.setCity(city);
                                    appUser.setProvince(province);
                                    appUser.setCountry(country);
                                    appUser.setPlayNumber(0);
                                    appUser.setUseYn("Y");
                                    bind(appUser, 1l);
                                    appUserStore.save(appUser, Persistent.SAVE);
                                }

                            }
                        }
                    }
                }
                if (appUser != null) {
                    Integer days = GameUtils.intDays;
                    Integer hours = GameUtils.intHours;
                    JSONObject userObj = JsonUtils.formIdEntity(appUser, 0);
                    if (userObj != null) {
                        GameUtils.minish(userObj);
                        userObj.put("userId", appUser.getId() + "");
                        resultObj.put("userData", userObj);
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(appUser.getId());
                            if (appUserMan != null) {
                                days = appUserMan.getDays();
                                hours = appUserMan.getHours();
                            }
                        } else if (appUser.getGender() == 2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(appUser.getId());
                            if (appUserLady != null) {
                                days = appUserLady.getDays();
                                hours = appUserLady.getHours();
                            }
                        }
                        userObj.put("days", days);
                        userObj.put("hours", hours);
                        resultObj.put("userData", userObj);
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
    public String info(String code, Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserManHistStore appUserManHistStore = hsfServiceFactory.consumer(AppUserManHistStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLadyHistStore appUserLadyHistStore = hsfServiceFactory.consumer(AppUserLadyHistStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserManHistStore != null
                    && appUserLadyHistStore != null) {
                AppUser appUser = null;
                if (userId != null) {
                    appUser = appUserStore.getById(userId);
                } else if (StringUtils.isNotBlank(code)) {
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
                                appUser = appUserStore.getByOpenId(openid);
                            }
                        }
                    }
                }
                if (appUser != null) {
                    Integer days = GameUtils.intDays;
                    Integer hours = GameUtils.intHours;
                    String lastComment = "";
                    JSONObject userObj = JsonUtils.formIdEntity(appUser, 0);
                    if (userObj != null) {
                        GameUtils.minish(userObj);
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(appUser.getId());
                            if (appUserMan != null) {
                                days = appUserMan.getDays();
                                hours = appUserMan.getHours();
                            }
                            AppUserManHist appUserManHist = appUserManHistStore.getByUserId(appUser.getId());
                            if (appUserManHist != null) {
                                lastComment = appUserManHist.getComment();
                            }
                        } else if (appUser.getGender() == 2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(appUser.getId());
                            if (appUserLady != null) {
                                days = appUserLady.getDays();
                                hours = appUserLady.getHours();
                            }
                            AppUserLadyHist appUserLadyHist = appUserLadyHistStore.getByUserId(appUser.getId());
                            if (appUserLadyHist != null) {
                                lastComment = appUserLadyHist.getComment();
                            }
                        }
                        userObj.put("days", days);
                        userObj.put("hours", hours);
                        userObj.put("lastComment", lastComment);
                        resultObj.put("userData", userObj);
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

    private void loadUserData(JSONObject resultObj, Long userId) throws BizException {
        JSONObject infoObj = null;
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            AppUserCoupleStore appUserCoupleStore = hsfServiceFactory.consumer(AppUserCoupleStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserLimitStore != null && appUserJobStore != null
                    && appUserCarStore != null && appUserHouseStore != null && appUserClothesStore != null && appUserLuxuryStore != null && appUserCoupleStore != null
                    && appUserFundStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getGender(), 1);
                    resultObj.put("attrList", attrArray);
                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                    AppUserCouple appUserCouple = appUserCoupleStore.getByUserId(userId);
                    String myJobId = "";
                    String myCoupleId = "";
                    if (appUserJob != null) {
                        myJobId = appUserJob.getJobId().getId() + "";
                    }
                    if (appUserCouple != null) {
                        myCoupleId = appUserCouple.getCoupleId().getId() + "";
                    }
                    Integer fund = appUserFundStore.getSumByUserId(userId);
                    if (fund == null) {
                        fund = 0;
                    }
                    if (fund < 0) {
                        fund = 0;
                    }
                    JSONArray myFundArray = new JSONArray();
                    Integer sumFundBuy = 0;
                    Integer sumFundMoney = 0;
                    Integer diffFundMoney = 0;
                    JSONObject myFundDiff = new JSONObject();
                    List<AppUserFund> appUserFundList = appUserFundStore.getByUserId(userId);
                    if (appUserFundList != null && !appUserFundList.isEmpty()) {
                        for (AppUserFund appUserFund : appUserFundList) {
                            ResFund resFund = appUserFund.getFundId();
                            if (resFund != null) {
                                myFundArray.add(resFund.getId() + "");
                                myFundDiff.put(resFund.getId() + "", GameUtils.formatGroupingUsed((appUserFund.getMoney().longValue() - appUserFund.getBuy().longValue())));
                                sumFundBuy += appUserFund.getBuy();
                                sumFundMoney += appUserFund.getMoney();
                            }
                        }
                        diffFundMoney = sumFundMoney - sumFundBuy;
                    }
                    boolean live = true;
                    if (appUser.getGender() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            if (appUserMan.getHealth() < 0) {
                                appUserMan.setDays(0);
                                appUserMan.setHours(0);
                                live = false;
                            }
                            JSONArray myCarArray = new JSONArray();
                            JSONObject myCarNumber = new JSONObject();
                            List<AppUserCar> appUserCarList = appUserCarStore.getByUserId(userId);
                            if (appUserCarList != null && !appUserCarList.isEmpty()) {
                                Set<Long> carSetIds = new HashSet<>();
                                for (AppUserCar appUserCar : appUserCarList) {
                                    ResCar resCar = appUserCar.getCarId();
                                    if (resCar != null) {
                                        if (!carSetIds.contains(resCar.getId())) {
                                            myCarNumber.put(resCar.getId() + "", 1);
                                            myCarArray.add(resCar.getId() + "");
                                            carSetIds.add(resCar.getId());
                                        } else {
                                            int carNumber = myCarNumber.getInt(resCar.getId() + "");
                                            myCarNumber.put(resCar.getId() + "", carNumber + 1);
                                        }
                                    }
                                }
                            }


                            JSONArray myHouseArray = new JSONArray();
                            JSONObject myHouseNumber = new JSONObject();
                            List<AppUserHouse> appUserHouseList = appUserHouseStore.getByUserId(userId);
                            if (appUserHouseList != null && !appUserHouseList.isEmpty()) {
                                Set<Long> houseSetIds = new HashSet<>();
                                for (AppUserHouse appUserHouse : appUserHouseList) {
                                    ResHouse resHouse = appUserHouse.getHouseId();
                                    if (resHouse != null) {
                                        if (!houseSetIds.contains(resHouse.getId())) {
                                            myHouseNumber.put(resHouse.getId() + "", 1);
                                            myHouseArray.add(resHouse.getId() + "");
                                            houseSetIds.add(resHouse.getId());
                                        } else {
                                            int houseNumber = myHouseNumber.getInt(resHouse.getId() + "");
                                            myHouseNumber.put(resHouse.getId() + "", houseNumber + 1);
                                        }
                                    }
                                }
                            }

                            Integer day = appUserMan.getDays();
                            Integer jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "JOB");
                            Integer luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUCK");
                            Integer houseLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "HOUSE");
                            Integer carLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "CAR");
                            Integer coupleLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "COUPLE");
                            Integer fundLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FUND");
                            infoObj = JsonUtils.formIdEntity(appUserMan, 0);
                            if (infoObj != null) {
                                GameUtils.man(infoObj, jobLimit, fundLimit, luckLimit, houseLimit, carLimit, coupleLimit);
                                infoObj.put("myCarArray", myCarArray);
                                infoObj.put("myCarNumber", myCarNumber);
                                infoObj.put("myHouseArray", myHouseArray);
                                infoObj.put("myHouseNumber", myHouseNumber);
                                infoObj.put("currentDay", GameUtils.currentDay(day));
                            }
                            if (!live) {
                                appUserManStore.save(appUserMan, Persistent.UPDATE);
                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            if (appUserLady.getHealth() < 0) {
                                appUserLady.setDays(0);
                                appUserLady.setHours(0);
                                live = false;
                            }
                            JSONArray myClothesArray = new JSONArray();
                            JSONObject myClothesNumber = new JSONObject();
                            List<AppUserClothes> appUserClothesList = appUserClothesStore.getByUserId(userId);
                            if (appUserClothesList != null && !appUserClothesList.isEmpty()) {
                                Set<Long> clothesSetIds = new HashSet<>();
                                for (AppUserClothes appUserClothes : appUserClothesList) {
                                    ResClothes resClothes = appUserClothes.getClothesId();
                                    if (resClothes != null) {
                                        if (!clothesSetIds.contains(resClothes.getId())) {
                                            myClothesNumber.put(resClothes.getId() + "", 1);
                                            myClothesArray.add(resClothes.getId() + "");
                                            clothesSetIds.add(resClothes.getId());
                                        } else {
                                            int carNumber = myClothesNumber.getInt(resClothes.getId() + "");
                                            myClothesNumber.put(resClothes.getId() + "", carNumber + 1);
                                        }
                                    }
                                }
                            }


                            JSONArray myLuxuryArray = new JSONArray();
                            JSONObject myLuxuryNumber = new JSONObject();
                            List<AppUserLuxury> appUserLuxuryList = appUserLuxuryStore.getByUserId(userId);
                            if (appUserLuxuryList != null && !appUserLuxuryList.isEmpty()) {
                                Set<Long> luxurySetIds = new HashSet<>();
                                for (AppUserLuxury appUserLuxury : appUserLuxuryList) {
                                    ResLuxury resLuxury = appUserLuxury.getLuxuryId();
                                    if (resLuxury != null) {
                                        if (!luxurySetIds.contains(resLuxury.getId())) {
                                            myLuxuryNumber.put(resLuxury.getId() + "", 1);
                                            myLuxuryArray.add(resLuxury.getId() + "");
                                            luxurySetIds.add(resLuxury.getId());
                                        } else {
                                            int houseNumber = myLuxuryNumber.getInt(resLuxury.getId() + "");
                                            myLuxuryNumber.put(resLuxury.getId() + "", houseNumber + 1);
                                        }
                                    }
                                }
                            }

                            Integer day = appUserLady.getDays();
                            Integer jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "JOB");
                            Integer luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUCK");
                            Integer clothesLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "CLOTHES");
                            Integer luxuryLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "LUXURY");
                            Integer coupleLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "COUPLE");
                            Integer fundLimit = appUserLimitStore.getCountByUserIdDayAction(userId, day, "FUND");
                            infoObj = JsonUtils.formIdEntity(appUserLady, 0);
                            if (infoObj != null) {
                                GameUtils.lady(infoObj, jobLimit, fundLimit, luckLimit, clothesLimit, luxuryLimit, coupleLimit);
                                infoObj.put("myClothesArray", myClothesArray);
                                infoObj.put("myClothesNumber", myClothesNumber);
                                infoObj.put("myLuxuryArray", myLuxuryArray);
                                infoObj.put("myLuxuryNumber", myLuxuryNumber);
                                infoObj.put("currentDay", GameUtils.currentDay(day));
                            }
                            if (!live) {
                                appUserLadyStore.save(appUserLady, Persistent.UPDATE);
                            }
                        }
                    }
                    if (infoObj != null) {
                        infoObj.put("live", live);
                        infoObj.put("myFundArray", myFundArray);
                        infoObj.put("myFundDiff", myFundDiff);
                        infoObj.put("diffFundMoney", diffFundMoney);
                        infoObj.put("fund", GameUtils.formatGroupingUsed(fund.longValue()));
                        infoObj.put("myJobId", myJobId);
                        infoObj.put("myCoupleId", myCoupleId);
                        resultObj.put("userState", infoObj);
                        if (!live) {
                            JSONArray resultArray = new JSONArray();
                            GameUtils.addResultArray(resultArray, "你死了！最终败给了老天！（健康值不足1）长期不在意健康，透支自己的身体，什么也没留下就离开了。你拼命的想：如果还有重来一次的机会，我愿意用一切身外之物交换。", null);
                            GameUtils.addResultArray(resultArray, "幸运的是我们帮你实现了这个愿望，用尽你一切身外之物换取了灵丹妙药，虽然财物没了，但是命保住了。", null);
                            resultObj.put("resultArray", resultArray);
                        }
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

                    Integer days = 0;
                    boolean newGame = false;
                    if (appUser.getGender() == 1) {
                        AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan == null) {
                            appUserMan = new AppUserMan();
                            appUserMan.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserMan.setUserId(appUser);
                            appUserMan.setHealth(100);
                            appUserMan.setMoney(8000);
                            appUserMan.setAbility(100);
                            appUserMan.setExperience(100);
                            appUserMan.setHappy(100);
                            appUserMan.setPositive(100);
                            appUserMan.setConnections(100);
                            appUserMan.setDays(GameUtils.intDays);
                            appUserMan.setHours(GameUtils.intHours);
                            appUserMan.setScore(0);
                            appUserMan.setUseYn("Y");
                            newGame = true;
                            bind(appUserMan, userId);
                            appUser.setPlayNumber(appUser.getPlayNumber() + 1);
                            bind(appUser, userId);
                            appUserManStore.save(appUserMan, Persistent.SAVE, appUser);
                        } else {
                            if (appUserMan.getScore() > 0 && StringUtils.isNotBlank(appUserMan.getComment())) {
                                replay(userId);
                                newGame = true;
                                appUser.setPlayNumber(appUser.getPlayNumber() + 1);
                                appUser.setLastScore(0);
                                appUser.setLastComment(null);
                            }
                            bind(appUser, userId);
                            appUserStore.save(appUser, Persistent.UPDATE);
                        }
                        days = appUserMan.getDays();
                    } else if (appUser.getGender() == 2) {
                        AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady == null) {
                            appUserLady = new AppUserLady();
                            appUserLady.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserLady.setUserId(appUser);
                            appUserLady.setHealth(100);
                            appUserLady.setMoney(8000);
                            appUserLady.setAbility(100);
                            appUserLady.setWisdom(100);
                            appUserLady.setHappy(100);
                            appUserLady.setBeauty(100);
                            appUserLady.setPopularity(100);
                            appUserLady.setDays(GameUtils.intDays);
                            appUserLady.setHours(GameUtils.intHours);
                            appUserLady.setScore(0);
                            appUserLady.setUseYn("Y");
                            bind(appUserLady, userId);
                            appUser.setPlayNumber(appUser.getPlayNumber() + 1);
                            bind(appUser, userId);
                            appUserLadyStore.save(appUserLady, Persistent.SAVE, appUser);
                            newGame = true;
                        } else {
                            if (appUserLady.getScore() > 0 && StringUtils.isNotBlank(appUserLady.getComment())) {
                                replay(userId);
                                newGame = true;
                                appUser.setPlayNumber(appUser.getPlayNumber() + 1);
                                appUser.setLastScore(0);
                                appUser.setLastComment(null);
                            }
                            bind(appUser, userId);
                            appUserStore.save(appUser, Persistent.UPDATE);
                        }
                        days = appUserLady.getDays();
                    }
                    loadUserData(resultObj, userId);
                    String nightText = "第" + GameUtils.dayText(days) + "天";
                    resultObj.put("nightText", nightText);
                    resultObj.put("newGame", newGame);
                    if (newGame) {
                        JSONArray resultArray = new JSONArray();
                        GameUtils.addResultArray(resultArray, "北京是你的舞台，初到北京，给你8000启动资金。", null);
                        GameUtils.addResultArray(resultArray, "你可以先找份最初级工作，这样每天可以获得工资。安顿好后要多四处逛逛见见市面，提高你的个人成长能力。", null);
                        GameUtils.addResultArray(resultArray, "看" + GameUtils.gameDays + "天后你能混出什么样来", null);
                        resultObj.put("resultArray", resultArray);
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
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            ResTipStore resTipStore = hsfServiceFactory.consumer(ResTipStore.class);
            if (appUserStore != null && resJobStore != null && resPlanStore != null && resCarStore != null && resHouseStore != null && resClothesStore != null
                    && resLuxuryStore != null && resCoupleStore != null && resLuckStore != null && resTipStore != null && resFundStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONArray jobArray = new JSONArray();
                    JSONArray planArray = new JSONArray();
                    JSONArray coupleArray = new JSONArray();
                    JSONArray carArray = new JSONArray();
                    JSONArray houseArray = new JSONArray();
                    JSONArray clothesArray = new JSONArray();
                    JSONArray luxuryArray = new JSONArray();
                    JSONArray luckArray = new JSONArray();
                    JSONArray fundArray = new JSONArray();
                    JSONArray tipArray = new JSONArray();
                    List<Selector> selectorList = new ArrayList<>();
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getGender()));
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
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getGender()));
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
                    selectorList.add(SelectorUtils.$eq("gender", appUser.getGender()));
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

                    selectorList.clear();
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    selectorList.add(SelectorUtils.$order("investPrice", true));
                    List<ResLuck> luckList = resLuckStore.getList(selectorList);
                    if (luckList != null && !luckList.isEmpty()) {
                        for (ResLuck resLuck : luckList) {
                            JSONObject luckObj = JsonUtils.formIdEntity(resLuck, 0);
                            if (luckObj != null) {
                                GameUtils.minish(luckObj);
                                luckArray.add(luckObj);
                            }
                        }
                    }

                    selectorList.clear();
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    selectorList.add(SelectorUtils.$order("probability", false));
                    List<ResFund> fundList = resFundStore.getList(selectorList);
                    if (fundList != null && !fundList.isEmpty()) {
                        for (ResFund resFund : fundList) {
                            JSONObject fundObj = JsonUtils.formIdEntity(resFund, 0);
                            if (fundObj != null) {
                                GameUtils.minish(fundObj);
                                fundArray.add(fundObj);
                            }
                        }
                    }

                    selectorList.clear();
                    selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                    List<ResTip> tipList = resTipStore.getList(selectorList);
                    if (tipList != null && !tipList.isEmpty()) {
                        for (ResTip resTip : tipList) {
                            JSONObject tipObj = JsonUtils.formIdEntity(resTip, 0);
                            if (tipObj != null) {
                                GameUtils.minish(tipObj);
                                tipArray.add(tipObj);
                            }
                        }
                    }

                    if (appUser.getGender() == 1) {
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

                    } else if (appUser.getGender() == 2) {
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
                    resultObj.put("tipArray", tipArray);
                    resultObj.put("jobArray", jobArray);
                    resultObj.put("planArray", planArray);
                    resultObj.put("coupleArray", coupleArray);
                    resultObj.put("carArray", carArray);
                    resultObj.put("houseArray", houseArray);
                    resultObj.put("clothesArray", clothesArray);
                    resultObj.put("luxuryArray", luxuryArray);
                    resultObj.put("luckArray", luckArray);
                    resultObj.put("fundArray", fundArray);
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
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            AppUserFundMarketStore appUserFundMarketStore = hsfServiceFactory.consumer(AppUserFundMarketStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserJobStore != null
                    && appUserCarStore != null && appUserHouseStore != null && appUserClothesStore != null && appUserLuxuryStore != null && resJobEffectStore != null
                    && resCarEffectStore != null && resHouseEffectStore != null && resClothesEffectStore != null && resLuxuryEffectStore != null
                    && resFundStore != null && appUserFundStore != null && appUserFundMarketStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);

                    JSONArray resultArray = new JSONArray();
                    JSONArray effectArray = null;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    Integer days = 0;
                    Integer hours = 0;
                    if (appUser.getGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            days = appUserMan.getDays();
                            hours = appUserMan.getHours();
                        }
                    } else if (appUser.getGender() == 2) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            days = appUserLady.getDays();
                            hours = appUserLady.getHours();
                        }
                    }
                    List<AppUserFundDetail> appUserFundDetails = new ArrayList<>();
                    List<AppUserFundMarket> appUserFundMarkets = new ArrayList<>();
                    List<AppUserFund> appUserFundList = appUserFundStore.getByUserId(userId);
                    if (appUserFundList != null && !appUserFundList.isEmpty()) {
                        int marketDay = days - 1;
                        for (AppUserFund appUserFund : appUserFundList) {
                            ResFund resFund = appUserFund.getFundId();
                            if (resFund != null) {
                                List<Double> doubleList = new ArrayList<>();
                                doubleList.add(resFund.getProbability());
                                doubleList.add(NumberUtils.subtract(1.00, resFund.getProbability()));

                                AppUserFundMarket appUserFundMarket = appUserFundMarketStore.getByUserFundId(userId, resFund.getId());
                                if (appUserFundMarket != null) {
                                    String market = appUserFundMarket.getMarket();
                                    if (StringUtils.isNotBlank(market)) {
                                        JSONArray marketArray = JSONArray.fromObject(market);
                                        int d = GameUtils.gameDays - marketDay;
                                        int sum = 7 + d;
                                        if (marketArray.size() < sum) {
                                            int diff = sum - marketArray.size();
                                            for (int i = 1; i <= diff; i++) {
                                                marketArray.add(String.valueOf(GameUtils.fundMarket(doubleList, resFund.getMinNum(), resFund.getMaxNum())));
                                            }
                                            appUserFundMarket.setMarket(marketArray.toString());
                                            appUserFundMarket.setUseYn("Y");
                                            bind(appUserFundMarket, userId);
                                        }

                                        Double newMarket = marketArray.getDouble(marketArray.size() - 1);
                                        Integer beforeMoney = appUserFund.getMoney();
                                        Integer afterMoney = 0;
                                        if (newMarket > 0) {
                                            afterMoney = Double.valueOf(NumberUtils.add(appUserFund.getMoney(), NumberUtils.multiply(appUserFund.getMoney(), NumberUtils.divide(newMarket, 100, 2), 0))).intValue();
                                        } else if (newMarket < 0) {
                                            Double fundMarket = NumberUtils.subtract(100, NumberUtils.subtract(0, newMarket, 2), 2);
                                            afterMoney = Double.valueOf(NumberUtils.multiply(appUserFund.getMoney(), NumberUtils.divide(fundMarket, 100, 2), 0)).intValue();
                                        }

                                        appUserFund.setMarket(newMarket);
                                        appUserFund.setMoney(afterMoney);
                                        bind(appUserFund, userId);
                                        AppUserFundDetail appUserFundDetail = new AppUserFundDetail();
                                        appUserFundDetail.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                        appUserFundDetail.setUserFundId(appUserFund);
                                        appUserFundDetail.setBeforeMoney(beforeMoney);
                                        appUserFundDetail.setAfterMoney(afterMoney);
                                        appUserFundDetail.setDay(marketDay);
                                        appUserFundDetail.setMarket(newMarket);
                                        bind(appUserFundDetail, userId);
                                        appUserFundDetail.setUseYn("Y");

                                        appUserFundDetails.add(appUserFundDetail);
                                        appUserFundMarkets.add(appUserFundMarket);
                                    }
                                }
                            }
                        }
                    }
                    if (appUser.getGender() == 1) {
                        if (appUserMan != null) {
                            if (days > 0 && hours == 0) {
                                appUserMan.setHours(GameUtils.intHours);
                                appUserMan.setDays(days - 1);
                                String resultText = "第" + GameUtils.dayText(appUserMan.getDays()) + "天,";
                                List<AppUserCar> appUserCarList = appUserCarStore.getByUserId(userId);
                                List<AppUserHouse> appUserHouseList = appUserHouseStore.getByUserId(userId);
                                if (appUserJob == null && appUserCarList.isEmpty() && appUserHouseList.isEmpty()) {
                                    resultText += "你又没工作，又没车，又没房，哎，仅仅做了个梦！";
                                    GameUtils.addResultArray(resultArray, resultText, null);
                                } else {
                                    resultText += "因为你";
                                    AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                    if (appUserJob != null) {
                                        List<ResJobEffect> jobEffectList = resJobEffectStore.getListByJobId(appUserJob.getJobId().getId());
                                        if (jobEffectList != null && !jobEffectList.isEmpty()) {
                                            resultText += "有一份工作,";
                                            GameUtils.useEffect(jobEffectList, appUserMan);
                                        }
                                    }
                                    if (appUserCarList != null && !appUserCarList.isEmpty()) {
                                        resultText += "有座驾,";
                                        for (AppUserCar appUserCar : appUserCarList) {
                                            List<ResCarEffect> carEffectList = resCarEffectStore.getListByCarId(appUserCar.getCarId().getId());
                                            if (carEffectList != null && !carEffectList.isEmpty()) {
                                                GameUtils.useEffect(carEffectList, appUserMan);
                                            }
                                        }
                                    }
                                    if (appUserHouseList != null && !appUserHouseList.isEmpty()) {
                                        resultText += "有房产";
                                        for (AppUserHouse appUserHouse : appUserHouseList) {
                                            List<ResHouseEffect> houseEffectList = resHouseEffectStore.getListByHouseId(appUserHouse.getHouseId().getId());
                                            if (houseEffectList != null && !houseEffectList.isEmpty()) {
                                                GameUtils.useEffect(houseEffectList, appUserMan);
                                            }
                                        }
                                    }
                                    effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                    GameUtils.addResultArray(resultArray, resultText, null);
                                    GameUtils.addResultArray(resultArray, "最终", effectArray);
                                }
                                appUserManStore.nextDay(appUserMan, Persistent.UPDATE, appUserFundList, appUserFundDetails, appUserFundMarkets);
                                resultObj.put("result", 0);
                                resultObj.put("resultArray", resultArray);
                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        if (appUserLady != null) {
                            if (days > 0 && hours == 0) {
                                appUserLady.setHours(GameUtils.intHours);
                                appUserLady.setDays(days - 1);
                                String resultText = "第" + GameUtils.dayText(appUserLady.getDays()) + "天,";
                                List<AppUserClothes> appUserClothesList = appUserClothesStore.getByUserId(userId);
                                List<AppUserLuxury> appUserLuxuryList = appUserLuxuryStore.getByUserId(userId);
                                if (appUserJob == null && appUserClothesList.isEmpty() && appUserLuxuryList.isEmpty()) {
                                    resultText += "你又没工作，又没衣服，又没饰品，哎，仅仅做了个梦！";
                                    GameUtils.addResultArray(resultArray, resultText, null);
                                } else {
                                    resultText += "因为你";
                                    AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                    if (appUserJob != null) {
                                        List<ResJobEffect> jobEffectList = resJobEffectStore.getListByJobId(appUserJob.getJobId().getId());
                                        if (jobEffectList != null && !jobEffectList.isEmpty()) {
                                            resultText += "有一份工作,";
                                            GameUtils.useEffect(jobEffectList, appUserLady);
                                        }
                                    }
                                    if (appUserClothesList != null && !appUserClothesList.isEmpty()) {
                                        resultText += "有衣服,";
                                        for (AppUserClothes appUserClothes : appUserClothesList) {
                                            List<ResClothesEffect> clothesEffectList = resClothesEffectStore.getListByClothesId(appUserClothes.getClothesId().getId());
                                            if (clothesEffectList != null && !clothesEffectList.isEmpty()) {
                                                GameUtils.useEffect(clothesEffectList, appUserLady);
                                            }
                                        }
                                    }
                                    if (appUserLuxuryList != null && !appUserLuxuryList.isEmpty()) {
                                        resultText += "有饰品,";
                                        for (AppUserLuxury appUserLuxury : appUserLuxuryList) {
                                            List<ResLuxuryEffect> luxuryEffectList = resLuxuryEffectStore.getListByLuxuryId(appUserLuxury.getLuxuryId().getId());
                                            if (luxuryEffectList != null && !luxuryEffectList.isEmpty()) {
                                                GameUtils.useEffect(luxuryEffectList, appUserLady);
                                            }
                                        }
                                    }

                                    effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                    GameUtils.addResultArray(resultArray, resultText, null);
                                    GameUtils.addResultArray(resultArray, "最终", effectArray);
                                }
                                appUserLadyStore.nextDay(appUserLady, Persistent.UPDATE, appUserFundList, appUserFundDetails, appUserFundMarkets);
                                resultObj.put("result", 0);
                                resultObj.put("resultArray", resultArray);
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
    public String done(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            AppUserCoupleStore appUserCoupleStore = hsfServiceFactory.consumer(AppUserCoupleStore.class);
            ResCoupleRequireStore resCoupleRequireStore = hsfServiceFactory.consumer(ResCoupleRequireStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            AppUserRankingsStore appUserRankingsStore = hsfServiceFactory.consumer(AppUserRankingsStore.class);
            ResCommentStore resCommentStore = hsfServiceFactory.consumer(ResCommentStore.class);
            AppUserManHistStore appUserManHistStore = hsfServiceFactory.consumer(AppUserManHistStore.class);
            AppUserLadyHistStore appUserLadyHistStore = hsfServiceFactory.consumer(AppUserLadyHistStore.class);
            ResCommentEvalStore resCommentEvalStore = hsfServiceFactory.consumer(ResCommentEvalStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserJobStore != null
                    && resJobRequireStore != null && appUserCarStore != null && appUserHouseStore != null && appUserClothesStore != null
                    && appUserLuxuryStore != null && appUserCoupleStore != null && resCoupleRequireStore != null && resCommentStore != null && appUserManHistStore != null
                    && resCommentEvalStore != null && appUserLadyHistStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    JSONObject userObj = JsonUtils.formIdEntity(appUser, 0);
                    if (userObj != null) {
                        GameUtils.minish(userObj);
                        resultObj.put("userData", userObj);
                    }
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    Integer score = 0;
                    String comment = "";
                    boolean flagRanking = true;
                    int day = 0;
                    int hour = 0;
                    if (appUser.getGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            if (StringUtils.isNotBlank(appUserMan.getComment()) && appUserMan.getScore() != 0) {
                                resultObj.put("comment", appUserMan.getComment());
                                flagRanking = false;
                            } else {
                                day = appUserMan.getDays();
                                hour = appUserMan.getHours();
                                if (day == 0 && hour == 0) {

                                    String jobTitle = "无";

                                    String coupleTitle = "无";

                                    JSONArray carTitle = null;

                                    JSONArray houseTitle = null;

                                    JSONArray commentText = null;

                                    score += GameUtils.getScoreAttr(appUserMan.getHealth(), "Health", appUser.getGender());
                                    if (score > 140) {
                                        score += 200 * appUserMan.getHealth();
                                    } else if (score > 100) {
                                        score += 150 * appUserMan.getHealth();
                                    } else if (score > 80) {
                                        score += 100 * appUserMan.getHealth();
                                    }
                                    score += GameUtils.getScoreAttr(appUserMan.getAbility(), "Ability", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserMan.getExperience(), "Experience", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserMan.getHappy(), "Happy", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserMan.getPositive(), "Positive", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserMan.getConnections(), "Connections", appUser.getGender());

                                    Integer fundMoney = appUserFundStore.getSumByUserId(userId);
                                    if (fundMoney == null || fundMoney < 0) {
                                        fundMoney = 0;
                                    }
                                    if (fundMoney == 0) {
                                        score -= 50000;
                                    } else if (fundMoney > 100000) {
                                        score += 100000;
                                    }
                                    Integer baseFundMoney = appUserFundStore.getSumBuyByUserId(userId);
                                    if (baseFundMoney == null || baseFundMoney < 0) {
                                        baseFundMoney = 0;
                                    }
                                    Integer diffFundMoney = fundMoney - baseFundMoney;

                                    score += GameUtils.getScoreMoney(appUserMan.getMoney());
                                    score += GameUtils.getScoreMoney(fundMoney);

                                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                                    Integer maxJobLevel = 0;
                                    Integer maxCarLevel = 0;
                                    Integer maxHouseLevel = 0;
                                    Integer maxCoupleLevel = 0;
                                    if (appUserJob != null) {
                                        maxJobLevel = appUserJob.getJobId().getLevel();
                                        jobTitle = appUserJob.getJobId().getTitle();
                                        List<ResJobRequire> jobRequireList = resJobRequireStore.getListByJobId(appUserJob.getJobId().getId());
                                        if (jobRequireList != null && !jobRequireList.isEmpty()) {
                                            for (ResJobRequire resJobRequire : jobRequireList) {
                                                score += GameUtils.getScoreAttr(resJobRequire.getValue(), resJobRequire.getAttrKey(), appUser.getGender());
                                            }
                                        }
                                        if (appUserJob.getJobId().getLevel() >= 3) {
                                            score += 10000 * appUserJob.getJobId().getLevel();
                                        }
                                    } else {
                                        score -= 50000;
                                    }
                                    List<AppUserCar> appUserCarList = appUserCarStore.getByUserId(userId);
                                    carTitle = new JSONArray();
                                    if (appUserCarList != null && !appUserCarList.isEmpty()) {
                                        for (AppUserCar appUserCar : appUserCarList) {
                                            carTitle.add(appUserCar.getCarId().getTitle());
                                            if (appUserCar.getCarId().getLevel() > maxCarLevel) {
                                                maxCarLevel = appUserCar.getCarId().getLevel();
                                            }
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserMan.getDays(), appUserCar.getCarId().getSellPrice(), appUserCar.getCarId().getOffsetSell());
                                            score += GameUtils.getScoreMoney(currentSellPrice);
                                            if (appUserCar.getCarId().getLevel() >= 3) {
                                                score += 10000 * appUserCar.getCarId().getLevel();
                                            }

                                        }
                                    } else {
                                        score -= 80000;
                                        carTitle.add("无");
                                    }

                                    List<AppUserHouse> appUserHouseList = appUserHouseStore.getByUserId(userId);
                                    houseTitle = new JSONArray();
                                    if (appUserHouseList != null && !appUserHouseList.isEmpty()) {
                                        for (AppUserHouse appUserHouse : appUserHouseList) {
                                            houseTitle.add(appUserHouse.getHouseId().getTitle());
                                            if (appUserHouse.getHouseId().getLevel() > maxHouseLevel) {
                                                maxHouseLevel = appUserHouse.getHouseId().getLevel();
                                            }
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserMan.getDays(), appUserHouse.getHouseId().getSellPrice(), appUserHouse.getHouseId().getOffsetSell());
                                            score += GameUtils.getScoreMoney(currentSellPrice);
                                            if (appUserHouse.getHouseId().getLevel() >= 3) {
                                                score += 20000 * appUserHouse.getHouseId().getLevel();
                                            }
                                        }
                                    } else {
                                        score -= 100000;
                                        houseTitle.add("无");
                                    }
                                    AppUserCouple appUserCouple = appUserCoupleStore.getByUserId(userId);
                                    if (appUserCouple != null) {
                                        coupleTitle = appUserCouple.getCoupleId().getTitle();
                                        score += 200000;
                                        maxCoupleLevel = 1;
                                    } else {
                                        score -= 50000;
                                    }

                                    if (maxJobLevel > 0 && maxCarLevel > 0 && maxHouseLevel > 0 && maxCoupleLevel > 0) {
                                        score += 100000;
                                        if (maxJobLevel >= 5 && maxCarLevel >= 4 && maxHouseLevel >= 4) {
                                            score += 300000;
                                        } else if (maxJobLevel >= 6 && maxCarLevel >= 5 && maxHouseLevel >= 5) {
                                            score += 500000;
                                        }
                                    }

                                    ScriptEngineManager manager = new ScriptEngineManager();
                                    ScriptEngine engine = manager.getEngineByName("js");
                                    engine.put("jobLevel", maxJobLevel);
                                    engine.put("carLevel", maxCarLevel);
                                    engine.put("houseLevel", maxHouseLevel);
                                    engine.put("coupleLevel", maxCoupleLevel);
                                    engine.put("score", score);

                                    engine.put("money", appUserMan.getMoney());
                                    engine.put("health", appUserMan.getHealth());
                                    engine.put("ability", appUserMan.getAbility());
                                    engine.put("experience", appUserMan.getExperience());
                                    engine.put("happy", appUserMan.getHappy());
                                    engine.put("positive", appUserMan.getPositive());
                                    engine.put("connections", appUserMan.getConnections());
                                    engine.put("comment", appUserMan.getComment());
                                    engine.put("fundMoney", fundMoney);
                                    engine.put("baseFundMoney", baseFundMoney);
                                    engine.put("diffFundMoney", diffFundMoney);

                                    List<ResComment> resCommentList = resCommentStore.getList(appUser.getGender());
                                    comment = "qiong";
                                    if (resCommentList != null && !resCommentList.isEmpty()) {

                                        for (ResComment commentMatch : resCommentList) {
                                            Object result = engine.eval(commentMatch.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                comment = commentMatch.getComment();
                                                break;
                                            }
                                        }
                                    }

                                    engine.put("comment", comment);

                                    commentText = new JSONArray();
                                    List<ResCommentEval> commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "money");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String moneyComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                moneyComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        commentText.add("你手头有现金" + appUserMan.getMoney() + "，" + moneyComment);
                                    }


                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "fund");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String fundComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                fundComment = commentEval.getComment();
                                                break;
                                            }
                                        }

                                        if (fundMoney > 0) {
                                            List<AppUserFund> appUserFundList = appUserFundStore.getByUserId(userId);
                                            if (appUserFundList != null && !appUserFundList.isEmpty()) {
                                                commentText.add("你在北京投资了" + fundMoney + "，其中:");
                                                String fundDetail = "";
                                                for (AppUserFund appUserFund : appUserFundList) {
                                                    fundDetail = appUserFund.getFundId().getTitle() + "总共投资了:" + appUserFund.getBuy() + ",投资回报:" +
                                                            GameUtils.calProfit(appUserFund.getMoney(), appUserFund.getBuy()) + "。";
                                                    commentText.add(fundDetail);
                                                }
                                                commentText.add(fundComment);
                                            }

                                        } else {
                                            commentText.add("你在北京投资了" + fundMoney + "，" + fundComment);
                                        }


                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "health");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String healthComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                healthComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(healthComment)) {
                                            commentText.add(healthComment);
                                        }

                                    }
                                    if (appUserJob != null) {
                                        commentText.add("你有一份工作：" + appUserJob.getJobId().getTitle() + "，" + appUserJob.getJobId().getRemarks());
                                    } else {
                                        if ((appUserMan.getMoney() + fundMoney) > 100000) {
                                            commentText.add("你没有工作，不过有的是办法赚钱");
                                        } else {
                                            commentText.add("你没有工作，成天游手好闲");
                                        }
                                    }

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "car");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String carComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                carComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(carComment)) {
                                            commentText.add(carComment);
                                        }

                                    }

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "house");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String houseComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                houseComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(houseComment)) {
                                            commentText.add(houseComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "ability");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String abilityComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                abilityComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(abilityComment)) {
                                            commentText.add(abilityComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "experience");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String experienceComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                experienceComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(experienceComment)) {
                                            commentText.add(experienceComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "happy");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String happyComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                happyComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(happyComment)) {
                                            commentText.add(happyComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "positive");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String positiveComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                positiveComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(positiveComment)) {
                                            commentText.add(positiveComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "connections");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String connectionsComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                connectionsComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(connectionsComment)) {
                                            commentText.add(connectionsComment);
                                        }
                                    }

                                    if (appUserCouple != null) {
                                        commentText.add("你的对象是：" + appUserCouple.getCoupleId().getTitle() + "，" + appUserCouple.getCoupleId().getComment());
                                    } else {
                                        commentText.add("你没有对象，单身的你和右手成为了最好的朋友，麒麟臂侠说的就是你！");
                                    }

                                    commentText.add("最后根据你的各项属性、资产以及独特的经历，综合评分为：" + score);

                                    commentText.add("你的这段北京生活经历最后评级为：" + GameUtils.getCommentText(comment));

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "comment");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String commentComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                commentComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(commentComment)) {
                                            commentText.add(commentComment);
                                        }

                                    }

                                    appUserMan.setScore(score);
                                    appUserMan.setComment(comment);

                                    AppUserManHist appUserManHist = appUserManHistStore.getByUserId(userId);
                                    Persistent persistentHist = Persistent.UPDATE;
                                    boolean updateFlag = false;
                                    if (appUserManHist == null) {
                                        persistentHist = Persistent.SAVE;
                                        appUserManHist = new AppUserManHist();
                                        appUserManHist.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                        appUserManHist.setUserId(appUser);
                                        updateFlag = true;
                                    } else {
                                        if (appUserMan.getScore() > appUserManHist.getScore()) {
                                            updateFlag = true;
                                        }
                                    }
                                    if (updateFlag) {
                                        appUserManHist.setHealth(appUserMan.getHealth());
                                        appUserManHist.setMoney(appUserMan.getMoney());
                                        appUserManHist.setFundMoney(fundMoney);
                                        appUserManHist.setAbility(appUserMan.getAbility());
                                        appUserManHist.setExperience(appUserMan.getExperience());
                                        appUserManHist.setHappy(appUserMan.getHappy());
                                        appUserManHist.setPositive(appUserMan.getPositive());
                                        appUserManHist.setConnections(appUserMan.getConnections());
                                        appUserManHist.setDays(appUserMan.getDays());
                                        appUserManHist.setHours(appUserMan.getHours());
                                        appUserManHist.setScore(appUserMan.getScore());
                                        appUserManHist.setComment(appUserMan.getComment());
                                        appUserManHist.setJobTitle(jobTitle);
                                        appUserManHist.setCoupleTitle(coupleTitle);
                                        appUserManHist.setCarTitle(carTitle != null ? carTitle.toString() : null);
                                        appUserManHist.setHouseTitle(houseTitle != null ? houseTitle.toString() : null);
                                        appUserManHist.setCommentText(commentText != null ? commentText.toString() : null);
                                        appUserManHist.setUseYn("Y");
                                    }
                                    bind(appUserManHist, userId);
                                    appUserMan.setJobTitle(jobTitle);
                                    appUserMan.setCoupleTitle(coupleTitle);
                                    appUserMan.setCarTitle(carTitle != null ? carTitle.toString() : null);
                                    appUserMan.setHouseTitle(houseTitle != null ? houseTitle.toString() : null);
                                    appUserMan.setCommentText(commentText != null ? commentText.toString() : null);

                                    resultObj.put("comment", appUserMan.getComment());
                                    bind(appUserMan, userId);
                                    appUser.setLastComment(comment);
                                    appUser.setLastScore(score);
                                    bind(appUser, userId);
                                    appUserManStore.saveDone(appUserMan, Persistent.UPDATE, appUser, appUserManHist, persistentHist);
                                } else {
                                    if (StringUtils.isNotBlank(appUser.getLastComment()) && appUser.getLastScore() != 0) {
                                        resultObj.put("comment", appUser.getLastComment());
                                        flagRanking = false;
                                    }
                                }
                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            if (StringUtils.isNotBlank(appUserLady.getComment()) && appUserLady.getScore() != 0) {
                                resultObj.put("comment", appUserLady.getComment());
                                flagRanking = false;
                            } else {
                                day = appUserLady.getDays();
                                hour = appUserLady.getHours();
                                if (day == 0 && hour == 0) {

                                    String jobTitle = "无";

                                    String coupleTitle = "无";

                                    JSONArray clothesTitle = null;

                                    JSONArray luxuryTitle = null;

                                    JSONArray commentText = null;

                                    score += GameUtils.getScoreAttr(appUserLady.getHealth(), "Health", appUser.getGender());
                                    if (score > 140) {
                                        score += 200 * appUserLady.getHealth();
                                    } else if (score > 100) {
                                        score += 150 * appUserLady.getHealth();
                                    } else if (score > 80) {
                                        score += 100 * appUserLady.getHealth();
                                    }
                                    score += GameUtils.getScoreAttr(appUserLady.getAbility(), "Ability", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserLady.getWisdom(), "Wisdom", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserLady.getHappy(), "Happy", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserLady.getBeauty(), "Beauty", appUser.getGender());
                                    score += GameUtils.getScoreAttr(appUserLady.getPopularity(), "Popularity", appUser.getGender());

                                    Integer fundMoney = appUserFundStore.getSumByUserId(userId);
                                    if (fundMoney == null || fundMoney < 0) {
                                        fundMoney = 0;
                                    }
                                    if (fundMoney == 0) {
                                        score -= 50000;
                                    } else if (fundMoney > 100000) {
                                        score += 100000;
                                    }
                                    Integer baseFundMoney = appUserFundStore.getSumBuyByUserId(userId);
                                    if (baseFundMoney == null || baseFundMoney < 0) {
                                        baseFundMoney = 0;
                                    }
                                    Integer diffFundMoney = fundMoney - baseFundMoney;

                                    score += GameUtils.getScoreMoney(appUserLady.getMoney());
                                    score += GameUtils.getScoreMoney(fundMoney);

                                    AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                                    Integer maxJobLevel = 0;
                                    Integer maxClothesLevel = 0;
                                    Integer maxLuxuryLevel = 0;
                                    Integer maxCoupleLevel = 0;
                                    if (appUserJob != null) {
                                        maxJobLevel = appUserJob.getJobId().getLevel();
                                        jobTitle = appUserJob.getJobId().getTitle();
                                        List<ResJobRequire> jobRequireList = resJobRequireStore.getListByJobId(appUserJob.getJobId().getId());
                                        if (jobRequireList != null && !jobRequireList.isEmpty()) {
                                            for (ResJobRequire resJobRequire : jobRequireList) {
                                                score += GameUtils.getScoreAttr(resJobRequire.getValue(), resJobRequire.getAttrKey(), appUser.getGender());
                                            }
                                        }
                                        if (appUserJob.getJobId().getLevel() >= 3) {
                                            score += 10000 * appUserJob.getJobId().getLevel();
                                        }
                                    } else {
                                        score -= 50000;
                                    }
                                    List<AppUserClothes> appUserClothesList = appUserClothesStore.getByUserId(userId);
                                    clothesTitle = new JSONArray();
                                    if (appUserClothesList != null && !appUserClothesList.isEmpty()) {
                                        for (AppUserClothes appUserClothes : appUserClothesList) {
                                            clothesTitle.add(appUserClothes.getClothesId().getTitle());
                                            if (appUserClothes.getClothesId().getLevel() > maxClothesLevel) {
                                                maxClothesLevel = appUserClothes.getClothesId().getLevel();
                                            }
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserLady.getDays(), appUserClothes.getClothesId().getSellPrice(), appUserClothes.getClothesId().getOffsetSell());

                                            score += GameUtils.getScoreMoney(currentSellPrice);
                                            if (appUserClothes.getClothesId().getLevel() >= 3) {
                                                score += 10000 * appUserClothes.getClothesId().getLevel();
                                            }

                                        }
                                    } else {
                                        score -= 80000;
                                        clothesTitle.add("无");
                                    }

                                    List<AppUserLuxury> appUserLuxuryList = appUserLuxuryStore.getByUserId(userId);
                                    luxuryTitle = new JSONArray();
                                    if (appUserLuxuryList != null && !appUserLuxuryList.isEmpty()) {
                                        for (AppUserLuxury appUserLuxury : appUserLuxuryList) {
                                            luxuryTitle.add(appUserLuxury.getLuxuryId().getTitle());
                                            if (appUserLuxury.getLuxuryId().getLevel() > maxLuxuryLevel) {
                                                maxLuxuryLevel = appUserLuxury.getLuxuryId().getLevel();
                                            }
                                            Integer currentSellPrice = GameUtils.dynamicPrice(appUserLady.getDays(), appUserLuxury.getLuxuryId().getSellPrice(), appUserLuxury.getLuxuryId().getOffsetSell());

                                            score += GameUtils.getScoreMoney(currentSellPrice);
                                            if (appUserLuxury.getLuxuryId().getLevel() >= 3) {
                                                score += 20000 * appUserLuxury.getLuxuryId().getLevel();
                                            }
                                        }
                                    } else {
                                        score -= 100000;
                                        luxuryTitle.add("无");
                                    }
                                    AppUserCouple appUserCouple = appUserCoupleStore.getByUserId(userId);
                                    if (appUserCouple != null) {
                                        coupleTitle = appUserCouple.getCoupleId().getTitle();
                                        score += 200000;
                                        maxCoupleLevel = 1;
                                    } else {
                                        score -= 50000;
                                    }

                                    if (maxJobLevel > 0 && maxClothesLevel > 0 && maxLuxuryLevel > 0 && maxCoupleLevel > 0) {
                                        score += 100000;
                                        if (maxJobLevel >= 5 && maxClothesLevel >= 4 && maxLuxuryLevel >= 4) {
                                            score += 300000;
                                        } else if (maxJobLevel >= 6 && maxClothesLevel >= 5 && maxLuxuryLevel >= 5) {
                                            score += 500000;
                                        }
                                    }

                                    ScriptEngineManager manager = new ScriptEngineManager();
                                    ScriptEngine engine = manager.getEngineByName("js");
                                    engine.put("jobLevel", maxJobLevel);
                                    engine.put("clothesLevel", maxClothesLevel);
                                    engine.put("luxuryLevel", maxLuxuryLevel);
                                    engine.put("coupleLevel", maxCoupleLevel);
                                    engine.put("score", score);

                                    engine.put("money", appUserLady.getMoney());
                                    engine.put("health", appUserLady.getHealth());
                                    engine.put("ability", appUserLady.getAbility());
                                    engine.put("wisdom", appUserLady.getWisdom());
                                    engine.put("happy", appUserLady.getHappy());
                                    engine.put("beauty", appUserLady.getBeauty());
                                    engine.put("popularity", appUserLady.getPopularity());
                                    engine.put("comment", appUserLady.getComment());
                                    engine.put("fundMoney", fundMoney);
                                    engine.put("baseFundMoney", baseFundMoney);
                                    engine.put("diffFundMoney", diffFundMoney);

                                    List<ResComment> resCommentList = resCommentStore.getList(appUser.getGender());
                                    comment = "qiong";
                                    if (resCommentList != null && !resCommentList.isEmpty()) {

                                        for (ResComment commentMatch : resCommentList) {
                                            Object result = engine.eval(commentMatch.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                comment = commentMatch.getComment();
                                                break;
                                            }
                                        }
                                    }

                                    engine.put("comment", comment);

                                    commentText = new JSONArray();
                                    List<ResCommentEval> commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "money");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String moneyComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                moneyComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        commentText.add("你手头有现金" + appUserLady.getMoney() + "，" + moneyComment);
                                    }


                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "fund");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String fundComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                fundComment = commentEval.getComment();
                                                break;
                                            }
                                        }

                                        if (fundMoney > 0) {
                                            List<AppUserFund> appUserFundList = appUserFundStore.getByUserId(userId);
                                            if (appUserFundList != null && !appUserFundList.isEmpty()) {
                                                commentText.add("你在北京投资了" + fundMoney + "，其中:");
                                                String fundDetail = "";
                                                for (AppUserFund appUserFund : appUserFundList) {
                                                    fundDetail = appUserFund.getFundId().getTitle() + "总共投资了:" + appUserFund.getBuy() + ",投资回报:" +
                                                            GameUtils.calProfit(appUserFund.getMoney(), appUserFund.getBuy()) + "。";
                                                    commentText.add(fundDetail);
                                                }
                                                commentText.add(fundComment);
                                            }

                                        } else {
                                            commentText.add("你在北京投资了" + fundMoney + "，" + fundComment);
                                        }


                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "health");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String healthComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                healthComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(healthComment)) {
                                            commentText.add(healthComment);
                                        }

                                    }
                                    if (appUserJob != null) {
                                        commentText.add("你有一份工作：" + appUserJob.getJobId().getTitle() + "，" + appUserJob.getJobId().getRemarks());
                                    } else {
                                        if ((appUserLady.getMoney() + fundMoney) > 100000) {
                                            commentText.add("你没有工作，不过有的是办法赚钱");
                                        } else {
                                            commentText.add("你没有工作，成天游手好闲");
                                        }
                                    }

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "car");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String carComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                carComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(carComment)) {
                                            commentText.add(carComment);
                                        }

                                    }

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "house");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String houseComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                houseComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(houseComment)) {
                                            commentText.add(houseComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "ability");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String abilityComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                abilityComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(abilityComment)) {
                                            commentText.add(abilityComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "wisdom");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String wisdomComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                wisdomComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(wisdomComment)) {
                                            commentText.add(wisdomComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "happy");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String happyComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                happyComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(happyComment)) {
                                            commentText.add(happyComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "beauty");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String beautyComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                beautyComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(beautyComment)) {
                                            commentText.add(beautyComment);
                                        }

                                    }
                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "popularity");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String popularityComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                popularityComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(popularityComment)) {
                                            commentText.add(popularityComment);
                                        }
                                    }

                                    if (appUserCouple != null) {
                                        commentText.add("你的对象是：" + appUserCouple.getCoupleId().getTitle() + "，" + appUserCouple.getCoupleId().getComment());
                                    } else {
                                        commentText.add("你没有对象，可能是因为长得太漂亮，别人觉得你有男朋友了，慢慢接受孤独终老的现实吧！");
                                    }

                                    commentText.add("最后根据你的各项属性、资产以及独特的经历，综合评分为：" + score);

                                    commentText.add("你的这段北京生活经历最后评级为：" + GameUtils.getCommentText(comment));

                                    commentEvalList = resCommentEvalStore.getList(appUser.getGender(), "comment");
                                    if (commentEvalList != null && !commentEvalList.isEmpty()) {
                                        String commentComment = "";
                                        for (ResCommentEval commentEval : commentEvalList) {
                                            Object result = engine.eval(commentEval.getLogicMatch());
                                            if (result.toString().equals("true")) {
                                                commentComment = commentEval.getComment();
                                                break;
                                            }
                                        }
                                        if (StringUtils.isNotBlank(commentComment)) {
                                            commentText.add(commentComment);
                                        }

                                    }

                                    appUserLady.setScore(score);
                                    appUserLady.setComment(comment);

                                    AppUserLadyHist appUserLadyHist = appUserLadyHistStore.getByUserId(userId);
                                    Persistent persistentHist = Persistent.UPDATE;
                                    boolean updateFlag = false;
                                    if (appUserLadyHist == null) {
                                        persistentHist = Persistent.SAVE;
                                        appUserLadyHist = new AppUserLadyHist();
                                        appUserLadyHist.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                        appUserLadyHist.setUserId(appUser);
                                        updateFlag = true;
                                    } else {
                                        if (appUserLady.getScore() > appUserLadyHist.getScore()) {
                                            updateFlag = true;
                                        }
                                    }
                                    if (updateFlag) {
                                        appUserLadyHist.setHealth(appUserLady.getHealth());
                                        appUserLadyHist.setMoney(appUserLady.getMoney());
                                        appUserLadyHist.setFundMoney(fundMoney);
                                        appUserLadyHist.setAbility(appUserLady.getAbility());
                                        appUserLadyHist.setWisdom(appUserLady.getWisdom());
                                        appUserLadyHist.setHappy(appUserLady.getHappy());
                                        appUserLadyHist.setBeauty(appUserLady.getBeauty());
                                        appUserLadyHist.setPopularity(appUserLady.getPopularity());
                                        appUserLadyHist.setDays(appUserLady.getDays());
                                        appUserLadyHist.setHours(appUserLady.getHours());
                                        appUserLadyHist.setScore(appUserLady.getScore());
                                        appUserLadyHist.setComment(appUserLady.getComment());
                                        appUserLadyHist.setJobTitle(jobTitle);
                                        appUserLadyHist.setCoupleTitle(coupleTitle);
                                        appUserLadyHist.setClothesTitle(clothesTitle != null ? clothesTitle.toString() : null);
                                        appUserLadyHist.setLuxuryTitle(luxuryTitle != null ? luxuryTitle.toString() : null);
                                        appUserLadyHist.setCommentText(commentText != null ? commentText.toString() : null);
                                        appUserLadyHist.setUseYn("Y");
                                    }
                                    appUserLady.setJobTitle(jobTitle);
                                    appUserLady.setCoupleTitle(coupleTitle);
                                    appUserLady.setClothesTitle(clothesTitle != null ? clothesTitle.toString() : null);
                                    appUserLady.setLuxuryTitle(luxuryTitle != null ? luxuryTitle.toString() : null);
                                    appUserLady.setCommentText(commentText != null ? commentText.toString() : null);

                                    bind(appUserLadyHist, userId);
                                    resultObj.put("comment", appUserLady.getComment());
                                    bind(appUserLady, userId);
                                    appUser.setLastComment(comment);
                                    appUser.setLastScore(score);
                                    bind(appUser, userId);
                                    appUserLadyStore.saveDone(appUserLady, Persistent.UPDATE, appUser, appUserLadyHist, persistentHist);
                                } else {
                                    if (StringUtils.isNotBlank(appUser.getLastComment()) && appUser.getLastScore() != 0) {
                                        resultObj.put("comment", appUser.getLastComment());
                                        flagRanking = false;
                                    }
                                }
                            }
                        }

                    }
                    if (flagRanking) {
                        AppUserRankings appUserRankings = appUserRankingsStore.getByUserId(userId);
                        Persistent persistent = Persistent.UPDATE;
                        if (appUserRankings == null) {
                            persistent = Persistent.SAVE;
                            appUserRankings = new AppUserRankings();
                            appUserRankings.setId(DrdsIDUtils.getID(DrdsTable.APP));
                            appUserRankings.setUserId(appUser);
                        }
                        appUserRankings.setScore(score);
                        appUserRankings.setComment(comment);
                        appUserRankings.setUseYn("Y");
                        bind(appUserRankings, userId);
                        appUserRankingsStore.save(appUserRankings, persistent);
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
    public String rankings(Long userId, Integer start, Integer limit) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserRankingsStore appUserRankingsStore = hsfServiceFactory.consumer(AppUserRankingsStore.class);
            if (appUserStore != null && appUserRankingsStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<Selector> selectorList = new ArrayList<>();
                    selectorList.add(SelectorUtils.$alias("userId", "userId"));
                    selectorList.add(SelectorUtils.$order("score", false));
                    Page<AppUserRankings> appUserRankingsPage = appUserRankingsStore.getPageList(start, limit, selectorList);
                    JSONArray rankingsArray = new JSONArray();
                    String mySeq = limit + "+";
                    if (appUserRankingsPage != null) {
                        List<AppUserRankings> appUserRankingsList = appUserRankingsPage.getResultList();
                        if (appUserRankingsList != null && !appUserRankingsList.isEmpty()) {
                            int seqIndex = 1;
                            for (AppUserRankings appUserRankings : appUserRankingsList) {
                                AppUser rankingsUser = appUserRankings.getUserId();
                                if (rankingsUser != null) {
                                    if (rankingsUser.getId().equals(userId)) {
                                        mySeq = seqIndex + "";
                                    }
                                    JSONObject userObj = JsonUtils.formIdEntity(rankingsUser, 0);
                                    if (userObj != null) {
                                        userObj.put("score", appUserRankings.getScore());
                                        userObj.put("comment", appUserRankings.getComment());
                                        GameUtils.minish(userObj);
                                        rankingsArray.add(userObj);
                                    }
                                    seqIndex++;
                                }

                            }
                        }
                    }
                    JSONObject userObj = JsonUtils.formIdEntity(appUser, 0);
                    if (userObj != null) {
                        GameUtils.minish(userObj);
                        AppUserRankings appUserRankings = appUserRankingsStore.getByUserId(userId);
                        if (appUserRankings != null) {
                            userObj.put("score", appUserRankings.getScore());
                            userObj.put("comment", appUserRankings.getComment());
                            userObj.put("seq", mySeq);
                        } else {
                            userObj.put("seq", "暂无");
                            userObj.put("score", 0);
                            userObj.put("comment", appUser.getLastComment());
                        }
                        resultObj.put("list", rankingsArray);
                        resultObj.put("myData", userObj);
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

    @Override
    public String myReport(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            if (appUserStore != null && appUserManStore != null && appUserLadyStore != null && appUserFundStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    JSONObject infoObj = null;
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getGender(), 1);
                    resultObj.put("attrList", attrArray);
                    Integer fundMoney = appUserFundStore.getSumByUserId(userId);
                    if (fundMoney == null || fundMoney < 0) {
                        fundMoney = 0;
                    }
                    if (appUser.getGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            infoObj = JsonUtils.formIdEntity(appUserMan, 0);
                            if (infoObj != null) {
                                infoObj.put("fundMoney", fundMoney);
                                GameUtils.minish(infoObj);

                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            infoObj = JsonUtils.formIdEntity(appUserLady, 0);
                            if (infoObj != null) {
                                infoObj.put("fundMoney", fundMoney);
                                GameUtils.minish(infoObj);
                            }
                        }
                    }
                    resultObj.put("data", infoObj);
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
    public String report(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManHistStore appUserManHistStore = hsfServiceFactory.consumer(AppUserManHistStore.class);
            AppUserLadyHistStore appUserLadyHistStore = hsfServiceFactory.consumer(AppUserLadyHistStore.class);
            if (appUserStore != null && appUserManHistStore != null && appUserLadyHistStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserManHist appUserManHist = null;
                    AppUserLadyHist appUserLadyHist = null;
                    JSONObject infoObj = null;
                    JSONArray attrArray = new JSONArray();
                    GameUtils.attrList(attrArray, appUser.getGender(), 1);
                    resultObj.put("attrList", attrArray);
                    if (appUser.getGender() == 1) {
                        appUserManHist = appUserManHistStore.getByUserId(userId);
                        if (appUserManHist != null) {
                            infoObj = JsonUtils.formIdEntity(appUserManHist, 0);
                            if (infoObj != null) {
                                GameUtils.minish(infoObj);

                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        appUserLadyHist = appUserLadyHistStore.getByUserId(userId);
                        if (appUserLadyHist != null) {
                            infoObj = JsonUtils.formIdEntity(appUserLadyHist, 0);
                            if (infoObj != null) {
                                GameUtils.minish(infoObj);
                            }
                        }
                    }
                    resultObj.put("data", infoObj);
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
    public String replay(Long userId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            AppUserCarStore appUserCarStore = hsfServiceFactory.consumer(AppUserCarStore.class);
            AppUserHouseStore appUserHouseStore = hsfServiceFactory.consumer(AppUserHouseStore.class);
            AppUserClothesStore appUserClothesStore = hsfServiceFactory.consumer(AppUserClothesStore.class);
            AppUserLuxuryStore appUserLuxuryStore = hsfServiceFactory.consumer(AppUserLuxuryStore.class);
            AppUserCoupleStore appUserCoupleStore = hsfServiceFactory.consumer(AppUserCoupleStore.class);
            AppUserFundStore appUserFundStore = hsfServiceFactory.consumer(AppUserFundStore.class);
            AppUserFundMarketStore appUserFundMarketStore = hsfServiceFactory.consumer(AppUserFundMarketStore.class);
            AppUserFundDetailStore appUserFundDetailStore = hsfServiceFactory.consumer(AppUserFundDetailStore.class);
            AppUserLuckStore appUserLuckStore = hsfServiceFactory.consumer(AppUserLuckStore.class);
            AppUserPlanStore appUserPlanStore = hsfServiceFactory.consumer(AppUserPlanStore.class);
            if (appUserStore != null) {
                AppUser appUser = null;
                if (userId != null) {
                    appUser = appUserStore.getById(userId);
                    if (appUser != null) {
                        if (appUser.getGender() == 1) {
                            AppUserMan appUserMan = appUserManStore.getByUserId(userId);
                            if (appUserMan != null) {
                                appUserMan.setHealth(100);
                                appUserMan.setMoney(8000);
                                appUserMan.setAbility(100);
                                appUserMan.setExperience(100);
                                appUserMan.setHappy(100);
                                appUserMan.setPositive(100);
                                appUserMan.setConnections(100);
                                appUserMan.setDays(GameUtils.intDays);
                                appUserMan.setHours(GameUtils.intHours);
                                appUserMan.setScore(0);
                                appUserMan.setComment(null);
                                appUserMan.setJobTitle(null);
                                appUserMan.setCoupleTitle(null);
                                appUserMan.setCarTitle(null);
                                appUserMan.setHouseTitle(null);
                                appUserMan.setCommentText(null);
                                List<AppUserLimit> userLimitList = appUserLimitStore.getListByUserId(userId);
                                AppUserJob userJob = appUserJobStore.getByUserId(userId);
                                List<AppUserCar> userCarList = appUserCarStore.getByUserId(userId);
                                List<AppUserHouse> userHouseList = appUserHouseStore.getByUserId(userId);
                                AppUserCouple userCouple = appUserCoupleStore.getByUserId(userId);
                                List<AppUserFund> userFundList = appUserFundStore.getByUserId(userId);
                                List<AppUserFundMarket> userFundMarketList = appUserFundMarketStore.getByUserId(userId);
                                List<AppUserFundDetail> userFundDetailList = appUserFundDetailStore.getByUserId(userId);
                                List<AppUserLuck> userLuckList = appUserLuckStore.getByUserId(userId);
                                List<AppUserPlan> userPlanList = appUserPlanStore.getByUserId(userId);
                                appUserManStore.delete(appUserMan, userLimitList, userJob, userCarList
                                        , userHouseList, userCouple, userFundList, userFundMarketList, userFundDetailList, userLuckList, userPlanList);
                                resultObj.put("result", 0);
                            }
                        } else if (appUser.getGender() == 2) {
                            AppUserLady appUserLady = appUserLadyStore.getByUserId(userId);
                            if (appUserLady != null) {
                                appUserLady.setHealth(100);
                                appUserLady.setMoney(8000);
                                appUserLady.setAbility(100);
                                appUserLady.setWisdom(100);
                                appUserLady.setHappy(100);
                                appUserLady.setBeauty(100);
                                appUserLady.setPopularity(100);
                                appUserLady.setDays(GameUtils.intDays);
                                appUserLady.setHours(GameUtils.intHours);
                                appUserLady.setScore(0);
                                appUserLady.setComment(null);
                                appUserLady.setJobTitle(null);
                                appUserLady.setCoupleTitle(null);
                                appUserLady.setClothesTitle(null);
                                appUserLady.setLuxuryTitle(null);
                                appUserLady.setCommentText(null);
                                List<AppUserLimit> userLimitList = appUserLimitStore.getListByUserId(userId);
                                AppUserJob userJob = appUserJobStore.getByUserId(userId);
                                List<AppUserClothes> userClothesList = appUserClothesStore.getByUserId(userId);
                                List<AppUserLuxury> userLuxuryList = appUserLuxuryStore.getByUserId(userId);
                                AppUserCouple userCouple = appUserCoupleStore.getByUserId(userId);
                                List<AppUserFund> userFundList = appUserFundStore.getByUserId(userId);
                                List<AppUserFundMarket> userFundMarketList = appUserFundMarketStore.getByUserId(userId);
                                List<AppUserFundDetail> userFundDetailList = appUserFundDetailStore.getByUserId(userId);
                                List<AppUserLuck> userLuckList = appUserLuckStore.getByUserId(userId);
                                List<AppUserPlan> userPlanList = appUserPlanStore.getByUserId(userId);
                                appUserLadyStore.delete(appUserLady, userLimitList, userJob, userClothesList
                                        , userLuxuryList, userCouple, userFundList, userFundMarketList, userFundDetailList, userLuckList, userPlanList);
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
}
