package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.GdListHdrParam;
import id.my.berkah.tcash1.model.ListGdDtl;
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Azec
 */
public class GoodDeliveryCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Textbox txtbuId, txtbuDesc, txtbucode, txtwhid, txtwhcode, txtwhdesc, txtsupid, txtsupcode, txtsupdesc,
            txtgooddeliverynocode, txtexpeditioncode, txtexpeditiondesc, txtsupliersiteid, txtsupliersitecode, txtsupliersitedesc,
            txtstatus, txtWO, txtremarks, txtamount, txtcreationdate, txtcreationby, txtsubmitedby, txtsubmiteddate, txtcanceledDate, txtcanceledby,
            txtapprovedate, txtapproveddby, flag, txtsuppdeliveryid, txtpoid, txtpo, txtsuppdeliveryno, txtsuppdeliverydate, txtstatusid;
    @Wire
    Datebox txtarivalfrom, txtarivalto;
    @Wire
    Window win_Good_delivery, win_GD_detail;
    @Wire
    Button btnbu, btnwh, btnsup, btnsite, btnwo, btnexpediton;
    @Wire
    Listbox listbox;
    ModelTcashCTLR model = new ModelTcashCTLR();
    public Listitem selectRow = new Listitem();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        if (txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
            flag.setText("INSERT");
            txtstatusid.setText("");
//            txtarivalfrom.setValue(cal.getTime());
//            txtarivalto.setValue(cal.getTime());
            colorMandatory();
        } else {
            flag.setText("UPDATE");
            selectlisthdr();
            refreshDtl();
            validateForm();
            colorDefault();
        }
        if (txtstatusid.getText().equalsIgnoreCase("1")) {
            txtstatus.setText("Draft");
        }

    }

    public void validateForm() {
        if (listbox.getItemCount() == 0) {
            btnbu.setDisabled(true);
            btnwh.setDisabled(false);
            btnsup.setDisabled(false);
            btnsite.setDisabled(false);
            btnwo.setDisabled(false);
            txtarivalfrom.setDisabled(false);
            txtarivalto.setDisabled(false);
            btnexpediton.setDisabled(false);
        } else {
            btnbu.setDisabled(true);
            btnwh.setDisabled(true);
            btnsup.setDisabled(true);
            btnsite.setDisabled(true);
            btnwo.setDisabled(true);
            txtarivalfrom.setDisabled(true);
            txtarivalto.setDisabled(true);
            btnexpediton.setDisabled(true);
        }
        System.out.println("suppdelivno" + txtsuppdeliveryid.getValue());
        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
            btnbu.setDisabled(false);
        } else {
            btnbu.setDisabled(true);
        }
    }

    @Listen("onClick=#btnbu")
    public void lovbu() {
        txtwhid.setValue("");
        txtwhcode.setValue("");
        txtwhdesc.setValue("");
        txtsupid.setValue("");
        txtsupcode.setValue("");
        txtsupdesc.setValue("");
        txtpoid.setValue("");
        txtWO.setValue("");
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_id as \"Bu id\",bu_code as \"Bussiness Code\",bu_description as \"Bussiness Unit Description\" from table(pkg_tcash_lov.LovBU('" + "" + "','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU('" + " " + "','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbuId, txtbucode, txtbuDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Bussiness Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        btnwh.setDisabled(false);
        w.setParent(win_Good_delivery);
        w.doModal();

    }

