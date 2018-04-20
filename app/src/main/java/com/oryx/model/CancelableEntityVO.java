package com.oryx.model;

import java.util.Date;

public class CancelableEntityVO extends EntityVO {
    protected Date cancelDate;
    protected String canceledBy;
    protected boolean checkCancel;

    /**
     * Obtient la valeur de la propriété cancelDate.
     *
     * @return possible object is
     * {@link Date }
     */
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * Définit la valeur de la propriété cancelDate.
     *
     * @param value allowed object is
     *              {@link Date }
     */
    public void setCancelDate(Date value) {
        this.cancelDate = value;
    }

    /**
     * Obtient la valeur de la propriété canceledBy.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCanceledBy() {
        return canceledBy;
    }

    /**
     * Définit la valeur de la propriété canceledBy.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCanceledBy(String value) {
        this.canceledBy = value;
    }

    /**
     * Obtient la valeur de la propriété checkCancel.
     */
    public boolean isCheckCancel() {
        return checkCancel;
    }

    /**
     * Définit la valeur de la propriété checkCancel.
     */
    public void setCheckCancel(boolean value) {
        this.checkCancel = value;
    }

}
