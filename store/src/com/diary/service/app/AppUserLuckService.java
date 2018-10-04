package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLuck;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserLuckStore;
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
public class AppUserLuckService extends HQuery implements AppUserLuckStore {

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLadyService appUserLadyService;

    @Inject
    private AppUserLimitService appUserLimitService;

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLuck getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserLuck.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserLuck> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserLuck.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLuck getByUserId(Long userId) throws StoreException {
        return $($alias("luckId","luckId"),$eq("userId.id", userId)).get(AppUserLuck.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserLuck appUserLuck) throws StoreException {
        $(appUserLuck).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLuck appUserLuck, Persistent persistent, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        $(appUserLuck).save(persistent);
        this.appUserManService.save(appUserMan,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLuck appUserLuck, Persistent persistent, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException {
        $(appUserLuck).save(persistent);
        this.appUserLadyService.save(appUserLady,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }
}
