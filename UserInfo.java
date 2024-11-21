import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите ФИО (через пробел): ");
            String fullName = scanner.nextLine().trim();
            String[] nameParts = fullName.split(" ");
            if (nameParts.length != 3) {
                throw new IllegalArgumentException("ФИО должно состоять из трех слов (Фамилия Имя Отчество).");
            }
            System.out.print("Введите дату рождения (в формате дд.мм.гггг или дд/мм/гггг): ");
            String birthDateInput = scanner.nextLine().trim();

            String lastName = nameParts[0];
            String firstName = nameParts[1];
            String patronymic = nameParts[2];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd.MM.yyyy][dd/MM/yyyy]");
            LocalDate birthDate = LocalDate.parse(birthDateInput, formatter);

            LocalDate currentDate = LocalDate.now();

            int age = currentDate.getYear() - birthDate.getYear();
            if (currentDate.getDayOfYear() < birthDate.getDayOfYear()) {
                age--;
            }

            String ageSuffix = getAgeSuffix(age);

            String gender = determineGender(patronymic);

            String initials = String.format("%s %s.%s.", lastName, firstName.charAt(0), patronymic.charAt(0));
            System.out.println("Инициалы: " + initials);
            System.out.println("Пол: " + gender);
            System.out.println("Возраст: " + age + " " + ageSuffix);

        } catch (DateTimeParseException e) {
            System.out.println("Error: неверный формат даты. Убедитесь, что вы используете формат дд.мм.гггг или дд/мм/гггг.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    private static String determineGender(String patronymic) {
        if (patronymic.endsWith("ч")) {
            return "Мужской";
        } else if (patronymic.endsWith("а")) {
            return "Женский";
        } else {
            return "Не удалось определить пол";
        }
    }

    private static String getAgeSuffix(int age) {
        if (age % 100 >= 11 && age % 100 <= 14) {
            return "лет";
        }
        switch (age % 10) {
            case 1:
                return "год";
            case 2:
            case 3:
            case 4:
                return "года";
            default:
                return "лет";
        }
    }
}
