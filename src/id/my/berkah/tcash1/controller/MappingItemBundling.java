/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListDTLMappingBDL;
import id.my.berkah.tcash1.model.ListHDRMappingBDL;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.IDefines;
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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class MappingItemBundling extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_map_item;

    @Wire
    Textbox txtitemId, txtitemcode, txtitemDesc, txtUOM, flag, txtitembndlid, txtid;

    @Wire
    Listbox listbox;

    @Wire
    Button btnitem;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#win_map_item")
    public void onCreateWindow() {
        setColors();
        if (txtitemId.getText().isEmpty()) {
            flag.setText("insert");
        } else {
            flag.setText("update");
            whenUpdate();
        }

        setRender();
        refreshDetail();

    }

    @Listen("onClick=#btnadd")
    public void addRecord() {

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListMappingItemBundling.zul");
        cf.setFunctionName("ADD");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            if (txtitemId.getText().isEmpty()) {
                Messagebox.show("Please select Item for mapping", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("bdlId", txtitemId.getText());
            Window w = (Window) Executions.createComponents("/Tcash/CompoisitionMappingItem.zul", null, map);
            w.setAttribute("parentCTRL", this);
            w.doModal();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#close")
    public void closeWindowMapping() {
        Messagebox.show("Are you sure want to close?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            win_map_item.detach();
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
    }

    @Listen("onClick=#new")
    public void newRecord() {
        txtitemId.setText("");
        txtitemcode.setText("");
        txtitemDesc.setText("");
        txtUOM.setText("");
        listbox.getItems().clear();
        setColors();
    }

    @Listen("onClick=#btnitem")
    public void LovItemBunling() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\", item_description as \"Description\" ,uom as \"UOM\" from table(pkg_tcash_mapping_bdl.LovItemBundling(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_mapping_bdl.LovItemBundling(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtitemId, txtitemcode, txtitemDesc, txtUOM});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_map_item);
        w.doModal();

    }

    @Listen("onFocus=#btnitem")
    public void autoRefreshByButton() {
        selectHDR();
        refreshDetail();
    }

    public void refreshDetail() {
        if (!txtitemId.getText().isEmpty()) {
            List<ListDTLMappingBDL> listdata = model.getListDetailBdl(txtitemId.getText());
            listbox.setModel(new ListModelList<ListDTLMappingBDL>(listdata));

            if (!listbox.getItems().isEmpty()) {
                flag.setText("update");
                txtUOM.setReadonly(true);
            } else {
            }
        } else {

        }
    }

    public void setRender() {
        listbox.setItemRenderer(new ListitemRenderer<ListDTLMappingBDL>() {

            @Override
            public void render(Listitem lstm, ListDTLMappingBDL t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, t.getId(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_code(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_description(), "left");

                Listcell lc = new Listcell();
//                Listcell lcLAR = new Listcell();
                Checkbox cb = new Checkbox();
//                Checkbox cbLAR = new Checkbox();
                cb.setDisabled(true);
//                cbLAR.setDisabled(true);
                if (t.getDefault_sn().equals("0")) {
                    cb.setChecked(false);
                } else {
                    cb.setChecked(true);
                }

                lc.appendChild(cb);
//                lcLAR.appendChild(cbLAR);
                lstm.appendChild(lc);
//                lstm.appendChild(lcLAR);

                CHelper.Listbox_addLabel(lstm, t.getCreated_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreated_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getExpiry_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getExpiry_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getUom(), "left");
                CHelper.Listbox_addLabel(lstm, t.getComposition(), "left");
//                CHelper.Listbox_addLabel(lstm, t.getDefault_sn(), "left");

            }
        });
    }

    public void addNewRecord() {
        btnitem.setDisabled(false);
        txtitemId.setText("");
        txtitembndlid.setText("");
        txtitemcode.setText("");
        txtitemDesc.setText("");
        txtUOM.setText("");
        txtUOM.setDisabled(false);
        listbox.getItems().clear();
    }

    @Listen("onClick=#newrecord")
    public void buttonNew() {
        addNewRecord();
        setColors();
    }

    void whenUpdate() {
        txtUOM.setReadonly(true);
        btnitem.setDisabled(true);
    }

    void doubleClickLisbox() {
        int index = listbox.getSelectedIndex();
        if (index > -1) {
            ListDTLMappingBDL selected = (ListDTLMappingBDL) listbox.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("detail", selected);
            map.put("bdlId", txtitemId.getText());
            Window w = (Window) Executions.createComponents("/Tcash/CompoisitionMappingItem.zul", null, map);
            w.setAttribute("parentCTRL", this);
            w.doModal();
        } else {
            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
        }

    }

    @Listen(Events.ON_DOUBLE_CLICK + "=#listbox")
    public void doubleClick() {
        doubleClickLisbox();
    }

    void selectHDR() {
        try {
            if (!txtitemId.getText().isEmpty()) {
                List<ListHDRMappingBDL> list = model.getListHeaderbdl(txtitemId.getText());
                txtitembndlid.setText(list.get(0).getItem_bundling_id());
                txtitemId.setText(list.get(0).getItem_bundling_id());
                txtitemcode.setText(list.get(0).getItem_code());
                txtitemDesc.setText(list.get(0).getItem_description());
                txtUOM.setText(list.get(0).getUom());
            } else {
            }

        } catch (Exception e) {
        }

    }

    public void setColors() {
        if (txtitemId.getText().equals("")) {
            txtitemcode.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//            txtitemDesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//             txtUOM.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        } else {
            txtitemcode.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//            txtitemDesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//            txtUOM.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        }
    }

    @Listen("onClick=#btnexp")
    public void buttonExpire() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListMappingItemBundling.zul");
        cf.setFunctionName("TERMINATE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            if (txtitemId.getText().equals("")) {
                Messagebox.show("Please select item for expire", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

            if (listbox.getItems().isEmpty()) {
                Messagebox.show("This Mapping has not been saved\nCannot Terminate", "Mapping Item Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

            Messagebox.show("Are you sure want to terminate this mapping item?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {

                                Map<String, Object> map = model.doExpireMappingbdlAll(txtitemId.getText(), userId);
                                if (map.get("OutError").equals(0)) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Mapping Bundling", Messagebox.OK, Messagebox.INFORMATION);
                                    selectHDR();
                                    refreshDetail();
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
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

    @Listen("onClick=#btndelete")
    public void deleteLine() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListMappingItemBundling.zul");
        cf.setFunctionName("DELETE");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            if (listbox.getSelectedItems().isEmpty()) {
                Messagebox.show("No Record selected", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {

                Messagebox.show("Are you sure want to delete this mapping item?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            @Override
                            public void onEvent(Event e) {
                                if (Messagebox.ON_OK.equals(e.getName())) {
                                    Map<String, Object> map = model.doDeleteMappingbdl(txtid.getText());
                                    if (map.get("OutError").equals(0)) {
                                        refreshDetail();
                                        selectHDR();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

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

    @Listen("onSelect = #listbox")
    public void selectListboxDtlItem() {
        int index = listbox.getSelectedIndex();
        if (index > -1) {
            ListDTLMappingBDL selected = (ListDTLMappingBDL) listbox.getModel().getElementAt(index);
            txtid.setValue(selected.getId());
        } else {
            Messagebox.show("No Record Selected", "Mapping Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

}
