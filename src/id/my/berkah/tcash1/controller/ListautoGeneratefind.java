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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListautoGeneratefind extends SelectorComposer<Component>{
    
    @Wire
    Window win_list_auto_find;
    
    @Wire
    Textbox txtauto;
    
    @Wire
    Datebox dateboxfrom,dateboxto;
    
    ModelTcashCTLR model =new ModelTcashCTLR();
    ListautoGenerate parentController;
    
    @Listen("onCreate=#win_list_auto_find")
    public void onCreateWindow(){
        parentController=(ListautoGenerate)win_list_auto_find.getAttribute("parentController");
    }
     @Listen("onClick=#Close1")
    public void closeWindow(){
        win_list_auto_find.detach();
    }
    
    @Listen("onClick=#goFind")
    public void findByParameter(){
        
        List<ListHdrAutoGenParam>list = model.getListHdrAutoGen(txtauto.getValue(), dateboxfrom.getText(),dateboxto.getText());
        parentController.listbox.setModel(new ListModelList<ListHdrAutoGenParam>(list));
        
        parentController.listbox.setItemRenderer(new ListitemRenderer<ListHdrAutoGenParam>() {

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
        win_list_auto_find.detach();
    }
    
}
