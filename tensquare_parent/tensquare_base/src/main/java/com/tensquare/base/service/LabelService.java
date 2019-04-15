package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return  labelDao.findById(id).get();
    }

    public void  save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void  update(Label label){
        labelDao.save(label);
    }

    public void  deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        /**
         *
         * @param root 根对象
         * @param query 查询关键字
         * @param cb 用来封装条件对象
         *
         */
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new 一个集合。存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    /**
                     * 查询标题信息
                     *  where labelname like '%'labelname'%'
                     */
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),"%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    /**
                     * 查询状态
                     * where state = "1"
                     */
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr); //and 等于 where labelname like '%'labelname'%' and  where state = "1"

            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装分页对象
        Pageable pageable= PageRequest.of(page-1,size);
        return  labelDao.findAll(
                new Specification<Label>() {
                    @Override
                    public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        //new 一个集合。存放所有的条件
                        List<Predicate> list = new ArrayList<>();
                        if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                            /**
                             * 查询标题信息
                             *  where labelname like '%'labelname'%'
                             */
                            Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                            list.add(predicate);
                        }
                        if(label.getState()!=null && !"".equals(label.getState())){
                            /**
                             * 查询状态
                             * where state = "1"
                             */
                            Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                            list.add(predicate);
                        }
                        Predicate[] parr = new Predicate[list.size()];
                        parr = list.toArray(parr);
                        return cb.and(parr); //and 等于 where labelname like '%'labelname'%' and  where state = "1"

                    }
                },pageable);
    }
}