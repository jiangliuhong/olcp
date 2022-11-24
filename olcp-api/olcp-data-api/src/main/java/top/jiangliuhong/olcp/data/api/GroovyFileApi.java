package top.jiangliuhong.olcp.data.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jiangliuhong.olcp.data.bean.GroovyFileVO;
import top.jiangliuhong.olcp.data.service.GroovyFileService;

import java.util.Base64;

@Tag(name = "groovy", description = "Groovy文件API")
@RestController
@RequestMapping("/api/v1/data/groovy")
public class GroovyFileApi {

    @Autowired
    private GroovyFileService fileService;


    @PostMapping
    public String save(@RequestBody GroovyFileVO fileVO) {
        this.decodeScript(fileVO);
        fileService.save(fileVO);
        return fileVO.getId();
    }

    @PutMapping
    public void update(@RequestBody GroovyFileVO fileVO) {
        this.decodeScript(fileVO);
        fileService.update(fileVO);
    }

    private void decodeScript(GroovyFileVO fileVO) {
        if (StringUtils.isNotBlank(fileVO.getScript())) {
            byte[] decode = Base64.getDecoder().decode(fileVO.getScript());
            fileVO.setScript(new String(decode));
        }
    }

}
