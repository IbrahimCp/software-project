package kz.ibrahim.SoftwareProject.external;

import com.google.gson.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


@Component
public class CodeForcesService {

    private final String URL = "https://codeforces.com/api/";
    private final RestTemplate restTemplate = new RestTemplate();

    public JsonObject getApiResponse(String apiUrl) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return JsonParser.parseString(Objects.requireNonNull(responseEntity.getBody())).getAsJsonObject();
        } else {
            throw new RuntimeException("HTTP Request Failed. Response Code: " + responseEntity.getStatusCodeValue());
        }
    }
    private LocalDateTime unixToDate(Long unixTime) {
        return LocalDateTime.ofEpochSecond(unixTime, 0, ZoneOffset.ofHours(5));
    }

    public List<String> getUserRanks(String contestURL, List<String> handles) throws IOException {
        if (handles.isEmpty()) {
            return new ArrayList<>();
        }
        TreeMap<String, Integer> userRank = new TreeMap<>();

        String[] p = contestURL.split("/");
        StringBuilder APIUrl = new StringBuilder(URL + "contest.standings?contestId=" + p[p.length - 1] + "&handles=");
        for (int i = 0; i < handles.size(); i++) {
            APIUrl.append(handles.get(i));
            if (i != handles.size() - 1) {
                APIUrl.append(";");
            }
        }
        System.out.println(APIUrl);
        JsonObject response = getApiResponse(APIUrl.toString());

        for (String s : handles) {
            int rank = getHandleRank(response, s);
            System.out.println(rank);
            if (rank != -1) {
                userRank.put(s, rank);
            }
        }

        return new ArrayList<>(userRank.descendingKeySet());
    }

    public Map<String, LocalDateTime> getSolvedProblems(String handle, List<String> problems) throws IOException {
        System.out.println("before api " + unixToDate(System.currentTimeMillis() / 1000));
        Map<String, LocalDateTime> result = new HashMap<>();

        String APIUrl = URL + "user.status?handle=" + handle;

        JsonArray jsonArray = getApiResponse(APIUrl).getAsJsonArray("result");
        System.out.println("after api " + unixToDate(System.currentTimeMillis() / 1000));

        HashSet<String> problemNames = new HashSet<>();

        for (String problem : problems) {
            String[] p = problem.split("/");
            problemNames.add(p[p.length - 3] + p[p.length - 1]);
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject problem = jsonArray.get(i).getAsJsonObject();

            if (problem.has("contestId") && problem.get("verdict").getAsString().equals("OK")) {
                String contestId = problem.get("contestId").getAsString();
                String problemId = problem.get("problem").getAsJsonObject().get("index").getAsString();

                if (problemNames.contains(contestId + problemId)) {
                    String url = "https://codeforces.com/contest/" + contestId + "/problem/" + problemId;
                    result.put(url, unixToDate(problem.get("creationTimeSeconds").getAsLong()));
                }
            }
        }

        System.out.println("after: " + unixToDate(System.currentTimeMillis() / 1000));

        return result;
    }

    public LocalDateTime getContestStartTime(String contestURL) throws IOException {
        String APIUrl = URL + "contest.list";

        String[] URLDetails = contestURL.split("/");
        String contestId = URLDetails[URLDetails.length - 1];

        JsonObject response = getApiResponse(APIUrl);

        JsonArray jsonArray = response.get("result").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            String currentContestId = object.getAsJsonObject().get("id").getAsString();
            if (currentContestId.equals(contestId)) {
                return unixToDate(object.getAsJsonObject().get("startTimeSeconds").getAsLong());
            }
        }
        return null;
    }

    public String getContestName(String contestURL) throws IOException {
        String APIUrl = URL + "contest.list";

        String[] URLDetails = contestURL.split("/");
        String contestId = URLDetails[URLDetails.length - 1];

        JsonObject response = getApiResponse(APIUrl);
        JsonArray jsonArray = response.get("result").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            String currentContestId = object.getAsJsonObject().get("id").getAsString();
            if (currentContestId.equals(contestId)) {
                return object.getAsJsonObject().get("name").getAsString();
            }
        }
        return null;
    }

    private int getHandleRank(JsonObject data, String handle) {
        JsonArray rows = data.getAsJsonObject("result").getAsJsonArray("rows");

        for (int i = 0; i < rows.size(); i++) {
            JsonObject row = rows.get(i).getAsJsonObject();

            if (row.has("party") && row.getAsJsonObject("party").has("members")) {
                JsonArray members = row.getAsJsonObject("party").getAsJsonArray("members");

                for (int j = 0; j < members.size(); j++) {
                    JsonObject member = members.get(j).getAsJsonObject();

                    if (member.has("handle") && member.get("handle").getAsString().equals(handle)) {
                        return row.get("rank").getAsInt();
                    }
                }
            }
        }

        return -1;
    }

    public String problemName(String url) throws IOException{
        String APIUrl = URL + "problemset.problems";

        String[] URLDetails = url.split("/");
        String contestId = URLDetails[URLDetails.length - 3];
        String problemIndex = URLDetails[URLDetails.length - 1];


        JsonObject response = getApiResponse(APIUrl);
        JsonArray jsonArray = response.get("result").getAsJsonObject().get("problems").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            String currentContestId = object.getAsJsonObject().get("contestId").getAsString();
            String currentContestIndex = object.getAsJsonObject().get("index").getAsString();
            if (currentContestId.equals(contestId) && currentContestIndex.equals(problemIndex)) {
                return object.getAsJsonObject().get("name").getAsString();
            }
        }
        return null;
    }
}