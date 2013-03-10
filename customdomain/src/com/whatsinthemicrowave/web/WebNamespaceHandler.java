package com.whatsinthemicrowave.web;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class WebNamespaceHandler extends NamespaceHandlerSupport {

    public static final String NAMESPACE = "http://whatsinthemicrowave.com/domain";
    
	@Override
	public void init() {
        registerBeanDefinitionParser("WebApp", new WebApplicationBeanDefinitionParser());        
	}

}
