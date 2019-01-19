package obj.cores;
import java.util.ArrayList;

public class Gini extends ArrayList<Double>{
    private static final long serialVersionUID=1L;
    private double initAvg;
//Constructors
    public Gini(){
        super();
        init(100,100);
    }

    public Gini(int size,double money){
        super();
        if(money>0&&size>0&&money*size<1000000)
            init(size,money);
        else
            init(100,100);
    }

    public Gini(ArrayList<Double> source){
        super(source);
        initAvg=getAvg();
        if(initAvg<=0)
            initAvg=1;
    }
//Methods-inits
    public void init(int size,double money){
        if(money<1||size<1||money*size>1000000)
            return;
        clear();
        for(int i=0;i<size;i++)
            add(money);
        this.initAvg=money;
    }

    public void init(){
        init(size(),initAvg);
    }
//Methods-gets
    protected ArrayList<Double> getACopy(){
        ArrayList<Double> result=
            new ArrayList<Double>();
        for(int i=0;i<size();i++)
            result.add(get(i));
        return result;
    }

    public ArrayList<Double> getSorted(boolean isZ2A){
        ArrayList<Double> a=getACopy();
        int chosen,k=isZ2A?1:-1;
        double space;
        for(int i=0;i<a.size();i++){
            chosen=i;
            for(int j=i;j<a.size();j++)
                if(k*a.get(j)>k*a.get(chosen))
                    chosen=j;
            space=a.get(chosen);
            a.set(chosen,a.get(i));
            a.set(i,space);
        }
        return a;
    }

    public double getMax(){
        double max=0;
        for(int i=0;i<size();i++)
            if(max<get(i))
                max=get(i);
        return max;
    }

    public double getGini(){
        int n=size();
        double s0=0,s1=0;
        ArrayList<Double> ary=getSorted(false);
        for(int i=0;i<n;i++){
            s0+=(n-i)*ary.get(i);
            s1+=ary.get(i);
        }
        return 1-2*s0/(n+1)/s1;
    }

    public double getAvg(){
        int s=0;
        for(int i=0;i<size();i++)
            s+=get(i);
        return s/size();
    }

    public int getMaxIndex(){
        int r=0;
        for(int i=0;i<size();i++)
            if(get(r)<get(i))
                r=i;
        return r;
    }

    public int getMinIndex(){
        int r=0;
        for(int i=0;i<size();i++)
            if(get(r)>get(i))
                r=i;
        return r;
    }
}
