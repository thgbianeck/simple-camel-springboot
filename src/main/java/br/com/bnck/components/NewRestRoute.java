package br.com.bnck.components;

import br.com.bnck.beans.NameAddress;
import br.com.bnck.processor.InboundMessageProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.net.ConnectException;

@Component
public class NewRestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(JMSException.class, ConnectException.class)
                .routeId("jmsExceptionRouteId")
                .handled(true)
                .log(LoggingLevel.INFO, "JMS Exception has occurred; handling gracefully");

        restConfiguration()
                .component("jetty")
                .host("0.0.0.0")
                .port(8080)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true);

        rest("masterclass")
                .produces("application/json")
                .post("nameAddress")
                .type(NameAddress.class)
                .to("direct:post-nameaddress");


        from("direct:post-nameaddress")
                .routeId("newRestRouteId")
                .log(LoggingLevel.INFO, "${body}")
//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO, "Transformed Body: ${body}")
//                .convertBodyTo(String.class)
//                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");
                .log(LoggingLevel.INFO, ">>> Sending to DB EndPoint")
                .to("direct:toDB")
                .log(LoggingLevel.INFO, ">>> Sending to AMQ EndPoint")
                .to("direct:toActiveMQ")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform().simple("Message Processed and Result Generated with Body: ${body}")
                .end();
        ;

        from("direct:toDB")
                .routeId("toDBId")
                .log(LoggingLevel.INFO, ">>> In DB EndPoint")
                .to("jpa:" + NameAddress.class.getName())
        ;

        from("direct:toActiveMQ")
                .routeId("toActiveMQId")
                .log(LoggingLevel.INFO, ">>> In AMQ EndPoint")
                .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly")
        ;
    }
}
