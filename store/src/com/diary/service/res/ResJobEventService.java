package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResJobEvent;
import com.diary.providers.store.res.ResJobEventStore;
import com.google.inject.Inject;
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
public class ResJobEventService extends HQuery implements ResJobEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResJobEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResJobEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobEvent> getListByJobId(Long jobId) throws StoreException {
        return $($alias("jobId", "jobId"), $eq("jobId.id", jobId)).list(ResJobEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobEvent> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResJobEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResJobEvent resJobEvent, Persistent persistent) throws StoreException {
        $(resJobEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResJobEvent resJobEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        $(resJobEvent).save(persistent);
        resEventService.save(resEvent, resEventPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResJobEvent resJobEvent) throws StoreException {
        $(resJobEvent).delete();
    }
}
