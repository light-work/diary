package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResCarEvent;
import com.diary.providers.store.res.ResCarEventStore;
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
public class ResCarEventService extends HQuery implements ResCarEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCarEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCarEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCarEvent> getListByCarId(Long carId) throws StoreException {
        return $($alias("carId", "carId"), $eq("carId.id", carId)).list(ResCarEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCarEvent resCarEvent, Persistent persistent) throws StoreException {
        $(resCarEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCarEvent resCarEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        $(resCarEvent).save(persistent);
        resEventService.save(resEvent, persistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCarEvent resCarEvent) throws StoreException {
        $(resCarEvent).delete();
    }
}
