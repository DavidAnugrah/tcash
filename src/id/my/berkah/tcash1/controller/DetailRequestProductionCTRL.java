/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.LovBUParam;
import id.my.berkah.tcash1.model.LovItemParam;
import id.my.berkah.tcash1.model.LovWhParam;
import id.my.berkah.tcash1.model.RpListDtlParam;
import id.my.berkah.util.IDefines;
import id.my.berkah.util.IDefines.COLORS;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class DetailRequestProductionCTRL extends SelectorComposer<Component> implements IDefines {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    public Textbox getTxtout() {
        return txtout;
    }

    public void setTxtout(Textbox txtout) {
        this.txtout = txtout;
    }

    @Wire
    Window win_dtl_Request_production;

    @Wire
    Textbox flag, txtbuId, txtbucode, txtregcode, txtbuDesc, txtout, txtitemid, txtitemcode, txtitemdesc, txtregid, txtregdesc, txtwhid, txtwhcode, txtwhdesc, txtorderunit, txtid, txtstatus, txtposition, txtneeddate,
            txtreqid, txtrequisitiondtlid, txtrequisitionline, txtregno, txtrequisitionid, statusreq;

    @Wire
    Intbox txtquntity;

    @Wire
    Button newrecord, save, close, btnwh, btnitem, btnbu;

    @Wire
    Datebox dtneeddate;

    ModelTcashCTLR model = new ModelTcashCTLR();

    private RequestProductionCTRL parentControllerq;

    String qty;
    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date = new Date();

    int total = 0;

    @Listen("onCreate=#win_dtl_Request_production")
    public void onCreateWindow() {
        parentControllerq = (RequestProductionCTRL) win_dtl_Request_production.getAttribute("parentControllerq");
        if (statusreq.getValue().equalsIgnoreCase("Draft") && txtbuId.getText().equals("")) {
            flag.setText("INSERT");
            btnwh.setDisabled(true);
            dtneeddate.setConstraint("no past");
            txtquntity.setStyle("background-color:#FFFACD;text-align:right");
//                txtquntity.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            dtneeddate.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        } else {
            flag.setText("UPDATE");
            btnwh.setDisabled(true);
            dtneeddate.setConstraint("no past");
            txtquntity.setStyle("background-color:#FFFACD;text-align:right");
//                txtquntity.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            dtneeddate.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        }
        if (statusreq.getValue().equalsIgnoreCase("Submitted") || statusreq.getValue().equalsIgnoreCase("Approved") || statusreq.getValue().equalsIgnoreCase("Canceled")) {
            disableALL();
            readonly();
            txtquntity.setStyle("background-color:#ffffff;text-align:right");
            txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            dtneeddate.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        }
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtstatus.getText().equals("INSERT")) {
            Messagebox.show("Are you sure want to close?\nData has not Saved yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                win_dtl_Request_production.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else {
            win_dtl_Request_production.detach();
        }
    }

    @Listen("onClick=#newrecord")
    public void addnewrecord() {
        int a = 1;
        try {
            total = Integer.parseInt(txtrequisitionline.getValue()) + a;
        } catch (WrongValueException | NumberFormatException ed) {

        }
        txtbuId.setText("");
        txtbucode.setText("");
        txtbuDesc.setText("");
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");
        txtorderunit.setText("");
        txtquntity.setValue(0);
        txtout.setText("");
//        txtrequisitiondtlid.setText("");
        txtrequisitionline.setText("" + total);
        dtneeddate.setText("");
        flag.setText("INSERT");
        dtneeddate.setConstraint("no past");
        txtquntity.setStyle("background-color:#FFFACD;text-align:right");
//           txtquntity.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        dtneeddate.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    @Listen("onClick=#btnbu")
    public void lovbu() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_code as \"Business Unit Code\",bu_description as \"Business Unit Description\",bu_id as \"bu id\" from table(pkg_tcash_lov.LovBU('','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU('','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbucode, txtbuDesc, txtbuId});
        composerLov.setHiddenColumn(new int[]{0, 3});

        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");

        composerLov.setTitle("List Of Bussiness Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_dtl_Request_production);
        btnwh.setDisabled(false);
        w.doModal();

    }

    @Listen("onOK=#txtbucode")
    public void onOkTxtSearchBu() {
        try {
            List<LovBUParam> lovBUParams = model.getLovBU(txtbucode.getText(), responsibilityId, userId);
            txtbuId.setValue(lovBUParams.get(0).getBu_id());
            txtbucode.setValue(lovBUParams.get(0).getBu_code());
            txtbuDesc.setValue(lovBUParams.get(0).getBu_description());
            btnwh.setDisabled(false);
            txtwhid.setText("");
            txtwhcode.setText("");
            txtwhdesc.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            lovbu();
            btnwh.setDisabled(false);
        }
    }

    @Listen("onClick=#btnitem")
    public void lovbitem() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, Item_Id as \"item id\",Item_code as \"Item Code\",Item_Description as \"Item Description\",Unit as\"Unit\" from table(pkg_tcash_lov.LovItem(''))where (upper(Item_code) like upper('?') or upper(Item_Description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovItem(''))Where Item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc, txtorderunit});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("550px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_dtl_Request_production);
        w.doModal();

    }

    @Listen("onClick=#btnwh")
    public void lovwh() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Warehouse Id\",wh_code as \"Warehouse Code\",wh_description as \"Warehouse Description\" from table(pkg_tcash_lov.LovWh('','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))"
                + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWh('','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode, txtwhdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WareHouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_dtl_Request_production);
        w.doModal();

    }

    @Listen(Events.ON_OK + "= #txtitemcode")
    public void onOkTxtSearchItem() {
        try {
            List<LovItemParam> lovItemParams = model.getLovItem(txtitemcode.getText());
            txtitemid.setValue(lovItemParams.get(0).getItemId());
            txtitemcode.setValue(lovItemParams.get(0).getItemcode());
            txtitemdesc.setValue(lovItemParams.get(0).getItemDescription());
        } catch (Exception e) {
            e.printStackTrace();
            lovbitem();
        }
    }

    @Listen(Events.ON_OK + "= #txtwhcode")
    public void onOkTxtSearchWh() {
        try {
            List<LovWhParam> getLovWhParams = model.getLovWh(txtwhcode.getText(), txtbuId.getText(), userId, responsibilityId);
            txtwhid.setValue(getLovWhParams.get(0).getWhid());
            txtwhcode.setValue(getLovWhParams.get(0).getWhcode());
            txtwhdesc.setValue(getLovWhParams.get(0).getWhdescription());
        } catch (Exception e) {
            e.printStackTrace();
            lovwh();
        }
    }

    @Listen("onClick=#save")
    public void save1() {

        if (flag.getText().equals("INSERT")) {
            Messagebox.show("Are you sure want to Save?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                
                                 ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListRequestProduction.zul");
                                cf.setFunctionName("SAVE");
                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                if (oe.getOutError() == 0) {
                                    
                                qty = String.valueOf(txtquntity.getValue());
                                RpListDtlParam rpListDtlParam = new RpListDtlParam();
                                String DateFrom = "";
                                if (dtneeddate.getText().isEmpty()) {
                                    rpListDtlParam.setRequireddate("");
                                } else {
                                    DateFrom = dt.format(dtneeddate.getValue());
                                    rpListDtlParam.setRequireddate(DateFrom);
                                }
                                Map<String, Object> map = model.doRpSaveDtl(flag.getText(),
                                        txtreqid.getValue(),
                                        "",
                                        "0",
                                        txtposition.getText(),
                                        txtitemid.getValue(),
                                        txtitemcode.getValue(),
                                        txtbuId.getValue(),
                                        txtbucode.getValue(),
                                        txtwhid.getValue(),
                                        txtwhcode.getValue(),
                                        txtorderunit.getText(),
                                        txtquntity.getValue().toString(),
                                        DateFrom,
                                        userId);

                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                    parentControllerq.requery();
                                    parentControllerq.selectlisthdr();
                                    flag.setText("UPDATE");
//                                        txtquntity.setStyle("text-align:right");
//                                        txtquntity.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//                                        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//                                        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//                                        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//                                        dtneeddate.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                                
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }

                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );

        } else {
            String DateFrom = "";
            RpListDtlParam rpListDtlParam = new RpListDtlParam();
            if (dtneeddate.getText().isEmpty()) {
                rpListDtlParam.setRequireddate("");
            } else {
                DateFrom = dt.format(dtneeddate.getValue());
                rpListDtlParam.setRequireddate(DateFrom);
            }

            Map<String, Object> map = model.doRpSaveDtl(flag.getText(), txtrequisitionid.getValue(), txtregno.getText(), "1", txtrequisitionline.getText(), txtitemid.getValue(), txtitemcode.getValue(),
                    txtbuId.getValue(), txtbucode.getValue(), txtwhid.getValue(), txtwhcode.getValue(),
                    txtorderunit.getText(), txtquntity.getValue().toString(), DateFrom, userId);
            if (txtitemid.getText().equals("")) {
                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (txtbuId.getText().equals("")) {
                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                if (map.get("OutError").equals(0)) {
                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                    parentControllerq.requery();
                    parentControllerq.selectlisthdr();
                } else {
                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                }
            }

        }

    }

    private void readonly() {
        txtbucode.setReadonly(true);
        txtbuDesc.setReadonly(true);
        txtitemcode.setReadonly(true);
        txtitemdesc.setReadonly(true);
        txtwhcode.setReadonly(true);
        txtwhdesc.setReadonly(true);
        dtneeddate.setDisabled(true);
        txtwhcode.setReadonly(true);
        txtwhdesc.setReadonly(true);
        txtquntity.setReadonly(true);
        txtorderunit.setReadonly(true);

//        newrecord.setDisabled(true);
//        save.setDisabled(true);
    }

    private void disableALL() {
        btnitem.setDisabled(true);
        btnbu.setDisabled(true);
        btnwh.setDisabled(true);
        newrecord.setDisabled(true);
    }

}
