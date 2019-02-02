package api.aspects;

import api.exceptions.WrongDataException;
import api.model.User;
import api.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckCurrentUserAspect {

    private final UserRepository repository;

    public CheckCurrentUserAspect(UserRepository repository) {
        this.repository = repository;
    }

    @Before("@annotation(api.annotations.CheckAuthorities)")
    public void checkCurrentUserById(JoinPoint joinPoint) {

        CodeSignature signature
                = (CodeSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();

        int index = -1;
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("userId")) {
                index = i;
            }
        }

        Object[] args = joinPoint.getArgs();

        Integer userId = null;

        if (index != -1 && args[index] instanceof Integer) {
            userId = (Integer) args[index];
        }

        if (!checkUserById(userId)) {
            throw new WrongDataException("User id is incorrect");
        }

    }

    public boolean checkUserById(Integer userId) {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();
                User user = repository.findByUsername(username);

                return user.getId().equals(userId);
            }
        }
        return false;
    }

}