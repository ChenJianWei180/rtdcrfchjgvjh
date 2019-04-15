package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/13 10:47
 * @Description: tensquare_parent
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String>, JpaSpecificationExecutor<Friend> {
    public NoFriend findByUseridAndFriendid(String userid, String friendid);
}
