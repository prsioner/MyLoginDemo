package userlogindemo.demo.business.file;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import userlogindemo.demo.util.ResultUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        System.out.print("调用文件上传接口");
        if (!file.isEmpty()) {
            String saveFileName = file.getOriginalFilename();
            String fileloadSavePath = "D:\\webFileSystem\\upload\\";
            File saveFile = new File(fileloadSavePath + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return ResultUtils.dealFileResult(saveFile.getAbsolutePath(), " 上传成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResultUtils.dealFileResult(" e.getMessage()","上传失败");
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtils.dealFileResult( e.getMessage(),"上传失败" );
            }
        } else {
            return ResultUtils.dealFileResult("","上传失败，因为文件为空");
        }
    }

    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFiles")
    @ResponseBody
    public String uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }


    /**
     * 文件下载
     * 文件路劲path：D:\webFileSystem\providedownload\test_download.txt
     */

    //文件下载相关代码
    @RequestMapping("/download/yingyongbao_7242130.apk")
    public String testDownload(HttpServletResponse res) {
        System.out.print("文件下载接口.....");
        String fileName = "yingyongbao_7242130.apk";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //res.encodeURL()
        if (fileName != null) {
            //设置文件路径
            File file = new File("D:/webFileSystem/providedownload/yingyongbao_7242130.apk");
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                res.setContentType("application/force-download");// 设置强制下载不打开
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                long fileSize = file.length();
                res.setContentLengthLong(fileSize);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = res.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                System.out.print("文件不存在!");
            }
        }
        return "下载失败";
    }

}

