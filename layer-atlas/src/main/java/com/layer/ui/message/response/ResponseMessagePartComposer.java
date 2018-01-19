package com.layer.ui.message.response;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.MessageItemStatusViewModel;
import com.layer.ui.message.MessagePartUtils;
import com.layer.ui.util.json.AndroidFieldNamingStrategy;

@SuppressWarnings("WeakerAccess")
public class ResponseMessagePartComposer {

    public MessagePart buildResponseMessagePart(LayerClient layerClient, ResponseModel responseModel) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new AndroidFieldNamingStrategy()).create();

        String rootMimeTpe = MessagePartUtils.getAsRoleRoot(
                MessageItemStatusViewModel.RESPONSE_ROOT_MIME_TYPE);

        ResponseMetadata responseMetadata = createResponseMetadata(responseModel);
        return layerClient.newMessagePart(rootMimeTpe, gson.toJson(responseMetadata).getBytes());
    }

    private ResponseMetadata createResponseMetadata(ResponseModel responseModel) {
        ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setMessageIdToRespondTo(responseModel.getMessageIdToRespondTo().toString());
        responseMetadata.setNodeIdToRespondTo(responseModel.getNodeIdToRespondTo());

        responseMetadata.setParticipantData(responseModel.getParticipantData());
        return responseMetadata;
    }
}
