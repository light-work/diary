package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResCar;
import com.diary.entity.res.ResCarEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.CarBiz;
import com.diary.providers.store.res.ResCarEffectStore;
import com.diary.providers.store.res.ResCarStore;
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

public class CarBizImp extends BaseBiz implements CarBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            if (resCarStore != null&&resCarEffectStore!=null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("buyPrice", true));
                Page<ResCar> resCarPage = resCarStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resCarPage != null) {
                    List<ResCar> jobList = resCarPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResCar resCar : jobList) {
                            selectorList.clear();
                            selectorList.add(SelectorUtils.$eq("carId.id", resCar.getId()));
                            List<ResCarEffect> jobEffectList = resCarEffectStore.getList(selectorList);
                            JSONArray jobEffectArray = new JSONArray();
                            if (jobEffectList != null && !jobEffectList.isEmpty()) {
                                for (ResCarEffect resCarEffect : jobEffectList) {
                                    JSONObject resCarEffectObj = JsonUtils.formIdEntity(resCarEffect);
                                    if (resCarEffectObj != null) {
                                        jobEffectArray.add(resCarEffectObj);
                                    }
                                }
                            }
                            JSONObject jobObj = JsonUtils.formIdEntity(resCar);
                            if (jobObj != null) {
                                jobObj.put("effectList",jobEffectArray);
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resCarPage);
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
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            if (resCarStore != null) {
                ResCar resCar = new ResCar();
                resCar.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resCar.setTitle(title);
                resCar.setBuyPrice(buyPrice);
                resCar.setSellPrice(sellPrice);
                resCar.setRemarks(remarks);
                resCar.setOffsetBuy(offsetBuy);
                resCar.setOffsetSell(offsetSell);
                bind(resCar, 1l);
                resCar.setUseYn("Y");
                resCarStore.save(resCar, Persistent.SAVE);
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
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            if (resCarStore != null) {
                ResCar resCar = resCarStore.getById(id);
                if (resCar != null) {
                    resCar.setTitle(title);
                    resCar.setBuyPrice(buyPrice);
                    resCar.setSellPrice(sellPrice);
                    resCar.setOffsetBuy(offsetBuy);
                    resCar.setOffsetSell(offsetSell);
                    resCar.setRemarks(remarks);
                    bind(resCar, 1l);
                    resCarStore.save(resCar, Persistent.UPDATE);
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
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            if (resCarStore != null) {
                ResCar resCar = resCarStore.getById(id);
                if (resCar != null) {
                    bind(resCar, 1l);
                    resCar.setUseYn("Y");
                    resCarStore.save(resCar, Persistent.UPDATE);
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
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            if (resCarStore != null) {
                ResCar resCar = resCarStore.getById(id);
                if (resCar != null) {
                    bind(resCar, 1l);
                    resCar.setUseYn("N");
                    resCarStore.save(resCar, Persistent.UPDATE);
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
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            ResCarStore resCarStore = hsfServiceFactory.consumer(ResCarStore.class);
            if (resCarEffectStore != null && resCarStore != null) {
                ResCar resCar = resCarStore.getById(jobId);
                if (resCar != null) {
                    ResCarEffect resCarEffect = new ResCarEffect();
                    resCarEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resCarEffect.setCarId(resCar);
                    resCarEffect.setOperation(operation);
                    resCarEffect.setAttrKey(attrKey);
                    resCarEffect.setValue(value);
                    bind(resCarEffect, 1l);
                    resCarEffect.setUseYn("Y");
                    resCarEffectStore.save(resCarEffect, Persistent.SAVE);
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
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            if (resCarEffectStore != null) {
                ResCarEffect resCarEffect = resCarEffectStore.getById(id);
                if (resCarEffect != null) {
                    resCarEffect.setOperation(operation);
                    resCarEffect.setAttrKey(attrKey);
                    resCarEffect.setValue(value);
                    bind(resCarEffect, 1l);
                    resCarEffectStore.save(resCarEffect, Persistent.UPDATE);
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
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            if (resCarEffectStore != null) {
                ResCarEffect resCarEffect = resCarEffectStore.getById(id);
                if (resCarEffect != null) {
                    resCarEffectStore.delete(resCarEffect);
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
            ResCarEffectStore resCarEffectStore = hsfServiceFactory.consumer(ResCarEffectStore.class);
            if (resCarEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$alias("carId", "carId"));
                selectorList.add(SelectorUtils.$eq("carId.id", jobId));
                List<ResCarEffect> jobEffectList = resCarEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResCarEffect resCarEffect : jobEffectList) {
                        JSONObject resCarEffectObj = JsonUtils.formIdEntity(resCarEffect);
                        if (resCarEffectObj != null) {
                            jobEffectArray.add(resCarEffectObj);
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
