package list;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.*;
import java.io.Serializable;
import java.util.List;

@Stateless
public class JMSService implements Serializable{
    @Resource(mappedName = "java:/ConnectionFactory")
    private TopicConnectionFactory topicConnectionFactory;
    @Resource(mappedName = "java:/ConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;
    @Resource(mappedName = "java:/jms/topic/Lab6topic")
    private Topic topic;
    @Resource(mappedName = "java:/jms/queue/Lab6queue")
    private Queue queue;
    private TopicSession topicSession;
    private QueueSession queueSession;
    private TopicConnection topicConnection;
    private QueueConnection queueConnection;



    public void sendMessageToAll(List<MessageListener> subscribers, String txt) {
        try {
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            for(MessageListener subscriber : subscribers){
                TopicSubscriber sub = topicSession.createSubscriber(topic);
                sub.setMessageListener(subscriber);
            }
            TopicPublisher publisher = topicSession.createPublisher(topic);
            topicConnection.start();
            TextMessage message = topicSession.createTextMessage(txt);
            publisher.send(message);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (topicSession != null) {
                try {
                    topicSession.close();
                    topicSession = null;
                } catch (JMSException e) { e.printStackTrace(); }
            }
        }
    }

    public void sendMessageToOneSubscriber(MessageListener subscriber, String txt) {
        try {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver receiver= queueSession.createReceiver(queue);
            receiver.setMessageListener(subscriber);
            QueueSender publisher = queueSession.createSender(queue);
            queueConnection.start();
            TextMessage message = queueSession.createTextMessage(txt);
            publisher.send(message);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                    queueConnection = null;
                } catch (JMSException e) { e.printStackTrace(); }
            }
        }
    }

}
