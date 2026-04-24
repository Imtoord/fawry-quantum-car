interface Engine {
    void start();
    void stop();
    void setSpeed(int speed);
}

enum EngineType { GASOLINE, ELECTRONIC, HYBRID }

class GasolineEngine implements Engine {
    public void start()             { System.out.println("Gasoline engine started"); }
    public void stop()              { System.out.println("Gasoline engine stopped"); }
    public void setSpeed(int speed) { System.out.println("Gasoline engine speed: " + speed); }
}

class ElectronicEngine implements Engine {
    public void start()             { System.out.println("Electric engine started"); }
    public void stop()              { System.out.println("Electric engine stopped"); }
    public void setSpeed(int speed) { System.out.println("Electric engine speed: " + speed); }
}

class MixedHybridEngine implements Engine {
    private GasolineEngine gasEngine = new GasolineEngine();
    private ElectronicEngine electricEngine = new ElectronicEngine();
    private Engine operatingEngine;
    private int speed = 0;

    public void start() {
        operatingEngine = electricEngine;
        electricEngine.start();
    }

    public void stop() {
        operatingEngine.stop();
    }

    public void setSpeed(int speed) {
        if (speed >= 50 && this.speed < 50) {
            gasEngine.start();
            electricEngine.stop();
            operatingEngine = gasEngine;
        } else if (speed < 50 && this.speed >= 50) {
            electricEngine.start();
            gasEngine.stop();
            operatingEngine = electricEngine;
        }
        this.speed = speed;
        operatingEngine.setSpeed(speed);
    }
}

class Car {
    private Engine engine;
    private int speed = 0;

    Car(Engine engine) { this.engine = engine; }

    void setEngine(Engine engine) { this.engine = engine; }

    void start() {
        speed = 0;
        engine.start();
    }

    void stop() {
        if (speed != 0) {
            System.out.println("Can't stop, speed must be 0. Current: " + speed);
            return;
        }
        engine.stop();
    }

    void accelerate() {
        if (speed < 200) {
            speed += 20;
            engine.setSpeed(speed);
        }
    }

    void brake() {
        if (speed > 0) {
            speed -= 20;
            engine.setSpeed(speed);
        }
    }
}

class CarFactory {
    static Car createCar(EngineType type) {
        return new Car(createEngine(type));
    }

    static void installEngine(Car car, EngineType type) {
        car.setEngine(createEngine(type));
    }

    private static Engine createEngine(EngineType type) {
        switch (type) {
            case GASOLINE:   return new GasolineEngine();
            case ELECTRONIC: return new ElectronicEngine();
            case HYBRID:     return new MixedHybridEngine();
            default:         throw new IllegalArgumentException("Unknown type");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("--- Gasoline Car ---");
        Car gasCar = CarFactory.createCar(EngineType.GASOLINE);
        gasCar.start();
        gasCar.accelerate();
        gasCar.accelerate();
        gasCar.brake();
        gasCar.brake();
        gasCar.stop();

        System.out.println("\n--- Electric Car ---");
        Car electricCar = CarFactory.createCar(EngineType.ELECTRONIC);
        electricCar.start();
        electricCar.accelerate();
        electricCar.accelerate();
        electricCar.brake();
        electricCar.brake();
        electricCar.stop();

        System.out.println("\n--- Hybrid Car ---");
        Car hybridCar = CarFactory.createCar(EngineType.HYBRID);
        hybridCar.start();
        hybridCar.accelerate();
        hybridCar.accelerate();
        hybridCar.accelerate();
        hybridCar.brake();
        hybridCar.brake();
        hybridCar.brake();
        hybridCar.stop();

        System.out.println("\n--- Engine Replacement ---");
        Car myCar = CarFactory.createCar(EngineType.GASOLINE);
        myCar.start();
        myCar.accelerate();
        CarFactory.installEngine(myCar, EngineType.HYBRID);
        myCar.start();
        myCar.accelerate();
        myCar.accelerate();
        myCar.accelerate();
        myCar.brake();
        myCar.brake();
        myCar.brake();
        myCar.stop();
    }
}
