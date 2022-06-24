package br.com.bnck.components;

import br.com.bnck.beans.NameAddress;
import br.com.bnck.processor.InboundMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LegacyFileRoute extends RouteBuilder {

    BeanIODataFormat inboundDataFormat =
            new BeanIODataFormat("InboundMessageBeanIOMapping.xml", "inputMessageStream");

    @Override
    public void configure() throws Exception {

        from("file:src/data/input?fileName=inputFile.csv&noop=true")
                .routeId("legacyFileMoveRouteId")
                .split(body().tokenize("\n", 1, true))
                .unmarshal(inboundDataFormat)
                    .process(new InboundMessageProcessor())
                    .log(LoggingLevel.INFO, "Transformed Body: ${body}")
                    .convertBodyTo(String.class)
                    .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();

    }
}
