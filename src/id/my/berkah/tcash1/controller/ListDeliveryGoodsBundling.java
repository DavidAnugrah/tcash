/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import static id.my.berkah.tcash1.controller.ListBundlingGoodsDeliveryFind.CODE;
import id.my.berkah.tcash1.model.BundlingGDListHDRParam;
import id.my.berkah.util.IDefines;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListDeliveryGoodsBundling extends SelectorComposer<Component> {

    @Wire
    Paging userPaging;
   
   
    @Wire
    Listbox listbox;
    ModelTcashCTLR model = new ModelTcashCTLR();
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;
    
    
    @Listen("onCreate=#List_Good_Delivery")
    public void onCreate(){
        setRender();
    }

    @Listen("onClick=#new")
    public void addnewrecord() {
        Window window = (Window) Executions.createComponents(
                "/Tcash/BundlingGoodsDelivery.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
        Window w = (Window)Executions.createComponents("/Tcash/ListBundlingGoodsDeliveryFind.zul", null, null);
        w.setAttribute("parentController", this);
       w.doModal();
    }
  
    public void buttonFind(String bundling,String gdId,String gdNo,String DateFrom,String DateTo,String Status){
        List<BundlingGDListHDRParam>find= model.getGdListHdrBundling(bundling,gdId,gdNo,DateFrom,DateTo,Status);
        listbox.setModel(new ListModelList<BundlingGDListHDRParam>(find));
    }

//    public void requery() {
//        String dateFromx = "";
//        String dateToX = "";
//        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
//        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
//        Textbox txtbundlingNo = (Textbox) new Path(parent1, "txtbundlingNo").getComponent();
//        Textbox txtGdid = (Textbox) new Path(parent1, "txtGdid").getComponent();
//        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
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
//         cmbStatus.setValue("");
//         
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
//
//        String statusTrc = "";
//        if (cmbStatus.getValue().equalsIgnoreCase("Draft")) {
//            statusTrc = "1";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Submit")) {
//            statusTrc = "2";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
//            statusTrc = "3";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
//            statusTrc = "9";
//        }
//        List<Integer> jumlahRecord = model.getCountGdListHdr("", "",
//                "", dateFromx, dateToX, "");
//        if (!jumlahRecord.isEmpty()) {
//            JumlahRecord = jumlahRecord.get(0);
//        }
//        System.out.println(jumlahRecord);
//
//        Path pg = new Path("/List_Good_Delivery");
//        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
//        page.setPageSize(pageSize);
//        page.setTotalSize(JumlahRecord);
//
//        page.setActivePage(0);
//        refresh(1);
//    }

//    public void refresh(int activePage) {
//
//        String dateFromx = "";
//        String dateToX = "";
//        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        Path parent1 = new Path("/List_Good_Delivery/win_list_GD_find");
//        Textbox txtsuppdelivery = (Textbox) new Path(parent1, "txtsuppdelivery").getComponent();
//        Textbox txtbundlingNo = (Textbox) new Path(parent1, "txtbundlingNo").getComponent();
//        Textbox txtGdid = (Textbox) new Path(parent1, "txtGdid").getComponent();
//        Textbox txtGdNo = (Textbox) new Path(parent1, "txtGdNo").getComponent();
//        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
//        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
//
//        String statusTrc = "";
//        if (cmbStatus.getValue().equalsIgnoreCase("Draft")) {
//            statusTrc = "1";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Submit")) {
//            statusTrc = "2";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Approved")) {
//            statusTrc = "3";
//        }
//        else if (cmbStatus.getValue().equalsIgnoreCase("Cancel")) {
//            statusTrc = "9";
//        }
//
//        Path pg = new Path("/List_Good_Delivery");
//        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();
//
//        listbox1.getItems().clear();
//        List<BundlingGDListHDRParam> BundlingGDListHDRParam = model.getGdListHdrBundling(txtbundlingNo.getText(), txtGdid.getValue(),
//                txtGdNo.getValue(), dateFromx, dateToX, statusTrc, "" + activePage, "" + pageSize);
//        for (BundlingGDListHDRParam GdListHdrParam : BundlingGDListHDRParam) {
//
////            Listcell select = new Listcell();
//            Listcell suppdeliveryid = new Listcell();
//            Listcell poid = new Listcell();
//            Listcell supplierid = new Listcell();
//            Listcell suppliernumber = new Listcell();
//            Listcell suppliername = new Listcell();
//            Listcell suppdeliverydate = new Listcell();
//            Listcell siteid = new Listcell();
//            Listcell sitecode = new Listcell();
//            Listcell sitename = new Listcell();
//            Listcell buid = new Listcell();
//            Listcell bucode = new Listcell();
//            Listcell budescription = new Listcell();
//            Listcell whcode = new Listcell();
//            Listcell whdescription = new Listcell();
//            Listcell whid = new Listcell();
//            Listcell suppdeliverystatus = new Listcell();
//            Listcell suppdeliverystatusdesc = new Listcell();
//            Listcell suppdeliveryremark = new Listcell();
//            Listcell createddate = new Listcell();
//            Listcell createdby = new Listcell();
//            Listcell createdbyname = new Listcell();
//            Listcell modifieddate = new Listcell();
//            Listcell modifiedby = new Listcell();
//            Listcell modifiedbyname = new Listcell();
//            Listcell approveddate = new Listcell();
//            Listcell approvedby = new Listcell();
//            Listcell approvedbyname = new Listcell();
//            Listcell canceleddate = new Listcell();
//            Listcell canceledby = new Listcell();
//            Listcell canceledbyname = new Listcell();
//            Listcell BundlingID = new Listcell();
//            Listcell BundlingNo = new Listcell();
//            Listcell suppdeliveryno = new Listcell();
//            Listcell esttimearrive = new Listcell();
//            Listcell esttimearriveto = new Listcell();
//            Listcell lineremark = new Listcell();
//            Listcell forwardingagent = new Listcell();
//            Listcell agentname = new Listcell();
//            Listcell status = new Listcell();
//
//            suppdeliveryid.setLabel(GdListHdrParam.getSupp_delivery_id());
//            poid.setLabel(GdListHdrParam.getPo_id());
//            budescription.setLabel(GdListHdrParam.getBu_description());
//            supplierid.setLabel(GdListHdrParam.getSupplier_id());
//            suppliernumber.setLabel(GdListHdrParam.getSupplier_number());
//            suppliername.setLabel(GdListHdrParam.getSupplier_name());
//            suppdeliverydate.setLabel(GdListHdrParam.getSupp_delivery_date());
//            siteid.setLabel(GdListHdrParam.getSite_id());
//            sitecode.setLabel(GdListHdrParam.getSite_code());
//            sitename.setLabel(GdListHdrParam.getSite_name());
//            buid.setLabel(GdListHdrParam.getBu_id());
//            bucode.setLabel(GdListHdrParam.getBu_code());
//            budescription.setLabel(GdListHdrParam.getBu_description());
//            whcode.setLabel(GdListHdrParam.getWh_code());
//            whdescription.setLabel(GdListHdrParam.getWh_description());
//            whid.setLabel(GdListHdrParam.getWh_id());
//            suppdeliverystatus.setLabel(GdListHdrParam.getSupp_delivery_status());
//            suppdeliverystatusdesc.setLabel(GdListHdrParam.getSupp_delivery_status_desc());
//            suppdeliveryremark.setLabel(GdListHdrParam.getSupp_delivery_remark());
//            createddate.setLabel(GdListHdrParam.getCreated_date());
//            createdby.setLabel(GdListHdrParam.getCreated_by());
//            createdbyname.setLabel(GdListHdrParam.getCreated_by_name());
//            modifieddate.setLabel(GdListHdrParam.getModified_date());
//            modifiedby.setLabel(GdListHdrParam.getModified_by());
//            modifiedbyname.setLabel(GdListHdrParam.getModified_by_name());
//            approveddate.setLabel(GdListHdrParam.getApproved_date());
//            approvedby.setLabel(GdListHdrParam.getApproved_by());
//            approvedbyname.setLabel(GdListHdrParam.getApproved_by_name());
//            canceleddate.setLabel(GdListHdrParam.getCanceled_date());
//            canceledby.setLabel(GdListHdrParam.getCanceled_by());
//            canceledbyname.setLabel(GdListHdrParam.getCanceled_by_name());
//            BundlingID.setLabel(GdListHdrParam.getBundling_id());
//            BundlingNo.setLabel(GdListHdrParam.getBundling_no());
//            suppdeliveryno.setLabel(GdListHdrParam.getSupp_delivery_no());
//            esttimearrive.setLabel(GdListHdrParam.getEst_time_arrive());
//            esttimearriveto.setLabel(GdListHdrParam.getEst_time_arrive_to());
//            lineremark.setLabel(GdListHdrParam.getLine_remark());
//            forwardingagent.setLabel(GdListHdrParam.getForwarding_agent());
//            agentname.setLabel(GdListHdrParam.getAgent_name());
//            status.setLabel(GdListHdrParam.getStatus());
//
//            Listitem listitem = new Listitem();
//            listitem.appendChild(suppdeliveryid);
//            listitem.appendChild(suppdeliveryno);
//            listitem.appendChild(BundlingID);
//            listitem.appendChild(BundlingNo);
//            listitem.appendChild(poid);
//            listitem.appendChild(supplierid);
//            listitem.appendChild(suppliernumber);
//            listitem.appendChild(suppliername);
//            listitem.appendChild(suppdeliverydate);
//            listitem.appendChild(siteid);
//            listitem.appendChild(sitecode);
//            listitem.appendChild(sitename);
//            listitem.appendChild(buid);
//            listitem.appendChild(bucode);
//            listitem.appendChild(budescription);
//            listitem.appendChild(whcode);
//            listitem.appendChild(whdescription);
//            listitem.appendChild(whid);
//            listitem.appendChild(suppdeliverystatus);
//            listitem.appendChild(suppdeliverystatusdesc);
//            listitem.appendChild(suppdeliveryremark);
//            listitem.appendChild(createddate);
//            listitem.appendChild(createdby);
//            listitem.appendChild(createdbyname);
//            listitem.appendChild(modifieddate);
//            listitem.appendChild(modifiedby);
//            listitem.appendChild(modifiedbyname);
//            listitem.appendChild(approveddate);
//            listitem.appendChild(approvedby);
//            listitem.appendChild(approvedbyname);
//            listitem.appendChild(canceleddate);
//            listitem.appendChild(canceledby);
//            listitem.appendChild(canceledbyname);
//            listitem.appendChild(esttimearrive);
//            listitem.appendChild(esttimearriveto);
//            listitem.appendChild(lineremark);
//            listitem.appendChild(forwardingagent);
//            listitem.appendChild(agentname);
//            listitem.appendChild(status);
//
//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//              
//                @Override
//                public void onEvent(Event t) throws Exception {
//                    Path parent1 = new Path("/List_Good_Delivery");
//                    Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
//                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
//                    txtid.setValue(suppdeliveryid);
//                }
//            });
//
//            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
//                    form(suppdeliveryid);
//                }
//            });
//            listbox1.appendChild(listitem);
//        }
//    }

   

    @Listen("onClick=#edit")
    public void edit() {
       int index = listbox.getSelectedIndex();
        if (index >-1) {
            BundlingGDListHDRParam selected = (BundlingGDListHDRParam) listbox.getModel().getElementAt(index);
            Map<String,Object> map = new HashMap<>();
            map.put("header", selected);
            Window w = (Window )Executions.createComponents("/Tcash/BundlingGoodsDelivery.zul", null, map);
            w.doModal();
        }else{
            Messagebox.show("No record selected","Bundling Goods Delivery",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
    
    @Listen("onDoubleClick=#listbox")
    public void doubleClick(){
        edit();
    }
    
    
    
       @Listen("onClick=#refresh")
        public void buttonRefreh(){
        List<BundlingGDListHDRParam>list = model.getGdListHdrBundling();
        listbox.setModel(new ListModelList<BundlingGDListHDRParam>(list));
    }
    
    public void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<BundlingGDListHDRParam>() {

            @Override
            public void render(Listitem lstm, BundlingGDListHDRParam t, int i) throws Exception {
                new Listcell(t.getSupp_delivery_id()).setParent(lstm);
                new Listcell(t.getSupp_delivery_no()).setParent(lstm);
                new Listcell(t.getBundling_id()).setParent(lstm);
                new Listcell(t.getBundling_no()).setParent(lstm);
                new Listcell(t.getPo_id()).setParent(lstm);
                new Listcell(t.getSupplier_id()).setParent(lstm);
                new Listcell(t.getSupplier_number()).setParent(lstm);
                new Listcell(t.getSupplier_name()).setParent(lstm);
                new Listcell(t.getSupp_delivery_date()).setParent(lstm);
                new Listcell(t.getSite_id()).setParent(lstm);
                new Listcell(t.getSite_code()).setParent(lstm);
                new Listcell(t.getSite_name()).setParent(lstm);
                new Listcell(t.getBu_id()).setParent(lstm);
                new Listcell(t.getBu_code()).setParent(lstm);
                new Listcell(t.getBu_description()).setParent(lstm);
                new Listcell(t.getWh_code()).setParent(lstm);
                new Listcell(t.getWh_description()).setParent(lstm);
                new Listcell(t.getWh_id()).setParent(lstm);
                new Listcell(t.getForwarding_agent()).setParent(lstm);
                new Listcell(t.getAgent_name()).setParent(lstm);
                 new Listcell(t.getEst_time_arrive()).setParent(lstm);
                new Listcell(t.getEst_time_arrive_to()).setParent(lstm);
                new Listcell(t.getLine_remark()).setParent(lstm);
                new Listcell(t.getSupp_delivery_remark()).setParent(lstm);
                new Listcell(t.getCreated_date()).setParent(lstm);
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getCreated_by_name()).setParent(lstm);
                new Listcell(t.getModified_date()).setParent(lstm);
                new Listcell(t.getModified_by()).setParent(lstm);
                new Listcell(t.getModified_by_name()).setParent(lstm);
                new Listcell(t.getSubmitted_date()).setParent(lstm);
                new Listcell(t.getSubmitted_by()).setParent(lstm);
                new Listcell(t.getApproved_date()).setParent(lstm);
                new Listcell(t.getApproved_by()).setParent(lstm);
                new Listcell(t.getApproved_by_name()).setParent(lstm);
                new Listcell(t.getCanceled_date()).setParent(lstm);
                new Listcell(t.getCanceled_by()).setParent(lstm);
                new Listcell(t.getCanceled_by_name()).setParent(lstm);
               
                new Listcell(t.getSupp_delivery_status()).setParent(lstm);
                new Listcell(t.getSupp_delivery_status_desc()).setParent(lstm);
            }
        });
    }
}
