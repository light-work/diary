package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResClothesEvent;
import com.diary.providers.store.res.ResClothesEventStore;
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
public class ResClothesEventService extends HQuery implements ResClothesEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResClothesEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResClothesEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResClothesEvent> getListByClothesId(Long clothesId) throws StoreException {
        return $($alias("clothesId", "clothesId"), $eq("clothesId.id", clothesId)).list(ResClothesEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResClothesEvent resClothesEvent, Persistent persistent) throws StoreException {
        $(resClothesEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResClothesEvent resClothesEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        $(resClothesEvent).save(persistent);
        resEventService.save(resEvent, persistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResClothesEvent resClothesEvent) throws StoreException {
        $(resClothesEvent).delete();
    }
}
