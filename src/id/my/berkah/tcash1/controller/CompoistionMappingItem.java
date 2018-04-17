/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.IDefines;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class CompoistionMappingItem extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_add_mapping;

    @Wire
    Textbox txtitemid, txtitemcode, txtitemdesc, txtUOM, txtitembdlid, txtflag, txtid;

    @Wire
    Intbox txtcomp;

    @Wire
    Button btnitem, add;

    MappingItemBundling parentCTRL;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#win_add_mapping")
    public void oncreateWindow() {
        parentCTRL = (MappingItemBundling) win_add_mapping.getAttribute("parentCTRL");
        if (txtitemid.getText().isEmpty()) {
            txtflag.setText("insert");
        } else {
            txtflag.setText("update");
            btnitem.setDisabled(true);
        }
        setColors();
    }

    @Listen("onClick=#Close1")
    public void closeWindow() {
        win_add_mapping.detach();
    }

    @Listen("onClick=#add")
    public void Buttonsave() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListMappingItemBundling.zul");
        cf.setFunctionName("ADD");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            if (txtitemid.getText().isEmpty()) {
                Messagebox.show("Item can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            if (txtUOM.getText().isEmpty()) {
                Messagebox.show("UOM can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            if (txtcomp.getText().isEmpty()) {
                Messagebox.show("Composition can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

            if (txtflag.getText().equalsIgnoreCase("insert")) {
                Messagebox.show("Are you sure want to add this mapping item?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(Event e) {
                                if (Messagebox.ON_OK.equals(e.getName())) {
                                    Map<String, Object> map = model.doInsertMappingbdl(txtitembdlid.getText(), txtitemid.getText(), txtUOM.getText(), txtcomp.getText(), userId);
                                    if (map.get("err").equals(0)) {
                                        txtitembdlid.setText(map.get("bundleid").toString());
                                        parentCTRL.txtitembndlid.setText(map.get("bundleid").toString());
                                        Messagebox.show(map.get("msg").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.INFORMATION);
                                        setColors();
                                        parentCTRL.refreshDetail();
                                        win_add_mapping.detach();
                                    } else {
                                        Messagebox.show(map.get("msg").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                                }

                            }

                        }
                );
            } else {
                Messagebox.show("Are you sure want to update this mapping item?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(Event e) {
                                if (Messagebox.ON_OK.equals(e.getName())) {
                                    Map<String, Object> map = model.doModifyMappingbdl(txtid.getText(), txtitemid.getText(), txtUOM.getText(), txtcomp.getText(), userId);
                                    if (map.get("err").equals(0)) {
                                        Messagebox.show(map.get("msg").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.INFORMATION);
                                    } else {
                                        Messagebox.show(map.get("msg").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                    parentCTRL.refreshDetail();
                                    win_add_mapping.detach();
                                } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                                }

                            }

                        }
                );

            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnitem")
    public void LOVItem() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\", item_description as \"Description\" ,uom as \"UOM\" from table(pkg_tcash_mapping_bdl.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_mapping_bdl.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc, txtUOM});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item Bundling");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_add_mapping);
        w.doModal();
    }

    @Listen("onClick=#btnexp")
    public void deleteRecord() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListMappingItemBundling.zul");
        cf.setFunctionName("TERMINATE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            if (txtitemid.getText().isEmpty()) {
                Messagebox.show("Item can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            Messagebox.show("Are you sure want to terminate this mapping item?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                Map<String, Object> map = model.doExpireMappingbdl(txtid.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.INFORMATION);
                                    parentCTRL.refreshDetail();
                                    win_add_mapping.detach();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "Mapping Item Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
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

    @Listen("onClick=#defaultbtn")
    public void setDefault() {
        if (txtitemid.getText().isEmpty()) {
            Messagebox.show("Item can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        Messagebox.show("Are you sure want to set this item as default SN?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            Map<String, Object> map = model.doSetDefaultSN(txtid.getText(), txtitembdlid.getText());
                            if (map.get("OutError").equals(0)) {
                                Messagebox.show(map.get("OutMessages").toString(), "Composition Item", Messagebox.OK, Messagebox.INFORMATION);
                                parentCTRL.refreshDetail();
                            } else {
                                Messagebox.show(map.get("OutMessages").toString(), "Composition Item", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
    }

    public void setColors() {
        if (txtflag.getText().equalsIgnoreCase("insert")) {
            txtitemcode.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
            txtitemdesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//        txtUOM.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
            txtcomp.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        } else {
            txtitemcode.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            txtitemdesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            txtUOM.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//            txtcomp.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        }

    }

    @Listen("onClick=#new")
    public void buttonnew() {
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtUOM.setText("");
        txtcomp.setText("1");
        txtflag.setText("insert");
        btnitem.setDisabled(false);
        add.setLabel("Save");
//        add.setVisible(true);
        setColors();
    }

//    @Listen("onClick=#btnlar")
//    public void setLAR() {
//        if (txtitemid.getText().isEmpty()) {
//            Messagebox.show("Item can not be null", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        Messagebox.show("Are you sure want to set LAR for this item?",
//                "Question", Messagebox.OK | Messagebox.CANCEL,
//                Messagebox.QUESTION,
//                new org.zkoss.zk.ui.event.EventListener() {
//                    @Override
//                    public void onEvent(Event e) {
//                        if (Messagebox.ON_OK.equals(e.getName())) {
//
//                            Map<String, Object> map = model.doSetLarType(txtid.getText(), txtitembdlid.getText());
//                            if (map.get("OutError").equals(0)) {
//                                Messagebox.show(map.get("OutMessages").toString(), "Composition Item", Messagebox.OK, Messagebox.INFORMATION);
//                                parentCTRL.refreshDetail();
//                            } else {
//                                    Messagebox.show(map.get("OutMessages").toString(), "Composition Item", Messagebox.OK, Messagebox.EXCLAMATION);
//                                }
//                            
//                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
//
//                        }
//                    }
//                }
//        );
//
//    }
}
