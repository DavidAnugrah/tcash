/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.BundlingGDListHDRParam;
import id.my.berkah.tcash1.model.ListGdDtlBundlingParam;
import id.my.berkah.util.IDefines;
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

/**
 *
 * @author Azec
 */
public class BundlingGoodsDelivery extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Textbox txtbuId, txtbuDesc, txtbucode, txtwhid, txtwhcode, txtwhdesc, txtsupid, txtsupcode, txtsupdesc,
            txtgooddeliverynocode, txtexpeditioncode, txtexpeditiondesc, txtsupliersiteid, txtsupliersitecode, txtsupliersitedesc,
            txtstatus, txtbundling,txtbundlingid, txtremarks, txtamount, txtcreationdate, txtcreationby, txtSubmitedby, txtSubmiteddate, txtcanceledDate, txtcanceledby,
            txtapproveddate,txtapprovedby,flag, txtsuppdeliveryid, txtsuppdeliveryno, txtsuppdeliverydate, txtstatusid,txtgdID;
    @Wire
    Datebox txtarivalfrom, txtarivalto;
    @Wire
    Window win_Good_delivery_bundling;
    @Wire
    Button btnbu, btnwh, btnsup, btnsite, btnwo,btnexpediton;
    @Wire
    Listbox listbox;
    ModelTcashCTLR model = new ModelTcashCTLR();
    public Listitem selectRow = new Listitem();

    @Override
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
            txtremarks.setReadonly(false);

        } else {
            flag.setText("UPDATE");
            selectlisthdr();
            refreshDtl();
            validateForm();
            colorDefault();
            txtremarks.setReadonly(false);
        }
       

    }
    
    public void validateForm(){
        if (listbox.getItemCount() == 0) {
            btnbu.setDisabled(true);
            btnwh.setDisabled(false);
            btnsup.setDisabled(false);
            btnsite.setDisabled(false);
            btnwo.setDisabled(false);
            txtarivalfrom.setDisabled(false);
            txtarivalto.setDisabled(false);
            btnexpediton.setDisabled(false);
            txtremarks.setDisabled(false);
            
        }else{
            btnbu.setDisabled(true);
            btnwh.setDisabled(true);
            btnsup.setDisabled(true);
            btnsite.setDisabled(true);
            btnwo.setDisabled(true);
            btnexpediton.setDisabled(true);
            txtarivalfrom.setDisabled(true);
            txtarivalto.setDisabled(true);
        }
        if(txtsuppdeliveryid.getValue().equalsIgnoreCase("")){
            btnbu.setDisabled(false);
        }
        else{
            btnbu.setDisabled(true);
        }
    } 

    @Listen("onClick=#btnbu")
    public void lovbu() {
        txtwhid.setValue("");txtwhcode.setValue("");txtwhdesc.setValue("");
        txtsupid.setValue(""); txtsupcode.setValue(""); txtsupdesc.setValue("");
        txtbundlingid.setValue(""); txtbundling.setValue("");
        txtsupliersiteid.setValue(""); txtsupliersitecode.setValue(""); txtsupliersitedesc.setValue("");
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
        w.setParent(win_Good_delivery_bundling);
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
        txtsupid.setValue(""); txtsupcode.setValue(""); txtsupdesc.setValue("");
        txtbundlingid.setValue(""); txtbundling.setValue("");
        txtsupliersiteid.setValue(""); txtsupliersitecode.setValue(""); txtsupliersitedesc.setValue("");
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
        w.setParent(win_Good_delivery_bundling);
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
        txtbundlingid.setValue(""); txtbundling.setValue("");
        txtsupliersiteid.setValue(""); txtsupliersitecode.setValue(""); txtsupliersitedesc.setValue("");
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
        w.setParent(win_Good_delivery_bundling);
        w.doModal();

    }

    @Listen("onClick=#btnsite")
    public void lovsuppliersite() {
        txtbundlingid.setValue(""); txtbundling.setValue("");
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
        w.setParent(win_Good_delivery_bundling);
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
        composerLov.setQuery("select * from (select rownum as No, bundling_id as \"Id\",bundling_no as \"Bundling No\",bundling_date as \"Date\",item_code as \"Item Code\",item_desc as\"Desciption\",qty as\"Qty\" from table(pkg_bundling_gd.LovBundling('" + txtbundling.getText() + "','" + txtwhid.getText() + "','" + txtsupid.getText() + "'))where (upper(bundling_no) like upper('?') or upper(item_code) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_bundling_gd.LovBundling('" + txtbundling.getText() + "','" + txtwhid.getText() + "','" + txtsupid.getText() + "'))Where bundling_no LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtbundlingid, txtbundling});
        composerLov.setHiddenColumn(new int[]{0, 1,3,4,6});

        composerLov.setTitle("List Of Bundling No");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_delivery_bundling);
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
        w.setParent(win_Good_delivery_bundling);
        w.doModal();
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtstatus.getText().equalsIgnoreCase("Draft")) {
               Messagebox.show("Are you sure want to close?\nData has not Submitted yet",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                           win_Good_delivery_bundling.detach();
                        } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {

                        }
                    }
                }
        );
        } else if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
               Messagebox.show("Are you sure want to close?\nData has not Approved yet",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                           win_Good_delivery_bundling.detach();
                        } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {

                        }
                    }
                }
        );
            
            
        }else {
            win_Good_delivery_bundling.detach(); 
        }
     
    }

    public void selectlisthdr() {
        try{
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dte = new SimpleDateFormat("dd-MM-yyyy");
        if (!txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
            List<BundlingGDListHDRParam> BundlingGDListHDRParam = model.selectGdListHdrBundling(txtsuppdeliveryid.getValue());
            txtbuId.setValue(BundlingGDListHDRParam.get(0).getBu_id());
            txtbucode.setValue(BundlingGDListHDRParam.get(0).getBu_code());
            txtbuDesc.setValue(BundlingGDListHDRParam.get(0).getBu_description());
            txtgooddeliverynocode.setValue(BundlingGDListHDRParam.get(0).getSupp_delivery_no());
            txtgdID.setValue(BundlingGDListHDRParam.get(0).getSupp_delivery_id());
            txtwhid.setValue(BundlingGDListHDRParam.get(0).getWh_id());
            txtwhcode.setValue(BundlingGDListHDRParam.get(0).getWh_code());
            txtwhdesc.setValue(BundlingGDListHDRParam.get(0).getWh_description());
            txtsupid.setValue(BundlingGDListHDRParam.get(0).getSupplier_id());
            txtsupcode.setValue(BundlingGDListHDRParam.get(0).getSupplier_number());
            txtsupdesc.setValue(BundlingGDListHDRParam.get(0).getSupplier_name());
            txtexpeditioncode.setValue(BundlingGDListHDRParam.get(0).getForwarding_agent());
            txtexpeditiondesc.setValue(BundlingGDListHDRParam.get(0).getAgent_name());
            txtsupliersiteid.setValue(BundlingGDListHDRParam.get(0).getSite_id());
            txtsupliersitecode.setValue(BundlingGDListHDRParam.get(0).getSite_code());
            txtsupliersitedesc.setValue(BundlingGDListHDRParam.get(0).getSite_name());
            txtbundlingid.setValue(BundlingGDListHDRParam.get(0).getBundling_id());
            txtbundling.setValue(BundlingGDListHDRParam.get(0).getBundling_no());
            txtremarks.setValue(BundlingGDListHDRParam.get(0).getSupp_delivery_remark());
            txtcreationdate.setValue(BundlingGDListHDRParam.get(0).getCreated_date());
            txtcreationby.setValue(BundlingGDListHDRParam.get(0).getCreated_by_name());
            txtSubmitedby.setValue(BundlingGDListHDRParam.get(0).getSubmitted_by());
            txtSubmiteddate.setValue(BundlingGDListHDRParam.get(0).getSubmitted_date());
            txtcanceledDate.setValue(BundlingGDListHDRParam.get(0).getCanceled_date());
            txtcanceledby.setValue(BundlingGDListHDRParam.get(0).getCanceled_by_name());
            txtapprovedby.setValue(BundlingGDListHDRParam.get(0).getApproved_by_name());
            txtapproveddate.setValue(BundlingGDListHDRParam.get(0).getApproved_date());
            try{
            date = dte.parse(BundlingGDListHDRParam.get(0).getEst_time_arrive());
            txtarivalfrom.setValue(date);
            date1 = dt.parse(BundlingGDListHDRParam.get(0).getEst_time_arrive_to());
            txtarivalto.setValue(date1);
            }
            catch(Exception e){
            }
            txtexpeditioncode.setValue(BundlingGDListHDRParam.get(0).getForwarding_agent());
            txtexpeditiondesc.setValue(BundlingGDListHDRParam.get(0).getAgent_name());
            txtstatusid.setValue(BundlingGDListHDRParam.get(0).getSupp_delivery_status());
            txtstatus.setValue(BundlingGDListHDRParam.get(0).getSupp_delivery_status_desc());
        }else{
            
        }
        }catch(Exception E){
            System.out.println(""+E);
        }
        validateForm();
    }

    public void refreshDtl() {

        listbox.getItems().clear();
        List<ListGdDtlBundlingParam> ListGdDtlBundlingParam = model.selectGdListDtlBundling(txtsuppdeliveryid.getValue());
        for (ListGdDtlBundlingParam ListGdDtl1 : ListGdDtlBundlingParam) {
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
//            hlr_map_id.setLabel(ListGdDtl1.getHlr_map_id());

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
            listitem.appendChild(qty);
            listitem.appendChild(delivered);
            listitem.appendChild(ttl_dqty);
            listitem.appendChild(qty_received);
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
                    String qty = ((Listcell) selectRow.getChildren().get(8)).getLabel();
                    String iD = ((Listcell) selectRow.getChildren().get(18)).getLabel();

                    Map<String, Object> map = model.doGdDtlQtyValidate(gdId, poId, poLine, lineNo, itemId, orderqty.toString().replace(",", ""), qty.toString().replace(",", ""));
                    if (map.get("outError").toString().equalsIgnoreCase("0")) {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);

                    }
                    else {
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

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
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
                    String modul ="INSERT";
                    if (!txtsuppdeliveryid.getValue().equalsIgnoreCase("")){
                        modul = "UPDATE";
                    }
                    Map<String, Object> map = model.doGdSaveHdrBundling(modul,
                            txtsuppdeliveryid.getText(),
                            txtsuppdeliverydate.getText(),
                            txtbuId.getText(),
                            txtsupid.getText(),
                            txtsupliersiteid.getText(),
                            txtbundlingid.getText(),
                            txtcreationdate.getText(),
                            txtwhid.getText(),
                            txtexpeditioncode.getText(),
                            Date,
                            Date1,
                            "1",
                            txtremarks.getText(),
                            userId);
                    if (map.get("outError").toString().equalsIgnoreCase("0")) {
                        if (modul.equals("INSERT")) {
                            txtsuppdeliveryid.setText(map.get("outSuppDeliverId").toString());
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                        selectlisthdr();
                        colorDefault();
                           btnbu.setDisabled(true);
                            btnwh.setDisabled(true);
                            btnsup.setDisabled(true);
                            btnsite.setDisabled(true);
                            btnwo.setDisabled(true);
                            btnexpediton.setDisabled(true);
                            txtarivalfrom.setDisabled(true);
                            txtarivalto.setDisabled(true);
                        } else {
                             Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                        selectlisthdr();
                        colorDefault();
                        }
                        
                    }
                    else {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
                else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                }
            }
        });
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    public void formAddPoLine1() {
//        System.out.println("openForm");
        Map<String, Object> map = new HashMap<>();
        map.put("buid", txtbuId.getText());
        map.put("bundlingId", txtbundlingid.getText());
        map.put("gdID", txtgdID.getText());
        map.put("whID", txtwhid.getText());
        System.out.println("bu Id : "+txtbuId.getText());
        System.out.println("gd Id : "+txtgdID.getText());
        System.out.println("bdl Id : "+  txtbundlingid.getText());
        System.out.println( "wh Id : "+txtwhid.getText());
        Window w = (Window) Executions.createComponents("/Tcash/BundlingGoodsDeliveryDetailCTRL.zul", null, map);
        w.setAttribute("parentController", this);
        w.doModal();
    }

    @Listen("onClick=#btnadd")
    public void adddetail() {
         
//        if (txtstatus.getText().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first","Bundling goods delivery",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
//         
//        if (txtstatus.getText().equalsIgnoreCase("submitted")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//         
//        if (txtstatus.getText().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("received")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
        cf.setFunctionName("ADD");

        //OutErrMsg oe = new OutErrMsg();

        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
        if (txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
        Messagebox.show("Please save this transaction first","Goods Delivery",Messagebox.OK,Messagebox.EXCLAMATION);
        }
        else {
            Map<String, Object> map = model.doGdSelectPOValidateBundling(txtbundlingid.getValue(), txtstatusid.getValue());
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
                formAddPoLine1();
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    @Listen("onClick=#submit")
    public void submitButton() {
      
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
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
                        String delivQty = ((Listcell) listCells.get(9)).getLabel();
                    
                        Map<String, Object> map = model.doGdSubmitDtlValidateLineBundling(txtsuppdeliveryid.getValue(), lineNo,
                                itemId, itemCode, delivQty.toString().replace(",", ""));
                        if (map.get("outError").toString().equalsIgnoreCase("0")) {
                            
                        }
                        else {
                            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            return;
                        }
                    }
                    validateSubmit();
                }
                else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                }
            }
        });
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    public void validateSubmit() {
        System.out.println("validate Submit");
        Map<String, Object> map = model.doGdSubmitValidateBundling(txtsuppdeliveryid.getValue(), userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        System.out.println(map.get("outError").toString());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            submit();
        }
        else {
            Messagebox.show(map.get("outMessages").toString() , "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

    }

    public void submit() {
        System.out.println("Submit");
        Map<String, Object> map = model.doGdSubmitBundling(txtsuppdeliveryid.getValue(),userId);
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
            selectlisthdr();
            colorDefault();
        }
        else {
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
//            Messagebox.show("This transaction status is still Draft", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Approved")) {
//            Messagebox.show("This transaction has been Approved.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("RECEIVED")) {
//            Messagebox.show("This transaction has been Received.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction has been canceled.", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
        cf.setFunctionName("APPROVE");

        //OutErrMsg oe = new OutErrMsg();

        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
        Messagebox.show("Are you sure want to Approve ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event e){
                if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                    approve();
                }
                else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                }
            }
        });
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    public void approve() {
        Map<String, Object> map = model.doGdApproveBundling(txtsuppdeliveryid.getValue(), userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
            selectlisthdr();
            colorDefault();
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#cancel")
    public void cancelButton() {

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
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
                                if (gdCancelLine()== 0) {
                                    gdCancel();
                                }
                            }
                         }else{
                             gdCancel();
                         }
                    }
                    selectlisthdr();
                }
                else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                }
            }
        });
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    public int gdCancelValidate() {
        Map<String, Object> map = model.doGdCancelValidate(userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        }
        else {
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
                    txtbundlingid.getValue(), poLineRef);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return 1;
            }
        }
        return 0;
    }

    public void gdCancel() {
        Map<String, Object> map = model.doGdCancelBundling(txtsuppdeliveryid.getValue(), userId);
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
        colorDefault();
        }
        
        else {
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
            Map<String, Object> map = model.doGdCancelLineBundling(txtsuppdeliveryid.getValue(), lineNo, txtbundlingid.getValue(), itemId, poLineRef);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                 return 1;
            }
        }
        return 0;
    }

    @Listen("onClick=#delete")
    public void deleteBtn() {
//       if (txtstatus.getText().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first","Bundling goods delivery",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
//         
//        if (txtstatus.getText().equalsIgnoreCase("submitted")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//         
//        if (txtstatus.getText().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("canceled")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("received")) {
//            Messagebox.show("This transaction has been "+txtstatus.getText());
//            return;
//        }
         String lineId = "";
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            lineId = ((Listcell) listCells.get(0)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Work Order : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
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
                    String GdDtlId = "";
                    try{
                    List<Component> listCells = listbox.getSelectedItem().getChildren();
                    id = ((Listcell) listCells.get(19)).getLabel();
                    lineNo = ((Listcell) listCells.get(1)).getLabel();
                    poId = ((Listcell) listCells.get(5)).getLabel();
                    posNum = ((Listcell) listCells.get(6)).getLabel();
                    qty = ((Listcell) listCells.get(7)).getLabel();
                    itemId = ((Listcell) listCells.get(2)).getLabel();
                    delivQty = ((Listcell) listCells.get(9)).getLabel();
                    GdDtlId = ((Listcell) listCells.get(0)).getLabel();
                        System.out.println(id+"id");
                        System.out.println(lineNo+"line");
                        System.out.println(poId+"po");
                        System.out.println(posNum+"num");
                        System.out.println(qty+"qty");
                        System.out.println(itemId+"item");
                        System.out.println(delivQty+"delivery");
                        System.out.println(GdDtlId+"gdDTL");
                    } catch (Exception a) {
                        Messagebox.show("Choose List First !");
                        return;
                    }
                    
                    Map<String, Object> map = model.doGdDeleteDtlBundling(txtsuppdeliveryid.getText(), id, lineNo, txtbundlingid.getText(), posNum, qty.toString().replace(",", ""), itemId, delivQty.toString().replace(",", ""),userId);
                    if (map.get("outError").toString().equalsIgnoreCase("0")) {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                        selectlisthdr();
                        refreshDtl();
                    }
                    else {
                        Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                    
                }
                else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                }
            }
        });
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }

    public int doGdEditSN() {
        String inItemid ="";
        String poId = "";
        String poLineRef = "";
        try {
        List<Component> listCells = listbox.getSelectedItem().getChildren();
        
        inItemid = ((Listcell) listCells.get(2)).getLabel();
        poId = ((Listcell) listCells.get(5)).getLabel();
        poLineRef = ((Listcell) listCells.get(6)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        }
        Map<String, Object> map = model.doGdEditSNBundling(inItemid,poId,poLineRef,txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Goods Delivery Bundling : Message", Messagebox.OK, Messagebox.EXCLAMATION);
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
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
        cf.setFunctionName("EDIT_SN");

        //OutErrMsg oe = new OutErrMsg();

        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
        if(doGdEditSN()==1){
            return;
        }
            doGdEditSN();
        System.out.println("openFormSR");
        BundlingSelectRangeGD BundlingSelectRangeGD = new BundlingSelectRangeGD();
        BundlingSelectRangeGD.setBundlingGoodsDelivery(this);
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
            gdDtlId = ((Listcell) listCells.get(19)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        BundlingSelectRangeGD.setPoId(poId);
        BundlingSelectRangeGD.setSupDelivid(txtsuppdeliveryid.getValue());
        BundlingSelectRangeGD.setLineNo(lineNo);
        BundlingSelectRangeGD.setPoLineRef(posNum);
        BundlingSelectRangeGD.setInItemid(inItemid);
        BundlingSelectRangeGD.setInQty(inQty.toString().replace(",", ""));
        BundlingSelectRangeGD.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
        BundlingSelectRangeGD.setInWhId(inWhId);
        BundlingSelectRangeGD.setInCityid(inCityid);
        BundlingSelectRangeGD.setInHlrMapId(inHlrMapId);
        BundlingSelectRangeGD.setInOrderDate(inOrderDate);
        BundlingSelectRangeGD.setInPoPriceUnit(inPoPriceUnit);
        BundlingSelectRangeGD.setGdDtlId(gdDtlId);
        BundlingSelectRangeGD.setStatus(txtstatusid.getText());
        Map<String, Object> map = new HashMap<>();
        map.put("controller", BundlingSelectRangeGD);
        Window window = (Window) Executions.createComponents("/Tcash/BundlingSelectRangeGD.zul", null, map);
        window.doModal();
        }
        else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
    }
    
    @Listen("onClick=#preview")
    public void printWo() throws IOException{
        
        if (txtstatus.getText().equalsIgnoreCase("")) {
            Messagebox.show("Please save this transaction first","Bundling goods delivery",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
         
        if (txtstatus.getText().equalsIgnoreCase("draft")) {
            Messagebox.show("This transaction status has been "+txtstatus.getText());
            return;
        }
        if (txtstatus.getText().equalsIgnoreCase("submitted")) {
            Messagebox.show("This transaction status has been "+txtstatus.getText());
            return;
        }
         
        if (txtstatus.getText().equalsIgnoreCase("canceled")) {
            Messagebox.show("This transaction status has been "+txtstatus.getText());
            return;
        }
          ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsDelivery.zul");
        cf.setFunctionName("PREVIEW");

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
    if ((!this.txtsuppdeliveryid.getText().equalsIgnoreCase("")) && (txtsuppdeliveryid.getText() != null))
    {
      HashMap map = new HashMap();
      map.put("p_gd_id", txtsuppdeliveryid.getText());
      map.put("SUBREPORT_DIR", subreport);
      map.put("logo", logo);
      System.out.println(map);
            JRreportWindow jRreportWindow = new JRreportWindow(this.win_Good_delivery_bundling, true, map, "/reports/reportGDTCASH_bundling.jasper", "pdf", "Print Data GD", "Data Goods Delivery");
    }
    else {
      Messagebox.show("Save transaction first.", "Message", 1, "z-msgbox z-msgbox-exclamation");
    }
    }else
        {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);			        	
        }
}
    
    @Listen("onClick=#btnrefresh")
    public void refreshAll() throws ParseException {
        selectlisthdr();
        refreshDtl();
    }
    
    
    void colorMandatory(){
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtbundling.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtarivalfrom.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtarivalto.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
       txtexpeditioncode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtexpeditiondesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
    }
    
    void colorDefault(){
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtbundling.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtarivalfrom.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtarivalto.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtexpeditioncode.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtexpeditiondesc.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
    }
    
    @Listen("onClick=#newrecord")
    public void newRecord(){
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
        txtarivalfrom.setValue(null);
        txtarivalto.setValue(null);
        txtremarks.setText("");
        txtexpeditioncode.setText("");
        txtexpeditiondesc.setText("");
        txtgooddeliverynocode.setText("");
        txtbundling.setText("");
        txtbundlingid.setText("");        
        txtcanceledDate.setText("");
        txtcanceledby.setText("");
        txtcreationdate.setText("");
        txtcreationby.setText("");
        txtSubmitedby.setText("");
        txtSubmiteddate.setText("");
        txtapproveddate.setText("");
        txtapprovedby.setText("");
        txtstatus.setText("");
        txtstatusid.setText("1");
        listbox.getItems().clear();
        enableButton();
        colorMandatory();
        
    }
    
    void enableButton(){
       btnbu.setDisabled(false);
       btnwo.setDisabled(false);
       btnwh.setDisabled(false);
       btnsup.setDisabled(false);
       btnsite.setDisabled(false);
       btnexpediton.setDisabled(false);
       txtarivalfrom.setDisabled(false);
       txtarivalto.setDisabled(false);
    }
}