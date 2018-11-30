package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFormLast;
import com.diary.providers.store.app.AppUserFormLastStore;
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
public class AppUserFormLastService extends HQuery implements AppUserFormLastStore {


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFormLast getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserFormLast.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFormLast getByUserId(Long userId) throws StoreException {
        return $($alias("userId","userId"),$eq("userId.id",userId)).get(AppUserFormLast.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserFormLast> appUserFormLastList) throws StoreException {
        $(appUserFormLastList).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserFormLast appUserFormLast, Persistent persistent) throws StoreException {
        $(appUserFormLast).save(persistent);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<AppUserFormLast> appUserFormLasts, Persistent persistent) throws StoreException {
        $(appUserFormLasts).save(persistent);
    }


}
