package kz.ibrahim.SoftwareProject.util;

import io.micrometer.common.lang.NonNullApi;
import kz.ibrahim.SoftwareProject.models.Contest;
import kz.ibrahim.SoftwareProject.models.Problem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.*;

@NonNullApi
@Component
public class ProblemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Problem.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Problem problem = (Problem) target;
        String problemUrl = problem.getUrl();
        System.out.println(problemUrl);
        System.out.println(isValidCodeforcesUrl(problemUrl));
        if (!isValidCodeforcesUrl(problemUrl)) {
            errors.rejectValue("url", "","non valid problem url");
        }
    }

    public static boolean isValidCodeforcesUrl(String url) {
        // Define the pattern for Codeforces URL
        String pattern = "https://codeforces\\.com/contest/\\d+/problem/[A-Z]";

        // Compile the pattern
        Pattern compiledPattern = Pattern.compile(pattern);

        // Match the input URL with the pattern
        Matcher matcher = compiledPattern.matcher(url);

        // Check if the URL matches the pattern
        return matcher.matches();
    }
}
