package com.anandhu.sde_dev.model;

import com.anandhu.sde_dev.common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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

    private  Engineer(String name, String techStack, Gender gender) {
        this.name = name;
        this.techStack = techStack;
        this.gender = gender;
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
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

    public void updateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;

    }
    public void updateGender(Gender gender){
        if(gender == null){
            throw new IllegalArgumentException("Gender cannot be Null");
        }
        this.gender = gender;
    }
    public void updateSalary(BigDecimal salary){
        if(salary == null || salary.signum() < 0){
            throw new IllegalArgumentException("Salary cannot be negative");
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
        if (this == o) return true;
        if (!(o instanceof Engineer other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}