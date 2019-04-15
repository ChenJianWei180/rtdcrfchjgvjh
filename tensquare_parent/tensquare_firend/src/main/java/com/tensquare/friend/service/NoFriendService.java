package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: I Need A Dr.
 * @Date: 2019/4/13 10:46
 * @Description: tensquare_parent
 */
@Service
@Transactional
public class NoFriendService {
    @Autowired
    private NoFriendDao noFriendDao;

    public int addNoFriend(String userid, String friendid) {
        //先判断是否存在重复数据
        NoFriend nofriend = noFriendDao.findByUseridAndFriendid(userid,friendid);
        if (nofriend != null) {
            return 0;
        }
        //添加单项好友 userid 到 friend,islike为0
        nofriend = new NoFriend();
        nofriend.setUserid(userid);
        nofriend.setFriendid(friendid);
        noFriendDao.save(nofriend);
       return 1;
    }
}
