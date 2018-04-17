package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.LovForwardAgentParam;
import id.my.berkah.tcash1.model.LovSiteParam;
import id.my.berkah.tcash1.model.LovsupParam;
import id.my.berkah.tcash1.model.PcListDtlParam;
import id.my.berkah.tcash1.model.PcListHdrParam;
import id.my.berkah.util.IDefines;
import id.my.berkah.util.IDefines.COLORS;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
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
public class PurchaseContract extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Paging userPaging;

    @Wire
    Listbox listbox;

    @Wire
    Window win_purchase_contract;

    @Wire
    Textbox txtcontractid, txtcontract, txtsupplierid, txtsuppliercode, txtsupplierdesc, txtTOPid,
            txtforwardcode, txtforwarddesc, txtsupsiteid, txtsupsitecode, txtsupsitedesc, txtContractDate, txtdesc, txtcurrency,
            txtdoc, txtstat, txtflag, txtout, txttermiated, txttermiatedby, txtcreationdate, txtcreationby,
            txtmodifieddate, txtmodifiedby, txtstatdesc, txtcontractdtlId, txttotal;

    public Textbox getTxtstat() {
        return txtstat;
    }

    public void setTxtstat(Textbox txtstat) {
        this.txtstat = txtstat;
    }

    double total = 0;

    ModelTcashCTLR model = new ModelTcashCTLR();
    PcListHdrParam pcListHdrParam = new PcListHdrParam();

    @Wire
    Datebox txteffectiveDate, txtexpiredate;

    @Wire
    Combobox txtTOPcode;

    @Wire
    Button btnsup, newrecord, btnfwd, btnsupsite, btnadd, btnrefresh, btnsave, btnactive, btnterminated, btndelete, btnrevised;

    @Wire
    Intbox txtquantity, txtdiscountamount, txtamount;

    private int startPageNumber = 1;
    private final int pageSize = 5;
    private int JumlahRecord = 0;
    ListPurchaseContract parentController;

    @Listen("onClick=#close")
    public void close() {
        if (txtcontractid.getText().isEmpty()) {
            win_purchase_contract.detach();
            return;
        }
        if (txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
            Messagebox.show("Are you sure want to close?\nTransaction not yet Active ",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                parentController.requery();
                                win_purchase_contract.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else {
            parentController.requery();
            win_purchase_contract.detach();

        }

    }

    @Listen("onCreate=#win_purchase_contract")
    public void oncreate() throws ParseException {
        parentController = (ListPurchaseContract) win_purchase_contract.getAttribute("parentController");
        requery();
        onCreate();
        if (txtstatdesc.getText().equalsIgnoreCase("ACTIVE")) {
            disableall();
            txteffectiveDate.setDisabled(true);
            txtexpiredate.setDisabled(true);
            btnfwd.setDisabled(true);
            colorDefault();
        } else if (txtstatdesc.getText().equals("") || txtstatdesc.getText() == null) {
            txteffectiveDate.setConstraint("no past");
            txtexpiredate.setConstraint("no past");
            colorMAndatory();
        } else if (txtstatdesc.getText().equalsIgnoreCase("TERMINATED")) {
            disableall();
            txteffectiveDate.setDisabled(true);
            txtexpiredate.setDisabled(true);
            btnfwd.setDisabled(true);
            colorDefault();
        } else if (txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
            btnsup.setDisabled(true);
            btnsupsite.setDisabled(true);
            txteffectiveDate.setConstraint("no past");
            txtexpiredate.setConstraint("no past");
            colorMAndatory();
        } else {
            colorMAndatory();
        }

        if (txtcontractid.getText().equals("")) {
            txtflag.setText("INSERT");
            txtcurrency.setText("IDR");
            txtquantity.setValue(0);
            txtamount.setValue(0);
//            txtamount.setStyle("text-align:right");

        } else {
            txtflag.setText("UPDATE");
            txtcurrency.setText("IDR");
            onCreate();
        }

    }

    @Listen("onSelect=#txtTOPcode")
    public void combobox() {
        if (txtTOPcode.getSelectedIndex() == 0) {
            txtTOPid.setText("1");
        } else {
            txtTOPid.setText("2");
        }
    }

    @Listen("onClick=#txteffectiveDate")
    public void validateBackDate() {
        if (txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
            txteffectiveDate.setConstraint("no past");
        } else if (txtstatdesc.getText().equalsIgnoreCase("ACTIVE") || txtstatdesc.getText().equalsIgnoreCase("TERMINATED")) {
            txteffectiveDate.setDisabled(true);
        }
    }

    @Listen("onClick=#btnsave")
    public void save() {
        String Dates = "";
        String Date1 = "";
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Dates = dt.format(txteffectiveDate.getValue());
            Date1 = dt.format(txtexpiredate.getValue());
        } catch (Exception e) {

        }

        Messagebox.show("Are you sure want to Save this contract?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) throws ParseException {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            String Dates = "";
                            String Date1 = "";
                            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                Dates = dt.format(txteffectiveDate.getValue());
                                Date1 = dt.format(txtexpiredate.getValue());
                            } catch (Exception ex) {

                            }

                            Map<String, Object> validation = model.dopcValidasiHdr("SAVE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                                    txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                                    txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                            if (validation.get("OutError").equals(0)) {

                                if (txtflag.getText().equalsIgnoreCase("INSERT")) {

                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("SAVE");
//    	
                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                                    if (oe.getOutError() == 0) {
                                        Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                                                txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(), txtforwardcode.getText(), "1", userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                                                txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);
                                        if (map.get("outError").toString().equals("0")) {
//                                                Messagebox.show(map.get("outMessages").toString());
                                            txtout.setText(map.get("outContractId").toString());
                                            txtflag.setText("UPDATE");
                                            selectlisthdr();
                                            autorefresh();
                                            parentController.requery();
//                                                disableSave();
//                                                colorDefault();
//                                            btnactive.setDisabled(false);
                                            Map<String, Object> map1 = model.doPcUpdateStatus(txtcontractid.getValue(), "1", userId);
                                            if (map1.get("OutError").equals(0)) {
                                                Messagebox.show(map1.get("OutMessages").toString());
                                                selectlisthdr();
                                                autorefresh();
//                                                    disableSave();
                                                parentController.requery();
                                            } else {
                                                Messagebox.show(map.get("OutMessages").toString());

                                            }

                                        } else {
                                            Messagebox.show(map.get("outMessages").toString());
                                        }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

                                } else if (txtflag.getText().equalsIgnoreCase("UPDATE") && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
                                    Map<String, Object> validation1 = model.dopcValidasiHdr("UPDATE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                                            txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                                            txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                                    if (validation1.get("OutError").equals(0)) {
                                        Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                                                txtsuppliercode.getText(), txtTOPid.getValue(), txtcurrency.getText(), txtforwardcode.getText(), "1", userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                                                txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                                        if (map.get("outError").equals(0)) {
                                            Messagebox.show(map.get("outMessages").toString());
                                            selectlisthdr();
                                            autorefresh();
                                            parentController.requery();
                                            Map<String, Object> map1 = model.doPcUpdateStatus(txtcontractid.getValue(), "1", userId);
                                            if (map1.get("OutError").equals(0)) {
//                                                    Messagebox.show(map1.get("OutMessages").toString());
                                                selectlisthdr();
                                                autorefresh();
//                                                    disableSave();
//                                                    colorDefault();
                                                parentController.requery();
                                            } else {
                                                Messagebox.show(map.get("OutMessages").toString());
                                            }
                                        } else {
                                            Messagebox.show(map.get("outMessages").toString());
                                        }
                                    } else {
                                        Messagebox.show(validation1.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                }

                            } else {
                                Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        }
                    }

                }
        );

    }

    @Listen("onClick = #btnsup")
    public void lovsupplier() {
        HashMap map = new HashMap<>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Id\",supplier_number as \"Supplier Number\",supplier_name as \"Supplier Name\" from table(pkg_tcash_lov.LovSupplierPC(''))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSupplierPC('" + " " + "'))Where supplier_number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupplierid, txtsuppliercode, txtsupplierdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        Path parent = new Path("/win_purchase_contract");
        Textbox txtsupsiteid1 = (Textbox) new Path(parent, "txtsupsiteid").getComponent();
        Textbox txtsupsitecode1 = (Textbox) new Path(parent, "txtsupsitecode").getComponent();
        Textbox txtsupsitedesc1 = (Textbox) new Path(parent, "txtsupsitedesc").getComponent();

        txtsupsiteid1.setText("");
        txtsupsitecode1.setText("");
        txtsupsitedesc1.setText("");
        composerLov.setTitle("List Of Supplier");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_purchase_contract);
        w.doModal();

    }

    @Listen(Events.ON_OK + "= #txtsuppliercode")
    public void onOkTxtSearchSupplier() {
        try {
            List<LovsupParam> LovsupParam = model.getLovSupplierPC(txtsuppliercode.getText());
            txtsupplierid.setValue(LovsupParam.get(0).getSupplierid());
            txtsuppliercode.setValue(LovsupParam.get(0).getSuppliernumber());
            txtsupplierdesc.setValue(LovsupParam.get(0).getSuppliername());
            txtsupsiteid.setText("");
            txtsupsitecode.setText("");
            txtsupsitedesc.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            lovsupplier();
        }
    }

    @Listen("onClick = #btnsupsite")
    public void lovsuppliersite() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, supplier_id as \"Id\",supplier_number as \"Supplier Number\",supplier_name as \"Supplier Name\" from table(pkg_tcash_lov.LovSite('" + txtsupplierid.getText() + "'))where (upper(supplier_number) like upper('?') or upper(supplier_name) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovSite('" + txtsupplierid.getText() + "'))Where supplier_number LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtsupsiteid, txtsupsitecode, txtsupsitedesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Supplier Site");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_purchase_contract);
        w.doModal();

    }

    @Listen(Events.ON_OK + "= #txtsupsitecode")
    public void onOkTxtSearchSuppliersite() {
        try {
            List<LovSiteParam> LovSiteParam = model.getLovSite(txtsupplierid.getText());
            txtsupsiteid.setValue(LovSiteParam.get(0).getSupplierid());
            txtsupsitecode.setValue(LovSiteParam.get(0).getSuppliernumber());
            txtsupsitedesc.setValue(LovSiteParam.get(0).getSuppliername());
        } catch (Exception e) {
            e.printStackTrace();
            lovsuppliersite();
        }
    }

    @Listen("onClick = #btnfwd")
    public void lovfowrward() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, forwarding_agent as \"Forward Agent\",description as \"Description\" from table(pkg_tcash_lov.LovForwardAgent('" + "" + "'))where (upper(forwarding_agent) like upper('?') or upper(description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovForwardAgent('" + "" + "'))Where forwarding_agent LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtforwardcode, txtforwarddesc});
        composerLov.setHiddenColumn(new int[]{0});

        composerLov.setTitle("List Of Forwarding Agent");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_purchase_contract);
        w.doModal();

    }

    @Listen(Events.ON_OK + "= #txtforwardcode")
    public void onOkTxtSearchForwardCode() {
        try {
            List<LovForwardAgentParam> LovForwardAgentParam = model.getLovForwardAgent(txtforwardcode.getText());
            txtforwardcode.setValue(LovForwardAgentParam.get(0).getForwardingagent());
            txtforwarddesc.setValue(LovForwardAgentParam.get(0).getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            lovfowrward();
        }
    }

    @Listen("onClick=#btnactive")
    public void activity() {
        String Dates = "";
        String Date1 = "";
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Dates = dt.format(txteffectiveDate.getValue());
            Date1 = dt.format(txtexpiredate.getValue());
        } catch (Exception ex) {

        }
//        if (txtstat.getText().equals("")) {
//            Messagebox.show("Please save this transaction first", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        if (txtstat.getText().equalsIgnoreCase("terminated")) {
//            Messagebox.show("This transaction status has been Terminated", "Purchase Contraact", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        if (listbox.getItems().isEmpty()) {
//            Messagebox.show("Please add detail first", "Purchase Contraact", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        Map<String, Object> validation = model.dopcValidasiHdr("ACTIVE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

        if (validation.get("OutError").equals(0)) {
            Messagebox.show("Are you sure want to Active this contract?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) throws ParseException {
                            if (Messagebox.ON_OK.equals(e.getName())) {

                                if (listbox.getItemCount() == 0 && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
                                    Messagebox.show("Please add detail purchase contract first");
                                } else if (txtstatdesc.getText().equals("")) {
                                    Messagebox.show("Please save this transaction first.", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                                } else {

//                                if (txtstatdesc.getText().equals("DRAFT") || txtstatdesc.getText().equals("REVISED")) {
                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("ACTIVATE");
//    	
                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                    if (oe.getOutError() == 0) {
//                                    if (txtsupplierid.getText().equals("")) {
//                                        Messagebox.show("Supplier cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtsupsiteid.getText().equals("")) {
//                                        Messagebox.show("Supplier site cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtquantity.getValue().equals(0)) {
//                                        Messagebox.show("Agreed quantity cannot be zero", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtquantity.getText().equals("")) {
//                                        Messagebox.show("Agreed quantity cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtamount.getText().equals(0)) {
//                                        Messagebox.show("Agreed amount cannot be zero", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtamount.getText().equals("")) {
//                                        Messagebox.show("Agreed amount cant be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtTOPid.getText().equals("")) {
//                                        Messagebox.show("Term of payment cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txtforwardcode.getText().equals("")) {
//                                        Messagebox.show("Forwading agent cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else if (txteffectiveDate.getText().equals("")) {
//                                        Messagebox.show("Effective date cannot be empty", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else {
                                        String Dates = "";
                                        String Date1 = "";
                                        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                                        try {
                                            Dates = dt.format(txteffectiveDate.getValue());
                                            Date1 = dt.format(txtexpiredate.getValue());
                                        } catch (Exception ex) {

                                        }
                                        Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                                                txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(), txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                                                txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                                        if (map.get("outError").equals(0)) {

                                            selectlisthdr();
                                            autorefresh();
                                            colorDefault();
                                            txteffectiveDate.setDisabled(true);
                                            txtexpiredate.setDisabled(true);
//                                                disableSave();
//                                                btnactive.setDisabled(false);

                                            Map<String, Object> map1 = model.doPcUpdateStatus(txtcontractid.getText(), "2", userId);
                                            if (map1.get("OutError").equals(0)) {
                                                Messagebox.show(map1.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.INFORMATION);
                                                autorefresh();
                                                disableall();
                                                colorDefault();
                                            } else {
                                                Messagebox.show(map1.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                                            }

                                        } else {
                                            Messagebox.show(map.get("outMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                                        }

//                              
//                                    }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                }
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                            }
                        }
                    }
            );
        } else {
            Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void disableall() {
        txtTOPcode.setDisabled(true);
        txtsuppliercode.setReadonly(true);
        txtforwardcode.setReadonly(true);
        txtdesc.setReadonly(true);
//        txteffectiveDate.setDisabled(true);
//        txtexpiredate.setReadonly(true);
        txtdesc.setReadonly(true);
        txtdoc.setReadonly(true);
        txtquantity.setReadonly(true);
        txtamount.setReadonly(true);
        txtdoc.setReadonly(true);
        txtforwardcode.setReadonly(true);
        txtsupsitecode.setReadonly(true);
        txtTOPcode.setDisabled(true);
//        btnadd.setDisabled(true);
//        btnrefresh.setDisabled(true);
//        btnsave.setDisabled(true);
        btnsup.setDisabled(true);
        btnsupsite.setDisabled(true);
        btnfwd.setDisabled(true);
//        btnactive.setDisabled(true);
//        btndelete.setDisabled(true);
//        listbox.setDisabled(true);

    }

    public void enableall() {
        txtTOPcode.setDisabled(false);
        txtsuppliercode.setReadonly(false);
        txtforwardcode.setReadonly(false);
        txtdesc.setReadonly(false);
        txteffectiveDate.setReadonly(false);
        txtexpiredate.setReadonly(false);
        txtquantity.setReadonly(false);
        txtamount.setReadonly(false);
        txtdoc.setReadonly(false);
        btnadd.setDisabled(false);
        btnrefresh.setDisabled(false);
        btnsave.setDisabled(false);
        btnsup.setDisabled(false);
        btnsupsite.setDisabled(false);
//        btnfwd.setDisabled(false);
        btnactive.setDisabled(false);
        listbox.setDisabled(false);

    }

    @Listen("onClick=#btnterminated")
    public void aterminated() {

//        if (txtstatdesc.getText().equalsIgnoreCase("ACTIVE") && !txtcontractid.getText().equals("")) {
        Messagebox.show("Are you sure want to terminated ?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) throws ParseException {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            String Dates = "";
                            String Date1 = "";
                            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                Dates = dt.format(txteffectiveDate.getValue());
                                Date1 = dt.format(txtexpiredate.getValue());
                            } catch (Exception ex) {

                            }

                            Map<String, Object> validation = model.dopcValidasiHdr("TERMINATED", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                                    txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                                    txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                            if (validation.get("OutError").equals(0)) {

                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                cf.setFunctionName("TERMINATE");
                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                if (oe.getOutError() == 0) {

                                    Map<String, Object> map = model.doPcUpdateStatus(txtcontractid.getText(), "3", userId);
                                    if (map.get("OutError").equals(0)) {
                                        Messagebox.show(map.get("OutMessages").toString());
                                        selectlisthdr();
                                        autorefresh();
                                        parentController.requery();
//                                        disableTerminated();
                                        colorDefault();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString());
                                    }
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else {
                                Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }

                    }
                }
        );

//        } 
    }

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);//, pageSize);
    }

//    @Listen("onClick=#btnrefresh")
    public void requery() {

        List<Integer> jumlahRecord = model.getCountPcListDtl(txtcontractid.getValue());

        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        Path pg = new Path("/win_purchase_contract");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);
        page.setActivePage(0);
        selectlisthdr();
        refresh(1);

    }

    private void refresh(int activePage) {

        listbox.getItems().clear();
        List<PcListDtlParam> PcListDtlParam = model.getPcListDtl(txtcontractid.getValue(), "" + activePage, "" + pageSize);
        for (PcListDtlParam PcListDtlParam1 : PcListDtlParam) {

            Listcell contractid = new Listcell();
            Listcell positionnumber = new Listcell();
            Listcell itemid = new Listcell();
            Listcell item = new Listcell();
            Listcell itemdesc = new Listcell();
            Listcell minimumquantity = new Listcell();
            Listcell maximumquantity = new Listcell();
            Listcell purchaseunit = new Listcell();
            Listcell price = new Listcell();
            Listcell discpercentage = new Listcell();
            Listcell discamount = new Listcell();
            Listcell purchasepriceunit = new Listcell();
            Listcell amount = new Listcell();
            Listcell purchasepricegroup = new Listcell();
            Listcell contractdtlid = new Listcell();

            contractid.setLabel(PcListDtlParam1.getContractid());
            positionnumber.setLabel(PcListDtlParam1.getPositionnumber());
            itemid.setLabel(PcListDtlParam1.getItemid());
            item.setLabel(PcListDtlParam1.getItem());
            itemdesc.setLabel(PcListDtlParam1.getItemdescription());
            minimumquantity.setLabel(PcListDtlParam1.getMinimumquantity());
            minimumquantity.setStyle("text-align:right");
            maximumquantity.setLabel(PcListDtlParam1.getMaximumquantity());
            maximumquantity.setStyle("text-align:right");
            purchaseunit.setLabel(PcListDtlParam1.getPurchaseunit());
            price.setLabel(PcListDtlParam1.getPrice());
            price.setStyle("text-align:right");
            discpercentage.setLabel(PcListDtlParam1.getDiscpercentage());
            discpercentage.setStyle("text-align:right");
            discamount.setLabel(PcListDtlParam1.getDiscamount());
            discamount.setStyle("text-align:right");
            purchasepriceunit.setLabel(PcListDtlParam1.getPurchasepriceunit());
            amount.setLabel(PcListDtlParam1.getAmount());
            amount.setStyle("text-align:right");
            purchasepricegroup.setLabel(PcListDtlParam1.getPurchasepricegroup());
            contractdtlid.setLabel(PcListDtlParam1.getContractdtlid());

            Listitem listitem = new Listitem();
            listitem.appendChild(contractid);
            listitem.appendChild(positionnumber);
            listitem.appendChild(itemid);
            listitem.appendChild(item);
            listitem.appendChild(itemdesc);
            listitem.appendChild(minimumquantity);
            listitem.appendChild(maximumquantity);
            listitem.appendChild(purchaseunit);
            listitem.appendChild(price);
            listitem.appendChild(discpercentage);
            listitem.appendChild(discamount);
            listitem.appendChild(purchasepriceunit);
            listitem.appendChild(amount);
            listitem.appendChild(purchasepricegroup);
            listitem.appendChild(contractdtlid);
            try {
                total += Double.parseDouble(PcListDtlParam1.getMaximumquantity());
            } catch (Exception e) {
            }

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String contractdtlid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    txtcontractdtlId.setText(contractdtlid);

                }
            });

            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String contractid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    String positionnumber = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                    String itemid = ((Listcell) t.getTarget().getChildren().get(2)).getLabel();
                    String item = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                    String itemdesc = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                    String minimumquantity = ((Listcell) t.getTarget().getChildren().get(5)).getLabel();
                    String maximumquantity = ((Listcell) t.getTarget().getChildren().get(6)).getLabel();
                    String purchaseunit = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    String price = ((Listcell) t.getTarget().getChildren().get(8)).getLabel();
                    String discpercentage = ((Listcell) t.getTarget().getChildren().get(9)).getLabel();
                    String discamount = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                    String purchasepriceunit = ((Listcell) t.getTarget().getChildren().get(11)).getLabel();
                    String amount = ((Listcell) t.getTarget().getChildren().get(12)).getLabel();
                    String purchasepricegroup = ((Listcell) t.getTarget().getChildren().get(13)).getLabel();
                    String contractdtlid = ((Listcell) t.getTarget().getChildren().get(14)).getLabel();
                    form(contractid, positionnumber, itemid, item, itemdesc, minimumquantity, maximumquantity, purchaseunit, price, discpercentage, discamount, purchasepriceunit, amount, purchasepricegroup, contractdtlid);
                }
            });
            listbox.appendChild(listitem);
        }

