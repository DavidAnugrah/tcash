/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Ari
 */
public class DlgAddItemProductionIssue extends SelectorComposer<Component>
{

    @Wire
    Window dlgAddItem;

    @Wire
    Textbox txtOperation, txtProductionIssuedId, txtProductionIssuedIdDtl, txtItemCodeId, txtItemCode, txtItemDescription;

    DlgProductionIssue dlgProductionIssue;

    
    ModelTcashCTLR model = new ModelTcashCTLR();

        String data;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
                System.out.println(data+"data=");

       Map map= Executions.getCurrent().getArg();
        data= map.get("woId").toString();
        System.out.println(data+"data");
        
    }

    @Listen("onClick=#btnLovItemCode")
    public void lovItemCode()
    {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();

        composerLov.setQuery("select * from(select rownum as No,t.* from table(pkg_tcash_prod_issue.LovItem('?','"+data+"'))t) where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_prod_issue.LovItem('?','"+data+"'))");
        composerLov.setColumnWidth(new String[]
        {
            "50px", "50px", "105px", "270px", "60px"
        });
        composerLov.setSelectedColumn(new int[]
        {
            1, 2, 3
        });
        composerLov.setComponentTransferData(new Textbox[]
        {
            txtItemCodeId, txtItemCode, txtItemDescription
        });
        composerLov.setHiddenColumn(new int[]
        {
            1
        });

        composerLov.setTitle("List Of Item");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(dlgAddItem);
        w.doModal();
    }

    @Listen("onClick=#btnAdd")
    public void add()
    {
        String status = txtOperation.getText();
//        String ProductionIssuedId = txtProductionIssuedId.getText();
        if (status.equalsIgnoreCase("Add"))
        {
            validateInsertDtl();
        } else if (status.equalsIgnoreCase("Edit"))
        {
            validateUpdateDtl();
        }
    }

    public void validateInsertDtl()
    {
        String itemId = txtItemCodeId.getText();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        HashMap map = new HashMap<String, Object>();
        map.put("InItemId", itemId);
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.validateInsertDtl(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        if (OutError.equalsIgnoreCase("0"))
        {
//            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
        else{
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        insertDtl(map);
    }

    public void insertDtl(Map map)
    {
        map.put("InProdIssueId", txtProductionIssuedId.getText());
        map.put("OutProdIssueDtlId", null);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.insertDtl(map);

        String OutProdIssueId = String.valueOf(map.get("OutProdIssueDtlId"));
        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("0"))
        {
//            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
        else{
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        dlgAddItem.detach();
        dlgProductionIssue.listDtl();
    }

    public void validateUpdateDtl()
    {
        String PiId = txtProductionIssuedId.getText();
        String PiIdDtl = txtProductionIssuedIdDtl.getText();
        String itemId = txtItemCodeId.getText();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        HashMap map = new HashMap<String, Object>();
        map.put("InProdIssueId", PiId);
        map.put("InProdIssueDtlId", PiIdDtl);
        map.put("InItemId", itemId);
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.validateUpdateDtl(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

       if (OutError.equalsIgnoreCase("0"))
        {
//            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
        else{
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        updateDtl(map);
    }

    public void updateDtl(Map map)
    {
        map.put("InProdIssueId", null);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.updateDtl(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));

        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
       if (OutError.equalsIgnoreCase("0"))
        {
//            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        }
        else{
            Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        dlgAddItem.detach();
        dlgProductionIssue.listDtl();
    }

    @Listen("onClick=#btnClose")
    public void close()
    {
        dlgAddItem.detach();
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
