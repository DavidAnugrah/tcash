/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListDtlbundlingParam;
import id.my.berkah.tcash1.model.ListExportExcelParam;
import id.my.berkah.tcash1.model.ListHdrBundlingParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.IDefines;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModel;
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
public class BundlingCTRL extends SelectorComposer<Component> {

    private final String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_bundling_CTRL;

    @Wire
    Textbox txtitemid, txtitemcode, txtitemdesc, txtstatus, txtbdlid,
            txtbundlingno, txtbndlingdate, txtCreatedBy, txtReleaseBy,
            txtApproveBy, txtCancelBy, txtCreatedDate, txtRelaseDate, item, txtflag,
            txtApproveDate, txtCancelDate, txtsupplierdesc, txtsuppliercode, txtsupplierid,
            txtbuId, txtbucode, txtbuDesc, txtwipid, txtwipcode, txtwipdesc, txtwhid, txtwhcode, txtwhdesc, txtbdldtid,txtsize;

    @Wire
    Decimalbox txtqty;

    @Wire
    Listbox listbox;

    @Wire
    Button btnitem, btnSupp, btnbu, btnwip, btnwh;

    ModelTcashCTLR model = new ModelTcashCTLR();
    ListBundling parentController;
    ListModel<ListDtlbundlingParam> DtlItemModel;

