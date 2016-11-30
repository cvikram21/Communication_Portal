package com.example.communicationportal;

import android.util.Log;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darshita on 11/29/16.
 */
public class ListMessages {
    public static List<String> listMessagesMatchingQuery(Gmail service, String userId,
                                                          String query) throws IOException {
        Log.d("expression","in ListMessages");
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

        List<Message> messages = new ArrayList<Message>();
        List<String> data = new ArrayList<String>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

        for (Message message : messages) {
            data.add(getMessage(service,"me",message.getId()));
            System.out.println(messages);
            System.out.println(message.toPrettyString());
        }

        return data;
    }
    public static String getMessage(Gmail service, String userId, String messageId)
            throws IOException {
        Message message = service.users().messages().get(userId, messageId).execute();

        System.out.println("Message snippet: " + message.getSnippet());
        String actualmessage = message.getSnippet().replaceAll(".[O]n.*", ":reply");
        return actualmessage;
    }
}
