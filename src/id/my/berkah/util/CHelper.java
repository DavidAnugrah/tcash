package id.my.berkah.util;

import java.util.Properties;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

public class CHelper {

    private static Properties propConfig;

    public static void ShowMessage(String message, String type) {
        String title = "";
        if (type.equals(Messagebox.INFORMATION)) {
            title = "Information";
        } else if (type.equals(Messagebox.EXCLAMATION)) {
            title = "Warning";
        }
        if (type.equals(Messagebox.ERROR)) {
            title = "Error";
        }
        Messagebox.show(message, title, Messagebox.OK, type);
    }

    public static void Listbox_addComponent(Listitem listItem, Component comp) {
        Listcell cell = new Listcell();
        cell.appendChild(comp);
        listItem.appendChild(cell);
    }

    public static Label Listbox_addLabel(Listitem listItem, String value, String alignment, String style) {
        Listcell cell = new Listcell();
        Label label = new Label(value);
        label.setStyle(style == null ? "color:#000000;" : style);
        label.setHflex("1");
        Vbox box = new Vbox();
        box.setHflex("1");
        box.setAlign(alignment);
        box.appendChild(label);
        cell.appendChild(box);
        listItem.appendChild(cell);
        return label;
    }

    public static Label Listbox_addLabel(Listitem listItem, String value, String alignment) {
        return Listbox_addLabel(listItem, value, alignment, null);
    }

    public static Textbox Listbox_addTextbox(Listitem listItem, String value) {
        Listcell cell = new Listcell();
        Textbox textbox = new Textbox(value);
        textbox.setInplace(true);
        textbox.setStyle("color:#000000;");
        textbox.setHflex("1");
        cell.appendChild(textbox);
        listItem.appendChild(cell);
        return textbox;
    }

    public static Checkbox Listbox_addCheckbox(Listitem listItem, String value) {
        Listcell cell = new Listcell();
        Checkbox checkbox = new Checkbox(value);
        checkbox.setStyle("color:#000000;");
        Vbox box = new Vbox();
        box.setHflex("1");
        box.appendChild(checkbox);
        cell.appendChild(box);
        listItem.appendChild(cell);
        return checkbox;
    }

    public static String getConfig(String fieldName) {
        if (propConfig == null) {
            propConfig = new Properties();
            try {
                propConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return propConfig.getProperty(fieldName);
    }
}
