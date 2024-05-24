package cn.scalecraft.chatgpt.data.domain.weixin.service;

import cn.scalecraft.chatgpt.data.domain.weixin.model.entity.UserBehaviorMessageEntity;


public interface IWeiXinBehaviorService {

    String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity);

}
