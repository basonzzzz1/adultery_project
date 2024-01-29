package com.adultery_project.payload.request;

import java.util.UUID;

public class ChatRoomDeleteRequest {
    private UUID idChatRoom;

    public ChatRoomDeleteRequest() {
    }

    public ChatRoomDeleteRequest(UUID idChatRoom) {
        this.idChatRoom = idChatRoom;
    }

    public UUID getIdChatRoom() {
        return idChatRoom;
    }

    public void setIdChatRoom(UUID idChatRoom) {
        this.idChatRoom = idChatRoom;
    }
}