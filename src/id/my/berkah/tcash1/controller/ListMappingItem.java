/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListHDRMappingBDL;
import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListMappingItem extends SelectorComposer<Component>{
 
   
    
      @Wire
    Listbox listbox;
    
      @Wire
      Textbox txtid,txtitemcode,txtitemdesc;
    ModelTcashCTLR model=new ModelTcashCTLR();

    public ListMappingItem() {
    }
   @Listen("onClick=#new")
   public void newRecord(){
       Window w =(Window)Executions.createComponents("Tcash/MappingITemBundling.zul", null, null);
       w.doModal();
   }
   
   @Listen("onCreate=#List_mapping_item")
    public void onCreateWindow(){
        setrenderHDR();
    }
    

   
   @Listen("onClick=#find")
   public void findParameter(){
       Window w =(Window)Executions.createComponents("/Tcash/ListMappingItemBundlingFind.zul", null, null);
       w.setAttribute("parentControl",this);
       w.doModal();
   }
   
  
   
   public void refresh(){
       List<ListHDRMappingBDL> listdata=model.getListHeaderbdl();
       listbox.setModel(new ListModelList<ListHDRMappingBDL>(listdata));
   }
   
   void setrenderHDR(){
       listbox.setItemRenderer(new ListitemRenderer<ListHDRMappingBDL>() {

           @Override
           public void render(Listitem lstm, ListHDRMappingBDL t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, t.getItem_bundling_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_code(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_description(), "left");
                CHelper.Listbox_addLabel(lstm, t.getUom(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreated_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreated_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getTerminate_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getTerminate_date(), "left");
           }
       });
   }
   
   @Listen("onClick=#refresh")
   public void buttonRefresh(){
       refresh();
   }
   
   
      @Listen(Events.ON_CLICK + " = #edit")
    public void btnEditOnClick() {
        int index = listbox.getSelectedIndex();
        if (index > -1) {
            ListHDRMappingBDL selected = (ListHDRMappingBDL) listbox.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("header", selected);
            Window w = (Window) Executions.createComponents("/Tcash/MappingITemBundling.zul", null, map);
            w.setAttribute("parentCTRL", this);
            w.doModal();
        } else {
            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
        }
    }
    
    @Listen(Events.ON_DOUBLE_CLICK+ "=#listbox")
    public void doubleClick(){
        btnEditOnClick();
    }
    
    
    public void doFind(String item){
        List<ListHDRMappingBDL>listfind=model.getListHeaderbdl(item);
        listbox.setModel(new ListModelList<ListHDRMappingBDL>(listfind));
    
    }
    
   
}
