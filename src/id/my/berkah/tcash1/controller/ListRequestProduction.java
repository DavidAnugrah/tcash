/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.RpListHdrParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.ParseException;
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

/**
 *
 * @author Azec
 */
public class ListRequestProduction extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_list_Po_find;
    
    @Wire
    Combobox cmbstatus;
    
    @Wire
    Textbox txtreqin, txtrpn,txtreid, txtbuid, txtbucode, txtBuid, txtBucode, txtstatusfnd, txtBudesc, txtid, txtreqnum, txtreqstat, txtbudes, txtreqdte, txtprocesste, txtprocessby, txtcanceleddte, txtcanceledby, txtapproveby, txtapprovedate,txtcreatedate,txtcreateby;

    @Wire
    Datebox dateboxfrom, dateboxto;

    @Wire
    Paging userPaging;

    ModelTcashCTLR model = new ModelTcashCTLR();

    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_Po_find.setVisible(false);
    }

    @Listen("onClick=#find")
    public void filter() {
        win_list_Po_find.setVisible(true);
    }

    @Listen("onClick=#Close1")
    public void closeFind() {
        win_list_Po_find.setVisible(false);
//        clearFindParam();
    }
    
    @Listen("onClick=#Clear")
    public void clearParamFinder(){
        clearFindParam();
    }

    @Listen("onClick=#new")
    public void addnew() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/RequestProductionCTRL.zul", null, null);
        window.setAttribute("parentControllerq", this);
        window.doModal();
    }

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
        refresh(startPageNumber);
    }
    
    private void refresh(int activePage){
        userPaging.setPageSize(pageSize);
        refreshFind(activePage);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refreshhdr(activePage);//, pageSize);
                
    }

    @Listen("onClick=#refresh")
    public void requery() {
        Path parent1 = new Path("/List_Request_PO/win_list_Po_find");
        Textbox txtBucodefind = (Textbox) new Path(parent1, "txtBucode").getComponent();
        Textbox txtrpnfind = (Textbox) new Path(parent1, "txtrpn").getComponent();
        Textbox txtreidfind = (Textbox) new Path(parent1, "txtreid").getComponent();
        clearFindParam();
        List<Integer> jumlahRecord = model.getCountRpListHdr(txtBucodefind.getText(),txtrpnfind.getText(),txtreidfind.getText(),userId,responsibilityId);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Request_PO");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        refreshhdr(1);
    }

    public void refreshhdr(int activePage) {
        Path parent1 = new Path("/List_Request_PO/win_list_Po_find");
        Textbox txtBucodehdr = (Textbox) new Path(parent1, "txtBucode").getComponent();
        Textbox txtrpn = (Textbox) new Path(parent1, "txtrpn").getComponent();
        Textbox rpidhdr = (Textbox) new Path(parent1, "txtreid").getComponent();

        Path pg = new Path("/List_Request_PO");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<RpListHdrParam> RpListHdrParam = model.getRpListHdr(txtBucodehdr.getText(),txtrpn.getValue(), rpidhdr.getText(),userId,responsibilityId, "" + activePage, "" + pageSize);
        for (RpListHdrParam RpListHdrParam1 : RpListHdrParam) {

            Listcell buid = new Listcell();
            Listcell bucode = new Listcell();
            Listcell budescription = new Listcell();
            Listcell id = new Listcell();
            Listcell requisitionid = new Listcell();
            Listcell requisitionnumber = new Listcell();
            Listcell requisitiondate = new Listcell();
            Listcell processeddate = new Listcell();
            Listcell processedby = new Listcell();
            Listcell createdate = new Listcell();
            Listcell createby = new Listcell();
            Listcell cancelledby = new Listcell();
            Listcell cancelleddate = new Listcell();
            Listcell approveby = new Listcell();
            Listcell approvedate = new Listcell();
            Listcell requisitionstatus = new Listcell();
            
            
            buid.setLabel(RpListHdrParam1.getBuid());
            bucode.setLabel(RpListHdrParam1.getBucode());
            budescription.setLabel(RpListHdrParam1.getBudescription());
            id.setLabel(RpListHdrParam1.getBuid());
            requisitionid.setLabel(RpListHdrParam1.getRequisitionid());
            requisitionnumber.setLabel(RpListHdrParam1.getRequisitionnumber());
            requisitiondate.setLabel(RpListHdrParam1.getRequisitiondate());
            processedby.setLabel(RpListHdrParam1.getProcessedby());
            processeddate.setLabel(RpListHdrParam1.getProcesseddate());
            createdate.setLabel(RpListHdrParam1.getCreationdate());
            createby.setLabel(RpListHdrParam1.getCreatedby());
            cancelledby.setLabel(RpListHdrParam1.getCancelledby());
            cancelleddate.setLabel(RpListHdrParam1.getCancelleddate());
            approveby.setLabel(RpListHdrParam1.getApproveby());
            approvedate.setLabel(RpListHdrParam1.getApprovedate());
            requisitionstatus.setLabel(RpListHdrParam1.getRequisitionstatus());

            Listitem listitem = new Listitem();
            listitem.appendChild(buid);
            listitem.appendChild(bucode);
            listitem.appendChild(budescription);
            listitem.appendChild(id);
            listitem.appendChild(requisitionid);
            listitem.appendChild(requisitionnumber);
            listitem.appendChild(requisitiondate);
            listitem.appendChild(createdate);
            listitem.appendChild(createby);
            listitem.appendChild(processeddate);
            listitem.appendChild(processedby);
            listitem.appendChild(approvedate);
            listitem.appendChild(approveby);
            listitem.appendChild(cancelleddate);
            listitem.appendChild(cancelledby);
            listitem.appendChild(requisitionstatus);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String buid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String id = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String requisitionnumber = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String requisitiondate = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String processeddate = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String processedby = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String createdate = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String createby = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String cancelleddate = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String cancelledby = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String approveby = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String approvedate = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String requisitionstatus = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();

                    txtbuid.setText(buid);
                    txtbucode.setText(bucode);
                    txtbudes.setText(budescription);
                    txtid.setText(id);
                    txtreqin.setText(requisitionid);
                    txtreqnum.setText(requisitionnumber);
                    txtreqdte.setText(requisitiondate);
                    txtprocesste.setText(processeddate);
                    txtprocessby.setText(processedby);
                    txtcreatedate.setText(createdate);
                    txtcreateby.setText(createby);
                    txtcanceleddte.setText(cancelleddate);
                    txtcanceledby.setText(cancelledby);
                    txtapproveby.setText(approveby);
                    txtapprovedate.setText(approvedate);
                    txtreqstat.setText(requisitionstatus);

                }

            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                   String buid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String id = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String requisitionnumber = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String requisitiondate = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String processeddate = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String processedby = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String createdate = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String createby = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String cancelleddate = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String cancelledby = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String approveby = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String approvedate = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String requisitionstatus = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    form(buid, bucode, budescription, id, requisitionid, requisitionnumber, requisitiondate, processeddate, processedby, createdate, createby, cancelleddate, cancelledby, approveby, approvedate, requisitionstatus);
                }
            });
            listbox1.appendChild(listitem);
        }
    }

    public void form(String buid, String bucode, String budescription, String id, String requisitionid, String requisitionnumber, String requisitiondate, String processeddate, String processedby,String createdate,String createby, String cancelleddate, String cancelledby, String approveby, String approvedate, String requisitionstatus) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("buid", buid);
        map.put("bucode", bucode);
        map.put("budescription", budescription);
        map.put("id", id);
        map.put("requisitionid", requisitionid);
        map.put("requisitionnumber", requisitionnumber);
        map.put("requisitiondate", requisitiondate);
        map.put("processeddate", processeddate);
        map.put("processedby", processedby);
        map.put("createdate", createdate);
        map.put("createby", createby);
        map.put("cancelleddate", cancelleddate);
        map.put("cancelledby", cancelledby);
        map.put("approveby", approveby);
        map.put("approvedate", approvedate);
        map.put("requisitionstatus", requisitionstatus);
        Window window = (Window) Executions.createComponents(
                "/Tcash/RequestProductionCTRL.zul", null, map);
        window.setAttribute("parentControllerq",this);
        window.doModal();
    }

    @Listen("onClick=#goFind")
    public void findList() {
        
        String DateFrom = "";
        String Dateto = "";
        Path parent1 = new Path("/List_Request_PO/win_list_Po_find");
        Textbox txtBucodee = (Textbox) new Path(parent1, "txtBucode").getComponent();
        Textbox txtrpn = (Textbox) new Path(parent1, "txtrpn").getComponent();
        Textbox txtstatusfndd = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();
        Datebox dateboxfro = (Datebox)new Path(parent1, "dateboxfrom").getComponent();
        Datebox dateboxto1 = (Datebox)new Path(parent1, "dateboxto").getComponent();
        
        System.out.println(dateboxfro.getText());
        System.out.println(dateboxto1.getText());
        
//        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

         RpListHdrParam rpListHdrParams= new RpListHdrParam();
           if (dateboxfro.getText().isEmpty()){
                rpListHdrParams.setRequisitiondate("");
            } else {
                  
//                DateFrom = dt.format(dateboxfro.getText());
                rpListHdrParams.setRequisitiondate(dateboxfro.getText());
            }
            if (dateboxto1.getText().isEmpty()) {
                rpListHdrParams.setRequisitiondate("");
            } else {
//                Dateto =dt.format(dateboxto1.getText());
                rpListHdrParams.setRequisitiondate(dateboxto1.getText());
            }
       
        List<Integer> jumlahRecord = model.getCountRpListFind(txtBucodee.getText(), txtrpn.getValue(), dateboxfro.getText(), dateboxto1.getText(), txtstatusfndd.getText(),userId,responsibilityId);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        

        Path pg = new Path("/List_Request_PO");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);
       
        page.setActivePage(0);
        refreshFind(1);

        closeFind();
    }

    private void clearFindParam() {
        Path parent = new Path("/List_Request_PO/win_list_Po_find");
        Textbox txtBuidfind = (Textbox) new Path(parent, "txtBuid").getComponent();
        Textbox txtBucodefind = (Textbox) new Path(parent, "txtBucode").getComponent();
        Textbox txtBudescfind = (Textbox) new Path(parent, "txtBudesc").getComponent();
        Textbox txtrpn = (Textbox) new Path(parent, "txtrpn").getComponent();
        Textbox txtreid1 = (Textbox) new Path(parent, "txtreid").getComponent();
        Textbox txtstatusfind = (Textbox) new Path(parent, "txtstatusfnd").getComponent();
        Datebox dateboxfromfind = (Datebox) new Path(parent, "dateboxfrom").getComponent();
        Datebox dateboxtofind = (Datebox) new Path(parent, "dateboxto").getComponent();
        Combobox cmb = (Combobox) new Path(parent, "cmbstatus").getComponent();
        txtBucodefind.setText("");
        txtrpn.setText("");
        txtreid1.setText("");
        txtBuidfind.setText("");
        txtBudescfind.setText("");
        txtstatusfind.setText("");
        dateboxfromfind.setValue(null);
        dateboxtofind.setValue(null);
        cmb.setSelectedIndex(0);
    }

    @Listen("onClick=#edit")
    public void detaillist() {
        Map<String, Object> map = new HashMap<>();

        map.put("buid", txtbuid.getText());
        map.put("bucode", txtbucode.getText());
        map.put("budescription", txtbudes.getText());
        map.put("id", txtid.getText());
        map.put("requisitionid", txtreqin.getText());
        map.put("requisitionnumber", txtreqnum.getText());
        map.put("requisitiondate", txtreqdte.getText());
        map.put("processeddate", txtprocesste.getText());
        map.put("processedby", txtprocessby.getText());
        map.put("cancelleddate", txtcanceleddte.getText());
        map.put("cancelledby", txtcanceledby.getText());
        map.put("approveby", txtapproveby.getText());
        map.put("approvedate", txtapprovedate.getText());
        map.put("requisitionstatus", txtreqstat.getText());
        if (!txtreqin.getText().equals("")) {
            Window window = (Window) Executions.createComponents(
                    "/Tcash/RequestProductionCTRL.zul", null, map);
             window.setAttribute("parentController",this);
            window.doModal();
        } else {
            Messagebox.show("No record selected", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
        }

    }

    @Listen("onClick=#btnbu")
    public void lov() {

        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_code as \"Bussiness Unit Code\",bu_description as \"Bussiness Unit Description\",bu_id as \"bu id\" from table(pkg_tcash_lov.LovBU('" + "" + "','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU('" + " " + "','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtBucode, txtBudesc, txtBuid});
        composerLov.setHiddenColumn(new int[]{0, 3});

        composerLov.setTitle("List Of Bussiness Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_Po_find);
        w.doModal();

    }

    public void refreshFind(int activePage) {

        Path parent1 = new Path("/List_Request_PO/win_list_Po_find");
        Textbox txtBucodefnd = (Textbox) new Path(parent1, "txtBucode").getComponent();
        Textbox txtrpn = (Textbox) new Path(parent1, "txtrpn").getComponent();
        Datebox dateboxfromfnd = (Datebox) new Path(parent1, "dateboxfrom").getComponent();
        Datebox dateboxtofnd = (Datebox) new Path(parent1, "dateboxto").getComponent();
        Textbox txtstatusfind = (Textbox) new Path(parent1, "txtstatusfnd").getComponent();

        Path pg = new Path("/List_Request_PO");
        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();

        listbox1.getItems().clear();
        List<RpListHdrParam> RpListHdrParam = model.getRpListFind(txtBucodefnd.getValue(), txtrpn.getValue(), dateboxfromfnd.getText(),dateboxtofnd.getText(), txtstatusfind.getValue(),userId,responsibilityId, "" + activePage,""+ pageSize);
      
        for (RpListHdrParam RpListHdrParam1 : RpListHdrParam) {

            Listcell buid = new Listcell();
            Listcell bucode = new Listcell();
            Listcell budescription = new Listcell();
            Listcell id = new Listcell();
            Listcell requisitionid = new Listcell();
            Listcell requisitionnumber = new Listcell();
            Listcell requisitiondate = new Listcell();
            Listcell processeddate = new Listcell();
            Listcell processedby = new Listcell();
            Listcell createdate = new Listcell();
            Listcell createby = new Listcell();
            Listcell cancelleddate = new Listcell();
            Listcell cancelledby = new Listcell();
            Listcell approvedate = new Listcell();
            Listcell approveby = new Listcell();
            Listcell requisitionstatus = new Listcell();

            buid.setLabel(RpListHdrParam1.getBuid());
            bucode.setLabel(RpListHdrParam1.getBucode());
            budescription.setLabel(RpListHdrParam1.getBudescription());
            id.setLabel(RpListHdrParam1.getBuid());
            requisitionid.setLabel(RpListHdrParam1.getRequisitionid());
            requisitionnumber.setLabel(RpListHdrParam1.getRequisitionnumber());
            requisitiondate.setLabel(RpListHdrParam1.getRequisitiondate());
            processeddate.setLabel(RpListHdrParam1.getProcesseddate());
            processedby.setLabel(RpListHdrParam1.getProcessedby());
            createdate.setLabel(RpListHdrParam1.getCreationdate());
            createby.setLabel(RpListHdrParam1.getCreatedby());
            cancelleddate.setLabel(RpListHdrParam1.getCancelleddate());
            cancelledby.setLabel(RpListHdrParam1.getCancelledby());
            approveby.setLabel(RpListHdrParam1.getApproveby());
            approvedate.setLabel(RpListHdrParam1.getApprovedate());
            requisitionstatus.setLabel(RpListHdrParam1.getRequisitionstatus());
            
            Listitem listitem = new Listitem();
//            listitem.appendChild(buid);
//            listitem.appendChild(bucode);
//            listitem.appendChild(budescription);
//            listitem.appendChild(id);
//            listitem.appendChild(requisitionid);
//            listitem.appendChild(requisitionnumber);
//            listitem.appendChild(requisitiondate);
//            listitem.appendChild(processeddate);
//            listitem.appendChild(processedby);
//            listitem.appendChild(createdate);
//            listitem.appendChild(createby);
//            listitem.appendChild(cancelleddate);
//            listitem.appendChild(cancelledby);
//            listitem.appendChild(approvedate);
//            listitem.appendChild(approveby);
//            listitem.appendChild(requisitionstatus);
            listitem.appendChild(buid);
            listitem.appendChild(bucode);
            listitem.appendChild(budescription);
            listitem.appendChild(id);
            listitem.appendChild(requisitionid);
            listitem.appendChild(requisitionnumber);
            listitem.appendChild(requisitiondate);
            listitem.appendChild(createdate);
            listitem.appendChild(createby);
            listitem.appendChild(processeddate);
            listitem.appendChild(processedby);
            listitem.appendChild(approvedate);
            listitem.appendChild(approveby);
            listitem.appendChild(cancelleddate);
            listitem.appendChild(cancelledby);
            listitem.appendChild(requisitionstatus);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    String buid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String id = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String requisitionnumber = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String requisitiondate = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String processeddate = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String processedby = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String createdate = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String createby = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String cancelleddate = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String cancelledby = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String approvedate = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String approveby = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String requisitionstatus = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();

                    Path parent = new Path("/List_Request_PO");
                    Textbox txtbuid = (Textbox) new Path(parent, "txtbuid").getComponent();
                    Textbox txtreqin = (Textbox) new Path(parent, "txtreqin").getComponent();
                    Textbox txtbucode = (Textbox) new Path(parent, "txtbucode").getComponent();
                    Textbox txtbudes = (Textbox) new Path(parent, "txtbudes").getComponent();
                    Textbox txtid = (Textbox) new Path(parent, "txtid").getComponent();
                    Textbox txtreqnum = (Textbox) new Path(parent, "txtreqnum").getComponent();
                    Textbox txtreqdte = (Textbox) new Path(parent, "txtreqdte").getComponent();
                    Textbox txtreqstat = (Textbox) new Path(parent, "txtreqstat").getComponent();
                    Textbox txtprocesste = (Textbox) new Path(parent, "txtprocesste").getComponent();
                    Textbox txtprocessby = (Textbox) new Path(parent, "txtprocessby").getComponent();
                    Textbox txtcreatedate = (Textbox) new Path(parent, "txtcreatedate").getComponent();
                    Textbox txtcreateby = (Textbox) new Path(parent, "txtcreateby").getComponent();
                    Textbox txtcanceleddte = (Textbox) new Path(parent, "txtcanceleddte").getComponent();
                    Textbox txtcanceledby = (Textbox) new Path(parent, "txtcanceledby").getComponent();
                    Textbox txtapproveby = (Textbox) new Path(parent, "txtapproveby").getComponent();
                    Textbox txtapprovedate = (Textbox) new Path(parent, "txtapprovedate").getComponent();
                    
                    txtbuid.setText(buid);
                    txtbucode.setText(bucode);
                    txtbudes.setText(budescription);
                    txtid.setText(id);
                    txtreqin.setText(requisitionid);
                    txtreqnum.setText(requisitionnumber);
                    txtreqdte.setText(requisitiondate);
                    txtprocesste.setText(processeddate);
                    txtprocessby.setText(processedby);
                    txtcreatedate.setText(createdate);
                    txtcreateby.setText(createby);
                    txtcanceleddte.setText(cancelleddate);
                    txtcanceledby.setText(cancelledby);
                    txtapproveby.setText(approveby);
                    txtapprovedate.setText(approvedate);
                    txtreqstat.setText(requisitionstatus);

                }

            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                  String buid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String id = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String requisitionnumber = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String requisitiondate = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String processeddate = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String processedby = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String createdate = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String createby = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String cancelleddate = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String cancelledby = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String approvedate = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String approveby = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String requisitionstatus = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    form(buid, bucode, budescription, id, requisitionid, requisitionnumber, requisitiondate, processeddate, processedby, createdate, createby, cancelleddate, cancelledby, approveby, approvedate, requisitionstatus);
                }
            });
            listbox1.appendChild(listitem);
        }
    }
    
    
    @Listen("onSelect=#cmbstatus")
    public void cmbStatus(){
        if (cmbstatus.getSelectedIndex()==0) {
            txtstatusfnd.setText("");
        } else if(cmbstatus.getSelectedIndex()==1) {
             txtstatusfnd.setText("1");
        }else if(cmbstatus.getSelectedIndex()==2){
            txtstatusfnd.setText("2");
        }else if(cmbstatus.getSelectedIndex()==3){
            txtstatusfnd.setText("3");
        }else {
            txtstatusfnd.setText("4");
        }
    }
    
    @Listen("onClick=#clear")
    public void clearButton(){
        txtBuid.setText("");
        txtBucode.setText("");
        txtBudesc.setText("");
        txtreid.setText("");
        txtrpn.setText("");
        dateboxfrom.setText("");
        dateboxto.setText("");
        cmbstatus.setSelectedIndex(-1);
        txtstatusfnd.setText("");
    }
}
