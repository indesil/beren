package pl.jlabs.beren.test.model;

import java.util.HashMap;
import java.util.Set;

public class Orders {
    private HashMap<String, Invoice> invoiceMap;
    private Set<Object> additionalData;

    public HashMap<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public void setInvoiceMap(HashMap<String, Invoice> invoiceMap) {
        this.invoiceMap = invoiceMap;
    }

    public Set<Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Set<Object> additionalData) {
        this.additionalData = additionalData;
    }
}
