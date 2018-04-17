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
public class ListAddDtlPairingParam {
   public String item_code,
        item_description,sn_from,sn_to,qty;

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