//        @Listen(Events.ON_OK + "= #txtbucode")
//    public void onOkTxtSearchBu() {
//        try {
//      List<LovBUParam> lovBUParams = model.getLovBU(txtbucode.getText(),responsibilityId, userId);
//          txtbuId.setValue(lovBUParams.get(0).getBu_id());
//          txtbucode.setValue(lovBUParams.get(0).getBu_code());
//          txtbuDesc.setValue(lovBUParams.get(0).getBu_description());
//          } catch (Exception e) {
//            e.printStackTrace();
//            lovbu();
//            btnwh.setDisabled(false);
//        }
//    }
    @Listen("onClick=#btnwh")
    public void lovwh() {
        txtsupid.setValue("");
        txtsupcode.setValue("");
        txtsupdesc.setValue("");
        txtpoid.setValue("");
        txtWO.setValue("");
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"WareHouse Id\",wh_code as \"WareHouse Code\",wh_description as \"WareHouse Description\" from table(pkg_tcash_lov.LovWh('" + "" + "','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))"
                + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWh('" + " " + "','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode, txtwhdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WareHouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_delivery);
        w.doModal();
    }

//      @Listen(Events.ON_OK + "= #txtwhcode")
//    public void onOkTxtSearchWh() {
//        try {
//    List<LovWhParam> getLovWhParams= model.getLovWh(txtwhid.getText(),txtbuId.getText(),userId,responsibilityId);
//          txtwhid.setValue(getLovWhParams.get(0).getWhid());
//          txtwhcode.setValue(getLovWhParams.get(0).getWhcode());
//          txtwhdesc.setValue(getLovWhParams.get(0).getWhdescription());
//          } catch (Exception e) {
//            e.printStackTrace();
//            lovwh();
//        }
//    }
    @Listen("onClick=#btnsup")
    public void lovsupplier() {
        txtpoid.setValue("");
        txtWO.setValue("");
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, Supplier_Id as \"Supplier id\",Supplier_Number as \"Supplier Number\",Supplier_Name as \"Supplier Name\" from table(pkg_tcash_lov.LovSupplierGD('" + "" + "'))where (upper(Supplier_Number) like upper('?') or upper(Supplier_Name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSupplierGD('" + " " + "'))Where Supplier_Number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupid, txtsupcode, txtsupdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        btnwh.setDisabled(false);
        w.setParent(win_Good_delivery);
        w.doModal();

    }

    @Listen("onClick=#btnsite")
    public void lovsuppliersite() {
        txtpoid.setValue("");
        txtWO.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Supplier id\",supplier_number as \"Supplier Site Code\",supplier_name as \"Supplier Site Name\" from table(pkg_tcash_lov.LovSite('" + txtsupid.getText() + "'))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSite('" + txtsupid.getText() + "'))Where supplier_number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupliersiteid, txtsupliersitecode, txtsupliersitedesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier Site");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_delivery);
        w.doModal();

    }

