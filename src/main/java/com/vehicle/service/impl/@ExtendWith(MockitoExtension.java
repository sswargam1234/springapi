@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void getVehicleByRegistrationNumber_WhenVehicleExists_ReturnsVehicle() {
        // Arrange
        String registrationNumber = "ABC123";
        Vehicle expectedVehicle = Vehicle.builder()
            .id(1L)
            .registrationNumber(registrationNumber)
            .make("Toyota")
            .model("Camry")
            .year(2020)
            .color("Black")
            .type("Sedan")
            .build();

        when(vehicleRepository.findByRegistrationNumber(registrationNumber))
            .thenReturn(Optional.of(expectedVehicle));

        // Act
        Optional<Vehicle> result = vehicleService.getVehicleByRegistrationNumber(registrationNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedVehicle, result.get());
        verify(vehicleRepository).findByRegistrationNumber(registrationNumber);
    }

    @Test
    void getVehicleByRegistrationNumber_WhenVehicleDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        String registrationNumber = "XYZ789";
        when(vehicleRepository.findByRegistrationNumber(registrationNumber))
            .thenReturn(Optional.empty());

        // Act
        Optional<Vehicle> result = vehicleService.getVehicleByRegistrationNumber(registrationNumber);

        // Assert
        assertTrue(result.isEmpty());
        verify(vehicleRepository).findByRegistrationNumber(registrationNumber);
    }
}