package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResult;
import com.diary.providers.store.res.ResEventResultStore;
import com.google.inject.Singleton;
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
public class ResEventResultService extends HQuery implements ResEventResultStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventResult getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventResult.class);
    }

    @Override
    public ResEventResult getByOrder(Integer displayOrder) throws StoreException {
        return $($eq("displayOrder", displayOrder)).get(ResEventResult.class);
    }

    @Override
    public Integer getMaxOrder() throws StoreException {
        return $($max("displayOrder")).value(ResEventResult.class, Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventResult> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResEventResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventResult resEventResult, Persistent persistent) throws StoreException {
        $(resEventResult).save(persistent);
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void saveOrder(ResEventResult resEventResult, Persistent persistent, ResEventResult resEventResultOrder) throws StoreException {
        $(resEventResult).save(persistent);
        $(resEventResultOrder).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventResult resEventResult) throws StoreException {
        $(resEventResult).delete();
    }
}
