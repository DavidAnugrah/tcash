/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.PcListHdrParam;
import id.my.berkah.util.IDefines.RETURN_STATUS;
import id.my.berkah.util.controller.LovController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;


public class ListPurchaseContract extends SelectorComposer<Component>{
    @Wire
    Paging userPaging;
       
    @Wire
    Window List_Detail_Purchase_Contract,win_list_PC_find;
       
    @Wire
    Textbox txcontractId,txcontract,txdesc,txcontractdate,txsuppid,txsupp,txsupName,txtTop,txtTopname,txtcurrency,
            txsupId,txtagent,txtagentname,txtcontractstat,txtsupsite,txtsupsiteid,txtsupsitename,txtcontractmap,txtquantity
            ,txttermidate,txttermiby,txtupdateby,txtupdatedate,txtefecdate,txtexpidate,txtstatusdate,txtamunt,txtcreateper,
            txtcreatedate,txtcreateby,txtcontracstatus,txtstatusfnd,
            txtsupid,txtsuppliercode,txtsupdesc;
    
    @Wire
    Combobox cmbstatus;
    
    @Wire
    Datebox dateboxfrom,dateboxto;
    
    ModelTcashCTLR model= new ModelTcashCTLR();
    
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;
    
    
    @Override
      public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_PC_find.setVisible(false);
    }
       
        @Listen("onClick=#find")
   public void filter(){
       win_list_PC_find.setVisible(true);
       
   }
   
   @Listen("onClick=#goFind")
    public void findContract() {

        Path parent1 = new Path("/List_Purchase_Contract/win_list_PC_find");
        Textbox txtSupid = (Textbox) new Path(parent1, "txtsupid").getComponent();
        Textbox txtContract = (Textbox) new Path(parent1, "txtContract").getComponent();
        Datebox Dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox Dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox statusfnd = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();
        
        List<Integer> jumlahRecord = model.getCountPcListHdr(txtSupid.getText(), txtContract.getText(),Dateboxfrom.getText(),Dateboxto.getText(),statusfnd.getText());
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Purchase_Contract");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);

        closefind();
    }
   
    @Listen("onClick=#new")
   public void addnew(){
       Map<String ,Object> mapper= new HashMap<>();
//       mapper.put("requisitionid", txtreqid.getText());
         org.zkoss.zul.Window window = (org.zkoss.zul.Window)Executions.createComponents(
                "/Tcash/PurchaseContractCTRL.zul", null,null);
         window.setAttribute("parentController",this);
        window.doModal();
     }
   
    @Listen("onClick=#Close1")
   public void closeFind(){
       win_list_PC_find.setVisible(false);
   }
   
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }
    
       @Listen("onClick=#close")
   public void closelis(){
       List_Detail_Purchase_Contract.detach();
   }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);//, pageSize);
    }
    
      @Listen("onClick=#detail")
   public void viewdetail(){
       Map<String,Object> map = new HashMap<>();
       map.put("InContractId", txcontractId.getText());
         if (txcontractId.getText().equals("")) {
             Messagebox.show("Silahkan Pilih Contract terlebih dahulu");
         }else{
         org.zkoss.zul.Window window = (org.zkoss.zul.Window)Executions.createComponents(
                "/Tcash/ListDetailPurchaseContract.zul", null,map);
          window.setAttribute("parentController",this);
        window.doModal();
     }
   }
    
      @Listen("onClick=#refresh")
     public void requery(){
         Path parent1 = new Path("/List_Purchase_Contract/win_list_PC_find");
      Textbox txtContract = (Textbox) new Path(parent1, "txtContract").getComponent();
      Textbox txtsuppliercode = (Textbox) new Path(parent1, "txtsuppliercode").getComponent();
        Datebox Dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox Dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox statusfnd = (Textbox) new Path(parent1, "txtstatusfnd").getComponent(); 
        clearFindParam();
        List<Integer> jumlahRecord = model.getCountPcListHdr(txtsuppliercode.getValue(),txtContract.getValue(),Dateboxfrom.getText(),Dateboxto.getText(),statusfnd.getText());
              
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        

        Path pg = new Path("/List_Purchase_Contract");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refresh(1);
        
     }

    private void refresh(int activePage) {
     Path parent1 = new Path("/List_Purchase_Contract/win_list_PC_find");
     Textbox txtsuppliercode = (Textbox) new Path(parent1, "txtsuppliercode").getComponent();
      Textbox txtContract = (Textbox) new Path(parent1, "txtContract").getComponent();
      Datebox Dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox Dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox statusfnd = (Textbox) new Path(parent1, "txtstatusfnd").getComponent(); 
        
        
      Path pg = new Path("/List_Purchase_Contract");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();
        
       listbox1.getItems().clear();
       listbox1.setSizedByContent(true);
       List<PcListHdrParam> PcListHdrParam = model.getPcListHdr(txtsuppliercode.getValue(),txtContract.getValue(),Dateboxfrom.getText(),Dateboxto.getText(),statusfnd.getText(),""+activePage,""+pageSize);
       for (PcListHdrParam PcListHdrParam1 : PcListHdrParam) {
           
            Listcell contractid = new Listcell();
            Listcell contract = new Listcell();
            Listcell description = new Listcell();
            Listcell contractdate = new Listcell();
            Listcell supplierid = new Listcell();
            Listcell supplier = new Listcell();
            Listcell suppliername = new Listcell();
            Listcell termsofpayment = new Listcell();
            Listcell termsofpaymentname = new Listcell();
            Listcell currency = new Listcell();
            Listcell forwardingagent = new Listcell();
            Listcell forwardingagentname = new Listcell();
            Listcell contractstatus = new Listcell();
            Listcell suppliersite = new Listcell();
            Listcell siteid = new Listcell();
            Listcell sitename = new Listcell();
            Listcell contractmapno = new Listcell();
            Listcell agreedquantity = new Listcell();
            Listcell terminatedate = new Listcell();
            Listcell terminateby = new Listcell();
            Listcell agreedamount = new Listcell();
            Listcell createdperiod = new Listcell();
            Listcell creationdate = new Listcell();
            Listcell createdby = new Listcell();
            Listcell lastupdatedate = new Listcell();
            Listcell lastupdatedby = new Listcell();
            Listcell effectivedate = new Listcell();
            Listcell expirydate = new Listcell();
            Listcell contractstatusname = new Listcell();
           
            contractid.setLabel(PcListHdrParam1.getContractid());
            contract.setLabel(PcListHdrParam1.getContract());
            description.setLabel(PcListHdrParam1.getDescription());
            contractdate.setLabel(PcListHdrParam1.getContractdate());
            supplierid.setLabel(PcListHdrParam1.getSupplierid());
            supplier.setLabel(PcListHdrParam1.getSupplier());
            supplier.setStyle("text-align:center");
            suppliername.setLabel(PcListHdrParam1.getSuppliername());
            termsofpayment.setLabel(PcListHdrParam1.getTermsofpayment());
            termsofpaymentname.setLabel(PcListHdrParam1.getTermsofpaymentname());
            currency.setLabel(PcListHdrParam1.getCurrency());
            currency.setStyle("text-align:center");
            forwardingagent.setLabel(PcListHdrParam1.getForwardingagent());
            forwardingagent.setStyle("text-align:center");
            forwardingagentname.setLabel(PcListHdrParam1.getForwardingagentname());
            contractstatus.setLabel(PcListHdrParam1.getContractstatus());
            suppliersite.setLabel(PcListHdrParam1.getSuppliersite());
            suppliersite.setStyle("text-align:center");
            siteid.setLabel(PcListHdrParam1.getSiteid());
            sitename.setLabel(PcListHdrParam1.getSitename());
            contractmapno.setLabel(PcListHdrParam1.getContractmapno());
            agreedquantity.setLabel(PcListHdrParam1.getAgreedquantity());
            agreedquantity.setStyle("text-align:right");
            terminatedate.setLabel(PcListHdrParam1.getTerminatedate());
            terminateby.setLabel(PcListHdrParam1.getTerminateby());
            agreedamount.setLabel(PcListHdrParam1.getAgreedamount());
            agreedamount.setStyle("text-align:right");
            createdperiod.setLabel(PcListHdrParam1.getCreatedperiod());
            creationdate.setLabel(PcListHdrParam1.getCreationdate());
            createdby.setLabel(PcListHdrParam1.getCreatedby());
            lastupdatedate.setLabel(PcListHdrParam1.getLastupdatedate());
            lastupdatedby.setLabel(PcListHdrParam1.getLastupdatedby());
            effectivedate.setLabel(PcListHdrParam1.getEffectivedate());
            expirydate.setLabel(PcListHdrParam1.getExpirydate());
            contractstatusname.setLabel((PcListHdrParam1.getContractstatusname()));
      
            
            Listitem listitem = new Listitem();
            listitem.appendChild(contractid);
            listitem.appendChild(contract);
            listitem.appendChild(contractdate);
            listitem.appendChild(supplierid);
            listitem.appendChild(supplier);
            listitem.appendChild(suppliername);
            listitem.appendChild(description);
            listitem.appendChild(termsofpayment);
            listitem.appendChild(termsofpaymentname);
            listitem.appendChild(currency);
            listitem.appendChild(forwardingagent);
            listitem.appendChild(forwardingagentname);
            listitem.appendChild(contractstatus);
            listitem.appendChild(suppliersite);
            listitem.appendChild(siteid);
            listitem.appendChild(sitename);
            listitem.appendChild(contractmapno);
            listitem.appendChild(agreedquantity);
            listitem.appendChild(agreedamount);
            listitem.appendChild(effectivedate);
            listitem.appendChild(expirydate);
            listitem.appendChild(createdperiod);
            listitem.appendChild(creationdate);
            listitem.appendChild(createdby);
            listitem.appendChild(lastupdatedate);
            listitem.appendChild(lastupdatedby);
            listitem.appendChild(terminatedate);
            listitem.appendChild(terminateby);
            listitem.appendChild(contractstatusname);
            
            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String contractid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String contract = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String description = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String contractdate = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String supplierid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String supplier = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String suppliername = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String termsofpayment = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String termsofpaymentname = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String currency = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String forwardingagent = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String forwardingagentname = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String contractstatus = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String suppliersite = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String siteid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String sitename = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    String contractmapno = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                    String agreedquantity = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                    String terminatedate = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                    String terminateby = ((Listcell) t.getTarget().getChildren().get(19)).getLabel();
                    String agreedamount = ((Listcell) t.getTarget().getChildren().get(20)).getLabel();
                    String createdperiod = ((Listcell) t.getTarget().getChildren().get(21)).getLabel();
                    String creationdate = ((Listcell) t.getTarget().getChildren().get(22)).getLabel();
                    String createdby = ((Listcell) t.getTarget().getChildren().get(23)).getLabel();
                    String lastupdatedate = ((Listcell) t.getTarget().getChildren().get(24)).getLabel();
                    String lastupdatedby = ((Listcell) t.getTarget().getChildren().get(25)).getLabel();
                    String effectivedate = ((Listcell) t.getTarget().getChildren().get(26)).getLabel();
                    String expirydate = ((Listcell) t.getTarget().getChildren().get(27)).getLabel();
                    String contractstatusname = ((Listcell) t.getTarget().getChildren().get(28)).getLabel();
                    
                     Path parent = new Path("/List_Purchase_Contract");
                     Textbox txcontractId = (Textbox) new Path(parent, "txcontractId").getComponent();
                     Textbox txcontract = (Textbox) new Path(parent, "txcontract").getComponent();
                     Textbox txdesc = (Textbox) new Path(parent, "txdesc").getComponent();
                     Textbox txcontractdate = (Textbox) new Path(parent, "txcontractdate").getComponent();
                     Textbox txsuppid = (Textbox) new Path(parent, "txsuppid").getComponent();
                     Textbox txsupp = (Textbox) new Path(parent, "txsupp").getComponent();
                     Textbox txsupName = (Textbox) new Path(parent, "txsupName").getComponent();
                     Textbox txtTop = (Textbox) new Path(parent, "txtTop").getComponent();
                     Textbox txtTopname = (Textbox) new Path(parent, "txtTopname").getComponent();
                     Textbox txtcurrency = (Textbox) new Path(parent, "txtcurrency").getComponent();
                     Textbox txtagent = (Textbox) new Path(parent, "txtagent").getComponent();
                     Textbox txtagentname = (Textbox) new Path(parent, "txtagentname").getComponent();
                     Textbox txtcontractstat = (Textbox) new Path(parent, "txtcontractstat").getComponent();
                     Textbox txtsupsite = (Textbox) new Path(parent, "txtsupsite").getComponent();
                     Textbox txtsupsiteid = (Textbox) new Path(parent, "txtsupsiteid").getComponent();
                     Textbox txtsupsitename = (Textbox) new Path(parent, "txtsupsitename").getComponent();
                     Textbox txtcontractmap = (Textbox) new Path(parent, "txtcontractmap").getComponent();
                     Textbox txtquantity = (Textbox) new Path(parent, "txtquantity").getComponent();
                     Textbox txttermidate = (Textbox) new Path(parent, "txttermidate").getComponent();
                     Textbox txttermiby = (Textbox) new Path(parent, "txttermiby").getComponent();
                     Textbox txtamunt = (Textbox) new Path(parent, "txtamunt").getComponent();
                     Textbox txtcreateper = (Textbox) new Path(parent, "txtcreateper").getComponent();
                     Textbox txtcreatedate = (Textbox) new Path(parent, "txtcreatedate").getComponent();
                     Textbox txtcreateby = (Textbox) new Path(parent, "txtcreateby").getComponent();
                     Textbox txtupdateby = (Textbox) new Path(parent, "txtupdateby").getComponent();
                     Textbox txtupdatedate = (Textbox) new Path(parent, "txtupdatedate").getComponent();
                     Textbox txtefecdate = (Textbox) new Path(parent, "txtefecdate").getComponent();
                     Textbox txtexpidate = (Textbox) new Path(parent, "txtexpidate").getComponent();
                     Textbox txtcontracstatus = (Textbox) new Path(parent, "txtcontracstatus").getComponent();
                   
                  
                    txcontractId.setText(contractid);
                    txcontract.setText(contract);
                    txdesc.setText(description);
                    txcontractdate.setText(contractdate);
                    txsuppid.setText(supplierid);
                    txsupp.setText(supplier);
                    txsupName.setText(suppliername);
                    txtTop.setText(termsofpayment);
                    txtTopname.setText(termsofpaymentname);
                    txtcurrency.setText(currency);
                    txtagent.setText(forwardingagent);
                    txtagentname.setText(forwardingagentname);
                    txtcontractstat.setText(contractstatus);
                    txtsupsite.setText(suppliersite);
                    txtsupsiteid.setText(siteid);
                    txtsupsitename.setText(sitename);
                    txtcontractmap.setText(contractmapno);
                    txtquantity.setText(agreedquantity);
                    txttermidate.setText(terminatedate);
                    txttermiby.setText(terminateby);
                    txtamunt.setText(agreedamount);
                    txtcreateper.setText(createdperiod);
                    txtcreatedate.setText(creationdate);
                    txtcreateby.setText(createdby);
                    txtupdateby.setText(lastupdatedby);
                    txtupdatedate.setText(lastupdatedate);
                    txtefecdate.setText(effectivedate);
                    txtexpidate.setText(expirydate);
                    txtcontracstatus.setText(contractstatusname);
                }
            });
  
             listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String contractid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String contract = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String contractdate = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String supplierid = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String supplier = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String suppliername = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String description = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String termsofpayment = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String termsofpaymentname = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String currency = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String forwardingagent = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String forwardingagentname = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String contractstatus = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String suppliersite = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String siteid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String sitename = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    String contractmapno = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                    String agreedquantity = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                    String agreedamount = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                    String effectivedate = ((Listcell) t.getTarget().getChildren().get(19)).getLabel();
                    String expirydate = ((Listcell) t.getTarget().getChildren().get(20)).getLabel();
                    String createdperiod = ((Listcell) t.getTarget().getChildren().get(21)).getLabel();
                    String creationdate = ((Listcell) t.getTarget().getChildren().get(22)).getLabel();
                    String createdby = ((Listcell) t.getTarget().getChildren().get(23)).getLabel();
                    String lastupdatedate = ((Listcell) t.getTarget().getChildren().get(24)).getLabel();
                    String lastupdatedby = ((Listcell) t.getTarget().getChildren().get(25)).getLabel();
                    String terminatedate = ((Listcell) t.getTarget().getChildren().get(26)).getLabel();
                    String terminateby = ((Listcell) t.getTarget().getChildren().get(27)).getLabel();
                    String contractstatusname = ((Listcell) t.getTarget().getChildren().get(28)).getLabel();
                    form(contractid, contract, description, contractdate, supplierid, supplier, suppliername, termsofpayment, termsofpaymentname, currency, forwardingagent, forwardingagentname, contractstatus, suppliersite, siteid, sitename, contractmapno, agreedquantity, terminatedate, terminateby, agreedamount, createdperiod, creationdate, createdby, lastupdatedate, lastupdatedby, effectivedate, expirydate, contractstatusname);
                }
            });
              listbox1.appendChild(listitem);
       }
    }
    
    @Listen("onCLick=#Close1")
    public void closefind(){
        win_list_PC_find.setVisible(false);
    }
    
    public void form(String contractid,
            String contract,
            String description,
            String contractdate,
            String supplierid,
            String supplier,
            String suppliername,
            String termsofpayment,
            String termsofpaymentname,
            String currency,
            String forwardingagent,
            String forwardingagentname,
            String contractstatus,
            String suppliersite,
            String siteid,
            String sitename,
            String contractmapno,
            String agreedquantity,
            String terminatedate,
            String terminateby,
            String agreedamount,
            String createdperiod,
            String creationdate,
            String createdby,
            String lastupdatedate,
            String lastupdatedby,
            String effectivedate,
            String expirydate,
            String contractstatusname
            ) {
         Date date1 = new Date();
         Date date2 = new Date();
         
          SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date1 = dt1.parse(effectivedate);
        } catch (ParseException ex) {
            Logger.getLogger(ListPurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            date2 = dt1.parse(expirydate);
        } catch (ParseException ex) {
            Logger.getLogger(ListPurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
        }
       Map<String,Object> map = new HashMap<>();
       map.put("contractid", contractid);
       map.put("contract", contract);
       map.put("description", description);
       map.put("contractdate", contractdate);
       map.put("supplierid", supplierid);
       map.put("supplier", supplier);
       map.put("suppliername", suppliername);
       map.put("termsofpayment", termsofpayment);
       map.put("termsofpaymentname", termsofpaymentname);
       map.put("currency", currency);
       map.put("forwardingagent", forwardingagent);
       map.put("forwardingagentname", forwardingagentname);
       map.put("contractstatus", contractstatus);
       map.put("suppliersite", suppliersite);
       map.put("siteid", siteid);
       map.put("sitename", sitename);
       map.put("contractmapno", contractmapno);
       map.put("agreedquantity", agreedquantity);
       map.put("terminatedate", terminatedate);
       map.put("terminateby", terminateby);
       map.put("agreedamount", agreedamount);
       map.put("createdperiod", createdperiod);
       map.put("creationdate", creationdate);
       map.put("createdby", createdby);
       map.put("lastupdatedate", lastupdatedate);
       map.put("lastupdatedby", lastupdatedby);
       map.put("effectivedate", date1);
       map.put("expirydate", date2);
      map.put("contractstatusname", contractstatusname);
      
        Window window = (Window)Executions.createComponents(
                "/Tcash/PurchaseContractCTRL.zul", null,map);
         window.setAttribute("parentController",this);
        window.doModal();
   }
    
    @Listen("onClick=#edit")
    public void addedit() throws ParseException{
         if (!txcontractId.getText().equals("")) {
         Date date1 = new Date();
         Date date2 = new Date();
         SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
         date1 = dt1.parse(txtefecdate.getValue());
        try {
            date2 = dt1.parse(txtexpidate.getText().equals("")? txtexpidate.getText():txtexpidate.getValue());
        } catch (ParseException ex) {
            Logger.getLogger(ListPurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
        }
       Map<String,Object> map = new HashMap<>();
       map.put("contractid", txcontractId.getText());
       map.put("contract", txcontract.getText());
       map.put("description", txdesc.getText());
       map.put("contractdate", txcontractdate.getText());
       map.put("supplierid", txsuppid.getText());
       map.put("supplier", txsupp.getText());
       map.put("suppliername", txsupName.getText());
       map.put("termsofpayment", txtTop.getText());
       map.put("termsofpaymentname", txtTopname.getText());
       map.put("currency", txtcurrency.getText());
       map.put("forwardingagent", txtagent.getText());
       map.put("forwardingagentname", txtagentname.getText());
       map.put("contractstatus", txtcontractstat.getText());
       map.put("suppliersite", txtsupsite.getText());
       map.put("siteid", txtsupsiteid.getText());
       map.put("sitename", txtsupsitename.getText());
       map.put("contractmapno", txtcontractmap.getText());
       map.put("agreedquantity", txtquantity.getText());
       map.put("terminatedate", txttermidate.getText());
       map.put("terminateby", txttermiby.getText());
       map.put("agreedamount", txtamunt.getText());
       map.put("createdperiod", txtcreateper.getText());
       map.put("creationdate", txtcreatedate.getText());
       map.put("createdby", txtcreateby.getText());
       map.put("lastupdatedate", txtupdatedate.getText());
       map.put("lastupdatedby", txtupdateby.getText());
       map.put("effectivedate", date1);
       map.put("expirydate", date2);
          map.put("contractstatusname",txtcontracstatus.getText());
            Window window = (Window)Executions.createComponents(
                "/Tcash/PurchaseContractCTRL.zul", null,map);
             window.setAttribute("parentController",this);
        window.doModal();
       } else {
             Messagebox.show("No record selected","Purchase Contract",Messagebox.OK,Messagebox.EXCLAMATION);
       }
    
       
    }

 
    private void clearFindParam() {
           Path parent1 = new Path("/List_Purchase_Contract/win_list_PC_find");
        Textbox txtsuppliercode = (Textbox) new Path(parent1, "txtsuppliercode").getComponent();
        Textbox txttxtsupdesc = (Textbox) new Path(parent1, "txtsupdesc").getComponent();
        Textbox txtContract = (Textbox) new Path(parent1, "txtContract").getComponent();
       Datebox Dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox Dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox statusfnd = (Textbox) new Path(parent1, "txtstatusfnd").getComponent(); 
        Combobox status = (Combobox) new Path(parent1, "cmbstatus").getComponent(); 
        
        txtContract.setText("");
        txtsuppliercode.setText("");
        txttxtsupdesc.setText("");
        Dateboxfrom.setValue(null);
        Dateboxto.setValue(null);
        statusfnd.setValue(null);
        status.setSelectedIndex(0);
    }
    
    @Listen("onClick=#bttonclear")
    public void clearParamFinder(){
        clearFindParam();
    }
    
//    @Listen("onCreate=#List_Purchase_Contract")
//    public void onCreateCombobox(){
//        cmbstatus.setModel(new ListModelList<>(RETURN_STATUS.DESC));
//    }
    
    @Listen("onSelect=#cmbstatus")
    public void selectStatus(){
        if (cmbstatus.getSelectedIndex()==0) {
            txtstatusfnd.setText("");
        } else  if (cmbstatus.getSelectedIndex()==1){
             txtstatusfnd.setText("1");
        }else  if (cmbstatus.getSelectedIndex()==2){
             txtstatusfnd.setText("2");
        }else{
             txtstatusfnd.setText("3");
        }
    }
    
    @Listen("onClick = #btnsup")
    public void lovsupplier() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Id\",supplier_number as \"Supplier Number\",supplier_name as \"Supplier Name\" from table(pkg_tcash_lov.LovSupplierPC(''))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSupplierPC('" + " " + "'))Where supplier_number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupid, txtsuppliercode, txtsupdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_PC_find);
        w.doModal();

    }
}
    
  

