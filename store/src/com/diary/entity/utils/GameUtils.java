package com.diary.entity.utils;

import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameUtils {

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
    public static void main(String[] args) throws Exception{
        List<Double> doubleList=new ArrayList<>();
        doubleList.add(0.01);
        doubleList.add(0.79);
        for(int i=1;i<=200;i++){
            System.out.println(lottery(doubleList));
        }

    }

    public static String dayText(Integer day) throws Exception {
        String dayText = null;
        int totalDays = 7;
        int diffDays = totalDays - day;
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
        }
        return dayText;
    }

    public static void man(JSONObject infoObj, Integer jobLimit,
                           Integer financialLimit,
                           Integer luckLimit,
                           Integer houseLimit,
                           Integer carLimit, Integer coupleLimit) throws Exception {
        if (infoObj != null) {
            infoObj.put("jobLimit", jobLimit);
            infoObj.put("financialLimit", financialLimit);
            infoObj.put("luckLimit", luckLimit);
            infoObj.put("houseLimit", houseLimit);
            infoObj.put("carLimit", carLimit);
            infoObj.put("coupleLimit", coupleLimit);


            minish(infoObj);
            converInfoNumber(infoObj, "health");
            converInfoNumber(infoObj, "money");
            converInfoNumber(infoObj, "profit");
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
                            Integer financialLimit,
                            Integer luckLimit,
                            Integer clothesLimit,
                            Integer luxuryLimit, Integer coupleLimit) throws Exception {
        if (infoObj != null) {
            infoObj.put("jobLimit", jobLimit);
            infoObj.put("financialLimit", financialLimit);
            infoObj.put("luckLimit", luckLimit);
            infoObj.put("clothesLimit", clothesLimit);
            infoObj.put("luxuryLimit", luxuryLimit);
            infoObj.put("coupleLimit", coupleLimit);
            minish(infoObj);
            converInfoNumber(infoObj, "health");
            converInfoNumber(infoObj, "money");
            converInfoNumber(infoObj, "profit");
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

    public static String getAttrNameMan(String attrKey) {
        String attrName = null;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                attrName = "健康";
                break;
            case "MONEY":
                attrName = "金钱";
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
        }
        return attrName;
    }

    public static String getAttrNameLady(String attrKey) {
        String attrName = null;
        attrKey = attrKey.toUpperCase();
        switch (attrKey) {
            case "HEALTH":
                attrName = "健康";
                break;
            case "MONEY":
                attrName = "金钱";
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


        if (gender.intValue() == 1) {
            JSONObject operationHEALTH = new JSONObject();
            operationHEALTH.put("text", "健康");
            operationHEALTH.put("value", isManage == 0 ? "HEALTH" : "HEALTH".toLowerCase());
            jsonArray.add(operationHEALTH);

            JSONObject operationMONEY = new JSONObject();
            operationMONEY.put("text", "金钱");
            operationMONEY.put("value", isManage == 0 ? "MONEY" : "MONEY".toLowerCase());
            jsonArray.add(operationMONEY);

            if (isManage != 0) {
                JSONObject operationPROFIT = new JSONObject();
                operationPROFIT.put("text", "理财收益");
                operationPROFIT.put("value", isManage == 0 ? "PROFIT" : "PROFIT".toLowerCase());
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
        } else if (gender.intValue() == 0) {
            JSONObject operationHEALTH = new JSONObject();
            operationHEALTH.put("text", "健康");
            operationHEALTH.put("value", isManage == 0 ? "HEALTH" : "HEALTH".toLowerCase());
            jsonArray.add(operationHEALTH);

            JSONObject operationMONEY = new JSONObject();
            operationMONEY.put("text", "金钱");
            operationMONEY.put("value", isManage == 0 ? "MONEY" : "MONEY".toLowerCase());
            jsonArray.add(operationMONEY);

            if (isManage != 0) {
                JSONObject operationPROFIT = new JSONObject();
                operationPROFIT.put("text", "理财收益");
                operationPROFIT.put("value", isManage == 0 ? "PROFIT" : "PROFIT".toLowerCase());
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

    public static boolean requirePass(List<?> requireList, Object userObj) throws Exception {
        boolean pass = true;
        for (Object require : requireList) {
            String requireKey = BeanUtils.getValue(require, "attrKey").toString().toLowerCase();
            if (StringUtils.isNotBlank(requireKey)) {
                Integer userValue = BeanUtils.getValue(userObj, requireKey, Integer.class);
                if (userValue != null) {
                    Integer requireValue = BeanUtils.getValue(require, "value", Integer.class);
                    if (requireValue != null) {
                        if (userValue.intValue() < requireValue.intValue()) {
                            pass = false;
                            break;
                        }
                    }
                }
            }
        }
        return pass;
    }

    private static String diffValue(Integer value1,Integer value2,Integer gender,String key){
        if(value1!=null&&value2!=null){
            if(value1==value2){
                return "";
            }else if(value1>value2){
                if(gender==1){
                    return getAttrNameMan(key)+"-"+(value1-value2)+",";
                }else{
                    return getAttrNameLady(key)+"-"+(value1-value2)+",";
                }
            }else if(value1<value2){
                if(gender==1){
                    return getAttrNameMan(key)+"+"+(value2-value1)+",";
                }else{
                    return getAttrNameLady(key)+"+"+(value2-value1)+",";
                }
            }
        }
        return "";
    }

    public static String diffEffectMan(AppUserMan appUserManBefore, AppUserMan appUserManAfter) throws Exception {
        String resultEffect = "";
        if (appUserManBefore != null && appUserManAfter != null) {
            Integer healthB=appUserManBefore.getHealth();
            Integer moneyB=appUserManBefore.getMoney();
            Integer abilityB=appUserManBefore.getAbility();
            Integer experienceB=appUserManBefore.getExperience();
            Integer happyB=appUserManBefore.getHappy();
            Integer positiveB=appUserManBefore.getPositive();
            Integer connectionsB=appUserManBefore.getConnections();

            Integer healthA=appUserManAfter.getHealth();
            Integer moneyA=appUserManAfter.getMoney();
            Integer abilityA=appUserManAfter.getAbility();
            Integer experienceA=appUserManAfter.getExperience();
            Integer happyA=appUserManAfter.getHappy();
            Integer positiveA=appUserManAfter.getPositive();
            Integer connectionsA=appUserManAfter.getConnections();

            resultEffect+=diffValue(healthB,healthA,1,"health");
            resultEffect+=diffValue(moneyB,moneyA,1,"money");
            resultEffect+=diffValue(abilityB,abilityA,1,"ability");
            resultEffect+=diffValue(experienceB,experienceA,1,"experience");
            resultEffect+=diffValue(happyB,happyA,1,"happy");
            resultEffect+=diffValue(positiveB,positiveA,1,"positive");
            resultEffect+=diffValue(connectionsB,connectionsA,1,"connections");

        }
        return resultEffect;
    }

    public static String diffEffectLady(AppUserLady appUserLadyBefore, AppUserLady appUserLadyAfter) throws Exception {
        String resultEffect = "";
        if (appUserLadyBefore != null && appUserLadyAfter != null) {
            Integer healthB=appUserLadyBefore.getHealth();
            Integer moneyB=appUserLadyBefore.getMoney();
            Integer abilityB=appUserLadyBefore.getAbility();
            Integer wisdomB=appUserLadyBefore.getWisdom();
            Integer happyB=appUserLadyBefore.getHappy();
            Integer beautyB=appUserLadyBefore.getBeauty();
            Integer popularityB=appUserLadyBefore.getPopularity();

            Integer healthA=appUserLadyAfter.getHealth();
            Integer moneyA=appUserLadyAfter.getMoney();
            Integer abilityA=appUserLadyAfter.getAbility();
            Integer wisdomA=appUserLadyAfter.getWisdom();
            Integer happyA=appUserLadyAfter.getHappy();
            Integer beautyA=appUserLadyBefore.getBeauty();
            Integer popularityA=appUserLadyBefore.getPopularity();


            resultEffect+=diffValue(healthB,healthA,0,"health");
            resultEffect+=diffValue(moneyB,moneyA,0,"money");
            resultEffect+=diffValue(abilityB,abilityA,0,"ability");
            resultEffect+=diffValue(wisdomB,wisdomA,0,"wisdom");
            resultEffect+=diffValue(happyB,happyA,0,"happy");
            resultEffect+=diffValue(beautyB,beautyA,0,"beauty");
            resultEffect+=diffValue(popularityB,popularityA,0,"popularity");

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
                        if (operation.equals("SUB")) {
                            effectValue = effectValue - value;
                        } else if (operation.equals("ADD")) {
                            effectValue = effectValue + value;
                        }
                        BeanUtils.setValue(userObj, effectKey, effectValue);
                    }
                }
            }
        }
    }
}
