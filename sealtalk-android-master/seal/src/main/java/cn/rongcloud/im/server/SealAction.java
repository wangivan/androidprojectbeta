package cn.rongcloud.im.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.rongcloud.im.model.DataEntity;
import cn.rongcloud.im.server.network.http.HttpException;
import cn.rongcloud.im.server.network.http.RequestParams;
import cn.rongcloud.im.server.request.AddGroupMemberRequest;
import cn.rongcloud.im.server.request.AddToBlackListRequest;
import cn.rongcloud.im.server.request.AgreeFriendsRequest;
import cn.rongcloud.im.server.request.ChangePasswordRequest;
import cn.rongcloud.im.server.request.CheckPhoneRequest;
import cn.rongcloud.im.server.request.CreateGroupRequest;
import cn.rongcloud.im.server.request.DeleteFriendRequest;
import cn.rongcloud.im.server.request.DeleteGroupMemberRequest;
import cn.rongcloud.im.server.request.DismissGroupRequest;
import cn.rongcloud.im.server.request.FriendInvitationRequest;
import cn.rongcloud.im.server.request.JoinGroupRequest;
import cn.rongcloud.im.server.request.LoginRequest;
import cn.rongcloud.im.server.request.QuitGroupRequest;
import cn.rongcloud.im.server.request.RegisterRequest;
import cn.rongcloud.im.server.request.RemoveFromBlacklistRequest;
import cn.rongcloud.im.server.request.RestPasswordRequest;
import cn.rongcloud.im.server.request.SendCodeRequest;
import cn.rongcloud.im.server.request.SetFriendDisplayNameRequest;
import cn.rongcloud.im.server.request.SetGroupDisplayNameRequest;
import cn.rongcloud.im.server.request.SetGroupNameRequest;
import cn.rongcloud.im.server.request.SetGroupPortraitRequest;
import cn.rongcloud.im.server.request.SetNameRequest;
import cn.rongcloud.im.server.request.SetPortraitRequest;
import cn.rongcloud.im.server.request.VerifyCodeRequest;
import cn.rongcloud.im.server.response.AddGroupMemberResponse;
import cn.rongcloud.im.server.response.AddToBlackListResponse;
import cn.rongcloud.im.server.response.AgreeFriendsResponse;
import cn.rongcloud.im.server.response.ChangePasswordResponse;
import cn.rongcloud.im.server.response.CheckPhoneResponse;
import cn.rongcloud.im.server.response.CreateGroupResponse;
import cn.rongcloud.im.server.response.DayReportResponse;
import cn.rongcloud.im.server.response.DefaultConversationResponse;
import cn.rongcloud.im.server.response.DeleteFriendResponse;
import cn.rongcloud.im.server.response.DeleteGroupMemberResponse;
import cn.rongcloud.im.server.response.DismissGroupResponse;
import cn.rongcloud.im.server.response.FriendInvitationResponse;
import cn.rongcloud.im.server.response.GetBlackListResponse;
import cn.rongcloud.im.server.response.GetGroupInfoResponse;
import cn.rongcloud.im.server.response.GetGroupMemberResponse;
import cn.rongcloud.im.server.response.GetGroupResponse;
import cn.rongcloud.im.server.response.GetTokenResponse;
import cn.rongcloud.im.server.response.GetUserInfoByIdResponse;
import cn.rongcloud.im.server.response.GetUserInfoByPhoneResponse;
import cn.rongcloud.im.server.response.GetUserInfosResponse;
import cn.rongcloud.im.server.response.JoinGroupResponse;
import cn.rongcloud.im.server.response.LineChartDataResponse;
import cn.rongcloud.im.server.response.LoginResponse;
import cn.rongcloud.im.server.response.PieChartDataResponse;
import cn.rongcloud.im.server.response.PieChartSignalWorkShopDataResponse;
import cn.rongcloud.im.server.response.PieChartSingleWorkShopOutPutDataResponse;
import cn.rongcloud.im.server.response.PieChartWorkShopOutPutDataResponse;
import cn.rongcloud.im.server.response.PublishRequirementResponse;
import cn.rongcloud.im.server.response.QiNiuTokenResponse;
import cn.rongcloud.im.server.response.QuitGroupResponse;
import cn.rongcloud.im.server.response.RegisterResponse;
import cn.rongcloud.im.server.response.RemoveFromBlackListResponse;
import cn.rongcloud.im.server.response.RestPasswordResponse;
import cn.rongcloud.im.server.response.SearchPublishResponse;
import cn.rongcloud.im.server.response.SendCodeResponse;
import cn.rongcloud.im.server.response.SetFriendDisplayNameResponse;
import cn.rongcloud.im.server.response.SetGroupDisplayNameResponse;
import cn.rongcloud.im.server.response.SetGroupPortraitResponse;
import cn.rongcloud.im.server.response.SetNameResponse;
import cn.rongcloud.im.server.response.SetPortraitResponse;
import cn.rongcloud.im.server.response.UserRelationshipResponse;
import cn.rongcloud.im.server.response.VerifyCodeResponse;
import cn.rongcloud.im.server.response.SetGroupNameResponse;
import cn.rongcloud.im.server.response.VersionResponse;
import cn.rongcloud.im.server.utils.NLog;
import cn.rongcloud.im.server.utils.json.JsonMananger;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
public class SealAction extends BaseAction {
    private final String CONTENTTYPE = "application/json";
    private final String ENCODING = "utf-8";
    private final String IP= "139.196.16.119:9090";
//    private final String IP = "192.168.2.101:8002";
//    private final String IP = "192.168.2.66:8000";

