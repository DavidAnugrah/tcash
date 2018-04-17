/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ProductionIssued;
import id.my.berkah.util.ParameterGlobal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Ari
 */
public class ListProductionIssue extends SelectorComposer<Component>
{

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window List_ProductionIssue, win_list_find;

    @Wire
    Paging userPaging;

    ListModel<ProductionIssued> PiModel;

    @Wire
    Listbox listboxPi;

    ModelTcashCTLR model = new ModelTcashCTLR();

    ProductionIssued productionIssued;

    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        refresh("", "", "", "", "");
    }


    @Listen("onClick=#refresh")
    public void refreshList()
    {
        refresh("", "", "", "", "");
    }

    public void refresh(String ProdIssueNo, String DateFrom, String DateTo, String Status, String WoNo)
    {
        Map map = new HashMap<String, Object>();
        map.put("InProdIssueNo", ProdIssueNo);
        map.put("InDateFrom", DateFrom);
        map.put("InDateTo", DateTo);
        map.put("InStatus", Status);
        map.put("InWoNo", WoNo);

        List<ProductionIssued> listProductionIssued = model.listPi(map);
        PiModel = new ListModelList<ProductionIssued>(listProductionIssued);
        listboxPi.setModel(PiModel);
        listboxPi.setItemRenderer(new ListitemRenderer<ProductionIssued>()
        {
            public void render(final Listitem lstm, ProductionIssued t, final int i) throws Exception
            {
                int rowNum = (i + 1);
                new Listcell("" + rowNum).setParent(lstm);
                new Listcell(t.getProd_issue_id()).setParent(lstm);
                new Listcell(t.getProd_issue_no()).setParent(lstm);
                new Listcell(t.getProd_issue_date()).setParent(lstm);
                new Listcell(t.getBu_id()).setParent(lstm);
                new Listcell(t.getBu_code()).setParent(lstm);
                new Listcell(t.getWo_id()).setParent(lstm);
                new Listcell(t.getWo_no()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_description()).setParent(lstm);
                new Listcell(t.getWh_id()).setParent(lstm);
                new Listcell(t.getWh_desc()).setParent(lstm);
                new Listcell(t.getWip_wh_id()).setParent(lstm);
                new Listcell(t.getWip_wh_desc()).setParent(lstm);
                new Listcell(t.getStatus()).setParent(lstm);
                new Listcell(t.getStatus_desc()).setParent(lstm);
                new Listcell(t.getCreate_period()).setParent(lstm);
                new Listcell(t.getCreate_date()).setParent(lstm);
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getApprove_date()).setParent(lstm);
                new Listcell(t.getApproved_by()).setParent(lstm);
                new Listcell(t.getSubmit_date()).setParent(lstm);
                new Listcell(t.getSubmited_by()).setParent(lstm);
                lstm.addEventListener("onDoubleClick", new EventListener()
                {
                    @Override
                    public void onEvent(Event event)
                    {
                        editForm();
                    }
                });
            }
        });
    }

    @Listen("onSelect = #listboxPi")
    public void selectListBoxPI()
    {
        Set<ProductionIssued> selectedPi = ((ListModelList<ProductionIssued>) PiModel).getSelection();
        for (ProductionIssued Pi : selectedPi)
        {
            this.productionIssued = Pi;
        }
    }

    @Listen("onClick=#new")
    public void addnew()
    {
        Map map = new HashMap();
        DlgProductionIssue dlgProductionIssue = new DlgProductionIssue();
        dlgProductionIssue.setListProductionIssue(this);
        map.put("controller", dlgProductionIssue);
        map.put("operation", "Add");
        Executions.createComponents("/Tcash/DlgProductionIssue.zul", null, map);
    }

   
    public void edit()
    {
        if (listboxPi.getSelectedCount() != 0)
        {
            Map map = new HashMap();
            map.put("businessUnitId", productionIssued.getBu_id());
            map.put("businessUnit", productionIssued.getBu_code());
            map.put("creationDate", productionIssued.getCreate_date());
            map.put("productionIssuedId", productionIssued.getProd_issue_id());
            map.put("productionIssuedNumber", productionIssued.getProd_issue_no());
            map.put("createdBy", productionIssued.getCreated_by());
            map.put("productionIssuedDate", productionIssued.getProd_issue_date());
            map.put("approveDate", productionIssued.getApprove_date());
            map.put("workOrderNumberId", productionIssued.getWo_id());
            map.put("workOrderNumber", productionIssued.getWo_no());
            map.put("approvedBy", productionIssued.getApproved_by());
            map.put("itemFinishGoodsId", productionIssued.getItem_id());
            map.put("itemFinishGoods", productionIssued.getItem_code());
            map.put("submitDate", productionIssued.getSubmit_date());
            map.put("warehouseId", productionIssued.getWh_id());
            map.put("warehouse", productionIssued.getWh_desc());
            map.put("submitedBy", productionIssued.getSubmited_by());
            map.put("wipWarehouseId", productionIssued.getWip_wh_id());
            map.put("wipWarehouse", productionIssued.getWip_wh_desc());
            map.put("status", productionIssued.getStatus_desc());
            DlgProductionIssue dlgProductionIssue = new DlgProductionIssue();
            dlgProductionIssue.setListProductionIssue(this);
            map.put("controller", dlgProductionIssue);
            map.put("operation", "Edit");
            Executions.createComponents("/Tcash/DlgProductionIssue.zul", null, map);
        } else
        {
            Messagebox.show("Data Belum dipilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
    
     @Listen("onClick=#edit")
    public void editForm(){
        int index = listboxPi.getSelectedIndex();
        if (index >-1) {
            ProductionIssued selected = (ProductionIssued) listboxPi.getModel().getElementAt(index);
            Map <String,Object> map = new HashMap ();
            map.put("header", selected);
            map.put("operation", "Edit");
             Window w = (Window) Executions.createComponents("/Tcash/DlgProductionIssue.zul", null, map);
             w.doModal();
        } else {
        }
    }

    @Listen("onClick=#cancel")
    public void cancel()
    {
        if (listboxPi.getSelectedCount() != 0)
        {
            DlgProductionIssue dlgProductionIssue = new DlgProductionIssue();
            dlgProductionIssue.validateStatus( productionIssued.getProd_issue_id(),4);
            refresh("", "", "", "", "");
        } else
        {
            Messagebox.show("Data Belum dipilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Listen("onClick=#find")
    public void find()
    {
        Map map = new HashMap();
        FindProductionIssue findProductionIssue = new FindProductionIssue();
        findProductionIssue.setListProductionIssue(this);
        map.put("controller", findProductionIssue);
        Executions.createComponents("/Tcash/FindProductionIssue.zul", null, map);
    }
    
    
    public void filterParam(Map map){
        List<ProductionIssued> listProductionIssued = model.listPi(map);
        listboxPi.setModel(new ListModelList<ProductionIssued>(listProductionIssued));
    }
}
