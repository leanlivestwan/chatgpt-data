package cn.scalecraft.chatgpt.data.domain.openai.service.rule;

import cn.scalecraft.chatgpt.data.domain.openai.model.aggregates.ChatProcessAggregate;
import cn.scalecraft.chatgpt.data.domain.openai.model.entity.RuleLogicEntity;


public interface ILogicFilter<T> {

    RuleLogicEntity<ChatProcessAggregate> filter(ChatProcessAggregate chatProcess, T data) throws Exception;

}
