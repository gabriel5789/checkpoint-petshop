package br.com.fiap.petshot.model;

public class UnidadeMedida {
    private Integer id;
    private String desc;

    public UnidadeMedida() {

    }

    public UnidadeMedida(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "UnidadeMedida{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }
}
