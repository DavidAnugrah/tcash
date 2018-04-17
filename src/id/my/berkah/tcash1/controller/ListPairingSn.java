//
//package id.my.berkah.tcash1.controller;
//
//import id.my.berkah.tcash1.model.ListPairingHdrParam;
//import id.my.berkah.util.CHelper;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Datebox;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Window;
//
///**
// *
// * @author Azec
// */
//public class ListPairingSn extends SelectorComposer<Component> {
//    
//    @Wire
//    Window win_pairing_find;
//    
//    @Wire
//    Listbox listbox;
//    
//    @Wire
//    Textbox txtPI,txtwoidfnd,txtItemifnd;
//    
//    @Wire
//    Datebox dateboxfrom,dateboxto;
//    
//    @Wire
//    Combobox cmbstatus;
//    
//    ModelTcashCTLR model=new ModelTcashCTLR();
//     private Map lastSearch;
//     
//    @Listen("onCreate=#List_pairing")
//    public void onCreateWindow(){
//   
//       setListboxRender();
//     
//    }
//    
//    
//    @Listen("onClick=#new")
//    public void newrecord(){
//        Window w= (Window)Executions.createComponents("/Tcash/PairingSN.zul",null, null);
//        w.doModal();
//    }
//    
//    @Listen("onClick=#find")
//    public void findParameter(){
//       Window w= (Window)Executions.createComponents("/Tcash/ListPairingFind.zul",null, null);
//       w.setAttribute("parentController", this);
//        w.doModal();
//    }
//    
//            @Listen("onClick=#refresh")
//            public void buttonRefresh(){
//             refresh();
//            }
//    
//    void refresh(){
//        List<ListPairBundlingParam> list = model.getListPairBundling("","");
//        listbox.setModel(new ListModelList<ListPairBundlingParam>(list));
//    }
//    
//   void setListboxRender(){
//        listbox.setItemRenderer(new ListitemRenderer<ListPairBundlingParam>() {
//
//
//            @Override
//            public void render(Listitem lstm, ListPairBundlingParam t, int i) throws Exception {
//                new Listcell(t.getBp_id()).setParent(lstm);
//                new Listcell(t.getBp_no()).setParent(lstm);
//                new Listcell(t.getBp_date()).setParent(lstm);
//                new Listcell(t.getPo_no_tcash()).setParent(lstm);
//                new Listcell(t.getPo_no_loop()).setParent(lstm);
//                new Listcell(t.getPo_id_tcash()).setParent(lstm);
//                new Listcell(t.getPo_id_loop()).setParent(lstm);
//            }
//        });
//    }
////   
////   void goFind(String tcash,String loop){
////        List<ListPairBundlingParam> list = model.getListPairBundling(tcash,loop);
////        listbox.setModel(new ListModelList<ListPairBundlingParam>(list));
////   }
////   
////   
////    @Listen(Events.ON_CLICK + " = #edit")
////    public void btnEditOnClick() {
////        int index = listbox.getSelectedIndex();
////        if (index > -1) {
////            ListPairBundlingParam selected = (ListPairBundlingParam) listbox.getModel().getElementAt(index);
////            Map map = new HashMap();
////            map.put("header", selected);
////            Window w = (Window) Executions.createComponents("/Tcash/PairingSN.zul", null, map);
////            w.setAttribute("parentCTRL", this);
////            w.doModal();
////        } else {
////            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
////        }
////    }
////
////    @Listen(Events.ON_DOUBLE_CLICK + " = #listbox")
////    public void listbox1OnDoubleClick() {
////        btnEditOnClick();
////    }
////  
////   }
//
