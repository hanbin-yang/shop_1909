package com.sifo.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sifo.entity.Goods;
import com.sifo.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {


    @Autowired
    SolrClient solrClient;

    /**
     *  添加一个商品索引库
     * @param goods
     * @return
     */
    @Override
    public int insertSolr(Goods goods) {

        SolrInputDocument document=new SolrInputDocument();

        document.addField("id",goods.getId());
        document.addField("subject",goods.getSubject());
        document.addField("info",goods.getInfo());
        document.addField("image",goods.getFmUrl());
        document.addField("price", goods.getPrice().doubleValue());
        document.addField("save",goods.getSave());

        try {
            solrClient.add(document);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据关键字分页查询索引库集合
     * @param iPage，分页信息
     * @param keyword，关键字
     * @return
     */
    @Override
    public IPage<Goods> querySolr(Page<Goods> iPage,String keyword) {

        SolrQuery query=new SolrQuery();

        if(keyword!=null && !"".equals(keyword)){
            query.setQuery("subject:"+keyword+" || info:"+keyword);
        }else {
            query.setQuery("*:*");
        }

        //初始分页 limit(0,10)
        if(iPage!=null){
            query.setStart((int) (iPage.getCurrent()-1));
            query.setRows((int) iPage.getSize());
        }else {
            query.setStart(0);
            query.setRows(10);
        }


        //query当前页是从0开始，所以要+1操作
        IPage<Goods> page=new Page<>(query.getStart()+1,query.getRows());

        //设置搜索的高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        query.addHighlightField("subject");

        query.setHighlightSnippets(3);//设置折叠的次数
        query.setHighlightFragsize(10);//设置这段的关键字前后摘取字数大小

        try {
            QueryResponse response=solrClient.query(query);
            SolrDocumentList results = response.getResults();

            //符合关键字的记录条数
            long numFound = results.getNumFound();

            page.setTotal(numFound);

            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            List<Goods> list =new ArrayList<>();

            for (SolrDocument document : results) {
                Goods goods=new Goods();
                //从索引库中添加商品
                goods.setId(Integer.valueOf(document.get("id").toString()));
                goods.setSubject((String) document.get("subject"))
                        //.setInfo((String) document.get("info"))
                        .setFmUrl((String) document.get("image"))
                        .setPrice(BigDecimal.valueOf((Double) document.get("price")))
                        .setSave((Integer) document.get("save"));

                if(highlighting.containsKey(goods.getId()+"")){
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");
                    List<String> subject = stringListMap.get("subject");
                    if(subject!=null)
                    {
                        goods.setSubject(subject.get(0));
                    }
                }

                //把商品对象添加到集合
                list.add(goods);
            }
            //把商品集合添加到分页对象
            page.setRecords(list);
            return page;

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查询不到，返回null
        return null;
    }
}
