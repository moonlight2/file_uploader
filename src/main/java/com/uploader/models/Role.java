package com.uploader.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "roleValue", length = 128, nullable = true)
    private String roleValue;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Account acc;

    public Role(String roleValue, Account acc) {
        this.roleValue = roleValue;
        this.acc = acc;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleValue() {
        return roleValue;
    }

	public Role setRoleValue(String roleValue) {
        this.roleValue = roleValue;
		return this;
    }
		
    public Role() {
    }
}
