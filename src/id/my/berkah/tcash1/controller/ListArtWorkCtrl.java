/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ArtWorkListParam;
import id.my.berkah.util.ParameterGlobal;
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
public class ListArtWorkCtrl extends SelectorComposer<Component> {
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];

    @Wire
    Window win_list_find;
    
    @Wire
    Paging userPaging;
    
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
        win_list_find.setVisible(false);
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
      
        Path parent1 = new Path("/List_ArtWork/win_list_find");
         Textbox txtArt = (Textbox) new Path(parent1, "txtArt").getComponent();
         
        Path parent = new Path("/List_ArtWork");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();
        List<ArtWorkListParam> ArtWorkListParam = model.getListArtList(txtArt.getValue(),
                "" + activePage, "" + pageSize);
        for (ArtWorkListParam ArtWorkListParam1 : ArtWorkListParam) {
            Listcell id = new Listcell();
            Listcell item_Desc = new Listcell();
            Listcell item_desc = new Listcell();
            Listcell filename = new Listcell();
            Listcell created_by = new Listcell();
            Listcell created_date = new Listcell();
            Listcell update_by = new Listcell();
            Listcell update_date = new Listcell();
            Listcell expired_by = new Listcell();
            Listcell expired_date = new Listcell();

            id.setLabel(ArtWorkListParam1.getArt_id());
            item_Desc.setLabel(ArtWorkListParam1.getArt_code());
            item_desc.setLabel(ArtWorkListParam1.getArt_description());
            filename.setLabel(ArtWorkListParam1.getFilename());
            created_by.setLabel(ArtWorkListParam1.getCreated_by());
            created_date.setLabel(ArtWorkListParam1.getCreated_date());
            update_by.setLabel(ArtWorkListParam1.getUpdated_by());
            update_date.setLabel(ArtWorkListParam1.getUpdated_date());
            expired_by.setLabel(ArtWorkListParam1.getExpired_by());
            expired_date.setLabel(ArtWorkListParam1.getExpired_date());

            Listitem listitem = new Listitem();
            listitem.appendChild(id);
            listitem.appendChild(item_Desc);
            listitem.appendChild(item_desc);
            listitem.appendChild(filename);
            listitem.appendChild(created_by);
            listitem.appendChild(created_date);
            listitem.appendChild(update_by);
            listitem.appendChild(update_date);
            listitem.appendChild(expired_by);
            listitem.appendChild(expired_date);
            
            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
                public void onEvent(Event t) throws Exception {
                    String poId = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
                    
                    
                    Path parent1 = new Path("/List_ArtWork");
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
         Path parent1 = new Path("/List_ArtWork/win_list_find");
        Textbox txtArt = (Textbox) new Path(parent1, "txtArt").getComponent();
        
        txtArt.setValue("");
        
        List<Integer> jumlahRecord = model.getCountArtList("");
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_ArtWork");
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
        win_list_find.setVisible(true);
    }
    
    @Listen("onClick=#goFind")
    public void goFind() {
         Path parent1 = new Path("/List_ArtWork/win_list_find");
        Textbox txtArt = (Textbox) new Path(parent1, "txtArt").getComponent();
        
        List<Integer> jumlahRecord = model.getCountArtList(txtArt.getValue());
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_ArtWork");
        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
        page.setPageSize(pageSize);
        page.setTotalSize(JumlahRecord);

//        if (page.getActivePage() > 1) {
//            page.setActivePage(1);
//        }
        page.setActivePage(0);
//        refreshModelTcashCTLR(1);
        refresh(1);
        win_list_find.setVisible(false);
    }

    @Listen("onClick=#Close1")
    public void closeFind() {
        win_list_find.setVisible(false);
    }
    
    public void editWo(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/Tcash/ArtWork.zul", null, map);
        window.doModal();
    }
    
    @Listen("onClick=#edit")
    public void edit(){
        Path parent1 = new Path("/List_ArtWork");
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
                "/Tcash/ArtWork.zul", null, null);
        window.doModal();
    }
}
