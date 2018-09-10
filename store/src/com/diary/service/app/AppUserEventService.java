package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserEvent;
import com.diary.providers.store.app.AppUserEventStore;
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
public class AppUserEventService extends HQuery implements AppUserEventStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserEvent> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserEvent.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserEvent> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).list(AppUserEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserEvent appUserEvent) throws StoreException {
        $(appUserEvent).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserEvent appUserEvent, Persistent persistent) throws StoreException {
        $(appUserEvent).save(persistent);
    }
}
