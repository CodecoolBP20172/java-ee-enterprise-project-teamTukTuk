package com.codecool.enterpriseproject.api;

public class ErrorJSON {

    private boolean allFieldsRequired;
    private boolean passwordMismatch;
    private boolean tooShortName;
    private boolean invalidName;
    private boolean emailExists;
    private boolean emailInvalid;
    private boolean couldNotParseAge;
    private boolean ageOutsideInterval;
    private boolean valid;

    public boolean isAllFieldsRequired() {
        return allFieldsRequired;
    }

    public void setAllFieldsRequired(boolean allFieldsRequired) {
        this.allFieldsRequired = allFieldsRequired;
    }

    public boolean isPasswordMismatch() {
        return passwordMismatch;
    }

    public void setPasswordMismatch(boolean passwordMismatch) {
        this.passwordMismatch = passwordMismatch;
    }

    public boolean isTooShortName() {
        return tooShortName;
    }

    public void setTooShortName(boolean tooShortName) {
        this.tooShortName = tooShortName;
    }

    public boolean isInvalidName() {
        return invalidName;
    }

    public void setInvalidName(boolean invalidName) {
        this.invalidName = invalidName;
    }

    public boolean isEmailExists() {
        return emailExists;
    }

    public void setEmailExists(boolean emailExists) {
        this.emailExists = emailExists;
    }

    public boolean isCouldNotParseAge() {
        return couldNotParseAge;
    }

    public void setCouldNotParseAge(boolean couldNotParseAge) {
        this.couldNotParseAge = couldNotParseAge;
    }

    public boolean isAgeOutsideInterval() {
        return ageOutsideInterval;
    }

    public void setAgeOutsideInterval(boolean ageOutsideInterval) {
        this.ageOutsideInterval = ageOutsideInterval;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isEmailInvalid() {
        return emailInvalid;
    }

    public void setEmailInvalid(boolean emailInvalid) {
        this.emailInvalid = emailInvalid;
    }
}
