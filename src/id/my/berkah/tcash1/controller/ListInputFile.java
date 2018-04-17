/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListIFParamTrc;
import id.my.berkah.util.ParameterGlobal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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

public class ListInputFile extends SelectorComposer<Component> {
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @Wire
    Paging userPaging;
    @Wire
    Window List_Input_File;
    @Wire
    Textbox txtid,txtifno,txtifid,txtWoid,txtWoNo,txtstat;
    
    @Wire
    Datebox txtDateFrom,txtDateTo;
    
    @Wire
     Combobox cmbStatus;
    
    @Wire
    Listbox listbox;
    
     private Map lastSearch;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
   
    @Listen("onClick=#new")
    public void addnewrecord() {
        Window window = (Window) Executions.createComponents(
                "/Tcash/InputFileTrc.zul", null, null);
        window.doModal();
    }

    @Listen("onClick=#find")
    public void addfind() {
      Window window = (Window) Executions.createComponents(
                "/Tcash/ListInputFileFind.zul", null, null);
      window.setAttribute("parentController", this);
        window.doModal();
    }
    
    public void findParam(String IfId,String poId,String DateFrom,String Dateto,String Status){
        List<ListIFParamTrc>list = model.selectListIFTrc(IfId, poId, DateFrom, Dateto, Status,userId,responsibilityId);
        listbox.setModel(new ListModelList<ListIFParamTrc>(list));
        System.out.println(list.size());
    }
    
    
    
    
   

//    @Listen("onClick=#goFind")
//    public void addgoFind() {
//
//        List<Integer> jumlahRecord = model.getCountIF(txtifid.getText(),txtWoid.getText(),txtDateFrom.getText(),txtDateTo.getText(),txtstat.getText());
//        if (!jumlahRecord.isEmpty()) {
//            JumlahRecord = jumlahRecord.get(0);
//        }
//        System.out.println(jumlahRecord);
//
//        Path pg = new Path("/List_Input_File");
//        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
//        page.setPageSize(pageSize);
//        page.setTotalSize(JumlahRecord);
//
//        page.setActivePage(0);
////        refresh(1);
//        win_list_find.setVisible(false);
//    }

 

  @Listen("onCreate=#List_Input_File")
                public void onCreateWindow(){
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//
//        Path parent = new Path("/List_Input_File/win_list_find");
//        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();
//
//        txtDateFrom.setValue(cal.getTime());
        setRender();
    }

    @Listen("onClick=#refresh")
    public void setmodel(){
      List<ListIFParamTrc>list = model.selectListIFTrc();
      listbox.setModel(new ListModelList<ListIFParamTrc>(list));
      listbox.setSizedByContent(true);
    }
    
      void setRender(){
      listbox.setItemRenderer(new ListitemRenderer<ListIFParamTrc>() {
          @Override
          public void render(Listitem lstm, ListIFParamTrc t, int i) throws Exception {
           new Listcell(t.getIfid()).setParent(lstm);
            new Listcell(t.getIfno()).setParent(lstm);
            new Listcell(t.getIfdate()).setParent(lstm);
            new Listcell(t.getFilename()).setParent(lstm);
            new Listcell(t.getPoid()).setParent(lstm);
            new Listcell(t.getPurchaseorder()).setParent(lstm);
            new Listcell(t.getCreatedate()).setParent(lstm);
            new Listcell(t.getCreatedby()).setParent(lstm);
            new Listcell(t.getUpdatedate()).setParent(lstm);
            new Listcell(t.getUpdatedby()).setParent(lstm);
            new Listcell(t.getSubmitdate()).setParent(lstm);
            new Listcell(t.getSubmitedby()).setParent(lstm);
            new Listcell(t.getApprovedate()).setParent(lstm);
            new Listcell(t.getApprovedby()).setParent(lstm);
            new Listcell(t.getCanceldate()).setParent(lstm);
            new Listcell(t.getCanceledby()).setParent(lstm);
            new Listcell(t.getStatus()).setParent(lstm);
          }
        });
    }
    
