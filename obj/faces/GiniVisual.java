package obj.faces;
import obj.funcs.*;
import obj.cores.GiniYang;
import obj.faces.JSet;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public final class GiniVisual extends GiniYang{
    private static final long serialVersionUID=1L;
    private JFrame frm=UI.newFrm("GiniYang",800,600);
    private Thread thread;
    private boolean[][] record;

    private JPanel pnl=new JPanel();
    private JLabel[] lbl;
    public Color colorBack=new Color(33,37,43);
    public Color colorFront=new Color(157,165,180);

    private JPanel[] pnlFrm=new JPanel[3];
    private JComboBox<String> cbbPset;
    private JSet[] setArgs=new JSet[4],
        setRun=new JSet[2],
        setData=new JSet[8];
    private JCheckBox ckbSBS=new JCheckBox("Step by step",true),
        ckbSrt=new JCheckBox("Sort Result",true),
        ckbHlt=new JCheckBox("Highlight",false);
    private JButton btnCtrl=new JButton("Start"),
        btnHelp=new JButton("Help the poor"),
        btnRset=new JButton("Set coefficient");
//Constructors
    public GiniVisual(){
        super(3,5);
        initAll();
    }
//Methods-Main
    public void setVisible(boolean isVisible){
        frm.setVisible(isVisible);
    }
    @Override
    public void next(int steps,double coe,
        int choiceNum,boolean isToPoor){
        super.next(steps,coe,choiceNum,isToPoor);
        record[0][getMinIndex()]=true;
        record[1][getMaxIndex()]=true;
        setSetData();
        refreshPanel();
    }

    private void refreshPanel(boolean isSorted){
        if(lbl.length!=size())
            initPnlGram();
        double max=getAvg()*9>getMax()?getAvg()*9:getMax();
        int width=pnl.getWidth()/size(),
            space=(pnl.getWidth()-width*size())/2;
        ArrayList<Double> ary=isSorted
            ?getSorted(true):getACopy();
        int index=getIndex(0.2);
        for(int i=0;i<size();i++){
            lbl[i].setBounds(width*i+space,
                (int)(pnl.getHeight()*(1-ary.get(i)/max)),
                width,
                (int)(pnl.getHeight()*ary.get(i)/max));
            lbl[i].setBackground((ckbHlt.isSelected()&&i<=index)?
                colorFront.darker():colorFront);
        }
    }

    private void refreshPanel(){
        refreshPanel(ckbSrt.isSelected());
    }
//Methods-Inits
    private void initPnlGram(){
        initPnlGram(ckbHlt.isSelected());
    }

    private void initPnlGram(boolean isHighlighted){
        try{
            for(int i=0;i<lbl.length;i++){
                lbl[i].setVisible(false);
                pnl.remove(lbl[i]);
                lbl[i]=null;
            }
        }catch(Exception e){
            System.out.println("Error(Ignored): "
                +"Panel:Gram-Lbls haven't been"
                +" prepared properly");
        }
        lbl=new JLabel[size()];
        pnl.setLayout(null);
        for(int i=0;i<size();i++){
            lbl[i]=new JLabel();
            lbl[i].setOpaque(true);
            lbl[i].setBackground(colorFront);
            pnl.add(lbl[i]);
            lbl[i].setVisible(true);
        }
        pnl.setBackground(colorBack);
    }

    public void initAll(){
        initRecord();
        initCbb();
        initBtn();
        initCkb();
        initJSet();
        initPanel();
        initFrm();
        cbbPset.setSelectedIndex(0);
        setSetData();
    }

    private void initRecord(){
        record=new boolean[2][size()];
        for(int i=0;i<record.length;i++)
            for(int j=0;j<record[0].length;j++)
                record[i][j]=false;
    }

    private void initCbb(){
        cbbPset=new JComboBox<String>();
        for(int i=0;i<3;i++)
            cbbPset.addItem("Preset "+(i+1));
        cbbPset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                switch(cbbPset.getSelectedIndex()){
                case 0:
                    setAllArgs(3.0,5.0,1.0,1.0);
                break;
                case 1:
                    setAllArgs(10.0,10.0,1.0,1.0);
                break;
                case 2:
                    setAllArgs(100.0,100.0,1.0,1.0);
                break;
                }
            }
        });
    }

    private void initBtn(){
        btnRset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!chkArgs())
                    return;
                init((int)Double.parseDouble(
                    setArgs[0].getText()),
                    (int)Double.parseDouble(
                    setArgs[1].getText()));
                initRecord();
                refreshPanel();
                setSetData();
            }
        });
        btnHelp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                double avg=getAvg();
                set(getMaxIndex(),getMax()-avg);
                set(getMinIndex(),get(getMinIndex())+avg);
                refreshPanel();
                setSetData();
            }
        });
        btnCtrl.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(setArgs[0].getText().equals("Edi&Henry"))
                    setColor();
                if((!btnCtrl.isEnabled())||(!chkArgs()))
                    return;
                if(btnCtrl.getText().equals("Start")){
                    if(ckbSBS.isSelected()){
                        threadStart();
                        setEnabled(false);
                        btnCtrl.setText("Stop");
                    }else
                        next((int)Double.parseDouble(
                            setRun[0].getText()),
                            Double.parseDouble(
                            setArgs[2].getText()),
                            (int)Double.parseDouble(
                            setArgs[3].getText()),
                            true);
                }else{
                    threadStop();
                    setEnabled(true);
                    btnCtrl.setText("Start");
                }
            }
        });
    }

    private void initCkb(){
        ckbSrt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ckbHlt.setSelected(ckbSrt.isSelected());
                refreshPanel();
            }
        });
        ckbHlt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!ckbSrt.isSelected())
                    ckbHlt.setSelected(false);
            }
        });
    }

    private void initJSet(){
        setArgs[0]=new JSet("People: ");
        setArgs[1]=new JSet("Coins : ");
        setArgs[2]=new JSet("ExRate: ");
        setArgs[3]=new JSet("Check : ");
        setRun[0]=new JSet("Steps: ");
        setRun[1]=new JSet("Delay: ");
        setData[0]=new JSet("Steps :",false);
        setData[1]=new JSet(" Max  : ",false);
        setData[2]=new JSet("Median: ",false);
        setData[3]=new JSet(" Min  : ",false);
        setData[4]=new JSet(" Avg  : ",false);
        setData[5]=new JSet(" Gini : ",false);
        setData[6]=new JSet(" UTBP : ",false);
        setData[7]=new JSet(" UTBR : ",false);
    }

    private void initPanel(){
        pnl=new JPanel();
        initPnlGram();
        for(int i=0;i<pnlFrm.length;i++)
            pnlFrm[i]=new JPanel(new GridBagLayout());
        pnlFrm[0].add(cbbPset,new GBC(0,0,1,1,0,0));
        for(int i=0;i<setArgs.length;i++)
            pnlFrm[0].add(setArgs[i],new GBC(0,i+1,1,1,0,0));
        pnlFrm[0].add(btnRset,new GBC(0,
            setArgs.length+1,1,1,0,0));
        pnlFrm[1].add(ckbSrt,new GBC(0,0,1,1,0,0));
        for(int i=0;i<setRun.length;i++)
            pnlFrm[1].add(setRun[i],new GBC(0,i+1,1,1,0,0));
        pnlFrm[1].add(ckbSBS,new GBC(0,setRun.length+1,1,1,0,0));
        pnlFrm[1].add(ckbHlt,new GBC(0,setRun.length+2,1,1,0,0));
        pnlFrm[1].add(btnCtrl,new GBC(0,setRun.length+3,1,1,0,0));
        pnlFrm[1].add(btnHelp,new GBC(0,setRun.length+4,1,1,0,0));
        for(int i=0;i<setData.length;i++)
            pnlFrm[2].add(setData[i],new GBC(0,i,1,1,1,0));
    }

    private void initFrm(){
        frm.add(pnl,new GBC(0,0,1,pnlFrm.length,1,1));
        for(int i=0;i<pnlFrm.length;i++)
            frm.add(pnlFrm[i],new GBC(1,i,1,1,0,1));
    }
