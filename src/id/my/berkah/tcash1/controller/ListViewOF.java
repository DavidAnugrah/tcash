/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListOFDtlParam;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListViewOF  extends SelectorComposer<Component> {
    
    @Wire
    Listbox listboxview;
    
    @Wire
    Textbox txtOF,txtline;
    
    @Wire
    Button close;
    
    @Wire
    Window win_view_OF;
    
    @Listen("onCreate=#win_view_OF")
    public void onCreateWindow(){
        setRender();
        refresh();
    }
    
    ModelTcashCTLR model =new ModelTcashCTLR();
    
    public void refresh(){
        List<ListOFDtlParam>list = model.getselectListOFDtl(txtOF.getText(),txtline.getText());
        listboxview.setModel(new ListModelList<>(list));
        listboxview.setSizedByContent(true);
    }
    
    public void setRender(){
        listboxview.setItemRenderer(new ListitemRenderer<ListOFDtlParam>() {

              @Override
            public void render(Listitem lstm, ListOFDtlParam t, int i) throws Exception {
                new Listcell(t.getOf_dtl_id()).setParent(lstm);
                new Listcell(t.getOf_id()).setParent(lstm);
                new Listcell(t.getFilename()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_desc()).setParent(lstm);
                new Listcell(t.getOf_no()).setParent(lstm);
                new Listcell(t.getSn_tcash()).setParent(lstm);
                new Listcell(t.getSn_inventory()).setParent(lstm);
                new Listcell(t.getUid_code()).setParent(lstm);
                new Listcell(t.getStatus()).setParent(lstm);
                new Listcell(t.getMessage()).setParent(lstm);
                
            }
            
        });
    }
    
    @Listen("onClick=#close")
    public void buttonClose(){
        win_view_OF.detach();
    }
}
