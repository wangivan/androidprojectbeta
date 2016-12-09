package cn.rongcloud.im.server.response;

import java.util.List;

/**
 * Created by Ivan.Wang on 2016/11/29.
 */

public class PieChartSignalWorkShopDataResponse {

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
        private String code;
        private String data;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public String getData() {return data;}

        public void setData(String data) {this.data = data;}

    }
}
