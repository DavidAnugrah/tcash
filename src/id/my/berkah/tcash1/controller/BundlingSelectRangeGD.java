/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListBundlingGdEditSNParam;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
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
 * @author Azec
 */
public class BundlingSelectRangeGD extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    
    @Wire
    Window win_select_range_GD_bundling;
    @Wire
    Listbox lboxHdr, listbox;
    @Wire
    Label txtTotalQty, txtTotal;
    
    @Wire
    Button selectRange,unselectRange;
    
    @Wire
    Paging userPaging,userPagingDtl;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    BundlingGoodsDelivery BundlingGoodsDelivery;
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
    String postNumb="";
    String poLineref="";
    String blockFrom="";
    String blockto="";
    String Qty="";
    String status="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private int startPageNumberHdr = 1;
    private final int pageSizeHdr = 100;
    private int JumlahRecordHdr = 100;
    
    private int startPageNumberDtl = 1;
    private final int pageSizeDtl = 100;
    private int JumlahRecordHdrDtl = 100;
    
    Listitem selectRow = new Listitem();
    BundlingGoodsDelivery parentcontroller;
            
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        countHdr();
        countRst();
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

        int ttl = 0;
        lboxHdr.getItems().clear();
        List<ListBundlingGdEditSNParam> ListBundlingGdEditSNParam = model.selectGdListEditSNBundling("UNSELECT", poId, "",
                supDelivid, lineNo,""+activePage,""+pageSizeHdr);
        for (ListBundlingGdEditSNParam ListBundlingGdEditSNParam1 : ListBundlingGdEditSNParam) {
            Listcell po_id = new Listcell();
            Listcell position_number = new Listcell();
            Listcell serial_key_id = new Listcell();
            Listcell blockfrom = new Listcell();
            Listcell blockTo = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_line = new Listcell();
            Listcell receive_id = new Listcell();
            Listcell receive_line = new Listcell();
            Listcell created_period = new Listcell();
            Listcell qty = new Listcell();

            po_id.setLabel(ListBundlingGdEditSNParam1.getPo_id());
            position_number.setLabel(ListBundlingGdEditSNParam1.getPosition_number());
            serial_key_id.setLabel(ListBundlingGdEditSNParam1.getSerial_key_id());
            blockfrom.setLabel(ListBundlingGdEditSNParam1.getRange_from());
            blockTo.setLabel(ListBundlingGdEditSNParam1.getRange_to());
            supp_delivery_id.setLabel(ListBundlingGdEditSNParam1.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListBundlingGdEditSNParam1.getSupp_delivery_line());
            receive_id.setLabel(ListBundlingGdEditSNParam1.getReceive_id());
            receive_line.setLabel(ListBundlingGdEditSNParam1.getReceive_line());
            created_period.setLabel(ListBundlingGdEditSNParam1.getCreated_period());
            qty.setLabel(ListBundlingGdEditSNParam1.getQty());


            Listitem listitem = new Listitem();

            listitem.appendChild(po_id);
            listitem.appendChild(position_number);
            listitem.appendChild(serial_key_id);
            listitem.appendChild(blockfrom);
            listitem.appendChild(blockTo);
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(supp_delivery_line);
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_line);
            listitem.appendChild(created_period);
            listitem.appendChild(qty);

            lboxHdr.appendChild(listitem);
          
        }
