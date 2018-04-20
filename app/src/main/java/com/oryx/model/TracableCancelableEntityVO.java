package com.oryx.model;

import java.util.Date;

public class TracableCancelableEntityVO extends CancelableEntityVO {
    protected Date createDate;
    protected String createdBy;
    protected Date updateDate;
    protected String updatedBy;

    /**
     * Obtient la valeur de la propriété createDate.
     *
     * @return possible object is
     * {@link Date }
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Définit la valeur de la propriété createDate.
     *
     * @param value allowed object is
     *              {@link Date }
     */
    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    /**
     * Obtient la valeur de la propriété createdBy.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Définit la valeur de la propriété createdBy.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Obtient la valeur de la propriété updateDate.
     *
     * @return possible object is
     * {@link Date }
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Définit la valeur de la propriété updateDate.
     *
     * @param value allowed object is
     *              {@link Date }
     */
    public void setUpdateDate(Date value) {
        this.updateDate = value;
    }

    /**
     * Obtient la valeur de la propriété updatedBy.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Définit la valeur de la propriété updatedBy.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUpdatedBy(String value) {
        this.updatedBy = value;
    }

}
