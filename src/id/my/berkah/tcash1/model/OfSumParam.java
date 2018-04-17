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
public class OfSumParam {
    public String of_id,filename,item_code,item_name,line_no,total_qty,success_qty,failed_qty;

    public String getOf_id() {
        return of_id;
    }

    public void setOf_id(String of_id) {
        this.of_id = of_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getSuccess_qty() {
        return success_qty;
    }

    public void setSuccess_qty(String success_qty) {
        this.success_qty = success_qty;
    }

    public String getFailed_qty() {
        return failed_qty;
    }

    public void setFailed_qty(String failed_qty) {
        this.failed_qty = failed_qty;
    }
}
