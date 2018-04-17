/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListHdrBundlingParam;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ItemRenderer;
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
public class ListBundling extends SelectorComposer<Component>{
      private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
     private Map lastSearch;
    @Wire
    Listbox listbox;
    
    @Listen("onCreate=#List_bundling")
    public void onCreateWindow(){
        setREnder();
    }
    
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    
    @Listen("onClick=#new")
    public void btnnew(){
        Window w = (Window)Executions.createComponents("/Tcash/BundlingCTRL.zul", null, null);
        w.setAttribute("parentController", this);
        w.doModal();
    }
    
    @Listen("onClick=#find")
    public void tnFind(){
        Window w = (Window)Executions.createComponents("/Tcash/ListBundlingFind.zul", null, null);
        w.setAttribute("parentController", this);
        w.doModal();
    }
    
    @Listen("onClick=#refresh")
    public void btnrefresh(){
        List<ListHdrBundlingParam>list =model.getListHdrBundling();
        listbox.setModel(new ListModelList<ListHdrBundlingParam>(list));
        listbox.setSizedByContent(true);
    }
    
    public void setREnder(){
        listbox.setItemRenderer(new ListitemRenderer<ListHdrBundlingParam>() {

            @Override
            public void render(Listitem lstm, ListHdrBundlingParam t, int i) throws Exception {
            new Listcell (t.getBundling_id()).setParent(lstm);
            new Listcell (t.getBundling_no()).setParent(lstm);
            new Listcell (t.getBundling_date()).setParent(lstm);
            new Listcell (t.getItem_id()).setParent(lstm);
            new Listcell (t.getItem_code()).setParent(lstm);
            new Listcell (t.getItem_desc()).setParent(lstm);
            new Listcell (t.getSupplier_Id()).setParent(lstm);
            new Listcell (t.getSupplier_code()).setParent(lstm);
            new Listcell (t.getSupplier_name()).setParent(lstm);
            new Listcell (t.getQty()).setParent(lstm);
            new Listcell (t.getCreated_date()).setParent(lstm);
            new Listcell (t.getCreated_by()).setParent(lstm);
            new Listcell (t.getProcessed_date()).setParent(lstm);
            new Listcell (t.getProcessed_by()).setParent(lstm);
            new Listcell (t.getApproved_date()).setParent(lstm);
            new Listcell (t.getApproved_by()).setParent(lstm);
            new Listcell (t.getUpdated_date()).setParent(lstm);
            new Listcell (t.getUpdated_by()).setParent(lstm);
            new Listcell (t.getCanceled_date()).setParent(lstm);
            new Listcell (t.getCanceled_by()).setParent(lstm);
            new Listcell (t.getCreated_period()).setParent(lstm);
            new Listcell (t.getStatus()).setParent(lstm);
            }
        });
    }
    
    @Listen("onClick=#edit")
    public void editButton(){
        int index = listbox.getSelectedIndex();
        if (index >-1) {
            ListHdrBundlingParam selected = (ListHdrBundlingParam) listbox.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("header", selected);
            System.out.println(selected);
            Window w = (Window)Executions.createComponents("/Tcash/BundlingCTRL.zul", null, map);
            w.setAttribute("parentController", this);
            w.doModal();
        }else{
            Messagebox.show("No record selected","Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
    public void goFind(String BdlId,Map searchMap){
        System.out.println("Find: "+ searchMap);
        lastSearch=searchMap;
        List<ListHdrBundlingParam>list = model.getListHdrBundling(BdlId,userId,responsibilityId, searchMap);
        listbox.setModel(new ListModelList<ListHdrBundlingParam>(list));
    }
    
    @Listen("onDoubleClick=#listbox")
    public void doubleClick(){
        editButton();
    }
    }
