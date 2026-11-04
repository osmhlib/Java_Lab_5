package service;

import exception.InvalidRecordException;
import model.GradeRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class GradeImporter {

    private final GradeParser parser = new GradeParser();

    public void importFile(Path inputPath, Path outputPath) {

        int imported = 0;
        int rejected = 0;

        long sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        try (
                BufferedReader reader =
                        Files.newBufferedReader(
                                inputPath,
                                StandardCharsets.UTF_8
                        )
        ) {

            // пропускаємо заголовок
            reader.readLine();

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                if (line.isBlank()) {
                    System.out.println(
                            "Рядок " + lineNumber + ": порожній рядок"
                    );
                    rejected++;
                    continue;
                }

                try {

                    GradeRecord record = parser.parse(line);

                    imported++;

                    int grade = record.getGrade();

                    sum += grade;

                    if (grade < min) {
                        min = grade;
                    }

                    if (grade > max) {
                        max = grade;
                    }

                } catch (InvalidRecordException e) {

                    rejected++;

                    System.out.println(
                            "Рядок " + lineNumber + ": " + e.getMessage()
                    );
                }
            }

            writeReport(
                    outputPath,
                    imported,
                    rejected,
                    sum,
                    min,
                    max
            );

            System.out.println();
            System.out.println(
                    "Імпортовано: " + imported +
                            ", відхилено: " + rejected +
                            ". Звіт записано у " + outputPath
            );

        } catch (NoSuchFileException e) {

            System.out.println(
                    "Помилка: файл " + inputPath + " не знайдено"
            );

        } catch (IOException e) {

            System.out.println(
                    "Помилка роботи з файлом: " + e.getMessage()
            );
        }
    }

    private void writeReport(
            Path outputPath,
            int imported,
            int rejected,
            long sum,
            int min,
            int max
    ) throws IOException {

        try (
                BufferedWriter writer =
                        Files.newBufferedWriter(
                                outputPath,
                                StandardCharsets.UTF_8
                        )
        ) {

            writer.write("Звіт імпорту");
            writer.newLine();

            writer.write("Кількість записів: " + imported);
            writer.newLine();

            writer.write("Відхилено: " + rejected);
            writer.newLine();

            if (imported > 0) {

                double average = (double) sum / imported;

                writer.write(
                        String.format(
                                "Середній бал: %.2f",
                                average
                        )
                );
                writer.newLine();

                writer.write("Мінімальний бал: " + min);
                writer.newLine();

                writer.write("Максимальний бал: " + max);
                writer.newLine();

            } else {

                writer.write("Коректні записи відсутні");
                writer.newLine();
            }
        }
    }
}