package obj.funcs;
import java.awt.*;
import javax.swing.*;

public class UI{
    public static UIManager.LookAndFeelInfo[] getLookAndFeel(){
        return UIManager.getInstalledLookAndFeels();
    }

    public static void setLookAndFeel(String name){
        try{
            if(name.equals("WindowsClassic"))
                name="com.sun.java.swing.plaf"
                    +".windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(name);
        }catch(Exception e){
            System.out.println("Error: LookAndFeel file \""
                +name+"\" not found");
        }
    }

    public static void setLookAndFeel(){
        setLookAndFeel(UIManager
            .getSystemLookAndFeelClassName());
    }

    public static JFrame newFrm(String title,
        int width,int height){
        JFrame frm=new JFrame(title);
        frm.setLayout(new GridBagLayout());
        frm.setDefaultCloseOperation(
            WindowConstants.EXIT_ON_CLOSE);
        frm.setSize(width,height);
        return frm;
    }
}
