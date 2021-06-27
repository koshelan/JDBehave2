import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Frog frog = new Frog();
        System.out.println(frog);

        List<FrogCommand> commands = new ArrayList<>();
        int curCommand = -1;

        while (true) {
            System.out.println();
            printCommandTypes();
            input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }

            //считываем ввод и конструируем комманду, если
            //пользователь не хотел выйти
            try {
                if (input.equals("<<")/*пользователь хочет отменить действие*/) {
                    if (curCommand < 0) {
                        System.out.println("Нечего отменять!");
                    } else {
                        commands.get(curCommand).undo();
                        curCommand--;
                    }
                } else if (input.equals(">>")/*пользователь хочет повторить ОТМЕНЁННОЕ действие*/) {
                    if (curCommand == commands.size() - 1) {
                        System.out.println("Нечего повторять!");
                    } else {
                        curCommand++;
                        commands.get(curCommand).todo();
                    }
                } else if (input.equals("!!")/*пользователь хочет повторить действие*/) {
                    if (curCommand < 0) {
                        System.out.println("Нечего повторять!");
                    } else {
                        FrogCommand cmd = commands.get(curCommand);
                        if (cmd.todo()) {
                            while (curCommand != commands.size() - 1) {
                                //удаляем все команды которые были отменены
                                commands.remove(curCommand + 1);
                            }
                            commands.add(cmd);
                            curCommand++;
                        } else {
                            System.out.println("Невозможно повторить операцию: выход за границы диапазона");
                        }
                    }
                } else { //пользователь ввёл новое движение для лягушки
                    while (curCommand != commands.size() - 1) {
                        //удаляем все команды которые были отменены
                        commands.remove(curCommand + 1);
                    }
                    int x = Integer.parseInt(input);
                    FrogCommand cmd = new FrogCommands().jumpCommand(frog, x);
                    if (cmd.todo()) {
                        curCommand++;
                        commands.add(cmd);
                    } else {
                        System.out.println("операция не удалась: выход за границы диапазона");
                    }
                }

            } catch (Exception e) {
                System.out.println("Введены некоректные данные : " + e.getMessage());
            }

            System.out.println(frog);
        }
    }

    private static void printCommandTypes() {
        System.out.println("Введите команду (\n+N - прыгни на N шагов направо\n" +
                "-N - прыгни на N шагов налево\n" +
                "<< - Undo (отмени последнюю команду)\n" +
                ">> - Redo (повтори отменённую команду)\n" +
                "!! - повтори последнюю команду\n" +
                "0 - выход))");
    }
}
