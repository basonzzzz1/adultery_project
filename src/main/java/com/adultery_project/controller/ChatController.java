package com.adultery_project.controller;

import com.adultery_project.models.*;
import com.adultery_project.payload.request.*;
import com.adultery_project.repository.MessageRequestRepository;
import com.adultery_project.service.serviceImpl.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
    @Autowired
    CskhServiceImpl cskhService;
    @Autowired
    RotationValueServiceImpl rotationValueService;
    @Autowired
    ExchangePointsServiceImpl exchangePointsService;

    @PostMapping("/addPoint")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addPoint(@RequestBody InputRequest inputRequest) {
        User principal = userDetailsService.findByUsername(inputRequest.getUsername()).get();
        int pointPrincipal = principal.getPoint();
        principal.setPoint(pointPrincipal + inputRequest.getPoint());
        userDetailsService.save(principal);
        return ResponseEntity.ok("Thêm điểm thành công !");
    }

    @PostMapping("/deletePoint")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public ResponseEntity<?> deletePoint(@RequestBody InputRequest inputRequest) {
        User principal = userDetailsService.findByUsername(inputRequest.getUsername()).get();
        int pointPrincipal = principal.getPoint();
        if (pointPrincipal < 0 || inputRequest.getPoint() > pointPrincipal) {
            throw new RuntimeException("Số điểm hiện tại không đủ !");
        } else {
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
            if (chatRoomRequest.getPoint() > user.getPoint()) {
                throw new RuntimeException();
            } else {
                ChatRoom chatRoom = chatRoomService.save(new ChatRoom(LocalDateTime.now(), chatRoomRequest.getPassword()));
                template.convertAndSend("/chat/" + chatRoomRequest.getUsername() + "/queue/createChatRoom", chatRoom);
                return "phòng đã được tạo thành công id phòng là : " + chatRoom.getId() + " , password của phòng là : " + chatRoom.getPassword();
            }
        } else {
            throw new RuntimeException();
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
    public ChatRoom findByChatRoomId(@RequestBody UUID idRoom) {
        return chatRoomService.findById(idRoom);
    }

    @PostMapping("/checkChatRoom")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public ResponseEntity<?> findByChatRoomId(@RequestBody RequestChatRoomLogin requestChatRoomLogin) {
        User principal = userDetailsService.getLoggedInUser().get();
        ChatRoom chatRoom = chatRoomService.findById(requestChatRoomLogin.getId());
        if (chatRoomService.existsByPassword(requestChatRoomLogin.getPassword())) {
            if (chatRoom.getUser1() == null || chatRoom.getUser2() == null) {
                if (chatRoom.getUser1() == null) {
                    if (chatRoom.getUser2() == principal) {
                        return ResponseEntity.ok("Vào Phòng thành công !");
                    }
                    chatRoom.setUser1(principal);
                    chatRoomService.save(chatRoom);
                    return ResponseEntity.ok("Vào Phòng thành công !");
                } else {
                    if (chatRoom.getUser1() == principal) {
                        return ResponseEntity.ok("Vào Phòng thành công !");
                    }
                    chatRoom.setUser2(principal);
                    chatRoomService.save(chatRoom);
                    return ResponseEntity.ok("Vào Phòng thành công !");
                }
            } else if (chatRoom.getUser1() == principal || chatRoom.getUser2() == principal) {
                return ResponseEntity.ok("Vào Phòng thành công !");
            }
        }
        return ResponseEntity.ok("Vào Phòng không thành công !");
    }

    @PostMapping("/findAllMessageInChatRoomId")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public List<Message> finAllMessageInChatRoom(@RequestBody UUID id) {
        ChatRoom chatRoom = chatRoomService.findById(id);
        return messageService.findByChatRoom(chatRoom);
    }

    @PostMapping("/create/message")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public Message createMessage(@RequestBody Message message) throws IOException {
        ChatRoom chatRoom = chatRoomService.findById(message.getChatRoom().getId());
        User user = userDetailsService.getLoggedInUser().get();
        Message newMessage = new Message(message.getContent(), message.getImage(), LocalDateTime.now(), false, user, chatRoom);
        template.convertAndSend("/chat/user/queue/position-update", newMessage);
        return messageService.save(newMessage);
    }

    @GetMapping("/chatRoom/all")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public List<ChatRoom> getAllChatRoom() {
        return chatRoomService.getAll();
    }

    @GetMapping("/getAllCskhInUser")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public List<Cskh> getAllCskhUserAdmin() {
        User principal = userDetailsService.getLoggedInUser().get();
        return cskhService.initialStateAllChatFriends(principal.getId());
    }

    @PostMapping("/createCskh")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public ResponseEntity<String> createCskh(@RequestBody CskhRepuest cskhRepuest) {
        User principal = userDetailsService.getLoggedInUser().get();
        List<User> userList = userDetailsService.findAll();

        for (int i = 0; i < userList.size(); i++) {
            Set<Role> roleSet = userList.get(i).getRoles();
            for (Role role : roleSet) {
                if (role.getName() == ERole.ROLE_ADMIN) {
                    Cskh cskh = new Cskh(cskhRepuest.getContent(), LocalDateTime.now(), false, principal, userList.get(i));
                    cskhService.save(cskh);
                    template.convertAndSend("/chat/user/queue/cskh", cskh);
                }
            }
        }
        return ResponseEntity.ok("bạn đã gửi cskh thành công !");
    }

    @PostMapping("/createCskhAdmin")
    @PreAuthorize(" hasAnyRole('ADMIN')")
    public ResponseEntity<String> createCskhAdmin(@RequestBody CskhRequestAdmin cskhRequestAdmin) {
        User principal = userDetailsService.getLoggedInUser().get();
        User user = userDetailsService.findByUsername(cskhRequestAdmin.getUsername()).get();
        for (Role role : principal.getRoles()) {
            if (role.getName() == ERole.ROLE_ADMIN) {
                Cskh cskh = new Cskh(cskhRequestAdmin.getContent(), LocalDateTime.now(), false, principal, user);
                cskhService.save(cskh);
                template.convertAndSend("/chat/user/queue/cskh", cskh);
                return ResponseEntity.ok("bạn đã gửi cskh thành công !");
            }
        }
        throw new RuntimeException();
    }

    @PostMapping("/getAllCskhInUserDetail")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public List<Cskh> getAllCskhInUserDetail(@RequestBody CskhRequestAdmin cskhRequestAdmin) {
        User principal = userDetailsService.getLoggedInUser().get();
        User user = userDetailsService.findByUsername(cskhRequestAdmin.getUsername()).get();
        List<Cskh> cskhList = cskhService.getAllCskhInUser(principal.getId(), user.getId());
        return cskhList;
    }

    @GetMapping("/getAllCskhUser")
    @PreAuthorize("hasAnyRole('USER')")
    public List<Cskh> getAllCskhUser() {
        User principal = userDetailsService.getLoggedInUser().get();
        List<Cskh> cskhList = cskhService.findByFromUserOrToUser(principal, principal);
        return cskhList;
    }

    @PostMapping("/deleteChatRoom")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteChatRoom(@RequestBody ChatRoomDeleteRequest chatRoomDeleteRequest) {
        messageService.deleteAllByChatRoom_Id(chatRoomDeleteRequest.getIdChatRoom());
        chatRoomService.delete(chatRoomDeleteRequest.getIdChatRoom());
        return ResponseEntity.ok("xóa thành công !");
    }
    @GetMapping("/getValueSpin")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public List<RotationValue> getValueSpin(){
        return rotationValueService.getAll();
    }
    @PostMapping("/setListValueSpin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<RotationValue> setListValueSpin(@RequestBody List<RotationValue> rotationValueList){
        for (RotationValue rotationValue : rotationValueList) {
            rotationValueService.save(rotationValue);
        }
        return rotationValueList;
    }
    @GetMapping("/getAllExchangePoints")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ExchangePoints> getAllExchangePoints(){
        return exchangePointsService.getAll();
    }
    @PostMapping("/createExchangePoints")
    @PreAuthorize("hasAnyRole('USER') or hasAnyRole('ADMIN')")
    public ExchangePoints createExchangePoints(@RequestBody ExchangePoints exchangePoints){
        return exchangePointsService.save(exchangePoints);
    }
    @PostMapping("/deleteExchangePoints")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> deleteExchangePoints(@RequestBody ExchangePoints exchangePoints){
        exchangePointsService.delete(exchangePoints.getId());
        return ResponseEntity.ok("xóa thành công !");
    }
}