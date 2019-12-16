package com.ivlieva.task.ui.myQuery;

public class Q1 {
    private String name;
    private String sum;

    public Q1(String name, String sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Q1{" +
                "name='" + name + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }
}
