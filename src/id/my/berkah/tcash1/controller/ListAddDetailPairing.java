/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListAddDtlPairingParam;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
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
public class ListAddDetailPairing extends SelectorComposer<Component>{
    
    @Wire
    Button btnCancel,btnSubmit;
    
    @Wire
    Window win_Add_Line_pairing;
    
    @Wire
    Textbox txttcashId,txtloopId;
    
    @Wire
    Listbox listbox;
    
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    public void reresh(){
        listbox.getItems().clear();
        List<ListAddDtlPairingParam>list =model.getListAddDtlPairing(txttcashId.getValue(),txtloopId.getText());
        listbox.setModel(new ListModelList<ListAddDtlPairingParam>(list));
    }
    
    public void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<ListAddDtlPairingParam>() {

            @Override
            public void render(Listitem lstm, ListAddDtlPairingParam t, int i) throws Exception {
//               lstm.appendChild(new Listcell(new Checkbox(t.getItem_code())));

                new Listcell(t.getItem_description()).setParent(lstm);
                new Listcell(t.getSn_from()).setParent(lstm);
                new Listcell(t.getSn_to()).setParent(lstm);
                new Listcell(t.getQty()).setParent(lstm);
            }
        });
    }
    
}
