/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.SelectRange;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
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
 * @author Ari
 */
public class DlgSelectRange extends SelectorComposer<Component>
{

    @Wire
    Window dlgSelectRange;

    @Wire
    Listbox listboxSelect, listboxUnselect;

    
    @Wire
    Textbox txtProdIssueDtlId,txtItemId,txtwoId,txtwhId;

    DlgProductionIssue dlgProductionIssue;

    ListModel<SelectRange> DtlItemHeaderModel;

    ListModel<SelectRange> DtlItemDetailModel;

    SelectRange dtlItmSelectRangeHeader;

    SelectRange dtlItmSelectRangeDetail;

    ModelTcashCTLR model = new ModelTcashCTLR();

    String ProdIssueDtlId;

    public String getProdIssueDtlId()
    {
        return ProdIssueDtlId;
    }

    public void setProdIssueDtlId(String ProdIssueDtlId)
    {
        this.ProdIssueDtlId = ProdIssueDtlId;
    }

    DlgProductionIssue  parentController;
    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
         parentController = (DlgProductionIssue)dlgSelectRange.getAttribute("parentController");
        listHeaderRange();
        listDetailRange();
    }

    @Listen("onSelect = #listboxSelect")
    public void selectListboxHdrItem()
    {
        Set<SelectRange> selectedHdr = ((ListModelList<SelectRange>) DtlItemHeaderModel).getSelection();
        for (SelectRange DtlItem : selectedHdr)
        {
            this.dtlItmSelectRangeHeader = DtlItem;
        }
    }

    @Listen("onSelect = #listboxUnselect")
    public void selectListboxDtlItem()
    {
        Set<SelectRange> selectedDtl = ((ListModelList<SelectRange>) DtlItemDetailModel).getSelection();
        for (SelectRange DtlItem : selectedDtl)
        {
            this.dtlItmSelectRangeDetail = DtlItem;
        }
    }

    public void listHeaderRange()
    {
        String InProdIssueDtlId = this.getProdIssueDtlId();
        Map map = new HashMap<String, Object>();
        map.put("InProdIssueDtlId", InProdIssueDtlId);

        List<SelectRange> dtlItmProductionIssue = model.listHeaderRange(map);
        DtlItemHeaderModel = new ListModelList<SelectRange>(dtlItmProductionIssue);
        listboxSelect.setModel(DtlItemHeaderModel);
        listboxSelect.setItemRenderer(new ListitemRenderer<SelectRange>()
        {
            public void render(final Listitem lstm, SelectRange t, final int i) throws Exception
            {
//                int rowNum = (i + 1);
//                new Listcell("" + rowNum).setParent(lstm);
                new Listcell(t.getProd_serial_id()).setParent(lstm);
                new Listcell(t.getProd_dtl_issue_id()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getSn_from()).setParent(lstm);
                new Listcell(t.getSn_to()).setParent(lstm);
                new Listcell(t.getQuantity()).setParent(lstm);
                new Listcell(t.getItem_detail_id()).setParent(lstm);
                new Listcell(t.getStorage_id()).setParent(lstm);
                new Listcell(t.getItem_loc_id()).setParent(lstm);
                new Listcell(t.getLot_id()).setParent(lstm);
                new Listcell(t.getWh_id()).setParent(lstm);
                new Listcell(t.getWh_loc_id()).setParent(lstm);
                lstm.addEventListener("onDoubleClick", new EventListener()
                {
                    @Override
                    public void onEvent(Event event)
                    {
//                        editLine();
                    }
                });
            }
        });
    }

    public void listDetailRange()
    {
        String InProdIssueDtlId = this.getProdIssueDtlId();
        Map map = new HashMap<String, Object>();
        map.put("InProdIssueDtlId", InProdIssueDtlId);

        List<SelectRange> dtlItmProductionIssue = model.listDetailRange(map);
        DtlItemDetailModel = new ListModelList<SelectRange>(dtlItmProductionIssue);
        listboxUnselect.setModel(DtlItemDetailModel);
        listboxUnselect.setItemRenderer(new ListitemRenderer<SelectRange>()
        {
            public void render(final Listitem lstm, SelectRange t, final int i) throws Exception
            {
//                int rowNum = (i + 1);
//                new Listcell("" + rowNum).setParent(lstm);
                new Listcell(t.getProd_serial_id()).setParent(lstm);
                new Listcell(t.getProd_dtl_issue_id()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getSn_from()).setParent(lstm);
                new Listcell(t.getSn_to()).setParent(lstm);
                new Listcell(t.getQuantity()).setParent(lstm);
                new Listcell(t.getItem_detail_id()).setParent(lstm);
                new Listcell(t.getStorage_id()).setParent(lstm);
                new Listcell(t.getItem_loc_id()).setParent(lstm);
                new Listcell(t.getLot_id()).setParent(lstm);
                new Listcell(t.getWh_id()).setParent(lstm);
                new Listcell(t.getWh_loc_id()).setParent(lstm);
                lstm.addEventListener("onDoubleClick", new EventListener()
                {
                    @Override
                    public void onEvent(Event event)
                    {
//                        editLine();
                    }
                });
            }
        });
    }

    public boolean validateSelectSn()
    {
        
        String InItemDtlId = dtlItmSelectRangeHeader.getItem_detail_id();
        Map map = new HashMap<String, Object>();
        map.put("InItemDtlId", InItemDtlId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        
        System.out.println("Map validateSelect SN : "+map);
        model.validateSelectSn(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("1"))
        {
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return false;
        } else
        {
            return true;
        }
    }

    @Listen("onClick=#btnSelect")
    public void select()
    {
        if (listboxSelect.getSelectedCount() != 0)
        {
            if (validateSelectSn())
            {
                showInputSelectRange("Select");
            }
        } else
        {
            Messagebox.show("Data Belum Di Pilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Listen("onClick=#btnUnselect")
    public void unSelect()
    {
        if (listboxUnselect.getSelectedCount() != 0)
        {
//            if (validateSelectSn())
//            {
                showInputSelectRange("UnSelect");
//            }
        } else
        {
            Messagebox.show("Data Belum Di Pilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void showInputSelectRange(String Select)
    {
        Map map = new HashMap();
        DlgInputSelectRange dlgInputSelectRange = new DlgInputSelectRange();
        dlgInputSelectRange.setDlgSelectRange(this);
        map.put("controller", dlgInputSelectRange);
        if (Select.equalsIgnoreCase("Select"))
        {
            map.put("Operation", Select);
            map.put("ProdIssueDtlId", dtlItmSelectRangeHeader.getProd_dtl_issue_id());
            map.put("ItemLocId", dtlItmSelectRangeHeader.getItem_loc_id());
            map.put("FromSN", dtlItmSelectRangeHeader.getSn_from());
            map.put("Qty", dtlItmSelectRangeHeader.getQuantity());
            map.put("ToSN", dtlItmSelectRangeHeader.getSn_to());
            map.put("WhId", txtwhId.getText());
            map.put("WoId", txtwoId.getText());
            map.put("ItemId", txtItemId.getText());
            
        } else if (Select.equalsIgnoreCase("UnSelect"))
        {
            map.put("Operation", Select);
            map.put("ProdIssueDtlId", dtlItmSelectRangeDetail.getProd_dtl_issue_id());
            map.put("ItemLocId", dtlItmSelectRangeDetail.getItem_loc_id());
            map.put("FromSN", dtlItmSelectRangeDetail.getSn_from());
            map.put("Qty", dtlItmSelectRangeDetail.getQuantity());
            map.put("ToSN", dtlItmSelectRangeDetail.getSn_to());
             map.put("WhId", txtwhId.getText());
            map.put("WoId", txtwoId.getText());
            map.put("ItemId", txtItemId.getText());
        }
      Executions.createComponents("Tcash/DlgInputSelectRange.zul", null, map);
    }

    @Listen("onClick=#btnClose")
    public void close()
    {
        dlgSelectRange.detach();
    }

    public DlgProductionIssue getDlgProductionIssue()
    {
        return dlgProductionIssue;
    }

    public void setDlgProductionIssue(DlgProductionIssue dlgProductionIssue)
    {
        this.dlgProductionIssue = dlgProductionIssue;
    }

}
