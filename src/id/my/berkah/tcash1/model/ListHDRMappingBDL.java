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
public class ListHDRMappingBDL {
    public String item_bundling_id,item_code,item_description,uom,terminate_by,terminate_date, created_date,
        created_by;


    public String getTerminate_by() {
        return terminate_by;
    }

    public void setTerminate_by(String terminate_by) {
        this.terminate_by = terminate_by;
    }

    public String getTerminate_date() {
        return terminate_date;
    }

    public void setTerminate_date(String terminate_date) {
        this.terminate_date = terminate_date;
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

    public String getItem_bundling_id() {
        return item_bundling_id;
    }

    public void setItem_bundling_id(String item_bundling_id) {
        this.item_bundling_id = item_bundling_id;
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
