/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListGrSelectGDParam;
import id.my.berkah.util.ParameterGlobal;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
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
public class BundlingGoodsReceiptDetail extends SelectorComposer<Component> {
    
     private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window win_GR_detail_bundling;
    @Wire
    Listbox listbox;
    @Wire
    Textbox txtitemid, txtitemcode, txtitemdesc, txtregdesc, txthlr, txtblokfrom, txtuom, txtblokto, txtorderqty,
            txtdeliveryqty, txtotal, txtoutstanding, txtlineremark, txtstatus, txtsuppdeliveryid, txtpoid, txtsuppdeliverydate,
            txtpo, txtlineno, flag, txtcreateddate, txtmodifdate, txtcreatedby, txtmodifiedby, txtid, txtcityid, txtwhid, InOrderDate, txtorderdate, txtcreatperiode;
    @Wire
    Datebox txtprodate;
    ModelTcashCTLR model = new ModelTcashCTLR();
    BundlingGoodsReceipt BundlingGoodsReceipt;

    String poId = "";
    String grId = "";
    String gdHdrId = "";
    String poNo="";
    String whId="";

    @Listen("onCreate=#win_GR_detail_bundling")
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
        List<ListGrSelectGDParam> ListGrSelectGDParam = model.selectGrListSelectGDbundling(gdHdrId, poId,grId);
        for (ListGrSelectGDParam ListGrSelectGDParam1 : ListGrSelectGDParam) {
           
            Listcell line_no = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_description = new Listcell();
            Listcell po_id = new Listcell();
            Listcell po_line_ref = new Listcell();
            Listcell ordered_quantity = new Listcell();
            Listcell qty = new Listcell();
            Listcell outstanding_qty = new Listcell();
            Listcell qty_received = new Listcell();
            Listcell id = new Listcell();
            Listcell back_order = new Listcell();
            Listcell delivered = new Listcell();
            Listcell range_from = new Listcell();
            Listcell range_to = new Listcell();
            Listcell city_id = new Listcell();
            Listcell city_name = new Listcell();
            Listcell wh_id = new Listcell();
            Listcell order_date = new Listcell();
            Listcell po_price_unit = new Listcell();
            Listcell status_input_file = new Listcell();
            Listcell hlr = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell Received_qty = new Listcell();
            Listcell po_line_id = new Listcell();            
            Listcell line_remark = new Listcell();
            Listcell line_status = new Listcell();
            Listcell created_date = new Listcell();
            Listcell created_by = new Listcell();
            Listcell modified_date = new Listcell();
            Listcell modified_by = new Listcell();
            Listcell hlr_map_id = new Listcell();
            Listcell supp_delivery_id = new Listcell();

            
            supp_delivery_id.setLabel(ListGrSelectGDParam1.getSupp_delivery_id());
            line_no.setLabel(ListGrSelectGDParam1.getLine_no());
            item_id.setLabel(ListGrSelectGDParam1.getItem_id());
            item_code.setLabel(ListGrSelectGDParam1.getItem_code());
            item_description.setLabel(ListGrSelectGDParam1.getItem_description());
            po_id.setLabel(ListGrSelectGDParam1.getPo_id());
            po_line_ref.setLabel(ListGrSelectGDParam1.getPo_line_ref());
            ordered_quantity.setLabel(ListGrSelectGDParam1.getOrdered_quantity());
            qty_received.setLabel(ListGrSelectGDParam1.getQty_received());
            qty.setLabel(ListGrSelectGDParam1.getQty());
            outstanding_qty.setLabel(ListGrSelectGDParam1.getOutstanding());
            line_remark.setLabel(ListGrSelectGDParam1.getLine_remark());
            line_status.setLabel(ListGrSelectGDParam1.getLine_status());
            created_date.setLabel(ListGrSelectGDParam1.getCreated_date());
            created_by.setLabel(ListGrSelectGDParam1.getCreated_by());
            modified_date.setLabel(ListGrSelectGDParam1.getModified_date());
            modified_by.setLabel(ListGrSelectGDParam1.getModified_by());
            hlr_map_id.setLabel(ListGrSelectGDParam1.getHlr_map_id());
            id.setLabel(ListGrSelectGDParam1.getId());
            city_id.setLabel(ListGrSelectGDParam1.getCity_id());
            city_name.setLabel(ListGrSelectGDParam1.getCity_name());
            wh_id.setLabel(ListGrSelectGDParam1.getWh_id());
            order_date.setLabel(ListGrSelectGDParam1.getOrder_date());
            po_price_unit.setLabel(ListGrSelectGDParam1.getPo_price_unit());
            back_order.setLabel(ListGrSelectGDParam1.getBack_order());
            delivered.setLabel(ListGrSelectGDParam1.getDelivered());
            status_input_file.setLabel(ListGrSelectGDParam1.getStatus_input_file());
            hlr.setLabel(ListGrSelectGDParam1.getHlr());
            regional_id.setLabel(ListGrSelectGDParam1.getRegional_id());
            regional_description.setLabel(ListGrSelectGDParam1.getRegional_description());
            range_from.setLabel(ListGrSelectGDParam1.getRange_from());
            range_to.setLabel(ListGrSelectGDParam1.getRange_to());
            Received_qty.setLabel(ListGrSelectGDParam1.getReceived_qty());
            po_line_id.setLabel(ListGrSelectGDParam1.getPo_line_id());
           
            
            ordered_quantity.setStyle("text-align: right");
            outstanding_qty.setStyle("text-align: right");
            qty.setStyle("text-align: right");
            qty_received.setStyle("text-align: right");
            Received_qty.setStyle("text-align: right");
            delivered.setStyle("text-align: right");

            Listitem listitem = new Listitem();
            listitem.appendChild(line_no);//0
            listitem.appendChild(item_id);//1
            listitem.appendChild(item_code);//2
            listitem.appendChild(item_description);//3
            listitem.appendChild(po_id);//4
            listitem.appendChild(po_line_ref);//5
            listitem.appendChild(ordered_quantity);//6
            listitem.appendChild(qty);//7
            listitem.appendChild(qty_received);//8
            listitem.appendChild(delivered);//9
            listitem.appendChild(outstanding_qty);//10
            listitem.appendChild(line_remark);//11
            listitem.appendChild(line_status);//12
            listitem.appendChild(created_date);//13
            listitem.appendChild(created_by);//14
            listitem.appendChild(modified_date);//15
            listitem.appendChild(modified_by);//16
            listitem.appendChild(hlr_map_id);//17
            listitem.appendChild(id);//18
            listitem.appendChild(city_id);//19
            listitem.appendChild(city_name);//20
            listitem.appendChild(wh_id);//21
            listitem.appendChild(order_date);//22
            listitem.appendChild(po_price_unit);//23
            listitem.appendChild(back_order);//24
            listitem.appendChild(status_input_file);//25
            listitem.appendChild(hlr);//26
            listitem.appendChild(regional_id);//27
            listitem.appendChild(regional_description);//28
            listitem.appendChild(range_from);//29
            listitem.appendChild(range_to);//30
            listitem.appendChild(Received_qty);//31
            listitem.appendChild(po_line_id);//32
            listitem.appendChild(supp_delivery_id);//33
            listbox.appendChild(listitem);
        }
    }

    @Listen("onClick=#close")
    public void close() {
        win_GR_detail_bundling.detach();
    }
    
    @Listen("onClick=#listbox")
    public void onClicklistbox(){
        int a = 0;
                    for (int i = 0; i < listbox.getItemCount(); i++) {
                        if (listbox.getItems().get(i).isSelected()) {
                            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();
                            String inLineGd = ((Listcell) listCells.get(0)).getLabel();
                            String inItemid = ((Listcell) listCells.get(1)).getLabel();
                            String inItemCode = ((Listcell) listCells.get(2)).getLabel();
                            String poId = ((Listcell) listCells.get(4)).getLabel();
                            String poLineRef = ((Listcell) listCells.get(5)).getLabel();
                            String inQty = ((Listcell) listCells.get(7)).getLabel();
                            String inRcvQty = ((Listcell) listCells.get(8)).getLabel();
                            String hlrMapId = ((Listcell) listCells.get(17)).getLabel();
                            String inWhId = ((Listcell) listCells.get(21)).getLabel();
                            String inId = ((Listcell) listCells.get(18)).getLabel();
                            
                            String delivered = ((Listcell) listCells.get(7)).getLabel();
                            String regionalId = ((Listcell) listCells.get(27)).getLabel();
                            String po_line_id = ((Listcell) listCells.get(32)).getLabel();
                            System.out.println("inLineGd"+inLineGd);
                            System.out.println("inItemid"+inItemid);
                            System.out.println("inItemCode"+inItemCode);
                            System.out.println("inQty"+inQty);
                            System.out.println("inRcvQty"+inRcvQty);
                            System.out.println("hlrMapId"+hlrMapId);
                            System.out.println("inWhId"+inWhId);
                            System.out.println("inId"+inId);
                            System.out.println("poId"+poId);
                            System.out.println("poLineRef"+poLineRef);
                            System.out.println("delivered"+delivered);
                            System.out.println("regionalId"+regionalId);
                            System.out.println("po_line_id"+po_line_id);
    }
                    }
    }

    @Listen("onClick=#save")
    public void save() {
        Messagebox.show("Are you sure want to save?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event e) {
                if (Messagebox.ON_OK.equals(e.getName())) {
                    int a = 0;
                    for (int i = 0; i < listbox.getItemCount(); i++) {
                        if (listbox.getItems().get(i).isSelected()) {
                            List<Component> listCells = listbox.getItemAtIndex(i).getChildren();
                            String inLineGd = ((Listcell) listCells.get(0)).getLabel();
                            String inItemid = ((Listcell) listCells.get(1)).getLabel();
                            String inItemCode = ((Listcell) listCells.get(2)).getLabel();
                            String poId = ((Listcell) listCells.get(4)).getLabel();
                            String poLineRef = ((Listcell) listCells.get(5)).getLabel();
                            String inQty = ((Listcell) listCells.get(7)).getLabel();
                            String inRcvQty = ((Listcell) listCells.get(8)).getLabel();
                            String hlrMapId = ((Listcell) listCells.get(17)).getLabel();
                            String inWhId = ((Listcell) listCells.get(21)).getLabel();
                            String inId = ((Listcell) listCells.get(18)).getLabel();
                            String delivered = ((Listcell) listCells.get(7)).getLabel();
                            String regionalId = ((Listcell) listCells.get(27)).getLabel();
                            String po_line_id = ((Listcell) listCells.get(32)).getLabel();
                            System.out.println("inLineGd"+inLineGd);
                            System.out.println("inItemid"+inItemid);
                            System.out.println("inItemCode"+inItemCode);
                            System.out.println("inQty"+inQty);
                            System.out.println("inRcvQty"+inRcvQty);
                            System.out.println("hlrMapId"+hlrMapId);
                            System.out.println("inWhId"+inWhId);
                            System.out.println("inId"+inId);
                            System.out.println("poId"+poId);
                            System.out.println("poLineRef"+poLineRef);
                            System.out.println("delivered"+delivered);
                            System.out.println("regionalId"+regionalId);
                            System.out.println("po_line_id"+po_line_id);
                            Map<String, Object> map = model.doGrSaveDtlbundling("INSERT", grId,inLineGd,
                                    inId, inItemid,inItemCode,"", inQty.toString().replace(",", ""), delivered.toString().replace(",", ""),"",userId,"","","",poId,poNo,poLineRef,
                                    "","","",delivered.toString().replace(",", ""),"",inLineGd,gdHdrId,
                                    whId,"","");
                            if (map.get("outError").toString().equals("0")) {
                            }
                            else {
                                Messagebox.show(map.get("outMessages").toString(), "Message", Messagebox.OK, Messagebox.EXCLAMATION);
                                return;
                            }
                            a++;
                        }

                    }
                    if(a>0){
//                        Messagebox.show("Saved", "Message", Messagebox.OK, Messagebox.inf);
                        win_GR_detail_bundling.detach();
                    }
                    BundlingGoodsReceipt.refreshDtl(grId);
                }
                else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                }
            }
        });
    }

 

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getGrId() {
        return grId;
    }

    public void setGrId(String grId) {
        this.grId = grId;
    }

    public String getGdHdrId() {
        return gdHdrId;
    }

    public void setGdHdrId(String gdHdrId) {
        this.gdHdrId = gdHdrId;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }
    
    
    public BundlingGoodsReceipt getBundlingGoodsReceipt() {
        return BundlingGoodsReceipt;
    }

    public void setBundlingGoodsReceipt(BundlingGoodsReceipt BundlingGoodsReceipt) {
        this.BundlingGoodsReceipt = BundlingGoodsReceipt;
    }
}

    
