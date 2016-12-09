package cn.rongcloud.im.server.response;



/**
 * Created by Ivan.Wang on 2016/7/24.
 */
public class PublishRequirementResponse  {


    // 1.发布数据提交成功 --》 审核中
    // 2.发布数据提交失败 --》 重新发布
    // 3.发布数据审核成功 --》 成功发布



    private  int code ;

    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
