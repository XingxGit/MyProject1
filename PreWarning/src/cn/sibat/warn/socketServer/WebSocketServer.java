package cn.sibat.warn.socketServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketold")
public class WebSocketServer {
	public static Map<String,Session> userSessionMap = new HashMap<String,Session>();
	/**
	 * 
	 * @param relationId
	 * @param userCode
	 * @param session
	*/
	@OnOpen
	 public void onOpen(
//			 @PathParam("user_id") String user_id,Session session
	 ){
		System.out.println("use onopen");
//		userSessionMap.put(user_id, session);
	
	}
	 
	/**
	 *
	 * @param relationId
	 * @param userCode
	 * @param message
	 * @return
	 * @throws IOException 
	*/
	@OnMessage
	 public void onMessage(String message, Session session) throws IOException {
		Session querySession = userSessionMap.get(message);
		System.out.println(message);
		querySession.getBasicRemote().sendText(message+" 传来消息");
		final Set<Session> sessions = session.getOpenSessions();
        //客户端互相发送消息
        for (Session sessionp : sessions) {
            sessionp.getBasicRemote().sendText(message);
        }
		session.getBasicRemote().sendText("消息已发送");
	}
	 
	/**
	 * 
	 * @param relationId
	 * @param userCode
	 * @param session
	*/
	@OnError
	 public void onError(
	 Throwable throwable,
	 Session session) {
	
	}
	 
	/**
	 * 
	 * @param relationId
	 * @param userCode
	 * @param session
	*/
	@OnClose
	 public void onClose(
	 Session session) {
		
	 
	}

}
