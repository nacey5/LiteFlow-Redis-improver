
package com.hzh.xml_rule.service.impl;

import com.hzh.liteflow_redis.service.RedisService;
import com.hzh.xml_rule.service.IClassFlowService;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dahuang
 * @version : ClassFlowService.java, v 0.1 2023-06-20 11:29 dahuang
 */

@Service
public class ClassFlowService implements IClassFlowService {

    @Resource
    private FlowExecutor flowExecutor;

    @Resource
    private RedisService redisService;

    @Override
    public String loadChainName(String chainName, String requestParam) {
        LiteflowResponse response = flowExecutor.execute2Resp(chainName, requestParam);
        if (response.isSuccess()){
            return chainName;
        }
        return "";
    }

    @Override
    public List<String> loadAllChains() {
        //获得所有的key，然后去加载对应的chain
        List<String> keys = redisService.keys();
        List<String> effectiveList=new ArrayList<>();
        keys.forEach(key->{
            try {
                loadChainName(key,"");
                effectiveList.add(key);
            }catch (Exception ignore){}
        });
        return effectiveList;
    }
}