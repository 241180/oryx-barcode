package com.oryx.barcode.model;

import android.os.Parcel;

public class BrandVO
        extends TracableCancelableEntityVO {

    protected String brandCode;
    protected String brandName;
    protected String description;


    /**
     * Obtient la valeur de la propriété brandCode.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * Définit la valeur de la propriété brandCode.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBrandCode(String value) {
        this.brandCode = value;
    }

    /**
     * Obtient la valeur de la propriété brandName.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * Définit la valeur de la propriété brandName.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBrandName(String value) {
        this.brandName = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
