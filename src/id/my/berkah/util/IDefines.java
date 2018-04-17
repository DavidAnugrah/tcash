package id.my.berkah.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vbox;

public interface IDefines {

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_ID = new SimpleDateFormat("dd-MM-yyyy");
    public static final NumberFormat numberFormat = NumberFormat.getInstance();

    public static String[] LISTBOX_SELECTION_COLORS = {
        "#ffffff",//odd
        "#cce6ff",//even
        "#1a90ff",//selected
        "#ffd633",//picked
        "background-color:#FFFACD",//mandatory
        "background-color:#ffffff"//default
    };

    interface COLORS {

        public static final byte ODD = 0;
        public static final byte EVEN = ODD + 1;
        public static final byte SELECTED = EVEN + 1;
        public static final byte PICKED = SELECTED + 1;
        public static final byte MANDATORY = PICKED + 1;
        public static final byte DEFAULT = MANDATORY + 1;
    }

    public interface RETURN_STATUS {

//        public static final byte ALL = 0;
        public static final byte DRAFT = 1;
        public static final byte SUBMITTED = 2;
        public static final byte APPROVED = 3;
        public static final byte CANCELED = 4;
        

        public static final String DESC[] = {"Draft","Submitted","Approved","Canceled"};
        public static final int CODE[] = {DRAFT,SUBMITTED, APPROVED,CANCELED};
    }
    public interface RETURN_STATUS_pi {

//        public static final byte ALL = 0;
        public static final byte DRAFT = 1;
        public static final byte SUBMITTED = 2;
        public static final byte APPROVED = 3;
        public static final byte CANCELED = 4;
        

        public static final String DESC[] = {"Draft","Submitted","Approved","Canceled"};
        public static final int CODE[] = {DRAFT,SUBMITTED, APPROVED,CANCELED};
    }
    
    public interface RETURN_STATE{
        public static final byte DRAFT =1 ;
        public static final byte PROCESSED =2 ;
        public static final byte RELEASED =3;
        public static final byte APPROVED =4;
        public static final byte CANCELED =5 ;
        
        public static final String Desc[]={"Draft","Processes","Released","Approved","Canceled"};
        public static final int code[]={DRAFT,PROCESSED,RELEASED,APPROVED,CANCELED};
    }
    
    
    
    
    
    public interface RETURN_STATE_BUNDLING{
        public static final byte DRAFT =1 ;
        public static final byte SUBMITTED =2 ;
        public static final byte APPROVED =3;
        public static final byte CANCELED =4 ;
        public static final byte COMPLETED =5 ;
        public static final byte FAILED =6 ;
        
        public static final String Desc[]={"Draft","Submitted","Approved","Canceled","Completed","Failed"};
        public static final int code[]={DRAFT,SUBMITTED,APPROVED,CANCELED,COMPLETED,FAILED};
    }
    
    

    public interface MODIF_FLAG {
        public static final byte NEW = 0;
        public static final byte SAVED = NEW + 1;
    }
    
    
       
//      public static Checkbox Listbox_addCheckbox(Listitem listItem, String value) {
//        Listcell cell = new Listcell();
//        Checkbox checkbox = new Checkbox(value);
//        checkbox.setStyle("color:#000000;");
//        Vbox box = new Vbox();
//        box.setHflex("1");
//        box.appendChild(checkbox);
//        cell.appendChild(box);
//        listItem.appendChild(cell);
//        return checkbox;
//    }
    
}
