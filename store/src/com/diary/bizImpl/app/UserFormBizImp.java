package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.app.AppUserFormLast;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.app.UserFormBiz;
import com.diary.providers.store.app.AppUserFormLastStore;
import com.diary.providers.store.app.AppUserFormStore;
import com.diary.providers.store.app.AppUserStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.Date;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserFormBizImp extends BaseBiz implements UserFormBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String submit(Long userId, String formId,String action) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            if(formId.equals("the formId is a mock one")){
                return resultObj.toString();
            }
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserFormStore appUserFormStore = hsfServiceFactory.consumer(AppUserFormStore.class);
            AppUserFormLastStore appUserFormLastStore = hsfServiceFactory.consumer(AppUserFormLastStore.class);
            if (appUserStore != null && appUserFormStore != null&&appUserFormLastStore!=null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserForm appUserForm = new AppUserForm();
                    appUserForm.setId(DrdsIDUtils.getID(DrdsTable.APP));
                    appUserForm.setFormId(formId);
                    appUserForm.setUserId(appUser);
                    Date curDate = DateFormatUtil.getCurrentDate(false);
                    Integer year=DateFormatUtil.getDayInYear(curDate);
                    Integer month=DateFormatUtil.getDayInMonth(curDate) + 1;
                    Integer day=DateFormatUtil.getDayInDay(curDate);

                    appUserForm.setYear(year);
                    appUserForm.setMonth(month);
                    appUserForm.setDay(day);
                    bind(appUserForm, userId);
                    appUserForm.setUseYn("Y");

                   AppUserFormLast appUserFormLast= appUserFormLastStore.getByUserId(userId);
                    Persistent persistentLast=Persistent.UPDATE;
                    if(appUserFormLast==null){
                        persistentLast=Persistent.SAVE;
                        appUserFormLast=new AppUserFormLast();
                        appUserFormLast.setId(DrdsIDUtils.getID(DrdsTable.APP));
                        appUserFormLast.setUserId(appUser);
                    }
                    appUserFormLast.setYear(year);
                    appUserFormLast.setMonth(month);
                    appUserFormLast.setDay(day);
                    appUserFormLast.setLossDay(0);
                    String lastAction=appUserFormLast.getLastAction();
                    JSONArray lastActionArray=null;
                    if(StringUtils.isBlank(lastAction)){
                        lastActionArray=new JSONArray();
                    }else{
                        lastActionArray=JSONArray.fromObject(lastAction);
                    }
                    if(lastActionArray!=null){
                        if(lastActionArray.size()>5){
                            lastActionArray.remove(0);
                        }
                        lastActionArray.add(action);
                    }
                    appUserFormLast.setLastAction(lastActionArray.toString());
                    bind(appUserForm, userId);

                    appUserFormStore.save(appUserForm, Persistent.SAVE,appUserFormLast,persistentLast);
                    resultObj.put("result",  0);
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
