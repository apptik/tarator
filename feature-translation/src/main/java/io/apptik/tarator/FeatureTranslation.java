package io.apptik.tarator;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gherkin.AstBuilder;
import gherkin.ast.GherkinDocument;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;

public class FeatureTranslation {

    private static gherkin.Parser<GherkinDocument> parser = new gherkin.Parser<>(new AstBuilder());
    private static Map<String, Map<String, String>> translationsItem = new HashMap<>();
    private static Pattern pattern = Pattern.compile("\"#[^\"]*\"");

    public static void main(String[] args) throws IOException {

        translationsItem = getTranslations();

        File[] files = new File("app/src/androidTest/assets/features/local").listFiles();


    }

    private static Map<String, Map<String, String>> getTranslations() throws IOException {
        Map<String, Map<String, String>> res = new HashMap<>();

        String translations = new String (Files.readAllBytes(Paths.get
                ("feature-translation/features/remote/translations.txt")));

        BufferedReader bufReader = new BufferedReader(new StringReader(translations));
        String line;
        String currItem = null;
        String currItemLang = null;
        Map<String, String> transItem = new HashMap<>();

        while( (line=bufReader.readLine()) != null ) {
            line = line.trim();

            // title
            if (line.startsWith("#")) {
                if (currItem != null) {
                    res.put(currItem, transItem);
                    transItem = new HashMap<>();
                }
                currItem = line.substring(1);
            }
            // language line
            else if (line.length() >= 4 &&
                    IsoUtil.isValidISOLanguage(line.substring(0, 2)) &&
                    line.substring(0, 4).contains(":")) {
                currItemLang = line.substring(0, line.indexOf(":"));
                transItem.put(currItemLang, line.substring(line.indexOf(":")+1));
            }
            // in case multiple lines
            else if (line.length() > 0) {
                if (currItemLang == null) throw new RuntimeException("error parsing");
                transItem.put(currItemLang, transItem.get(currItemLang) + " " +
                line);
            }
        }


        return res;
    }



    private static void formatAllFiles(final File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                formatAllFiles(file.listFiles());
            } else {
                try {
                    String strings = new String (Files.readAllBytes(Paths.get(file.getPath
                            ())));
                    GherkinDocument gherkinDocument = parser.parse(strings);
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);

                    for (final ScenarioDefinition scenario: gherkinDocument.getFeature()
                            .getChildren()) {

                        System.out.println(scenario.getName());

                        for (Step step : scenario.getSteps()) {
                            Matcher mat = pattern.matcher(step.getText());

                            while (mat.find()) {
                                System.out.println("mat found : " + mat.group() );
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static final class IsoUtil {
        private static final Set<String> ISO_LANGUAGES = new HashSet<String>
                (Arrays.asList(Locale.getISOLanguages()));

        private IsoUtil() {}

        public static boolean isValidISOLanguage(String s) {
            return ISO_LANGUAGES.contains(s.toLowerCase());
        }

    }

}
