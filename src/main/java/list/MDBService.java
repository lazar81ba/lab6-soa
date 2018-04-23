package list;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MDBService implements MessageListener{
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.print(tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
