package program;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Animal {
    private String name;
    private LocalDate birthDate;
    private List<String> commands;

    public Animal(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.commands = new ArrayList<>();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public String getName() {
        return name;
    }

    public abstract String getType();

    public abstract String getSubType();

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<String> getCommands() {
        return commands;
    }
}

abstract class DomesticAnimal extends Animal {
    public DomesticAnimal(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Домашние";
    }
}

abstract class PackAnimal extends Animal {
    public PackAnimal(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Вьючные";
    }
}

class Cat extends DomesticAnimal {
    public Cat(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Кошка";
    }
}

class Hamster extends DomesticAnimal {
    public Hamster(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Хомяк";
    }
}

class Dog extends DomesticAnimal {
    public Dog(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Собака";
    }
}

class Horse extends PackAnimal {
    public Horse(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Лошадь";
    }
}

class Camel extends PackAnimal {
    public Camel(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Верблюд";
    }
}

class Donkey extends PackAnimal {
    public Donkey(String name, LocalDate birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getSubType() {
        return "Осел";
    }
}

class Counter implements AutoCloseable {
    private int count = 0;
    private boolean closed = false;

    public void add() {
        if (closed) {
            throw new IllegalStateException("Counter is closed");
        }
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void close() {
        closed = true;
    }
}

public class Main {
    private static List<Animal> animals = new ArrayList<>();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1. Завести новое животное");
            System.out.println("2. Изменить тип и подтип животного");
            System.out.println("3. Увидеть список команд всех животных");
            System.out.println("4. Обучить животное новым командам");
            System.out.println("5. Показать всех животных");
            System.out.println("6. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    try (Counter counter = new Counter()) {
                        System.out.println("Введите имя животного:");
                        String name = scanner.nextLine();
                        System.out.println("Выберите тип животного:");
                        System.out.println("1. Домашние");
                        System.out.println("2. Вьючные");
                        int typeChoice = scanner.nextInt();
                        scanner.nextLine();
                        Animal animal;
                        if (typeChoice == 1) {
                            System.out.println("Выберите подтип животного:");
                            System.out.println("1. Кошка");
                            System.out.println("2. Хомяк");
                            System.out.println("3. Собака");
                            int subTypeChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (subTypeChoice == 1) {
                                animal = new Cat(name, null);
                            } else if (subTypeChoice == 2) {
                                animal = new Hamster(name, null);
                            } else if (subTypeChoice == 3) {
                                animal = new Dog(name, null);
                            } else {
                                System.out.println("Неверный выбор. Попробуйте еще раз.");
                                break;
                            }
                        } else if (typeChoice == 2) {
                            System.out.println("Выберите подтип животного:");
                            System.out.println("1. Лошадь");
                            System.out.println("2. Верблюд");
                            System.out.println("3. Осел");
                            int subTypeChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (subTypeChoice == 1) {
                                animal = new Horse(name, null);
                            } else if (subTypeChoice == 2) {
                                animal = new Camel(name, null);
                            } else if (subTypeChoice == 3) {
                                animal = new Donkey(name, null);
                            } else {
                                System.out.println("Неверный выбор. Попробуйте еще раз.");
                                break;
                            }
                        } else {
                            System.out.println("Неверный выбор. Попробуйте еще раз.");
                            break;
                        }
                        System.out.println("Введите дату рождения животного в формате дд.мм.гггг:");
                        String birthDateString = scanner.nextLine();
                        LocalDate birthDate = LocalDate.parse(birthDateString, dateFormatter);
                        animal = animal.getClass().getConstructor(String.class, LocalDate.class).newInstance(animal.getName(), birthDate);
                        animals.add(animal);
                        counter.add();
                        System.out.println("Животное добавлено. Количество добавленных животных: " + counter.getCount());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Введите имя животного:");
                    String name = scanner.nextLine();
                    Animal animal = findAnimalByName(name);
                    if (animal != null) {
                        System.out.println("Выберите новый тип животного:");
                        System.out.println("1. Домашние");
                        System.out.println("2. Вьючные");
                        int typeChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (typeChoice == 1) {
                            System.out.println("Выберите новый подтип животного:");
                            System.out.println("1. Кошка");
                            System.out.println("2. Хомяк");
                            System.out.println("3. Собака");
                            int subTypeChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (subTypeChoice == 1) {
                                animal = new Cat(animal.getName(), animal.getBirthDate());
                            } else if (subTypeChoice == 2) {
                                animal = new Hamster(animal.getName(), animal.getBirthDate());
                            } else if (subTypeChoice == 3) {
                                animal = new Dog(animal.getName(), animal.getBirthDate());
                            } else {
                                System.out.println("Неверный выбор. Попробуйте еще раз.");
                                break;
                            }
                        } else if (typeChoice == 2) {
                            System.out.println("Выберите новый подтип животного:");
                            System.out.println("1. Лошадь");
                            System.out.println("2. Верблюд");
                            System.out.println("3. Осел");
                            int subTypeChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (subTypeChoice == 1) {
                                animal = new Horse(animal.getName(), animal.getBirthDate());
                            } else if (subTypeChoice == 2) {
                                animal = new Camel(animal.getName(), animal.getBirthDate());
                            } else if (subTypeChoice == 3) {
                                animal = new Donkey(animal.getName(), animal.getBirthDate());
                            } else {
                                System.out.println("Неверный выбор. Попробуйте еще раз.");
                                break;
                            }
                        } else {
                            System.out.println("Неверный выбор. Попробуйте еще раз.");
                            break;
                        }
                        animals.removeIf(a -> a.getName().equals(name));
                        animals.add(animal);
                        System.out.println("Тип и подтип животного изменены.");
                    } else {
                        System.out.println("Животное с таким именем не найдено.");
                    }
                    break;
                case 3:
                    for (Animal a : animals) {
                        System.out.println(a.getName() + ":");
                        for (String command : a.getCommands()) {
                            System.out.println(command);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Введите имя животного:");
                    name = scanner.nextLine();
                    animal = findAnimalByName(name);
                    if (animal != null) {
                        System.out.println("Введите команду:");
                        String command = scanner.nextLine();
                        animal.addCommand(command);
                        System.out.println("Команда добавлена.");
                    } else {
                        System.out.println("Животное с таким именем не найдено.");
                    }
                    break;
                case 5:
                    System.out.println("Выберите тип животных для отображения:");
                    System.out.println("1. Домашние");
                    System.out.println("2. Вьючные");
                    System.out.println("3. Все");
                    int showChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (showChoice == 1) {
                        showAnimalsByType("Домашние");
                    } else if (showChoice == 2) {
                        showAnimalsByType("Вьючные");
                    } else if (showChoice == 3) {
                        showAnimalsByType(null);
                    } else {
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                    }
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    private static void showAnimalsByType(String type) {
        System.out.println("Список животных:");
        for (Animal animal : animals) {
            if (type == null || animal.getType().equals(type)) {
                String birthDateString = animal.getBirthDate().format(dateFormatter);
                System.out.println(animal.getName() + " (" + animal.getType() + ", " + animal.getSubType() + ", " + birthDateString + ")");
            }
        }
    }

    private static Animal findAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }
}