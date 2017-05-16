package com.tahiti.robot.jd;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;

public class App {
	public static void main(String[] args) {
		//先获取分类列表
		HttpGetRequest start = new HttpGetRequest("https://www.jd.com/allSort.aspx");
		start.setCharset("GBK");
		GeccoEngine.create().classpath("com.tahiti.robot.jd")
		//开始抓取的页面地址
				.start(start)
				//开启几个爬虫线程
				.thread(1).loop(false).debug(false)
				//单个爬虫每次抓取完一个请求后的间隔时间
				.interval(2000).run();

		//分类列表下的商品列表采用3线程抓取
		GeccoEngine.create().classpath("com.tahiti.robot.jd")
		//开始抓取的页面地址
				.start(AllSortPipeline.sortRequests)
				//开启几个爬虫线程
				.thread(5).debug(false)
				//单个爬虫每次抓取完一个请求后的间隔时间
				.interval(2000).loop(false).start();

		//		HttpGetRequest start = new HttpGetRequest("https://item.jd.com/4116260.html");
		//		start.setCharset("GBK");
		//		GeccoEngine.create().classpath("com.tahiti.robot.jd")
		//		.start(start)
		//		.thread(1).interval(2000)
		//		.loop(false)
		//		.mobile(false).start();
	}
}
