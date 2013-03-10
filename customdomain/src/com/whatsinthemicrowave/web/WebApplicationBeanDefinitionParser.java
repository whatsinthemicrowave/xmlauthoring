// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.web;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.w3c.dom.Element;
import com.whatsinthemicrowave.security.SecurityNamespaceHandler;
import static com.whatsinthemicrowave.utils.Utils.getChildElements;

public class WebApplicationBeanDefinitionParser extends AbstractSingleBeanDefinitionParser  {
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getBeanClass(Element element) {
        return WebApplication.class; 
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder bean) {
    	
        Map<QName, List<Element>> children = getChildElements(element);

        List<Element> elems = children.get(new QName(SecurityNamespaceHandler.NAMESPACE, "RsaCredential"));
        Element rsaElem = elems.get(0);
        createBeanDefinitionFromElement(rsaElem, parserContext);
    }

    protected boolean shouldGenerateId() {
        return true;
    }

    private static BeanDefinition createBeanDefinitionFromElement(Element element, ParserContext parserContext) {
        BeanDefinitionParserDelegate delegate = parserContext.getDelegate();
        XmlReaderContext readerContext = parserContext.getReaderContext();
        String namespaceUri = element.getNamespaceURI();
        
        NamespaceHandler handler = readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
        return handler.parse(element, new ParserContext(readerContext, delegate));
    }
}
