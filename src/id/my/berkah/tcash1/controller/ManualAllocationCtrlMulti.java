/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoListAddLineHdrParam;
import id.my.berkah.tcash1.model.ListWoListHLRDtlParam;
import id.my.berkah.tcash1.model.ListWoListHLRParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.ParseException;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class ManualAllocationCtrlMulti extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    WorkOrderCTRL workOrderCTRL;
    @Wire
    Listbox lboxHdr, lboxHlr;
    @Wire
    Window win_Manual_Alloc;
    @Wire
    Row row1;
    @Wire
    Textbox txtHlrMapId, txtMapDesc, txtLot, txtItemLocId, txtItemCode, txtItemDesc, txtQty, txtBlockFrom, txtBlockTo;
    @Wire
    Label txtTotalQty;
    @Wire
    Button btnLovHlr;
    ModelTcashCTLR model = new ModelTcashCTLR();
    String poId = "";
    String poLine = "";
    String orderType = "";
    String poLineId = "";
    String posNum = "";

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        if (orderType.equals("2") || orderType.equals("3")) {
            row1.setVisible(false);
        }
        refreshHdr();
//        String itemcnt = "" + lboxHdr.getItemCount();
//        if (!itemcnt.equals("0")) {
//            try{
//            refreshHlr();
//            }
//            catch(Exception e){
//                
//            }
//            btnLovHlr.setDisabled(true);
//        }

    }

    public void refreshHlr() {
        String hlrMapId = "";
        try {
            List<Component> listCells = lboxHdr.getItemAtIndex(0).getChildren();
            hlrMapId = ((Listcell) listCells.get(6)).getLabel();
        } catch (Exception e) {
//            e.printStackTrace();
            hlrMapId = txtHlrMapId.getValue();
        }
        int ttl = 0;
        lboxHlr.getItems().clear();
        List<ListWoListHLRDtlParam> ListWoListHLRDtlParam = model.selectWoListHLRDtl(poId, poLine, orderType, hlrMapId);
        for (ListWoListHLRDtlParam ListWoListHLRParam1 : ListWoListHLRDtlParam) {
            Listcell block_map_description = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell lot = new Listcell();
            Listcell qty = new Listcell();
            Listcell block_from = new Listcell();
            Listcell block_to = new Listcell();
            Listcell hlr_map_id = new Listcell();
            Listcell item_loc_id = new Listcell();
            Listcell is_old = new Listcell();

            block_map_description.setLabel(ListWoListHLRParam1.getBlock_Map_Description());
            item_code.setLabel(ListWoListHLRParam1.getItem_Code());
            item_description.setLabel(ListWoListHLRParam1.getItem_Description());
            lot.setLabel(ListWoListHLRParam1.getLot());
            qty.setLabel(ListWoListHLRParam1.getQty());
            block_from.setLabel(ListWoListHLRParam1.getBlock_From());
            block_to.setLabel(ListWoListHLRParam1.getBlock_To());
            hlr_map_id.setLabel(ListWoListHLRParam1.getHlr_Map_Id());
            item_loc_id.setLabel(ListWoListHLRParam1.getItem_Loc_Id());
            is_old.setLabel(ListWoListHLRParam1.getIs_old());

            Listitem listitem = new Listitem();

            listitem.appendChild(block_map_description);
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(lot);
            listitem.appendChild(qty);
            listitem.appendChild(block_from);
            listitem.appendChild(block_to);
            listitem.appendChild(hlr_map_id);
            listitem.appendChild(item_loc_id);
            listitem.appendChild(is_old);

//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });

            lboxHlr.appendChild(listitem);
        }

    }

    public void refreshHdr() {
        int ttl = 0;
        lboxHdr.getItems().clear();
        List<ListWoListHLRParam> ListWoListHLRParam = model.selectWoListHLR(poId, poLine);
        for (ListWoListHLRParam ListWoListHLRParam1 : ListWoListHLRParam) {
            Listcell po_line = new Listcell();
            Listcell po_line_id = new Listcell();
            Listcell qty = new Listcell();
            Listcell hlr_description = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell regional_code = new Listcell();
            Listcell hlr_map_id = new Listcell();
            Listcell item_loc_id = new Listcell();
            Listcell position_num = new Listcell();
            Listcell po_id = new Listcell();
            Listcell dtl_po_id = new Listcell();
            Listcell dtl_hlr_po_id = new Listcell();

            po_line.setLabel(ListWoListHLRParam1.getPo_line());
            po_line_id.setLabel(ListWoListHLRParam1.getPo_line_id());
            qty.setLabel(ListWoListHLRParam1.getQty());
            hlr_description.setLabel(ListWoListHLRParam1.getHlr_description());
            regional_description.setLabel(ListWoListHLRParam1.getRegional_description());
            regional_code.setLabel(ListWoListHLRParam1.getRegional_code());
            hlr_map_id.setLabel(ListWoListHLRParam1.getHlr_map_id());
            item_loc_id.setLabel(ListWoListHLRParam1.getItem_loc_id());
            position_num.setLabel(ListWoListHLRParam1.getPosition_num());
            po_id.setLabel(ListWoListHLRParam1.getPo_id());
            dtl_po_id.setLabel(ListWoListHLRParam1.getDtl_po_id());
            dtl_hlr_po_id.setLabel(ListWoListHLRParam1.getDtl_hlr_po_id());

            Listitem listitem = new Listitem();

            listitem.appendChild(po_line);
            listitem.appendChild(po_line_id);
            listitem.appendChild(qty);
            listitem.appendChild(hlr_description);
            listitem.appendChild(regional_description);
            listitem.appendChild(regional_code);
            listitem.appendChild(hlr_map_id);
            listitem.appendChild(item_loc_id);
            listitem.appendChild(position_num);
            listitem.appendChild(po_id);
            listitem.appendChild(dtl_po_id);
            listitem.appendChild(dtl_hlr_po_id);

//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
//                    
//                    refreshDtl(poId);
//                }
//            });
            ttl = ttl + Integer.parseInt(qty.getLabel());
            lboxHdr.appendChild(listitem);
        }
        txtTotalQty.setValue("" + ttl);
//        String itemcnt = "" + lboxHdr.getItemCount();
//        if (!itemcnt.equals("0")) {
//            btnLovHlr.setDisabled(true);
//        }else{
//            btnLovHlr.setDisabled(false);
//        }
    }

    @Listen("onClick=#btnLovHlr")
    public void btnLovHlr() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,Block_Map_Description as \"Hlr Desc\",Item_Code as \"Item Code\",Item_Description as \"Item Description\", Lot as \"Lot\", Qty as \"Qty\",Block_From as \"Block From\",Block_To as \"Block To\",Hlr_Map_Id as \"Hlr Map Id\",Item_Loc_Id as \"Item Loc Id\",is_old as \"Is Old\" from (select Block_Map_Description,Item_Code, Item_Description,Lot,Qty,Block_From,Block_To,Hlr_Map_Id,Item_Loc_Id,is_old from table(pkg_tcash_lov.LovHlrMap(" + poId + "," + poLine + "," + orderType + ",'')))\n"
                + "where (upper(BLOCK_MAP_DESCRIPTION) like upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovHlrMap(" + poId + "," + poLine + "," + orderType + ",'')) where \n"
                + "(upper(BLOCK_MAP_DESCRIPTION) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "200px", "75px", "320px", "120px", "180px", "140px", "140px", "150px", "100px", "100px", "100px", "100px"});
        composerLov.setSelectedColumn(new int[]{8, 1, 4, 9, 2, 3, 5, 6, 7});
        composerLov.setComponentTransferData(new Textbox[]{txtHlrMapId, txtMapDesc, txtLot, txtItemLocId, txtItemCode, txtItemDesc, txtQty, txtBlockFrom, txtBlockTo});
        composerLov.setHiddenColumn(new int[]{2, 3, 4, 6, 7, 8, 9, 10});

        composerLov.setTitle("List Of Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_Manual_Alloc);
        w.doModal();

//        if (!txtHlrMapId.getValue().equals("")) {
//            refreshHlr();
//        }
    }

    public void save(String itemLocId, String hlrMapId) {
        Map<String, Object> map = model.doWoSaveHlr(poId, poLine, poLineId, itemLocId, hlrMapId,
                orderType, "0");
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString());
            clearHlr();
            refreshHdr();
        }
        else {
            Messagebox.show(map.get("outMessages").toString());
        }
    }
    
    public void clearHlr(){
        txtHlrMapId.setValue(""); 
        txtMapDesc.setValue(""); 
        txtLot.setValue(""); 
        txtItemLocId.setValue("");
        txtItemCode.setValue(""); 
        txtItemDesc.setValue(""); 
        txtQty.setValue("");
        txtBlockFrom.setValue(""); 
        txtBlockTo.setValue("");
    }

    @Listen("onClick=#btnSave")
    public void btnSave() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {

//                    for (int i = 0; i < lboxHlr.getItemCount(); i++) {
//                        if (lboxHlr.getItems().get(i).isSelected()) {
//                            List<Component> listCells = lboxHlr.getItemAtIndex(i).getChildren();

//                            String hlrMapId = ((Listcell) listCells.get(7)).getLabel();
//                            String itemLocId = ((Listcell) listCells.get(8)).getLabel();
                            save(txtItemLocId.getValue(), txtHlrMapId.getValue());
//                        }
//                    }
                }
            }
        };
        Messagebox.show("Are you sure want to save this transaction ?", "Message ", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);

    }

    @Listen("onClick=#btnDelete")
    public void deleteHlr() {
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    delete();
                }
                String itemcnt = "" + lboxHdr.getItemCount();
                if (!itemcnt.equals("0")) {
                    btnLovHlr.setDisabled(false);
                }
            }
        };
        Messagebox.show("Are you sure want to delete this record?", "Message", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);

    }

    public void delete() {
        List<Component> listCells = lboxHdr.getSelectedItem().getChildren();
        String dtlHlrPoId = ((Listcell) listCells.get(11)).getLabel();
        String qty = ((Listcell) listCells.get(2)).getLabel();
        Map<String, Object> map = model.doWoDeleteHlr(poId, orderType, dtlHlrPoId, qty);
        if (map.get("outError").toString().equals("0")) {
            Messagebox.show(map.get("outMessages").toString());
            refreshHdr();
        }
        else {
            Messagebox.show(map.get("outMessages").toString());
        }
    }

    public void detailForm() {
        try {
            List<Component> listCells = lboxHdr.getSelectedItem().getChildren();

            String inOrderQty = ((Listcell) listCells.get(2)).getLabel();
            String hlrMapId = ((Listcell) listCells.get(6)).getLabel();
            String itemLocId = ((Listcell) listCells.get(7)).getLabel();
            String dtlHlrPoId = ((Listcell) listCells.get(11)).getLabel();
            String posNum = ((Listcell) listCells.get(8)).getLabel();
            String dtlPoId = ((Listcell) listCells.get(10)).getLabel();
            String idPoLine = ((Listcell) listCells.get(1)).getLabel();

            SelectRangeCtrl selectRangeCtrl = new SelectRangeCtrl();
            selectRangeCtrl.setManualAllocationCtrlMulti(this);
            selectRangeCtrl.setInHlrMapId(hlrMapId);
            selectRangeCtrl.setItemLocId(itemLocId);
            selectRangeCtrl.setOrderType(orderType);
            selectRangeCtrl.setDtlHlrPoId(dtlHlrPoId);
            selectRangeCtrl.setPoId(poId);
            selectRangeCtrl.setPoLine(poLine);
            selectRangeCtrl.setPoLineId(idPoLine);
            selectRangeCtrl.setInOrderQty(inOrderQty);
            selectRangeCtrl.setPosNum(posNum);
            selectRangeCtrl.setDtlPoId(dtlPoId);
            Map<String, Object> map = new HashMap<>();
            map.put("controller", selectRangeCtrl);
            Window window = (Window) Executions.createComponents(
                    "/Tcash/SelectRange.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Choose List First");
            e.printStackTrace();
            return;
        }
    }

    @Listen("onClick=#btnDetail")
    public void klikBtnDetail() {
        detailForm();
    }

    @Listen("onClick=#btnClose")
    public void btnClose() throws ParseException {
        win_Manual_Alloc.detach();
        workOrderCTRL.headerList();
        workOrderCTRL.refreshDetail();
    }

    public WorkOrderCTRL getWorkOrderCTRL() {
        return workOrderCTRL;
    }

    public void setWorkOrderCTRL(WorkOrderCTRL workOrderCTRL) {
        this.workOrderCTRL = workOrderCTRL;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
}
