package es.udc.rs.deliveries.client.service.rest.DtosClient;

import java.net.URI;
import java.util.List;

public class ShipmentListInterval {
    private List<ClientShipmentDto> shipments;
    private URI nextIntervalUri;
    private URI previousIntervalUri;

    public ShipmentListInterval(List<ClientShipmentDto> shipments, URI nextIntervalUri, URI previousIntervalUri) {
        this.shipments = shipments;
        this.nextIntervalUri = nextIntervalUri;
        this.previousIntervalUri = previousIntervalUri;
    }

    public List<ClientShipmentDto> getshipments() {
        return shipments;
    }

    public void setshipments(List<ClientShipmentDto> shipments) {
        this.shipments = shipments;
    }

    public URI getNextIntervalUri() {
        return nextIntervalUri;
    }

    public void setNextIntervalUri(URI nextIntervalUri) {
        this.nextIntervalUri = nextIntervalUri;
    }

    public URI getPreviousIntervalUri() {
        return previousIntervalUri;
    }

    public void setPreviousIntervalUri(URI previousIntervalUri) {
        this.previousIntervalUri = previousIntervalUri;
    }

    @Override
    public String toString() {
        return "ShipmentListInterval{" +
                "\nshipments=" + shipments +
                ",\n nextIntervalUri=" + nextIntervalUri +
                ",\n previousIntervalUri=" + previousIntervalUri +
                '}';
    }
}
