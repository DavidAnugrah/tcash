/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoPairing;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

/**
 *
 *
 */
public class ListGenSNCTRL extends SelectorComposer<Component> {
    String poId;
    String poLine;
    String upLoadId;

    public String getUpLoadId() {
        return upLoadId;
    }

    public void setUpLoadId(String upLoadId) {
        this.upLoadId = upLoadId;
    }

  

    public String getPoLine() {
        return poLine;
    }

    public void setPoLine(String poLine) {
        this.poLine = poLine;
    }
    
    WorkOrderCTRL workOrderCTRL;
    
    @Wire
    Listbox listbox;
    
    @Wire
    Window win_Gen_SN;
    
    @Wire
    Label txttotal1,txttotal2;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        refresh();
//        setRender();
     
    }
    
    public void refresh(){
        listbox.getItems().clear();
        List<ListWoPairing> list = model.selectListWOPairing(poId,poLine);
        listbox.setModel(new ListModelList<>(list));
        
                getGetSnInvQty(poId, poLine);

        listbox.setItemRenderer(new ListitemRenderer<ListWoPairing>() {

            @Override
            public void render(Listitem lstm, ListWoPairing t, int i) throws Exception {
                new Listcell (t.getPo_id()).setParent(lstm);
                new Listcell (t.getSn_tcash()).setParent(lstm);
                new Listcell (t.getSn_inv()).setParent(lstm);
            }
        });
        
    }

    
//    void setRender(){
        
    
    @Listen("onClick=#refresh")
    public void btnrefresh(){
        refresh();
    }
    
    @Listen("onClick=#close")
    public void close(){
         
        win_Gen_SN.detach();
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public WorkOrderCTRL getWorkOrderCTRL() {
        return workOrderCTRL;
    }

    public void setWorkOrderCTRL(WorkOrderCTRL workOrderCTRL) {
        this.workOrderCTRL = workOrderCTRL;
    }
    
    public void getGetSnInvQty(String poID,String poLine){
        Map<String,Object>map = model.getGetSnInvQty(poID,poLine);
        txttotal1.setValue( map.get("OutSnInvQty").toString());
        txttotal2.setValue( map.get("OutOrderQty").toString());
        }
    }
    
    
