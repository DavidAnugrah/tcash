/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListBundlingGRHDR;
import id.my.berkah.tcash1.model.ListresultGrListDtlParam;
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
import java.util.Calendar;
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
public class BundlingGoodsReceipt extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    Listitem selectRow = new Listitem();

    @Wire
    Textbox txtbuId, txtbuDesc, txtbucode, txtwhid, txtwhcode, txtwhdesc, txtsupid, txtsupcode, txtsupdesc,
            txtgooddeliverynocode, txtexpeditioncode, txtexpeditiondesc, txtsupliersiteid, txtsupliersitecode, txtsupliersitedesc,
            txtstatus, txtWO, txtremarks, txtamount, txtcreationdate, txtcreationby, txtConfirmedDate, txtConfirmedby,
            flag, txtsuppdeliveryid, txtprosesdate, txtpoid, txtpo, txtsuppdeliveryno, txtsuppdeliverydate, txtstatusid, txtGdId, txtGd, txtApproveDate, txtApproveBy,
            txtWhLocId, txtWhLocCode, txtWhLocDesc, txtQuery, txtcanceleddate, txtcanceledby;
    @Wire
    Datebox txtarivalfrom, txtarivalto;
    @Wire
    Window win_Good_Reciept_bundling, win_GR_detail_bundling;
    @Wire
    Button btnbu, btnwh, btnsup, btnsite, btnwo, btngd, newrecord;
    @Wire
    Listbox listbox;
    ModelTcashCTLR model = new ModelTcashCTLR();

    String qtyTemp = "";
    final Decimalbox txtlotSize = new Decimalbox();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        selectlisthdr();
        refreshDtl(txtsuppdeliveryid.getValue());
        formValidate();
    }

    public void formValidate() {
        if (txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
            flag.setText("INSERT");
            txtstatusid.setText("");
            mandatory();
        } else {
            display();
            if (listbox.getItemCount() == 0) {
                btnbu.setDisabled(false);
                btnwh.setDisabled(false);
                btnsup.setDisabled(false);
                btnsite.setDisabled(false);
                btnwo.setDisabled(false);
                btngd.setDisabled(false);
            } else {
                flag.setText("UPDATE");
                btnbu.setDisabled(true);
                btnwh.setDisabled(true);
                btnsup.setDisabled(true);
                btnsite.setDisabled(true);
                btnwo.setDisabled(true);
                btngd.setDisabled(true);
                display();
            }

        }

        if (txtstatusid.getText().equalsIgnoreCase("1")) {
            txtstatus.setText("Draft");
            mandatory();
        }
        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
            btnbu.setDisabled(false);
        } else {
            btnbu.setDisabled(true);
            display();
        }

    }

    @Listen("onClick=#go")
    public void gosearch() {
        List<Component> listCells = listbox.getItemAtIndex(0).getChildren();
        String lineNo = ((Listcell) listCells.get(1)).getLabel();
        String orderQty = ((Textbox) ((Listcell) listCells.get(3)).getChildren().get(0)).getValue();
//        String orderQty = ((Textbox) ((Listcell) selectRow.getChildren().get(9)).getChildren().get(0)).getValue();
        System.out.println(orderQty);
        refreshDtl(orderQty.toString().replace(",", ""));
    }

    @Listen("onClick=#query")
    public void query() {

        listbox.getItems().clear();
        Listcell receive_id = new Listcell();
        Listcell receive_line = new Listcell();
        Listcell po_header_id = new Listcell();
        Listcell po_header_no = new Listcell();
        final Textbox txpoNo = new Textbox();
        Listcell po_line_id = new Listcell();
        Listcell supp_delivery_id = new Listcell();
        Listcell supp_delivery_line = new Listcell();
        Listcell item_id = new Listcell();
        Listcell item_code = new Listcell();
        Listcell city_id = new Listcell();
        Listcell city_code = new Listcell();
        Listcell location = new Listcell();
        Listcell hlr_map_id = new Listcell();
        Listcell hlr = new Listcell();
        Listcell unit_of_measure = new Listcell();
        Listcell qty_order = new Listcell();
        Listcell qty_delivered = new Listcell();
        Listcell qty_deliver = new Listcell();
        Listcell qty_receive = new Listcell();
        Listcell qty_accept = new Listcell();
        Listcell qty_approved = new Listcell();
        Listcell qty_reject = new Listcell();
        Listcell qty_balance = new Listcell();
        Listcell qty_cancel = new Listcell();
        Listcell lot_id = new Listcell();
        Listcell date_lot = new Listcell();
        Listcell expiry_date = new Listcell();
        Listcell po_distribution_id = new Listcell();
        Listcell lot_size = new Listcell();
        Listcell reason_id = new Listcell();
        Listcell receive_line_status = new Listcell();
        Listcell receive_dtl_id = new Listcell();
        Listcell create_by = new Listcell();
        Listcell create_date = new Listcell();
        Listcell update_by = new Listcell();
        Listcell update_date = new Listcell();
        Listcell wh_loc_id = new Listcell();
        Listcell wh_loc_code = new Listcell();
        Listcell regional_id = new Listcell();
        Listcell regional_description = new Listcell();

        txpoNo.setWidth("110px");
        txpoNo.setInplace(true);
        po_header_no.appendChild(txpoNo);

        Listitem listitem = new Listitem();
        listitem.appendChild(receive_id);
        listitem.appendChild(receive_line);
        listitem.appendChild(po_header_id);
        listitem.appendChild(po_header_no);
        listitem.appendChild(po_line_id);
        listitem.appendChild(supp_delivery_id);//5
        listitem.appendChild(supp_delivery_line);
        listitem.appendChild(item_id);
        listitem.appendChild(item_code);
        listitem.appendChild(city_id);
        listitem.appendChild(city_code);//10
        listitem.appendChild(location);
        listitem.appendChild(hlr_map_id);
        listitem.appendChild(hlr);
        listitem.appendChild(unit_of_measure);
        listitem.appendChild(qty_order);//15
        listitem.appendChild(qty_delivered);
        listitem.appendChild(qty_deliver);
        listitem.appendChild(qty_receive);
        listitem.appendChild(qty_accept);
        listitem.appendChild(qty_approved);//20
        listitem.appendChild(qty_reject);
        listitem.appendChild(qty_balance);
        listitem.appendChild(qty_cancel);
        listitem.appendChild(lot_id);
        listitem.appendChild(date_lot);//25
        listitem.appendChild(expiry_date);
        listitem.appendChild(po_distribution_id);
        listitem.appendChild(lot_size);
        listitem.appendChild(reason_id);
        listitem.appendChild(receive_line_status);//30
        listitem.appendChild(receive_dtl_id);
        listitem.appendChild(create_by);
        listitem.appendChild(create_date);
        listitem.appendChild(update_by);
        listitem.appendChild(update_date);
        listitem.appendChild(wh_loc_id);
        listitem.appendChild(wh_loc_code);
        listitem.appendChild(regional_id);
        listitem.appendChild(regional_description);

        listbox.appendChild(listitem);
    }

    @Listen("onClick=#btnbu")
    public void lovbu() {
        txtwhid.setValue("");
        txtwhcode.setValue("");
        txtwhdesc.setValue("");
        txtsupid.setValue("");
        txtsupcode.setValue("");
        txtsupdesc.setValue("");
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        txtpoid.setValue("");
        txtWO.setValue("");
        txtGdId.setValue("");
        txtGd.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_id as \"Bu id\",bu_code as \"Bussiness Code\",bu_description as \"Bussiness Unit Description\" from table(pkg_tcash_lov.LovBU('" + "" + "','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovBU('" + " " + "','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU('" + " " + "','" + responsibilityId + "','" + userId + "')) where \n"
                + "(upper(bu_code) like upper('?') or upper(bu_description) like upper('?'))");
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
        w.setParent(win_Good_Reciept_bundling);
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
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        txtpoid.setValue("");
        txtWO.setValue("");
        txtGdId.setValue("");
        txtGd.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"WareHouse Id\",wh_code as \"WareHouse Code\",wh_description as \"WareHouse Description\" from table(pkg_tcash_lov.LovWh('" + "" + "','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))"
                + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovWh('" + " " + "','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWh('" + " " + "','" + txtbuId.getText() + "','" + userId + "','" + responsibilityId + "')) where \n"
                + "(upper(wh_code) like upper('?') or upper(wh_description) like upper('?'))");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode, txtwhdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WareHouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_Reciept_bundling);
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
        txtsupliersiteid.setValue("");
        txtsupliersitecode.setValue("");
        txtsupliersitedesc.setValue("");
        txtpoid.setValue("");
        txtWO.setValue("");
        txtGdId.setValue("");
        txtGd.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, Supplier_Id as \"Supplier id\",Supplier_Number as \"Supplier Number\",Supplier_Name as \"Supplier Name\" from table(pkg_tcash_lov.LovSupplierGD('" + "" + "'))where (upper(Supplier_Number) like upper('?') or upper(Supplier_Name) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovSupplierGD('" + " " + "'))Where Supplier_Number LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSupplierGD('')) where \n"
                + "(upper(Supplier_Number) like upper('?') or upper(Supplier_Name) like upper('?'))");
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
        w.setParent(win_Good_Reciept_bundling);
        w.doModal();

    }

    @Listen("onClick=#btnsite")
    public void lovsuppliersite() {
        txtpoid.setValue("");
        txtWO.setValue("");
        txtGdId.setValue("");
        txtGd.setValue("");
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Supplier id\",supplier_number as \"Supplier Site Code\",supplier_name as \"Supplier Site Name\" from table(pkg_tcash_lov.LovSite('" + txtsupid.getText() + "'))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovSite('" + txtsupid.getText() + "'))Where supplier_number LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSite('" + txtsupid.getText() + "')) where \n"
                + "(upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?'))");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupliersiteid, txtsupliersitecode, txtsupliersitedesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier Site");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_Reciept_bundling);
        w.doModal();

    }

