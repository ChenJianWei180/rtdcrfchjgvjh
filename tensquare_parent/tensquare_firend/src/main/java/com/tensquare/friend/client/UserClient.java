package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/14 20:37
 * @Description: tensquare_parent
 */
@FeignClient("tensquare-user")
public interface UserClient {

    @RequestMapping(value = "/user/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
    public void uodateFanscountAndFollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
