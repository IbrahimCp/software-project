package kz.ibrahim.SoftwareProject.util;

import io.micrometer.common.lang.NonNullApi;
import kz.ibrahim.SoftwareProject.models.Contest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NonNullApi
@Component
public class ContestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Contest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contest contest = (Contest) target;
        String contestUrl = contest.getUrl();
        if (!isValidCodeforcesUrl(contestUrl)) {
            errors.rejectValue("url","", "non valid contest url");
        }
    }

    public static boolean isValidCodeforcesUrl(String url) {
        String pattern = "https://codeforces\\.com/contest/\\d+";
        Pattern compiledPattern = Pattern.compile(pattern);

        Matcher matcher = compiledPattern.matcher(url);
        return matcher.matches();
    }

}
