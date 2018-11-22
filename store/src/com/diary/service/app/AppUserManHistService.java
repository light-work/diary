package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserManHist;
import com.diary.providers.store.app.AppUserManHistStore;
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
public class AppUserManHistService extends HQuery implements AppUserManHistStore {




    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserManHist getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserManHist.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserManHist> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserManHist.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserManHist getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).get(AppUserManHist.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserManHist appUserManHist, Persistent persistent) throws StoreException {
        $(appUserManHist).save(persistent);
    }


}
