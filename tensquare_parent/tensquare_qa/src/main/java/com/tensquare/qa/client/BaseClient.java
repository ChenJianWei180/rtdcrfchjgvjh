package com.tensquare.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/12 18:42
 * @Description: tensquare_parent
 */
@FeignClient("tensquare-base")
public interface BaseClient {
    /**
     *
     * @author: I Need A Dr.
     * @date: 2019/4/12 18:46
     */
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
