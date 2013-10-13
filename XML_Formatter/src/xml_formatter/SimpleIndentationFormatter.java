package xml_formatter;

import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;


public class SimpleIndentationFormatter {
  
  private static final int CONTEXT_DEFAULT       = 0;
  private static final int CONTEXT_ELEMENT       = 1;
  private static final int CONTEXT_ELEMENT_CLOSE = 2;
  private static final int CONTEXT_PI            = 3;
  private static final int CONTEXT_COMMENT       = 4;
  private static final int CONTEXT_CDATA         = 5;
  private static final int CONTEXT_DOCTYPE       = 6;
  private static final int CONTEXT_DTD           = 7;
  private static final int CONTEXT_ATTRIBUTE     = 8;
  
  private static final char EOF = '\0';
  
  private StringBuilder new_doc;
  
  private char[] buffer;
  private int index;
  private int level;
  private int node_counter;
  
  private int last_pos;
  private int last_level;
  private int last_context;
  
  private FormatterConfiguration config;
  
  
  public SimpleIndentationFormatter(FormatterConfiguration configuration) {
    config = configuration;
  }
  
  
  public void format(IDocument document) {
    
    int size = document.get().length();
    buffer = new char[size + 1];
    System.arraycopy(document.get().toCharArray(), 0, buffer, 0, size);
    buffer[size] = EOF;
    level = 0;
    node_counter = 0;
    new_doc = new StringBuilder(buffer.length * 2);
    last_pos = 0;
    char c;
    
    index = -1;
    
    while (buffer[++index] != EOF) {
      
      if (buffer[index] == '<') {
        c = buffer[++index];
        
        if (c == '/') {
          
          level--;
          readContent(CONTEXT_ELEMENT_CLOSE, '>');
          
        } else if (c == '!') {
          
          c = buffer[++index];
          if (c == '-') {
            
            readContent(CONTEXT_COMMENT, '-', '-', '>');
            
          } else if (c == '[') {
            
            readContent(CONTEXT_CDATA, ']', ']', '>');
            
          } else {
            
            while (buffer[++index] != EOF) {
              
              checkAndHandleLinebreaks(CONTEXT_DOCTYPE);
              
              if (buffer[index] == '[') {
                if (readContent(CONTEXT_DTD, ']', ']', '>')) break;
              } else if (buffer[index] == '>') {
                break;
              }
            }
            index++;
          }
          
        } else if (c == '?') {
          
          readContent(CONTEXT_PI, '?', '>');
          
        } else {
          
          while (buffer[++index] != EOF) {
            
            checkAndHandleLinebreaks(CONTEXT_ELEMENT);
            
            if (buffer[index] == '"' || buffer[index] == '\'') {
              
              readContent(CONTEXT_ATTRIBUTE, buffer[index]);
              
            } else if (buffer[index] == '>') {
              
              if (buffer[index - 1] != '/') level++;
              break;
            }
          }
        }
      } else {
        
        readContent(CONTEXT_DEFAULT, '<');
        index--;
      }
      
      if (buffer[index] == EOF) {
        MessageBox msgbox = new MessageBox(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        msgbox.setText("Action failed");
        msgbox.setMessage("The document must not contain xml errors!");
        msgbox.open();
        return;
      }
      
      new_doc.append('\n');
    }
    
    handleLinebreak(false);
    
    document.set(new_doc.toString());
  }
  
  private void checkAndHandleLinebreaks(int context) {
    
    while (buffer[index] == 13 || buffer[index] == 10) {
      if (buffer[index] == 13) {
        if (buffer[index + 1] == 10) {
          index++;
        }
      }
      
      handleLinebreak(true);
      
      while (buffer[++index] != EOF) {
        if (buffer[index] != '\t' && buffer[index] != ' ') {
          break;
        }
      }
      
      if (buffer[index] == EOF) {
        return;
      }
      
      last_pos = index;
      last_context = context;
    }
    
    last_level = level;
  }
  
  private void handleLinebreak(boolean newline) {
    
    if (last_context == CONTEXT_ELEMENT) {
      last_level += config.attribute_indentation_size;
    }
    
    new_doc.append(indent(last_level));
    new_doc.append(buffer, last_pos, index - last_pos);
    
    if (newline) {
      new_doc.append('\n');
    }
  }
  
  private boolean readContent(int context, char... delimeters) {
    
    while (buffer[++index] != EOF) {
      
      checkAndHandleLinebreaks(context);
      
      boolean endFound = checkClose(delimeters);
      if (endFound) {
        return true;
      }
    }
    
    return false;
  }
  
  private boolean checkClose(char[] delimeters) {
    
    for (int i = 0; i < delimeters.length; i++) {
      if (buffer[index - i] != delimeters[delimeters.length - i - 1]) {
        return false;
      }
    }
    
    return true;
  }
  
  private String indent(int level) {
    StringBuilder sb = new StringBuilder(level * config.indentation_size);
    for (int i = 0; i < level; i++) {
      for (int n = 0; n < config.indentation_size; n++) {
        sb.append(config.indentation_character);
      }
    }

    return sb.toString();
  }
}
