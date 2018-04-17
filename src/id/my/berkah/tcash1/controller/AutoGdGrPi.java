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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class AutoGdGrPi extends SelectorComposer<Component>{
       private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    
    @Wire
    Window win_auto;

    @Wire
    Textbox txtwo,txtwoid,txtwhdesc,txtwhid,txtwhcode,txtautonumber,txtautoid,txtautdate,txtcreationby,txtcreationdate
            ,txtgdid,txtgdnumber,txtgrid,txtgrnumber,txtprodissueid,txtprodissue,txtwipreceiptid,txtwipreceiptnumber;
    
    @Wire
    Button btnwo;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
//  ListautoGenerate  parentController ;

//    @Listen("onCreate=#win_auto")
//    public void onCreateWindow(){
//        parentController=(ListautoGenerate)win_auto.getAttribute("parentController");
//    }
    
    @Listen("onClick=#btnwo")
    public void lovWO() {
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, po_id as \"Id\",purchase_order as \"Pruchase Order Number\" from table(pkg_tcash_auto_gen.LovWO(''))where (upper(po_id) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_auto_gen.LovWO(''))Where purchase_order LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2});
        composerLov.setComponentTransferData(new Textbox[]{txtwoid, txtwo});
        composerLov.setHiddenColumn(new int[]{0, 1});

        txtwoid.setText("");
        txtwo.setText("");
        composerLov.setTitle("List Of Purchase Order");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_auto);
        w.doModal();
    }

      @Listen("onClick=#btnwh")
        public void lOVWhreceipt(){
         HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, wh_id as \"Id\",wh_code as \"Warehouse Code\",wh_description as \"Description\" from table(pkg_tcash_auto_gen.LovWh('','583','"+userId+"','2'))where (upper(wh_code) like upper('?') or upper(wh_description) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_auto_gen.LovWh('','583','"+userId+"','2'))Where wh_description LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2,3});
        composerLov.setComponentTransferData(new Textbox[]{txtwhid, txtwhcode,txtwhdesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        txtwhid.setText("");
        txtwhcode.setText("");
        txtwhdesc.setText("");
        composerLov.setTitle("List WIP Receipt");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_auto);
        w.doModal();
    }

    @Listen("onClick=#btnsubmit")
    public void buttnSubmit(){
    submitAutoGenerateLOOP1();
    }
    
    
    
    @Listen(Events.ON_CLICK+"=#close")
    public void buttonClose(){
        win_auto.detach();
    }
    
    public void submitAutoGenerateLOOP1(){
        System.out.println("wo "+txtwoid.getValue());
        System.out.println("wh wip "+txtwhid.getValue());
        System.out.println("user "+userId);
        Map<String,Object>map = model.dorunningAuto(txtwoid.getValue(), txtwhid.getValue(),userId);
        if (map.get("OutError").equals(0)) {
            Messagebox.show(map.get("OutMessages").toString(),"Auto Generator",Messagebox.OK,Messagebox.EXCLAMATION);
            txtautoid.setText(map.get("OutAutoGenId").toString());
            txtautonumber.setText(map.get("OutAutoGenNum").toString());
            txtautdate.setText(map.get("OutAutoGenDate").toString());
            txtcreationby.setText(map.get("OutUserName").toString());
            txtcreationdate.setText(map.get("OutAutoGenDate").toString());
            txtgdid.setText(map.get("OutGdHdrId").toString());
            txtgdnumber.setText(map.get("OutGdHdrNo").toString());
            txtgrid.setText(map.get("OutReceiveId").toString());
            txtgrnumber.setText(map.get("OutReceiveNo").toString());
            txtprodissueid.setText(map.get("OutProdIssueId").toString());
            txtprodissue.setText(map.get("OutProdIssueNo").toString());
            txtwipreceiptid.setText(map.get("OutWipRcpId").toString());
            txtwipreceiptnumber.setText(map.get("OutWipRcpNo").toString());
           System.out.println("success 2");
        } else {
             Messagebox.show(map.get("OutMessages").toString(),"Auto Generator",Messagebox.OK,Messagebox.EXCLAMATION);
        }
//        parentController.buttonRefresh();
        
    }
   
}


