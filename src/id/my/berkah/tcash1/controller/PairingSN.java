///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package id.my.berkah.tcash1.controller;
//
//import id.my.berkah.tcash1.model.ListGenIFParam;
//import id.my.berkah.tcash1.model.ListPairingDTLParam;
//import id.my.berkah.util.CHelper;
//import id.my.berkah.util.ParameterGlobal;
//import id.my.berkah.util.controller.LovController;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.Path;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zul.Doublebox;
//import org.zkoss.zul.Filedownload;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Window;
//
///**
// *
// * @author Azec
// */
//public class PairingSN extends SelectorComposer<Component>{
//     private String[] global = ParameterGlobal.getParameterGlobal();
//    String userId = global[0];
//    String responsibilityId = global[2];
//    String FILENAME="";
//    @Wire
//    Window win_pairingsn,win_if;
//    
//    @Wire
//    Textbox txtworkid,txtwordnum,txtwipdesc,txtwipcode,txtwipid,txtid,txtpairing,txtcreationby,txtshopdate,txtcreationdate,
//            txtapprovedate,txtapprovby,txtSubmitdate,txtstatus,txtsbmtby,txtloopdesc,txtloopid;
//    
//    @Wire
//    Doublebox txtqty;
//    
//    @Wire
//    Listbox listbox;
//    
//   ListPairingSn parentCTRL;
//       private Map lastSearch;
//    ModelTcashCTLR model = new ModelTcashCTLR();
//    
//    
//    @Listen("onCreate=#win_pairingsn")
//    public void onCreateWindow(){
////        parentCTRL=(ListPairingSn)win_pairingsn.getAttribute("parentCTRL");
////        selectHDR();
////        renderrequeryDetail();
////        refreshrender();
//    }
//    
//    @Listen("onClick=#close")
//    public void closeWindow(){
//        win_pairingsn.detach();
//    }
//    
//    
//     @Listen("onClick=#btnIF")
//    public void generate() throws IOException {
////        InputFileCTRL inp = new InputFileCTRL();
////        inp.generateFile();ge
//        generateFile();
//    }
//    
//    public void generateFile() throws IOException{
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//        
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
//        Properties properties = new Properties();
//        properties.load(inputStream);
//        String download_directory = properties.getProperty("download_directory");
//        
//        Path parent1 = new Path("/win_pairingsn");
//        Textbox txtFileName = (Textbox) new Path(parent1, "txtFileName").getComponent();
//        Textbox txtWoId = (Textbox) new Path(parent1, "txtWoId").getComponent();
//
//        try {
//            
//            String content = "";
//            FILENAME = download_directory+txtFileName.getValue();
//            fw = new FileWriter(FILENAME);
//            bw = new BufferedWriter(fw);
//            List<ListGenIFParam> ListGenIFParam = model.selectGenFile(txtWoId.getValue());
//            int i = 0;
//            
//            for(ListGenIFParam ListGenIFParam1 :ListGenIFParam){
//                if (i != 0){
//                    bw.newLine();
//                }
////                System.out.println("hasilnya"+ListGenIFParam1.getSn_tcash()+";"+ListGenIFParam1.getSn_inv());
//                content = ListGenIFParam1.getSn_tcash()+"|"+ListGenIFParam1.getSn_inv();
//                
//                bw.write(content);
//                i++;
//            }
//            System.out.println("Done");
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        } finally {
//
//            try {
//
//                if (bw != null) {
//                    bw.close();
//                }
//
//                if (fw != null) {
//                    fw.close();
//                }
//                Filedownload.save(new File(download_directory+txtFileName.getValue()),null);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//    
//    
//    @Listen("onClick=#btnprod")
//    public void LOVWO(){
//         HashMap map = new HashMap<String, Object>();
//        LovController composerLov = new LovController();
//        composerLov.setQuery("select * from (select rownum as No, po_id as \"id\",purchase_order as \"Wo Number\" from table(pkg_tcash_bundling.LovWoTcash(''))where (upper(po_id) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovWoTcash('')) where \n"
//                + "(upper(po_id) like upper('?') or upper(purchase_order) like upper('?'))");
//        composerLov.setSelectedColumn(new int[]{1, 2});
//        composerLov.setComponentTransferData(new Textbox[]{txtworkid,txtwordnum});
//        composerLov.setHiddenColumn(new int[]{0, 1});
//
//        composerLov.setTitle("List Of Workorder");
//        composerLov.setWidth("500px");
//        composerLov.setHeight("335px");
//        composerLov.setPageSize(10);
//        map.put("composerLov", composerLov);
//
//        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
//        w.setParent(win_pairingsn);
//        w.doModal();
//    }
//    
//    
//    @Listen("onClick=#btnloop")
//    public void LOVWIP(){
//         HashMap map = new HashMap<String, Object>();
//        LovController composerLov = new LovController();
//        composerLov.setQuery("select * from (select rownum as No, po_id as \"id\",purchase_order as \"Wo Number\" from table(pkg_tcash_bundling.LovWoLoop(''))where (upper(po_id) like upper('?') or upper(purchase_order) like upper('?')))where No BETWEEN param1 and param2");
//        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_bundling.LovWoLoop('')) where \n"
//                + "(upper(po_id) like upper('?') or upper(purchase_order) like upper('?'))");
//        composerLov.setSelectedColumn(new int[]{1, 2});
//        composerLov.setComponentTransferData(new Textbox[]{txtloopid,txtloopdesc});
//        composerLov.setHiddenColumn(new int[]{0, 1});
//
//        composerLov.setTitle("List Of Loop");
//        composerLov.setWidth("500px");
//        composerLov.setHeight("335px");
//        composerLov.setPageSize(10);
//        map.put("composerLov", composerLov);
//
//        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
//        w.setParent(win_pairingsn);
//        w.doModal();
//    }
//    
////    @Listen("onClick=#btnsave")
////    public void buttonSave(){
////        if (txtid.getText().isEmpty()) {
////                    validateSaveHdr();
////        } else {
////            validateUpdateHdr();
////        }
////        selectHDR();
////    }
//        
////    void save(){
////        Map<String,Object> map = model.doPairingInsertHdr(txtworkid.getText(),txtitemid.getText(), txtqty.getValue(), userId);
////        if (map.get("OutError").equals(0)) {
////            Messagebox.show(map.get("OutMessages").toString());
////            txtid.setText(map.get("OutPairingId").toString());
////            
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////   public void validateSaveHdr(){
////     
////        Map<String,Object> map = model.doPairingValidateInsertHdr(txtworkid.getValue(),txtitemid.getValue());
////        if (map.get("OutError").equals(0)) {
////            save();
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    
////    }
////    
////    void selectHDR(){
////        if (!txtid.getText().equals("")) {
////             List<ListPairingHdrParam>list= model.getPairingListHdr(txtid.getText());
////        txtid.setText(list.get(0).getPairing_id());
////        txtpairing.setText(list.get(0).getPairing_no());
////        txtshopdate.setText(list.get(0).getPairing_date());
////        txtworkid.setText(list.get(0).getWo_id());
////        txtwordnum.setText(list.get(0).getWo_no());
////        txtitemid.setText(list.get(0).getItem_id());
////        txtitemcode.setText(list.get(0).getItem_code());
////        txtitemdesc.setText(list.get(0).getItem_desc());
////        txtqty.setText(list.get(0).getQuantity());
////        txtstatus.setText(list.get(0).getStatus());
////        txtcreationby.setText(list.get(0).getCreated_by());
////        txtcreationdate.setText(list.get(0).getCreate_date());
////        txtapprovedate.setText(list.get(0).getApprove_date());
////        txtapprovby.setText(list.get(0).getApproved_by());
////        txtSubmitdate.setText(list.get(0).getSubmit_date());
////        txtsbmtby.setText(list.get(0).getSubmited_by());
////        parentCTRL.buttonRefresh();
////        } else {
////        }
////       
////    }
////    
////    void validateUpdateHdr(){
////        Map<String,Object>map= model.doPairingValidateUpdateHdr(txtid.getText(), txtworkid.getText(), txtitemid.getText());
////         if (map.get("OutError").equals(0)) {
//////            Messagebox.show(map.get("OutMessages").toString());
////             updateHDR();
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////    
////    
////    void updateHDR(){
////         Map<String,Object>map= model.doPairingUpdateHdr(txtid.getText(), txtworkid.getText(), txtitemid.getText(),txtqty.getValue(),userId);
////         if (map.get("OutError").equals(0)) {
//////            Messagebox.show(map.get("OutMessages").toString());
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////    
////    void Approve(){
////           Map<String,Object>map= model.doPairingApproveHdr(txtid.getText(),userId);
////         if (map.get("OutError").equals(0)) {
////            Messagebox.show(map.get("OutMessages").toString());
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////    
////    
////    void doSubmitHdr(){
////          Map<String,Object>map= model.doPairingSubmitHdr(txtid.getText(),userId);
////         if (map.get("OutError").equals(0)) {
////            Messagebox.show(map.get("OutMessages").toString());
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////    void doCancelHdr(){
////          Map<String,Object>map= model.doPairingCancelHdr(txtid.getText(),userId);
////         if (map.get("OutError").equals(0)) {
//////            Messagebox.show(map.get("OutMessages").toString());
////        } else {
////             Messagebox.show(map.get("OutMessages").toString());
////        }
////    }
////    
////    @Listen("onClick=#btnsubmit")
////    public void submit(){
////         validateUpdateHdr();
////        updateHDR();
////        doSubmitHdr();
////        selectHDR();
////    }
////    
////    @Listen("onClick=#btncancel")
////    public void buttonCancel(){
////        doCancelHdr();
////        selectHDR();
////    }
////    
////    @Listen("onClick=#btnapproved")
////    public void buttonApprove(){
////        validateUpdateHdr();
////        updateHDR();
////        Approve();
////        selectHDR();
////    }
////    
//    
////    void requeryDetail(){
////        List<ListPairingDTLParam>list = model.getPairingListDtl(txtid.getText());
////        listbox.setModel(new ListModelList<ListPairingDTLParam>(list));
////    }
////    
////    void renderrequeryDetail(){
////        listbox.setItemRenderer(new ListitemRenderer<ListPairingDTLParam>() {
////
////            @Override
////            public void render(Listitem lstm, ListPairingDTLParam t, int i) throws Exception {
////                CHelper.Listbox_addLabel(lstm, "" + (i + 1), "right");
////                CHelper.Listbox_addLabel(lstm, t.getItem_id(), "left");
////                CHelper.Listbox_addLabel(lstm, t.getProd_issue_id(), "left");
////                CHelper.Listbox_addLabel(lstm, t.getFrom_sn(), "left");
////                CHelper.Listbox_addLabel(lstm, t.getTo_sn(), "left");
////                CHelper.Listbox_addLabel(lstm, t.getCreate_date(), "left");
////                CHelper.Listbox_addLabel(lstm, t.getCreated_by(), "left");
////            }
////        });
////
////}
//    
//    
////    void refreshpairing(){
////        List<ListSnPairingParam> list = model.getListSnPairing(txtworkid.getText(), txtloopid.getText());
////        listbox.setModel(new ListModelList<ListSnPairingParam>(list));
////
////    }
////    
////    void refreshrender(){
////        listbox.setItemRenderer(new ListitemRenderer<ListSnPairingParam>() {
////            @Override
////            public void render(Listitem lstm, ListSnPairingParam t, int i) throws Exception {
////                new Listcell(t.getSn_from()).setParent(lstm);
////                new Listcell(t.getSn_to()).setParent(lstm);
////                new Listcell(t.getQty()).setParent(lstm);
////            }
////        });
////    }
//    
//           public void PairingSNLoop(){
//               Map<String,Object>map = model.PairingSnTcashLoop(txtworkid.getText(), txtloopid.getText());
//               if (map.get("OutError").equals(0)) {
//                   Messagebox.show(map.get("OutMessages").toString());
//               } else {
//                   Messagebox.show(map.get("OutMessages").toString());
//               }
//           } 
//    
//           
//           @Listen("onClick=#btnadd")
//           public void adddetail(){
//               Map<String,Object>map=new HashMap<>();
//               map.put("InPoIdTcash", txtworkid.getText());
//               map.put("InPoIdLoop", txtloopid.getText());
//               Window w = (Window)Executions.createComponents("/Tcash/ListDtlPairingSN.zul",null, map);
//               w.doModal();
//           }
//    
//}
