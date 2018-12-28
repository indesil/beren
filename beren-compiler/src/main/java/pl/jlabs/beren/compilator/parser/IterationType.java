package pl.jlabs.beren.compilator.parser;

public enum IterationType {
    NONE(""), EACH_MAP_KEY("#forEachKey"), EACH_VALUE("#forEachValue");

    private String operationPrefix;

    IterationType(String operationPrefix) {
        this.operationPrefix = operationPrefix;
    }

    public String getOperationPrefix() {
        return operationPrefix;
    }

    public static IterationType ofPrefix(String prefix) {
        for (IterationType value : values()) {
            if(value.getOperationPrefix().equals(prefix)) {
                return value;
            }
        }

        return null;
    }
}
