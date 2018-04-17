/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.LovBUParam;
import id.my.berkah.tcash1.model.RpListDtlParam;
import id.my.berkah.tcash1.model.RpListHdrParam;
import id.my.berkah.util.IDefines;
import id.my.berkah.util.IDefines.COLORS;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class RequestProductionCTRL extends SelectorComposer<Component> implements IDefines{

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String query;
    private String SAVE_PATH;
    private String message;
    private List<RpListDtlParam> RpListDtlParam;
    List<String[]> data = new ArrayList<String[]>();

    @Wire
    Paging userPaging;

    @Wire
    Window win_Request_production;

    @Wire
    Textbox flag, txtrpid, txtstatus, txtbuId, txtout, txtbucode, txtbuDesc, txtid, txtdate, txtrpcode, txtprocessdate, txtprocessedby, txtcanceleddate, txtCanceledby, txtappeddate, txtappby,
            txtreqid, status, txtreqin, txtitemid, txtitemdesc, txtuom, txtregionalid, txtregionaldesc, txtbuid1, txtbudesc1, txtwhid1, txtwhdesc1, txtreqdtlid, txtcreatby, txtcreatdate,txtcreateddate,txtcreatedby;

    @Wire
    Listbox listbox;

    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    @Wire
    Button submit, newrecord, approve, btnbrowse, btnadd, btndelete, btnrefresh, save, cancel, btnbu;

    ModelTcashCTLR model = new ModelTcashCTLR();

    ListRequestProduction parentControllerq;
    @Listen("onCreate=#win_Request_production")
    public void onCreateWindow() {
        parentControllerq=(ListRequestProduction)win_Request_production.getAttribute("parentControllerq");
        selectlisthdr();
        requery();

        if (txtbuId.getText().equals("")) {
            flag.setText("INSERT");
           
//            btndelete.setDisabled(true);
        } else {
            flag.setText("UPDATE");
//            btndelete.setDisabled(true);
        }

        if (txtstatus.getText().equalsIgnoreCase("Draft")) {
            txtbucode.setReadonly(true);
            txtbuDesc.setReadonly(true);
            txtrpcode.setReadonly(true);
            txtdate.setReadonly(true);
            txtstatus.setReadonly(true);
//            enableSave();
            btnbu.setDisabled(true);
//            txtbucode.setDisabled(true);
//            btndelete.setDisabled(true);
              txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        } else if (txtstatus.getText().equals("")) {
            btnbu.setDisabled(false);
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        }
//        if (txtstatus.getText().equals("") || txtstatus.getText() == null) {
//            disableCreateNew();
        if (!txtrpcode.getText().equals("")) {
            btnbu.setDisabled(true);
            txtbucode.setReadonly(true);
             txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        } else if (txtstatus.getText().equalsIgnoreCase("Draft") && RpListDtlParam.isEmpty()) {
//            enableSave();
            btnbu.setDisabled(true);
            txtbucode.setDisabled(true);
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        } else if (txtstatus.getText().equalsIgnoreCase("Submitted") || txtstatus.getText().equalsIgnoreCase("Approved")) {
//            enableSave();
            btnbu.setDisabled(true);
            txtbucode.setDisabled(true);
             txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//            btnbrowse.setDisabled(true);

        }
//            btndelete.setDisabled(true);
//            submit.setDisabled(true);
//        } else if (txtstatus.getText().equals("Draft")) {
////            enableSave();
////            btndelete.setDisabled(true);
//        }
//
//        if (txtstatus.getText().equals("Approved") || txtstatus.getText().equals("Canceled")) {
////            disableALL();
//            
//        }

    }

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtrpid.getText().isEmpty()) {
            win_Request_production.detach();
            return;
        }
        if (txtstatus.getText().equals("Draft")) {
              Messagebox.show("Are you sure want to close?\nData has not Submmited yet",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            win_Request_production.detach();
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
        } else  if (txtstatus.getText().equals("Submitted")) {
               Messagebox.show("Are you sure want to close?\nData has not Approved yet",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            win_Request_production.detach();
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
        } else{
          win_Request_production.detach();   
        }
      
    }

    @Listen("onClick=#save")
    public void save1() {
            Messagebox.show("Are you sure want to Save?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {

                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListRequestProduction.zul");
                                cf.setFunctionName("SAVE");
                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                if (oe.getOutError() == 0) {

                                    if (flag.getText().equals("INSERT")) {
                                        Map<String, Object> map = model.doRpSaveHdr(flag.getText(), txtrpid.getText(), "1", txtbuId.getText(), userId);
                                        if (map.get("OutError").equals(0)) {
                                            Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                            txtrpid.setText(map.get("OutReqId").toString());
                                            flag.setText("UPDATE");
                                            btnbu.setDisabled(true);
                                            txtbucode.setReadonly(true);
                                            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                            txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                            txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                            parentControllerq.requery();
                                        } else {
                                            Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                        }
                                      selectlisthdr();

                                    } else {
                                        Map<String, Object> map = model.doRpSaveHdr(flag.getText(), txtrpid.getText(), "1", txtbuId.getText(), userId);
                                      
                                            if (map.get("OutError").toString().equals(0)) {
                                                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                                txtout.setValue(map.get("OutReqId").toString());
                                                btnbu.setDisabled(true);
                                                txtbucode.setReadonly(true);
                                            } else {
                                                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);

                                            }
                                             selectlisthdr();
                                    }
                                    
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }

                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
//        }
    }
//    }

    @Listen("onClick = #btnbu")
    public void lovbu() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, bu_code as \"Business Unit Code\",bu_description as \"Business Unit Description\",bu_id as \"bu id\" from table(pkg_tcash_lov.LovBU('" + "" + "','" + responsibilityId + "','" + userId + "'))where (upper(bu_code) like upper('?') or upper(bu_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU('" + " " + "','" + responsibilityId + "','" + userId + "'))Where bu_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbucode, txtbuDesc, txtbuId});
        composerLov.setHiddenColumn(new int[]{0, 3});

        composerLov.setTitle("List Of Business Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Request_production);
        w.doModal();

    }

    @Listen(Events.ON_OK + "= #txtbucode")
    public void onOkTxtSearchBu() {
        try {
            List<LovBUParam> LovBUParam = model.getLovBU(txtbucode.getText(), responsibilityId, userId);
            txtbuId.setValue(LovBUParam.get(0).getBu_id());
            txtbucode.setValue(LovBUParam.get(0).getBu_code());
            txtbuDesc.setValue(LovBUParam.get(0).getBu_description());
        } catch (Exception e) {
            e.printStackTrace();
            lovbu();
        }
    }

    @Listen("onClick=#newrecord")
    public void addbaru() {
        txtbuId.setText("");
        txtbuDesc.setText("");
        txtbucode.setText("");
        flag.setText("INSERT");
        txtout.setText("");
        txtrpid.setText("");
        txtstatus.setText("");
        txtappby.setText("");
        txtappeddate.setText("");
        txtcreateddate.setText("");
        txtcreatedby.setText("");
        txtCanceledby.setText("");
        txtcanceleddate.setText("");
        txtprocessedby.setText("");
        txtprocessdate.setText("");
        txtrpcode.setText("");
        txtdate.setText("");
        txtid.setText("");
        listbox.getItems().clear();
        btnbu.setDisabled(false);
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    @Listen("onClick=#submit")
    public void submit() {
//        if (txtstatus.getText().equalsIgnoreCase("")) {
//             Messagebox.show("Please save this transaction first","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
//        
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        } else if (txtstatus.getText().equalsIgnoreCase("SUBMITTED")) { 
//                Messagebox.show("This transaction status has been Submitted","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        }else if (txtstatus.getText().equalsIgnoreCase("CANCELED")) { 
//              Messagebox.show("This transaction status has been Canceled","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        } else{
        
        Messagebox.show("Are you sure want to Submit?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
//                            if (txtstatus.getText().equals("Draft")) {
                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListRequestProduction.zul");
                            cf.setFunctionName("SUBMIT");
                            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                            if (oe.getOutError() == 0) {

                                Map<String, Object> map = model.doRpSubmit(txtrpid.getText(), txtbuId.getText(), userId, responsibilityId, "2");
                                if (txtbuId.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtrpid.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtstatus.getValue().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else {
                                    if (map.get("OutError").equals(0)) {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                        selectlisthdr();
                                        btnbu.setDisabled(true);
                                        txtbucode.setReadonly(true);
                                       txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                        txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
//                                         btnbrowse.setDisabled(true);
                                        parentControllerq.requery();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                }

                            } else {

                                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        }
                    }
                }
        );
//    }
    }

    @Listen("onClick=#approve")
    public void approve() {
        
//          if (txtstatus.getText().equalsIgnoreCase("")) {
//             Messagebox.show("Please Save this Request first","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("draft")) {
//             Messagebox.show("This transaction status still Draft","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        }
//        else if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        }else if (txtstatus.getText().equalsIgnoreCase("CANCELED")) { 
//              Messagebox.show("This transaction status has been Canceled","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        } else{
        
        Messagebox.show("Are you sure want to Approve ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListRequestProduction.zul");
                            cf.setFunctionName("APPROVE");
                            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                            if (oe.getOutError() == 0) {
                                Map<String, Object> map = model.doRpApproved(txtrpid.getText(), txtbuId.getText(), "3", userId);
                                if (txtbuId.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtrpid.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtstatus.getValue().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else {
                                    if (map.get("OutError").equals(0)) {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                        selectlisthdr();
                                        btnbu.setDisabled(true);
                                        txtbucode.setReadonly(true);
                                        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                        txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
                                        parentControllerq.requery();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                }
                            } else {
                                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }

                    }
                }
        );
//    }
    }

    @Listen("onClick=#cancel")
    public void cancel() {
        
//          if (txtstatus.getText().equalsIgnoreCase("")) {
//             Messagebox.show("Please Save this transaction first","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        } else if (txtstatus.getText().equalsIgnoreCase("SUBMITTED")) { 
//                Messagebox.show("This transaction status has been Submitted","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        }else if (txtstatus.getText().equalsIgnoreCase("CANCELED")) { 
//              Messagebox.show("This transaction status  has been Canceled","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        } else{
//        

        Messagebox.show("Are you sure want to Cancel ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            ProcedureUtilImpl ci = new ProcedureUtilImpl();
                            ParamCekFunction cf = new ParamCekFunction();
                            cf.setUserId(global[0]);
                            cf.setResponsibilityId(global[2]);
                            cf.setFileName("/Tcash/ListRequestProduction.zul");
                            cf.setFunctionName("CANCEL");
                            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                            if (oe.getOutError() == 0) {

                                Map<String, Object> map = model.doRpCancel(txtrpid.getText(), txtbuId.getText(), "4", userId);
                                if (txtbuId.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtrpid.getText().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else if (txtstatus.getValue().equals("")) {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else {
                                    if (map.get("OutError").equals(0)) {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                        selectlisthdr();
                                        btnbu.setDisabled(true);
                                        txtbucode.setReadonly(true);
                                        parentControllerq.requery();
//                                         btnbrowse.setDisabled(true);
//                                            disableALL();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);

                                    }
                                }
                            } else {
                                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        }
                    }
                }
        );
//    }
    }

    private void refresh(int activePage) {
        listbox.getItems().clear();
        RpListDtlParam = model.getRpListDtl(txtrpid.getValue(), "" + activePage, "" + pageSize);
        addListChild(RpListDtlParam);
    }

    private void addListChild(List<RpListDtlParam> list) {

        for (RpListDtlParam RpListDtlParam1 : list) {

            Listcell requisitionid = new Listcell();
            Listcell requisitiondtlid = new Listcell();
            Listcell requisitionline = new Listcell();
            Listcell requireddate = new Listcell();
            Listcell position = new Listcell();
            Listcell itemid = new Listcell();
            Listcell itemcode = new Listcell();
            Listcell itemname = new Listcell();
            Listcell regionalid = new Listcell();
            Listcell regionalcode = new Listcell();
            Listcell regionaldescription = new Listcell();
            Listcell buid = new Listcell();
            Listcell bucode = new Listcell();
            Listcell budescription = new Listcell();
            Listcell warehouseid = new Listcell();
            Listcell warehouse = new Listcell();
            Listcell warehousedescription = new Listcell();
            Listcell warehousecode = new Listcell();
            Listcell whdescription = new Listcell();
            Listcell orderunit = new Listcell();
            Listcell orderquantity = new Listcell();
            Listcell purchaseorderqty = new Listcell();

            requisitionid.setLabel(RpListDtlParam1.getRequisitionid());
            requisitiondtlid.setLabel(RpListDtlParam1.getRequisitiondtlid());
            requisitionline.setLabel(RpListDtlParam1.getRequisitionline());
            requireddate.setLabel(RpListDtlParam1.getRequireddate());
            position.setLabel(RpListDtlParam1.getPosition());
            itemid.setLabel(RpListDtlParam1.getItemid());
            itemcode.setLabel(RpListDtlParam1.getItemcode());
            itemname.setLabel(RpListDtlParam1.getItemname());
            orderunit.setLabel(RpListDtlParam1.getOrderunit());
            orderquantity.setLabel(RpListDtlParam1.getOrderquantity());
            orderquantity.setStyle("text-align:right");
            purchaseorderqty.setLabel(RpListDtlParam1.getPurchaseorderqty());
            buid.setLabel(RpListDtlParam1.getBuid());
            bucode.setLabel(RpListDtlParam1.getBucode());
            budescription.setLabel(RpListDtlParam1.getBudescription());
            warehouseid.setLabel(RpListDtlParam1.getWarehouseid());
            warehouse.setLabel(RpListDtlParam1.getWarehouse());
            warehousedescription.setLabel(RpListDtlParam1.getWarehousedescription());
            whdescription.setLabel(RpListDtlParam1.getWhdescription());
            warehousecode.setLabel(RpListDtlParam1.getWarehousecode());
            regionalid.setLabel(RpListDtlParam1.getRegionalid());
            regionaldescription.setLabel(RpListDtlParam1.getRegionaldescription());

            Listitem listitem = new Listitem();
            listitem.appendChild(requisitionid);
            listitem.appendChild(requisitiondtlid);
            listitem.appendChild(requisitionline);
            listitem.appendChild(requireddate);
            listitem.appendChild(position);
            listitem.appendChild(itemid);
            listitem.appendChild(itemcode);
            listitem.appendChild(itemname);
            listitem.appendChild(regionalid);
            listitem.appendChild(regionalcode);
            listitem.appendChild(regionaldescription);
            listitem.appendChild(buid);
            listitem.appendChild(bucode);
            listitem.appendChild(budescription);
            listitem.appendChild(warehouseid);
            listitem.appendChild(warehouse);
            listitem.appendChild(warehousedescription);
            listitem.appendChild(warehousecode);
            listitem.appendChild(whdescription);
            listitem.appendChild(orderunit);
            listitem.appendChild(orderquantity);
            listitem.appendChild(purchaseorderqty);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String requisitiondtlid = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String requisitionline = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String requireddate = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String position = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String itemcode = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String itemname = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String regionalid = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String regionalcode = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String regionaldescription = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String buid = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String warehouseid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String warehouse = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    String warehousedescription = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                    String whdescription = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                    String warehousecode = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                    String orderunit = ((Listcell) t.getTarget().getChildren().get(19)).getLabel();
                    String orderquantity = ((Listcell) t.getTarget().getChildren().get(20)).getLabel();
                    String purchaseorderqty = ((Listcell) t.getTarget().getChildren().get(21)).getLabel();

                    txtreqid.setText(requisitionid);
                    txtreqdtlid.setText(requisitiondtlid);
                   
                }
            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {

                    String requisitionid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String requisitiondtlid = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String requisitionline = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String requireddate = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String position = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String itemid = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String itemcode = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String itemname = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String regionalid = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String regionalcode = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String regionaldescription = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String buid = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String bucode = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String budescription = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String warehouseid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    String warehouse = ((Listcell) t.getTarget().getChildren().get(15)).getLabel();
                    String warehousedescription = ((Listcell) t.getTarget().getChildren().get(16)).getLabel();
                    String whdescription = ((Listcell) t.getTarget().getChildren().get(18)).getLabel();
                    String warehousecode = ((Listcell) t.getTarget().getChildren().get(17)).getLabel();
                    String orderunit = ((Listcell) t.getTarget().getChildren().get(19)).getLabel();
                    String orderquantity = ((Listcell) t.getTarget().getChildren().get(20)).getLabel();
                    String purchaseorderqty = ((Listcell) t.getTarget().getChildren().get(21)).getLabel();
                    form(requisitionid, requisitiondtlid, requisitionline, requireddate, position, itemid, itemcode, itemname, orderunit, orderquantity, purchaseorderqty, buid, bucode, budescription, warehouseid, warehouse, warehousedescription, whdescription, warehousecode, regionalid, regionalcode, regionaldescription);
                }
            });
            listbox.appendChild(listitem);
        }
    }

