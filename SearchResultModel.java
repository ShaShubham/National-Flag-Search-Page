package com.akqa.aem.training.aem201.core.models;

import com.akqa.aem.training.aem201.core.Bean.ImageDataBean;
import com.akqa.aem.training.aem201.core.servlets.SearchFlagServlet;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class SearchResultModel {

    List<ImageDataBean> imageDataList = null;
    @Self
    Resource resource;

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)


    @PostConstruct
    protected void getQueryBuild() {
        ResourceResolver resourceResolver = resource.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);
        String countryName = SearchFlagServlet.sendSearchText();


        Map<String, String> predicate = new HashMap<>();
        predicate.put("path", "/content/dam/aem201");
        predicate.put("type", "dam:Asset");
        predicate.put("nodename", countryName);

        Query query = null;

        try {
            query = builder.createQuery(PredicateGroup.create(predicate), session);

        } catch (Exception e) {

        }
        SearchResult searchResult = query.getResult();
        imageDataList = new ArrayList<ImageDataBean>();

        for (Hit hit : searchResult.getHits()) {

            ImageDataBean imageDataBean = new ImageDataBean();

            String path = null;

            try {
                path = hit.getPath();
                Resource imageResource = resourceResolver.getResource(path);
                Asset image = imageResource.adaptTo(Asset.class);
                String Title = image.getName();

                imageDataBean.setPath(path);
                imageDataBean.setTitle(Title);
                imageDataList.add(imageDataBean);

            } catch (RepositoryException e) {

            }

        }

    }

    public List<ImageDataBean> getImageDataList() {
        return imageDataList;
    }
}