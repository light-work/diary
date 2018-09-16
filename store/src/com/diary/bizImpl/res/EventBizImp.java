package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventResult;
import com.diary.entity.res.ResEventResultEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.EventBiz;
import com.diary.providers.store.res.ResEventResultEffectStore;
import com.diary.providers.store.res.ResEventResultStore;
import com.diary.providers.store.res.ResEventStore;
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

public class EventBizImp extends BaseBiz implements EventBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String list(Integer start, Integer limit,Integer gender, String source, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            if (resEventStore!=null&& resEventResultStore != null && resEventResultEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$eq("gender", gender));
                selectorList.add(SelectorUtils.$eq("source", source));
                Page<ResEvent> resEventPage = resEventStore.getPageList(start,limit,selectorList);
                JSONArray eventArray = new JSONArray();
                if(resEventPage!=null){
                    List<ResEvent> resEventList=resEventPage.getResultList();
                    if (resEventList != null && !resEventList.isEmpty()) {
                        for (ResEvent resEvent : resEventList) {
                            selectorList.clear();
                            selectorList.add(SelectorUtils.$eq("eventId.id", resEvent.getId()));
                            selectorList.add(SelectorUtils.$order("displayOrder",true));
                            JSONArray eventResultArray = new JSONArray();
                            List<ResEventResult> eventResultList = resEventResultStore.getList(selectorList);
                            if (eventResultList != null && !eventResultList.isEmpty()) {
                                for (ResEventResult resEventResult : eventResultList) {
                                    selectorList.clear();
                                    selectorList.add(SelectorUtils.$eq("resultId.id", resEventResult.getId()));
                                    JSONArray eventResultEffectArray = new JSONArray();
                                    List<ResEventResultEffect> eventResultEffectList = resEventResultEffectStore.getList(selectorList);
                                    if (eventResultEffectList != null && !eventResultEffectList.isEmpty()) {
                                        for (ResEventResultEffect resEventResultEffect : eventResultEffectList) {
                                            JSONObject resEventResultEffectObj = JsonUtils.formIdEntity(resEventResultEffect);
                                            if (resEventResultEffectObj != null) {
                                                eventResultEffectArray.add(resEventResultEffectObj);
                                            }
                                        }
                                    }
                                    if(resEventResult.getValue()==null){
                                        resEventResult.setValue(0);
                                    }
                                    JSONObject resEventResultObj = JsonUtils.formIdEntity(resEventResult);
                                    if (resEventResultObj != null) {
                                        resEventResultObj.put("effectList", eventResultEffectArray);
                                        eventResultArray.add(resEventResultObj);
                                    }
                                }
                            }
                            JSONObject resEventObj = JsonUtils.formIdEntity(resEvent);
                            if (resEventObj != null) {
                                resEventObj.put("resultList", eventResultArray);
                                eventArray.add(resEventObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resEventPage);
                    resultObj.put("pageObj", pageObj);
                }
                resultObj.put("list", eventArray);
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
    public String addEvent(Integer gender,String source, String content) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            if (resEventStore != null) {
                ResEvent resEvent=new ResEvent();
                resEvent.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resEvent.setContent(content);
                resEvent.setGender(gender);
                resEvent.setSource(source);
                bind(resEvent, 1l);
                resEventStore.save(resEvent, Persistent.SAVE);
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
    public String enable(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            if (resEventStore != null) {
                ResEvent resEvent = resEventStore.getById(id);
                if (resEvent != null) {
                    bind(resEvent, 1l);
                    resEvent.setUseYn("Y");
                    resEventStore.save(resEvent, Persistent.UPDATE);
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
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            if (resEventStore != null) {
                ResEvent resEvent = resEventStore.getById(id);
                if (resEvent != null) {
                    bind(resEvent, 1l);
                    resEvent.setUseYn("N");
                    resEventStore.save(resEvent, Persistent.UPDATE);
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
    public String upResult(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(id);
                if (resEventResult != null) {
                    Integer currentOrder = resEventResult.getDisplayOrder();
                    if (currentOrder > 1) {
                        currentOrder = currentOrder - 1;
                        ResEventResult resEventResultOrder = resEventResultStore.getByOrder(currentOrder);
                        if (resEventResultOrder != null) {
                            resEventResultOrder.setDisplayOrder(resEventResultOrder.getDisplayOrder() + 1);
                            resEventResult.setDisplayOrder(currentOrder);
                            bind(resEventResult, 1l);
                            bind(resEventResultOrder, 1l);
                            resEventResultStore.saveOrder(resEventResult, Persistent.UPDATE, resEventResultOrder);
                            resultObj.put("result", 0);
                        }
                    }
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
    public String downResult(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(id);
                if (resEventResult != null) {
                    Integer maxOrder = resEventResultStore.getMaxOrder();
                    Integer currentOrder = resEventResult.getDisplayOrder();
                    if (currentOrder < maxOrder) {
                        currentOrder = currentOrder + 1;
                        ResEventResult resEventResultOrder = resEventResultStore.getByOrder(currentOrder);
                        if (resEventResultOrder != null) {
                            resEventResultOrder.setDisplayOrder(resEventResultOrder.getDisplayOrder() - 1);
                            resEventResult.setDisplayOrder(currentOrder);
                            bind(resEventResult, 1l);
                            bind(resEventResultOrder, 1l);
                            resEventResultStore.saveOrder(resEventResult, Persistent.UPDATE, resEventResultOrder);
                            resultObj.put("result", 0);
                        }
                    }
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
    public String addEffect(Long resultId, String operation, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultEffectStore != null && resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(resultId);
                if (resEventResult != null) {
                    ResEventResultEffect resEventResultEffect = new ResEventResultEffect();
                    resEventResultEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resEventResultEffect.setResultId(resEventResult);
                    resEventResultEffect.setOperation(operation);
                    resEventResultEffect.setAttrKey(attrKey);
                    resEventResultEffect.setValue(value);
                    bind(resEventResultEffect, 1l);
                    resEventResultEffect.setUseYn("Y");
                    resEventResultEffectStore.save(resEventResultEffect, Persistent.SAVE);
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
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            if (resEventResultEffectStore != null) {
                ResEventResultEffect resEventResultEffect = resEventResultEffectStore.getById(id);
                if (resEventResultEffect != null) {
                    resEventResultEffect.setOperation(operation);
                    resEventResultEffect.setAttrKey(attrKey);
                    resEventResultEffect.setValue(value);
                    bind(resEventResultEffect, 1l);
                    resEventResultEffectStore.save(resEventResultEffect, Persistent.UPDATE);
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
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            if (resEventResultEffectStore != null) {
                ResEventResultEffect resEventResultEffect = resEventResultEffectStore.getById(id);
                if (resEventResultEffect != null) {
                    resEventResultEffectStore.delete(resEventResultEffect);
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
    public String editEvent(Long id, String content) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            if (resEventStore != null) {
                ResEvent resEvent = resEventStore.getById(id);
                if (resEvent != null) {
                    resEvent.setContent(content);
                    bind(resEvent, 1l);
                    resEvent.setUseYn("Y");
                    resEventStore.save(resEvent, Persistent.UPDATE);
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
    public String addResult(Long eventId, String resultText, String content) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventStore resEventStore = hsfServiceFactory.consumer(ResEventStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventStore != null && resEventResultStore != null) {
                ResEvent resEvent = resEventStore.getById(eventId);
                if (resEvent != null) {
                    Integer currentOrder = resEventResultStore.getMaxOrder();
                    if (currentOrder == null) {
                        currentOrder = 0;
                    }
                    ResEventResult resEventResult = new ResEventResult();
                    resEventResult.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resEventResult.setEventId(resEvent);
                    resEventResult.setResultText(resultText);
                    resEventResult.setDisplayOrder(currentOrder+1);
                    resEventResult.setContent(content);
                    bind(resEventResult, 1l);
                    resEventResult.setUseYn("Y");
                    resEventResultStore.save(resEventResult, Persistent.SAVE);
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
    public String editResult(Long id, String resultText, String content) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(id);
                if (resEventResult != null) {
                    resEventResult.setResultText(resultText);
                    resEventResult.setContent(content);
                    bind(resEventResult, 1l);
                    resEventResultStore.save(resEventResult, Persistent.UPDATE);
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
    public String enableResult(Long resultId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(resultId);
                if (resEventResult != null) {
                    bind(resEventResult, 1l);
                    resEventResult.setUseYn("Y");
                    resEventResultStore.save(resEventResult, Persistent.UPDATE);
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
    public String disableResult(Long resultId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(resultId);
                if (resEventResult != null) {
                    bind(resEventResult, 1l);
                    resEventResult.setUseYn("N");
                    resEventResultStore.save(resEventResult, Persistent.UPDATE);
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
    public String setRequire(Long resultId, String compare, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(resultId);
                if (resEventResult != null) {
                    bind(resEventResult, 1l);
                    resEventResult.setCompare(compare);
                    resEventResult.setAttrKey(attrKey);
                    resEventResult.setValue(value);
                    resEventResultStore.save(resEventResult, Persistent.UPDATE);
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
    public String clearRequire(Long resultId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            if (resEventResultStore != null) {
                ResEventResult resEventResult = resEventResultStore.getById(resultId);
                if (resEventResult != null) {
                    bind(resEventResult, 1l);
                    resEventResult.setCompare(null);
                    resEventResult.setAttrKey(null);
                    resEventResult.setValue(null);
                    resEventResultStore.save(resEventResult, Persistent.UPDATE);
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
}
