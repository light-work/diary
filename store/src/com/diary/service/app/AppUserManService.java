package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserManStore;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
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
public class AppUserManService extends HQuery implements AppUserManStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserMan getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserMan.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserMan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserMan.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserMan getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).get(AppUserMan.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserMan appUserMan, Persistent persistent) throws StoreException {
        $(appUserMan).save(persistent);
    }
}
