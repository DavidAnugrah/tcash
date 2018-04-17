/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGdItemParam;
import id.my.berkah.tcash1.model.ListWoListAddLineDtlParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class GoodDeliveryDetailCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_GD_detail;
    @Wire
    Listbox listbox;
    @Wire
    Textbox txtitemid, txtitemcode, txtitemdesc, txtregdesc, txthlr, txtblokfrom, txtuom, txtblokto, txtorderqty,
            txtdeliveryqty, txtotal, txtoutstanding, txtlineremark, txtstatus, txtsuppdeliveryid, txtpoid, txtsuppdeliverydate,
            txtpo, txtlineno, flag, txtcreateddate, txtmodifdate, txtcreatedby, txtmodifiedby, txtid, txtcityid, txtwhid, InOrderDate, txtorderdate, txtcreatperiode;
    @Wire
    Datebox txtprodate;
    ModelTcashCTLR model = new ModelTcashCTLR();
    GoodDeliveryCTRL goodDeliveryCTRL;
    String poId = "";
    String gdHdrId = "";
    String whId="";

    @Listen("onCreate=#win_GD_detail")
    public void whenOncreateWindow() {
//           if (txtitemcode.getText().equals("")) {
//             flag.setText("INSERT");
//         } else {
//               flag.setText("UPDATE");
//         }
        refreshDtl();
    }

    public void refreshDtl() {
        listbox.getItems().clear();
        List<ListGdItemParam> ListGdItemParam = model.selectGdListPoItem(poId,whId,gdHdrId);
        for (ListGdItemParam ListGdItemParam1 : ListGdItemParam) {
            Listcell po_id = new Listcell();
            Listcell position_num = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell order_date = new Listcell();
            Listcell po_price_unit = new Listcell();
            Listcell ordered_quantity = new Listcell();
            Listcell warehouse_id = new Listcell();
            Listcell delivered_qty = new Listcell();
            Listcell qty = new Listcell();
            Listcell wh_id = new Listcell();
            Listcell city_id = new Listcell();
            Listcell city_desc = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell bu_id = new Listcell();
            Listcell hlr_map_id = new Listcell();
            Listcell range_from = new Listcell();
            Listcell Range_to = new Listcell();

//            Listcell chk = new Listcell();
//            final Checkbox chbx = new Checkbox();

            po_id.setLabel(ListGdItemParam1.getPo_id());
            position_num.setLabel(ListGdItemParam1.getPosition_num());
            item_id.setLabel(ListGdItemParam1.getItem_id());
            item_code.setLabel(ListGdItemParam1.getItem_code());
            item_description.setLabel(ListGdItemParam1.getItem_description());
            order_date.setLabel(ListGdItemParam1.getOrder_date());
            po_price_unit.setLabel(ListGdItemParam1.getPo_price_unit());
            ordered_quantity.setLabel(ListGdItemParam1.getOrdered_quantity());
            warehouse_id.setLabel(ListGdItemParam1.getWh_id());
            delivered_qty.setLabel(ListGdItemParam1.getDelivered_qty());
            qty.setLabel(ListGdItemParam1.getQty());
            wh_id.setLabel(ListGdItemParam1.getWh_id());
            city_id.setLabel(ListGdItemParam1.getCity_id());
            city_desc.setLabel(ListGdItemParam1.getCity_desc());
            regional_id.setLabel(ListGdItemParam1.getRegional_id());
            city_desc.setLabel(ListGdItemParam1.getCity_desc());
            regional_description.setLabel(ListGdItemParam1.getRegional_description());
            bu_id.setLabel(ListGdItemParam1.getBu_id());
            hlr_map_id.setLabel(ListGdItemParam1.getHlr_map_id());
            range_from.setLabel(ListGdItemParam1.getRange_from());
            Range_to.setLabel(ListGdItemParam1.getRange_to());
            
            ordered_quantity.setStyle("text-align: right");
            delivered_qty.setStyle("text-align: right");
            qty.setStyle("text-align: right");

//            chk.appendChild(chbx);

            Listitem listitem = new Listitem();
//            listitem.appendChild(chk);
            
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(order_date);
            listitem.appendChild(po_price_unit);
            listitem.appendChild(ordered_quantity);
            listitem.appendChild(warehouse_id);
            listitem.appendChild(delivered_qty);
            listitem.appendChild(qty);//7
            listitem.appendChild(wh_id);
            listitem.appendChild(city_id);
            listitem.appendChild(city_desc);
            listitem.appendChild(regional_id);//11
            listitem.appendChild(regional_description);
            listitem.appendChild(bu_id);
            listitem.appendChild(hlr_map_id);
            listitem.appendChild(range_from);
            listitem.appendChild(Range_to);
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

    @Listen("onClick=#close")
    public void close() {
        win_GD_detail.detach();
    }

    @Listen("onClick=#save")
    public void save() {
//        Messagebox.show("Are you sure want to save?",
//                "Question", Messagebox.OK | Messagebox.CANCEL,
//                Messagebox.QUESTION,
//                new org.zkoss.zk.ui.event.EventListener() {
//            public void onEvent(Event e) {
//                if (Messagebox.ON_OK.equals(e.getName())) {
        try {
                    for (int i = 0; i < listbox.getItemCount(); i++) {
                        if (listbox.getItems().get(i).isSelected()) {
                            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();
                            String inPositionNum = ((Listcell) listCells.get(18)).getLabel();
                            String inItemid = ((Listcell) listCells.get(19)).getLabel();
                            String inQty = ((Listcell) listCells.get(7)).getLabel();
                            String inOrderedQty = ((Listcell) listCells.get(4)).getLabel();
                            String inWhId = ((Listcell) listCells.get(8)).getLabel();
                            String inCityid = ((Listcell) listCells.get(9)).getLabel();
                            String inHlrMapId = ((Listcell) listCells.get(14)).getLabel();
                            String inOrderDate = ((Listcell) listCells.get(2)).getLabel();
                            String inPoPriceUnit = ((Listcell) listCells.get(3)).getLabel();
                            Map<String, Object> map = model.doGdSaveDtl("INSERT", 
                                    gdHdrId, 
                                    poId,
                                    inPositionNum,
                                    inPositionNum,
                                    inItemid,
                                    inQty.toString().replace(",", ""),
                                    inOrderedQty.toString().replace(",", ""), 
                                    inWhId,
                                    inCityid,
                                    inHlrMapId, 
                                    inOrderDate,
                                    "",
                                    userId, 
                                    inPoPriceUnit,
                                    "");
                            if (map.get("outError").toString().equals("0")) {
                                
                            }else {
                                Messagebox.show(map.get("outMessages").toString(), "Goods Delivery : Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                return;
                            }
                       
                        }
                    }
                   goodDeliveryCTRL.refreshDtl();
                        win_GD_detail.detach();

                
          } catch (Exception e) {
              Messagebox.show("Please Choose detail first","Goods Delivery",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }
//                else if (Messagebox.ON_CANCEL.equals(e.getName())) {
//                }
//            }
//        });
//    }

//    @Listen("onClick=#btnitem")
//    public void LovItem() {
//        HashMap map = new HashMap<String, Object>();
//        LovController composerLov = new LovController();
//        composerLov.setQuery("select * from (select rownum as No, Item_Id as \"Id\",Item_code as \"Item Code\",Item_Description as \"Item Description\", Unit as \"Unit\" from table(pkg_ifm.LovItem('" + "" + "'))where (upper(Item_code) like upper('?') or upper(Item_Description) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_hrn_test.LovItem('" + " " + "'))Where Item_code LIKE '%?%'");
//        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4});
//        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc, txtuom});
//        composerLov.setHiddenColumn(new int[]{0, 1});
//
//        composerLov.setTitle("List Of Item");
//        composerLov.setWidth("500px");
//        composerLov.setHeight("335px");
//        composerLov.setPageSize(10);
//        map.put("composerLov", composerLov);
//
//        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
//        w.setParent(win_GD_detail);
//        w.doModal();
//    }

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

    public String getGdHdrId() {
        return gdHdrId;
    }

    public void setGdHdrId(String gdHdrId) {
        this.gdHdrId = gdHdrId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }
    
}
