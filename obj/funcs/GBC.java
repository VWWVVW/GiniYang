package obj.funcs;
import java.awt.Insets;
import java.awt.GridBagConstraints;

public class GBC extends GridBagConstraints{
    private static final long serialVersionUID=1L;

    public GBC(){
        super(0,0,1,1,1,1,CENTER,BOTH,new Insets(0,0,0,0),0,0);
    }

    public GBC(int x,int y,int width,int height,
        int xWeight,int yWeight){
        super(x,y,width,height,(double)xWeight,(double)yWeight,
            CENTER,BOTH,new Insets(0,0,0,0),0,0);
    }

    public GBC(int x,int y,int width,int height,
        double xWeight,double yWeight){
        super(x,y,width,height,xWeight,yWeight,
            CENTER,BOTH,new Insets(0,0,0,0),0,0);
    }

    public GBC(int x,int y,int width,int height,
        int xWeight,int yWeight,int anchor,int fill,
        Insets insets,int ipadx,int ipady){
        super(x,y,width,height,(double)xWeight,(double)yWeight,
            anchor,fill,insets,ipadx,ipady);
    }

    public GBC(int x,int y,int width,int height,
        double xWeight,double yWeight,int anchor,int fill,
        Insets insets,int ipadx,int ipady){
        super(x,y,width,height,xWeight,yWeight,
            anchor,fill,insets,ipadx,ipady);
    }

    public GBC(int x,int width,double xWeight){
        super(x,0,width,1,xWeight,1,CENTER,BOTH,
            new Insets(0,0,0,0),0,0);
    }

    public GBC(int x,int width,int xWeight){
        super(x,0,width,1,(double)xWeight,1,CENTER,BOTH,
            new Insets(0,0,0,0),0,0);
    }

    public void setInsets(int i1,int i2,int i3,int i4){
        this.insets=new Insets(i1,i2,i3,i4);
    }
}
