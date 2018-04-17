/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListInvSelectSNParam;
import id.my.berkah.tcash1.model.ListSelectSNTempParam;
import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListRangeShopFloor extends SelectorComposer<Component>{
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String buId= global[5];
    
    @Wire
    Window ListSelectRange_ShopFloor;
    
    @Wire
    Textbox txtsfcDtlId,txtstatus,txflag1,txtitemId;
    
    @Wire
    Listbox listboxSelect,listboxUnselect;
    
    @Wire
    Button btnSelect,btnUnselect;
    
    
   ShoFloorCTRL parentController1;
    ModelTcashCTLR model= new ModelTcashCTLR();
    
   @Listen("onCreate=#ListSelectRange_ShopFloor")
   public void oncreateWindow(){
       parentController1=(ShoFloorCTRL)ListSelectRange_ShopFloor.getAttribute("parentController1");
       setrenderRangeSFC();
       setinvRender();
       refreshTemp();
       refreshInvSn();
   }
   
    @Listen("onClick=#btnClose")
    public void closeRange(){
        ListSelectRange_ShopFloor.detach();
    }
    
    
    @Listen("onClick=#btnSelect")
    public void selectForm(){
       validateStatusSelect();
         if (!txtitemId.getText().isEmpty()) {
            if (txflag1.getText().equals("select")) {
             Map<String,Object> mapper =model.doValidateSelectSnSFC(txtitemId.getText());
             if (mapper.get("err").equals(0)) {
       Map<String,Object>map= new HashMap<>();
        map.put("sfcid", txtsfcDtlId.getText());
        map.put("itemid", txtitemId.getText());
        map.put("userId", userId);
        map.put("status", txtstatus.getText());
       map.put("flag", txflag1.getText());
             txflag1.setText("");
             txflag1.setText("select");
        Window w = (Window)Executions.createComponents("/Tcash/SelectSN_1.zul", null, null);
       w.doModal();
         } else {
            Messagebox.show(mapper.get("msg").toString(),"Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
         } else {
                  Messagebox.show("This Button for Select Range S/N","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
            }
        } else {
            Messagebox.show("No Record Selected","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
   
    @Listen("onClick=#btnUnselect")
     public void unselectForm(){
         validateStatusSelect();
          if (txflag1.getText().equals("unselect")) {
             Map<String,Object> mapper =model.doValidateSelectSnSFC(txtitemId.getText());
             if (mapper.get("err").equals(0)) {
       Map<String,Object>map= new HashMap<>();
        map.put("sfcid", txtsfcDtlId.getText());
        map.put("itemid", txtitemId.getText());
        map.put("userId", userId);
        map.put("status", txtstatus.getText());
       map.put("flag", txflag1.getText());
             txflag1.setText("");
             txflag1.setText("select");
       Window w = (Window)Executions.createComponents("/Tcash/SelectSN_1.zul", null, null);
       w.doModal();
         } else {
            Messagebox.show(mapper.get("msg").toString(),"Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
             }
         } else {
                  Messagebox.show("This Button for unSelect Range S/N","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
          }
     }

     public void refreshTemp(){
         List<ListSelectSNTempParam>listdata=model.getListSelectSNTempSFC(txtsfcDtlId.getText());
         listboxSelect.setModel(new ListModelList<ListSelectSNTempParam>(listdata));
     }
     
     public void setrenderRangeSFC(){
         listboxSelect.setItemRenderer(new ListitemRenderer<ListSelectSNTempParam>() {

             @Override
             public void render(Listitem lstm, ListSelectSNTempParam t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm,t.getSFC_ID(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSFC_DTL_ID(), "left");
                CHelper.Listbox_addLabel(lstm,t.getItem_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSn_from(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSn_to(), "left");
                CHelper.Listbox_addLabel(lstm,t.getQuantity(), "right");
                CHelper.Listbox_addLabel(lstm,t.getItem_detail_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getStorage_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getItem_loc_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getLot_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getWh_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getWh_loc_id(), "left");
             }
         });
     }
     
     void refreshInvSn(){
          List<ListInvSelectSNParam>listdata=model.getListInvSelectSNSFC(txtsfcDtlId.getText());
         listboxSelect.setModel(new ListModelList<ListInvSelectSNParam>(listdata));
     }
     
     void setinvRender(){
         listboxSelect.setItemRenderer(new ListitemRenderer<ListInvSelectSNParam>() {

             @Override
             public void render(Listitem lstm, ListInvSelectSNParam t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm,t.getSFC_ID(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSFC_DTL_ID(), "left");
                CHelper.Listbox_addLabel(lstm,t.getItem_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSn_from(), "left");
                CHelper.Listbox_addLabel(lstm,t.getSn_to(), "left");
                CHelper.Listbox_addLabel(lstm,t.getQuantity(), "right");
                CHelper.Listbox_addLabel(lstm,t.getItem_detail_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getStorage_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getItem_loc_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getLot_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getWh_id(), "left");
                CHelper.Listbox_addLabel(lstm,t.getWh_loc_id(), "left");
             
             }
         });
     }
     
     
      void validateStatusSelect(){
        if (txtstatus.getText().equals("1")) {
            
        } else if (txtstatus.getText().equals("2")) {
            Messagebox.show("This WIP Receipt Number Has been Submitted","Select Range",Messagebox.OK,Messagebox.EXCLAMATION);
        } else if (txtstatus.getText().equals("3")) {
            Messagebox.show("This WIP Receipt Number Has been Approved","Select Range",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
      
      @Listen(Events.ON_CLICK +"listboxSelect")
      public void selectTemp(){
           int index = listboxSelect.getSelectedIndex();
        if (index > -1) {
            ListShopFloorParam selected = (ListShopFloorParam) listboxSelect.getModel().getElementAt(index);
           txflag1.setText("select");
      }else {
            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
        }
      }
      
      @Listen(Events.ON_CLICK +"listboxUnselect")
      public void selectinvSN(){
           int index = listboxUnselect.getSelectedIndex();
        if (index > -1) {
            ListShopFloorParam selected = (ListShopFloorParam) listboxUnselect.getModel().getElementAt(index);
           txflag1.setText("unselect");
      }else {
            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
        }
      }
      
      void disableButton(){
          if (txtstatus.getText().equals("2")||txtstatus.getText().equals("3")) {
              btnSelect.setDisabled(true);
              btnUnselect.setDisabled(true);
          } else {
                btnSelect.setDisabled(false);
              btnUnselect.setDisabled(false);
          }
      }
      
      
  
}
