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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class InputQtyCtrl extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String blockFrom = "";
    String blockto = "";
    String Qty = "";

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
    String storeStatus = "";
    String dtlHlrPoId = "";
    String tmpFrom = "";
    String tmpTo = "";
    String tmpQty = "";
    String poId = "";
    String poLine = "";
    String poLineId = "";
    String itemLocId = "";
    String inOrderQty = "";
    String inHlrMapId = "";
    String orderType = "";
    String action = "";
    String posNum = "";
    String dtlPoId = "";
    @Wire
    Textbox txtBlokFrom, txtBlokTo;
    @Wire
    Intbox txtQty;
    @Wire
    Window win_select_qty;
    SelectRangeCtrl selectRangeCtrl;
    ModelTcashCTLR model = new ModelTcashCTLR();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        txtBlokFrom.setValue(blockFrom);
    }

    public void selectRange() {

        System.out.println(txtQty.getValue());
        System.out.println(txtBlokFrom.getValue());
        System.out.println(txtBlokTo.getValue());
        if (orderType.equals("1")) {
            Map<String, Object> map = model.doWoSelectAllocationINV(poId, poLineId, poLine, itemLocId, inHlrMapId,storeStatus, txtBlokFrom.getValue(), txtBlokTo.getValue(), "" + txtQty.getValue(), "" + dtlPoId,dtlHlrPoId);
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();
                
            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
        else {
            Map<String, Object> map = model.doWoAllocateNBR(poId, posNum, itemLocId, dtlHlrPoId, "" + txtQty.getValue(),
                    txtBlokFrom.getValue(), txtBlokTo.getValue(), storeStatus, tmpQty, tmpFrom, tmpTo, inOrderQty, inHlrMapId);
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//                createLine();
                exitForm();

            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
    }

    public void unselectRange() {
        if (orderType.equals("1")) {
            Map<String, Object> map = model.doWoUnSelectAllocationINV(poId, poLine,poLineId,dtlPoId,dtlHlrPoId, itemLocId, storeStatus,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(),
                    "" + txtQty.getValue());
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();

            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
        else {
            System.out.println("doWoUnSelectAllocateNBR");
            Map<String, Object> map = model.doWoUnSelectAllocateNBR(poId, poLine, posNum, dtlHlrPoId, itemLocId,
                    txtBlokFrom.getValue(), txtBlokTo.getValue(),
                    "" + txtQty.getValue());
            if (map.get("outError").toString().equals("0")) {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                exitForm();

            }
            else {
                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }
    }
    
//    public void createLine(){
//        Map<String, Object> map = model.doWoCreateLineDetail(poId, poLineId,dtlPoId);
//            if (map.get("outError").toString().equals("0")) {
//                Messagebox.show(map.get("outMessages").toString());
//                exitForm();
//
//            }
//            else {
//                Messagebox.show(map.get("outMessages").toString());
//            }
//    }

    public void exitForm() {


        win_select_qty.detach();

        if (orderType.equals("1")) {
            System.out.println("refreshINV");
            selectRangeCtrl.countINV();
            selectRangeCtrl.countINVDtl();

//            woAllocateINVTemp();
        }
        else {
            selectRangeCtrl.countNBR();
            selectRangeCtrl.countNBRDtl();

//            woAllocateNBRTemp();
        }

    }

    @Listen("onClick=#btnClose")
    public void btnClose() {
        exitForm();
    }

    @Listen("onClick=#btnOk")
    public void btnOk() {
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
        Messagebox.show("Are you sure want to " + action + " this record?", "Message ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);

    }

    public String getBlockFrom() {
        return blockFrom;
    }

    public void setBlockFrom(String blockFrom) {
        this.blockFrom = blockFrom;
    }

    public SelectRangeCtrl getSelectRangeCtrl() {
        return selectRangeCtrl;
    }

    public void setSelectRangeCtrl(SelectRangeCtrl selectRangeCtrl) {
        this.selectRangeCtrl = selectRangeCtrl;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getDtlHlrPoId() {
        return dtlHlrPoId;
    }

    public void setDtlHlrPoId(String dtlHlrPoId) {
        this.dtlHlrPoId = dtlHlrPoId;
    }

    public String getTmpFrom() {
        return tmpFrom;
    }

    public void setTmpFrom(String tmpFrom) {
        this.tmpFrom = tmpFrom;
    }

    public String getTmpTo() {
        return tmpTo;
    }

    public void setTmpTo(String tmpTo) {
        this.tmpTo = tmpTo;
    }

    public String getTmpQty() {
        return tmpQty;
    }

    public void setTmpQty(String tmpQty) {
        this.tmpQty = tmpQty;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getPoLine() {
        return poLine;
    }

    public void setPoLine(String poLine) {
        this.poLine = poLine;
    }

    public String getItemLocId() {
        return itemLocId;
    }

    public void setItemLocId(String itemLocId) {
        this.itemLocId = itemLocId;
    }

    public String getInOrderQty() {
        return inOrderQty;
    }

    public void setInOrderQty(String inOrderQty) {
        this.inOrderQty = inOrderQty;
    }

    public String getInHlrMapId() {
        return inHlrMapId;
    }

    public void setInHlrMapId(String inHlrMapId) {
        this.inHlrMapId = inHlrMapId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(String poLineId) {
        this.poLineId = poLineId;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public String getDtlPoId() {
        return dtlPoId;
    }

    public void setDtlPoId(String dtlPoId) {
        this.dtlPoId = dtlPoId;
    }
}
