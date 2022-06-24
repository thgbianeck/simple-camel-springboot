package br.com.bnck.processor;

import br.com.bnck.beans.NameAddress;
import br.com.bnck.beans.OutboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InboundMessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setBody(
                new OutboundNameAddress(
                        nameAddress.getName(),
                        returnOutboundAddress(nameAddress)));
    }

    private String returnOutboundAddress(NameAddress nameAddress) {
        StringBuilder concatenatedAddress = new StringBuilder(200);
        concatenatedAddress.append(nameAddress.getHouseNumber())
                .append(" ").append(nameAddress.getCity())
                .append(", ").append(nameAddress.getProvince())
                .append(" ").append(nameAddress.getPostalCode());

        return concatenatedAddress.toString();
    }
}
