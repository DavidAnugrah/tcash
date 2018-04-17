/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.listDtlRegionalParam;
import id.my.berkah.util.IDefines;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class BundlingAllocated extends SelectorComposer<Component>{
     private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
    @Wire
    Window win_bundling_allocated;
    
    @Wire
    Listbox listbox;
    
    @Wire
    Textbox txtregId,txtregDesc,flag,itemId, txttresh,txtbundlingid,txtbdldtlid,txtoutId,txtitemcode,txtitemDesc,txtbundlingno,txtwip,txtstatus;
    
    @Wire
    Intbox txtqty;
    
    @Wire
    Label labeltotal;
    
    @Wire
    Button add,btnreg;
    
    
 
    listDtlRegionalParam listDtlRegionalParam;
    BundlingCTRL BundlingCTRL;
   
    ModelTcashCTLR model= new ModelTcashCTLR();
    
    @Listen("onCreate=#win_bundling_allocated")
    public void onCreateWindow(){
        BundlingCTRL = (BundlingCTRL)win_bundling_allocated.getAttribute("parentController");
        aftercompose();
       setrender();
       refresh();
    }
//    
    @Listen("onClick=#btnreg")
    public void lovReg(){
        System.out.println(txtwip.getText());
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, regional_id as \" Id\",regional_description as \"Regional \",treshold_qty as \"Available Qty\" from table(pkg_tcash_bundling.LovRegional('" + itemId.getText() + "','"+txtwip.getText()+"'))"
                + "where (upper(regional_id) like upper('?') or upper(regional_description) like upper('?')))"
                + "where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovRegional('" +itemId.getText() + "','"+txtwip.getText()+"'))Where regional_description LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtregId, txtregDesc,txttresh});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Regional");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        txtoutId.setText("");
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_bundling_allocated);
        w.doModal();

    }
    
    @Listen("onClick=#close")
    public void closebtn(){
     win_bundling_allocated.detach();
     BundlingCTRL.Refresh();
     BundlingCTRL.listboxDetail();
    }
    
    @Listen("onClick=#add")
    public void btnsave(){
        if (txtregId.getText().isEmpty()) {
            Messagebox.show("Regional can not be empty","Bundling Allocated",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        if (txtoutId.getText().isEmpty()) {
        insertqtyRegional();
//       add.setLabel("Update");
        } else {
        updateQtyRregional();
        txtregId.setText("");
       txtregDesc.setText("");
       txtqty.setText("0");
       txttresh.setText("");
        add.setLabel("Add Line");
        }
        BundlingCTRL.listboxDetail();
       
     }
    
    public void refresh(){
        
       labeltotal.setValue(String.valueOf("0"));
//       listbox.getItems().clear();
       List<listDtlRegionalParam>list = model.getListDtlRegional(txtbdldtlid.getText());
       listbox.setModel(new ListModelList<listDtlRegionalParam>(list));
       int total =0;
        for(listDtlRegionalParam listDtlRegionalParam1:list) {
         total += Integer.valueOf(listDtlRegionalParam1.getQty());
         labeltotal.setValue(String.valueOf(total));
        }
    }
       
        void setrender(){
       listbox.setItemRenderer(new ListitemRenderer<listDtlRegionalParam>() {

           @Override
           public void render(Listitem lstm, listDtlRegionalParam t, int i) throws Exception {
             
             
             new Listcell(t.getId()).setParent(lstm);
             new Listcell(t.getBundling_id()).setParent(lstm);
             new Listcell(t.getBundling_dtl_id()).setParent(lstm);
             new Listcell(t.getItem_id()).setParent(lstm);
             new Listcell(t.getRegional_id()).setParent(lstm);
             new Listcell(t.getRegional_code()).setParent(lstm);
             new Listcell(t.getRegional_desc()).setParent(lstm);
             new Listcell(t.getQty()).setParent(lstm);
             new Listcell(t.getTreshold_qty()).setParent(lstm);
             new Listcell(t.getCreated_date()).setParent(lstm);
             new Listcell(t.getCreated_by()).setParent(lstm);
             new Listcell(t.getUpdated_date()).setParent(lstm);
             new Listcell(t.getUpdated_by()).setParent(lstm);
           
          
           }
           
       });
    }
    

    public void addListbundling(){
        Map<String,Object>map = model.doOnUpdateDtl(txtbdldtlid.getText(), labeltotal.getValue());
        if (map.get("OutError").equals(0)) {
         add.setLabel("Update");
         newrecord();
        } else {
              Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
    @Listen("onClick=#listbox")
    public void onClickListDtl(){
        int index = listbox.getSelectedIndex();
        if (index >-1) {
            listDtlRegionalParam selected = (listDtlRegionalParam) listbox.getModel().getElementAt(index);
            txtregId.setText(selected.getRegional_id());
            txtregDesc.setText(selected.getRegional_desc());
            txtbdldtlid.setText(selected.getBundling_dtl_id());
            txtbundlingid.setText(selected.getBundling_id());
            txtqty.setText(selected.getQty());
            txtoutId.setText(selected.getId());
            txttresh.setText(selected.getTreshold_qty());
            add.setLabel("Update");
            btnreg.setDisabled(false);
        } 
    }
    
    
   @Listen("onClick=#btndelete")
   public void deleteLine(){
       if (listbox.getSelectedItems().isEmpty()) {
             Messagebox.show("No Record Selected","Bundling",Messagebox.OK,Messagebox.EXCLAMATION);   
       } else {
       Map<String,Object> map = model.doDeleteQtyByRegional(txtoutId.getText());
       if (map.get("OutError").equals(0)) {
        setrender();
        refresh();
                 addListbundling();
        newrecord();
        BundlingCTRL.listboxDetail();
       } else {
           Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.EXCLAMATION);      
       }
       
       }
       
   }
   
   @Listen("onClick=#newrecord")
   public void newrecord(){
       txtregId.setText("");
       txtregDesc.setText("");
       txttresh.setText("");
       txtqty.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]+";text-align:right");
       txtqty.setValue(0);
       txtregDesc.setText("");
       add.setLabel("Add Detail");
       txtoutId.setText("");
       btnreg.setDisabled(false);
   }
   
   public void insertqtyRegional(){
       
          Map<String,Object>map1= model.doInsertQtyByRegional(
                txtbundlingid.getText(), 
                txtbdldtlid.getText(),
                itemId.getText(),
                txtregId.getText(),
                txttresh.getText(), 
                txtqty.getValue().toString().replace(",", ""),
                userId);
        if (map1.get("OutError").equals(0)) {
            txtoutId.setText(map1.get("OutId").toString());
            btnreg.setDisabled(true);
//            Messagebox.show(map1.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.INFORMATION);
              updateSubDtl();
        } else {
            Messagebox.show(map1.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
        }
   }
   
   
   public void updateQtyRregional(){
       Map<String,Object>map = model.doUpdateQtyByRegional(txtoutId.getText(), txtqty.getValue().toString().replace(",", ""), userId);
       if (map.get("OutError").equals(0)) {
            updateSubDtl();
       } else {
           Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
       }
   }
   
   @Listen("onChanging=#txtoutId")
   public void buttonchange(){
       if (txtoutId.getText().isEmpty()) {
       } else {
             add.setLabel("Update");
       }
   }
   
   
     public void updateSubDtl(){
        try {
        Map<String,Object>map = model.doOnInsertSubDtl(txtbundlingid.getText(), txtbundlingno.getText(),txtregId.getText(),itemId.getText(),txtqty.getValue().toString().replace(",", ""),userId);
        if (map.get("OutError").equals(0)) {
        refresh();
        addListbundling();
        } else {
            Messagebox.show(map.get("OutMessages").toString(),"Bundling",Messagebox.OK,Messagebox.INFORMATION);
        }
    }  catch (NullPointerException e) {
        
         }
     
     }
     
     public void aftercompose(){
         String status = BundlingCTRL.txtstatus.getText();
         if (status.equalsIgnoreCase("draft")) {
             txtqty.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]+";text-align:right");
             txtregDesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
//             txttresh.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]+";text-align:right");
             txtqty.setValue(0);
         }else{
         txtqty.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]+";text-align:right");
          txtregDesc.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
          btnreg.setDisabled(true);
          add.setDisabled(true);
