//package com.sinosoft.ap.system.socketio.service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.corundumstudio.socketio.listener.DisconnectListener;
//import com.sinosoft.ap.system.socketio.domain.MessageEntity;
//
//@Component
//public class SocketIoService {
//	
//	@Value("${wss.ip}")
//	private String sockerIp;
//	
//	@Value("${wss.port}")
//	private String socketPort;
//	
//	@Value("${}")
//	private long maxHttpContentLength;
//	
//	static SocketIOServer server;
//	static Map<String, SocketIOClient> clientsMap = new HashMap<String, SocketIOClient>();
//
//	public void startServer() throws InterruptedException, IOException{
//		Properties prop = new Properties();
//		prop.load(in);
//		String socketServerIp = prop.getProperty("socketServerIp").trim();
//		int socketServerPort = Integer.parseInt(prop.getProperty("socketServerPort").trim());
//		Configuration config = new Configuration();
//		config.setHostname(socketServerIp);
//		config.setPort(socketServerPort);
//		config.setMaxFramePayloadLength(1024 * 1024);
//        config.setMaxHttpContentLength(1024 * 1024);
//        server = new SocketIOServer(config);
//        
//        //监听广告推送事件，sendAddItg为事件名称，自定义
//        server.addEventListener("sendAddItg", MessageEntity.class, new DataListener<MessageEntity>(){
//			@Override
//			public void onData(SocketIOClient client, MessageEntity data,
//					AckRequest ackSender) throws Exception {
//				String sa = client.getRemoteAddress().toString();
//                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
//                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
////                System.out.println(clientIp+"：客户端：************"+data);
//			}
//           
//        });
//        
//        server.addEventListener("sendEditItg", MessageEntity.class, new DataListener<MessageEntity>(){
//			@Override
//			public void onData(SocketIOClient client, MessageEntity data,
//					AckRequest ackSender) throws Exception {
//				String sa = client.getRemoteAddress().toString();
//                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
//                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
////                System.out.println(clientIp+"：客户端：************"+data);
//			}
//           
//        });
//        
//        server.addEventListener("sendManageItg", MessageEntity.class, new DataListener<MessageEntity>(){
//			@Override
//			public void onData(SocketIOClient client, MessageEntity data,
//					AckRequest ackSender) throws Exception {
//				String sa = client.getRemoteAddress().toString();
//                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
//                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
////                System.out.println(clientIp+"：客户端：************"+data);
//			}
//           
//        });
//        
//        server.addEventListener("recManageItg", MessageEntity.class, new DataListener<MessageEntity>(){
//			@Override
//			public void onData(SocketIOClient client, MessageEntity data,
//					AckRequest ackSender) throws Exception {
//				String sa = client.getRemoteAddress().toString();
//                String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端连接的ip
//                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
//			}
//           
//        });
//        
//        //添加客户端连接事件
//        server.addConnectListener(new ConnectListener() {
//		@Override
//		public void onConnect(SocketIOClient client) {
//		    String sa = client.getRemoteAddress().toString();
//		    String clientIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
//		    System.out.println(clientIp+"-------------------------"+"客户端已连接");
//		    Map params = client.getHandshakeData().getUrlParams();
//		    
//		    //获取客户端连接的uuid参数
//		    Object object = params.get("uuid");
//		    String uuid = "";
//		    if(object != null){
//		        uuid = ((List<String>)object).get(0);
//		        //将uuid和连接客户端对象进行绑定
//		        clientsMap.put(uuid,client);
//		    }
//		    //给客户端发送消息
////		    client.sendEvent("sendAddItg",clientIp+"客户端你好，我是服务端，有什么能帮助你的？");
//		}
//        });
//        //添加客户端断开连接事件
//        server.addDisconnectListener(new DisconnectListener(){
//            @Override
//            public void onDisconnect(SocketIOClient client) {
//                // TODO Auto-generated method stub
//                String sa = client.getRemoteAddress().toString();
//                String clientIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
//                System.out.println(clientIp+"-------------------------"+"客户端已断开连接");
//                
//                //给客户端发送消息
////                client.sendEvent("sendAddItg",clientIp+"客户端你好，我是服务端，期待下次和你见面");
//            }
//        });
//        server.start();
//	}
//	
//	public void stopServer(){
//        if(server != null){
//            server.stop();
//            server = null;
//        }
//    }
//	
//	 /**
//     *  给所有连接客户端推送消息
//     * @param eventType 推送的事件类型
//     * @param message  推送的内容
//     */
//	public void sendMessageToAllClient(String eventType,MessageEntity message){
//        Collection<SocketIOClient> clients = server.getAllClients();
//        for(SocketIOClient client: clients){
//            client.sendEvent(eventType,message);
//        }
//    }
//	
//	/**
//     * 给具体的客户端推送消息
//     * @param deviceId 设备类型
//     * @param eventType推送事件类型
//     * @param message 推送的消息内容
//     */
//    public void sendMessageToOneClient(String uuid,String eventType,String message){
//        try {
//            if(uuid != null && !"".equals(uuid)){
//                SocketIOClient client = (SocketIOClient)clientsMap.get(uuid);
//                if(client != null){
//                    client.sendEvent(eventType,message);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public static SocketIOServer getServer() {
//        return server;
//    }
//}
