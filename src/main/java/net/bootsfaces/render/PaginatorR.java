/**
 *  Copyright 2014 Riccardo Massera (TheCoder4.Eu)
 *  
 *  This file is part of BootsFaces.
 *  
 *  BootsFaces is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BootsFaces is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with BootsFaces. If not, see <http://www.gnu.org/licenses/>.
 */

package net.bootsfaces.render;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import net.bootsfaces.component.PaginableData;

/**
 *
 * @author thecoder4.eu
 */
public class PaginatorR extends Renderer {
    
    @Override
    public void encodeBegin(FacesContext context, UIComponent c) throws IOException {
        /*
         * <div style="margin-bottom: 15px;" class="navbar navbar-static-top">
         *  <div class="navbar-inner">
         *    <div class="pull-left" style="width:25%;"><a class="btn btn-small">&lt;</a></div>
         *    <div class="pull-left" style="width:50%; text-align: center;">(1 of 10)</div>
         *    <div class="pull-left" style="width:25%;"><a class="btn btn-small pull-right">&gt;</a></div>
         *   </div>
         * </div>
         */

        Map<String, Object> attrs = c.getAttributes();
        String cid = c.getClientId(context);
        
        boolean panel=A.toBool(attrs.get(A.PANEL),true);
        String show = A.asString(attrs.get(A.SHOWON),A.BOTH);

        ResponseWriter rw = context.getResponseWriter();
        rw.startElement("div", c); //External Div

        rw.writeAttribute("id", cid, null);
        if(panel) { rw.writeAttribute("class", "thumbnail", "class"); }
        
        if(show.equals("top")||show.equals(A.BOTH)) {
           encodePaginator(context, c, "top");
        }
    }
    
    private void encodePaginator(FacesContext context, UIComponent c, String show) throws IOException {
        PaginableData data = findPaginableData(c);
        
        if (data != null) {
            String cid = c.getClientId(context);
            ResponseWriter rw = context.getResponseWriter();
            
            rw.startElement("div", null); //Navbar
            rw.writeAttribute("class", "navbar navbar-static-"+show, "class");
            rw.startElement("div", null); //Navbar inner
            rw.writeAttribute("class", "navbar-inner", "class");
            rw.startElement("div", null); //Left link
            rw.writeAttribute("class", "pull-left", "class");
            rw.writeAttribute(H.STYLE, "width:25%;", H.STYLE);
            rw.startElement("a", c);
            rw.writeAttribute("href", "#", null);
            rw.writeAttribute("class", "btn btn-small", "class");
            rw.writeAttribute("onclick", "return BsF.ajax.paginate(this,null,'<','" + cid + "','" + cid + " " + data.getClientId() + "');", null);
            rw.writeText("<", null);
            rw.endElement("a");
            rw.endElement("div"); //Left link

            rw.startElement("div", null); //Pages
            rw.writeAttribute("class", "pull-left", "class");
            rw.writeAttribute(H.STYLE, "width:50%; text-align: center;", H.STYLE);
            rw.writeText("(" + data.getCurrentPage() + " of " + data.getPages() + ")", null);
            rw.endElement("div"); //Pages

            rw.startElement("div", null); //Right link
            rw.writeAttribute("class", "pull-left", "class");
            rw.writeAttribute(H.STYLE, "width:25%;", H.STYLE);
            rw.startElement("a", c);
            rw.writeAttribute("href", "#", null);
            rw.writeAttribute("class", "btn btn-small pull-right", "class");
            rw.writeAttribute("onclick", "return BsF.ajax.paginate(this,null,'>','" + cid + "','" + cid + " " + data.getClientId() + "');", null);
            rw.writeText(">", null);
            rw.endElement("a");
            rw.endElement("div"); //Right link

            rw.endElement("div"); //Navbar inner

            rw.endElement("div"); //Navbar
        }
        //encodeInputHidden(rw, c);
    }
    
    /*private void encodeInputHidden(ResponseWriter rw, UIComponent c) throws IOException {
        String id = c.getClientId();
        rw.startElement(H.INPUT, c);
        rw.writeAttribute(H.TYPE, H.HIDDEN, null);
        rw.writeAttribute(H.ID, id, null);
        rw.writeAttribute(H.NAME, id, null);
        rw.endElement(H.INPUT);
    }*/
    
    @Override
    public void encodeEnd(FacesContext context, UIComponent c) throws IOException {
        String show = A.asString(c.getAttributes().get(A.SHOWON),A.BOTH);
        ResponseWriter rw = context.getResponseWriter();
        if(show.equals("bottom")||show.equals(A.BOTH)) {
           encodePaginator(context, c, "bottom");
        }
        rw.endElement("div"); //External Div
    }
    /*private void writeLink(ResponseWriter rw, UIComponent c, String value, String formId) throws IOException {
      //rw.writeText(" ", null);
      rw.startElement(H.A, c);
      rw.writeAttribute(H.HREF, C.HASH, null);
      rw.writeAttribute(H.CLASS, "btn btn-small", H.CLASS);
      //writer.writeAttribute("onclick", onclickCode(formId, id, value), null);
      rw.writeText(value, null);
      rw.endElement(H.A);
   }*/
    
    @Override
    public void decode(FacesContext context, UIComponent c) {
        String id = c.getClientId(context);
        Map<String, String> parameters = context.getExternalContext().getRequestParameterMap();
        String response = (String) parameters.get(id);
        if (response == null || response.equals("")) {
            return;
        }
        
        int skipstep = A.toInt(c.getAttributes().get("skipStep"));
        PaginableData data = findPaginableData(c);
        
        if (data != null) {
            int first = data.getFirst();
            int itemcount = data.getRowCount();
            int pagesize = data.getRowsPerPage(); //data.getRows();
            if (pagesize <= 0) {
                pagesize = itemcount;
            }
            
            if (response.equals("<")) {
                first -= pagesize;
            } else if (response.equals(">")) {
                if (first + pagesize <= itemcount) {
                    first += pagesize;
                }
            } else if (response.equals("<<")) {
                first -= pagesize * skipstep;
            } else if (response.equals(">>")) {
                first += pagesize * skipstep;
            } /* else {
                int page = Integer.parseInt(response);
                first = (page - 1) * pagesize;
            } */
            
            if (first < 0) {
                first = 0;
            }
            data.setFirst(first);
        }
    }

    private PaginableData findPaginableData(UIComponent c) {
        String forId = A.asString(c.getAttributes().get("for"));
        PaginableData data=null;
        if (forId != null) { //If the user supplies the Id, get the component by Id
            data = (PaginableData) c.findComponent(forId);
        } else { // otherwise, it must be a Child
            for(UIComponent child : c.getChildren()) {
                if(child instanceof Paginable) { data=(PaginableData) child; }
            }
        }
        return data;
    }
    
    /*@Override
    public void encodeChildren(FacesContext context, UIComponent c) throws IOException {
        //Do nothing
        //R.renderChildren(context, c);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }*/

}
