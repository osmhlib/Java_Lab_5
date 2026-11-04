package service;

import exception.InvalidRecordException;
import model.GradeRecord;

public class GradeParser {

    public GradeRecord parse(String line) throws InvalidRecordException {

        String[] parts = line.split(";", -1);

        if (parts.length != 3) {
            throw new InvalidRecordException(
                    "очікувалось 3 поля, отримано " + parts.length
            );
        }

        String studentName = parts[0].trim();
        String subject = parts[1].trim();
        String gradeText = parts[2].trim();

        if (studentName.isEmpty()) {
            throw new InvalidRecordException("ПІБ не може бути порожнім");
        }

        if (subject.isEmpty()) {
            throw new InvalidRecordException("предмет не може бути порожнім");
        }

        int grade;

        try {
            grade = Integer.parseInt(gradeText);
        } catch (NumberFormatException e) {
            throw new InvalidRecordException(
                    "\"" + gradeText + "\" не є цілим числом",
                    e
            );
        }

        if (grade < 0 || grade > 100) {
            throw new InvalidRecordException(
                    "оцінка " + grade + " поза межами 0..100"
            );
        }

        return new GradeRecord(
                studentName,
                subject,
                grade
        );
    }
}