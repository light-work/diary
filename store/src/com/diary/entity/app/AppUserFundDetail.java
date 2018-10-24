package com.diary.entity.app;

import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-15
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "APP_USER_FUND_DETAIL")
public class AppUserFundDetail extends IdEntity implements Tracker {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AppUserFund userFundId;

    private Integer beforeMoney;

    private Integer afterMoney;

    private Double market;

    private Integer day;

    private Date created;

    private String createdBy;

    private Date updated;

    private String updatedBy;

    private String useYn;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_FUND_ID")
    public AppUserFund getUserFundId() {
        return userFundId;
    }

    public void setUserFundId(AppUserFund userFundId) {
        this.userFundId = userFundId;
    }

    @Column(name = "BEFORE_MONEY")
    public Integer getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Integer beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    @Column(name = "AFTER_MONEY")
    public Integer getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(Integer afterMoney) {
        this.afterMoney = afterMoney;
    }

    @Column(name = "MARKET")
    public Double getMarket() {
        return market;
    }

    public void setMarket(Double market) {
        this.market = market;
    }

    @Column(name = "DAY")
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }



    @Column(name = "CREATED", updatable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "UPDATED")
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "UPDATED_BY")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "USE_YN")
    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
