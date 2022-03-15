package com.jobgate.demo.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jobgate.demo.Models.CustomListSerializer;
import com.jobgate.demo.Models.SubCategory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt","updatedAt"},allowGetters = true)
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name="Photo", columnDefinition = "avatar.png")
    private String photo;
    @OneToMany(targetEntity =SubCategory.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    @JsonSerialize(using = CustomListSerializer.class)
    private List<SubCategory>  subCategories;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}