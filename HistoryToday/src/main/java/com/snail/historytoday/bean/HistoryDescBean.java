package com.snail.historytoday.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author uidq5232
 * @data 2023-6-6
 */
public class HistoryDescBean implements Serializable {

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
        private String eId;
        private String title;
        private String content;
        private String picNo;
        private List<PicUrlDTO> picUrl;

        public String geteId() {
            return eId;
        }

        public void seteId(String eId) {
            this.eId = eId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicNo() {
            return picNo;
        }

        public void setPicNo(String picNo) {
            this.picNo = picNo;
        }

        public List<PicUrlDTO> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<PicUrlDTO> picUrl) {
            this.picUrl = picUrl;
        }

        public static class PicUrlDTO implements Serializable{
            private String picTitle;
            private String suffix;
            private Integer id;
            private String url;

            public String getPicTitle() {
                return picTitle;
            }

            public void setPicTitle(String picTitle) {
                this.picTitle = picTitle;
            }

            public String getSuffix() {
                return suffix;
            }

            public void setSuffix(String suffix) {
                this.suffix = suffix;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
