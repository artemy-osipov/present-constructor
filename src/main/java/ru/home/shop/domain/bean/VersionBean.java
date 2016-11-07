package ru.home.shop.domain.bean;

public class VersionBean {

    /*Идентификатор сущности*/
    private Integer id;
    /*Идентификатор версии*/
    private Integer vid;
    /*Является ли версия последней*/
    private Boolean last;
    /*Является ли сущность действующей*/
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
