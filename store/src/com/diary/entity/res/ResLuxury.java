package com.diary.entity.res;

import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-15
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "RES_LUXURY")
public class ResLuxury extends IdEntity implements Tracker {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private Integer buyPrice;

    private Integer sellPrice;

    private Integer offsetBuy;

    private Integer offsetSell;

    private String remarks;

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

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "BUY_PRICE")
    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Column(name = "SELL_PRICE")
    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Column(name = "OFFSET_BUY")
    public Integer getOffsetBuy() {
        return offsetBuy;
    }

    public void setOffsetBuy(Integer offsetBuy) {
        this.offsetBuy = offsetBuy;
    }

    @Column(name = "OFFSET_SELL")
    public Integer getOffsetSell() {
        return offsetSell;
    }

    public void setOffsetSell(Integer offsetSell) {
        this.offsetSell = offsetSell;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
