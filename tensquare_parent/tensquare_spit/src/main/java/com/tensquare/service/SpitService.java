package com.tensquare.service;

import com.tensquare.dao.SpitDao;
import com.tensquare.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private  IdWorker idWorker;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String spitId){
        return spitDao.findById(spitId).get();
    }

    public void save(Spit spit){
         spit.setId(idWorker.nextId()+"");
         spit.setPublishtime(new Date());//发布日期
         spit.setVisits(0);//浏览量
         spit.setShare(0);//分享数
         spit.setThumbup(0);//点赞数
         spit.setComment(0);//回复数
         spit.setState("1");//状态
        //如果当前添加的吐槽有父节点，则父节点数加一
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
         spitDao.save(spit);
    }

    public void update(Spit spit){
         spitDao.save(spit);
    }

    public  void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findBparentid(String parantid, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parantid,pageable);
    }

    public void thumbup(String spitId) {
        //影响效率
      /*  Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup( (spit.getThumbup()==null ? 0 : spit.getThumbup() ) + 1);
        spitDao.save(spit);*/
      //原生mongo
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");

    }

}
