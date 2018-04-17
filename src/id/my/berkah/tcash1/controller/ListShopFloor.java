/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListShopFloor extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String buId = global[5];

    @Wire
    Listbox listbox1;

    @Wire
    Window List_Shopfloor, win_list_shopfloor_find;

    ModelTcashCTLR model;

    ListModel<ListShopFloorParam> paramshopfloor;
    ListShopFloorParam listShopFloorParam;

    @Listen("onCreate=#List_Shopfloor")
    public void oncreate() {
        model = new ModelTcashCTLR();
       setListboxRenderer();
        listbox1.setSizedByContent(true);
    }

    public void doFind(String buId, String shopid, String datefrom, String dateto, String woId, String woType, String itemId, String whId, String status) {
        List<ListShopFloorParam> listData = model.getListShopFloor(buId, shopid, datefrom, dateto, woId, woType, itemId, whId, status);
        Map map = new HashMap();
        map.put("InBuId", buId == null ? "": buId);
        map.put("InSfcId", shopid == null ? "": shopid);
        map.put("InSfcDateFrom", datefrom == null ? "": datefrom);
        map.put("InSfcDateTo", dateto == null ? "": dateto);
        map.put("InWoId", woId == null ? "": woId);
        map.put("InWoType", woType == null ? "": woType);
        map.put("InItemId", itemId == null ? "": itemId);
        map.put("InWhID", whId == null ? "": whId);
        map.put("InStatus", status == null ? "": status);
        listbox1.setModel(new ListModelList<ListShopFloorParam>(listData));
    }

    @Listen("onClick=#find")
    public void formFind() {
        Window w = (Window) Executions.createComponents("/Tcash/ListShopFloorFind.zul", null, null);
        w.setAttribute("parenCTRL", this);
        w.doModal();
    }

    @Listen("onClick=#new")
    public void newRecord() {
        Window w = (Window) Executions.createComponents("/Tcash/ShopFloor.zul", null, null);
        w.setAttribute("parentCTRL", this);
        w.doModal();
    }

    @Listen("onClick=#refresh")
    public void buttonRefresh() {
        refresh();
    }

    public void refresh() {
        List<ListShopFloorParam> list = model.getListShopFloor();
        listbox1.setModel(new ListModelList<ListShopFloorParam>(list));
    }

    @Listen(Events.ON_CLICK + " = #btnEdit")
    public void btnEditOnClick() {
        int index = listbox1.getSelectedIndex();
        if (index > -1) {
            ListShopFloorParam selected = (ListShopFloorParam) listbox1.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("header", selected);
            Window w = (Window) Executions.createComponents("/Tcash/ShopFloor.zul", null, map);
            w.setAttribute("parentCTRL", this);
            w.doModal();
        } else {
            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
        }
    }

    @Listen(Events.ON_DOUBLE_CLICK + " = #listbox1")
    public void listbox1OnDoubleClick() {
        btnEditOnClick();
    }

//    @Listen("onSelect = #listbox1")
//    public void selectListboxOrdFrmTch() {
//        Set<ListShopFloorParam> selectedshop = ((ListModelList<ListShopFloorParam>) paramshopfloor).getSelection();
//        for (ListShopFloorParam shopfloor : selectedshop) {
//            this.listShopFloorParam = shopfloor;
//        }
//    }
//    
    
     private void setListboxRenderer() {
        listbox1.setItemRenderer(new ListitemRenderer<ListShopFloorParam>() {

            @Override
            public void render(Listitem lstm, ListShopFloorParam t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, "" + (i + 1), "right");
                CHelper.Listbox_addLabel(lstm, t.getSfc_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getBu_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getBu_code(), "left");
                CHelper.Listbox_addLabel(lstm, t.getBu_description(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSfc_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSfc_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWo_type(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWo_type_name(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWo_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWo_no(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_code(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_description(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWip_wh_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWh_code(), "left");
                CHelper.Listbox_addLabel(lstm, t.getWh_description(), "left");
                CHelper.Listbox_addLabel(lstm, t.getQuantity_complete(), "right");
                CHelper.Listbox_addLabel(lstm, t.getQuantity_order(), "right");
                CHelper.Listbox_addLabel(lstm, t.getTotal_complete(), "right");
                CHelper.Listbox_addLabel(lstm, t.getQuantity_outstanding(), "right");
                CHelper.Listbox_addLabel(lstm, t.getStatus(), "left");
                CHelper.Listbox_addLabel(lstm, t.getStatus_name(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreate_period(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreate_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreated_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCreated_by_name(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSubmit_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSubmited_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSubmited_by_name(), "left");
                CHelper.Listbox_addLabel(lstm, t.getApprove_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getApprovd_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getApprovd_by_name(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCancel_date(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCanceled_by(), "left");
                CHelper.Listbox_addLabel(lstm, t.getCanceled_by_name(), "left");
            }
        });
    }

}
