package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserJob;
import com.diary.providers.store.app.AppUserJobStore;
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
public class AppUserJobService extends HQuery implements AppUserJobStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserJob getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserJob.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserJob> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserJob.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserJob> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).list(AppUserJob.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserJob appUserJob) throws StoreException {
        $(appUserJob).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserJob appUserJob, Persistent persistent) throws StoreException {
        $(appUserJob).save(persistent);
    }
}
