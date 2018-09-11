package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.providers.biz.res.CommonBiz;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class CommonBizImp extends BaseBiz implements CommonBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String getAttr(Integer gender) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            JSONArray jsonArray = new JSONArray();

            if(gender.intValue()==1){
                JSONObject operationHEALTH = new JSONObject();
                operationHEALTH.put("text", "健康");
                operationHEALTH.put("value", "HEALTH");
                jsonArray.add(operationHEALTH);

                JSONObject operationMONEY = new JSONObject();
                operationMONEY.put("text", "金钱");
                operationMONEY.put("value", "MONEY");
                jsonArray.add(operationMONEY);

                JSONObject operationABILITY = new JSONObject();
                operationABILITY.put("text", "工作能力");
                operationABILITY.put("value", "ABILITY");
                jsonArray.add(operationABILITY);

                JSONObject operationEXPERIENCE = new JSONObject();
                operationEXPERIENCE.put("text", "社会经验");
                operationEXPERIENCE.put("value", "EXPERIENCE");
                jsonArray.add(operationEXPERIENCE);

                JSONObject operationHAPPY = new JSONObject();
                operationHAPPY.put("text", "快乐");
                operationHAPPY.put("value", "HAPPY");
                jsonArray.add(operationHAPPY);

                JSONObject operationPOSITIVE = new JSONObject();
                operationPOSITIVE.put("text", "正义");
                operationPOSITIVE.put("value", "POSITIVE");
                jsonArray.add(operationPOSITIVE);

                JSONObject operationCONNECTIONS = new JSONObject();
                operationCONNECTIONS.put("text", "人脉");
                operationCONNECTIONS.put("value", "CONNECTIONS");

                jsonArray.add(operationCONNECTIONS);
            }else if(gender.intValue()==0){
                JSONObject operationHEALTH = new JSONObject();
                operationHEALTH.put("text", "健康");
                operationHEALTH.put("value", "HEALTH");
                jsonArray.add(operationHEALTH);

                JSONObject operationMONEY = new JSONObject();
                operationMONEY.put("text", "金钱");
                operationMONEY.put("value", "MONEY");
                jsonArray.add(operationMONEY);

                JSONObject operationABILITY = new JSONObject();
                operationABILITY.put("text", "工作能力");
                operationABILITY.put("value", "ABILITY");
                jsonArray.add(operationABILITY);

                JSONObject operationWISDOM = new JSONObject();
                operationWISDOM.put("text", "智慧");
                operationWISDOM.put("value", "WISDOM");
                jsonArray.add(operationWISDOM);

                JSONObject operationBEAUTY = new JSONObject();
                operationBEAUTY.put("text", "美貌");
                operationBEAUTY.put("value", "BEAUTY");
                jsonArray.add(operationBEAUTY);

                JSONObject operationHAPPY = new JSONObject();
                operationHAPPY.put("text", "快乐");
                operationHAPPY.put("value", "HAPPY");
                jsonArray.add(operationHAPPY);



                JSONObject operationPOPULARITY = new JSONObject();
                operationPOPULARITY.put("text", "知名度");
                operationPOPULARITY.put("value", "POPULARITY");

                jsonArray.add(operationPOPULARITY);
            }


            resultObj.put("result", 0);
            resultObj.put("list", jsonArray);
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
    public String getOperation() throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject operationPlus = new JSONObject();
            operationPlus.put("text", "增加");
            operationPlus.put("value", "plus".toUpperCase());
            jsonArray.add(operationPlus);

            JSONObject operationSub = new JSONObject();
            operationSub.put("text", "减少");
            operationSub.put("value", "sub".toUpperCase());
            jsonArray.add(operationSub);

            resultObj.put("result", 0);
            resultObj.put("list", jsonArray);
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
