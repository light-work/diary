package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserRankings;
import com.diary.providers.store.app.AppUserRankingsStore;
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
public class AppUserRankingsService extends HQuery implements AppUserRankingsStore {


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserRankings getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserRankings.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserRankings getByUserId(Long userId) throws StoreException {
        return $($eq("userId.id",userId)).get(AppUserRankings.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByLtAsset(Integer asset) throws StoreException {
        return $($lt("asset",asset),$count("id")).value(AppUserRankings.class,Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCount() throws StoreException {
        return $($count("id")).value(AppUserRankings.class,Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getMin() throws StoreException {
        return $($min("score")).value(AppUserRankings.class,Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserRankings> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserRankings.class, start, limit);
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserRankings appUserRankings, Persistent persistent) throws StoreException {
        $(appUserRankings).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserRankings appUserRankings) throws StoreException {
        $(appUserRankings).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserRankings> appUserRankingsList) throws StoreException {
        $(appUserRankingsList).delete();
    }
}
