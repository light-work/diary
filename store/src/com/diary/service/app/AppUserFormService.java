package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.app.AppUserFormLast;
import com.diary.providers.store.app.AppUserFormStore;
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
public class AppUserFormService extends HQuery implements AppUserFormStore {

    @Inject
    private AppUserFormLastService appUserFormLastService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserForm getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserForm.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserForm> getByUserId(Long userId,Integer year,Integer month,Integer day) throws StoreException {
        return $($eq("userId.id", userId),$eq("year", year),$eq("month", month),$eq("day", day)).list(AppUserForm.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserForm> appUserFormList) throws StoreException {
        $(appUserFormList).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserForm appUserForm, Persistent persistent) throws StoreException {
        $(appUserForm).save(persistent);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserForm appUserForm, Persistent persistent, AppUserFormLast appUserFormLast, Persistent persistentLast) throws StoreException {
        $(appUserForm).save(persistent);
        if(appUserFormLast!=null){
            this.appUserFormLastService.save(appUserFormLast,persistentLast);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<AppUserForm> appUserForms, Persistent persistent) throws StoreException {
        $(appUserForms).save(persistent);
    }


}
