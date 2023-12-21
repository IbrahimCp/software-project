package kz.ibrahim.SoftwareProject.external;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CodeForcesAdapter implements OnlineJudge {

    private final CodeForcesService codeForcesService;
    public CodeForcesAdapter(CodeForcesService codeForcesService){
        this.codeForcesService = codeForcesService;
    }

    @Override
    public List<String> getUserRanks(String contestURL, List<String> handles) throws IOException {
        return codeForcesService.getUserRanks(contestURL, handles);
    }

    @Override
    public Map<String, LocalDateTime> getSolvedProblems(String handle, List<String> problems) throws IOException {
        return codeForcesService.getSolvedProblems(handle, problems);
    }

    @Override
    public LocalDateTime getContestStartTime(String contestURL) throws IOException {
        return codeForcesService.getContestStartTime(contestURL);
    }

    @Override
    public String getContestName(String contestURL) throws IOException {
        return codeForcesService.getContestName(contestURL);
    }
}
