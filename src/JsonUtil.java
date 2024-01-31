import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtil {

    private static final Gson GSON = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final String GET_PERSON_BY_COMMENT = "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String GET_PERSON_TODOS = "https://jsonplaceholder.typicode.com/users/1/todos";


    public static List<Person> sendGet(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Person>>() {
        }.getType());
    }


    public static Person sendPost(URI uri, Person person) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(person);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, BodyHandlers.ofString());

        return GSON.fromJson(response.body(), Person.class);

    }

    public static Person getPersonId(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), Person.class);
    }
    public static List<Person> getPersonUsername(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<Person>>() {}.getType());
    }

    public static void sendDelete(URI uri, Person person) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(person);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        final HttpResponse<String> send = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(send);
    }

    public static List<PersonComment> getPersonComment(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<PersonComment>>() {
        }.getType());
    }

    public static List<PersonTodos> getPersonTodos(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<PersonTodos>>() {
        }.getType());
    }


    public static void writeToFile() throws IOException, InterruptedException {

        int highestId = findBiggestId();

        List<PersonComment> persons = getPersonComment(URI.create(GET_PERSON_BY_COMMENT));
        for (PersonComment person : persons) {
            person.setUserId(highestId);
            if (person.getId() == highestId) {
                String filePath = "user-" + person.getUserId() + "-post-" + person.getId() + "-comments.json";
                FileWriter fileWriter = new FileWriter(filePath);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(person.getBody());
                bufferedWriter.close();
            }

        }
    }

    public static int findBiggestId() throws IOException, InterruptedException {
        final List<Person> allPerson = sendGet(URI.create(JsonParser.CREATE_USER_URL));
        List<Integer> list = new ArrayList<>();
        for (Person person : allPerson) {
            list.add(person.getId());
        }
        return Collections.max(list);
    }


    public static void getPersonTodos(int identifier) throws IOException, InterruptedException {
        List<PersonTodos> persons = getPersonTodos(URI.create(GET_PERSON_TODOS));
        for (PersonTodos person : persons) {
            person.setUserId(identifier);
            if (!person.getCompleted()) {
                System.out.println(person.getTitle());
            }
        }
    }


}
