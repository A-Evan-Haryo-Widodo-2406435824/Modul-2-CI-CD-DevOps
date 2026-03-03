package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

@ExtendWith(MockitoExtension.class)
class InMemoryCarRepositoryTest {

    @InjectMocks
    InMemoryCarRepository carRepository;

    @Test
    void testCreateAndFind(){
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testCreateWithNullIdGeneratesUUID(){
        Car car = new Car();
        car.setCarName("Honda Civic");
        car.setCarColor("White");
        car.setCarQuantity(2);

        Car savedCar = carRepository.create(car);

        assertNotNull(savedCar.getCarId());
        assertEquals("Honda Civic", savedCar.getCarName());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar(){
        Car car1 = new Car();
        car1.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car1.setCarName("Mobil 1");
        car1.setCarColor("Merah");
        car1.setCarQuantity(10);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("fb558e9f-1c39-460e-8860-71af6af63bd6");
        car2.setCarName("Mobil 2");
        car2.setCarColor("Biru");
        car2.setCarQuantity(5);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());

        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testEditCar_Success(){
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Toyota Yaris Cross");
        updatedCar.setCarColor("White");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar);

        assertNotNull(result);
        assertEquals("Toyota Yaris Cross", result.getCarName());
        assertEquals("White", result.getCarColor());
        assertEquals(10, result.getCarQuantity());


        Car storedCar = carRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Toyota Yaris Cross", storedCar.getCarName());
        assertEquals("White", storedCar.getCarColor());
        assertEquals(10, storedCar.getCarQuantity());
    }

    @Test
    void testEditCar_Failed_NotFound(){
        Car updatedCar = new Car();
        updatedCar.setCarName("Mobil Hantu");
        updatedCar.setCarColor("Transparan");
        updatedCar.setCarQuantity(0);

        Car result = carRepository.update("id-yang-tidak-ada", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCar_Success(){
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
        carRepository.create(car);

        carRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Car deletedCar = carRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(deletedCar);
    }

    @Test
    void testDeleteCar_Failed_NotFound(){
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
        carRepository.create(car);

        carRepository.delete("id-yang-salah");

        Car remainingCar = carRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(remainingCar);
    }

    @Test
    void testFindByIdFailed(){
        Car car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
        carRepository.create(car);

        Car result = carRepository.findById("id-yang-salah");
        assertNull(result);
    }

    @Test
    void testFindByIdForMoreThanOneCars(){
        Car car1 = new Car();
        car1.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car1.setCarName("Mobil Pertama");

        Car car2 = new Car();
        car2.setCarId("fb558e9f-1c39-460e-8860-71af6af63bd6");
        car2.setCarName("Mobil Kedua");

        carRepository.create(car1);
        carRepository.create(car2);

        Car result = carRepository.findById("fb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(result);
        assertEquals(car2.getCarId(), result.getCarId());
        assertEquals("Mobil Kedua", result.getCarName());
    }
}