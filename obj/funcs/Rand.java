package obj.funcs;
import java.util.Random;
import java.awt.Color;

public class Rand extends Random{
    private static final long serialVersionUID=1L;
    //return a double in [min,max);
    public double getDouble(double min,double max){
        return (max-min)*nextDouble()+min;
    }
    //return a int in [min,max];
    public int getInt(int min,int max){
        return (int)getDouble(min,max+1);
    }

    public Color getColor(int r0,int r1,
        int g0,int g1,int b0,int b1){
        return (new Color(getInt(r0,r1),
            getInt(g0,g1),getInt(b0,b1)));
    }

    public Color getColor(int min,int max){
        return getColor(min,max,min,max,min,max);
    }

    public Color getColor(){
        return getColor(0,255,0,255,0,255);
    }
}
