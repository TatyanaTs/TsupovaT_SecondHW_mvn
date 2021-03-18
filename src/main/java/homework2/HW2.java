package homework2;

import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.util.concurrent.TimeUnit;

public class HW2 {
    public static void main(String[] args) {

        thread(new HomeWorkProducer(), false);

    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public static class HomeWorkProducer implements Runnable {
        public void run() {
            try {
                // Create a Connection
                Connection connection = new ConnectionImpl();
                connection.start(); // этот метод выводит строку,
                                    //которой нет в результатах ДЗ, но по логике он должен быть

                // Create a Session
                Session session = connection.createSession(true);

                // Create the destination (Topic or Queue)
                Destination destination = session.createDestination("myQueue");

                // Create a MessageProducer from the Session to the Topic or Queue
                Producer producer = session.createProducer(destination);

                String[] messages = new String[] {"Раз", "Два", "Три"};

                // Tell the producer to send the message
                for (int i = 0; i < messages.length; i++) {
                    producer.send(messages[i]);
                    TimeUnit.SECONDS.sleep(2);
                }

                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }
}
