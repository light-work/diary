package com.diary.entity.app;

import com.diary.entity.res.ResPlan;
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
@Table(name = "APP_LUXURY_PLAN")
public class AppUserPlan extends IdEntity implements Tracker {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AppUser userId;

    private ResPlan planId;

    private Integer planOfDay;

    private Integer planOfHour;

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
    @JoinColumn(name = "USER_ID")
    public AppUser getUserId() {
        return userId;
    }

    public void setUserId(AppUser userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ID")
    public ResPlan getPlanId() {
        return planId;
    }

    public void setPlanId(ResPlan planId) {
        this.planId = planId;
    }


    @Column(name = "PLAN_OF_DAY")
    public Integer getPlanOfDay() {
        return planOfDay;
    }

    public void setPlanOfDay(Integer planOfDay) {
        this.planOfDay = planOfDay;
    }

    @Column(name = "PLAN_OF_HOUR")
    public Integer getPlanOfHour() {
        return planOfHour;
    }

    public void setPlanOfHour(Integer planOfHour) {
        this.planOfHour = planOfHour;
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
