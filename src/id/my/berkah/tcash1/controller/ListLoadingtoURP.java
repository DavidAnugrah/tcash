/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoParam;
import id.my.berkah.tcash1.model.ListWoRcvParam;
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
public class ListLoadingtoURP extends SelectorComposer<Component> {

    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    @Wire
    Window List_Loading_to_URP, win_list_find;
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

        Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
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
        Path parent = new Path("/List_Loading_to_URP");
        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
        listbox1.getItems().clear();

        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");

        Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
        Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        List<ListloadtourpParam> ListloadtourpParam = model.selectListloadtourp(txtIDONo.getValue(),dateFromx,dateToX,
                "" + activePage, "" + pageSize);
        for (ListloadtourpParam ListloadtourpParam1 : ListloadtourpParam) {
            Listcell ido_number = new Listcell();
            Listcell block_from = new Listcell();
            Listcell block_to = new Listcell();
            Listcell qty = new Listcell();
            Listcell date_create_loading = new Listcell();
            Listcell date_finish_loading = new Listcell();
            Listcell lar_no = new Listcell();
            Listcell lar_status = new Listcell();

            ido_number.setLabel(ListloadtourpParam1.getIdo_number());
            lar_no.setLabel(ListloadtourpParam1.getLar_no());
            block_from.setLabel(ListloadtourpParam1.getBlock_from());
            block_to.setLabel(ListloadtourpParam1.getBlock_to());
            qty.setLabel(ListloadtourpParam1.getQty());
            lar_status.setLabel(ListloadtourpParam1.getLar_status());
            date_create_loading.setLabel(ListloadtourpParam1.getDate_create_loading());
            date_finish_loading.setLabel(ListloadtourpParam1.getDate_finish_loading());

            qty.setStyle("text-align: right");

            Listitem listitem = new Listitem();
            listitem.appendChild(ido_number);
            listitem.appendChild(lar_no);
            listitem.appendChild(lar_status);
            listitem.appendChild(block_from);
            listitem.appendChild(block_to);
            listitem.appendChild(qty);
            listitem.appendChild(date_create_loading);
            listitem.appendChild(date_finish_loading);

            listbox1.appendChild(listitem);
        }
    }

    @Listen("onClick=#refresh")
    public void refreshBtn() {
        String dateFromx = "";
        String dateToX = "";
        SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
//       
        Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
        Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        List<Integer> jumlahRecord = model.getCountloadtourp(txtIDONo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Loading_to_URP");
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
        Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
        Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();
        Datebox findFromDate1 = (Datebox) new Path(parent1, "txtDateFrom").getComponent();
        Datebox findToDate1 = (Datebox) new Path(parent1, "txtDateTo").getComponent();

        dateFromx = rdf.format(findFromDate1.getValue());
        dateToX = rdf.format(findToDate1.getValue());

        List<Integer> jumlahRecord = model.getCountloadtourp(txtIDONo.getValue(),dateFromx,dateToX);
        if (!jumlahRecord.isEmpty()) {
            JumlahRecord = jumlahRecord.get(0);
        }
        System.out.println(jumlahRecord);

        Path pg = new Path("/List_Loading_to_URP");
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
//    public void print() throws FileNotFoundException {
//        Path parent = new Path("/List_Loading_to_URP");
//        Listbox listbox1 = (Listbox) new Path(parent, "listbox").getComponent();
//
//        if (listbox1.getItemCount() == 0) {
//            Messagebox.show("No data to be printed.", "Message", Messagebox.OK, Messagebox.EXCLAMATION);
//            return;
//        }
//
//        EventListener<Messagebox.ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
//            @Override
//            public void onEvent(Messagebox.ClickEvent event) throws Exception {
//                if (Messagebox.Button.YES.equals(event.getButton())) {
//                    Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
//                    Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();
//
//                    HashMap map = new HashMap();
//                    map.put("idoNo", txtIDONo.getText());
//                    new JRreportWindow(List_Loading_to_URP, true, map, "/reports/report_loading_to_urp.jasper", "xls", "Print Data Loading to URP", "Data Loading to URP");
//                }
//            }
//        };
//        Messagebox.show("Are you sure want to print this data?", "Message", new Messagebox.Button[]{
//            Messagebox.Button.YES, Messagebox.Button.NO
//        }, Messagebox.QUESTION, clickListener);
//    }

    @Listen("onClick=#print")
    public void printXLS() throws FileNotFoundException {
        Path parent = new Path("/List_Loading_to_URP");
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
                    
                    Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
                    Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();

                            File xlFile=new File(download_directory+ "Loading Data To URP" + "_" + txtIDONo.getValue() + ".xls");
//                    File xlFile = new File("D://" + "Loading Data To URP" + "_" + txtIDONo.getValue() + ".xls");
                    
                    ListLoadingtoURP test = new ListLoadingtoURP();
                    test.fillDataZkos(xlFile);
                            Filedownload.save(new File(download_directory+ "Loading Data To URP" + "_" + txtIDONo.getValue() + ".xls"),null);
//                    Filedownload.save(new File("D://" + "Loading Data To URP" + "_" + txtIDONo.getValue() + ".xls"), null);
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
            Path parent1 = new Path("/List_Loading_to_URP/win_list_find");
            Textbox txtIDONo = (Textbox) new Path(parent1, "txtIDONo").getComponent();
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

            String title1 = "IDO Number";
            String title2 = "LAR Number";
            String title3 = "LAR Status";
            String title4 = "Block From";
            String title5 = "Block To";
            String title6 = "QTY";
            String title7 = "Date Create Loading";
            String title8 = "Date Finish Loading";
            
            Label column1 = new Label(0, 0, title1,format);
            Label column2 = new Label(1, 0, title2,format);
            Label column3 = new Label(2, 0, title3,format);
            Label column4 = new Label(3, 0, title4,format);
            Label column5 = new Label(4, 0, title5,format);
            Label column6 = new Label(5, 0, title6,format);
            Label column7 = new Label(6, 0, title7,format);
            Label column8 = new Label(7, 0, title8,format);
            
            sheet1.addCell(column1);
            sheet1.addCell(column2);
            sheet1.addCell(column3);
            sheet1.addCell(column4);
            sheet1.addCell(column5);
            sheet1.addCell(column6);
            sheet1.addCell(column7);
            sheet1.addCell(column8);
//           }
            List<ListloadtourpParam> ListloadtourpParam = model.selectListloadtourpPrint(txtIDONo.getValue(),dateFromx,dateToX);
            int j = 0;
            for (ListloadtourpParam ListloadtourpParam1 : ListloadtourpParam) {
                System.out.println(ListloadtourpParam1.getIdo_number());
                String col1 = ListloadtourpParam1.getIdo_number();
                String col2 = ListloadtourpParam1.getLar_no();
                String col3 = ListloadtourpParam1.getBlock_from();
                String col4 = ListloadtourpParam1.getBlock_to();
                String col5 = ListloadtourpParam1.getQty();
                String col6 = ListloadtourpParam1.getLar_status();
                String col7 = ListloadtourpParam1.getDate_create_loading();
                String col8 = ListloadtourpParam1.getDate_finish_loading();
//          
                Label cols1 = new Label(0, j + 1, col1, formatRow);
                Label cols2 = new Label(1, j + 1, col2, formatRow);
                Label cols3 = new Label(2, j + 1, col3, formatRow);
                Label cols4 = new Label(3, j + 1, col4, formatRow);
                Label cols5 = new Label(4, j + 1, col5, formatRow);
                Label cols6 = new Label(5, j + 1, col6, formatRow);
                Label cols7 = new Label(6, j + 1, col7, formatRow);
                Label cols8 = new Label(7, j + 1, col8, formatRow);

                sheet1.addCell(cols1);
                sheet1.addCell(cols2);
                sheet1.addCell(cols3);
                sheet1.addCell(cols4);
                sheet1.addCell(cols5);
                sheet1.addCell(cols6);
                sheet1.addCell(cols7);
                sheet1.addCell(cols8);
                
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
