/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.model;

/**
 *
 * @author Ari
 */
public class DtlItmProductionIssue
{

    String prod_dtl_issue_id, prod_issue_id, item_id, item_code, item_description, quantity, unit,
            create_date, created_by;

    public String getProd_dtl_issue_id()
    {
        return prod_dtl_issue_id;
    }

    public void setProd_dtl_issue_id(String prod_dtl_issue_id)
    {
        this.prod_dtl_issue_id = prod_dtl_issue_id;
    }

    public String getProd_issue_id()
    {
        return prod_issue_id;
    }

    public void setProd_issue_id(String prod_issue_id)
    {
        this.prod_issue_id = prod_issue_id;
    }

    public String getItem_id()
    {
        return item_id;
    }

    public void setItem_id(String item_id)
    {
        this.item_id = item_id;
    }

    public String getItem_code()
    {
        return item_code;
    }

    public void setItem_code(String item_code)
    {
        this.item_code = item_code;
    }

    public String getItem_description()
    {
        return item_description;
    }

    public void setItem_description(String item_description)
    {
        this.item_description = item_description;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getCreate_date()
    {
        return create_date;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getCreated_by()
    {
        return created_by;
    }

    public void setCreated_by(String created_by)
    {
        this.created_by = created_by;
    }
}
