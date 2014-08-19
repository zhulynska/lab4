/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.sumdu.j2ee.Zhulynska.mdb;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Remote;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
            propertyName = "destination", propertyValue = "queue/MyQueue")})

public class MessageBean implements MessageListener{

    @PersistenceContext(unitName = "loggs")
    private EntityManager manager;

    public MessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                System.out.println("MDB " + msg.getText());
                MessageEntity some = new MessageEntity();
                some.setMessage(msg.getText());
                manager.persist(some);
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public List<MessageEntity> showAll() throws RemoteException {
        return manager.createQuery("from MessageEntity m").getResultList();
    }*/
}
