package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Yaris");
        car.setCarColor("Black");
        car.setCarQuantity(5);
    }

    @Test
    void testCreate() {
        Car savedCar = carService.create(car);

        verify(carRepository, times(1)).create(car);

        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> carIterator = carList.iterator();

        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> result = carService.findAll();

        verify(carRepository, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(car.getCarId(), result.get(0).getCarId());
    }

    @Test
    void testFindById() {

        when(carRepository.findById(car.getCarId())).thenReturn(car);

        Car result = carService.findById(car.getCarId());

        verify(carRepository, times(1)).findById(car.getCarId());
        assertNotNull(result);
        assertEquals(car.getCarId(), result.getCarId());
        assertEquals(car.getCarName(), result.getCarName());
    }

    @Test
    void testUpdate() {
        carService.update(car.getCarId(), car);

        verify(carRepository, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById(car.getCarId());

        verify(carRepository, times(1)).delete(car.getCarId());
    }
}