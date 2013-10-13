package xml_formatter;

public final class FormatterConfiguration {
  
  public final static int DO_NOT_CHANGE = 0;
  public final static int SINGLE_LINE   = 1;
  public final static int INDENT_ALL    = 2;
  
  public char    indentation_character      = ' ';
  public int     indentation_size           = 2;
  public int     attribute_indentation_size = 2;
  public int     attribute_formatting       = DO_NOT_CHANGE;
  public boolean keep_text_together         = true;
  public boolean keep_empty_lines           = true;
}
