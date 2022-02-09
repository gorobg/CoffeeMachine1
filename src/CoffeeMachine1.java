import java.util.Scanner;

public class CoffeeMachine1 {
    // enums for marking and choosing states
    public enum State {
        CHOOSING_ACTION, CHOOSING_COFFEE,
        REFILLING_WATTER, REFILLING_MILK,
        REFILLING_BEANS, REFILLING_CUPS
    }

    // constants for required products for the different drinks
    static final int WATER_PER_COFFEE = 250;
    static final int WATER_PER_LATTE = 350;
    static final int WATER_PER_CAPPUCCINO = 200;
    static final int MILK_PER_LATTE = 75;
    static final int MILK_PER_CAPPUCCINO = 100;
    static final int BEANS_PER_COFFEE = 16;
    static final int BEANS_PER_LATTE = 20;
    static final int BEANS_PER_CAPPUCCINO = 12;
    // initial resources in the machine
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;
    private State state = State.CHOOSING_ACTION;
    private static boolean exit = false;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachine1 coffeeMachine1 = new CoffeeMachine1();
        while (!exit) {
            coffeeMachine1.printState();
            coffeeMachine1.inputInterpreter(scanner.next());
        }
    }

    public void inputInterpreter(String userInput) {
        switch (this.state) {
            case CHOOSING_COFFEE:
                buyAction(userInput);
                break;
            case REFILLING_WATTER:
                fillAction(userInput);
                break;
            case REFILLING_MILK:
                fillAction(userInput);
                break;
            case REFILLING_BEANS:
                fillAction(userInput);
                break;
            case REFILLING_CUPS:
                fillAction(userInput);
                break;
            default:
                chooseAction(userInput);
                break;
        }
    }

    private void printState() {
        switch (this.state) {
            case CHOOSING_COFFEE:
                System.out.printf("%nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu%n");
                break;
            case REFILLING_WATTER:
                System.out.printf("%nWrite how many ml of water you want to add:%n");
                break;
            case REFILLING_MILK:
                System.out.printf("Write how many ml of milk you want to add:%n");
                break;
            case REFILLING_BEANS:
                System.out.printf("Write how many grams of coffee beans you want to add:%n");
                break;
            case REFILLING_CUPS:
                System.out.printf("Write how many disposable cups of coffee you want to add:%n");
                break;
            default:
                System.out.printf("Write action (buy, fill, take, remaining, exit): %n");
                break;
        }
    }

    public void printAvailableResources() {
        System.out.printf("%nThe coffee machine has: %n%d ml of water%n%d ml of milk" +
                "%n%d g of coffee beans%n%d disposable cups%n$%d of money%n%n", this.water, this.milk, this.beans, this.cups, this.money);
    }

    public void buyAction(String input) {
        String insufficientResource = "";  //empty string to store and print if there are insufficient resources
        switch (input) {
            case "1":
                insufficientResource = water - WATER_PER_COFFEE < 0 ? "water" :
                        beans - BEANS_PER_COFFEE < 0 ? "coffee beans" :
                                cups < 1 ? "disposable cups" : insufficientResource;
                if (insufficientResource.isEmpty()) {
                    water -= WATER_PER_COFFEE;
                    beans -= BEANS_PER_COFFEE;
                    money += 4;
                    cups -= 1;
                }
                break;
            case "2":
                insufficientResource = water - WATER_PER_LATTE < 0 ? "water" :
                        milk - MILK_PER_LATTE < 0 ? "milk" :
                                beans - BEANS_PER_LATTE < 0 ? "coffee beans" :
                                        cups < 1 ? "disposable cups" : insufficientResource;
                if (insufficientResource.isEmpty()) {
                    water -= WATER_PER_LATTE;
                    milk -= MILK_PER_LATTE;
                    beans -= BEANS_PER_LATTE;
                    money += 7;
                    cups -= 1;
                }
                break;
            case "3":
                insufficientResource = water - WATER_PER_CAPPUCCINO < 0 ? "water" :
                        milk - MILK_PER_CAPPUCCINO < 0 ? "milk" :
                                beans - BEANS_PER_CAPPUCCINO < 0 ? "coffee beans" :
                                        cups < 1 ? "disposable cups" : insufficientResource;
                if (insufficientResource.isEmpty()) {
                    water -= WATER_PER_CAPPUCCINO;
                    milk -= MILK_PER_CAPPUCCINO;
                    beans -= BEANS_PER_CAPPUCCINO;
                    money += 6;
                    cups -= 1;
                }
                break;
            case "back":
                insufficientResource = "back";
                break;
            default:
                insufficientResource = "Invalid";
                break;
        }
        System.out.printf(insufficientResource.isEmpty() ? "I have enough resources, preparing your beverage!%n%n" :
                insufficientResource.equals("Invalid") ? "Invalid input!%n%n" :
                        insufficientResource.equals("back") ? "%n" : "Sorry, not enough, %s!%n%n", insufficientResource);
        this.state = State.CHOOSING_ACTION;
    }

    public void fillAction(String input) {
        try {
            int inp = Integer.parseInt(input);
            switch (this.state) {
                case REFILLING_WATTER:
                    this.water += inp;
                    this.state = State.REFILLING_MILK;
                    break;
                case REFILLING_MILK:
                    this.milk += inp;
                    this.state = State.REFILLING_BEANS;
                    break;
                case REFILLING_BEANS:
                    this.beans += inp;
                    this.state = State.REFILLING_CUPS;
                    break;
                case REFILLING_CUPS:
                    this.cups += inp;
                    this.state = State.CHOOSING_ACTION;
                    System.out.println();
                    break;
                default:
                    break;
            }
        }catch (NumberFormatException nfe){
            System.out.println("Invalid input. Please enter a valid integer number");
            return;
        }

    }

    public void takeIncomeAction() {
        System.out.printf("%nI gave you: %d%n%n", money);
        money -= money;
    }

    public void chooseAction(String input) {
        switch (input) {
            case "buy":
                this.state = State.CHOOSING_COFFEE;
                break;
            case "fill":
                this.state = State.REFILLING_WATTER;
                break;
            case "take":
                takeIncomeAction();
                break;
            case "remaining":
                printAvailableResources();
                break;
            case "exit":
                exit = true;
                break;
            default:
                System.out.println("Invalid action. Should write buy, fill, take, remaining or exit.");
                break;
        }
    }
}
