package com.mamoru.pstpage;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

//@Component
//public class ServletContainerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
//
//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//
//       Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//       connector.setPort(8080);
//
//       factory.addAdditionalTomcatConnectors(connector);
//    }
//}