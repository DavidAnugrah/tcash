/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.DtlItmProductionIssue;
import id.my.berkah.tcash1.model.ProductionIssued;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

/**
 *
 *add
 */
public class DlgProductionIssue extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];

    @Wire
    Window windowProductionIssue;

    @Wire
    Textbox txtOperation,
            txtBusinessUnitId,
            txtBusinessUnit,
            txtCreationDate,
            txtProductionIssuedId,
            txtProductionIssuedNumber,
            txtCreatedBy,
            txtProductionIssuedDate,
            txtApproveDate,
            txtWorkOrderNumberId,
            txtWorkOrderNumber,
            txtApprovedBy,
            txtItemFinishGoodsId,
            txtItemFinishGoods,
            txtSubmitDate,
            txtWarehouseId,
            txtWarehouse,
            txtSubmitedBy,
            txtWIPWarehouseId,
            txtWIPWarehouse,
            txtStatus,
            txtStatusid;

    @Wire
    Listbox listboxDtlItem;

    @Wire
    Button btnLovRegional,
            btnLovWorkOrder,
            btnLovItemFinishGoods,
            btnLovWarehouse,
            btnLovWIPWarehouse,
            btnNew,
            btnSave,
            btnAddLine,
            btnEditLine,
            btnSelectSN,
            btnAutoAllocateSN,
            btnApprove,
            btnSubmit,
            btnClose,
            btndelete;

    ListProductionIssue listProductionIssue;

    ListModel<DtlItmProductionIssue> DtlItemModel;

    DtlItmProductionIssue dtlItmProductionIssue;
    
    ProductionIssued productionIssued;

   
  
    ModelTcashCTLR model = new ModelTcashCTLR();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        if (txtOperation.getText().equalsIgnoreCase("Edit")) {
//            validateForm(txtStatus.getText());
            listDtl();
            attrdecStatus();
            listHdr(txtProductionIssuedId.getValue());
        }
    }

    public Textbox getTxtWarehouseId() {
        return txtWarehouseId;
    }

    public void setTxtWarehouseId(Textbox txtWarehouseId) {
        this.txtWarehouseId = txtWarehouseId;
    }

    public void validateForm(String status) {
        if (status.equalsIgnoreCase("DRAFT")) {

        } else if (status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("CANCELED")) {
            btnLovRegional.setDisabled(true);
            btnLovWorkOrder.setDisabled(true);
            btnLovItemFinishGoods.setDisabled(true);
            btnLovWarehouse.setDisabled(true);
            btnLovWIPWarehouse.setDisabled(true);

//            btnNew.setDisabled(true);
            btnSave.setDisabled(true);
            btnAddLine.setDisabled(true);
            btnEditLine.setDisabled(true);
            btnSelectSN.setDisabled(true);
            btnAutoAllocateSN.setDisabled(true);
            btnApprove.setDisabled(true);
            btnSubmit.setDisabled(true);
            btndelete.setDisabled(true);
//            btnClose.setDisabled(true);
        }
    }

    @Listen(Events.ON_CLICK + " = #btnLovRegional")
    public void btnLovRegional() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovBU('?'," + ParameterGlobal.getParameterGlobal()[2] + "," + ParameterGlobal.getParameterGlobal()[0] + "))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovBU('?'," + ParameterGlobal.getParameterGlobal()[2] + "," + ParameterGlobal.getParameterGlobal()[0] + "))");
        composerLov.setColumnWidth(new String[]{
            "50px", "50px", "100px", "335px"
        });
        composerLov.setSelectedColumn(new int[]{
            1, 2
        });
        composerLov.setComponentTransferData(new Textbox[]{
            txtBusinessUnitId, txtBusinessUnit
        });
        composerLov.setHiddenColumn(new int[]{
            1
        });

        composerLov.setTitle("List Of Business Unit");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(windowProductionIssue);
        w.doModal();
    }

    @Listen(Events.ON_CLICK + " = #btnLovWorkOrder")
    public void btnLovWorkOrder() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovWO('?'))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovWO('?'))");
        composerLov.setColumnWidth(new String[]{
            "50px", "50px", "435px"
        });
        composerLov.setSelectedColumn(new int[]{
            1, 2
        });
        composerLov.setComponentTransferData(new Textbox[]{
            txtWorkOrderNumberId, txtWorkOrderNumber
        });
        composerLov.setHiddenColumn(new int[]{
            1
        });

        composerLov.setTitle("List Of Work Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(windowProductionIssue);
        w.doModal();
    }

    @Listen(Events.ON_CLICK + " = #btnLovItemFinishGoods")
    public void btnLovItemFinishGoods() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovItem('?'))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovItem('?'))");
        composerLov.setColumnWidth(new String[]{
            "50px", "50px", "105px", "270px", "60px"
        });
        composerLov.setSelectedColumn(new int[]{
            1, 3
        });
        composerLov.setComponentTransferData(new Textbox[]{
            txtItemFinishGoodsId, txtItemFinishGoods
        });
        composerLov.setHiddenColumn(new int[]{
            1
        });

        composerLov.setTitle("List Of Item Finish Goods");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(windowProductionIssue);
        w.doModal();
    }

    @Listen(Events.ON_CLICK + " = #btnLovWarehouse")
    public void btnLovWarehouse() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovWh('?'," + txtBusinessUnitId.getText() + "," + ParameterGlobal.getParameterGlobal()[0] + "," + ParameterGlobal.getParameterGlobal()[2] + "))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovWh('?'," +  txtBusinessUnitId.getText() + "," + ParameterGlobal.getParameterGlobal()[0] + "," + ParameterGlobal.getParameterGlobal()[2] + "))");
        composerLov.setColumnWidth(new String[]{
            "50px", "50px", "105px", "328px"
        });
        composerLov.setSelectedColumn(new int[]{
            1, 3
        });
        composerLov.setComponentTransferData(new Textbox[]{
            txtWarehouseId, txtWarehouse
        });
        composerLov.setHiddenColumn(new int[]{
            1
        });

        composerLov.setTitle("List Of Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(windowProductionIssue);
        w.doModal();
    }

    @Listen(Events.ON_CLICK + " = #btnLovWIPWarehouse")
    public void btnLovWIPWarehouse() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovWh('?'," + txtBusinessUnitId.getText() + "," + ParameterGlobal.getParameterGlobal()[0] + "," + ParameterGlobal.getParameterGlobal()[2] + "))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovWh('?'," +txtBusinessUnitId.getText() + "," + ParameterGlobal.getParameterGlobal()[0] + "," + ParameterGlobal.getParameterGlobal()[2] + "))");
        composerLov.setColumnWidth(new String[]{
            "50px", "50px", "105px", "328px"
        });
        composerLov.setSelectedColumn(new int[]{
            1, 3
        });
        composerLov.setComponentTransferData(new Textbox[]{
            txtWIPWarehouseId, txtWIPWarehouse
        });
        composerLov.setHiddenColumn(new int[]{
            1
        });

        composerLov.setTitle("List Of Warehouse");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(windowProductionIssue);
        w.doModal();
    }

    @Listen("onClick=#btnNew")
    public void newPI() {
        txtBusinessUnitId.setText("");
        txtBusinessUnit.setText("");
        txtCreationDate.setText("");
        txtProductionIssuedId.setText("");
        txtProductionIssuedNumber.setText("");
        txtCreatedBy.setText("");
        txtProductionIssuedDate.setText("");
        txtApproveDate.setText("");
        txtWorkOrderNumberId.setText("");
        txtWorkOrderNumber.setText("");
        txtApprovedBy.setText("");
        txtItemFinishGoodsId.setText("");
        txtItemFinishGoods.setText("");
        txtSubmitDate.setText("");
        txtWarehouseId.setText("");
        txtWarehouse.setText("");
        txtSubmitedBy.setText("");
        txtWIPWarehouseId.setText("");
        txtWIPWarehouse.setText("");
        txtStatus.setText("");
        listDtl();
    }

    @Listen("onClick=#btnSave")
    public void save() {
        String ProductionIssuedId = txtProductionIssuedId.getText();
        if (ProductionIssuedId.equalsIgnoreCase("")) {
            validateInsertHdr();
        } else {
            validateUpdateHdr();
        }
    }

    public void validateInsertHdr() {
        String BuId = txtBusinessUnitId.getText();
        String WOId = txtWorkOrderNumberId.getText();
        String ItemId = "1";
        String WhId = txtWarehouseId.getText();
        String WipId = txtWIPWarehouseId.getText();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        HashMap map = new HashMap<String, Object>();
        map.put("InBuId", BuId);
        map.put("InWoId", WOId);
        map.put("InItemId", ItemId);
        map.put("InWhId", WhId);
        map.put("InWipWhId", WipId);
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.validateInsertHdr(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("1")) {
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        insertHdr(map);
    }

    public Textbox getTxtWorkOrderNumberId() {
        return txtWorkOrderNumberId;
    }

    public void setTxtWorkOrderNumberId(Textbox txtWorkOrderNumberId) {
        this.txtWorkOrderNumberId = txtWorkOrderNumberId;
    }

    public void insertHdr(Map map) {
        String WoNo = txtWorkOrderNumber.getText();
        map.put("InWoNo", WoNo);
        map.put("OutProdIssueId", null);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.insertHdr(map);

        String OutProdIssueId = String.valueOf(map.get("OutProdIssueId"));
        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("1")) {
            return;
        }
        listHdr(OutProdIssueId);
    }

    public void listHdr(String InProdIssueId) {
        HashMap map = new HashMap<String, Object>();
        map.put("InProdIssueId", InProdIssueId);
        ProductionIssued productionIssued = model.listHdr(map);

        txtBusinessUnitId.setText(productionIssued.getBu_id());
        txtBusinessUnit.setText(productionIssued.getBu_code());
        txtCreationDate.setText(productionIssued.getCreate_date());
        txtProductionIssuedId.setText(productionIssued.getProd_issue_id());
        txtProductionIssuedNumber.setText(productionIssued.getProd_issue_no());
        txtCreatedBy.setText(productionIssued.getCreated_by());
        txtProductionIssuedDate.setText(productionIssued.getProd_issue_date());
        txtApproveDate.setText(productionIssued.getApprove_date());
        txtWorkOrderNumberId.setText(productionIssued.getWo_id());
        txtWorkOrderNumber.setText(productionIssued.getWo_no());
        txtApprovedBy.setText(productionIssued.getApproved_by());
        txtItemFinishGoodsId.setText(productionIssued.getItem_id());
        txtItemFinishGoods.setText(productionIssued.getItem_code());
        txtSubmitDate.setText(productionIssued.getSubmit_date());
        txtWarehouseId.setText(productionIssued.getWh_id());
        txtWarehouse.setText(productionIssued.getWh_desc());
        txtSubmitedBy.setText(productionIssued.getSubmited_by());
        txtWIPWarehouseId.setText(productionIssued.getWip_wh_id());
        txtWIPWarehouse.setText(productionIssued.getWip_wh_desc());
        txtStatus.setText(productionIssued.getStatus_desc());
    }

    public void validateUpdateHdr() {
        String BuId = txtBusinessUnitId.getText();
        String WOId = txtWorkOrderNumberId.getText();
        String ItemId = "1";
        String WhId = txtWarehouseId.getText();
        String WipId = txtWIPWarehouseId.getText();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        HashMap map = new HashMap<String,Object>();
        map.put("InBuId", BuId);
        map.put("InWoId", WOId);
       map.put("InItemId", ItemId);
        map.put("InWhId", WhId);
        map.put("InWipWhId", WipId);
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.validateUpdateHdr(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("1")) {
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        updateHdr(map);
    }

    public void updateHdr(Map map) {
        String ProdIssueId = txtProductionIssuedId.getText();
        String WoNo = txtWorkOrderNumber.getText();
        map.put("InProdIssueId", ProdIssueId);
        map.put("InWoNo", WoNo);
        map.put("OutProdIssueId", null);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.updateHdr(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("1")) {
            return;
        }
        listHdr(ProdIssueId);
    }

    public void listDtl() {
        String InInProdIssueId = txtProductionIssuedId.getText();
        Map map = new HashMap<String, Object>();
        map.put("InInProdIssueId", InInProdIssueId);

        List<DtlItmProductionIssue> dtlItmProductionIssue = model.listDtl(map);
        DtlItemModel = new ListModelList<DtlItmProductionIssue>(dtlItmProductionIssue);
        listboxDtlItem.setModel(DtlItemModel);
        listboxDtlItem.setItemRenderer(new ListitemRenderer<DtlItmProductionIssue>() {
            public void render(final Listitem lstm, DtlItmProductionIssue t, final int i) throws Exception {
//                int rowNum = (i + 1);
//                new Listcell("" + rowNum).setParent(lstm);
                new Listcell(t.getProd_dtl_issue_id()).setParent(lstm);
                new Listcell(t.getProd_issue_id()).setParent(lstm);
                new Listcell(t.getItem_id()).setParent(lstm);
                new Listcell(t.getItem_code()).setParent(lstm);
                new Listcell(t.getItem_description()).setParent(lstm);
                new Listcell(t.getQuantity()).setParent(lstm);
                new Listcell(t.getUnit()).setParent(lstm);
                new Listcell(t.getCreated_by()).setParent(lstm);
                new Listcell(t.getCreate_date()).setParent(lstm);
                lstm.addEventListener("onDoubleClick", new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        editLine();
                    }
                });
            }
        });
    }

    @Listen("onSelect = #listboxDtlItem")
    public void selectListboxDtlItem() {
        Set<DtlItmProductionIssue> selectedDtl = ((ListModelList<DtlItmProductionIssue>) DtlItemModel).getSelection();
        for (DtlItmProductionIssue DtlItem : selectedDtl) {
            this.dtlItmProductionIssue = DtlItem;
        }
    }

    @Listen("onClick=#btnAddLine")
    public void addLine() {
         if (txtStatusid.getText().isEmpty()){
            Messagebox.show("Please save this issue first","Production Issue",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        } 
        if (txtStatus.getText().equalsIgnoreCase("SUBMITTED")||txtStatus.getText().equalsIgnoreCase("APPROVED")||txtStatus.getText().equalsIgnoreCase("CANCELED")){
            Messagebox.show("This Issue has been "+ txtStatus.getValue(),"Production Issue",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        } 
        
        Map map = new HashMap();
        DlgAddItemProductionIssue dlgAddItemProductionIssue = new DlgAddItemProductionIssue();
        dlgAddItemProductionIssue.setDlgProductionIssue(this);
        map.put("controller", dlgAddItemProductionIssue);
        map.put("operation", "Add");
        map.put("woId", txtWorkOrderNumberId.getText());
        map.put("productionIssuedId", txtProductionIssuedId.getText());
        Executions.createComponents("Tcash/DlgAddItemProductionIssue.zul", null, map);
//    }
    }

//    @Listen("onClick=#btnEditLine")
    public void editLine() {
        if (listboxDtlItem.getSelectedCount() != 0) {
            Map map = new HashMap();
            DlgAddItemProductionIssue dlgAddItemProductionIssue = new DlgAddItemProductionIssue();
            dlgAddItemProductionIssue.setDlgProductionIssue(this);
            map.put("controller", dlgAddItemProductionIssue);
            map.put("operation", "Edit");
            map.put("productionIssuedId", dtlItmProductionIssue.getProd_issue_id());
            map.put("productionIssuedIdDtl", dtlItmProductionIssue.getProd_dtl_issue_id());
            map.put("ItemCodeId", dtlItmProductionIssue.getItem_id());
            map.put("ItemCode", dtlItmProductionIssue.getItem_code());
            map.put("ItemDescription", dtlItmProductionIssue.getItem_description());
            map.put("productionIssuedId", txtProductionIssuedId.getText());
            map.put("woId", txtWorkOrderNumberId.getText());
            Executions.createComponents("Tcash/DlgAddItemProductionIssue.zul", null, map);
        } else {
            Messagebox.show("Data Belum dipilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void validateStatus(String prodIssueId,int status) {
        HashMap map = new HashMap<String, Object>();
        map.put("InProdIssueId", prodIssueId);
        map.put("InStatus", status);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.validateStatus(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("1")) {
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        updateStatusPi(map);
    }

    public void updateStatusPi(Map map) {
        String userId = ParameterGlobal.getParameterGlobal()[0];
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.updateStatusPi(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("1")) {
            return;
        }
    }

    @Listen("onClick=#btnSelectSN")
    public void selectSN() {
        if (listboxDtlItem.getSelectedCount() != 0) {
            insertSelectSnTemp();
            Map map = new HashMap();
            DlgSelectRange dlgSelectRange = new DlgSelectRange();
            dlgSelectRange.setDlgProductionIssue(this);
            dlgSelectRange.setProdIssueDtlId(dtlItmProductionIssue.getProd_dtl_issue_id());
            
          
            map.put("controller", dlgSelectRange);
            map.put("ProdIssueDtlId", dtlItmProductionIssue.getProd_dtl_issue_id());
            map.put("WoId",txtWorkOrderNumberId.getText());
            map.put("WhId",txtWarehouseId.getText());
            map.put("ItemId",dtlItmProductionIssue.getItem_id());
            Window w =(Window)Executions.createComponents("Tcash/DlgSelectRange.zul", null, map);
            w.setAttribute("parentController", this);
        } else {
            Messagebox.show("Data Belum dipilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void insertSelectSnTemp() {
        String ProdIssueDetailId = dtlItmProductionIssue.getProd_dtl_issue_id();
        String woId = txtWorkOrderNumberId.getText();
        String whId = txtWarehouseId.getText();
        String itemId = dtlItmProductionIssue.getItem_id();
        String userId = ParameterGlobal.getParameterGlobal()[0];
        HashMap map = new HashMap();
        map.put("InProdIssueDtlId", ProdIssueDetailId);
        map.put("InWoId", woId);
        map.put("InWhId", whId);
        map.put("InItemId", itemId);
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.insertSelectSnTemp(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("1")) {
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
    }

    @Listen(Events.ON_CLICK + " = #btnAutoAllocateSN")
    public void autoAllocateSN() {
        if (txtStatus.getText().equals("")) {
            Messagebox.show("Please Save this issue first");
            return; 
        }
        if (!txtStatus.getText().equalsIgnoreCase("draft")) {
            Messagebox.show("This Issue has been "+txtStatus.getText());
            return;
        }
        if (listboxDtlItem.getSelectedCount() != 0) {
            HashMap map = new HashMap<String, Object>();
            map.put("InPoId", txtProductionIssuedId.getText());
            map.put("InProdIssueDtlId", dtlItmProductionIssue.getProd_dtl_issue_id());
            map.put("InUserId", ParameterGlobal.getParameterGlobal()[0]);
            map.put("OutError", null);
            map.put("OutMessages", null);
            model.autoInvSelectSN(map);

            String OutError = String.valueOf(map.get("OutError"));
            String OutMessages = String.valueOf(map.get("OutMessages"));
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            if (OutError.equalsIgnoreCase("1")) {
                return;
            }
        } else {
            Messagebox.show("Data Belum dipilih", "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Listen("onClick=#btnSubmit")
    public void submit() {
        validateStatus( txtProductionIssuedId.getText(),2);
        listHdr(txtProductionIssuedId.getText());
//        validateForm(txtStatus.getText());
    }

    @Listen("onClick=#btnApprove")
    public void approve() {
        validateStatus(txtProductionIssuedId.getText(),3);
        listHdr(txtProductionIssuedId.getText());
//        validateForm(txtStatus.getText());
    }

    @Listen("onClick=#btnClose")
    public void close() {
//        listProductionIssue.refresh();
        windowProductionIssue.detach();
    }

    public ListProductionIssue getListProductionIssue() {
        return listProductionIssue;
    }

    public void setListProductionIssue(ListProductionIssue listProductionIssue) {
        this.listProductionIssue = listProductionIssue;
    }

    @Listen("onClick=#btndelete")
    public void deleteRow() {
        Messagebox.show("Are you sure want to Delete?",
                "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        if (Messagebox.ON_OK.equals(e.getName())) {
                            if (!txtStatus.getText().equalsIgnoreCase("draft")) {
                                Messagebox.show("This Issue has been" +txtStatus.getText());
                                return;
                            }
                            if (dtlItmProductionIssue.getProd_dtl_issue_id() == null) {
                                Messagebox.show("Please select data first");
                                return;
                            } else {
                            String txtProdDtl = dtlItmProductionIssue.getProd_dtl_issue_id();
                            Map<String, Object> map = model.doDeleteListDtl(txtProductionIssuedId.getValue(), txtProdDtl, txtStatusid.getText(), userId);
                            if (map.get("OutError").equals(0)) {
                                Messagebox.show(map.get("OutMessages").toString());
                                listDtl();
                            } else {
                                Messagebox.show(map.get("OutMessages").toString());
                            }
                            }
                        } else if (Messagebox.ON_CANCEL.equals(e.getName())) {

                        }
                    }

                });
    }

    @Listen(Events.ON_CTRL_KEY + " = #listbox")
    public void delete(Event event) {

        int keyCode = ((KeyEvent) event).getKeyCode();
        String s = "";
        switch (keyCode) {
            case KeyEvent.DELETE: {
                System.out.println(keyCode+" key");
                deleteRow();
            }
        }

    }

    void attrdecStatus() {
        if (txtStatus.getText().equalsIgnoreCase("Submitted")) {
            txtStatusid.setText("2");
        } else if (txtStatus.getText().equalsIgnoreCase("Approved")) {
            txtStatusid.setText("3");
        } else if (txtStatus.getText().equalsIgnoreCase("Canceled")) {
            txtStatusid.setText("4");
        } else {
            txtStatusid.setText("0");
        }
    }
}
