package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.utils.GameUtils;
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
            GameUtils.attrList(jsonArray,gender,0);
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
            operationPlus.put("value", "add".toUpperCase());
            jsonArray.add(operationPlus);

            JSONObject operationSub = new JSONObject();
            operationSub.put("text", "减少");
            operationSub.put("value", "sub".toUpperCase());
            jsonArray.add(operationSub);

            JSONObject operationPlusPerc = new JSONObject();
            operationPlus.put("text", "百分比增加");
            operationPlus.put("value", "addperc".toUpperCase());
            jsonArray.add(operationPlusPerc);

            JSONObject operationPerc = new JSONObject();
            operationSub.put("text", "百分比减少");
            operationSub.put("value", "subperc".toUpperCase());
            jsonArray.add(operationPerc);

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
    public String getCompare() throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject compareGt = new JSONObject();
            compareGt.put("text", "大于");
            compareGt.put("value", ">");
            jsonArray.add(compareGt);

            JSONObject compareLt = new JSONObject();
            compareLt.put("text", "小于");
            compareLt.put("value", "<");
            jsonArray.add(compareLt);

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
    public String getEventSource(Integer gender) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            JSONArray jsonArray = new JSONArray();
            if(gender==1){
                JSONObject carObj = new JSONObject();
                carObj.put("text", "汽车类事件");
                carObj.put("value", "CAR");
                jsonArray.add(carObj);

                JSONObject houseObj = new JSONObject();
                houseObj.put("text", "房屋类事件");
                houseObj.put("value", "HOUSE");
                jsonArray.add(houseObj);
            }else if(gender==0){
                JSONObject clothesObj = new JSONObject();
                clothesObj.put("text", "服装类事件");
                clothesObj.put("value", "CLOTHES");
                jsonArray.add(clothesObj);

                JSONObject luxuryObj = new JSONObject();
                luxuryObj.put("text", "奢侈品类事件");
                luxuryObj.put("value", "LUXURY");
                jsonArray.add(luxuryObj);
            }
            JSONObject planObj = new JSONObject();
            planObj.put("text", "日常消遣类事件");
            planObj.put("value", "PLAN");
            jsonArray.add(planObj);

            JSONObject jobObj = new JSONObject();
            jobObj.put("text", "工作类事件");
            jobObj.put("value", "JOB");
            jsonArray.add(jobObj);

            JSONObject randomObj = new JSONObject();
            randomObj.put("text", "随机类事件");
            randomObj.put("value", "RANDOM");
            jsonArray.add(randomObj);

//            JSONObject coupleObj = new JSONObject();
//            coupleObj.put("text", "情侣类事件");
//            coupleObj.put("value", "COUPLE");
//            jsonArray.add(coupleObj);

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
