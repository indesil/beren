package pl.jlabs.beren.test.model;

public class OrdersCreateRequest {
    private String source;
    private short requestId;
    private Orders orders;

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
}
