/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.tcash1.controller;

import id.my.berkah.tcash1.model.ListWoListSNParam;
import id.my.berkah.util.JRreportWindow;
import id.my.berkah.util.ParameterGlobal;
import id.my.berkah.util.controller.LovController;
import id.my.berkah.util.implement.ProcedureUtilImpl;
import id.my.berkah.util.model.ParamCekFunction;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author arry
 */
public class BAParamCTRL extends SelectorComposer<Component> {
    private String[] global = ParameterGlobal.getParameterGlobal();
    String userId = global[0];
    String responsibilityId = global[2];
    String poId="";
    String woNo="";
    
    WorkOrderCTRL workOrderCTRL;
    
    @Wire
    Listbox listbox;
    
    @Wire
    Textbox txtNamaPertama,txtJabatanPertama,txtNikPertama,txtNamaKedua,txtJabatanKedua,txtNikKedua;
    
    @Wire
    Window win_BA;
    
    ModelTcashCTLR model = new ModelTcashCTLR();
    
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //
    }
    
    @Listen("onClick=#btnPihak1")
    public void lovPihak1() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,PH1_NAMA as \"NAMA\",PH1_JABATAN as \"JABATAN\",PH1_NIK as \"NIK\" from (select PH1_NAMA,PH1_JABATAN,PH1_NIK from table(pkg_tcash_lov.LovBast('PERTAMA')))\n"
                + "where (upper(PH1_NAMA) like upper('?') or upper(PH1_JABATAN) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBast('PERTAMA')) where \n"
                + "(upper(PH1_NAMA) like upper('?') or upper(PH1_JABATAN) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtNamaPertama,txtJabatanPertama,txtNikPertama});
        composerLov.setHiddenColumn(new int[]{0});

        composerLov.setTitle("List Of PIC");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_BA);
        w.doModal();
    }
    
    @Listen("onClick=#btnPihak2")
    public void lovPihak2() {
        HashMap map = new HashMap<String, Object>();

        LovController composerLov = new LovController();
        composerLov.setQuery("select * from (select rownum as No,PH1_NAMA as \"NAMA\",PH1_JABATAN as \"JABATAN\",PH1_NIK as \"NIK\" from (select PH1_NAMA,PH1_JABATAN,PH1_NIK from table(pkg_tcash_lov.LovBast('PERTAMA')))\n"
                + "where (upper(PH1_NAMA) like upper('?') or upper(PH1_JABATAN) like \n"
                + "upper('?'))) where No BETWEEN param1 and param2 ");

        composerLov.setQueryTotal("select count(*) from table(pkg_tcash_lov.LovBast('PERTAMA')) where \n"
                + "(upper(PH1_NAMA) like upper('?') or upper(PH1_JABATAN) like upper('?'))");
//       
        composerLov.setColumnWidth(new String[]{"100px", "100px", "150px", "320px"});
        composerLov.setSelectedColumn(new int[]{1, 2, 3});
        composerLov.setComponentTransferData(new Textbox[]{txtNamaKedua,txtJabatanKedua,txtNikKedua});
        composerLov.setHiddenColumn(new int[]{0});

        composerLov.setTitle("List Of PIC");
        composerLov.setWidth("500px");
        composerLov.setHeight("350px");
        composerLov.setPageSize(10);
        map.put("composerLov", composerLov);
        Window w = (Window) Executions.createComponents("/fnd/lov/lov.zul", null, map);
        w.setParent(win_BA);
        w.doModal();
    }
    
    @Listen("onClick=#printBA")
    public void printWo() throws IOException {
        if (isComplete())
        {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("paradise.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String subreport = properties.getProperty("subreporthrn");
        String logo = properties.getProperty("logo_hrn");
        ProcedureUtilImpl ci = new ProcedureUtilImpl();
        ParamCekFunction cf = new ParamCekFunction();
        cf.setUserId(this.global[0]);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0);
//        
        Date date = new Date();
//        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
//        System.out.println(cal);
        
        String tgl = "";
        String bln = "";
        String thn = "";
        SimpleDateFormat tanggal = new SimpleDateFormat("dd");
        SimpleDateFormat bulan = new SimpleDateFormat("MM");
        SimpleDateFormat tahun = new SimpleDateFormat("yyyy");
        
        tgl = tanggal.format(date);
        bln = bulan.format(date);
        thn = tahun.format(date);
        
        System.out.println(tgl);
        System.out.println(bln);
        System.out.println(thn);
        System.out.println(poId);

        HashMap map = new HashMap();
        map.put("SUBREPORT_DIR", subreport);
        map.put("tgl", tgl);
        map.put("bln", bln);
        map.put("tahun", thn);
        map.put("nama_pertama", txtNamaPertama.getValue());
        map.put("nama_kedua", txtNamaKedua.getValue());
        map.put("jabatan_pertama", txtJabatanPertama.getValue());
        map.put("jabatan_kedua", txtJabatanKedua.getValue());
        map.put("NIK", txtNikPertama.getValue());
        map.put("po_id", poId);
        map.put("woNo", woNo);
        map.put("logo", logo);
            System.out.println(map);
            JRreportWindow jRreportWindow = new JRreportWindow(this.win_BA, true, map, "/reports/BeritaAcaraHRN.jasper", "pdf", "Print Berita Acara", "Berita Acara");
        } else
        {
            Messagebox.show("Complete all textbox first.", "Message", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
    
    private boolean isComplete()
    {
        if (txtNamaPertama.getText().equals(""))
        {
            return false;
        }

        if (txtNamaKedua.getText().equals(""))
        {
            return false;
        }

        if (txtJabatanPertama.getText().equals(""))
        {
            return false;
        }

        if (txtJabatanKedua.getText().equals(""))
        {
            return false;
        }

        if (txtNikPertama.getText().equals(""))
        {
            return false;
        }

        return true;
    }
    
    
    @Listen("onClick=#close")
    public void close(){
        win_BA.detach();
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public WorkOrderCTRL getWorkOrderCTRL() {
        return workOrderCTRL;
    }

    public void setWorkOrderCTRL(WorkOrderCTRL workOrderCTRL) {
        this.workOrderCTRL = workOrderCTRL;
    }

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }
    
    
}
