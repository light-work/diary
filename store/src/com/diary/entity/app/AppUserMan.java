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
@Table(name = "APP_USER_MAN")
public class AppUserMan extends IdEntity implements Tracker, Cloneable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AppUser userId;

    private Integer health;

    private Integer money;

    private Integer fundMoney;

    private Integer ability;


    private Integer experience;

    private Integer happy;


    private Integer positive;

    private Integer connections;

    private Integer days;

    private Integer hours;

    private Integer house;


    private Integer car;

    private Integer score;

    private String comment;

    private String commentText;

    private Integer asset;

    private Integer carAsset;

    private Integer houseAsset;

    private Integer attrScore;

    private String jobTitle;

    private String coupleTitle;

    private String carTitle;

    private String houseTitle;

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


    @Transient
    public Integer getFundMoney() {
        return fundMoney;
    }

    public void setFundMoney(Integer fundMoney) {
        this.fundMoney = fundMoney;
    }

    @Transient
    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    @Transient
    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public AppUser getUserId() {
        return userId;
    }

    public void setUserId(AppUser userId) {
        this.userId = userId;
    }

    @Column(name = "HEALTH")
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    @Column(name = "MONEY")
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Column(name = "SCORE")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "ABILITY")
    public Integer getAbility() {
        return ability;
    }

    public void setAbility(Integer ability) {
        this.ability = ability;
    }

    @Column(name = "EXPERIENCE")
    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Column(name = "HAPPY")
    public Integer getHappy() {
        return happy;
    }

    public void setHappy(Integer happy) {
        this.happy = happy;
    }


    @Column(name = "POSITIVE")
    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    @Column(name = "CONNECTIONS")
    public Integer getConnections() {
        return connections;
    }

    public void setConnections(Integer connections) {
        this.connections = connections;
    }

    @Column(name = "DAYS")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Column(name = "HOURS")
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "ASSET")
    public Integer getAsset() {
        return asset;
    }

    public void setAsset(Integer asset) {
        this.asset = asset;
    }

    @Column(name = "CAR_ASSET")
    public Integer getCarAsset() {
        return carAsset;
    }

    public void setCarAsset(Integer carAsset) {
        this.carAsset = carAsset;
    }

    @Column(name = "HOUSE_ASSET")
    public Integer getHouseAsset() {
        return houseAsset;
    }

    public void setHouseAsset(Integer houseAsset) {
        this.houseAsset = houseAsset;
    }

    @Column(name = "ATTR_SCORE")
    public Integer getAttrScore() {
        return attrScore;
    }

    public void setAttrScore(Integer attrScore) {
        this.attrScore = attrScore;
    }

    @Column(name = "COMMENT_TEXT")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Column(name = "JOB_TITLE")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Column(name = "COUPLE_TITLE")
    public String getCoupleTitle() {
        return coupleTitle;
    }

    public void setCoupleTitle(String coupleTitle) {
        this.coupleTitle = coupleTitle;
    }

    @Column(name = "CAR_TITLE")
    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    @Column(name = "HOUSE_TITLE")
    public String getHouseTitle() {
        return houseTitle;
    }

    public void setHouseTitle(String houseTitle) {
        this.houseTitle = houseTitle;
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

    public Object clone() {
        AppUserMan sc = null;
        try {
            sc = (AppUserMan) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sc;
    }
}
