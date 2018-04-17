/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ArtWorkListParam;
import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.MVItemToVoucherParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class ArtWorkCtrl extends SelectorComposer<Component>{
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    
    @Wire
    Window winArtWork;
    @Wire
    Textbox txtId, txtArtCode,txtArtDesc;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        if (txtId.getValue() != "") {
            txtArtCode.setReadonly(true);
            headerList();
        }
        else {
        }
    }
    
   
   public void headerList() {
        List<ArtWorkListParam> ListWoParam = model.selectArtListHdr(txtId.getValue());
        txtArtCode.setValue(ListWoParam.get(0).getArt_code());
        txtArtDesc.setValue(ListWoParam.get(0).getArt_description());
    }
   
   @Listen("onClick=#btnSave")
    public void btnsave() {

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (txtId.getValue() == "") {
                        save();
                    }
                    else {
                         update();
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Save?", "Message", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
   
   public void save(){
        Map<String, Object> map = model.doArtInsert(txtArtCode.getValue(),
                txtArtDesc.getValue(), "",
                userId);
        if (map.get("outError").toString().equals("0")) {
            txtId.setValue(map.get("outId").toString());
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            headerList();
            txtArtCode.setReadonly(true);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   
    
   public void update(){
        Map<String, Object> map = model.doArtUpdate(txtId.getValue(),txtArtCode.getValue(), txtArtDesc.getValue(),
                "",userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            headerList();
            txtArtCode.setReadonly(true);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   public void expired(){
        Map<String, Object> map = model.doArtExpire(txtId.getValue(),userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            txtArtCode.setReadonly(true);
            txtArtDesc.setReadonly(true);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   @Listen("onClick=#btnExpired")
    public void expiredMap() {

        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (!txtId.getValue().equals("")) {
                        expired();
                    }
//                    else {
//                        Messagebox.show(map.get("outMessages").toString());
//                    }
                }
            }
        };
        Messagebox.show("Are you sure want to Expired?", "Message", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }
   
   @Listen("onClick=#btnNew")
   public void newTrc(){
       txtId.setValue(""); 
       txtArtCode.setValue("");
       txtArtDesc.setValue("");
       txtArtCode.setReadonly(false);
   }
   
   @Listen("onClick=#btnClose")
   public void close(){
       winArtWork.detach();
   }
    
}
