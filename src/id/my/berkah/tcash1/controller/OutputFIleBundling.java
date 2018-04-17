/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.BdlListDtlParam;
import id.my.berkah.tcash1.model.BdlListHdrParam;
import id.my.berkah.tcash1.model.ListOFParam;
import id.my.berkah.util.IDefines;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
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
 * @author Azec
 */
public class OutputFIleBundling extends SelectorComposer<Component> {

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
            txtApproveBy, txtApproveDate, txtCancelBy, txtCancelDate;

    @Wire
    Button uploadFile, btnWo, btnSave, submit, approve, cancel;

    @Wire
    Window win_OF_bundling;

    ModelTcashCTLR model = new ModelTcashCTLR();
    ListOfBundling parentController;

    private Media media = null;
    private String SAVE_PATH;

    @Listen("onCreate=#win_OF_bundling")
    public void oncReateWindow() {
        parentController = (ListOfBundling) win_OF_bundling.getAttribute("parentController");
        if (!txtIfId.getValue().equals("")) {
            headerList();
            refresh();
            setRender();
            colorMandatory();
            disabled();
        } else {
            uploadFile.setDisabled(true);
        }
    }

    @Listen("onClick=#btnNew")
    public void btnNew() {
        txtIfId.setText("");
        txtIfNo.setText("");
        txtWoId.setText("");
        txtWoNo.setText("");
        txtFileName.setText("");
        txtStatus.setText("");
        txtCreatedBy.setText(null);
        txtCreatedDate.setText(null);
        txtApproveBy.setText(null);
        txtApproveDate.setText(null);
        txtCancelBy.setText(null);
        txtCancelDate.setText(null);
        
        listbox.getItems().clear();
        enableButton();
    }

    void colorMandatory() {
        txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
    }

    @Listen("onClick=#close")
    public void close() {
        win_OF_bundling.detach();
    }

    void disabled() {
        btnWo.setDisabled(true);
        btnSave.setDisabled(true);
        submit.setDisabled(true);
        approve.setDisabled(true);
        if (txtStatus.getText().equalsIgnoreCase("DRAFT")) {
            btnWo.setDisabled(false);
            btnSave.setDisabled(false);
            submit.setDisabled(false);
            approve.setDisabled(false);
        } else if (txtStatus.getText().equalsIgnoreCase("SUBMIT")) {
            btnWo.setDisabled(false);
            btnSave.setDisabled(false);
            submit.setDisabled(true);
            approve.setDisabled(false);
            uploadFile.setDisabled(true);
            btnWo.setDisabled(true);
        } else if (txtStatus.getText().equalsIgnoreCase("APPROVED")) {
            cancel.setDisabled(true);
        } else if (txtStatus.getText().equalsIgnoreCase("CANCELED")) {
            btnWo.setDisabled(true);
            btnSave.setDisabled(true);
            submit.setDisabled(true);
            approve.setDisabled(true);
            cancel.setDisabled(true);
               uploadFile.setDisabled(false);
        } else {
            cancel.setDisabled(false);
        }

    }
    
