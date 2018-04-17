/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import java.util.Calendar;
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
public class ListBundlingGoodsDeliveryFind extends SelectorComposer<Component> {
       public static final byte DRAFT = 1;
        public static final byte SUBMITTED = 2;
        public static final byte APPROVED = 3;
        public static final byte RECEIVED = 4;
        public static final byte CANCELED = 9;
        public static final String DESC[] = {"Draft","Submit","Approve","Received","Cancel"};
        public static final int CODE[] = {DRAFT,SUBMITTED, APPROVED,RECEIVED,CANCELED};
        
    @Wire
    Window win_list_GD_find;
    
     @Wire
    Textbox txtid,txtbundlingNo,txtGdid,txtGdNo;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
    
    @Wire
    Combobox cmbStatus;
    
 ListDeliveryGoodsBundling   parentController;
    
    @Listen("onCreate=#win_list_GD_find")
   public void onCreateWindow(){
       cmbStatus.setModel(new ListModelList<>(DESC));
//       cmbStatus.setSelectedIndex(1);
       parentController = (ListDeliveryGoodsBundling) win_list_GD_find.getAttribute("parentController");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        txtDateFrom.setValue(cal.getTime());
   }
   
   @Listen("onClick=#goFind")
   public void buttonFindParam(){
//       Map map = new HashMap();
//       map.put("InBundlingNo", txtbundlingNo.getText());
//       map.put("InGdId", txtGdid.getText());
//       map.put("InGdNo", txtGdNo.getText());
//        if (txtDateFrom.getValue() != null && txtDateTo.getValue() !=null) {
//       map.put("InStartDate", txtDateFrom.getValue()==null?"":IDefines.DATE_FORMAT_ID.format(txtDateFrom.getValue()));
//       map.put("InEndDate", txtDateTo.getValue()==null?"":IDefines.DATE_FORMAT_ID.format(txtDateTo.getValue()));
//        }else{
//            map.put("InStartDate", null);
//            map.put("InEndDate", null);
//        }
//        map.put("InStatus", (cmbStatus.getSelectedIndex() == -1) ? null:CODE[cmbStatus.getSelectedIndex()]);
       String status;
       if (cmbStatus.getSelectedIndex() == -1) {
           status = "";
       } else {
           status = String.valueOf(CODE[cmbStatus.getSelectedIndex()]);
       }
       parentController.buttonFind(txtbundlingNo.getText(), txtGdid.getText(), txtGdNo.getText(), txtDateFrom.getText(), txtDateTo.getText(),status);
      win_list_GD_find.detach();
   }
   
      @Listen("onClick=#Close1")
    public void addClose1() {
        win_list_GD_find.detach();
    }
}
