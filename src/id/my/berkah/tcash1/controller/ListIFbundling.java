/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListIFBndParam;
import id.my.berkah.tcash1.model.ListIFParamTrc;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListIFbundling extends SelectorComposer<Component>{
  @Wire
    Paging userPaging;
    @Wire
    Window win_list_find_bnd;
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
                "/Tcash/BundlingIFTcash.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        win_list_find_bnd.setVisible(true);
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
        List<Integer> jumlahRecord = model.getCountIFBnd("");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Input_File_Bundling");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refresh(1);
        win_list_find_bnd.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void addClose1() {
        win_list_find_bnd.setVisible(false);
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_find_bnd.setVisible(false);
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//
//        Path parent = new Path("/List_Input_File/win_list_find");
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
        List<Integer> jumlahRecord = model.getCountIFBnd("");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Input_File_Bundling");
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
//        Path parent1 = new Path("/List_Input_File/win_list_find");
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

        Path pg = new Path("/List_Input_File_Bundling");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<ListIFBndParam> ListIFParam = model.selectListIFBnd("", "" + activePage, "" + pageSize);
        for (ListIFBndParam ListIFParam1 : ListIFParam) {

            Listcell if_id= new Listcell();
            Listcell if_no= new Listcell();
            Listcell if_date= new Listcell();
            Listcell filename= new Listcell();
            Listcell status= new Listcell();
            Listcell po_id= new Listcell();
            Listcell po_no= new Listcell();
            Listcell po_no_loop= new Listcell();
            Listcell create_date= new Listcell();
            Listcell created_by= new Listcell();
            Listcell update_date= new Listcell();
            Listcell updated_by= new Listcell();
            Listcell submit_date= new Listcell();
            Listcell submited_by= new Listcell();
            Listcell approve_date= new Listcell();
            Listcell approved_by= new Listcell();
            Listcell cancel_date= new Listcell();
            Listcell canceled_by= new Listcell();

            if_id.setLabel(ListIFParam1.getIf_id());
            if_no.setLabel(ListIFParam1.getIf_no());
            if_date.setLabel(ListIFParam1.getIf_date());
            filename.setLabel(ListIFParam1.getFilename());
            status.setLabel(ListIFParam1.getStatus());
            po_id.setLabel(ListIFParam1.getPo_id());
            po_no.setLabel(ListIFParam1.getPurchase_order());
            po_no_loop.setLabel(ListIFParam1.getPurchase_order_loop());
            create_date.setLabel(ListIFParam1.getCreate_date());
            created_by.setLabel(ListIFParam1.getCreated_by());
            update_date.setLabel(ListIFParam1.getUpdate_date());
            updated_by.setLabel(ListIFParam1.getUpdated_by());
            submit_date.setLabel(ListIFParam1.getSubmit_date());
            submited_by.setLabel(ListIFParam1.getSubmited_by());
            approve_date.setLabel(ListIFParam1.getApprove_date());
            approved_by.setLabel(ListIFParam1.getApproved_by());
            cancel_date.setLabel(ListIFParam1.getCancel_date());
            canceled_by.setLabel(ListIFParam1.getCanceled_by());

            Listitem listitem = new Listitem();
            listitem.appendChild(if_id);
            listitem.appendChild(if_no);
            listitem.appendChild(if_date);
            listitem.appendChild(filename);
            listitem.appendChild(status);
            listitem.appendChild(po_id);
            listitem.appendChild(po_no);
            listitem.appendChild(po_no_loop);
            listitem.appendChild(create_date);
            listitem.appendChild(created_by);
            listitem.appendChild(update_date);
            listitem.appendChild(updated_by);
            listitem.appendChild(submit_date);
            listitem.appendChild(submited_by);
            listitem.appendChild(approve_date);
            listitem.appendChild(approved_by);
            listitem.appendChild(cancel_date);
            listitem.appendChild(canceled_by);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    Path parent1 = new Path("/List_Input_File_Bundling");
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
        Window window = (Window) Executions.createComponents("/Tcash/BundlingIFTcash.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#edit")
    public void edit() {
        Path parent1 = new Path("/List_Input_File_Bundling");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if (txtid.getValue() == "") {
            System.out.println("return");
            return;
        }
        form(txtid.getValue());
    }
}
