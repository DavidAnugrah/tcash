/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWipParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class SetupWipCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_setup_wip;

    @Wire
    Textbox txtiwipId, txtwipcode, txtwipdesc, txtcreate, txtcreateby, txtexpd, txtexpby, txtout;

    @Wire
    Button btnwip, save;

    ModelTcashCTLR model = new ModelTcashCTLR();
    ListSetupWIP parentController;

    @Listen("onCreate=#win_setup_wip")
    public void onCreatewindow() {
        parentController = (ListSetupWIP) win_setup_wip.getAttribute("parentController");
        if (txtexpd.getText().isEmpty()) {
            btnwip.setDisabled(false);
            save.setDisabled(false);
        } else {
            btnwip.setDisabled(true);
            save.setDisabled(true);
        }
    }

    @Listen("onClick=#btnwip")
    public void lovWIP() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Id\",wh_code as \"Wh Code\", wh_description as \"Description\"  from table(pkg_tcash_setup.LovWh('','','" + userId + "','" + responsibilityId + "'))where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_setup.LovWh('','','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtiwipId, txtwipcode, txtwipdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WIP Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_setup_wip);
        w.doModal();
    }

    @Listen("onClick=#newrecord")
    public void butotnNew() {
        txtiwipId.setText("");
        txtwipcode.setText("");
        txtwipdesc.setText("");
        txtcreate.setText("");
        txtcreateby.setText("");
        txtout.setText("");
        txtexpby.setText("");
        txtexpd.setText("");
        btnwip.setDisabled(false);
        save.setDisabled(false);
    }

    @Listen("onClick=#save")
    public void buttonSave() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWIPSetup.zul");
        cf.setFunctionName("SAVE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to save?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                Map<String, Object> map = model.doWipInsert(txtiwipId.getText(), txtwipcode.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString());
                                    txtout.setText(map.get("OutId").toString());
                                    parentController.buttonRefresh();
                                    getlistHDR();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString());
                                }
                                parentController.buttonRefresh();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btndelete")
    public void buttonDelete() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWIPSetup.zul");
        cf.setFunctionName("DELETE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to delete?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                Map<String, Object> map = model.doWipDelete(txtiwipId.getText());
                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString());
                                    parentController.buttonRefresh();
                                    butotnNew();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString());
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnexp")
    public void buttonExpire() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWIPSetup.zul");
        cf.setFunctionName("EXPIRE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to expire?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                Map<String, Object> map = model.doWipExpire(txtout.getText(), txtiwipId.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString());
                                    parentController.buttonRefresh();
                                    getlistHDR();
                                    btnwip.setDisabled(true);
                                    save.setDisabled(true);
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString());
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#close")
    public void buttonClose() {
        win_setup_wip.detach();
    }

    public void getlistHDR() {

        if (txtiwipId.getText() != null) {
            List<ListWipParam> list = model.getListWip(txtiwipId.getText());
            txtout.setText(list.get(0).getId());
            txtiwipId.setText(list.get(0).getWh_id());
            txtwipcode.setText(list.get(0).getWh_code());
            txtwipdesc.setText(list.get(0).getWh_desc());
            txtcreate.setText(list.get(0).getCreated_date());
            txtcreateby.setText(list.get(0).getCreated_by());
            txtexpd.setText(list.get(0).getExpired_date());
            txtexpby.setText(list.get(0).getExpired_by());
        }

    }

}
