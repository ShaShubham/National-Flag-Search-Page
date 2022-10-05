package com.akqa.aem.training.aem201.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;


@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="cq:Page",
        methods=HttpConstants.METHOD_GET,
        selectors = "search.flag",
        extensions="html")

@ServiceDescription("Search Flag Servlet")
public class SearchFlagServlet extends SlingAllMethodsServlet {

   public static String searchText="";

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        searchText = req.getParameter("countryName");
        searchText+="*";


    }
    public static String sendSearchText(){
      return searchText;

    }

}
