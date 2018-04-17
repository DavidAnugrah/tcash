/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGdEditSNParam;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author arry
 */
public class SlcRangeGDCtrl extends SelectorComposer<Component> {

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
    
    @Wire
    Paging userPaging,userPagingDtl;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    GoodDeliveryCTRL goodDeliveryCTRL;
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
    String gdDtlId = "";
    String status="";
    String BuId="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuId() {
        return BuId;
    }

    public void setBuId(String BuId) {
        this.BuId = BuId;
    }
    
    private int startPageNumberHdr = 1;
    private final int pageSizeHdr = 100;
    private int JumlahRecordHdr = 100;
    
    private int startPageNumberDtl = 1;
    private final int pageSizeDtl = 100;
    private int JumlahRecordHdrDtl = 100;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//        System.out.println("poId=" + poId);
//        System.out.println("poLineRef=" + poLineRef);
//        System.out.println("supDelivid=" + supDelivid);
//        System.out.println("lineNo=" + lineNo);
        countHdr();
        countRst();
//        refreshHdr(1);
//        refreshResult(1);
        validationButton();
    }
    
     @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumberHdr = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumberHdr);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSizeHdr);
            refreshHdr(activePage);
        
    }

    public void refreshHdr(int activePage) {
        System.out.println("refreshHdr");
          System.out.println("poID"+poId);
        System.out.println("+polineREF"+poLineRef);
        System.out.println("+supDelivid"+supDelivid);
        System.out.println("+lineNo"+lineNo);
        lboxHdr.getItems().clear();
        List<ListGdEditSNParam> ListGdEditSNParam = model.selectGdListEditSN("", poId, poLineRef,
                supDelivid, lineNo,""+activePage,""+pageSizeHdr);
        int ttl = 0;
        for (ListGdEditSNParam ListGdEditSNParam1 : ListGdEditSNParam) {
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

            po_id.setLabel(ListGdEditSNParam1.getPo_id());
            position_number.setLabel(ListGdEditSNParam1.getPosition_number());
            serial_key_id.setLabel(ListGdEditSNParam1.getSerial_key_id());
            blockFrom.setLabel(ListGdEditSNParam1.getRange_from());
            blockTo.setLabel(ListGdEditSNParam1.getRange_to());
            supp_delivery_id.setLabel(ListGdEditSNParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListGdEditSNParam1.getSupp_delivery_line());
            receive_id.setLabel(ListGdEditSNParam1.getReceive_id());
            receive_line.setLabel(ListGdEditSNParam1.getReceive_line());
            created_period.setLabel(ListGdEditSNParam1.getCreated_period());
            qty.setLabel(ListGdEditSNParam1.getQty());


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

            lboxHdr.appendChild(listitem);
            ttl = ttl + Integer.parseInt(ListGdEditSNParam1.getQty());

        }
        sumSelectQty("","0", "0");

    }
    
    public void countHdr(){
        System.out.println("count");
        System.out.println("poID"+poId);
        System.out.println("+polineREF"+poLineRef);
        System.out.println("+supDelivid"+supDelivid);
        System.out.println("+lineNo"+lineNo);
        List<Integer> jumlahRecord = model.getCountEditSNHdr("", poId, poLineRef,
                supDelivid, lineNo);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdr = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPaging.setPageSize(pageSizeHdr);
        userPaging.setTotalSize(JumlahRecordHdr);

        userPaging.setActivePage(0);
        refreshHdr(1);
    }
    
    public void countRst(){
        List<Integer> jumlahRecord = model.getCountEditSNRst("RESULT", poId, poLineRef,
                supDelivid, lineNo);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdrDtl = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPagingDtl.setPageSize(pageSizeDtl);
        userPagingDtl.setTotalSize(JumlahRecordHdrDtl);

        userPagingDtl.setActivePage(0);
        refreshResult(1);
    }

    public void refreshResult(int activePage) {
        System.out.println("refreshRST");
        listbox.getItems().clear();
        List<ListGdEditSNParam> ListGdEditSNParam = model.selectGdListEditSN("RESULT", poId, poLineRef,
                supDelivid, lineNo,""+activePage,""+pageSizeHdr);
        for (ListGdEditSNParam ListGdEditSNParam1 : ListGdEditSNParam) {
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

            po_id.setLabel(ListGdEditSNParam1.getPo_id());
            position_number.setLabel(ListGdEditSNParam1.getPosition_number());
            serial_key_id.setLabel(ListGdEditSNParam1.getSerial_key_id());
            blockFrom.setLabel(ListGdEditSNParam1.getRange_from());
            blockTo.setLabel(ListGdEditSNParam1.getRange_to());
            supp_delivery_id.setLabel(ListGdEditSNParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListGdEditSNParam1.getSupp_delivery_line());
            receive_id.setLabel(ListGdEditSNParam1.getReceive_id());
            receive_line.setLabel(ListGdEditSNParam1.getReceive_line());
            created_period.setLabel(ListGdEditSNParam1.getCreated_period());
            qty.setLabel(ListGdEditSNParam1.getQty());


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

            listbox.appendChild(listitem);
        }
        sumSelectQty("R",supDelivid, lineNo);
    }

    public void inputQtyForm(String action) {
        InputQtyCtrlGD inputQtyCtrlGD = new InputQtyCtrlGD();
        if (action.equals("SELECT")) {
            try{
            List<Component> listCells = lboxHdr.getSelectedItem().getChildren();
            
            String blockFrom = ((Listcell) listCells.get(3)).getLabel();
            String blockto = ((Listcell) listCells.get(4)).getLabel();
            String Qty = ((Listcell) listCells.get(10)).getLabel();
            inputQtyCtrlGD.setBlockFrom(blockFrom);
            inputQtyCtrlGD.setBlockto(blockto);
            inputQtyCtrlGD.setQty(Qty);
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
            inputQtyCtrlGD.setBlockFrom(blockFrom);
            inputQtyCtrlGD.setBlockto(blockto);
            inputQtyCtrlGD.setQty(Qty);
            }
            catch(Exception e){
                Messagebox.show("Choose List Footer First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }

        inputQtyCtrlGD.setSlcRangeGDCtrl(this);
        inputQtyCtrlGD.setAction(action);
        inputQtyCtrlGD.setPoId(poId);
        inputQtyCtrlGD.setLineNo(lineNo);
        inputQtyCtrlGD.setPoLineRef(poLineRef);
        inputQtyCtrlGD.setSupDelivid(supDelivid);
        inputQtyCtrlGD.setInItemid(inItemid);
        inputQtyCtrlGD.setInQty(inQty.toString().replace(",", ""));
        inputQtyCtrlGD.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
        inputQtyCtrlGD.setInWhId(inWhId);
        inputQtyCtrlGD.setInCityid(inCityid);
        inputQtyCtrlGD.setInHlrMapId(inHlrMapId);
        inputQtyCtrlGD.setInOrderDate(inOrderDate);
        inputQtyCtrlGD.setInPoPriceUnit(inPoPriceUnit);
        inputQtyCtrlGD.setGdDtlId(gdDtlId);
        Map<String, Object> map = new HashMap<>();
        map.put("controller", inputQtyCtrlGD);
        Window window = (Window) Executions.createComponents(
                "/Tcash/InputQty.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#selectRange")
    public void selectRange() {
         Map<String, Object> map = model.doGrSelectRangeValidate(userId, responsibilityId, BuId,inWhId, 
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
          Map<String, Object> map = model.doGrUnSelectRangeValidate(userId, responsibilityId,  BuId,inWhId, status);
        if (map.get("outError").toString().equals("0")) {
        inputQtyForm("UNSELECT");
    } else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void sumSelectQty(String mode,String gdId,String linegdId) {
        Map<String, Object> map = model.doGdSumSelectQty(poId,
                poLineRef, gdId,linegdId);
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
        Map<String, Object> map = model.doGdSaveDtl("UPDATE", supDelivid, poId,
                poLineRef, lineNo,inItemid, "" + qty.toString().replace(",", ""), "", inWhId, inCityid, inHlrMapId, inOrderDate,"", userId, inPoPriceUnit,  gdDtlId);
        if (map.get("outError").toString().equals("0")) {
//            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    public void selectRangeOk() {
        String quantityTtl=txtTotalQty.getValue();
        if(txtTotalQty.getValue().equals("")){
            quantityTtl = "0";
        }
        Map<String, Object> map = model.doGdSelectRangeOk(poId,
                poLineRef, inQty.toString().replace(",", ""), quantityTtl.toString().replace(",", ""));
        if (map.get("outError").toString().equals("0")) {
            updateQty(map.get("outQty").toString());
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
    }

    @Listen("onClick=#btnSubmit")
    public void submitBtn() {
        selectRangeOk();
        win_select_range_GD.detach();
        goodDeliveryCTRL.refreshDtl();
    }
    
      public void validationButton(){
      
        if ( status.equals("1")) {
            selectRange.setDisabled(false);
            unselectRange.setDisabled(false);
        } else {
            selectRange.setDisabled(true);
            unselectRange.setDisabled(true);
        }
    }

    public GoodDeliveryCTRL getGoodDeliveryCTRL() {
        return goodDeliveryCTRL;
    }

    public void setGoodDeliveryCTRL(GoodDeliveryCTRL goodDeliveryCTRL) {
        this.goodDeliveryCTRL = goodDeliveryCTRL;
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

    public String getGdDtlId() {
        return gdDtlId;
    }

    public void setGdDtlId(String gdDtlId) {
        this.gdDtlId = gdDtlId;
    }
}
