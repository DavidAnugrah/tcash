/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.GrListHdrParam;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class ListRecieptGood extends SelectorComposer<Component> {

    @Wire
    Paging userPaging;
    @Wire
    Window win_list_GR_find;
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
                "/Tcash/GoodRecieptTrc.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        win_list_GR_find.setVisible(true);
    }

    @Listen("onClick=#goFind")
    public void addgoFind() {
      String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Good_Reciept/win_list_GR_find");
        Textbox txtReciept = (Textbox) new Path(parent1, "txtReciept").getComponent();
        Textbox txtGrNo = (Textbox) new Path(parent1, "txtGrNo").getComponent();
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
        else if (cmbStatus.getValue().equalsIgnoreCase("Confirm Recieved")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
            statusTrc = "9";
        }
        
        
        List<Integer> jumlahRecord = model.getCountGrListHdr(txtReciept.getText(), txtGrNo.getValue(), txtWoNo.getValue(),
                txtGdNo.getValue(), dateFromx, dateToX, statusTrc);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Good_Reciept");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
        win_list_GR_find.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void addClose1() {
        win_list_GR_find.setVisible(false);
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_GR_find.setVisible(false);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        Path parent = new Path("/List_Good_Reciept/win_list_GR_find");
        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();

        txtDateFrom.setValue(cal.getTime());
    }

    @Listen("onClick=#refresh")
    public void requery() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Good_Reciept/win_list_GR_find");
        Textbox txtReciept = (Textbox) new Path(parent1, "txtReciept").getComponent();
        Textbox txtGrNo = (Textbox) new Path(parent1, "txtGrNo").getComponent();
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
        else if (cmbStatus.getValue().equalsIgnoreCase("Confirm Recieved")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
            statusTrc = "9";
        }
        
        
         Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, 0);
         findFromDate1.setValue(cal.getTime());
         findToDate1.setValue(cal1.getTime());
         txtReciept.setValue("");
         txtGrNo.setValue("");
         txtGdNo.setValue("");
         txtWoNo.setValue("");
         cmbStatus.setValue("");
        

        List<Integer> jumlahRecord = model.getCountGrListHdr(txtReciept.getText(), txtGrNo.getValue(), txtWoNo.getValue(),
                txtGdNo.getValue(), dateFromx, dateToX, statusTrc);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Good_Reciept");
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

        Path parent1 = new Path("/List_Good_Reciept/win_list_GR_find");
        Textbox txtReciept = (Textbox) new Path(parent1, "txtReciept").getComponent();
        Textbox txtGrNo = (Textbox) new Path(parent1, "txtGrNo").getComponent();
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
        else if (cmbStatus.getValue().equalsIgnoreCase("Confirm Recieved")) {
            statusTrc = "2";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
            statusTrc = "3";
        }
        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
            statusTrc = "9";
        }

        Path pg = new Path("/List_Good_Reciept");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<GrListHdrParam> GrListHdrParam = model.getGrListHdr(txtReciept.getText(), txtGrNo.getValue(), txtWoNo.getValue(),
                txtGdNo.getValue(), dateFromx, dateToX, statusTrc, "" + activePage, "" + pageSize);
        for (GrListHdrParam GrListHdrParam1 : GrListHdrParam) {

            Listcell receive_id = new Listcell();
            Listcell receive_no = new Listcell();
            Listcell bu_id = new Listcell();
            Listcell bu_code = new Listcell();
            Listcell bu_description = new Listcell();
            Listcell wh_id = new Listcell();
            Listcell wh_code = new Listcell();
            Listcell wh_description = new Listcell();
            Listcell wh_loc = new Listcell();
            Listcell supplier_id = new Listcell();
            Listcell supplier_number = new Listcell();
            Listcell supplier_description = new Listcell();
            Listcell site_id = new Listcell();
            Listcell site_code = new Listcell();
            Listcell site_description = new Listcell();
            Listcell vendor_id = new Listcell();
            Listcell vendor_site_id = new Listcell();
            Listcell vendor_site = new Listcell();
            Listcell po_header_id = new Listcell();
            Listcell po_header_no = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_no = new Listcell();
            Listcell receive_status = new Listcell();
            Listcell receive_status_desc = new Listcell();
            Listcell cancel_status = new Listcell();
            Listcell create_date = new Listcell();
            Listcell create_by = new Listcell();
            Listcell created_by_name = new Listcell();
            Listcell update_date = new Listcell();
            Listcell update_by = new Listcell();
            Listcell updated_by_name = new Listcell();
            Listcell confirmed_date = new Listcell();
            Listcell confirmed_by = new Listcell();
            Listcell confirmed_by_name = new Listcell();
            Listcell approved_date = new Listcell();
            Listcell approved_by = new Listcell();
            Listcell approved_by_name = new Listcell();
            Listcell canceled_date = new Listcell();
            Listcell canceled_name = new Listcell();

            receive_id.setLabel(GrListHdrParam1.getReceive_id());
            receive_no.setLabel(GrListHdrParam1.getReceive_no());
            bu_id.setLabel(GrListHdrParam1.getBu_id());
            bu_code.setLabel(GrListHdrParam1.getBu_code());
            bu_description.setLabel(GrListHdrParam1.getBu_description());
            wh_id.setLabel(GrListHdrParam1.getWh_id());
            wh_code.setLabel(GrListHdrParam1.getWh_code());
            wh_description.setLabel(GrListHdrParam1.getWh_description());
            wh_loc.setLabel(GrListHdrParam1.getWh_loc());
            supplier_id.setLabel(GrListHdrParam1.getSupplier_id());
            supplier_number.setLabel(GrListHdrParam1.getSupplier_number());
            supplier_description.setLabel(GrListHdrParam1.getSupplier_description());
            site_id.setLabel(GrListHdrParam1.getSite_id());
            site_code.setLabel(GrListHdrParam1.getSite_code());
            site_description.setLabel(GrListHdrParam1.getSite_description());
            vendor_id.setLabel(GrListHdrParam1.getVendor_id());
            vendor_site_id.setLabel(GrListHdrParam1.getVendor_site_id());
            vendor_site.setLabel(GrListHdrParam1.getVendor_site());
            po_header_id.setLabel(GrListHdrParam1.getPo_header_id());
            po_header_no.setLabel(GrListHdrParam1.getPo_header_no());
            supp_delivery_id.setLabel(GrListHdrParam1.getSupp_delivery_id());
            supp_delivery_no.setLabel(GrListHdrParam1.getSupp_delivery_no());
            receive_status.setLabel(GrListHdrParam1.getReceive_status());
            receive_status_desc.setLabel(GrListHdrParam1.getReceive_status_desc());
            cancel_status.setLabel(GrListHdrParam1.getCancel_status());
            create_date.setLabel(GrListHdrParam1.getCreate_date());
            create_by.setLabel(GrListHdrParam1.getCreate_by());
            created_by_name.setLabel(GrListHdrParam1.getCreated_by_name());
            update_date.setLabel(GrListHdrParam1.getUpdate_date());
            update_by.setLabel(GrListHdrParam1.getUpdate_by());
            updated_by_name.setLabel(GrListHdrParam1.getUpdated_by_name());
            confirmed_date.setLabel(GrListHdrParam1.getConfirmed_date());
            confirmed_by.setLabel(GrListHdrParam1.getConfirmed_by());
            confirmed_by_name.setLabel(GrListHdrParam1.getConfirmed_by_name());
            approved_date.setLabel(GrListHdrParam1.getApproved_date());
            approved_by.setLabel(GrListHdrParam1.getApproved_by());
            approved_by_name.setLabel(GrListHdrParam1.getApproved_by_name());
            canceled_date.setLabel(GrListHdrParam1.getCanceled_date());
            canceled_name.setLabel(GrListHdrParam1.getCanceled_by_name());

            Listitem listitem = new Listitem();
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_no);
            listitem.appendChild(supp_delivery_no);
            listitem.appendChild(po_header_no);
            listitem.appendChild(bu_id);
            listitem.appendChild(bu_code);
            listitem.appendChild(bu_description);
            listitem.appendChild(wh_id);
            listitem.appendChild(wh_code);
            listitem.appendChild(wh_description);
            listitem.appendChild(wh_loc);
            listitem.appendChild(supplier_id);
            listitem.appendChild(supplier_number);
            listitem.appendChild(supplier_description);
            listitem.appendChild(site_id);
            listitem.appendChild(site_code);
            listitem.appendChild(site_description);
            listitem.appendChild(vendor_id);
            listitem.appendChild(vendor_site_id);
            listitem.appendChild(vendor_site);
            listitem.appendChild(po_header_id);
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(receive_status);
            listitem.appendChild(cancel_status);
            listitem.appendChild(create_date);
            listitem.appendChild(create_by);
            listitem.appendChild(created_by_name);
            listitem.appendChild(update_date);
            listitem.appendChild(update_by);
            listitem.appendChild(updated_by_name);
            listitem.appendChild(confirmed_date);
            listitem.appendChild(confirmed_by);
            listitem.appendChild(confirmed_by_name);
            listitem.appendChild(approved_date);
            listitem.appendChild(approved_by);
            listitem.appendChild(approved_by_name);
            listitem.appendChild(canceled_date);
            listitem.appendChild(canceled_name);
            listitem.appendChild(receive_status_desc);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();

                    Path parent1 = new Path("/List_Good_Reciept");
                    Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
                    txtid.setValue(suppdeliveryid);
                }
            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
//                    form(suppdeliveryid, poid, supplierid, suppliernumber, suppliername, suppdeliverydate, siteid, sitecode, sitename, buid, bucode, budescription, whcode, whdescription, whid, suppdeliverystatus, suppdeliverystatusdesc, suppdeliveryremark, createddate, createdby, createdbyname, modifieddate, modifiedby, modifiedbyname, approveddate, approvedby, approvedbyname, canceleddate, canceledby, canceledbyname, purchaseorder, suppdeliveryno, esttimearrive, esttimearriveto, lineremark, forwardingagent, agentname, status);
                    form(suppdeliveryid);
                }
            });
            listbox1.appendChild(listitem);
        }
    }

    public void form(String suppdeliveryid) {
        Map<String, Object> map = new HashMap<>();
        map.put("suppdeliveryid", suppdeliveryid);

        Window window = (Window) Executions.createComponents("/Tcash/GoodRecieptTrc.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#edit")
    public void edit() {
        Path parent1 = new Path("/List_Good_Reciept");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if (txtid.getValue().equals("")||txtid.getValue().isEmpty()) {
            Messagebox.show("No record selected","Goods Receipt",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        form(txtid.getValue());
    }
}
