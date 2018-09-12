package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResHouse;
import com.diary.entity.res.ResHouseEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.HouseBiz;
import com.diary.providers.store.res.ResHouseEffectStore;
import com.diary.providers.store.res.ResHouseStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.Page;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class HouseBizImp extends BaseBiz implements HouseBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("buyPrice", true));
                Page<ResHouse> resHousePage = resHouseStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resHousePage != null) {
                    List<ResHouse> jobList = resHousePage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResHouse resHouse : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resHouse);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resHousePage);
                    resultObj.put("pageObj", pageObj);
                }
                resultObj.put("list", jobArray);
                resultObj.put("result", 0);
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String add(String title, Integer buyPrice, Integer sellPrice, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseStore != null) {
                ResHouse resHouse = new ResHouse();
                resHouse.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resHouse.setTitle(title);
                resHouse.setBuyPrice(buyPrice);
                resHouse.setSellPrice(sellPrice);
                resHouse.setRemarks(remarks);
                bind(resHouse, 1l);
                resHouse.setUseYn("Y");
                resHouseStore.save(resHouse, Persistent.SAVE);
                resultObj.put("result", 0);
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String edit(Long id, String title, Integer buyPrice, Integer sellPrice, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseStore != null) {
                ResHouse resHouse = resHouseStore.getById(id);
                if (resHouse != null) {
                    resHouse.setTitle(title);
                    resHouse.setBuyPrice(buyPrice);
                    resHouse.setSellPrice(sellPrice);
                    resHouse.setRemarks(remarks);
                    bind(resHouse, 1l);
                    resHouseStore.save(resHouse, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String enable(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseStore != null) {
                ResHouse resHouse = resHouseStore.getById(id);
                if (resHouse != null) {
                    bind(resHouse, 1l);
                    resHouse.setUseYn("Y");
                    resHouseStore.save(resHouse, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String disable(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseStore != null) {
                ResHouse resHouse = resHouseStore.getById(id);
                if (resHouse != null) {
                    bind(resHouse, 1l);
                    resHouse.setUseYn("N");
                    resHouseStore.save(resHouse, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String addEffect(Long jobId, String operation, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseEffectStore resHouseEffectStore = hsfServiceFactory.consumer(ResHouseEffectStore.class);
            ResHouseStore resHouseStore = hsfServiceFactory.consumer(ResHouseStore.class);
            if (resHouseEffectStore != null && resHouseStore != null) {
                ResHouse resHouse = resHouseStore.getById(jobId);
                if (resHouse != null) {
                    ResHouseEffect resHouseEffect = new ResHouseEffect();
                    resHouseEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resHouseEffect.setHouseId(resHouse);
                    resHouseEffect.setOperation(operation);
                    resHouseEffect.setAttrKey(attrKey);
                    resHouseEffect.setValue(value);
                    bind(resHouseEffect, 1l);
                    resHouseEffect.setUseYn("Y");
                    resHouseEffectStore.save(resHouseEffect, Persistent.SAVE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String editEffect(Long id, String operation, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseEffectStore resHouseEffectStore = hsfServiceFactory.consumer(ResHouseEffectStore.class);
            if (resHouseEffectStore != null) {
                ResHouseEffect resHouseEffect = resHouseEffectStore.getById(id);
                if (resHouseEffect != null) {
                    resHouseEffect.setOperation(operation);
                    resHouseEffect.setAttrKey(attrKey);
                    resHouseEffect.setValue(value);
                    bind(resHouseEffect, 1l);
                    resHouseEffectStore.save(resHouseEffect, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String deleteEffect(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseEffectStore resHouseEffectStore = hsfServiceFactory.consumer(ResHouseEffectStore.class);
            if (resHouseEffectStore != null) {
                ResHouseEffect resHouseEffect = resHouseEffectStore.getById(id);
                if (resHouseEffect != null) {
                    resHouseEffectStore.delete(resHouseEffect);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String effectList(Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResHouseEffectStore resHouseEffectStore = hsfServiceFactory.consumer(ResHouseEffectStore.class);
            if (resHouseEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResHouseEffect> jobEffectList = resHouseEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResHouseEffect resHouseEffect : jobEffectList) {
                        JSONObject resHouseEffectObj = JsonUtils.formIdEntity(resHouseEffect);
                        if (resHouseEffectObj != null) {
                            jobEffectArray.add(resHouseEffectObj);
                        }
                    }
                }
                resultObj.put("list", jobEffectArray);
                resultObj.put("result", 0);
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

}
