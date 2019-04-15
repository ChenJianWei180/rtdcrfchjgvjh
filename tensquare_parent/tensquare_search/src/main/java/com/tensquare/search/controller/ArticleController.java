package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/8 13:51
 * @Description: tensquare_parent
 */
@RestController
@RequestMapping(value = "/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findById(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData = articleService.findById(key,page,size);
        return new Result(true, StatusCode.OK,"查找成功", new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
