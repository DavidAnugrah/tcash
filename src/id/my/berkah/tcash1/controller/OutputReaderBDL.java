/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OutputReaderBDL extends Thread {

    String fileName;
    String ofId;
    String ofNo;
    String poId;
    String inUploadId = "0";
    String outUploadId = "0";
//    private String[] global = ParameterGlobal.getParameterGlobal();
//    String userId = global[5];
    ModelTcashCTLR model = new ModelTcashCTLR();
//    String userId="18737";

    public OutputReaderBDL(String ofId,String ofNo,String poId, String fileName) {
        this.ofId = ofId;
        this.ofNo = ofNo;
        this.poId = poId;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            String result = "";
            BufferedReader br = null;
            String sCurrentLine = "";
            try {
                File xlFile = new File(fileName);
                br = new BufferedReader(new FileReader(xlFile));

                System.out.println(result);
                while ((sCurrentLine = br.readLine()) != null) {
                    System.out.println("Listcell" + "=" + sCurrentLine);
                    
                    String[] component = sCurrentLine.split(Pattern.quote("|"));
                    System.out.println("component 1"+ component[0].toString());
                    System.out.println("component 2"+ component[1].toString());
                    System.out.println("component 3"+ component[2].toString());
                    Map<String, Object> maper = model.doOfInsertDetailbdl(ofId, ofNo,poId, component[0].toString(), component[1].toString(), component[2].toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
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
