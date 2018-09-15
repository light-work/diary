package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResLuxuryEvent;
import com.diary.providers.store.res.ResLuxuryEventStore;
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
public class ResLuxuryEventService extends HQuery implements ResLuxuryEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResLuxuryEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResLuxuryEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResLuxuryEvent> getListByLuxuryId(Long luxuryId) throws StoreException {
        return $($alias("luxuryId", "luxuryId"), $eq("luxuryId.id", luxuryId)).list(ResLuxuryEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResLuxuryEvent resLuxuryEvent, Persistent persistent) throws StoreException {
        $(resLuxuryEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResLuxuryEvent resLuxuryEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        $(resLuxuryEvent).save(persistent);
        resEventService.save(resEvent, resEventPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResLuxuryEvent resLuxuryEvent) throws StoreException {
        $(resLuxuryEvent).delete();
    }
}
