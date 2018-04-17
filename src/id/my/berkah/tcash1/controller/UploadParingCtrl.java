/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoPairing;
import id.my.berkah.tcash1.model.wolistHDRPAram;
import id.my.berkah.util.ParameterGlobal;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jxl.read.biff.BiffException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class UploadParingCtrl extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
//    private Media media = null;
    private String SAVE_PATH;
    private String message;
    String poId;
    String poLine;
    String supId;
    String supCode;
    String itemId;
    String count;
    String uploadId;
    String uploadDtlId;
    String fileName;
    String outpath;
    String outFile;
    String sn;
    String batch = "";

    Desktop _desktop;

    @Wire
    Textbox txtUpload, txtPoId, txtSupId, txtSupCode, txtItemId, txtPoLine, txtCount, txtuploadId, txtuploadDtlId, txtoutpat, txtoutfile;
    @Wire
    Intbox txtQty;
    @Wire
    Window win_upload;
    @Wire
    Button uploadFile, genSn;
    @Wire
    Label txttotal, Labeltotal;

    @Wire
    Listbox listbox;
    WorkOrderCTRL parentControler;
    ModelTcashCTLR model = new ModelTcashCTLR();
    List<ListWoPairing> list;

//    @Override
//    public void doAfterCompose(Component comp) throws Exception {
//        super.doAfterCompose(comp);
    @Listen("onCreate=#win_upload")
    public void onCreateWindow() {
        parentControler = (WorkOrderCTRL) win_upload.getAttribute("parentControler");
        uploadId = txtuploadId.getValue();
        uploadDtlId = txtuploadDtlId.getValue();

        fileName = txtUpload.getValue();
        poId = txtPoId.getValue();
        poLine = txtPoLine.getValue();
        supId = txtSupId.getValue();
        supCode = txtSupCode.getValue();
        itemId = txtItemId.getValue();
        setrender();
        refresh();
        refreshHDR();
        validatebuttonGen();
//        if (parentControler.txtStatus.getText().equalsIgnoreCase("Upload In Progress")) {
//            genSn.setDisabled(false);
//        } else {
//            genSn.setDisabled(true);
//        }

    }

    @Listen("onClick=#btnClose")
    public void btnClose() throws ParseException {
        win_upload.detach();
//        parentControler.updateStatus();
//        parentControler.doWoCreateRangeSNInv();
    }

    private int saveFile(Media media) throws IOException {
        int result = 1;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        SAVE_PATH = properties.getProperty("upload_directory_WOTCASH");

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

    @Listen("onUpload=#uploadFile")
    public void upload(UploadEvent evt) throws IOException, ParseException, BiffException {
//        org.zkoss.util.media.Media media = Fileupload.get();
//              txtUpload.setText("");
//              txtuploadId.setText("");
        Media media = evt.getMedia();
//        media.getStreamData();
        File file = new File(media.getName());
        txtUpload.setText(file.getAbsolutePath());
//        System.out.println(media + " mediaa");s

        if (txtUpload.getText().contains(".txt")) {
            Messagebox.show("Upload file just allowed extension file .xls", "Upload Pairing", Messagebox.OK, Messagebox.EXCLAMATION);
            return;

        }
//        if (txtUpload.getText().contains(".csv")) {
//            Messagebox.show("Upload file just allowed excel file .xls","Upload Pairing",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//            
//        }
        if (txtUpload.getText().contains(".xlsx")) {
            Messagebox.show("Upload file just allowed extension file .xls", "Upload Pairing", Messagebox.OK, Messagebox.EXCLAMATION);
            return;

        }
//        if (txtUpload.getText().contains(".xls")) {
//            Messagebox.show("Upload file just allowed .xls format","Upload Pairing",Messagebox.OK,Messagebox.EXCLAMATION);
//            return;
//            
//        }

//        Media media = evt.getName();
//        txtUpload.setText(media.getName());
//        String content = null;
//        if (media.isBinary()) {
//         content = new String(media.getByteData());
//            } else {
//         content = media.getStringData();
//            }
//            System.out.println(content);
//        String[] split = content.split("\n");
//        System.out.println(split.length);
//        Workbook workbook = Workbook.getWorkbook(media.getStreamData());
//            workbook.
//            Sheet sheet = workbook.getSheet(0);  //sesuaikan sheet mana yang mau di ambil. 
//        for (int row=0; row < sheet.getRows();row++){
//      try{
//            Cell[] cellColumn = sheet.getRow(row);
//            int cellCount = cellColumn.length;
//                    txtoutpat.setText(String.valueOf(cellCount));
//                    System.out.println(cellCount+" cell count");
//              }catch(Exception e){
//                 e.printStackTrace();
//              }
//      System.out.println(row+ " row");
//      System.out.println(sheet+" sheet");
//      System.out.println(workbook+" workbook");
////  }
//        alert("The full path is: "+myFile.getAbsolutePath());
//        
//        if (txtUpload.getText().contains(".txt")) {
//            String replace = media.getStringData().;
//        } 
//        else if (txtUpload.getText().contains(".csv")) {
//            String replace = txtUpload.getText().replace(".csv", "xls");
//        } 
        int i = saveFile(media);
//        
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//
//        txtoutpat.setText("c:\\temp\\");
//        txtoutfile.setText(media.getName());
//        
//        Map<String,Object>map =model.doGetPathFile(txtoutpat.getText()+txtoutfile.getText());
//        if (map.get("OutError").equals(0)) {
//            txtoutpat.setText(map.get("OutPath").toString());
//            txtoutfile.setText(map.get("OutFile").toString());
////            try {
////                String v;
////v ="if WScript.Arguments.Count < 2 Then \n" + 
////"WScript.Echo \"Error! Please specify the source path and the destination. Usage: XlsToCsv SourcePath.xls Destination.csv\"\n" +
////"Wscript.Quit\n " +
////"End If \n" +
////"Dim oExcel \n" +
////"Set oExcel = CreateObject(\"Excel.Application\")\n " +
////"Dim oBook \n" +
////"Set oBook = oExcel.Workbooks.Open(Wscript.Arguments.Item(0))\n" +
////"oBook.SaveAs WScript.Arguments.Item(1), 6\n" +
////"oBook.Close False \n" +
////"oExcel.Quit \n " +
////"WScript.Echo \"Done\"";
//
//
////                fw =  new FileWriter("XlsToCsv.vbs");
////                bw = new BufferedWriter(fw);
////                bw.write(v);
//              
////            } catch (Exception e) {
////                 e.printStackTrace();
////            } finally{
////                 try {
////
////                if (bw != null) {
////                    bw.close();
////                }
////
////                if (fw != null) {
////                    fw.close();
////                }
////               
////            } catch (IOException ex) {
////                ex.printStackTrace();
////            }
////            }
//            try {
//                fw = new FileWriter(txtoutpat.getText()+"bacth.bat");
//                bw = new BufferedWriter(fw);
//               // bw.write("XlsToCsv.vbs "+ txtoutpat.getText() + txtoutfile.getText() + " "+ txtoutpat.getText() + txtoutfile.getText().replace(".xls",".csv")+ "\n"); ;
//              //  bw.write("pause \n"); ;
//                bw.write("type " + txtoutpat.getText() + txtoutfile.getText() + " find /v /c \"\" " + txtoutpat.getText() + txtoutfile.getText()+ " > " + txtoutpat.getText() +"CountRec.txt") ;
//                Runtime.getRuntime().exec("cmd /c start " + txtoutpat.getText() +"bacth.bat");
//
//
//            } catch (Exception e) {
//                  e.printStackTrace();
//            } finally{
//                 try {
//
//                if (bw != null) {
//                    bw.close();
//                }
//
//                if (fw != null) {
//                    fw.close();
//                }
//
////             
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            
//            }

//        int order = Integer.parseInt(txtCount.getValue());
//        int resultarray = Integer.parseInt(txtoutpat.getValue());
//        if (resultarray >order) {
//            Messagebox.show("The quantity of order quantity must be equal to the number of uploads","Upload Pairing SN",Messagebox.OK,Messagebox.EXCLAMATION);
//           }else {
//                            for (int i = 0; i < split.length; i++) {
//                                x = split[i];
//                                sn=x;
//                                row =String.valueOf(i);
//         System.out.println(batch+ "============");
        Map<String, Object> mapping = model.doGetUploadBatch(null);
        if (mapping.get("OutError").equals(0)) {
            batch = (mapping.get("OutBatchId").toString());
            System.out.println(batch);
            WoUploadReadExcel excel = new WoUploadReadExcel(uploadId, uploadDtlId, SAVE_PATH + media.getName(), supId, supCode, poId, poLine, itemId, sn, batch, userId);
            Thread t = new Thread(excel);
            t.start();
        } else {
            Messagebox.show(mapping.get("OutMessages").toString());
        }

//            
//            t.isAlive();
//            
//            System.out.println(t);
//            }
//        }
//}
//            Messagebox.show("Success upload "+list.size()+" from "+txttotal.getValue(),"Upload Pairing",Messagebox.OK,Messagebox.INFORMATION);
        Messagebox.show("Upload being proccess on the background\n "
                + "Click Refresh for to know state of proccess", "Work Order : Message", Messagebox.OK, Messagebox.INFORMATION);
//        }
//                     updateStatus(poId, "2",userId);

//        parentControler.headerList();
    }

    public void refresh() {

        listbox.getItems().clear();
        list = model.selectListWOPairing(txtPoId.getValue(), txtPoLine.getText());
        listbox.setModel(new ListModelList<>(list));
        txttotal.setValue(String.valueOf(list.size()));
        Labeltotal.setValue(String.valueOf(txtCount.getText()));
    }

    public void setrender() {
        listbox.setItemRenderer(new ListitemRenderer<ListWoPairing>() {

            @Override
            public void render(Listitem lstm, ListWoPairing t, int i) throws Exception {
                new Listcell(t.getPo_id()).setParent(lstm);
                new Listcell(t.getSn_tcash()).setParent(lstm);
                new Listcell(t.getSn_inv()).setParent(lstm);
            }
        });

    }

    public void refreshHDR() {
        try {
            validatebuttonGen();
            if (!txtPoId.getText().equals("")) {
                List<wolistHDRPAram> list1 = model.getWoListPairingHDR(txtPoId.getText(), txtPoLine.getText());
                System.out.println(parentControler.txtPoId.getText());
                txtPoId.setValue(list1.get(0).getPo_id());
                txtuploadId.setValue(list1.get(0).getUpload_id());
                txtUpload.setValue(list1.get(0).getFilename());
            }
        } catch (Exception e) {
//            System.out.println(""+e);
        }

    }

    @Listen("onClick=#refresh")
    public void btnrefresh() {
        refresh();
        refreshHDR();
        parentControler.headerList();
        validatebuttonGen();
//              checkLogSN();
        String uploaded = txtCount.getText();
        String totalupload = txttotal.getValue();
//        if (uploaded.equals(totalupload)) {
//           genSn.setDisabled(true);
//        } else {
//           genSn.setDisabled(false);
//        }

    }

    @Listen("onClick=#close")
    public void close() {
        win_upload.detach();
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    void validatebuttonGen() {

        int countList = list.size();
        int countOrder = Integer.parseInt(txtCount.getText());

        if (countList== countOrder) {
            uploadFile.setDisabled(true);
         
            if (!parentControler.txtStatus.getText().equalsIgnoreCase("Upload In Progress")) {
                genSn.setDisabled(true);
                               
            } else {
                genSn.setDisabled(false);
                try {
                  if (list.get(0).getSn_inv().isEmpty()) {
                    genSn.setDisabled(false);
                }else{
                     genSn.setDisabled(true);
                } 
                } catch (Exception e) {
                }
               
            }
            
          

        } else {
            genSn.setDisabled(true);
            uploadFile.setDisabled(false);
        }

        parentControler.headerList();

    }

    @Listen("onClick=#View")
    public void checkError() {
        Map map = new HashMap();
        map.put("OutUploadId", txtPoId.getText());
        map.put("filename", txtUpload.getText());
        map.put("poLine", txtPoLine.getText());
        Window w = (Window) Executions.createComponents("/Tcash/ListViewLogs.zul", null, map);
        w.setAttribute("parentControll", this);
        w.doModal();
    }

    //=================================================================================
    @Listen("onClick=#genSn")
    public void buttonGenerate() {
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(global[0]);
        cf.setResponsibilityId(global[2]);
        cf.setFileName("/Tcash/ListWorKOrder.zul");
        cf.setFunctionName("GENERATE_SN");
        ParamCekFunction oe = (ParamCekFunction) ci.getFunction(cf);

        if (oe.getOutError() == 0) {
            validatebuttonGen();

            WoGenerateSn excel = new WoGenerateSn(poId, poLine, uploadId, supId, supCode, itemId, userId);
            Thread t = new Thread(excel);
            t.start();

            genSn.setDisabled(true);
            parentControler.headerList();
        } else {
            Messagebox.show(oe.getOutMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
//         Map<String, Object> map1 = model.doWoUpdatePairingSn(txtPoId.getText(),txtPoLine.getText(),txtuploadId.getText(),txtSupId.getText(), txtSupCode.getText(),txtItemId.getText());
//        if (map1.get("OutError").equals(0)) {
//            updateStatus(txtPoId.getText(), "3", userId);
//            doWoCreateRangeSNInv();
//        } else {
//            Messagebox.show(map1.get("OutMessages").toString(), "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
    }

    public void checkLogSN() {
        Map<String, Object> mapper = model.dowoUploadSNCheck(txtuploadId.getText(), txtPoId.getText(), txtPoLine.getText());
        if (mapper.get("OutError").equals(0)) {
        } else {
            Messagebox.show(mapper.get("OutMessages").toString());
        }
    }

    public void updateStatus(String poId, String status, String userId) {
        Map<String, Object> map = model.doWoUpdateStatus(poId, status, userId);

    }

    public void doWoCreateRangeSNInv() {
        System.out.println(poId + "poID create RangeSN");
        Map<String, Object> map = model.doWoCreateRangeSNInv(poId, poLine);

    }

    @Listen("onClick=#clear")
    public void clearData() {
        String status = parentControler.txtStatus.getText();
        if (!status.equalsIgnoreCase("Upload In Progress")) {
            Messagebox.show("Can not Clear data\nThis Transaction has been " + status);
            return;
        }

        if (listbox.getItems().isEmpty()) {
            Messagebox.show("No record Uploaded", "Work order", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        Messagebox.show("Are you sure want to clear?\nThis will erase all Generating SN ",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equalsIgnoreCase(e.getName())) {

                            Map<String, Object> map = model.doClearUpload(txtPoId.getText(),txtPoLine.getText());
                            if (map.get("OutError").equals(0)) {
                                btnrefresh();
                            } else {
                                Messagebox.show(map.get("OutMessages").toString(), "Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
                            }

                        } else if (Messagebox.ON_CANCEL.equalsIgnoreCase(e.getName())) {

                        }
                    }
                }
        );

//  public void refreshForHDRWO(){
//      parentControler.headerList();
//  }
    }
}
