package list;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.jms.MessageListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
public class ListBrokerBean implements Serializable{

    public ListBrokerBean() throws NamingException {
        jndiProperties.put(Context.URL_PKG_PREFIXES,
                "org.jboss.ejb.client.naming");
        jmsService = lookupJMSService();
        subscribers = lookupSubscribers();
    }

    @EJB(mappedName = "java:global/lab6/JMSService!list.JMSService")
    JMSService jmsService;
    @EJB(mappedName = "java:global/lab6/Subscribers!list.Subscribers")
    Subscribers subscribers;
    private final static Hashtable jndiProperties = new Hashtable();


    public Map<String,List<String>> getThematicLists() {
        return thematicLists;
    }

    private final Map<String,List<String>> thematicLists =new HashMap<>();

    public void addNewThematicList(String listName){
        thematicLists.put(listName, new LinkedList<>());
    }

    public List<String> getList(String listName){
        return thematicLists.get(listName);
    }

    public void addNewRecordToList(String listName, String record){
        thematicLists.get(listName).add(record);
    }

    public void sendToAllSubscribers(String text){
       List<MessageListener> list = new ArrayList(subscribers.getMap().values());
       jmsService.sendMessageToAll(list,text);
    }

    public void sendToOneSubscriber(String subscriber, String text){
        MessageListener sub = subscribers.getMap().get(subscriber);
        if(sub!=null)
            jmsService.sendMessageToOneSubscriber(sub,text);
    }

    public List<String> getSubscriberList(){
        return subscribers.getMap().keySet().stream().collect(Collectors.toList());
    }

    private static JMSService lookupJMSService() throws NamingException {
        final Context context = new InitialContext(jndiProperties);
        return (JMSService) context.lookup("java:global/lab6/JMSService!list.JMSService");
    }
    private static Subscribers lookupSubscribers() throws
            NamingException {
        final Context context = new InitialContext(jndiProperties);
        return (Subscribers) context.lookup("java:global/lab6/Subscribers!list.Subscribers");
    }

}
