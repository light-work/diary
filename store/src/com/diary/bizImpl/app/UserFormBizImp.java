package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.app.UserFormBiz;
import com.diary.providers.store.app.AppUserFormStore;
import com.diary.providers.store.app.AppUserStore;
import com.google.inject.Inject;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.DateFormatUtil;
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
    public String submit(Long userId, String formId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserFormStore appUserFormStore = hsfServiceFactory.consumer(AppUserFormStore.class);
            if (appUserStore != null && appUserFormStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    AppUserForm appUserForm = new AppUserForm();
                    appUserForm.setId(DrdsIDUtils.getID(DrdsTable.APP));
                    appUserForm.setFormId(formId);
                    appUserForm.setUserId(appUser);
                    Date curDate = DateFormatUtil.getCurrentDate(false);
                    appUserForm.setYear(DateFormatUtil.getDayInYear(curDate));
                    appUserForm.setMonth(DateFormatUtil.getDayInMonth(curDate) + 1);
                    appUserForm.setDay(DateFormatUtil.getDayInDay(curDate));
                    bind(appUserForm, userId);
                    appUserForm.setUseYn("Y");
                    appUserFormStore.save(appUserForm, Persistent.SAVE);
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
