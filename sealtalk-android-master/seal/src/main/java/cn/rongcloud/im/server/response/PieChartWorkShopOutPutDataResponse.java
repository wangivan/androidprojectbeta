package cn.rongcloud.im.server.response;

import java.util.List;

/**
 * Created by Ivan.Wang on 2016/11/29.
 */

public class PieChartWorkShopOutPutDataResponse {

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private List<ResultEntity> result;

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        private String type;
        private String ztName;
        private String data;

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setZtName(String ztName) {
            this.ztName = ztName;
        }

        public String getZtName() {
            return ztName;
        }

        public String getData() {return data;}

        public void setData(String data) {this.data = data;}

    }
}
