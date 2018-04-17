/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoPairing;
import java.util.List;
import java.util.Map;
import jxl.Cell;

/**
 *
 * @author Azec
 */
public class WoGenerateSn extends Thread{
    
 
    String supplierId;
    String supplierCode;
    String poId;
    String poLine;
    String itemId;
    String uploadId = "";
    String total;
    String userId;
  
  List<ListWoPairing> list;
    int j = 0;
    Cell sn = null;
    UploadParingCtrl UploadParingCtrl;

    public UploadParingCtrl getUploadParingCtrl() {
        return UploadParingCtrl;
    }

    public void setUploadParingCtrl(UploadParingCtrl UploadParingCtrl) {
        this.UploadParingCtrl = UploadParingCtrl;
    }
    
    

    ModelTcashCTLR model = new ModelTcashCTLR();
    
  
    
    public WoGenerateSn(String poId,String poLine,String uploadId,String supplierId, String supplierCode,String itemId,String userId){
        this.poId = poId;
        this.poLine = poLine;
        this.uploadId = uploadId;
        this.supplierId = supplierId;
        this.supplierCode = supplierCode;
        this.itemId = itemId;
        this.userId=userId;
     
        
    }
    
     @Override
    public void run() {
        try {
              int i = 0;

//            while (true) {
            
//                try {
//                    if (list.isEmpty()||list.equals(null)) {
//                    break;
//                }
//               
//                if (sn.getContents().equals("") || sn.getContents() == null) {
//                    break;
//                }
                
                 Map<String, Object> map1 = model.doWoUpdatePairingSn(poId,poLine,uploadId,supplierId, supplierCode,itemId,userId);
                 doWoCreateRangeSNInv();
//                 updateStatus(poId, "3", itemId);
                 
            }catch(Exception s){
                s.printStackTrace();
            }

            
    }
    
      public void updateStatus(String poId,String status,String userId) {
        Map<String, Object> map = model.doWoUpdateStatus(poId, status, userId);
       
    }
        public void doWoCreateRangeSNInv() {
        Map<String, Object> map = model.doWoCreateRangeSNInv(poId,poLine);
       
        }
    
}
