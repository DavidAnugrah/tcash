/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.getRegAvailableQtyParam;
import id.my.berkah.util.ParameterGlobal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class BundlingAllocatedNonRegional extends SelectorComposer<Component> {
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
    @Wire
    Window  win_bundling_allocated_non_regional;
    
    @Wire
    Textbox itemId,txtbundlingdtlid,txtbundlingid,txtbundlingno,txtavailabelqty,txtwip,txtstatus;
    
    @Wire
    Intbox txtqty;
    
    @Wire
    Button btnsave;

    BundlingCTRL BundlingCTRL;
      
    ModelTcashCTLR model1= new ModelTcashCTLR();
      
    BundlingAllocated bdlAllocated= new BundlingAllocated();
    
    @Listen("onCreate=#win_bundling_allocated_non_regional")
    public void onCreateWindow(){
    BundlingCTRL = (BundlingCTRL) win_bundling_allocated_non_regional.getAttribute("parentController");
    getFunction();
        if (BundlingCTRL.txtstatus.getText().equalsIgnoreCase("draft")) {
            txtqty.setStyle("background-color:#FFFACD;text-align:right");
            txtavailabelqty.setStyle("text-align:right");
        
        } else {
            txtqty.setStyle("background-color:#FFFFFF;text-align:right");
             txtavailabelqty.setStyle("text-align:right");
             btnsave.setDisabled(true);
        }
    }
    
    @Listen("onClick=#close")
    public void closeWindow(){
        win_bundling_allocated_non_regional.detach();
    }
    
    
    
    public void buttonSave(){
        Map<String,Object>map= model1.doOnUpdateDtl(
                txtbundlingdtlid.getText(),
                txtqty.getValue().toString());
        if (map.get("OutError").equals(0)) {
            Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.INFORMATION);
            BundlingCTRL.listboxDetail();
            getFunction();

        } else {
              Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
   @Listen("onClick=#Edit")
    public void btnEditSN() {
        Map<String, Object> map = model1.dovalidateEditSN(txtbundlingid.getText(),"",itemId.getText());
        if (map.get("OutError").equals(0)) {
                Map<String,Object> map1 = new HashMap();
                map1.put("bundling_id", txtbundlingid.getText());
                map1.put("item",itemId.getText() );
                map1.put("AvQty", txtqty.getText());
                map1.put("status", txtstatus.getText());
                map1.put("nonRegional", this);
                Window w = (Window) Executions.createComponents("/Tcash/ListEditSNBundling.zul", null, map1);
                w.setAttribute("parentControllers", this);
        } else {
              Messagebox.show(map.get("OutMessages").toString());
        }
    }
    
     @Listen("onClick=#btnsave")
     public void updateSubDtl(){
         System.out.println("Qty ========"+txtqty.getValue().toString().replace(",", ""));
        Map<String,Object>map = model1.doOnInsertSubDtl(txtbundlingid.getText(), txtbundlingno.getText(),"",itemId.getText(),txtqty.getValue().toString().replace(",", ""),userId);
        if (map.get("OutError").equals(0)) {
            buttonSave();
        } else {
            Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.INFORMATION);
        }
    
     }
     
     public void getFunction(){
         Map<String,Object>map= model1.getNonRegAvaiQty(txtbundlingid.getText(),null,itemId.getText(),txtwip.getText());
         txtavailabelqty.setValue(map.get("OutAvaiQty").toString());
         txtqty.setValue(Integer.parseInt(map.get("OutQty").toString()));
         BundlingCTRL.listboxDetail();
     }
     
     public void refreshHDR(){
         
     }
}