//               getSNuNsELECT();
            sumSelectQty("","0", "0");

    }
    
    public void countHdr(){
        List<Integer> jumlahRecord = model.getCountEditSNHdrBundling("UNSELECT", poId, poLineref,
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
        List<Integer> jumlahRecord = model.getCountEditSNRst("SELECT", poId, "",
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
        listbox.getItems().clear();
        List<ListBundlingGdEditSNParam> ListBundlingGdEditSNParam = model.selectGdListEditSNBundling("SELECT", poId, "",
                supDelivid, lineNo,""+activePage,""+pageSizeHdr);
        int ttlrest=0;
        for (ListBundlingGdEditSNParam ListBundlingGdEditSNParam2 : ListBundlingGdEditSNParam) {
            Listcell po_id = new Listcell();
            Listcell position_number = new Listcell();
            Listcell serial_key_id = new Listcell();
            Listcell blockfrom = new Listcell();
            Listcell blockTo = new Listcell();
            Listcell supp_delivery_id = new Listcell();
            Listcell supp_delivery_line = new Listcell();
            Listcell receive_id = new Listcell();
            Listcell receive_line = new Listcell();
            Listcell created_period = new Listcell();
            Listcell qty = new Listcell();

            po_id.setLabel(ListBundlingGdEditSNParam2.getPo_id());
            position_number.setLabel(ListBundlingGdEditSNParam2.getPosition_number());
            serial_key_id.setLabel(ListBundlingGdEditSNParam2.getSerial_key_id());
            blockfrom.setLabel(ListBundlingGdEditSNParam2.getRange_from());
            blockTo.setLabel(ListBundlingGdEditSNParam2.getRange_to());
            supp_delivery_id.setLabel(ListBundlingGdEditSNParam2.getSupp_delivery_id());
            supp_delivery_line.setLabel(ListBundlingGdEditSNParam2.getSupp_delivery_line());
            receive_id.setLabel(ListBundlingGdEditSNParam2.getReceive_id());
            receive_line.setLabel(ListBundlingGdEditSNParam2.getReceive_line());
            created_period.setLabel(ListBundlingGdEditSNParam2.getCreated_period());
            qty.setLabel(ListBundlingGdEditSNParam2.getQty());


            Listitem listitem = new Listitem();

            listitem.appendChild(po_id);
            listitem.appendChild(position_number);
            listitem.appendChild(serial_key_id);
            listitem.appendChild(blockfrom);
            listitem.appendChild(blockTo);
            listitem.appendChild(supp_delivery_id);
            listitem.appendChild(supp_delivery_line);
            listitem.appendChild(receive_id);
            listitem.appendChild(receive_line);
            listitem.appendChild(created_period);
            listitem.appendChild(qty);

           listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
//                     selectRow = (Listitem) t.getTarget().getParent().getParent();
                      poLineref = ((Listcell) t.getTarget().getChildren().get(1)).getLabel();
                   blockFrom = ((Listcell) t.getTarget().getChildren().get(3)).getLabel();
                   blockto = ((Listcell) t.getTarget().getChildren().get(4)).getLabel();
                   Qty = ((Listcell) t.getTarget().getChildren().get(10)).getLabel();
                  
                }
            });
            listbox.appendChild(listitem);
            
            
        }
        sumSelectQty("R",supDelivid, lineNo);