        @Listen("onClick=#edit")
    public void edit(){
        int index = listbox.getSelectedIndex();
        if (index >-1) {
            ListIFParamTrc selected =(ListIFParamTrc)listbox.getModel().getElementAt(index);
            Map map = new HashMap();
            map.put("header", selected);
            Window window = (Window) Executions.createComponents("/Tcash/InputFileTrc.zul", null, map);
            window.doModal();
        }else{
            Messagebox.show("No record selected", "Input file", Messagebox.OK,Messagebox.EXCLAMATION);
        }    
    
    }
    
    
//    public void requery() {
////
//        Path parent1 = new Path("/List_Input_File/win_list_find");
//        Textbox txtifid1 = (Textbox) new Path(parent1, "txtifid").getComponent();
//        Textbox txtWoid1 = (Textbox) new Path(parent1, "txtWoid").getComponent();
//        Textbox txtstat1 = (Textbox) new Path(parent1, "txtstat").getComponent();
//        Datebox txtDateFrom1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox txtDateTo1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//
//       
//        List<Integer> jumlahRecord = model.getCountIF(txtifid1.getText(),txtWoid1.getText(),txtDateFrom1.getText(),txtDateTo1.getText(),txtstat1.getText());
//        if (!jumlahRecord.isEmpty()) {
//            JumlahRecord = jumlahRecord.get(0);
//        }
//        System.out.println(jumlahRecord);
//
//        Path pg = new Path("/List_Input_File");
//        Paging page = (Paging) new Path(pg, "userPaging").getComponent();
//        page.setPageSize(pageSize);
//        page.setTotalSize(JumlahRecord);
//
//        page.setActivePage(0);
//        refresh(1);
//    }

//    public void refresh(int activePage) {
//
//        Path pg = new Path("/List_Input_File");
//        Listbox listbox1 = (Listbox) new Path(pg, "listbox").getComponent();
//
//        
//           Path parent1 = new Path("/List_Input_File/win_list_find");
//        Textbox txtifid1 = (Textbox) new Path(parent1, "txtifid").getComponent();
//        Textbox txtWoid1 = (Textbox) new Path(parent1, "txtWoid").getComponent();
//        Textbox txtstat1 = (Textbox) new Path(parent1, "txtstat").getComponent();
//        Datebox txtDateFrom1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//        Datebox txtDateTo1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//        
//        listbox1.getItems().clear();
//        List<ListIFParam> ListIFParamTrc = model.selectListIF(txtifid1.getText(), txtWoid1.getText(),txtDateFrom1.getText(),txtDateTo1.getText(),txtstat1.getText(),"" +activePage, ""+pageSize);
//        for (ListIFParamTrc ListIFParam1 : ListIFParamTrc) {
//
//            Listcell if_id= new Listcell();
//            Listcell if_no= new Listcell();
//            Listcell if_date= new Listcell();
//            Listcell filename= new Listcell();
//            Listcell status= new Listcell();
//            Listcell po_id= new Listcell();
//            Listcell po_no= new Listcell();
//            Listcell create_date= new Listcell();
//            Listcell created_by= new Listcell();
//            Listcell update_date= new Listcell();
//            Listcell updated_by= new Listcell();
//            Listcell submit_date= new Listcell();
//            Listcell submited_by= new Listcell();
//            Listcell approve_date= new Listcell();
//            Listcell approved_by= new Listcell();
//            Listcell cancel_date= new Listcell();
//            Listcell canceled_by= new Listcell();
//
//            if_id.setLabel(ListIFParam1.getIf_id());
//            if_no.setLabel(ListIFParam1.getIf_no());
//            if_date.setLabel(ListIFParam1.getIf_date());
//            filename.setLabel(ListIFParam1.getFilename());
//            status.setLabel(ListIFParam1.getStatus());
//            po_id.setLabel(ListIFParam1.getPo_id());
//            po_no.setLabel(ListIFParam1.getPurchase_order());
//            create_date.setLabel(ListIFParam1.getCreate_date());
//            created_by.setLabel(ListIFParam1.getCreated_by());
//            update_date.setLabel(ListIFParam1.getUpdate_date());
//            updated_by.setLabel(ListIFParam1.getUpdated_by());
//            submit_date.setLabel(ListIFParam1.getSubmit_date());
//            submited_by.setLabel(ListIFParam1.getSubmited_by());
//            approve_date.setLabel(ListIFParam1.getApprove_date());
//            approved_by.setLabel(ListIFParam1.getApproved_by());
//            cancel_date.setLabel(ListIFParam1.getCancel_date());
//            canceled_by.setLabel(ListIFParam1.getCanceled_by());
//
//    
//            Listitem listitem = new Listitem();
//            listitem.appendChild(if_id);
//            listitem.appendChild(if_no);
//            listitem.appendChild(if_date);
//            listitem.appendChild(filename);
//            listitem.appendChild(status);
//            listitem.appendChild(po_id);
//            listitem.appendChild(po_no);
//            listitem.appendChild(create_date);
//            listitem.appendChild(created_by);
//            listitem.appendChild(update_date);
//            listitem.appendChild(updated_by);
//            listitem.appendChild(submit_date);
//            listitem.appendChild(submited_by);
//            listitem.appendChild(approve_date);
//            listitem.appendChild(approved_by);
//            listitem.appendChild(cancel_date);
//            listitem.appendChild(canceled_by);
//
//            
//            
//            listitem.addEventListener(Events.ON_CLICK, new EventListener() {
//                @Override
//                public void onEvent(Event t) throws Exception {
//                    Path parent1 = new Path("/List_Input_File");
//                    Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
//                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
//                    txtid.setValue(suppdeliveryid);
//                }
//            });
//
//            listitem.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//                @Override
//                public void onEvent(Event t) throws Exception {
//                    String suppdeliveryid = ((Listcell) t.getTarget().getChildren().get(0)).getLabel();
//                    form(suppdeliveryid);
//                }
//            });
//            listbox1.appendChild(listitem);
//        }
//    }

//    public void form(String id) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("ifId", id);
//        Window window = (Window) Executions.createComponents("/Tcash/InputFileTrc.zul", null, map);
//        window.doModal();
//    }

//    public void edit() {
//        Path parent1 = new Path("/List_Input_File");
//        final Textbox txtid = (Textbox) new Path(parent1, "txtid").getComponent();
//        if (txtid.getValue() == "") {
//            System.out.println("return");
//            return;
//        }
//        form(txtid.getValue());
//    }
//    

    
    @Listen("onDoubleClick=#listbox")
    public void doubleCLick(){
        edit();
    }
}
