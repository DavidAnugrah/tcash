/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.MVItemToVoucherParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class MappingITVCtrl extends SelectorComposer<Component>{
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    
    @Wire
    Window winmapItV;
    @Wire
    Textbox txtIdMap, txtItemId, txtItemCode,txtItemDesc,
            txtVoucherTypeCode, txtVoucherTypeDesc;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (txtIdMap.getValue() != "") {
            headerList();
        }
        else {
        }
    }
    
   
   public void headerList() {
        List<MVItemToVoucherParam> ListWoParam = model.selectMvListHdr(txtIdMap.getValue());
        txtItemId.setValue(ListWoParam.get(0).getItem_id());
        txtItemCode.setValue(ListWoParam.get(0).getItem_code());
        txtItemDesc.setValue(ListWoParam.get(0).getItem_desc());
        txtVoucherTypeCode.setValue(ListWoParam.get(0).getVoucher_type_code());
        txtVoucherTypeDesc.setValue(ListWoParam.get(0).getVoucher_type_desc());
    }
   
   @Listen("onClick=#btnLovItem")
    public void lovItem(){
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,item_id as \"Item Id\",item_code as \"Item Code\", item_description as \"Item Description\" from (select item_id,item_code,item_description from table(pkg_tcash_lov.LovItem('')))\n"
                + "where (upper(item_code) like upper('?') or upper(item_description) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovItem('')) where \n"
                + "(upper(item_code) like upper('?') or upper(item_description) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtItemId, txtItemCode, txtItemDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(winmapItV);
        w.doModal();
    }
   
   @Listen("onClick=#btnLovVoucher")
    public void LovVoucher(){
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,voucher_type_code as \"Voucher Type\", voucher_type_desc as \"Voucher Type Description\" from (select voucher_type_code,voucher_type_desc from table(pkg_tcash.LovVouchertype('')))\n"
                + "where (upper(voucher_type_code) like upper('?') or upper(voucher_type_desc) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash.LovVouchertype('')) where \n"
                + "(upper(voucher_type_code) like upper('?') or upper(voucher_type_desc) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px","150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtVoucherTypeCode, txtVoucherTypeDesc});
        composerLov.setHiddenColumn(new int[]{0});

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(winmapItV);
        w.doModal();
    }
    
   @Listen("onClick=#btnSave")
    public void btnsave() {

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtIdMap.getValue() == "") {
                        save();
                    }
                    else {
                         Messagebox.show("Data Can't be change", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "Message ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
   
   public void save(){
        Map<String, Object> map = model.doMvInsert(txtItemId.getValue(),
                txtItemCode.getValue(), txtItemDesc.getValue(),
                txtVoucherTypeCode.getValue(), txtVoucherTypeDesc.getValue(),userId);
        if (map.get("outError").toString().equals("0")) {
            txtIdMap.setValue(map.get("outId").toString());
            headerList();
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
    
   public void update(){
        Map<String, Object> map = model.domvUpdate(txtIdMap.getValue(),txtItemId.getValue(),
                txtItemCode.getValue(), txtItemDesc.getValue(),
                txtVoucherTypeCode.getValue(), txtVoucherTypeDesc.getValue(),userId);
        if (map.get("outError").toString().equals("0")) {
            headerList();
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   public void expired(){
        Map<String, Object> map = model.domvExpire(txtIdMap.getValue(),userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   @Listen("onClick=#btnExpired")
    public void expiredMap() {

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (!txtIdMap.getValue().equals("")) {
                        expired();
                    }
//                    else {
//                        Messagebox.show(map.get("outMessages").toString());
//                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Expired?", "Message ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
   
   @Listen("onClick=#btnNew")
   public void newTrc(){
       txtIdMap.setValue(""); 
       txtItemId.setValue(""); 
       txtItemCode.setValue("");
       txtItemDesc.setValue("");
    txtVoucherTypeCode.setValue("");
    txtVoucherTypeDesc.setValue("");
   }
   
   @Listen("onClick=#btnClose")
   public void close(){
       winmapItV.detach();
   }
    
}
