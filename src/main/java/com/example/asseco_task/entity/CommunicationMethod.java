package com.example.asseco_task.entity;

import com.example.asseco_task.enums.CommunicationMethodEnum;
import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "communication_method")
@Cacheable(value = false)
public class CommunicationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "communication_method_generator")
    @SequenceGenerator(name="communication_method_generator", sequenceName = "communication_method_communication_method_id_seq", allocationSize=1)
    @Column(name = "communication_method_id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CommunicationMethodEnum type;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CommunicationMethodEnum getType() {
        return type;
    }

    public void setType(CommunicationMethodEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunicationMethod that = (CommunicationMethod) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value) && type == that.type && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, type, user);
    }
}
