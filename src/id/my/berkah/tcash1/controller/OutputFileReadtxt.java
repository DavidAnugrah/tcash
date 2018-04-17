package id.my.berkah.tcash1.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

public class OutputFileReadtxt extends Thread {

    String fileName;
    String ofId;
    String ofNo;
    String poId;
    String media;
    String inUploadId = "0";
    String outUploadId = "0";
//    private String[] global = ParameterGlobal.getParameterGlobal();
//    String userId = global[5];
    ModelTcashCTLR model = new ModelTcashCTLR();
//    String userId="18737";

    public OutputFileReadtxt(String ofId,String ofNo,String poId,String media, String fileName) {
        this.ofId = ofId;
        this.ofNo = ofNo;
        this.poId = poId;
        this.media=media;
        this.fileName = fileName;
      
    }


    @Override
    public void run() {
        try {
            String result = "";
            BufferedReader br = null;
            String sCurrentLine = "";
            try {
                
                File xlFile = new File(media);
                br = new BufferedReader(new FileReader(xlFile));
                
                while ((sCurrentLine = br.readLine()) != null) {
                    System.out.println("Listcell" + "=" + sCurrentLine);
                  Map<String,Object>mapVA = model.doOfValidateDetail(ofId, sCurrentLine,fileName);
                    if (mapVA.get("OutStatus").equals("Y")) {
                   
                    } else {
                     if  (sCurrentLine.contains("|")){
                         String[] component = sCurrentLine.split(Pattern.quote("|"));    
                        Map<String,Object>map = model.doOfInsertLogs(ofId, ofNo, poId, component[0].toString(), component[1].toString(), component[2].toString());
                    }else  if  (sCurrentLine.contains("\t")){
                            String[] component = sCurrentLine.split(Pattern.quote("\t"));  
                          Map<String,Object>map = model.doOfInsertLogs(ofId, ofNo, poId, component[0].toString(), component[1].toString(), component[2].toString());
                    }else  if  (sCurrentLine.contains(":")){
                        String[] component = sCurrentLine.split(Pattern.quote(":"));  
                        Map<String,Object>map = model.doOfInsertLogs(ofId, ofNo, poId, component[0].toString(), component[1].toString(), component[2].toString());
                    } else  if  (sCurrentLine.contains(";")){
                        String[] component = sCurrentLine.split(Pattern.quote(";"));  
                          Map<String,Object>map = model.doOfInsertLogs(ofId, ofNo, poId, component[0].toString(), component[1].toString(), component[2].toString());
                    }else{
                        String[] component = sCurrentLine.split(Pattern.quote(" ")); 
                  Map<String, Object> maper = model.doOfInsertDetail(ofId, ofNo,poId, component[0].toString(), component[1].toString(), component[2].toString(),fileName);
                }
                }
                }
            } catch ( ArrayIndexOutOfBoundsException e) {
               
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
