package com.oryx.barcode.model;

import android.os.Parcel;

public class ProductVO extends TracableCancelableEntityVO {

    protected String code;
    protected String name;
    protected String description;
    protected ProductCategoryVO category;
    protected String categoryId;
    protected BrandVO brand;
    protected String brandId;

    /**
     * Obtient la valeur de la propriété code.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit la valeur de la propriété code.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
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

    /**
     * Obtient la valeur de la propriété category.
     *
     * @return possible object is
     * {@link ProductCategoryVO }
     */
    public ProductCategoryVO getCategory() {
        return category;
    }

    /**
     * Définit la valeur de la propriété category.
     *
     * @param value allowed object is
     *              {@link ProductCategoryVO }
     */
    public void setCategory(ProductCategoryVO value) {
        this.category = value;
    }

    /**
     * Obtient la valeur de la propriété categoryId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Définit la valeur de la propriété categoryId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryId(String value) {
        this.categoryId = value;
    }

    /**
     * Obtient la valeur de la propriété brand.
     *
     * @return possible object is
     * {@link BrandVO }
     */
    public BrandVO getBrand() {
        return brand;
    }

    /**
     * Définit la valeur de la propriété brand.
     *
     * @param value allowed object is
     *              {@link BrandVO }
     */
    public void setBrand(BrandVO value) {
        this.brand = value;
    }

    /**
     * Obtient la valeur de la propriété brandId.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * Définit la valeur de la propriété brandId.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBrandId(String value) {
        this.brandId = value;
    }

}