//        @Listen("onClick=#btnselcetwo")
//        public void selectWO(){
//            Window window = (Window)Executions.createComponents("/Tcash/ListWoItem.zul", null,null);
//        window.doModal();
//        }
    @Listen("onClick=#btnwo")
    public void selectWO() {
        System.out.println(txtwhid.getValue());
        System.out.println(txtsupid.getValue());
        System.out.println(txtsupliersiteid.getValue());
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, po_id as \"Purchase Id\",purchase_order as \"Work order\" from table(pkg_tcash_lov.LovPoGD('" + txtwhid.getText() + "','" + txtsupid.getText() + "','" + txtsupliersiteid.getText() + "','" + txtpoid.getText() + "'))where (upper(po_id) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovPoGD('" + txtwhid.getText() + "','" + txtsupid.getText() + "','" + txtsupliersiteid.getText() + "','" + txtpoid.getText() + "'))Where po_id LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtpoid, txtWO});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Work Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_delivery);
        w.doModal();
    }

    @Listen("onClick=#btnexpediton")
    public void lovexpedition() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, forwarding_agent as \"Forwading Agent\",description as \"Description\" from table(pkg_tcash_lov.LovForwardAgent('" + "" + "'))where (upper(forwarding_agent) like upper('?') or upper(description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovForwardAgent('" + "" + "'))Where forwarding_agent LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtexpeditioncode, txtexpeditiondesc});
        composerLov.setHiddenColumn(new int[]{0});

        composerLov.setTitle("List Of Expedition");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_delivery);
        w.doModal();
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtgooddeliverynocode.getText().isEmpty()) {
            win_Good_delivery.detach();
            return;
        }
        if (txtstatus.getText().equalsIgnoreCase("Draft")) {
            Messagebox.show("Are you sure want to close?\nData has not Submited yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                win_Good_delivery.detach();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtstatus.getText().equalsIgnoreCase("Submited")) {
            Messagebox.show("Are you sure want to close?\nData has not Approved yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                win_Good_delivery.detach();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {

                            }
                        }
                    }
            );

        } else {
            win_Good_delivery.detach();
        }

    }

    public void selectlisthdr() {
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dte = new SimpleDateFormat("dd-MM-yyyy");
        if (!txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
            List<GdListHdrParam> GdListHdrParam = model.selectGdListHdr(txtsuppdeliveryid.getValue());
            txtbuId.setValue(GdListHdrParam.get(0).getBuid());
            txtbucode.setValue(GdListHdrParam.get(0).getBucode());
            txtbuDesc.setValue(GdListHdrParam.get(0).getBudescription());
            txtgooddeliverynocode.setValue(GdListHdrParam.get(0).getSuppdeliveryno());
            txtwhid.setValue(GdListHdrParam.get(0).getWhid());
            txtwhcode.setValue(GdListHdrParam.get(0).getWhcode());
            txtwhdesc.setValue(GdListHdrParam.get(0).getWhdescription());
            txtsupid.setValue(GdListHdrParam.get(0).getSupplierid());
            txtsupcode.setValue(GdListHdrParam.get(0).getSuppliernumber());
            txtsupdesc.setValue(GdListHdrParam.get(0).getSuppliername());
            txtexpeditioncode.setValue(GdListHdrParam.get(0).getForwardingagent());
            txtexpeditiondesc.setValue(GdListHdrParam.get(0).getAgentname());
            txtsupliersiteid.setValue(GdListHdrParam.get(0).getSiteid());
            txtsupliersitecode.setValue(GdListHdrParam.get(0).getSitecode());
            txtsupliersitedesc.setValue(GdListHdrParam.get(0).getSitename());
            txtpoid.setValue(GdListHdrParam.get(0).getPoid());
            txtWO.setValue(GdListHdrParam.get(0).getPurchaseorder());
            txtremarks.setValue(GdListHdrParam.get(0).getSuppdeliveryremark());
            txtcreationdate.setValue(GdListHdrParam.get(0).getCreateddate());
            txtcreationby.setValue(GdListHdrParam.get(0).getCreatedbyname());
            txtsubmitedby.setValue(GdListHdrParam.get(0).getSubmittedby());
            txtsubmiteddate.setValue(GdListHdrParam.get(0).getSubmitteddate());
            txtapproveddby.setValue(GdListHdrParam.get(0).getApprovedbyname());
            txtapprovedate.setValue(GdListHdrParam.get(0).getApproveddate());
            txtcanceledDate.setValue(GdListHdrParam.get(0).getCanceleddate());
            txtcanceledby.setValue(GdListHdrParam.get(0).getCanceledbyname());
            try {
                date = dte.parse(GdListHdrParam.get(0).getEsttimearrive());
                txtarivalfrom.setValue(date);
                date1 = dt.parse(GdListHdrParam.get(0).getEsttimearriveto());
                txtarivalto.setValue(date1);
            } catch (Exception e) {
            }
            txtexpeditioncode.setValue(GdListHdrParam.get(0).getForwardingagent());
            txtexpeditiondesc.setValue(GdListHdrParam.get(0).getAgentname());
            txtstatusid.setValue(GdListHdrParam.get(0).getSuppdeliverystatus());
            txtstatus.setValue(GdListHdrParam.get(0).getSuppdeliverystatusdesc());
        } else {

        }
        validateForm();
    }

    public void refreshDtl() {

        listbox.getItems().clear();
        List<ListGdDtl> ListGdDtl = model.selectGdListDtl(txtsuppdeliveryid.getValue());
        for (ListGdDtl ListGdDtl1 : ListGdDtl) {
            Listcell supp_delivery_id = new Listcell();
            Listcell line_no = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell po_id = new Listcell();
            Listcell po_line_ref = new Listcell();
            Listcell ordered_quantity = new Listcell();
            Listcell delivered = new Listcell();
            Listcell ttl_dqty = new Listcell();
            Listcell qty_received = new Listcell();
            Listcell qty = new Listcell();
            Listcell line_remark = new Listcell();
            Listcell line_status = new Listcell();
            Listcell created_date = new Listcell();
            Listcell created_by = new Listcell();
            Listcell modified_date = new Listcell();
            Listcell modified_by = new Listcell();
            Listcell id = new Listcell();
            Listcell city_id = new Listcell();
            Listcell city_name = new Listcell();
            Listcell wh_id = new Listcell();
            Listcell order_date = new Listcell();
            Listcell po_price_unit = new Listcell();
            Listcell back_order = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell hlr_map_id = new Listcell();
            final Textbox quantity = new Textbox();

            supp_delivery_id.setLabel(ListGdDtl1.getSupp_delivery_id());
            line_no.setLabel(ListGdDtl1.getLine_no());
            item_id.setLabel(ListGdDtl1.getItem_id());
            item_code.setLabel(ListGdDtl1.getItem_code());
            item_description.setLabel(ListGdDtl1.getItem_description());
            po_id.setLabel(ListGdDtl1.getPo_id());
            po_line_ref.setLabel(ListGdDtl1.getPo_line_ref());
            ordered_quantity.setLabel(ListGdDtl1.getOrdered_quantity());
            qty.setLabel(ListGdDtl1.getQty());
            qty_received.setLabel(ListGdDtl1.getQty_received());
            line_remark.setLabel(ListGdDtl1.getLine_remark());
            line_status.setLabel(ListGdDtl1.getLine_status());
            created_date.setLabel(ListGdDtl1.getCreated_date());
            created_by.setLabel(ListGdDtl1.getCreated_by());
            modified_date.setLabel(ListGdDtl1.getModified_date());
            modified_by.setLabel(ListGdDtl1.getModified_by());
            id.setLabel(ListGdDtl1.getId());
            city_id.setLabel(ListGdDtl1.getCity_id());
            city_name.setLabel(ListGdDtl1.getCity_name());
            wh_id.setLabel(ListGdDtl1.getWh_id());
            order_date.setLabel(ListGdDtl1.getOrder_date());
            po_price_unit.setLabel(ListGdDtl1.getPo_price_unit());
            back_order.setLabel(ListGdDtl1.getBack_order());
            delivered.setLabel(ListGdDtl1.getDelivered());
            regional_id.setLabel(ListGdDtl1.getRegional_id());
            regional_description.setLabel(ListGdDtl1.getRegional_description());
            ttl_dqty.setLabel(ListGdDtl1.getTtl_dqty());
            hlr_map_id.setLabel(ListGdDtl1.getHlr_map_id());

            qty.setValue(ListGdDtl1.getQty());
            quantity.setWidth("110px");
            quantity.setStyle("text-align: right");
            qty.setStyle("text-align: right");
            delivered.setStyle("text-align: right");
            ordered_quantity.setStyle("text-align: right");
            qty_received.setStyle("text-align: right");
            ttl_dqty.setStyle("text-align: right");
            back_order.setStyle("text-align: right");
//            qty.appendChild(quantity);

            Listitem listitem = new Listitem();
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(line_no);
            listitem.appendChild(item_id);
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(po_id);
            listitem.appendChild(po_line_ref);
            listitem.appendChild(ordered_quantity);
            listitem.appendChild(delivered);
            listitem.appendChild(ttl_dqty);
            listitem.appendChild(qty);
            listitem.appendChild(back_order);
            listitem.appendChild(line_remark);
            listitem.appendChild(line_status);
            listitem.appendChild(created_date);
            listitem.appendChild(created_by);
            listitem.appendChild(modified_date);
            listitem.appendChild(modified_by);
            listitem.appendChild(id);
            listitem.appendChild(city_id);
            listitem.appendChild(city_name);
            listitem.appendChild(wh_id);
            listitem.appendChild(order_date);//22
            listitem.appendChild(po_price_unit);
            listitem.appendChild(qty_received);
            listitem.appendChild(regional_id);
            listitem.appendChild(regional_description);
            listitem.appendChild(hlr_map_id);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
//                    
                }
            });

            quantity.addEventListener(Events.ON_OK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    System.out.println(quantity.getValue());
//                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    System.out.println("edit qty");
                    System.out.println(t.getTarget().getParent().getParent());
                    selectRow = (Listitem) t.getTarget().getParent().getParent();
                    System.out.println(t.getTarget());
                    String gdId = ((Listcell) selectRow.getChildren().get(0)).getLabel();
                    String poId = ((Listcell) selectRow.getChildren().get(5)).getLabel();
                    String poLine = ((Listcell) selectRow.getChildren().get(6)).getLabel();
                    String lineNo = ((Listcell) selectRow.getChildren().get(1)).getLabel();
                    String itemId = ((Listcell) selectRow.getChildren().get(2)).getLabel();
                    String orderqty = ((Listcell) selectRow.getChildren().get(7)).getLabel();
                    String qty = ((Listcell) selectRow.getChildren().get(10)).getLabel();

                    Map<String, Object> map = model.doGdDtlQtyValidate(gdId, poId, poLine, lineNo, itemId, orderqty.toString().replace(",", ""), qty.toString().replace(",", ""));
                    if (map.get("outError").toString().equalsIgnoreCase("0")) {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);

                    } else {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);

                    }
                    refreshDtl();
                }
            });

            listbox.appendChild(listitem);
        }
        validateForm();
    }

    @Listen("onClick=#save")
    public void save() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Submitted")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("received")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("SAVE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to Save ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                String Date = "";
                                String Date1 = "";
                                SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    Date = dt.format(txtarivalfrom.getValue());
                                    Date1 = dt.format(txtarivalto.getValue());
                                } catch (Exception ex) {

                                }
                                String modul = "INSERT";
                                if (!txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
                                    modul = "UPDATE";
                                }
                                Map<String, Object> map = model.doGdSaveHdr(modul,
                                        txtsuppdeliveryid.getText(),
                                        txtsuppdeliverydate.getText(),
                                        txtbuId.getText(),
                                        txtsupid.getText(),
                                        txtsupliersiteid.getText(),
                                        txtpoid.getText(),
                                        txtcreationdate.getText(),
                                        txtwhid.getText(),
                                        txtexpeditioncode.getText(),
                                        Date,
                                        Date1,
                                        "1",
                                        txtremarks.getText(),
                                        userId);
                                if (map.get("outError").toString().equalsIgnoreCase("0")) {
                                    Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                                    if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
                                        txtsuppdeliveryid.setText(map.get("outSuppDeliverId").toString());
                                    }
                                    selectlisthdr();
                                    colorDefault();
                                } else {
                                    Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void formAddPoLine() {
        System.out.println("openForm");
        GoodDeliveryDetailCTRL goodDeliveryDetailCTRL = new GoodDeliveryDetailCTRL();
        goodDeliveryDetailCTRL.setGoodDeliveryCTRL(this);
        goodDeliveryDetailCTRL.setPoId(txtpoid.getValue());
        goodDeliveryDetailCTRL.setGdHdrId(txtsuppdeliveryid.getValue());
        goodDeliveryDetailCTRL.setWhId(txtwhid.getValue());
        Map<String, Object> map = new HashMap<>();
        map.put("controller", goodDeliveryDetailCTRL);
        Window window = (Window) Executions.createComponents("/Tcash/GoodDeliveryDetail.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#btnadd")
    public void adddetail() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Received")) {
//            Messagebox.show("This transaction has been Received.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction has been Approved.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("ADD");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            if (txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
                Messagebox.show("Please save this transactions first", "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                Map<String, Object> map = model.doGdSelectPOValidate(txtpoid.getValue(), txtstatusid.getValue());
                if (map.get("outError").toString().equalsIgnoreCase("0")) {
                    formAddPoLine();
                } else {
                    Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                }
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#submit")
    public void submitButton() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Submitted")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("received")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("SUBMIT");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to Submit this transaction?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                for (int i = 0; i < listbox.getItemCount(); i++) {
                                    List<Component> listCells = listbox.getItemAtIndex(i).getChildren();

                                    String lineNo = ((Listcell) listCells.get(1)).getLabel();
                                    String itemId = ((Listcell) listCells.get(2)).getLabel();
                                    String itemCode = ((Listcell) listCells.get(3)).getLabel();
                                    String delivQty = ((Listcell) listCells.get(8)).getLabel();
                                    Map<String, Object> map = model.doGdSubmitDtlValidateLine(txtsuppdeliveryid.getValue(), lineNo,
                                            itemId, itemCode, delivQty.toString().replace(",", ""));
                                    if (map.get("outError").toString().equalsIgnoreCase("0")) {

                                    } else {
                                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                        return;
                                    }
                                }
                                validateSubmit();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void validateSubmit() {
        System.out.println("validate Submit");
        Map<String, Object> map = model.doGdSubmitValidate(txtsuppdeliveryid.getValue(), userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), "2");
        System.out.println(map.get("outError").toString());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            submit();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

    }

    public void submit() {
        Map<String, Object> map = model.doGdSubmit(txtsuppdeliveryid.getValue(), userId);
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
            selectlisthdr();
            colorDefault();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#approve")
    public void approveButton() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Draft")) {
//            Messagebox.show("This transacton status is still Draft.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Approved")) {
//            Messagebox.show("This transaction status has been Approved.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (txtstatus.getValue().equalsIgnoreCase("RECEIVED")) {
//            Messagebox.show("This transaction status has been Received.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status has been Canceled.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("APPROVE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to Approve ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                approve();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void approve() {
        Map<String, Object> map = model.doGdApprove(txtsuppdeliveryid.getValue(), userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
            selectlisthdr();
            colorDefault();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#cancel")
    public void cancelButton() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Submitted")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("received")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getValue(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("CANCEL");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to cancel ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                if (gdCancelValidate() == 0) {
                                    if (listbox.getItemCount() != 0) {
                                        if (gdCancelValidateLine() == 0) {
                                            if (gdCancelLine() == 0) {
                                                gdCancel();
                                            }
                                        }
                                    } else {
                                        gdCancel();
                                    }
                                }
                                selectlisthdr();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public int gdCancelValidate() {
        Map<String, Object> map = model.doGdCancelValidate(userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        }
        return 0;
    }

    public int gdCancelValidateLine() {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();

            String lineNo = ((Listcell) listCells.get(1)).getLabel();
            String poLineRef = ((Listcell) listCells.get(6)).getLabel();
            Map<String, Object> map = model.doGdCancelValidateLine(txtsuppdeliveryid.getValue(), lineNo,
                    txtpoid.getValue(), poLineRef);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return 1;
            }
        }
        return 0;
    }

    public void gdCancel() {
        Map<String, Object> map = model.doGdCancel(txtsuppdeliveryid.getValue(), userId);
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
            colorDefault();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);

        }
        selectlisthdr();
    }

    public int gdCancelLine() {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();

            String lineNo = ((Listcell) listCells.get(1)).getLabel();
            String itemId = ((Listcell) listCells.get(2)).getLabel();
            String poLineRef = ((Listcell) listCells.get(6)).getLabel();
            Map<String, Object> map = model.doGdCancelLine(txtsuppdeliveryid.getValue(), lineNo, txtpoid.getValue(), itemId, poLineRef);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return 1;
            }
        }
        return 0;
    }

    @Listen("onClick=#delete")
    public void deleteBtn() {
//        if (txtstatus.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
////        
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("Canceled")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("Received")) {
//            Messagebox.show("This transaction status has been " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        String lineId = "";
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            lineId = ((Listcell) listCells.get(0)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("DELETE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to delete ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                String id = "";
                                String lineNo = "";
                                String poId = "";
                                String posNum = "";
                                String qty = "";
                                String itemId = "";
                                String delivQty = "";
                                try {
                                    List<Component> listCells = listbox.getSelectedItem().getChildren();
                                    id = ((Listcell) listCells.get(18)).getLabel();
                                    lineNo = ((Listcell) listCells.get(1)).getLabel();
                                    poId = ((Listcell) listCells.get(5)).getLabel();
                                    posNum = ((Listcell) listCells.get(6)).getLabel();
                                    qty = ((Listcell) listCells.get(8)).getLabel();
                                    itemId = ((Listcell) listCells.get(2)).getLabel();
                                    delivQty = ((Listcell) listCells.get(9)).getLabel();
                                } catch (Exception a) {
                                    Messagebox.show("Choose List First !", "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
                                    return;
                                }

                                Map<String, Object> map = model.doGdDeleteDtl(txtsuppdeliveryid.getValue(), id, lineNo, poId, posNum, qty.toString().replace(",", ""), itemId, delivQty.toString().replace(",", ""), userId);
                                if (map.get("outError").toString().equalsIgnoreCase("0")) {
                                    Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                                } else {
                                    Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                                selectlisthdr();
                                refreshDtl();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public int doGdEditSN() {
        String inItemid = "";
        String poId = "";
        String poLineRef = "";
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();

            inItemid = ((Listcell) listCells.get(2)).getLabel();
            poId = ((Listcell) listCells.get(5)).getLabel();
            poLineRef = ((Listcell) listCells.get(6)).getLabel();
        } catch (Exception e) {
//            Messagebox.show("Choose List First !", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        }
        Map<String, Object> map = model.doGdEditSN(inItemid, poId, poLineRef, txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        }
        return 0;
    }

    @Listen("onClick=#Edit")
    public void editSNForm() {


        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("EDIT_SN");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
        if(doGdEditSN()==1){
            return;
        }
            doGdEditSN();
            System.out.println("openFormSR");
            SlcRangeGDCtrl slcRangeGDCtrl = new SlcRangeGDCtrl();
            slcRangeGDCtrl.setGoodDeliveryCTRL(this);
            String lineNo = "";
            String poId = "";
            String posNum = "";
            String inItemid = "";
            String inQty = "";
            String inOrderedQty = "";
            String inWhId = "";
            String inCityid = "";
            String inHlrMapId = "";
            String inOrderDate = "";
            String inPoPriceUnit = "";
            String gdDtlId = "";
            try {
                List<Component> listCells = listbox.getSelectedItem().getChildren();
                lineNo = ((Listcell) listCells.get(1)).getLabel();
                poId = ((Listcell) listCells.get(5)).getLabel();
                posNum = ((Listcell) listCells.get(6)).getLabel();
                inItemid = ((Listcell) listCells.get(2)).getLabel();
                inQty = ((Listcell) listCells.get(8)).getLabel();
                inOrderedQty = ((Listcell) listCells.get(7)).getLabel();
                inWhId = ((Listcell) listCells.get(21)).getLabel();
                inCityid = ((Listcell) listCells.get(19)).getLabel();
                inHlrMapId = ((Listcell) listCells.get(27)).getLabel();
                inOrderDate = ((Listcell) listCells.get(22)).getLabel();
                inPoPriceUnit = ((Listcell) listCells.get(23)).getLabel();
                gdDtlId = ((Listcell) listCells.get(18)).getLabel();
            } catch (Exception e) {
                Messagebox.show("Choose List First !", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            slcRangeGDCtrl.setPoId(poId);
            slcRangeGDCtrl.setSupDelivid(txtsuppdeliveryid.getValue());
            slcRangeGDCtrl.setBuId(txtbuId.getText());
            slcRangeGDCtrl.setStatus(txtstatusid.getText());
            slcRangeGDCtrl.setLineNo(lineNo);
            slcRangeGDCtrl.setPoLineRef(posNum);
            slcRangeGDCtrl.setInItemid(inItemid);
            slcRangeGDCtrl.setInQty(inQty.toString().replace(",", ""));
            slcRangeGDCtrl.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
            slcRangeGDCtrl.setInWhId(inWhId);
            slcRangeGDCtrl.setInCityid(inCityid);
            slcRangeGDCtrl.setInHlrMapId(inHlrMapId);
            slcRangeGDCtrl.setInOrderDate(inOrderDate);
            slcRangeGDCtrl.setInPoPriceUnit(inPoPriceUnit);
            slcRangeGDCtrl.setGdDtlId(gdDtlId);
            Map<String, Object> map = new HashMap<>();
            map.put("controller", slcRangeGDCtrl);
            Window window = (Window) Executions.createComponents("/Tcash/SelectRange_GD.zul", null, map);
            window.doModal();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#preview")
    public void printWo() throws IOException {
        if (txtstatus.getValue().equalsIgnoreCase("")) {
            Messagebox.show("Please save this transaction first", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtstatus.getText().equalsIgnoreCase("draft") || txtstatus.getText().equalsIgnoreCase("Submitted")) {
            Messagebox.show("This transaction status is still " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
//        
        if (txtstatus.getText().equalsIgnoreCase("Canceled")) {
            Messagebox.show("This transaction status has been " + txtstatus.getText(), "Goods Delivery", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListGoodDelivery.zul");
        cf.setFunctionName("PRINT");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String subreport = properties.getProperty("subreporthrn");
            String logo = properties.getProperty("logo_hrn");
//        if(!txtstatus.getValue().equalsIgnoreCase("Approved")){
//            Messagebox.show("Status must be Approved", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
            System.out.println(txtsuppdeliveryid.getText());
            if ((!this.txtsuppdeliveryid.getText().equalsIgnoreCase("")) && (txtsuppdeliveryid.getText() != null)) {
                HashMap map = new HashMap();
                map.put("p_gd_id", txtsuppdeliveryid.getText());
                map.put("SUBREPORT_DIR", subreport);
                map.put("logo", logo);
                System.out.println(map);
                JRreportWindow jRreportWindow = new JRreportWindow(this.win_Good_delivery, true, map, "/reports/reportGDTCASH.jasper", "pdf", "Print Data GD", "Data Goods Delivery");
            } else {
                Messagebox.show("Please save this transaction first.", "Message", 1, "z-msgbox z-msgbox-exclamation");
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnrefresh")
    public void refreshAll() throws ParseException {
        selectlisthdr();
        refreshDtl();
    }

    void colorMandatory() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtWO.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtarivalfrom.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtarivalto.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtexpeditioncode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtexpeditiondesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtremarks.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
    }

    void colorDefault() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtWO.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtarivalfrom.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtarivalto.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtexpeditioncode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtexpeditiondesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtremarks.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtremarks.setReadonly(true);
    }

    @Listen("onClick=#newrecord")
    public void newRecord() {
        txtbuId.setText("");
        txtbucode.setText("");
        txtbuDesc.setText("");
        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");
        txtsuppdeliveryid.setText("");
        txtsuppdeliveryno.setText("");
        txtsupcode.setText("");
        txtsupid.setText("");
        txtsupdesc.setText("");
        txtsupliersitecode.setText("");
        txtsupliersitedesc.setText("");
        txtsupliersiteid.setText("");
        txtsuppdeliverydate.setText("");
        txtWO.setText("");
        txtarivalfrom.setValue(null);
        txtarivalto.setValue(null);
        txtremarks.setText("");
        txtexpeditioncode.setText("");
        txtexpeditiondesc.setText("");
        txtgooddeliverynocode.setText("");
        txtpo.setText("");
        txtpoid.setText("");
        txtcanceledDate.setText("");
        txtcanceledby.setText("");
        txtcreationdate.setText("");
        txtcreationby.setText("");
        txtsubmitedby.setText("");
        txtsubmiteddate.setText("");
        txtapprovedate.setText("");
        txtapproveddby.setText("");
        txtstatus.setText("");
        txtstatusid.setText("1");
        listbox.getItems().clear();
        colorMandatory();
        enableButton();
    }

    void enableButton() {
        btnbu.setDisabled(false);
        btnwo.setDisabled(false);
        btnwh.setDisabled(false);
        btnsup.setDisabled(false);
        btnsite.setDisabled(false);
        btnexpediton.setDisabled(false);
        txtarivalfrom.setDisabled(false);
        txtarivalto.setDisabled(false);
        txtremarks.setReadonly(false);
    }
}
