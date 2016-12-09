package cn.rongcloud.im.server.response;

import java.util.List;

/**
 * Created by Ivan.Wang on 2016/11/28.
 */

public class LineChartDataResponse {

//    private ResultEntity result;
//
//    public void setResult(ResultEntity result) {
//        this.result = result;
//    }
//
//    public ResultEntity getResult() {
//        return result;
//    }

    private List<ResultEntity> result;

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity implements Comparable{
        private String electric;
        private String time;
        private String data;

        public void setElectric(String electric) {
            this.electric = electric;
        }

        public String getElectric() {
            return electric;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getData() {return data;}

        public void setData(String data) {this.data = data;}

        @Override
        public int compareTo(Object another) {
            ResultEntity entity = (ResultEntity)another;
            String anotherTime = entity.getTime();
            String array[] = anotherTime.split(":");
            Integer anotherTimeFirst = Integer.valueOf(array[0]);
            Integer thisTimeFirst = Integer.valueOf(this.time.split(":")[0]);
            return thisTimeFirst.compareTo(anotherTimeFirst);
        }
    }
}
