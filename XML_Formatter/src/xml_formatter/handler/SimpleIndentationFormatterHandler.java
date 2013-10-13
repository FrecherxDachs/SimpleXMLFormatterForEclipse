/*******************************************************************************
 * Copyright (c) 2008 Chase Technology Ltd - http://www.chasetechnology.co.uk
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Doug Satchwell (Chase Technology Ltd) - initial API and implementation
 *******************************************************************************/
package xml_formatter.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import xml_formatter.FormatterConfiguration;
import xml_formatter.SimpleIndentationFormatter;


public class SimpleIndentationFormatterHandler extends AbstractHandler {
  
  public Object execute(ExecutionEvent event) throws ExecutionException {
    
    IEditorPart editor = HandlerUtil.getActiveEditor(event);

    ITextEditor textEditor = null;
    if (editor instanceof ITextEditor) {
      textEditor = (ITextEditor) editor;
    } else {
      textEditor = (ITextEditor) editor.getAdapter(ITextEditor.class);
    }
    
    if (textEditor != null) {
      IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
      if (document != null) {
        
        FormatterConfiguration config = new FormatterConfiguration();
        SimpleIndentationFormatter formatter = new SimpleIndentationFormatter(config);
        formatter.format(document);
      }
    }

    return null;
  }
}
