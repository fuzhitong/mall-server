package cn.enjoy.sys.controller;

import cn.enjoy.sys.fs.FastDFSClientService;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zw on 2017/4/25.
 */

@RestController
public class CommonController extends BaseController {

    @Resource
    private FastDFSClientService dfsClient;

    @RequestMapping(value = "/api/upload", method = RequestMethod.POST)
    public HttpResponseBody<Map<String, Object>> upload(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadFile(file);
        Map<String, Object> result = new HashMap<>();
        result.put("fileUrl", imgUrl);
        return HttpResponseBody.successResponse("上传成功", result);
    }

    @RequestMapping(value = "/api/batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponseBody<List<Map<String, String>>> handleFileUpload(HttpServletRequest request) throws Exception {
        List<Map<String, String>> urls = new ArrayList<>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                String imgUrl = dfsClient.uploadFile(file);
                String fileName = file.getOriginalFilename();
                String fileIndex = i + "";
                Map<String, String> img = new HashMap<>();
                img.put("imgUrl", imgUrl);
                img.put("dfsPath", dfsClient.getDfsPath());
                img.put("fileName", fileName);
                img.put("fileIndex", fileIndex);
                urls.add(img);
            }
        }
        return HttpResponseBody.successResponse("上传成功", urls);
    }

    @RequestMapping(value = "/404.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object error404() {
        return "大写的404";
    }

    @RequestMapping(value = "/500.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object error500() {
        return "大写的500";
    }

}

