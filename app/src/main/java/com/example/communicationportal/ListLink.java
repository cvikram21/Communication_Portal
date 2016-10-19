package com.example.communicationportal;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

/**
 * Example program to list links from a URL.
 */
public class ListLink {
	static Document doc;
	static int count = 0;
    public void fetchUrls() throws IOException{
    	GetSet getSet = new GetSet();
    	EmailSearch emailSearch = new EmailSearch();
        WebSiteDetails webSiteDetails = new WebSiteDetails();
        String placeName = getSet.getPlaceName();
        Log.d("placeName in ListLink", placeName);
        String url = webSiteDetails.getWebSite(placeName);
        Log.d("URL",url);
        String message = "Problem occured while fetching urls";
		try {
			
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(10*1000).get();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			if(count < 5){
			fetchUrls();
			//System.out.println(count);
			count++;
			}
			else
			System.out.println(message);
		}
        Elements links = doc.select("a[href]");
        

        int i = 0;
        Set<String> set = new HashSet<String>();
        String urlLinks[] = new String[links.size()];
        for (Element link : links) {
        	//set.add(link.attr("abs:href"));
            urlLinks[i] = link.attr("abs:href");
            if(urlLinks[i].contains("about")||urlLinks[i].contains("contact")||urlLinks[i].contains("support")||urlLinks[i].contains("help")){
            	set.add(urlLinks[i]);
            }
            i++;
        }
        getSet.setUrls(urlLinks);
        String tempUrls[] = getSet.getUrls();
        for (int j = 0; j < tempUrls.length; j++ ){
        	//System.out.println(j+":"+tempUrls[j]);
        }
        System.out.println(set);
        emailSearch.setSet(set);
        emailSearch.emails();
    }
}