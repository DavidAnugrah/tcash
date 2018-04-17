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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListBundlingViewSN extends SelectorComposer<Component>{
    
    @Wire
    Window win_upload_logs_bdl;
    
    @Wire
    Listbox listbox;
    
    public void refresh(){
        
    }
    
    
    @Listen("onClick=#close")
    public void closeWindow(){
        win_upload_logs_bdl.detach();
    }
    
}