    void enableButton(){
        btnWo.setDisabled(false);
        btnSave.setDisabled(false);
        submit.setDisabled(false);
        approve.setDisabled(false);
        uploadFile.setDisabled(false);
        cancel.setDisabled(false);
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

            File file = new File(SAVE_PATH + media.getName());
            OutputStream fout = new FileOutputStream(file);
            fout.write(media.getByteData());
            fout.close();

            result = 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            parentController.refreshALL();

        }
        return result;
    }

    @Listen("onClick=#cancel")
    public void cancel() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doOfCancelHdrbdl(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                    headerList();
                    disabled();
                    colorMandatory();
                    parentController.refreshALL();
                }
            }
        };
        Messagebox.show("Are you sure want to Cancel?", "Output File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    public void validateInsertHDR() {
        if (txtIfId.getText().equals("")) {
            Map<String, Object> valIst = model.doOfValidateInsertHdrbdl(txtWoId.getValue());
            if (valIst.get("outError").equals("0")) {

            } else {
                Messagebox.show(valIst.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
    }

    public void validateUpdatehdr() {
        if (txtIfId.getText() != null) {
            Map<String, Object> valUp = model.doOfValidateUpdateHdrbdl(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
            if (valUp.get("outError").toString().equals("0")) {

            } else {
                Messagebox.show(valUp.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;

            }
        }
    }

    @Listen("onClick=#btnSave")
    public void saveOfBundling() {
        if (txtIfId.getText().equals("")) {
            Map<String, Object> save = model.doOfInsertHdrbdl(txtWoId.getValue(), userId);
            if (save.get("outError").toString().equals("0")) {
                txtIfId.setValue(save.get("OutIfId").toString());
                headerList();
                colorMandatory();
                disabled();
                uploadFile.setDisabled(false);
            } else {
                Messagebox.show(save.get("outMessages").toString());
            }

        } else {
            Map<String, Object> update = model.doOfUpdateHdrbdl(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
            Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
            headerList();
            disabled();
            colorMandatory();
            uploadFile.setDisabled(false);
        }
        parentController.refreshALL();
    }

    @Listen("onClick=#submit")
    public void submit() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doOfSubmitHdrbdl(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                    disabled();
                    colorMandatory();
                    parentController.refreshALL();
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
                    Map<String, Object> update = model.doOfApproveHdrbdl(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                    disabled();
                    colorMandatory();
                    parentController.refreshALL();
                }
            }

        };
        Messagebox.show("Are you sure want to Approve?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    private void headerList() {
        if (txtIfId.getText() != null) {
            List<BdlListHdrParam> ListIFParam = model.getBdlListHdr(txtIfId.getValue());
            txtIfNo.setValue(ListIFParam.get(0).getOf_no());
            txtWoId.setValue(ListIFParam.get(0).getPo_id());
            txtWoNo.setValue(ListIFParam.get(0).getPurchase_order());
            txtFileName.setValue(ListIFParam.get(0).getFilename());
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

    @Listen("onClick=#refresh")
    public void refresh() {
        List<BdlListDtlParam> param = model.BdlListDtl(txtIfId.getValue());
        listbox.setModel(new ListModelList<BdlListDtlParam>(param));
    }

    void setRender() {
        listbox.setItemRenderer(new ListitemRenderer<BdlListDtlParam>() {

            @Override
            public void render(Listitem lstm, BdlListDtlParam t, int i) throws Exception {
                new Listcell(t.getOf_dtl_id()).setParent(lstm);
                new Listcell(t.getOf_id()).setParent(lstm);
                new Listcell(t.getOf_no()).setParent(lstm);
                new Listcell(t.getSn_tcash()).setParent(lstm);
                new Listcell(t.getSn_inventory()).setParent(lstm);
                new Listcell(t.getUid_code()).setParent(lstm);
                new Listcell(t.getStatus()).setParent(lstm);
                new Listcell(t.getMessage()).setParent(lstm);
//                    lstm.addEventListener("onDoubleClick", new EventListener() {
////
////                        @Override
////                        public void onEvent(Event t) throws Exception {
////                            edit();
////                        }
//
//                      
//                    });
            }
        });
    }

    @Listen("onClick=#btnWo")
    public void lovWoOF() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"WO ID\",purchase_order as \"WORK ORDER NO\" from (select po_id,purchase_order from table(pkg_tcash_bdl.LovWoOF('')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bdl.LovWoOF('')) where \n"
                + "(upper(purchase_order) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtWoId, txtWoNo});
        composerLov.setHiddenColumn(new int[]{1});

        composerLov.setTitle("List Of PIC");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_OF_bundling);
        w.doModal();
    }

    @Listen("onUpload=#uploadFile")
    public void upload(UploadEvent evt) throws IOException {
        media = evt.getMedia();
        txtFileName.setText(media.getName());
        int i = saveFile(media);

        if (i == 0) {
            listbox.getItems().clear();
            Map<String, Object> map = model.doOfUpdateHdrbdl(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);

            OutputReaderBDL of = new OutputReaderBDL(txtIfId.getValue(), txtIfNo.getValue(), txtWoId.getValue(), SAVE_PATH + media.getName());
            Thread t = new Thread(of);
            t.start();
            Messagebox.show("Proses upload sedang di jalankan di background, "
                    + "klik refresh untuk mengetahui status proses berjalan.", "OF  : Message", Messagebox.OK, Messagebox.INFORMATION);
            headerList();
            refresh();
        }
    }
}
