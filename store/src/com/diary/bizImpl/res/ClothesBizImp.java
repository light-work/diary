package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResClothes;
import com.diary.entity.res.ResClothesEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.ClothesBiz;
import com.diary.providers.store.res.ResClothesEffectStore;
import com.diary.providers.store.res.ResClothesStore;
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

public class ClothesBizImp extends BaseBiz implements ClothesBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            if (resClothesStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("buyPrice", true));
                Page<ResClothes> resClothesPage = resClothesStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resClothesPage != null) {
                    List<ResClothes> jobList = resClothesPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResClothes resClothes : jobList) {
                            selectorList.clear();
                            selectorList.add(SelectorUtils.$eq("clothesId.id", resClothes.getId()));
                            List<ResClothesEffect> jobEffectList = resClothesEffectStore.getList(selectorList);
                            JSONArray jobEffectArray = new JSONArray();
                            if (jobEffectList != null && !jobEffectList.isEmpty()) {
                                for (ResClothesEffect resClothesEffect : jobEffectList) {
                                    JSONObject resClothesEffectObj = JsonUtils.formIdEntity(resClothesEffect);
                                    if (resClothesEffectObj != null) {
                                        jobEffectArray.add(resClothesEffectObj);
                                    }
                                }
                            }
                            JSONObject jobObj = JsonUtils.formIdEntity(resClothes);
                            if (jobObj != null) {
                                jobObj.put("effectList",jobEffectArray);
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resClothesPage);
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
    public String add(String title, Integer buyPrice, Integer sellPrice,Integer offsetBuy, Integer offsetSell, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            if (resClothesStore != null) {
                ResClothes resClothes = new ResClothes();
                resClothes.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resClothes.setTitle(title);
                resClothes.setBuyPrice(buyPrice);
                resClothes.setSellPrice(sellPrice);
                resClothes.setOffsetBuy(offsetBuy);
                resClothes.setOffsetSell(offsetSell);
                resClothes.setRemarks(remarks);
                bind(resClothes, 1l);
                resClothes.setUseYn("Y");
                resClothesStore.save(resClothes, Persistent.SAVE);
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
    public String edit(Long id, String title, Integer buyPrice, Integer sellPrice,Integer offsetBuy, Integer offsetSell, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            if (resClothesStore != null) {
                ResClothes resClothes = resClothesStore.getById(id);
                if (resClothes != null) {
                    resClothes.setTitle(title);
                    resClothes.setBuyPrice(buyPrice);
                    resClothes.setSellPrice(sellPrice);
                    resClothes.setOffsetBuy(offsetBuy);
                    resClothes.setOffsetSell(offsetSell);
                    resClothes.setRemarks(remarks);
                    bind(resClothes, 1l);
                    resClothesStore.save(resClothes, Persistent.UPDATE);
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
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            if (resClothesStore != null) {
                ResClothes resClothes = resClothesStore.getById(id);
                if (resClothes != null) {
                    bind(resClothes, 1l);
                    resClothes.setUseYn("Y");
                    resClothesStore.save(resClothes, Persistent.UPDATE);
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
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            if (resClothesStore != null) {
                ResClothes resClothes = resClothesStore.getById(id);
                if (resClothes != null) {
                    bind(resClothes, 1l);
                    resClothes.setUseYn("N");
                    resClothesStore.save(resClothes, Persistent.UPDATE);
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
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            ResClothesStore resClothesStore = hsfServiceFactory.consumer(ResClothesStore.class);
            if (resClothesEffectStore != null && resClothesStore != null) {
                ResClothes resClothes = resClothesStore.getById(jobId);
                if (resClothes != null) {
                    ResClothesEffect resClothesEffect = new ResClothesEffect();
                    resClothesEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resClothesEffect.setClothesId(resClothes);
                    resClothesEffect.setOperation(operation);
                    resClothesEffect.setAttrKey(attrKey);
                    resClothesEffect.setValue(value);
                    bind(resClothesEffect, 1l);
                    resClothesEffect.setUseYn("Y");
                    resClothesEffectStore.save(resClothesEffect, Persistent.SAVE);
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
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            if (resClothesEffectStore != null) {
                ResClothesEffect resClothesEffect = resClothesEffectStore.getById(id);
                if (resClothesEffect != null) {
                    resClothesEffect.setOperation(operation);
                    resClothesEffect.setAttrKey(attrKey);
                    resClothesEffect.setValue(value);
                    bind(resClothesEffect, 1l);
                    resClothesEffectStore.save(resClothesEffect, Persistent.UPDATE);
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
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            if (resClothesEffectStore != null) {
                ResClothesEffect resClothesEffect = resClothesEffectStore.getById(id);
                if (resClothesEffect != null) {
                    resClothesEffectStore.delete(resClothesEffect);
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
            ResClothesEffectStore resClothesEffectStore = hsfServiceFactory.consumer(ResClothesEffectStore.class);
            if (resClothesEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$alias("clothesId", "clothesId"));
                selectorList.add(SelectorUtils.$eq("clothesId.id", jobId));
                List<ResClothesEffect> jobEffectList = resClothesEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResClothesEffect resClothesEffect : jobEffectList) {
                        JSONObject resClothesEffectObj = JsonUtils.formIdEntity(resClothesEffect);
                        if (resClothesEffectObj != null) {
                            jobEffectArray.add(resClothesEffectObj);
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
