package com.semo.wonda.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
public class BasicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "delete_date")
    private Date deleteDate;
    @Column(name = "deleted")
    private Boolean deleted = false;
    @PrePersist
    protected void onCreate() {
        updateDate = new Date();
        createDate = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        if(deleted){
            deleteDate = new Date();
        }else{
            updateDate = new Date();
        }
    }
}
