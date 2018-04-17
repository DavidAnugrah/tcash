/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import java.io.*;
import jxl.*;
import java.util.Map;
import org.zkoss.zul.Messagebox;

public class WoUploadReadExcel extends Thread {

    String fileName;
    String supplierId;
    String supplierCode;
    String poId;
    String poLine;
    Workbook workbook;
    String file;
    String itemId;
    String uploadId = "";
    String uploadDtlId;
    String userId;
    String snInv;
    String batch;

    ModelTcashCTLR model = new ModelTcashCTLR();

    public WoUploadReadExcel(String uploadId, String uploadDtlId, String fileName, String supplierId,
            String supplierCode, String poId, String poLine, String itemId, String sn ,String batch,String userId) {

        this.uploadId = uploadId;
        this.poId = poId;
        this.poLine = poLine;
        this.fileName = fileName;
        this.supplierId = supplierId;
        this.supplierCode = supplierCode;
        this.itemId = itemId;
        this.userId = userId;
        this.batch = batch;
    }

    @Override
    public void run() {
        try {
            workbook = Workbook.getWorkbook(new File(fileName));
            Sheet sheet = workbook.getSheet(0);
            int i = 0;
            
            while (true) {
                                        
                try{
                Cell sn = sheet.getCell(0, i);
                
                if (sn.getContents().equals("") || sn.getContents() == null) {
//                    Map<String, Object> map1 = model.doWoUpdatePairingSn(poId, poLine, uploadId, supplierId, supplierCode, itemId);
//
//                    Map<String, Object> map = model.doWoCreateRangeSNInv(poId);
                    break;
                }
                 
                Map<String, Object> map = model.dowoUploadPairingSn(uploadId, uploadDtlId, fileName, supplierId, supplierCode, poId, poLine, itemId, sn.getContents(), i + "", batch,userId);
                uploadId = (map.get("OutUploadId").toString());
                uploadDtlId = (map.get("OutUploadDtlId").toString());
                }catch(Exception e){
                    break;
                }finally{
                  
                }
                i++;
            }
            
              Map<String, Object> map = model.doWoUpdateStatusUpload(poId, "2", userId);
              Map<String,Object>mapping=model.dowoUpdateQtyUploadSn(uploadId, userId);
              Map<String, Object> mapper = model.dowoUploadSNCheck(uploadId, poId, poLine);
//            Map<String, Object> map1 = model.doWoUpdatePairingSn(poId, poLine, uploadId, supplierId, supplierCode, itemId);
//            Map<String, Object> map2 = model.doWoUpdateStatus(poId, "3", userId);
//            Map<String, Object> map3 = model.doWoCreateRangeSNInv(poId);
            

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            Messagebox.show(ex.getMessage());
        }
    }
//
////    void upload() {
////        Map<String, Object> map = model.dowoUploadPairingSn(uploadId, uploadDtlId, fileName, supplierId, supplierCode, poId, poLine, itemId, sn.getContents(), i + "", userId);
////        if (map.get("outError").equals(0)) {
////            uploadId = map.get("OutUploadId").toString();
////            uploadDtlId = map.get("OutUploadDtlId").toString();
////            i++;
////        } else {
////            Messagebox.show(map.get("outMessages").toString());
////        }
////    }
////    public void uploadFile(){
////             Map<String, Object> map = model.dowoUploadPairingSn(uploadId, uploadDtlId, fileName, supplierId, supplierCode, poId, poLine, itemId, sn.getContents(), i+"", userId);
////                                 if (map.get("outError").equals(0)) {
////                                  uploadId=(map.get("OutUploadId").toString());
////                                    uploadDtlId=(map.get("OutUploadDtlId").toString());
////                                    buttonGenerate();
////                                    i++;
////                                } else {
//////                                    Messagebox.show(map.get("outMessages").toString());
////                                }
////                                }
//    public void buttonGenerate() {
//        Map<String, Object> map1 = model.doWoUpdatePairingSn(poId, poLine, uploadId, supplierId, supplierCode, itemId);
//        if (map1.get("OutError").equals(0)) {
//           
//            if (update.get("OutError").equals(0)) {
//
//            } else {
////                Messagebox.show(update.get("OutMessages").toString(), "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
//            }
//        } else {
////            Messagebox.show(map1.get("OutMessages").toString(), "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    }
////
//
//    public void doWoCreateRangeSNInv(String poId) {
//        Map<String, Object> map = model.doWoCreateRangeSNInv(poId);
//        if (map.get("OutError").equals(0)) {
//        } else {
////            Messagebox.show(map.get("OutMessages").toString(), "Work Order", Messagebox.OK, Messagebox.EXCLAMATION);
////            
//        }

//    }
//
//}
//        private EventListener listn = new EventListener() {
//
//       
//        @Override
//        public void onEvent(Event t) throws Exception {
//            UploadParingCtrl.setValueTextbox(uploadId, uploadDtlId);
//        }
//    };
//    }
}
