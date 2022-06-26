package br.com.bnck.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiverRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("activemq:queue:nameaddressqueue")
                .routeId("queuereceiverId")
                .log(LoggingLevel.INFO, this.log, " >>>> Message Received fro Queue: ${body}");
    }
}
