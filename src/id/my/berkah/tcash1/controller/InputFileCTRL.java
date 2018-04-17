/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.IfListDtlParam;
import id.my.berkah.tcash1.model.ListGenIFParam;
import id.my.berkah.tcash1.model.ListIFParam;
import id.my.berkah.util.IDefines;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class InputFileCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String poId = "";
    String woNo = "";
    String FILENAME = "";
    @Wire
    Listbox listbox;

    @Wire
    Textbox txtIfNo, txtIfId, txtWoId, txtWoNo, txtFileName, txtStatus, txtCreatedBy, txtCreatedDate, txtReleaseBy, txtReleaseDate,
            txtApproveBy, txtApproveDate, txtCancelBy, txtCancelDate,poLine;

    @Wire
    Window win_if;

    @Wire
    Button btnWo, submit, approve, download, btnSave, cancel, btnNew;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#win_if")
    public void onCreatewindow() {
        validateColors();
        setRender();
        if (!txtIfId.getValue().equals("")) {
            headerList();
            btnWo.setDisabled(true);
        }
        refresh();
    }

    @Listen("onClick=#btnWo")
    public void lovPihak1() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"Wo Id\",purchase_order as \"Work Order No\" from (select po_id,purchase_order from table(pkg_tcash_lov.LovWO('','"+userId+"','"+responsibilityId+"')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWO('','"+userId+"','"+responsibilityId+"')) where \n"
                + "(upper(purchase_order) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtWoId, txtWoNo});
        composerLov.setHiddenColumn(new int[]{1});
    
        composerLov.setTitle("List Of Work Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_if);
        w.doModal();
    }

    public void headerList() {
        if (!txtIfId.getText().isEmpty()) {
            List<ListIFParam> ListIFParam = model.selectListIF(txtIfId.getText());
            txtIfNo.setValue(ListIFParam.get(0).getIf_no());
            txtWoId.setValue(ListIFParam.get(0).getPo_id());
            txtWoNo.setValue(ListIFParam.get(0).getPurchase_order());
//            txtFileName.setValue(ListIFParam.get(0).getFilename());
            txtStatus.setValue(ListIFParam.get(0).getStatus());
            txtCreatedBy.setValue(ListIFParam.get(0).getCreated_by());
            txtCreatedDate.setValue(ListIFParam.get(0).getCreate_date());
            txtReleaseBy.setValue(ListIFParam.get(0).getSubmited_by());
            txtReleaseDate.setValue(ListIFParam.get(0).getSubmit_date());
            txtApproveBy.setValue(ListIFParam.get(0).getApproved_by());
            txtApproveDate.setValue(ListIFParam.get(0).getApprove_date());
            txtCancelBy.setValue(ListIFParam.get(0).getCanceled_by());
            txtCancelDate.setValue(ListIFParam.get(0).getCancel_date());
        }

    }

    @Listen("onClick=#btnSave")
    public void saveTrc() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtIfId.getValue().equals("")) {
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListInputFile.zul");
                        cf.setFunctionName("SAVE");

                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            Map<String, Object> valIst = model.doIfValidateInsertHdr(txtWoId.getValue());

                            if (valIst.get("outError").toString().equals("1")) {
                                Messagebox.show(valIst.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                return;
                            }

                            Map<String, Object> save = model.doIfInsertHdr(txtWoId.getValue(), userId);
                            if (save.get("outError").toString().equals("0")) {
                                txtIfId.setValue(save.get("OutIfId").toString());
                                refresh();
                            } else {
                                Messagebox.show(save.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                            }
                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }

                    } else {
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListInputFile.zul");
                        cf.setFunctionName("SAVE");

                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            Map<String, Object> valUp = model.doIfValidateUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
                            if (valUp.get("outError").toString().equals("1")) {
                                Messagebox.show(valUp.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                return;
                            }

                            Map<String, Object> update = model.doIfUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);

                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }
                    }
                    headerList();
                    btnWo.setDisabled(true);
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#submit")
    public void submit() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                    ParamCekFunction cf = new ParamCekFunction();

                    cf.setUserId(global[0]);
                    cf.setResponsibilityId(global[2]);
                    cf.setFileName("/Tcash/ListInputFile.zul");
                    cf.setFunctionName("SUBMIT");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {

                        Map<String, Object> update = model.doIfSubmitHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                        validateColors();
                        btnWo.setDisabled(true);

                    } else {
                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Submit?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#approve")
    public void approve() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {

                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                    ParamCekFunction cf = new ParamCekFunction();

                    cf.setUserId(global[0]);
                    cf.setResponsibilityId(global[2]);
                    cf.setFileName("/Tcash/ListInputFile.zul");
                    cf.setFunctionName("APPROVE");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {

                        Map<String, Object> update = model.doIfApproveHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                        btnWo.setDisabled(true);
                    } else {
                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Approve?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#cancel")
    public void cancel() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    ProcedureUtilImpl ci = new ProcedureUtilImpl();
                    ParamCekFunction cf = new ParamCekFunction();

                    cf.setUserId(global[0]);
                    cf.setResponsibilityId(global[2]);
                    cf.setFileName("/Tcash/ListInputFile.zul");
                    cf.setFunctionName("CANCEL");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {
                        Map<String, Object> update = model.doIfCancelHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                    } else {
                        Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Cancel?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    @Listen("onClick=#btnNew")
    public void btnNew() {
        txtIfNo.setText(null);
        txtIfId.setText(null);
        txtWoNo.setText(null);
        txtWoNo.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtWoId.setText(null);
        txtFileName.setText(null);
        txtStatus.setText(null);
        txtCreatedBy.setText(null);
        txtCreatedDate.setText(null);
        txtCancelBy.setText(null);
        txtCancelDate.setText(null);
        txtApproveBy.setText(null);
        txtApproveDate.setText(null);
        txtReleaseBy.setText(null);
        txtReleaseDate.setText(null);
        btnWo.setDisabled(false);
        listbox.getItems().clear();
        validateColors();
    }

    @Listen("onClick=#close")
    public void close() {
        if (txtIfId.getText().isEmpty()) {
            win_if.detach();
          return;
        }
        if (txtStatus.getText().equalsIgnoreCase("Draft") || txtStatus.getText().equals("")) {
            Messagebox.show("Are you sure want to close?\nData has not been Submitted yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                win_if.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("submitted")) {
            Messagebox.show("Are you sure want to close?\nData has not been approved yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                win_if.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );
        } else if (txtStatus.getText().equalsIgnoreCase("APPROVED")) {
            Messagebox.show("Are you sure want to close?\nData has not been Generate IF yet",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event e) {
                            if (Messagebox.ON_OK.equals(e.getName())) {
                                win_if.detach();
                            } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                            }
                        }
                    }
            );

        } else if (txtStatus.getText().equalsIgnoreCase("INPUT FILE COMPLETE")) {
            win_if.detach();
        } else {
            win_if.detach();
        }
    }

    @Listen("onClick=#download")
    public void generate() throws IOException {
        if (txtStatus.getText().equals("")) {
            Messagebox.show("Please save this transaction first", "Input File", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (txtStatus.getText().equalsIgnoreCase("draft")) {
            Messagebox.show("This transaction status is still draft", "Input File ", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        } else if (txtStatus.getText().equalsIgnoreCase("submitted")) {
            Messagebox.show("This transaction status is still Submitted", "Input File ", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        } else if (txtStatus.getText().equalsIgnoreCase("Canceled")) {
            Messagebox.show("This transaction status has been canceled", "Input File ", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        if (!txtFileName.getText().isEmpty()) {
       
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListInputFile.zul");
        cf.setFunctionName("GENERATE_IF");

        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            InputFileCTRL inp = new InputFileCTRL();
            inp.generateFile();
            headerList();
            btnWo.setDisabled(true);
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
         } else {
            Messagebox.show("Please choose detail first","Input File",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }

    public void generateFile() throws IOException {
        BufferedWriter bw = null;
        FileWriter fw = null;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String download_directory = properties.getProperty("download_directory");

        Path parent1 = new Path("/win_if");
        Textbox txtFileName1 = (Textbox) new Path(parent1, "txtFileName").getComponent();
        Textbox txtWoId1 = (Textbox) new Path(parent1, "txtWoId").getComponent();
        Textbox txtIfId1 = (Textbox) new Path(parent1, "txtIfId").getComponent();
        Textbox txtpoLine = (Textbox) new Path(parent1, "poLine").getComponent();
        Textbox txtwo = (Textbox) new Path(parent1, "txtWoNo").getComponent();

        try {

            String content = "";
            FILENAME = txtFileName1.getValue();
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            List<ListGenIFParam> ListGenIFParam = model.selectGenFile(txtWoId1.getValue(),txtpoLine.getText());
            int i = 0;

            for (ListGenIFParam ListGenIFParam1 : ListGenIFParam) {
                if (i != 0) {
                    bw.newLine();
                }
                content = ListGenIFParam1.getSn_tcash() + " " + ListGenIFParam1.getSn_inv();

                bw.write(content);

                i++;
            }
            
            Map<String, Object> map = model.doIfUpdateStatus(txtIfId1.getText(), "4", userId);
            if (map.get("OutError").equals(0)) {
                Messagebox.show(map.get("OutMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
            } else {
                Messagebox.show(map.get("OutMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }
                Filedownload.save(new File(txtFileName1.getValue()), null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }

    public void validateColors() {
        if (txtStatus.getText().equals("") || txtStatus.getText().equalsIgnoreCase("draft")) {
            txtWoNo.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
            btnWo.setDisabled(false);
        } else {
            txtWoNo.setStyle(IDefines.LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            btnWo.setDisabled(true);
        }

    }
    
    
    public void refresh(){
        List<IfListDtlParam>list= model.getIfListDtl(txtWoId.getText(),userId,responsibilityId);
        listbox.setModel(new ListModelList<>(list));
    }
    
    public void setRender(){
        listbox.setItemRenderer(new ListitemRenderer<IfListDtlParam>() {

            @Override
            public void render(Listitem lstm, IfListDtlParam t, int i) throws Exception {
                new Listcell(t.getPo_line()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_desc()).setParent(lstm);
                new Listcell(t.getOrdered_quantity()).setParent(lstm);
                new Listcell(t.getFile_name()).setParent(lstm);
                
            }
        });
    }
    
    @Listen("onSelect=#listbox")
    public void onSelectlistbox(){
        int index=listbox.getSelectedIndex();
         IfListDtlParam selected = (IfListDtlParam) listbox.getModel().getElementAt(index);
         poLine.setText(selected.getPo_line());
         txtFileName.setText(selected.getFile_name());
    }
}
