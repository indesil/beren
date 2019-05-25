package io.github.indesil.beren.test.features.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectFeaturesModel extends FeaturesTestModel<ObjectFeaturesModel> {
    private String string;
    private Object object;
    private Integer integerWrapper;
    private Long longWrapper;
    private Double doubleWrapper;
    private List<Object> list;
    private Set<Object> set;
    private Map<Object, Object> map;

    public String getString() {
        return string;
    }

    public Object getObject() {
        return object;
    }

    public List<Object> getList() {
        return list;
    }

    public Set<Object> getSet() {
        return set;
    }

    public Map<Object, Object> getMap() {
        return map;
    }

    public Integer getIntegerWrapper() {
        return integerWrapper;
    }

    public Long getLongWrapper() {
        return longWrapper;
    }

    public Double getDoubleWrapper() {
        return doubleWrapper;
    }

    public ObjectFeaturesModel setString(String string) {
        this.string = string;
        setLastModifiedValueDescription("string", string);
        return this;
    }

    public ObjectFeaturesModel setObject(Object object) {
        this.object = object;
        setLastModifiedValueDescription("object", object);
        return this;
    }

    public ObjectFeaturesModel setList(List<Object> list) {
        this.list = list;
        setLastModifiedValueDescription("list", list);
        return this;
    }

    public ObjectFeaturesModel setSet(Set<Object> set) {
        this.set = set;
        setLastModifiedValueDescription("set", set);
        return this;
    }

    public ObjectFeaturesModel setMap(Map<Object, Object> map) {
        this.map = map;
        setLastModifiedValueDescription("map", map);
        return this;
    }

    public ObjectFeaturesModel setIntegerWrapper(Integer integerWrapper) {
        this.integerWrapper = integerWrapper;
        setLastModifiedValueDescription("integerWrapper", integerWrapper);
        return this;
    }

    public ObjectFeaturesModel setLongWrapper(Long longWrapper) {
        this.longWrapper = longWrapper;
        setLastModifiedValueDescription("longWrapper", longWrapper);
        return this;
    }

    public ObjectFeaturesModel setDoubleWrapper(Double doubleWrapper) {
        this.doubleWrapper = doubleWrapper;
        setLastModifiedValueDescription("doubleWrapper", doubleWrapper);
        return this;
    }
}
