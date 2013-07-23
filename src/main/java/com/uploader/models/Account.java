
package com.uploader.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "account")
public class Account implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "firstName", length = 128, nullable = true)
    private String firstName;
    
    @Column(name = "lastName", length = 128, nullable = true)
    private String lastName;
    
    @Column(name = "zip", length = 128, nullable = true)
    private String zip;
    
    @Column(name = "gender", length = 128, nullable = true)
    private String gender;
    
    @Column(name = "birthday", length = 128, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    
    @Column(name = "sizeLimit", nullable = false)
    private Long sizeLimit;
    
    @NotEmpty 
    @Column(name = "email", length = 128, nullable = false)
    private String email;
    
    @NotEmpty 
    @Column(name = "password", length = 128, nullable = true)
    private String password;
    
    @OneToMany(cascade = {CascadeType.ALL},
    fetch = FetchType.EAGER, mappedBy = "acc")
    private Set<Role> roles;

    public Long getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(Long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Account() {
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getZip() {
        return zip;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            list.add(new GrantedAuthorityImpl(role.getRoleValue()));
        }
        return list;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Account mobj = (Account) obj;

        if (this.id.equals(mobj.id)) {
            return true;
        }
        return false;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
