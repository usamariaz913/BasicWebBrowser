
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author usama
 */
public class btnHandler implements ActionListener{
    GUIManager refg;
    Stack<String> next;
    int count;
    DefaultListModel<String> historyList;
    JList<String> list;
    String url;
    FavGUI favGUI;
    


    public btnHandler(GUIManager ref) {
       this.next=new Stack<String>();
        this.refg=ref;
        refg.buttons[0].setEnabled(false);
        refg.buttons[1].setEnabled(false);
        count=0;
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
                if (e.getActionCommand().equals("Home"))
                {
                    loadPage(refg.homeURL);    
                }
                if(e.getActionCommand().equals("Refresh"))
                {
                try {
                    String val=refg.tfAddress.getText();
                  
                    loadPage(val);
                    System.out.println("page reloaded");
                    
                }
                    catch(Exception ioexp){
                    JOptionPane.showMessageDialog(null,"page not found","bad url",JOptionPane.ERROR_MESSAGE); 
                }
            }
                
                if(e.getActionCommand().equals("Previous"))
                {
                   
                        String val=refg.pre.pop();
                        next.push(val);
                        loadPage(refg.pre.peek());
                        refg.fileHandler.writeFile(refg.pre.peek());
                        count++;
                        refg.count--;
                    if(!next.empty())
                    {
                         refg.buttons[1].setEnabled(true);
                    }
                        
                    if(refg.count<=1)
                    {
                         refg.buttons[0].setEnabled(false);
                    }

                }
                
                if(e.getActionCommand().equals("Next"))
                {
                    loadPage(next.peek());
                     refg.fileHandler.writeFile(next.peek());
                    String val=next.pop();
                    refg.pre.push(val);
                    refg.count++;
                    count--;
                    if(count<1)
                    {
                         refg.buttons[1].setEnabled(false);
                    }
                    if(!refg.pre.empty())
                    {
                         refg.buttons[0].setEnabled(true);
                    }                 
                }
                
                if(e.getActionCommand().equals("History"))
                {
                     historyList = new DefaultListModel<String>();
                   

                    historyList = refg.fileHandler.readFile("History.data");
                    list = new JList<String>(historyList);
                    
                    
                  

                    refg.historyFrame = new JFrame("History");
                    refg.historyFrame.add(new JScrollPane(list));

                    refg.historyFrame.setVisible(true);
                    refg.historyFrame.setLocation(200, 200);
                    refg.historyFrame.setSize(500, 700);
                    list.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {

                            String temp = list.getSelectedValue();
                            String[] temp2 = temp.split("-");
                            url = temp2[0];
                            refg.pre.push(url);
                            
                            refg.count++;
                            if(refg.count>1)
                            {
                                refg.buttons[0].setEnabled(true);
                            }
                            refg.fileHandler.writeFile(url);
                            loadPage(url);

                          
                            super.mouseClicked(me); 
                        }
                    
                         });
                }
                if(e.getActionCommand().equals("Favorite"))
                {
                    
                    favGUI = new FavGUI(refg);
                }
                
                
                if(e.getActionCommand().equals("Search"))
                {
                    String searchText, str;
                    searchText = JOptionPane.showInputDialog(null);
                    //System.out.println(searchText);

                    str = refg.jepMain.getText();
                    int count = 0;
                    
                    StringTokenizer myToken = new StringTokenizer(str, " ");

                    int temp;

                    while (myToken.hasMoreTokens()) {
                        temp = myToken.nextToken().indexOf(searchText);
                        if (temp == -1) {

                        } else {

                            count++;
                        }
                    }
                    if(count>0)
                    {
                        JOptionPane.showMessageDialog(null, count,"Word Count",JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Error Word no Found ","Word Count",JOptionPane.ERROR_MESSAGE);
                    }
                }
               
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
        public void loadPage(String url){
            try {

                refg.jepMain.setPage(url);

                refg.tfAddress.setText(url);

            } catch (IOException ioexp) {
                JOptionPane.showMessageDialog(null, "page not found", "bad url", JOptionPane.ERROR_MESSAGE);
            }
    }
}
