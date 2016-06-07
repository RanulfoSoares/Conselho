package br.com.conselho.util;

/**
 *
 * @author Thiago Henrique
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator {

	
    public void validate(String object) throws ValidatorException {

        String enteredEmail = (String) object.toString().toLowerCase();
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(enteredEmail);

        boolean matchFound = m.matches();

        if (!matchFound) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO! ", "E-mail Incorreto"));
        }
    }

    public static boolean validarEmail(String email) {

        String enteredEmail = email.toString().toLowerCase();
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(enteredEmail);

        boolean matchFound = m.matches();
        if (!matchFound) {
            return true;
        }
        return false;

    }
}
