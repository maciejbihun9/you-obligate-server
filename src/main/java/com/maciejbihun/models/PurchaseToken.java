package com.maciejbihun.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Will hold information about reservation service details.
 *
 * @author Maciej Bihun
 */
@Entity
@Table(name = "Bond")
public class PurchaseToken {

    // will be created after clicking 'register service unit' button
    // it will be created for Bond (available units, registered units)
    // this might work as a booking for service unit if a given bond
    // this will represent registered purchase of a bond unit
    // bond might have many PurchaseUnits, but one purchase won't change accounts,
    // those purchase tokens
    // status if was cancelled or purchase was not made,
    // PurchaseToken will be active for given amount of time and after it will expire
    // will be owned by a bond
    // bond will end with available units, registered units,
    // inform me if he has available units for this specific registered service

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PURCHASE_TOKEN_SEQ")
    @SequenceGenerator(name = "PURCHASE_TOKEN_SEQ", sequenceName = "PURCHASE_TOKEN_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "SERVICE_BUYER", nullable = false, updatable = false)
    private User customer;

    /**
     * After service was done a customer has to set service status to be approved.
     */
    @Basic(optional = false)
    @Column(name = "CUSTOMER_SERVICE_APPROVAL", nullable = false, updatable = false)
    private boolean customerServiceApproval = false;

    /**
     * After service was done an issuer has to set service status to be approved.
     */
    @Basic(optional = false)
    @Column(name = "ISSUER_SERVICE_APPROVAL", nullable = false, updatable = false)
    private boolean issuerServiceApproval = false;

    /**
     * This token will be only for one registered service unit.
     */
    @Basic(optional = false)
    @Column(name = "PURCHASE_UNITS", nullable = false, updatable = false)
    private Integer purchaseUnits = 1;

    @Basic(optional = false)
    @Column(name = "START_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime startDateTime = LocalDateTime.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "BOND_ID", nullable = false)
    private Bond bond;

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public boolean isCustomerServiceApproval() {
        return customerServiceApproval;
    }

    public void setCustomerServiceApproval(boolean customerServiceApproval) {
        this.customerServiceApproval = customerServiceApproval;
    }

    public boolean isIssuerServiceApproval() {
        return issuerServiceApproval;
    }

    public void setIssuerServiceApproval(boolean issuerServiceApproval) {
        this.issuerServiceApproval = issuerServiceApproval;
    }

    public Integer getPurchaseUnits() {
        return purchaseUnits;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Bond getBond() {
        return bond;
    }

    public void setBond(Bond bond) {
        this.bond = bond;
    }
}
