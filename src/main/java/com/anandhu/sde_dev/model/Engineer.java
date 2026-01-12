package com.anandhu.sde_dev.model;

import com.anandhu.sde_dev.common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Engineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private Integer age;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
    private BigDecimal salary;
    @NotBlank
    private String techStack;

    @OneToMany(mappedBy = "engineer")
    private List<Task> tasks = new ArrayList<>();


    protected Engineer() {
    }

    public Engineer(String name, String techStack, Gender gender) {
        this.name = name;
        this.techStack = techStack;
        this.gender = gender;
    }
    public Engineer(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public BigDecimal getSalary() {
        return salary;
    }


    public String getTechStack() {
        return techStack;
    }


    public static Engineer create(String name,String techStack, Gender gender){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if(techStack == null || techStack.isBlank()){
            throw new IllegalArgumentException("Teach Stack cannot be blank");
        }
        return new Engineer(name, techStack, gender);
    }

    public void updateProfile(String name, String techStack){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if(techStack == null || techStack.isBlank()){
            throw new IllegalArgumentException("Teach Stack cannot be blank");
        }
        this.name = name;
        this.techStack = techStack;
    }
    public void changeGender(Gender gender){
        if(gender == null){
            throw new IllegalArgumentException("Gender cannt be Null");
        }
        this.gender = gender;
    }
    public void updateSalary(BigDecimal salary){
        if(salary == null || salary.signum() < 0){
            throw new IllegalArgumentException("Salary cannot be Null");
        }
        this.salary = salary;
    }
    public void updateAge(Integer age){
        if(age != null && age < 0){
            throw new IllegalArgumentException("Invalid Age");
        }
        this.age = age;
    }
    public void updateTechStack(String techStack){
        if(techStack == null || techStack.isBlank()){
            throw new IllegalArgumentException("Tech Stack cannot be blank");
        }
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