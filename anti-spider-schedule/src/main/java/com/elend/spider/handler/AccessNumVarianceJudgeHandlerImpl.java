package com.elend.spider.handler;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elend.p2p.Result;
import com.elend.p2p.constant.ResultCode;

/**
 * 访问频率集中度校验（方差）
 * @author mgt
 * @date 2016年8月8日
 *
 */
public class AccessNumVarianceJudgeHandlerImpl implements JudgeHandler {
    
    private final static Logger logger = LoggerFactory.getLogger(AccessNumVarianceJudgeHandlerImpl.class);

    /**
     * 限制数量，根据具体的用处注入
     */
    private BigDecimal limitVariance;
    
    /**
     * 访问次数在限制值以上，才校验方差
     */
    private long accessNumLimit;
    
    public void setLimitVariance(BigDecimal limitVariance) {
        this.limitVariance = limitVariance;
    }
    

    public void setAccessNumLimit(long accessNumLimit) {
        this.accessNumLimit = accessNumLimit;
    }


    @Override
    public Result<String> judge(SpiderJudgeParam param) {
        
        BigDecimal varience=calAccessNumVarience(param.getAccessNumList());
        
        if(varience == null || varience.compareTo(BigDecimal.ZERO) < 0) {
            logger.info(" 访问频率集中度校验（方差）不检验，方差为空, ip:{}, getAccessNumVariance:{}", param.getIp(), varience);
            return new Result<>(ResultCode.SUCCESS);
        }
        
        //算出平均方差
        if(param.getAccessNum() > 0) {
            varience = varience.divide(new BigDecimal(param.getAccessNum()), 6, BigDecimal.ROUND_HALF_UP);
        }
        
        if(accessNumLimit > param.getAccessNum()) {
            logger.info("访问次数过小，不校验方差, ip:{}, getAccessNumVariance:{}，accessNumLimit：{}， totalAccessNum:{} ", param.getIp(), varience, accessNumLimit, param.getAccessNum());
            return new Result<>(ResultCode.SUCCESS);
        }
        
        logger.info("访问频率集中度校验，ip:{}, limitVariance:{}, actualNum:{}", param.getIp(), limitVariance, varience);
        if(varience.compareTo(limitVariance) <= 0) {
            logger.error("访问频率集中度校验不通过，ip:{}, limitVariance:{}, actualNum:{}", param.getIp(), limitVariance, varience);
            return new Result<>(ResultCode.FAILURE, null, "访问的时间间隔太固定，限制方差：" + limitVariance + "，实际方差：" + varience);
        }
        return new Result<>(ResultCode.SUCCESS);
    }
    
    /**
     * 计算访问频率方差
     * 时间跨度(1小时)，每条记录为1分钟的访问数据
     * @param accessNumList
     * 访问次数列表
     * @return
     */
    public static BigDecimal calAccessNumVarience(List<Long> accessNumList){
        if(accessNumList==null||accessNumList.size()<=0){
            logger.error("访问频率数据为空，计算方差失败...");
            return new BigDecimal("-1");
        }
        if(accessNumList.size()>60){
            logger.error("访问频率数据大于60个，数据异常...size:{}",accessNumList.size());
            accessNumList=accessNumList.subList(0, 60);
        }else if(accessNumList.size()<60){//不足60条的数据补0
            for(int i=0;i<60-accessNumList.size();i++){
                accessNumList.add(0L);
            }
        }
        BigDecimal sum=BigDecimal.ZERO;
        for(long accessNum:accessNumList){
            sum=sum.add(new BigDecimal(accessNum));
        }
        BigDecimal avg=sum.divide(new BigDecimal(60),6, BigDecimal.ROUND_HALF_UP);
        double variance=0D;
        for(long accessNum:accessNumList){
            variance+=Math.pow((new BigDecimal(accessNum).subtract(avg)).doubleValue(),2);
        }
        return new BigDecimal(variance).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

}
