/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListSelectSNPAram;
import id.my.berkah.util.CHelper;
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
public class ListEditSnBundling extends SelectorComposer<Component> {

    @Wire
    Window win_SelectRange_bundling;

    @Wire
    Listbox listboxSelect, listboxUnselect;

    @Wire
    Textbox txtbundling, flag, txtitem, txtreg, mode;

    @Wire
    Label txttotal, txttotal1;

    @Wire
    Button selectRange, unselectRange;

    ListSelectSNPAram ListSelectSNPAram;

    List<ListSelectSNPAram> listparam;
    BundlingAllocated parentController;
    BundlingAllocatedNonRegional parentControllers;
    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#win_SelectRange_bundling")
    public void oncReateWindow() {
        parentController = (BundlingAllocated) win_SelectRange_bundling.getAttribute("parentController");
        parentControllers = (BundlingAllocatedNonRegional) win_SelectRange_bundling.getAttribute("parentControllers");
        setRenderTemp();
        setRenderResult();
        refreshTemp();
        refreshResult();
        validationButton();
    }

    @Listen("onClick=#btnClose")
    public void btnClose() {
        if (!txtreg.getText().isEmpty()) {
            parentController.refresh();
            parentController.add.setLabel("Add Detail");
        }

        if (!mode.getText().isEmpty()) {
            parentControllers.getFunction();
        }
        win_SelectRange_bundling.detach();
    }

    @Listen("onClick=#selectRange")
    public void btnSelect() {
        int index = listboxSelect.getSelectedIndex();
//       
        if (index > -1) {
            ListSelectSNPAram selected = (ListSelectSNPAram) listboxSelect.getModel().getElementAt(index);
            Map map = new HashMap<String, Object>();
            map.put("header", selected);
            map.put("reg", txtreg.getText());
            map.put("Operation", flag.getText());
            Window w = (Window) Executions.createComponents("/Tcash/SelectEditSNBundling.zul", null, map);
            w.setAttribute("parentController", this);
            w.doModal();
        } else {
            Messagebox.show("Please choose header list", "EditSN", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    @Listen("onClick=#listboxSelect")
    public void clickListbox() {
        flag.setText("SELECT");
    }

    @Listen("onClick=#listboxUnselect")
    public void clickListboxDTL() {
        flag.setText("UNSELECT");
    }

    @Listen("onClick=#unselectRange")
    public void btnUnSelect() {
        int index = listboxUnselect.getSelectedIndex();
//     
        if (index > -1) {
            ListSelectSNPAram selected = (ListSelectSNPAram) listboxUnselect.getModel().getElementAt(index);
            Map map = new HashMap<String, Object>();
            map.put("header", selected);
            map.put("reg", txtreg.getText());
            map.put("Operation", flag.getText());
            Window w = (Window) Executions.createComponents("/Tcash/SelectEditSNBundling.zul", null, map);
            w.setAttribute("parentController", this);
            w.doModal();
        } else {
            Messagebox.show("Please choose footer list", "EditSN", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

    public void refreshTemp() {
        int ttl = 0;
        txttotal.setValue("0");
        List<ListSelectSNPAram> list = model.getListSelectSN("UNSELECT", txtbundling.getText(), txtitem.getText(), txtreg.getText());
        listboxSelect.setModel(new ListModelList<>(list));
        for (ListSelectSNPAram listSelectSNPAram : list) {
            ttl += Integer.parseInt(String.valueOf(listSelectSNPAram.getQty()) == null ? "0" : String.valueOf(listSelectSNPAram.getQty()));
            txttotal.setValue("" + ttl);
        }
    }

    public void setRenderTemp() {
        listboxSelect.setItemRenderer(new ListitemRenderer<ListSelectSNPAram>() {

            @Override
            public void render(Listitem lstm, ListSelectSNPAram t, int i) throws Exception {
                CHelper.Listbox_addLabel(lstm, t.getBundling_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getBundling_dtl_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getItem_loc_id(), "left");
                CHelper.Listbox_addLabel(lstm, t.getRange_from(), "left");
                CHelper.Listbox_addLabel(lstm, t.getRange_to(), "left");
                CHelper.Listbox_addLabel(lstm, t.getQty(), "right");
            }
        });
    }

    public void refreshResult() {
        int ttl1 = 0;
        txttotal1.setValue("0");
        List<ListSelectSNPAram> param = model.getListSelectSN("SELECT", txtbundling.getText(), txtitem.getText(), txtreg.getText());
        listboxUnselect.setModel(new ListModelList<ListSelectSNPAram>(param));
        for (ListSelectSNPAram listSelectSNPAram : param) {
            ttl1 += Integer.parseInt(String.valueOf(listSelectSNPAram.getQty()));
            txttotal1.setValue(String.valueOf(ttl1));
        }

    }

    public void setRenderResult() {
        listboxUnselect.setItemRenderer(new ListitemRenderer<ListSelectSNPAram>() {

            @Override
            public void render(Listitem lstm, ListSelectSNPAram t, int i) throws Exception {
                new Listcell(t.getBundling_id()).setParent(lstm);
                new Listcell(t.getBundling_dtl_id()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getItem_loc_id()).setParent(lstm);
                new Listcell(t.getRange_from()).setParent(lstm);
                new Listcell(t.getRange_to()).setParent(lstm);
                new Listcell(t.getQty()).setParent(lstm);

            }
        });
    }

    public void validationButton() {
        if (!txtreg.getText().isEmpty()) {
            if (parentController.txtstatus.getText().equalsIgnoreCase("draft") || parentController.txtstatus.getText().isEmpty()) {
                selectRange.setDisabled(false);
                unselectRange.setDisabled(false);
            } else {
                selectRange.setDisabled(true);
                unselectRange.setDisabled(true);
            }
            
        } else {

            if (parentControllers.txtstatus.getText().equalsIgnoreCase("draft") || parentControllers.txtstatus.getText().isEmpty()) {
                selectRange.setDisabled(false);
                unselectRange.setDisabled(false);
            } else {
                selectRange.setDisabled(true);
                unselectRange.setDisabled(true);
            }
        }
    }

}
