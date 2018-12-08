package com.maciejbihun.models;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name="ServiceTag")
public class ServiceTag extends Tag{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TAG_SEQ")
    @SequenceGenerator(name = "TAG_SEQ", sequenceName = "TAG_SEQ", allocationSize = 1)
    Long id;

    @Basic(optional = false)
    @Column(name = "NUMBER_OF_REGISTERED_SERVICES", nullable = false, updatable = true)
    private AtomicInteger numberOfRegisteredServices = new AtomicInteger(0);

    @Basic(optional = false)
    @Column(name = "NUMBER_OF_EXPECTED_SERVICES", nullable = false, updatable = true)
    private AtomicInteger numberOfExpectedServices = new AtomicInteger(0);



}
