package com.snail.historytoday.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author uidq5232
 * @data 2023-6-6
 */

public class HistoryBean implements Serializable {

    private String reason;
    private List<ResultDTO> result;
    private Integer errorCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultDTO> getResult() {
        return result;
    }

    public void setResult(List<ResultDTO> result) {
        this.result = result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public static class ResultDTO implements Serializable{
        private String day;
        private String date;
        private String title;
        private String eId;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String geteId() {
            return eId;
        }

        public void seteId(String eId) {
            this.eId = eId;
        }
    }
}
