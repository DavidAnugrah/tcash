/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Azec
 */
public class SelectEditSNBundling extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window winselectfromto;

    @Wire
    Textbox flag, txtitem, txtitemlocid, txtbundlingid, txtbundlingDtlId, txtsn, txtto, txtreg;

    @Wire
    Intbox txtqty;

    ListEditSnBundling parentController;
    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#winselectfromto")
    public void onCreateWindow() {
        parentController = (ListEditSnBundling) winselectfromto.getAttribute("parentController");
    }

    @Listen("onClick=#Close1")
    public void btnclose() {
        winselectfromto.detach();

    }

    @Listen("onClick=#Submit")
    public void buttonSubmit() {
        Map<String, Object> map = model.doSetSelectSN(flag.getText(), txtbundlingid.getText(), txtitemlocid.getText(), txtsn.getText(), txtto.getText(), txtreg.getText(), txtqty.getText(), userId);
        if (map.get("OutError").equals(0)) {
            parentController.refreshTemp();
            parentController.refreshResult();
            winselectfromto.detach();
        } else {
            Messagebox.show(map.get("OutMessages").toString(), "Edit SN Bundling", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }

}
