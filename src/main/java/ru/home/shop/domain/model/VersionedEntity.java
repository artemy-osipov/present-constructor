package ru.home.shop.domain.model;

public class VersionedEntity extends Entity {

    /*Идентификатор версии*/
    private Integer vid;
    /*Является ли версия последней*/
    private Boolean last;
    /*Является ли сущность действующей*/
    private Boolean active;

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
