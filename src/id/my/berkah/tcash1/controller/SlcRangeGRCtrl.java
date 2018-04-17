/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGrListEditSNParam;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class SlcRangeGRCtrl extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_select_range_GD;
    @Wire
    Listbox lboxHdr, listbox;
    @Wire
    Label txtTotalQty, txtTotal;
    
    @Wire
    Button selectRange,unselectRange;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    GoodRecieptCTRL goodRecieptCTRL;
    String poId = "";
    String poLineRef = "";
    String supDelivid = "";
    String lineNo = "";
    String inItemid = "";
    String inQty = "";
    String inOrderedQty = "";
    String inWhId = "";
    String inCityid = "";
    String inHlrMapId = "";
    String inOrderDate = "";
    String inPoPriceUnit = "";
    String gdId = "";
    String gdLine = "";
    String rcvId = "";
    String rcvLine = "";
    String status = "";
    String buId="";
    String delivQty="";
    String inregId="";
    String inItemCode="";
    String poNo="";
    String rcvDtlId="";
    String LotId="";
    String QtyReceive="";

    public String getQtyReceive() {
        return QtyReceive;
    }

    public void setQtyReceive(String QtyReceive) {
        this.QtyReceive = QtyReceive;
    }
    public String getLotId() {
        return LotId;
    }

    public void setLotId(String LotId) {
        this.LotId = LotId;
    }


    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println("gdId=" + gdId);
        System.out.println("gdLine=" + gdLine);
        System.out.println("rcvId=" + rcvId);
        System.out.println("rcvLine=" + rcvLine);
        System.out.println("buId=" + buId);
        System.out.println("status=" + status);
        System.out.println("poLineRef" + poLineRef);
        refreshHdr();
        refreshResult();
        validation();
    }

    public void refreshHdr() {
        System.out.println("refreshHdr");
        lboxHdr.getItems().clear();
        List<ListGrListEditSNParam> ListGrListEditSNParam = model.selectGrListEditSN("", gdId, gdLine,
                rcvId, rcvLine);
        int ttl = 0;
        for (ListGrListEditSNParam ListGrListEditSNParam1 : ListGrListEditSNParam) {
            Listcell po_id = new Listcell();
            Listcell position_number = new Listcell();
            Listcell serial_key_id = new Listcell();
            Listcell blockFrom = new Listcell();
            Listcell blockTo = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_line = new Listcell();
            Listcell receive_id = new Listcell();
            Listcell receive_line = new Listcell();
            Listcell created_period = new Listcell();
            Listcell qty = new Listcell();

            po_id.setLabel(ListGrListEditSNParam1.getPo_id());
            position_number.setLabel(ListGrListEditSNParam1.getPosition_number());
            serial_key_id.setLabel(ListGrListEditSNParam1.getSerial_key_id());
            blockFrom.setLabel(ListGrListEditSNParam1.getRange_from());
            blockTo.setLabel(ListGrListEditSNParam1.getRange_to());
            supp_delivery_id.setLabel(ListGrListEditSNParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListGrListEditSNParam1.getSupp_delivery_line());
            receive_id.setLabel(ListGrListEditSNParam1.getReceive_id());
            receive_line.setLabel(ListGrListEditSNParam1.getReceive_line());
            created_period.setLabel(ListGrListEditSNParam1.getCreated_period());
            qty.setLabel(ListGrListEditSNParam1.getQty());


            Listitem listitem = new Listitem();

            listitem.appendChild(po_id);
            listitem.appendChild(position_number);
            listitem.appendChild(serial_key_id);
            listitem.appendChild(blockFrom);
            listitem.appendChild(blockTo);
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(supp_delivery_line);
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_line);
            listitem.appendChild(created_period);
            listitem.appendChild(qty);


//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });

            lboxHdr.appendChild(listitem);

        }
        sumSelectQty("","0", "0");
    }

    public void refreshResult() {
//        System.out.println("refreshRST");
        listbox.getItems().clear();
        List<ListGrListEditSNParam> ListGrListEditSNParam = model.selectGrListEditSN("RESULT", gdId, gdLine,
                rcvId, rcvLine);
        int ttl = 0;
        for (ListGrListEditSNParam ListGrListEditSNParam1 : ListGrListEditSNParam) {
            Listcell po_id = new Listcell();
            Listcell position_number = new Listcell();
            Listcell serial_key_id = new Listcell();
            Listcell blockFrom = new Listcell();
            Listcell blockTo = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_line = new Listcell();
            Listcell receive_id = new Listcell();
            Listcell receive_line = new Listcell();
            Listcell created_period = new Listcell();
            Listcell qty = new Listcell();

            po_id.setLabel(ListGrListEditSNParam1.getPo_id());
            position_number.setLabel(ListGrListEditSNParam1.getPosition_number());
            serial_key_id.setLabel(ListGrListEditSNParam1.getSerial_key_id());
            blockFrom.setLabel(ListGrListEditSNParam1.getRange_from());
            blockTo.setLabel(ListGrListEditSNParam1.getRange_to());
            supp_delivery_id.setLabel(ListGrListEditSNParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListGrListEditSNParam1.getSupp_delivery_line());
            receive_id.setLabel(ListGrListEditSNParam1.getReceive_id());
            receive_line.setLabel(ListGrListEditSNParam1.getReceive_line());
            created_period.setLabel(ListGrListEditSNParam1.getCreated_period());
            qty.setLabel(ListGrListEditSNParam1.getQty());


            Listitem listitem = new Listitem();

            listitem.appendChild(po_id);
            listitem.appendChild(position_number);
            listitem.appendChild(serial_key_id);
            listitem.appendChild(blockFrom);
            listitem.appendChild(blockTo);
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(supp_delivery_line);
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_line);
            listitem.appendChild(created_period);
            listitem.appendChild(qty);


//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });
            listbox.appendChild(listitem);

        }
        sumSelectQty("R",rcvId, rcvLine);
    }

    public void inputQtyForm(String action) {
        InputQtyCtrlGR inputQtyCtrlGR = new InputQtyCtrlGR();
        if (action.equals("SELECT")) {
            try{
            List<Component> listCells = lboxHdr.getSelectedItem().getChildren();

            String blockFrom = ((Listcell) listCells.get(3)).getLabel();
            String blockto = ((Listcell) listCells.get(4)).getLabel();
            String Qty = ((Listcell) listCells.get(10)).getLabel();
            inputQtyCtrlGR.setBlockFrom(blockFrom);
            inputQtyCtrlGR.setBlockto(blockto);
            inputQtyCtrlGR.setQty(Qty);
            
            }
            catch(Exception e){
                Messagebox.show("Choose List Header First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

        }
        else {
            try{
            List<Component> listCells = listbox.getSelectedItem().getChildren();

            String blockFrom = ((Listcell) listCells.get(3)).getLabel();
            String blockto = ((Listcell) listCells.get(4)).getLabel();
            String Qty = ((Listcell) listCells.get(10)).getLabel();
            inputQtyCtrlGR.setBlockFrom(blockFrom);
            inputQtyCtrlGR.setBlockto(blockto);
            inputQtyCtrlGR.setQty(Qty);
            }
            catch(Exception e){
                Messagebox.show("Choose List Footer First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }

        inputQtyCtrlGR.setSlcRangeGRCtrl(this);
        inputQtyCtrlGR.setAction(action);
        inputQtyCtrlGR.setPoId(poId);
        inputQtyCtrlGR.setLineNo(lineNo);
        inputQtyCtrlGR.setPoLineRef(poLineRef);
        inputQtyCtrlGR.setSupDelivid(supDelivid);
        inputQtyCtrlGR.setInItemid(inItemid);
        inputQtyCtrlGR.setInQty(inQty.toString().replace(",", ""));
        inputQtyCtrlGR.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
        inputQtyCtrlGR.setInWhId(inWhId);
        inputQtyCtrlGR.setInCityid(inCityid);
        inputQtyCtrlGR.setInHlrMapId(inHlrMapId);
        inputQtyCtrlGR.setInOrderDate(inOrderDate);
        inputQtyCtrlGR.setInPoPriceUnit(inPoPriceUnit);
        inputQtyCtrlGR.setGdId(gdId);
        inputQtyCtrlGR.setGdLine(gdLine);
        inputQtyCtrlGR.setRcvId(rcvId);
        inputQtyCtrlGR.setRcvLine(rcvLine);
        Map<String, Object> map = new HashMap<>();
        map.put("controller", inputQtyCtrlGR);
        Window window = (Window) Executions.createComponents(
                "/Tcash/InputQty.zul", null, map);
        window.doModal();
    }
    
    public void sumSelectQty(String mode,String grId,String linegrId) {
        Map<String, Object> map = model.doGrSumSelectQty(poId,
                gdLine, grId,linegrId,gdId,gdLine);
        if (map.get("outError").toString().equals("0")) {
            if(mode.equals("R")){
            txtTotalQty.setValue(map.get("outSummary").toString());
            }else{
             txtTotal.setValue(map.get("outSummary").toString());
            }
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }
    
    public void updateQty(String qty) {
        Map<String, Object> map = model.doGrSaveDtl("UPDATE", rcvId, rcvLine,
                rcvDtlId, inItemid,inItemCode,"", inQty.toString().replace(",", ""), qty.toString().replace(",", ""),"",userId,inHlrMapId,"","",poId,poNo,rcvLine,
                "","","",inQty.toString().replace(",", ""),"",gdLine,gdId,
                inWhId,"",inregId);
        if (map.get("outError").toString().equals("0")) {
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#selectRange")
    public void selectRange() {
        Map<String, Object> map = model.doGrSelectRangeValidate(userId, responsibilityId, buId,inWhId, 
                status);
        if (map.get("outError").toString().equals("0")) {
            inputQtyForm("SELECT");
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }

    }

    @Listen("onClick=#unselectRange")
    public void unselectRange() {
         Map<String, Object> map = model.doGrUnSelectRangeValidate(userId, responsibilityId,  buId,inWhId, status);
        if (map.get("outError").toString().equals("0")) {
           inputQtyForm("UNSELECT");
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        
    }

    @Listen("onClick=#btnSubmit")
    public void submitBtn(){
        updateQty(txtTotalQty.getValue().toString().replace(",", ""));
        win_select_range_GD.detach();
        goodRecieptCTRL.refreshDtl();
    }
    
    public void validation(){
        if (status.equals("1")) {
            selectRange.setDisabled(false);
            unselectRange.setDisabled(false);
        } else {
            selectRange.setDisabled(true);
            unselectRange.setDisabled(true);
        }
    }

    public GoodRecieptCTRL getGoodRecieptCTRL() {
        return goodRecieptCTRL;
    }

    public void setGoodRecieptCTRL(GoodRecieptCTRL goodRecieptCTRL) {
        this.goodRecieptCTRL = goodRecieptCTRL;
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

    public String getSupDelivid() {
        return supDelivid;
    }

    public void setSupDelivid(String supDelivid) {
        this.supDelivid = supDelivid;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
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

    public String getInWhId() {
        return inWhId;
    }

    public void setInWhId(String inWhId) {
        this.inWhId = inWhId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(String delivQty) {
        this.delivQty = delivQty;
    }

    public String getInregId() {
        return inregId;
    }

    public void setInregId(String inregId) {
        this.inregId = inregId;
    }

    public String getInItemCode() {
        return inItemCode;
    }

    public void setInItemCode(String inItemCode) {
        this.inItemCode = inItemCode;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getRcvDtlId() {
        return rcvDtlId;
    }

    public void setRcvDtlId(String rcvDtlId) {
        this.rcvDtlId = rcvDtlId;
    }
    
}