    @Listen("onCreate=#win_bundling_CTRL")
    public void onCreateWindow() {
        Refresh();
        listboxDetail();
        parentController = (ListBundling) win_bundling_CTRL.getAttribute("parentController");
        if (txtstatus.getText().equalsIgnoreCase("")) {
            mandatory();
            enablebutton();
        } else if (txtstatus.getText().equalsIgnoreCase("Draft")) {
            disablebutton();
            defaultStyle();
            txtqty.setStyle("background-color:#FFFACD;text-align:right");
            txtqty.setReadonly(false);
        } else if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
            defaultStyle();
            disablebutton();
        } else if (txtstatus.getText().equalsIgnoreCase("Approved")) {
            defaultStyle();
            disablebutton();
        } else if (txtstatus.getText().equalsIgnoreCase("canceled")) {
            defaultStyle();
            disablebutton();
        } else {
            defaultStyle();
            disablebutton();
        }

    }

    @Listen("onClick=#close")
    public void btnClose() {
        win_bundling_CTRL.detach();
    }

    @Listen("onClick=#btnsave")
    public void btnSave() {
        if (txtstatus.getText().isEmpty()) {
            Messagebox.show("Are you sure want to save?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListBundling.zul");
                                cf.setFunctionName("SAVE");

                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                                if (oe.getOutError() == 0) {
                                    insert();
                                    parentController.btnrefresh();
                                    Refresh();
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else {
            Messagebox.show("Are you sure want to update?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListBundling.zul");
                                cf.setFunctionName("SAVE");

                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                                if (oe.getOutError() == 0) {
                                    update();
                                    parentController.btnrefresh();
                                    Refresh();
                                    listboxDetail();
                                    txtqty.setStyle("background-color:#FFFACD;text-align:right");
                                    txtqty.setReadonly(false);
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        }

    }

    public void insert() {
        Map<String, Object> map = model.doOnInsertHdr(txtbdlid.getText(), txtitemid.getText(), txtitemcode.getText(), txtitemdesc.getText(),
                txtbuId.getText(), txtbucode.getText(), txtbuDesc.getText(), txtwipid.getText(), txtwipcode.getText(), txtwipdesc.getText(),
                txtwhid.getText(), txtwhcode.getText(), txtwhdesc.getText(), txtqty.getText(), txtsupplierid.getText(), txtsuppliercode.getText(), txtsupplierdesc.getText(), userId);
        if (map.get("OutError").equals(0)) {
            txtbdlid.setText(map.get("OutBdlId").toString());
            Refresh();
            defaultStyle();
            disablebutton();
            txtqty.setStyle("background-color:#FFFACD;text-align:right");
            txtqty.setReadonly(false);
            saveDetail();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void update() {
        Map<String, Object> map = model.doOnUpdateHdr(txtbdlid.getText(), txtitemid.getText(), txtitemcode.getText(), txtitemdesc.getText(),
                txtqty.getText(), userId, txtsupplierid.getText(), txtsuppliercode.getText(), txtsupplierdesc.getText(), txtbuId.getText(), txtbucode.getText(), txtbuDesc.getText(), txtwipid.getText(), txtwipcode.getText(), txtwipdesc.getText(),
                txtwhid.getText(), txtwhcode.getText(), txtwhdesc.getText());
        if (map.get("OutError").equals(0)) {
            Refresh();
            listboxDetail();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick = #btnitem")
    public void lovItem() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_bundling_id as \"Id\",item_code as \"Item Code\",item_description as \"Description\" from table(pkg_tcash_bundling.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("600px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_CTRL);
        w.doModal();

    }

    @Listen("onClick = #btnbu")
    public void lovBu() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_id as \"Id\",bu_code as \"Bu Code\",bu_description as \"Description\" from table(pkg_tcash_bundling.LovBU('','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovBU('','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbuId, txtbucode, txtbuDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Bussiness Unit");
        composerLov.setWidth("600px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_CTRL);
        w.doModal();

    }

    @Listen("onClick=#btnwh")
    public void lovwh() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Warehouse Id\",wh_code as \"Warehouse Code\",wh_description as \"Warehouse Description\" from table(pkg_tcash_bundling.LovWh('','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))"
                + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovWh('','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode, txtwhdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WareHouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_CTRL);
        w.doModal();

    }

    @Listen("onClick=#btnwip")
    public void lovwipwh() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Warehouse Id\",wh_code as \"Warehouse Code\",wh_description as \"Warehouse Description\" from table(pkg_tcash_bundling.LovWhWip('','" + userId + "','" + responsibilityId + "'))"
                + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovWhWip('','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwipid, txtwipcode, txtwipdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WIP");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_CTRL);
        w.doModal();

    }

    public void Refresh() {
        if (!txtbdlid.getText().isEmpty()) {
            List<ListHdrBundlingParam> list = model.getListHdrBundling(txtbdlid.getValue(),userId,responsibilityId, null);
            txtbdlid.setText(list.get(0).getBundling_id());
            txtbundlingno.setText(list.get(0).getBundling_no());
            txtbndlingdate.setText(list.get(0).getBundling_date());
            txtitemid.setText(list.get(0).getItem_id());
            txtitemcode.setText(list.get(0).getItem_code());
            txtitemdesc.setText(list.get(0).getItem_desc());
            txtqty.setText(list.get(0).getQty());
            txtsupplierid.setText(list.get(0).getSupplier_Id());
            txtsuppliercode.setText(list.get(0).getSupplier_code());
            txtsupplierdesc.setText(list.get(0).getSupplier_name());
            txtbuId.setText(list.get(0).getBu_id());
            txtbucode.setText(list.get(0).getBu_code());
            txtbuDesc.setText(list.get(0).getBu_desc());
            txtwhid.setText(list.get(0).getWh_id());
            txtwhcode.setText(list.get(0).getWh_code());
            txtwhdesc.setText(list.get(0).getWh_desc());
            txtwipid.setText(list.get(0).getWip_id());
            txtwipcode.setText(list.get(0).getWip_code());
            txtwipdesc.setText(list.get(0).getWip_desc());
            txtstatus.setText(list.get(0).getStatus());
            txtCreatedBy.setText(list.get(0).getCreated_by());
            txtCreatedDate.setText(list.get(0).getCreated_date());
            txtReleaseBy.setText(list.get(0).getProcessed_by());
            txtRelaseDate.setText(list.get(0).getProcessed_date());
            txtApproveBy.setText(list.get(0).getApproved_by());
            txtApproveDate.setText(list.get(0).getApproved_date());
            txtCancelBy.setText(list.get(0).getCanceled_by());
            txtCancelDate.setText(list.get(0).getCanceled_date());
        }
    }

    public void mandatory() {
        txtsuppliercode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//       txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtwipcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//       txtwipdesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//       txtwhdesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtqty.setStyle("background-color:#FFFACD;text-align:right");
    }

    public void defaultStyle() {
        txtitemcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtsuppliercode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtqty.setStyle("background-color:#ffffff;text-align:right");
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtwipcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//        txtwipdesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
//        txtwhdesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);

    }

    @Listen("onClick=#newrecord")
    public void newREcord() {
        txtbdlid.setText("");
        txtbundlingno.setText("");
        txtbndlingdate.setText("");
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtqty.setText("");
        txtstatus.setText("");
        txtCreatedBy.setText("");
        txtCreatedDate.setText("");
        txtApproveBy.setText("");
        txtApproveDate.setText("");
        txtCancelBy.setText("");
        txtCancelDate.setText("");
        txtRelaseDate.setText("");
        txtReleaseBy.setText("");
        txtsupplierid.setText("");
        txtsuppliercode.setText("");
        txtsupplierdesc.setText("");
        listbox.getItems().clear();
        txtbuId.setText("");
        txtbucode.setText("");
        txtbuDesc.setText("");
        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");
        txtwipid.setText("");
        txtwipcode.setText("");
        txtwipdesc.setText("");
        mandatory();
        enablebutton();
    }

    public void saveDetail() {
        Map<String, Object> map = model.doOnInsertDtl(txtbdlid.getText(), userId);
        if (map.get("OutError").equals(0)) {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.INFORMATION);
            mandatory();
            listboxDetail();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void listboxDetail() {
        List<ListDtlbundlingParam> list = model.getListDtlBundling(txtbdlid.getText());
        listbox.setModel(new ListModelList<>(list));
        listbox.setSizedByContent(true);
//    }
//
//    public void setRender() {
        listbox.setItemRenderer(new ListitemRenderer<ListDtlbundlingParam>() {

            @Override
            public void render(Listitem lstm, ListDtlbundlingParam t, int i) throws Exception {
                new Listcell(t.getBundling_id()).setParent(lstm);
                new Listcell(t.getBundling_dtl_id()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_desc()).setParent(lstm);
                CHelper.Listbox_addLabel(lstm,t.getQty(),"right");
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getCreated_date()).setParent(lstm);
                new Listcell(t.getUpdated_by()).setParent(lstm);
                new Listcell(t.getUpdated_date()).setParent(lstm);
            }
        });
    }

    @Listen("onClick=#btnsubmit")
    public void buttonsubmit() {
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("CANCELED")) {
//            Messagebox.show("This transaction has been canceled", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("completed")) {
//            Messagebox.show("This transaction has been completed", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (listbox.getItems().isEmpty()) {
//            Messagebox.show("This transaction does not has detail", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtstatus.getText().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this bundling first ", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }

        Messagebox.show("Are you sure want to Submit this bundling?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {

                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListBundling.zul");
                            cf.setFunctionName("SUBMIT");
//    	
                            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                            if (oe.getOutError() == 0) {
                                submitStatus("2");
                                parentController.btnrefresh();
                            } else {
                                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );

    }

    @Listen("onClick=#btnapproved")
    public void buttonapprove() {
//        if (txtstatus.getText().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this bundling first ", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtstatus.getText().equalsIgnoreCase("DRAFT")) {
//            Messagebox.show("This transaction status is still Draft", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction has been approved", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtstatus.getText().equalsIgnoreCase("CANCELED")) {
//            Messagebox.show("This transaction has been canceled", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("completed")) {
//            Messagebox.show("This transaction has been completed", "Bunding", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }

        Messagebox.show("Are you sure want to Approve this bundling?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListBundling.zul");
                            cf.setFunctionName("APPROVE");
//    	
                            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                            if (oe.getOutError() == 0) {
                                submitStatus("3");
                                Refresh();
                                parentController.btnrefresh();
                            } else {
                                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );

    }

    @Listen("onClick=#btncancel")
    public void buttoncancel() {
        Messagebox.show("Are you sure want to cancel this bundling?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                             ParamCekFunction oe = null;
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListBundling.zul");
                            
                            if (txtstatus.getText().equalsIgnoreCase("Approved")) {
                                cf.setFunctionName("CANCEL_APPROVE");
                                oe = (ParamCekFunction) ci.getFunction(cf);
                               if (oe.getOutError() != 0) {
                                   Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                   return;
                               }
                            } else if(txtstatus.getText().equalsIgnoreCase("Submitted")){
                                 cf.setFunctionName("CANCEL_SUBMIT");
                                 oe = (ParamCekFunction) ci.getFunction(cf);
                               if (oe.getOutError() != 0) {
                                   Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                   return;
                               }
                            }else{
                                cf.setFunctionName("CANCEL");
                                 oe = (ParamCekFunction) ci.getFunction(cf);
                               if (oe.getOutError() != 0) {
                                   Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                   return;
                               }
                            }
                                doCanceled();
                                Refresh();
                                parentController.btnrefresh();
                          
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );

    }

    public void submitStatus(String status) {
        Map<String, Object> map = model.doUpdateStatusHdr(txtbdlid.getText(), status, userId);
        if (map.get("OutError").equals(0)) {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.INFORMATION);
            listboxDetail();
            disablebutton();
            defaultStyle();
            Refresh();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.INFORMATION);
            txtqty.setReadonly(false);
            txtqty.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        }
    }
    public void doCanceled() {
        Map<String, Object> map = model.doCanceled(txtbdlid.getText(), userId);
        if (map.get("OutError").equals(0)) {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.INFORMATION);
            listboxDetail();
//            disablebutton();
            defaultStyle();
            onCreateWindow();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.INFORMATION);
//            txtqty.setReadonly(false);
//            txtqty.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        }
    }

    @Listen("onClick = #btnSupp")
    public void lovsupplier() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Id\",supplier_number as \"Supplier Number\",supplier_name as \"Supplier Name\" from table(pkg_tcash_bundling.LovSupplier(''))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovSupplier('" + " " + "'))Where supplier_number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupplierid, txtsuppliercode, txtsupplierdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_CTRL);
        w.doModal();

    }

    public void disablebutton() {
        btnitem.setDisabled(true);
        btnSupp.setDisabled(true);
        btnbu.setDisabled(true);
        txtqty.setReadonly(true);
        btnwip.setDisabled(true);
        btnwh.setDisabled(true);
    }

    public void enablebutton() {
        btnitem.setDisabled(false);
        btnSupp.setDisabled(false);
        btnbu.setDisabled(false);
        txtqty.setReadonly(false);
        btnwip.setDisabled(false);
        btnwh.setDisabled(false);
    }

    @Listen("onClick=#btnview")
    public void ViewSNList() {
        Window w = (Window) Executions.createComponents("/Tcash/ListViewSNBundling.zul", null, null);
        w.doModal();
    }

    @Listen("onClick=#allocated")
    public void btnallocated() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundling.zul");
        cf.setFunctionName("ALLOCATE");
//    	
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
        if (oe.getOutError() == 0) {

            try {

                List<Component> list = listbox.getSelectedItem().getChildren();
                String line = ((Listcell) list.get(0)).getLabel();

                Map<String, Object> map = model.doGetItemRegional(item.getText());
                txtflag.setText(map.get("OutStatus").toString());

                int index = listbox.getSelectedIndex();
                if (txtflag.getText().equalsIgnoreCase("Y")) {
                    if (index > -1) {
                        ListDtlbundlingParam selected = (ListDtlbundlingParam) listbox.getModel().getElementAt(index);
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("detail", selected);
                        map1.put("bundlingNo", txtbundlingno.getText());
                        map1.put("whip", txtwipid.getText());
                        map1.put("status", txtstatus.getText());
                        System.out.println(map1);
                        Window w = (Window) Executions.createComponents("Tcash/BundlingAllocated.zul", null, map1);
                        w.setAttribute("parentController", this);
                        w.doModal();
                    } else {
                        Messagebox.show("No Record Selected", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                } else {
                    if (index > -1) {
                        ListDtlbundlingParam selected = (ListDtlbundlingParam) listbox.getModel().getElementAt(index);
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("dtl", selected);
                        map1.put("bundlingno", txtbundlingno.getText());
                        map1.put("wip", txtwipid.getText());
                        map1.put("status", txtstatus.getText());
                        Window w = (Window) Executions.createComponents("Tcash/BundlingAllocatedNonRegional.zul", null, map1);
                        w.setAttribute("parentController", this);
                        w.doModal();
                    }
                }

            } catch (Exception e) {
                Messagebox.show("Please Choose detail", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }

    }

    @Listen("onSelect = #listbox")
    public void selectListboxDtlItem() {
        int index = listbox.getSelectedIndex();
        if (index > -1) {
            ListDtlbundlingParam selected = (ListDtlbundlingParam) listbox.getModel().getElementAt(index);
            txtbdldtid.setValue(selected.getBundling_dtl_id());
            item.setText(selected.getItem_id());
        } else {
            Messagebox.show("No Record Selected", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void fillDataZkos(Listbox data, File file, Integer col, String InBundlingId, String InBundlingNo) {
        try {
            int s=1;
           
            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("Sheet "+s, 0);
             
//            sheet1.getSettings().setProtected(true);
            
            WritableCellFormat format = new WritableCellFormat();
            format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.getStyle(1));
            format.setBackground(Colour.GRAY_25);
            format.setVerticalAlignment(VerticalAlignment.CENTRE);
            format.setAlignment(Alignment.CENTRE);

            WritableCellFormat formatRow = new WritableCellFormat();
            formatRow.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.getStyle(1));
            formatRow.setBackground(Colour.WHITE);
            formatRow.setVerticalAlignment(VerticalAlignment.CENTRE);
            formatRow.setAlignment(Alignment.CENTRE);
            formatRow.setWrap(true);
            
            WritableCellFormat formatcell = new WritableCellFormat();
            formatcell.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.getStyle(1));
            formatcell.setBackground(Colour.WHITE);
            formatcell.setVerticalAlignment(VerticalAlignment.CENTRE);
            formatcell.setAlignment(Alignment.LEFT);
            formatcell.setWrap(true);
            
            
//            List<ListSnMapBundlingParam> result = model.getListSnMapBundling(txtbdlid.getText(), txtbundlingno.getText());
            List<ListExportExcelParam> result = model.getListExportExcel(txtbdlid.getText());
            listbox.setModel(new ListModelList<>(result));

//            int column =0;
//            
//            if (result.get(0).getSn1() !=  null) {
//                column = 1;
//            }else if(result.get(0).getSn2() !=  null){
//            column = 2;
//            }else if(result.get(0).getSn3() !=  null){
//            column = 3;
//            }else  if (result.get(0).getSn4() !=  null) {
//                column = 4;
//            }else if(result.get(0).getSn5() !=  null){
//                column = 5;
//            }
//            
//            System.out.println("colum "+ column);
            

            for (int h = 0; h <= col; h++) {
                              
                sheet1.mergeCells(0, 0, 0, 1);
                Label label= new Label(0, 0, "No",format);
                sheet1.addCell(label);
                sheet1.setColumnView(0,5);
                
                
                sheet1.mergeCells(1, 0, 1, 0);
                sheet1.setColumnView(1, 26);
                Label label1= new Label(1, 0, "SN Bundling",format);
                sheet1.addCell(label1);


                sheet1.mergeCells(2, 0, col+1, 0);
                sheet1.setColumnView(2, 26);
                Label label2= new Label(2, 0, "SN Contained",format);
                sheet1.addCell(label2); 
                
            }
            
            for (int i = 0; i < col; i++) {
                
                           
                Label column1 = new Label(0, 1, "",format);
                Label column2 = new Label(1, 1, "",format);
                Label column3 = new Label(2, 1, "",format);
                Label column4 = new Label(3, 1, "",format);
                Label column5 = new Label(4, 1, "",format);

                column1.setCellFormat(formatRow);
                column2.setCellFormat(formatRow);
                column3.setCellFormat(formatRow);
                column4.setCellFormat(formatRow);
                column5.setCellFormat(formatRow);

                sheet1.addCell(column1);
                sheet1.addCell(column2);
                sheet1.addCell(column3);
                sheet1.addCell(column4);
                sheet1.addCell(column5);

            }

//            int nomor = 1;
              
                
            
            ListModel model1 = data.getModel();
            
             
            for (int j = 0; j < model1.getSize(); j++) {            
               
             
                String rownum = result.get(j).getRow_no();
               
                String col1 = result.get(j).getSN_bdl();
                 if (result.get(j).getSN_bdl() == null || result.get(j).getSN_bdl().equals("")) {
                    col1 = "";
                  
                } else {
                    col1 = result.get(j).getSN_bdl();
                }

                String col2 = result.get(j).getSn1();
                 if (result.get(j).getSn1() == null || result.get(j).getSn1().equals("")) {
                    col2 = "";
                } else {
                    col2 = result.get(j).getSn1();
                }

                String col3 = result.get(j).getSn2();
                if (result.get(j).getSn2() == null || result.get(j).getSn2().equals("")) {
                    col3 = "";
                } else {
                    col3 = result.get(j).getSn2();
                }
                
                String col4=result.get(j).getSn3();
                if (result.get(j).getSn3() == null || result.get(j).getSn3().equals("")) {
                    col4 = "";
                } else {
                    col4 = result.get(j).getSn3();
                }
                String col5 = result.get(j).getSn4();
                if (result.get(j).getSn4() == null || result.get(j).getSn4().equals("")) {
                    col5 = "";
                } else {
                    col5 = result.get(j).getSn4();
                }
           

                Label cols0 = new Label(0, j + 1, rownum);
                Label cols1 = new Label(1, j + 1, col1);
                Label cols2 = new Label(2, j + 1, col2);
                Label cols3 = new Label(3, j + 1, col3);
                Label cols4 = new Label(4, j + 1, col4);
                Label cols5 = new Label(5, j + 1, col5);

                cols0.setCellFormat(formatRow);
                cols1.setCellFormat(formatRow);
                cols2.setCellFormat(formatRow);
                cols3.setCellFormat(formatRow);
                cols4.setCellFormat(formatRow);
                cols5.setCellFormat(formatRow);

                sheet1.addCell(cols0);
                sheet1.addCell(cols1);
                sheet1.addCell(cols2);
                sheet1.addCell(cols3);
                sheet1.addCell(cols4);
                sheet1.addCell(cols5);

                CellView cv;
                cv = sheet1.getColumnView(j);
                cv.setAutosize(true);
                
                sheet1.setColumnView(0, 5);
                sheet1.setColumnView(1, 50);
                sheet1.setColumnView(2, 50);
                sheet1.setColumnView(3, cv);
                sheet1.setColumnView(4, cv);
                sheet1.setColumnView(5, cv);
//                nomor++;

                if (sheet1.getRows() == 6000) {
                      s++;                
                    sheet1 = workbook1.createSheet("Sheet "+s, s);  
                    
                      for (int h = 0; h <= col; h++) {
                             
                sheet1.mergeCells(0, 0, 0, 1);
                Label label= new Label(0, 0, "No",format);
                sheet1.addCell(label);
                sheet1.setColumnView(0, 5);
                
                
                sheet1.mergeCells(1, 0, 1, 0);
                sheet1.setColumnView(1, 26);
                Label label1= new Label(1, 0, "SN Bundling",format);
                sheet1.addCell(label1);


                sheet1.mergeCells(2, 0, col+1, 0);
                sheet1.setColumnView(2, 26);
                Label label2= new Label(2, 0, "SN Contained",format);
                sheet1.addCell(label2); 
                
                
                
            }
            }
                
            }
            
                         
            CellView cell;
            cell = new CellView();

             String column1 = result.get(0).getSN_bdl();
             Label colss1 = new Label(1,  1, column1);
             colss1.setCellFormat(format);
             sheet1.addCell(colss1);
             cell =sheet1.getColumnView(1);
             cell.setAutosize(true);
             sheet1.setColumnView(1, 51);
             
             String column2 = result.get(0).getSn1();
             Label colss2 = new Label(2,  1, column2);
             colss2.setCellFormat(format);
             sheet1.addCell(colss2);
             cell =sheet1.getColumnView(2);
             cell.setAutosize(true);
             sheet1.setColumnView(2, 60);
             
             String column3 = result.get(0).getSn2();
             Label colss3 = new Label(3,  1, column3);
             colss3.setCellFormat(format);
             sheet1.addCell(colss3);
//             cell =sheet1.getColumnView(3);
//             cell.setAutosize(true);
//             sheet1.setColumnView(3, cell);
             
             String column4 = result.get(0).getSn3();
             Label colss4 = new Label(4,  1, column4);
             colss4.setCellFormat(format);
             sheet1.addCell(colss4);
             cell =sheet1.getColumnView(4);
             cell.setAutosize(true);
             sheet1.setColumnView(4, cell);
             
             String column5 = result.get(0).getSn4();
             Label colss5 = new Label(5,  1, column5);
             colss5.setCellFormat(format);
             sheet1.addCell(colss5);
             cell =sheet1.getColumnView(5);
             cell.setAutosize(true);
             sheet1.setColumnView(5, cell);
            
            int fontPointSize = 16;
            int rowHeight = (int) ((1.5d * fontPointSize) * 20);
            workbook1.write();
            workbook1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }

    }

    @Listen("onClick=#btnexcel")
    public void exportotExcel() throws IOException {
        if (txtstatus.getText().equalsIgnoreCase("draft")) {
            Messagebox.show("This transaction status still draft", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
            Messagebox.show("This transaction status is still  Submitted", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtstatus.getText().equalsIgnoreCase("Canceled")) {
            Messagebox.show("This transaction status has been Canceled", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
         if (txtstatus.getText().equalsIgnoreCase("N/A")) {
            Messagebox.show("This transaction status has been Canceled", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundling.zul");
        cf.setFunctionName("EXPORT");
//    	
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
        if (oe.getOutError() == 0) {
        
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String download_directory = properties.getProperty("download_directory");

//         File xlFile=new File("//home//apps//temp//"+"Contract"+"_"+contract+"_"+datefrom1+"_"+dateto1+"_"+wo1+".xls");
            File xlFile = new File(download_directory + "Bundling" + "_" + txtbdlid.getText() + txtbundlingno.getText() + ".xls");

            doEEBdlTcash(txtbdlid.getText());
             List<ListDtlbundlingParam> list = model.getListDtlBundling(txtbdlid.getText());
            txtsize.setText(String.valueOf(list.size()));
            System.out.println("size "+ txtsize.getText());
//            exportExcel(listbox, xlFile,20, txtbdlid.getText());
            fillDataZkos(listbox, xlFile,Integer.valueOf(txtsize.getValue()), txtbdlid.getText(), txtbundlingno.getText());
//         Filedownload.save(new File("//home//apps//temp//"+"Contract"+"_"+contract+"_"+datefrom1+"_"+dateto1+"_"+wo1+".xls"),null);
            Filedownload.save(new File(download_directory + "Bundling" + "_" + txtbdlid.getText() + txtbundlingno.getText() + ".xls"), null);
            listboxDetail();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void validate() {
        if (txtbuId.getText().isEmpty()) {
            Messagebox.show("Bussiness Unit cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        } else if (txtitemid.getText().isEmpty()) {
            Messagebox.show("Item cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);

        } else if (txtsupplierid.getText().isEmpty()) {
            Messagebox.show("Supplier cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        } else if (txtwipid.getText().isEmpty()) {
            Messagebox.show("WIP Warehoue cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        } else if (txtwhid.getText().isEmpty()) {
            Messagebox.show("Receiving WH cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        } else if (txtqty.getText().isEmpty()) {
            Messagebox.show("Qty cannot be empty", "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    public void doEEBdlTcash(String BundlingId) {
        Map<String, Object> map = model.doEEBdlTcash(BundlingId);
    }
    
            


}
