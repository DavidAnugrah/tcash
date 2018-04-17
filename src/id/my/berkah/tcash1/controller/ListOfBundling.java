/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.BdlListHdrParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListOfBundling extends SelectorComposer<Component>{
    
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    private Map lastSearch;
    @Wire
    Listbox listbox;
    
    @Listen("onCreate=#List_Output_File")
    public void onCreateWindow(){
        setRender();
    }
    
    @Listen("onClick=#refresh")
    public void refreshALL(){
        List<BdlListHdrParam>list=model.getBdlListHdr("");
       listbox.setModel(new ListModelList<BdlListHdrParam>(list));
    }
    
    public void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<BdlListHdrParam>() {

            @Override
            public void render(Listitem lstm, BdlListHdrParam t, int i) throws Exception {
                new Listcell(t.getOf_id()).setParent(lstm);
                new Listcell(t.getOf_no()).setParent(lstm);
                new Listcell(t.getOf_date()).setParent(lstm);
                new Listcell(t.getFilename()).setParent(lstm);
                new Listcell(t.getStatus()).setParent(lstm);
                new Listcell(t.getPo_id()).setParent(lstm);
                new Listcell(t.getPurchase_order()).setParent(lstm);
                new Listcell(t.getCreate_date()).setParent(lstm);
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getUpdate_date()).setParent(lstm);
                new Listcell(t.getUpdated_by()).setParent(lstm);
                new Listcell(t.getSubmit_date()).setParent(lstm);
                new Listcell(t.getSubmited_by()).setParent(lstm);
                new Listcell(t.getApprove_date()).setParent(lstm);
                new Listcell(t.getApproved_by()).setParent(lstm);
                new Listcell(t.getCancel_date()).setParent(lstm);
                new Listcell(t.getCanceled_by()).setParent(lstm);
              
            }
        });
    }
    
    
    @Listen("onClick=#edit")
      public void editForm() {
          int index = listbox.getSelectedIndex();
          System.out.println(index);
          if (index >-1) {
              BdlListHdrParam selected = (BdlListHdrParam)listbox.getModel().getElementAt(index);
              Map map = new HashMap();
              map.put("header", selected);
              Window w = (Window)Executions.createComponents("/Tcash/OfBunlding.zul", null, map);
              w.setAttribute("parentController", this);
              w.doModal();
          } else {
              Messagebox.show("No Record Selected");
          }
      }
      
      @Listen("onClick=#new")
      public void newREcord(){
       Window w = (Window)Executions.createComponents("/Tcash/OfBunlding.zul", null, null);
              w.setAttribute("parentController", this);
              w.doModal();
      }
      
      @Listen("onClick=#find")
      public void filterPAram(){
            Window w = (Window)Executions.createComponents("/Tcash/ListOfBundlingFind.zul", null, null);
              w.setAttribute("parentController", this);
              w.doModal();
      }
      
     public void doFind(Map searchMap){
            lastSearch=searchMap;
          List<BdlListHdrParam>list=model.getBdlListHdr(null,searchMap);
          listbox.setModel(new ListModelList<BdlListHdrParam> (list));
          
     }
     
     @Listen(Events.ON_DOUBLE_CLICK + " = #listbox")
    public void listbox1OnDoubleClick() {
        editForm();
    }
}
