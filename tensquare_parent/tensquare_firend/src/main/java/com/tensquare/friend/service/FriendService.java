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
public class FriendService {
    @Autowired
    private  FriendDao friendDao;

    @Autowired
    private  NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        //先判断是否存在重复数据
        Friend friend = friendDao.findByUseridAndFriendid(userid,friendid);
        if (friend != null) {
            return 0;
        }
        //添加单项好友 userid 到 friend,islike为0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //查找friend到userid是否存在数据，若有把双方的islike都改为1
        if (friendDao.findByUseridAndFriendid(friendid,userid)!=null) {
            friendDao.updateIslike("1",friendid,userid);
            friendDao.updateIslike("1",userid,friendid);
        }
       return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriendid(userid,friendid);
        friendDao.updateIslike("0",friendid,userid);
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

    }
}
