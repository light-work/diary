package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.providers.store.app.AppUserLadyStore;
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
public class AppUserLadyService extends HQuery implements AppUserLadyStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLady getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserLady.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserLady> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserLady.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLady getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).get(AppUserLady.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLady appUserLady, Persistent persistent) throws StoreException {
        $(appUserLady).save(persistent);
    }
}