//        getSNsELECT();

    }

    public void inputQtyForm(String action) {
        BundlingInputQtyGD BundlingInputQtyGD = new BundlingInputQtyGD();
        if (action.equals("SELECT")) {
            try{
            List<Component> listCells = lboxHdr.getSelectedItem().getChildren();
             poLineref=((Listcell) listCells.get(1)).getLabel();
             blockFrom = ((Listcell) listCells.get(3)).getLabel();
             blockto=((Listcell) listCells.get(4)).getLabel();
             Qty = ((Listcell) listCells.get(10)).getLabel();
            
            BundlingInputQtyGD.setBlockFrom(blockFrom);
            BundlingInputQtyGD.setPoLineRef(poLineref);
            BundlingInputQtyGD.setBlockto(blockto);
            BundlingInputQtyGD.setQty(Qty);
//           System.out.println(blockFrom);
//                System.out.println(poLineref);
            }
            catch(Exception e){
                Messagebox.show("Choose List Header First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }

        }
        else {
            try{
            List<Component> listCells = listbox.getSelectedItem().getChildren();
             poLineref=((Listcell) listCells.get(1)).getLabel();
             blockFrom = ((Listcell) listCells.get(3)).getLabel();
             blockto = ((Listcell) listCells.get(4)).getLabel();
             Qty = ((Listcell) listCells.get(10)).getLabel();
            BundlingInputQtyGD.setBlockFrom(blockFrom);
            BundlingInputQtyGD.setPoLineRef(poLineref);
            BundlingInputQtyGD.setBlockto(blockto);
            BundlingInputQtyGD.setQty(Qty);
               
            }
            catch(Exception e){
                Messagebox.show("Choose List Footer First !", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }

        BundlingInputQtyGD.setBundlingSelectRangeGD(this);
        BundlingInputQtyGD.setAction(action);
        BundlingInputQtyGD.setPoId(poId);
        BundlingInputQtyGD.setLineNo(lineNo);
//        BundlingInputQtyGD.setPoLineRef(poLineref);
        BundlingInputQtyGD.setSupDelivid(supDelivid);
        BundlingInputQtyGD.setInItemid(inItemid);
        BundlingInputQtyGD.setInQty(inQty.toString().replace(",", ""));
        BundlingInputQtyGD.setInOrderedQty(inOrderedQty.toString().replace(",", ""));
        BundlingInputQtyGD.setInWhId(inWhId);
        BundlingInputQtyGD.setInCityid(inCityid);
        BundlingInputQtyGD.setInHlrMapId(inHlrMapId);
        BundlingInputQtyGD.setInOrderDate(inOrderDate);
        BundlingInputQtyGD.setInPoPriceUnit(inPoPriceUnit);
        BundlingInputQtyGD.setGdDtlId(gdDtlId);
        BundlingInputQtyGD.setPostNumb(postNumb);
        Map<String, Object> map = new HashMap<>();
        map.put("controller", BundlingInputQtyGD);
        Window window = (Window) Executions.createComponents(
                "/Tcash/BundlingInputQtyCTRL.zul", null, map);
        window.doModal();
    }

    @Listen("onClick=#selectRange")
    public void selectRange() {
        inputQtyForm("SELECT");
    }

    @Listen("onClick=#unselectRange")
    public void unselectRange() {
        inputQtyForm("UNSELECT");
    }

    public void sumSelectQty(String mode,String gdId,String linegdId) {
//           List<Component> listCells = listbox.getSelectedItem().getChildren();
//           poLineref=((Listcell) listCells.get(1)).getLabel();
//            blockFrom = ((Listcell) listCells.get(3)).getLabel();
        Map<String, Object> map = model.doGdSumSelectQtyBundling(poId,
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
//           List<Component> listCells = listbox.getSelectedItem().getChildren();
//             poLineref=((Listcell) listCells.get(1)).getLabel();
//             blockFrom = ((Listcell) listCells.get(3)).getLabel();
        System.out.println("test");
        System.out.println("Update");
        System.out.println(supDelivid);
        System.out.println(poId);
        System.out.println(poLineRef);
        System.out.println(poLineRef);
        System.out.println(inItemid);
        System.out.println(qty);
        System.out.println(Qty);
        System.out.println(inWhId);
        System.out.println(inCityid);
        System.out.println(inHlrMapId);
        System.out.println(inOrderDate);
        System.out.println(lineNo);
        System.out.println(userId);
        System.out.println(inPoPriceUnit);
        System.out.println(gdDtlId);
        Map<String, Object> map = model.doGdSaveDtlBundling("UPDATE", supDelivid, poId,
                poLineRef,poLineRef, inItemid,qty.toString().replace(",", ""),qty.toString().replace(",", ""), inWhId, inCityid, inHlrMapId, inOrderDate,lineNo,userId, inPoPriceUnit,  gdDtlId);
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
//           List<Component> listCells = listbox.getSelectedItem().getChildren();
//            poLineref=((Listcell) listCells.get(1)).getLabel();
//             blockFrom = ((Listcell) listCells.get(3)).getLabel();
        Map<String, Object> map = model.doGdSelectRangeOkBundling(poId,
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
        win_select_range_GD_bundling.detach();
        BundlingGoodsDelivery.refreshDtl();
    }
    
    public void getSNsELECT(){
        Map<String,Object>map = model.doGetTotalSN("SELECT",supDelivid , lineNo, poId, poLineRef);
        txtTotal.setValue(map.get("OutTotalQty").toString());
    }
    public void getSNuNsELECT(){
        Map<String,Object>map = model.doGetTotalSN("unSELECT",supDelivid , lineNo, poId, poLineRef);
        txtTotalQty.setValue(map.get("OutTotalQty").toString());
    }
    
     public void validationButton(){
        if (status.equals("1")) {
            selectRange.setDisabled(false);
            unselectRange.setDisabled(false);
        } else {
            selectRange.setDisabled(true);
            unselectRange.setDisabled(true);
        }
    }
    
    
    public BundlingGoodsDelivery getBundlingGoodsDelivery() {
        return BundlingGoodsDelivery;
    }

    public void setBundlingGoodsDelivery(BundlingGoodsDelivery BundlingGoodsDelivery) {
        this.BundlingGoodsDelivery = BundlingGoodsDelivery;
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

    public String getPostNumb() {
        return postNumb;
    }

    public void setPostNumb(String postNumb) {
        this.postNumb = postNumb;
    }
    
}
