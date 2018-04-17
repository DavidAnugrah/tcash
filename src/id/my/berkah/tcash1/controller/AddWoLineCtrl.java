/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoListAddLineDtlParam;
import id.my.berkah.tcash1.model.ListWoListAddLineHdrParam;
import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.util.ParameterGlobal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

/**
 *
 * @author arry
 */
public class AddWoLineCtrl extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    WorkOrderCTRL workOrderCTRL;
    ModelTcashCTLR model = new ModelTcashCTLR();
    String inContractId = "";
    String orderType = "";
    String poid = "";
    String contractId = "";
    String poNo = "";
    @Wire
    private Listbox lboxHdr, listbox;
    @Wire
    Window win_Add_Line_Wo;
    
    public Listitem selectRow = new Listitem();

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
        refreshHdr();
    }

    public void refreshHdr() {
        lboxHdr.getItems().clear();
        List<ListWoListAddLineHdrParam> ListWoListAddLineHdrParam = model.selectWoListAddLineHdr(userId, responsibilityId, inContractId, orderType);
        for (ListWoListAddLineHdrParam ListWoListAddLineHdrParam1 : ListWoListAddLineHdrParam) {
            Listcell requisition_id = new Listcell();
            Listcell requisition_number = new Listcell();
            Listcell bu_id = new Listcell();
            Listcell bu_code = new Listcell();
            Listcell bu_description = new Listcell();
            Listcell requisition_date = new Listcell();
            Listcell currency = new Listcell();
            Listcell remarks = new Listcell();
            Listcell chk = new Listcell();
            
            
            Checkbox chbx = new Checkbox();

            requisition_id.setLabel(ListWoListAddLineHdrParam1.getRequisition_id());
            requisition_number.setLabel(ListWoListAddLineHdrParam1.getRequisition_number());
            bu_id.setLabel(ListWoListAddLineHdrParam1.getBu_id());
            bu_code.setLabel(ListWoListAddLineHdrParam1.getBu_code());
            bu_description.setLabel(ListWoListAddLineHdrParam1.getBu_description());
            requisition_date.setLabel(ListWoListAddLineHdrParam1.getRequisition_date());
            currency.setLabel(ListWoListAddLineHdrParam1.getCurrency());
            remarks.setLabel(ListWoListAddLineHdrParam1.getRemarks());
            chk.appendChild(chbx);

            Listitem listitem = new Listitem();

            listitem.appendChild(requisition_number);
            listitem.appendChild(bu_id);
            listitem.appendChild(bu_code);
            listitem.appendChild(bu_description);
            listitem.appendChild(requisition_date);
            listitem.appendChild(currency);
            listitem.appendChild(remarks);
            listitem.appendChild(requisition_id);

            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String poId = ((Listcell) t.getTarget().getChildren().get(7)).getLabel();
                    refreshDtl(poId);
                }
            });
            
           

            lboxHdr.appendChild(listitem);
        }
    }
    
    

    public void refreshDtl(final String reqId) {
        listbox.getItems().clear();
        List<ListWoListAddLineDtlParam> ListWoListAddLineDtlParam = model.selectWoListAddLineDtl(reqId,inContractId,orderType);
        for (ListWoListAddLineDtlParam ListWoListAddLineDtlParam1 : ListWoListAddLineDtlParam) {
            Listcell requisition_line = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_code = new Listcell();
            Listcell item_name = new Listcell();
            Listcell regional_id = new Listcell();
            Listcell regional_description = new Listcell();
            Listcell required_date = new Listcell();
            Listcell order_unit = new Listcell();
            Listcell order_quantity = new Listcell();
            Listcell bu_id = new Listcell();
            Listcell bu_code = new Listcell();
            Listcell bu_description = new Listcell();
            Listcell wh_description = new Listcell();
            Listcell purchase_order_qty = new Listcell();
            Listcell outstanding_qty = new Listcell();
            
            Listcell chk = new Listcell();
            final Checkbox chbx = new Checkbox();

            requisition_line.setLabel(ListWoListAddLineDtlParam1.getRequisition_line());
            item_id.setLabel(ListWoListAddLineDtlParam1.getItem_id());
            item_code.setLabel(ListWoListAddLineDtlParam1.getItem_code());
            item_name.setLabel(ListWoListAddLineDtlParam1.getItem_name());
            regional_id.setLabel(ListWoListAddLineDtlParam1.getRegional_id());
            regional_description.setLabel(ListWoListAddLineDtlParam1.getRegional_description());
            required_date.setLabel(ListWoListAddLineDtlParam1.getRequired_date());
            order_unit.setLabel(ListWoListAddLineDtlParam1.getOrder_unit());
            order_quantity.setLabel(ListWoListAddLineDtlParam1.getOrder_quantity());
            bu_id.setLabel(ListWoListAddLineDtlParam1.getBu_id());
            bu_code.setLabel(ListWoListAddLineDtlParam1.getBu_code());
            bu_description.setLabel(ListWoListAddLineDtlParam1.getBu_description());
            wh_description.setLabel(ListWoListAddLineDtlParam1.getWh_description());
            purchase_order_qty.setLabel(ListWoListAddLineDtlParam1.getPurchase_order_qty());
            outstanding_qty.setLabel(ListWoListAddLineDtlParam1.getOutstanding_qty());
            
            chk.appendChild(chbx);

            Listitem listitem = new Listitem();
            listitem.appendChild(chk);
            listitem.appendChild(requisition_line);
            listitem.appendChild(item_id);
            listitem.appendChild(item_code);
            listitem.appendChild(item_name);
            listitem.appendChild(regional_id);
            listitem.appendChild(regional_description);
            listitem.appendChild(required_date);
            listitem.appendChild(order_unit);
            listitem.appendChild(order_quantity);
            listitem.appendChild(bu_id);
            listitem.appendChild(bu_code);
            listitem.appendChild(bu_description);
            listitem.appendChild(wh_description);
            listitem.appendChild(purchase_order_qty);
            listitem.appendChild(outstanding_qty);
            
             chbx.addEventListener(Events.ON_CHECK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    if(chbx.isChecked()){
                        System.out.println(reqId);
                        System.out.println("checked");
                        selectRow = (Listitem) t.getTarget().getParent().getParent();
                    System.out.println(t.getTarget());
                    String poLineId = ((Listcell) selectRow.getChildren().get(1)).getLabel();
                        System.out.println(poLineId);
                    }
                    else{
                        System.out.println("unchecked");
                    }
                }
            });

            listbox.appendChild(listitem);
        }
    }

    @Listen("onClick=#btnAdd")
    public void addDtl(){
        
    }
    
    public void saveDetailAddLine(String reqId) {
        System.out.println(reqId);
        System.out.println(inContractId);
        System.out.println(poid);
        System.out.println(poNo);
        System.out.println(userId);
//        Map<String, Object> map = model.doWoSaveDtl(reqId, inContractId, poid, poNo, userId);
//        if (map.get("outError").toString().equals("0")) {
//            Messagebox.show(map.get("outMessages").toString());
//        }
//        else {
//            Messagebox.show(map.get("outMessages").toString());
//        }
    }

    @Listen("onClick=#btnSubmit")
    public void btnSubmit() {
        if (listbox.getItems().isEmpty()) {
            Messagebox.show("Please Select Request From button Request Production No","Work Order",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    for (int i = 0; i < lboxHdr.getItemCount(); i++) {
                        if (lboxHdr.getItems().get(i).isSelected()) {
                            List<Component> listCells = lboxHdr.getItemAtIndex(i).getChildren();

                            String colomRangeFrom = ((Listcell) listCells.get(7)).getLabel();
                            saveDetailAddLine(colomRangeFrom);
                        }
                    }
                }
            }
        };
        Messagebox.show("Are you sure want to save?", "Work Order", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);


    }

    @Listen("onClick=#btnCancel")
    public void closeForm() throws ParseException {
        win_Add_Line_Wo.detach();
        workOrderCTRL.refreshDetail();
        workOrderCTRL.headerList();
    }

    public WorkOrderCTRL getWorkOrderCTRL() {
        return workOrderCTRL;
    }

    public void setWorkOrderCTRL(WorkOrderCTRL workOrderCTRL) {
        this.workOrderCTRL = workOrderCTRL;
    }

    public String getInContractId() {
        return inContractId;
    }

    public void setInContractId(String inContractId) {
        this.inContractId = inContractId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }
}
