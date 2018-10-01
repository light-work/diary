package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserJob;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserJobStore;
import com.google.inject.Inject;
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

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLadyService appUserLadyService;

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
    public AppUserJob getByUserId(Long userId) throws StoreException {
        return $($eq("userId.id", userId)).get(AppUserJob.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserJob appUserJob) throws StoreException {
        $(appUserJob).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserJob appUserJob, Persistent persistent, AppUserMan appUserMan) throws StoreException {
        $(appUserJob).save(persistent);
        this.appUserManService.save(appUserMan,Persistent.UPDATE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserJob appUserJob, Persistent persistent, AppUserLady appUserLady) throws StoreException {
        $(appUserJob).save(persistent);
        this.appUserLadyService.save(appUserLady,Persistent.UPDATE);
    }
}
