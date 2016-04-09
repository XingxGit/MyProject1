package cn.sibat.warn.socketServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/websocket/{user_id}")
public class WebSocketTest {
    /**
     * 存储当前有效的session对象
     */
	private static Map<String, Session> sessionMap = new HashMap<String, Session>();
    private static Queue<Session> sessionSet = new ConcurrentLinkedQueue<Session>();
    
    @OnMessage
    public void onMessage(String message, Session currentSession) {
        System.out.println("Server say：Received: " + message);
        try {
        	String[] s = message.split(":");
        	System.out.println(s[0]);
        	Session gSession = sessionMap.get(s[0]);
        	if(gSession!=null){
        	gSession.getBasicRemote().sendText(s[1]);
        	}else{
        		currentSession.getBasicRemote().sendText("错误");
        	}
        	
        	
        	
        	
//        	System.out.println(currentSession==sessionSet.peek());
            //客户端互相发送消息，拿到的所有session是自己存储的
//            for (Session session : sessionSet) {
//            	session.getBasicRemote().sendText(message);
//			}
//        	currentSession.getBasicRemote().sendText("服务器返回数据");
            //客户端互相发送消息，拿到的所有已经开启的session，但是有的时候在有些时候取到的长度只为1
            /*final Set<Session> sessions = currentSession.getOpenSessions();
            System.out.println("sessions个数：" + sessions.size());
            for (Session session : sessions) {
                session.getBasicRemote().sendText(message);
            }*/
            /************启动定时公告**************/
          
            /**************************/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnOpen
    public void onOpen(@PathParam("user_id") String user_id,Session currentSession) {
//        if(sessionSet.contains(currentSession) == false){
//            sessionSet.add(currentSession);
    		System.out.println("user_id+ "+user_id);
            System.out.println("WebSocketTest.onOpen()========Add=" + sessionMap.size());
//        }
//        System.out.println("Server say：Client connected");
    	sessionMap.put(user_id, currentSession);
    }
    @OnClose
    public void onClose(Session currentSession) {
        if(sessionSet.contains(currentSession)){
            sessionSet.remove(currentSession);
        }
        System.out.println("Server say：Connection closed============Close=" + sessionMap.size());
    }
    
    @OnError
    public void onError(Session currentSession , Throwable throwable) {
    	System.out.println("Server error..." + throwable.getMessage());
    	onClose(currentSession);
    }
    public static Queue<Session> getSessionSet() {
        return sessionSet;
    }
    
}