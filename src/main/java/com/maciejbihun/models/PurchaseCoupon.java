package com.maciejbihun.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name = "PurchaseCoupon")
public class PurchaseCoupon {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PURCHASE_COUPON_SEQ")
    @SequenceGenerator(name = "PURCHASE_COUPON_SEQ", sequenceName = "PURCHASE_COUPON_SEQ", allocationSize = 1)
    Long id;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_OWNER_ID", nullable = false)
    private User owner;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOND_ID", nullable = false)
    private Bond bond;

    @Basic(optional = false)
    @Column(name = "SERVICE_UNITS", nullable = false)
    private Integer serviceUnits;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Bond getBond() {
        return bond;
    }

    public void setBond(Bond bond) {
        this.bond = bond;
    }

    public Integer getServiceUnits() {
        return serviceUnits;
    }

    public Integer subtractServiceUnits(int amountOfUnitsToSubtract){
        synchronized (this){
            this.serviceUnits = this.serviceUnits - amountOfUnitsToSubtract;
        }
        return this.serviceUnits;
    }

    public void setServiceUnits(Integer serviceUnits) {
        synchronized (this){
            this.serviceUnits = serviceUnits;
        }
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

}
