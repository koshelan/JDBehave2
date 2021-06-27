public class FrogCommands {

    public static FrogCommand jumpCommand(Frog frog, int steps) {
        return new FrogCommand() {
            @Override
            public boolean todo() {
                return frog.jump(steps);
            }

            @Override
            public boolean undo() {
                return frog.jump(-steps);
            }
        };
        // возвращаете объект команды, у которого
        // если вызвать .do(), то лягушка её выполнит,
        // если вызвать .undo(), то лягушка её отменит
    }


}
