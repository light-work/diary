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
@Table(name = "APP_USER_LADY")
public class AppUserLady extends IdEntity implements Tracker, Cloneable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AppUser userId;

    private Integer health;

    private Integer fundMoney;

    private Integer money;

    private Integer ability;


    private Integer wisdom;

    private Integer happy;


    private Integer beauty;

    private Integer popularity;

    private Integer days;

    private Integer hours;

    private Integer score;

    private String comment;

    private String commentText;

    private Integer asset;

    private Integer clothesAsset;

    private Integer luxuryAsset;

    private Integer attrScore;

    private String jobTitle;

    private String coupleTitle;

    private String clothesTitle;

    private String luxuryTitle;

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

    @Column(name = "HEALTH")
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }


    @Transient
    public Integer getFundMoney() {
        return fundMoney;
    }

    public void setFundMoney(Integer fundMoney) {
        this.fundMoney = fundMoney;
    }

    @Column(name = "MONEY")
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Column(name = "ABILITY")
    public Integer getAbility() {
        return ability;
    }

    public void setAbility(Integer ability) {
        this.ability = ability;
    }


    @Column(name = "HAPPY")
    public Integer getHappy() {
        return happy;
    }

    public void setHappy(Integer happy) {
        this.happy = happy;
    }

    @Column(name = "WISDOM")
    public Integer getWisdom() {
        return wisdom;
    }

    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    @Column(name = "BEAUTY")
    public Integer getBeauty() {
        return beauty;
    }

    public void setBeauty(Integer beauty) {
        this.beauty = beauty;
    }

    @Column(name = "POPULARITY")
    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
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

    @Column(name = "SCORE")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Column(name = "CLOTHES_TITLE")
    public String getClothesTitle() {
        return clothesTitle;
    }

    public void setClothesTitle(String clothesTitle) {
        this.clothesTitle = clothesTitle;
    }

    @Column(name = "LUXURY_TITLE")
    public String getLuxuryTitle() {
        return luxuryTitle;
    }

    public void setLuxuryTitle(String luxuryTitle) {
        this.luxuryTitle = luxuryTitle;
    }

    @Column(name = "ASSET")
    public Integer getAsset() {
        return asset;
    }

    public void setAsset(Integer asset) {
        this.asset = asset;
    }

    @Column(name = "CLOTHES_ASSET")
    public Integer getClothesAsset() {
        return clothesAsset;
    }

    public void setClothesAsset(Integer clothesAsset) {
        this.clothesAsset = clothesAsset;
    }

    @Column(name = "LUXURY_ASSET")
    public Integer getLuxuryAsset() {
        return luxuryAsset;
    }

    public void setLuxuryAsset(Integer luxuryAsset) {
        this.luxuryAsset = luxuryAsset;
    }

    @Column(name = "ATTR_SCORE")
    public Integer getAttrScore() {
        return attrScore;
    }

    public void setAttrScore(Integer attrScore) {
        this.attrScore = attrScore;
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
        AppUserLady sc = null;
        try {
            sc = (AppUserLady) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sc;
    }

}
