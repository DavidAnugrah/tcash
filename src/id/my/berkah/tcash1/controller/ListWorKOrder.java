/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.util.CHelper;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListWorKOrder extends SelectorComposer<Component> {
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_list_WO_find;
    
    @Wire
    Paging userPaging;
    
    @Wire
    Textbox txtbuId, txtbucode, txtbuDesc,txtPoNo;
    
    @Wire
    Combobox cmbStatus;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
       
          Calendar cal = Calendar.getInstance();
           Calendar cal1 = Calendar.getInstance();
    @Wire
    Listbox listbox;
//    @Wire
//    Tabbox centertab;
//    @Wire
//    Tab tbs;
    ModelTcashCTLR model = new ModelTcashCTLR();

    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_WO_find.setVisible(false);
        
     
        cal.add(Calendar.DATE, -7);

        Path parent = new Path("/List_WO/win_list_WO_find");
        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();

        txtDateFrom.setValue(cal.getTime());
    }
    
    @Listen("onClick=#btnLovBu")
    public void lovBu(){
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,BU_ID as \"BU Id\",BU_CODE as \"BU Code\", BU_DESCRIPTION as \"BU Description\" from (select bu_id,bu_code,bu_description from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + ")))\n"
                + "where (upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBU(" + "''" + "," + responsibilityId + "," + userId + ")) where \n"
                + "(upper(BU_CODE) like upper('?') or upper(BU_DESCRIPTION) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtbuId, txtbucode, txtbuDesc});
        composerLov.setHiddenColumn(new int[]{0, 1});

        composerLov.setTitle("List Of BU");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_list_WO_find);
        w.doModal();
    }
    
      @Listen("onPaging=#userPaging")
    public void onPagingUserPaging(PagingEvent pe) {
        startPageNumber = pe.getActivePage() + 1;
        refreshModelTcashCTLR(startPageNumber);
    }

    private void refreshModelTcashCTLR(int activePage) {
        userPaging.setPageSize(pageSize);
        refresh(activePage);//, pageSize);
    }

    public void refresh(int activePage) {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
      
        Path parent1 = new Path("/List_WO/win_list_WO_find");
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtbucode = (Textbox) new Path(parent1, "txtbucode").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Combobox cmbOrder = (Combobox) new Path(parent1, "cmbOrder").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        
        String status = "";
         if(cmbStatus.getValue().equalsIgnoreCase("Draft")){
            status="1";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")){
            status="2";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")){
            status="3";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("RELEASED")){
            status="4";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("APPROVED")){
            status="5";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")){
            status="6";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("COMPLETED")){
            status="7";
        }
//        else if(cmbStatus.getValue().equalsIgnoreCase("REJECTED")){
//            status="8";
//        }
        else if(cmbStatus.getValue().equalsIgnoreCase("CANCELED")){
            status="9";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("Generating SN")){
             status="10";
        }
        
        String order ="";
        if(cmbOrder.getValue().equals("Tcash")){
            order="1";
        }
        else if(cmbOrder.getValue().equals("Bundling Tcash")){
            order="2";
        }
        else if(cmbOrder.getValue().equals("One Stop Production")){
            order="3";
        }
//        dateFromx = rdf.format(findFromDate1.getValue());
//        dateToX = rdf.format(findToDate1.getValue());
        Path parent = new Path("/List_WO");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();
        List<ListWoParam> ListWoParam = model.selectListWo(userId,responsibilityId,txtPoNo.getValue(),txtbucode.getValue(),
                status,order,dateFromx,dateToX,
                "" + activePage, "" + pageSize);
        for (ListWoParam ListWoParam1 : ListWoParam) {
            Listcell po_id = new Listcell();
            Listcell purchase_order = new Listcell();
            Listcell bu_id = new Listcell();
            Listcell bu_code = new Listcell();
            Listcell bu_description = new Listcell();
            Listcell supplier_id = new Listcell();
            Listcell supplier_code = new Listcell();
            Listcell supplier_name = new Listcell();
            Listcell site_id = new Listcell();
            Listcell site_code = new Listcell();
            Listcell site_name = new Listcell();
            Listcell order_date = new Listcell();
            Listcell currency = new Listcell();
            Listcell contract_id = new Listcell();
            Listcell contract = new Listcell();
            Listcell order_type = new Listcell();
            Listcell order_type_desc = new Listcell();
            Listcell status_desc = new Listcell();
            Listcell terms_of_payment_id = new Listcell();
            Listcell terms_of_payment_desc = new Listcell();
            Listcell remarks = new Listcell();
            Listcell created_date = new Listcell();
            Listcell created_by_name = new Listcell();
            Listcell processed_date = new Listcell();
            Listcell processed_by_name = new Listcell();
            Listcell approved_date = new Listcell();
            Listcell approved_by_name = new Listcell();
            Listcell cancel_date = new Listcell();
            Listcell cancel_by_name = new Listcell();

            po_id.setLabel(ListWoParam1.getPo_id());
            purchase_order.setLabel(ListWoParam1.getPurchase_order());
            bu_id.setLabel(ListWoParam1.getBu_id());
            bu_code.setLabel(ListWoParam1.getBu_code());
            bu_description.setLabel(ListWoParam1.getBu_description());
            supplier_id.setLabel(ListWoParam1.getSupplier_id());
            supplier_code.setLabel(ListWoParam1.getSupplier_code());
            supplier_name.setLabel(ListWoParam1.getSupplier_name());
            site_id.setLabel(ListWoParam1.getSite_id());
            site_code.setLabel(ListWoParam1.getSite_code());
            site_name.setLabel(ListWoParam1.getSite_name());
            order_date.setLabel(ListWoParam1.getOrder_date());
            currency.setLabel(ListWoParam1.getCurrency());
            contract_id.setLabel(ListWoParam1.getContract_id());
            contract.setLabel(ListWoParam1.getContract());
            order_type.setLabel(ListWoParam1.getOrder_type());
            order_type_desc.setLabel(ListWoParam1.getOrder_type_desc());
            status_desc.setLabel(ListWoParam1.getStatus_desc());
            terms_of_payment_id.setLabel(ListWoParam1.getTerms_of_payment_id());
            terms_of_payment_desc.setLabel(ListWoParam1.getTerms_of_payment_desc());
            remarks.setLabel(ListWoParam1.getRemarks());
            created_date.setLabel(ListWoParam1.getCreated_date());
            created_by_name.setLabel(ListWoParam1.getCreated_by_name());
            processed_date.setLabel(ListWoParam1.getProcessed_date());
            processed_by_name.setLabel(ListWoParam1.getProcessed_by_name());
            approved_date.setLabel(ListWoParam1.getApproved_date());
            approved_by_name.setLabel(ListWoParam1.getApproved_by_name());
            cancel_date.setLabel(ListWoParam1.getCancel_date());
            cancel_by_name.setLabel(ListWoParam1.getCancel_by_name());

            Listitem listitem = new Listitem();
            listitem.appendChild(po_id);
            listitem.appendChild(purchase_order);
            listitem.appendChild(bu_id);
            listitem.appendChild(bu_code);
            listitem.appendChild(bu_description);
            listitem.appendChild(supplier_id);
            listitem.appendChild(supplier_code);
            listitem.appendChild(supplier_name);
            listitem.appendChild(site_id);
            listitem.appendChild(site_code);
            listitem.appendChild(site_name);
            listitem.appendChild(order_date);
            listitem.appendChild(currency);
            listitem.appendChild(contract_id);
            listitem.appendChild(contract);
            listitem.appendChild(order_type);
            listitem.appendChild(order_type_desc);
            listitem.appendChild(terms_of_payment_id);
            listitem.appendChild(terms_of_payment_desc);
            listitem.appendChild(remarks);
            listitem.appendChild(created_date);
            listitem.appendChild(created_by_name);
            listitem.appendChild(processed_date);
            listitem.appendChild(processed_by_name);
            listitem.appendChild(approved_date);
            listitem.appendChild(approved_by_name);
            listitem.appendChild(cancel_date);
            listitem.appendChild(cancel_by_name);
            listitem.appendChild(status_desc);
            
            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    
                    
                    Path parent1 = new Path("/List_WO");
                    Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
                    txtid.setValue(poId);
                }
            });
            
            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    
                    editWo(poId);
                }
            });
            
            listbox1.appendChild(listitem);
        }
    }

      @Listen("onClick=#refresh")
    public void refreshBtn() {
        listbox.setSizedByContent(true);
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//       
        Path parent1 = new Path("/List_WO/win_list_WO_find");
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtbucode = (Textbox) new Path(parent1, "txtbucode").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Combobox cmbOrder = (Combobox) new Path(parent1, "cmbOrder").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
         String status = "";
         if(cmbStatus.getValue().equalsIgnoreCase("Draft")){
            status="1";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")){
            status="2";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")){
            status="3";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("RELEASED")){
            status="4";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("APPROVED")){
            status="5";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")){
            status="6";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("COMPLETED")){
            status="7";
        }
//        else if(cmbStatus.getValue().equalsIgnoreCase("REJECTED")){
//            status="8";
//        }
        else if(cmbStatus.getValue().equalsIgnoreCase("CANCELED")){
            status="9";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("Generating SN")){
             status="10";
        }else{
            status="";
        }
        
        String order ="";
        if(cmbOrder.getValue().equals("Packaging")){
            order="1";
        }
        else if(cmbOrder.getValue().equals("Personalization")){
            order="2";
        }
        else if(cmbOrder.getValue().equals("One Stop Production")){
            order="3";
        }
        
        cal.add(Calendar.DATE,+7);
        cal.add(Calendar.DATE, -7);
        cal1.add(Calendar.DATE,0);
        
        txtPoNo.setValue("");
        txtbucode.setValue("");
        cmbStatus.setSelectedItem(null);
//        cmbOrder.setSelectedIndex(1);
        findFromDate1.setValue(cal.getTime());
        findToDate1.setValue(cal1.getTime());
                        
        List<Integer> jumlahRecord = model.getCountWo(userId, responsibilityId,txtPoNo.getValue(),txtbucode.getValue(),
                status,order,dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_WO");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        page.setActivePage(0);
        refreshModelTcashCTLR(1);
        refresh(1);
        
// 
    }
    
//        public void refreshall(){
//        List<ListWoParam>list = model.selectallWo(userId, responsibilityId, "", "", "", "","", "");
//        listbox.setModel(new ListModelList<ListWoParam>(list));
//        
//        listbox.setItemRenderer(new ListitemRenderer<ListWoParam>() {
//
//                    @Override
//                    public void render(Listitem lstm, ListWoParam t, int i) throws Exception {
//                      CHelper.Listbox_addLabel(lstm, t.getPo_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getPurchase_order(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getBu_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getBu_code(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getBu_description(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSupplier_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSupplier_code(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSupplier_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSite_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSite_code(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getSite_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getTerms_of_payment_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getTerms_of_payment_desc(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getOrder_date(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getCurrency(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getContract_id(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getContract(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getOrder_type(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getOrder_type_desc(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getStatus_desc(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getRemarks(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getCreated_by_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getProcessed_by_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getApproved_date(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getApproved_by_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getCancel_by_name(), "left");
//                      CHelper.Listbox_addLabel(lstm, t.getCancel_date(), "left");
//                    }
//       });
//  
//        
//}
//      @Listen(Events.ON_CLICK + " = #edit")
//    public void btnEditOnClick() {
//        int index = listbox.getSelectedIndex();
//        if (index > -1) {
//            ListWoParam selected = (ListWoParam) listbox.getModel().getElementAt(index);
//            Map map = new HashMap();
//            map.put("header", selected);
//            Window w = (Window) Executions.createComponents("/Tcash/WOexternalCTRL.zul", null, map);
////            w.setAttribute("parentCTRL", this);
//            w.doModal();
//        } else {
//            CHelper.ShowMessage("No record selected", Messagebox.EXCLAMATION);
//        }
//    }
//
//    @Listen(Events.ON_DOUBLE_CLICK + " = #listbox")
//    public void listbox1OnDoubleClick() {
//        btnEditOnClick();
//    }
//    

    @Listen("onClick=#find")
    public void filter() {
        win_list_WO_find.setVisible(true);
    }
    
    @Listen("onClick=#goFind")
    public void goFind() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
         Path parent1 = new Path("/List_WO/win_list_WO_find");
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtbucode = (Textbox) new Path(parent1, "txtbucode").getComponent();
        Combobox cmbStatus = (Combobox) new Path(parent1, "cmbStatus").getComponent();
        Combobox cmbOrder = (Combobox) new Path(parent1, "cmbOrder").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        
        String status = "";
         if(cmbStatus.getValue().equalsIgnoreCase("Draft")){
            status="1";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD IN PROGRESS")){
            status="2";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("UPLOAD COMPLETE")){
            status="3";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("RELEASED")){
            status="4";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("APPROVED")){
            status="5";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("INPUT FILE COMPLETED")){
            status="6";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("COMPLETED")){
            status="7";
        }
//        else if(cmbStatus.getValue().equalsIgnoreCase("REJECTED")){
//            status="8";
//        }
        else if(cmbStatus.getValue().equalsIgnoreCase("CANCELED")){
            status="9";
        }
        else if(cmbStatus.getValue().equalsIgnoreCase("Generating SN")){
             status="10";
        }
        
        String order ="";
        if(cmbOrder.getValue().equals("Packaging")){
            order="1";
        }
        else if(cmbOrder.getValue().equals("Personalization")){
            order="2";
        }
        else if(cmbOrder.getValue().equals("One Stop Production")){
            order="3";
        }
        List<Integer> jumlahRecord = model.getCountWo(userId, responsibilityId,txtPoNo.getValue(),txtbucode.getValue(),
                status,order,dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_WO");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        page.setActivePage(0);
//        refreshModelTcashCTLR(1);
        refresh(1);
        win_list_WO_find.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void closeFind() {
        win_list_WO_find.setVisible(false);
    }
    
    public void editWo(String poId){
        Map<String, Object> map = new HashMap<>();
        map.put("poId",poId);
        Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/WOexternalCTRL.zul", null, map);
        window.doModal();
    }
    
    @Listen("onClick=#edit")
    public void edit(){
        Path parent1 = new Path("/List_WO");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if(txtid.getValue().equals("")){
           Messagebox.show("No record selected","Work order",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }
        editWo(txtid.getValue());
    }

    @Listen("onClick=#new")
    public void addnew() {
        Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/WOexternalCTRL.zul", null, null);
        window.doModal();
    }
    
    @Listen("onClick=#clear")
    public void clearParam(){
         cal.add(Calendar.DATE,+7);
        cal.add(Calendar.DATE, -7);
        cal1.add(Calendar.DATE,0);
       txtPoNo.setText("");
       txtbuId.setText("");
       txtbucode.setText("");
       txtbuDesc.setText("");
       cmbStatus.setSelectedIndex(-1);
       txtDateFrom.setValue(cal.getTime());
       txtDateTo.setValue(cal1.getTime());
    }
    
//    @Listen("onClick=#print")
//    public void print() throws FileNotFoundException{
////        Messagebox.show(media.getName());
//        
//        String itemId = txtItemId.getText();
//        String packetId = txtPacketId.getText();
//        String locId = txtLocationId.getText();
//        String typeId = txtTypeId.getText();
//        String itemCode = txtItemCode.getText();
//        String packetCode = txtPacketCode.getText();
//        String locCode = txtLocationCode.getText();
//        String status = txtStatus.getText();
//        File xlFile=new File("//home//apps//temp//"+"DPA_RESOURCE"+"_"+itemCode+"_"+packetCode+"_"+locCode+"_"+status+".xls");
////        File xlFile=new File("D://"+"DPA_RESOURCE"+"_"+itemCode+"_"+packetCode+"_"+locCode+"_"+status+".xls");
//        
//        
////        Filedownload.save(xlFile,"Test.xls");
//////        xlFile.createNewFile("D:/⁠/⁠Test.xls");
//        ExcelExportDpaMtr test = new ExcelExportDpaMtr();
//        test.fillDataZkos(listbox, xlFile, 9,itemId,packetId,locId,typeId);
//        Filedownload.save(new File("//home//apps//temp//"+"DPA_RESOURCE"+"_"+itemCode+"_"+packetCode+"_"+locCode+"_"+status+".xls"),null);
////        Filedownload.save(new File("D://"+"DPA_RESOURCE"+"_"+itemCode+"_"+packetCode+"_"+locCode+"_"+status+".xls"),null);
//        
//    }
}
