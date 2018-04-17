package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListselectParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListSelectRange extends  SelectorComposer<Component>{
    
    @Wire
    Window dlgSelectRange;
    
    @Wire
    Textbox txtwipDtlId,txtitemId,txtprodDtlId,txtitemlocid,txtfromDtlId,txttoDtlId,txflag1,txtqtyDtlId,txtitemDtlId,txtstatus;
    
     @Wire
    Paging userPaging,userPaging1;
     
    @Wire
    Listbox listboxSelect,listboxUnselect;
    
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;
    
  DetailWIPReceipt  parentController1;
    
     @Listen("onPaging=#userPaging1")
    public void onPagingUserPaging1(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModel1(startPageNumber);
    }
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModel(startPageNumber);
    }
    
    private void refreshModel(int activePage){
        userPaging.setPageSize(pageSize);
        refresh(activePage);
    }
    private void refreshModel1(int activePage){
        userPaging.setPageSize(pageSize);
        refreshun(activePage);
    }
    
    @Listen("onCreate=#dlgSelectRange")
    public void onCreateWindow(){
        parentController1=(DetailWIPReceipt)dlgSelectRange.getAttribute("parentController1");
    requery();
//    requeryun();
//    LisInvSelecSN();
    LisInvUnSelecSN();
}
    
    @Listen("onClick=#btnClose")
    public void close(){
        parentController1.selecthdr();
        dlgSelectRange.detach();
    }
    
    ModelTcashCTLR model= new ModelTcashCTLR();
    
    
    public void requery(){
        Path pg= new Path("/dlgSelectRange");
//        Listbox listbox =(Listbox)new Path(pg,"listboxSelect").getComponent();
//        listbox.getItems().clear();
//        listbox.setSizedByContent(true);
        List<Integer> jumlahRecord = model.getCountListSelectSnTemp(txtwipDtlId.getText());
             if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refresh(1);

    }
    
    public void refresh(int activePage){
        
        listboxSelect.getItems().clear();
        listboxSelect.setSizedByContent(true);
        List<ListselectParam>ListselectParam=model.getListSelectSnTemp(txtwipDtlId.getText(),""+activePage,""+pageSize);
        for (ListselectParam ListselectParam1 : ListselectParam) {
            
            Listcell item = new Listcell();
            Listcell From = new Listcell();
            Listcell to = new Listcell();
            Listcell qty = new Listcell();
            Listcell item_detail_id = new Listcell();
            Listcell itemlocid = new Listcell();
            
            
            item.setLabel(ListselectParam1.getItemid());
            From.setLabel(ListselectParam1.getFromsn());
            to.setLabel(ListselectParam1.getTosn());
            qty.setLabel(ListselectParam1.getQty());
            item_detail_id.setLabel(ListselectParam1.getItemdetailid());
            itemlocid.setLabel(ListselectParam1.getItemlocid());
            
            Listitem listitem = new Listitem();
            listitem.appendChild(item);
            listitem.appendChild(From);
            listitem.appendChild(to);
            listitem.appendChild(qty);
            listitem.appendChild(item_detail_id);
            listitem.appendChild(itemlocid);
            
            listitem.addEventListener(Events.ON_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String item_detail_id = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    
                    Path pg = new Path("/dlgSelectRange");
                    Textbox txtfrom = (Textbox)new Path(pg,"txtfromDtlId").getComponent();
                    Textbox txttoDtlId = (Textbox)new Path(pg,"txttoDtlId").getComponent();
                    Textbox txtitemDtlId = (Textbox)new Path(pg,"txtitemDtlId").getComponent();
                     Textbox txflag1 = (Textbox)new Path(pg,"txflag1").getComponent();
                     Textbox txtitemlocid = (Textbox)new Path(pg,"txtitemlocid").getComponent();
                    
                    txflag1.setText("");
                    txtfrom.setText("");
                    txttoDtlId.setText("");
                    txtitemDtlId.setText("");
                    txtitemlocid.setText("");
                    
                    txflag1.setText("select");
                    txtfrom.setText(from);
                    txttoDtlId.setText(to);
                    txtitemDtlId.setText(item_detail_id);
                    txtitemlocid.setText(itemlocid);
                    
                }
                
            });
            
            listitem.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdtlid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    form(item, from, to, qty,itemdtlid,itemlocid);
                }
                
            });
                    listboxSelect.appendChild(listitem);
        }
    }
    
    
    @Listen("onClick=#btnSelect")
    public void openWindowSelect(){
            validateStatusSelect();
        if (!txtitemDtlId.getText().isEmpty()) {
            if (txflag1.getText().equals("select")) {
                
           
             txflag1.setText("");
             txflag1.setText("select");
     Map<String,Object>mapper =model.doValidateSelectSn(txtitemDtlId.getText());
       
        if (mapper.get("OutError").equals(0)) {
       Map<String,Object>map =new HashMap<String, Object>();
       map.put("InWipRcpId", txtwipDtlId.getText());
       map.put("item", txtitemId.getText());
       map.put("itemdtl", txtitemDtlId.getText());
       map.put("itemlocid", txtitemlocid.getText());
       map.put("from", txtfromDtlId.getText());
       map.put("to", txttoDtlId.getText());
       map.put("qty",txtqtyDtlId.getText());
       map.put("flag",txflag1.getText());
       Window w = (Window)Executions.createComponents("/Tcash/SelectSN.zul", null, map);
       w.setAttribute("parentController", this);
       w.doModal();
        } else {
            Messagebox.show(mapper.get("OutMessages").toString(),"Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
         } else {
                  Messagebox.show("This Button for Select Range S/N","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
            }
        } else {
            Messagebox.show("No Record Selected","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
       
    }
    
    @Listen("onClick=#btnUnselect")
    public void openWindowunSelect(){
            validateStatusSelect();
         if (!txtitemDtlId.getText().isEmpty()) {
              if (txflag1.getText().equals("unselect")) {
        txflag1.setText("");
        txflag1.setText("unselect");
       Map<String,Object>mapper =model.doValidateSelectSn(txtitemDtlId.getText());
       if (mapper.get("OutError").equals(0)) {
       Map<String,Object>map =new HashMap<String, Object>();
       map.put("InWipRcpId", txtwipDtlId.getText());
       map.put("item", txtitemId.getText());
       map.put("itemdtl", txtitemId.getText());
       map.put("itemlocid", txtitemlocid.getText());
       map.put("from", txtfromDtlId.getText());
       map.put("to", txttoDtlId.getText());
       map.put("qty",txtqtyDtlId.getText());
       map.put("flag", txflag1.getText());
       Window w = (Window)Executions.createComponents("/Tcash/SelectSN.zul", null, map);
        w.setAttribute("parentController", this);
       w.doModal();
        } else {
            Messagebox.show(mapper.get("OutMessages").toString(),"Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
        } else {
                  Messagebox.show("This Button for UnSelect Range S/N","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
            }
        } else {
            Messagebox.show("No Record Selected","Select S/N",Messagebox.OK,Messagebox.EXCLAMATION);
        }
       
    }
    
        
    public void form(String item,String from,String to,String qty,String itemdtlid,String itemlocid){
       Map<String,Object>map =new HashMap<String, Object>();
       map.put("item", item);
       map.put("from", from);
       map.put("to", to);
       map.put("qty",qty);
       map.put("item_detail_id",itemdtlid);
       map.put("itemlocid",itemlocid);
       map.put("flag",txflag1.getText());
       Window w = (Window)Executions.createComponents("/Tcash/SelectSN.zul", null, map);
       w.setAttribute("parentController", this);
       w.doModal();
    }
    
    public void LisInvSelecSN(){
        listboxSelect.getItems().clear();
        listboxSelect.setSizedByContent(true);
        List<Integer> jumlahRecord = model.getCountListInvSelectSN(txtwipDtlId.getText());
             if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/dlgSelectRange");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refreshInvSelectSN(1);
    }

    private void refreshInvSelectSN(int activePage) {
         listboxSelect.getItems().clear();
//        listboxUnselect.getItems().clear();
        List<ListselectParam>ListselectParam=model.getListInvSelectSN(txtwipDtlId.getText(),""+activePage,""+pageSize);
        for (ListselectParam ListselectParam1 : ListselectParam) {
            
            Listcell item = new Listcell();
            Listcell From = new Listcell();
            Listcell to = new Listcell();
            Listcell qty = new Listcell();
            Listcell item_detail_id = new Listcell();
            Listcell itemlocid = new Listcell();
            
            item.setLabel(ListselectParam1.getItemid());
            From.setLabel(ListselectParam1.getFromsn());
            to.setLabel(ListselectParam1.getTosn());
            qty.setLabel(ListselectParam1.getQty());
            item_detail_id.setLabel(ListselectParam1.getItemdetailid());
            itemlocid.setLabel(ListselectParam1.getItemlocid());
            
            Listitem listitem = new Listitem();
            listitem.appendChild(item);
            listitem.appendChild(From);
            listitem.appendChild(to);
            listitem.appendChild(qty);
            listitem.appendChild(item_detail_id);
            listitem.appendChild(itemlocid);
            
            listboxSelect.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdtlid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    
                    form(item, from, to, qty,itemdtlid,itemlocid);
                }
                
            });
                    listboxSelect.appendChild(listitem);
                    
        }   
    }

    public void requeryun() {
          Path pg= new Path("/dlgSelectRange");
        Listbox listbox =(Listbox)new Path(pg,"listboxUnselect").getComponent();
        listbox.getItems().clear();
        listbox.setSizedByContent(true);
        List<Integer> jumlahRecord = model.getCountListSelectSnTemp(txtwipDtlId.getText());
             if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Paging page = (Paging) new Path(pg, "userPaging1").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refreshun(1);
    }

    private void refreshun(int activePage) {
         
//        listboxSelect.getItems().clear();
//        listboxUnselect.getItems().clear();
        List<ListselectParam>ListselectParam=model.getListInvSelectSN(txtwipDtlId.getText(),""+activePage,""+pageSize);
        for (ListselectParam ListselectParam1 : ListselectParam) {
            
            Listcell item = new Listcell();
            Listcell From = new Listcell();
            Listcell to = new Listcell();
            Listcell qty = new Listcell();
            Listcell itemdtlid = new Listcell();
            Listcell ItemLocId = new Listcell();
            
            
            item.setLabel(ListselectParam1.getItemid());
            From.setLabel(ListselectParam1.getFromsn());
            to.setLabel(ListselectParam1.getTosn());
            qty.setLabel(ListselectParam1.getQty());
            itemdtlid.setLabel(ListselectParam1.getItemdetailid());
            ItemLocId.setLabel(ListselectParam1.getItemlocid());
            
            Listitem listitem = new Listitem();
            listitem.appendChild(item);
            listitem.appendChild(From);
            listitem.appendChild(to);
            listitem.appendChild(qty);
            listitem.appendChild(itemdtlid);
            listitem.appendChild(ItemLocId);
            
            listitem.addEventListener(Events.ON_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdtlid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String ItemLocId = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    
                    Path pg = new Path("/dlgSelectRange");
                    Textbox txtfrom = (Textbox)new Path(pg,"txtfromDtlId").getComponent();
                    Textbox txttoDtlId = (Textbox)new Path(pg,"txttoDtlId").getComponent();
                    Textbox txtitemDtlId = (Textbox)new Path(pg,"txtitemDtlId").getComponent();
                    Textbox txflag1 = (Textbox)new Path(pg,"txflag1").getComponent();
                    Textbox txtitemlocid = (Textbox)new Path(pg,"txtitemlocid").getComponent();
                    
                    
                    
                    txflag1.setText("");
                    txtfrom.setText("");
                    txttoDtlId.setText("");
                    txtitemDtlId.setText("");
                    txtitemlocid.setText("");
                    
                    txflag1.setText("unselect");
                    txtfrom.setText(from);
                    txttoDtlId.setText(to);
                    txtitemlocid.setText(ItemLocId);
                    txtitemDtlId.setText(itemdtlid);
                    
                }
                
            });
            
            listitem.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdtlid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
              
                    
                    form(item, from, to, qty,itemdtlid,itemlocid);
                }
                
            });
                    listboxUnselect.appendChild(listitem);
        }
    }
    
     public void LisInvUnSelecSN(){
        listboxUnselect.getItems().clear();
        listboxUnselect.setSizedByContent(true);
        List<Integer> jumlahRecord = model.getCountListInvSelectSN(txtwipDtlId.getText());
             if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/dlgSelectRange");
        Paging page = (Paging) new Path(pg, "userPaging1").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

         refreshInvUnSelectSN(1);
    }

    private void refreshInvUnSelectSN(int activePage) {
    
        listboxUnselect.getItems().clear();
        List<ListselectParam>ListselectParam=model.getListInvSelectSN(txtwipDtlId.getText(),""+activePage,""+pageSize);
        for (ListselectParam ListselectParam1 : ListselectParam) {
            
            Listcell item = new Listcell();
            Listcell From = new Listcell();
            Listcell to = new Listcell();
            Listcell qty = new Listcell();
            Listcell item_detail_id = new Listcell();
            Listcell itemlocid = new Listcell();
            
            item.setLabel(ListselectParam1.getItemid());
            From.setLabel(ListselectParam1.getFromsn());
            to.setLabel(ListselectParam1.getTosn());
            qty.setLabel(ListselectParam1.getQty());
            item_detail_id.setLabel(ListselectParam1.getItemdetailid());
            itemlocid.setLabel(ListselectParam1.getItemlocid());
            
            Listitem listitem = new Listitem();
            listitem.appendChild(item);
            listitem.appendChild(From);
            listitem.appendChild(to);
            listitem.appendChild(qty);
            listitem.appendChild(item_detail_id);
            listitem.appendChild(itemlocid);
            
            listitem.addEventListener(Events.ON_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String item_detail_id = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    
                    Path pg = new Path("/dlgSelectRange");
                    Textbox txtfrom = (Textbox)new Path(pg,"txtfromDtlId").getComponent();
                    Textbox txttoDtlId = (Textbox)new Path(pg,"txttoDtlId").getComponent();
                    Textbox txtitemDtlId = (Textbox)new Path(pg,"txtitemDtlId").getComponent();
                    Textbox txflag1 = (Textbox)new Path(pg,"txflag1").getComponent();
                    Textbox txtitemlocid = (Textbox)new Path(pg,"txtitemlocid").getComponent();
                    
                    
                    
                    txflag1.setText("");
                    txtfrom.setText("");
                    txttoDtlId.setText("");
                    txtitemDtlId.setText("");
                    txtitemlocid.setText("");
                    
                    txflag1.setText("unselect");
                    txtfrom.setText(from);
                    txttoDtlId.setText(to);
                    txtitemDtlId.setText(item_detail_id);
                    txtitemlocid.setText(itemlocid);
                }
                });
            listitem.addEventListener(Events.ON_DOUBLE_CLICK,new EventListener(){
                @Override
                public void onEvent(Event t) throws Exception {
                    String item = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String from = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String to = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String qty = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdtlid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemlocid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    
                    form(item, from, to, qty,itemdtlid,itemlocid);
                }
            });
                   listboxUnselect.appendChild(listitem);
                    
        }   
     
    }
    
    void clearparam(){
        txtfromDtlId.setText("");
        txttoDtlId.setText("");
        txtitemDtlId.setText("");
        txtitemId.setText("");
        txtitemlocid.setText("");
        txtqtyDtlId.setText("");
        txtprodDtlId.setText("");
    }
    
    void validateStatusSelect(){
        if (txtstatus.getText().equals("1")) {
            System.out.println("lewat validateStatusSelect");
        } else if (txtstatus.getText().equals("2")) {
            Messagebox.show("This WIP Receipt Number Has been Submitted","Select Range",Messagebox.OK,Messagebox.EXCLAMATION);
        } else if (txtstatus.getText().equals("3")) {
            Messagebox.show("This WIP Receipt Number Has been Approved","Select Range",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
}
