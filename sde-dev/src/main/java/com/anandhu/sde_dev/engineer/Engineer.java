package com.anandhu.sde_dev.engineer;

import com.anandhu.sde_dev.common.Gender;
import com.anandhu.sde_dev.task.Task;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Engineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private BigDecimal salary;
    private String techStack;

    @OneToMany(mappedBy = "engineer")
    private List<Task> tasks = new ArrayList<>();


    public Engineer() {
    }
    public Engineer(String name, Integer age, Gender gender, BigDecimal salary, String techStack) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.techStack = techStack;
    }
    public Engineer(String name, String techStack, Gender gender) {
        this.name = name;
        this.techStack = techStack;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return Objects.equals(id, engineer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}