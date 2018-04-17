/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWIPHdrParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class DetailWIPReceipt extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_dtl_receipt;

    @Wire
    Textbox txtprodissue, txtSubmitdate, txtsbmtby, txtapprovby, txtunitwip, txtapprovedate, txtitmcode, txtcreationby, txtstatdesc, txtwipid, txtoutwip, txtitmdesc, txtprodreceipt, txtwipreceiptate, txtcreationdate, txtprodissueid, txtwreceipt, txtitmid, txtstatid;

    @Wire
    Intbox txtqty;
    
    @Wire
    Button btnprod,btnitem,btnsave,btnsubmit,btnsn;
    
    ListWIPHDR  parentController;

    @Listen("onClick=#close")
    public void CloseWindow() {
        win_dtl_receipt.detach();
    }

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onClick=#btnprod")
    public void LOVpi() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, prod_issue_id as \"Id\",prod_issue_no as \"Production Issue Number\" from table(pkg_tcash_wip_receipt.LovPI(''))where (upper(prod_issue_id) like upper('?') or upper(prod_issue_no) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_wip_receipt.LovPI(''))Where prod_issue_no LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtprodissueid, txtprodissue});
        composerLov.setHiddenColumn(new int[]{0, 1});

        txtprodissueid.setText("");
        txtprodissue.setText("");
        composerLov.setTitle("List Of Production Issue");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_dtl_receipt);
        w.doModal();
    }

    @Listen("onClick=#btnitem")
    public void LOVItem() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\",item_description as\"Description\",unit from table(pkg_tcash_wip_receipt.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_wip_receipt.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtitmid, txtitmcode, txtitmdesc, txtunitwip});
        composerLov.setHiddenColumn(new int[]{0, 1});

        txtitmid.setText("");
        txtitmcode.setText("");
        txtitmdesc.setText("");
        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_dtl_receipt);
        w.doModal();
    }

    @Listen("onClick=#btnsave")
    public void dosaveHDR() {
        Map<String, Object> validate = model.doValidateInsertHdr(txtwipid.getText(), txtprodissueid.getText(), txtitmid.getText(), txtqty.getText(), userId);
        if (txtoutwip.getText().isEmpty()) {
            if (validate.get("OutError").equals(0)) {
                Messagebox.show("Are you sure want to save this WIP?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event e) throws ParseException {
                                if (Messagebox.ON_OK.equals(e.getName())) {

                                    Map<String, Object> map = model.doInsertWIPHDR(txtwipid.getText(), txtprodissueid.getText(), txtprodissue.getText(), txtitmid.getText(), txtqty.getText(), userId);
                                    if (map.get("OutError").equals(0)) {
                                        txtwipid.setText(map.get("OutWipRcpId").toString());
                                        Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                        selecthdr();
                                        parentController.requery();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

                                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                                }
                            }
                        }
                );

            } else {
                Messagebox.show(validate.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else {
            if (validate.get("OutError").equals(0)) {
                Messagebox.show("Are you sure want to save this WIP?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event e) throws ParseException {
                                if (Messagebox.ON_OK.equals(e.getName())) {

                                    Map<String, Object> mapper = model.doUpdateHdr(txtwipid.getText(), txtprodissueid.getText(), txtprodissue.getText(), txtitmid.getText(), txtqty.getText(), userId);
                                    if (mapper.get("OutError").equals(0)) {
                                        Messagebox.show(mapper.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                    } else {
                                        Messagebox.show(mapper.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                                }
                            }
                        }
                );
            }
        }
    }

    @Listen("onCreate=#win_dtl_receipt")
    public void onCreateWindow() {
        parentController=(ListWIPHDR)win_dtl_receipt.getAttribute("parentController");
        System.out.println(parentController);
        txtqty.setValue(0);
        selecthdr();
        onCreateDisableButton();
    }

    @Listen("onClick=#btnsn")
    public void selectRange() {
        Map<String, Object> mapper = model.doInsertSelectSnTemp(txtwipid.getValue(), txtprodissueid.getValue(), txtitmid.getValue(), userId);
        if (mapper.get("OutError").equals(0)) {
            parsing();
        } else {
            Messagebox.show(mapper.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void parsing() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InWipRcpId", txtwipid.getText());
        map.put("prodissueid", txtprodissueid.getText());
        map.put("itemid", txtitmid.getText());
        map.put("userId", userId);
        map.put("status", txtstatid.getText());
        Window w = (Window) Executions.createComponents("/Tcash/ListSelectRange.zul", null, map);
        w.setAttribute("parentController1", this);
        w.doModal();
    }

    public void selecthdr() {
        if (!txtwipid.getText().isEmpty()) {
            List<ListWIPHdrParam> listWIPHdrParam = model.selectListHdr(txtwipid.getValue());
            txtwreceipt.setText(listWIPHdrParam.get(0).getWiprcpno());
            txtwipid.setText(listWIPHdrParam.get(0).getWiprcpid());
            txtwipreceiptate.setText(listWIPHdrParam.get(0).getWiprcpdate());
            txtprodissueid.setText(listWIPHdrParam.get(0).getProdissueid());
            txtitmid.setText(listWIPHdrParam.get(0).getItemid());
            txtqty.setText(listWIPHdrParam.get(0).getQuantity());
            txtstatid.setText(listWIPHdrParam.get(0).getStatus());
            txtstatdesc.setText(listWIPHdrParam.get(0).getStatusdesc());
            txtcreationdate.setText(listWIPHdrParam.get(0).getCreatedate());
            txtcreationby.setText(listWIPHdrParam.get(0).getCreatedby());
            txtcreationby.setText(listWIPHdrParam.get(0).getCreatedby());
            txtapprovedate.setText(listWIPHdrParam.get(0).getApprovedate());
            txtapprovby.setText(listWIPHdrParam.get(0).getApprovedby());
            txtSubmitdate.setText(listWIPHdrParam.get(0).getSubmitdate());
            txtsbmtby.setText(listWIPHdrParam.get(0).getSubmitedby());
        } else {

        }
    }

    @Listen("onClick=#newrecord")
    public void newrecord() {
        txtwreceipt.setText("");
        txtwipid.setText("");
        txtwipreceiptate.setText("");
        txtprodissue.setText("");
        txtprodissueid.setText("");
        txtitmid.setText("");
        txtitmcode.setText("");
        txtitmdesc.setText("");
        txtqty.setText("0");
        txtstatdesc.setText("");
        txtstatid.setText("");
        txtcreationby.setText("");
        txtcreationdate.setText("");
        txtSubmitdate.setText("");
        txtsbmtby.setText("");
        txtapprovby.setText("");
        txtapprovedate.setText("");
        btnitem.setDisabled(false);
        btnprod.setDisabled(false);
        btnsave.setDisabled(false);
        btnsubmit.setDisabled(false);
    }

    @Listen("onClick=#btnsubmit")
    public void submitForm() {

        Map<String, Object> validation = model.doValidateUpdateHdr(txtwipid.getText(), txtprodissueid.getText(), txtitmid.getText(), txtqty.getText(), userId);
        if (validation.get("OutError").equals(0)) {
            Messagebox.show("Are you sure want to Submit ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) throws ParseException {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                Map<String, Object> map = model.doUpdateHdr(txtwipid.getText(), txtprodissueid.getText(), txtprodissue.getText(), txtitmid.getText(), txtqty.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    validateStatus("2");
                                    updateStatusWip();
                                    selecthdr();
                                     disableSubmit();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(validation.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
        }

    }

    void updateStatusWip() {
        if (txtstatid.getText().equals("1")) {
            Map<String, Object> map = model.doUpdateStatusWIP(txtwipid.getText(), "2", userId);
            if (map.get("OutError").equals(0)) {
                   parentController.requery();
                Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else if (txtstatid.getText().equals("2")) {
            Map<String, Object> map = model.doUpdateStatusWIP(txtwipid.getText(), "3", userId);
            if (map.get("OutError").equals(0)) {
                   parentController.requery();
                Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
            }

        }
    }

//    public void validateStatus() {
//         if (txtstatid.getText().equals("1")) {
//        Map<String, Object> map = model.doValidateStatus(txtwipid.getText(), "1");
//        if (map.get("OutError").equals(0)) {
//        } else {
//            Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    } else if(txtstatid.getText().equals("2")){
//         Map<String, Object> map = model.doValidateStatus(txtwipid.getText(), "2");
//        if (map.get("OutError").equals(0)) {
//        } else {
//            Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    }else if(txtstatid.getText().equals("3")){
//         Map<String, Object> map = model.doValidateStatus(txtwipid.getText(), "3");
//        if (map.get("OutError").equals(0)) {
//        } else {
//            Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    } 
//    }

    @Listen("onClick=#btnapproved")
    public void appoveForm() {
    Map<String, Object> validation = model.doValidateUpdateHdr(txtwipid.getText(), txtprodissueid.getText(), txtitmid.getText(), txtqty.getText(), userId);
        if (validation.get("OutError").equals(0)) {
            Messagebox.show("Are you sure want to Approve ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) throws ParseException {
                            if (Messagebox.ON_OK.equals(e.getName())) {

                                Map<String, Object> map = model.doUpdateHdr(txtwipid.getText(), txtprodissueid.getText(), txtprodissue.getText(), txtitmid.getText(), txtqty.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    validateStatus("3");
                                    updateStatusWip();
                                    selecthdr();
                                    disableApprove();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(validation.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
        }


}
    
    void disableSubmit(){
        btnitem.setDisabled(true);
        btnprod.setDisabled(true);
        btnsave.setDisabled(true);
      
    }
    
    void disableApprove(){
         btnitem.setDisabled(true);
        btnprod.setDisabled(true);
        btnsave.setDisabled(true);
        btnsubmit.setDisabled(true);
       btnsn.setDisabled(true);
       
    }
    
    void onCreateDisableButton(){
        if (txtstatid.getText().equals("1")) {
            
        } else  if (txtstatid.getText().equals("2")) {
            disableSubmit();
    }else  if (txtstatid.getText().equals("3")){
            disableApprove();
    } 
    }
    
    public void validateStatus(String status){
          Map<String, Object> map = model.doValidateStatus(txtwipid.getText(), status);
        if (map.get("OutError").equals(0)) {
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
        return ;
        }
    }
}
