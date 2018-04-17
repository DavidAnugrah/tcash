/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.MVItemToVoucherParam;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author arry
 */
public class ListMappingItemToVoucher extends SelectorComposer<Component> {
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_list_find_Item;
    
    @Wire
    Paging userPaging;
    
    @Wire
    Textbox txtbuId, txtbucode, txtbuDesc;

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
        win_list_find_Item.setVisible(false);
    }
    
    @Listen("onClick=#btnLovBu")
    public void lovItem(){
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,item_id as \"Item Id\",item_code as \"Item Code\", item_description as \"Item Description\" from (select item_id,item_code,item_description from table(pkg_tcash_lov.LovItem(" + "''" + ")))\n"
                + "where (upper(item_code) like upper('?') or upper(item_description) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovItem(" + "''" + ")) where \n"
                + "(upper(item_code) like upper('?') or upper(item_description) like upper('?'))");
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
        w.setParent(win_list_find_Item);
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
      
        Path parent1 = new Path("/List_Mapping_item_to_voucher/win_list_find_Item");
         Textbox txtItemCode = (Textbox) new Path(parent1, "txtItemCode").getComponent();
        Textbox txtVoucher = (Textbox) new Path(parent1, "txtVoucher").getComponent();
        
        Path parent = new Path("/List_Mapping_item_to_voucher");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();
        List<MVItemToVoucherParam> MVItemToVoucherParam = model.getListMvHdr(txtItemCode.getValue(),txtVoucher.getValue(),
                "" + activePage, "" + pageSize);
        for (MVItemToVoucherParam MVItemToVoucherParam1 : MVItemToVoucherParam) {
            Listcell id = new Listcell();
            Listcell item_id = new Listcell();
            Listcell item_Desc = new Listcell();
            Listcell item_desc = new Listcell();
            Listcell voucher_code = new Listcell();
            Listcell voucher_desc = new Listcell();
            Listcell created_by = new Listcell();
            Listcell created_date = new Listcell();
            Listcell update_by = new Listcell();
            Listcell update_date = new Listcell();
            Listcell expired_by = new Listcell();
            Listcell expired_date = new Listcell();

            id.setLabel(MVItemToVoucherParam1.getId_map());
            item_id.setLabel(MVItemToVoucherParam1.getItem_id());
            item_Desc.setLabel(MVItemToVoucherParam1.getItem_code());
            item_desc.setLabel(MVItemToVoucherParam1.getItem_desc());
            voucher_code.setLabel(MVItemToVoucherParam1.getVoucher_type_code());
            voucher_desc.setLabel(MVItemToVoucherParam1.getVoucher_type_desc());
            created_by.setLabel(MVItemToVoucherParam1.getCreated_by());
            created_date.setLabel(MVItemToVoucherParam1.getCreated_date());
            update_by.setLabel(MVItemToVoucherParam1.getUpdated_by());
            update_date.setLabel(MVItemToVoucherParam1.getUpdated_date());
            expired_by.setLabel(MVItemToVoucherParam1.getExpired_by());
            expired_date.setLabel(MVItemToVoucherParam1.getExpired_date());

            Listitem listitem = new Listitem();
            listitem.appendChild(id);
            listitem.appendChild(item_id);
            listitem.appendChild(item_Desc);
            listitem.appendChild(item_desc);
            listitem.appendChild(voucher_code);
            listitem.appendChild(voucher_desc);
            listitem.appendChild(created_by);
            listitem.appendChild(created_date);
            listitem.appendChild(update_by);
            listitem.appendChild(update_date);
            listitem.appendChild(expired_by);
            listitem.appendChild(expired_date);
            
            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    
                    
                    Path parent1 = new Path("/List_Mapping_item_to_voucher");
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
//       
         Path parent1 = new Path("/List_Mapping_item_to_voucher/win_list_find_Item");
        Textbox txtItemCode = (Textbox) new Path(parent1, "txtItemCode").getComponent();
        Textbox txtVoucher = (Textbox) new Path(parent1, "txtVoucher").getComponent();
        
        txtItemCode.setValue("");
        txtVoucher.setValue("");
        
        List<Integer> jumlahRecord = model.getCountMvHdr("", "");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Mapping_item_to_voucher");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        page.setActivePage(0);
//        refreshModelTcashCTLR(1);
        refresh(1);
    }

    @Listen("onClick=#find")
    public void filter() {
        win_list_find_Item.setVisible(true);
    }
    
    @Listen("onClick=#goFind")
    public void goFind() {
         Path parent1 = new Path("/List_Mapping_item_to_voucher/win_list_find_Item");
        Textbox txtItemCode = (Textbox) new Path(parent1, "txtItemCode").getComponent();
        Textbox txtVoucher = (Textbox) new Path(parent1, "txtVoucher").getComponent();
        
        List<Integer> jumlahRecord = model.getCountMvHdr(txtItemCode.getValue(), txtVoucher.getValue());
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Mapping_item_to_voucher");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        page.setActivePage(0);
//        refreshModelTcashCTLR(1);
        refresh(1);
        win_list_find_Item.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void closeFind() {
        win_list_find_Item.setVisible(false);
    }
    
    public void editWo(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/MappingItemToVoucherType.zul", null, map);
        window.doModal();
    }
    
    @Listen("onClick=#edit")
    public void edit(){
        Path parent1 = new Path("/List_Mapping_item_to_voucher");
        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
        if(txtid.getValue()==""){
            System.out.println("return");
            return;
        }
        editWo(txtid.getValue());
    }

    @Listen("onClick=#new")
    public void addnew() {
        Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/MappingItemToVoucherType.zul", null, null);
        window.doModal();
    }
}
