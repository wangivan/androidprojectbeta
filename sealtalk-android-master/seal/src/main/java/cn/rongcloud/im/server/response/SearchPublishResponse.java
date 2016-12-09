package cn.rongcloud.im.server.response;



import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.im.model.DataEntity;

/**
 * Created by Ivan.Wang on 2016/7/30.
 */
public class SearchPublishResponse  {

    private int code;
    private String message;
//    private DataEntity data;

    private List<DataEntity> dataList = new ArrayList<DataEntity>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public DataEntity getResult() {
//        return data;
//    }
//
//    public void setResult(DataEntity data) {
//        this.data = data;
//    }

    public List<DataEntity> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataEntity> dataList) {
        this.dataList = dataList;
    }

//    private class DataEntity{
//
//
//
//
//
//    }
}
