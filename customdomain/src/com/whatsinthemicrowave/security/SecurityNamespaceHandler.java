// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.security;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class SecurityNamespaceHandler extends NamespaceHandlerSupport {

    public static final String NAMESPACE = "http://whatsinthemicrowave.com/security";
    
	@Override
	public void init() {
        registerBeanDefinitionParser("RsaCredential", new RsaHttpsBeanDefinitionParser());        
	}

}
