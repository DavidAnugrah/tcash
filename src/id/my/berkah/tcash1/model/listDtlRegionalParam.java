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
public class listDtlRegionalParam {
    
    public String
        id,
        bundling_id,
        bundling_dtl_id,
        item_id,
        regional_id,
        regional_code,
        regional_desc,
        qty,
        treshold_qty,
        created_date ,
        created_by,
        updated_date,
        updated_by;

    public String getTreshold_qty() {
        return treshold_qty;
    }

    public void setTreshold_qty(String treshold_qty) {
        this.treshold_qty = treshold_qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getRegional_id() {
        return regional_id;
    }

    public void setRegional_id(String regional_id) {
        this.regional_id = regional_id;
    }

    public String getRegional_code() {
        return regional_code;
    }

    public void setRegional_code(String regional_code) {
        this.regional_code = regional_code;
    }

    public String getRegional_desc() {
        return regional_desc;
    }

    public void setRegional_desc(String regional_desc) {
        this.regional_desc = regional_desc;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }
}
