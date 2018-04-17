/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListHdrAutoGenParam;
import id.my.berkah.util.CHelper;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListautoGenerate extends SelectorComposer<Component>{
    
    @Wire
    Window win_list_auto_find;
    
    
    @Wire
    Listbox listbox;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    @Listen("onCreate=#List_auto")
    public void oncreateWindow(){
        refresh();
        setRender();
    }
    
   
    
    @Listen("onClick=#new")
    public void newRecord(){
        Window w= (Window)Executions.createComponents("/Tcash/AutoGDGRPI.zul",null, null);
        w.setAttribute("parentController", this);
        w.doModal();
    }
    
    @Listen("onClick=#find")
    public void findParameter(){
         Window w= (Window)Executions.createComponents("/Tcash/ListAutoGenFind.zul",null, null);
         w.setAttribute("parentController", this);
        w.doModal();
    }
    
    @Listen("onClick=#refresh")
    public void buttonRefresh(){
        refresh();
    }

    private void refresh() {
        List<ListHdrAutoGenParam>list=model.getListHdrAutoGen();
        listbox.setModel(new ListModelList<ListHdrAutoGenParam>(list));
        listbox.setSizedByContent(true);
    }
    
    private void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<ListHdrAutoGenParam>() {

            @Override
            public void render(Listitem lstm, ListHdrAutoGenParam t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, "" + (i + 1), "right");
                CHelper.Listbox_addLabel(lstm, t.getAuto_gen_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getAuto_gen_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getAuto_gen_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getPurchase_order(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSupp_delivery_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getReceive_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getOrdered_qty(), "left");
                CHelper.Listbox_addLabel(lstm, t.getDelivered_qty(), "left");
                CHelper.Listbox_addLabel(lstm, t.getReceived_qty(), "left");
                CHelper.Listbox_addLabel(lstm, t.getProd_issue_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWip_rcp_no(), "left");
            }
        });
    }
    
//    @Listen(Events.ON_CLICK + " = #btnEdit")
//    public void btnEditOnClick() {
//        int index = listbox.getSelectedIndex();
//        if (index > -1) {
//            ListHdrAutoGenParam selected = (ListHdrAutoGenParam) listbox.getModel().getElementAt(index);
//            Map map = new HashMap();
//            map.put("header", selected);
//            Window w = (Window) Executions.createComponents("/Tcash/AutoGDGRPI.zul", null, map);
//            w.setAttribute("parentController", this);
//            w.doModal();
//        } else {
//            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
//        }
//    }
//    
//      @Listen(Events.ON_DOUBLE_CLICK + " = #listboxs")
//    public void listbox1OnDoubleClick() {
//        btnEditOnClick();
//    }
}
