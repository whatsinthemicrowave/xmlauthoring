// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RsaFileBeanDefinitionParser extends AbstractSingleBeanDefinitionParser  {
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Class getBeanClass(Element element) {
        return RsaFile.class; 
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
    	
        Map<QName, List<Element>> children = getChildElements(element);

        List<Element> keyElems = children.get(new QName(SecurityNamespaceHandler.NAMESPACE, "PrivateKey"));
        Element privKeyElem = keyElems.get(0);
        bean.addPropertyValue("privateKeyFile", privKeyElem.getTextContent());

        List<Element> certElems = children.get(new QName(SecurityNamespaceHandler.NAMESPACE, "Certificate"));
        Element certificateElem = certElems.get(0);
        bean.addPropertyValue("certificateFile", certificateElem.getTextContent());

    }

    private static Map<QName, List<Element>> getChildElements(Element parent) {
        Map<QName, List<Element>> children = new HashMap<QName, List<Element>>();
        NodeList childNodes = parent.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) childNode;
                QName qname = new QName(e.getNamespaceURI(), e.getLocalName(), e.getPrefix());
                List<Element> elements = children.get(qname);
                if (elements == null) {
                    elements = new ArrayList<Element>();
                    children.put(qname, elements);
                }

                elements.add(e);
            }
        }

        return children;
    }

}
