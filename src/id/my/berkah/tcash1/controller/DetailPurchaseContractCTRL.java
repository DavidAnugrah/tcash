/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.IDefines.COLORS;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class DetailPurchaseContractCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_detail_pc1;

    @Wire
    Textbox txtitemid, txtitemdesc, txtitemcode, txtflag, txtcontractid, txtpositionnumber, txtcontractdtl, txtpurchaseunit, txtpurchase, txtrderqty, txtcontractdtlid, txtout, txtamunt, status;

    @Wire
    Intbox txtpurchasepriceunit, txtminqty, txtmaxqty, txtdiscountamount, txtdiscountpecentage;

    @Wire
    Datebox txteffectiveDate, txtexpiredate;

    @Wire
    Button btnitem, save, newrecord;

    ModelTcashCTLR model = new ModelTcashCTLR();

    private PurchaseContract parentcontroller;

    @Listen("onCreate=#win_detail_pc1")
    public void onCreateWindow() {

        parentcontroller = (PurchaseContract) win_detail_pc1.getAttribute("parentcontroller");

        if (status.getText().equalsIgnoreCase("DRAFT")) {
            colorMandatory();
//            txteffectiveDate.setConstraint("no past");
            if (txtitemid.getText().equals("")) {
                txtflag.setText("INSERT");
//                colorMandatory();
            } else {
                txtflag.setText("UPDATE");
            }

        } else if (status.getText().equalsIgnoreCase("ACTIVE")) {
            txtflag.setText("UPDATE");
            readonly();
            newrecord.setDisabled(true);
            colorDefault();
        } else if (status.getText().equalsIgnoreCase("TERMINATED")) {
            readonly();
            newrecord.setDisabled(true);
            colorDefault();
        }

    }

    @Listen("onClick=#close")
    public void close() {

        win_detail_pc1.detach();
    }

    @Listen("onClick = #btnitem")
    public void lovsupplier() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\",item_description as \"Description\",Unit as\"Unit\" from table(pkg_tcash_lov.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc, txtpurchaseunit});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("600px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_detail_pc1);
        w.doModal();

    }

    @Listen("onClick=#save")
    public void save1() {

        Messagebox.show("Are you sure want to Save?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {

                            Map<String, Object> validation = model.doPcValidasiDtl("SAVE", txtflag.getText(), txtcontractid.getText(), txtcontractdtlid.getText(), txtitemcode.getText(), txtitemid.getText(),
                                    txtrderqty.getValue(), txtpurchaseunit.getText(), txtpurchasepriceunit.getValue().toString(), txtdiscountpecentage.getValue().toString(), txtdiscountamount.getValue().toString(), txtminqty.getValue().toString(), txtmaxqty.getValue().toString());
                            if (validation.get("OutError").equals(0)) {

                                if (txtflag.getText().equals("INSERT")) {
                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("SAVE");
//    	
                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                    if (oe.getOutError() == 0) {
                                        Map<String, Object> map = model.doPcSaveDtl("INSERT", txtcontractid.getText(), txtcontractdtlid.getText(), txtitemcode.getText(), txtitemid.getText(), txtrderqty.getValue(), txtpurchaseunit.getText(), txtpurchasepriceunit.getValue().toString(), txtdiscountpecentage.getValue().toString(), txtdiscountamount.getValue().toString(), txtminqty.getValue().toString(), txtmaxqty.getValue().toString(), userId);

                                        if (map.get("OutError").equals(0)) {
                                            Messagebox.show(map.get("OutMessages").toString());
                                            parentcontroller.requery();
                                            parentcontroller.autorefresh();
                                            txtflag.setText("UPDATE");
                                            txtcontractdtlid.setText(map.get("OutContractDtlId").toString());
//                                            colorDefault();
                                        } else {
                                            Messagebox.show(map.get("OutMessages").toString(),"Purchase Contract",Messagebox.OK,Messagebox.EXCLAMATION);

                                        }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

                                } else {

//                                if (txtflag.getText().equals("UPDATE")) {
                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("SAVE");

                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                    if (oe.getOutError() == 0) {

                                        Map<String, Object> map = model.doPcSaveDtl(txtflag.getText(), txtcontractid.getText(), txtcontractdtlid.getText(), txtitemcode.getText(), txtitemid.getText(), txtrderqty.getValue(), txtpurchaseunit.getText(), txtpurchasepriceunit.getValue().toString(), txtdiscountpecentage.getValue().toString(), txtdiscountamount.getValue().toString(), txtminqty.getValue().toString(), txtmaxqty.getValue().toString(), userId);
                                        if (map.get("OutError").equals(0)) {
                                            Messagebox.show(map.get("OutMessages").toString());
                                            parentcontroller.requery();
                                            parentcontroller.autorefresh();
//                                            colorDefault();
                                        } else {
                                            Messagebox.show(map.get("OutMessages").toString(),"Purchase Contract",Messagebox.OK,Messagebox.EXCLAMATION);
                                        }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
//                                }
                                }
                            } else {
                                Messagebox.show(validation.get("OutMessages").toString());
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );

    }

    public void readonly() {
        txtitemcode.setReadonly(true);
        txtitemdesc.setReadonly(true);
        txtpurchasepriceunit.setReadonly(true);
        txtdiscountamount.setReadonly(true);
        txtdiscountpecentage.setReadonly(true);
        txtmaxqty.setReadonly(true);
        txtamunt.setReadonly(true);
        txtminqty.setReadonly(true);
//        save.setDisabled(true);
        btnitem.setDisabled(true);
//        txtpurchasepriceunit.setStyle("text-align:right");
//        txtdiscountpecentage.setStyle("text-align:right");
//        txtminqty.setStyle("text-align:right");
//        txtdiscountamount.setStyle("text-align:right");
//        txtmaxqty.setStyle("text-align:right");
//        txtamunt.setStyle("text-align:right");
//        txteffectiveDate.setDisabled(true);
//        txtexpiredate.setDisabled(true);
    }

  

    @Listen("onClick=#newrecord")
    public void newRecord() {
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtpurchaseunit.setText("");
        txtpurchasepriceunit.setValue(0);
        txtdiscountamount.setValue(0);
        txtmaxqty.setValue(0);
        txtminqty.setValue(0);
        txtdiscountpecentage.setValue(0);
        txtflag.setText("INSERT");
        btnitem.setDisabled(false);
        colorMandatory();
    }

//    PurchaseContract PurchaseContract;
//      @Listen("onBlur=#txtminqty")
//    public void validateMinQty(){
//         Path parent1 = new Path("/win_purchase_contract");
//      Intbox txtquantity = (Intbox) new Path(parent1, "txtquantity").getComponent();
////      Messagebox.show(txtquantity.getValue().toString());
//          if (txtquantity.getValue() < txtminqty.getValue()){
//              Messagebox.show("Agreed Quatity cannot Less than Minimum Quantity !","Pruchase Contract",Messagebox.OK,Messagebox.EXCLAMATION);
//          } 
//    }
////    
//     @Listen("onBlur=#txtmaxqty")
//    public void validateMaxQty(){
//           Path parent1 = new Path("/win_purchase_contract");
//      Intbox txtquantity = (Intbox) new Path(parent1, "txtquantity").getComponent();
//          if (txtquantity.getValue() > txtmaxqty.getValue()) {
//              Messagebox.show("Agreed Quatity cannot More than Maximum Quantity !","Pruchase Contract",Messagebox.OK,Messagebox.EXCLAMATION);
//          } 
//    }
    void colorMandatory() {
        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
//        txtpurchasepriceunit.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
//        txtmaxqty.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtmaxqty.setStyle("background-color:#FFFACD;text-align:right");
        txtpurchasepriceunit.setStyle("background-color:#FFFACD;text-align:right");

    }

    void colorDefault() {
        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//        txtpurchasepriceunit.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//        txtmaxqty.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtmaxqty.setStyle("background-color:#ffffff;text-align:right");
        txtpurchasepriceunit.setStyle("background-color:#ffffff;text-align:right");
    }

}
