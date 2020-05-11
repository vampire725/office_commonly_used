//package com.sinosoft.ap.commandcenter.socket.web;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.sinosoft.ap.commandcenter.socket.service.SocketIoService;
//
//@Controller
//@RestController
//@RequestMapping("/socketWeb")
//public class SocketIoController {
//
//	@Autowired
//	private SocketIoService service;
//	 
//	@RequestMapping("sendAdvertInfoMsg")
//    public void sendAdvertInfoMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        try {
//        	service.sendMessageToOneClient("ZYLPC", "sendAddItg", "推送的内容");
//            if(service.getServer() != null){
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//	
//	//停止socket服务
//    @RequestMapping("stopServer")
//    public void stopServer(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        try {
//            if(service.getServer() == null){
//                service.stopServer();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
