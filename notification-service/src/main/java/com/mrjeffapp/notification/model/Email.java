package com.mrjeffapp.notification.model;

public class Email {
    private String emailTypeCode;

    private String destination;

    private String subject;

    private String content;
    public Email(){

    }

    public Email(String emailTypeCode, String destination, String subject, String content) {
        this.emailTypeCode = emailTypeCode;
        this.destination = destination;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (emailTypeCode != null ? !emailTypeCode.equals(email.emailTypeCode) : email.emailTypeCode != null)
            return false;
        if (destination != null ? !destination.equals(email.destination) : email.destination != null) return false;
        if (subject != null ? !subject.equals(email.subject) : email.subject != null) return false;
        return content != null ? content.equals(email.content) : email.content == null;
    }

    @Override
    public int hashCode() {
        int result = emailTypeCode != null ? emailTypeCode.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public String getEmailTypeCode() {
        return emailTypeCode;
    }

    public void setEmailTypeCode(String emailTypeCode) {
        this.emailTypeCode = emailTypeCode;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailTypeCode='" + emailTypeCode + '\'' +
                ", destination='" + destination + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

