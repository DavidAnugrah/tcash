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
public class ListSelectSNPAram {
    public String  bundling_id,
        bundling_dtl_id,
        item_loc_id,
        item_id,
        range_from,
        range_to,
        qty;

    public String getBundling_id() {
        return bundling_id;
    }

    public void setBundling_id(String bundling_id) {
        this.bundling_id = bundling_id;
    }

    public String getBundling_dtl_id() {
        return bundling_dtl_id;
    }

    public void setBundling_dtl_id(String bundling_dtl_id) {
        this.bundling_dtl_id = bundling_dtl_id;
    }

    public String getItem_loc_id() {
        return item_loc_id;
    }

    public void setItem_loc_id(String item_loc_id) {
        this.item_loc_id = item_loc_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getRange_from() {
        return range_from;
    }

    public void setRange_from(String range_from) {
        this.range_from = range_from;
    }

    public String getRange_to() {
        return range_to;
    }

    public void setRange_to(String range_to) {
        this.range_to = range_to;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
