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
public class GdListPoItemBundlingParam {
    public String bundling_id,
            bundling_no,
            po_id,
            position_number,
            item_id,
            item_code,
            item_description,
            bundling_date,
            ordered_qty,
            total_gd_qty,
            qty,
            warehouse_id,
            wh_id,
            bu_id,
            range_from,
            Range_to;

    public String getOrdered_qty() {
        return ordered_qty;
    }

    public void setOrdered_qty(String ordered_qty) {
        this.ordered_qty = ordered_qty;
    }

    public String getTotal_gd_qty() {
        return total_gd_qty;
    }

    public void setTotal_gd_qty(String total_gd_qty) {
        this.total_gd_qty = total_gd_qty;
    }

    public String getBundling_id() {
        return bundling_id;
    }

    public void setBundling_id(String bundling_id) {
        this.bundling_id = bundling_id;
    }

    public String getBundling_no() {
        return bundling_no;
    }

    public void setBundling_no(String bundling_no) {
        this.bundling_no = bundling_no;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getPosition_number() {
        return position_number;
    }

    public void setPosition_number(String position_number) {
        this.position_number = position_number;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

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

    public String getBundling_date() {
        return bundling_date;
    }

    public void setBundling_date(String bundling_date) {
        this.bundling_date = bundling_date;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWh_id() {
        return wh_id;
    }

    public void setWh_id(String wh_id) {
        this.wh_id = wh_id;
    }

    public String getBu_id() {
        return bu_id;
    }

    public void setBu_id(String bu_id) {
        this.bu_id = bu_id;
    }

    public String getRange_from() {
        return range_from;
    }

    public void setRange_from(String range_from) {
        this.range_from = range_from;
    }

    public String getRange_to() {
        return Range_to;
    }

    public void setRange_to(String Range_to) {
        this.Range_to = Range_to;
    }
}
