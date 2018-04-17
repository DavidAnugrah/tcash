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
public class ListWIPHdrParam {
    public String wiprcpid,itemid,itemcode,itemdescription,
            wiprcpno,
            wiprcpdate,
            prodissueid,prodissueno,
            quantity,
            unit,
            status,
            statusdesc,
            createperiod,
            createdate,
            createdby,
            approvedate,
            approvedby,
            submitdate,
            submitedby;

    public String getProdissueno() {
        return prodissueno;
    }

    public void setProdissueno(String prodissueno) {
        this.prodissueno = prodissueno;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getWiprcpid() {
        return wiprcpid;
    }

    public void setWiprcpid(String wiprcpid) {
        this.wiprcpid = wiprcpid;
    }

    public String getWiprcpno() {
        return wiprcpno;
    }

    public void setWiprcpno(String wiprcpno) {
        this.wiprcpno = wiprcpno;
    }

    public String getWiprcpdate() {
        return wiprcpdate;
    }

    public void setWiprcpdate(String wiprcpdate) {
        this.wiprcpdate = wiprcpdate;
    }

    public String getProdissueid() {
        return prodissueid;
    }

    public void setProdissueid(String prodissueid) {
        this.prodissueid = prodissueid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusdesc() {
        return statusdesc;
    }

    public void setStatusdesc(String statusdesc) {
        this.statusdesc = statusdesc;
    }

    public String getCreateperiod() {
        return createperiod;
    }

    public void setCreateperiod(String createperiod) {
        this.createperiod = createperiod;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(String approvedate) {
        this.approvedate = approvedate;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(String submitdate) {
        this.submitdate = submitdate;
    }

    public String getSubmitedby() {
        return submitedby;
    }

    public void setSubmitedby(String submitedby) {
        this.submitedby = submitedby;
    }
}
