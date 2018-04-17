/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.ListWoRcvParam;
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
public class ListWORCVCTRL extends SelectorComposer<Component> {
    
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId=global[0];
    String responsibilityId = global[2];

    @Wire
    Window List_WO_RCV,win_list_find;
    
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
        win_list_find.setVisible(false);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        Path parent1 = new Path("/List_WO_RCV/win_list_find");
        Datebox txtDateFrom = (Datebox) new Path(parent1, "txtDateFrom").getComponent();

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
        Path parent = new Path("/List_WO_RCV");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();
        
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
      
        Path parent1 = new Path("/List_WO_RCV/win_list_find");
        Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<ListWoRcvParam> ListWoRcvParam = model.selectListWoRcv(txtContractNo.getValue(),txtPoNo.getValue(),txtRcvNo.getValue(),dateFromx,dateToX,
                "" + activePage, "" + pageSize);
        for (ListWoRcvParam ListWoRcvParam1 : ListWoRcvParam) {
            Listcell order_type= new Listcell();
            Listcell warehouse= new Listcell();
            Listcell item_code= new Listcell();
            Listcell item_description= new Listcell();
            Listcell item_group= new Listcell();
            Listcell block_from= new Listcell();
            Listcell block_to= new Listcell();
            Listcell ordered_quantity= new Listcell();
            Listcell qty_receive= new Listcell();
            Listcell qty_outstanding= new Listcell();
            Listcell vendor= new Listcell();
            Listcell contract= new Listcell();
            Listcell purchase_order= new Listcell();
            Listcell production_date= new Listcell();
            Listcell receive_no= new Listcell();
            Listcell complete_receipt_date_x= new Listcell();
            Listcell expired_date= new Listcell();
            Listcell first_receipt_date= new Listcell();
            Listcell last_receipt_date_x= new Listcell();
            Listcell partial_receipt_x= new Listcell();

            order_type.setLabel(ListWoRcvParam1.getOrder_type());
            warehouse.setLabel(ListWoRcvParam1.getWarehouse());
            item_code.setLabel(ListWoRcvParam1.getItem_code());
            item_description.setLabel(ListWoRcvParam1.getItem_description());
            item_group.setLabel(ListWoRcvParam1.getItem_group());
            block_from.setLabel(ListWoRcvParam1.getBlock_from());
            block_to.setLabel(ListWoRcvParam1.getBlock_to());
            ordered_quantity.setLabel(ListWoRcvParam1.getOrdered_quantity());
            qty_receive.setLabel(ListWoRcvParam1.getQty_receive());
            qty_outstanding.setLabel(ListWoRcvParam1.getQty_outstanding());
            vendor.setLabel(ListWoRcvParam1.getVendor());
            contract.setLabel(ListWoRcvParam1.getContract());
            purchase_order.setLabel(ListWoRcvParam1.getPurchase_order());
            production_date.setLabel(ListWoRcvParam1.getProduction_date());
            receive_no.setLabel(ListWoRcvParam1.getReceive_no());
            expired_date.setLabel(ListWoRcvParam1.getExpired_date());
            first_receipt_date.setLabel(ListWoRcvParam1.getReceipt_date());
            
            ordered_quantity.setStyle("text-align: right");
            qty_receive.setStyle("text-align: right");
            qty_outstanding.setStyle("text-align: right");
            partial_receipt_x.setStyle("text-align: right");

            Listitem listitem = new Listitem();
            listitem.appendChild(order_type);
            listitem.appendChild(warehouse);
            listitem.appendChild(item_code);
            listitem.appendChild(item_description);
            listitem.appendChild(item_group);
            listitem.appendChild(block_from);
            listitem.appendChild(block_to);
            listitem.appendChild(ordered_quantity);
            listitem.appendChild(qty_receive);
            listitem.appendChild(qty_outstanding);
            listitem.appendChild(vendor);
            listitem.appendChild(contract);
            listitem.appendChild(purchase_order);
            listitem.appendChild(production_date);
            listitem.appendChild(receive_no);
            listitem.appendChild(expired_date);
            listitem.appendChild(first_receipt_date);
            
            listbox1.appendChild(listitem);
        }
    }

  
    @Listen("onClick=#refresh")
    public void refreshBtn() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//       
         Path parent1 = new Path("/List_WO_RCV/win_list_find");
        Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<Integer> jumlahRecord = model.getCountWoRcv(txtContractNo.getValue(),txtPoNo.getValue(),txtRcvNo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_WO_RCV");
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
        
        Path parent1 = new Path("/List_WO_RCV/win_list_find");
        Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
        Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
        Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());
        
        List<Integer> jumlahRecord = model.getCountWoRcv(txtContractNo.getValue(),txtPoNo.getValue(),txtRcvNo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_WO_RCV");
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
//        Path parent = new Path("/List_WO_RCV");
//        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
//        
//        if (listbox1.getItemCount() == 0){
//            Messagebox.show("No data to be printed.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
//                @Override
//                public void onEvent(Messagebox.ClickEvent event) throws Exception {
//                    if (Messagebox.Button.YES.equals(event.getButton())) {
//        
//                    Path parent1 = new Path("/List_WO_RCV/win_list_find");
//                    Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
//                    Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
//                    Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();
//
//                    HashMap map = new HashMap();
//                    map.put("po_no", txtPoNo.getText());
//                    map.put("ctr_no", txtContractNo.getText());
//                    map.put("rcv_no", txtRcvNo.getText());
//                    new JRreportWindow(List_WO_RCV, true, map, "/reports/report_wo_rcv.jasper", "xls", "Print Data WO RCV", "Data WO Until Receiving");
//                    }
//                }
//            };
//            Messagebox.show("Are you sure want to print this data?", "Message", new Messagebox.Button[]{
//                Messagebox.Button.YES, Messagebox.Button.NO
//            }, Messagebox.QUESTION, clickListener);
//    }
    
    @Listen("onClick=#print")
    public void printXLS() throws FileNotFoundException{
//        Messagebox.show(media.getName());
        Path parent = new Path("/List_WO_RCV");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        if (listbox1.getItemCount() == 0){
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
                        
                    Path parent1 = new Path("/List_WO_RCV/win_list_find");
                    Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
                    Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
                    Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();

                    File xlFile=new File(download_directory+"WO Until Receiving"+"_"+txtPoNo.getValue()+".xls");
            //        Filedownload.save(xlFile,"Test.xls");
            ////        xlFile.createNewFile("D:/⁠/⁠Test.xls");
                    ListWORCVCTRL test = new ListWORCVCTRL();
                    test.fillDataZkos(xlFile);
                    Filedownload.save(new File(download_directory+"WO Until Receiving"+"_"+txtPoNo.getValue()+".xls"),null);
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
            Path parent1 = new Path("/List_WO_RCV/win_list_find");
            Textbox txtContractNo = (Textbox) new Path(parent1, "txtContractNo").getComponent();
            Textbox txtPoNo = (Textbox) new Path(parent1, "txtPoNo").getComponent();
            Textbox txtRcvNo = (Textbox) new Path(parent1, "txtRcvNo").getComponent();
            
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
            
            String title1="Order Type";
            String title2="Warehouse";
            String title3="Item Code";
            String title4="Item Description";
            String title5="Item Group";
            String title6="Block From";
            String title7="Block To";
            String title8="Ordered Quantity";
            String title9="Qty Receive";
            String title10="Qty Outstanding";
            String title11="Vendor";
            String title12="Contract";
            String title13="Purchase Order";
            String title14="Production Date";
            String title15="Receive No";
            String title16="Expired Date";
            String title17="Receipt Date";
            Label column1 = new Label(0, 0,title1);
            Label column2 = new Label(1, 0,title2);
            Label column3 = new Label(2, 0,title3);
            Label column4 = new Label(3, 0,title4);
            Label column5 = new Label(4, 0,title5);
            Label column6 = new Label(5, 0,title6);
            Label column7 = new Label(6, 0,title7);
            Label column8 = new Label(7, 0,title8);
            Label column9 = new Label(8, 0,title9);
            Label column10 = new Label(9, 0,title10);
            Label column11 = new Label(10, 0,title11);
            Label column12 = new Label(11, 0,title12);
            Label column13 = new Label(12, 0,title13);
            Label column14 = new Label(13, 0,title14);
            Label column15 = new Label(14, 0,title15);
            Label column16 = new Label(15, 0,title16);
            Label column17 = new Label(16, 0,title17);
            column1.setCellFormat(format);               
            column2.setCellFormat(format);               
            column3.setCellFormat(format);               
            column4.setCellFormat(format);               
            column5.setCellFormat(format);               
            column6.setCellFormat(format);               
            column7.setCellFormat(format);               
            column8.setCellFormat(format);               
            column9.setCellFormat(format);               
            column10.setCellFormat(format);               
            column11.setCellFormat(format);               
            column12.setCellFormat(format);               
            column13.setCellFormat(format);               
            column14.setCellFormat(format);               
            column15.setCellFormat(format);               
            column16.setCellFormat(format);               
            column17.setCellFormat(format);               
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
            sheet1.addCell(column13);
            sheet1.addCell(column14);
            sheet1.addCell(column15);
            sheet1.addCell(column16);
            sheet1.addCell(column17);
//           }
           List<ListWoRcvParam> ListWoRcvParam = model.selectListWoRcvPrint(txtContractNo.getValue(),txtPoNo.getValue(),txtRcvNo.getValue(),dateFromx,dateToX);
           int j = 0;
            for (ListWoRcvParam ListWoRcvParam1 : ListWoRcvParam) {
                    String col1 = ListWoRcvParam1.getOrder_type().toString();
                    String col2 = ListWoRcvParam1.getWarehouse().toString();
                    String col3 = ListWoRcvParam1.getItem_code().toString();
                    String col4 = ListWoRcvParam1.getItem_description().toString();
                    String col5 = ListWoRcvParam1.getItem_group().toString();
                    String col6 = ListWoRcvParam1.getBlock_from().toString();
                    String col7 = ListWoRcvParam1.getBlock_to().toString();
                    String col8 = ListWoRcvParam1.getOrdered_quantity().toString();
                    String col9 = ListWoRcvParam1.getQty_receive().toString();
                    String col10 = ListWoRcvParam1.getQty_outstanding().toString();
                    String col11 = ListWoRcvParam1.getVendor().toString();
                    String col12 = ListWoRcvParam1.getContract().toString();
                    String col13 = ListWoRcvParam1.getPurchase_order().toString();
                    String col14 = ListWoRcvParam1.getProduction_date().toString();
                    String col15 = ListWoRcvParam1.getReceive_no().toString();
                    String col16 = ListWoRcvParam1.getExpired_date().toString();
                    String col17 = ListWoRcvParam1.getReceipt_date().toString();
//          
                    Label cols1 = new Label(0,j+1,col1,formatRow);
                    Label cols2 = new Label(1,j+1,col2,formatRow);
                    Label cols3 = new Label(2,j+1,col3,formatRow);
                    Label cols4 = new Label(3,j+1,col4,formatRow);
                    Label cols5 = new Label(4,j+1,col5,formatRow);
                    Label cols6 = new Label(5,j+1,col6,formatRow);
                    Label cols7 = new Label(6,j+1,col7,formatRow);
                    Label cols8 = new Label(7,j+1,col8,formatRow);
                    Label cols9 = new Label(8,j+1,col9,formatRow);
                    Label cols10 = new Label(9,j+1,col10,formatRow);
                    Label cols11 = new Label(10,j+1,col11,formatRow);
                    Label cols12 = new Label(11,j+1,col12,formatRow);
                    Label cols13 = new Label(12,j+1,col13,formatRow);
                    Label cols14 = new Label(13,j+1,col14,formatRow);
                    Label cols15 = new Label(14,j+1,col15,formatRow);
                    Label cols16 = new Label(15,j+1,col16,formatRow);
                    Label cols17 = new Label(16,j+1,col17,formatRow);
                    
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
                    sheet1.setColumnView(11, 30);
                    sheet1.addCell(cols13);
                    sheet1.addCell(cols14);
                    sheet1.addCell(cols15);
                    sheet1.addCell(cols16);
                    sheet1.addCell(cols17);
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
