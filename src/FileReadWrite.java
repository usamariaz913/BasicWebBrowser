import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class FileReadWrite {
    File f;
    FileWriter fw;
    Date date= new Date();


    public FileReadWrite(String fileName) {
          f = new File(fileName);
    }


    DefaultListModel<String> readFile(String fileName)
    {
        DefaultListModel<String> historyList = new DefaultListModel<String>();
        try {
            File f= new  File(fileName);
            FileReader fr= new FileReader(f);
            BufferedReader br= new BufferedReader(fr);
            String line= br.readLine();
            
            while(line!=null)
            {
                line=br.readLine();
                historyList.add(0,line);
                
            }

            
            br.close();
            fr.close();
            
             
                    
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
                    return historyList;

    }
    
    void writeFile(String line)
    {
        try {
              
                fw = new FileWriter(f, true);
                PrintWriter pw = new PrintWriter(fw);
              
                pw.println(line + "- " + date);
                pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    void writeFile(String line,String fileName)
    {
        try {
            File f = new File(fileName);

            FileWriter fw = new FileWriter(f, true);  
            PrintWriter pw = new PrintWriter(fw);
            pw.println(line);
            pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
      void writeHome(String line,String fileName)
    {
        try {
            File f = new File(fileName);

            FileWriter fw = new FileWriter(f);  
            PrintWriter pw = new PrintWriter(fw);
            pw.println(line);
            pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    String readHome(String fileName)
    {
        String line=new String();
        try {
            File f= new  File(fileName);
            
            FileReader fr= new FileReader(f);
            BufferedReader br= new BufferedReader(fr);
            line= br.readLine();

            br.close();
            fr.close();
            
             
                    
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
         return line;

    }
    void deleteFile()
    {
        File f= new File("MyFile.data");
        f.delete();
    }
    
}
