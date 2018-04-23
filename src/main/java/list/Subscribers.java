package list;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.MessageListener;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Startup
public class Subscribers implements Serializable{


    public Map<String, MessageListener> getMap() {
        return map;
    }

    private final Map<String, MessageListener> map = new HashMap<>();

    @PostConstruct
    public void setupSubscribers(){
        map.put("MDBService",new MDBService());
        map.put("MDBSecondService", new MDBSecondService());
    }
}
