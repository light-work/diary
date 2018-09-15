package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResCoupleEvent;
import com.diary.providers.store.res.ResCoupleEventStore;
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
public class ResCoupleEventService extends HQuery implements ResCoupleEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCoupleEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCoupleEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCoupleEvent> getListByCoupleId(Long coupleId) throws StoreException {
        return $($alias("coupleId", "coupleId"), $eq("coupleId.id", coupleId)).list(ResCoupleEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCoupleEvent> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResCoupleEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCoupleEvent resCoupleEvent, Persistent persistent) throws StoreException {
        $(resCoupleEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCoupleEvent resCoupleEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        $(resCoupleEvent).save(persistent);
        resEventService.save(resEvent, resEventPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCoupleEvent resCoupleEvent) throws StoreException {
        $(resCoupleEvent).delete();
    }
}
