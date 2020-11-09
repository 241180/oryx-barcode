package com.oryx.barcode.model;

public class ProductCategoryVO
        extends TracableCancelableEntityVO {

    protected String categoryCode;
    protected String categoryName;
    protected String description;

    /**
     * Obtient la valeur de la propriété categoryCode.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Définit la valeur de la propriété categoryCode.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryCode(String value) {
        this.categoryCode = value;
    }

    /**
     * Obtient la valeur de la propriété categoryName.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Définit la valeur de la propriété categoryName.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryName(String value) {
        this.categoryName = value;
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
