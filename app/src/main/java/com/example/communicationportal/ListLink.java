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

import javax.net.ssl.SSLHandshakeException;

/**
 * Example program to list links from a URL.
 */
public class ListLink implements IListLink{
	static Document doc;
	static int count = 0;
    String url ;
    GetSet getSet = new GetSet();
    EmailSearch emailSearch = new EmailSearch();
    public void fetchUrls1(String u){
            url = u;
        try {
            fetchUrls();
        }catch (IOException e){
            Log.d("EXCEPTION fetchUrls1", e.getMessage());
        }
    }


    //    SharedPreferences.Editor editor = sharedPreferences.edit();
    public void fetchUrls() throws IOException{
    	String placeName = getSet.getPlaceName();
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        Log.d("URL",url);
        final String message = "Problem occured while fetching urls";
		//code from here written by vikram
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    Log.d("In runnable", "run method");
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(10*1000).get();
                } catch (SSLHandshakeException e){

                }catch (SocketTimeoutException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e){

                }
                //till here
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
                //following code added by teja
                getSet.setUrls(urlLinks);
                String tempUrls[] = getSet.getUrls();
                System.out.println(set);
                emailSearch.setSet(set);
                //till here
                try {
                    int status = 0;
                    emailSearch.emails(status);
                }catch (SSLHandshakeException e){

                }
            }
        }).start();

    }
}
//code written by ayesha