package com.youth.Entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author maoyan
 * @date 2022/7/18 19:29
 */

@Data
public class DicomEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientId;

    private String patientName;

    private String patientBirthDate;

    private String patientSex;

    private String studyId;

    private String studyDate;

    private String studyTime;

    private String error; //0正确

    private String jpgSavePath;

    private String httpPath;

    private String dicomPath;

    public DicomEntity() {}
}
