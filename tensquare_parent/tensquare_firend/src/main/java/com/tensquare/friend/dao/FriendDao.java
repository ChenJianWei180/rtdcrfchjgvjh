package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/13 10:47
 * @Description: tensquare_parent
 */
public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {
    public Friend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(nativeQuery = true,value = "update tb_friend set islike = ? where userid = ? and friendid = ?")
    public void updateIslike(String islike,String userid,String friendid);

    @Modifying
    @Query(nativeQuery = true,value = "delete from  tb_friend where userid = ? and friendid = ?")
    void deleteFriendid(String id, String friendid);
}
