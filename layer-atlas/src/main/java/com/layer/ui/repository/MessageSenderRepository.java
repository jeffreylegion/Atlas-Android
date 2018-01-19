package com.layer.ui.repository;


import android.content.Context;

import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.Conversation;
import com.layer.ui.message.response.ResponseSender;
import com.layer.ui.message.response.ChoiceResponseModel;

@SuppressWarnings("UnusedReturnValue")
public class MessageSenderRepository {

    private LayerClient mLayerClient;
    private Context mContext;

    public MessageSenderRepository(Context context, LayerClient layerClient) {
        mContext = context;
        mLayerClient = layerClient;
    }

    public boolean sendChoiceResponse(Conversation conversation, ChoiceResponseModel choiceResponse) {
        ResponseSender responseSender = new ResponseSender(mContext, mLayerClient);
        responseSender.setConversation(conversation);
        return responseSender.sendChoiceResponse(choiceResponse);
    }
}
