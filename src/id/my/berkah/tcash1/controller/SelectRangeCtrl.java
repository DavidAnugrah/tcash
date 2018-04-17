/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoListAllocateINVParam;
import id.my.berkah.tcash1.model.ListWoListAllocateNBRParam;
import id.my.berkah.tcash1.model.ListWoListAllocateNumberingTempParam;
import id.my.berkah.tcash1.model.ListWoListHLRParam;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
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
public class SelectRangeCtrl extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Listbox lboxHdr, listbox;
    @Wire
    Label txtTotal, txtTotalQty;
    @Wire
    Window win_select_range;
    
    @Wire
    Paging userPaging,userPagingDtl;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    String itemLocId = "";
    String inItemLocIdHdr = "";
    String inHlrMapId = "";
    String orderType = "";
    String dtlHlrPoId = "";
    String tmpFrom = "";
    String tmpTo = "";
    String tmpQty = "";
    String poId = "";
    String poLine = "";
    String poLineId = "";
    String inOrderQty = "";
    String posNum="";
    String dtlPoId="";
    ManualAllocationCtrlMulti ManualAllocationCtrlMulti;
    
    private int startPageNumberHdr = 1;
    private final int pageSizeHdr = 100;
    private int JumlahRecordHdr = 100;
    
    private int startPageNumberDtl = 1;
    private final int pageSizeDtl = 100;
    private int JumlahRecordHdrDtl = 100;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        if (orderType.equals("1")) {
            countINV();
            countINVDtl();
//            woAllocateINVTemp();
        }
        else {
            countNBR();
            countNBRDtl();
//            woAllocateNBRTemp();
        }
