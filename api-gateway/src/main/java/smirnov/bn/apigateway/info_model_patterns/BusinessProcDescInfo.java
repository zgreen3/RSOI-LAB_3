package smirnov.bn.apigateway.info_model_patterns;

//import smirnov.bn.service_2.*;

public class BusinessProcDescInfo {
    private Integer bizProcId;
    private String bizProcName;
    private String bizProcDescStr;

    public Integer getBizProcId() {
        return bizProcId;
    }

    public void setBizProcId(Integer bizProcId) {
        this.bizProcId = bizProcId;
    }

    public String getBizProcName() {
        return bizProcName;
    }

    public void setBizProcName(String bizProcName) {
        this.bizProcName = bizProcName;
    }

    public String getBizProcDescStr() {
        return bizProcDescStr;
    }

    public void setBizProcDescStr(String bizProcDescStr) {
        this.bizProcDescStr = bizProcDescStr;
    }
}
