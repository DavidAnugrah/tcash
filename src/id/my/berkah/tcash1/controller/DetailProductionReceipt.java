/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class DetailProductionReceipt extends SelectorComposer<Component> {
    
    @Wire
    Window win_dtlprod_receipt;
    @Wire
    Textbox txtprodreceipt,txtproddate,txtwhid,txtwhcode,txtwhdesc,txtitemid,txtitemcode,txtitemdesc,txtstatdesc,txtstatid;
    
    @Wire
    Intbox txtqty;
    
    @Listen("onClick=#close")
    public void closeWindow(){
        win_dtlprod_receipt.detach();
    }
    
    @Listen("onClick=#newrecord")
    public void newRecord(){
        txtprodreceipt.setText("");
        txtproddate.setText("");
        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtqty.setValue(0);
        txtstatdesc.setText("");
        txtstatdesc.setText("");
    }
}

