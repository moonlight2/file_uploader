package com.uploader.models;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author iosipov
 */
@Entity
@Table(name = "shared_file")
public class SharedFile {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "hashName", length = 128, nullable = false)
    private String hashName;
    
    @Column(name = "confirmation", length = 128, nullable = true)
    private String confirmation;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
    
    @Column(name = "expireDate", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date expireDate;
    
    
    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId")
    private UploadedFile file;

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public SharedFile() {
    }

    public SharedFile(String hashName, Date expireDate,  UploadedFile file) {
        this.hashName = hashName;
        this.expireDate = expireDate;
//        this.acc = acc;
        this.file = file;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

//    public Account getAcc() {
//        return acc;
//    }
//
//    public void setAcc(Account acc) {
//        this.acc = acc;
//    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
