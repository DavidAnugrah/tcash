/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListOFParam;
import id.my.berkah.tcash1.model.ListOFDtlParam;
import id.my.berkah.tcash1.model.ListPairingDTLParam;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
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
 * @author arry
 */
public class PairingSNCTRL extends SelectorComposer<Component> {
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
            txtApproveBy,txtApproveDate,txtCancelBy,txtCancelDate;
    
    @Wire
    Window win_OF;
    
    @Wire
    Paging userPaging;
    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    private Media media = null;
    private String SAVE_PATH;
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        if(!txtIfId.getValue().equals("")){
            headerList();
            refresh();
        }
    }
    
    @Listen("onClick=#btnWo")
    public void lovPihak1() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,po_id as \"WO ID\",purchase_order as \"WORK ORDER NO\" from (select po_id,purchase_order from table(pkg_tcash_lov.LovWO('')))\n"
                + "where (upper(purchase_order) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovWO('')) where \n"
                + "(upper(purchase_order) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtWoId,txtWoNo});
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
    
    public void headerList(){
        List<ListOFParam> ListIFParam = model.selectListOFTrc(txtIfId.getValue());
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
        
        List<ListPairingDTLParam> ListPairingDTLParam = model.selectListPairingDtl(txtIfId.getValue(), "" + activePage, "" + pageSize);
        for (ListPairingDTLParam ListPairingDTLParam1 : ListPairingDTLParam) {
            Listcell pairing_dtl_id= new Listcell();
            Listcell pairing_ID= new Listcell();
            Listcell item_id= new Listcell();
            Listcell prod_issue_id= new Listcell();
            Listcell from_sn= new Listcell();
            Listcell to_sn= new Listcell();
            Listcell create_date= new Listcell();
            Listcell created_by= new Listcell();
            
            pairing_dtl_id.setLabel(ListPairingDTLParam1.getPairing_dtl_id());
            pairing_ID.setLabel(ListPairingDTLParam1.getPairing_ID());
            item_id.setLabel(ListPairingDTLParam1.getItem_id());
            prod_issue_id.setLabel(ListPairingDTLParam1.getProd_issue_id());
            from_sn.setLabel(ListPairingDTLParam1.getFrom_sn());
            to_sn.setLabel(ListPairingDTLParam1.getTo_sn());
            create_date.setLabel(ListPairingDTLParam1.getCreate_date());
            created_by.setLabel(ListPairingDTLParam1.getCreated_by());
            
            Listitem listitem = new Listitem();
            listitem.appendChild(pairing_dtl_id);
            listitem.appendChild(pairing_ID);
            listitem.appendChild(item_id);
            listitem.appendChild(prod_issue_id);
            listitem.appendChild(from_sn);
            listitem.appendChild(to_sn);
            listitem.appendChild(create_date);
            listitem.appendChild(created_by);
            
            listbox.appendChild(listitem);
        }
    }
    
    @Listen("onClick=#refresh")
    public void refresh() {
        headerList();

        List<Integer> jumlahRecord = model.getCountOFDtl(txtIfId.getValue());
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }

        userPaging.setPageSize(pageSize);
        userPaging.setTotalSize(JumlahRecord);
        userPaging.setActivePage(0);
        refreshDetail(1);
    }
    
    @Listen("onClick=#btnSave")
    public void saveTrc() {
        if (txtIfId.getValue() == "") {
            Map<String, Object> valIst = model.doOfValidateInsertHdr(txtWoId.getValue());
            if(valIst.get("outError").toString().equals("1")){
                Messagebox.show(valIst.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }else{
            Map<String, Object> valUp = model.doOfValidateUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
            if(valUp.get("outError").toString().equals("1")){
                Messagebox.show(valUp.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
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
//                        cf.setFileName("/Tcash/ListWorKOrder.zul");
//                        cf.setFunctionName("INSERT");
//
//                        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);
//
//                        if (oe.getOutError() == 0) {
//                        }
//                        else {
//                            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                        }
                            
                            
                            Map<String, Object> save = model.doOfInsertHdr(txtWoId.getValue(), userId);
                            if(save.get("outError").toString().equals("0")){
                                txtIfId.setValue(save.get("OutIfId").toString());
                                headerList();
                            }
                            Messagebox.show(save.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    }
                    else {
//                        ProcedureUtilImpl ci = new ProcedureUtilImpl();
//                        ParamCekFunction cf = new ParamCekFunction();
//
//                        cf.setUserId(global[0]);
//                        cf.setResponsibilityId(global[2]);
//                        cf.setFileName("/Tcash/ListWorKOrder.zul");
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
                        Map<String, Object> update = model.doOfUpdateHdr(txtIfId.getValue(), txtWoId.getValue(), txtFileName.getValue(), userId);
                        Messagebox.show(update.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                        headerList();
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "PAIRING SN", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#submit")
    public void submit(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doOfSubmitHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Submit?", "PAIRING SN", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#approve")
    public void approve(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doOfApproveHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Approve?", "PAIRING SN", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#cancel")
    public void cancel(){
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    Map<String, Object> update = model.doOfCancelHdr(txtIfId.getValue(), userId);
                    Messagebox.show(update.get("outMessages").toString(), "PAIRING SN : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    headerList();
                }
            }
        };
        Messagebox.show("Are you sure want to Cancel?", "PAIRING SN", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
    
    @Listen("onClick=#btnNew")
    public void btnNew() {
        
    }
    
    @Listen("onClick=#close")
    public void close(){
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
        media = evt.getMedia();
        txtFileName.setText(media.getName());
//        if (!txtFileName.getValue().contains(".txt")) {
//            Messagebox.show("File yang diupload harus file .txt!", "Mass Perso : Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
        int i = saveFile(media);

        if (i == 0) {
//            s
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
    
    
}
