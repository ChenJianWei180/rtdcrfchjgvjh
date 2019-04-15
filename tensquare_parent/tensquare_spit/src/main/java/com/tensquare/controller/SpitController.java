package com.tensquare.controller;

import com.tensquare.pojo.Spit;
import com.tensquare.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *
     * 查找
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Spit> findAll(){
        return spitService.findAll();
    }

    /**
     * 根据ID查找
     * @param spitId
     * @return
     */
    @RequestMapping(value="/{spitId}",method=RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    /**
     * 增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result updateById(@RequestBody Spit spit,@PathVariable String spitId){
        spit.setId(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result updateById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据父Id查询
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageDate = spitService.findBparentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageDate.getTotalElements(),pageDate.getContent()) );
    }

    /**
     * 点赞
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        String userId = "111";

        if(redisTemplate.opsForValue().get("thumbup_"+spitId) != null){
            return new Result(true,StatusCode.REPERROR,"禁止重复点赞");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+spitId,1);
        return new Result(true,StatusCode.OK,"点赞成功");

    }
}
