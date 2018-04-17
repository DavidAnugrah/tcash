///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package id.my.berkah.tcash1.controller;
//
//import id.my.berkah.util.ParameterGlobal;
//import id.my.berkah.util.controller.LovController;
//import java.util.HashMap;
//import java.util.Map;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Window;
//
///**
// *
// * @author Azec
// */
//public class LoadPIPairing extends SelectorComposer<Component> {
//    private String[] global = ParameterGlobal.getParameterGlobal();
//    String userId = global[0];
//    
//    @Wire
//    Window win_load;
//    
//    ModelTcashCTLR model = new ModelTcashCTLR();
//    
//    @Wire
//    Textbox txtitemid,txtitemcode,txtitemdesc,txtPI,txtPIid ,txtqty,txtfromsn,txtsn,txtpairingId;
//    
//    PairingSN parentController;
//    
////    String idPairing = parentController.txtid.getText();
//    
//    @Listen("onCreate=#win_load")
//    public void oCreatewindow(){
//        parentController= (PairingSN)win_load.getAttribute("parentController");
//    }
//    @Listen("onClick=#Close1")
//    public void closeWindow(){
//        win_load.detach();
//    }
//    
//    
//    @Listen("onClick=#btnitemcode")
//    public void LovItem(){
//         HashMap map = new HashMap<String, Object>();
//        LovController composerLov = new LovController();
//        composerLov.setQuery("select * from (select rownum as No, item_id as \"id\",item_code as \"Item Code\" ,item_description as\"Description\",unit  from table(pkg_tcash_pairing.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_pairing.LovItem('')) where \n"
//                + "(upper(item_code) like upper('?') or upper(item_description) like upper('?'))");
//        composerLov.setSelectedColumn(new int[]{1, 2,3});
//        composerLov.setComponentTransferData(new Textbox[]{txtitemid,txtitemcode,txtitemdesc});
//        composerLov.setHiddenColumn(new int[]{0, 1});
//
//        composerLov.setTitle("List Of Item");
//        composerLov.setWidth("500px");
//        composerLov.setHeight("335px");
//        composerLov.setPageSize(10);
//        map.put("composerLov", composerLov);
//
//        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
//        w.setParent(win_load);
//        w.doModal();
//    }
//    
//    @Listen("onClick=#btnpi")
//    public void LovPI(){
//         HashMap map = new HashMap<String, Object>();
//        LovController composerLov = new LovController();
//        composerLov.setQuery("select * from (select rownum as No ,PROD_ISSUE_ID, PROD_ISSUE_NO as \"Prod Issue No\",QUANTITY as \"Qty\" ,FROM_SN as\"From S/N\",TO_SN as \" To S/N\"  from table(pkg_tcash_pairing.LovLoadPI('"+txtitemid.getText()+"'))where (upper(PROD_ISSUE_ID) like upper('?') or upper(PROD_ISSUE_NO) like upper('?')))where No BETWEEN param1 and param2 ");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_pairing.LovLoadPI('"+txtitemid.getText()+"')) where \n"
//                + "(upper(PROD_ISSUE_ID) like upper('?') or upper(PROD_ISSUE_NO) like upper('?'))");
//        composerLov.setSelectedColumn(new int[]{1,2,3,4,5});
//        composerLov.setComponentTransferData(new Textbox[]{txtPIid,txtPI,txtqty,txtfromsn,txtsn});
//        composerLov.setHiddenColumn(new int[]{0, 1});
//
//        composerLov.setTitle("List Of Production Issue");
//        composerLov.setWidth("500px");
//        composerLov.setHeight("335px");
//        composerLov.setPageSize(10);
//        map.put("composerLov", composerLov);
//
//        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
//        w.setParent(win_load);
//        w.doModal();
//    }
//    
//    
//    @Listen("onClick=#add")
//    public void addDetail(){
//        Map<String,Object>map = model.doPairingInsertDtl(txtpairingId.getText(), txtitemid.getText(), txtPIid.getText(), txtfromsn.getText(), txtsn.getText(), userId);
//        if (map.get("OutError").equals(0)) {
//            Messagebox.show(map.get("OutMessages").toString());
//            win_load.detach();
//        } else {
//            Messagebox.show(map.get("OutMessages").toString());
//        }
//    }
//}
