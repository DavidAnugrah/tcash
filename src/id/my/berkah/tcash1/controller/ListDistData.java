/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.ListWoRcvParam;
import id.my.berkah.tcash1.model.ListdistdataParam;
import id.my.berkah.tcash1.model.ListloadtourpParam;
import id.my.berkah.util.JRreportWindow;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 *
 * @author Azec
 */
public class ListDistData extends SelectorComposer<Component> {
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];

    @Wire
    Window List_Dist_Data,win_list_find;
    
    @Wire
    Paging userPaging;
    
    ModelTcashCTLR model = new ModelTcashCTLR();

    private int startPageNumber = 1;
    private final int pageSize = 10;
    private int JumlahRecord = 0;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        win_list_find.setVisible(false);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        Path parent = new Path("/List_Dist_Data/win_list_find");
        Datebox txtDateFrom = (Datebox) new Path(parent, "txtDateFrom").getComponent();

        txtDateFrom.setValue(cal.getTime());
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
        Path parent = new Path("/List_Dist_Data");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();
        
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
      
        Path parent1 = new Path("/List_Dist_Data/win_list_find");
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
        
        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<ListdistdataParam> ListdistdataParam = model.selectListdistdata(txtWoNo.getValue(),txtRefNo.getValue(),dateFromx,dateToX,
                "" + activePage, "" + pageSize);
        for (ListdistdataParam ListdistdataParam1 : ListdistdataParam) {
            Listcell item_group= new Listcell();
            Listcell item_description= new Listcell();
            Listcell transaction_type= new Listcell();
            Listcell wo_no= new Listcell();
            Listcell ref_no= new Listcell();
            Listcell branch= new Listcell();
            Listcell warehouse= new Listcell();
            Listcell relation_branch= new Listcell();
            Listcell warehouse_relation= new Listcell();
            Listcell transaction_date= new Listcell();
            Listcell qty_out= new Listcell();
            Listcell qty_in= new Listcell();

            item_group.setLabel(ListdistdataParam1.getItem_group());
            item_description.setLabel(ListdistdataParam1.getItem_description());
            transaction_type.setLabel(ListdistdataParam1.getTransaction_type());
            wo_no.setLabel(ListdistdataParam1.getWo_no());
            ref_no.setLabel(ListdistdataParam1.getRef_no());
            branch.setLabel(ListdistdataParam1.getBranch());
            warehouse.setLabel(ListdistdataParam1.getWarehouse());
            relation_branch.setLabel(ListdistdataParam1.getRelation_branch());
            warehouse_relation.setLabel(ListdistdataParam1.getWarehouse_relation());
            transaction_date.setLabel(ListdistdataParam1.getTransaction_date());
            qty_out.setLabel(ListdistdataParam1.getQty_out());
            qty_in.setLabel(ListdistdataParam1.getQty_in());
            
            qty_out.setStyle("text-align: right");
            qty_in.setStyle("text-align: right");

            Listitem listitem = new Listitem();
            listitem.appendChild(item_group);
            listitem.appendChild(item_description);
            listitem.appendChild(transaction_type);
            listitem.appendChild(wo_no);
            listitem.appendChild(ref_no);
            listitem.appendChild(branch);
            listitem.appendChild(warehouse);
            listitem.appendChild(relation_branch);
            listitem.appendChild(warehouse_relation);
            listitem.appendChild(transaction_date);
            listitem.appendChild(qty_out);
            listitem.appendChild(qty_in);
            
            listbox1.appendChild(listitem);
        }
    }

  
    @Listen("onClick=#refresh")
    public void refreshBtn() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//       
         Path parent1 = new Path("/List_Dist_Data/win_list_find");
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
        
        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<Integer> jumlahRecord = model.getCountdistdata(txtWoNo.getValue(),txtRefNo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Dist_Data");
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
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
         Path parent1 = new Path("/List_Dist_Data/win_list_find");
        Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
        Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
        
        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<Integer> jumlahRecord = model.getCountdistdata(txtWoNo.getValue(),txtRefNo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Dist_Data");
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
    
//    @Listen("onClick=#print")
//    public void print() throws FileNotFoundException{
//        Path parent = new Path("/List_Dist_Data");
//        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
//        
//        if (listbox1.getItemCount() == 0){
//            Messagebox.show("No data to be printed.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        
//        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
//                @Override
//                public void onEvent(Messagebox.ClickEvent event) throws Exception {
//                    if (Messagebox.Button.YES.equals(event.getButton())) {
//                    String dateFromx = "";
//                    String dateToX = "";
//                    SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//                    Path parent1 = new Path("/List_Dist_Data/win_list_find");
//                    Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
//                    Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
//                    Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
//                    Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();
//
//                    dateFromx = rdf.format(findFromDate1.getValue());
//                    dateToX = rdf.format(findToDate1.getValue());
//
//                    HashMap map = new HashMap();
//                    map.put("woNo", txtWoNo.getText());
//                    map.put("refNo", txtRefNo.getText());
//                    map.put("from", dateFromx);
//                    map.put("to", dateToX);
//                    new JRreportWindow(List_Dist_Data, true, map, "/reports/report_dist_data.jasper", "xls", "Print Data Loading to URP", "Data Loading to URP");
//                    }
//                }
//            };
//            Messagebox.show("Are you sure want to print this data?", "Message", new Messagebox.Button[]{
//                Messagebox.Button.YES, Messagebox.Button.NO
//            }, Messagebox.QUESTION, clickListener);
//    }
    
    @Listen("onClick=#print")
    public void printXLS() throws FileNotFoundException {
        Path parent = new Path("/List_Dist_Data");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();

        if (listbox1.getItemCount() == 0) {
            Messagebox.show("No data to be printed.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        
        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            @Override
            public void onEvent(Messagebox.ClickEvent event) throws Exception {
                if (Messagebox.Button.YES.equals(event.getButton())) {
                    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
                    Properties properties = new Properties();
                    properties.load(inputStream);
                    String download_directory = properties.getProperty("download_directory");
                    Path parent1 = new Path("/List_Dist_Data/win_list_find");
                    Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
                    Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
                    Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
                    Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

                    File xlFile=new File(download_directory+"List_Dist_Data"+"_"+txtRefNo.getValue()+".xls");
                    
                    ListDistData test = new ListDistData();
                    test.fillDataZkos(xlFile);
                    Filedownload.save(new File(download_directory+"List_Dist_Data"+"_"+txtRefNo.getValue()+".xls"), null);
                    //        xlFile.delete();
                }
            }
        };
        Messagebox.show("Are you sure want to print this data?", "Message", new Messagebox.Button[]{
            Messagebox.Button.YES, Messagebox.Button.NO
        }, Messagebox.QUESTION, clickListener);

    }
    
    public void fillDataZkos(File file) {

        try {
            String dateFromx = "";
            String dateToX = "";
            SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
            Path parent1 = new Path("/List_Dist_Data/win_list_find");
            Textbox txtWoNo = (Textbox) new Path(parent1, "txtWoNo").getComponent();
            Textbox txtRefNo = (Textbox) new Path(parent1, "txtRefNo").getComponent();
            Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
            Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

            dateFromx = rdf.format(findFromDate1.getValue());
            dateToX = rdf.format(findToDate1.getValue());

            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0);
            WritableCellFormat format = new WritableCellFormat();
            format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.getStyle(1));
            format.setBackground(Colour.GRAY_25);
            format.setVerticalAlignment(VerticalAlignment.CENTRE);
            WritableCellFormat formatRow = new WritableCellFormat();
            formatRow.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.getStyle(1));
            formatRow.setBackground(Colour.WHITE);
            formatRow.setVerticalAlignment(VerticalAlignment.CENTRE);

            String title1 = "Item Group";
            String title2 = "Item Description";
            String title3 = "Transaction Type";
            String title4 = "WO No";
            String title5 = "Ref No";
            String title6 = "Branch";
            String title7 = "Warehouse";
            String title8 = "Relation Branch";
            String title9 = "Warehouse Relation";
            String title10 = "Relation Branch";
            String title11 = "Qty Out";
            String title12 = "Qty In";
            
            Label column1 = new Label(0, 0, title1,format);
            Label column2 = new Label(1, 0, title2,format);
            Label column3 = new Label(2, 0, title3,format);
            Label column4 = new Label(3, 0, title4,format);
            Label column5 = new Label(4, 0, title5,format);
            Label column6 = new Label(5, 0, title6,format);
            Label column7 = new Label(6, 0, title7,format);
            Label column8 = new Label(7, 0, title8,format);
            Label column9 = new Label(8, 0, title9,format);
            Label column10 = new Label(9, 0, title10,format);
            Label column11 = new Label(10, 0, title11,format);
            Label column12 = new Label(11, 0, title12,format);
            
            sheet1.addCell(column1);
            sheet1.addCell(column2);
            sheet1.addCell(column3);
            sheet1.addCell(column4);
            sheet1.addCell(column5);
            sheet1.addCell(column6);
            sheet1.addCell(column7);
            sheet1.addCell(column8);
            sheet1.addCell(column9);
            sheet1.addCell(column10);
            sheet1.addCell(column11);
            sheet1.addCell(column12);
//           }
            List<ListdistdataParam> ListdistdataParam = model.selectListdistdataPrint(txtWoNo.getValue(),txtRefNo.getValue(),dateFromx,dateToX);
            int j = 0;
            for (ListdistdataParam ListdistdataParam1 : ListdistdataParam) {
                
                System.out.println(ListdistdataParam1.getItem_group());
                String col1 = ListdistdataParam1.getItem_group();
                String col2 = ListdistdataParam1.getItem_description();
                String col3 = ListdistdataParam1.getTransaction_type();
                String col4 = ListdistdataParam1.getWo_no();
                String col5 = ListdistdataParam1.getRef_no();
                String col6 = ListdistdataParam1.getBranch();
                String col7 = ListdistdataParam1.getWarehouse();
                String col8 = ListdistdataParam1.getRelation_branch();
                String col9 = ListdistdataParam1.getWarehouse_relation();
                String col10 = ListdistdataParam1.getTransaction_date();
                String col11 = ListdistdataParam1.getQty_out();
                String col12 = ListdistdataParam1.getQty_in();
//          
                Label cols1 = new Label(0, j + 1, col1, formatRow);
                Label cols2 = new Label(1, j + 1, col2, formatRow);
                Label cols3 = new Label(2, j + 1, col3, formatRow);
                Label cols4 = new Label(3, j + 1, col4, formatRow);
                Label cols5 = new Label(4, j + 1, col5, formatRow);
                Label cols6 = new Label(5, j + 1, col6, formatRow);
                Label cols7 = new Label(6, j + 1, col7, formatRow);
                Label cols8 = new Label(7, j + 1, col8, formatRow);
                Label cols9 = new Label(8, j + 1, col9, formatRow);
                Label cols10 = new Label(9, j + 1, col10, formatRow);
                Label cols11 = new Label(10, j + 1, col11, formatRow);
                Label cols12 = new Label(11, j + 1, col12, formatRow);

                sheet1.addCell(cols1);
                sheet1.addCell(cols2);
                sheet1.addCell(cols3);
                sheet1.addCell(cols4);
                sheet1.addCell(cols5);
                sheet1.addCell(cols6);
                sheet1.addCell(cols7);
                sheet1.addCell(cols8);
                sheet1.addCell(cols9);
                sheet1.addCell(cols10);
                sheet1.addCell(cols11);
                sheet1.addCell(cols12);
                
//                    nomor++;
                j++;
            }

            workbook1.write();
            workbook1.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
