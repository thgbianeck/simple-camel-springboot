package br.com.bnck.components;

import br.com.bnck.beans.NameAddress;
import br.com.bnck.processor.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.NamedQuery;

@Component
public class BatchJPAProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:readDB?period=10000")
                .routeId("readDBId")
                .to("jpa:" + NamedQuery.class.getName() + "?namedQuery=fetchAllRows")
                .split(body())
                    .process(new InboundMessageProcessor())
                    .log(LoggingLevel.INFO, "Transformed Body: ${body}")
                    .convertBodyTo(String.class)
                    .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .toD("jpa:" + NameAddress.class.getName() + "?nativeQuery=DELETE FROM NAME_ADDRESS WHERE id = ${header.consumedId}&useExecuteUpdate=true")
                .end()
        ;

    }
}
