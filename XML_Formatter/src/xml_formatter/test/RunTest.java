package xml_formatter.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import xml_formatter.FormatterConfiguration;
import xml_formatter.SimpleIndentationFormatter;


public class RunTest {
  
  public static void main(String[] args) {
    
    String content = readFile("src/xml_formatter/test/file.xsl");
    IDocument doc = new Document(content);
    
    FormatterConfiguration config = new FormatterConfiguration();
    
    SimpleIndentationFormatter formatter = new SimpleIndentationFormatter(config);
    
    formatter.format(doc);
    writeFile("src/xml_formatter/test/result.xsl", doc.get());
  }
  
  private static String readFile(String file) {
    
    try {
      
      FileInputStream fis = new FileInputStream(file);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      
      char[] buffer = new char[2048];
      StringBuilder sb = new StringBuilder();
      int readbytes = 0;
      
      while ((readbytes = br.read(buffer)) > 0) {
        sb.append(buffer, 0, readbytes);
      }
      
      br.close();
      
      return sb.toString();
      
    } catch (Exception ex) {
      
      ex.printStackTrace();
    }
    
    return null;
  }
  
  private static void writeFile(String file, String content) {
    
    try {
      
      FileOutputStream fos = new FileOutputStream(file);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      BufferedWriter bw = new BufferedWriter(osw);
      
      bw.write(content);
      bw.close();
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
