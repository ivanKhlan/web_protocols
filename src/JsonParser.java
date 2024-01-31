
import java.io.IOException;

import java.net.URI;
import java.util.List;


public class JsonParser {

    public static final String CREATE_USER_URL = "https://jsonplaceholder.typicode.com/users";
    public static final String GET_USER_BY_ID_URL = "https://jsonplaceholder.typicode.com/users/10";
    public static final String GET_USERS_BY_USERNAME = "https://jsonplaceholder.typicode.com/users?username=Karianne";
    public static final String DELETE_USER_BY_URL = "https://jsonplaceholder.typicode.com/users/1";

    public static void main(String[] args) throws IOException, InterruptedException {

        Person person = createDefaultPerson();

        System.out.println(JsonUtil.sendPost(URI.create(CREATE_USER_URL), person));

        final Person person2 = JsonUtil.getPersonId(URI.create(GET_USER_BY_ID_URL));
        System.out.println(person2);

        Person person3 = createDefaultPerson();
        final Person person3CreatedPerson = JsonUtil.sendPost(URI.create(CREATE_USER_URL), person3);

        JsonUtil.sendDelete(URI.create(DELETE_USER_BY_URL), person3CreatedPerson);

        final List<Person> person4 = JsonUtil.getPersonUsername(URI.create(GET_USERS_BY_USERNAME));
        System.out.println(person4);

        JsonUtil.writeToFile();

        JsonUtil.getPersonTodos(9);

    }

    public static Person createDefaultPerson() {
        return
                new Person(12,
                        "ivan",
                        "khlan",
                        "ivan@gmail.com",
                        new Address("polska", "11", "kyiv", "49000",
                                new Geo("65.3", "70.1")),
                        "380671554433",
                        "www.news.com.ua",
                        new Company("Yokr", "java", "more"));
    }

    public static Person createDefaultPersonWithoutId() {

        return
                new Person(
                        "ivan",
                        "khlan",
                        "ivan@gmail.com",
                        new Address("polska", "11", "kyiv", "49000",
                                new Geo("65.3", "70.1")),
                        "380671554433",
                        "www.news.com.ua",
                        new Company("Yokr", "java", "more"));
    }
}
