/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.IDefines;
import id.my.berkah.util.IDefines.RETURN_STATE_BUNDLING;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListBundlingFind extends SelectorComposer<Component>{
    @Wire
    Window win_list_find;
    
    @Wire
    Textbox txtitemid,txtitemcode,txtitemdesc,txtobundlingNo,txtobundlingid;
    
    @Wire
    Combobox cmbStatus;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
    
    ListBundling parentController;
    
    @Listen("onCreate=#win_list_find")
    public void  onCreateWindowFind(){
    Calendar cal = Calendar.getInstance();
    Calendar cal1 = Calendar.getInstance();
    cal.add(Calendar.DATE, -7);
    cal1.add(Calendar.DATE, 0);
    cmbStatus.setModel(new ListModelList<>(RETURN_STATE_BUNDLING.Desc));
    txtDateFrom.setValue(cal.getTime());
    txtDateTo.setValue(cal1.getTime());
    
    parentController=(ListBundling) win_list_find.getAttribute("parentController");
    }
    
    @Listen("onClick=#Close1")
    public void btnClose(){
     win_list_find.detach();
    }
    
       
       
        @Listen("onClick=#goFind")
      public void findParam(){
          Map map = new HashMap();
          map.put("bundling_no", "%"+txtobundlingNo.getText().trim() +"%");
            if (txtDateFrom.getValue() !=null && txtDateTo.getValue()!=null) {
                map.put("dateStart",IDefines.DATE_FORMAT_ID.format(txtDateFrom.getValue()));
                map.put("dateTo", IDefines.DATE_FORMAT_ID.format(txtDateTo.getValue()));
            } else {
                     map.put("dateStart",null);
                     map.put("dateTo", null);
            }
            map.put("statusId", cmbStatus.getSelectedIndex()==-1 ? null : RETURN_STATE_BUNDLING.Desc[cmbStatus.getSelectedIndex()]);
            System.out.println("map =="+map);
            parentController.goFind(txtobundlingid.getValue(), map);
            win_list_find.detach();
      }
        
        
}
