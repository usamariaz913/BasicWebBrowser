
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class FavGUI {
    JFrame favFrame;
    //FileReadWrite fileHndlr;
    DefaultListModel<String> favouriteList;
    JList list; 
    String url;
    GUIManager refg;
    
    
     public FavGUI(GUIManager obj) {
        refg=obj;
        initGui();
    }
     
    private void initGui(){
        
   
                favouriteList = new DefaultListModel<String>();
                favouriteList=refg.fileHandler2.readFile("Favourites.data");
                list = new JList<String>(favouriteList);
                
                                   
                favFrame= new JFrame("Favourites");

                favFrame.setVisible(true);
                favFrame.setLocation(800,200);
                favFrame.setSize(500, 500);
                favFrame.add( new JScrollPane(list));

                list.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent me) {
                            
                           String temp= (String) list.getSelectedValue();
                          
                            url=temp;
                            refg.pre.push(url);
                            refg.loadPage(url);

                            super.mouseClicked(me); 
                        }
    
                });
}
}
