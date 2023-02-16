package com.ortegapp.validation.validator;

import com.ortegapp.validation.annotation.PasswodsMatch;
import com.ortegapp.validation.annotation.PhoneNumber;
import com.ortegapp.validation.annotation.StrongPassword;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    int min, max;
    boolean number;

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        number = constraintAnnotation.hasNumber();

    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Rule> rules = new ArrayList<>();

        rules.add(new LengthRule(min, max));

        if (number)
            rules.add(new CharacterRule(EnglishCharacterData.Digit, 8));
        PasswordValidator phoneValidator = new PasswordValidator(rules);

        RuleResult result = phoneValidator.validate(new PasswordData(value));

        if (result.isValid())
            return true;

        return false;



    }
}
