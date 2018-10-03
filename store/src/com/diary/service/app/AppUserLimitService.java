package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserLimit;
import com.diary.providers.store.app.AppUserLimitStore;
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
public class AppUserLimitService extends HQuery implements AppUserLimitStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserLimit> getListByUserId(Long userId) throws StoreException {
        return $($eq("userId.id",userId)).list(AppUserLimit.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByUserIdDayAction(Long userId, Integer day, String action) throws StoreException {
        return $($eq("userId.id",userId),$eq("day",day),$eq("action",action),
                $count("id")).value(AppUserLimit.class,Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserLimit appUserLimit) throws StoreException {
        $(appUserLimit).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLimit appUserLimit, Persistent persistent) throws StoreException {
        $(appUserLimit).save(persistent);
    }

}
