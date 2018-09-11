package com.diary.entity.app;

import com.diary.entity.res.ResCar;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
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
@Table(name = "APP_USER_EVENT")
public class AppUserEvent extends IdEntity implements Tracker {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AppUser userId;

    private ResEvent eventId;

    private ResEventYes yesId;

    private ResEventNo noId;

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
    @JoinColumn(name = "EVENT_ID")
    public ResEvent getEventId() {
        return eventId;
    }

    public void setEventId(ResEvent eventId) {
        this.eventId = eventId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "YES_ID")
    public ResEventYes getYesId() {
        return yesId;
    }

    public void setYesId(ResEventYes yesId) {
        this.yesId = yesId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NO_ID")
    public ResEventNo getNoId() {
        return noId;
    }

    public void setNoId(ResEventNo noId) {
        this.noId = noId;
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
