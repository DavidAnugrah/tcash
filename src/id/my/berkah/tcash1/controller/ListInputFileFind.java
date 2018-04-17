/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.controller.LovController;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class ListInputFileFind extends SelectorComposer<Component>{
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    @Wire
    Textbox txtifid,txtifno,txtWoid,txtWoNo,txtstat;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
    
    @Wire
    Combobox  cmbStatus;
    
    @Wire
    Window win_list_find_IF;
    
   ListInputFile parentController;
   
   
    
    @Listen("onCreate=#win_list_find_IF")
    public void oncreateWindow(){
        parentController = (ListInputFile)win_list_find_IF.getAttribute("parentController");
    }
    
     @Listen("onClick = #lovif")
    public void lovIF() {
        HashMap map = new HashMap<String, Object>();
        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No, if_id as \"Id\",if_no as \"Input File No\",po_id as \"po id\",purchase_order as \"Work Order No\" from table(pkg_tcash_lov.LovIf(''))where (upper(if_no) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovIf('" + " " + "'))Where if_no LIKE '%?%' or purchase_order LIKE '%?%'");
        composerLov.setSelectedColumn(new int[]{1, 2, 3,4});
        composerLov.setComponentTransferData(new Textbox[]{txtifid, txtifno,txtWoid,txtWoNo});
        composerLov.setHiddenColumn(new int[]{0, 1,3});


        txtWoid.setText("");
        txtWoNo.setText("");
        composerLov.setTitle("List Of Input File");
        composerLov.setWidth("500px");
        composerLov.setHeight("335px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);

        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_find_IF);
        w.doModal();

    }
    
    @Listen("onClick=#Close1")
    public void closeWindow(){
        win_list_find_IF.detach();
    }
    
    @Listen("onClick=#goFind")
    public void find(){
         Map<String, Object> maper = new HashMap<String, Object>();
        maper.put("InIfId", txtifid.getText() == null ? "":txtifid.getText());
        maper.put("InPoId", txtWoid.getText()== null ? "":txtWoid.getText());
        maper.put("InDateFrom", txtDateFrom.getText()== null ? "":txtDateFrom.getText());
        maper.put("InDateTo", txtDateTo.getText()== null ? "":txtDateTo.getText());
        maper.put("InStatus", txtstat.getText()== null ? "":txtstat.getText());
        parentController.findParam(txtifid.getText(), txtWoid.getText(), txtDateFrom.getText(), txtDateTo.getText(), txtstat.getText());
        win_list_find_IF.detach();
    }   
    
        @Listen("onSelect=#cmbStatus")
    public void selectstatus(){
        if (cmbStatus.getSelectedIndex()==0) {
            txtstat.setText("");
        } else if (cmbStatus.getSelectedIndex()==1) {
            txtstat.setText("1");
        }else if (cmbStatus.getSelectedIndex()==2) {
            txtstat.setText("2");
        }else if (cmbStatus.getSelectedIndex()==3) {
            txtstat.setText("3");
        }else if (cmbStatus.getSelectedIndex()==4){
             txtstat.setText("4");
        }else if (cmbStatus.getSelectedIndex()==5){
            txtstat.setText("5");
        }
    }
    
}
