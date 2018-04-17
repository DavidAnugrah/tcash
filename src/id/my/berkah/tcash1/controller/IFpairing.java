/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGenIFParam;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class IFpairing extends SelectorComposer<Component>{
    @Wire
    Window win_upload;
    
    @Wire
    Textbox txtUpload;
    
        String FILENAME="";
        
        ModelTcashCTLR model =new ModelTcashCTLR();
    
    @Listen("onClick=#btnClose")
    public void closeWindow(){
        win_upload.detach();
    }
    
         @Listen("onClick=#uploadFile")
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
        
        Path parent1 = new Path("/win_if");
        Textbox txtFileName = (Textbox) new Path(parent1, "txtFileName").getComponent();
        Textbox txtWoId = (Textbox) new Path(parent1, "txtWoId").getComponent();

        try {
            
            String content = "";
            String poLine = "";
            FILENAME = download_directory+txtFileName.getValue();
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            List<ListGenIFParam> ListGenIFParam = model.selectGenFile(txtWoId.getValue(),poLine);
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
                Filedownload.save(new File(download_directory+txtFileName.getValue()),null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