//          txttresh.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]+";text-align:right");
         }
     }
     
     
     
    @Listen("onClick=#Edit")
    public void btnEditSN() {
//        if (listbox.getItems().isEmpty()) {
//            Messagebox.show("Please Add Detail first","bundling Allocated",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
        String id;
        if (listbox.getSelectedCount() == 0) {
//        try{
//        List<Component>list =  listbox.getSelectedItem().getChildren();
//        id = ((Listcell)list.get(0)).getLabel();
//        }catch(Exception e){
//           Messagebox.show("Choose List First !", "Bundling : Message", Messagebox.OK, Messagebox.EXCLAMATION);          return ;   
//        }
            Messagebox.show("Please choose detaill","Bundling",Messagebox.OK,Messagebox.EXCLAMATION);
            return ;
        
            }else{

        txtregDesc.setText("");
        txttresh.setText("");
        txtqty.setText("0");
        
        Map<String, Object> map = model.dovalidateEditSN(txtbundlingid.getText(),txtregId.getText(),itemId.getText());
        if (map.get("OutError").equals(0)) {
                Map<String,Object> map1 = new HashMap();
                map1.put("bundling_id", txtbundlingid.getText());
                map1.put("item", itemId.getText());
                map1.put("reg",txtregId.getText());
                Window w = (Window) Executions.createComponents("/Tcash/ListEditSNBundling.zul", null, map1);
                w.setAttribute("parentController", this);
        } else {
              Messagebox.show(map.get("OutMessages").toString());
        }
    }
    }
    }
    

