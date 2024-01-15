package com.adultery_project.controller;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.models.Message;
import com.adultery_project.models.User;
import com.adultery_project.payload.request.ChatRoomRequest;
import com.adultery_project.payload.request.InputRequest;
import com.adultery_project.payload.request.MessageRequest;
import com.adultery_project.payload.request.RequestChatRoomLogin;
import com.adultery_project.repository.MessageRequestRepository;
import com.adultery_project.service.service.ChatRoomService;
import com.adultery_project.service.serviceImpl.ChatRoomServiceImpl;
import com.adultery_project.service.serviceImpl.MessageServiceImpl;
import com.adultery_project.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Chat")
public class ChatController {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    ChatRoomServiceImpl chatRoomService;
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    MessageRequestRepository messageRequestRepository;
    @PostMapping("/addPoint")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addPoint(@RequestBody InputRequest inputRequest){
        User principal = userDetailsService.findByUsername(inputRequest.getUsername()).get();
        int pointPrincipal = principal.getPoint();
        principal.setPoint(pointPrincipal + inputRequest.getPoint());
        userDetailsService.save(principal);
        return ResponseEntity.ok("Thêm điểm thành công !");
    }
    @PostMapping("/deletePoint")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public ResponseEntity<?> deletePoint(@RequestBody InputRequest inputRequest){
        User principal = userDetailsService.findByUsername(inputRequest.getUsername()).get();
        int pointPrincipal = principal.getPoint();
        if(pointPrincipal < 0 || inputRequest.getPoint() > pointPrincipal){
           throw new RuntimeException("Số điểm hiện tại không đủ !");
        }else {
            principal.setPoint(pointPrincipal - inputRequest.getPoint());
            userDetailsService.save(principal);
            return ResponseEntity.ok("thành công !");
        }
    }

    @PostMapping("/notification/create/chatroom")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public MessageRequest notificationCreateChatRoom(@RequestBody MessageRequest messageRequest) {
        template.convertAndSend("/chat/user/queue/notification", messageRequest);
        messageRequestRepository.save(messageRequest);
        return messageRequest;
    }
    @PostMapping("/create/chatRoom")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        if (userDetailsService.existsByUsername(chatRoomRequest.getUsername())) {
            User user = userDetailsService.findByUsername(chatRoomRequest.getUsername()).get();
            if (chatRoomRequest.getPoint() < user.getPoint()) {
                return "Số điểm hiện tại của người dùng" + chatRoomRequest.getUsername() + "không đủ để tạo phòng chat !";
            } else {
                ChatRoom chatRoom = chatRoomService.save(new ChatRoom(LocalDateTime.now(), chatRoomRequest.getPassword()));
                template.convertAndSend("/chat/" + chatRoomRequest.getUsername() + "/queue/createChatRoom", chatRoom);
                return "phòng đã được tạo thành công id phòng là : " + chatRoom.getId() + " , password của phòng là : " + chatRoom.getPassword();
            }
        } else {
            return "Tên tài khoản không đúng !";
        }
    }

    @GetMapping("/message/all")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public List<Message> getAllMessage() {
        return messageService.finAllByLoggerInUser();
    }

    @GetMapping("/messageRequest/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<MessageRequest> getAllMessageRequest() {
        return messageRequestRepository.findAll();
    }
    @PostMapping("/findByChatRoomId")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public ChatRoom findByChatRoomId(@RequestBody UUID idRoom){
                return chatRoomService.findById(idRoom);
    }
    @PostMapping("/checkChatRoom")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public ResponseEntity<?> findByChatRoomId(@RequestBody RequestChatRoomLogin requestChatRoomLogin){
        User principal = userDetailsService.getLoggedInUser().get();
        ChatRoom chatRoom = chatRoomService.findById(requestChatRoomLogin.getId());
        if(chatRoomService.existsByPassword(requestChatRoomLogin.getPassword())){
            if(chatRoom.getUser1() == null || chatRoom.getUser2() == null){
                if(chatRoom.getUser1() == null){
                    if(chatRoom.getUser2() == principal){
                        return ResponseEntity.ok("Vào Phòng thành công !");
                    }
                    chatRoom.setUser1(principal);
                    chatRoomService.save(chatRoom);
                    return ResponseEntity.ok("Vào Phòng thành công !");
                }else {
                    if(chatRoom.getUser1() == principal){
                        return ResponseEntity.ok("Vào Phòng thành công !");
                    }
                    chatRoom.setUser2(principal);
                    chatRoomService.save(chatRoom);
                    return ResponseEntity.ok("Vào Phòng thành công !");
                }
            }else if(chatRoom.getUser1() == principal || chatRoom.getUser2() == principal){
                return ResponseEntity.ok("Vào Phòng thành công !");
            }
        }
        return ResponseEntity.ok("Vào Phòng không thành công !");
    }
    @PostMapping("/findAllMessageInChatRoomId")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public List<Message> finAllMessageInChatRoom(@RequestBody UUID id){
        ChatRoom chatRoom = chatRoomService.findById(id);
        return messageService.findByChatRoom(chatRoom);
    }
    @PostMapping("/create/message")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public Message createMessage(@RequestBody Message message){
        template.convertAndSend("/chat/user/queue/position-update",message);
        ChatRoom chatRoom = chatRoomService.findById(message.getChatRoom().getId());
        User user = userDetailsService.findByUsername(message.getUser().getUsername()).get();
        Message newMessage = new Message(message.getContent() ,LocalDateTime.now() ,false , user ,chatRoom);
        messageService.save(newMessage);
        return message;
    }
    @GetMapping("/chatRoom/all")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public List<ChatRoom> getAllChatRoom() {
        return chatRoomService.getAll();
    }

}