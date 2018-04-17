/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWIPHdrParam;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListWIPHDR extends SelectorComposer<Component> {

    @Wire
    Window win_list_mntr_find;

    @Wire
    Paging userPaging;

    @Wire
    Listbox listbox;

    @Wire
    Textbox wiprcpid, wiprcpno, wiprcpdate, prodissueid, prodissuedesc, itemid, itemcode, itemdesc, quantity, unit, status, statusdesc, createperiod, createdate, createdby, approvedate, approvedby, submitdate, submitedby,txtstatusfnd;

    @Wire
    Combobox cmbstatus;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModel(startPageNumber);
    }

    private void refreshModel(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);//, pageSize);
    }

    @Listen("onCreate=#List_wip_receipt")
    public void onCretaeWindow() {
        win_list_mntr_find.setVisible(false);
    }

    @Listen("onClick=#find")
    public void dinfParameter() {
       win_list_mntr_find.setVisible(true);
    }

   

    @Listen("onClick=#new")
    public void opneWindow() {
        Window window = (Window) Executions.createComponents("/Tcash/WIPReceipt.zul", null, null);
        window.doModal();
    }


    @Listen("onClick=#refresh")
    public void requery() {
       
        Path pg1 = new Path("/List_wip_receipt");
        Listbox listbox1 = (Listbox) new Path(pg1, "listbox").getComponent();
        Path parent1 = new Path("/List_wip_receipt/win_list_mntr_find");
        Textbox txtwip = (Textbox) new Path(parent1, "txtwip").getComponent();
        Datebox dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox txtstat = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();
        Textbox txtprod = (Textbox) new Path(parent1, "txtprod").getComponent();

        listbox1.getItems().clear();
        listbox1.setSizedByContent(true);
          clearFindParam();
        List<Integer> jumlahRecord = model.getCountwipListFindparam(txtwip.getText(),dateboxfrom.getText(),dateboxto.getText(),txtstat.getText(),txtprod.getText());

        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/List_wip_receipt");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refresh(1);

    }

    private void refresh(int activePage) {
        Path parent1 = new Path("/List_wip_receipt/win_list_mntr_find");
        Textbox txtwip = (Textbox) new Path(parent1, "txtwip").getComponent();
        Datebox dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox txtstat = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();
        Textbox txtprod = (Textbox) new Path(parent1, "txtprod").getComponent();

        Path pg = new Path("/List_wip_receipt");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();
        listbox1.getItems();
        listbox1.setSizedByContent(true);
//        listbox1.setModel(null);
//        ListDataEvent listDataEvent = new ListDataEvent( ListDataEvent.CONTENTS_CHANGED, -1, -1);

        List<ListWIPHdrParam> listWIPHdrParam = model.getListFindHdr(txtwip.getText(),dateboxfrom.getText(),dateboxto.getText(),txtstat.getText(),txtprod.getText(), "" + activePage, "" + pageSize);
        for (ListWIPHdrParam listWIPHdrParam1 : listWIPHdrParam) {

            Listcell txtwiprcpid = new Listcell();
            Listcell txtwiprcpno = new Listcell();
            Listcell txtwiprcpdate = new Listcell();
            Listcell txtprodissueid = new Listcell();
            Listcell txtprodissuedesc = new Listcell();
            Listcell txtitemid = new Listcell();
            Listcell txtitemcode = new Listcell();
            Listcell txtitemdesc = new Listcell();
            Listcell txtquantity = new Listcell();
            Listcell txtunit = new Listcell();
            Listcell txtstatus = new Listcell();
            Listcell txtstatusdesc = new Listcell();
            Listcell txtcreateperiod = new Listcell();
            Listcell txtcreatedate = new Listcell();
            Listcell txtcreatedby = new Listcell();
            Listcell txtapprovedate = new Listcell();
            Listcell txtapprovedby = new Listcell();
            Listcell txtsubmitdate = new Listcell();
            Listcell txtsubmitedby = new Listcell();

            txtwiprcpid.setLabel(listWIPHdrParam1.getWiprcpid());
            txtwiprcpno.setLabel(listWIPHdrParam1.getWiprcpno());
            txtwiprcpdate.setLabel(listWIPHdrParam1.getWiprcpdate());
            txtprodissueid.setLabel(listWIPHdrParam1.getProdissueid());
            txtprodissuedesc.setLabel(listWIPHdrParam1.getProdissueno());
            txtitemid.setLabel(listWIPHdrParam1.getItemid());
            txtitemcode.setLabel(listWIPHdrParam1.getItemcode());
            txtitemdesc.setLabel(listWIPHdrParam1.getItemdescription());
            txtquantity.setLabel(listWIPHdrParam1.getQuantity());
            txtquantity.setStyle("text-align:right");
            txtunit.setLabel(listWIPHdrParam1.getUnit());
            txtstatus.setLabel(listWIPHdrParam1.getStatus());
            txtstatusdesc.setLabel(listWIPHdrParam1.getStatusdesc());
            txtcreateperiod.setLabel(listWIPHdrParam1.getCreateperiod());
            txtcreatedate.setLabel(listWIPHdrParam1.getCreatedate());
            txtcreatedby.setLabel(listWIPHdrParam1.getCreatedby());
            txtapprovedate.setLabel(listWIPHdrParam1.getApprovedate());
            txtapprovedby.setLabel(listWIPHdrParam1.getApprovedby());
            txtsubmitdate.setLabel(listWIPHdrParam1.getSubmitdate());
            txtsubmitedby.setLabel(listWIPHdrParam1.getSubmitedby());

            Listitem listitem = new Listitem();
            listitem.appendChild(txtwiprcpid);
            listitem.appendChild(txtwiprcpno);
            listitem.appendChild(txtwiprcpdate);
            listitem.appendChild(txtprodissueid);
            listitem.appendChild(txtprodissuedesc);
            listitem.appendChild(txtitemid);
            listitem.appendChild(txtitemcode);
            listitem.appendChild(txtitemdesc);
            listitem.appendChild(txtquantity);
            listitem.appendChild(txtunit);
            listitem.appendChild(txtstatus);
            listitem.appendChild(txtstatusdesc);
            listitem.appendChild(txtcreateperiod);
            listitem.appendChild(txtcreatedate);
            listitem.appendChild(txtcreatedby);
            listitem.appendChild(txtapprovedate);
            listitem.appendChild(txtapprovedby);
            listitem.appendChild(txtsubmitdate);
            listitem.appendChild(txtsubmitedby);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                      String wiprcpid1 = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                      String wiprcpno1 = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                      String wiprcpdate1 = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                      String prodissueid1 = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                      String prodissuedesc1 = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                      String itemid1 = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                      String itemcode1 = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                      String itemdesc1 = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                      String quantity1 = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                      String unit1 = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                      String status1 = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                      String statusdesc1 = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                      String createperiod1 = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                      String createdate1 = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                      String createdby1 = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                      String approvedate1 = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                      String approvedby1 = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                      String submitdate1 = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                      String submitedby1 = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                      
                      Path pg= new Path("/List_wip_receipt");
                      Textbox wiprcpid=(Textbox)new Path(pg,"wiprcpid").getComponent();
                      Textbox wiprcpno=(Textbox)new Path(pg,"wiprcpno").getComponent();
                      Textbox wiprcpdate=(Textbox)new Path(pg,"wiprcpdate").getComponent();
                      Textbox prodissueid=(Textbox)new Path(pg,"prodissueid").getComponent();
                      Textbox prodissuedesc=(Textbox)new Path(pg,"prodissuedesc").getComponent();
                      Textbox itemid=(Textbox)new Path(pg,"itemid").getComponent();
                      Textbox itemcode=(Textbox)new Path(pg,"itemcode").getComponent();
                      Textbox itemdesc=(Textbox)new Path(pg,"itemdesc").getComponent();
                      Textbox quantity=(Textbox)new Path(pg,"quantity").getComponent();
                      Textbox unit=(Textbox)new Path(pg,"unit").getComponent();
                      Textbox status=(Textbox)new Path(pg,"status").getComponent();
                      Textbox statusdesc=(Textbox)new Path(pg,"statusdesc").getComponent();
                      Textbox createperiod=(Textbox)new Path(pg,"createperiod").getComponent();
                      Textbox createdate=(Textbox)new Path(pg,"createdate").getComponent();
                      Textbox createdby=(Textbox)new Path(pg,"createdby").getComponent();
                      Textbox approvedate=(Textbox)new Path(pg,"approvedate").getComponent();
                      Textbox approvedby=(Textbox)new Path(pg,"approvedby").getComponent();
                      Textbox submitdate=(Textbox)new Path(pg,"submitdate").getComponent();
                      Textbox submitedby=(Textbox)new Path(pg,"submitedby").getComponent();
                      
                      wiprcpid.setText(wiprcpid1);
                      wiprcpno.setText(wiprcpno1);
                      wiprcpdate.setText(wiprcpdate1);
                      prodissueid.setText(prodissueid1);
                      prodissuedesc.setText(prodissuedesc1);
                      itemid.setText(itemid1);
                      itemcode.setText(itemcode1);
                      itemdesc.setText(itemdesc1);
                      quantity.setText(quantity1);
                      unit.setText(unit1);
                      status.setText(status1);
                      statusdesc.setText(statusdesc1);
                      createperiod.setText(createperiod1);
                      createdate.setText(createdate1);
                      createdby.setText(createdby1);
                      approvedate.setText(approvedate1);
                      approvedby.setText(approvedby1);
                      submitdate.setText(submitdate1);
                      submitedby.setText(submitedby1);
                }

               
            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                      String wiprcpid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                      String wiprcpno = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                      String wiprcpdate = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                      String prodissueid = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                      String prodissuedesc = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                      String itemid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                      String itemcode = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                      String itemdesc = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                      String quantity = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                      String unit = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                      String status = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                      String statusdesc = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                      String createperiod = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                      String createdate = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                      String createdby = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                      String approvedate = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                      String approvedby = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                      String submitdate = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                      String submitedby = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                      form(wiprcpid, wiprcpno, wiprcpdate, prodissueid, prodissuedesc, itemid, itemcode, itemdesc, quantity, unit, status, statusdesc, createperiod, createdate, createdby, approvedate, approvedby, submitdate, submitedby);
                }
            });
            listbox1.appendChild(listitem);
        }
    }
    
   public void form(String wiprcpid,String wiprcpno,String wiprcpdate,String prodissueid,String prodissuedesc,String itemid,String itemcode,String itemdesc,
       String quantity,String unit,String status,String statusdesc,String createperiod,String createdate,String createdby,String approvedate,
       String approvedby,String submitdate,String submitedby){
       Map<String,Object>map = new HashMap<>();
       map.put("InWipRcpId", wiprcpid);
       map.put("wiprcpno", wiprcpno);
       map.put("wiprcpdate", wiprcpdate);
       map.put("prodissueid", prodissueid);
       map.put("prodissuedesc", prodissuedesc);
       map.put("itemid", itemid);
       map.put("itemcode", itemcode);
       map.put("itemdesc", itemdesc);
       map.put("quantity", quantity);
       map.put("unit", unit);
       map.put("status", status);
       map.put("statusdesc", statusdesc);
       map.put("createperiod", createperiod);
       map.put("createdate", createdate);
       map.put("createdby", createdby);
       map.put("approvedate", approvedate);
       map.put("approvedby", approvedby);
       map.put("submitdate", submitdate);
       map.put("submitedby", submitedby);
       Window w = (Window)Executions.createComponents("/Tcash/WIPReceipt.zul", null, map);
       w.setAttribute("parentController", this);
       w.doModal();
    }

    @Listen("onClick=#Clear")
    private void clearFindParam() {
        Path parent = new Path("/List_wip_receipt/win_list_mntr_find");
        Textbox txtwip = (Textbox) new Path(parent, "txtwip").getComponent();
        Datebox dateboxfrom = (Datebox) new Path(parent, "dateboxfrom").getComponent();
        Datebox dateboxto = (Datebox) new Path(parent, "dateboxto").getComponent();
        Textbox txtstat = (Textbox) new Path(parent, "txtstatusfnd").getComponent();
        Textbox txtprod = (Textbox) new Path(parent, "txtprod").getComponent();
        Combobox cmb = (Combobox) new Path(parent, "cmbstatus").getComponent();
        txtwip.setText("");
        dateboxfrom.setValue(null);
        dateboxto.setValue(null);
        txtstat.setText("");
        txtprod.setText("");
        cmb.setSelectedIndex(0);
    }
    @Listen("onClick=#goFind")
    public void findParam() {
        Path pg1 = new Path("/List_wip_receipt");
        Listbox listbox1 = (Listbox) new Path(pg1, "listbox").getComponent();
        Path parent1 = new Path("/List_wip_receipt/win_list_mntr_find");
        Textbox txtwip = (Textbox) new Path(parent1, "txtwip").getComponent();
        Datebox dateboxfrom = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox dateboxto = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox txtstat = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();
        Textbox txtprod = (Textbox) new Path(parent1, "txtprod").getComponent();
        
        listbox1.getItems().clear();
        listbox1.setSizedByContent(true);
        List<Integer> jumlahRecord = model.getCountwipListFindparam(txtwip.getText(), dateboxfrom.getText(), dateboxto.getText(), txtstat.getText(), txtprod.getText());

        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/List_wip_receipt");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);

        refresh(1);
        win_list_mntr_find.setVisible(false);

    }
    
   
    
    public void editForm(){
         Map<String,Object>map = new HashMap<>();
       map.put("InWipRcpId", wiprcpid.getText());
       map.put("wiprcpno", wiprcpno.getText());
       map.put("wiprcpdate", wiprcpdate.getText());
       map.put("prodissueid", prodissueid.getText());
       map.put("prodissuedesc", prodissuedesc.getText());
       map.put("itemid", itemid.getText());
       map.put("itemcode", itemcode.getText());
       map.put("itemdesc", itemdesc.getText());
       map.put("quantity", quantity.getText());
       map.put("unit", unit.getText());
       map.put("status", status.getText());
       map.put("statusdesc", statusdesc.getText());
       map.put("createperiod", createperiod.getText());
       map.put("createdate", createdate.getText());
       map.put("createdby", createdby.getText());
       map.put("approvedate", approvedate.getText());
       map.put("approvedby", approvedby.getText());
       map.put("submitdate", submitdate.getText());
       map.put("submitedby", submitedby.getText());
       if (!wiprcpid.getText().isEmpty()) {
            Window window = (Window) Executions.createComponents(
                    "/Tcash/WIPReceipt.zul", null, map);
                     window.setAttribute("parentController", this);
            window.doModal();
        } else {
            Messagebox.show("Please choose WIP Receipt first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
    
     @Listen("onClick=#edit")
    public void detaillist(){ 
       editForm();
    }
     @Listen("onSelect=#cmbstatus")
    public void cmbStatus(){
        if (cmbstatus.getSelectedIndex()==0) {
            txtstatusfnd.setText("");
        } else if(cmbstatus.getSelectedIndex()==1) {
             txtstatusfnd.setText("1");
        }else if(cmbstatus.getSelectedIndex()==2){
            txtstatusfnd.setText("2");
        }else{
            txtstatusfnd.setText("3");
        }
    }
    
  @Listen("onClick=#Close1")
  public void closeWindowFind(){
      win_list_mntr_find.setVisible(false);
  }

}