    /**
     * 构造方法
     *
     * @param context
     */
    public SealAction(Context context) {
        super(context);
    }


    /**
     * 检查手机是否被注册
     *
     * @param region
     * @param phone
     * @return
     * @throws HttpException
     */
    public CheckPhoneResponse checkPhoneAvailable(String region, String phone) throws HttpException {
//        String url = getURL("user/check_phone_available");
//        String json = JsonMananger.beanToJson(new CheckPhoneRequest(phone, region));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String uri = "http://"+IP+"/mobile/getinfo/check_phone_available/";
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("region",region);

        CheckPhoneResponse response = null;
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, CheckPhoneResponse.class);
        }
        return response;
    }


    /**
     * 发送验证码
     *
     * @param region
     * @param phone
     * @return
     * @throws HttpException
     */
    public SendCodeResponse sendCode(String region, String phone) throws HttpException {
//        String url = getURL("user/send_code");
//        String json = JsonMananger.beanToJson(new SendCodeRequest(region, phone));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String uri = "http://"+IP+"/mobile/getinfo/send_code/";
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("region",region);

        SendCodeResponse response = null;
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = JsonMananger.jsonToBean(result, SendCodeResponse.class);
        }
        return response;
    }

    /*
    * 200: 验证成功
    1000: 验证码错误
    2000: 验证码过期
    异常返回，返回的 HTTP Status Code 如下：

    400: 错误的请求
    500: 应用服务器内部错误
    * */

    /**
     * 验证验证码是否正确(必选先用手机号码调sendcode)
     *
     * @param region
     * @param phone
     * @return
     * @throws HttpException
     */
    public VerifyCodeResponse verifyCode(String region, String phone, String code) throws HttpException {
//        String url = getURL("user/verify_code");
//        String josn = JsonMananger.beanToJson(new VerifyCodeRequest(region, phone, code));

        String uri = "http://"+IP+"/mobile/getinfo/verify_code/";
        RequestParams params = new RequestParams();
        params.put("region",region);
        params.put("phone",phone);
        params.put("code",code);


        VerifyCodeResponse response = null;
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(josn, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            Log.e("VerifyCodeResponse", result);
            response = jsonToBean(result, VerifyCodeResponse.class);
        }
        return response;
    }

    /**
     * 注册
     *
     * @param nickname
     * @param password
     * @param
     * @return
     * @throws HttpException
     */
