package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListShopFloorDtlParam;
import id.my.berkah.tcash1.model.ListShopFloorParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ListDataListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Azec
 */
public class ShoFloorCTRL extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String buId = global[5];

    ListShopFloor parentCTRL;

    @Wire
    Window win_shopfloor;

    @Wire
    Listbox listbox;

    @Wire
    Combobox cmbwo;

    @Wire
    Textbox txtitemid, txtitemcode, txtitemdesc,
            txtwordcode, txtworddes, txtwipid, txtwipcode, txtwipdesc,
            txtshopdate, txtstat, txtshopFloor, txtsfcid, txtwotypefnd, txtstatid, txtcreationby, txtcreationdate, txtapprovedate, txtapprovby, txtSubmitdate, txtsbmtby, txtcanceldate, txtcancelby, txtqty, txtflag;

    @Wire
    Intbox txtquanout, txtquantotc, txtquantityc;

    @Wire
    Button btnsave, btnsn, btncancel, btnsubmit, btnapproved, btnshow, btnprod, btnitem, btnwip;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#win_shopfloor")
    public void onCreateWindow() {
        listbox.setSizedByContent(true);
        listdetailsetModel();
        flag();
        parentCTRL = (ListShopFloor) win_shopfloor.getAttribute("parentCTRL");
        setRender();
        listDetailShopfloor();
        disableButton();
    }

    @Listen("onClick=#close")
    public void closeWindow() {
        win_shopfloor.detach();
    }

    public void flag() {
        if (txtsfcid.getText().equals("") || txtsfcid.getText() == null) {
            txtflag.setText("insert");
        } else {
            txtflag.setText("update");
        }
    }

    @Listen("onClick=#btnsn")
    public void parsing() {
        buttonSave();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sfcid", txtsfcid.getText());
        map.put("status", txtstatid.getText());
        map.put("itemid", txtitemid.getText());
        map.put("userId", userId);
        map.put("status", txtstatid.getText());
        Window w = (Window) Executions.createComponents("/Tcash/ListSelectRange_1.zul", null, map);
        w.setAttribute("parentController1", this);
        w.doModal();
    }

    public void loVitemFinishGoods() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, item_id as \"Id\",item_code as \"Item Code\", item_description as \"Description\" ,unit as \"Unit\" from table(pkg_tcash_shopfloor.LovItem(''))where (upper(item_code) like upper('?') or upper(item_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovItem(''))Where item_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtitemid, txtitemcode, txtitemdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of Purchase Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_shopfloor);
        w.doModal();
    }

    @Listen("onClick=#btnprod")
    public void loVWo() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, po_id as \"Id\",purchase_order as \"Purcahse Order\" ,item_id as\"Item Id\",item_code as \"Item Code\",item_description as\"Description\",quantity_order as \"Qty\" from table(pkg_tcash_shopfloor.LovWO(''))where (upper(purchase_order) like upper('?') or upper(item_code) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovWO(''))Where purchase_order LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3, 4, 5, 6});
        composerLov.setComponentTransferData(new Textbox[]{txtwordcode, txtworddes, txtitemid, txtitemcode, txtitemdesc, txtqty});
        composerLov.setHiddenColumn(new int[]{0, 1, 3});

        composerLov.setTitle("List Of Work Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_shopfloor);
        w.doModal();
    }

    @Listen("onClick=#btnwip")
    public void loVWip() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Id\",wh_code as \"Warehouse Code\", wh_description as \"Description\"  from table(pkg_tcash_shopfloor.LovWh('','" + buId + "','" + userId + "','" + responsibilityId + "'))where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_shopfloor.LovWh('','" + buId + "','" + userId + "','" + responsibilityId + "'))Where wh_code LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtwipid, txtwipcode, txtwipdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of WIP Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_shopfloor);
        w.doModal();
    }

    public void createShopfloor() {

        Map<String, Object> map = model.doCreateShopFloor(buId, txtwotypefnd.getText(), txtwordcode.getText(), txtitemid.getText(), txtwipid.getText(), txtquantityc.getText(), "0", userId);
        if (map.get("err").equals(0)) {
            txtsfcid.setText(map.get("sfcId").toString());
            Messagebox.show(map.get("msg").toString());
            txtflag.setText("update");
            parentCTRL.buttonRefresh();
        } else {
            Messagebox.show(map.get("msg").toString());
        }
    }

    public void updatehopfloor() {
        Map<String, Object> map = model.doValidateQuantityComplete(txtwordcode.getText(), txtitemid.getText(), txtquantityc.getText());
        if (map.get("err").equals(0)) {

            Map<String, Object> mapupdate = model.doUpdateShopFloor(txtsfcid.getValue(), txtshopdate.getText(), txtwotypefnd.getValue(), txtwordcode.getValue(), txtitemid.getValue(), txtwipid.getValue(), txtquantityc.getText(), userId);
            if (mapupdate.get("err").equals(0)) {
                Messagebox.show(mapupdate.get("msg").toString());
                parentCTRL.buttonRefresh();
            } else {
                Messagebox.show(mapupdate.get("msg").toString());
            }

        } else {
            Messagebox.show(map.get("msg").toString());
        }

    }

    public void doApproveShopFloor() {
        Map<String, Object> map = model.doApproveShopFloor(txtsfcid.getText(), txtshopdate.getText(), txtwotypefnd.getText(), txtwordcode.getText(), txtitemid.getText(), txtwipid.getText(), txtqty.getText(), userId);
        if (map.get("err").equals(0)) {
            Messagebox.show(map.get("msg").toString());
            autorefresh();
            parentCTRL.buttonRefresh();
        } else {
            Messagebox.show(map.get("msg").toString());
        }
    }

    public void doSubmitShopFloor() {
        Map<String, Object> map = model.doSubmitShopFloor(txtsfcid.getText(), userId);
        if (map.get("err").equals(0)) {
            Messagebox.show(map.get("msg").toString());
            autorefresh();
            parentCTRL.buttonRefresh();
        } else {
            Messagebox.show(map.get("msg").toString());
        }

    }

    @Listen("onSelect=#cmbwo")
    public void selectCombowotype() {
        if (cmbwo.getSelectedIndex() == 0) {
            txtwotypefnd.setText("");
        } else if (cmbwo.getSelectedIndex() == 1) {
            txtwotypefnd.setText("1");
        } else if (cmbwo.getSelectedIndex() == 2) {
            txtwotypefnd.setText("2");
        } else {
            CHelper.ShowMessage("Wo Type Is Not Available", Messagebox.EXCLAMATION);
        }

    }

    public void newRecord() {
        txtsfcid.setText("");
        txtshopFloor.setText("");
        txtshopdate.setText("");
        cmbwo.setSelectedIndex(0);
        txtwotypefnd.setText("");
        txtwordcode.setText("");
        txtworddes.setText("");
        txtitemid.setText("");
        txtitemcode.setText("");
        txtitemdesc.setText("");
        txtwipcode.setText("");
        txtwipdesc.setText("");
        txtwipid.setText("");
        txtqty.setText("0");
        txtquanout.setText("0");
        txtquantotc.setText("0");
        txtquantityc.setText("0");
        txtstat.setText("");
        txtstatid.setText("");
        txtcreationby.setText("");
        txtcreationdate.setText("");
        txtcancelby.setText("");
        txtcanceldate.setText("");
        txtapprovby.setText("");
        txtapprovedate.setText("");
        txtsbmtby.setText("");
        txtSubmitdate.setText("");
        txtflag.setText("insert");
        btnsave.setDisabled(false);
        btnsn.setDisabled(false);
        btnsubmit.setDisabled(false);
        btnapproved.setDisabled(false);
        cmbwo.setReadonly(false);
        txtquantityc.setReadonly(false);
        listbox.getItems().clear();
    }

    @Listen("onClick=#newrecord")
    public void buttonNew() {
        newRecord();
    }

    @Listen("onClick=#btnsave")
    public void buttonSave() {
        if (txtflag.getText().equals("insert")) {
            createShopfloor();
        } else {
            updatehopfloor();
        }

        autorefresh();
        disableButton();
    }

    @Listen("onClick=#btnsubmit")
    public void buttonSubmit() {
        doSubmitShopFloor();
        autorefresh();
        disableButton();

    }

    @Listen("onClick=#btnapproved")
    public void buttonApprove() {
        doApproveShopFloor();
        autorefresh();
        disableButton();
    }

    public void listdetailsetModel() {
        if (txtsfcid.getText() != null) {
            List<ListShopFloorDtlParam> listdata = model.getListShopFloorDtl(txtsfcid.getText());
            listbox.setModel(new ListModelList<ListShopFloorDtlParam>(listdata));
        } else {

        }

    }

    @Listen("onCreate=#cmbwo")
    public void cmbwoonCreate() {
        if (txtwotypefnd.getText().equals("")) {
            cmbwo.setSelectedIndex(0);
        } else if (txtwotypefnd.getText().equals("1")) {
            cmbwo.setSelectedIndex(1);
        } else {
            cmbwo.setSelectedIndex(2);
        }
    }

    public void autorefresh() {
        if (!txtsfcid.getText().isEmpty()) {
            List<ListShopFloorParam> listShopFloorParam = model.selectListHdrSFC(txtsfcid.getText());
            txtsfcid.setText(listShopFloorParam.get(0).getSfc_id());
            txtshopFloor.setText(listShopFloorParam.get(0).getSfc_no());
            txtshopdate.setText(listShopFloorParam.get(0).getSfc_date());
            txtwotypefnd.setText(listShopFloorParam.get(0).getWo_type());
            txtwordcode.setText(listShopFloorParam.get(0).getWo_id());
            txtworddes.setText(listShopFloorParam.get(0).getWo_no());
            txtitemid.setText(listShopFloorParam.get(0).getItem_id());
            txtitemcode.setText(listShopFloorParam.get(0).getItem_code());
            txtitemdesc.setText(listShopFloorParam.get(0).getItem_description());
            txtwipid.setText(listShopFloorParam.get(0).getWip_wh_id());
            txtwipcode.setText(listShopFloorParam.get(0).getWh_code());
            txtwipdesc.setText(listShopFloorParam.get(0).getWh_description());
            txtquantityc.setText(listShopFloorParam.get(0).getQuantity_complete());
            txtstatid.setText(listShopFloorParam.get(0).getStatus());
            txtstat.setText(listShopFloorParam.get(0).getStatus_name());
            txtcreationdate.setText(listShopFloorParam.get(0).getCreate_date());
            txtcreationby.setText(listShopFloorParam.get(0).getCreated_by_name());
            txtSubmitdate.setText(listShopFloorParam.get(0).getSubmit_date());
            txtsbmtby.setText(listShopFloorParam.get(0).getSubmited_by_name());
            txtapprovby.setText(listShopFloorParam.get(0).getApprovd_by_name());
            txtapprovedate.setText(listShopFloorParam.get(0).getApprove_date());
            txtcanceldate.setText(listShopFloorParam.get(0).getCancel_date());
            txtcancelby.setText(listShopFloorParam.get(0).getCanceled_by_name());
            txtqty.setText(listShopFloorParam.get(0).getQuantity_order());
            txtquantotc.setText(listShopFloorParam.get(0).getTotal_complete());
            txtquanout.setText(listShopFloorParam.get(0).getQuantity_outstanding());
        } else {

        }
    }

    public void listDetailShopfloor() {
        List<ListShopFloorDtlParam> listdata = model.getListShopFloorDtl(txtsfcid.getText());
        listbox.setModel(new ListModelList<ListShopFloorDtlParam>(listdata));
    }

    public void setRender() {
        listbox.setItemRenderer(new ListitemRenderer<ListShopFloorDtlParam>() {

            @Override
            public void render(Listitem lstm, ListShopFloorDtlParam t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, t.getSFC_ID(), "left");
                CHelper.Listbox_addLabel(lstm, t.getITEM_ID(), "left");
                CHelper.Listbox_addLabel(lstm, t.getITEM_CODE(), "left");
                CHelper.Listbox_addLabel(lstm, t.getITEM_DESCRIPTION(), "left");
                CHelper.Listbox_addLabel(lstm, t.getFROM_SN(), "left");
                CHelper.Listbox_addLabel(lstm, t.getTO_SN(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSTATUS(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSTATUS_NAME(), "left");
                CHelper.Listbox_addLabel(lstm, t.getFILE_NAME(), "left");
                CHelper.Listbox_addLabel(lstm, t.getUPLOAD_DATE(), "left");
                CHelper.Listbox_addLabel(lstm, t.getSTATUS_MESSAGE(), "left");
                CHelper.Listbox_addLabel(lstm, t.getFILE_NAME(), "left");
            }
        });
    }

    public void disableButton() {
        if (txtstatid.getText().equals("0")) {

        } else if (txtstatid.getText().equals("1")) {
            btnprod.setDisabled(true);
            btnitem.setDisabled(true);
            btnwip.setDisabled(true);
            btnsave.setDisabled(true);
            btnsn.setDisabled(true);
            txtquantityc.setReadonly(true);
            cmbwo.setDisabled(true);
        } else if (txtstatid.getText().equals("2")) {
            btnsave.setDisabled(true);
            btnsubmit.setDisabled(true);
            btnsn.setDisabled(true);
            txtquantityc.setReadonly(true);
            cmbwo.setDisabled(true);
        } else if (txtstatid.getText().equals("3")) {
            btnsave.setDisabled(true);
            btnsubmit.setDisabled(true);
            btnapproved.setDisabled(true);
            btnsn.setDisabled(true);
            txtquantityc.setReadonly(true);
            cmbwo.setDisabled(true);
        }
    }

    @Listen("onClick=#btncancel")
    public void buttonCancel() {
        doCancelShopFloor();
        autorefresh();
        disableButton();
    }

    public void doCancelShopFloor() {
        if (txtstat.getText().equals("2")) {
            Messagebox.show("This Shopfloor has been Approved", "ShopFLoor", Messagebox.OK, Messagebox.EXCLAMATION);
        } else if (txtstat.getText().equals("3")) {
            Messagebox.show("This Shopfloor has been Canceled", "ShopFLoor", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            Map<String, Object> map = model.doCancelShopFloor(txtsfcid.getText(), userId);
            if (map.get("err").equals(0)) {
                Messagebox.show(map.get("msg").toString());
            } else {
                Messagebox.show(map.get("msg").toString());
            }
        }
    }

}
