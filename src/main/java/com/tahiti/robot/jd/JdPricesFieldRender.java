package com.tahiti.robot.jd;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import net.sf.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.FieldRenderName;
import com.geccocrawler.gecco.downloader.DownloaderContext;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.CustomFieldRender;

@FieldRenderName("jdPricesFieldRender")
public class JdPricesFieldRender implements CustomFieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean, Field field) {
		StringBuffer sb = new StringBuffer();
		
		ProductList jd = (ProductList) bean;
		Iterator<ProductBrief> briefIterator = jd.getDetails().iterator();
		while(briefIterator.hasNext()) {
			ProductBrief brief = briefIterator.next();
			sb.append("J_").append(brief.getCode()).append(",");
		}
		String skuIds = sb.toString();
		try {
			skuIds = URLEncoder.encode(skuIds, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = "http://p.3.cn/prices/mgets?skuIds=" + skuIds;
		HttpRequest subRequest = request.subRequest(url);
		try {
			HttpResponse subReponse = DownloaderContext.download(subRequest);
			String json = subReponse.getContent();
			List<JDPrice> prices = JSON.parseArray(json, JDPrice.class);
			beanMap.put(field.getName(), prices);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
