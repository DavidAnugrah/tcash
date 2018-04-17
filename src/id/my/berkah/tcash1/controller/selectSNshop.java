/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.util.ParameterGlobal;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
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
public class selectSNshop extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];

    @Wire
    Window winselectfromtoshop;

    @Wire
    Textbox txtitem, txtwip, txtto, txtsn, txtflag, txtitemlocid;

    @Wire
    Intbox txtqty;

    ListSelectRange parentController;

    ModelTcashCTLR model = new ModelTcashCTLR();

    @Listen("onCreate=#winselectfromto")
    public void oncreateWondow() {

    }

    @Listen("onClick=#Close1")
    public void closewindow() {
        winselectfromtoshop.detach();
//        clearparam();
    }

//    @Listen("onClick=#Submit")
//    public void submitRange() {
//
//        if (txtflag.getText().equals("select")) {
//            if (txtqty.getValue() == 0) {
//                Messagebox.show("Quantity Can't be 0", "Select Range S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//            } else {
//                Map<String, Object> map = model.doInvSelectSN(txtwip.getText(), txtitemlocid.getText(), txtsn.getText(), txtto.getText(), txtqty.getText(), userId);
//                if (map.get("OutError").equals(0)) {
//                    parentController.LisInvUnSelecSN();
//                    Path pg = new Path("/List_wip_receipt");
//                    Textbox txtwipid = (Textbox) new Path(pg, "wiprcpid").getComponent();
//                    Textbox txtprodissueid = (Textbox) new Path(pg, "prodissueid").getComponent();
//                    Textbox txtitmid = (Textbox) new Path(pg, "itemid").getComponent();
//                    Map<String, Object> mapper = model.doInsertSelectSnTemp(txtwipid.getValue(), txtprodissueid.getValue(), txtitmid.getValue(), userId);
//                    if (mapper.get("OutError").equals(0)) {
//                        Messagebox.show(map.get("OutMessages").toString(), "Select S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//                        parentController.requery();
////                    parentController.LisInvSelecSN();
////                    parentController.requeryun();
//                        winselectfromto.detach();
//
//                    } else {
//                        Messagebox.show(mapper.get("OutMessages").toString(), "WIP Receipt", Messagebox.OK, Messagebox.EXCLAMATION);
//                    }
//                } else {
//                    Messagebox.show(map.get("OutMessages").toString(), "Select S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//                }
//            }
//
//        } else {
//            if (txtqty.getValue() == 0) {
//                Messagebox.show("Quantity Can't be 0", "Select Range S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//            } else {
//                Map<String, Object> map = model.doInvUnselectSN(txtwip.getText(), txtitemlocid.getText(), txtsn.getText(), txtto.getText(), txtqty.getText(), userId);
//                if (map.get("OutError").equals(0)) {
//                    Messagebox.show(map.get("OutMessages").toString(), "Select S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//                    parentController.LisInvUnSelecSN();
//                    Path pg = new Path("/List_wip_receipt");
//                    Textbox txtwipid = (Textbox) new Path(pg, "wiprcpid").getComponent();
//                    Textbox txtprodissueid = (Textbox) new Path(pg, "prodissueid").getComponent();
//                    Textbox txtitmid = (Textbox) new Path(pg, "itemid").getComponent();
//                    Map<String, Object> mapper = model.doInsertSelectSnTemp(txtwipid.getValue(), txtprodissueid.getValue(), txtitmid.getValue(), userId);
//                    if (mapper.get("OutError").equals(0)) {
//                        parentController.requery();
//                        winselectfromto.detach();
//                    } else {
//                        Messagebox.show(map.get("OutMessages").toString(), "Select S/N", Messagebox.OK, Messagebox.EXCLAMATION);
//                    }
//
//                }
//            }
//
//        }
//    }
//
//    private void clearparam() {
//        txtflag.setText("");
//        txtitem.setText("");
//        txtitemlocid.setText("");
//        txtqty.setText("0");
//        txtsn.setText("");
//        txtto.setText("");
//    }
}
