package cn.rongcloud.im.server.response;

import java.util.List;
import java.util.Map;

/**
 * Created by ivan.wang on 2016/12/7.
 */

public class DayReportResponse {

    private int code;

    private List<ResultEntity> result;

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        private String ztName;
        private String code;
        private String gatherDate;
        private String summation;
        private Map<String,Float> data;

        public String getGatherDate() {
            return gatherDate;
        }

        public void setGatherDate(String gatherDate) {
            this.gatherDate = gatherDate;
        }

        public String getSummation() {
            return summation;
        }

        public void setSummation(String summation) {
            this.summation = summation;
        }

        public Map<String, Float> getData() {
            return data;
        }

        public void setData(Map<String, Float> data) {
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setZtName(String ztName) {
            this.ztName = ztName;
        }

        public String getZtName() {
            return ztName;
        }
    }
}
