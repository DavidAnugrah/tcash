/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.DtlItmProductionIssue;
import id.my.berkah.util.ParameterGlobal;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
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
public class DlgInputSelectRange extends SelectorComposer<Component>
{

    DlgSelectRange dlgSelectRange;
    
//    DlgSelectRange parentController;
    
    ListProductionIssue listProductionIssue;

    @Wire
    Window dlgInputSelectRange;

    @Wire
    Textbox txtOperation, txtProdIssueDtlId, txtItemLocId, txtFromSN, txtQty, txtToSN,txtItemId,txtwoId,txtwhId;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
//        parentController = (DlgSelectRange)dlgInputSelectRange.getAttribute("parentController");
//        System.out.println("---"+parentController);
    }

    @Listen("onClick=#btnClose")
    public void close()
    {
        dlgInputSelectRange.detach();
    }

    @Listen("onClick=#btnSubmit")
    public void submit()
    {
        String operation = txtOperation.getText();
        if (operation.equalsIgnoreCase("Select"))
        {
            invSelectSN();

        } else if (operation.equalsIgnoreCase("UnSelect"))
        {
            invUnselectSN();

        }
        
         dlgSelectRange.listHeaderRange();
        dlgSelectRange.listDetailRange();
        dlgInputSelectRange.detach();
       

    }

    public void invSelectSN()
    {
        HashMap map = new HashMap<String, Object>();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        map.put("InProdIssueDtlId", txtProdIssueDtlId.getText());
        map.put("InItemLocId", txtItemLocId.getText());
        map.put("InBlockFrom", txtFromSN.getText());
        map.put("InBlockTo", txtToSN.getText());
        map.put("InQty", txtQty.getText());
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
//        System.out.println("map :"+map);
        model.invSelectSN(map);

        String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));
        
        System.out.println(OutError);
        System.out.println(OutMessages);
//        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("1"))
        {
            return;
        }
        dlgSelectRange.listHeaderRange();
        System.out.println("============================" );
        dlgSelectRange.listDetailRange();
    }

    public void invUnselectSN()
    {
        HashMap map = new HashMap<String, Object>();
        String userId = ParameterGlobal.getParameterGlobal()[0];

        map.put("InProdIssueDtlId", txtProdIssueDtlId.getText());
        map.put("InItemLocId", txtItemLocId.getText());
        map.put("InBlockFrom", txtFromSN.getText());
        map.put("InBlockTo", txtToSN.getText());
        map.put("InQty", txtQty.getText());
        map.put("InUserId", userId);
        map.put("OutError", null);
        map.put("OutMessages", null);
        model.invUnselectSN(map);
      
         String OutError = String.valueOf(map.get("OutError"));
        String OutMessages = String.valueOf(map.get("OutMessages"));
        
        
        System.out.println(OutError);
        System.out.println(OutMessages);
        inserselect4un();
//        Messagebox.show(OutMessages, "Informasi", Messagebox.OK, Messagebox.INFORMATION);
        if (OutError.equalsIgnoreCase("1"))
        {
            Messagebox.show(map.get(OutMessages).toString());
        }
        dlgSelectRange.listHeaderRange();
        dlgSelectRange.listDetailRange();
    }
    
    public void inserselect4un(){
        String userId = ParameterGlobal.getParameterGlobal()[0];
        Map<String,Object>map = model.insertSelectSnTemp(txtProdIssueDtlId.getText(), txtwoId.getText(), txtwhId.getText(), txtItemId.getText(),userId);
        if (map.get("OutError").equals("1")) {
              Messagebox.show(map.get("OutMessages").toString());
        }
    }

    public DlgSelectRange getDlgSelectRange()
    {
        return dlgSelectRange;
    }

    public void setDlgSelectRange(DlgSelectRange dlgSelectRange)
    {
        this.dlgSelectRange = dlgSelectRange;
    }

   
}
