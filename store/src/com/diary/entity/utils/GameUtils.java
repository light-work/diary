package com.diary.entity.utils;

import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.res.ResEventResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.NoSuchPropertyException;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.OKHttpUtil;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.DecimalFormat;
import java.util.*;

public class GameUtils {

    public static final int gameDays = 7;

    public static final int intDays = 6;

    public static final int intHours = 6;

    public static double fundMarket(List<Double> doubleList, Double minNum, Double maxNum) {
        Random r = new Random();
        Double market = null;
        int point = r.nextInt(99 - 10) + 10;
        int flagInt = GameUtils.lottery(doubleList);
        int temp = r.nextInt(maxNum.intValue() - minNum.intValue()) + minNum.intValue();
        if (flagInt == 1) {
            temp = temp * -1;
        }
        market = new Double(temp + "." + point);
        return market;
    }

    public static int randGetIndex(int size) {
        int b = (int) (Math.random() * size - 1);
        return b;
    }

    public static void randSelector(List<Selector> selectorList) {
        List<Double> orignalRates = new ArrayList<>();
        orignalRates.add(0.49);
        orignalRates.add(0.51);
        int flag = lottery(orignalRates);
        if (flag == 0) {
            selectorList.add(SelectorUtils.$order("id", true));
        } else {
            selectorList.add(SelectorUtils.$order("id", false));
        }
    }

    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }

        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }

        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }

    public static void main(String[] args) throws Exception {
        JSONObject a = new JSONObject();
        a.put("id", "6453477704225898442");
        System.out.println(a.getLong("id") + "");
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(0.55);
        doubleList.add(0.45);
        JSONArray sa = new JSONArray();
        for (int i = 1; i <= 10; i++) {
            sa.add(String.valueOf(fundMarket(doubleList, 2.48d, 4.22)));
        }
        System.out.println(sa.toString());
        System.out.println(0 - (-8));


        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        engine.put("jobLevel", 3);
        engine.put("carLevel", 5);
        engine.put("houseLevel", 5);
        engine.put("coupleLevel", 1);
        engine.put("money", 900000);
        engine.put("fundMoney", 500000);
        engine.put("health", 80);
        engine.put("comment", "feng");
        Object result = engine.eval("comment==='feng'");
        System.out.println(result);

//        Map<Integer,String> stringMap=new HashMap<>();
//        stringMap.put(0,"hun");
//        stringMap.put(1,"ming");
//        stringMap.put(2,"feng");
//        stringMap.put(3,"lu");
//        stringMap.put(4,"qiong");
//        stringMap.put(5,"qiong");
//        List<String> matchList=new ArrayList<>();
//        matchList.add("(jobLevel>=5&&carLevel>=5&&houseLevel>=5&&coupleLevel>=1&&money>=2000000&&fundMoney>=500000&&health>=80)");
//        matchList.add("(jobLevel>=3&&carLevel>=3&&houseLevel>=3&&coupleLevel>=1&&money>=1000000&&fundMoney>=200000&&health>=70)");
//        matchList.add("(jobLevel>=3&&carLevel>=1&&houseLevel>=1&&coupleLevel>=0&&money>=500000&&fundMoney>=100000&&health>=60)");
//        matchList.add("(jobLevel>=1&&carLevel>=0&&houseLevel>=0&&coupleLevel>=0&&money>=300000&&fundMoney>=0&&health>=50)");
//        String str = "(jobLevel>=0&&carLevel==0&&houseLevel==0&&coupleLevel==0&&money<=300000&&fundMoney==0&&health>=50)";
//
//        int index=0;
//        for(String ma:matchList){
//             result = engine.eval(ma);
//            if(result.toString().equals("true")){
//                break;
//            }
//            index++;
//        }
//        System.out.println(stringMap.get(index));
//
//        System.out.println(DrdsIDUtils.getID(DrdsTable.APP));

        Map<String, String> parMap = new HashMap<>();
        parMap.put("userId", "6465580136151400448");
        String r = OKHttpUtil.post("https://game.jinrongzhushou.com/v1/user/replay", parMap);

        System.out.println(r);


    }

    public static Integer dynamicPrice(Integer day, Integer price, Integer offset) throws Exception {
        offset = offset * currentDay(day);
        offset = 100 + offset;
        Double dyPrice = NumberUtils.divide(NumberUtils.multiply(price, offset, 2), 100, 2);
        price = Long.valueOf(Math.round(dyPrice)).intValue();
        return price;
    }

    public static Integer currentDay(Integer day) throws Exception {
        return gameDays - day;
    }

    public static String dayText(Integer day) throws Exception {
        String dayText = null;
        int diffDays = gameDays - day;
        switch (diffDays) {
            case 1:
                dayText = "一";
                break;
            case 2:
                dayText = "二";
                break;
            case 3:
                dayText = "三";
                break;
            case 4:
                dayText = "四";
                break;
            case 5:
                dayText = "五";
                break;
            case 6:
                dayText = "六";
                break;
            case 7:
                dayText = "七";
                break;
            case 8:
                dayText = "八";
                break;
            case 9:
                dayText = "九";
                break;
            case 10:
                dayText = "十";
                break;
        }
        return dayText;
    }

    public static void man(JSONObject infoObj, Integer jobLimit,
                           Integer fundLimit,
                           Integer luckLimit,
                           Integer houseLimit,
                           Integer carLimit, Integer coupleLimit) throws Exception {
        if (infoObj != null) {
            infoObj.put("jobLimit", jobLimit);
            infoObj.put("fundLimit", fundLimit);
            infoObj.put("luckLimit", luckLimit);
            infoObj.put("houseLimit", houseLimit);
            infoObj.put("carLimit", carLimit);
            infoObj.put("coupleLimit", coupleLimit);


            minish(infoObj);
            infoObj.put("moneyNumber", infoObj.getLong("money"));
            converInfoNumber(infoObj, "health");
            converInfoNumber(infoObj, "money");
            converInfoNumber(infoObj, "ability");
            converInfoNumber(infoObj, "experience");
            converInfoNumber(infoObj, "happy");
            converInfoNumber(infoObj, "positive");
            converInfoNumber(infoObj, "connections");
            converInfoNumber(infoObj, "days");
            converInfoNumber(infoObj, "hours");
        }
    }

    public static void lady(JSONObject infoObj,
                            Integer jobLimit,
                            Integer fundLimit,
                            Integer luckLimit,
                            Integer clothesLimit,
                            Integer luxuryLimit, Integer coupleLimit) throws Exception {
        if (infoObj != null) {
            infoObj.put("jobLimit", jobLimit);
            infoObj.put("fundLimit", fundLimit);
            infoObj.put("luckLimit", luckLimit);
            infoObj.put("clothesLimit", clothesLimit);
            infoObj.put("luxuryLimit", luxuryLimit);
            infoObj.put("coupleLimit", coupleLimit);
            minish(infoObj);
            infoObj.put("moneyNumber", infoObj.getLong("money"));
            converInfoNumber(infoObj, "health");
            converInfoNumber(infoObj, "money");
            converInfoNumber(infoObj, "ability");
            converInfoNumber(infoObj, "wisdom");
            converInfoNumber(infoObj, "happy");
            converInfoNumber(infoObj, "beauty");
            converInfoNumber(infoObj, "popularity");
            converInfoNumber(infoObj, "days");
            converInfoNumber(infoObj, "hours");
        }
    }

    public static void minish(JSONObject jsonObject) {
        jsonObject.put("id", jsonObject.getLong("id") + "");
        jsonObject.remove("created");
        jsonObject.remove("createdBy");
        jsonObject.remove("updated");
        jsonObject.remove("updatedBy");
        jsonObject.remove("useYn");
    }

    public static Integer getBaseScoreAttrMan(String attrKey) {
        Integer base = 0;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                base = 1000;
                break;
            case "ABILITY":
                base = 600;
                break;
            case "EXPERIENCE":
                base = 600;
                break;
            case "HAPPY":
                base = 700;
                break;
            case "POSITIVE":
                base = 700;
                break;
            case "CONNECTIONS":
                base = 600;
                break;
        }
        return base;
    }

    public static String getAttrNameMan(String attrKey) {
        String attrName = null;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                attrName = "健康";
                break;
            case "MONEY":
                attrName = "现金";
                break;
            case "ABILITY":
                attrName = "工作能力";
                break;
            case "EXPERIENCE":
                attrName = "社会经验";
                break;
            case "HAPPY":
                attrName = "快乐";
                break;
            case "POSITIVE":
                attrName = "正义";
                break;
            case "CONNECTIONS":
                attrName = "人脉";
                break;
            case "CAR":
                attrName = "座驾";
                break;
            case "HOUSE":
                attrName = "房产";
                break;
        }
        return attrName;
    }

    public static Integer getBaseScoreAttrLady(String attrKey) {
        Integer base = 0;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                base = 1000;
                break;
            case "MONEY":
                base = 1000;
                break;
            case "ABILITY":
                base = 600;
                break;
            case "WISDOM":
                base = 600;
                break;
            case "HAPPY":
                base = 700;
                break;
            case "BEAUTY":
                base = 700;
                break;
            case "POPULARITY":
                base = 600;
                break;
        }
        return base;
    }

    public static String getAttrNameLady(String attrKey) {
        String attrName = null;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                attrName = "健康";
                break;
            case "MONEY":
                attrName = "现金";
                break;
            case "ABILITY":
                attrName = "工作能力";
                break;
            case "WISDOM":
                attrName = "处世智慧";
                break;
            case "HAPPY":
                attrName = "快乐";
                break;
            case "BEAUTY":
                attrName = "美貌";
                break;
            case "POPULARITY":
                attrName = "知名度";
                break;
        }
        return attrName;
    }

    public static void attrList(JSONArray jsonArray, Integer gender, Integer isManage) {


        if (gender == 1) {
            JSONObject operationHEALTH = new JSONObject();
            operationHEALTH.put("text", "健康");
            operationHEALTH.put("value", isManage == 0 ? "HEALTH" : "HEALTH".toLowerCase());
            jsonArray.add(operationHEALTH);

            JSONObject operationMONEY = new JSONObject();
            operationMONEY.put("text", "现金");
            operationMONEY.put("value", isManage == 0 ? "MONEY" : "MONEY".toLowerCase());
            jsonArray.add(operationMONEY);

            if (isManage != 0) {
                JSONObject operationPROFIT = new JSONObject();
                operationPROFIT.put("text", "理财收益");
                operationPROFIT.put("value", isManage == 0 ? "FUND" : "FUND".toLowerCase());
                jsonArray.add(operationPROFIT);
            }


            JSONObject operationABILITY = new JSONObject();
            operationABILITY.put("text", "工作能力");
            operationABILITY.put("value", isManage == 0 ? "ABILITY" : "ABILITY".toLowerCase());
            jsonArray.add(operationABILITY);

            JSONObject operationEXPERIENCE = new JSONObject();
            operationEXPERIENCE.put("text", "社会经验");
            operationEXPERIENCE.put("value", isManage == 0 ? "EXPERIENCE" : "EXPERIENCE".toLowerCase());
            jsonArray.add(operationEXPERIENCE);

            JSONObject operationHAPPY = new JSONObject();
            operationHAPPY.put("text", "快乐");
            operationHAPPY.put("value", isManage == 0 ? "HAPPY" : "HAPPY".toLowerCase());
            jsonArray.add(operationHAPPY);

            JSONObject operationPOSITIVE = new JSONObject();
            operationPOSITIVE.put("text", "正义");
            operationPOSITIVE.put("value", isManage == 0 ? "POSITIVE" : "POSITIVE".toLowerCase());
            jsonArray.add(operationPOSITIVE);

            JSONObject operationCONNECTIONS = new JSONObject();
            operationCONNECTIONS.put("text", "人脉");
            operationCONNECTIONS.put("value", isManage == 0 ? "CONNECTIONS" : "CONNECTIONS".toLowerCase());

            jsonArray.add(operationCONNECTIONS);
        } else if (gender == 2) {
            JSONObject operationHEALTH = new JSONObject();
            operationHEALTH.put("text", "健康");
            operationHEALTH.put("value", isManage == 0 ? "HEALTH" : "HEALTH".toLowerCase());
            jsonArray.add(operationHEALTH);

            JSONObject operationMONEY = new JSONObject();
            operationMONEY.put("text", "现金");
            operationMONEY.put("value", isManage == 0 ? "MONEY" : "MONEY".toLowerCase());
            jsonArray.add(operationMONEY);

            if (isManage != 0) {
                JSONObject operationPROFIT = new JSONObject();
                operationPROFIT.put("text", "理财收益");
                operationPROFIT.put("value", isManage == 0 ? "FUND" : "FUND".toLowerCase());
                jsonArray.add(operationPROFIT);
            }

            JSONObject operationABILITY = new JSONObject();
            operationABILITY.put("text", "工作能力");
            operationABILITY.put("value", isManage == 0 ? "ABILITY" : "ABILITY".toLowerCase());
            jsonArray.add(operationABILITY);

            JSONObject operationWISDOM = new JSONObject();
            operationWISDOM.put("text", "处世智慧");
            operationWISDOM.put("value", isManage == 0 ? "WISDOM" : "WISDOM".toLowerCase());
            jsonArray.add(operationWISDOM);

            JSONObject operationHAPPY = new JSONObject();
            operationHAPPY.put("text", "快乐");
            operationHAPPY.put("value", isManage == 0 ? "HAPPY" : "HAPPY".toLowerCase());
            jsonArray.add(operationHAPPY);

            JSONObject operationBEAUTY = new JSONObject();
            operationBEAUTY.put("text", "美貌");
            operationBEAUTY.put("value", isManage == 0 ? "BEAUTY" : "BEAUTY".toLowerCase());
            jsonArray.add(operationBEAUTY);


            JSONObject operationPOPULARITY = new JSONObject();
            operationPOPULARITY.put("text", "知名度");
            operationPOPULARITY.put("value", isManage == 0 ? "POPULARITY" : "POPULARITY".toLowerCase());

            jsonArray.add(operationPOPULARITY);
        }

    }

    public static void converInfoNumber(JSONObject infoObj, String key) throws Exception {
        if (infoObj.containsKey(key)) {
            if (key.equals("days") || key.equals("hours")) {
                infoObj.put(key, formatGroupingUsed(Long.valueOf(infoObj.getInt(key))));
            } else {
                infoObj.put(key, formatGroupingUsed(infoObj.getLong(key)));
            }

        }
    }

    public static String formatGroupingUsed(Long number) throws Exception {
        String str = DecimalFormat.getNumberInstance().format(number);
        return str;
    }

    public static JSONArray failAttrNames(List<?> requireList, Object userObj, Integer gender) throws Exception {
        JSONArray failArray = new JSONArray();
        for (Object require : requireList) {
            String requireKey = BeanUtils.getValue(require, "attrKey").toString().toLowerCase();
            if (StringUtils.isNotBlank(requireKey)) {
                Integer userValue = BeanUtils.getValue(userObj, requireKey, Integer.class);
                if (userValue != null) {
                    Integer requireValue = BeanUtils.getValue(require, "value", Integer.class);
                    if (requireValue != null) {
                        if (userValue < requireValue) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("op", "sub");
                            if (gender == 1) {
                                jsonObject.put("attrName", getAttrNameMan(requireKey));
                            } else {
                                jsonObject.put("attrName", getAttrNameLady(requireKey));
                            }
                            jsonObject.put("value",  requireValue);
                            failArray.add(jsonObject);
                        }
                    }
                }
            }
        }

        return failArray;
    }

    public static JSONArray requireCompare(List<ResEventResult> eventResults, Object userObj) throws Exception {
        JSONArray eventResultArray = new JSONArray();
        for (ResEventResult eventResult : eventResults) {
            String compare = eventResult.getCompare();
            if (StringUtils.isNotBlank(compare)) {
                String requireKey = eventResult.getAttrKey().toLowerCase();
                Integer value = eventResult.getValue();
                if (StringUtils.isNotBlank(requireKey) && value != null) {
                    Integer userValue = BeanUtils.getValue(userObj, requireKey, Integer.class);
                    if (userValue != null) {
                        if (compare.equals(">")) {
                            if (userValue <= value) {
                                continue;
                            }
                        } else if (compare.equals("<")) {
                            if (userValue > value) {
                                continue;
                            }
                        }
                    }
                }
            }
            JSONObject eventObj = JsonUtils.formIdEntity(eventResult, 0);
            if (eventObj != null) {
                GameUtils.minish(eventObj);
                eventObj.remove("compare");
                eventObj.remove("attrKey");
                eventObj.remove("value");
                eventObj.remove("displayOrder");
                eventResultArray.add(eventObj);
            }
        }
        return eventResultArray;
    }

    public static boolean requirePass(List<?> requireList, Object userObj) throws Exception {
        boolean pass = true;
        for (Object require : requireList) {
            String requireKey = BeanUtils.getValue(require, "attrKey").toString().toLowerCase();
            if (StringUtils.isNotBlank(requireKey)) {
                Integer userValue = BeanUtils.getValue(userObj, requireKey, Integer.class);
                if (userValue != null) {
                    Integer requireValue = BeanUtils.getValue(require, "value", Integer.class);
                    if (requireValue != null) {
                        if (userValue < requireValue) {
                            pass = false;
                            break;
                        }
                    }
                }
            }
        }
        return pass;
    }

    private static void diffValue(JSONArray resultEffect, Integer value1, Integer value2, Integer gender, String key) {
        JSONObject jsonObject = null;
        if (value1 != null && value2 != null) {
            if (value1 == value2) {
                return;
            } else if (value1 > value2) {
                jsonObject = new JSONObject();
                jsonObject.put("op", "sub");
                if (gender == 1) {
                    jsonObject.put("attrName", getAttrNameMan(key));
                } else {
                    jsonObject.put("attrName", getAttrNameLady(key));
                }
                jsonObject.put("value", "-" + (value1 - value2));
                resultEffect.add(jsonObject);
            } else if (value1 < value2) {
                jsonObject = new JSONObject();
                jsonObject.put("op", "add");
                if (gender == 1) {
                    jsonObject.put("attrName", getAttrNameMan(key));
                } else {
                    jsonObject.put("attrName", getAttrNameLady(key));
                }
                jsonObject.put("value", "+" + (value2 - value1));
                resultEffect.add(jsonObject);
            }
        }
    }

    public static JSONArray diffEffectMan(AppUserMan appUserManBefore, AppUserMan appUserManAfter) throws Exception {
        JSONArray resultEffect = new JSONArray();
        if (appUserManBefore != null && appUserManAfter != null) {
            Integer healthB = appUserManBefore.getHealth();
            Integer moneyB = appUserManBefore.getMoney();
            Integer abilityB = appUserManBefore.getAbility();
            Integer experienceB = appUserManBefore.getExperience();
            Integer happyB = appUserManBefore.getHappy();
            Integer positiveB = appUserManBefore.getPositive();
            Integer connectionsB = appUserManBefore.getConnections();

            Integer healthA = appUserManAfter.getHealth();
            Integer moneyA = appUserManAfter.getMoney();
            Integer abilityA = appUserManAfter.getAbility();
            Integer experienceA = appUserManAfter.getExperience();
            Integer happyA = appUserManAfter.getHappy();
            Integer positiveA = appUserManAfter.getPositive();
            Integer connectionsA = appUserManAfter.getConnections();

            diffValue(resultEffect, healthB, healthA, 1, "health");
            diffValue(resultEffect, moneyB, moneyA, 1, "money");
            diffValue(resultEffect, abilityB, abilityA, 1, "ability");
            diffValue(resultEffect, experienceB, experienceA, 1, "experience");
            diffValue(resultEffect, happyB, happyA, 1, "happy");
            diffValue(resultEffect, positiveB, positiveA, 1, "positive");
            diffValue(resultEffect, connectionsB, connectionsA, 1, "connections");

        }
        return resultEffect;
    }

    public static JSONArray diffEffectLady(AppUserLady appUserLadyBefore, AppUserLady appUserLadyAfter) throws Exception {
        JSONArray resultEffect = new JSONArray();
        if (appUserLadyBefore != null && appUserLadyAfter != null) {
            Integer healthB = appUserLadyBefore.getHealth();
            Integer moneyB = appUserLadyBefore.getMoney();
            Integer abilityB = appUserLadyBefore.getAbility();
            Integer wisdomB = appUserLadyBefore.getWisdom();
            Integer happyB = appUserLadyBefore.getHappy();
            Integer beautyB = appUserLadyBefore.getBeauty();
            Integer popularityB = appUserLadyBefore.getPopularity();

            Integer healthA = appUserLadyAfter.getHealth();
            Integer moneyA = appUserLadyAfter.getMoney();
            Integer abilityA = appUserLadyAfter.getAbility();
            Integer wisdomA = appUserLadyAfter.getWisdom();
            Integer happyA = appUserLadyAfter.getHappy();
            Integer beautyA = appUserLadyAfter.getBeauty();
            Integer popularityA = appUserLadyAfter.getPopularity();


            diffValue(resultEffect, healthB, healthA, 0, "health");
            diffValue(resultEffect, moneyB, moneyA, 0, "money");
            diffValue(resultEffect, abilityB, abilityA, 0, "ability");
            diffValue(resultEffect, wisdomB, wisdomA, 0, "wisdom");
            diffValue(resultEffect, happyB, happyA, 0, "happy");
            diffValue(resultEffect, beautyB, beautyA, 0, "beauty");
            diffValue(resultEffect, popularityB, popularityA, 0, "popularity");

        }
        return resultEffect;
    }

    public static void useEffect(List<?> effectList, Object userObj) throws Exception {
        if (effectList != null && !effectList.isEmpty()) {
            for (Object effect : effectList) {
                String effectKey = BeanUtils.getValue(effect, "attrKey").toString().toLowerCase();
                String operation = BeanUtils.getValue(effect, "operation").toString().toUpperCase();
                Integer value = BeanUtils.getValue(effect, "value", Integer.class);
                if (StringUtils.isNotBlank(effectKey) && value != null) {
                    Integer effectValue = BeanUtils.getValue(userObj, effectKey, Integer.class);
                    if (effectValue != null) {
                        String percent = "N";

                        Object percentObj = null;
                        try {
                            percentObj = BeanUtils.getValue(effect, "percent");
                        } catch (NoSuchPropertyException noSuchPropertyException) {

                        }
                        if (percentObj != null) {
                            percent = percentObj.toString().toUpperCase();
                        }
                        if (percent.equals("N")) {
                            if (operation.equals("SUB")) {
                                effectValue = effectValue - value;
                            } else if (operation.equals("ADD")) {
                                effectValue = effectValue + value;
                            }
                        } else if (percent.equals("Y")) {
                            if (operation.equals("SUB")) {
                                value = 0 - value;
                            }
                            value = 100 + value;
                            Double dyPrice = NumberUtils.divide(NumberUtils.multiply(effectValue, value, 2), 100, 2);
                            effectValue = Long.valueOf(Math.round(dyPrice)).intValue();
                        }
                        BeanUtils.setValue(userObj, effectKey, effectValue);
                    }
                }
            }
        }
    }


    public static void addResultArray(JSONArray resultArray, String resultText, JSONArray effectArray) throws Exception {
        if (resultArray != null && StringUtils.isNotBlank(resultText)) {
            JSONObject resultItem = new JSONObject();
            resultItem.put("text", resultText);
            if (effectArray != null && !effectArray.isEmpty()) {
                resultItem.put("effectArray", effectArray);
            }
            resultArray.add(resultItem);
        }
    }

    public static String callName(Integer gender) throws Exception {
        return gender == 2 ? "小姑娘" : "小伙子";
    }

    public static void useHour(Object appUserObj) throws Exception {
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

    public static String calProfit(Integer currentMoney, Integer baseMoney) throws Exception {
        Double profitPercent = 0.00d;
        String profitPercentText = "";
        if (currentMoney == baseMoney) {
            profitPercent = 0.00d;
            profitPercentText += profitPercent + "%";
        } else if (currentMoney > baseMoney) {
            profitPercent = NumberUtils.divide(NumberUtils.subtract(currentMoney, baseMoney, 2), baseMoney, 2);
            profitPercentText += "+" + profitPercent + "%";
        } else if (currentMoney < baseMoney) {
            profitPercent = NumberUtils.divide(NumberUtils.subtract(baseMoney, currentMoney, 2), baseMoney, 2);
            profitPercentText += "-" + profitPercent + "%";
        }
        return profitPercentText;
    }

    public static Integer getScore(Integer value, String attrKey) {
        Integer score = -1;
        if (value > 0) {
            score = value * 1000;
        } else {
            score = 0;
        }
        return score;
    }

    public static String getCommentText(String comment) {
        String commentText = null;
        comment = comment.toLowerCase();
        switch (comment) {
            case "hun":
                commentText = "混王之王";
                break;
            case "jing":
                commentText = "名动京城";
                break;
            case "feng":
                commentText = "风生水起";
                break;
            case "lu":
                commentText = "碌碌无为";
                break;
            case "qiong":
                commentText = "穷困潦倒";
                break;
        }
        return commentText;
    }

    public static Integer getScoreAttr(Integer value, String attrKey, Integer gender) {
        Integer score = 0;
        if (value > 0) {
            Integer base = 0;
            if (gender == 1) {
                base = getBaseScoreAttrMan(attrKey);
            } else if (gender == 2) {
                base = getBaseScoreAttrLady(attrKey);
            }
            score = value * base;
        }
        return score;
    }

    public static Integer getScoreMoney(Integer money) {
        Integer score = -1;
        if (money >= 0 && money < 10000) {
            score = 0;
        } else if (money >= 10001 && money < 100000) {
            score = 100000;
        } else if (money >= 100001 && money < 500000) {
            score = 20000;
        } else if (money >= 500001 && money < 1000000) {
            score = 300000;
        } else if (money >= 1000001 && money < 2000000) {
            score = 400000;
        } else if (money >= 2000001 && money < 3000000) {
            score = 500000;
        } else if (money >= 3000001 && money < 5000000) {
            score = 600000;
        } else if (money >= 5000001 && money < 8000000) {
            score = 700000;
        } else if (money >= 8000001 && money < 12000000) {
            score = 800000;
        } else if (money >= 12000001 && money < 30000000) {
            score = 1000000;
        } else if (money >= 30000001 && money < 50000000) {
            score = 1200000;
        } else if (money >= 50000001 && money < 80000000) {
            score = 1400000;
        } else if (money >= 80000001 && money < 100000000) {
            score = 2000000;
        } else if (money >= 100000001) {
            score = 2888888;
        }
        return score;
    }
}
