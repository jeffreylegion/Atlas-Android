package com.layer.ui.repository;


import android.content.Context;

import com.layer.sdk.LayerClient;
import com.layer.ui.R;
import com.layer.ui.identity.IdentityFormatter;
import com.layer.ui.identity.IdentityFormatterImpl;
import com.layer.ui.message.response.ResponseSender;
import com.layer.ui.response.ChoiceResponse;
import com.layer.ui.util.Log;

public class MessageSenderRepository {

    private LayerClient mLayerClient;
    private Context mContext;
    private IdentityFormatter mIdentityFormatter;

    public MessageSenderRepository(Context context, LayerClient layerClient) {
        mContext = context;
        mLayerClient = layerClient;
        mIdentityFormatter = new IdentityFormatterImpl(context);
    }

    public boolean sendChoiceResponse(ChoiceResponse choiceResponse) {
        ResponseSender responseSender = new ResponseSender(mContext, mLayerClient);
        String userName = mIdentityFormatter.getDisplayName(mLayerClient.getAuthenticatedUser());
        String statusText = mContext.getString(R.string.response_message_status_text, userName, choiceResponse.getChoiceText(), "TODO Label name");

        Log.d("ZZZ: nodeId: " + choiceResponse.getNodeIdToRespondTo());
        Log.d("ZZZ: response name: " + choiceResponse.getResponseName());
        Log.d("ZZZ: choice text: " + choiceResponse.getChoiceText());
        Log.d("ZZZ: choice id: " + choiceResponse.getChoiceId());
        Log.d("ZZZ: statusText: " + statusText);
        return false;
//        return responseSender.sendChoiceResponse(messageToRespondTo, nodeIdToRespondTo, choice, statusText);
    }
}
