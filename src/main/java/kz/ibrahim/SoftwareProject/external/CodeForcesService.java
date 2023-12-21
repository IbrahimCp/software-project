package kz.ibrahim.SoftwareProject.external;

import com.google.gson.*;


import java.io.*;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.net.URL;


public class CodeForcesService {

    private final String URL = "https://codeforces.com/api/";

//    public void main(String[] args) throws IOException {
//        List<String> problems = Arrays.asList("https://codeforces.com/contest/1913/problem/B", "https://codeforces.com/contest/1905/problem/B",
//                "https://codeforces.com/contest/1914/problem/F");
//
//        var cur = getSolvedProblems("ibrm", problems);
//
//        for (Map.Entry<String, LocalDateTime> entry : cur.entrySet()) {
//            String key = entry.getKey();
//            LocalDateTime value = entry.getValue();
//            System.out.println("Key: " + key + ", Value: " + value.toString());
//        }
//
//    }

    private LocalDateTime unixToDate(Long unixTime) {
        return LocalDateTime.ofEpochSecond(unixTime, 0, ZoneOffset.ofHours(6));
    }

    public List<String> getUserRanks(String contestURL, List<String> handles) throws IOException {
        TreeMap<String, Integer> userRank = new TreeMap<>();

        String[] p = contestURL.split("/");
        String APIUrl = URL + "contest.standings?contestId=" + p[p.length - 1];
        JsonObject response = getApiResponse(APIUrl);

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
        Map<String, LocalDateTime> result = new HashMap<>();

        String APIUrl = URL + "user.status?handle=" + handle;

        JsonArray jsonArray = getApiResponse(APIUrl).getAsJsonArray("result");

        List<String> problemNames = new ArrayList<>();

        for (String problem : problems) {
            String[] p = problem.split("/");
            problemNames.add(p[p.length - 3] + p[p.length - 1]);
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject problem = jsonArray.get(i).getAsJsonObject();

            if (problem.get("verdict").getAsString().equals("OK")) {
                String contestId = problem.get("contestId").getAsString();
                String problemId = problem.get("problem").getAsJsonObject().get("index").getAsString();

                if (problemNames.contains(contestId + problemId)) {
                    String url = "https://codeforces.com/contest/" + contestId + "/problem/" + problemId;
                    result.put(url, unixToDate(problem.get("creationTimeSeconds").getAsLong()));
                }
            }
        }

        return result;
    }

    public LocalDateTime getContestStartTime(String contestURL) throws IOException {
        String APIUrl = URL + "contest.list";

        String[] URLDetails = contestURL.split("/");
        String contestId = URLDetails[URLDetails.length - 1];

        JsonObject response = getApiResponse(APIUrl);

        System.out.println(response.toString());
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

    private JsonObject getApiResponse(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } else {
            throw new IOException("HTTP Request Failed. Response Code: " + responseCode);
        }
    }

}