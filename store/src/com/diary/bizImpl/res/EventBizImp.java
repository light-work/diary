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
import net.sf.json.JSONObject;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class EventBizImp extends BaseBiz implements EventBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

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
