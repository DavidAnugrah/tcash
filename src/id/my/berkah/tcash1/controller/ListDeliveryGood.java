/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.GdListHdrParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class ListDeliveryGood extends SelectorComposer<Component> {

    @Wire
    Paging userPaging;
    @Wire
    Window win_list_GD_find;
    @Wire
    Textbox txtid;
    ModelTcashCTLR model = new ModelTcashCTLR();
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);//, pageSize);
    }

    @Listen("onClick=#new")
    public void addnewrecord() {
        Window window = (Window) Executions.createComponents(
                "/Tcash/GoodDeliveryCTRL.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        win_list_GD_find.setVisible(true);
    }

    @Listen("onClick=#goFind")
    public void addgoFind() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        String statusTrc = "";
        if (cmbStatus.getValue().equals("Draft")) {
            statusTrc = "1";
        }
        else if (cmbStatus.getValue().equals("Submitted")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equals("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equals("Recieved")) {
            statusTrc = "4";
        }
        else if (cmbStatus.getValue().equals("Cancel")) {
            statusTrc = "9";
        }
        List<Integer> jumlahRecord = model.getCountGdListHdr(txtsuppdelivery.getText(), txtWoNo.getValue(),
                txtGdNo.getValue(), dateFromx, dateToX, statusTrc);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Good_Delivery");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
        win_list_GD_find.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void addClose1() {
        win_list_GD_find.setVisible(false);
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_GD_find.setVisible(false);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        Path parent = new Path("/List_Good_Delivery/win_list_GD_find");
        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();

        txtDateFrom.setValue(cal.getTime());
    }

    @Listen("onClick=#refresh")
    public void requery() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, 0);
         findFromDate1.setValue(cal.getTime());
         findToDate1.setValue(cal1.getTime());
         txtsuppdelivery.setValue("");
         txtGdNo.setValue("");
         txtWoNo.setValue("");
         cmbStatus.setValue("");
         
        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        String statusTrc = "";
        if (cmbStatus.getValue().equalsIgnoreCase("Draft")) {
            statusTrc = "1";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Submit")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
            statusTrc = "9";
        }
        List<Integer> jumlahRecord = model.getCountGdListHdr("", "",
                "", dateFromx, dateToX, "");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Good_Delivery");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
    }

    public void refresh(int activePage) {

        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        String statusTrc = "";
        if (cmbStatus.getValue().equalsIgnoreCase("Draft")) {
            statusTrc = "1";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Submit")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
            statusTrc = "9";
        }

        Path pg = new Path("/List_Good_Delivery");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<GdListHdrParam> gdListHdrParam = model.getGdListHdr(txtsuppdelivery.getText(), txtWoNo.getValue(),
                txtGdNo.getValue(), dateFromx, dateToX, statusTrc, "" + activePage, "" + pageSize);
        for (GdListHdrParam GdListHdrParam : gdListHdrParam) {

            Listcell select = new Listcell();
            Listcell suppdeliveryid = new Listcell();
            Listcell poid = new Listcell();
            Listcell supplierid = new Listcell();
            Listcell suppliernumber = new Listcell();
            Listcell suppliername = new Listcell();
            Listcell suppdeliverydate = new Listcell();
            Listcell siteid = new Listcell();
            Listcell sitecode = new Listcell();
            Listcell sitename = new Listcell();
            Listcell buid = new Listcell();
            Listcell bucode = new Listcell();
            Listcell budescription = new Listcell();
            Listcell whcode = new Listcell();
            Listcell whdescription = new Listcell();
            Listcell whid = new Listcell();
            Listcell suppdeliverystatus = new Listcell();
            Listcell suppdeliverystatusdesc = new Listcell();
            Listcell suppdeliveryremark = new Listcell();
            Listcell createddate = new Listcell();
            Listcell createdby = new Listcell();
            Listcell createdbyname = new Listcell();
            Listcell modifieddate = new Listcell();
            Listcell modifiedby = new Listcell();
            Listcell modifiedbyname = new Listcell();
            Listcell submitdate = new Listcell();
            Listcell submitby = new Listcell();
            Listcell approveddate = new Listcell();
            Listcell approvedby = new Listcell();
            Listcell approvedbyname = new Listcell();
            Listcell canceleddate = new Listcell();
            Listcell canceledby = new Listcell();
            Listcell canceledbyname = new Listcell();
            Listcell purchaseorder = new Listcell();
            Listcell suppdeliveryno = new Listcell();
            Listcell esttimearrive = new Listcell();
            Listcell esttimearriveto = new Listcell();
            Listcell lineremark = new Listcell();
            Listcell forwardingagent = new Listcell();
            Listcell agentname = new Listcell();

            suppdeliveryid.setLabel(GdListHdrParam.getSuppdeliveryid());
            poid.setLabel(GdListHdrParam.getPoid());
            budescription.setLabel(GdListHdrParam.getBudescription());
            supplierid.setLabel(GdListHdrParam.getSupplierid());
            suppliernumber.setLabel(GdListHdrParam.getSuppliernumber());
            suppliername.setLabel(GdListHdrParam.getSuppliername());
            suppdeliverydate.setLabel(GdListHdrParam.getSuppdeliverydate());
            siteid.setLabel(GdListHdrParam.getSiteid());
            sitecode.setLabel(GdListHdrParam.getSitecode());
            sitename.setLabel(GdListHdrParam.getSitename());
            buid.setLabel(GdListHdrParam.getBuid());
            bucode.setLabel(GdListHdrParam.getBucode());
            budescription.setLabel(GdListHdrParam.getBudescription());
            whcode.setLabel(GdListHdrParam.getWhcode());
            whdescription.setLabel(GdListHdrParam.getWhdescription());
            whid.setLabel(GdListHdrParam.getWhid());
            suppdeliverystatus.setLabel(GdListHdrParam.getSuppdeliverystatus());
            suppdeliverystatusdesc.setLabel(GdListHdrParam.getSuppdeliverystatusdesc());
            suppdeliveryremark.setLabel(GdListHdrParam.getSuppdeliveryremark());
            createddate.setLabel(GdListHdrParam.getCreateddate());
            createdby.setLabel(GdListHdrParam.getCreatedby());
            createdbyname.setLabel(GdListHdrParam.getCreatedbyname());
            modifieddate.setLabel(GdListHdrParam.getModifieddate());
            modifiedby.setLabel(GdListHdrParam.getModifiedby());
            modifiedbyname.setLabel(GdListHdrParam.getModifiedbyname());
            submitdate.setLabel(GdListHdrParam.getSubmitteddate());
            submitby.setLabel(GdListHdrParam.getSubmittedby());
            approveddate.setLabel(GdListHdrParam.getApproveddate());
            approvedby.setLabel(GdListHdrParam.getApprovedby());
            approvedbyname.setLabel(GdListHdrParam.getApprovedbyname());
            canceleddate.setLabel(GdListHdrParam.getCanceleddate());
            canceledby.setLabel(GdListHdrParam.getCanceledby());
            canceledbyname.setLabel(GdListHdrParam.getCanceledbyname());
            purchaseorder.setLabel(GdListHdrParam.getPurchaseorder());
            suppdeliveryno.setLabel(GdListHdrParam.getSuppdeliveryno());
            esttimearrive.setLabel(GdListHdrParam.getEsttimearrive());
            esttimearriveto.setLabel(GdListHdrParam.getEsttimearriveto());
            lineremark.setLabel(GdListHdrParam.getLineremark());
            forwardingagent.setLabel(GdListHdrParam.getForwardingagent());
            agentname.setLabel(GdListHdrParam.getAgentname());

            Listitem listitem = new Listitem();
            listitem.appendChild(suppdeliveryid);
            listitem.appendChild(suppdeliveryno);
            listitem.appendChild(purchaseorder);
            listitem.appendChild(poid);
            listitem.appendChild(supplierid);
            listitem.appendChild(suppliernumber);
            listitem.appendChild(suppliername);
            listitem.appendChild(suppdeliverydate);
            listitem.appendChild(siteid);
            listitem.appendChild(sitecode);
            listitem.appendChild(sitename);
            listitem.appendChild(buid);
            listitem.appendChild(bucode);
            listitem.appendChild(budescription);
            listitem.appendChild(whcode);
            listitem.appendChild(whdescription);
            listitem.appendChild(whid);
            listitem.appendChild(esttimearrive);
            listitem.appendChild(esttimearriveto);
            listitem.appendChild(suppdeliverystatus);
            listitem.appendChild(suppdeliveryremark);
            listitem.appendChild(createddate);
            listitem.appendChild(createdby);
            listitem.appendChild(createdbyname);
            listitem.appendChild(modifieddate);
            listitem.appendChild(modifiedby);
            listitem.appendChild(modifiedbyname);
            listitem.appendChild(submitdate);
            listitem.appendChild(submitby);
            listitem.appendChild(approveddate);
            listitem.appendChild(approvedby);
            listitem.appendChild(approvedbyname);
            listitem.appendChild(canceleddate);
            listitem.appendChild(canceledby);
            listitem.appendChild(canceledbyname);
            listitem.appendChild(lineremark);
            listitem.appendChild(forwardingagent);
            listitem.appendChild(agentname);
            listitem.appendChild(suppdeliverystatusdesc);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    Path parent1 = new Path("/List_Good_Delivery");
                    Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    txtid.setValue(suppdeliveryid);
                }
            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    form(suppdeliveryid);
                }
            });
            listbox1.appendChild(listitem);
        }
    }

    public void form(String suppdeliveryid) {
        Map<String, Object> map = new HashMap<>();
        map.put("suppdeliveryid", suppdeliveryid);
        Window window = (Window) Executions.createComponents("/Tcash/GoodDeliveryCTRL.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#edit")
    public void edit() {
        Path parent1 = new Path("/List_Good_Delivery");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if (txtid.getValue().equals("")) {
            Messagebox.show("No record selected","Goods Delivery",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        form(txtid.getValue());
    }
}
