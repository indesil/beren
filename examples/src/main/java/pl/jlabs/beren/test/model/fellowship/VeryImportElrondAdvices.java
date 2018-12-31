package pl.jlabs.beren.test.model.fellowship;

public class VeryImportElrondAdvices {
    private Integer allAdvices;
    private String areVeryImportant;
    private Object weCantMissAny;
    private double evenThisOne;

    public Integer getAllAdvices() {
        return allAdvices;
    }

    public VeryImportElrondAdvices withAllAdvices(Integer allAdvices) {
        this.allAdvices = allAdvices;
        return this;
    }

    public String getAreVeryImportant() {
        return areVeryImportant;
    }

    public VeryImportElrondAdvices withAreVeryImportant(String areVeryImportant) {
        this.areVeryImportant = areVeryImportant;
        return this;
    }

    public Object getWeCantMissAny() {
        return weCantMissAny;
    }

    public VeryImportElrondAdvices withWeCantMissAny(Object weCantMissAny) {
        this.weCantMissAny = weCantMissAny;
        return this;
    }

    public double getEvenThisOne() {
        return evenThisOne;
    }

    public VeryImportElrondAdvices withEvenThisOne(double maybeExceptThisOne) {
        this.evenThisOne = maybeExceptThisOne;
        return this;
    }
}
