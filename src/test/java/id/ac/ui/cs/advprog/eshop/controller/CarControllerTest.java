package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

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
    void testCreateCarPage() {
        String viewName = carController.createCarPage(model);
        verify(model).addAttribute(eq("car"), any(Car.class));
        assertEquals("CreateCar", viewName);
    }

    @Test
    void testCreateCarPost() {
        String viewName = carController.createCarPost(car, model);
        verify(carService).create(car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testCarListPage() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        when(carService.findAll()).thenReturn(carList);

        String viewName = carController.carListPage(model);

        verify(carService).findAll();
        verify(model).addAttribute("cars", carList);

        assertEquals("CarList", viewName);
    }

    @Test
    void testEditCarPage() {
        when(carService.findById(car.getCarId())).thenReturn(car);

        String viewName = carController.editCarPage(car.getCarId(), model);

        verify(carService).findById(car.getCarId());
        verify(model).addAttribute("car", car);

        assertEquals("EditCar", viewName);
    }

    @Test
    void testEditCarPost() {
        String viewName = carController.editCarPost(car, model);

        verify(carService).update(car.getCarId(), car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testDeleteCar() {
        String viewName = carController.deleteCar(car.getCarId());

        verify(carService).deleteCarById(car.getCarId());
        assertEquals("redirect:listCar", viewName);
    }
}