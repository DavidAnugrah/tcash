/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListOFDtlParam;
import id.my.berkah.tcash1.model.ListOFParam;
import id.my.berkah.tcash1.model.OfSumParam;
import id.my.berkah.util.IDefines;
import static id.my.berkah.util.IDefines.LISTBOX_SELECTION_COLORS;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.Label;
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
 * @author arry
 */
public class OutputFileCTRL extends SelectorComposer<Component> {

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
    Window win_OF;

    @Wire
    Label txttotal, txttotal1;

    @Wire
    Paging userPaging;
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    ModelTcashCTLR model = new ModelTcashCTLR();

    private Media media = null;
    private String SAVE_PATH;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        setRender();
//        doGetOrderQty();
        if (!txtIfId.getValue().equals("")) {
            headerList();
            btnrefresh();
            colorMandatory();
            disabled();
        } else {
            uploadFile.setDisabled(true);
         txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        }

    }

    @Listen("onClick=#btnWo")
    public void lovPihak1() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"WO ID\",purchase_order as \"WORK ORDER NO\" from (select po_id,purchase_order from table(pkg_tcash_lov.LovWoOF('','"+userId+"','"+responsibilityId+"')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWoOF('','"+userId+"','"+responsibilityId+"')) where \n"
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
        w.setParent(win_OF);
        w.doModal();
    }

    public void headerList() {
        try {
            List<ListOFParam> ListIFParam = model.selectListOFTrc(txtIfId.getValue());
            txtIfNo.setValue(ListIFParam.get(0).getOf_no());
            txtWoId.setValue(ListIFParam.get(0).getPo_id());
            txtWoNo.setValue(ListIFParam.get(0).getPurchase_order());
            txtFileName.setValue(ListIFParam.get(0).getFilename());
            txtStatus.setValue(ListIFParam.get(0).getStatus_desc());
            txtCreatedBy.setValue(ListIFParam.get(0).getCreated_by());
            txtCreatedDate.setValue(ListIFParam.get(0).getCreate_date());
            txtReleaseBy.setValue(ListIFParam.get(0).getSubmited_by());
            txtReleaseDate.setValue(ListIFParam.get(0).getSubmit_date());
            txtApproveBy.setValue(ListIFParam.get(0).getApproved_by());
            txtApproveDate.setValue(ListIFParam.get(0).getApprove_date());
            txtCancelBy.setValue(ListIFParam.get(0).getCanceled_by());
            txtCancelDate.setValue(ListIFParam.get(0).getCancel_date());
        } catch (Exception e) {
        }
    }

    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refreshDetail(activePage);//, pageSize);
    }

    public void refreshDetail(int activePage) {
        listbox.getItems().clear();

        List<ListOFDtlParam> ListOFDtlParam = model.selectListOFDtl(txtIfId.getValue(), "" + activePage, "" + pageSize);
        for (ListOFDtlParam ListOFDtlParam1 : ListOFDtlParam) {
            Listcell of_dtl_id = new Listcell();
            Listcell of_id = new Listcell();
            Listcell of_no = new Listcell();
            Listcell sn_tcash = new Listcell();
            Listcell sn_inventory = new Listcell();
            Listcell uid_code = new Listcell();
            Listcell status = new Listcell();
            Listcell message = new Listcell();

            of_dtl_id.setLabel(ListOFDtlParam1.getOf_dtl_id());
            of_id.setLabel(ListOFDtlParam1.getOf_id());
            of_no.setLabel(ListOFDtlParam1.getOf_no());
            sn_tcash.setLabel(ListOFDtlParam1.getSn_tcash());
            sn_inventory.setLabel(ListOFDtlParam1.getSn_inventory());
            uid_code.setLabel(ListOFDtlParam1.getUid_code());
            status.setLabel(ListOFDtlParam1.getStatus());
            message.setLabel(ListOFDtlParam1.getMessage());

            Listitem listitem = new Listitem();
            listitem.appendChild(of_dtl_id);
            listitem.appendChild(of_id);
            listitem.appendChild(of_no);
            listitem.appendChild(sn_tcash);
            listitem.appendChild(sn_inventory);
            listitem.appendChild(uid_code);
            listitem.appendChild(status);
            listitem.appendChild(message);

            listbox.appendChild(listitem);
        }
    }

    @Listen("onClick=#refresh")
    public void btnrefresh() {
        headerList();
        List<OfSumParam> OfSumParam = model.getOfListSumDtl(txtIfId.getValue());
        listbox.setModel(new ListModelList<>(OfSumParam));
        txttotal.setValue(String.valueOf(OfSumParam.size()) == null || String.valueOf(OfSumParam.size()).trim().equals("") ? "0" : String.valueOf(OfSumParam.size()));
        doGetOrderQty();
        listbox.setSizedByContent(true);
    }

    void setRender() {
        listbox.getItems().clear();
        listbox.setItemRenderer(new ListitemRenderer<OfSumParam>() {
            @Override
            public void render(Listitem lstm, OfSumParam t, int i) throws Exception {
                new Listcell(t.getOf_id()).setParent(lstm);
                new Listcell(t.getFilename()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_name()).setParent(lstm);
                new Listcell(t.getLine_no()).setParent(lstm);
                new Listcell(t.getTotal_qty()).setParent(lstm);
                new Listcell(t.getSuccess_qty()).setParent(lstm);
                new Listcell(t.getFailed_qty()).setParent(lstm);
            }
        });
    }
    
    @Listen("onClick=#View")
    public void buttonView(){
        int index = listbox.getSelectedIndex();
        if (index >-1) {
            OfSumParam select = (OfSumParam) listbox.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("header", select);
            Window w = (Window) Executions.createComponents("/Tcash/ViewSNOF.zul",null,map);
            w.doModal();
        } else {
            Messagebox.show("No record selected","Output File",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#btnSave")
    public void saveTrc() {
//        if (txtStatus.getText().equalsIgnoreCase("Submitted")) {
//            Messagebox.show("This transaction status  has been submitted","Output File",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        } 
//        if (txtStatus.getText().equalsIgnoreCase("Approved")) {
//            Messagebox.show("This transaction status  has been approved","Output File",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        } 
//        if (txtStatus.getText().equalsIgnoreCase("Canceled")) {
//            Messagebox.show("This transaction status has been canceled","Output File",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//        } 
//        
        
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtIfId.getValue() == "") {
            Map<String, Object> valIst = model.doOfValidateInsertHdr(txtWoId.getValue());
            if (valIst.get("outError").toString().equals("1")) {
                Messagebox.show(valIst.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        } else {
            Map<String, Object> valUp = model.doOfValidateUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
            if (valUp.get("outError").toString().equals("1")) {
                Messagebox.show(valUp.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
                    
                    if (txtIfId.getValue() == "") {
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListOutputFile.zul");
                        cf.setFunctionName("SAVE");

                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            Map<String, Object> save = model.doOfInsertHdr(txtWoId.getValue(), userId);
                            if (save.get("outError").toString().equals("0")) {
                                txtIfId.setValue(save.get("OutIfId").toString());
                                headerList();
                                colorMandatory();
                                disabled(); 
                                doGetOrderQty();
                                uploadFile.setDisabled(false);
                            }
                            Messagebox.show(save.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }

                    } else {
                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
                        ParamCekFunction cf = new ParamCekFunction();

                        cf.setUserId(global[0]);
                        cf.setResponsibilityId(global[2]);
                        cf.setFileName("/Tcash/ListOutputFile.zul");
                        cf.setFunctionName("SAVE");

                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                        if (oe.getOutError() == 0) {
                            Map<String, Object> update = model.doOfUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
                            Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                            headerList();
                            disabled();
                            colorMandatory();
                            uploadFile.setDisabled(false);
                            btnrefresh();
                        } else {
                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        }

                    }
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
                    cf.setFileName("/Tcash/ListOutputFile.zul");
                    cf.setFunctionName("SUBMIT");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {
                        Map<String, Object> update = model.doOfSubmitHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                        disabled();
                        colorMandatory();
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
                    cf.setFileName("/Tcash/ListOutputFile.zul");
                    cf.setFunctionName("APPROVE");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {
                        Map<String, Object> update = model.doOfApproveHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                        btnrefresh();
                        disabled();
                        colorMandatory();
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
                    cf.setFileName("/Tcash/ListOutputFile.zul");
                    cf.setFunctionName("CANCEL");

                    ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

                    if (oe.getOutError() == 0) {
                        Map<String, Object> update = model.doOfCancelHdr(txtIfId.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.INFORMATION);
                        headerList();
                        disabled();
                        colorMandatory();
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
        txtIfId.setText("");
        txtIfNo.setText("");
        txtWoId.setText("");
        txtWoNo.setText("");
        txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
        txtFileName.setText("");
        txtStatus.setText("");
        txtCancelBy.setText("");
        txtCancelDate.setText("");
        txtCreatedBy.setText("");
        txtCreatedDate.setText("");
        txtApproveBy.setText("");
        txtApproveDate.setText("");
        txtReleaseBy.setText("");
        txtReleaseDate.setText("");
        txttotal.setValue("0");
        txttotal1.setValue("0");
        btnWo.setDisabled(false);
        uploadFile.setDisabled(true);
        listbox.getItems().clear();
    }

    @Listen("onClick=#close")
    public void close() {
        win_OF.detach();
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
//            InputStream fin = media.getStreamData();
//            in = new BufferedInputStream(fin);
//            File baseDir = new File(SAVE_PATH);
//
//            if (!baseDir.exists())
//            {
//                baseDir.mkdirs();
//            }

            File file = new File(SAVE_PATH + media.getName());
            OutputStream fout = new FileOutputStream(file);
            fout.write(media.getByteData());
            fout.close();
//            out = new BufferedOutputStream(fout);
//            byte buffer[] = new byte[1024];
//            int ch = in.read(buffer);
//            while (ch != -1)
//            {
//
//                out.write(buffer, 0, ch);
//                ch = in.read(buffer);
//            }

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
        }
        return result;
    }

    @Listen("onUpload=#uploadFile")
    public void upload(UploadEvent evt) throws IOException {
        if (txtStatus.getText().equals("APPROVED")) {
            uploadFile.setDisabled(true);
        }

        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();

        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListOutputFile.zul");
        cf.setFunctionName("UPLOAD");

        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            media = evt.getMedia();
            txtFileName.setText(media.getName());
//        if (!txtFileName.getValue().contains(".txt")) {
//            Messagebox.show("File yang diupload harus file .txt!", "Mass Perso : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }d
            int i = saveFile(media);

            if (i == 0) {

                listbox.getItems().clear();
                Map<String, Object> mapper = model.getOfClearDetail(txtIfId.getText(),txtFileName.getText());
                if (mapper.get("OutError").equals(0)) {                  
                    Map<String,Object>val = model.doOfValidateFileName(txtWoId.getText(), txtFileName.getText());
                if (val.get("OutError").equals(0)) {
               Map<String, Object> map = model.doOfUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
                } else {
                    Messagebox.show(val.get("OutMessages").toString(),"Output File",Messagebox.OK,Messagebox.EXCLAMATION);
                    return;
                }
                    OutputFileReadtxt of = new OutputFileReadtxt(txtIfId.getValue(), txtIfNo.getValue(), txtWoId.getValue(), SAVE_PATH + media.getName(),txtFileName.getText());
                    Thread t = new Thread(of);
                    t.start();
                    Messagebox.show("The upload process is running in the background, "
                            + "click the refresh button to see the status of the running process.", "Output File  : Message", Messagebox.OK, Messagebox.INFORMATION);

                }else {
                    Messagebox.show(mapper.get("OutMessages").toString(),"Output File",Messagebox.OK,Messagebox.EXCLAMATION);
                    
                }

            }
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
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

    void colorMandatory() {
        txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
    }

    void disabled() {

        if (txtStatus.getText().equalsIgnoreCase("DRAFT")) {
            txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
             txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.MANDATORY]);
            btnWo.setDisabled(true);
        } else if (txtStatus.getText().equalsIgnoreCase("SUBMITTED")) {
            btnWo.setDisabled(true);
            txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
             txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            uploadFile.setDisabled(true);
        } else if (txtStatus.getText().equalsIgnoreCase("APPROVED")) {
            btnWo.setDisabled(true);
            uploadFile.setDisabled(true);
            txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
             txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
        } else if (txtStatus.getText().equalsIgnoreCase("CANCELED")) {
            btnWo.setDisabled(true);
            txtWoNo.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
             txtFileName.setStyle(LISTBOX_SELECTION_COLORS[IDefines.COLORS.DEFAULT]);
            uploadFile.setDisabled(true);
        } else {
            cancel.setDisabled(false);
        }

    }

//    
    public void doGetOrderQty() {
        Map<String, Object> map = model.doGetOrderQty(txtWoId.getText());
        if (txtWoId.getText().isEmpty()) {
            txttotal1.setValue("0");
        } else {
            txttotal1.setValue(String.valueOf(map.get("OutOrderQty")));
        }

    }

}
