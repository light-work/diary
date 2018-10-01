package com.diary.entity.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DecimalFormat;

public class GameUtils {

    public static void man(JSONObject infoObj) throws Exception {
        if (infoObj != null) {
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

    public static void lady(JSONObject infoObj) throws Exception {
        if (infoObj != null) {
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
        jsonObject.put("id",jsonObject.getLong("id")+"");
        jsonObject.remove("created");
        jsonObject.remove("createdBy");
        jsonObject.remove("updated");
        jsonObject.remove("updatedBy");
        jsonObject.remove("useYn");
    }

    public static String getAttrNameMan(String attrKey) {
        String attrName=null;
        attrKey=attrKey.toUpperCase();
       switch (attrKey){
           case "HEALTH":
               attrName="健康";
               break;
           case "MONEY":
               attrName="金钱";
               break;
           case "ABILITY":
               attrName="工作能力";
               break;
           case "EXPERIENCE":
               attrName="社会经验";
               break;
           case "HAPPY":
               attrName="快乐";
               break;
           case "POSITIVE":
               attrName="正义";
               break;
           case "CONNECTIONS":
               attrName="人脉";
               break;
       }
       return attrName;
    }

    public static String getAttrNameLady(String attrKey) {
        String attrName=null;
        attrKey=attrKey.toUpperCase();
        switch (attrKey){
            case "HEALTH":
                attrName="健康";
                break;
            case "MONEY":
                attrName="金钱";
                break;
            case "ABILITY":
                attrName="工作能力";
                break;
            case "WISDOM":
                attrName="处世智慧";
                break;
            case "HAPPY":
                attrName="快乐";
                break;
            case "BEAUTY":
                attrName="美貌";
                break;
            case "POPULARITY":
                attrName="知名度";
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

}
