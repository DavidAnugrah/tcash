/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListSNUploadErrorParam;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ViewLogs extends SelectorComposer<Component>{
    
    @Wire
    Window win_upload_logs;
    
    @Wire
    Listbox listboxlogs;
    
    @Wire
    Label txttotal1;
    
    @Wire
     Textbox txtUpload,txtfile,txtpoLine;
    
    @Wire
   Button ExportXlsx;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
     String FILENAME="";
   UploadParingCtrl parentControll;
   
//   String uploadId;
    @Listen("onCreate=#win_upload_logs")
    public void oncREateWindow(){
        parentControll = (UploadParingCtrl) win_upload_logs.getAttribute("parentControll");
//        uploadId = parentControll.uploadId;
//        System.out.println(uploadId);
        setRenderLogs();
         checklog();
         if (listboxlogs.getItems().isEmpty()) {
           ExportXlsx.setDisabled(true);
        } else {
             ExportXlsx.setDisabled(false);
        }
    }
    
    @Listen("onClick=#closelogs")
    public void closeLogs(){
        win_upload_logs.detach();
    }
    

    public void checklog(){
        listboxlogs.getItems().clear();
        List<ListSNUploadErrorParam>listlogs =  model.getListSNUploadError(txtUpload.getText(),txtpoLine.getText());
        listboxlogs.setModel(new ListModelList<ListSNUploadErrorParam>(listlogs));
        txttotal1.setValue(String.valueOf(listlogs.size()));
    }
    
    public void setRenderLogs(){
        listboxlogs.setItemRenderer(new ListitemRenderer<ListSNUploadErrorParam>(){

            @Override
            public void render(Listitem lstm1, ListSNUploadErrorParam t, int i) throws Exception {
                new Listcell(t.getError_description()).setParent(lstm1);
            }
        
    });
    }
    
    @Listen("onClick=#ExportXlsx")
    public void exportToTxt() throws IOException{
         ViewLogs inp1 = new ViewLogs();
         inp1.generateFile1();
    }
    
    public void generateFile1() throws IOException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String download_directory = properties.getProperty("download_directory");
        
        Path parent1 = new Path("/win_upload_logs");
        Textbox txtupload = (Textbox) new Path(parent1, "txtUpload").getComponent();
        Textbox txtpoline = (Textbox) new Path(parent1, "txtpoLine").getComponent();

        try {
            
            String content = "";
            FILENAME = download_directory+"logs.txt";
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            List<ListSNUploadErrorParam>ListSNUploadErrorParam =  model.getListSNUploadError(txtupload.getText(),txtpoline.getText());
            int i = 0;
            
            for(ListSNUploadErrorParam ListSNUploadErrorParam1 :ListSNUploadErrorParam){
                if (i != 0){
                    bw.newLine();
                }
                System.out.println("hasilnya "+ListSNUploadErrorParam1.getError_description());
                content = ListSNUploadErrorParam1.getError_description();
                
                bw.write(content);
              
                i++;
            }
            System.out.println("Done");
        
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
                
                Filedownload.save(new File(download_directory+"logs.txt"),null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
    

