///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package id.my.berkah.tcash1.controller;
//
//import id.my.berkah.util.IDefines;
//import java.util.HashMap;
//import java.util.Map;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Datebox;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Window;
//
///**
// *
// * @author Azec
// */
//public class ListPairingFind extends SelectorComposer<Component>  implements IDefines {
//    
//       @Wire
//    Window win_pairing_find;
//    
//    @Wire
//    Listbox listbox;
//    
//    @Wire
//    Textbox txtworkid,txtwordnum,txtloopid,txtloopdesc;
//    
//    @Wire
//    Combobox cmbstatus;
//    
//    ModelTcashCTLR model=new ModelTcashCTLR();
//
//     
//     private ListPairingSn parentController;
//     
//       @Listen("onCreate=#win_pairing_find")
//    public void onCreateWindow(){
//       parentController =(ListPairingSn)win_pairing_find.getAttribute("parentController");
//    }
//    
//    @Listen("onClick=#Close1")
//    public void closeFind(){
//        win_pairing_find.detach();
//    }
//    
//    
//     @Listen("onClick=#goFind")
//   public void searchParam(){
//       Map map = new HashMap();
//        map.put("InPoNoTcash", txtworkid.getText() == null||txtworkid.getText().equals("")?  "":txtworkid.getText());
//       map.put("InPoNoLoop", txtloopid.getText()== null||txtworkid.getText().equals("") ?  "":txtloopid.getText());
//        
//        parentController.goFind(txtworkid.getText(), txtloopid.getText());
//        clear();
//        win_pairing_find.detach();
//    }
//   
//   void clear(){
//       txtworkid.setText("");
//       txtwordnum.setText("");
//       txtloopid.setText("");
//       txtloopdesc.setValue(null);
//   }
//}
