package secondTask;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class secondTask {

    public static void main(String[] args) {

        try {
            File file = new File(args[0]);

            //создаем BufferedReader для построчного считывания
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));

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

            // считаем сначала первую строку
            String line = reader.readLine();

            while (line != null) {
                producer.send(line); // Tell the producer to send the message
                TimeUnit.SECONDS.sleep(2);

                // считываем следующую строку
                line = reader.readLine();

            }
            reader.close();

            // Clean up
            session.close();
            connection.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DummyException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
