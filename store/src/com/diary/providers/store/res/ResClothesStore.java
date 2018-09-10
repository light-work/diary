package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResClothes;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResClothesStore {


    ResClothes getById(Long id, Selector... selectors) throws StoreException;

    Page<ResClothes> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    void save(ResClothes resClothes, Persistent persistent) throws StoreException;

    void delete(ResClothes resClothes) throws StoreException;

}
