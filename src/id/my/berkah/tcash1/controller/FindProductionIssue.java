/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.IDefines;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

/**
 *
 * @author Ari
 */
public class FindProductionIssue extends SelectorComposer<Component>
{

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Wire
    Window windowFindPIDialog;

    @Wire
    Datebox dateFrom,
            dateTo;

    @Wire
    Textbox txtProdIssueNo,
            txtWoNo;

    @Wire
    Button btnLovProdIssueNo,
            btnLovWoNo;

    @Wire
    Combobox cmbStatus;

    ListProductionIssue listProductionIssue;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        cmbStatus.setModel(new ListModelList<>(IDefines.RETURN_STATUS_pi.DESC));
    }

   
    public void ok()
    {
        if (checkParam())
        {
            listProductionIssue.refresh(txtProdIssueNo.getText(), dateFrom.getText(), dateTo.getText(), cmbStatus.getValue(), txtWoNo.getText());
        }
    }

    public boolean checkParam()
    {
        boolean status = false;
        if (dateFrom.getText().equalsIgnoreCase(""))
        {
            Messagebox.show("Period from belum di pilih");
        } else if (dateTo.getText().equalsIgnoreCase(""))
        {
            Messagebox.show("Period to belum di pilih");
        } else
        {
            String from = dateFrom.getText();
            String to = dateTo.getText();
            try
            {
                Date x = simpleDateFormat.parse(from);
                Date y = simpleDateFormat.parse(to);
                if (x.compareTo(y) <= 0)
                {
//                    if (txtProdIssueNo.getText().equalsIgnoreCase(""))
//                    {
//                        alert("Production Issue belum di pilih");
//                        txtProdIssueNo.focus();
//                        return status;
//                    }
//                    if (txtWoNo.getText().equalsIgnoreCase(""))
//                    {
//                        alert("Work Order Number Belum di pilih");
//                        txtWoNo.focus();
//                        return status;
//                    }
                    status = true;
                } else
                {
                    Messagebox.show("Period to harus lebih besar atau sama dengan period from");
                }
            } catch (ParseException ex)
            {
                Logger.getLogger(FindProductionIssue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    @Listen("onClick=#btnClose")
    public void close()
    {
        windowFindPIDialog.detach();
    }

    public ListProductionIssue getListProductionIssue()
    {
        return listProductionIssue;
    }

    public void setListProductionIssue(ListProductionIssue listProductionIssue)
    {
        this.listProductionIssue = listProductionIssue;
    }
    
     @Listen("onClick=#btnOk")
    public void find(){
        Map<String,Object> map = new HashMap();
        map.put("InProdIssueNo", (txtProdIssueNo.getValue()== null||txtProdIssueNo.getValue().trim().equals("")) ? "":txtProdIssueNo.getValue().trim());
         if (dateFrom.getValue() !=  null && dateTo.getValue() !=  null) {
             map.put("InDateFrom", simpleDateFormat.format(dateFrom.getValue()));
             map.put("InDateTo",  simpleDateFormat.format(dateTo.getValue()));
         } else {
              map.put("InDateFrom", null);
        map.put("InDateTo", null);
         }
        map.put("InStatus",(cmbStatus.getSelectedIndex()==-1) ? null : IDefines.RETURN_STATUS_pi.CODE[cmbStatus.getSelectedIndex()]);
        map.put("InWoNo", txtWoNo.getValue().trim().equals("")||txtWoNo.getValue().trim()==null ?null:txtWoNo.getValue().trim());
        System.out.println(map);
        listProductionIssue.filterParam(map);
        windowFindPIDialog.detach();
     }
}