//Methods-Thread
    private void setThread(int msDelay,int steps,
        double coe,int choiceNum,boolean isToPoor){
        thread=new Thread(new Runnable(){
            public void run(){
                while(true)
                    try{
                        Thread.sleep(msDelay);
                        next(steps,coe,choiceNum,isToPoor);
                    }catch(Exception e){
                        break;
                    }
            }
        });
    }

    private boolean threadStart(){
        if(!chkArgs())
            return false;
        setThread((int)Double.parseDouble(setRun[1].getText()),
            (int)Double.parseDouble(setRun[0].getText()),
            Double.valueOf(setArgs[2].getText()),
            (int)Double.parseDouble(setArgs[3].getText()),
            true);
        try{
            thread.start();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void threadStop(){
        thread.interrupt();
    }
//Methods-Sets
    private void setSetData(){
        ArrayList<Double> sorted=getSorted(true);
        int midIndex=size()/2;
        int minNum=0,maxNum=0;
        for(int i=0;i<size();i++){
            if(record[0][i])
                minNum++;
            if(record[1][i])
                maxNum++;
        }
        setData[0].setText(""+getSteps());
        setData[1].setText(""+sorted.get(0));
        setData[2].setText(""+((size()%2==1)
            ?(sorted.get(midIndex)):((sorted.get(midIndex)
            +sorted.get(midIndex-1))/2)));
        setData[3].setText(""+sorted.get(size()-1));
        setData[4].setText(""+getAvg());
        setData[5].setText(String.format("%1.3f",getGini()));
        setData[6].setText(minNum+"("+
            (int)(minNum*100/size())+"%)");
        setData[7].setText(maxNum+"("+
            (int)(maxNum*100/size())+"%)");
    }

    private void setAllArgs(double...args){
        if(setRun[0].getText().equals(""))
            setRun[0].setText("1000");
        if(setRun[1].getText().equals(""))
            setRun[1].setText("10");
        if(args.length==setArgs.length)
            for(int i=0;i<args.length;i++)
                setArgs[i].setText(args[i]+"");
    }

    private void setEnabled(boolean isEnabled){
        cbbPset.setEnabled(isEnabled);
        for(int i=0;i<setArgs.length;i++)
            setArgs[i].setEditable(isEnabled);
        btnRset.setEnabled(isEnabled);
        for(int i=0;i<setRun.length;i++)
            setRun[i].setEditable(isEnabled);
        ckbSBS.setEnabled(isEnabled);
        btnHelp.setEnabled(isEnabled);
    }
//Methods-Nothing important
    private boolean chkArgs(){
        if(!(chkArgs(setArgs)&&chkArgs(setRun)))
            return false;
        double people=Double.valueOf(setArgs[0].getText()),
            coins=Double.valueOf(setArgs[1].getText()),
            exRate=Double.valueOf(setArgs[2].getText()),
            check=Double.valueOf(setArgs[3].getText()),
            steps=Double.valueOf(setRun[0].getText()),
            delay=Double.valueOf(setRun[1].getText());
        if(people!=(int)people||check!=(int)check
            ||steps!=(int)steps||delay!=(int)delay)
            return false;
        if(people<1||people>500||coins<1||coins>500
            ||exRate<0||exRate>2||check<1||check>=size()
            ||steps<1||steps>10000||delay<9||delay>306)
            return false;
        return true;
    }

    private boolean chkArgs(final JSet[] s){
        for(int i=0;i<s.length;i++)
            if(!isNum(s[i].getText()))
                return false;
        return true;
    }

    public void setColor(){
        Rand rnd=new Rand();
        colorBack=rnd.getColor();
        colorFront=rnd.getColor();
        initPnlGram();
        refreshPanel();
    }

    private boolean isNum(String str){
        return str.matches("-?[0-9]+.?[0-9]*");
    }
}
