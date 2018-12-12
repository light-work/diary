package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.app.AppUserPush;
import com.diary.providers.store.app.AppUserPushStore;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserPushService extends HQuery implements AppUserPushStore {

    @Inject
    private AppUserFormService appUserFormService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserPush getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserPush.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserPush> getByUserId(Long userId,Integer year,Integer month,Integer day) throws StoreException {
        return $($eq("userId.id", userId),$eq("year", year),$eq("month", month),$eq("day", day)).list(AppUserPush.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserPush> appUserPushList) throws StoreException {
        $(appUserPushList).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserPush appUserPush, Persistent persistent) throws StoreException {
        $(appUserPush).save(persistent);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserPush appUserPush, Persistent persistent, AppUserForm appUserForm) throws StoreException {
        $(appUserPush).save(persistent);
        if(appUserForm!=null){
            this.appUserFormService.delete(appUserForm);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<AppUserPush> appUserPushs, Persistent persistent) throws StoreException {
        $(appUserPushs).save(persistent);
    }


}
