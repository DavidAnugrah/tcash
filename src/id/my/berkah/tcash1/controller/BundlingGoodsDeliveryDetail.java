/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.GdListPoItemBundlingParam;
import id.my.berkah.tcash1.model.ListGdItemParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class BundlingGoodsDeliveryDetail extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_GD_detail_bundling;
    @Wire
    Listbox listbox;
    @Wire
    Textbox bndlingId, gdID, whId;
    @Wire
    Datebox txtprodate;
    ModelTcashCTLR model = new ModelTcashCTLR();
    BundlingGoodsDelivery parentController;

    @Listen("onCreate=#win_GD_detail_bundling")
    public void whenOncreateWindow() {
//           if (txtitemcode.getText().equals("")) {
//             flag.setText("INSERT");
//         } else {
//               flag.setText("UPDATE");
//         }
        parentController = (BundlingGoodsDelivery) win_GD_detail_bundling.getAttribute("parentController");

        refreshDtl1();
    }

//    public void refreshDtl() {
//        listbox.getItems().clear();
//        listbox.setSizedByContent(true);
//        List<GdListPoItemBundlingParam> GdListPoItemBundlingParam = model.selectGdListPoItemBundling(bndlingId.getText(),whId.getText(),gdID.getText());
//        listbox.setModel(new ListModelList<GdListPoItemBundlingParam>(GdListPoItemBundlingParam));
//        
//    }
//    public void setRender(){
//        listbox.setItemRenderer(new ListitemRenderer<GdListPoItemBundlingParam>() {
//
//            @Override
//            public void render(Listitem lstm, GdListPoItemBundlingParam t, int i) throws Exception {
////                 Listcell lc=new Listcell();
////                Checkbox cb = new Checkbox();
////                lc.appendChild(cb);
////                lstm.appendChild(lc);
////                CHelper.Listbox_addLabel(lstm, "" + (i + 1), "right");
//                new Listcell(t.getBundling_id()).setParent(lstm);
//                new Listcell(t.getBundling_no()).setParent(lstm);
//                new Listcell(t.getPo_id()).setParent(lstm);
//                new Listcell(t.getPosition_number()).setParent(lstm);
//                new Listcell(t.getItem_id()).setParent(lstm);
//                new Listcell(t.getItem_code()).setParent(lstm);
//                new Listcell(t.getItem_description()).setParent(lstm);
//                new Listcell(t.getBundling_date()).setParent(lstm);
//                new Listcell(t.getQty()).setParent(lstm);
//                new Listcell(t.getWarehouse_id()).setParent(lstm);
//                new Listcell(t.getWh_id()).setParent(lstm);
//                new Listcell(t.getBu_id()).setParent(lstm);
//                new Listcell(t.getRange_from()).setParent(lstm);
//                new Listcell(t.getRange_to()).setParent(lstm);
//                
//            }
//        });
//    }
    @Listen("onClick=#close")
    public void close() {
        win_GD_detail_bundling.detach();
    }

    @Listen("onClick=#save")
    public void save() {
        List list = new ArrayList();
        List listSave = new ArrayList();

        for (int i = 0; i < listbox.getItemCount(); i++) {

            if (listbox.getItems().get(i).isSelected()) {
                listSave.add(i);
            } else {
                list.add(i);
            }
        }

        if (list.size() == listbox.getItemCount()) {
            Messagebox.show("Please Choose detail first", "Goods DeliveryBundling", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        list.clear();

        for (int x = 0; x < listSave.size(); x++) {
            int i = Integer.valueOf(listSave.get(x).toString());
            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();
            String inLine = ((Listcell) listCells.get(i)).getLabel();
            String inItemCode = ((Listcell) listCells.get(0)).getLabel();
            String inItemDesc = ((Listcell) listCells.get(1)).getLabel();
            String inBundlingDate = ((Listcell) listCells.get(2)).getLabel();
            String inNo = ((Listcell) listCells.get(3)).getLabel();
            String inORderQty = ((Listcell) listCells.get(4)).getLabel();
            String inTotalGDQty = ((Listcell) listCells.get(5)).getLabel();
            String inQty = ((Listcell) listCells.get(6)).getLabel();
            String inWareHouseId = ((Listcell) listCells.get(7)).getLabel();
            String inwhId = ((Listcell) listCells.get(8)).getLabel();
            String inBuId = ((Listcell) listCells.get(9)).getLabel();
            String inRangfrom = ((Listcell) listCells.get(10)).getLabel();
            String inRangTo = ((Listcell) listCells.get(11)).getLabel();
            String inID = ((Listcell) listCells.get(12)).getLabel();
            String inpoId = ((Listcell) listCells.get(13)).getLabel();
            String inPositionNum = ((Listcell) listCells.get(14)).getLabel();
            String inItemid = ((Listcell) listCells.get(15)).getLabel();
//
            System.out.println(inLine + "line");
            System.out.println(inItemCode + "itemcode");
            System.out.println(inItemDesc + "itemdesc");
            System.out.println(inBundlingDate + "date");
            System.out.println(inNo + "no");
            System.out.println(inQty + "qty");
            System.out.println(inWareHouseId + "whid");
            System.out.println(inwhId + "whidk");
            System.out.println(inBuId + "buid");
            System.out.println(inRangfrom + "from");
            System.out.println(inRangTo + "to");
            System.out.println(inID + "id");
            System.out.println(inpoId + "poid");
            System.out.println(inPositionNum + "postnum");
            System.out.println(inItemid + "item");
            Map<String, Object> map = model.doGdSaveDtlBundling("INSERT", gdID.getText(), inpoId,
                    inPositionNum, inPositionNum, inItemid, inQty.toString().replace(",", ""), inQty.toString().replace(",", ""), inwhId, "", "", inBundlingDate, "", userId, "", "");
            if (map.get("outError").toString().trim().equals("0")) {
                list.add(i);
            } else {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
            
            if (list.size() == listSave.size()) {
                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.INFORMATION);
                parentController.refreshDtl();
                win_GD_detail_bundling.detach();
            }else{
                System.out.println("");
            }
        }

    }

    public void refreshDtl1() {
        listbox.getItems().clear();
        List<GdListPoItemBundlingParam> GdListPoItemBundlingParam = model.selectGdListPoItemBundling(bndlingId.getText(), whId.getText(), gdID.getText());
        listbox.setSizedByContent(true);
        for (GdListPoItemBundlingParam GdListPoItemBundlingParam1 : GdListPoItemBundlingParam) {
            Listcell id = new Listcell();
            Listcell bundlingno = new Listcell();
            Listcell po_id = new Listcell();
            Listcell position_num = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell bundingdate = new Listcell();
            Listcell Orderedquantity = new Listcell();
            Listcell TotalGdquantity = new Listcell();
            Listcell quantity = new Listcell();
            Listcell warehouse_id = new Listcell();
            Listcell wh_id = new Listcell();
            Listcell buID = new Listcell();
            Listcell range_from = new Listcell();
            Listcell Range_to = new Listcell();

            id.setLabel(GdListPoItemBundlingParam1.getBundling_id());
            bundlingno.setLabel(GdListPoItemBundlingParam1.getBundling_no());
            po_id.setLabel(GdListPoItemBundlingParam1.getPo_id());
            position_num.setLabel(GdListPoItemBundlingParam1.getPosition_number());
            item_id.setLabel(GdListPoItemBundlingParam1.getItem_id());
            item_code.setLabel(GdListPoItemBundlingParam1.getItem_code());
            item_description.setLabel(GdListPoItemBundlingParam1.getItem_description());
            bundingdate.setLabel(GdListPoItemBundlingParam1.getBundling_date());
            Orderedquantity.setLabel(GdListPoItemBundlingParam1.getOrdered_qty());
            TotalGdquantity.setLabel(GdListPoItemBundlingParam1.getTotal_gd_qty());
            quantity.setLabel(GdListPoItemBundlingParam1.getQty());
            warehouse_id.setLabel(GdListPoItemBundlingParam1.getWh_id());
            wh_id.setLabel(GdListPoItemBundlingParam1.getWh_id());
            buID.setLabel(GdListPoItemBundlingParam1.getBu_id());
            range_from.setLabel(GdListPoItemBundlingParam1.getRange_from());
            Range_to.setLabel(GdListPoItemBundlingParam1.getRange_to());

            Orderedquantity.setStyle("text-align: right");
            TotalGdquantity.setStyle("text-align: right");
            quantity.setStyle("text-align: right");

            Listitem listitem = new Listitem();

            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(bundingdate);
            listitem.appendChild(bundlingno);
            listitem.appendChild(Orderedquantity);
            listitem.appendChild(TotalGdquantity);
            listitem.appendChild(quantity);
            listitem.appendChild(warehouse_id);
            listitem.appendChild(wh_id);
            listitem.appendChild(buID);
            listitem.appendChild(range_from);
            listitem.appendChild(Range_to);
            listitem.appendChild(id);
            listitem.appendChild(po_id);
            listitem.appendChild(position_num);
            listitem.appendChild(item_id);
//            chbx.addEventListener(Events.ON_CHECK, new EventListener() {
//                public void onEvent(Event t) throws Exception {
//                    if (chbx.isChecked()) {
//                        System.out.println(reqId);
//                        System.out.println("checked");
//                        selectRow = (Listitem) t.getTarget().getParent().getParent();
//                        System.out.println(t.getTarget());
//                        String poLineId = ((Listcell) selectRow.getChildren().get(1)).getLabel();
//                        System.out.println(poLineId);
//                    }
//                    else {
//                        System.out.println("unchecked");
//                    }
//                }
//            });

            listbox.appendChild(listitem);
        }
    }

}
