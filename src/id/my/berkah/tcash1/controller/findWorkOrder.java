/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.util.Calendar;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class findWorkOrder extends SelectorComposer<Component> {
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];
    
    
    @Wire
    Window   win_list_WO_find;
    
      @Wire
    Textbox txtbuId, txtbucode, txtbuDesc;
      
      @Wire
      Datebox txtDateFrom,txtDateTo;
     
      
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        txtDateFrom.setValue(cal.getTime());
    }
      
      
      
    @Listen("onClick=#btnLovBu")
    public void lovBu(){
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,BU_ID as \"BU Id\",BU_CODE as \"BU Code\", BU_DESCRIPTION as \"BU Description\" from (select bu_id,bu_code,bu_description from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + ")))\n"
                + "where (upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + ")) where \n"
                + "(upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbuId, txtbucode, txtbuDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of BU");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_WO_find);
        w.doModal();
    }
}
