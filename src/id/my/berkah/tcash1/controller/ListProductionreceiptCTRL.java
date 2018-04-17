/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListProductionreceiptCTRL extends SelectorComposer<Component>{
    
    
    @Wire
    Window win_list_mntr_find;
    
    
    @Wire
    Listbox listbox;
    
      @Wire
    Paging userPaging;

       private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;
    
    @Listen("onCreate=#List_Prod_receiptList_Prod_receipt")
    public void onCreateWindow(){
        win_list_mntr_find.setVisible(false);
    }
    
    @Listen("onClick=#refresh")
    public void requery(){
        Window w = (Window)Executions.createComponents("/Tcash/DetailProductionReceipt.zul",null, null);
        w.doModal();
    }
    
        @Listen("onClick=#Clear")
    public void clearParamFinder(){
        clearFindParam();
    }

    private void clearFindParam() {
        Path parent = new Path("/List_Mass_Prov/win_list_mntr_find");
        Textbox txtBuidfind = (Textbox) new Path(parent, "txtprod").getComponent();
        Datebox dateboxfromfind = (Datebox) new Path(parent, "dateboxfrom").getComponent();
        Datebox dateboxtofind = (Datebox) new Path(parent, "dateboxto").getComponent();
         txtBuidfind.setText("");
        dateboxfromfind.setValue(null);
        dateboxtofind.setValue(null);
    }
    
       @Listen("onClick=#find")
    public void filter() {
        win_list_mntr_find.setVisible(true);
    }

    @Listen("onClick=#Close1")
    public void closeFind() {
        win_list_mntr_find.setVisible(false);
//        clearFindParam();
    }
    
    @Listen("onClick=#new")
    public void newForm(){
        Window w = (Window)Executions.createComponents("/Tcash/DetailProductionReceipt.zul", null, null);
        w.doModal();
    }
    
     @Listen("onClick=#edit")
    public void editForm(){
        if (listbox.getItems().isEmpty()) {
            Messagebox.show(" No Record Choosen","WIP Receipt",Messagebox.OK,Messagebox.EXCLAMATION);
        } else {
        }
    }
    
}
