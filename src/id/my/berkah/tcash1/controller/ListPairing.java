/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.GdListHdrParam;
import id.my.berkah.tcash1.model.ListIFParamTrc;
import id.my.berkah.tcash1.model.ListOFParam;
import id.my.berkah.tcash1.model.ListPairingParam;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class ListPairing extends SelectorComposer<Component> {

    @Wire
    Paging userPaging;
    @Wire
    Window win_list_find;
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
                "/Tcash/PairingSN.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        win_list_find.setVisible(true);
    }

    @Listen("onClick=#goFind")
    public void addgoFind() {
//        String dateFromx = "";
//        String dateToX = "";
//        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
//        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
//        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
//        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
//        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
//        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
//
//        String statusTrc = "";
//        if (cmbStatus.getValue().equals("Draft")) {
//            statusTrc = "1";
//        }
//        else if (cmbStatus.getValue().equals("Submitted")) {
//            statusTrc = "2";
//        }
//        else if (cmbStatus.getValue().equals("Approved")) {
//            statusTrc = "3";
//        }
//        else if (cmbStatus.getValue().equals("Recieved")) {
//            statusTrc = "4";
//        }
//        else if (cmbStatus.getValue().equals("Cancel")) {
//            statusTrc = "9";
//        }
        List<Integer> jumlahRecord = model.getCountOF("");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Output_File");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
        win_list_find.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void addClose1() {
        win_list_find.setVisible(false);
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_find.setVisible(false);
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//
//        Path parent = new Path("/List_Pairing/win_list_find");
//        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();
//
//        txtDateFrom.setValue(cal.getTime());
    }

    @Listen("onClick=#refresh")
    public void requery() {
//        String dateFromx = "";
//        String dateToX = "";
//        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
//        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
//        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
//        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
//        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
//        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//        
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//        Calendar cal1 = Calendar.getInstance();
//        cal1.add(Calendar.DATE, 0);
//         findFromDate1.setValue(cal.getTime());
//         findToDate1.setValue(cal1.getTime());
//         txtsuppdelivery.setValue("");
//         txtGdNo.setValue("");
//         txtWoNo.setValue("");
//         cmbStatus.setValue("");
//         
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
//
//        String statusTrc = "";
//        if (cmbStatus.getValue().equals("Draft")) {
//            statusTrc = "1";
//        }
//        else if (cmbStatus.getValue().equals("Submitted")) {
//            statusTrc = "2";
//        }
//        else if (cmbStatus.getValue().equals("Approved")) {
//            statusTrc = "3";
//        }
//        else if (cmbStatus.getValue().equals("Recieved")) {
//            statusTrc = "4";
//        }
//        else if (cmbStatus.getValue().equals("Cancel")) {
//            statusTrc = "9";
//        }
        List<Integer> jumlahRecord = model.getCountPairing("");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Pairing");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
    }

    public void refresh(int activePage) {

//        String dateFromx = "";
//        String dateToX = "";
//        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Path parent1 = new Path("/List_Output_File/win_list_find");
//        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
//        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
//        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
//        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
//        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
//
//        String statusTrc = "";
//        if (cmbStatus.getValue().equals("Draft")) {
//            statusTrc = "1";
//        }
//        else if (cmbStatus.getValue().equals("Submitted")) {
//            statusTrc = "2";
//        }
//        else if (cmbStatus.getValue().equals("Approved")) {
//            statusTrc = "3";
//        }
//        else if (cmbStatus.getValue().equals("Recieved")) {
//            statusTrc = "4";
//        }
//        else if (cmbStatus.getValue().equals("Cancel")) {
//            statusTrc = "9";
//        }

        Path pg = new Path("/List_Pairing");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<ListPairingParam> ListPairingParam = model.selectListPairing("", "" + activePage, "" + pageSize);
        for (ListPairingParam ListPairingParam1 : ListPairingParam) {
            Listcell pairing_id= new Listcell();
            Listcell pairing_no= new Listcell();
            Listcell pairing_date= new Listcell();
            Listcell wo_type= new Listcell();
            Listcell wo_id= new Listcell();
            Listcell purchase_order= new Listcell();
            Listcell item_id= new Listcell();
            Listcell item_code= new Listcell();
            Listcell item_description= new Listcell();
            Listcell quantity= new Listcell();
            Listcell status= new Listcell();
            Listcell create_date= new Listcell();
            Listcell created_by= new Listcell();
            Listcell create_period= new Listcell();
            Listcell submit_date= new Listcell();
            Listcell submited_by= new Listcell();
            Listcell approve_date= new Listcell();
            Listcell approved_by= new Listcell();

            pairing_id.setLabel(ListPairingParam1.getPairing_id());
            pairing_no.setLabel(ListPairingParam1.getPairing_no());
            pairing_date.setLabel(ListPairingParam1.getPairing_date());
            wo_type.setLabel(ListPairingParam1.getWo_type());
            wo_id.setLabel(ListPairingParam1.getWo_id());
            purchase_order.setLabel(ListPairingParam1.getPurchase_order());
            item_id.setLabel(ListPairingParam1.getItem_id());
            item_code.setLabel(ListPairingParam1.getItem_code());
            item_description.setLabel(ListPairingParam1.getItem_description());
            quantity.setLabel(ListPairingParam1.getQuantity());
            status.setLabel(ListPairingParam1.getStatus());
            create_date.setLabel(ListPairingParam1.getCreate_date());
            created_by.setLabel(ListPairingParam1.getCreated_by());
            create_period.setLabel(ListPairingParam1.getCreate_period());
            submit_date.setLabel(ListPairingParam1.getSubmit_date());
            submited_by.setLabel(ListPairingParam1.getSubmited_by());
            approve_date.setLabel(ListPairingParam1.getApprove_date());
            approved_by.setLabel(ListPairingParam1.getApproved_by());

            Listitem listitem = new Listitem();
            listitem.appendChild(pairing_id);
            listitem.appendChild(pairing_no);
            listitem.appendChild(pairing_date);
            listitem.appendChild(wo_type);
            listitem.appendChild(wo_id);
            listitem.appendChild(purchase_order);
            listitem.appendChild(item_id);
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(quantity);
            listitem.appendChild(status);
            listitem.appendChild(create_date);
            listitem.appendChild(created_by);
            listitem.appendChild(create_period);
            listitem.appendChild(submit_date);
            listitem.appendChild(submited_by);
            listitem.appendChild(approve_date);
            listitem.appendChild(approved_by);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    Path parent1 = new Path("/List_Pairing");
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

    public void form(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("ifId", id);
        Window window = (Window) Executions.createComponents("/Tcash/PairingSN.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#edit")
    public void edit() {
        Path parent1 = new Path("/List_Pairing");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if (txtid.getValue() == "") {
            System.out.println("return");
            return;
        }
        form(txtid.getValue());
    }
}
