package com.example.communicationportal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;


public class EmailSearch implements IEmailSearch{
	ListEmails listEmails = new ListEmails();
	GetSet getSet = new GetSet();
	Set<String> set = new HashSet<String>();
	Set<String> set1 = new HashSet<String>();
	public void setSet(Set<String> set) {
		this.set = set;
	}
	public void emails(int ij) throws SSLHandshakeException{
		Pattern VALID = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", Pattern.CASE_INSENSITIVE);

		String urls[] = new String[set.size()];
		set.toArray(urls);
		for(int i = 0; i < set.size(); i++){
			Document doc = null;
			try {
				doc = Jsoup.connect(urls[i]).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(10*1000).get();
			}catch (IllegalArgumentException e){
				continue;
			}catch (SSLHandshakeException e){
				continue;
			}catch (SSLException e){
				continue;
			}catch (MalformedURLException e){
				continue;
			}catch (UnsupportedMimeTypeException e){
				continue;
			} catch (HttpStatusException e){
				continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String text = doc.body().text();
			Matcher matcher = VALID.matcher(text);
			while (matcher.find()) {
				set1.add(matcher.group());
			}
		}
		//listEmails.listEmails(set1);
		getSet.setEmailset1(set1);
        //line written by vikram
	}
}
//code written by teja
