package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.entity.res.ResJob;
import com.diary.entity.res.ResJobRequire;
import com.diary.entity.res.ResLuck;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserJobBiz;
import com.diary.providers.biz.app.UserLuckBiz;
import com.diary.providers.store.app.*;
import com.diary.providers.store.res.ResJobRequireStore;
import com.diary.providers.store.res.ResJobStore;
import com.diary.providers.store.res.ResLuckStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.NumberUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserLuckBizImp extends BaseBiz implements UserLuckBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;


    @Override
    public String applyLuck(Long userId, Long luckId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            AppUserLuckStore appUserLuckStore = hsfServiceFactory.consumer(AppUserLuckStore.class);
            if (appUserManStore != null && appUserLadyStore != null && appUserStore != null
                    && resLuckStore != null && appUserLimitStore != null && appUserLuckStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                ResLuck resLuck = resLuckStore.getById(luckId);
                if (appUser != null && resLuck != null) {
                    boolean pass = false;
                    Integer luckLimit = 1;
                    Integer day = -1;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    AppUserLimit appUserLimit = null;

                    JSONArray resultArray = new JSONArray();
                    JSONArray effectArray = null;

                    if (appUser.getUserGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            day = appUserMan.getDays();
                            luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "LUCK");
                            if (appUserMan.getMoney() >= resLuck.getInvestPrice()) {
                                pass = true;
                            }

                        }
                    } else if (appUser.getUserGender() == 0) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            day = appUserLady.getDays();
                            luckLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "LUCK");
                            if (appUserLady.getMoney() >= resLuck.getInvestPrice()) {
                                pass = true;
                            }
                        }
                    }
                    if (luckLimit == 0) {
                        appUserLimit = new AppUserLimit();
                        appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                        appUserLimit.setUserId(appUser);
                        appUserLimit.setAction("LUCK");
                        appUserLimit.setDay(day);
                        bind(appUserLimit, userId);
                        appUserLimit.setUseYn("Y");
                    }
                    if (pass && luckLimit == 0) {
                        AppUserLuck appUserLuck = new AppUserLuck();
                        appUserLuck.setId(DrdsIDUtils.getID(DrdsTable.APP));
                        appUserLuck.setUserId(appUser);
                        appUserLuck.setLuckId(resLuck);
                        appUserLuck.setDay(day);
                        appUserLuck.setUseYn("Y");
                        bind(appUserLuck, userId);

                        List<Double> doubleList = new ArrayList<>();
                        doubleList.add(resLuck.getProbability());
                        doubleList.add(NumberUtils.subtract(1, resLuck.getProbability(), 2));
                        int status = GameUtils.lottery(doubleList);
                        appUserLuck.setStatus(status);
                        if (appUser.getUserGender() == 1) {
                            if (appUserMan != null) {
                                AppUserMan oldMan = (AppUserMan) appUserMan.clone();
                                appUserMan.setMoney(appUserMan.getMoney() - resLuck.getInvestPrice());
                                if (status == 0) {
                                    appUserMan.setMoney(appUserMan.getMoney() + resLuck.getGainPrice());
                                }
                                effectArray = GameUtils.diffEffectMan(oldMan, appUserMan);
                                GameUtils.useHour(appUserMan);
                                appUserLuckStore.save(appUserLuck, Persistent.SAVE, appUserMan, appUserLimit);
                            }
                        } else if (appUser.getUserGender() == 0) {
                            if (appUserLady != null) {
                                AppUserLady oldLady = (AppUserLady) appUserLady.clone();
                                appUserLady.setMoney(appUserLady.getMoney() - resLuck.getInvestPrice());
                                if (status == 0) {
                                    appUserLady.setMoney(appUserLady.getMoney() + resLuck.getGainPrice());
                                }
                                effectArray = GameUtils.diffEffectLady(oldLady, appUserLady);
                                GameUtils. useHour(appUserLady);
                                appUserLuckStore.save(appUserLuck, Persistent.SAVE, appUserLady, appUserLimit);
                            }
                        }
                        if (status == 0) {
                            GameUtils.addResultArray(resultArray,
                                    "哎哟喂！" + GameUtils.callName(appUser.getUserGender()) + "，运气不错，这可是平日行善积德的回报。", null);
                            GameUtils.addResultArray(resultArray,
                                    "最终", effectArray);
                        } else {
                            if (appUser.getUserGender() == 1) {
                                GameUtils.addResultArray(resultArray,
                                        "哎！" + GameUtils.callName(appUser.getUserGender()) + "，运气太差，平日要扶老奶奶过马路，听女朋友的话，孝敬父母。", null);
                            } else if (appUser.getUserGender() == 0) {
                                GameUtils.addResultArray(resultArray,
                                        "哎！" + GameUtils.callName(appUser.getUserGender()) + "，运气太差，平日要给老爷爷让座，爱护小动物，孝敬父母。", null);
                            }
                            GameUtils.addResultArray(resultArray,
                                    "最终", effectArray);
                        }
                        resultObj.put("result", status);
                        resultObj.put("resultArray", resultArray);
                    } else {
                        if (luckLimit == 0) {
                            if (appUser.getUserGender() == 1) {
                                if (appUserMan != null) {
                                    GameUtils.useHour(appUserMan);
                                    appUserManStore.save(appUserMan, Persistent.UPDATE, appUserLimit);
                                }
                            } else if (appUser.getUserGender() == 0) {
                                if (appUserLady != null) {
                                    GameUtils. useHour(appUserLady);
                                    appUserLadyStore.save(appUserLady, Persistent.UPDATE, appUserLimit);
                                }
                            }
                        }
                        if (luckLimit == 1) {
                            GameUtils.addResultArray(resultArray, "抱歉，每日只能试一次手气!", null);
                        } else {
                            GameUtils.addResultArray(resultArray, "凡事都需要有本钱，哪怕想试试手气也不例外!", null);
                        }
                        resultObj.put("result", 1);
                        resultObj.put("resultArray", resultArray);
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

}
