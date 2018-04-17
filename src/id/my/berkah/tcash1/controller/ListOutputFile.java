/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListOFParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ListOutputFile extends SelectorComposer<Component> {

    @Wire
    Paging userPaging;
    @Wire
    Window win_list_find;
    @Wire
    Textbox txtid;
    
    @Wire
    Listbox listbox;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    @Listen("onClick=#new")
    public void addnewrecord() {
        Window window = (Window) Executions.createComponents(
                "/Tcash/OutputFileTrc.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        Window w = (Window) Executions.createComponents("/Tcash/ListOutputFileFind.zul", null, null);
        w.setAttribute("parentController",this);
        w.doModal();
    }


  @Listen("onCreate=#List_Output_File")
   public void onCreateWindow(){
        setREnder();
    }


    @Listen("onClick=#edit")
    public void edit() {
       int index = listbox.getSelectedIndex();
        if (index >-1) {
            ListOFParam selected = (ListOFParam) listbox.getModel().getElementAt(index);
            Map<String,Object>map = new HashMap<>();
            map.put("header", selected);
            Window w = (Window) Executions.createComponents("/Tcash/OutputFileTrc.zul", null, map);
            w.setAttribute("parentController", this);
            w.doModal();
        }else{
            Messagebox.show("No record Selected","Output File",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
    @Listen("onDoubleClick=#listbox")
    public void doubleClick(){
        edit();
    }
    
    @Listen("onClick=#refresh")
    public void refreshOf(){
        List<ListOFParam>list = model.getselectListOF();
        listbox.setModel(new ListModelList<ListOFParam>(list));
    }
    
    public void setREnder(){
        listbox.setItemRenderer(new ListitemRenderer<ListOFParam>() {

            @Override
            public void render(Listitem lstm, ListOFParam t, int i) throws Exception {
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
            new Listcell(t.getStatus_desc()).setParent(lstm);
            }
        });
    }
    
    public void FindParam(Map searchMap){
        System.out.println("Find : "+searchMap);
        List<ListOFParam>listfid =model.getselectListOF(null, searchMap);
        listbox.setModel(new ListModelList<ListOFParam>(listfid));
        System.out.println(listfid.size());
    }
}
