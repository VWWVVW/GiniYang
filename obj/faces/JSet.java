package obj.faces;
import obj.funcs.*;
import java.awt.*;
import javax.swing.*;

public class JSet extends JPanel{
    private static final long serialVersionUID=1L;
    public JLabel lbl=new JLabel();
    public JTextField txt=new JTextField();

    public JSet(String title){
        super(new GridBagLayout());
        add(title,"");
    }

    public JSet(String title,boolean isEditable){
        super(new GridBagLayout());
        add(title,"");
        txt.setEditable(isEditable);
    }

    private void add(String title,String text){
        add(lbl,new GBC(0,1,0));
        add(txt,new GBC(1,1,1));
        lbl.setText(title);
        txt.setText(text);
    }

    public void setText(String str){
        txt.setText(str);
    }

    public void setEditable(boolean isEditable){
        txt.setEditable(isEditable);
    }

    public final String getText(){
        return txt.getText();
    }
}
