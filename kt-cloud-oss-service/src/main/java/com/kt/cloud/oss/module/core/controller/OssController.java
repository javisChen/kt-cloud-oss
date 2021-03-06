package com.kt.cloud.oss.module.core.controller;//package com.kt.cloud.oss.module.demo.controller;
//
import com.kt.cloud.oss.module.core.dto.OssUploadReqDTO;
import com.kt.cloud.oss.module.core.dto.OssUploadRespDTO;
import com.kt.cloud.oss.module.core.service.OssService;
import com.kt.component.dto.SingleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "OSS操作")
@RequestMapping("/v1/oss")
@RestController
public class OssController {

    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @ApiOperation(value = "上传OSS")
    @PostMapping("/upload")
    public SingleResponse<OssUploadRespDTO> upload(OssUploadReqDTO ossUploadDTO) {
        return SingleResponse.ok(ossService.upload(ossUploadDTO));
    }
}
