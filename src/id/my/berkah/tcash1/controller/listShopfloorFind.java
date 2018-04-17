/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class listShopfloorFind extends SelectorComposer<Component> {
     private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String buId =  global[5];
    
    @Wire
    Window win_list_shopfloor_find;
    
    @Wire
    Textbox txtBuid, txtBucode,txtBudesc,txtsfcid, txtsfcno,txtsfcdate,txtitemid, txtitemcode,txtitemdesc, txtwoid,txtwodesc,txtwhid, txtwhcode,txtwhesc,
            txtwotypefnd,txtstatusfnd;
    
     
    @Wire
    Combobox cmbstatus,cmbwo;
    
    ModelTcashCTLR model;
    
    @Wire
    Datebox  dateboxfrom,dateboxto;
    
    ListShopFloor parenCTRL;
    @Listen("onCreate=#win_list_shopfloor_find")
    public void oncreatefind(){
    model= new ModelTcashCTLR();
        parenCTRL = (ListShopFloor)win_list_shopfloor_find.getAttribute("parenCTRL");
    }
    
    @Listen("onClick=#Close1")
    public void closeWindowFind(){
        win_list_shopfloor_find.detach();
    }
    
    @Listen("onClick=#goFind")
    public void findPAram(){
    parenCTRL.doFind(txtBuid.getText(),txtsfcid.getText(),dateboxfrom.getText(),dateboxto.getText(),txtwoid.getText(),txtwotypefnd.getText(),txtitemid.getText(),txtwhid.getText(),txtstatusfnd.getText());
 
    clearparam();
    win_list_shopfloor_find.detach();
    }
    
    
      @Listen("onClick=#btnbu")
    public void loVBu(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_id as \"Id\",bu_code as \"Bussiness Code\",bu_description as\"Description\" from table(pkg_tcash_shopfloor.LovBU('','"+responsibilityId+"','"+userId+"'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovBU('','"+responsibilityId+"','"+userId+"'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtBuid, txtBucode,txtBudesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of Business Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_shopfloor_find);
        w.doModal();
    }
    
    
      @Listen("onClick=#btnsfc")
    public void loVshopfloor(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, SFC_ID as \"Id\",SFC_NO as \"ShopFloor No\",SFC_DATE as\"Date\" from table(pkg_tcash_shopfloor.LovShopFloor(''))where (upper(SFC_NO) like upper('?') or upper(SFC_DATE) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovShopFloor(''))Where SFC_NO LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtsfcid, txtsfcno,txtsfcdate});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of Shopfloor");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_shopfloor_find);
        w.doModal();
    }
    
   // @Listen("onClick=#btnitem")
    public void loVitemFinishGoods(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\", item_description as \"Description\" ,unit as \"Unit\" from table(pkg_tcash_shopfloor.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode,txtitemdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_shopfloor_find);
        w.doModal();
    }
    
    @Listen("onClick=#btnwo")
    public void loVWo(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, po_id as \"Id\",purchase_order as \"Work Order\",item_id as\"Item Id\",item_code as \"Item Code\",item_description as\"Description\",quantity_order as \"qty\" from table(pkg_tcash_shopfloor.LovWO(''))where (upper(po_id) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovWO(''))Where purchase_order LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1,2,3,4,5,6});
        composerLov.setComponentTransferData(new Textbox[]{ txtwoid,txtwodesc ,txtitemid,txtitemcode,txtitemdesc});
        composerLov.setHiddenColumn(new int[]{0, 1,3});

      
        composerLov.setTitle("List Of Work Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_shopfloor_find);
        w.doModal();
    }
    
    @Listen("onClick=#btnwh")
    public void loVWip(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Id\",wh_code as \"Warehouse Code\", wh_description as \"Description\"  from table(pkg_tcash_shopfloor.LovWh('','"+buId+"','"+userId+"','"+responsibilityId+"'))where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovWh('','"+buId+"','"+userId+"','"+responsibilityId+"'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode,txtwhesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of WIP Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_shopfloor_find);
        w.doModal();
    }
   
    
    void clearparam(){
        txtBuid.setText("");
        txtBucode.setText("");
        txtBudesc.setText("");
        txtsfcid.setText("");
        txtsfcno.setText("");
        txtsfcdate.setText("");
        dateboxfrom.setText(null);
        dateboxto.setText(null);
        txtwoid.setText("");
        txtwodesc.setText("");
        txtwotypefnd.setText("");
        cmbwo.setSelectedIndex(0);
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhesc.setText("");
        txtstatusfnd.setText("");
        cmbstatus.setSelectedIndex(0);
    }
    
      @Listen("onSelect=#cmbwo")
    public void selectCombowotype(){
        if (cmbwo.getSelectedIndex()==0) {
            txtwotypefnd.setText("");
        } else if (cmbwo.getSelectedIndex()==1){
            txtwotypefnd.setText("1");
        } else if (cmbwo.getSelectedIndex()==2){
            txtwotypefnd.setText("2");
        } else{
            CHelper.ShowMessage("Wo Type Is Not Available", Messagebox.EXCLAMATION);
        }
}
    
      @Listen("onSelect=#cmbstatus")
    public void selectCombobox(){
        if (cmbstatus.getSelectedIndex()==0) {
            txtstatusfnd.setText("");
        } else if (cmbstatus.getSelectedIndex()==1){
            txtstatusfnd.setText("0");
        } else if (cmbstatus.getSelectedIndex()==2){
            txtstatusfnd.setText("1");
        } else if (cmbstatus.getSelectedIndex()==3){
            txtstatusfnd.setText("3");
        } else{
            CHelper.ShowMessage("Status Is Not Available", Messagebox.EXCLAMATION);
        }
}
}
