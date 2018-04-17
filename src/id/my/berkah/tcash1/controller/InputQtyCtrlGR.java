/*
 * To change this template, choose Tools | Templates
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class InputQtyCtrlGR extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
    @Wire
    Textbox txtBlokFrom, txtBlokTo;
    @Wire
    Decimalbox txtQty;
    @Wire
    Window win_select_qty;
    SlcRangeGRCtrl slcRangeGRCtrl;
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    String action="";
    String poId = "";
    String poLineRef = "";
    String supDelivid = "";
    String lineNo = "";
    String blockFrom="";
    String blockto="";
    String Qty="";

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
    String inItemid="";
    String inQty="";
    String inOrderedQty="";
    String inWhId="";
    String inCityid="";
    String inHlrMapId="";
    String inOrderDate="";
    String inPoPriceUnit="";
    
    String gdId = "";
    String gdLine = "";
    String rcvId = "";
    String rcvLine = "";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        txtBlokFrom.setValue(blockFrom);
        txtBlokTo.setValue(blockto);
        txtQty.setValue(Qty);
    }

    public void selectRange() {
        String qty = ""+txtQty.getValue();
        System.out.println("QTY="+qty);
        if(qty.equals("null")){
            qty = "0";
        }
        System.out.println(txtQty.getValue());
        System.out.println(txtBlokFrom.getValue());
        System.out.println(txtBlokTo.getValue());
            Map<String, Object> map = model.doGrSelectRange("SELECT",gdId,gdLine,rcvId,rcvLine,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(), qty.toString().replace(",", ""),userId);
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                updateQty();
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
            Map<String, Object> map = model.doGrSelectRange("UNSELECT",gdId,gdLine,rcvId,rcvLine,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(), qty.toString().replace(",", ""),userId);
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
    }
    
//    public void updateQty(){
//        Map<String, Object> map = model.doGdSaveDtl("UPDATE", supDelivid, poId,
//                                    poLineRef, inItemid, inQty, inOrderedQty, inWhId, inCityid, inHlrMapId, inOrderDate, userId, inPoPriceUnit,lineNo);
//                            if (map.get("outError").toString().equals("0")) {
//                            }
//                            else {
//                                Messagebox.show(map.get("outMessages").toString());
//                                return;
//                            }
//    }

    public void exitForm() {
        win_select_qty.detach();
        slcRangeGRCtrl.refreshHdr();
        slcRangeGRCtrl.refreshResult();
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
        Messagebox.show(Messages, "Message ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);
    }

    public SlcRangeGRCtrl getSlcRangeGRCtrl() {
        return slcRangeGRCtrl;
    }

    public void setSlcRangeGRCtrl(SlcRangeGRCtrl slcRangeGRCtrl) {
        this.slcRangeGRCtrl = slcRangeGRCtrl;
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

    public String getGdId() {
        return gdId;
    }

    public void setGdId(String gdId) {
        this.gdId = gdId;
    }

    public String getGdLine() {
        return gdLine;
    }

    public void setGdLine(String gdLine) {
        this.gdLine = gdLine;
    }

    public String getRcvId() {
        return rcvId;
    }

    public void setRcvId(String rcvId) {
        this.rcvId = rcvId;
    }

    public String getRcvLine() {
        return rcvLine;
    }

    public void setRcvLine(String rcvLine) {
        this.rcvLine = rcvLine;
    }

   
}
