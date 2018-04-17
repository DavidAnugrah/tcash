/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListSetupWIPFind extends SelectorComposer<Component> {
      private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_list_wip_find;
    
    @Wire
    Textbox txtid,txtwipcode,txtwipdesc;
    
  ListSetupWIP parentController;
    
   @Listen("onCreate=#win_list_wip_find")
    public void onCreateWindow(){
       parentController =(ListSetupWIP)win_list_wip_find.getAttribute("parentController");
    }
          
    @Listen("onClick=#Close1")
    public void buttonClose(){
        win_list_wip_find.detach();
    }
    
    
    @Listen("onClick=#goFind")
    public void buttongoFind(){
        parentController.goFind(txtid.getText());
        txtid.setText(null);
        txtwipcode.setText(null);
        txtwipdesc.setText(null);
        win_list_wip_find.detach();
    }
    
    
    @Listen("onClick=#btnwip")
    public void lovWIP(){
             HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Id\",wh_code as \"Wh Code\", wh_description as \"Description\"  from table(pkg_tcash_setup.LovSearchWh('','','"+userId+"','"+responsibilityId+"'))where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_setup.LovSearchWh('','','"+userId+"','"+responsibilityId+"'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1,2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtid, txtwipcode,txtwipdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

      
        composerLov.setTitle("List Of WIP Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_wip_find);
        w.doModal();
    }
}
