package ru.otus.testing.shell;

import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.testing.exception.UnsupportedLocalException;
import ru.otus.testing.service.LocalizationService;
import ru.otus.testing.service.SurveyService;

import java.util.Locale;

@ShellComponent
public class SurveyCommands {

    private final SurveyService surveyService;
    private final LocalizationService localizationService;
    private final MessageSource messageSource;

    public SurveyCommands(SurveyService surveyService, LocalizationService localizationService, MessageSource messageSource) {
        this.surveyService = surveyService;
        this.localizationService = localizationService;
        this.messageSource = messageSource;
    }


    @ShellMethod(value = "init local shel command", key = {"l", "locale"})
    public String initLocale(String localName) {
        try {
            localizationService.setCurrentLocale(getLocale(localName));
            return messageSource.getMessage("selected.local", null, this.localizationService.getCurrentLocale());
        } catch (UnsupportedLocalException e) {
            String errorMessage = messageSource.getMessage("unsupported.local", new String[]{e.getUnsupportedLocalName()}, Locale.forLanguageTag("ru-RU")) +
                    " / " + messageSource.getMessage("unsupported.local", new String[]{e.getUnsupportedLocalName()}, Locale.forLanguageTag("en-US"));
            return errorMessage;
        }
    }

    @ShellMethod(value = "survey run", key = {"run", "test"})
    @ShellMethodAvailability(value = "isRunTestCommandAvailable")
    public void runTest() {
        surveyService.runTest();
    }

    private Availability isRunTestCommandAvailable() {
        if (localizationService.getCurrentLocale() == null) {
            String errorMessage = messageSource.getMessage("local.notselected", null, Locale.forLanguageTag("ru-RU")) +
                    " / " + messageSource.getMessage("local.notselected", null, Locale.forLanguageTag("en-US"));
            return Availability.unavailable(errorMessage);
        }
        return Availability.available();
    }

    private Locale getLocale(String localName) throws UnsupportedLocalException {
        if (localName.toLowerCase().equals("en")) {
            return Locale.forLanguageTag("en-US");
        }
        if (localName.toLowerCase().equals("ru")) {
            return Locale.forLanguageTag("ru-RU");
        }
        throw new UnsupportedLocalException(localName);
    }
}
