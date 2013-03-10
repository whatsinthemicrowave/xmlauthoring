// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils {
	
    public static Map<QName, List<Element>> getChildElements(Element parent) {
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
