package com.example.validateforminput;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PersonForm {

    @NotNull
    @Size(min=2, max=30)
    private String name;

    @AssertTrue
    private boolean working;

    @NotNull
    @Min(value = 18, groups = WebController.class)
    @Min(value = 20, groups = WebController.class)
    private Integer age;

    public String getName() {
        return name;
    }


    public boolean isWorking() {
        return working;
    }

    @Digits(integer=6, fraction=2)
    BigDecimal price;


    @Negative
    int basementFloor;

    public int getBasementFloor() {
        return basementFloor;
    }

    public void setBasementFloor(int basementFloor) {
        this.basementFloor = basementFloor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return "Person(Name: " + this.name + ", Age: " + this.age + ")";
    }

    
}
