package kz.ibrahim.SoftwareProject.external;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Component
public interface OnlineJudge {

    List<String> getUserRanks(String contestURL, List<String> handles) throws IOException;

    Map<String, LocalDateTime> getSolvedProblems(String handle, List<String> problems) throws IOException;

    LocalDateTime getContestStartTime(String contestURL) throws IOException;

    String getContestName(String contestURL) throws IOException;
    String getProblemName(String problemURL) throws IOException;
}
