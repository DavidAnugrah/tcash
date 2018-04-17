/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGenIFParam;
import id.my.berkah.tcash1.model.ListIFBndParam;
import id.my.berkah.tcash1.model.ListIFParamTrc;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class IFBundlingTcash extends SelectorComposer<Component> {
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String poId="";
    String woNo="";
    String FILENAME="";
    @Wire
    Listbox listbox;
    
    @Wire
    Textbox txtIfNo,txtIfId,txtWoId,txtWoNo,txtFileName,txtStatus,txtCreatedBy,txtCreatedDate,txtReleaseBy,txtReleaseDate,
            txtApproveBy,txtApproveDate,txtCancelBy,txtCancelDate,txtWoloopId,txtWoloopNo;
    
    @Wire
    Window win_if_bundling;
    
    @Wire
            Button btnWo,submit,approve,download,btnSave,cancel,btnNew;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (txtStatus.getText().equals("SUBMMITED")||txtStatus.getText().equals("APPROVED")) {
            validateButton();
        } 
        
        System.out.println("idnyahh"+txtIfId.getValue());
        if(!txtIfId.getValue().equals("")){
            headerList();
        }
    }
    
    @Listen("onClick=#btnWo")
    public void lovPihak1() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"WO ID\",purchase_order as \"WORK ORDER NO\" from (select po_id,purchase_order from table(pkg_tcash_lov.LovWOBundling('')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWOBundling('')) where \n"
                + "(upper(purchase_order) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtWoId,txtWoNo});
        composerLov.setHiddenColumn(new int[]{1});

        composerLov.setTitle("List Of Wo");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_if_bundling);
        w.doModal();
    }
    
    @Listen("onClick=#btnWoloop")
    public void lovLoop() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"WO ID\",purchase_order as \"WORK ORDER NO\" from (select po_id,purchase_order from table(pkg_tcash_lov.LovWoLoop('')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWoLoop('')) where \n"
                + "(upper(purchase_order) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtWoloopId,txtWoloopNo});
        composerLov.setHiddenColumn(new int[]{1});

        composerLov.setTitle("List Of Wo Loop");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_if_bundling);
        w.doModal();
    }
    
    public void headerList(){
        List<ListIFBndParam> ListIFParam = model.selectListIFTrcBnd(txtIfId.getValue());
        txtIfNo.setValue(ListIFParam.get(0).getIf_no());
        txtWoId.setValue(ListIFParam.get(0).getPo_id());
        txtWoNo.setValue(ListIFParam.get(0).getPurchase_order());
        txtWoloopNo.setValue(ListIFParam.get(0).getPurchase_order_loop());
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
    
    @Listen("onClick=#btnSave")
    public void saveTrc() {
        if (txtIfId.getValue() == "") {
            Map<String, Object> valIst = model.doIfValidateInsertHdr(txtWoId.getValue());
            if(valIst.get("outError").toString().equals("1")){
                Messagebox.show(valIst.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }else{
            Map<String, Object> valUp = model.doIfValidateUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
            if(valUp.get("outError").toString().equals("1")){
                Messagebox.show(valUp.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtIfId.getValue() == "") {
//                        Calendar cal = Calendar.getInstance();
//                        cal.add(Calendar.DATE, 0);
//                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
//                        ParamCekFunction cf = new ParamCekFunction();
//
//                        cf.setUserId(global[0]);
//                        cf.setResponsibilityId(global[2]);
//                        cf.setFileName("/tcash/ListWorKOrder.zul");
//                        cf.setFunctionName("INSERT");
//
//                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
//
//                        if (oe.getOutError() == 0) {
//                        }
//                        else {
//                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                        }
                            
                            
                            Map<String, Object> save = model.doIfInsertBndHdr(txtWoId.getValue(), userId);
                            if(save.get("outError").toString().equals("0")){
                                txtIfId.setValue(save.get("OutIfId").toString());
                                headerList();
                            }
                            Messagebox.show(save.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                    else {
//                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
//                        ParamCekFunction cf = new ParamCekFunction();
//
//                        cf.setUserId(global[0]);
//                        cf.setResponsibilityId(global[2]);
//                        cf.setFileName("/tcash/ListWorKOrder.zul");
//                        cf.setFunctionName("UPDATE");
//
//                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
//
//                        if (oe.getOutError() == 0) {
//                        }
//                        else {
//                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                        }
//                        String status="";
//                            if(){
//                                
//                            }
                        Map<String, Object> update = model.doIfUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        headerList();
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#submit")
    public void submit(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doIfSubmitHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Submit?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#approve")
    public void approve(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doIfApproveHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Approve?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#cancel")
    public void cancel(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doIfCancelHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Cancel?", "Input File", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#btnNew")
    public void btnNew() {
        
    }
    
    @Listen("onClick=#close")
    public void close(){
        win_if_bundling.detach();
    }
    
    @Listen("onClick=#download")
    public void generate() throws IOException {
        InputFileCTRL inp = new InputFileCTRL();
        inp.generateFile();
    }
    
    public void generateFile() throws IOException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String download_directory = properties.getProperty("download_directory");
        
        Path parent1 = new Path("/win_if_bundling");
        Textbox txtFileName1 = (Textbox) new Path(parent1, "txtFileName").getComponent();
        Textbox txtWoId1 = (Textbox) new Path(parent1, "txtWoId").getComponent();
        Textbox txtIfId1 = (Textbox) new Path(parent1, "txtIfId").getComponent();

        try {
            
            String content = "";
            String poLine = "";
            FILENAME = download_directory+txtFileName1.getValue();
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            List<ListGenIFParam> ListGenIFParam = model.selectGenFile(txtWoId1.getValue(),poLine);
            int i = 0;
            
            for(ListGenIFParam ListGenIFParam1 :ListGenIFParam){
                if (i != 0){
                    bw.newLine();
                }
                System.out.println("hasilnya"+ListGenIFParam1.getSn_tcash()+";"+ListGenIFParam1.getSn_inv());
                content = ListGenIFParam1.getSn_tcash()+"|"+ListGenIFParam1.getSn_inv();
                
                bw.write(content);
                i++;
            }
            System.out.println("Done");
                         
             Map<String,Object>map=model.doIfUpdateStatus(txtIfId1.getText(),"4" , userId);
        if (map.get("OutError").equals(0)) {
             Messagebox.show(map.get("OutMessages").toString(), "Input File : Message", Messagebox.OK, Messagebox.EXCLAMATION);
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
                Filedownload.save(new File(download_directory+txtFileName1.getValue()),null);
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
    
    
//    public void updatestatus(){
//        String status = "4";
//       
//    }
    
    void validateButton(){
       btnWo.setDisabled(true);
       btnSave.setDisabled(true);
       submit.setDisabled(true);
       approve.setDisabled(true);
       cancel.setDisabled(true);
       download.setDisabled(true);
    }
    
}
