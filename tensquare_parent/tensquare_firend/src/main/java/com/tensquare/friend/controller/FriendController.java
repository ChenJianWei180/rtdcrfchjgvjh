package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import com.tensquare.friend.service.NoFriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/13 10:45
 * @Description: tensquare_parent
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private NoFriendService nofriendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        //判断是否登录
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims != null) {
            return new Result(false,StatusCode.LOGINERROR,"权限不足！");
        }
        //判断添加好友还是添加非好友
        if (type != null && !"".equals(type)) {
            if(type.equals("1")){
                //添加好友
                int flag =  friendService.addFriend(claims.getId(),friendid);
                if(flag==0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }else{
                    userClient.uodateFanscountAndFollowcount(claims.getId(),friendid,1);
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }else if(type.equals("2")){
                //添加非好友
                int flag =  nofriendService.addNoFriend(claims.getId(),friendid);
                if(flag==0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                }else{
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }
            return new Result(false,StatusCode.ERROR,"参数异常");
        }else{
            return new Result(false,StatusCode.ERROR,"参数异常");
        }
    }
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //判断是否登录
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims != null) {
            return new Result(false,StatusCode.LOGINERROR,"权限不足！");
        }
        friendService.deleteFriend(claims.getId(),friendid);
        userClient.uodateFanscountAndFollowcount(claims.getId(),friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