//        @Listen("onClick=#btnselcetwo")
//        public void selectWO(){
//            Window window = (Window)Executions.createComponents("/Tcash/ListWoItem.zul", null,null);
//        window.doModal();
//        }
    @Listen("onClick=#btnwo")
    public void selectWO() {
        txtGdId.setValue("");
        txtGd.setValue("");
        System.out.println(txtwhid.getValue());
        System.out.println(txtsupid.getValue());
        System.out.println(txtsupliersiteid.getValue());
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bundling_id as \"Id\",po_id,bundling_no as \"Bundling No\" ,bundling_date,item_code as \"Item Code\",item_desc as\"Item\",qty from table(pkg_bundling_gr.LovBundling('','"+txtbuId.getText() +"','"+txtwhid.getText()+"','"+txtsupid.getText()+"'))where (upper(bundling_no) like upper('?') or upper(item_code) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovPoGr('" + txtwhid.getText() + "','" + txtsupid.getText() + "','" + txtsupliersiteid.getText() + "',''))Where po_id LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_bundling_gr.LovBundling('','"+txtbuId.getText() +"','"+txtwhid.getText()+"','"+txtsupid.getText()+"')) where \n"
                + "(upper(item_code) like upper('?'))");
        composerLov.setSelectedColumn(new int[]{1, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtpoid, txtWO});
        composerLov.setHiddenColumn(new int[]{0, 1, 2, 4, 6, 7});

        composerLov.setTitle("List Of Bundling");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_Reciept_bundling);
        w.doModal();
    }

    @Listen("onClick=#btngd")
    public void selectGD() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, SUPP_DELIVERY_ID as \"Supp Delivery Id\",SUPP_DELIVERY_NO as \"Supp Delivery No\" from table(pkg_bundling_gr.LovGD('" + txtpoid.getText() + "','" + userId + "','" + responsibilityId + "'))where (upper(SUPP_DELIVERY_ID) like upper('?') or upper(SUPP_DELIVERY_NO) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovGD('" + txtpoid.getText() + "','" + userId + "','" + responsibilityId + "'))Where po_id LIKE '%?%'");
        composerLov.setQueryTotal("select count(*) from table(pkg_bundling_gr.LovGD('" + txtpoid.getText() + "','" + userId + "','" + responsibilityId + "')) where \n"
                + "(upper(SUPP_DELIVERY_ID) like upper('?') or upper(SUPP_DELIVERY_NO) like upper('?'))");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtGdId, txtGd});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Goods Delivery");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Good_Reciept_bundling);
        w.doModal();
    }

    @Listen("onClick=#addWHloc")
    public void lovaddWHloc() {
        if (txtstatus.getValue().equalsIgnoreCase("canceled")) {
            Messagebox.show("This transaction has been Canceled.", "Message", 1, "z-msgbox z-msgbox-exclamation");
            return;
        }
        if (txtstatus.getValue().equalsIgnoreCase("Approved")) {
            Messagebox.show("This transaction has been Approved.", "Message", 1, "z-msgbox z-msgbox-exclamation");
            return;
        }
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            String lineNo = ((Listcell) listCells.get(1)).getLabel();

            HashMap map = new HashMap<String, Object>();
            LovController composerLov = new LovController();
            composerLov.setQuery("select * from (select rownum as No, wh_id as \"WH LOC Id\",wh_code as \"WH LOC Code\",wh_description as \"WH LOC Description\" from table(pkg_tcash_lov.LovWhLoc('" + txtwhid.getText() + "','" + txtstatusid.getValue() + "'))"
                    + "where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))"
                    + "where No BETWEEN param1 and param2");
            composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWhLoc('" + txtwhid.getText() + "','" + txtstatusid.getValue() + "')) where \n"
                    + "(upper(wh_code) like upper('?') or upper(wh_description) like upper('?'))");
            composerLov.setSelectedColumn(new int[]{1, 2, 3});
            composerLov.setComponentTransferData(new Textbox[]{txtWhLocId, txtWhLocCode, txtWhLocDesc});
            composerLov.setHiddenColumn(new int[]{0, 1});
            composerLov.setEventListener(new EventListener() {

                @Override
                public void onEvent(Event t) throws Exception {
                    LOVLsitener();
                }
            });

            composerLov.setTitle("List Of WareHouse");
            composerLov.setWidth("500px");
            composerLov.setHeight("335px");
            composerLov.setPageSize(10);
            map.put("composerLov", composerLov);

            Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
            w.setParent(win_Good_Reciept_bundling);
            w.doModal();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

    }

    void LOVLsitener() {
        String whLocIdTmp = "";
        String lineNo = "";
        try {
            List<Component> listCells = listbox.getSelectedItem().getChildren();
            lineNo = ((Listcell) listCells.get(1)).getLabel();
        } catch (Exception e) {
            Messagebox.show("Choose List First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (!whLocIdTmp.equalsIgnoreCase(txtWhLocId.getValue()) || !txtWhLocId.getValue().equalsIgnoreCase("")) {
            Map<String, Object> map1 = model.doGrUpdateDtlWhLoc(txtsuppdeliveryid.getValue(), lineNo, txtWhLocId.getValue());
            if (map1.get("outError").toString().equalsIgnoreCase("0")) {
                Messagebox.show(map1.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.INFORMATION);
                System.out.println("ok");
                refreshDtl(txtsuppdeliveryid.getValue());
            } else {
                Messagebox.show(map1.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                System.out.println("no");
                return;
            }
        }
    }

    @Listen("onClick=#close")
    public void close() {
    win_Good_Reciept_bundling.detach();
    }

    public void selectlisthdr() {
        if (!txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
            display();
            List<ListBundlingGRHDR> GrListHdrParam = model.selectGrListHdrbundling(txtsuppdeliveryid.getValue());
            txtbuId.setValue(GrListHdrParam.get(0).getBu_id());
            System.out.println(txtbuId.getValue());
            txtbucode.setValue(GrListHdrParam.get(0).getBu_code());
            txtbuDesc.setValue(GrListHdrParam.get(0).getBu_description());
            txtgooddeliverynocode.setValue(GrListHdrParam.get(0).getReceive_no());
            txtwhid.setValue(GrListHdrParam.get(0).getWh_id());
            txtwhcode.setValue(GrListHdrParam.get(0).getWh_code());
            txtwhdesc.setValue(GrListHdrParam.get(0).getWh_description());
            txtsupid.setValue(GrListHdrParam.get(0).getSupplier_id());
            txtsupcode.setValue(GrListHdrParam.get(0).getSupplier_number());
            txtsupdesc.setValue(GrListHdrParam.get(0).getSupplier_description());
            txtsupliersiteid.setValue(GrListHdrParam.get(0).getSite_id());
            txtsupliersitecode.setValue(GrListHdrParam.get(0).getSite_code());
            txtsupliersitedesc.setValue(GrListHdrParam.get(0).getSite_description());
            txtstatus.setValue(GrListHdrParam.get(0).getReceive_status_desc());
            txtpoid.setValue(GrListHdrParam.get(0).getPo_header_id());
            txtWO.setValue(GrListHdrParam.get(0).getPo_header_no());
            txtcreationdate.setValue(GrListHdrParam.get(0).getCreate_date());
            txtcreationby.setValue(GrListHdrParam.get(0).getCreated_by_name());
            txtstatusid.setValue(GrListHdrParam.get(0).getReceive_status());
            txtstatus.setValue(GrListHdrParam.get(0).getReceive_status_desc());
            txtConfirmedDate.setValue(GrListHdrParam.get(0).getConfirmed_date());
            txtConfirmedby.setValue(GrListHdrParam.get(0).getConfirmed_by_name());
            txtApproveBy.setValue(GrListHdrParam.get(0).getApproved_by_name());
            txtApproveDate.setValue(GrListHdrParam.get(0).getApproved_date());
            txtGdId.setValue(GrListHdrParam.get(0).getSupp_delivery_id());
            txtGd.setValue(GrListHdrParam.get(0).getSupp_delivery_no());
            txtcanceledby.setValue(GrListHdrParam.get(0).getCanceledby());
            txtcanceleddate.setValue(GrListHdrParam.get(0).getCanceleddate());
        }
    }

    public void refreshDtl(String id) {

        listbox.getItems().clear();
        List<ListresultGrListDtlParam> ListresultGrListDtlParam = model.selectGrListDtlbundling(id);
        for (ListresultGrListDtlParam ListresultGrListDtlParam1 : ListresultGrListDtlParam) {
            Listcell receive_id = new Listcell();
            Listcell receive_line = new Listcell();
            Listcell po_header_id = new Listcell();
            Listcell po_header_no = new Listcell();
            Listcell po_line_id = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_line = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell city_id = new Listcell();
            Listcell city_code = new Listcell();
            Listcell location = new Listcell();
            Listcell hlr_map_id = new Listcell();
            Listcell hlr = new Listcell();
            Listcell unit_of_measure = new Listcell();
            Listcell qty_order = new Listcell();
            Listcell qty_delivered = new Listcell();
            Listcell qty_deliver = new Listcell();
            Listcell qty_receive = new Listcell();
            Listcell qty_accept = new Listcell();
            Listcell qty_approved = new Listcell();
            Listcell qty_ttl_rcv = new Listcell();
            Listcell outstanding = new Listcell();
            Listcell qty_cancel = new Listcell();
            Listcell lot_id = new Listcell();
            Listcell date_lot = new Listcell();
            Listcell expiry_date = new Listcell();
            Listcell po_distribution_id = new Listcell();
            Listcell lot_size = new Listcell();
            Listcell reason_id = new Listcell();
            Listcell receive_line_status = new Listcell();
            Listcell receive_dtl_id = new Listcell();
            Listcell create_by = new Listcell();
            Listcell create_date = new Listcell();
            Listcell update_by = new Listcell();
            Listcell update_date = new Listcell();
            Listcell wh_loc_id = new Listcell();
            Listcell wh_loc_code = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell lineref = new Listcell();

            receive_id.setLabel(ListresultGrListDtlParam1.getReceive_id());
            receive_line.setLabel(ListresultGrListDtlParam1.getReceive_line());
            po_header_id.setLabel(ListresultGrListDtlParam1.getPo_header_id());
            po_header_no.setLabel(ListresultGrListDtlParam1.getPo_header_no());
            po_line_id.setLabel(ListresultGrListDtlParam1.getPo_line_id());
            supp_delivery_id.setLabel(ListresultGrListDtlParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListresultGrListDtlParam1.getSupp_delivery_line());
            item_id.setLabel(ListresultGrListDtlParam1.getItem_id());
            item_code.setLabel(ListresultGrListDtlParam1.getItem_code());
            item_description.setLabel(ListresultGrListDtlParam1.getItem_description());
            city_id.setLabel(ListresultGrListDtlParam1.getCity_id());
            city_code.setLabel(ListresultGrListDtlParam1.getCity_code());
            location.setLabel(ListresultGrListDtlParam1.getLocation());
            hlr_map_id.setLabel(ListresultGrListDtlParam1.getHlr_map_id());
            hlr.setLabel(ListresultGrListDtlParam1.getHlr());
            unit_of_measure.setLabel(ListresultGrListDtlParam1.getUnit_of_measure());
            qty_order.setLabel(ListresultGrListDtlParam1.getQty_order());
            qty_delivered.setLabel(ListresultGrListDtlParam1.getQty_delivered());
            qty_deliver.setLabel(ListresultGrListDtlParam1.getQty_deliver());
            qty_receive.setLabel(ListresultGrListDtlParam1.getQty_receive());
            qty_accept.setLabel(ListresultGrListDtlParam1.getQty_accept());
            qty_approved.setLabel(ListresultGrListDtlParam1.getQty_approved());
            qty_ttl_rcv.setLabel(ListresultGrListDtlParam1.getQty_ttl_rcv());
            outstanding.setLabel(ListresultGrListDtlParam1.getOutstanding());
            qty_cancel.setLabel(ListresultGrListDtlParam1.getQty_cancel());
            lot_id.setLabel(ListresultGrListDtlParam1.getLot_id());
            date_lot.setLabel(ListresultGrListDtlParam1.getDate_lot());
            expiry_date.setLabel(ListresultGrListDtlParam1.getExpiry_date());
            po_distribution_id.setLabel(ListresultGrListDtlParam1.getPo_distribution_id());
            lot_size.setLabel(ListresultGrListDtlParam1.getLot_size());
            reason_id.setLabel(ListresultGrListDtlParam1.getReason_id());
            receive_line_status.setLabel(ListresultGrListDtlParam1.getReceive_line_status());
            receive_dtl_id.setLabel(ListresultGrListDtlParam1.getReceive_dtl_id());
            create_by.setLabel(ListresultGrListDtlParam1.getCreate_by());
            create_date.setLabel(ListresultGrListDtlParam1.getCreate_date());
            update_by.setLabel(ListresultGrListDtlParam1.getUpdate_by());
            update_date.setLabel(ListresultGrListDtlParam1.getUpdate_date());
            wh_loc_id.setLabel(ListresultGrListDtlParam1.getWh_loc_id());
            wh_loc_code.setLabel(ListresultGrListDtlParam1.getWh_loc_code());
            regional_id.setLabel(ListresultGrListDtlParam1.getRegional_id());
            regional_description.setLabel(ListresultGrListDtlParam1.getRegional_description());
            lineref.setLabel(ListresultGrListDtlParam1.getPo_line_ref());
//            try{
//            txtlotSize.setValue(ListresultGrListDtlParam1.getLot_size());
//            }
//            catch(Exception e){
//                txtlotSize.setValue("0");
//            }
//            txtlotSize.setWidth("110px");
//            txtlotSize.setStyle("text-align: right");
//            txtlotSize.setConstraint("no negative");
//            if (!txtstatusid.getValue().equals("1")) {
//                txtlotSize.setReadonly(true);
//            }
//            lot_size.appendChild(txtlotSize);

            qty_order.setStyle("text-align: right");
            qty_delivered.setStyle("text-align: right");
            qty_deliver.setStyle("text-align: right");
            qty_receive.setStyle("text-align: right");
            qty_accept.setStyle("text-align: right");
            qty_approved.setStyle("text-align: right");
            qty_ttl_rcv.setStyle("text-align: right");
            outstanding.setStyle("text-align: right");
            qty_cancel.setStyle("text-align: right");

            Listitem listitem = new Listitem();
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_line);
            listitem.appendChild(po_header_id);
            listitem.appendChild(po_header_no);
            listitem.appendChild(po_line_id);
            listitem.appendChild(supp_delivery_id);//5
            listitem.appendChild(supp_delivery_line);
            listitem.appendChild(item_id);
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(city_id);
            listitem.appendChild(city_code);//11
            listitem.appendChild(location);
            listitem.appendChild(hlr_map_id);
            listitem.appendChild(hlr);
            listitem.appendChild(unit_of_measure);
            listitem.appendChild(qty_order);//16
            listitem.appendChild(qty_delivered);
            listitem.appendChild(qty_deliver);
            listitem.appendChild(qty_receive);
            listitem.appendChild(qty_accept);
            listitem.appendChild(qty_approved);//21
            listitem.appendChild(qty_ttl_rcv);
            listitem.appendChild(outstanding);
            listitem.appendChild(qty_cancel);
            listitem.appendChild(lot_id);
            listitem.appendChild(date_lot);//26
            listitem.appendChild(expiry_date);
            listitem.appendChild(po_distribution_id);
            listitem.appendChild(lot_size);
            listitem.appendChild(reason_id);
            listitem.appendChild(receive_line_status);//31
            listitem.appendChild(receive_dtl_id);
            listitem.appendChild(create_by);
            listitem.appendChild(create_date);
            listitem.appendChild(update_by);
            listitem.appendChild(update_date);//36
            listitem.appendChild(wh_loc_id);
            listitem.appendChild(wh_loc_code);
            listitem.appendChild(regional_id);
            listitem.appendChild(regional_description);
            listitem.appendChild(lineref);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    selectRow = (Listitem) t.getTarget();
                }
            });

            txtlotSize.addEventListener(Events.ON_BLUR, new EventListener() {
                public void onEvent(final Event t) throws Exception {
                    if (qtyTemp.equalsIgnoreCase(txtlotSize.getValue().toString().replace(",", "").replace("-", ""))) {
                        return;
                    }
                    if (!txtstatus.getValue().equalsIgnoreCase("Draft")) {
                        return;
                    }
                    EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
                        @Override
                        public void onEvent(Messagebox.ClickEvent event) throws Exception {
                            if (Messagebox.Button.YES.equals(event.getButton())) {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                                System.out.println("edit qty");
                                System.out.println(t.getTarget().getParent().getParent());
                                selectRow = (Listitem) t.getTarget().getParent().getParent();
                                System.out.println(t.getTarget());
                                String rcvDtlId = ((Listcell) selectRow.getChildren().get(32)).getLabel();

                                Map<String, Object> map = model.doGrSetLotSizebundling(txtsuppdeliveryid.getValue(), rcvDtlId, txtlotSize.getValue().toString().replace(",", ""));
                                if (map.get("outError").toString().equals("0")) {
                                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    refreshDtl(txtsuppdeliveryid.getValue());
                                } else {
                                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);

                                }

                            }
                        }
                    };
                    Messagebox.show("Are you sure want to Edit this Lot Size?", "Goods Reciept", new Messagebox.Button[]{
                        Messagebox.Button.YES, Messagebox.Button.NO
                    }, Messagebox.QUESTION, clickListener);

//                        refreshDtl(txtsuppdeliveryid.getValue());
                }
            });

            listbox.appendChild(listitem);
        }

        formValidate();

    }

    @Listen("onClick=#Edit")
    public void editSNForm() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("EDIT_SN");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
