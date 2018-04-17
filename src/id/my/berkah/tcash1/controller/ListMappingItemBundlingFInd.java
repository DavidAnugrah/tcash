/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListHDRMappingBDL;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListMappingItemBundlingFInd extends SelectorComposer<Component>{
      @Wire
    Window win_list_mapping_find;
    
      @Wire
    Listbox listbox;
    
      @Wire
      Textbox txtid,txtitemcode,txtitemdesc;
    ModelTcashCTLR model=new ModelTcashCTLR();
    
   ListMappingItem parentControl;
   
   @Listen("onCreate=#win_list_mapping_find")
   public void oncreateWindow(){
       parentControl=(ListMappingItem)win_list_mapping_find.getAttribute("parentControl");
   }
      @Listen("onClick=#goFind")
    public void filterSearch(){
      parentControl.doFind(txtid.getText());
        clearparam();
        win_list_mapping_find.detach();
    }
    
        @Listen("onClick=#btnitemcode")
    public void LovItemBunling(){
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\", item_description as \"Description\"  from table(pkg_tcash_mapping_bdl.LovItemBundling(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_mapping_bdl.LovItemBundling(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1,2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtid, txtitemcode,txtitemdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_mapping_find);
        w.doModal();
    }
    
    @Listen("onClick=#Clear")
     void clearparam(){
        txtid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
    }
     
      @Listen("onClick=#Close1")
   public void closeFind(){
       win_list_mapping_find.setVisible(false);
   }
   
}
