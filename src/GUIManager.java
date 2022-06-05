
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Stack;
import javax.swing.*;
import javax.swing.event.*;

public class GUIManager {
    //Frames
    JFrame fMain;
    JFrame historyFrame;
 
    JTextField tfAddress;
    JPanel setBtnPan,panBtn,pan,pan2;
//    
    JMenuBar menueBar;
    JMenu set;
    JMenuItem setHome;
    JMenuItem setFav;
    
    JEditorPane jepMain;
    JButton btnGo;//setHome,setFav ;
    btnHandler handler;
    
    Stack<String> pre;
    String homeURL="https://www.google.com/" ;
    
    //File Handlers
    FileReadWrite fileHandler,fileHandler2,homeHadnler;
   
    int count;
     
    JButton [] buttons = {new JButton("Previous"),
        new JButton("Next"),
        new JButton("Home"),
        new JButton("Refresh"),
        new JButton("History"),
        new JButton("Favorite"),
        new JButton("Search")};
    
    
    DefaultListModel<String> historyList;

    /** Creates a new instance of GUIManager */
    
    public GUIManager() throws IOException {
        initGui();
    }
    
    private void initGui() throws IOException{
        
        fMain = new JFrame();
        Container c = fMain.getContentPane();
        c.setLayout(new FlowLayout());
        this.pre = new Stack<String>();
        count=0;
        
        
        menueBar= new JMenuBar();
       
        menueBar.add(Box.createHorizontalGlue());
        menueBar.setVisible(true);
        //fMain.add(menueBar);
      
        set= new JMenu("Set");
        
        setHome= new JMenuItem("Set Home");
        setFav= new JMenuItem("Set Favourite");
        
        set.add(setHome);
        set.add(setFav);
        menueBar.add(set);
        menueBar.setAlignmentX(SwingConstants.LEFT);
        
//        set.setMaximumSize(new Dimension(300,2));
        menueBar.setMaximumSize(new Dimension(300,2));
        
        fileHandler= new FileReadWrite("History.data");
        fileHandler2= new FileReadWrite("Favourites.data");
        homeHadnler=new FileReadWrite("Home.data");
        
        // Here I design the Button Pannel
        setBtnPan = new JPanel();
        
        setBtnPan.setLayout(new GridLayout(1,10));
        setBtnPan.add(menueBar);
        fMain.add(setBtnPan);
        
        homeURL=homeHadnler.readHome("Home.data");

        
        
        
        panBtn = new JPanel();    
        panBtn.setLayout(new GridLayout(1, 8));
    
         ImageIcon [] icons = { new ImageIcon("previous_button.jpg"),new ImageIcon("next_button.png"),
             new ImageIcon("home.png"),new ImageIcon("refresh.jpg"),
             new ImageIcon("history.jpg"),new ImageIcon("favorite.png"),new ImageIcon("search.png")};
         
         for (int i = 0; i < buttons.length; i++) {
              Image img = icons[i].getImage() ;  
              Image newimg = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
              icons[i] = new ImageIcon( newimg );
              buttons[i].setIcon(icons[i]);
              buttons[i].setVerticalTextPosition(SwingConstants.BOTTOM);
              buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);
              buttons[i].setBackground(Color.WHITE);
              buttons[i].setBorder(BorderFactory.createLineBorder(Color.white));
             // buttons[i].setSize(new Dimension(50, 50));
              //buttons[i].setBorder();
              //buttons[i].setPreferredSize(new Dimension(500, 250));
        }
      
        for (int i = 0; i < buttons.length; i++) {
            panBtn.add(buttons[i]);
        }

         
        fMain.add(panBtn);
        panBtn.setPreferredSize(new Dimension(700, 100));
       
        pan=new JPanel();
        pan.setLayout(new BorderLayout(5,10));
        
        pan2=new JPanel();
        pan2.setLayout(new BorderLayout());
        
        jepMain = new JEditorPane();
        jepMain.setBorder(BorderFactory.createLineBorder(Color.green));
        
        jepMain.setEditable(false);
        jepMain.setPreferredSize(new Dimension(700,500));
        
        
        jepMain.addHyperlinkListener(new HyperlinkListener()
        {
            public void hyperlinkUpdate(HyperlinkEvent e) 
            {
                if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
                    loadPage(e.getURL().toString());
            }
        });
        
        btnGo = new JButton("Go");
        btnGo.setForeground(Color.white);
        btnGo.setBackground(Color.BLACK);
        
        btnGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String goVal;
                goVal=tfAddress.getText();
                //fileHandler.writeFile(goVal);
                loadPage(goVal);
                count++;
                
                pre.push(tfAddress.getText());
                    if(pre.empty())
                    {
                         buttons[0].setEnabled(false);
                    }
                    else if(count>1)
                    {
                         buttons[0].setEnabled(true);
                    }
            }
        });
        
        tfAddress = new JTextField(58);
        tfAddress.setBorder(BorderFactory.createLineBorder(Color.green));
        tfAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            loadPage(e.getActionCommand());
            }
        });
        
           setHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                homeURL = JOptionPane.showInputDialog(null);
                
                 homeHadnler.writeHome(homeURL,"Home.data");

            }
        });
           
           setFav.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                String str=JOptionPane.showInputDialog(null);

              fileHandler2.writeFile(str,"Favourites.data");
    
                
            }
        });
        
         handler=new btnHandler(this);
         for (int i = 0; i < buttons.length; i++) {
             buttons[i].addActionListener(handler);
         }
       
        
        pan.add(tfAddress,BorderLayout.CENTER);
        pan.add(btnGo,BorderLayout.EAST);
        
        pan2.add(new JScrollPane(jepMain,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
        fMain.add(pan);
        fMain.add(pan2);
        fMain.revalidate();
       
        jepMain.setPage(homeURL);
        tfAddress.setText(homeURL);
   
        fMain.setSize(720,685);
        fMain.setLocation(50,30);
        fMain.setVisible(true);
        fMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void loadPage(String url){
        try{
        
            jepMain.setPage(url);
            fileHandler.writeFile(url);
            tfAddress.setText(url);
            
        }
        catch(IOException ioexp){
            JOptionPane.showMessageDialog(null,"page not found","bad url",JOptionPane.ERROR_MESSAGE);    
        }
    }
}
