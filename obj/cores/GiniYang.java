package obj.cores;
import obj.funcs.Rand;
import obj.cores.Gini;
import java.util.ArrayList;

public class GiniYang extends Gini{
    private static final long serialVersionUID=1L;
    private long steps=0;
//Constructors
    public GiniYang(){
        super();
    }

    public GiniYang(int size,double money){
        super(size,money);
    }

    public GiniYang(ArrayList<Double> source){
        super(source);
    }
    @Override
    public void init(int size,double money){
        super.init(size,money);
        steps=0;
    }
//Methods-next
    protected void next(double coe,
        int choiceNum,boolean isToPoor){
        if(choiceNum<1)
            return;
        Rand rnd=new Rand();
        int c0,c1;
        for(int i=0;i<size();i++){
            if(get(i)==1)
                continue;
            set(i,get(i)-1);
            c0=isToPoor?getMaxIndex():getMinIndex();
            for(int j=0;j<choiceNum;j++){
                //The chosen ones may repeat here
                c1=rnd.getInt(0,size()-1);
                if(isToPoor?(get(c1)<get(c0)):(get(c1)>get(c0)))
                    c0=c1;
            }
            set(c0,get(c0)+coe);
        }
        try{
            steps++;
        }catch(Exception e){
            System.out.println("Error(Ignored): "
                +"GiniYang-steps is too long");
        }
    }
    //Mind that it's based on next(double,int,boolean)
    public void next(int steps,double coe,
        int choiceNum,boolean isToPoor){
        for(;steps>0;steps--)
            next(coe,choiceNum,isToPoor);
    }
//Methods-gets
    public final long getSteps(){
        return steps;
    }
}
