package com.teros.api_control_manager.service.response;

import com.teros.api_control_manager.model.response.CommonResult;
import com.teros.api_control_manager.model.response.ListResult;
import com.teros.api_control_manager.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    public enum CommonResponse {
        SUCCESS(0, "successed"),
        FAIL(-1, "failed");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        getResult(result, true);
        return result;
    }

    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        getResult(result, true);
        return result;
    }

    // 성공/실패 결과 처리
    public CommonResult getResult(boolean isSuccess) {
        return setResult(null, isSuccess);
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private CommonResult getResult(CommonResult result, boolean isSuccess) {
        return setResult(result, isSuccess);
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private CommonResult setResult(CommonResult paramResult, boolean isSuccess) {
        CommonResult result = new CommonResult();
        if(paramResult != null)
            result = paramResult;

        result.setSuccess(isSuccess);
        result.setReasonCode(CommonResponse.SUCCESS.getCode());
        result.setReasonMessage(CommonResponse.SUCCESS.getMsg());

        if (isSuccess == false) {
            result.setReasonCode(CommonResponse.FAIL.getCode());
            result.setReasonMessage(CommonResponse.FAIL.getMsg());
        }
        return result;
    }

}
