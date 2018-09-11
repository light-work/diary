package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResPlanEvent;
import com.diary.providers.store.res.ResPlanEventStore;
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
public class ResPlanEventService extends HQuery implements ResPlanEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResPlanEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResPlanEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResPlanEvent> getListByPlanId(Long planId) throws StoreException {
        return $($alias("planId", "planId"), $eq("planId.id", planId)).list(ResPlanEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResPlanEvent> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResPlanEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResPlanEvent resPlanEvent, Persistent persistent) throws StoreException {
        $(resPlanEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResPlanEvent resPlanEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        $(resPlanEvent).save(persistent);
        resEventService.save(resEvent, persistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResPlanEvent resPlanEvent) throws StoreException {
        $(resPlanEvent).delete();
    }
}
