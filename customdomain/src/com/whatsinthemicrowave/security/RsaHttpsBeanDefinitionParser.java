// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.security;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import static com.whatsinthemicrowave.utils.Utils.getChildElements;

public class RsaHttpsBeanDefinitionParser extends AbstractSingleBeanDefinitionParser  {
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getBeanClass(Element element) {
        return RsaHttps.class; 
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
    	
        Map<QName, List<Element>> children = getChildElements(element);

        List<Element> keyElems = children.get(new QName(SecurityNamespaceHandler.NAMESPACE, "PrivateKey"));
        Element privKeyElem = keyElems.get(0);
        bean.addPropertyValue("privateKeyURL", privKeyElem.getTextContent());

        List<Element> certElems = children.get(new QName(SecurityNamespaceHandler.NAMESPACE, "Certificate"));
        Element certificateElem = certElems.get(0);
        bean.addPropertyValue("certificateURL", certificateElem.getTextContent());

    }

}
