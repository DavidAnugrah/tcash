/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class BundlingInputQtyGD extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
    @Wire
    Textbox txtBlokFrom, txtBlokTo;
    @Wire
    Decimalbox txtQty;
    @Wire
    Window win_select_qty_bundling;
    BundlingSelectRangeGD BundlingSelectRangeGD;

    public BundlingSelectRangeGD getBundlingSelectRangeGD() {
        return BundlingSelectRangeGD;
    }

    public void setBundlingSelectRangeGD(BundlingSelectRangeGD BundlingSelectRangeGD) {
        this.BundlingSelectRangeGD = BundlingSelectRangeGD;
    }
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    String action="";
    String poId = "";
    String poLineRef = "";
    String supDelivid = "";
    String lineNo = "";
    String blockFrom="";
    String blockto="";
    String Qty="";
    String inItemid="";
    String inQty="";
    String inOrderedQty="";
    String inWhId="";
    String inCityid="";
    String inHlrMapId="";
    String inOrderDate="";
    String inPoPriceUnit="";
    String gdDtlId="";
    String postNumb="";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        txtBlokFrom.setValue(blockFrom);
        txtBlokTo.setValue(blockto);
        txtQty.setValue(Qty);
        
        System.out.println("BundlingInputQtyGD");
    }

    public void selectRange() {

        System.out.println(txtQty.getValue());
        System.out.println(txtBlokFrom.getValue());
        System.out.println(txtBlokTo.getValue());
        String qty = ""+txtQty.getValue();
        System.out.println("QTY="+qty);
        if(qty.equals("null")){
            qty = "0";
        }
            Map<String, Object> map = model.doGdSelectRangeBundling("SELECT",poId,poLineRef,supDelivid,lineNo,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(), qty.toString().replace(",", ""));
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        
    }

    public void unselectRange() {
        String qty = ""+txtQty.getValue();
        System.out.println("QTY="+qty);
        if(qty.equals("null")){
            qty = "0";
        }
            Map<String, Object> map = model.doGdSelectRangeBundling("UNSELECT",poId,poLineRef,supDelivid,lineNo,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(), qty.toString().replace(",", ""));
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
    }
    
    

    public void exitForm() {
        win_select_qty_bundling.detach();
        BundlingSelectRangeGD.countHdr();
        BundlingSelectRangeGD.countRst();
    }

    @Listen("onClick=#btnClose")
    public void btnClose() {
        exitForm();
    }

    @Listen("onClick=#btnOk")
    public void btnOk() {
        String Messages = "";
        if (action.equals("SELECT")) {
            Messages = "Are you sure that selected serial is correct?";
        }
        else{
            Messages = "Are you sure that unselected serial is correct?";
        }
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    if (action.equals("SELECT")) {
                        selectRange();
                     
                    }
                    else {
                        unselectRange();
                        
                    }
                }
            }
        };
           BundlingSelectRangeGD.countHdr();
           BundlingSelectRangeGD.countRst();
        Messagebox.show(Messages, "Bundling Goods Delivery ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

   

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPoLineRef() {
        return poLineRef;
    }

    public void setPoLineRef(String poLineRef) {
        this.poLineRef = poLineRef;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getBlockFrom() {
        return blockFrom;
    }

    public void setBlockFrom(String blockFrom) {
        this.blockFrom = blockFrom;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSupDelivid() {
        return supDelivid;
    }

    public void setSupDelivid(String supDelivid) {
        this.supDelivid = supDelivid;
    }

    public String getInItemid() {
        return inItemid;
    }

    public void setInItemid(String inItemid) {
        this.inItemid = inItemid;
    }

    public String getInQty() {
        return inQty;
    }

    public void setInQty(String inQty) {
        this.inQty = inQty;
    }

    public String getInOrderedQty() {
        return inOrderedQty;
    }

    public void setInOrderedQty(String inOrderedQty) {
        this.inOrderedQty = inOrderedQty;
    }

    public String getInCityid() {
        return inCityid;
    }

    public void setInCityid(String inCityid) {
        this.inCityid = inCityid;
    }

    public String getInHlrMapId() {
        return inHlrMapId;
    }

    public void setInHlrMapId(String inHlrMapId) {
        this.inHlrMapId = inHlrMapId;
    }

    public String getInOrderDate() {
        return inOrderDate;
    }

    public void setInOrderDate(String inOrderDate) {
        this.inOrderDate = inOrderDate;
    }

    public String getInPoPriceUnit() {
        return inPoPriceUnit;
    }

    public void setInPoPriceUnit(String inPoPriceUnit) {
        this.inPoPriceUnit = inPoPriceUnit;
    }

    public String getInWhId() {
        return inWhId;
    }

    public void setInWhId(String inWhId) {
        this.inWhId = inWhId;
    }

    public String getGdDtlId() {
        return gdDtlId;
    }

    public void setGdDtlId(String gdDtlId) {
        this.gdDtlId = gdDtlId;
    }

    public String getPostNumb() {
        return postNumb;
    }

    public void setPostNumb(String postNumb) {
        this.postNumb = postNumb;
    }

    public String getBlockto() {
        return blockto;
    }

    public void setBlockto(String blockto) {
        this.blockto = blockto;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String Qty) {
        this.Qty = Qty;
    }
    
    

    
   
}
