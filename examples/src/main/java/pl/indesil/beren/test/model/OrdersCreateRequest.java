package pl.indesil.beren.test.model;

import java.util.List;

public class OrdersCreateRequest {
    private String source;
    private short requestId;
    private Orders orders;
    private List<Address> addresses;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public short getRequestId() {
        return requestId;
    }

    public void setRequestId(short requestId) {
        this.requestId = requestId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public OrdersCreateRequest setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }
}
