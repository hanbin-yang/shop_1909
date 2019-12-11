package com.sifo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sifo.entity.Goods;
import com.sifo.entity.ResultData;
import com.sifo.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    //private String uploadPath="D:/uploadpath/images";

    @Reference
    IGoodsService goodsService;


    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @RequestMapping("list")
    public String list(ModelMap modelMap){

        //IPage<Goods> goodsIPage = goodsService.getGoodsList();

        List<Goods> goodsList=goodsService.getList();

        modelMap.addAttribute("goodsList",goodsList);
        return "goods_list";
    }


    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping("uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){

        //使用FastDFS上传图片
        StorePath resultPath=null;
        try {
            resultPath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null
            );
            System.out.println("上传结果:"+resultPath.getFullPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData("http://www.img.com:8080/"+resultPath.getFullPath());

/*        //将原始文件名重命名为 UUID+"_"+原始文件名
        String fileName= UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
       //设置上传到什么路径,这里为 D:/uploadpath/images
        String path=uploadPath + "/" + fileName;
        try(
                InputStream in = file.getInputStream();
                OutputStream out=new FileOutputStream(path);
        ) {
            IOUtils.copy(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData(path);*/
    }


    /**
     * 图片回显
     */
/*    @RequestMapping("showImg")
    @ResponseBody
    public void showImg(String imgPath, HttpServletResponse response){

        try(
                //把带过来的路径进行输入流操作
                InputStream in= new FileInputStream(imgPath);

                //设置响应输出流
                OutputStream out=response.getOutputStream();
        ) {
            //输出到调用者即img标签去显示图片
            IOUtils.copy(in,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @RequestMapping("insert")
    public String insert(Goods goods){

        goodsService.insert(goods);

        return "redirect:/goods/list";
    }

}
