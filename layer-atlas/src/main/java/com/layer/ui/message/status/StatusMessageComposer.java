package com.layer.ui.message.status;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.layer.sdk.LayerClient;
import com.layer.sdk.messaging.MessagePart;
import com.layer.ui.message.MessageItemStatusViewModel;
import com.layer.ui.message.MessagePartUtils;
import com.layer.ui.util.json.AndroidFieldNamingStrategy;

public class StatusMessageComposer {

    public MessagePart buildStatusMessagePart(LayerClient layerClient, String status) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new AndroidFieldNamingStrategy()).create();

        String statusMimeType = MessagePartUtils.getAsRoleWithParentId(
                MessageItemStatusViewModel.STATUS_ROOT_MIME_TYPE, "status", null, "root");
        StatusMetadata statusMetadata = new StatusMetadata();
        statusMetadata.setText(status);
        return layerClient.newMessagePart(statusMimeType, gson.toJson(statusMetadata).getBytes());
    }
}
