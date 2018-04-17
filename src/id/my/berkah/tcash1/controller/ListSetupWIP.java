/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWipParam;
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
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListSetupWIP extends SelectorComposer<Component>{
    
    @Wire
    Listbox listbox;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    
    @Listen("onCreate=#List_wip_setup")
    public void onCreateWindow(){
        setRender();
    }
    
    @Listen("onClick=#new")
    public void buttonNEw(){
        Window w = (Window) Executions.createComponents("/Tcash/SetupWIPCTRL.zul", null, null);
        w.setAttribute("parentController",this);
        w.doModal();
    }
    
    @Listen("onClick=#refresh")
    public void buttonRefresh(){
        List<ListWipParam>list =model.getListWip("");
        listbox.setModel(new ListModelList<>(list));
    }
    
    public void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<ListWipParam>() {

            @Override
            public void render(Listitem lstm, ListWipParam t, int i) throws Exception {
                new Listcell(t.getId()).setParent(lstm);
                new Listcell(t.getWh_id()).setParent(lstm);
                new Listcell(t.getWh_code()).setParent(lstm);
                new Listcell(t.getWh_desc()).setParent(lstm);
                new Listcell(t.getFlag()).setParent(lstm);
                new Listcell(t.getCreated_date()).setParent(lstm);
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getExpired_date()).setParent(lstm);
                new Listcell(t.getExpired_by()).setParent(lstm);
            }
        });
    }
    
    @Listen("onClick=#find")
    public void buttonFind(){
        Window w = (Window) Executions.createComponents("/Tcash/ListSetupWipFind.zul", null, null);
        w.setAttribute("parentController",this);
        w.doModal();
    }
    
    
    @Listen("onClick=#edit")
    public void buttonEdit(){
        int index =listbox.getSelectedIndex();
        if (index >-1) {
            ListWipParam selected = (ListWipParam) listbox.getModel().getElementAt(index);
            Map map =new HashMap();
            map.put("header",selected);
             Window w = (Window) Executions.createComponents("/Tcash/SetupWIPCTRL.zul", null, map);
        w.setAttribute("parentController",this);
        w.doModal();
        } else {
            Messagebox.show("no record selected","Setup WIP",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
    @Listen("onDoubleClick=#listbox")
    public void onDouble(){
        buttonEdit();
    }
    
    public void goFind(String inWIP){
        List<ListWipParam>list =model.getListWip(inWIP);
        listbox.setModel(new ListModelList<>(list));
    }
}
