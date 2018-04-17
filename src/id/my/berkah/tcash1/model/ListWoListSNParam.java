/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.model;

/**
 *
 * @author arry
 */
public class ListWoListSNParam {
    private String po_line,
                        item_id,
                        item_code,
                        item_description,
                        supplier_code,
                        supplier_description,
                        sn_last_from,
                        sn_last_to,
                        qty,
                        denom,hrn_error_code,hrn_error_txt;

    public String getPo_line() {
        return po_line;
    }

    public void setPo_line(String po_line) {
        this.po_line = po_line;
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

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public String getSupplier_description() {
        return supplier_description;
    }

    public void setSupplier_description(String supplier_description) {
        this.supplier_description = supplier_description;
    }

    public String getSn_last_from() {
        return sn_last_from;
    }

    public void setSn_last_from(String sn_last_from) {
        this.sn_last_from = sn_last_from;
    }

    public String getSn_last_to() {
        return sn_last_to;
    }

    public void setSn_last_to(String sn_last_to) {
        this.sn_last_to = sn_last_to;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getHrn_error_code() {
        return hrn_error_code;
    }

    public void setHrn_error_code(String hrn_error_code) {
        this.hrn_error_code = hrn_error_code;
    }

    public String getHrn_error_txt() {
        return hrn_error_txt;
    }

    public void setHrn_error_txt(String hrn_error_txt) {
        this.hrn_error_txt = hrn_error_txt;
    }
    
}