//        userPaging.setPageSize(pageSize);
//        userPaging.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
//        userPaging.setActivePage(3);
    }

    public void woAllocateNBRTemp() {
        Map<String, Object> map = model.doWoAllocateINVTemp(itemLocId, inHlrMapId, userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void woAllocateINVTemp() {
        Map<String, Object> map = model.doWoAllocateINVTemp(itemLocId, inHlrMapId, userId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
    
    @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumberHdr = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumberHdr);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSizeHdr);
        if (orderType.equals("1")) {
            refreshINV(activePage);
//            woAllocateINVTemp();
        }
        else {
            refreshNbr(activePage);
//            woAllocateNBRTemp();
        }
//        refresh(activePage);//, pageSize);
    }

    public void refreshNbr(int activePage) {
        try {
            System.out.println("refreshNbr");
            lboxHdr.getItems().clear();
            List<ListWoListAllocateNumberingTempParam> ListWoListAllocateNumberingTempParam = model.selectWoListAllocateNumberingTemp(itemLocId, inHlrMapId, userId,
                    ""+activePage,""+pageSizeHdr);
            try {
                if (!ListWoListAllocateNumberingTempParam.get(0).getMessages().equals("")) {
                    Messagebox.show(ListWoListAllocateNumberingTempParam.get(0).getMessages(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            tmpFrom = ListWoListAllocateNumberingTempParam.get(0).getBlock_From();
            tmpTo = ListWoListAllocateNumberingTempParam.get(0).getBlock_To();
            tmpQty = "1";
            inItemLocIdHdr = ListWoListAllocateNumberingTempParam.get(0).getItem_Loc_Id();

            for (ListWoListAllocateNumberingTempParam ListWoListAllocateNumberingTempParam1 : ListWoListAllocateNumberingTempParam) {
                Listcell Item_Detail_Id = new Listcell();
                Listcell Storage_Id = new Listcell();
                Listcell Store_Status = new Listcell();
                Listcell Block_From = new Listcell();
                Listcell Block_To = new Listcell();
                Listcell Item_Id = new Listcell();
                Listcell Item_Loc_Id = new Listcell();
                Listcell Session_Id = new Listcell();
                Listcell Messages = new Listcell();
                Listcell hlr_map_desc = new Listcell();
                Listcell qty = new Listcell();

                Item_Detail_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Detail_Id());
                Storage_Id.setLabel(ListWoListAllocateNumberingTempParam1.getStorage_Id());
                Store_Status.setLabel(ListWoListAllocateNumberingTempParam1.getStore_Status());
                Block_From.setLabel(ListWoListAllocateNumberingTempParam1.getBlock_From());
                Block_To.setLabel(ListWoListAllocateNumberingTempParam1.getBlock_To());
                Item_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Id());
                Item_Loc_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Loc_Id());
                Session_Id.setLabel(ListWoListAllocateNumberingTempParam1.getSession_Id());
                Messages.setLabel(ListWoListAllocateNumberingTempParam1.getMessages());
                hlr_map_desc.setLabel(ListWoListAllocateNumberingTempParam1.getHlr_map_desc());
                qty.setLabel("1");

                qty.setStyle("text-align: right");

                Listitem listitem = new Listitem();

                listitem.appendChild(Item_Detail_Id);
                listitem.appendChild(Storage_Id);
                listitem.appendChild(Store_Status);
                listitem.appendChild(hlr_map_desc);
                listitem.appendChild(Block_From);
                listitem.appendChild(Block_To);
                listitem.appendChild(qty);
                listitem.appendChild(Item_Id);
                listitem.appendChild(Item_Loc_Id);
                listitem.appendChild(Session_Id);
                listitem.appendChild(Messages);

//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });

                lboxHdr.appendChild(listitem);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshINV(int activePage) {
        try {
            lboxHdr.getItems().clear();
            List<ListWoListAllocateNumberingTempParam> ListWoListAllocateNumberingTempParam = model.selectWoListAllocateINVTemp(itemLocId, inHlrMapId, userId,
                    ""+activePage,""+pageSizeHdr);
            try {
                if (!ListWoListAllocateNumberingTempParam.get(0).getMessages().equals("")) {
                    Messagebox.show(ListWoListAllocateNumberingTempParam.get(0).getMessages());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                tmpFrom = ListWoListAllocateNumberingTempParam.get(0).getBlock_From();
                tmpTo = ListWoListAllocateNumberingTempParam.get(0).getBlock_To();
                tmpQty = ListWoListAllocateNumberingTempParam.get(0).getQty();
            }
            int ttl = 0;
            for (ListWoListAllocateNumberingTempParam ListWoListAllocateNumberingTempParam1 : ListWoListAllocateNumberingTempParam) {
                Listcell Item_Detail_Id = new Listcell();
                Listcell Storage_Id = new Listcell();
                Listcell Store_Status = new Listcell();
                Listcell Block_From = new Listcell();
                Listcell Block_To = new Listcell();
                Listcell Item_Id = new Listcell();
                Listcell Item_Loc_Id = new Listcell();
                Listcell Session_Id = new Listcell();
                Listcell Messages = new Listcell();
                Listcell hlr_map_desc = new Listcell();
                Listcell qty = new Listcell();

                Item_Detail_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Detail_Id());
                Storage_Id.setLabel(ListWoListAllocateNumberingTempParam1.getStorage_Id());
                Store_Status.setLabel(ListWoListAllocateNumberingTempParam1.getStore_Status());
                Block_From.setLabel(ListWoListAllocateNumberingTempParam1.getBlock_From());
                Block_To.setLabel(ListWoListAllocateNumberingTempParam1.getBlock_To());
                Item_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Id());
                Item_Loc_Id.setLabel(ListWoListAllocateNumberingTempParam1.getItem_Loc_Id());
                Session_Id.setLabel(ListWoListAllocateNumberingTempParam1.getSession_Id());
                Messages.setLabel(ListWoListAllocateNumberingTempParam1.getMessages());
                hlr_map_desc.setLabel(ListWoListAllocateNumberingTempParam1.getHlr_map_desc());
                qty.setLabel(ListWoListAllocateNumberingTempParam1.getQty());

                qty.setStyle("text-align: right");

                Listitem listitem = new Listitem();

                listitem.appendChild(Item_Detail_Id);
                listitem.appendChild(Storage_Id);
                listitem.appendChild(Store_Status);
                listitem.appendChild(hlr_map_desc);
                listitem.appendChild(Block_From);
                listitem.appendChild(Block_To);
                listitem.appendChild(qty);
                listitem.appendChild(Item_Id);
                listitem.appendChild(Item_Loc_Id);
                listitem.appendChild(Session_Id);
                listitem.appendChild(Messages);


//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });
                ttl= ttl+ Integer.parseInt(ListWoListAllocateNumberingTempParam1.getQty());
                txtTotal.setValue(""+ttl);
                lboxHdr.appendChild(listitem);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Listen("onPaging=#userPagingDtl")
    public void onPagingUserPagingDtl(PagingEvent pe) {
        startPageNumberDtl = pe.getActivePage() + 1;
        refreshModelTcashCTLRDtl(startPageNumberDtl);
    }

    private void refreshModelTcashCTLRDtl(int activePage) {
        userPaging.setPageSize(pageSizeDtl);
        if (orderType.equals("1")) {
            refreshDtlInv(activePage);
//            woAllocateINVTemp();
        }
        else {
            refreshDtlNbr(activePage);
//            woAllocateNBRTemp();
        }
//        refresh(activePage);//, pageSize);
    }
    
    public void countNBR(){
        List<Integer> jumlahRecord = model.getCountAllocateNBR(itemLocId, inHlrMapId, userId);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdr = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPaging.setPageSize(pageSizeHdr);
        userPaging.setTotalSize(JumlahRecordHdr);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        userPaging.setActivePage(0);
        txtTotal.setValue(""+JumlahRecordHdr);
//        refreshModelTcashCTLR(1);
        refreshNbr(1);
    }
    
    public void countINV(){
        List<Integer> jumlahRecord = model.getCountAllocateINV(itemLocId, inHlrMapId, userId);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdr = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPaging.setPageSize(pageSizeHdr);
        userPaging.setTotalSize(JumlahRecordHdr);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        userPaging.setActivePage(0);
//        refreshModelTcashCTLR(1);
        refreshINV(1);
    }
    
    public void countNBRDtl(){
        List<Integer> jumlahRecord = model.getCountAllocateNBRDtl(poId,poLine);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdr = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPagingDtl.setPageSize(pageSizeDtl);
        userPagingDtl.setTotalSize(JumlahRecordHdrDtl);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        userPagingDtl.setActivePage(0);
        txtTotalQty.setValue(""+JumlahRecordHdrDtl);
//        refreshModelTcashCTLR(1);
        refreshDtlNbr(1);
    }
    public void countINVDtl(){
        List<Integer> jumlahRecord = model.getCountAllocateINVDtl(dtlPoId,itemLocId);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecordHdr = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        userPagingDtl.setPageSize(pageSizeDtl);
        userPagingDtl.setTotalSize(JumlahRecordHdrDtl);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        userPagingDtl.setActivePage(0);
        txtTotalQty.setValue(""+JumlahRecordHdrDtl);
//        refreshModelTcashCTLR(1);
        refreshDtlInv(1);
    }

    public void refreshDtlNbr(int activePage) {
        try {
            System.out.println("refreshDtl");
            listbox.getItems().clear();
            List<ListWoListAllocateNBRParam> ListWoListAllocateNBRParam = model.selectWoListAllocateNBR(poId,posNum,
                    ""+activePage,""+pageSizeHdr);
            for (ListWoListAllocateNBRParam ListWoListAllocateNBRParam1 : ListWoListAllocateNBRParam) {
                Listcell po_id = new Listcell();
                Listcell position_number = new Listcell();
                Listcell seq_id = new Listcell();
                Listcell hlr = new Listcell();
                Listcell blockFrom = new Listcell();
                Listcell blockTo = new Listcell();
                Listcell match_seq = new Listcell();
                Listcell store_status = new Listcell();
                Listcell created_period = new Listcell();
                Listcell qty = new Listcell();

                po_id.setLabel(ListWoListAllocateNBRParam1.getPo_id());
                position_number.setLabel(ListWoListAllocateNBRParam1.getPosition_number());
                seq_id.setLabel(ListWoListAllocateNBRParam1.getSeq_id());
                hlr.setLabel(ListWoListAllocateNBRParam1.getSeq_id());
                blockFrom.setLabel(ListWoListAllocateNBRParam1.getSerial_no());
                blockTo.setLabel(ListWoListAllocateNBRParam1.getSerial_no());
                match_seq.setLabel(ListWoListAllocateNBRParam1.getMatch_seq());
                store_status.setLabel(ListWoListAllocateNBRParam1.getStore_status());
                created_period.setLabel(ListWoListAllocateNBRParam1.getCreated_period());
                qty.setLabel("1");

                qty.setStyle("text-align: right");

                Listitem listitem = new Listitem();

                listitem.appendChild(po_id);
                listitem.appendChild(position_number);
                listitem.appendChild(seq_id);
                listitem.appendChild(hlr);
                listitem.appendChild(blockFrom);
                listitem.appendChild(blockTo);
                listitem.appendChild(qty);
                listitem.appendChild(match_seq);
                listitem.appendChild(store_status);
                listitem.appendChild(created_period);

                listbox.appendChild(listitem);

            }
            txtTotalQty.setValue("" + listbox.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refreshDtlInv(int activePage) {
        try {
            int ttl=0;
            System.out.println("refreshInvDtl");
            listbox.getItems().clear();
            List<ListWoListAllocateINVParam> ListWoListAllocateINVParam = model.selectWoListAllocateINV(dtlPoId,itemLocId,
                    ""+activePage,""+pageSizeHdr);
            for (ListWoListAllocateINVParam ListWoListAllocateINVParam1 : ListWoListAllocateINVParam) {
                Listcell allocation_id = new Listcell();
                Listcell created_period = new Listcell();
                Listcell dtl_po_id = new Listcell();
                Listcell item_detail_id = new Listcell();
                Listcell item_id = new Listcell();
                Listcell item_loc_id = new Listcell();
                Listcell lot_id = new Listcell();
                Listcell qty = new Listcell();
                Listcell range_from = new Listcell();
                Listcell range_to = new Listcell();
                Listcell wh_id = new Listcell();
                Listcell wh_loc_id = new Listcell();

                allocation_id.setLabel(ListWoListAllocateINVParam1.getAllocation_id());
                created_period.setLabel(ListWoListAllocateINVParam1.getCreated_period());
                dtl_po_id.setLabel(ListWoListAllocateINVParam1.getDtl_po_id());
                item_detail_id.setLabel(ListWoListAllocateINVParam1.getItem_detail_id());
                item_id.setLabel(ListWoListAllocateINVParam1.getItem_id());
                item_loc_id.setLabel(ListWoListAllocateINVParam1.getItem_loc_id());
                lot_id.setLabel(ListWoListAllocateINVParam1.getLot_id());
                qty.setLabel(ListWoListAllocateINVParam1.getQty());
                range_from.setLabel(ListWoListAllocateINVParam1.getRange_from());
                range_to.setLabel(ListWoListAllocateINVParam1.getRange_to());
                wh_id.setLabel(ListWoListAllocateINVParam1.getWh_id());
                wh_loc_id.setLabel(ListWoListAllocateINVParam1.getWh_loc_id());

                qty.setStyle("text-align: right");

                Listitem listitem = new Listitem();

                listitem.appendChild(allocation_id);
                listitem.appendChild(created_period);
                listitem.appendChild(dtl_po_id);
                listitem.appendChild(item_detail_id);
                listitem.appendChild(range_from);
                listitem.appendChild(range_to);
                listitem.appendChild(qty);
                listitem.appendChild(item_id);
                listitem.appendChild(item_loc_id);
                listitem.appendChild(lot_id);
                listitem.appendChild(wh_id);
                listitem.appendChild(wh_loc_id);


                ttl = ttl + Integer.parseInt(qty.getLabel());
                listbox.appendChild(listitem);

            }
            txtTotalQty.setValue("" + ttl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputQtyForm(String action) {
        InputQtyCtrl inputQtyCtrl = new InputQtyCtrl();
        inputQtyCtrl.setSelectRangeCtrl(this);
        if (action.equals("SELECT")) {
            List<Component> listCells = lboxHdr.getSelectedItem().getChildren();

            String blockFrom = ((Listcell) listCells.get(4)).getLabel();
            String blockto = ((Listcell) listCells.get(5)).getLabel();
            String Qty = ((Listcell) listCells.get(6)).getLabel();
            String storeStatus = ((Listcell) listCells.get(2)).getLabel();

            inputQtyCtrl.setBlockFrom(blockFrom);
            inputQtyCtrl.setBlockto(blockto);
            inputQtyCtrl.setQty(Qty);
            inputQtyCtrl.setStoreStatus(storeStatus);
        }
        else {
            List<Component> listCells = listbox.getSelectedItem().getChildren();

            String blockFrom = ((Listcell) listCells.get(4)).getLabel();
            String blockto = ((Listcell) listCells.get(5)).getLabel();
            String Qty = ((Listcell) listCells.get(6)).getLabel();
            inputQtyCtrl.setBlockFrom(blockFrom);
        }

        inputQtyCtrl.setItemLocId(itemLocId);
        inputQtyCtrl.setTmpFrom(tmpFrom);
        inputQtyCtrl.setTmpTo(tmpTo);
        inputQtyCtrl.setTmpQty(tmpQty);
        inputQtyCtrl.setPoId(poId);
        inputQtyCtrl.setPoLine(poLine);
        inputQtyCtrl.setInOrderQty(inOrderQty);
        inputQtyCtrl.setInHlrMapId(inHlrMapId);
        inputQtyCtrl.setOrderType(orderType);
        inputQtyCtrl.setDtlHlrPoId(dtlHlrPoId);
        inputQtyCtrl.setAction(action);
        inputQtyCtrl.setPoLineId(poLineId);
        inputQtyCtrl.setPosNum(posNum);
        inputQtyCtrl.setDtlPoId(dtlPoId);
        Map<String, Object> map = new HashMap<>();
        map.put("controller", inputQtyCtrl);
        Window window = (Window) Executions.createComponents(
                "/Tcash/InputQty.zul", null, map);
        window.doModal();
    }

    public void validateStatus(String action) {
        Map<String, Object> map = model.doWoValidateStatus(poId);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            inputQtyForm(action);
        }
        else {
            Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#selectRange")
    public void selectRange() {
        validateStatus("SELECT");
    }

    @Listen("onClick=#unselectRange")
    public void unselectRange() {
        validateStatus("UNSELECT");
    }

    @Listen("onClick=#btnSubmit")
    public void btnSubmit() {
        ManualAllocationCtrlMulti.refreshHdr();
//        ManualAllocationCtrlMulti.refreshHlr();
        win_select_range.detach();
    }

    public ManualAllocationCtrlMulti getManualAllocationCtrlMulti() {
        return ManualAllocationCtrlMulti;
    }

    public void setManualAllocationCtrlMulti(ManualAllocationCtrlMulti ManualAllocationCtrlMulti) {
        this.ManualAllocationCtrlMulti = ManualAllocationCtrlMulti;
    }

    public String getItemLocId() {
        return itemLocId;
    }

    public void setItemLocId(String itemLocId) {
        this.itemLocId = itemLocId;
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

    public String getDtlHlrPoId() {
        return dtlHlrPoId;
    }

    public void setDtlHlrPoId(String dtlHlrPoId) {
        this.dtlHlrPoId = dtlHlrPoId;
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

    public String getInOrderQty() {
        return inOrderQty;
    }

    public void setInOrderQty(String inOrderQty) {
        this.inOrderQty = inOrderQty;
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
