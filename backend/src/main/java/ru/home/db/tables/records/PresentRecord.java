/*
 * This file is generated by jOOQ.
 */
package ru.home.db.tables.records;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import ru.home.db.tables.Present;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2",
        "schema version:PUBLIC_1.5.0"
    },
    date = "2018-10-16T00:20:10.442Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PresentRecord extends UpdatableRecordImpl<PresentRecord> implements Record4<UUID, String, BigDecimal, LocalDateTime> {

    private static final long serialVersionUID = -710230558;

    /**
     * Setter for <code>PUBLIC.present.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.present.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>PUBLIC.present.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.present.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.present.price</code>.
     */
    public void setPrice(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.present.price</code>.
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>PUBLIC.present.date</code>.
     */
    public void setDate(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.present.date</code>.
     */
    public LocalDateTime getDate() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, BigDecimal, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, BigDecimal, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Present.PRESENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Present.PRESENT.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return Present.PRESENT.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return Present.PRESENT.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component4() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value4() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PresentRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PresentRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PresentRecord value3(BigDecimal value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PresentRecord value4(LocalDateTime value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PresentRecord values(UUID value1, String value2, BigDecimal value3, LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PresentRecord
     */
    public PresentRecord() {
        super(Present.PRESENT);
    }

    /**
     * Create a detached, initialised PresentRecord
     */
    public PresentRecord(UUID id, String name, BigDecimal price, LocalDateTime date) {
        super(Present.PRESENT);

        set(0, id);
        set(1, name);
        set(2, price);
        set(3, date);
    }
}