//    public RegisterResponse (String nickname, String password, String verification_token,String phonenumber) throws HttpException {
//        String url = getURL("user/register");
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(JsonMananger.beanToJson(new RegisterRequest(nickname, password, verification_token)), ENCODING);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
////        String uri = "http://i-plc.cn/mobile/getinfo/register";
////        RequestParams params = new RequestParams();
////        params.put("nickname",nickname);
////        params.put("password",password);
////        params.put("verification_token",verification_token);
////        params.put("phonenumber",phonenumber);
//        RegisterResponse response = null;
////        String result = httpManager.post(uri,params);
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
//        if (!TextUtils.isEmpty(result)) {
//            NLog.e("RegisterResponse", result);
//            response = jsonToBean(result, RegisterResponse.class);
//        }
//        return response;
//    }


    /**
     * 注册
     *
     * @param nickname
     * @param password
     * @param
     * @return
     * @throws HttpException
     */
    public RegisterResponse register(String nickname, String password,String phonenumber) throws HttpException {
     //   String url = getURL("user/register");
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(JsonMananger.beanToJson(new RegisterRequest(nickname, password, verification_token)), ENCODING);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String uri = "http://"+IP+"/mobile/getinfo/register/";
        RequestParams params = new RequestParams();
        params.put("nickname",nickname);
        params.put("password",password);
       // params.put("verification_token",verification_token);
        params.put("phonenumber",phonenumber);
        RegisterResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            NLog.e("RegisterResponse", result);
            response = jsonToBean(result, RegisterResponse.class);
        }
        return response;
    }



    /**
     * 登录: 登录成功后，会设置 Cookie，后续接口调用需要登录的权限都依赖于 Cookie。
     *
     * @param region
     * @param phone
     * @param password
     * @return
     * @throws HttpException
     */
    public LoginResponse login(String region, String phone, String password) throws HttpException {
//        String DOMAIN = "http://api.sealtalk.im/";
//        String uri = "http://i-plc.cn/mobile/getinfo/login/";
        String uri = "http://"+IP+"/mobile/getinfo/login/";
//        String uri = getURL("user/login");
//        String json = JsonMananger.beanToJson(new LoginRequest(region, phone, password));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String result = httpManager.post(mContext, uri, entity, CONTENTTYPE);
        RequestParams params = new RequestParams();
        params.put("region",region);
        params.put("phone",phone);
        params.put("password",password);
        LoginResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            NLog.e("LoginResponse", result);
            response = JsonMananger.jsonToBean(result, LoginResponse.class);
        }

        return response;
    }


    /**
     * 获取 token 前置条件需要登录   502 坏的网关 测试环境用户已达上限
     *
     * @return
     * @throws HttpException
     */
    public GetTokenResponse getToken() throws HttpException {
        String url = getURL("user/get_token");
        String result = httpManager.get(url);
        GetTokenResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("GetTokenResponse", result);
            response = jsonToBean(result, GetTokenResponse.class);
        }
        return response;
    }

    /**
     * 设置自己的昵称
     *
     * @param nickname
     * @return
     * @throws HttpException
     */
    public SetNameResponse setName(String nickname) throws HttpException {
        String url = getURL("user/set_nickname");
        String json = JsonMananger.beanToJson(new SetNameRequest(nickname));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SetNameResponse response = null;
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetNameResponse.class);
        }
        return response;
    }

    /**
     * 设置用户头像
     *
     * @param portraitUri
     * @return
     * @throws HttpException
     */
    public SetPortraitResponse setPortrait(String portraitUri) throws HttpException {
        String url = getURL("user/set_portrait_uri");
        String json = JsonMananger.beanToJson(new SetPortraitRequest(portraitUri));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SetPortraitResponse response = null;
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetPortraitResponse.class);
        }
        return response;
    }


    /**
     * 当前登录用户通过旧密码设置新密码  前置条件需要登录才能访问
     *
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws HttpException
     */
    public ChangePasswordResponse changePassword(String oldPassword, String newPassword) throws HttpException {
        String url = getURL("user/change_password");
        String json = JsonMananger.beanToJson(new ChangePasswordRequest(oldPassword, newPassword));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        ChangePasswordResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("ChangePasswordResponse", result);
            response = jsonToBean(result, ChangePasswordResponse.class);
        }
        return response;
    }


    /**
     * 通过手机验证码重置密码
     *
     * @param password           密码，6 到 20 个字节，不能包含空格
     * @param verification_token 调用 /user/verify_code 成功后返回的 activation_token
     * @return
     * @throws HttpException
     */
    public RestPasswordResponse restPassword(String password, String verification_token) throws HttpException {
        String uri = getURL("user/reset_password");
        String json = JsonMananger.beanToJson(new RestPasswordRequest(password, verification_token));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, uri, entity, CONTENTTYPE);
        RestPasswordResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("RestPasswordResponse", result);
            response = jsonToBean(result, RestPasswordResponse.class);
        }
        return response;
    }

    /**
     * 根据 id 去服务端查询用户信息
     *
     * @param userid
     * @return
     * @throws HttpException
     */
    public GetUserInfoByIdResponse getUserInfoById(String userid) throws HttpException {
        String url = "http://"+IP+"/mobile/getinfo/user/";
//        String url = getURL("user/" + userid);
        RequestParams params = new RequestParams();
        params.put("UserID",userid);
//        String result = httpManager.get(url);
        String result = httpManager.post(url, params);
        GetUserInfoByIdResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetUserInfoByIdResponse.class);
        }
        return response;
    }


    /**
     * 通过国家码和手机号查询用户信息
     *
     * @param region
     * @param phone
     * @return
     * @throws HttpException
     */
    public GetUserInfoByPhoneResponse getUserInfoFromPhone(String region, String phone) throws HttpException {
        String url = "http://"+IP+"/mobile/getinfo/searchuser/";
        RequestParams params = new RequestParams();
        params.put("region",region);
        params.put("phone",phone);
//        String url = getURL("user/find/" + region + "/" + phone);
//        String result = httpManager.get(url);
        String result = httpManager.post(url,params);
        GetUserInfoByPhoneResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetUserInfoByPhoneResponse.class);
        }
        return response;
    }


    /**
     * 发送好友邀请
     *
     * @param userid           好友id
     * @param addFriendMessage is
     * @return
     * @throws HttpException
     */
    public FriendInvitationResponse sendFriendInvitation(String userid, String usertargetId, String addFriendMessage) throws HttpException {
        String url = "http://"+IP+"/mobile/getinfo/friendship/invite/";
//        String url = getURL("friendship/invite");
//        String json = JsonMananger.beanToJson(new FriendInvitationRequest(usertargetId, addFriendMessage));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        RequestParams params = new RequestParams();
        params.put("UserRid",userid);
        params.put("UserAid",usertargetId);
        params.put("InviteMessage",addFriendMessage);
        FriendInvitationResponse response = null;
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, FriendInvitationResponse.class);
        }
        return response;
    }


    /**
     * 获取发生过用户关系的列表
     *
     * @return
     * @throws HttpException
     */
    public UserRelationshipResponse getAllUserRelationship(String id) throws HttpException {
//        String url = getURL("friendship/all");
        String url = "http://"+IP+"/mobile/getinfo/friendship/all/";
        RequestParams params = new RequestParams();
        params.put("UserID",id);
        String result = httpManager.post(url, params);
//        String result = httpManager.get(url);
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            if(jsonObject.getString("result").isEmpty())
//                return null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        UserRelationshipResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, UserRelationshipResponse.class);
        }
        return response;
    }

    /**
     * 获取用户的好友
     *
     * @return
     * @throws HttpException
     */
    public UserRelationshipResponse getAllUserFriend(String id) throws HttpException {
//        String url = getURL("friendship/all");
        String url = "http://"+IP+"/mobile/getinfo/befriend/";
        RequestParams params = new RequestParams();
        params.put("UserID",id);
        String result = httpManager.post(url, params);
//        String result = httpManager.get(url);
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            if(jsonObject.getString("result").isEmpty())
//                return null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        UserRelationshipResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, UserRelationshipResponse.class);
        }
        return response;
    }


    /**
     * 同意对方好友邀请
     *
     * @param friendId
     * @return
     * @throws HttpException
     */
    public AgreeFriendsResponse agreeFriends(String userid,String friendId) throws HttpException {
        String url = "http://"+IP+"/mobile/getinfo/friendship/agree/";
//        String url = getURL("friendship/agree");
//        String json = JsonMananger.beanToJson(new AgreeFriendsRequest(friendId));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        RequestParams params = new RequestParams();
        //params.put("UserRid",userid);
        //params.put("UserAid",friendId);
        params.put("UserRid",friendId);
        params.put("UserAid",userid);
        AgreeFriendsResponse response = null;
        String result = httpManager.post(url, params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, AgreeFriendsResponse.class);
        }
        return response;
    }

    /**
     * 创建群组
     *
     * @param name      群组名
     * @param memberIds 群组成员id
     * @return
     * @throws HttpException
     */
    public CreateGroupResponse createGroup(String name, List<String> memberIds) throws HttpException {
/*        String url = getURL("group/create");
        String json = JsonMananger.beanToJson(new CreateGroupRequest(name, memberIds));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        CreateGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, CreateGroupResponse.class);
        }
        return response;*/

        String url = "http://"+IP+"/mobile/getinfo/create_group/";
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("memberIds",memberIds);
        String result = httpManager.post(url, params);
        CreateGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, CreateGroupResponse.class);
        }
        return response;

    }

    /**
     * 创建者设置群组头像
     *
     * @param groupId
     * @param portraitUri
     * @return
     * @throws HttpException
     */
    public SetGroupPortraitResponse setGroupPortrait(String groupId, String portraitUri) throws HttpException {
        String url = getURL("group/set_portrait_uri");
        String json = JsonMananger.beanToJson(new SetGroupPortraitRequest(groupId, portraitUri));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        SetGroupPortraitResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetGroupPortraitResponse.class);
        }
        return response;
    }

    /**
     * 获取当前用户所属群组列表
     *
     * @return
     * @throws HttpException
     */
    public GetGroupResponse getGroups(String userId) throws HttpException {
        String url = "http://"+IP+"/mobile/getinfo/user/groups/";
//        String url = getURL("user/groups");
        RequestParams params = new RequestParams();
        params.put("userId",userId);
        String result = httpManager.post(url,params);
        GetGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetGroupResponse.class);
        }
        return response;
    }

    /**
     * 根据 群组id 查询该群组信息   403 群组成员才能看
     *
     * @param groupId
     * @return
     * @throws HttpException
     */
    public GetGroupInfoResponse getGroupInfo(String groupId) throws HttpException {
        //String url = getURL("group/" + groupId);
        String url = "http://"+IP+"/mobile/getinfo/groupinfo/";
        RequestParams params = new RequestParams();
        params.put("groupId",groupId);
        String result = httpManager.post(url, params);
        GetGroupInfoResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetGroupInfoResponse.class);
        }
        return response;
    }

    /**
     * 根据群id获取群组成员
     *
     * @param groupId
     * @return
     * @throws HttpException
     */
    public GetGroupMemberResponse getGroupMember(String groupId) throws HttpException {
        //String url = getURL("group/" + groupId + "/members");
        String url = "http://"+IP+"/mobile/getinfo/group/getDetailMembers/";
        RequestParams params = new RequestParams();
        params.put("groupId",groupId);
        String result = httpManager.post(url, params);
        GetGroupMemberResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetGroupMemberResponse.class);
        }
        return response;
    }

    /**
     * 当前用户添加群组成员
     *
     * @param groupId
     * @param memberIds
     * @return
     * @throws HttpException
     */
    public AddGroupMemberResponse addGroupMember(String groupId, List<String> memberIds) throws HttpException {
       // String url = getURL("group/add");
        String url = "http://"+IP+"/mobile/group/add/";
       // String json = JsonMananger.beanToJson(new AddGroupMemberRequest(groupId, memberIds));
        RequestParams params = new RequestParams();
        params.put("groupId",groupId);
        params.put("memberIds",memberIds);
     /*   StringEntity entity = null;

        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        String result = httpManager.post(url,params);
        AddGroupMemberResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, AddGroupMemberResponse.class);
        }
        return response;
    }

    /**
     * 创建者将群组成员提出群组
     *
     * @param groupId
     * @param memberIds
     * @return
     * @throws HttpException
     */
    public DeleteGroupMemberResponse deleGroupMember(String groupId, List<String> memberIds) throws HttpException {
        //String url = getURL("group/kick");
        String url = "http://"+IP+"/mobile/group/kick/";
        //String json = JsonMananger.beanToJson(new DeleteGroupMemberRequest(groupId, memberIds));
       // StringEntity entity = null;
    /*    try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        RequestParams params = new RequestParams();
        params.put("groupId",groupId);
        params.put("memberIds",memberIds);
        String result =  httpManager.post(url,params);
        DeleteGroupMemberResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, DeleteGroupMemberResponse.class);
        }
        return response;
    }

    /**
     * 创建者更改群组昵称
     *
     * @param groupId
     * @param name
     * @return
     * @throws HttpException
     */
    public SetGroupNameResponse setGroupName(String groupId, String name) throws HttpException {
        String url = getURL("group/rename");
        String json = JsonMananger.beanToJson(new SetGroupNameRequest(groupId, name));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        SetGroupNameResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetGroupNameResponse.class);
        }
        return response;
    }

    /**
     * 用户自行退出群组
     *
     * @param groupId
     * @return
     * @throws HttpException
     */
    public QuitGroupResponse quitGroup(String groupId) throws HttpException {
        String url = getURL("group/quit");
        String json = JsonMananger.beanToJson(new QuitGroupRequest(groupId));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        QuitGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, QuitGroupResponse.class);
        }
        return response;
    }

    /**
     * 创建者解散群组
     *
     * @param groupId
     * @return
     * @throws HttpException
     */
    public DismissGroupResponse dissmissGroup(String groupId) throws HttpException {
        String url = getURL("group/dismiss");
        String json = JsonMananger.beanToJson(new DismissGroupRequest(groupId));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        DismissGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, DismissGroupResponse.class);
        }
        return response;
    }


    /**
     * 修改自己的当前的群昵称
     *
     * @param groupId
     * @param displayName
     * @return
     * @throws HttpException
     */
    public SetGroupDisplayNameResponse setGroupDisplayName(String groupId, String displayName) throws HttpException {
        String url = getURL("group/set_display_name");
        String json = JsonMananger.beanToJson(new SetGroupDisplayNameRequest(groupId, displayName));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        SetGroupDisplayNameResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetGroupDisplayNameResponse.class);
        }
        return response;
    }

    /**
     * 删除好友
     *
     * @param friendId
     * @return
     * @throws HttpException
     */
    public DeleteFriendResponse deleteFriend(String userId,String friendId) throws HttpException {
//        String url = getURL("friendship/delete");
        String url = "http://"+IP+"/mobile/getinfo/deleteFriends/";
        RequestParams params = new RequestParams();
        params.put("userId",userId);
        params.put("friendId",friendId);
//        String json = JsonMananger.beanToJson(new DeleteFriendRequest(friendId));
//        StringEntity entity = null;
//        try {
//            entity = new StringEntity(json, ENCODING);
//            entity.setContentType(CONTENTTYPE);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        String result = httpManager.post(url,params);
        DeleteFriendResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, DeleteFriendResponse.class);
        }
        return response;
    }

    /**
     * 设置好友的备注名称 未测试
     *
     * @param friendId
     * @param displayName
     * @return
     * @throws HttpException
     */
    public SetFriendDisplayNameResponse setFriendDisplayName(String friendId, String displayName) throws HttpException {
        String url = getURL("friendship/set_display_name");
        String json = JsonMananger.beanToJson(new SetFriendDisplayNameRequest(friendId, displayName));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        SetFriendDisplayNameResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, SetFriendDisplayNameResponse.class);
        }
        return response;
    }

    /**
     * 获取黑名单
     *
     * @return
     * @throws HttpException
     */
    public GetBlackListResponse getBlackList() throws HttpException {
        String url = getURL("user/blacklist");
        String result = httpManager.get(mContext, url);
        GetBlackListResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetBlackListResponse.class);
        }
        return response;
    }

    /**
     * 加入黑名单
     *
     * @param friendId
     * @return
     * @throws HttpException
     */
    public AddToBlackListResponse addToBlackList(String friendId) throws HttpException {
        String url = getURL("user/add_to_blacklist");
        String json = JsonMananger.beanToJson(new AddToBlackListRequest(friendId));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        AddToBlackListResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, AddToBlackListResponse.class);
        }
        return response;
    }

    /**
     * 移除黑名单
     *
     * @param friendId
     * @return
     * @throws HttpException
     */
    public RemoveFromBlackListResponse removeFromBlackList(String friendId) throws HttpException {
        String url = getURL("user/remove_from_blacklist");
        String json = JsonMananger.beanToJson(new RemoveFromBlacklistRequest(friendId));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        RemoveFromBlackListResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, RemoveFromBlackListResponse.class);
        }
        return response;
    }

    public QiNiuTokenResponse getQiNiuToken() throws HttpException {
        String url = getURL("user/get_image_token");
        String result = httpManager.get(mContext, url);
        QiNiuTokenResponse q = null;
        if (!TextUtils.isEmpty(result)) {
            q = jsonToBean(result, QiNiuTokenResponse.class);
        }
        return q;
    }


    /**
     * 当前用户加入某群组
     *
     * @param groupId
     * @return
     * @throws HttpException
     */
    public JoinGroupResponse JoinGroup(String groupId) throws HttpException {
        String url = getURL("group/join");
        String json = JsonMananger.beanToJson(new JoinGroupRequest(groupId));
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, ENCODING);
            entity.setContentType(CONTENTTYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = httpManager.post(mContext, url, entity, CONTENTTYPE);
        JoinGroupResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, JoinGroupResponse.class);
        }
        return response;
    }


    /**
     * 获取默认群组 和 聊天室
     *
     * @return
     * @throws HttpException
     */
    public DefaultConversationResponse getDefaultConversation() throws HttpException {
        String url = getURL("misc/demo_square");
        String result = httpManager.get(mContext, url);
        DefaultConversationResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, DefaultConversationResponse.class);
        }
        return response;
    }

    /**
     * 根据一组ids 获取 一组用户信息
     *
     * @param ids
     * @return
     * @throws HttpException
     */
    public GetUserInfosResponse getUserInfos(List<String> ids) throws HttpException {
        String url = getURL("user/batch?");
        StringBuilder sb = new StringBuilder();
        for (String s : ids) {
            sb.append("id=");
            sb.append(s);
            sb.append("&");
        }
        String stringRequest = sb.substring(0, sb.length() - 1);
        String newUrl = url + stringRequest;
        String result = httpManager.get(mContext, newUrl);
        GetUserInfosResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, GetUserInfosResponse.class);
        }
        return response;
    }


    public VersionResponse getSealTalkVersion(String url) throws HttpException {
        String result = httpManager.get(mContext, url.trim());
        VersionResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result, VersionResponse.class);
        }
        return response;
    }


    public PublishRequirementResponse publish(String username, String title, String description, String certificateNo, String feature) throws HttpException{
//        String uri = getURL("publish");
//        String uri =  "http://i-plc.cn/mobile/getinfo/publish";
        String uri = "http://i-plc.cn/mobile/getinfo/publish/";
        RequestParams params = new RequestParams();;
        params.put("username",username);
        params.put("title",title);
        params.put("description",description);
        params.put("certificateNo",certificateNo);
        params.put("feature",feature);
        PublishRequirementResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,PublishRequirementResponse.class);
        }
        return response;
    }

    public SearchPublishResponse searchDetails(int search, String username) throws HttpException {
        String uri = "http://i-plc.cn/mobile/getinfo/searchpublish/";
        RequestParams params = new RequestParams();
        params.put("search",Integer.toString(search));
        params.put("username","admin");
        SearchPublishResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject object = new JSONObject(result);
                String data = object.getString("data");
                List<DataEntity> list = jsonToList(data,DataEntity.class);
                response = jsonToBean(result,SearchPublishResponse.class);
                response.setDataList(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public LineChartDataResponse  getLineChartData() throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.energy.GetReporta";
        LineChartDataResponse response = null;
        String result = httpManager.post(uri);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,LineChartDataResponse.class);
//            try {
//                JSONArray obj2 = new JSONArray(result);
//                List<LineChartDataResponse.ResultEntity> list = new ArrayList<>();
//                for (int i = 0; i < obj2.length(); i++) {
//                    JSONObject temp = new JSONObject(obj2.getString(i));
//                    LineChartDataResponse.ResultEntity entity = new LineChartDataResponse.ResultEntity();
//                    entity.setGatherTime(temp.getString("gatherTime"));
//                    entity.setQuantity(temp.getString("sum(equipment1cost1.DiffData)"));
//                    list.add(entity);
//                }
//                response.setResult(list);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
        return response;
    }

    public PieChartDataResponse getPieChartData() throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.energy.GetReportb";
        PieChartDataResponse response = null;
        String result = httpManager.post(uri);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,PieChartDataResponse.class);
        }
        return response;
    }

    public LineChartDataResponse  getLineChartData(String param) throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.energy.GetReportc";
        RequestParams params = new RequestParams();
        params.put("ztName",param);
        LineChartDataResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,LineChartDataResponse.class);
        }
        return response;
    }

    public PieChartSignalWorkShopDataResponse getPieChartData(String param) throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.energy.GetReportd";
        RequestParams params = new RequestParams();
        params.put("ztName",param);
        PieChartSignalWorkShopDataResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,PieChartSignalWorkShopDataResponse.class);
        }
        return response;
    }

    public PieChartWorkShopOutPutDataResponse getPieChartWorkShopOutPutData() throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.output.GetReportOutPuta";
        PieChartWorkShopOutPutDataResponse response = null;
        String result = httpManager.post(uri);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,PieChartWorkShopOutPutDataResponse.class);
        }
        return response;
    }

    public PieChartSingleWorkShopOutPutDataResponse getPieChartSingleWorkShopOutPutData(String param) throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.output.GetReportOutPutb";
        RequestParams params = new RequestParams();
        params.put("ztName",param);
        PieChartSingleWorkShopOutPutDataResponse response = null;
        String result = httpManager.post(uri,params);
        if (!TextUtils.isEmpty(result)) {
            response = jsonToBean(result,PieChartSingleWorkShopOutPutDataResponse.class);
        }
        return response;
    }

    public DayReportResponse getDayReportData(String workshopname,String type, List<String> strings, String time) throws HttpException {
        String uri = "http://xdf.i-plc.cn/request?rname=i_plc.Page.mobile.reportday.ReportDays";
        RequestParams params = new RequestParams();
        params.put("ztName",workshopname);
        JSONArray list = new JSONArray();
        for (int i = 0;i< strings.size();i++) {
            try {
                list.put(i,strings.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        params.put("machCodeList",list.toString());
        params.put("operationValue",type);
        //"2016-10-21"
        params.put("gatherDate",time);
        String result = httpManager.post(uri,params);
        DayReportResponse response = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                response = new DayReportResponse();
                JSONObject temp = new JSONObject(result);
                response.setCode(temp.getInt("code"));
                JSONArray jsonArray = temp.getJSONArray("result");
                List<DayReportResponse.ResultEntity> results = new ArrayList<DayReportResponse.ResultEntity>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = new JSONObject(jsonArray.getString(i));
                    DayReportResponse.ResultEntity entity = new DayReportResponse.ResultEntity();
                    entity.setCode(obj.getString("code"));
                    entity.setZtName(obj.getString("ztName"));
                    entity.setGatherDate(obj.getString("gatherDate"));
                    entity.setSummation(obj.getString("summation"));
                    Map<String,Float> data = new TreeMap<String,Float>(new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return Integer.valueOf(lhs).compareTo(Integer.valueOf(rhs));
                        }
                    });
                    for (int point = 1;point<=24;point++ ) {
                        data.put(String.valueOf(point),Float.valueOf(obj.getString(String.valueOf(point))));
                    }

                    entity.setData(data);
                    results.add(entity);
                }
                response.setResult(results);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }


}
