package com.ivlieva.task.ui.myQuery;

public class Q2 {
    private String name;
    private String max;

    public Q2(String name, String max) {
        this.name = name;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Q2{" +
                "name='" + name + '\'' +
                ", max='" + max + '\'' +
                '}';
    }
}
