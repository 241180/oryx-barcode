package com.oryx.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EntityVO implements Parcelable{

    protected String id;
    protected Integer version;

    protected EntityVO(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            version = null;
        } else {
            version = in.readInt();
        }
    }

    public static final Creator<EntityVO> CREATOR = new Creator<EntityVO>() {
        @Override
        public EntityVO createFromParcel(Parcel in) {
            return new EntityVO(in);
        }

        @Override
        public EntityVO[] newArray(int size) {
            return new EntityVO[size];
        }
    };

    /**
     * Obtient la valeur de la propriété id.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété version.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Définit la valeur de la propriété version.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setVersion(Integer value) {
        this.version = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        if (version == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(version);
        }
    }
}
