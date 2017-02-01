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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gherkin.AstBuilder;
import gherkin.ast.GherkinDocument;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;

public class FeatureTranslation {

    private static gherkin.Parser<GherkinDocument> parser = new gherkin.Parser<>(new AstBuilder());
    private static Map<String, Map<String, String>> translationsItem = new HashMap<>();


    public static void main(String[] args) {
        translationsItem = getTranslations();

        File[] files = new File("app/src/androidTest/assets/features/local").listFiles();


    }

    private static Map<String, Map<String, String>> getTranslations() {
        Map<String, Map<String, String>> res = new HashMap<>();

        try {
            String translations = new String (Files.readAllBytes(Paths.get
                    ("feature-translation/features/remote/translations.txt")));


            final String[] occ = translations.split("\\s*#\\s*");

            for (String trans : occ) {

                BufferedReader bufReader = new BufferedReader(new StringReader(trans));
                Map<String, String> transItem = new HashMap<>();
                String firstLine=bufReader.readLine();
                String line=null;
                System.out.println("firstLine : -"+ firstLine);

                while( (line=bufReader.readLine()) != null ) {

                    if (line.matches("^[a-zA-Z]{1,6}\\s*:.*")) {
                        transItem.put((line.substring(0, line.indexOf(":"))).trim(),
                                line.substring(line.indexOf(":")+1).trim());
                        System.out.println("trans : -"+ transItem);

                    }
                }

                res.put(firstLine, transItem);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
                            .getChildren()
                            ) {

                        System.out.println(scenario.getName());


                        for (Step step : scenario.getSteps()) {
                            Pattern pattern = Pattern.compile("\"#.*\"");
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

}