//        System.out.println("openFormSR");
            BundlingSelectRangeGr slcRangeGRCtrl = new BundlingSelectRangeGr();
            slcRangeGRCtrl.setBundlingGoodsReceipt(this);
            String lineNo = "";
            String gdId = "";
            String gdLine = "";
            String inItemid = "";
            String inQty = "";
            String inOrderedQty = "";
            String inWhId = "";
            String inCityid = "";
            String inHlrMapId = "";
            String inOrderDate = "";
            String inPoPriceUnit = "";
            String inwoLine = "";
            String inregId = "";
            String inItemCode = "";
            String rcvDtlId = "";
            String delivQty = "";
            String lotId = "";
            String Qtyreceive = "";
            String Qtyreject = "";
            try {
                List<Component> listCells = listbox.getSelectedItem().getChildren();
                lineNo = ((Listcell) listCells.get(1)).getLabel();
                inwoLine = ((Listcell) listCells.get(41)).getLabel();
                gdId = ((Listcell) listCells.get(5)).getLabel();
                gdLine = ((Listcell) listCells.get(6)).getLabel();
                inItemid = ((Listcell) listCells.get(7)).getLabel();
                inItemCode = ((Listcell) listCells.get(8)).getLabel();
                inQty = ((Listcell) listCells.get(17)).getLabel();
                inOrderedQty = ((Listcell) listCells.get(21)).getLabel();
                inregId = ((Listcell) listCells.get(39)).getLabel();
                rcvDtlId = ((Listcell) listCells.get(32)).getLabel();
                lotId = ((Listcell) listCells.get(27)).getLabel();
                Qtyreceive = ((Listcell) listCells.get(19)).getLabel();
                System.out.println("lineNo" + lineNo);
                System.out.println("inwoLine" + inwoLine);
                System.out.println("gdId" + gdId);
                System.out.println("gdLine" + gdLine);
                System.out.println("inItemid" + inItemid);
                System.out.println("inItemCode" + inItemCode);
                System.out.println("inOrderedQty" + inOrderedQty);
                System.out.println("inQty" + inQty);
                System.out.println("inregId" + inregId);
                System.out.println("rcvDtlId" + rcvDtlId);
                System.out.println("lotId" + lotId);
                System.out.println("Qtyreceive" + Qtyreceive);
            } catch (Exception e) {
                Messagebox.show(" Choose list first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            slcRangeGRCtrl.setRcvId(txtsuppdeliveryid.getValue());
            slcRangeGRCtrl.setRcvLine(lineNo);
            slcRangeGRCtrl.setGdId(gdId);
            slcRangeGRCtrl.setGdLine(gdLine);
            slcRangeGRCtrl.setInItemid(inItemid);
            slcRangeGRCtrl.setInQty(inQty.toString().replace(",", ""));
            slcRangeGRCtrl.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
            slcRangeGRCtrl.setInWhId(txtwhid.getValue());
            slcRangeGRCtrl.setInCityid(inCityid);
//        slcRangeGRCtrl.setInHlrMapId(inHlrMapId);
            slcRangeGRCtrl.setInOrderDate(inOrderDate);
            slcRangeGRCtrl.setInPoPriceUnit(inPoPriceUnit);
            slcRangeGRCtrl.setStatus(txtstatusid.getValue());
            slcRangeGRCtrl.setBuId(txtbuId.getValue());
            slcRangeGRCtrl.setPoLineRef(inwoLine);
            slcRangeGRCtrl.setPoId(txtpoid.getValue());
            slcRangeGRCtrl.setInregId(inregId);
            slcRangeGRCtrl.setInItemCode(inItemCode);
            slcRangeGRCtrl.setPoNo(txtpo.getValue());
            slcRangeGRCtrl.setRcvDtlId(rcvDtlId);
            slcRangeGRCtrl.setLotId(lotId);
            slcRangeGRCtrl.setQtyReceive(Qtyreceive);
            System.out.println(selectRow);
            Map<String, Object> map = new HashMap<>();
            map.put("controller", slcRangeGRCtrl);
            Window window = (Window) Executions.createComponents("/Tcash/BundlingSelectRangeGD.zul", null, map);
            window.doModal();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#save")
    public void save() {

//        if (txtstatus.getValue().equalsIgnoreCase("Draft")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("confirm receipt")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("CANCELLED")) {
//             Messagebox.show("This transaction has been Canceled.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
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
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                String modul = "INSERT";
                                if (!txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
                                    modul = "UPDATE";
                                }
                                Map<String, Object> map = model.doGrSaveHdrbundling(modul, txtsuppdeliveryid.getValue(),
                                        txtpoid.getText(),
                                        txtWO.getText(),
                                        txtwhid.getText(),
                                        txtbuId.getText(),
                                        txtGdId.getText(),
                                        txtsupid.getText(),
                                        txtsupliersiteid.getText(),
                                        userId,
                                        "1");
                                if (map.get("outError").toString().equalsIgnoreCase("0")) {
                                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.INFORMATION);
                                    if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
                                        txtsuppdeliveryid.setText(map.get("outReceiveId").toString());
                                    }
                                    selectlisthdr();
                                    display();
                                } else {
                                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void formAddGDLine() {

        BundlingGoodsReceiptDetail BundlingGoodsReceiptDetail = new BundlingGoodsReceiptDetail();
        BundlingGoodsReceiptDetail.setBundlingGoodsReceipt(this);
        BundlingGoodsReceiptDetail.setPoId(txtpoid.getValue());
        BundlingGoodsReceiptDetail.setGdHdrId(txtGdId.getValue());
        BundlingGoodsReceiptDetail.setGrId(txtsuppdeliveryid.getValue());
        BundlingGoodsReceiptDetail.setPoNo(txtWO.getValue());
        BundlingGoodsReceiptDetail.setWhId(txtwhid.getValue());
        System.out.println("GD" + txtGdId.getText());
        System.out.println("po" + txtpoid.getText());
        System.out.println("grid" + txtsuppdeliveryid.getText());
        Map<String, Object> map = new HashMap<>();
        map.put("controller", BundlingGoodsReceiptDetail);
        Window window = (Window) Executions.createComponents("/Tcash/BundlingGoodsReceiptDetail.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#btnadd")
    public void adddetail() {
//        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Approved")) {
//            Messagebox.show("This transaction has been Approved.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("Confirm Receipt")) {
//            Messagebox.show("This transaction has been Confirm Receipt.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("ADD");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            if (txtsuppdeliveryid.getText().equalsIgnoreCase("")) {
                save();
//                 selectlisthdr();
            } else {
                Map<String, Object> map = model.doGrSelectGdValidatebundling(txtGdId.getValue(), txtstatusid.getValue());
                if (map.get("outError").toString().equalsIgnoreCase("0")) {
                    formAddGDLine();
                } else {
                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    System.out.println("no");
                    return;
                }
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#submit")
    public void submitButton() {

//        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if(txtstatus.getValue().equalsIgnoreCase("Confirm receipt")){
//            Messagebox.show("This transaction has been Confirm Receipt.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//        if(txtstatus.getValue().equalsIgnoreCase("Approved")){
//            Messagebox.show("This transaction has been Approved.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//        if(txtstatus.getValue().equalsIgnoreCase("canceled")){
//            Messagebox.show("This transaction has been Canceled.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//         if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail good receipt first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("CONFIRM");

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
                                validateSubmit();
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void validateConfirmLine() {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();
//        try{
            String lineNo = ((Listcell) listCells.get(1)).getLabel();
            String itemId = ((Listcell) listCells.get(7)).getLabel();
            String itemCode = ((Listcell) listCells.get(8)).getLabel();
            String gdId = ((Listcell) listCells.get(5)).getLabel();
            String gdLineNo = ((Listcell) listCells.get(6)).getLabel();
            String qtyRecieve = ((Listcell) listCells.get(19)).getLabel();
            String whLocId = ((Listcell) listCells.get(37)).getLabel();
            String whLoc = ((Listcell) listCells.get(38)).getLabel();

            Map<String, Object> map = model.doGrConfirmRcvValidateLinebundling(txtsuppdeliveryid.getValue(), lineNo, qtyRecieve.toString().replace(",", ""), gdId,
                    gdLineNo, itemId, itemCode, whLoc, whLocId);
            if (map.get("outError").toString().equals("0")) {
//                  Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.INFORMATION);
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
//        }catch(IndexOutOfBoundsException e){
//            Messagebox.show("Please Select detail for confirm receive","Goods Receive",Messagebox.OK,Messagebox.EXCLAMATION);
//        }
        }
        submit();
    }

    public void validateSubmit() {
        System.out.println("validate Submit");
        Map<String, Object> map = model.doGrConfirmRcvValidatebundling(txtsuppdeliveryid.getValue(), userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        System.out.println(map.get("outError").toString());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            validateConfirmLine();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

    }

    public void submit() {
//        System.out.println("Submit");
        Map<String, Object> map = model.doGrConfirmReceivebundling(txtsuppdeliveryid.getValue(), userId);
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.INFORMATION);
            selectlisthdr();
            refreshDtl(txtsuppdeliveryid.getValue());
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#approve")
    public void approveButton() {
//        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail good receipt first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if(txtstatus.getValue().equalsIgnoreCase("Approved")){
//            Messagebox.show("Goods Reciept status has been Approved.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("APPROVE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            grApproveValidate();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void grApproveValidate() {
        Map<String, Object> map = model.doGrApproveValidatebundling(userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
            grApproveValidateLine();
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    public void grApproveValidateLine() {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();

            String lineNo = ((Listcell) listCells.get(1)).getLabel();
            String gdId = ((Listcell) listCells.get(5)).getLabel();
            String gdLineNo = ((Listcell) listCells.get(6)).getLabel();
            String itemId = ((Listcell) listCells.get(7)).getLabel();
            String itemCode = ((Listcell) listCells.get(8)).getLabel();
            String rcvQty = ((Listcell) listCells.get(19)).getLabel();
            String whLoc = ((Listcell) listCells.get(37)).getLabel();
            String whLocCode = ((Listcell) listCells.get(38)).getLabel();
            Map<String, Object> map = model.doGrApproveValidateLinebundling(txtsuppdeliveryid.getValue(), lineNo, rcvQty.toString().replace(",", ""),
                    gdId, gdLineNo, itemId, itemCode, whLoc, whLocCode);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
               
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
        approve();
    }

    public void approve() {
        Messagebox.show("Are you sure want to Approve ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                            Map<String, Object> map = model.doGrApprovebundling(txtsuppdeliveryid.getValue(), txtpoid.getValue(),
                                    userId);
                            if (map.get("outError").toString().equalsIgnoreCase("0")) {
                                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.INFORMATION);
                                selectlisthdr();
                                refreshDtl(txtsuppdeliveryid.getValue());

                            } else {
                                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                return;
                            }
                        } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                        }
                    }
                });

    }

    public void updateHdr() {
        Map<String, Object> map = model.doGrSaveHdrbundling("UPDATE", txtsuppdeliveryid.getValue(),
                txtpoid.getText(),
                txtWO.getText(),
                txtwhid.getText(),
                txtbuId.getText(),
                txtGdId.getText(),
                txtsupid.getText(),
                txtsupliersiteid.getText(),
                userId,
                txtstatusid.getText());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#cancel")
    public void cancelButton() {
//        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        
//         if(txtstatus.getValue().equalsIgnoreCase("Confirm Receipt")){
//            Messagebox.show("This transaction has been Confirmed Receipt.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//         if(txtstatus.getValue().equalsIgnoreCase("Approved")){
//            Messagebox.show("This transaction has been Approved.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
//         if(txtstatus.getValue().equalsIgnoreCase("canceled")){
//            Messagebox.show("This transaction has been canceled.", "Message", 1, "z-msgbox z-msgbox-exclamation");
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("CANCEL");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {

            if (listbox.getItemCount() == 0) {
                gdCancel("0");
            } else {
                if (gdCancelValidate() == 0) {
                    if (gdCancelValidateLine() == 0) {
                        gdCancel("1");
                    }
                }
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public int gdCancelValidate() {
        Map<String, Object> map = model.doGrCancelValidatebundling(userId, responsibilityId,
                txtbuId.getValue(), txtwhid.getValue(), txtstatusid.getValue());
        if (map.get("outError").toString().equalsIgnoreCase("0")) {
        } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        }
        return 0;
    }

    public int gdCancelValidateLine() {
        for (int i = 0; i < listbox.getItemCount(); i++) {
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();

            String lineNo = ((Listcell) listCells.get(1)).getLabel();
            String gdId = ((Listcell) listCells.get(5)).getLabel();
            String gdLineNo = ((Listcell) listCells.get(6)).getLabel();
            String itemId = ((Listcell) listCells.get(7)).getLabel();
            Map<String, Object> map = model.doGrCancelLine(txtsuppdeliveryid.getValue(), lineNo,
                    gdId, gdLineNo, itemId);
            if (map.get("outError").toString().equalsIgnoreCase("0")) {
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return 1;
            }
        }
        return 0;
    }

    public int gdCancel(final String flag) {
        Messagebox.show("Are you sure want to cancel ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                            Map<String, Object> map = model.doGrCancelbundling(txtsuppdeliveryid.getValue(), flag);
                            if (map.get("outError").toString().equalsIgnoreCase("0")) {
                                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                selectlisthdr();
                            } else {
                                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                        }
                    }
                });

        return 0;
    }

    @Listen("onClick=#delete")
    public void deleteBtn() {

//        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
//            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (listbox.getItemCount() == 0) {
//            Messagebox.show("Please add detail goods receipt first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("approved")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("confirm receipt")) {
//            Messagebox.show("This transaction has been " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getValue().equalsIgnoreCase("CANCELLED")) {
//             Messagebox.show("This transaction has been Canceled.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("DELETE");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            Messagebox.show("Are you sure want to delete ?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        @Override
                        public void onEvent(Event e) throws ParseException {
                            if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {
                                String id = "";
                                String lineNo = "";
                                String gdId = "";
                                String gdLineNo = "";
                                String delivQty = "";
                                try {
                                    List<Component> listCells = listbox.getSelectedItem().getChildren();

                                    id = ((Listcell) listCells.get(32)).getLabel();
                                    lineNo = ((Listcell) listCells.get(1)).getLabel();
                                    gdId = ((Listcell) listCells.get(5)).getLabel();
                                    gdLineNo = ((Listcell) listCells.get(6)).getLabel();
                                    delivQty = ((Listcell) listCells.get(19)).getLabel();
                                } catch (Exception ea) {
                                    Messagebox.show("Please Choose detail first.");
                                    return;
                                }

                                Map<String, Object> map = model.doGrDeleteDtlbundling(id, delivQty.toString().replace(",", ""), gdId, gdLineNo, userId);
                                if (map.get("outError").toString().equalsIgnoreCase("0")) {
//                        Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    selectlisthdr();
                                    refreshDtl(txtsuppdeliveryid.getValue());
                                } else {
                                    Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {
                            }
                        }
                    });
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#preview")
    public void printWo() throws IOException {
        if (txtsuppdeliveryid.getValue().equalsIgnoreCase("")) {
            Messagebox.show("Please save this transaction first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (listbox.getItemCount() == 0) {
            Messagebox.show("Please add detail goods receipt first", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtstatus.getValue().equalsIgnoreCase("Draft")) {
            Messagebox.show("This transaction status is still " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtstatus.getValue().equalsIgnoreCase("confirm receipt")) {
            Messagebox.show("This transaction status is still " + txtstatus.getValue(), "Goods Reciept : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        
        if (txtstatus.getValue().equalsIgnoreCase("CANCELED")) {
            Messagebox.show("This transaction has been Canceled.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListBundlingGoodsReceipt.zul");
        cf.setFunctionName("PREVIEW");

        //OutErrMsg oe = new OutErrMsg();
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String subreport = properties.getProperty("subreporthrn");
            String logo = properties.getProperty("logo_hrn");
            System.out.println(txtsuppdeliveryid.getText());
            if ((!this.txtsuppdeliveryid.getText().equalsIgnoreCase("")) && (txtsuppdeliveryid.getText() != null)) {
                HashMap map = new HashMap();
                map.put("p_rcv_id", txtsuppdeliveryid.getText());
                map.put("SUBREPORT_DIR", subreport);
                map.put("logo", logo);
                System.out.println(map);
                JRreportWindow jRreportWindow = new JRreportWindow(this.win_Good_Reciept_bundling, true, map, "/reports/GR_Tcash_Report_bundling.jasper", "pdf", "Print Data Good Reciept", "Data Goods Reciept");
            } else {
                Messagebox.show("Please save this transaction first.", "Good Receipt : Message", 1, "z-msgbox z-msgbox-exclamation");
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnrefresh")
    public void refreshAll() throws ParseException {
        selectlisthdr();
        refreshDtl(txtsuppdeliveryid.getValue());
    }

    public void mandatory() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtWhLocCode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtWO.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtGd.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtGdId.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtlotSize.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    public void display() {
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtwhcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupliersitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtWhLocCode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtWO.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtGd.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtGdId.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtlotSize.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
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
        txtsuppdeliverydate.setText("");
        txtsupid.setText("");
        txtsupcode.setText("");
        txtsupdesc.setText("");
        txtsupliersiteid.setText("");
        txtsupliersitecode.setText("");
        txtsupliersitedesc.setText("");
        txtWO.setText("");
        txtsuppdeliveryno.setText("");
        txtstatus.setText("");
        txtstatusid.setText("1");
        txtcreationdate.setText("");
        txtcreationby.setText("");
        txtApproveBy.setText("");
        txtApproveDate.setText("");
        txtConfirmedDate.setText("");
        txtConfirmedby.setText("");
//        txtmodifiedby.setText("");
//        txtmodifieddate.setText("");
        txtgooddeliverynocode.setText("");
        txtGdId.setText("");
        txtGd.setText("");
        listbox.getItems().clear();
        mandatory();
        btnbu.setDisabled(false);
        btnwh.setDisabled(false);
        btnsup.setDisabled(false);
        btnsite.setDisabled(false);
        btnwo.setDisabled(false);
        btngd.setDisabled(false);
    }

}
