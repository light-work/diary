package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLadyHist;
import com.diary.providers.store.app.AppUserLadyHistStore;
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
public class AppUserLadyHistService extends HQuery implements AppUserLadyHistStore {




    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLadyHist getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserLadyHist.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserLadyHist> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserLadyHist.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLadyHist getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).get(AppUserLadyHist.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLadyHist appUserLadyHist, Persistent persistent) throws StoreException {
        $(appUserLadyHist).save(persistent);
    }


}
