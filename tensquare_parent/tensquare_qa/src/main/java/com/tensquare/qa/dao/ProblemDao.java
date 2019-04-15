package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问答列表
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem , tb_pl where id = problemid and labelid = ? order by replytime desc ")
    public Page<Problem> newlist(String id, Pageable pageable);

    /**
     * 最热问答列表
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem , tb_pl where id = problemid and labelid = ? order by reply desc ")
    public Page<Problem> hotlist(String id,Pageable pageable);

    /**
     * 等待问答列表
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem , tb_pl where id = problemid and labelid = ? and reply=0 order by createtime desc ")
    public Page<Problem> waitlist(String id,Pageable pageable);

}
