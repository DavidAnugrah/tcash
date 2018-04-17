/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListOfBundlingFind  extends SelectorComposer<Component>{
        public static final byte DRAFT = 1;
        public static final byte SUBMITTED = 2;
        public static final byte APPROVED = 3;
        public static final byte CANCELED = 4;
        public static final String DESC[] = {"DRAFT","SUBMIT","APPROVED","CANCEL"};
        public static final int CODE[] = {DRAFT,SUBMITTED, APPROVED,CANCELED};
        public static final SimpleDateFormat DATE_FORMAT_ID = new SimpleDateFormat("dd-MM-yyyy");
        
    @Wire
    Window win_list_find; 
    
    @Wire
    Combobox cmbStatus;
    
    @Wire
    Textbox txtofid,txtofNo,txtWoNo;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
   
   ListOfBundling parentController;
   
   @Listen("onCreate=#win_list_find")
   public void onCreateWindow(){
       cmbStatus.setModel(new ListModelList<>(DESC));
       parentController =(ListOfBundling)win_list_find.getAttribute("parentController");
   }
    @Listen("onClick=#Close1")
    public void closeWindow(){
        win_list_find.detach();
    }
    
    @Listen("onClick=#goFind")
    public void buttonFind(){
       Map<String,Object>map = new HashMap<>(); 
       map.put("ofno","%"+ txtofNo.getValue().trim()+"%");
       map.put("purchaseorder", (txtWoNo.getText().trim().equalsIgnoreCase("")||txtWoNo.getText()== null) ? null: txtWoNo.getText().trim());
        if (txtDateFrom.getValue() != null && txtDateTo.getValue() !=null) {
             map.put("dateStart", DATE_FORMAT_ID.format(txtDateFrom.getValue()));
             map.put("dateTo", DATE_FORMAT_ID.format(txtDateTo.getValue()));
        } else {
            map.put("dateStart", null);
            map.put("dateTo", null);
        }
       map.put("statusfnd",(cmbStatus.getSelectedIndex() == -1) ? null: DESC[cmbStatus.getSelectedIndex()]);
       
       parentController.doFind(map);
       win_list_find.detach();
    }
    
    
    
  
}
