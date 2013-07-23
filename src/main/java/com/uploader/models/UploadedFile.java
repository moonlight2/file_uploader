package com.uploader.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "file")
public class UploadedFile implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "fileName", length = 128, nullable = false)
    private String fileName;
    @Column(name = "filePath", length = 128, nullable = false)
    private String filePath;
    
    @Column(name = "hashName", length = 128, nullable = false)
    private String hashName;
    
    @Column(name = "fileSize", nullable = false)
    private Long fileSize;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Account acc;

    public Set<SharedFile> getSharedFiles() {
        return sharedFiles;
    }

    public void setSharedFiles(Set<SharedFile> sharedFiles) {
        this.sharedFiles = sharedFiles;
    }
    
    @OneToMany(cascade = {CascadeType.ALL},
    fetch = FetchType.EAGER, mappedBy = "file")
    private Set<SharedFile> sharedFiles;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public UploadedFile() {
    }

    public UploadedFile(String fileName, String hashName, Account acc) {
        this.fileName = fileName;
        this.hashName = hashName;
        this.acc = acc;
    }
    
    public UploadedFile(String fileName, String filePath, String hashName, Account acc, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.hashName = hashName;
        this.fileSize = fileSize;
        this.acc = acc;
    }


    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    @Override
    public String toString() {
        return "UploadedFile{" + "id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", hashName=" + hashName + ", acc=" + acc + ", sharedFiles=" + sharedFiles + '}';
    }


    
    
    
    
}
