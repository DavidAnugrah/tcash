/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.IDefines;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListOutputFilefind extends SelectorComposer<Component>{
    @Wire
    Window win_list_find;
    
    @Wire
    Textbox txtofNo,txtWoNo,txtWoid;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
            
    @Wire
    Combobox cmbStatus;
    
    ListOutputFile parent;
    
    @Listen("onCreate=#win_list_find")
    public void oncreateWindow(){
        parent = (ListOutputFile) win_list_find.getAttribute("parentController");
        conboBox();
    }
    
    
    public void conboBox(){
        cmbStatus.setModel(new ListModelList<>(IDefines.RETURN_STATUS.DESC));
    }
    
    @Listen("onClick=#goFind")
    public void filter(){
        Map<String,Object>map =new HashMap<String,Object>();
        map.put("ofno","%"+txtofNo.getText().trim()+"%");
        map.put("purchaseorder", txtWoNo.getText().trim() == null||txtWoNo.getText().trim().equals("") ?null:txtWoNo.getText());
        if (txtDateFrom.getValue() != null&&txtDateTo.getValue() != null) {
             map.put("dateStart", IDefines.DATE_FORMAT_ID.format(txtDateFrom.getValue()));
             map.put("dateTo", IDefines.DATE_FORMAT_ID.format(txtDateTo.getValue()));
        } else {
             map.put("dateStart", null);
             map.put("dateTo", null);
        }
        map.put("status", (cmbStatus.getSelectedIndex() ==-1) ? null : IDefines.RETURN_STATUS.CODE[cmbStatus.getSelectedIndex()]);
        parent.FindParam(map);
        win_list_find.detach();
       
    }
    
    @Listen("onClick=#Close1")
    public void closeWindow(){
        win_list_find.detach();
    }
    
}
