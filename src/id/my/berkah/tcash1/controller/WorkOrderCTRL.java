/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoListDtlParam;
import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.util.IDefines.COLORS;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.JRreportWindow;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class WorkOrderCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_Work_Order;

    @Wire
    Textbox txtbuId, txtbucode, txtbuDesc,
            txtsupId, txtsupcode, txtsupdesc,
            txtssupsiteId, txtssupsite, txtsupsitedesc,
            txtSupConNo, txtSupConNoDesc, txtTermId, txtTermDesc,
            txtPoId, txtStatus, txtremark, txtWoNo,
            txtCreatedBy, txtReleaseBy, txtApproveBy, txtCancelBy,
            txtCreatedDate, txtReleaseDate, txtApproveDate, txtCancelDate, txtOrderType,
            txtartid, txtartcode, txtartdesc, txtpoline, txtqtytem, txtitemId,txtLocDesc;

    @Wire
    Listbox listbox;
    @Wire
    Datebox txtprodate;
    @Wire
    Button btnLovBu, btnsup, btnsupsite, btnSupConNo;
    ModelTcashCTLR model = new ModelTcashCTLR();
    Listitem selectRow = new Listitem();
    String poLine = "";
    String itemId = "";
    String qtyTemp = "";
    String status = "";
//    Listcell listcell =new Listcell();

    @Wire
    Button Upload, ViewgenSn,btnLoc;
    
    @Wire
    Combobox cmbOrder;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        combobox();

        if (!txtPoId.getValue().equals("") && !txtStatus.getText().equalsIgnoreCase("DRAFT")) {
            headerList();
            refreshDetail();
            validateForm();
            txtprodate.setConstraint("");
            colorDefault();
        } else {
            txtprodate.setConstraint("no past");
            colorMandatory();
            txtStatus.setText("");
            listbox.setSizedByContent(true);
        }
        if (txtStatus.getText().equalsIgnoreCase("DRAFT")) {
            colorMandatory();
            txtprodate.setConstraint("no past");
        }
    }

    public void validateForm() {
        if (txtStatus.getText().equalsIgnoreCase("") || txtStatus.getText().equalsIgnoreCase("DRAFT")) {
            btnLovBu.setDisabled(false);
            btnsup.setDisabled(false);
            btnsupsite.setDisabled(false);
            btnSupConNo.setDisabled(false);
            txtprodate.setReadonly(false);
            txtprodate.setDisabled(false);
//            txtprodate.setConstraint("no past");
            txtremark.setDisabled(false);
            cmbOrder.setReadonly(true);
            colorMandatory();
            if (!listbox.getItems().isEmpty()) {
                btnLovBu.setDisabled(true);
                btnsup.setDisabled(true);
                btnsupsite.setDisabled(true);
                btnSupConNo.setDisabled(true);
                txtprodate.setReadonly(true);
//                txtprodate.setConstraint("no past");
                txtremark.setReadonly(true);
                cmbOrder.setReadonly(true);
            }
        } else {
            btnLovBu.setDisabled(true);
            btnsup.setDisabled(true);
            btnsupsite.setDisabled(true);
            btnSupConNo.setDisabled(true);
            txtprodate.setReadonly(true);
            txtprodate.setDisabled(true);
            txtremark.setReadonly(true);
            cmbOrder.setReadonly(true);
//            cmbOrder.setDisabled(true);
            txtprodate.setConstraint("");
            colorDefault();
        }
        if (txtWoNo.getValue().equals("")) {
            btnLovBu.setDisabled(false);
        } else {
            btnLovBu.setDisabled(true);
        }

        if (txtStatus.getText().equalsIgnoreCase("Approved")) {
            btnLovBu.setDisabled(true);
            btnsup.setDisabled(true);
            btnsupsite.setDisabled(true);
            btnSupConNo.setDisabled(true);
            txtprodate.setReadonly(true);
            txtprodate.setDisabled(true);
            cmbOrder.setReadonly(true);
            txtprodate.setConstraint("");
            txtbucode.setReadonly(true);
            txtbucode.setReadonly(true);
            txtsupcode.setReadonly(true);
            txtssupsite.setReadonly(true);
            txtremark.setReadonly(true);
            colorDefault();
        }
    }

    @Listen("onClick=#btnLovBu")
    public void lovBu() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,BU_ID as \"BU Id\",BU_CODE as \"BU Code\", BU_DESCRIPTION as \"BU Description\" from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + "))where (upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + ")) where \n"
                + "(upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbuId, txtbucode, txtbuDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});
        whenClickLOVBU();
        composerLov.setTitle("List Of BU");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();
    }

    @Listen("onClick=#btnsup")
    public void lovbtnsup() {

        txtssupsiteId.setValue("");
        txtssupsite.setValue("");
        txtsupsitedesc.setValue("");
        txtSupConNo.setValue("");
        txtSupConNoDesc.setValue("");
        txtTermId.setValue("");
        txtTermDesc.setValue("");

        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,SUPPLIER_ID as \"Supplier Id\",SUPPLIER_NUMBER as \"Supplier Code\", SUPPLIER_NAME as \"Supplier Description\" from  table(pkg_tcash_lov.LovSupplier(" + "''" + "))\n"
                + "where (upper(supplier_number) like upper('?') or upper(supplier_name) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSupplier(" + "''" + ")) where \n"
                + "(upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupId, txtsupcode, txtsupdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();
    }

    @Listen("onClick=#btnsupsite")
    public void lovbtnsupsite() {
        System.out.println(txtsupId.getValue());
        if (txtsupId.getValue().equals("")) {
            return;
        }
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,supplier_id as \"Site Id\",supplier_number as \"Site Code\", supplier_name as \"Site Description\" from  table(pkg_tcash_lov.LovSite(" + txtsupId.getValue() + "))\n"
                + "where (upper(supplier_number) like upper('?') or upper(supplier_name) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSite(" + txtsupId.getValue() + ")) where \n"
                + "(upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtssupsiteId, txtssupsite, txtsupsitedesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier Site");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();
    }

    @Listen("onClick=#btnSupConNo")
    public void lovbtnSupConNo() {
        if (txtssupsiteId.getValue().equals("")) {
            return;
        }

        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,contract_id as \"Contract Id\",contract as \"Contract\", terms_of_payment as \"Terms Of Payment\", term_name as \"Term Name\" from table(pkg_tcash_lov.LovContract(" + txtsupId.getValue() + "," + txtssupsiteId.getValue() + "))\n"
                + "where (upper(contract) like upper('?') or upper(contract) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovContract(" + txtsupId.getValue() + "," + txtssupsiteId.getValue() + ")) where \n"
                + "(upper(contract) like upper('?') or upper(contract) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px", "320px", "100px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
        composerLov.setComponentTransferData(new Textbox[]{txtSupConNo, txtSupConNoDesc, txtTermId, txtTermDesc});
        composerLov.setHiddenColumn(new int[]{0, 1, 3});

        composerLov.setTitle("List Of Contract");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();
    }

    public void headerList() {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println(txtPoId.getValue());
        if (txtPoId.getText().isEmpty()) {

        } else {
            try {
                List<ListWoParam> ListWoParam = model.selectWo(userId, responsibilityId, txtPoId.getValue());
                txtbuId.setValue(ListWoParam.get(0).getBu_id());
                txtbucode.setValue(ListWoParam.get(0).getBu_code());
                txtbuDesc.setValue(ListWoParam.get(0).getBu_description());
                txtsupId.setValue(ListWoParam.get(0).getSupplier_id());
                txtsupcode.setValue(ListWoParam.get(0).getSupplier_code());
                txtsupdesc.setValue(ListWoParam.get(0).getSupplier_name());
                txtssupsiteId.setValue(ListWoParam.get(0).getSite_id());
                txtssupsite.setValue(ListWoParam.get(0).getSite_code());
                txtsupsitedesc.setValue(ListWoParam.get(0).getSite_name());
                txtSupConNo.setValue(ListWoParam.get(0).getContract_id());
                txtSupConNoDesc.setValue(ListWoParam.get(0).getContract());
                txtTermId.setValue(ListWoParam.get(0).getTerms_of_payment_id());
                txtTermDesc.setValue(ListWoParam.get(0).getTerms_of_payment_desc());
                txtStatus.setValue(ListWoParam.get(0).getStatus_desc());
                txtOrderType.setValue(ListWoParam.get(0).getOrder_type());
                cmbOrder.setValue(ListWoParam.get(0).getOrder_type_desc());
                txtremark.setValue(ListWoParam.get(0).getRemarks());
                txtWoNo.setValue(ListWoParam.get(0).getPurchase_order());
                txtCreatedBy.setValue(ListWoParam.get(0).getCreated_by_name());
                txtReleaseDate.setValue(ListWoParam.get(0).getProcessed_date());
                txtReleaseBy.setValue(ListWoParam.get(0).getProcessed_by_name());
                txtApproveBy.setValue(ListWoParam.get(0).getApproved_by_name());
                txtCancelBy.setValue(ListWoParam.get(0).getCancel_by_name());
                txtCreatedDate.setValue(ListWoParam.get(0).getCreated_date());
                txtApproveDate.setValue(ListWoParam.get(0).getApproved_date());
                txtCancelDate.setValue(ListWoParam.get(0).getCancel_date());
                date = dt.parse(ListWoParam.get(0).getOrder_date());
                System.out.println("date" + date);
                txtprodate.setValue(date);
            } catch (ParseException ex) {
                Logger.getLogger(WorkOrderCTRL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void refreshDetail() {

        listbox.getItems().clear();
        listbox.setSizedByContent(true);
        List<ListWoListDtlParam> ListWoListDtlParam = model.selectWoListDtl(txtPoId.getValue());
        try {
            poLine = ListWoListDtlParam.get(0).getPo_line();
        } catch (Exception e) {
            System.out.println("" + e);
        }
        for (ListWoListDtlParam ListWoListDtlParam1 : ListWoListDtlParam) {
            Listcell po_line_id = new Listcell();
            Listcell po_id = new Listcell();
            Listcell purchase_order = new Listcell();
            Listcell po_line = new Listcell();
            Listcell supplier = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item = new Listcell();
            Listcell item_description = new Listcell();
            Listcell purchase_unit = new Listcell();
            Listcell ordered_quantity = new Listcell();
            Listcell receiving_bu_id = new Listcell();
            Listcell receiving_bu_code = new Listcell();
            Listcell receiving_bu_desc = new Listcell();
            Listcell warehouse_id = new Listcell();
            Listcell warehouse = new Listcell();
            Listcell warehouse_desc = new Listcell();
            Listcell requisition_dtl_id = new Listcell();
            Listcell requisition_id = new Listcell();
            Listcell requisition_number = new Listcell();
            Listcell requisition_line = new Listcell();
            Listcell line_requisition_number = new Listcell();
            Listcell contract_dtl_id = new Listcell();
            Listcell contract_id = new Listcell();
            Listcell contract = new Listcell();
            Listcell contract_line_no = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell expired_date = new Listcell();
            Listcell outstanding = new Listcell();
            Listcell art_description = new Listcell();
//            Listcell artwork = new Listcell();
//            Image art =  new Image();
            final Decimalbox orderQty = new Decimalbox();

            po_id.setLabel(ListWoListDtlParam1.getPo_id());
            po_line_id.setLabel(ListWoListDtlParam1.getPo_line_id());
            purchase_order.setLabel(ListWoListDtlParam1.getPurchase_order());
            po_line.setLabel(ListWoListDtlParam1.getPo_line());
            supplier.setLabel(ListWoListDtlParam1.getSupplier());
            item_id.setLabel(ListWoListDtlParam1.getItem_id());
            item.setLabel(ListWoListDtlParam1.getItem());
            item_description.setLabel(ListWoListDtlParam1.getItem_description());
            purchase_unit.setLabel(ListWoListDtlParam1.getPurchase_unit());
            receiving_bu_id.setLabel(ListWoListDtlParam1.getReceiving_bu_id());
            receiving_bu_code.setLabel(ListWoListDtlParam1.getReceiving_bu_code());
            receiving_bu_desc.setLabel(ListWoListDtlParam1.getReceiving_bu_desc());
            warehouse_id.setLabel(ListWoListDtlParam1.getWarehouse_id());
            warehouse.setLabel(ListWoListDtlParam1.getWarehouse());
            warehouse_desc.setLabel(ListWoListDtlParam1.getWarehouse_desc());
            requisition_dtl_id.setLabel(ListWoListDtlParam1.getRequisition_dtl_id());
            requisition_id.setLabel(ListWoListDtlParam1.getRequisition_id());
            requisition_number.setLabel(ListWoListDtlParam1.getRequisition_number());
            requisition_line.setLabel(ListWoListDtlParam1.getRequisition_line());
            line_requisition_number.setLabel(ListWoListDtlParam1.getLine_requisition_number());
            contract_dtl_id.setLabel(ListWoListDtlParam1.getContract_dtl_id());
            contract_id.setLabel(ListWoListDtlParam1.getContract_id());
            contract.setLabel(ListWoListDtlParam1.getContract());
            contract_line_no.setLabel(ListWoListDtlParam1.getContract_line_no());
            regional_id.setLabel(ListWoListDtlParam1.getRegional_id());
            regional_description.setLabel(ListWoListDtlParam1.getRegional_description());
            expired_date.setLabel(ListWoListDtlParam1.getExpired_date());
            outstanding.setLabel(ListWoListDtlParam1.getOutstanding());
            art_description.setLabel(ListWoListDtlParam1.getArt_description());

            if (!txtStatus.getValue().equalsIgnoreCase("Draft")) {
                orderQty.setReadonly(true);
                orderQty.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
            } else {
                orderQty.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
            }

            outstanding.setStyle("text-align: right");
//            art.setSrc("/img/1449847171_Show.png");
//            art_description.setImage("/img/1449847171_Show.png");
            try {
                orderQty.setValue(ListWoListDtlParam1.getOrdered_quantity());
            } catch (Exception e) {
                orderQty.setValue("0");
            }
            orderQty.setWidth("110px");
            orderQty.setStyle("text-align: right");
            orderQty.setConstraint("no negative");
            ordered_quantity.appendChild(orderQty);
//            artwork.appendChild(art);

            Listitem listitem = new Listitem();
            listitem.appendChild(po_line_id);
            listitem.appendChild(po_id);
            listitem.appendChild(purchase_order);
            listitem.appendChild(po_line);
            listitem.appendChild(supplier);
            listitem.appendChild(item_id);
            listitem.appendChild(item);
            listitem.appendChild(item_description);
            listitem.appendChild(purchase_unit);
            listitem.appendChild(ordered_quantity);
            listitem.appendChild(receiving_bu_id);//10
            listitem.appendChild(receiving_bu_code);
            listitem.appendChild(receiving_bu_desc);
            listitem.appendChild(warehouse_id);
            listitem.appendChild(warehouse);
            listitem.appendChild(warehouse_desc);
            listitem.appendChild(requisition_dtl_id);
            listitem.appendChild(requisition_id);
            listitem.appendChild(requisition_number);
            listitem.appendChild(requisition_line);
            listitem.appendChild(line_requisition_number);
            listitem.appendChild(contract_dtl_id);
            listitem.appendChild(contract_id);
            listitem.appendChild(contract);
            listitem.appendChild(contract_line_no);
            listitem.appendChild(expired_date);
            listitem.appendChild(outstanding);
            listitem.appendChild(art_description);
//            listitem.appendChild(artwork);
//            listitem.appendChild(regional_id);
//            listitem.appendChild(regional_description);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    selectRow = (Listitem) t.getTarget();
                    poLine = ((Listcell) selectRow.getChildren().get(3)).getLabel();
                    itemId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
                    qtyTemp = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    txtpoline.setText(poLine);
                    txtitemId.setText(itemId);
                    txtqtytem.setText(qtyTemp);
                }
            });
            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    selectRow = (Listitem) t.getTarget();
                    poLine = ((Listcell) selectRow.getChildren().get(3)).getLabel();
                    itemId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
                    qtyTemp = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    txtpoline.setText(poLine);
                    txtitemId.setText(itemId);
                    txtqtytem.setText(qtyTemp);
                }
            });
            orderQty.addEventListener(Events.ON_CHANGE, new EventListener() {
                public void onEvent(final Event t) throws Exception {
                    if (qtyTemp.equals(orderQty.getValue().toString().replace(",", "").replace("-", ""))) {
                        return;
                    }
                    if (!txtStatus.getValue().equalsIgnoreCase("Draft")) {
                        return;
                    }
                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                    ParamCekFunction cf = new ParamCekFunction();

                    cf.setUserId(global[0]);
                    cf.setResponsibilityId(global[2]);
                    cf.setFileName("/Tcash/ListWorKOrder.zul");
                    cf.setFunctionName("CHANGE QTY");

                    //OutErrMsg oe = new OutErrMsg();
                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {
                        Messagebox.show("Are you sure want to edit this quantity?",
                                "Question", Messagebox.OK | Messagebox.CANCEL,
                                Messagebox.QUESTION,
                                new org.zkoss.zk.ui.event.EventListener() {
                                    @Override
                                    public void onEvent(Event e) {
                                        if (Messagebox.ON_OK.equals(e.getName())) {
                                            selectRow = (Listitem) t.getTarget().getParent().getParent();
//                                    System.out.println(t.getTarget());
                                            String poLineId = ((Listcell) selectRow.getChildren().get(0)).getLabel();
                                            String poId = ((Listcell) selectRow.getChildren().get(1)).getLabel();
                                            String poLine = ((Listcell) selectRow.getChildren().get(3)).getLabel();
                                            String itemId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
                                            String itemCode = ((Listcell) selectRow.getChildren().get(6)).getLabel();
                                            String ordqty = ((Listcell) selectRow.getChildren().get(9)).getLabel();
                                            String itemPrice = ((Listcell) selectRow.getChildren().get(10)).getLabel();
                                            String reqId = ((Listcell) selectRow.getChildren().get(17)).getLabel();
                                            String reqLine = ((Listcell) selectRow.getChildren().get(19)).getLabel();
                                            String contractDtlId = ((Listcell) selectRow.getChildren().get(21)).getLabel();
                                            String contractId = ((Listcell) selectRow.getChildren().get(22)).getLabel();

                                            Map<String, Object> map = model.doWoUpdateDtl(poId, poLineId, poLine, itemId, itemCode, itemPrice.toString().replace(",", ""), contractId,
                                                    contractDtlId, reqId, "", "", reqLine, orderQty.getValue().toString().replace(",", ""), userId);
                                            if (map.get("outError").toString().equals("0")) {
                                                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
                                                refreshDetail();
                                            } else {
                                                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);

                                            }

                                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                                        }
                                    }
                                }
                        );

                        refreshDetail();
                    } else {
                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
            });
            orderQty.addEventListener(Events.ON_FOCUS, new EventListener() {
                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    System.out.println(orderQty.getValue());
                    qtyTemp = orderQty.getValue().toString().replace(",", "").replace("-", "");
                }
            });
            listbox.appendChild(listitem);
        }
        validateForm();
    }

    @Listen("onClick=#btnDeleteLine")
    public void btnDeleteLineDtl() {
//        if (txtPoId.getValue().equals("")) {
//            Messagebox.show("Please save this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (!txtStatus.getText().equalsIgnoreCase("DRAFT")) {
//            Messagebox.show("This transaction status  has been " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("DELETE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent event) throws Exception {
                    if (Messagebox.Button.YES.equals(event.getButton())) {
                        deleteDtl();
                    }
                }
            };
            Messagebox.show("Are you sure want to Delete this line?", "Work Order", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO
            }, Messagebox.QUESTION, clickListener);

        } else {
            Messagebox.show(oe.getOutMessages(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void deleteDtl() {
        try {

            String poLineId = ((Listcell) selectRow.getChildren().get(0)).getLabel();
            String poId = ((Listcell) selectRow.getChildren().get(1)).getLabel();
            String poLine = ((Listcell) selectRow.getChildren().get(3)).getLabel();
            String itemId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
            String itemCode = ((Listcell) selectRow.getChildren().get(6)).getLabel();
            String orderQty = ((Decimalbox) ((Listcell) selectRow.getChildren().get(9)).getChildren().get(0)).getValue().toString().replace(",", "");
            String itemPrice = ((Listcell) selectRow.getChildren().get(10)).getLabel();
            String reqId = ((Listcell) selectRow.getChildren().get(17)).getLabel();
            String reqLine = ((Listcell) selectRow.getChildren().get(19)).getLabel();
            String contractDtlId = ((Listcell) selectRow.getChildren().get(21)).getLabel();
            String contractId = ((Listcell) selectRow.getChildren().get(22)).getLabel();
            Map<String, Object> map = model.doWoDeleteDtl(poLineId, poLine, contractId,
                    contractDtlId, reqId, reqLine, orderQty.toString().replace(",", ""), userId);
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
                refreshDetail();
                validateForm();
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Messagebox.show("Please choose detail this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void save(String Action) throws ParseException {
        String dateFromx = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateFromx = rdf.format(txtprodate.getValue());
        } catch (Exception e) {
        }

        String order = "";
        if (cmbOrder.getValue().equalsIgnoreCase("Tcash")) {
            order = "1";
        }
//        else if (cmbOrder.getValue().equals("Bundling TCash")) {
//            order = "2";
//        }

        String status = "";
        if (txtStatus.getValue().equalsIgnoreCase("Draft") || txtStatus.getValue().equals("")) {
            status = "1";
        } else if (txtStatus.getValue().equalsIgnoreCase("Released")) {
            status = "2";
        } else if (txtStatus.getValue().equalsIgnoreCase("Approved")) {
            status = "3";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD INCOMPLETE")) {
            status = "4";
        } else {
            status = "7";
        }

        Map<String, Object> map = model.doWoSaveHeader(Action,
                txtPoId.getValue(), txtbuId.getValue(), txtsupId.getValue(), txtsupcode.getValue(),
                txtssupsiteId.getValue(), txtSupConNo.getValue(), txtSupConNoDesc.getValue(), userId, order, status,
                txtremark.getValue(), txtTermId.getValue(), txtTermDesc.getValue(), dateFromx);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
            System.out.println(map.get("outPoId").toString());
            System.out.println(map.get("outPoNo").toString());
            txtPoId.setValue(map.get("outPoId").toString());
            txtWoNo.setValue(map.get("outPoNo").toString());
            headerList();
//            txtStatus.setText("Draft");s
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void addLineForm() {
        if (txtStatus.getText().equalsIgnoreCase("draft")) {
            AddWoLineCtrl1 addWoLineCtrl1 = new AddWoLineCtrl1();
            addWoLineCtrl1.setWorkOrderCTRL(this);
            addWoLineCtrl1.setInContractId(txtSupConNo.getValue());
            addWoLineCtrl1.setOrderType(txtOrderType.getValue());
            addWoLineCtrl1.setPoid(txtPoId.getValue());
            addWoLineCtrl1.setPoNo(txtWoNo.getValue());
            Map<String, Object> map = new HashMap<>();
            map.put("controller", addWoLineCtrl1);
            Window window = (Window) Executions.createComponents(
                    "/Tcash/AddLineWoTest.zul", null, map);
            window.doModal();
        } else {
            Messagebox.show("This transaction status has been " + txtStatus.getText(), "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
        }

    }

//    public void manualAllocForm() {
//        try {
//            List<Component> listCells = listbox.getSelectedItem().getChildren();
//            String poLine = ((Listcell) listCells.get(3)).getLabel();
//            String poLineId = ((Listcell) listCells.get(0)).getLabel();
//            ManualAllocationCtrlMulti ManualAllocationCtrlMulti = new ManualAllocationCtrlMulti();
//            ManualAllocationCtrlMulti.setWorkOrderCTRL(this);
//            ManualAllocationCtrlMulti.setPoId(txtPoId.getValue());
//            ManualAllocationCtrlMulti.setPoLine(poLine);
//            ManualAllocationCtrlMulti.setPoLineId(poLineId);
//            ManualAllocationCtrlMulti.setOrderType(txtOrderType.getValue());
//            Map<String, Object> map = new HashMap<>();
//            map.put("controller", ManualAllocationCtrlMulti);
//            Window window = (Window) Executions.createComponents(
//                    "/Tcash/ManualAlloc.zul", null, map);
//            window.doModal();
//        } catch (Exception e) {
//            Messagebox.show("Choose List First", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            e.printStackTrace();
//            return;
//        }

//    }

    public void doRelease() throws ParseException {
        if (txtStatus.getValue().equalsIgnoreCase("Draft")) {
            status = "1";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")) {
            status = "2";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")) {
            status = "3";
        } else if (txtStatus.getValue().equalsIgnoreCase("RELEASED")) {
            status = "4";
        } else if (txtStatus.getValue().equalsIgnoreCase("APPROVED")) {
            status = "5";
        } else if (txtStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")) {
            status = "6";
        } else if (txtStatus.getValue().equalsIgnoreCase("COMPLETED")) {
            status = "7";
        } else if (txtStatus.getValue().equalsIgnoreCase("REJECTED")) {
            status = "8";
        } else if (txtStatus.getValue().equalsIgnoreCase("CANCELED")) {
            status = "9";
        }
        Map<String, Object> map = model.doWoReleased(
                txtPoId.getValue(), poLine, "4", txtbuId.getValue(), userId, responsibilityId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
            headerList();
            refreshDetail();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void doSubmit() throws ParseException {
        if (txtStatus.getValue().equalsIgnoreCase("Draft")) {
            status = "1";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")) {
            status = "2";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")) {
            status = "3";
        } else if (txtStatus.getValue().equalsIgnoreCase("RELEASED")) {
            status = "4";
        } else if (txtStatus.getValue().equalsIgnoreCase("APPROVED")) {
            status = "5";
        } else if (txtStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")) {
            status = "6";
        } else if (txtStatus.getValue().equalsIgnoreCase("COMPLETED")) {
            status = "7";
        } else if (txtStatus.getValue().equalsIgnoreCase("REJECTED")) {
            status = "8";
        } else if (txtStatus.getValue().equalsIgnoreCase("CANCELED")) {
            status = "9";
        }
        Map<String, Object> map = model.doWoSubmit(
                txtPoId.getValue(), poLine, "5", userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
            headerList();
            refreshDetail();

        } else {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnaddwo")
    public void btnaddwo() {

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("ADD");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            if (txtWoNo.getValue().equals("")) {
                return;
            }

            if (txtStatus.getValue().equalsIgnoreCase("Draft")) {
                status = "1";
            } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")) {
                status = "2";
            } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")) {
                status = "3";
            } else if (txtStatus.getValue().equalsIgnoreCase("RELEASED")) {
                status = "4";
            } else if (txtStatus.getValue().equalsIgnoreCase("APPROVED")) {
                status = "5";
            } else if (txtStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")) {
                status = "6";
            } else if (txtStatus.getValue().equalsIgnoreCase("COMPLETED")) {
                status = "7";
            } else if (txtStatus.getValue().equalsIgnoreCase("REJECTED")) {
                status = "8";
            } else if (txtStatus.getValue().equalsIgnoreCase("CANCELED")) {
                status = "9";
            } else {
                status = "10";
            }
            Map<String, Object> map = model.doWoValidateAddLine(status);
            if (map.get("outError").toString().equals("0")) {
                addLineForm();
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnSave")
    public void btnsave() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtPoId.getValue() == "") {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, 0);
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListWorKOrder.zul");
                        cf.setFunctionName("SAVE");

                        //OutErrMsg oe = new OutErrMsg();
                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            save("INSERT");
                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }

                    } else {
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListWorKOrder.zul");
                        cf.setFunctionName("SAVE");

                        //OutErrMsg oe = new OutErrMsg();
                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            save("UPDATE");
                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
        validateForm();

    }

//    @Listen("onClick=#manualalloc")
//    public void btnmanualalloc() {
//        manualAllocForm();
//    }

    @Listen("onClick=#release")
    public void release() {
//
//        if (txtPoId.getValue().equals("")) {
//            Messagebox.show("Please save this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getText().equalsIgnoreCase("draft")) {
//            Messagebox.show("This transaction status  still " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getText().equalsIgnoreCase("Upload In Progress")) {
//            Messagebox.show("This transaction status  still " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtStatus.getText().equalsIgnoreCase("release")) {
//            Messagebox.show("This transaction status  has been " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getText().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status  has been " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("RELEASE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent event) throws Exception {
                    if (Messagebox.Button.YES.equals(event.getButton())) {
                        doRelease();
                    }
                }
            };
            Messagebox.show("Are you sure want to Released?", "Work Order", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO
            }, Messagebox.QUESTION, clickListener);
        }
//        else {
//            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
    }

    @Listen("onClick=#approve")
    public void approve() {
//        if (txtPoId.getValue().equals("")) {
//            Messagebox.show("Please save this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transacation first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtStatus.getValue().equalsIgnoreCase("draft")) {
//            Messagebox.show("This transaction status  still draft", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtStatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status  has been Canceled", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtStatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction status  has been Approved", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getValue().equalsIgnoreCase("Input File Complete")) {
//            Messagebox.show("This transaction status  has been Input File Completed", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getValue().equalsIgnoreCase("Completed")) {
//            Messagebox.show("This transaction status  has been Completed", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getValue().equalsIgnoreCase("Generating SN")) {
//            Messagebox.show("This transaction status is still Generating SN", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtStatus.getValue().equalsIgnoreCase("Upload Complete")) {
//            Messagebox.show("This transaction status is still Upload Complete", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("APPROVE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
                @Override
                public void onEvent(Messagebox.ClickEvent event) throws Exception {
                    if (Messagebox.Button.YES.equals(event.getButton())) {
                        doSubmit();
                    }
                }
            };
            Messagebox.show("Are you sure want to Approve?", "Work Order", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO
            }, Messagebox.QUESTION, clickListener);
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void doWoCancelCommit() throws ParseException {
        Map<String, Object> map = model.doWoCancelCommit(
                txtPoId.getValue(), userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
            headerList();
            refreshDetail();
        }
//        else {
//            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
    }

    public void cancelline() throws ParseException {
        if (txtStatus.getValue().equalsIgnoreCase("Draft")) {
            status = "1";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")) {
            status = "2";
        } else if (txtStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")) {
            status = "3";
        } else if (txtStatus.getValue().equalsIgnoreCase("RELEASED")) {
            status = "4";
        } else if (txtStatus.getValue().equalsIgnoreCase("APPROVED")) {
            status = "5";
        } else if (txtStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")) {
            status = "6";
        } else if (txtStatus.getValue().equalsIgnoreCase("COMPLETED")) {
            status = "7";
        } else if (txtStatus.getValue().equalsIgnoreCase("REJECTED")) {
            status = "8";
        } else if (txtStatus.getValue().equalsIgnoreCase("CANCELED")) {
            status = "9";
        }
        List<ListWoListDtlParam> ListWoListDtlParam = model.selectWoListDtl(txtPoId.getValue());
        try {
            ListWoListDtlParam.get(0).getPo_id();
        } catch (Exception e) {
            return;
        }

        for (ListWoListDtlParam ListWoListDtlParam1 : ListWoListDtlParam) {
            Map<String, Object> map = model.doWoCancel(
                    ListWoListDtlParam1.getPo_id(), "9",
                    ListWoListDtlParam1.getPo_line(),
                    ListWoListDtlParam1.getPo_line_id(),
                    ListWoListDtlParam1.getOrdered_quantity().toString().replace(",", ""),
                    ListWoListDtlParam1.getRequisition_id(),
                    ListWoListDtlParam1.getRequisition_line(),
                    ListWoListDtlParam1.getItem_id(),
                    ListWoListDtlParam1.getContract_id(),
                    ListWoListDtlParam1.getContract_dtl_id());
            if (map.get("outError").toString().equals("0")) {

            } else {
                Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
        doWoCancelCommit();
    }

    @Listen("onClick=#cancel")
    public void cancel() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        ParamCekFunction oe = null;

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        
        if (txtStatus.getText().equalsIgnoreCase("Released")) {
           cf.setFunctionName("CANCEL_SUBMITTED");
           oe = (ParamCekFunction) ci.getFunction(cf);
            if (oe.getOutError() != 0) {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
            
        } else if(txtStatus.getText().equalsIgnoreCase("Approved")){
           cf.setFunctionName("CANCEL_APPROVED");
           oe = (ParamCekFunction) ci.getFunction(cf);
            if (oe.getOutError() != 0) {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
            
        } else{
        cf.setFunctionName("CANCEL");
        oe = (ParamCekFunction) ci.getFunction(cf);
         if (oe.getOutError() != 0) {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        }
        
      
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtStatus.getText().equalsIgnoreCase("DRAFT")) {
                        if (listbox.getItemCount() == 0) {
                            doWoCancelCommit();
                            return;
                        } else {
                            cancelline();
                        }

                    } else {
                        doWoCancelnew();
                    }

                }
            }

        };
        Messagebox.show("Are you sure want to Cancel?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#closewo")
    public void closewo() {
        if (txtWoNo.getText().isEmpty()) {
            win_Work_Order.detach();
            return;
        }
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                }
            }
        };
        Messagebox.show("Are you sure close this transaction?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtStatus.getText().equalsIgnoreCase("Draft")) {
            Messagebox.show("Are you sure want to close?\nData has not upload yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
//                            parentController.requery();
                                win_Work_Order.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("UPLOAD IN PROGRESS")) {
            Messagebox.show("Are you sure want to close?\nUpload has not complete yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
//                            parentController.requery();
                                win_Work_Order.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("UPLOAD INCOMPLETE")) {
            Messagebox.show("Are you sure want to close?\nUpload has not complete yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
//                            parentController.requery();
                                win_Work_Order.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("RELEASED")) {
            Messagebox.show("Are you sure want to close?\nUpload has not approved yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                win_Work_Order.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("APPROVED")) {
            win_Work_Order.detach();
        } else {
            win_Work_Order.detach();
        }
    }

    public void formGENSn() {

        ListGenSNCTRL listGenSNCTRL = new ListGenSNCTRL();
        listGenSNCTRL.setWorkOrderCTRL(this);
        listGenSNCTRL.setPoId(txtPoId.getValue());
        listGenSNCTRL.setPoLine(txtpoline.getValue());
        Map<String, Object> map = new HashMap<>();
        map.put("controller", listGenSNCTRL);
        Window window = (Window) Executions.createComponents(
                "/Tcash/List_Generate_SN.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#ViewgenSn")
    public void ViewgenSn() throws ParseException {
        int index = listbox.getSelectedIndex();
        if (index > -1) {

            formGENSn();
            headerList();
        } else {
            Messagebox.show("Please choose list first", "Work order", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

    }

    public void formUpload() {
        try {
//            List<Component> listCells = listbox.getSelectedItem().getChildren();
            String poline = ((Listcell) selectRow.getChildren().get(3)).getLabel();
            String itemId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
            String orderQty = ((Decimalbox) ((Listcell) selectRow.getChildren().get(9)).getChildren().get(0)).getValue().toString().replace(",", "");

            Map<String, Object> map1 = model.doWOValidateGetSN(txtsupId.getValue(), txtsupcode.getValue(), itemId);

            int row = listbox.getSelectedIndex();
            if (map1.get("outError").toString().equals("0")) {
                Map<String, Object> map = new HashMap<>();
                //            map.put("controller", uploadParingCtrl);
                map.put("poId", txtPoId.getValue());
                map.put("poline", poline);
                map.put("supId", txtsupId.getValue());
                map.put("supCode", txtsupcode.getValue());
                map.put("itemId", itemId);
                map.put("count", orderQty);
                Window window = (Window) Executions.createComponents(
                        "/Tcash/UploadPairWO.zul", null, map);
                window.setAttribute("parentControler", this);
                window.doModal();
            } else {
                Messagebox.show(map1.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } catch (Exception e) {
            Messagebox.show("Choose List First", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            e.printStackTrace();
        }
    }

    @Listen("onClick=#Upload")
    public void uploadBundling() {
//        if (txtPoId.getValue().equals("")) {
//            Messagebox.show("Please save this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transaction first", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getValue().equalsIgnoreCase("draft") ) {
//            Messagebox.show("This transaction status  still " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtStatus.getValue().equalsIgnoreCase("released") || txtStatus.getValue().equalsIgnoreCase("approved") || txtStatus.getValue().equalsIgnoreCase("Input File Completed") || txtStatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status  has been " + txtStatus.getText(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("UPLOAD_SN");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

//        if (oe.getOutError() == 0) {
//            EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
//                @Override
//                public void onEvent(Messagebox.ClickEvent event) throws Exception {
//                    if (Messagebox.Button.YES.equals(event.getButton())) {
//                        String status = "";
//                        if (txtStatus.getValue().equals("Draft")) {
//                            status = "1";
//                        }
//                        else if (txtStatus.getValue().equals("Released")) {
//                            status = "2";
//                        }
//                        else if (txtStatus.getValue().equals("Approved")) {
//                            status = "3";
//                        }
//                        else if (txtStatus.getValue().equals("SN Paired")) {
//                            status = "4";
//                        }
//                        else if (txtStatus.getValue().equals("Ready to Delivery")) {
//                            status = "5";
//                        }
//                        else if (txtStatus.getValue().equals("Canceled")) {
//                            status = "9";
//                        }
        formUpload();
//                    }
//                }
//            };
//            Messagebox.show("Do you want to Upload pairing?", "Work Order", new Messagebox.Button[]{
//                Messagebox.Button.YES, Messagebox.Button.NO
//            }, Messagebox.QUESTION, clickListener);
////        }
//        else {
//            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
    }

    @Listen("onClick=#btnNew")
    public void newTrc() throws IOException, InterruptedException {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 0);
        txtbuId.setValue("");
        txtbucode.setValue("");
        txtbuDesc.setValue("");
        txtsupId.setValue("");
        txtsupcode.setValue("");
        txtsupdesc.setValue("");
        txtssupsiteId.setValue("");
        txtssupsite.setValue("");
        txtsupsitedesc.setValue("");
        txtSupConNo.setValue("");
        txtSupConNoDesc.setValue("");
        txtTermId.setValue("");
        txtTermDesc.setValue("");
        txtPoId.setValue("");
        txtStatus.setValue("");
        txtremark.setValue("");
        txtWoNo.setValue("");
        txtCreatedBy.setValue("");
        txtReleaseBy.setValue("");
        txtApproveBy.setValue("");
        txtCancelBy.setValue("");
        txtCreatedDate.setValue("");
        txtReleaseDate.setValue("");
        txtApproveDate.setValue("");
        txtCancelDate.setValue("");
        cmbOrder.setValue("TCASH");
        txtOrderType.setValue("");
        txtprodate.setValue(null);
        txtprodate.setWidth("210px");
        listbox.getItems().clear();
        validateForm();
        btnLovBu.setDisabled(false);
        colorMandatory();

    }

    @Listen("onClick=#printWo")
    public void printWo() throws IOException {
//        if (!txtStatus.getValue().equals("SN Generated")) {
//            if(!txtStatus.getValue().equals("Ready to Delivery")){
//                if(!txtStatus.getValue().equals("Completed")){
//                   Messagebox.show("WO status must be SN Generated,Ready to Delivery or Completed to print", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                    return; 
//                }
//            }
//            
//        }

        if (txtStatus.getText().equalsIgnoreCase("draft") || txtStatus.getText().equalsIgnoreCase("UPLOAD IN PROGRESS")
                || txtStatus.getText().equalsIgnoreCase("UPLOAD COMPLETE") || txtStatus.getText().equalsIgnoreCase("RELEASED")) {
            Messagebox.show("This transaction status  must be Approved", "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        if (txtStatus.getText().equalsIgnoreCase("canceled")) {
            Messagebox.show("This transaction status  has been Canceled", "Work Order Tcash", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String subreport = properties.getProperty("subreporthrn");
        String logo = properties.getProperty("logo_hrn");
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(this.global[0]);

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("PRINT");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            if ((!this.txtPoId.getText().equals("")) && (txtPoId.getText() != null)) {
                HashMap map = new HashMap();
                map.put("po_id", txtPoId.getText());
                map.put("SUBREPORT_DIR", subreport);
                map.put("logo", logo);
                System.out.println(map);
                JRreportWindow jRreportWindow = new JRreportWindow(this.win_Work_Order, true, map, "/reports/WO_External_tcash.jasper", "pdf", "Print Data Work Order", "Data WO");
            } else {
                Messagebox.show("Pleases save this transaction first.", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnaddart")
    public void lovArtWork() {
        if (txtStatus.getValue().equalsIgnoreCase("canceled")) {
            Messagebox.show("This transaction status  has been canceled", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtStatus.getValue().equalsIgnoreCase("Released")) {
            Messagebox.show("This transaction status  has been Released", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtStatus.getValue().equalsIgnoreCase("approved")) {
            Messagebox.show("This transaction status  has been approved", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtStatus.getValue().equalsIgnoreCase("Input File Completed")) {
            Messagebox.show("This transaction status  has been Input File Completed", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        if (txtStatus.getValue().equalsIgnoreCase("Completed")) {
            Messagebox.show("This transaction status  has been Completed", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        String lineId = "";
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            lineId = ((Listcell) listCells.get(0)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        String artWork = "";
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, art_id as \"ID\",art_code as \"Artwork Code\",art_description as \"Artwork Description\" from table(pkg_tcash_lov.LovArt(''))"
                + "where (upper(art_code) like upper('?') or upper(art_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovArt('')) where \n"
                + "(upper(art_code) like upper('?') or upper(art_description) like upper('?'))");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtartid, txtartcode, txtartdesc});
        composerLov.setHiddenColumn(new int[]{1});

        composerLov.setEventListener(new EventListener() {

            @Override
            public void onEvent(Event t) throws Exception {
                setAddwork();
            }
        });
        composerLov.setTitle("List Of Artwork");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();

    }

    public void setAddwork() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("ADD ARTWORK");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            String lineId = "";
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            lineId = ((Listcell) listCells.get(0)).getLabel();
            if (!txtartid.getValue().equals("")) {
                Map<String, Object> map1 = model.doWoSetArtWork(txtPoId.getValue(), lineId, txtartid.getValue());
                if (map1.get("outError").equals("0")) {
                } else {
                    Messagebox.show(map1.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
                }
            }
            refreshDetail();
           headerList();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#printBA")
    public void paramBA() {

        System.out.println(txtPoId.getValue());
        BAParamCTRL BAParamCTRL1 = new BAParamCTRL();
        BAParamCTRL1.setWorkOrderCTRL(this);
        BAParamCTRL1.setPoId(txtPoId.getValue());
        BAParamCTRL1.setWoNo(txtWoNo.getValue());
        Map<String, Object> map = new HashMap<>();
        map.put("controller", BAParamCTRL1);
        Window window = (Window) Executions.createComponents(
                "/Tcash/ParamBA.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#btnrefresh")
    public void refreshAll() throws ParseException {
        headerList();
        refreshDetail();

    }

    void colorMandatory() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtssupsite.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtSupConNoDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtprodate.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtremark.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//        cmbOrder.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    void colorDefault() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupdesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtssupsite.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupsitedesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtSupConNoDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtprodate.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//        cmbOrder.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtremark.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);

//        cmbOrder.setDisabled(true);
    }

    void whenClickLOVBU() {
        txtsupcode.setText("");
        txtsupdesc.setText("");
        txtssupsite.setText("");
        txtsupsitedesc.setText("");
        txtSupConNoDesc.setText("");
    }

    public void updateStatus() {
        Map<String, Object> map = model.doWoUpdateStatus(txtPoId.getValue(), "4", userId);
        if (map.get("OutError").equals(0)) {
//            Messagebox.show(map.get("OutMessages").toString());
        } else {
//            Messagebox.show(map.get("OutMessages").toString());
        }
    }

    public void combobox() {
        if (txtOrderType.getValue().equals("1")) {
            cmbOrder.setValue("TCASH");
            Upload.setDisabled(false);
            ViewgenSn.setDisabled(false);
        } else {
            cmbOrder.setValue("TCASH");
            Upload.setDisabled(true);
            Upload.setVisible(true);
            ViewgenSn.setDisabled(true);
            ViewgenSn.setVisible(true);
        }
    }

//  @Listen("onClick=#genSn")
//  public void GenerateSn(){
//    doWoCreateRangeSNInv();
//  }
//  
//      public class ShellTest {
    public void osCommand(String rw) throws java.io.IOException, java.lang.InterruptedException {
        // Get runtime
        java.lang.Runtime rt = java.lang.Runtime.getRuntime();
        // Start a new process: UNIX command ls
        java.lang.Process p = rt.exec(rw);
        // Show exit code of process
        System.out.println("Process exited with code = " + rt.toString());
    }

    public void doWoCancelnew() {
        Map<String, Object> map = model.doWoCancelnew(
                txtPoId.getValue(), userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
            headerList();
            refreshDetail();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
    
    @Listen("onSelect=#cmbOrder")
    public void comOrder(){
        if (cmbOrder.getSelectedIndex()==0) {
            btnLoc.setDisabled(true);
        } else {
             btnLoc.setDisabled(false);
        }
    }
    
    @Listen("onClick=#btnLoc")
    public void btnLovLoc(){
         HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery(" select * from table(pkg_ifm_work_order.LovLocation(1))\n"
                + "where (upper(LOCATION) like upper('?') or upper(LOCATION) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_ifm_work_orderLovLocation(1)) where \n"
                + "(upper(LOCATION) like upper('?') or upper(LOCATION) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px"});
        composerLov.setSelectedColumn(new int[]{1});
        composerLov.setComponentTransferData(new Textbox[]{txtLocDesc});
        

        composerLov.setTitle("List Of Location");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Work_Order);
        w.doModal();
       
    }
}
//}
