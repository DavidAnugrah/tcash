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
public class ListInvSelectSNParam {
   public String SFC_ID,
            SFC_DTL_ID,
            item_id,
             sn_from,
            sn_to,
            quantity,
            item_detail_id,
            storage_id,
            item_loc_id,
            lot_id,
            wh_id,
            wh_loc_id;

    public String getSFC_ID() {
        return SFC_ID;
    }

    public void setSFC_ID(String SFC_ID) {
        this.SFC_ID = SFC_ID;
    }

    public String getSFC_DTL_ID() {
        return SFC_DTL_ID;
    }

    public void setSFC_DTL_ID(String SFC_DTL_ID) {
        this.SFC_DTL_ID = SFC_DTL_ID;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getSn_from() {
        return sn_from;
    }

    public void setSn_from(String sn_from) {
        this.sn_from = sn_from;
    }

    public String getSn_to() {
        return sn_to;
    }

    public void setSn_to(String sn_to) {
        this.sn_to = sn_to;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItem_detail_id() {
        return item_detail_id;
    }

    public void setItem_detail_id(String item_detail_id) {
        this.item_detail_id = item_detail_id;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getItem_loc_id() {
        return item_loc_id;
    }

    public void setItem_loc_id(String item_loc_id) {
        this.item_loc_id = item_loc_id;
    }

    public String getLot_id() {
        return lot_id;
    }

    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
    }

    public String getWh_id() {
        return wh_id;
    }

    public void setWh_id(String wh_id) {
        this.wh_id = wh_id;
    }

    public String getWh_loc_id() {
        return wh_loc_id;
    }

    public void setWh_loc_id(String wh_loc_id) {
        this.wh_loc_id = wh_loc_id;
    }
}
