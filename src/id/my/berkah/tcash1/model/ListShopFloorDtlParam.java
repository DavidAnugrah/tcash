/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.model;

/**
 *
 * @author Azec
 */
public class ListShopFloorDtlParam {
    public String SFC_ID,
            ITEM_ID,
            ITEM_CODE,
            ITEM_DESCRIPTION,
            FROM_SN,
            TO_SN,
            STATUS,
            STATUS_NAME,
            FILE_NAME,
            UPLOAD_DATE,
            STATUS_MESSAGE;

    public String getSFC_ID() {
        return SFC_ID;
    }

    public void setSFC_ID(String SFC_ID) {
        this.SFC_ID = SFC_ID;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_DESCRIPTION() {
        return ITEM_DESCRIPTION;
    }

    public void setITEM_DESCRIPTION(String ITEM_DESCRIPTION) {
        this.ITEM_DESCRIPTION = ITEM_DESCRIPTION;
    }

    public String getFROM_SN() {
        return FROM_SN;
    }

    public void setFROM_SN(String FROM_SN) {
        this.FROM_SN = FROM_SN;
    }

    public String getTO_SN() {
        return TO_SN;
    }

    public void setTO_SN(String TO_SN) {
        this.TO_SN = TO_SN;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTATUS_NAME() {
        return STATUS_NAME;
    }

    public void setSTATUS_NAME(String STATUS_NAME) {
        this.STATUS_NAME = STATUS_NAME;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getUPLOAD_DATE() {
        return UPLOAD_DATE;
    }

    public void setUPLOAD_DATE(String UPLOAD_DATE) {
        this.UPLOAD_DATE = UPLOAD_DATE;
    }

    public String getSTATUS_MESSAGE() {
        return STATUS_MESSAGE;
    }

    public void setSTATUS_MESSAGE(String STATUS_MESSAGE) {
        this.STATUS_MESSAGE = STATUS_MESSAGE;
    }
}