//    @Listen(Events.ON_CTRL_KEY + " = #listbox")
//    public void delete(Event event) {
//
//        int keyCode = ((KeyEvent) event).getKeyCode();
//        String s = "";
//        switch (keyCode) {
//            case KeyEvent.DELETE: {
//                DeleteListbox();
//            }
//        }
//
//    }
    @Listen("onClick=#btnrefresh")
    public void requery() {
        List<Integer> jumlahRecord = model.getCountRpListDtl(txtrpid.getValue());

        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/win_Request_production");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

        page.setActivePage(0);
        selectlisthdr();
        refresh(1);
    }

    @Listen("onClick=#btnadd")
    public void detaillist() {
         ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListRequestProduction.zul");
        cf.setFunctionName("ADD");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
        if (txtrpid.getText().isEmpty() && !txtbuId.getText().isEmpty()) {
                Messagebox.show("Please save this transaction first","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            }
        } else if (!txtrpid.getText().isEmpty() && !txtbuId.getText().isEmpty() && txtstatus.getText().equalsIgnoreCase("Draft")) {
            Map<String, Object> map = new HashMap<>();
            map.put("requisitionid", txtrpid.getText());
            map.put("state", txtstatus.getText());
            btnbu.setDisabled(true);
            txtbucode.setReadonly(true); 
            txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
            txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
            txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
            if (!txtbuId.getText().equals("")) {
                Window window = (Window) Executions.createComponents(
                        "/Tcash/DetailRequestProduction.zul", null, map);
                window.setAttribute("parentControllerq", this);
                window.doModal();
            } else {
                Messagebox.show("Please choose business unit first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
    }
         else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void form(String requisitionid, String requisitiondtlid, String requisitionline, String requireddate, String position, String itemid, String itemcode, String itemname, String orderunit, String orderquantity, String purchaseorderqty, String buid, String bucode, String budescription, String warehouseid, String warehouse, String warehousedescription, String whdescription, String warehousecode, String regionalid, String regionalcode, String regionaldescription) throws ParseException {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        date = dt.parse(requireddate);
        Map<String, Object> map = new HashMap<>();
        map.put("requisitionid", requisitionid);
        map.put("requisitiondtlid", requisitiondtlid);
        map.put("requisitionline", requisitionline);
        map.put("requireddate", date);
        map.put("position", position);
        map.put("itemid", itemid);
        map.put("itemcode", itemcode);
        map.put("itemname", itemname);
        map.put("orderunit", orderunit);
        map.put("orderquantity", orderquantity);
        map.put("purchaseorderqty", purchaseorderqty);
        map.put("buid", buid);
        map.put("bucode", bucode);
        map.put("budescription", budescription);
        map.put("warehouseid", warehouseid);
        map.put("warehouse", warehouse);
        map.put("warehousedescription", warehousedescription);
        map.put("whdescription", whdescription);
        map.put("warehousecode", warehousecode);
        map.put("regionalid", regionalid);
        map.put("regionalcode", regionalcode);
        map.put("regionaldescription", regionaldescription);
        map.put("state", txtstatus.getText());
        map.put("InReqNo", txtrpcode.getText());
        Window window = (Window) Executions.createComponents(
                "/Tcash/DetailRequestProduction.zul", null, map);
        window.setAttribute("parentControllerq", this);
        window.doModal();
    }

    public void selectlisthdr() {
        try{
        if (!txtrpid.getValue().isEmpty()) {
            List<RpListHdrParam> RpListHdrParam = model.selectRpListHdr(txtrpid.getValue(),userId,responsibilityId);
            txtbuId.setValue(RpListHdrParam.get(0).getBuid());
            txtbucode.setValue(RpListHdrParam.get(0).getBucode());
            txtbuDesc.setValue(RpListHdrParam.get(0).getBudescription());
            txtid.setValue(RpListHdrParam.get(0).getId());
            txtreqid.setValue(RpListHdrParam.get(0).getRequisitionid());
            txtrpcode.setValue(RpListHdrParam.get(0).getRequisitionnumber());
            txtdate.setValue(RpListHdrParam.get(0).getRequisitiondate());
            txtprocessdate.setValue(RpListHdrParam.get(0).getProcesseddate());
            txtprocessedby.setValue(RpListHdrParam.get(0).getProcessedby());
            txtcreateddate.setValue(RpListHdrParam.get(0).getCreationdate());
            txtcreatedby.setValue(RpListHdrParam.get(0).getCreatedby());
            txtCanceledby.setValue(RpListHdrParam.get(0).getCancelledby());
            txtcanceleddate.setValue(RpListHdrParam.get(0).getCancelleddate());
            txtappby.setValue(RpListHdrParam.get(0).getApproveby());
            txtappeddate.setValue(RpListHdrParam.get(0).getApprovedate());
            txtstatus.setValue(RpListHdrParam.get(0).getRequisitionstatus());
            txtcreatby.setText(RpListHdrParam.get(0).getCreatedby());
            txtcreatdate.setText(RpListHdrParam.get(0).getCreationdate());
            System.out.println(RpListHdrParam.get(0).getCreationdate());
            System.out.println(RpListHdrParam.get(0).getCreatedby());
        }
      }catch(Exception e){
            
        }
        
    }

    private int saveFile(Media media) throws IOException {
        int result = 1;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        SAVE_PATH = properties.getProperty("upload_directory");

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            InputStream fin = media.getStreamData();
            in = new BufferedInputStream(fin);
            File baseDir = new File(SAVE_PATH);

            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }

            File file = new File(SAVE_PATH + media.getName());
            OutputStream fout = new FileOutputStream(file);
            out = new BufferedOutputStream(fout);
            byte buffer[] = new byte[1024];
            int ch = in.read(buffer);
            while (ch != -1) {

                out.write(buffer, 0, ch);
                ch = in.read(buffer);
            }

            result = 0;
        } catch (IOException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();

                }

            } catch (IOException e) {
                message = e.getMessage();
            }
        }
        return result;

    }

    @Listen("onUpload=#btnbrowse")
    public int upload(UploadEvent event) {
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//          return 1;
//        } else if (txtstatus.getText().equalsIgnoreCase("SUBMITTED")) { 
//                Messagebox.show("This transaction status has been Submitted","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        return 1;
//        }else if (txtstatus.getText().equalsIgnoreCase("CANCELED")) { 
//              Messagebox.show("This transaction status has been Canceled","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//        return 1;
//        } 
        if (!txtrpcode.getText().isEmpty() && txtstatus.getText().equalsIgnoreCase("Draft")) {

            ProcedureUtilImpl ci = new ProcedureUtilImpl();
            ParamCekFunction cf = new ParamCekFunction();
            cf.setUserId(global[0]);
            cf.setResponsibilityId(global[2]);
            cf.setFileName("/Tcash/ListRequestProduction.zul");
            cf.setFunctionName("UPLOAD");
//    	
            ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

            if (oe.getOutError() == 0) {

                if (!txtrpid.getText().equals("")) {

                    try {
                        Media[] medias = event.getMedias();
                        for (Media media : medias) {
                            Clients.showNotification("upload details: " + "\r"
                                    + " name " + media.getName()
                                    + " size " + (media.isBinary() ? media.getByteData().length : media.getStringData().length())
                                    + " type " + media.getContentType()
                            );

                            saveFile(media);
                            String content = null;

                            if (media.isBinary()) {
                                content = new String(media.getByteData());

                            } else {
                                content = media.getStringData();
                            }

                            String[] lines = content.trim().split("\n");

                            //List<RpListDtlParam> list = new ArrayList<>();
                            String x;
                            String datas[];
                            for (int i = 1; i < lines.length; i++) {
                                x = lines[i];
                                datas = x.split(";");
                                System.out.println("baris = " + i);
                                for (int j = 0; j < datas.length; j++) {
                                    String result = datas[j];
                                    System.out.println(result);
                                }
                                if (datas.length == 0) {
                                    break;
                                }
                                String ItemCode, BuCode, WhCode, Qty,
                                        ItemDescription, BuDesc, WhDesc, ItemId, BuId, WhId, Uom, NeedDate;

                                ItemCode = datas[0].trim();
                                BuCode = datas[1].trim();
                                WhCode = datas[2].trim();
                                Qty = datas[3].trim();
                                NeedDate = datas[4].trim();

                                Map<String, Object> map = model.doRpGetUploadAttr(ItemCode, BuCode, WhCode);

                                ItemId = String.valueOf(map.get("OutItemId"));
                                ItemDescription = (map.get("OutItemDescription").toString());
                                Uom = String.valueOf(map.get("OutUOM"));
                                BuId = String.valueOf(map.get("OutBuId"));
                                BuDesc = map.get("OutBuDesc").toString();
                                WhId = String.valueOf(map.get("OutWhId"));
                                WhDesc = map.get("OutWhDesc").toString();
                                if (map.get("OutError").equals(0)) {
                                    Map<String, Object> map1 = model.doRpSaveDtl("INSERT",
                                            txtrpid.getValue(),
                                            txtrpcode.getValue(),
                                            "0",
                                            "0",
                                            ItemId,
                                            ItemCode,
                                            BuId,
                                            BuCode,
                                            WhId,
                                            WhCode,
                                            Uom,
                                            Qty,
                                            NeedDate,
                                            userId);
                                    if (map1.get("OutError").equals(0)) {
//                                        txtreqdtlid.setText(map1.get("OutReqDtlId").toString());
                                        requery();
                                    } else {
                                        Messagebox.show(map1.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                } else {
                                    Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            }
                            parentControllerq.requery();
                            Messagebox.show("Succes upload file", "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                else {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("InReqId", txtrpid.getText());
//                    if (!txtbuId.getText().equals("")) {
//                        Window window = (Window) Executions.createComponents(
//                                "/Tcash/DetailRequestProduction.zul", null, map);
//                        window.doModal();
//                    } else {
//                        Messagebox.show("Please choose business unit first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
//                    }
//                }

            } else {
                Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
            Messagebox.show("This transaction status has been Submitted", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
            return 1;
        } else if (txtstatus.getText().equalsIgnoreCase("Approved")) {
            Messagebox.show("This transaction status has been Approved", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
          return 1;
        } else if (txtstatus.getText().equals("")||txtstatus.getText() == null) {
            Messagebox.show("Please save this transaction first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
              return 1;
        } else if (txtstatus.getText().equalsIgnoreCase("Canceled")) {
            Messagebox.show("This transaction status has ben Canceled", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
              return 1;
        }
        return 0;
    }

    @Listen("onClick=#btndelete")
    public void DeleteListbox() {
//        if (txtstatus.getText().equalsIgnoreCase("APPROVED")) {
//            Messagebox.show("This transaction status has been Approved","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            return ;
//        } else if (txtstatus.getText().equalsIgnoreCase("SUBMITTED")) { 
//                Messagebox.show("This transaction status has been Submitted","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//            return ;
//        }else if (txtstatus.getText().equalsIgnoreCase("CANCELED")) { 
//              Messagebox.show("This transaction status has been Canceled","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
//              return ;
//        }
        int index = listbox.getSelectedIndex();
        if (index<0) {
            Messagebox.show("Please Choose detail ","Request Production",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        Messagebox.show("Are you sure want to Delete?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            if (!txtrpid.getText().isEmpty()) {
                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListRequestProduction.zul");
                                cf.setFunctionName("DELETE");

                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                if (oe.getOutError() == 0) {

                                    try {
                                        if (txtreqdtlid.getText().isEmpty()) {
                                            Messagebox.show("Please choose detail request production  first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                        } else {

                                            String Status = "";

                                            if (txtstatus.getText().equalsIgnoreCase("Submitted")) {
                                                Status = "2";
                                            } else if (txtstatus.getText().equalsIgnoreCase("Approved")) {
                                                Status = "3";
                                            } else if (txtstatus.getText().equalsIgnoreCase("Canceled")) {
                                                Status = "4";
                                            } else if (txtstatus.getText().equalsIgnoreCase("Draft")) {
                                                Status = "1";
                                            }
                                            Map<String, Object> map = model.doRpDelete(txtrpid.getText(), txtreqdtlid.getText(), Status, userId);
                                            if (map.get("OutError").equals(0)) {
                                                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.INFORMATION);
                                                selectlisthdr();
                                                requery();
                                            } else {
                                                Messagebox.show(map.get("OutMessages").toString(), "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                                            }
                                        }
                                    } catch (Exception ex) {
                                    }
                                } else {

                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else {
                                Messagebox.show("Please choose detail request production  first", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
    }

    public void disableALL() {
//        btnbrowse.setDisabled(true);
        btnadd.setDisabled(true);
//        btndelete.setDisabled(true);
        btnrefresh.setDisabled(true);
        submit.setDisabled(true);
        approve.setDisabled(true);
        cancel.setDisabled(true);
        save.setDisabled(true);
    }

    public void disableapprove() {
        btnbrowse.setDisabled(true);
        btnadd.setDisabled(true);
//        btndelete.setDisabled(true);
        btnrefresh.setDisabled(true);
        submit.setDisabled(true);
        approve.setDisabled(true);
        cancel.setDisabled(true);
        save.setDisabled(true);
    }

    public void disableCreateNew() {
        btnbrowse.setDisabled(true);
        btnadd.setDisabled(true);
//        btndelete.setDisabled(true);
        submit.setDisabled(true);
        approve.setDisabled(true);
        cancel.setDisabled(true);
         txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
             txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    public void enableSave() {
//        btnbrowse.setDisabled(false);
//        btnadd.setDisabled(false);
//        btndelete.setDisabled(false);
//        btnrefresh.setDisabled(false);
//        newrecord.setDisabled(false);
//        save.setDisabled(false);
//        submit.setDisabled(false);
//        approve.setDisabled(true);
//        cancel.setDisabled(false);
        txtbucode.setReadonly(true);
//        btnbu.setDisabled(true);
    }

    public void enableSubmit() {
        btnbrowse.setDisabled(true);
        btnadd.setDisabled(true);
        btndelete.setDisabled(true);
        btnrefresh.setDisabled(true);
        submit.setDisabled(true);
        approve.setDisabled(false);
        cancel.setDisabled(false);
        save.setDisabled(true);
        txtbucode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtbuDesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtbudesc1.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
    }

//    @Listen("onSelect=#listbox")
//    public void onBlurListbox() {
//        if (txtstatus.getText().equals("Draft")) {
//            btndelete.setDisabled(false);
//        } else {
//            btndelete.setDisabled(true);
//        }
//    }
//    @Listen("onAfterRender=#listbox")
//    public void onAfterRenderListbox() {
//        if (RpListDtlParam.isEmpty()) {
//            submit.setDisabled(true);
//        } else {
//            submit.setDisabled(false);
//        }
//    }
}
