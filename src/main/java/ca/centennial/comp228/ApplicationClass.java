package ca.centennial.comp228;

import java.util.Date;

public class ApplicationClass {
    private String fullName;
    private String currentAddress;
    private String contactNumber;
    private String highestEduAtt;
    private String email;
    private String gender;

    private Date avaliableDate;
    private String desiredPosition;
    private double desiredSalary;
    private boolean authorizedToWork;
    private boolean haveRelativesWorking;
    private String explainRelatives;

    private String signature;

    public ApplicationClass(
            String fullName,
            String currentAddress,
            String contactNumber,
            String highestEduAtt,
            String email,
            String gender,
            Date avaliableDate,
            String desiredPosition,
            double desiredSalary,
            boolean authorizedToWork,
            boolean haveRelativesWorking,
            String explainRelatives,
            String signature
    ) {
        this.fullName = fullName;
        this.currentAddress = currentAddress;
        this.contactNumber = contactNumber;
        this.highestEduAtt = highestEduAtt;
        this.email = email;
        this.gender = gender;
        this.avaliableDate = avaliableDate;
        this.desiredPosition = desiredPosition;
        this.desiredSalary = desiredSalary;
        this.authorizedToWork = authorizedToWork;
        this.haveRelativesWorking = haveRelativesWorking;
        this.explainRelatives = haveRelativesWorking ? explainRelatives : null;
        this.signature = signature;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getHighestEduAtt() {
        return highestEduAtt;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Date getAvaliableDate() {
        return avaliableDate;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public double getDesiredSalary() {
        return desiredSalary;
    }

    public boolean isAuthorizedToWork() {
        return authorizedToWork;
    }

    public boolean isHaveRelativesWorking() {
        return haveRelativesWorking;
    }

    public String getExplainRelatives() {
        return explainRelatives;
    }

    public String getSignature() {
        return signature;
    }
}