//        txttotal.setValue("" + total);
    }

    public void form(String contractid, String positionnumber, String itemid, String item, String itemdesc, String minimumquantity,
            String maximumquantity, String purchaseunit, String price, String discpercentage, String discamount, String purchasepriceunit, String amount, String purchasepricegroup,
            String contractdtlid) {

        Map<String, Object> map = new HashMap<>();
        map.put("contractid", contractid);
        map.put("positionnumber", positionnumber);
        map.put("itemid", itemid);
        map.put("item", item);
        map.put("itemdesc", itemdesc);
        map.put("minimumquantity", minimumquantity);
        map.put("maximumquantity", maximumquantity);
        map.put("purchaseunit", purchaseunit);
        map.put("price", price);
        map.put("discpercentage", discpercentage);
        map.put("discamount", discamount);
        map.put("purchasepriceunit", purchasepriceunit);
        map.put("amount", amount);
        map.put("purchasepricegroup", purchasepricegroup);
        map.put("contractdtlid", contractdtlid);
        map.put("rderqty", txtquantity.getValue());
        map.put("state", txtstatdesc.getValue());
        Window window = (Window) Executions.createComponents(
                "/Tcash/DetailPurchaseContract.zul", null, map);
        window.setAttribute("parentcontroller", this);
//        colorDefault();
        window.doModal();
    }

    @Listen("onClick=#btnadd")
    public void detaillist() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListPurchaseContract.zul");
        cf.setFunctionName("ADD");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            String Dates = "";
            String Date1 = "";
            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

            if (txtflag.getText().equalsIgnoreCase("INSERT") && txtcontractid.getText().isEmpty() && txtstatdesc.getText().equals("")) {
                Messagebox.show("Please save this transaction first", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (txtflag.getText().equalsIgnoreCase("UPDATE") && !txtcontractid.getText().isEmpty() && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
                try {
                    Dates = dt.format(txteffectiveDate.getValue());

                    Date1 = dt.format(txtexpiredate.getValue());
                } catch (Exception ex) {

                }
                Map<String, Object> validation = model.dopcValidasiHdr("SAVE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                        txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                        txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                if (validation.get("OutError").equals(0)) {
                    Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                            txtsuppliercode.getText(), txtTOPid.getValue(), txtcurrency.getText(), txtforwardcode.getText(), "1", userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                            txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);
                    autorefresh();
                    if (map.get("outError").equals(0)) {
                        map.put("contractid", txtcontractid.getText());
                        map.put("rderqty", txtquantity.getValue());
                        map.put("state", txtstatdesc.getValue());
                        if (!txtcontractid.getText().equals("")) {
                            Window window = (Window) Executions.createComponents(
                                    "/Tcash/DetailPurchaseContract.zul", null, map);
                            window.setAttribute("parentcontroller", this);
                            window.doModal();
                        } else {
                            Messagebox.show("Please save this transaction first.", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                        }
                    } else {
                        Messagebox.show(map.get("outMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                } else {
                    Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);

                }

            } else if (txtstatdesc.getText().equalsIgnoreCase("ACTIVE")) {
                Messagebox.show("This transaction status has been Active", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (txtflag.getText().equalsIgnoreCase("INSERT") && txtcontractid.getText().isEmpty() && txtstatdesc.getText().equals("") && txtsuppliercode.getText().isEmpty()) {
                Messagebox.show("Please save this transaction first.", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (txtstatdesc.getText().equalsIgnoreCase("TERMINATED")) {
                Messagebox.show("This transaction status has been terminated.", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (txtflag.getText().equalsIgnoreCase("UPDATE") && !txtcontractid.getText().isEmpty()) {
                Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                        txtsuppliercode.getText(), txtTOPid.getValue(), txtcurrency.getText(), txtforwardcode.getText(), "1", userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                        txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                if (map.get("outError").equals(0)) {
                    map.put("contractid", txtcontractid.getText());
                    map.put("rderqty", txtquantity.getValue());
                    map.put("state", txtstatdesc.getValue());
                    if (!txtcontractid.getText().equals("")) {
                        Window window = (Window) Executions.createComponents(
                                "/Tcash/DetailPurchaseContract.zul", null, map);
                        window.setAttribute("parentcontroller", this);
                        window.doModal();
                    }
                }
            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    private void selectlisthdr() {
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dte = new SimpleDateFormat("dd-MM-yyyy");
        if (!txtout.getText().equals("")) {
            List<PcListHdrParam> PcListHdrParam = model.selectPcListHdr(txtout.getValue());
            txtcontractid.setValue(PcListHdrParam.get(0).getContractid());
            txtcontract.setValue(PcListHdrParam.get(0).getContract());
            txtdesc.setValue(PcListHdrParam.get(0).getDescription());
            txtContractDate.setValue(PcListHdrParam.get(0).getContractdate());
            txtsupplierid.setValue(PcListHdrParam.get(0).getSupplierid());
            txtsuppliercode.setValue(PcListHdrParam.get(0).getSupplier());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSuppliername());
            txtTOPcode.setValue(PcListHdrParam.get(0).getTermsofpaymentname());
            txtTOPid.setValue(PcListHdrParam.get(0).getTermsofpayment());
            txtcurrency.setValue(PcListHdrParam.get(0).getCurrency());
            txtforwardcode.setValue(PcListHdrParam.get(0).getForwardingagent());
            txtforwarddesc.setValue(PcListHdrParam.get(0).getForwardingagentname());
            txtstat.setValue(PcListHdrParam.get(0).getContractstatus());
            txtsupsitecode.setValue(PcListHdrParam.get(0).getSuppliersite());
            txtsupsiteid.setValue(PcListHdrParam.get(0).getSiteid());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSitename());
            txtdoc.setValue(PcListHdrParam.get(0).getContractmapno());
            txtquantity.setText(PcListHdrParam.get(0).getAgreedquantity());
            txttermiated.setValue(PcListHdrParam.get(0).getTerminatedate());
            txttermiatedby.setValue(PcListHdrParam.get(0).getTerminateby());
            txtamount.setText(PcListHdrParam.get(0).getAgreedamount());
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreatedperiod());
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreationdate());
            txtcreationby.setValue(PcListHdrParam.get(0).getCreatedby());
            txtmodifiedby.setValue(PcListHdrParam.get(0).getLastupdatedby());
            txtmodifieddate.setValue(PcListHdrParam.get(0).getLastupdatedate());
            try {
                date = dt.parse(PcListHdrParam.get(0).getEffectivedate());
                if (PcListHdrParam.get(0).getExpirydate() != null) {
                    date1 = dt.parse(PcListHdrParam.get(0).getExpirydate());
                    txtexpiredate.setValue(date1);
                } else {
//                    txtexpiredate.setValue(null);
                }
            } catch (ParseException ex) {
                Logger.getLogger(PurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
            }
            txteffectiveDate.setValue(date);
            txtexpiredate.setValue(date1);
            txtstatdesc.setValue(PcListHdrParam.get(0).getContractstatusname());
        } else {

        }

    }

    public void autorefresh() {
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dte = new SimpleDateFormat("dd-MM-yyyy");
        if (!txtcontractid.getText().equals("")) {
            List<PcListHdrParam> PcListHdrParam = model.selectPcListHdr(txtcontractid.getValue());
            txtcontractid.setValue(PcListHdrParam.get(0).getContractid());
            txtcontract.setValue(PcListHdrParam.get(0).getContract());
            txtdesc.setValue(PcListHdrParam.get(0).getDescription());
            txtContractDate.setValue(PcListHdrParam.get(0).getContractdate());
            txtsupplierid.setValue(PcListHdrParam.get(0).getSupplierid());
            txtsuppliercode.setValue(PcListHdrParam.get(0).getSupplier());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSuppliername());
            txtTOPcode.setValue(PcListHdrParam.get(0).getTermsofpaymentname());
            txtTOPid.setValue(PcListHdrParam.get(0).getTermsofpayment());
            txtcurrency.setValue(PcListHdrParam.get(0).getCurrency());
            txtforwardcode.setValue(PcListHdrParam.get(0).getForwardingagent());
            txtforwarddesc.setValue(PcListHdrParam.get(0).getForwardingagentname());
            txtstat.setValue(PcListHdrParam.get(0).getContractstatus());
            txtsupsitecode.setValue(PcListHdrParam.get(0).getSuppliersite());
            txtsupsiteid.setValue(PcListHdrParam.get(0).getSiteid());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSitename());
            txtdoc.setValue(PcListHdrParam.get(0).getContractmapno());
            txtquantity.setText(PcListHdrParam.get(0).getAgreedquantity());
            txttermiated.setValue(PcListHdrParam.get(0).getTerminatedate());
            txttermiatedby.setValue(PcListHdrParam.get(0).getTerminateby());
            txtamount.setText(PcListHdrParam.get(0).getAgreedamount());
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreatedperiod());
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreationdate());
            txtcreationby.setValue(PcListHdrParam.get(0).getCreatedby());
            txtmodifiedby.setValue(PcListHdrParam.get(0).getLastupdatedby());
            txtmodifieddate.setValue(PcListHdrParam.get(0).getLastupdatedate());
            try {
                date = dt.parse(PcListHdrParam.get(0).getEffectivedate());
                if (PcListHdrParam.get(0).getExpirydate() != null) {
                    date1 = dt.parse(PcListHdrParam.get(0).getExpirydate());
                    txtexpiredate.setValue(date1);
                } else {
//                    txtexpiredate.setValue(null);
                }
            } catch (ParseException ex) {
                Logger.getLogger(PurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
            }
            txteffectiveDate.setValue(date);
            txteffectiveDate.setWidth("210px");
            txtexpiredate.setValue(date1);
            txtexpiredate.setWidth("210px");
            txtstatdesc.setValue(PcListHdrParam.get(0).getContractstatusname());
        } else {

        }
    }

    public void onCreate() {
        Date date = new Date();
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        if (!txtcontractid.getText().isEmpty()) {
            List<PcListHdrParam> PcListHdrParam = model.selectPcListHdr(txtcontractid.getValue());
            txtcontractid.setValue(PcListHdrParam.get(0).getContractid());
            txtcontract.setValue(PcListHdrParam.get(0).getContract());
            txtdesc.setValue(PcListHdrParam.get(0).getDescription());
            txtContractDate.setValue(PcListHdrParam.get(0).getContractdate());
            txtsupplierid.setValue(PcListHdrParam.get(0).getSupplierid());
            txtsuppliercode.setValue(PcListHdrParam.get(0).getSupplier());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSuppliername());
            txtTOPcode.setValue(PcListHdrParam.get(0).getTermsofpaymentname());
            txtTOPid.setValue(PcListHdrParam.get(0).getTermsofpayment());
            txtcurrency.setValue(PcListHdrParam.get(0).getCurrency());
            txtforwardcode.setValue(PcListHdrParam.get(0).getForwardingagent());
            txtforwarddesc.setValue(PcListHdrParam.get(0).getForwardingagentname());
            txtstat.setValue(PcListHdrParam.get(0).getContractstatus());
            txtsupsitecode.setValue(PcListHdrParam.get(0).getSuppliersite());
            txtsupsiteid.setValue(PcListHdrParam.get(0).getSiteid());
            txtsupplierdesc.setValue(PcListHdrParam.get(0).getSitename());
            txtdoc.setValue(PcListHdrParam.get(0).getContractmapno());
            txtquantity.setText(String.valueOf(PcListHdrParam.get(0).getAgreedquantity()));
            txttermiated.setValue(PcListHdrParam.get(0).getTerminatedate());
            txttermiatedby.setValue(PcListHdrParam.get(0).getTerminateby());
            txtamount.setText(PcListHdrParam.get(0).getAgreedamount());
//            txtamount.setStyle("text-align:right");
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreatedperiod());
            txtcreationdate.setValue(PcListHdrParam.get(0).getCreationdate());
            txtcreationby.setValue(PcListHdrParam.get(0).getCreatedby());
            txtmodifiedby.setValue(PcListHdrParam.get(0).getLastupdatedby());
            txtmodifieddate.setValue(PcListHdrParam.get(0).getLastupdatedate());
            try {
                date = dt.parse(PcListHdrParam.get(0).getEffectivedate());
            } catch (ParseException ex) {
                Logger.getLogger(PurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (PcListHdrParam.get(0).getExpirydate() != null) {
                    date1 = dt.parse(PcListHdrParam.get(0).getExpirydate());
                    txtexpiredate.setValue(date1);
                } else {
                    txtexpiredate.setValue(null);
                }

            } catch (ParseException ex) {
                Logger.getLogger(PurchaseContract.class.getName()).log(Level.SEVERE, null, ex);
            }

            txteffectiveDate.setValue(date);

            txtstatdesc.setValue(PcListHdrParam.get(0).getContractstatusname());
        } else {

        }
    }

    @Listen("onClick=#newrecord")
    public void addnewrecord() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        txtcontractid.setText("");
        txtcontract.setText("");
        txtTOPcode.setSelectedIndex(0);
        txtTOPid.setText("");
        txtsupplierid.setText("");
        txtsuppliercode.setText("");
        txtsupplierdesc.setText("");
        txtforwardcode.setText("");
        txtforwarddesc.setText("");
        txtsupsiteid.setText("");
        txtsupsitecode.setText("");
        txtsupsitedesc.setText("");
        txtContractDate.setText("");
        txtdesc.setText("");
        txtcurrency.setValue("");
        txtquantity.setValue(0);
        txtdoc.setText("");
        txtamount.setText("0");
        txtstat.setText("");
        txtstatdesc.setText("");
        txtout.setText("");
        txtflag.setText("INSERT");
        listbox.getItems().clear();
        enableall();
//        btnadd.setDisabled(true);
        txteffectiveDate.setValue(null);
        txtexpiredate.setValue(null);
        newrecord.setDisabled(false);
        btnfwd.setDisabled(false);
        txteffectiveDate.setDisabled(false);
        txtexpiredate.setDisabled(false);
//        txteffectiveDate.setValue(null);
//        txtexpiredate.setValue(null);
        txtTOPcode.setSelectedItem(null);
        txtcreationdate.setText("");
        txtcreationby.setText("");
        txtmodifieddate.setText("");
        txtmodifiedby.setText("");
        txteffectiveDate.setConstraint("no past");
        txtexpiredate.setConstraint("no past");
        txttermiated.setText("");
        txttermiatedby.setText("");
        colorMAndatory();
    }

    @Listen("onClick=#btndelete")
    public void deleteLine() {

        Messagebox.show("Are you sure want to Delete?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {

                            if (!txtcontractdtlId.getText().isEmpty() && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
                                ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                ParamCekFunction cf = new ParamCekFunction();
                                cf.setUserId(global[0]);
                                cf.setResponsibilityId(global[2]);
                                cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                cf.setFunctionName("DELETE");

                                ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                                if (oe.getOutError() == 0) {
//                                    if (txtcontractdtlId.getText().isEmpty()) {
//                                        Messagebox.show("Silahkan Pilih Data yang akan di Hapus", "Request Production", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    } else {

//                                        if (txtstatdesc.getText().equals("ACTIVE")) {
//                                            Messagebox.show("Data Telah di ACTIVE!", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                        } else if (txtstatdesc.getText().equals("TERMINATED")) {
//                                            Messagebox.show("Data Telah di TERMINATED!", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                        } else if (txtstatdesc.getText().equals("REVISED")) {
//                                            Messagebox.show("Data Telah di REVISED!", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
//                                        } else if (txtstatdesc.getText().equals("DRAFT")) {
                                    Map<String, Object> map = model.doPcDeleteDtl(txtcontractid.getValue(), txtcontractdtlId.getValue(), userId);
                                    if (map.get("OutError").equals(0)) {
                                        Messagebox.show(map.get("OutMessages").toString());
                                        selectlisthdr();
                                        requery();
                                    } else {
                                        Messagebox.show(map.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                } else {
                                    Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                }
                            } else if (!txtcontractdtlId.getText().isEmpty() && txtstatdesc.getText().equalsIgnoreCase("ACTIVE")) {
                                Messagebox.show("Can not delete detail purchase contract because this transaction has been actived", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                Messagebox.show("Please choose detail first", "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }
                }
        );
    }

//    @Listen(Events.ON_CTRL_KEY + " = #listbox")
//    public void delete(Event event) {
//
//        int keyCode = ((KeyEvent) event).getKeyCode();
//        String s = "";
//        switch (keyCode) {
//            case KeyEvent.DELETE: {
//                deleteLine();
//            }
//        }
//
//    }
    public void disableNEw() {
        btnadd.setDisabled(true);
        btndelete.setDisabled(true);
        btnactive.setDisabled(true);
        btnterminated.setDisabled(true);
        btnrevised.setDisabled(true);

    }

    public void disableSave() {
        btnadd.setDisabled(false);
        btndelete.setDisabled(false);
        btnactive.setDisabled(false);
//        btnterminated.setDisabled(true);
//        btnrevised.setDisabled(true);
        btnsup.setDisabled(true);
        btnsupsite.setDisabled(true);
        btnfwd.setDisabled(true);
//         txtquantity.setStyle("background-color:#FFFACD");
//        txtamount.setStyle("background-color:#FFFACD");
    }

    public void disableTerminated() {
        newrecord.setDisabled(true);
        btnadd.setDisabled(true);
        btndelete.setDisabled(true);
        btnrefresh.setDisabled(true);
        btnsave.setDisabled(true);
        btnactive.setDisabled(true);
        btnrevised.setDisabled(true);
        btnterminated.setDisabled(true);
        btnsup.setDisabled(true);
        btnsupsite.setDisabled(true);
        btnfwd.setDisabled(true);
//         txtquantity.setStyle("background-color:#FFFACD");
//        txtamount.setStyle("background-color:#FFFACD");

    }

    void colorMAndatory() {
        txtsuppliercode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtsupsitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtquantity.setStyle("background-color:#FFFACD;text-align:right");
        txtamount.setStyle("background-color:#FFFACD;text-align:right");
        txteffectiveDate.setStyle("background-color:#FFFACD");
        txtexpiredate.setStyle("background-color:#FFFACD");

        txtforwardcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtTOPcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
        txtdoc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.MANDATORY]);
    }

    void colorDefault() {
        txtsuppliercode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtsupsitecode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtquantity.setStyle("background-color:#ffffff;text-align:right");
        txtamount.setStyle("background-color:#ffffff;text-align:right");
        txteffectiveDate.setStyle("background-color:#ffffff");
        txtexpiredate.setStyle("background-color:#ffffff");
        txteffectiveDate.setWidth("210px");
        txtexpiredate.setWidth("210px");
        txtTOPcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtforwarddesc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtforwardcode.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
        txtdoc.setStyle(LISTBOX_SELECTION_COLORS[COLORS.DEFAULT]);
    }

    @Listen("onChange=#txtamount")
    public void onChangeAmount() {
        String Dates = "";
        String Date1 = "";
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Dates = dt.format(txteffectiveDate.getValue());
            Date1 = dt.format(txtexpiredate.getValue());
        } catch (Exception ex) {

        }
        if (txtflag.getText().equalsIgnoreCase("UPDATE") && !txtcontractid.getText().isEmpty() && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {
            Map<String, Object> validation = model.dopcValidasiHdr("SAVE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                    txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                    txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

            if (validation.get("OutError").equals(0)) {

                Messagebox.show("Are you sure want to update this Agreed Amount of contract?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event e) throws ParseException {
                                if (Messagebox.ON_OK.equals(e.getName())) {
                                    String Dates = "";
                                    String Date1 = "";
                                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                                    try {
                                        Dates = dt.format(txteffectiveDate.getValue());
                                        Date1 = dt.format(txtexpiredate.getValue());
                                    } catch (Exception ex) {

                                    }
                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("SAVE");
                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                                    if (oe.getOutError() == 0) {

                                        Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                                                txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(), txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                                                txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                                        if (map.get("outError").equals(0)) {
                                            Messagebox.show(map.get("outMessages").toString());
                                            selectlisthdr();
                                            autorefresh();
                                        } else {
                                            Messagebox.show(map.get("outMessages").toString());
                                        }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

                                }
                            }
                        });
            } else {
                Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } else {

        }

    }

    @Listen("onChange=#txtquantity")
    public void onChangeQty() {
        String Dates = "";
        String Date1 = "";
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Dates = dt.format(txteffectiveDate.getValue());
            Date1 = dt.format(txtexpiredate.getValue());
        } catch (Exception ex) {

        }
        if (txtflag.getText().equalsIgnoreCase("UPDATE") && !txtcontractid.getText().isEmpty() && txtstatdesc.getText().equalsIgnoreCase("DRAFT")) {

            Map<String, Object> validation = model.dopcValidasiHdr("SAVE", txtflag.getText(), txtcontractid.getText(), txtdesc.getText(),
                    txtsupplierid.getText(), txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(),
                    txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(), txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

            if (validation.get("OutError").equals(0)) {
                Messagebox.show("Are you sure want to update this Agreed Quantity of contract?",
                        "Question", Messagebox.OK | Messagebox.CANCEL,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event e) throws ParseException {
                                if (Messagebox.ON_OK.equals(e.getName())) {
                                    String Dates = "";
                                    String Date1 = "";
                                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                                    try {
                                        Dates = dt.format(txteffectiveDate.getValue());
                                        Date1 = dt.format(txtexpiredate.getValue());
                                    } catch (Exception ex) {

                                    }

                                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                                    ParamCekFunction cf = new ParamCekFunction();
                                    cf.setUserId(global[0]);
                                    cf.setResponsibilityId(global[2]);
                                    cf.setFileName("/Tcash/ListPurchaseContract.zul");
                                    cf.setFunctionName("SAVE");
                                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
                                    if (oe.getOutError() == 0) {

                                        Map<String, Object> map = model.doPcSaveHdr(txtflag.getText(), txtcontractid.getText(), txtdesc.getText(), txtsupplierid.getText(),
                                                txtsuppliercode.getText(), txtTOPid.getText(), txtcurrency.getText(), txtforwardcode.getText(), txtstat.getText(), userId, txtsupsitecode.getText(), txtsupsiteid.getText(),
                                                txtdoc.getText(), txtquantity.getValue().toString(), txtamount.getValue().toString(), Dates, Date1);

                                        if (map.get("outError").equals(0)) {
                                            Messagebox.show(map.get("outMessages").toString());
                                            selectlisthdr();
                                            autorefresh();

                                        } else {
                                            Messagebox.show(map.get("outMessages").toString());
                                        }
                                    } else {
                                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }

                                }
                            }
                        });
            } else {
                Messagebox.show(validation.get("OutMessages").toString(), "Purchase Contract", Messagebox.OK, Messagebox.EXCLAMATION);
            }

        } else {

        }
    }

}
