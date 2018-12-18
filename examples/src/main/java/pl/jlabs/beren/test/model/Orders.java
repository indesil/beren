package pl.jlabs.beren.test.model;

import java.util.Map;
import java.util.Set;

public class Orders {
    private Map<String, Invoice> invoiceMap;
    private Set<Object> additionalData;

    public Map<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public void setInvoiceMap(Map<String, Invoice> invoiceMap) {
        this.invoiceMap = invoiceMap;
    }

    public Set<Object> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Set<Object> additionalData) {
        this.additionalData = additionalData;
    }
}
