package ru.home.shop.domain.bean;

public class Report {

    private String name;
    private byte[] content;

    public Report(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }
}
